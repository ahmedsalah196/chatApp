/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatapplicaton;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextField;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.AccessibleAction;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.swing.JTextPane;

/**
 * FXML Controller class
 *
 * @author ahmedsalah
 */
public class LoginController implements Initializable {

 Socket s;
 DataOutputStream dout = null;
 DataInputStream din = null;
 JFXSnackbar snackbar;
 EventHandler handler;
 @FXML
 private JFXComboBox < Label > status;

 @Override
 public void initialize(URL url, ResourceBundle rb) {
  status.setValue(new Label("Online"));
  handler = new EventHandler() {
   @Override
   public void handle(Event evnet) {
    snackbar.close();
   }
  };
  status.getItems().add(new Label("Online"));
  status.getItems().add(new Label("Busy"));
  status.getItems().add(new Label("Away"));
 }

 @FXML
 public JFXTextField portnumbertxtbox;
 @FXML
 private JFXTextField username;
 @FXML
 AnchorPane root;
 @FXML
 private JFXPasswordField password;
 private void show(ActionEvent e) {
  try {
   Stage stage = new Stage();
   FXMLLoader loader = new FXMLLoader();
   loader.setLocation(getClass().getResource("lobby.fxml"));
   loader.load();
   LobbyController lc = loader.getController();
   lc.s = s;
   lc.din = din;
   lc.dout = dout;
   lc.username = username.getText();

   Parent root1 = loader.getRoot();
   Scene scene = new Scene(root1);
   stage.setScene(scene);
   stage.show();
   polling poll = new polling(s, dout, din, username.getText(), lc);
   Thread thread = new Thread(poll);
   thread.start();
   Node source = (Node) e.getSource();
   Stage stage1 = (Stage) source.getScene().getWindow();
   stage1.close();
   stage.setOnCloseRequest(new EventHandler < WindowEvent > () {
    @Override
    public void handle(WindowEvent we) {
     poll.stop();
     try {
      dout.writeUTF("close," + username.getText());
      Thread.sleep(500);
      System.exit(1);
     } catch (Exception ex) {
      Logger.getLogger(LobbyController.class.getName()).log(Level.SEVERE, null, ex);
     }
    }
   });

  } catch (IOException ex) {
   Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
  }

 }
 @FXML
 void signin(ActionEvent event) {
  String stat = status.getValue().getText();
  String portNo = portnumbertxtbox.getText();
  if (portNo.equals("")) portNo = "3002";

  if (Integer.parseInt(portNo) < 1024 || Integer.parseInt(portNo) > 49151) {
   snackbar = new JFXSnackbar(root);
   snackbar.show("Please Select a Port Number between 1024 and 49151", 3000);
  } else if (Integer.parseInt(portNo) == 3001) {
   snackbar = new JFXSnackbar(root);
   snackbar.show("Please Use another Port Number other than 3001 as it's usually used for server", 3000);
  } else {
   if (stat == null) stat = "Online";

   try {


    dout.writeUTF("signin," + username.getText() + "," + password.getText() + "," + stat + "," + portNo);
   } catch (Exception ex) {
    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
   }


   try {
    String message = din.readUTF();
    System.out.println(message);
    if (message.equals("valid signin")) {
     new ClientListeningThread(portNo).start();
     show(event);
    } else {
     snackbar = new JFXSnackbar(root);
     snackbar.show("Invalid Username/Password, please try again", "Okay", 5000, handler);
    }
   } catch (Exception e) {
    e.printStackTrace();
   }


  }
 }

 @FXML
 void signup(ActionEvent event) {
  try {
   dout.writeUTF("signup," + username.getText() + "," + password.getText());
  } catch (IOException ex) {
   Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
  }


  try {
   String message = din.readUTF();
   System.out.println("S" + message);
   if (message.equals("valid signup")) {
    snackbar = new JFXSnackbar(root);
    snackbar.show("Signup successful please signin", "Okay", 4000, handler);
   } else {
    snackbar = new JFXSnackbar(root);
    snackbar.show("Username alreay used", "Okay", 3000, handler);
   }
  } catch (Exception e) {
   e.printStackTrace();
  }
 }
}