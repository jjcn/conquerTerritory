//package edu.duke.ece651.group4.RISK.shared;
//
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.net.ServerSocket;
//import java.net.Socket;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class ClientTest {
//    private final static int TIME = 1000;
//    private final static int PORT = 7777;
//    static ServerSocket hostSocket;
//
//    @BeforeAll
//    static void setUpAll() throws IOException, InterruptedException {
//        hostSocket = new ServerSocket(PORT);
//    }
//
//    @Test
//    public void test_accept() throws IOException {
//        new Thread(() -> {
//            try {
//                Socket s = hostSocket.accept();
//                assertNotNull(s);
//                Client clientSocket = new Client(s);
//            } catch (IOException ignored) {
//            }
//        }).start();
//        new Client("localhost", String.valueOf(PORT));
//    }
//
//    @Test
//    public void testSendAndRecvStringObject() throws IOException, ClassNotFoundException {
//        new Thread( ()-> {
//            try{
//                Socket s = hostSocket.accept();
//                Client clientInServer = new Client(s);
//                String strFromClient = (String) clientInServer.recvObject();
//                assertEquals(strFromClient, "Hi, this is client");
//                clientInServer.sendObject("Copy that, this is server");
//            } catch (IOException | ClassNotFoundException e) {
//            }
//        }
//        ).start();
//        Client clientSocket = new Client("127.0.0.1", String.valueOf(PORT));
//        clientSocket.sendObject("Hi, this is client");
//        String strFromServer = (String) clientSocket.recvObject();
//        assertEquals(strFromServer, "Copy that, this is server");
//    }
//    @Test
//    public void test_isClose() throws IOException {
//        new Thread( ()-> {
//            try{
//                Socket s = hostSocket.accept();
//                Client clientInServer = new Client(s);
//            } catch (IOException e) {
//            }
//        }
//        ).start();
//        Client clientSocket = new Client("localhost", String.valueOf(PORT));
//        clientSocket.close();
//    }
//
//    @Test
//    public void test_sendAndRecvTerritoryObject() throws IOException, ClassNotFoundException {
//        new Thread( ()-> {
//            try{
//                Socket s = hostSocket.accept();
//                Client clientInServer = new Client(s);
//                Territory terr = (Territory) clientInServer.recvObject();
//                clientInServer.sendObject(terr);
//            } catch (IOException | ClassNotFoundException e) {
//            }
//        }
//        ).start();
//        Client clientSocket = new Client("127.0.0.1", String.valueOf(PORT));
//        Territory terr = new Territory("PlayerA");
//        terr.addUnit(5);
//        clientSocket.sendObject(terr);
//        Territory terrRecv = (Territory) clientSocket.recvObject();
//        assertEquals(terr, terrRecv);
//        assertEquals(terr.getName(), terrRecv.getName());
//        assertEquals(terr.checkPopulation(), terrRecv.checkPopulation());
//    }
//
//    @Test
//    public void test_sendAndRecvWorldOBject()throws IOException, ClassNotFoundException{
//
////        World world = new World(15);
////        Player p1 = new TextPlayer("p1");
////        Player p2 = new TextPlayer("p2");
////        ArrayList<Territory> terrs = new ArrayList<Territory>();
////        terrs.add(new Territory("terr1", p1, 2, new Random(0)));
////        terrs.add(new Territory("terr2", p1, 3, new Random(0)));
////        terrs.add(new Territory("terr3", p2, 1, new Random(0)));
////        terrs.add(new Territory("terr4", p2, 4, new Random(0)));
////        for (Territory terr : terrs) {
////            world.addTerritory(terr);
////        }
////        world.addConnection("terr1", "terr2");
////        world.addConnection("terr2", "terr3");
////        world.addConnection("terr3", "terr4");
//
//        Player p1 = new TextPlayer("p1");
//        Player p2 = new TextPlayer("p2");
//        World world = new World(6);
//        world.stationTroop("6",new Troop(5,p1));
//        world.stationTroop("1",new Troop(5,p1));
//        world.stationTroop("2",new Troop(5,p1));
//        world.stationTroop("3",new Troop(5,p2));
//        world.stationTroop("4",new Troop(5,p2));
//        world.stationTroop("5",new Troop(5,p2));
//        System.out.println(new WorldTextView(world).displayWorld(world));
//
//        new Thread( ()-> {
//            try{
//                Socket s = hostSocket.accept();
//                Client clientInServer = new Client(s);
//                clientInServer.sendObject(world);
//            } catch (IOException e) {
//            }
//        }
//        ).start();
//
//        Client clientSocket = new Client("127.0.0.1", String.valueOf(PORT));
//        World worldRecv = (World) clientSocket.recvObject();
//        List<Territory> groups = worldRecv.getAllTerritories();
//        System.out.println(new WorldTextView(worldRecv).displayWorld(worldRecv));
//        for(Territory t : groups){
//            System.out.println(t.getOwner().getName());
//            System.out.println(t.checkPopulation());
//        }
//
//    }
//}