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
public class ReceivingMessageThread extends Thread {


 ListView lvChatWindow;
 ObservableList < String > chatMessages;
 DataInputStream dinpt;
 String user2Name;

 public ReceivingMessageThread(String user2Name, ListView lvChatWindow, ObservableList < String > chatMessages, DataInputStream dinpt) {
  this.lvChatWindow = lvChatWindow;
  this.chatMessages = chatMessages;
  this.dinpt = dinpt;
  this.user2Name = user2Name;
 }

 public void run() {

  while (true) {

   try {

    String receivedMessage = dinpt.readUTF();
  

    Platform.runLater(() -> {
     chatMessages.add(user2Name + ": " + receivedMessage);


    });

   } catch (Exception e) {
    e.printStackTrace();
   }
  }

 }

}