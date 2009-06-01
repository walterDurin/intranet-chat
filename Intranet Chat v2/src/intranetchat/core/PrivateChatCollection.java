/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package intranetchat.core;

import intranetchat.display.PrivateChat;
import intranetchat.saving.SavedValues;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * This class is in control of all of the private chat sessions and deals with incoming messages
 * @author Philip White
 */
public class PrivateChatCollection implements Observer{
    private static PrivateChatCollection instance;
    private ArrayList<PrivateChat> privateChat;
    private Observable observable;
    private SavedValues values;

    /**
     * This creates an instance of the class and then returns it to the calling
     * class
     * @param obs network observer
     * @return an instance of the private chat collection
     */
    public static synchronized PrivateChatCollection getInstance(Observable obs){
        if(instance == null){
            instance = new PrivateChatCollection(obs);
        }
        return instance;
    }

    /**
     * a private constructor that is required for the singleton
     * @param obs the network observer
     */
    private PrivateChatCollection(Observable obs){
        privateChat = new ArrayList<PrivateChat>();
        values = SavedValues.getInstance();
        observable = obs;
        observable.addObserver(this);

    }

    /**
     * This starts a private chat session
     * @param destinationID the networkID of the communicating program
     * @param destinationName the name of the communicating program
     */
    public void startNewPrivateChat(String destinationID, String destinationName){
        PrivateChat chat = new PrivateChat(observable,destinationID,destinationName,this);
        privateChat.add(chat);

    }

    /**
     * This starts a private chat session and then send a message to it this is
     * for when a user gets sent a message from another user
     * @param destinationID the networkID of the other program
     * @param destinationName the network name of the other program
     * @param message the message that the user sent
     */
    public void startNewPrivateChat(String destinationID, String destinationName, String message){
        PrivateChat chat = new PrivateChat(observable,destinationID,destinationName,this);
        chat.sortMessage(message);
        privateChat.add(chat);

    }

    /**
     * this remove a chat instance from the collection once it has finished
     * @param destinationID the networkID of the other program
     */
    public void removePrivateChat(String destinationID){
        for(int i = 0 ;i < privateChat.size();i++){
            String id = privateChat.get(i).destinationID;
            if(id.compareTo(destinationID)==0){
                privateChat.remove(i);
                return;
            }
        }
    }

    /**
     * this checks to see if a instance of a chat with a certain program already
     * exists
     * @param destinationID NetworkID of other program
     * @return whether it is present or not
     */
    private boolean chatExists(String destinationID){
        for(int i = 0 ;i < privateChat.size();i++){
            String id = privateChat.get(i).destinationID;
            if(id.compareTo(destinationID)==0){
                return true;
            }
        }
        return false;
    }

    /**
     * this alerts the appropiate methods when an observable class sends an
     * update to the observers
     * @param o the observerable class
     * @param arg object sent by the class
     */
    public void update(Observable o, Object arg) {
        if(o instanceof NetworkListener){
            NetworkListener list = (NetworkListener) o;
            String[] mes = list.getMessage().split("~");
            //this will search for the id's presence in the collection
            if(Integer.parseInt(mes[1])== 3){
                if(Integer.parseInt(mes[2]) == values.networkID){
                    if(!chatExists(mes[0])){
                        startNewPrivateChat(mes[0],mes[3],list.getMessage());
                    }
                }
            }
        }
    }

}
