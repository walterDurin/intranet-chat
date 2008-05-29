/*
 * Network.java
 *
 * Created on 01 February 2008, 11:22
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package net.intrenetchat;

import java.io.IOException;
import java.net.*;
import java.util.*;
import javax.swing.JOptionPane;

/**
 * This class is to connect to the network
 * @author Philip White
 */
public class Network {
    private volatile static Network uniqueInstance;
    private InetAddress group;
    private MulticastSocket ms;
    private int port;
     private int unique;
    /**
     * Creates a new instance of Network
     */
    private Network() {
        Random random = new Random();
        unique = random.nextInt(1000000);
        Saving s = Saving.getInstance();
        port = s.getPort();
        try{
            group = InetAddress.getByName("228.0.0.1");
            ms = new MulticastSocket(port);
            ms.joinGroup(group);
        }catch(Exception e){
        }
    }
    
    /**
     * Singleton for the Network
     * @return A unique instance of the network class
     */
    public static Network getInstance(){
        if(uniqueInstance == null){
            synchronized(Network.class){
                uniqueInstance = new Network();
            }
        }
        return uniqueInstance;
    }
    
    /**
     * Method used to leave the multicast group
     */
    public void leaveMulticast(){
        try{
            ms.leaveGroup(group);
        }catch(Exception e){
            
            JOptionPane.showMessageDialog(null, "Unable to leave group ");}
    }
    
    /**
     * Sends a multicast message
     * @param message outgoing message
     */
    public void sendMulticast(String message){
        String out = unique+message+"~";
        byte[] bytes = out.getBytes();
        int length = bytes.length;
        try{
            
            DatagramPacket dp = new DatagramPacket(bytes, length,group, port);
            ms.send(dp);
        }catch(Exception e){JOptionPane.showMessageDialog(null, "Unable to send message");}
        

    }
    
    /**
     * Recieves multicast message
     * @return The incoming message in a String
     */
    public String recieveMulticast(){
        String s;
        byte[] b = new byte[1000];
    DatagramPacket rec = new DatagramPacket(b, b.length);
        try {
            ms.receive(rec);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Unable to start listener exiting");
            System.exit(1);
        }
        s = new String(rec.getData());
        return s;
    }
    
    /**
     * Gets the port number
     * @return port
     */
    public int getPort(){
        return port;
    }
    
    /**
     * Sets the port number
     * @param p port number
     */
    public void setPort(int p){
        port = p;
    }
    
    public String getUnique(){
        return unique+"";
    }
}
