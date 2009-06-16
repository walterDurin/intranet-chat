/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package intranetchat.core;

import java.io.File;
import java.util.Observable;


/**
 *
 * @author philip
 */
public class FileServer extends Observable implements Runnable{
    File outGoing;

    public FileServer(File f){
        outGoing = f;
    }

    public void run() {
        
    }

}
