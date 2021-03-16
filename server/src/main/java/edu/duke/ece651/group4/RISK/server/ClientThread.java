package edu.duke.ece651.group4.RISK.server;

import edu.duke.ece651.group4.RISK.shared.Client;
import edu.duke.ece651.group4.RISK.shared.World;

import java.util.concurrent.CyclicBarrier;

public class ClientThread extends Thread{
    World theWorld;
    PlayerState playerState;
    HostState hostState;
    Client theClient;
    String playerName;
    CyclicBarrier barrier;
//    Map<Integer, List<Territory>> groups;
    int playerID;

    public ClientThread(World w, String playerName, int playerID, CyclicBarrier barrier, PlayerState playerState, Client theClient,HostState hoststate){
        this.theWorld = w;
        this.playerState = playerState;
        this.theClient  = theClient;
        this.hostState = hoststate;
        this.playerName = playerName;
        this.barrier = barrier;
        this.playerID = playerID;
    }

    /*
    *  This sends a player info to each Client.
    * */
    private void sendPlayerNameToClient(){

    }

    /*
    *  This send the newest worldMap to the Client
    * */
    private void sendWorldToClient(){

    }

    /*
    * This is to select territory for each player.
    * */
    private void selectUnits(){
//        List<Territory> myGroup = groups.get(playerID);
    }

    /*
    * This handles all orders from the Client
    * */
    private void doActionPhase(){
        updateActionOnWorld();
    }

    /*
    * This function is used to update world with any action received from the Client
    * This function has to be locked. This is because all players are sharing the
    * same world
    * */
    synchronized private void updateActionOnWorld(){

    }


    /*
    * This is a function to check if  the player in this clientThread lost the game
    * It will iterate all territories in the world to see if there is a terr that belongs to this Player
    * */

    public boolean isPlayerLost(){
        return false;
    }


//    /*
//    *
//    * */
//
    private boolean isPlayerWin(){
        return false;
    }

    /*
    *  This message to Winner to let him or her know
    * */
    private void sendWinnerMessage(){

    }

    public void run(){
        try {
            sendPlayerNameToClient();
            sendWorldToClient();
            selectUnits();
            barrier.await();
            sendWorldToClient();

            /*
            * keep receiving message from the Client to do action
            * */
            while(true) {
                while (playerState.isReadyToDoAction()) {
                    doActionPhase();
                }
                barrier.await();
                while (hostState.isFinishUpdate()) {
                    sendWorldToClient();
                }

                // After send all worlds to each player,
                // we need to change the hostState to "ReadyToNext"
                barrier.await();
                hostState.changeStateTo("ReadyToNext");
                if (isPlayerLost()) {
                    playerState.changeStateTo("Lose");
                }
                else{
                    playerState.changeStateTo("ReadyToNextTurn");
                }
                if(isPlayerWin()){
                    sendWinnerMessage(); // If we find winner, this thread will close
                    break;
                }

                // if we find a winner, and hostState will be endGame. Every thread should close
                if(hostState.isEndGame()){
                    playerState.changeStateTo("Quit");
                    break;
                }
            }
        }catch (Exception ignored){

        }
    }

}
