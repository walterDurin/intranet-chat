/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package intranetchatv3.core;

import intranetchatv3.df.NetworkObj;
import intranetchatv3.df.User;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Philip
 */
public class UserCollection {
    private static volatile UserCollection instance;
    private ArrayList<User> users;

    private UserCollection(){
        users = new ArrayList<User>();
    }

    public static synchronized UserCollection getInstance(){
        if(instance == null){
            instance = new UserCollection();
        }
        return instance;
    }

    public void addUser(NetworkObj o){
        if(!userPresent(o.getSourceID())){
            User u = new User(o.getSource(),o.getSourceID());
            users.add(u);
        }
    }

    public String updateUser(NetworkObj o){
        User u;
        String past = "";
        if((u = getUser(o.getSourceID()))!= null){
            if(u.getName().compareTo(o.getSource())!=0){
                int loc = users.lastIndexOf(u);
                past = u.getName();
                u.setName(o.getSource());
                users.set(loc, u);
            }
        }
        return past;
    }

    public void removeUser(NetworkObj o){
        User u;
        if((u = getUser(o.getSourceID()))!= null){
            users.remove(u);
        }
    }

    private boolean userPresent(String id){
        Iterator<User> i = users.iterator();
        while(i.hasNext()){
            if(i.next().getID().compareTo(id)==0){
                return true;
            }
        }
        return false;
    }

    public User getUser(String id){
        Iterator<User> i = users.iterator();
        while(i.hasNext()){
            User u = i.next();
            if(u.getID().compareTo(id)==0){
                return u;
            }
        }
        return null;
    }

    public Object[] getUsers(){
        return users.toArray();
    }
}
