/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package intranetchat.core;

import java.io.File;
import java.util.Observable;
import java.util.Observer;

/**
 *
 * @author Philip
 */
public class FileTransfer extends Observable implements Observer{
    boolean authenticated = false;
    int position = 0;
    public void sendFile(File f){

    }

    public void getFile(String ip,int fileSize){

    }

    public void update(Observable o, Object arg) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isAuthenticated(){
        return authenticated;
    }

    public int getPosition(){
        return position;
    }

}
