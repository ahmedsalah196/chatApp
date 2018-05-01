/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatserver;

public class user {
   String username,password;
   boolean adm;

    public user(String username, String password, boolean adm) {
        this.username = username;
        this.password = password;
        if(password.startsWith("adm"))adm=true;
        else adm=false;
    }
    
}
