/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package intranetchatv3.display;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import javax.swing.JFrame;


/**
 *
 * @author Philip
 */
public class Splash extends JFrame{

    public Splash(){
        this.setUndecorated(true);
        buildFrame();
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        int cX = d.width/2;
        int cY = d.height/2;
        int x = 640/2;
        int y = 200/2;
        this.setLocation(cX - x, cY - y);
        pack();
    }

    private void buildFrame(){

        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH; // components grow in both dimensions
        c.insets = new Insets(0, 0, 0, 0);

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.weighty = 0.0;
        c.weightx = 0.0;
        SplashPanel panel = new SplashPanel();
        Dimension d = new Dimension(640,200);
        panel.setPreferredSize(d);
        panel.setBackground(Color.red);
        this.add(panel,c);
    }
}
