/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package intranetchatv3.display.pc;

import intranetchatv3.core.*;
import intranetchatv3.df.*;
import intranetchatv3.display.EmoticonStyle;
import intranetchatv3.display.userListRenderer;
import intranetchatv3.saving.FileSaving;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.*;
import javax.swing.*;
import javax.swing.text.*;

/**
 *
 * @author Philip
 */
public class PrivateChat extends JFrame implements ActionListener, WindowListener{
    private NetworkInterface network;
    private VariableStore store;
    private Encryption encrypt;
    public String chatID;
    public ArrayList<String> parts;
    private JList participants;
    private JLabel name;
    private JTextPane input;
    private JTextField output;
    private DefaultListModel party;
    private StyledDocument cDoc;
    private EmoticonStyle eStyle;

    public PrivateChat(String id, ArrayList<String> users) {
        encrypt = new Encryption();
        chatID = id;
        parts = users;
        store = VariableStore.getInstance();
        network = NetworkInterface.getInstance();
        party = new DefaultListModel();
        buildFrame();
        menuBuild();
        cDoc = input.getStyledDocument();
        eStyle = new EmoticonStyle(cDoc);
        this.setLocation(300, 200);
        pack();
        getUsers();
        participants.setBackground(store.back);
        output.setBackground(store.back);
        input.setBackground(store.back);
        this.addWindowListener(this);
    }

