/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatapplicaton;

import com.jfoenix.controls.JFXTextField;
import java.io.DataOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 *
 * @author ASUS
 */
public class FXMLDocumentController implements Initializable {
    
    Socket s;
    DataOutputStream dout = null;
    @FXML
    private Label label;
    @FXML
    private JFXTextField  messageArea;
    @FXML
    private void send(ActionEvent event) {
       String message = messageArea.getText().trim();
   try{
  
dout.writeUTF(message);
//dout.flush();  
//dout.close();  
//s.close();
   }
   catch(Exception Ex){}

}
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
