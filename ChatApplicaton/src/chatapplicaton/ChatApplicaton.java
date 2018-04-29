/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatapplicaton;

import java.io.DataOutputStream;
import java.net.Socket;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author ASUS
 */
public class ChatApplicaton extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Socket so = null;
        DataOutputStream dout = null;
         try{
           
System.out.println("GONNA TRY");
so = new Socket("localhost",3001); 

dout=new DataOutputStream(so.getOutputStream());
System.out.println("DONE ATTEMPT");
         }catch(Exception ex){
         ex.printStackTrace();};
         
        
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("FXMLDocument.fxml"));
        
        try {loader.load();} catch(Exception e) {
           e.printStackTrace();
          }
        
        FXMLDocumentController fxdc = loader.getController();
        fxdc.s = so;
        fxdc.dout = dout;
       
       
                
        Parent root = loader.getRoot();
        
        Scene scene1 = new Scene(root);
        
        stage.setScene(scene1);
        stage.show();
        stage.setResizable(false);
}
    public static void main(String[] args) {
        launch(args);
    }
}

    
    