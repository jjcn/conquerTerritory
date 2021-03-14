package edu.duke.ece651.group4.RISK.shared;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClientTest {
    private final static int TIME = 1000;
    private final static int PORT = 9999;
    static ServerSocket hostSocket;
    static Client clientSocket;
    static Client clientInServer;

    @Test
    public void setUpAServerSocket(){
        new Thread( ()-> {
            try{
                 hostSocket = new ServerSocket(PORT);
            } catch (IOException e) {
            }
            try {
                Thread.sleep(TIME);
            } catch (InterruptedException e) {
            }
        }
        ).start();
    }

    public void testSendAndRecvStringObject() throws IOException, ClassNotFoundException {
        new Thread( ()-> {
            try{
                Socket s = hostSocket.accept();
                clientInServer = new Client(s);
                String strFromClient = (String) clientInServer.recvObject();
                assertEquals(strFromClient, "Hi, this is client");
                clientInServer.sendObject("Copy that, this is server");
            } catch (IOException | ClassNotFoundException e) {
            }
            try {
                Thread.sleep(TIME);
            } catch (InterruptedException e) {
            }
        }
        ).start();

        clientSocket = new Client("localhost", String.valueOf(PORT));
        clientSocket.sendObject("Hi, this is client");
        String strFromServer = (String) clientSocket.recvObject();
        assertEquals(strFromServer, "Copy that, this is server");
        String strNULL = (String) clientSocket.recvObject();
        assertEquals(strFromServer, null);
    }
}