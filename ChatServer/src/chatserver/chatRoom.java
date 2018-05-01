package chatserver;

import java.io.*;
import java.util.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class chatRoom {

    //lets assume clientsInRooms be arraylist of Strings(names or IPs) lsa m4 3aref 
    public ArrayList<connection> clientsInRoom;
    public String roomName;
    public static int roomNumberTotake = 1;
    public static int portNumbersTotake = 20000;
    public ServerSocket serverSocket;
    public int currentPortNum;
    public int currentRoomNum;
    public DataInputStream roomDataInputStream;
    public DataOutputStream roomDataOutputStream;
    public ArrayList<String> messages;
    

    public chatRoom() {
        this.currentPortNum = chatRoom.portNumbersTotake++;
        this.currentRoomNum = chatRoom.roomNumberTotake++;
        this.roomName = "room." + (this.currentRoomNum);
        this.clientsInRoom = new ArrayList();
        this.messages = new ArrayList<>();
        this.roomDataInputStream=null;
        this.roomDataOutputStream=null;
        try {
            this.serverSocket = new ServerSocket(this.currentPortNum);
        } catch (Exception ex) {
            Logger.getLogger(chatRoom.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addToRoom(connection s) {
        //client Call it to Join the chat by adding his socket to the arraylist  
        this.clientsInRoom.add(s);
    }

    public void addToRoom(Socket clientSocket, String userName) {
        this.clientsInRoom.add(new connection(clientSocket, userName));
    }

    //send msg to all other clients in the room 
    public void sendMsgToAll(String msg) {
        System.out.println("Message = "+msg);
        int size = this.clientsInRoom.size();
        this.messages.add(msg);
        //change for loop to threading process
        for (int i = 0; i < size; i++) {
            try {
                //send message 
                connection tmp = (connection)this.clientsInRoom.get(i);
                this.roomDataOutputStream = new DataOutputStream(tmp.s.getOutputStream());
                this.roomDataOutputStream.writeUTF(msg+"=> socket:"+tmp.username);
                //recieve acknowlogement
            } 
            catch (Exception ex) {
                Logger.getLogger(chatRoom.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static chatRoom createChatRoom(Socket s,String username){
        chatRoom r = new chatRoom();
        r.addToRoom(s, username);
        return r;
    }

    @Override
    public String toString() {
        return "chatRoom{" +"Number of Clients="+this.clientsInRoom.size()+ "clientsInRoom=" + clientsInRoom + ", \nroomName=" + roomName + ", \nserverSocket=" + serverSocket + ", \ncurrentPortNum=" + currentPortNum + ", \ncurrentRoomNum=" + currentRoomNum + '}';
    }
    
    
    
}


/* required from GUI
1)function to process the client request




*/
