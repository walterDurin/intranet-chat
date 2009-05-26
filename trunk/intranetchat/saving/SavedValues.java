/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package intranetchat.saving;

import java.awt.Color;
import java.awt.Font;
import java.util.Observable;

/**
 * This class is designed to store all of the system variables for the program
 * @author Philip White
 */
public class SavedValues extends Observable {
    private static SavedValues instance;
    public int port;
    public int networkID;
    public int x;
    public int y;
    public Font font;
    public Color background;
    public Color foreGround;

    public static synchronized SavedValues getInstance(){
        if(instance == null){
            instance = new SavedValues();
        }
        return instance;
    }

    private SavedValues(){
        port = 5454;
        networkID = 0000000;
        x = 200;
        y = 200;
    }

    public void inputValues(String variables){
        
    }

    public void ValuesChanged(){
        this.setChanged();
        this.notifyObservers();
    }

}
