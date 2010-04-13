/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package intranetchatv3.df;

/**
 *
 * @author Philip
 */
public class User {
    private String username;
    private String netid;

    public User (String name, String id){
        username = name;
        netid = id;
    }

    public String getName(){
        return username;
    }

    public String getID(){
        return netid;
    }

    public void setName(String name){
        username = name;
    }

}
