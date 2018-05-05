/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatapplicaton;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author ahmedsalah
 */
public class ChatRoomController implements Initializable {

 Socket s;
 DataOutputStream dout = null;
 DataInputStream din = null;
 String admin, user;
 int id;
 @FXML
 public Label name;
 @FXML
 private JFXListView < String > members;
 @FXML
 private JFXListView < JFXButton > kick;
 @FXML
 private JFXTextArea history;

 @FXML
 private JFXTextArea message;

 @Override
 public void initialize(URL url, ResourceBundle rb) {

 }
 public void fill(String hstry, ObservableList < String > users) {
  if (!users.get(0).equals("")) {
   members.setItems(users);
   members.refresh();
   ObservableList < JFXButton > kicks = FXCollections.observableArrayList();
   for (int i = 0; i < users.size(); i++) {
    JFXButton but = new JFXButton(users.get(i));
    if (!user.equals(admin) || users.get(i).equals(admin))
     but.setDisable(true);
    if (!user.equals(admin)) kick.setDisable(true);

    but.setOnAction(new EventHandler < ActionEvent > () {
     @Override
     public void handle(ActionEvent event) {
      try {
     //  System.out.println("Kicking " + but.getText());
       dout.writeUTF("room,kick," + id + "," + but.getText());
      } catch (IOException ex) {
       Logger.getLogger(ChatRoomController.class.getName()).log(Level.SEVERE, null, ex);
      }

     }
    });
    kicks.add(but);
   }
   kick.setItems(kicks);
   kick.refresh();
  }
  history.setText(hstry);
 }
 @FXML
 void send(ActionEvent event) {
  try {
   dout.writeUTF("room,send," + id + "," + user + ": " + message.getText());
   message.setText("");
  } catch (IOException ex) {
   Logger.getLogger(ChatRoomController.class.getName()).log(Level.SEVERE, null, ex);
  }
 }
}