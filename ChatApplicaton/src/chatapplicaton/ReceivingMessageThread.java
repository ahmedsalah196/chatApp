/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatapplicaton;

import java.io.DataInputStream;
import java.net.Socket;

/**
 *
 * @author ASUS
 */
public class ReceivingMessageThread extends Thread{
    
    
      protected DataInputStream dinpt;


    public ReceivingMessageThread(DataInputStream dinpt) {
        this.dinpt = dinpt;
    }
    
    public void run(){
        
        
        try{
        
       String receivedMessage = dinpt.readUTF();
            
            
            
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        }
    }
}
