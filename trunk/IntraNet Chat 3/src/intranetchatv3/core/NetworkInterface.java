/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package intranetchatv3.core;

import intranetchatv3.df.*;
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
    private Serializer serial;
    private InetAddress group;
    private MulticastSocket ms;
    private String netID;
    private int portNum;
    public NetworkObj lastSent;

    public static synchronized NetworkInterface getInstance(){
        if(instance == null){
            instance = new NetworkInterface();
        }
        return instance;
    }

    private NetworkInterface(){
        serial = new Serializer();
        Random r = new Random();
        netID = r.nextInt(1000000)+"";
        portNum = 5454;
        VariableStore store = VariableStore.getInstance();
        if(store.networkID.compareTo("0")==0){
            store.networkID = netID;
        }else{
            netID = store.networkID;
            portNum = Integer.parseInt(store.netPort);
        }
        try{
            group = InetAddress.getByName("228.0.0.1");
            ms = new MulticastSocket(portNum);
            ms.joinGroup(group);
        }catch(Exception e){
            System.out.println("error");
        }
    }

    /**
     * This sends a message via a multicast link
     * @param message message being sent
     * @throws java.io.IOException thrown if the connection has failed and the
     * message can't be sent
     */
    public void sendMulticast(NetworkObj ob)throws IOException{
        String trans = serial.serializeMessage(ob);
        DatagramPacket dp = new DatagramPacket(trans.getBytes(),trans.getBytes().length,group,portNum);
        ms.send(dp);
        lastSent = ob;
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
    public NetworkObj recieveMulticast(){
        byte[] bytes = new byte[5000];
        DatagramPacket dp = new DatagramPacket(bytes, bytes.length);
        try{
            ms.receive(dp);
        }catch(IOException e)
        {
            System.out.println("error");
        }
        String s = new String(bytes);
        return serial.deserializeMessage(s);
    }

    /**
     * returns the unique network id for the current session
     * @return the networkID
     */
    public String getID(){
        return netID +"";
    }
}
