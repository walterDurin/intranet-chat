/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package intranetchat.core;

import intranetchat.display.PrivateChat;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * This class is in control of all of the private chat sessions and deals with incoming messages
 * @author Philip
 */
public class PrivateChatCollection implements Observer{
    private static PrivateChatCollection instance;
    private ArrayList<PrivateChat> privateChat;
    private Observable observable;

    public static synchronized PrivateChatCollection getInstance(Observable obs){
        if(instance == null){
            instance = new PrivateChatCollection(obs);
        }
        return instance;
    }

    private PrivateChatCollection(Observable obs){
        privateChat = new ArrayList<PrivateChat>();
        observable = obs;

    }

    public void DeliverMessage(String[] message){

    }

    public void startNewPrivateChat(String destinationID, String destinationName){
        PrivateChat chat = new PrivateChat(observable,destinationID,destinationName);
        privateChat.add(chat);
    }

    public void removePrivateChat(String DestinationID){

    }

    public void update(Observable o, Object arg) {
        if(o instanceof NetworkListener){
            NetworkListener list = (NetworkListener) o;
            list.getMessage();
            //this will search for the id's presence in the collection

        }
    }

}
