/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package intranetchatv3.display.pc;

import intranetchatv3.core.UserCollection;
import intranetchatv3.df.User;
import intranetchatv3.display.userListRenderer;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.*;

/**
 *
 * @author Philip
 */
public class AddDialog extends JDialog implements ActionListener {
    private DefaultListModel uc;
    private PrivateChat parent;
    private UserCollection userscol;
    private JList users;
    private Object[] added;
    public AddDialog(PrivateChat pc) {
        super((JFrame)pc, "Add User", true);
        userscol = UserCollection.getInstance();
        parent = pc;
        buildFrame();
        pack();
        getFreeUsers();
    }

    private void buildFrame(){
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH; // components grow in both dimensions
        c.insets = new Insets(2, 2, 2, 2);

        JLabel label = new JLabel("Select Users");
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        c.gridheight = 1;
        c.weighty = 1.0;
        c.weightx = 0.0;
        this.add(label, c);

        users = new JList();
        users.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        uc = new DefaultListModel();
        users.setModel(uc);
        users.setCellRenderer(new userListRenderer());
        JScrollPane uscroll = new JScrollPane(users);
        Dimension d = new Dimension(200,400);
        uscroll.setPreferredSize(d);
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 2;
        c.gridheight = 4;
        c.weighty = 1.0;
        c.weightx = 0.0;
        this.add(uscroll, c);

        JButton button = new JButton("Add Users");
        button.addActionListener(this);
        c.gridx = 0;
        c.gridy = 5;
        c.gridwidth = 2;
        c.gridheight = 1;
        c.weighty = 1.0;
        c.weightx = 0.0;
        this.add(button, c);
    }

    private void getFreeUsers(){
        Object[] us = userscol.getUsers();
        for(int i = 0 ; i < us.length ; i++){
            User u = (User)us[i];
            if(!currentParticipant(u.getID())){
                uc.addElement(u);
            }
        }
        if(uc.size() == 0){
            User u = new User("No other users online","");
            uc.addElement(u);
        }
    }

    private boolean currentParticipant(String id){
        ArrayList<String> us = parent.parts;
        Iterator<String> i = us.iterator();
        while(i.hasNext()){
            if(i.next().compareTo(id)==0){
                return true;
            }
        }
        return false;
    }

    public void actionPerformed(ActionEvent e) {
        if(uc.size() == 1){
            if(users.getSelectedIndex() != -1){
                User u = (User)uc.get(0);
                if(u.getID().compareTo("")!=0){
                    added = new Object[1];
                    added[0] = users.getSelectedValue();
                }
            }
            dispose();
        }else{
            if(users.getSelectedIndices().length > 0){
                added = users.getSelectedValues();
            }
        }
    }

    public Object[] getAddedUsers(){
        return added;
    }

}
