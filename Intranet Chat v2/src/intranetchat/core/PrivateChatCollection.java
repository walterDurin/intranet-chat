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
 * @author Philip
 */
public class PrivateChatCollection implements Observer{
    private static PrivateChatCollection instance;
    private ArrayList<PrivateChat> privateChat;
    private Observable observable;
    private SavedValues values;

    public static synchronized PrivateChatCollection getInstance(Observable obs){
        if(instance == null){
            instance = new PrivateChatCollection(obs);
        }
        return instance;
    }

    private PrivateChatCollection(Observable obs){
        privateChat = new ArrayList<PrivateChat>();
        values = SavedValues.getInstance();
        observable = obs;
        observable.addObserver(this);

    }

    public void startNewPrivateChat(String destinationID, String destinationName){
        PrivateChat chat = new PrivateChat(observable,destinationID,destinationName,this);
        privateChat.add(chat);

    }

    public void startNewPrivateChat(String destinationID, String destinationName, String message){
        PrivateChat chat = new PrivateChat(observable,destinationID,destinationName,this);
        chat.sortMessage(message);
        privateChat.add(chat);

    }

    public void removePrivateChat(String destinationID){
        for(int i = 0 ;i < privateChat.size();i++){
            String id = privateChat.get(i).destinationID;
            if(id.compareTo(destinationID)==0){
                privateChat.remove(i);
                return;
            }
        }
    }

    private boolean chatExists(String destinationID){
        for(int i = 0 ;i < privateChat.size();i++){
            String id = privateChat.get(i).destinationID;
            if(id.compareTo(destinationID)==0){
                privateChat.remove(i);
                return true;
            }
        }
        return false;
    }
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
