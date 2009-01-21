/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.intrenetchat;

/**
 * Store for the usernames
 * @author Philip White
 */
public class FriendList {
    private volatile static FriendList uniqueInstance;
    private int nfl;
    private Friends[] friends;
    
    private FriendList(){
        nfl = 0;
        friends = new Friends[50];
    }
    
    /**
     * Returns an instance of the FriendList
     * @return The unique instance of the FriendList
     */
    public static synchronized FriendList getInstance(){
        if(uniqueInstance == null){
            synchronized(FriendList.class){
                uniqueInstance = new FriendList();
            }
        }
        return uniqueInstance;
    }
    
    /**
     * Adds a member to the friendArray
     * @param compname Computers Name
     * @param username Users Username
     */
    public void addfriend(String compname, String username){
        Friends f = new Friends(compname, username);
        friends[nfl] = f;
        nfl++;
    }
    
    /**
     * Checks to see if there is an instance of a member
     * @param compname Computers Name
     * @return whether it is present or not
     */
    public boolean checkFriend(String compname){
        boolean test = false;
        for(int i=0;i<nfl;i++){
            if(friends[i].getComp().compareTo(compname)==0){
                test = true;
            }
        }
        return test;
    }
    
    /**
     * Removes a member from the FriendArray
     * @param compname Computers Name
     */
    public void removeFriend(String compname){
        if(this.checkFriend(compname)){
            for(int i=0;i<nfl;i++){
                if(friends[i].getComp().compareTo(compname)==0){
                    nfl--;
                    friends[i] = friends[nfl];
                    friends[nfl] = null;
                }
            }
        }
    }
    
    /**
     * Returns the Computer Name from the username
     * @param username Users name
     * @return The unique computer name 
     */
    public String getCompName(String username){
        String res = "";
        for(int i=0;i<nfl;i++){
            if(friends[i].getHost().compareTo(username)==0){
                res = friends[i].getComp();
            }
        }
        return res;
    }
    
    /**
     * Changes the username 
     * @param compname Computers Name
     * @param uname Users Name
     */
    public void changeUsername(String compname,String uname){
        for(int i=0;i<nfl;i++){
            if(friends[i].getComp().compareTo(compname)==0){
                friends[i].setHost(uname);
            }
        }
    }
    
    /**
     * Returns the computers hostname
     * @param compname Computers Name
     * @return The hostname of the user
     */
    public String getHost(String compname){
        String res = "";
        for(int i=0;i<nfl;i++){
            if(friends[i].getComp().compareTo(compname)==0){
                res = friends[i].getHost();
            }
        }
        return res;
    }
}
