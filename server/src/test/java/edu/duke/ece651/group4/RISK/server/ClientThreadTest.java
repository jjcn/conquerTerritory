package edu.duke.ece651.group4.RISK.server;

import edu.duke.ece651.group4.RISK.shared.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CyclicBarrier;

import static org.junit.jupiter.api.Assertions.*;

class ClientThreadTest {
//    private final static int TIME = 1000;
//    private final static int PORT = 7777;
//    static ServerSocket hostSocket;
//    @BeforeAll
//    static void setUpAll() throws IOException, InterruptedException {
//        hostSocket = new ServerSocket(PORT);
//    }
//
//    public void test_sendAndRecvWorld() throws IOException {
//        Player p1 = new TextPlayer("Player0");
//        Player p2 = new TextPlayer("p2");
//        World world = new World(6);
//        world.stationTroop("6",new Troop(5,p1));
//        world.stationTroop("1",new Troop(5,p1));
//        world.stationTroop("2",new Troop(5,p1));
//        world.stationTroop("3",new Troop(5,p2));
//        world.stationTroop("4",new Troop(5,p2));
//        world.stationTroop("5",new Troop(5,p2));
//        System.out.println(new WorldTextView(world).displayWorld(world));
//        World theWorld = world;
//        int numOfPlayers = 1;
//        HostState hostState = new HostState("ready");
//        String playerName;
//        CyclicBarrier barrier = new CyclicBarrier(numOfPlayers); ;
//        ArrayList<String> playerNames = new ArrayList<String>();
//
//
//        new Thread( ()-> {
//            try{
//                Client clientSocket = new Client("127.0.0.1", String.valueOf("8888"));
//                String name = (String) clientSocket.recvObject();
//                World worldRecv = (World) clientSocket.recvObject();
////                World = (World) clientSocket.recvObject();
//            } catch (IOException | ClassNotFoundException e) {
//            }
//        }
//        ).start();
//
//        int PlayerID = 0;
//        int numTerritoryPerPlayer=3;
//        int playerNum = 2;
//        HashMap<Integer, List<Territory>> groups=(HashMap<Integer, List<Territory>>) theWorld.divideTerritories(playerNum);
//        ServerSocket hostSocket = new ServerSocket(8888);
//        Socket s = hostSocket.accept();
//        Client theClient = new Client(s);
//        PlayerState playerState = new PlayerState("Ready");
//        playerNames.add( "Player" + PlayerID);
//        hostState.addOnePlayerState(playerState);
//
//        ClientThread theThread = new ClientThread(theWorld, playerNames.get(PlayerID), PlayerID, barrier, playerState,
//                theClient, hostState,groups.get(PlayerID));
////        theThread.start();
//        theThread.sendWorldToClient();
//
//
//    }
}