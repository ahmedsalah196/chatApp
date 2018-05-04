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
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author ahmedsalah
 */
public class ChatRoomController implements Initializable {

    Socket s;
    DataOutputStream dout = null;
    DataInputStream din = null;
    String admin,user;
    int id;
    @FXML
    public Label name;
    @FXML
    private JFXListView<String> members;
    @FXML
    private JFXListView<JFXButton> kick;
    @FXML
    private JFXTextArea history;

    @FXML
    private JFXTextArea message;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
    public void run() {
        try{
            System.out.println("removing "+user);
            dout.writeUTF("room,remove,"+id+","+user);
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
    }
}));
    } 
    
    public void fill(String hstry,ObservableList<String> users){
        if(!users.get(0).equals("")){
        members.setItems(users);
        members.refresh();
        }
        history.setText(hstry);
    }
    @FXML
    void send(ActionEvent event) {
        try {
            dout.writeUTF("room,send,"+id+","+user+": " +message.getText());
        } catch (IOException ex) {
            Logger.getLogger(ChatRoomController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
