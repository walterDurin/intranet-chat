/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package intranetchatv3.display;

import intranetchatv3.df.VariableStore;
import intranetchatv3.saving.FileSaving;
import intranetchatv3.saving.VariableSave;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Philip
 */
public class MainDisplayListener implements ActionListener, MouseListener{
    

    public void actionPerformed(ActionEvent e) {

        if(e.getActionCommand().compareTo("Send")==0){
            MainDisplay display = MainDisplay.getInstance();
            display.sendMessage();
        }else if(e.getActionCommand().compareTo("Preference")==0){
            MainDisplay display = MainDisplay.getInstance();
            Preferences p = new Preferences(display);
            p.setVisible(true);
            if(p.isChanged()){
                p.saveData();
                display.updateSettings();
                VariableSave vs = new VariableSave();
                vs.saveValues();
            }
        }else if(e.getActionCommand().compareTo("Clear Screen")==0){
            MainDisplay display = MainDisplay.getInstance();
            display.clearContents();
        }else if(e.getActionCommand().compareTo("Save Chat")==0){
            MainDisplay display = MainDisplay.getInstance();
            FileSaving fs = new FileSaving();
            fs.saveLog(display.getContents(), "public.log");
        }else if(e.getActionCommand().compareTo("Exit")==0){
            MainDisplay display = MainDisplay.getInstance();
            display.exitProgram();
        }else if(e.getActionCommand().compareTo("About")==0){
            MainDisplay display = MainDisplay.getInstance();
            About about = new About((JFrame)display);
            about.setVisible(true);
        }else{
            System.out.println("ERROR");
        }
    }

    public void mouseClicked(MouseEvent e) {
        if((e.getClickCount() > 1)&&(e.getClickCount() < 3)){
            String input = JOptionPane.showInputDialog("Enter new username:");
            if(input != null){
                VariableStore store = VariableStore.getInstance();
                store.username = input;
                MainDisplay display = MainDisplay.getInstance();
                display.updateSettings();
                VariableSave vs = new VariableSave();
                vs.saveValues();
            }
        }

    }

    public void mousePressed(MouseEvent e) {}

    public void mouseReleased(MouseEvent e) {}

    public void mouseEntered(MouseEvent e) {}

    public void mouseExited(MouseEvent e) {}

}
