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
        DataOutputStream dtotpt = null;
        String msgin = "";
        try {
            dtinpt = new DataInputStream(this.socket.getInputStream());
            dtotpt = new DataOutputStream(this.socket.getOutputStream());
        } catch (Exception e) {
            return;
        }
        while (!msgin.equals("exit")) {
            try {
                msgin = dtinpt.readUTF();
                // txtbxarea.setText(txtbxarea.getText().trim()+"\n Client:"+msgin);
                System.out.println(msgin);

            } catch (Exception ex) {
            };

        }
    }

    public void processWord(String recieved) {
        //message schema 
        // create room : create@username
        // join room : join@roomNumber@username
        // normal msg : normal@roomNumber@username@msg
        String[] s = recieved.split("@");

        if (s[0].equalsIgnoreCase("create")) {
            chatRoom tmp = chatRoom.createChatRoom(this.socket, s[1]);
            ChatServer.chatRoomsAvailable.add(tmp);
        } 
        else if (s[0].equalsIgnoreCase("join")) {
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

    }

}
