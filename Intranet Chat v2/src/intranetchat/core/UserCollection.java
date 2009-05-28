/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package intranetchat.core;

import java.util.ArrayList;


/**
 *
 * @author Philip
 */
public class UserCollection{
    private ArrayList<Users> userCollection;
    private static volatile UserCollection instance;

    private UserCollection (){
        userCollection = new ArrayList<Users>();
 
    }

    public static synchronized UserCollection getInstance(){
        if(instance == null){
            instance = new UserCollection();
        }
        return instance;
    }

    public void addUser(Users s){
        if(!userCollection.contains(s)){
            userCollection.add(s);
        }
    }

    public int removeUser(String id){
        for(int i = 0 ; i < userCollection.size(); i++ ){
            if(userCollection.get(i).getNetworkID().compareTo(id) == 0){
                userCollection.remove(i);
                return i;
            }
        }
        return -1;
    }

    public boolean userExists(String id){
        for(int i = 0 ; i < userCollection.size(); i++ ){
            if(userCollection.get(i).getNetworkID().compareTo(id) == 0){
                return true;
            }
        }
        return false;

    }

    public int replaceUsername(String id, String name){
        for(int i = 0 ; i < userCollection.size(); i++ ){
            if(userCollection.get(i).getNetworkID().compareTo(id) == 0){
                userCollection.get(i).setUsername(name);
                return i;
            }
        }
        return -1;
    }

    public Users getUser(int i){
        return userCollection.get(i);
    }
}
