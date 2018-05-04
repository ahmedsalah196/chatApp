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
        String portNo;
        String user2Name;
        
         ClientListeningThread(String portNo){
             this.portNo=portNo;
        
    }
    //public static ArrayList<chatRoom> chatRoomsAvailable;
 public void run() {
       
        
       // chatRoomsAvailable = new ArrayList<chatRoom>();
 
        try {
      
    
      
            serverSocket = new ServerSocket(Integer.parseInt(portNo));
        } catch (Exception e) {
            
            System.out.print("X");
               e.printStackTrace();
         

        }
        while (true) {
            try {
                System.out.println("ClientLISTENING");
                socket = serverSocket.accept();
              
                try{
                DataInputStream dinpt =new DataInputStream(socket.getInputStream());
                  user2Name = dinpt.readUTF();
                }catch(Exception e){e.printStackTrace();};
              
                
            } 
            catch (Exception e) {
                System.out.println("I/O error: " + e);
              
            }
            
            new ChatThread(socket,user2Name ).start();
        }
        
    }
    public static void main(String[] args) {
        launch(args);
    }
    
}
