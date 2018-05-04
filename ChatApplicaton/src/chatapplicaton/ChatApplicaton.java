/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatapplicaton;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author ASUS
 */
public class ChatApplicaton extends Application {
    Socket so = null;
        DataOutputStream dout = null;
        DataInputStream din = null;
    @Override
    public void start(Stage stage) throws Exception {

         try{

System.out.println("GONNA TRY");
so = new Socket("localhost",3001); 
dout= new DataOutputStream(so.getOutputStream());
din = new DataInputStream(so.getInputStream());
         }catch(Exception ex){
         
         ex.printStackTrace();};
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("login.fxml"));
        try {loader.load();} catch(Exception e) {
           e.printStackTrace();
          }
        LoginController logc = loader.getController();
        logc.s = so;
        logc.dout = dout;
        logc.din = din;    
     
        Parent root = loader.getRoot();
        Scene scene1 = new Scene(root);
        stage.setScene(scene1);
        stage.show();
        stage.setResizable(false);
          
             //   System.out.println("IIO"+LoginController.portnumbertxtbox.getText());
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
          @Override
          public void handle(WindowEvent we) {
              System.exit(1);
          }
      });
}
    public static void main(String[] args) {
        launch(args);
    }
}

    
    