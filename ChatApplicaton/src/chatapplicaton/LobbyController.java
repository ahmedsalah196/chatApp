
package chatapplicaton;

import com.jfoenix.controls.JFXListView;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author ahmedsalah
 */
class polling implements Runnable{

    static Socket s;
    static DataOutputStream dout = null;
    static DataInputStream din = null;
    String username;
    LobbyController lc;
    public polling(Socket s,DataOutputStream dout,DataInputStream din,String username,LobbyController lc) {
       this.s=s;
       this.dout=dout;
       this.din=din;
       this.username = username;
       this.lc=lc;
    }

    
    @Override
    public void run() {

        while(true)
        try {
            dout.writeUTF("request statuses");
            String[] statuses=din.readUTF().split(",");
            ObservableList<String> stats=FXCollections.observableArrayList(statuses);
            System.out.println(statuses);
            System.out.println(stats);
            for(String str:stats){
                if ( str.startsWith(username)){
                    stats.remove(str);
                    break;
                }
            }
            lc.fillusers(stats);
            Thread.sleep(2000);
        } catch (Exception ex) {
            Logger.getLogger(polling.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
public class LobbyController implements Initializable {

    String username;
     Socket s;
     DataOutputStream dout = null;
     DataInputStream din = null;
    @FXML
    private JFXListView<String> users;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
    public void run() {
        try{
            dout.writeUTF("close,"+username);
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
    }
}));
    }   
    public void fillusers(ObservableList<String> usr){
        users.setItems(usr);
        users.refresh();
    }

}
