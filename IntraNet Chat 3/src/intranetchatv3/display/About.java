/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package intranetchatv3.display;

import intranetchatv3.df.VariableStore;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Philip
 */
public class About extends JDialog implements ActionListener{
    private Font font;
    public About (JFrame parent){
        super(parent,"About",true);
        buildFrame();
        pack();
        this.setLocation(250,200);
    }

    private void buildFrame(){
        font = new Font("Ariel",Font.PLAIN,14);
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH; // components grow in both dimensions
        c.insets = new Insets(0, 0, 2, 0);

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.weighty = 1.0;
        c.weightx = 0.0;
        JPanel panel = new AboutPanel();
        Dimension d = new Dimension(320,100);
        panel.setPreferredSize(d);
        panel.setMinimumSize(d);
        panel.setMaximumSize(d);
        this.add(panel, c);
        c.insets = new Insets(0, 2, 0, 2);
        c.gridy = 1;
        JLabel copyright = new JLabel("© 2010 Philip White");
        copyright.setFont(font);
        this.add(copyright,c);

        c.gridy = 2;
        JLabel email = new JLabel("phil@antaresproject.me.uk");
        email.setFont(font);
        this.add(email,c);

        c.gridy = 3;
        JLabel site = new JLabel("www.antaresproject.me.uk");
        site.setFont(font);
        this.add(site,c);

        c.gridy = 4;
        JLabel version = new JLabel("Java Version : "+System.getProperty("java.version"));
        version.setFont(font);
        this.add(version,c);

        c.gridy = 5;
        JLabel os = new JLabel("OS : "+System.getProperty("os.name"));
        os.setFont(font);
        this.add(os,c);

        c.gridy = 6;
        JButton exit = new JButton("Close");
        exit.addActionListener(this);
        exit.setFont(font);
        this.add(exit,c);
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().compareTo("Close")==0){
            this.dispose();
        }
    }

}
