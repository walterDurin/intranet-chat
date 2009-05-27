/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package intranetchat.core;

/**
 *
 * @author Philip
 */
public class Users {
    private String username;
    private String networkID;

    public Users(String uname, String netID){
        username = uname;
        networkID = netID;
    }

    public void setUsername(String uname){
        username = uname;
    }

    public String getUsername(){
        return username;
    }

    public String getNetworkID(){
        return networkID;
    }
}
