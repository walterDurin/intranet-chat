/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package intranetchatv3.core;

import intranetchatv3.df.VariableStore;
import java.awt.GraphicsEnvironment;

/**
 *
 * @author Philip
 */
public class FontThread implements Runnable{

    public void run() {
        VariableStore vs = VariableStore.getInstance();
        GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();
        vs.fonts = e.getAvailableFontFamilyNames();
    }

}
