package edu.duke.ece651.group4.RISK.server;
import java.net.ServerSocket;
public class HostApp {
    ServerSocket hostSocket;


//    HostApp(int port) throws IOException {
//        this.hostSocket = new ServerSocket(port);
//    }
//    void getConnectedWithAClient() throws IOException {
//
//        String test_string = "First connection";
//        while (true){
//            Socket clientSocket = this.hostSocket.accept();
//            new Thread(new Runnable () {
//                @Override
//                public void run() {
//                    BufferedReader bufferedReader = null;
//                    try {
//                        bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), "UTF-8"));
//                        String str = "";
//                        while ((str = bufferedReader.readLine()) != null) {
//                            System.out.println("From Client ：" + str);
//                        }
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }).start();
//        }
//    }
}