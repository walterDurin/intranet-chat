/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.intrenetchat;

import javax.swing.JOptionPane;


/**
 * Class that Listens to messages on the network
 * @author Philip White
 */
public class NetworkListener implements Runnable{
    private Network net;
    private Gui gui;
    private Saving save;
    private Main parent;
    private PrivateArray priv;
    private AudioWarnings audio;
    /**
     * Constructor to start the thread that listens for messages
     * @param m Instance of the main
     */
    public NetworkListener(Main m){
        net = Network.getInstance();
        gui = Gui.getInstance();
        save = Saving.getInstance();
        priv = PrivateArray.getInstance();
        audio = AudioWarnings.getInstance();
        parent = m;
    }

    /**
     * Metohd that listens for incoming messages and deals with them accordingly
     */
    public void run() {
        do{
            String mess = net.recieveMulticast();
            String[] array = mess.split("~");
            if(array.length > 1){
                this.loop(array);
            }
            try{
                Thread.sleep(200);
            }catch(InterruptedException e){
                JOptionPane.showMessageDialog(null, "Error L01 please see administator for details");
            }
        }while(true);
    }
    
    private void loop(String[] array){
        switch(Integer.parseInt(array[1])){
            case 1:
                this.onScreen(array);
                break;
            case 2:
                this.SystemCall(array);
                break;
            case 3:
                this.PrivateMessage(array);
        }
    }
        
    private void onScreen(String[] array){
        String extra = "> ";
        if(!(array[3].compareTo("")== 0)){
            parent.stateChecker(false);
            if(array[0].compareTo(net.getUnique())==0){
                extra = "< "; 
            }
            gui.addMessage(extra+array[2]+": "+array[3]);
        } 
    }
    
    private void SystemCall(String[] array){
        switch(Integer.parseInt(array[3])){
            case 1:
                net.sendMulticast("~2~"+save.getName()+"~2");
                break;
            case 2:
                gui.addUser(array[0],array[2]);
                break;
            case 3:
                gui.removeUser(array[0],array[2]);
                if(!gui.isVisible()){
                    audio.UserLeft();
                }
                break;
            case 4:
                System.exit(0);
                break;
        }
    }
    
    private void PrivateMessage(String[] array){
        if(array[2].compareTo(net.getUnique())==0){
            if(!priv.isThere(array[0])){
                priv.connect(array[0], array[3]);
            }
            priv.PrivateMessage(priv.getIndex("> "+array[0]),array[3]+": ",array[4]);
        }
    }
}