    private void buildFrame(){
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH; // components grow in both dimensions
        c.insets = new Insets(2, 2, 2, 2);

        participants = new JList();
        participants.setModel(party);
        participants.setCellRenderer(new userListRenderer());
        JScrollPane uscroll = new JScrollPane(participants);
        Dimension d = new Dimension(100,300);
        uscroll.setPreferredSize(d);
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        c.gridheight = 4;
        c.weighty = 1.0;
        c.weightx = 0.0;
        this.add(uscroll, c);

        name = new JLabel(store.username);
        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.weighty = 1.0;
        c.weightx = 0.0;
        this.add(name, c);

        input = new JTextPane();
        input.setEditable(false);
        JScrollPane inscroll = new JScrollPane(input);
        d = new Dimension(200, 300);
        inscroll.setPreferredSize(d);
        c.gridx = 2;
        c.gridy = 0;
        c.gridwidth = 3;
        c.gridheight = 4;
        c.weighty = 1.0;
        c.weightx = 0.0;
        this.add(inscroll, c);

        output = new JTextField();
        c.gridx = 1;
        c.gridy = 4;
        c.gridwidth = 4;
        c.gridheight = 1;
        c.weighty = 1.0;
        c.weightx = 0.0;
        this.add(output, c);
        output.addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    sendMessage();
                }
            }
            public void keyTyped(KeyEvent e) {}
            public void keyReleased(KeyEvent e) {}});
    }

    private void menuBuild(){
        JMenuBar menu = new JMenuBar();
        JMenu file = new JMenu("File");
        menu.add(file);
        JMenuItem clear = new JMenuItem("Clear Screen");
        clear.addActionListener(this);
        file.add(clear);
        JMenuItem saveChat = new JMenuItem("Save Conversation");
        file.add(saveChat);
        saveChat.addActionListener(this);
        JMenuItem closeWin = new JMenuItem("Close Window");
        file.add(closeWin);
        closeWin.addActionListener(this);
        JMenu users = new JMenu("Users");
        menu.add(users);
        JMenuItem addUser = new JMenuItem("Add Users");
        addUser.addActionListener(this);
        users.add(addUser);
        this.setJMenuBar(menu);
    }

    public void appendMessage(NetworkObj o){
        String message;
        if(o.getEncrypted()){
            message = encrypt.decryptString(o.getMessage());
        }else{
            message = o.getMessage();
        }
        StringBuffer buf = new StringBuffer(getTime());
        buf.append(" : ");
        buf.append(o.getSource());
        buf.append(" : ");
        buf.append(message);

        MutableAttributeSet chatAttr = new SimpleAttributeSet();
            if(o.getSourceID().compareTo(store.networkID)==0){
               StyleConstants.setForeground( chatAttr, store.out );
            }else{
               StyleConstants.setForeground( chatAttr, store.in );
            }
        String[] out = new String(buf).split(" ");
        try{
        for(int i = 0; i < out.length; i++){
            if(eStyle.isInList(out[i]) && store.emoticons){
                cDoc.insertString(cDoc.getLength(), out[i], cDoc.getStyle(out[i]));
                cDoc.insertString(cDoc.getLength(), " ", chatAttr);
            }else{
                cDoc.insertString(cDoc.getLength(), out[i]+" ", chatAttr);
            }
        }
        cDoc.insertString(cDoc.getLength(), "\n", chatAttr);
        input.setCaretPosition(cDoc.getLength());
        } catch (BadLocationException ex) {}
    }

    public void addUsers(NetworkObj o){
        parts = o.getList();
        getUsers();
    }

    public void removeUser(NetworkObj o){
        String rem = o.getSourceID();
        parts.remove(rem);
        getUsers();
    }

    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        
        if(action.compareTo("Clear Screen")==0){
            input.setText("");
        }else if(action.compareTo("Save Conversation")==0){
            FileSaving fs = new FileSaving();
            fs.saveLog(output.getText(), chatID+"_private.log");
        }else if(action.compareTo("Close Window")==0){
            closeWindow();
            this.dispose();
        }else if(action.compareTo("Add Users")==0){
            AddDialog ad = new AddDialog(this);
            ad.setVisible(true);
            Object[] add = ad.getAddedUsers();
            if(add != null){
                //add to database and send to network
                for( int i = 0 ; i < add.length ; i++ ){
                    User u = (User) add[i];
                    parts.add(u.getID());
                }
                getUsers();
                broadcastAddition();
            }
        }else if(action.compareTo("Send")==0){
            sendMessage();
        }
    }

    private void closeWindow(){
        NetworkObj no = new NetworkObj();
        no.setSource(store.username);
        no.setSourceID(store.networkID);
        no.setList(parts);
        no.setType(MessageTypes.PRIVATE_USERLEFT);
        no.setPrivateID(chatID);
        try {
            network.sendMulticast(no);
        } catch (IOException ex) {}
    }

    private void getUsers(){
        party.clear();
        UserCollection u = UserCollection.getInstance();
        Iterator<String> i = parts.iterator();
        while(i.hasNext()){
            party.addElement(u.getUser(i.next()));
        }

    }

    private String getTime(){
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR);
        String minute = calendar.get(Calendar.MINUTE)+"";
        String second = calendar.get(Calendar.SECOND)+"";
        if(calendar.get(Calendar.AM_PM)== Calendar.PM){
            hour = hour + 12;
        }
        String h = hour+"";
        if(h.length() < 2){
            h = "0"+h;
        }
        if(minute.length() < 2){
            minute = "0"+minute;
        }
        if(second.length() < 2){
            second = "0"+second;
        }
        return "["+h+":"+minute+":"+second+"]";
    }

    private void broadcastAddition(){
        NetworkObj no = new NetworkObj();
        no.setSource(store.username);
        no.setSourceID(store.networkID);
        no.setPrivateID(chatID);
        no.setType(MessageTypes.PRIVATE_USERADD);
        no.setList(parts);
        try{
            network.sendMulticast(no);
        }catch(IOException e){}
    }

    private void sendMessage(){
        String out = output.getText();
        output.setText("");
        NetworkObj no = new NetworkObj();
        no.setSource(store.username);
        no.setSourceID(store.networkID);
        no.setList(parts);
        no.setType(MessageTypes.PRIVATE_MESSAGE);
        no.setPrivateID(chatID);
        String outMessage;
        if(store.encrypted){
            no.setEncrypted(true);
            outMessage = encrypt.encryptString(out);
        }else{
            no.setEncrypted(false);
            outMessage = out;
        }
        no.setMessage(outMessage);
        try {
            network.sendMulticast(no);
        } catch (IOException ex) {}
    }

    public void windowClosed(WindowEvent e) {}
    public void windowOpened(WindowEvent e) {}
    public void windowClosing(WindowEvent e) {
        closeWindow();
    }
    public void windowIconified(WindowEvent e) {}
    public void windowDeiconified(WindowEvent e) {}
    public void windowActivated(WindowEvent e) {}
    public void windowDeactivated(WindowEvent e) {}
}