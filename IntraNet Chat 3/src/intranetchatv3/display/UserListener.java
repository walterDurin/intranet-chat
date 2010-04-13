/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package intranetchatv3.display;


import intranetchatv3.core.PrivateChatController;
import intranetchatv3.df.User;
import intranetchatv3.df.VariableStore;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 *
 * @author Philip
 */
public class UserListener implements MouseListener{


    public void mouseClicked(MouseEvent e) {
        if(e.getClickCount() > 1 && e.getClickCount() < 3){
            MainDisplay m = MainDisplay.getInstance();
            VariableStore store = VariableStore.getInstance();
            PrivateChatController pcc = PrivateChatController.getInstance();
            Object u = m.getUsers();
            User us = (User)u;
            if(us.getID().compareTo(store.networkID)!=0){
                pcc.creatChat(u);
            }
        }
    }

    public void mousePressed(MouseEvent e) {    }

    public void mouseReleased(MouseEvent e) {    }

    public void mouseEntered(MouseEvent e) {    }

    public void mouseExited(MouseEvent e) {    }

}
