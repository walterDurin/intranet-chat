/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package intranetchat.core;


import java.util.Observable;


/**
 * This is the tread which is tasked with dealing with the incoming messages and
 * informing all required observers that there is a message waiting
 * @author Philip White
 */
public class NetworkListener extends Observable implements Runnable{
    private NetworkInterface network;
    private String incoming;
    public void NetworkListener(){

    }

    /**
     * creates an instance of a networkInterface and then is set into a constant
     * loop to listen for messages
     */
    public void run() {
        network = NetworkInterface.getInstance();
        while(true){
            String s = network.recieveMulticast();
            this.messageRecieved(s);
            try{
                Thread.sleep(10);
            }catch(InterruptedException ex){ }
        }
    }

    /**
     * this method will inform all observers that there is a message waiting
     * to be looked at
     * @param message the incoming message
     */
    public void messageRecieved(String message){
        incoming = message;
        this.setChanged();
        this.notifyObservers();
    }

    /**
     * returns the message that was last picked up
     * @return the incoming message
     */
    public String getMessage(){
        return incoming;
    }
}
