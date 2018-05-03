/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatapplicaton;

/**
 *
 * @author ASUS
 */
public class OngoingChatController {
    
    import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class FXMLDocumentController implements Initializable {

    @FXML private ListView lvChatWindow;
    @FXML private TextField tfUser1, tfUser2;

    ObservableList<String> chatMessages = FXCollections.observableArrayList();//create observablelist for listview


    //Method use to handle button press that submits the 1st user's text to the listview.
    @FXML
    private void handleUser1SubmitMessage(ActionEvent event) {
        chatMessages.add("User 1: " + tfUser1.getText());//get 1st user's text from his/her textfield and add message to observablelist
        tfUser1.setText("");//clear 1st user's textfield
    }

    //Method use to handle button press that submits the 2nd user's text to the listview.
    @FXML
    private void handleUser2SubmitMessage(ActionEvent event) {
        chatMessages.add("User 2: " + tfUser2.getText());//get 2nd user's text from his/her textfield and add message to observablelist
        tfUser2.setText("");//clear 2nd user's textfield
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        lvChatWindow.setItems(chatMessages);//attach the observablelist to the listview
    }      
}
    
}
