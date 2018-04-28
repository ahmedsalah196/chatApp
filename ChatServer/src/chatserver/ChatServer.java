package chatserver;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Service;
import javafx.concurrent.Task;


//Example 26 (updated)

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.PrintStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.ServerSocket;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/*
 * A chat server that delivers public and private messages.
 */
 
public class ChatServer extends Application
{
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
        /*
         Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        
        
        ServerSocket serverSocket = null;
        Socket socket = null;

        try {
            serverSocket = new ServerSocket(3001);
        } catch (IOException e) {
            e.printStackTrace();

        }
        while (true) {
            String msgin = "";
            try {
                
                System.out.println(InetAddress.getLocalHost());
                System.out.println("LISTENING");
                Socket sckt = serverSocket.accept();  
         
            }catch(Exception e)
            {
            }
                    new EchoThread(socket).start();
            // new thread for a client
           
        }           
  */

  
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
