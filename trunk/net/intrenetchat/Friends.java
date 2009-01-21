/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.intrenetchat;

/**
 * Singular Friend instance
 * @author Philip White
 */
public class Friends {
    private String computerUsername;
    private String hostname;
    
    /**
     * Main Constructor
     * @param c ComputerUsername
     * @param h hostname
     */
    public Friends(String c,String h){
        computerUsername = c;
        hostname = h;
    }
    
    /**
     * Gets the Computers Username
     * @return The unique computers name
     */
    public String getComp(){
        return computerUsername;
    }
    
    /**
     * Gets the computer hostname
     * @return The users hostname
     */
    public String getHost(){
        return hostname;
    }
    
    /**
     * Sets the computers Username
     * @param c Computers Name
     */
    public void setComp(String c){
        computerUsername = c;
    }
    
    /**
     * sets the users hostname
     * @param h Users name
     */
    public void setHost(String h){
        hostname = h;
    }
}
