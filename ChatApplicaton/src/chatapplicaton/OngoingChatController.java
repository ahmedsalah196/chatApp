/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatapplicaton;

import com.jfoenix.controls.JFXButton;
import java.io.DataOutputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class OngoingChatController implements Initializable {



 @FXML ListView lvChatWindow;
 @FXML private TextField tfUser1, tfUser2;
 @FXML JFXButton sendButton;
 @FXML Label otheruser;

 public DataOutputStream dtotpt;
 ObservableList < String > chatMessages = FXCollections.observableArrayList(); //create observablelist for listview


 //Method use to handle button press that submits the 1st user's text to the listview.
 @FXML
 public void handleUser1SubmitMessage(ActionEvent event) {

  try {

   chatMessages.add("ME :" + tfUser1.getText());
   lvChatWindow.refresh();

  
   dtotpt.writeUTF(tfUser1.getText());
  } catch (Exception e) {
   e.printStackTrace();
  };


 }
 @Override
 public void initialize(URL url, ResourceBundle rb) {
  // TODO
  lvChatWindow.setItems(chatMessages); //attach the observablelist to the listview
  otheruser.setText("Chatting with ");
 }
}