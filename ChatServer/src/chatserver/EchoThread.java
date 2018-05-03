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
        ChatServer.online.add(new user(tokens[1],tokens[2],tokens[3],socket.getInetAddress().getHostAddress()));
     //   save();
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
       // save();
        return true;
    }
    private void save() {
        Gson gson = new Gson();
        Type type = new TypeToken < ArrayList < user >> () {}.getType();
        String json = gson.toJson(ChatServer.allusers, type);
        try {
            FileWriter fw = new FileWriter("users.json");
            fw.write(json);
            fw.close();
        } catch (Exception e) {
            e.getMessage();
        }
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



        // txtbxarea.setText(txtbxarea.getText().trim()+"\n Client:"+msgin);

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
                }
                  


            }
        } catch (Exception e) {
            e.printStackTrace();
        };
        //msgin=dtinput.readUTF();
    }
    /*public void processWord(String recieved) {
        //message schema 
        // create room : create@username
        // join room : join@roomNumber@username
        // normal msg : normal@roomNumber@username@msg
        String[] s = recieved.split("@");

        if (s[0].equalsIgnoreCase("create")) {
            chatRoom tmp = chatRoom.createChatRoom(this.socket, s[1]);
            ChatServer.chatRoomsAvailable.add(tmp);
        } else if (s[0].equalsIgnoreCase("join")) {
            for (int i = 0; i < ChatServer.chatRoomsAvailable.size(); i++) {
                chatRoom tmp = ChatServer.chatRoomsAvailable.get(i);
                if (tmp.currentRoomNum == Integer.parseInt(s[1])) {
                    tmp.addToRoom(this.socket, s[2]);
                    break;
                }
            }
        } else {
            for (int i = 0; i < ChatServer.chatRoomsAvailable.size(); i++) {
                chatRoom tmp = ChatServer.chatRoomsAvailable.get(i);
                if (tmp.currentRoomNum == Integer.parseInt(s[1])) {
                    //mwgood w hkml 3ady
                    for (int j = 0; j < tmp.clientsInRoom.size(); j++) {
                        connection tmp2 = tmp.clientsInRoom.get(j);
                        if (tmp2.username.equalsIgnoreCase(s[2])) {
                            tmp.sendMsgToAll(s[3]);
                            System.out.println(tmp.toString());
                            break;
                        }
                    }
                }
            }
        }

    }*/

}