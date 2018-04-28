/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
*/
package chatserver;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;


public class EchoThread extends Thread {
    protected Socket socket;

    public EchoThread(Socket clientSocket) {
        this.socket = clientSocket;
    }

    public void run() {
       
        String line;
        DataInputStream dtinpt = null;
        DataOutputStream dtotpt =null;
            String msgin = "";
            try {
               dtinpt = new DataInputStream(this.socket.getInputStream());    
               dtotpt = new DataOutputStream(this.socket.getOutputStream());  
            }
            catch(Exception e){return;}
            while(!msgin.equals("exit"))  
            {  
                try{
                msgin =dtinpt.readUTF();  
               // txtbxarea.setText(txtbxarea.getText().trim()+"\n Client:"+msgin);
               System.out.println(msgin);
            } catch(Exception ex) {};
            
                
            
          
            }   
        }
    
}
    