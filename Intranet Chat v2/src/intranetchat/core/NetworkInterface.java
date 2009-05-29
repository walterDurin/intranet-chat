/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package intranetchat.core;

import intranetchat.saving.SavedValues;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Random;

/**
 * This is the network interface that deals with all network commands from the
 * rest of the program
 * @author Philip White
 * @version 1.0
 */
public class NetworkInterface {
    private volatile static NetworkInterface instance;
    private InetAddress group;
    private MulticastSocket ms;
    private int networkID;
    private int portNum;

    SavedValues saved;

    /**
     * Static instance creator that creates a singleton
     * @return
     */
    public static synchronized NetworkInterface getInstance(){
        if(instance == null){
            instance = new NetworkInterface();
        }
        return instance;
    }

    /**
     * private constructor so that only the class itself can create an instance
     * of its self
     */
    private NetworkInterface(){
        Random r = new Random();
        networkID = r.nextInt(1000000);
        saved = SavedValues.getInstance();
        saved.networkID = networkID;
        portNum = saved.port;
        try{
            group = InetAddress.getByName("228.0.0.1");
            ms = new MulticastSocket(portNum);
            ms.joinGroup(group);
        }catch(Exception e){
            

        }
    }

    /**
     * This sends a message via a multicast link
     * @param message message being sent
     * @throws java.io.IOException thrown if the connection has failed and the
     * message can't be sent
     */
    public void sendMulticast(String message)throws IOException{
        String trans = networkID+"~"+message;
        DatagramPacket dp = new DatagramPacket(trans.getBytes(),trans.getBytes().length,group,portNum);
        ms.send(dp);
    }

    /**
     * this method allows the program to leave the multicast group
     */
    public void leaveMulticastGroup(){
        try {
            ms.leaveGroup(group);
        } catch (IOException ex) {
        }
    }

    /**
     * waits until a message is recieved and then returns the message
     * @return the incoming message
     */
    public String recieveMulticast(){
        byte[] bytes = new byte[5000];
        DatagramPacket dp = new DatagramPacket(bytes, bytes.length);
        try{
            ms.receive(dp);
        }catch(IOException e)
        {

        }
        String s = new String(bytes);
        return s;
    }

    /**
     * returns the unique network id for the current session
     * @return the networkID
     */
    public int getID(){
        return networkID;
    }
}
