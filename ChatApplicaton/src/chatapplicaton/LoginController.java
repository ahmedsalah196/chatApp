/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatapplicaton;

import com.jfoenix.controls.JFXPasswordField;
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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author ahmedsalah
 */
public class LoginController implements Initializable {

    Socket s;
    DataOutputStream dout = null;
    DataInputStream din = null;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
     @FXML
    private JFXTextField username;

    @FXML
    private JFXPasswordField password;

    @FXML
    void signin(ActionEvent event) {
        try {
            dout.writeUTF("signin,"+username.getText()+","+password.getText());
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try{
            String message = din.readUTF();
            System.out.println(message);
            
            }catch(Exception e){e.printStackTrace();}
        
    }

    @FXML
    void signup(ActionEvent event) {
        try {
            dout.writeUTF("signup,"+username.getText()+","+password.getText());
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
         try{
            String message = din.readUTF();
            System.out.println("S"+message);
            
            }catch(Exception e){e.printStackTrace();}
    }

}
