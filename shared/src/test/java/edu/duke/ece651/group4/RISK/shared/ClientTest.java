package edu.duke.ece651.group4.RISK.shared;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

class ClientTest {
    private final static int TIME = 1000;
    private final static int PORT = 9999;
    static ServerSocket hostSocket;

    @BeforeAll
    static void setUpAll() throws IOException, InterruptedException {
        hostSocket = new ServerSocket(PORT);
    }

    @Test
    public void test_accept() throws IOException {
        new Thread(() -> {
            try {
                Socket s = hostSocket.accept();
                assertNotNull(s);
                Client clientSocket = new Client(s);
            } catch (IOException ignored) {
            }
        }).start();
        new Client("localhost", String.valueOf(PORT));
    }

    @Test
    public void testSendAndRecvStringObject() throws IOException, ClassNotFoundException {
        new Thread( ()-> {
            try{
                Socket s = hostSocket.accept();
                Client clientInServer = new Client(s);
                String strFromClient = (String) clientInServer.recvObject();
                assertEquals(strFromClient, "Hi, this is client");
                clientInServer.sendObject("Copy that, this is server");
            } catch (IOException | ClassNotFoundException e) {
            }
        }
        ).start();
        Client clientSocket = new Client("127.0.0.1", String.valueOf(PORT));
        clientSocket.sendObject("Hi, this is client");
        String strFromServer = (String) clientSocket.recvObject();
        assertEquals(strFromServer, "Copy that, this is server");
    }
    @Test
    public void test_isClose() throws IOException {
        new Thread( ()-> {
            try{
                Socket s = hostSocket.accept();
                Client clientInServer = new Client(s);
            } catch (IOException e) {
            }
        }
        ).start();
        Client clientSocket = new Client("localhost", String.valueOf(PORT));
        clientSocket.close();
    }

    @Test
    public void test_sendAndRecvTerritoryObject() throws IOException, ClassNotFoundException {
        new Thread( ()-> {
            try{
                Socket s = hostSocket.accept();
                Client clientInServer = new Client(s);
                Territory terr = (Territory) clientInServer.recvObject();
                clientInServer.sendObject(terr);
            } catch (IOException | ClassNotFoundException e) {
            }
        }
        ).start();
        Client clientSocket = new Client("127.0.0.1", String.valueOf(PORT));
        Territory terr = new Territory("PlayerA");
        clientSocket.sendObject(terr);
        Territory terrRecv = (Territory) clientSocket.recvObject();
        assertEquals(terr, terrRecv);
        assertEquals(terr.getName(), terrRecv.getName());
    }

}