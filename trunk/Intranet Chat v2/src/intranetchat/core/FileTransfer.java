/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package intranetchat.core;

import intranetchat.saving.SavedValues;
import java.io.File;
import java.util.Observable;
import java.util.Observer;

/**
 *
 * @author Philip
 */
public class FileTransfer extends Observable implements Observer{
    SavedValues values;
    boolean authenticated = false;
    int position = 0;

    public FileTransfer(){
        values = SavedValues.getInstance();
    }

    public void sendFile(File f){

    }

    public void getFile(String ip,int fileSize){

    }

    public void update(Observable o, Object arg) {
        if(o instanceof NetworkListener){
            NetworkListener nl = (NetworkListener)o;
            String in = nl.getMessage();
            String[] breakup = in.split("~");
            //Checks to see if the message received is a file transfer reply method
            if(Integer.parseInt(breakup[1]) == 2){
                if(Integer.parseInt(breakup[3]) == 6){
                    if(Integer.parseInt(breakup[2]) == values.networkID){
                        if(breakup[5].compareTo("YES")==0){
                            authenticated = true;
                            
                        }else{
                            authenticated = false;
                        }
                        this.setChanged();
                        this.notifyObservers();
                    }
                }
            }
        }
    }

    public boolean isAuthenticated(){
        return authenticated;
    }

    public int getPosition(){
        return position;
    }

    public String getLocalIP(){


        return "192.168.2.7";
    }

}
