package edu.duke.ece651.group4.RISK.server;

import edu.duke.ece651.group4.RISK.shared.*;


import java.io.IOException;
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
    public void sendPlayerNameToClient() throws IOException {
        this.theClient.sendObject(this.playerName);
    }

    /*
     *  This send the newest worldMap to the Client
     * */
    public void sendWorldToClient() throws IOException {
        Player p1 = new TextPlayer("p1");
        Player p2 = new TextPlayer("p2");
        World world = new World(6);
        world.stationTroop("6",new Troop(5,p1));
        world.stationTroop("1",new Troop(5,p1));
        world.stationTroop("2",new Troop(5,p1));
        world.stationTroop("3",new Troop(5,p2));
        world.stationTroop("4",new Troop(5,p2));
        world.stationTroop("5",new Troop(5,p2));
        this.theClient.sendObject(world);
    }

    /*
    * This send init territory to each player.
    * */
    public void sendInitTerritory() throws IOException {this.theClient.sendObject(this.initTerritory); }

    /*
     * This is to select territory for each player.
     * */
    public void selectUnits(){
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
        while( !playerState.isDoneOneTurn()) {
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
            playerState.changeStateTo("EndOneTurn");
        }

    }


    /*
     * This is a function to check if  the player in this clientThread lost the game
     * It will iterate all territories in the world to see if there is a terr that belongs to this Player
     * If a player lose, we will change the playerState to Lose
     * */
    public boolean isPlayerLost(){
        return this.theWorld.checkLost(this.playerName);
    }


    /*
    * This is to check if there is only one winner in the world after the world
    * is updated by the host
    * */
    private boolean isGameEnd(){
        return this.theWorld.isGameEnd();
    }

    /*
    *  This function is to get name of winner and will send it to all clients
    * */
    private String getWinnerName(){
        return this.theWorld.getWinner();
    }

    /*
     *  This message to Winner Name to everyone.
     * */
    private void sendWinnerMessage(){
        sendInfo(getWinnerName() + " is the winner!",this.theClient);
    }


    @Override
    public void run(){
        System.out.println( this.playerName + " gets connected with the server.");
        System.out.println( "Please wait for other player to get connected.");
        try {
            barrier.await();
            sendPlayerNameToClient();
//            this.theClient.sendObject(this.playerName);
            System.out.println("To " + this.playerName + ", send the name" );
            sendWorldToClient();
//            this.theClient.sendObject(this.theWorld);
            System.out.println("To " + this.playerName + ", send the world" );
            sendInitTerritory();
            System.out.println("To " + this.playerName + ", send territories" );
            barrier.await();
            selectUnits();
            System.out.println("To " + this.playerName + ", update select Units ");
            if(!this.theWorld.checkLost(this.playerName)){
                System.out.println("To " + this.playerName + ", world did not update the units ");
            }
            barrier.await();
            sendWorldToClient();
            System.out.println("To " + this.playerName + ", send the first complete world.");
            /*
             * keep receiving message from the Client to do action
             * */
            while(true) {
                doActionPhase();
                System.out.println(this.playerName + "finish this turn of moving and attacking.");
                barrier.await(); // Wait everyone to finish their actions

                while (!hostState.isFinishUpdate()) {} // wait hostState to update the world
                sendWorldToClient();
                barrier.await();

                //check If the game ends, send message to all players and quit
                if(isGameEnd()){
                    playerState.changeStateTo("Quit");
                    sendWinnerMessage();
                    System.out.println(this.playerName + "quit the game.");
                    break;
                }
                //Everyone need to change their state after this
                if (isPlayerLost()) {
                    playerState.changeStateTo("Lose");
                    System.out.println(this.playerName + "lose the game now.");
                }
                else{
                    playerState.changeStateTo("Ready");
                }

                barrier.await();
                hostState.changeStateTo("WaitForUpdateWorld");
            }

//            sendWinnerMessage(); // If we find winner, this thread will close
            // if we find a winner, and hostState will be endGame. Every thread should close
        }catch (Exception ignored){

        }
    }

    private Object receiveInfo(Object o, Client c){

        try {
                o = c.recvObject();
            } catch (Exception e) {
                System.out.println("Socket name problem!");
            }

        return o;
    }

    private void sendInfo(Object o, Client c){

        try {
            c.sendObject(o);
        } catch (Exception e) {
            System.out.println("Socket problem!");
        }


    }
}

