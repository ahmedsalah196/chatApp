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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

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
private void show(ActionEvent e){
    Parent techRoot;
        try {
            techRoot = FXMLLoader.load(getClass().getResource("lobby.fxml"));
            Scene techScene = new Scene(techRoot);
            Stage techStage=(Stage)((Node)e.getSource()).getScene().getWindow();
        techStage.setScene(techScene);
        techStage.show();
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
}
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
                    if(message.equals("valid signin"))
                        show(event);
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
            if(message.equals("Svalid signup"))
                        show(event);
            }catch(Exception e){e.printStackTrace();}
    }

}
