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
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.channels.Channels;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EchoThread extends Thread {

    protected Socket socket;


    public EchoThread(Socket clientSocket) {
        this.socket = clientSocket;
    }
    private boolean signin(String[] tokens) {
        boolean ret = false;
        for (user u: ChatServer.allusers) {
            if (u.username.equals(tokens[1]))
                if (u.password.equals(tokens[2]))
                    ret = true;
        }
        for (user u: ChatServer.online) {
            if (u.username.equals(tokens[1])) {
                ChatServer.online.remove(u);
            }
        }
        user u=new user(tokens[1],tokens[2],tokens[3],socket.getInetAddress().getHostAddress());
        System.out.println(socket.getInetAddress().getHostAddress());
        if(socket.getInetAddress().getHostAddress().equals("127.0.0.1")||socket.getInetAddress().getHostAddress().equals("localhost"))
            try {
                u.ip=InetAddress.getLocalHost().toString().substring(InetAddress.getLocalHost().toString().lastIndexOf('/')+1);
        } catch (UnknownHostException ex) {
            Logger.getLogger(EchoThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(u.ip);
        ChatServer.online.add(u);
        return ret;
    }
    private boolean signup(String[] tokens) {
        for (user u: ChatServer.allusers) {
            if (u.username.equals(tokens[1]))
                return false;
        }
        user u =new user(tokens[1],tokens[2],socket.getInetAddress().getHostAddress());
        System.out.println(socket.getInetAddress().getHostAddress());
        ChatServer.allusers.add(u);
        return true;
    }
    public void run() {

        DataInputStream dtinpt = null;
        DataOutputStream dtotpt = null;
        String msgin = "";
        try {
            dtinpt = new DataInputStream(this.socket.getInputStream());
            dtotpt = new DataOutputStream(this.socket.getOutputStream());
        } catch (Exception e) {
            return;
        }
        try {

            while (true) {
                msgin = dtinpt.readUTF();

                String[] tokens = msgin.split(",");


                if (tokens[0].equals("signin")) {


                    if (!signin(tokens)) {

                        System.out.println("SENDING INVALID TO CLIENT");

                        dtotpt.writeUTF("invalid signin");
                    } else {
                        dtotpt.writeUTF("valid signin");
                        
                    }
                } else if (tokens[0].equals("signup")) {

                    if (!signup(tokens)) {

                        System.out.println("SENDING INVALID TO CLIENT");

                        dtotpt.writeUTF("invalid signup");
                    } else {
                        dtotpt.writeUTF("valid signup");
                    }

                }
                else if(tokens[0].equals("close")){
                    for(user u:ChatServer.online)
                        if(tokens[1].equals(u.username))
                        {
                            System.out.println("Closing"+u.username);
                            ChatServer.online.remove(u);
                            break;
                        }
                }
                else if (tokens[0].equals("request statuses")){
                    String ret="";
                    for(user u:ChatServer.allusers){
                        boolean on=false;
                        for(user u2:ChatServer.online)
                            if(u2.username.equals(u.username)){
                                on=true;
                                u.status=u2.status;
                            }
                        if(!on){
                            u.status="offline";
                        }                        
                        ret+=u.username+" - "+u.status+",";
                        
                    }
                    ret = ret.substring(0, ret.length() - 1);
                    System.out.println(ret);
                    dtotpt.writeUTF(ret);
                    ret="";
                    for(chatRoom cr:ChatServer.rooms){
                        ret+=cr.roomName+",";
                    }
                    if(ret.length()>0)
                    ret = ret.substring(0, ret.length() - 1);
                    dtotpt.writeUTF(ret);    
                }
                else if(tokens[0].equals("room")){
                    if(tokens[1].equals("create")){
                        chatRoom cr=new chatRoom(tokens[2], tokens[3]);
                        ChatServer.rooms.add(cr);
                    }
                    else if(tokens[1].equals("add")){
                        for(chatRoom cr:ChatServer.rooms){
                            if(Integer.parseInt(tokens[2])==cr.roomNumberTotake){
                                if(!cr.addToRoom(tokens[3])){
                                    dtotpt.writeUTF("blocked");
                                }
                                else{
                                    dtotpt.writeUTF("valid");
                                }
                            }
                                
                        }
                    }
                    else if(tokens[1].equals("kick")){
                        for(chatRoom cr:ChatServer.rooms)
                            if(Integer.parseInt(tokens[2])==cr.roomNumberTotake)
                                cr.kickAclient(tokens[3]);
                    }
                    else if(tokens[1].equals("send")){
                        for(chatRoom cr:ChatServer.rooms)
                            if(Integer.parseInt(tokens[2])==cr.roomNumberTotake)
                                cr.sendMsgToAll(tokens[3]);
                    }
                    else if (tokens[1].equals("request")){
                        String ret="",ret2="";
                        for(chatRoom cr:ChatServer.rooms)
                            if(Integer.parseInt(tokens[2])==cr.roomNumberTotake){
                                for(String m:cr.messages)
                                    ret+=m+"\n";
                                for(user u:cr.clientsInRoom){
                                    ret2+=u.username+",";
                                }
                            }
                        dtotpt.writeUTF(ret);
                        if(ret2.length()>0)
                        ret2 = ret2.substring(0, ret2.length() - 1);
                        dtotpt.writeUTF(ret2);
                    }
                     else if(tokens[1].equals("remove")){
                        for(chatRoom cr:ChatServer.rooms)
                            if(Integer.parseInt(tokens[2])==cr.roomNumberTotake)
                                cr.remove(tokens[3]);
                     }
                    
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        };
    }
}