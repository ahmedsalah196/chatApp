/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatapplicaton;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
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
    int id;
    @FXML
    private Label name;
    @FXML
    private JFXListView<String> members;
    @FXML
    private JFXListView<JFXButton> kick;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    } 
    
    
}
