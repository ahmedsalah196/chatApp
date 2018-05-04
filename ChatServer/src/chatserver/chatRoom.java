package chatserver;

import java.io.*;
import java.util.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class chatRoom {

    //lets assume clientsInRooms be arraylist of Strings(names or IPs) lsa m4 3aref 
    public ArrayList<user> clientsInRoom;
    public String adminUser;
    public String roomName;
    public static int roomNumberTotake = 0;
    public static int portNumbersTotake = 20000;
    public ServerSocket serverSocket;
    public int currentPortNum;
    public int currentRoomNum;
    public ArrayList<String> messages;
    public ArrayList<String> blocked=new ArrayList<>();

    public chatRoom(String name,String username) {
        this.currentPortNum = chatRoom.portNumbersTotake++;
        this.currentRoomNum = chatRoom.roomNumberTotake++;
        this.clientsInRoom = new ArrayList();
        this.messages = new ArrayList<>();
        try {
            this.serverSocket = new ServerSocket(this.currentPortNum);
        } catch (Exception ex) {
            Logger.getLogger(chatRoom.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.roomName=name;
        this.adminUser =username;
        for(user u:ChatServer.online){
            if(u.username.equals(username))
                clientsInRoom.add(u);
        }
    }

    /* 
       addToRoom => used to make a user Join the room 
       parameters=> userClient : indicate the desired client to join the room
                    sameORcopy : indicate whehter for the same user reference only (copy reference)
                                  or for a new instance of the newClient Joined ( copy values in a new instance)
     */
    public boolean addToRoom(String msg) {
        
        //check if blocked
        for(String u:blocked){
            if(u.equals(msg))
                return false;
        }
        for(user u:clientsInRoom){
            if(u.username.equals(msg))
                return false;
        }
        for(user u:ChatServer.online){
            if(u.username.equals(msg)){
                clientsInRoom.add(u);
                return true;
            }

        }
        return false;
    }

    /*
      sendMsgToAll => used to send msg to all clients in the room
      parameters => msg : message written in the text area in the GUI
     */
    public void sendMsgToAll(String msg) {        
        this.messages.add(msg);
    }

    /*
        createChatRoom => used to create a new Chat room 
        parameters => username : string representing the user created the room
                      password : string repesenting the password of that user account
                      IP: string representing the IP of the user creating the room as it will required for transactions  
     */

    /*
        kick any user by remove him from the arrayList
        DISCLAIMER : must be used by the room admin only
     */
    public void kickAclient(String username){
        for (int i = 0; i < this.clientsInRoom.size(); i++) {
            if (this.clientsInRoom.get(i).username.equals(username)) {
                blocked.add(clientsInRoom.get(i).username);
                this.clientsInRoom.remove(i);
                break;
            }
        }
    }
    public void remove(String username){
        for (int i = 0; i < this.clientsInRoom.size(); i++) {
            if (this.clientsInRoom.get(i).username.equals(username)) {
                this.clientsInRoom.remove(i);
                break;
            }
        }
    }
    /*
        kick any user by remove him from the arrayList
        DISCLAIMER : must be used by the room admin only
     */
    public void kickAclient(String username, String roomAdminUserName) {
        if (roomAdminUserName.equals(this.adminUser)) {
            for (int i = 0; i < this.clientsInRoom.size(); i++) {
            if (this.clientsInRoom.get(i).username.equals(username)) {
                blocked.add(clientsInRoom.get(i).username);
                this.clientsInRoom.remove(i);
                break;
            }
        }
        }
        
    }

    public String toString() {
        return "chatRoom{" + "Number of Clients=" + this.clientsInRoom.size()
                + "\nclientsInRoom=" + clientsInRoom
                + ",\nroomName=" + roomName + ", "
                + "\nserverSocket=" + serverSocket
                + ",\ncurrentPortNum=" + currentPortNum
                + ",\ncurrentRoomNum=" + currentRoomNum
                + "\n}";
    }

}
