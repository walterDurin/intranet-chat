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

    public static final String DEFAULT_PATH = "inc.conf";
    private static SavedValues instance;
    private ReadFiles reader;
    private WriteFiles writer;
    /** This is the port number that the program is using **/
    public int port;
    /** The network id that is being used by the program **/
    public int networkID;
    /** The username that is being used by the user **/
    public String networkName;
    /** The programs current look and feel **/
    public String landf;
    /** The program x location **/
    public int x;
    /** The programs y location **/
    public int y;
    /** The currently used font **/
    public Font font;
    /** The background colour of the displays **/
    public Color background;
    /** The font colour of the program **/
    public Color foreGround;
    /** This is the system message colour **/
    public Color systemColour;
    /** Whether the sound effect is played when another user enters **/
    public boolean soundEntrance;
    /** Whether the sound effect is played when a message is received **/
    public boolean soundMessage;
    /** Whether the private chat messages are logged **/
    public boolean privateLog;
    /** Whether the public chat messages are logged **/
    public boolean publicLog;
    /** Whethere the private chat message are encrypted **/
    public boolean encrypted;

    public static synchronized SavedValues getInstance(){
        if(instance == null){
            instance = new SavedValues();
        }
        return instance;
    }

    /**
     * This is where all the default values for the saved values are created
     */
    private SavedValues(){
        reader = new ReadFiles();
        writer = new WriteFiles();
        port = 5454;
        networkID = 0000000;
        networkName = "new user";
        x = 200;
        y = 200;
        background = Color.WHITE;
        foreGround = Color.BLACK;
        systemColour = Color.RED;
        font = new Font("Dialog",Font.PLAIN,12);
        landf = "Metal";
        soundEntrance = false;
        soundMessage = false;
        privateLog = false;
        publicLog = false;
        encrypted = false;
    }

    /**
     * This will be called when the program wants to load the values from the file
     * @param variables the saved variables
     */
    public void updateValues(String variables){
        String[] variableArray = variables.split("/z");
        port = Integer.parseInt(variableArray[0]);
        networkID = Integer.parseInt(variableArray[1]);
        networkName = variableArray[2];
        x = Integer.parseInt(variableArray[3]);
        y = Integer.parseInt(variableArray[4]);
        background = this.getColour(variableArray[5]);
        foreGround = this.getColour(variableArray[6]);
        font = this.getUserFont(variableArray[7]);
        landf = variableArray[8];
        soundEntrance = this.intConverter(Integer.parseInt(variableArray[9]));
        soundMessage = this.intConverter(Integer.parseInt(variableArray[10]));
        privateLog = this.intConverter(Integer.parseInt(variableArray[11]));
        publicLog = this.intConverter(Integer.parseInt(variableArray[12]));
        encrypted = this.intConverter(Integer.parseInt(variableArray[13]));
    }

    /**
     * This will load the values from a file located on the file system
     * @param path the path to the file
     */
    public void importValues(String path){
        if(reader.checkFilePresence(path)){
            String s = reader.readFile(path);
            updateValues(s);
        }else{
            exportValues(path);
        }
    }

    /**
     * This will export the system variables from the program into a file
     * @param path the location where the file is to be saved
     */
    public void exportValues(String path){
        StringBuffer buf = new StringBuffer("");
        buf.append(port+"/z");
        buf.append(networkID+"/z");
        buf.append(networkName+"/z");
        buf.append(x+"/z");
        buf.append(y+"/z");
        String s = background.getRed()+","+background.getGreen()+","+background.getBlue();
        buf.append(s+"/z");
        s = foreGround.getRed()+","+foreGround.getGreen()+","+foreGround.getBlue()+",";
        buf.append(s+"/z");
        s = font.getName()+","+font.getStyle()+","+font.getSize();
        buf.append(s+"/z");
        buf.append(landf+"/z");
        buf.append(booleanConverter(soundEntrance)+"/z");
        buf.append(booleanConverter(soundMessage)+"/z");
        buf.append(booleanConverter(privateLog)+"/z");
        buf.append(booleanConverter(publicLog)+"/z");
        buf.append(booleanConverter(encrypted)+"/z");
        String out = new String(buf);
        writer.writeFile(path, out);
    }

    /**
     * This will alert all of the observers that the values have been changed
     */
    public void ValuesChanged(){
        this.setChanged();
        this.notifyObservers();
    }

    /**
     * converts boolean values into int values so they can be saved
     * @param b the boolean to be changed
     * @return the int that will be saved
     */
    private int booleanConverter(boolean b){
        int i = 0;
        if(b){
            i = 1;
        }
        return i;
    }

    /**
     * Converts an int value into a boolean value to be read by the program
     * @param u an int from a saved value
     * @return the boolean to be used in the program
     */
    private boolean intConverter(int u){
        boolean b = false;
        if(u == 1){
            b = true;
        }
        return b;
    }

    /**
     * creates a Color class instance from the RGB values that are saved
     * @param colorVals RGB values that were saved
     * @return the Color instance for the program
     */
    private Color getColour(String colorVals){
        String[] cv = colorVals.split(",");
        Color c = new Color(Integer.parseInt(cv[0]),Integer.parseInt(cv[1]),Integer.parseInt(cv[2]));
        return c;
    }

    /**
     * creates a Font class instance from the saved values
     * @param fontVals the font values that were saved
     * @return the Font instance for the program
     */
    private Font getUserFont(String fontVals){
        String[] fv = fontVals.split(",");
        return new Font(fv[0],Integer.parseInt(fv[1]),Integer.parseInt(fv[2]));

    }

    /**
     * Saves the log files in a given location
     * @param path the location where the file will be saved
     * @param log the log to be saved
     */
    public void saveLog(String path, String log){
        writer.writeFile(path, log);
    }

}
