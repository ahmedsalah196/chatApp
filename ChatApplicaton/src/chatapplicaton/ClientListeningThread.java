/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatapplicaton;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.stage.Stage;

/**
 *
 * @author ASUS
 */
public class ClientListeningThread extends Thread{
    

     ServerSocket serverSocket = null;
        Socket socket = null;
    //public static ArrayList<chatRoom> chatRoomsAvailable;
 public void run() {
       
        
       // chatRoomsAvailable = new ArrayList<chatRoom>();

        try {
            serverSocket = new ServerSocket(3004);
        } catch (IOException e) {
            e.printStackTrace();

        }
        while (true) {
            try {
                System.out.println("ClientLISTENING");
                socket = serverSocket.accept();
              
         
               
            } 
            catch (IOException e) {
                System.out.println("I/O error: " + e);
            }
            
            new ChatThread(socket).start();
        }
        
    }
    public static void main(String[] args) {
        launch(args);
    }
    
}
