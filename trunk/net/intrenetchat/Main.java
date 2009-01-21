/*
 * Main.java
 *
 * Created on 01 February 2008, 09:58
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package net.intrenetchat;


import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;


/**
 * This is the Main class used to start the program
 * @author Philip White
 */
public class Main {
    private Saving save;
    private Network n1;
    private Gui g1;
    private TrayIcon trayIcon;
    
    /**
     * This is the Main constructor for the main class
     */
    public Main() {
        save = Saving.getInstance();
        if(!save.loadLocation()){
            String name = JOptionPane.showInputDialog(g1, "Please Enter A Username");
            if(name.compareTo("")!=0){
                save.setName(name);
            }
            save.saveVariables();
            save.saveLocation();
        }
        save.loadLocation();
        save.loadVariables();
        n1 = Network.getInstance();
        g1 = Gui.getInstance();
        g1.setVisible(true);
        (new Thread(new NetworkListener(this))).start();
        n1.sendMulticast("~2~"+save.getName()+"~1~");
    }
    
    /**
     * This method is runn when the program is exiting
     */
    public void leave(){
        n1.sendMulticast("2~"+save.getName()+"~3");
        save.saveVariables();
        save.saveLocation();
        System.exit(0);
    }
    
    /**
     * This is the method that load the system tray
     */
    private void systemTray() {
        //Check the SystemTray support
        if (!SystemTray.isSupported()) {
            System.out.println("SystemTray is not supported");
            g1.setWindowsState(1);
            return;
        }
        g1.setWindowsState(0);
        PopupMenu popup = new PopupMenu();
        trayIcon = new TrayIcon(createImage("network.png", "tray icon"));
        SystemTray tray = SystemTray.getSystemTray();
        // Create a popup menu components
        MenuItem aboutItem = new MenuItem("About");
        MenuItem openMain = new MenuItem("Open Main");
        MenuItem close = new MenuItem("Exit Program");
        //Add components to popup menu
        popup.add(aboutItem);
        popup.add(openMain);
        popup.add(close);
        trayIcon.setPopupMenu(popup);
        trayIcon.setImageAutoSize(true);
        
        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            System.out.println("TrayIcon could not be added.");
            return;
        }
        trayIcon.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                g1.setVisible(true);
                stateChecker(true);
            }
        });
        aboutItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               About ab = new About(g1, true,save.getX()+50,save.getY()+20);
               ab.setVisible(true);
            }
        });
        openMain.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                g1.setVisible(true);
                stateChecker(true);
            }
        });
        close.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                leave();
            }
        });
    }
    
    /**
     * creates a icon for the system tray
     * @param path file path
     * @param description description of the picture
     * @return The image for the system tray
     */
    protected Image createImage(String path, String description) {
        URL imageURL = Main.class.getResource(path);
        
        if (imageURL == null) {
            System.err.println("Resource not found: " + path);
            return null;
        } else {
            return (new ImageIcon(imageURL, description)).getImage();
        }
    }
    
    /**
     * checks to see if there is an incoming message and if the program is minimalized
     * @param state true or false
     */
    public void stateChecker(boolean state){
        if((!g1.isVisible())&&(!state)){
            if (trayIcon != null) {
                trayIcon.setImage(this.createImage("network2.png", "tray icon"));
            }
        }else if(state){
            if (trayIcon != null) {
                trayIcon.setImage(this.createImage("network.png", "tray icon"));
            }
        }
    }

    /**
     * Method to start the program
     * @param args the command line arguments 
     */
    public static void main(String[] args) {
        Main m1 = new Main();
        m1.systemTray();
    }
}