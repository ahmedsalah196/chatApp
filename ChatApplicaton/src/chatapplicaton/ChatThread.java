/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatapplicaton;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

/**
 *
 * @author ASUS
 */
public class ChatThread extends Thread {
   
    protected Socket socket;


    public ChatThread(Socket clientSocket) {
        this.socket = clientSocket;
    }
    
    
    
     public void run() {

        DataInputStream dtinpt = null;
        DataOutputStream dtotpt = null;

        try {
            dtinpt = new DataInputStream(this.socket.getInputStream());
            dtotpt = new DataOutputStream(this.socket.getOutputStream());
        } catch (Exception e) {
            return;
        }
        
        new ReceivingMessageThread(dtinpt).start();



            while (true) {
    
}
