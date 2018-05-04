/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatapplicaton;

import java.io.DataInputStream;
import java.net.Socket;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

/** 
 *
 * @author ASUS
 */
public class ReceivingMessageThread extends Thread{
    
    
      ListView lvChatWindow;
      ObservableList<String> chatMessages;
      DataInputStream dinpt;

    public ReceivingMessageThread( ListView lvChatWindow, ObservableList<String> chatMessages,   DataInputStream dinpt) {
        this.lvChatWindow= lvChatWindow;
        this.chatMessages = chatMessages;
        this.dinpt = dinpt;
    }
    
    public void run(){
     
        while(true)
        {
        try{
        System.out.println("RUNNING");
       String receivedMessage = dinpt.readUTF();
       System.out.println("I GOT THIS "+ receivedMessage);
        System.out.println("RUNNING2");
       
        Platform.runLater(() -> {   
       chatMessages.add("User 2: " + receivedMessage);//get 2nd user's text from his/her textfield and add message to observablelist
     
   
          });
            
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        }
 
    }
    
}

