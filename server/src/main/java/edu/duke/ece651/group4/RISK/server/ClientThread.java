package edu.duke.ece651.group4.RISK.server;

import edu.duke.ece651.group4.RISK.shared.*;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CyclicBarrier;

public class ClientThread extends Thread{
    World theWorld;
    PlayerState playerState;
    HostState hostState;
    Client theClient;
    String playerName;
    CyclicBarrier barrier;
    List<Territory> initTerritory;
    int playerID;

    public ClientThread(World w, String playerName, int playerID, CyclicBarrier barrier, PlayerState playerState, Client theClient,HostState hoststate, List<Territory> initTerritory){
        this.theWorld = w;
        this.playerState = playerState;
        this.theClient  = theClient;
        this.hostState = hoststate;
        this.playerName = playerName;
        this.barrier = barrier;
        this.playerID = playerID;
        this.initTerritory=initTerritory;
    }

    /*
     *  This sends a player info to each Client.
     * */
    private void sendPlayerNameToClient(){
        sendInfo(this.playerName,this.theClient);
    }

    /*
     *  This send the newest worldMap to the Client
     * */
    private void sendWorldToClient(){
        sendInfo(this.theWorld,this.theClient);
    }

    /*
     * This is to select territory for each player.
     * */
    private void selectUnits(){
        int orderNum=this.initTerritory.size();
        while(orderNum>0) {
            PlaceOrder newOrder = null;
            newOrder = (PlaceOrder) receiveInfo(newOrder, this.theClient);

            this.theWorld.stationTroop(newOrder.getDesName(),newOrder.getActTroop());
            orderNum--;
        }

    }

    /*
     * This handles all orders from the Client
     * */
    private void doActionPhase(){

        while( !playerState.isFinishOneAction()) {
            BasicOrder newOrder = null;
            newOrder = (BasicOrder) receiveInfo(newOrder, this.theClient);
            updateActionOnWorld(newOrder);
        }
    }

    /*
     * This function is used to update world with any action received from the Client
     * This function has to be locked. This is because all players are sharing the
     * same world
     * */
    synchronized private void updateActionOnWorld(BasicOrder receiveMessage){

        if (receiveMessage.getActionName() == 'A') {
            this.theWorld.moveTroop(theWorld.findTerritory(receiveMessage.getSrcName()), receiveMessage.getActTroop(), theWorld.findTerritory(receiveMessage.getDesName()));
        } else if (receiveMessage.getActionName() == 'M') {
            this.theWorld.attackATerritory(theWorld.findTerritory(receiveMessage.getSrcName()), receiveMessage.getActTroop(), theWorld.findTerritory(receiveMessage.getDesName()));
        } else {
            playerState.changeStateTo("E");
        }

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

    private Object receiveInfo(Object o, Client c){
        while(o==null) {

            try {
                o = c.recvObject();
            } catch (Exception e) {
                System.out.println("Socket name problem!");
            }
        }
        return o;
    }

    private void sendInfo(Object o, Client c){
        while(o==null) {

            try {
                c.sendObject(o);
            } catch (Exception e) {
                System.out.println("Socket problem!");
            }
        }

    }

    @Override
    public void run(){
        try {
            sendPlayerNameToClient();
            barrier.await();
            sendWorldToClient();
            selectUnits();
            barrier.await();
            sendWorldToClient();

            /*
             * keep receiving message from the Client to do action
             * */
            while(!hostState.isEndGame()) {

                doActionPhase();

                barrier.await();
                while (!hostState.isFinishUpdate()) {

                }
                sendWorldToClient();
                // After send all worlds to each player,
                // we need to change the hostState to "ReadyToNext"
                barrier.await();
                hostState.changeStateTo("ReadyToNext");//?playerstate=readytonext
                //if state=lose do not change to ready to next

//                if (this.theWorld.checkLost(this.playerName)) {
//                    playerState.changeStateTo("Lose");
//                }
//                else{
//                    playerState.changeStateTo("ReadyToNextTurn");
//                }

            }

            sendWinnerMessage(); // If we find winner, this thread will close
            // if we find a winner, and hostState will be endGame. Every thread should close
//            if(hostState.isEndGame()){
//                playerState.changeStateTo("Quit");
//                break;
//            }
        }catch (Exception ignored){

        }
    }

}

