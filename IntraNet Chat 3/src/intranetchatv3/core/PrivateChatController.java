/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package intranetchatv3.core;

import intranetchatv3.df.MessageTypes;
import intranetchatv3.df.NetworkObj;
import intranetchatv3.df.User;
import intranetchatv3.df.VariableStore;
import intranetchatv3.display.pc.PrivateChat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 *
 * @author Philip
 */
public class PrivateChatController {
    private static volatile PrivateChatController instance;
    private ArrayList<PrivateChat> conversations;
    private VariableStore store;

    private PrivateChatController(){
        conversations = new ArrayList<PrivateChat>();
        store = VariableStore.getInstance();
    }

    public static synchronized PrivateChatController getInstance(){
        if(instance == null){
            instance = new PrivateChatController();
        }
        return instance;
    }

    public void incoming(NetworkObj o){
        boolean updated = false;
        Iterator<PrivateChat> i = conversations.iterator();
        while(i.hasNext()){
            PrivateChat p = i.next();
            if(o.getPrivateID().compareTo(p.chatID)==0){
                switch(o.getType()){
                    case MessageTypes.PRIVATE_MESSAGE:
                        p.appendMessage(o);
                        updated = true;
                        break;
                    case MessageTypes.PRIVATE_USERADD:
                        p.addUsers(o);
                        updated = true;
                        break;
                    case MessageTypes.PRIVATE_USERLEFT:
                        p.removeUser(o);
                        updated = true;
                        break;
                }

            }
        }
        if(!updated && inList(o)){
            createExistingChat(o);

        }

    }

    private void createExistingChat(NetworkObj o){
        ArrayList<String> users = o.getList();
        PrivateChat p = new PrivateChat(o.getPrivateID(),users);
        if(o.getType() == MessageTypes.PRIVATE_MESSAGE){
            p.appendMessage(o);
        }
        conversations.add(p);
        p.setVisible(true);
    }

    public void creatChat(Object us){
        ArrayList<String> users = new ArrayList<String>();
        User u = (User)us;
        users.add(u.getID());
        users.add(store.networkID);
        PrivateChat p = new PrivateChat(randomIDgen(),users);
        conversations.add(p);
        p.setVisible(true);
    }

    private String randomIDgen(){
        Random r = new Random();
        return r.nextInt(1000000)+"";
    }

    private boolean inList(NetworkObj o){
        ArrayList<String> a = o.getList();
        Iterator<String> i = a.iterator();
        while(i.hasNext()){
            if(i.next().compareTo(store.networkID)==0){
                return true;
            }
        }
        return false;
    }
}
