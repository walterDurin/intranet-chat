/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package intranetchatv3.display;

import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author Philip
 */
public class AboutPanel extends JPanel{
    private Image img;

    /**
     * Main constructor for the aboutpanel
     */
    public AboutPanel() {
        try{
          img = ImageIO.read(new URL(getClass().getResource("about-logo.png"), "about-logo.png"));
        }catch(Exception e){}
    }

    /**
     * Paints the image onto the panel
     * @param g Graphics
     */
    public void paintComponent(Graphics g) {
        g.drawImage(img, 0, 0, null);
    }
}
