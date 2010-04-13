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
public class SplashPanel extends JPanel{
            private Image img;

            public SplashPanel(){
                try{
                    img = ImageIO.read(new URL(getClass().getResource("startup-logo.png"), "startup-logo.png"));
                }catch(Exception e){ }
            }
            @Override
            public void paintComponent(Graphics g) {
                g.drawImage(img, 0, 0, null);
            }
}
