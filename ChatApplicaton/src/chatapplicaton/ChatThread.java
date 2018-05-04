/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatapplicaton;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author ASUS
 */
public class ChatThread extends Thread implements Initializable{
    
    protected Socket socket;
    @FXML public ListView lvChatWindow;
    @FXML public TextField tfUser1;
       DataInputStream dtinpt = null;
        DataOutputStream dtotpt = null;
  ObservableList<String> chatMessages = FXCollections.observableArrayList();//create observablelist for listview

    public ChatThread(Socket clientSocket) {
        this.socket = clientSocket;
        this.lvChatWindow = lvChatWindow;
      
        try{
        dtotpt = new DataOutputStream(clientSocket.getOutputStream());
        dtinpt = new DataInputStream(clientSocket.getInputStream());}
        catch(Exception e){ e.printStackTrace();}
    }
    
      public void initialize(URL url, ResourceBundle rb) {
      //    lvChatWindow.setItems(chatMessages);
      }
    
      
 



    public void run() {
        // Update UI here.
 Platform.runLater(() -> {
        Stage stage=new Stage();
        FXMLLoader loader = new FXMLLoader();
        

       System.out.println("MAAAAAAAAIN" + this.dtotpt);
         
         
        loader.setLocation(getClass().getResource("OngoingChat.fxml"));
        try {
            loader.load();
        } catch(Exception e) {
           e.printStackTrace();
        }
        OngoingChatController c = loader.getController();
         c.dtotpt = this.dtotpt;
         this.lvChatWindow=c.lvChatWindow;
         
          lvChatWindow.setItems(chatMessages);
          c.chatMessages=chatMessages;
        System.out.println("XJADKLJADJSALDJALD"+lvChatWindow);
        new ReceivingMessageThread(lvChatWindow,chatMessages, dtinpt).start();
        
          
  
   
        
        Parent root = loader.getRoot();
        Scene scene1 = new Scene(root);
        stage.setScene(scene1);
        stage.show();
       
      
        
    



 });
}
         }
                 
            
