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
 *
 * @author Philip
 */
public class NetworkInterface {
    private volatile static NetworkInterface instance;
    private InetAddress group;
    private MulticastSocket ms;
    private int networkID;
    private int portNum;

    SavedValues saved;


    public static synchronized NetworkInterface getInstance(){
        if(instance == null){
            instance = new NetworkInterface();
        }
        return instance;
    }

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

    public void sendMulticast(String message)throws IOException{
        String trans = networkID+"~"+message;
        DatagramPacket dp = new DatagramPacket(trans.getBytes(),trans.getBytes().length,group,portNum);
        ms.send(dp);
    }

    public void leaveMulticastGroup(){
        try {
            ms.leaveGroup(group);
        } catch (IOException ex) {
        }
    }

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

    public int getID(){
        return networkID;
    }
}
