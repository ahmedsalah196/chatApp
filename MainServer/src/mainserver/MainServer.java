/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainserver;
import java.io.*;  
import java.net.*;  

public class MainServer {  
public static void main(String[] args){ 
    
         System.out.println("listening");
try{  
ServerSocket ss= new ServerSocket(3001);  
Socket s=ss.accept();//establishes connection   
DataInputStream dis=new DataInputStream(s.getInputStream());  
String  str=(String)dis.readUTF();  
System.out.println("message= "+str);  
ss.close();  

}catch(Exception e){
   
    System.out.println(e);}  
}
    
  
}


 
