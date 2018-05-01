package chatserver;


//Example 26 (updated)

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.ServerSocket;
import javafx.application.Application;
import javafx.stage.Stage;
import java.util.*;

public class ChatServer extends Application
{
    static ArrayList<connection> online = new  ArrayList<connection>() ;
    static ArrayList<user> allusers = new ArrayList<user> ();
    @Override
    public void start(Stage stage) throws Exception {
        ServerSocket serverSocket = null;
        Socket socket = null;

        try {
            serverSocket = new ServerSocket(3001);
        } catch (IOException e) {
            e.printStackTrace();

        }
        while (true) {
            try {
                System.out.println("LISTENING");
                System.out.println(InetAddress.getLocalHost());
                socket = serverSocket.accept();
                System.out.println("DONE");
            } catch (IOException e) {
                System.out.println("I/O error: " + e);
            }
            // new thread for a client
            new EchoThread(socket).start();
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
    
}
