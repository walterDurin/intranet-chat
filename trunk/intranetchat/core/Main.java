/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package intranetchat.core;


import intranetchat.display.MainDisplay;
import java.util.Observable;

/**
 *
 * @author Philip
 */
public class Main {
    private MainDisplay display;


    public Main () {
        NetworkInterface network = NetworkInterface.getInstance();
        Observable obs = new NetworkListener();
        display = new MainDisplay(obs, network);
        (new Thread((NetworkListener)obs)).start();
    }

    public void run(){
        display.setVisible(true);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Main m = new Main();
        m.run();
    }

}
