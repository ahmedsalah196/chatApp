package chatserver;

import java.io.*;
import java.util.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

class threadedMsgSender implements Runnable {

    private String targetIP, message;
    private DataOutputStream dout;
    private DataInputStream din;

    public threadedMsgSender(String targetIP, String message) {
        this.targetIP = targetIP;
        this.message = message;
        this.din = null;
        this.dout = null;
    }

    public void run() {
        try {
            Socket s = new Socket(this.targetIP, 3001);
            this.dout = new DataOutputStream(s.getOutputStream());
            this.dout.writeUTF(this.message);
            //recieve acknowlogement lw 3aezeen
        } catch (Exception ex) {
            Logger.getLogger(chatRoom.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

public class chatRoom {

    //lets assume clientsInRooms be arraylist of Strings(names or IPs) lsa m4 3aref 
    public ArrayList<user> clientsInRoom;
    public user adminUser;
    public String roomName;
    public static int roomNumberTotake = 1;
    public static int portNumbersTotake = 20000;
    public ServerSocket serverSocket;
    public int currentPortNum;
    public int currentRoomNum;
    public ArrayList<String> messages;

    public chatRoom() {
        this.currentPortNum = chatRoom.portNumbersTotake++;
        this.currentRoomNum = chatRoom.roomNumberTotake++;
        this.roomName = "room." + (this.currentRoomNum);
        this.clientsInRoom = new ArrayList();
        this.messages = new ArrayList<>();
        try {
            this.serverSocket = new ServerSocket(this.currentPortNum);
        } catch (Exception ex) {
            Logger.getLogger(chatRoom.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /* 
       addToRoom => used to make a user Join the room 
       parameters=> userClient : indicate the desired client to join the room
                    sameORcopy : indicate whehter for the same user reference only (copy reference)
                                  or for a new instance of the newClient Joined ( copy values in a new instance)
     */
    public void addToRoom(user newClient, boolean sameORcopy) {
        //sameORcopy = true, for the same user reference only (copy reference)
        //sameORcopy = false, for a new instance of the newClient Joined ( copy values )
        if (sameORcopy) {
            //add the reference of the user to the arrayList
            this.clientsInRoom.add(newClient);
        } else {
            //add a copy of that user to the arraylist
            this.clientsInRoom.add(new user(newClient.username, newClient.password, newClient.ip, newClient.status));
        }
    }

    /*
      sendMsgToAll => used to send msg to all clients in the room
      parameters => msg : message written in the text area in the GUI
     */
    public void sendMsgToAll(String msg) {
        //format of message to send to each client
        String msgToSend = "from =>" + "UserName" + "\n" + "Message => " + msg;
        //get Number of Clients in the room
        int size = this.clientsInRoom.size();
        //create a thread for each client in the room and Start it's execution
        Thread[] tharr = new Thread[size];
        for (int i = 0; i < size; i++) {
            tharr[i] = new Thread(new threadedMsgSender(this.clientsInRoom.get(i).ip, msgToSend));
            tharr[i].start();
        }
        for (int i = 0; i < size; i++) {
            try {
                //wait for each thread to finish it's execution
                tharr[i].join();
            } catch (InterruptedException ex) {
                Logger.getLogger(chatRoom.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //store it in the messages sent in this chatRoom
        this.messages.add(msgToSend);
    }

    /*
        createChatRoom => used to create a new Chat room 
        parameters => username : string representing the user created the room
                      password : string repesenting the password of that user account
                      IP: string representing the IP of the user creating the room as it will required for transactions  
     */
    public static chatRoom createChatRoom(String username, String password, String IP) {
        chatRoom r = new chatRoom();
        //assign room admin
        r.adminUser = new user(username, password, IP);
        r.adminUser.adm = true;
        //add that user to the room
        r.addToRoom(r.adminUser, true);
        return r;
    }

    /*
        kick any user by remove him from the arrayList
        DISCLAIMER : must be used by the room admin only
     */
    public void kickAclient(String IPtoRemove){
        for (int i = 0; i < this.clientsInRoom.size(); i++) {
            if (this.clientsInRoom.get(i).ip.equalsIgnoreCase(IPtoRemove)) {
                this.clientsInRoom.remove(i);
                break;
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
