/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package intranetchat.core;


import java.util.Observable;


/**
 *
 * @author Philip
 */
public class NetworkListener extends Observable implements Runnable{
    private NetworkInterface network;
    private String incoming;
    public void NetworkListener(){

    }

    public void run() {
        network = NetworkInterface.getInstance();
        while(true){
            String s = network.recieveMulticast();
            this.messageRecieved(s);
            try{
                Thread.sleep(1000);
            }catch(InterruptedException ex){ }
        }
    }

    public void messageRecieved(String message){
        incoming = message;
        this.setChanged();
        this.notifyObservers();
    }

    public String getMessage(){
        return incoming;
    }
}
