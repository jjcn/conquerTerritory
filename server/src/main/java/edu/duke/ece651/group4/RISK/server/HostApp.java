package edu.duke.ece651.group4.RISK.server;

import edu.duke.ece651.group4.RISK.shared.Client;
import edu.duke.ece651.group4.RISK.shared.World;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.CyclicBarrier;

public class HostApp {
    ServerSocket hostSocket;
    World theWorld;
    HostState hostState;
    int NumOfPlayers;
    ArrayList<String> playerNames;
    CyclicBarrier barrier;

    /*
    * This constructs the hostApp
    * */
    HostApp(ServerSocket hostSocket){
        this.hostSocket = hostSocket;
        this.hostState = new HostState();
        this.barrier = null;
        this.NumOfPlayers = 0;
        this.theWorld = null;
        this.playerNames = new ArrayList<String>();

    }

    /*
    *  This function is to read init info about this whole game
    *  This need to set up the world, the number of players.
    *  First, we read all info we need to set up a world.
    *  We need to set up the number of players.
    *  We need to assign PlayerID to each block of territories
    *  We need to create ListArray<String> Player Names
    *  We need to make sure all variables related world and players are initialized
    * */

    private void setUpWorld(){

    }



    /*
     * This setup connection between server and clients
     * Will assign a PlayerID to each thread we are creating
     *
     *  */

    private void setUpClients() {
        int PlayerID = 0;
        while(true) {
            try {
                Socket s = hostSocket.accept();
                Client theClient = new Client(s);
                PlayerState playerState = new PlayerState("R");
                hostState.addOnePlayerState(playerState);
                ClientThread theThread = new ClientThread(theWorld, playerNames.get(PlayerID), PlayerID, barrier, playerState,
                        theClient, hostState);

            theThread.run();
            }catch(IOException e){
            }
        }
    }

    /*
    * This will finish all attacks on world and get a final new world.
    * And change the hostSate to "finishUpdateMap"
    * */

    private void finishBattlesOneTurn() {
        while(hostState.isAllPlayersDoneOneTurn()) {
            theWorld.doAllBattles();
            hostState.changeStateTo("finishUpdateMap");
        }
    }

    /*
    * This is the the main function of the game process
    *
    *
    * */
    public void run() {
        setUpWorld();
        barrier = new CyclicBarrier(NumOfPlayers);
        setUpClients();
        while(true) {
            finishBattlesOneTurn();
            if(hostState.isEndGame()) {
                hostState.changeStateTo("endGame");
                break;  // we should decide to quit the game after all thread are quit.
            }
        }
    }

    public static void main(String[] args) throws IOException {
        ServerSocket hostSocket = new ServerSocket(9999);
        HostApp hostApp = new HostApp(hostSocket);
        hostApp.run();
    }
}

