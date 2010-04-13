/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package intranetchatv3.display;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 *
 * @author Philip
 */
public class FrameListener implements WindowListener{

    public void windowOpened(WindowEvent e) {}
    public void windowClosing(WindowEvent e) {}
    public void windowClosed(WindowEvent e) {
        MainDisplay display = MainDisplay.getInstance();
        display.exitProgram();
    }
    public void windowIconified(WindowEvent e) {}
    public void windowDeiconified(WindowEvent e) {}
    public void windowActivated(WindowEvent e) {}
    public void windowDeactivated(WindowEvent e) {}

}
