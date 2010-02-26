/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package intranetchat.core;


import intranetchat.display.About;
import intranetchat.display.MainDisplay;
import intranetchat.display.Preference;
import intranetchat.display.StartDisplay;
import intranetchat.saving.SavedValues;
import java.awt.AWTException;
import java.awt.CheckboxMenuItem;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.Observable;
import javax.swing.ImageIcon;

/**
 *
 * @author Philip
 */
public class Main {
    private MainDisplay display;
    private TrayIcon icon;
    SavedValues values;
    CheckboxMenuItem mute;

    /**
     * This method will start the entire program sequence
     * first it loads the default saved values
     * this is followed by checking to find a saved file in the filesystem
     * after searching it is then added to the saved variables
     * once completed the system tray is created and put in place
     * then the network interface is started
     */
    public Main () {
        StartDisplay d = new StartDisplay();
        d.setVisible(true);
        values = SavedValues.getInstance();
        String os = System.getProperty("os.name");
        if(os.compareTo("Windows 7")==0||os.compareTo("Windows Vista")==0||os.compareTo("Windows XP")==0){
            values.DEFAULT_PATH = System.getProperty("user.home")+"\\inc.sets";
            
        }else{
            values.DEFAULT_PATH = "~\\inc\\inc.sets";
        }
        values.importValues(values.DEFAULT_PATH);
        MulticastInterface network = MulticastInterface.getInstance();
        Observable obs = new NetworkListener();
        display = new MainDisplay(obs, network);
        this.startTrayIcon();
        values.ValuesChanged();
        d.setVisible(false);
        (new Thread((NetworkListener)obs)).start();
    }

    /**
     * creates the image for the system tray icon
     * @param path path to the file
     * @param descrip name of the file
     * @return image for the tray icon
     */
    private Image createImage(String path, String descrip) {
        URL url = Main.class.getResource(path);
        if(url != null){
            return(new ImageIcon(url, descrip)).getImage();
        }
        return null;
    }

    /**
     * This method creates a tray icon mounts all of the menus on it and then
     * adds it to the system tray
     */
    private void startTrayIcon(){
        if(!SystemTray.isSupported()){
            display.traySupported = false;
            return;
        }
        icon = new TrayIcon(createImage("icon.png", "tray icon"));
        PopupMenu menu = new PopupMenu();
        SystemTray tray = SystemTray.getSystemTray();
        //creates all the menu items
        MenuItem about = new MenuItem("About");
        MenuItem pref = new MenuItem("Preferences");
        MenuItem open = new MenuItem("Show Chat");
        MenuItem exit = new MenuItem("Quit");
        mute = new CheckboxMenuItem("Mute Sounds");
        if(!(values.soundEntrance || values.soundMessage)){
            mute.setState(false);
        }
        //adds the menu items to the system tray
        menu.add(open);
        menu.addSeparator();
        menu.add(about);
        menu.add(pref);
        menu.add(mute);
        menu.addSeparator();
        menu.add(exit);
        icon.setPopupMenu(menu);
        icon.setImageAutoSize(true);
        //mounts the system tray
        try{
            tray.add(icon);
        }catch(AWTException ex){
            return;
        }
        //Sets the listeners for the tray commands
        icon.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                display.setVisible(true);
            }
        });

        open.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                display.setVisible(true);
            }
        });

        about.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                About about = new About(null ,false);
            }
        });

        pref.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                Preference prefs = new Preference(null, true);
            }
        });

        mute.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                if(mute.getState()){
                    values.soundEntrance = false;
                    values.soundMessage = false;
                }else{
                    values.soundEntrance = true;
                    values.soundMessage = true;
                }
            }
        });

        exit.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                display.saving();
                System.exit(0);
            }
        });
        
    }

    //runs the display methods
    public void run(){
        display.setVisible(true);
    }

    /**
     * Starts the full program
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Main m = new Main();
        m.run();
    }
}
