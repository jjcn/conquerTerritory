/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package edu.duke.ece651.group4.RISK.client;


import edu.duke.ece651.group4.RISK.shared.*;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class PlayerApp {
    private Client playerClient;
    private TextPlayer myPlayer;
    private World theWorld;
    private final int totalPopulation;
    private Random rnd;
    private WorldTextView myView;
    private PrintStream out;

    public PlayerApp(Client myClient,String name,PrintStream out, Reader inputReader,World theWorld,int num,Random rnd,boolean mode) {
        this.playerClient=myClient;
        this.myPlayer=new TextPlayer(out,inputReader, name,rnd,mode);
        this.theWorld=theWorld;
        this.totalPopulation=num;
        this.rnd=rnd;
        this.myView=new WorldTextView(theWorld);
        this.out=out;
    }

    public PlayerApp(Client myClient,String name,PrintStream out, Reader inputReader,World theWorld,int num) {
        this(myClient,name,out,inputReader,theWorld,num,new Random(),false);

    }



    public TextPlayer getMyPlayer() {
        return myPlayer;
    }

    public void doPlacementPhase() throws IOException, ClassNotFoundException {
        this.out.println(this.myView.displayWorld(this.theWorld));
        List<Territory> myGroup =null;
        myGroup=(List<Territory>) receiveInfo(myGroup,this.playerClient);
        this.out.println("Finish all setup and let's start the game!");
        List<Order> orders = null;
        while(orders==null) {
            try {
                orders = this.myPlayer.doPlacement(myGroup, this.totalPopulation);

                for(Order p:orders) {
                    PlaceOrder newOrder=(PlaceOrder) p;

                    this.theWorld.findTerritory(newOrder.getDesName());
                }
            }catch(Exception e){
                this.out.println("Wrong placement Assignment");
            }
        }

        for(Order p:orders){

            this.playerClient.sendObject((PlaceOrder)p);
//            sendInfo((PlaceOrder)p,this.playerClient);
        }

        this.theWorld = (World) this.playerClient.recvObject();
        this.out.println("All placement are done");
        this.out.println(this.myView.displayWorld( this.theWorld ));

        if(!this.theWorld.checkLost(this.myPlayer.getName())){
            this.out.println("To " + this.myPlayer.getName() + ", world did not update the units ");
        }
    }

    public World getTheWorld(){
        return this.theWorld;
    }

    public void runGame() throws IOException, ClassNotFoundException {
        this.out.println(this.myView.displayWorld(this.theWorld));
        while(!this.theWorld.checkLost(this.myPlayer.getName()) && !this.theWorld.isGameEnd()) {
            doActionPhase();
        }
        if(this.theWorld.checkLost(this.myPlayer.getName())) {
            this.out.println("You lost");
        }
        if(this.theWorld.isGameEnd()) {
            this.out.println("Winner is "+this.theWorld.getWinner());
            this.playerClient.close();
            return;
        }

        boolean exit=false;
        while(!exit){
            exit= this.myPlayer.checkExit();
            World newWorld=null;
            this.theWorld=(World) receiveInfo(newWorld,this.playerClient);
            this.out.println(this.myView.displayWorld( this.theWorld ));
            String report=(String) this.playerClient.recvObject();
            this.out.println("Turn Ended");
            this.out.println(report);

            if(this.theWorld.isGameEnd()) {
                this.out.println("Winner is "+this.theWorld.getWinner());
                this.playerClient.close();
                return;
            }
        }
        this.playerClient.close();
    }

    public void doActionPhase() throws IOException, ClassNotFoundException {
        boolean turnEnd = false;

        while(!turnEnd) {
            boolean received = false;
            BasicOrder receiveMessage = null;
            while(!received) {

                try {
                    receiveMessage = this.myPlayer.doOneAction();
                    BasicOrder m = receiveMessage;
                    if (receiveMessage.getActionName() != 'D') {
                        m = new BasicOrder(new String(receiveMessage.getSrcName()),
                                           new String(receiveMessage.getDesName()),
                                           receiveMessage.getActTroop().clone(),
                                           receiveMessage.getActionName());
                    }else{
                        turnEnd=true;

                    }

                    executeOrder(receiveMessage);
//                    if(message==null){
                        received = true;
//                    }else{
//                        out.println(message);
//                        throw new IllegalArgumentException();
//                    }

                    this.out.println("Action updated");
                    this.out.println(this.myView.displayWorld(this.theWorld));
                    sendInfo(m, this.playerClient);
                } catch(Exception e) {
//                    this.out.println("Please enter exits territory!");
                    this.out.println(e.getMessage());
                }

            }

        }

        World newWorld = null;
//        this.theWorld=(World) receiveInfo(newWorld,this.playerClient);
        this.theWorld = (World) this.playerClient.recvObject();

        String report=(String) this.playerClient.recvObject();


        this.out.println("Turn Ended");
        this.out.println(report);
        this.out.println(this.myView.displayWorld(this.theWorld));
    }

    private void executeOrder(BasicOrder receiveMessage){
        if (receiveMessage.getActionName() == 'M') {
             this.theWorld.moveTroop(receiveMessage);
        } else if (receiveMessage.getActionName() == 'A') {
             this.theWorld.attackATerritory(receiveMessage);
        }



    }





    public static void main(String[] args) throws IOException, ClassNotFoundException {
        String instruct1 = "Please enter the hostName";
        String instruct2 = "Please enter the port";
        BufferedReader inRead = new BufferedReader(new InputStreamReader(System.in));

        Client myClient = null;
        boolean setConnect = false;

        while (!setConnect) {
            try {
                System.out.println(instruct1);
                String hostName = inRead.readLine();
                System.out.println(hostName);
                System.out.println(instruct2);
                String port = inRead.readLine();
                System.out.println(port);
//                String hostName = "Alexs-MacBook-Pro.local";
//                String port = "9999";
                myClient = new Client(hostName, port);
                setConnect = true;
            } catch (Exception e) {
                System.out.println("Please enter correct host information!");

            }
        }

        String name = null;
        World gameWorld = null;
        System.out.println( "Wait for setup from the server.");
//        name= (String) myClient.recvObject();

        name = (String) receiveInfo(name,myClient);
        System.out.println( "Get the name：" +name +"from the server.");
        gameWorld = (World) receiveInfo(gameWorld,myClient);
//        gameWorld=(World) myClient.recvObject();
        System.out.println( "Get the world from the server.");


        PlayerApp myApp=new PlayerApp(myClient,name,System.out,inRead,gameWorld,15);
        myApp.doPlacementPhase();
        myApp.runGame();


    }

    public static Object receiveInfo(Object o, Client c){

        try {
            o = c.recvObject();
        } catch (Exception e) {
            System.out.println("Socket receive object problem!");
        }

        return o;
    }

    public static void sendInfo(Object o, Client c) throws IOException {

        try {
            c.sendObject(o);
        } catch (Exception e) {
            System.out.println("Socket send object problem!");
        }


    }

}