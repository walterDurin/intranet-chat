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
    public int port;
    public int networkID;
    public String networkName;
    public String landf;
    public int x;
    public int y;
    public Font font;
    public Color background;
    public Color foreGround;
    public boolean soundEntrance;
    public boolean soundMessage;
    public boolean privateLog;
    public boolean publicLog;
    public boolean encrypted;

    public static synchronized SavedValues getInstance(){
        if(instance == null){
            instance = new SavedValues();
        }
        return instance;
    }

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
        font = new Font("Trebuchet MS",Font.PLAIN,12);
        landf = "Windows";
        soundEntrance = false;
        soundMessage = false;
        privateLog = false;
        publicLog = false;
        encrypted = false;
    }

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
    
    public void importValues(String path){
        if(reader.checkFilePresence(path)){
            String s = reader.readFile(path);
            updateValues(s);
        }else{
            exportValues(path);
        }
    }

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

    public void ValuesChanged(){
        this.setChanged();
        this.notifyObservers();
    }
    
    private int booleanConverter(boolean b){
        int i = 0;
        if(b){
            i = 1;
        }
        return i;
    }
    
    private boolean intConverter(int u){
        boolean b = false;
        if(u == 1){
            b = true;
        }
        return b;
    }

    private Color getColour(String colorVals){
        String[] cv = colorVals.split(",");
        Color c = new Color(Integer.parseInt(cv[0]),Integer.parseInt(cv[1]),Integer.parseInt(cv[2]));
        return c;
    }

    private Font getUserFont(String fontVals){
        String[] fv = fontVals.split(",");
        return new Font(fv[0],Integer.parseInt(fv[1]),Integer.parseInt(fv[2]));

    }

    public void saveLog(String path, String log){
        writer.writeFile(path, log);
    }

}
