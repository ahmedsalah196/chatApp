/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
*/
package chatserver;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.lang.reflect.Type;

public class EchoThread extends Thread {
    protected Socket socket;


    public EchoThread(Socket clientSocket) {
        this.socket = clientSocket;
    }
private boolean signin(String[] tokens){
  boolean ret=false;
    for(user u:ChatServer.allusers){
        if(u.username.equals(tokens[1]))
            if(u.password.equals(tokens[2]))
                ret=true;
    }
    for(connection c: ChatServer.online){
        if(c.username.equals(tokens[1])){
            c.s=socket;
        }
    }
   //save();
    return ret;
}
private boolean signup(String[] tokens){
    for(user u:ChatServer.allusers){
        if (u.username.equals(tokens[1]))
            return false;
    }
    ChatServer.allusers.add(new user(tokens[1],tokens[2]));
    ChatServer.online.add(new connection(socket,tokens[1]));
    //save();
    return true;
}
private void save(){
        Gson gson=new Gson();
        Type type=new TypeToken<ArrayList<user>>(){}.getType();
        String json=gson.toJson(ChatServer.allusers,type);
        Gson gson2=new Gson();
        Type type2=new TypeToken<ArrayList<connection>>(){}.getType();
        String json2=gson2.toJson(ChatServer.online,type2);
        try{
            FileWriter fw=new FileWriter("users.json");
            fw.write(json);
            fw=new FileWriter("connections.json");
            fw.write(json2);
            fw.close();
        }
        catch(Exception e){
            e.getMessage();
        }
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
           
               
           
               // txtbxarea.setText(txtbxarea.getText().trim()+"\n Client:"+msgin);
            
        try{
            
            while(true)
            {
                msgin = dtinpt.readUTF();
       
                String[] tokens = msgin.split(",");
               
                
              if(tokens[0].equals("signin") ){
           
                   
                        if(!signin(tokens)){
                       
                         System.out.println("SENDING INVALID TO CLIENT");
                         
                          dtotpt.writeUTF("invalid signin");
                        }
                        else {
                           dtotpt.writeUTF("valid signin");
                        }
                }
              
              
              else if(tokens[0].equals("signup"))
              {
                  
                   if(!signup(tokens)){
                       
                         System.out.println("SENDING INVALID TO CLIENT");
                         
                          dtotpt.writeUTF("invalid signup");
                        }
                        else {
                           dtotpt.writeUTF("valid signup");
                        }
                         
              }
              
              
                    
            }}catch(Exception e){e.printStackTrace();};
                              //msgin=dtinput.readUTF();
        }
    
   
            
                
    
}
          
            

    