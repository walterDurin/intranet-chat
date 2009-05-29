/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package intranetchat.core;

/**
 * a singluar instance of a user
 * @author Philip
 */
public class Users {
    private String username;
    private String networkID;

    /**
     * sets the username and networkID
     * @param uname username
     * @param netID networkID
     */
    public Users(String uname, String netID){
        username = uname;
        networkID = netID;
    }

    /**
     * resets the username of the user
     * @param uname username
     */
    public void setUsername(String uname){
        username = uname;
    }

    /**
     * returns the username of the instance
     * @return username
     */
    public String getUsername(){
        return username;
    }

    /**
     * returns the networkID of the instance
     * @return networkID
     */
    public String getNetworkID(){
        return networkID;
    }
}
