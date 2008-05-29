/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.intrenetchat;

/**
 * Stores the private chats that are being used
 * @author Philip White
 */
public class PrivateArray {
    private PrivateChat[] array;
    private volatile static PrivateArray getInstance;
    private int nfl;

    private PrivateArray(int size){
        array = new PrivateChat[size];
        nfl = 0;

    }
    
    /**
     * Creates a unique instance of the private array
     * @return PrivateArray
     */
    public static PrivateArray getInstance(){
        if(getInstance == null){
            synchronized(PrivateArray.class){
                getInstance = new PrivateArray(50);
            }
        }
        return getInstance;
    }
    
    /**
     * Starts a private chat instance
     * @param cUser Computers name to connect to
     * @param us Other username
     * @return Whether or not it was successfully created
     */
    public boolean connect(String cUser,String us){
        PrivateChat p;
       boolean test = false; 
       if(nfl < array.length){
           (new Thread(p = new PrivateChat(cUser,us,this))).start();
           array[nfl] = p;
           nfl++;
       }
       return test;
    }
    
    /**
     * Disconnects private chat
     * @param cUser computers username
     */
    public void disconnect(String cUser){
        if(this.isThere(cUser)){
            int loc = this.getIndex(cUser);
            nfl--;
            array[loc] = array[nfl];
            array[nfl] = null;
        }
    }
    
    /**
     * Checks to see if there was an instance of private chat already
     * @param cUser Computers username
     * @return If it's present
     */
    public boolean isThere(String cUser){
        boolean test = false;
        for(int i=0;i < nfl;i++){
            if(array[i].getCUser().compareTo(cUser)==0){
                test = true;
            }
        }      
        return test;
    }
    
    /**
     * Returns the index of the private chat
     * @param cUser Computers username
     * @return Index number of the object
     */
    public int getIndex(String cUser){
        int index = 0;
        for(int i=0;i < nfl;i++){
            if(array[i].getCUser().compareTo(cUser)==0){
                index = i;
            }
        }
        return index;
    }

    /**
     * Returns the PrivateChat instance
     * @param cUser Computer username
     * @return Instance of the private chat
     */
    public PrivateChat returnUser(String cUser){
        PrivateChat p = null;
        for(int i=0;i < array.length;i++){
            if(array[i].getCUser().compareTo(cUser)==0){
                p = array[i];
            }
        }
        return p;
    }
    
    /**
     * Appends a message on a private chat window
     * @param index Array index
     * @param message Incoming message
     */
    public void PrivateMessage(int index, String name, String message){
        array[index].appendMessage(name ,message);
    }
}
