/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.intrenetchat;

import javax.swing.*;
import java.awt.*;

/**
 * Picture Panel for the About window
 * @author Philip White
 */
public class AboutPanel extends JPanel{
    private Image img;
    
    /**
     * Main constructor for the aboutpanel
     */
    public AboutPanel() {
    try{
      img = javax.imageio.ImageIO.read(new java.net.URL(getClass().getResource("abban.png"), "abban.png"));
    }catch(Exception e){
        System.out.println("Error AP01 please see administator for details");
        }
    }

    /**
     * Paints the image onto the panel
     * @param g Graphics
     */
    public void paintComponent(Graphics g) {
        g.drawImage(img, 0, 0, null);
    }
}
