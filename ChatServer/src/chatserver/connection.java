/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatserver;
import java.net.Socket;

public class connection {
    Socket s;
    String username;

    public connection(Socket s, String username) {
        this.s = s;
        this.username = username;
    }
}
