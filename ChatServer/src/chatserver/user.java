/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatserver;

public class user {
   String username,password,ip,status;
   boolean adm;
    public user(String username, String password,String status, String ip) {
        this.username = username;
        this.password = password;
        this.ip=ip;
        this.status=status;
        if(password.startsWith("adm"))adm=true;
        else adm=false;
    }

    public user(String username, String password, String ip) {
        this.username = username;
        this.password = password;
        this.ip = ip;
        if(password.startsWith("adm"))adm=true;
        else adm=false;
        status="Available.png";
    }
    
    
}
