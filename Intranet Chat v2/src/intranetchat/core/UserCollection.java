/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package intranetchat.core;

import java.util.ArrayList;


/**
 * This is a collection of users that are avaliable and are displayed on the main
 * screen
 * @author Philip White
 */
public class UserCollection{
    private ArrayList<Users> userCollection;
    private static volatile UserCollection instance;

    /**
     * private constructor required for a singleton
     */
    private UserCollection (){
        userCollection = new ArrayList<Users>();
 
    }

    /**
     * static method that returns an instance of this class
     * @return instance of this class
     */
    public static synchronized UserCollection getInstance(){
        if(instance == null){
            instance = new UserCollection();
        }
        return instance;
    }

    /**
     * this adds a user to the collection
     * @param s users details
     */
    public void addUser(Users s){
        if(!userCollection.contains(s)){
            userCollection.add(s);
        }
    }

    /**
     * this removes a user from the collection
     * @param id the networkID of the user
     * @return the position of the user in the collection
     */
    public int removeUser(String id){
        for(int i = 0 ; i < userCollection.size(); i++ ){
            if(userCollection.get(i).getNetworkID().compareTo(id) == 0){
                userCollection.remove(i);
                return i;
            }
        }
        return -1;
    }

    /**
     * returns whether the user exists in the collection
     * @param id the networkID of the user
     * @return whether it is present
     */
    public boolean userExists(String id){
        for(int i = 0 ; i < userCollection.size(); i++ ){
            if(userCollection.get(i).getNetworkID().compareTo(id) == 0){
                return true;
            }
        }
        return false;

    }

    /**
     * This method replaces the username of a users
     * @param id the networkID of the user
     * @param name the replacement name of the user
     * @return the position of the user in the collection
     */
    public int replaceUsername(String id, String name){
        for(int i = 0 ; i < userCollection.size(); i++ ){
            if(userCollection.get(i).getNetworkID().compareTo(id) == 0){
                userCollection.get(i).setUsername(name);
                return i;
            }
        }
        return -1;
    }

    /**
     * returns the user from the index given
     * @param i position in the collection
     * @return the user
     */
    public Users getUser(int i){
        return userCollection.get(i);
    }

    public Users getUser(String userID){
        for(int i = 0 ; i < userCollection.size(); i++ ){
            if(userCollection.get(i).getNetworkID().compareTo(userID) == 0){
                return userCollection.get(i);
            }
        }
        return null;
    }
}
