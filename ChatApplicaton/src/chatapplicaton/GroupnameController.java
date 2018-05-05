/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatapplicaton;

import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ahmedsalah
 */
public class GroupnameController implements Initializable {

 @FXML
 private JFXTextField name;
 LobbyController lc;
 @Override
 public void initialize(URL url, ResourceBundle rb) {
  // TODO
 }
 @FXML
 void create(ActionEvent e) {
  lc.newRoom(name.getText());
  Node source = (Node) e.getSource();
  Stage stage1 = (Stage) source.getScene().getWindow();
  stage1.close();
 }
}