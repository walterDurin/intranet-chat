/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package intranetchatv3.display;

import intranetchatv3.core.*;
import intranetchatv3.df.*;
import intranetchatv3.saving.VariableSave;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import javax.swing.*;
import javax.swing.text.*;

/**
 *
 * @author Philip
 */
public class MainDisplay extends JFrame {
    private static volatile MainDisplay instance;
    private NetworkInterface net;
    private VariableStore store;
    private MainDisplayListener listen;
    private UserListener listListen;
    private JTextPane contents;
    private JList users;
    private JLabel username;
    private JTextField transmit;
    private StyledDocument cDoc;
    private DefaultListModel userList;
    private EmoticonStyle eStyle;

    public static synchronized MainDisplay getInstance(){
        if(instance == null){
            instance = new MainDisplay();
        }
        return instance;
    }

    private MainDisplay(){
        super("IntraNet Chat V3.0");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        store = VariableStore.getInstance();
        net = NetworkInterface.getInstance();
        listen = new MainDisplayListener();
        listListen = new UserListener();
        this.setLocation(store.location);
        buildLayout();
        menuLayout();
        updateSettings();
        cDoc = contents.getStyledDocument();
        eStyle = new EmoticonStyle(cDoc);
        sendJoined();
    }

    private void menuLayout(){
        JMenuBar menuBar = new JMenuBar();
        JMenu file = new JMenu("File");
        menuBar.add(file);
        JMenuItem clear = new JMenuItem("Clear Screen");
        clear.addActionListener(listen);
        file.add(clear);
        JMenuItem sc = new JMenuItem("Save Chat");
        sc.addActionListener(listen);
        file.add(sc);
        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(listen);
        file.add(exit);
        JMenu setting = new JMenu("Settings");
        menuBar.add(setting);
        JMenuItem pref = new JMenuItem("Preference");
        pref.addActionListener(listen);
        setting.add(pref);
        JMenu help = new JMenu("Help");
        menuBar.add(help);
        JMenuItem about = new JMenuItem("About");
        about.addActionListener(listen);
        help.add(about);
        this.setJMenuBar(menuBar);
    }

    private void buildLayout(){

        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH; // components grow in both dimensions
        c.insets = new Insets(2, 2, 2, 2);

        //User List
        userList = new DefaultListModel();
        users = new JList();
        users.setModel(userList);
        users.addMouseListener(listListen);
        users.setCellRenderer(new userListRenderer());
        JScrollPane uscroll = new JScrollPane(users);
        Dimension d = new Dimension(150,400);
        uscroll.setPreferredSize(d);
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        c.gridheight = 6;
        c.weighty = 1.0;
        c.weightx = 0.0;
        this.add(uscroll, c);
        
        //Main Screen
        contents = new JTextPane();
        contents.setEditable(false);
        JScrollPane cscroll = new JScrollPane(contents);
        d = new Dimension(600,400);
        cscroll.setPreferredSize(d);
        c.gridx = 2;
        c.gridy = 0;
        c.gridwidth = 10;
        c.gridheight = 6;
        c.weightx = c.weighty = 1.0;
        this.add(cscroll, c);

        username = new JLabel();
        username.addMouseListener(listen);
        c.insets = new Insets(2, 10, 2, 10);
        c.gridx = 0;
        c.gridy = 6;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.weightx = c.weighty = 0.0;
        this.add(username, c);

        //data to be transmitted
        transmit = new JTextField();
        c.insets = new Insets(2, 2, 2, 2);
        c.gridx = 1;
        c.gridy = 6;
        c.gridwidth = 11;
        c.gridheight = 1;
        c.weightx = c.weighty = 0.0;
        this.add(transmit, c);
        transmit.addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    sendMessage();
                }
            }
            public void keyTyped(KeyEvent e) {}
            public void keyReleased(KeyEvent e) {}});
        username.setText(store.username);
        this.addWindowListener(new FrameListener());
    }

    public void addContents(String message, Color text){
        MutableAttributeSet chatAttr = new SimpleAttributeSet();
        StyleConstants.setForeground( chatAttr, text );
        String[] out = message.split(" ");
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
        contents.setCaretPosition(cDoc.getLength());
        } catch (BadLocationException ex) {}
    }

    public void clearContents(){
        contents.setText("");
    }

    public String getContents(){
        return contents.getText();
    }

    private void setDisplayLF(String name){
        UIManager.LookAndFeelInfo plaf[] = UIManager.getInstalledLookAndFeels();
        for (int i = 0, n = plaf.length; i < n; i++) {
            if(plaf[i].getName().compareTo(name)==0){
                try{
                    UIManager.setLookAndFeel(plaf[i].getClassName());
                    SwingUtilities.updateComponentTreeUI(this);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    public void sendMessage(){
        NetworkObj out = new NetworkObj();
        out.setSource(store.username);
        out.setSourceID(store.networkID);
        out.setType(MessageTypes.PUBLIC_MESSAGE);
        out.setMessage(transmit.getText());
        transmit.setText("");
        try {
            net.sendMulticast(out);
        } catch (IOException ex) {
            System.out.println("Transmit Error");
        }
    }

    public void userList(){
        UserCollection uc = UserCollection.getInstance();
        Object[] ul = uc.getUsers();
        userList.clear();
        for(int i = 0;i < ul.length;i++){
            User u = (User)ul[i];
            userList.addElement(u);
        }
    }

    private void sendJoined(){
        NetworkObj out = new NetworkObj();
        out.setSource(store.username);
        out.setSourceID(store.networkID);
        out.setType(MessageTypes.USER_JOINED);
        try {
            net.sendMulticast(out);
        } catch (IOException ex) {
            System.out.println("Transmit Error");
        }
    }

    public Object getUsers(){
        return users.getSelectedValue();
    }

    public void updateSettings(){
        if(username.getText().compareTo(store.username) != 0){
            username.setText(store.username);
            nameChange();
        }
        contents.setFont(store.font);
        transmit.setFont(store.font);
        users.setFont(store.font);
        contents.setBackground(store.back);
        users.setBackground(store.back);
        transmit.setBackground(store.back);
        setDisplayLF(store.lf);
        pack();
    }

    private void nameChange(){
        NetworkObj netObj = new NetworkObj();
        netObj.setSource(store.username);
        netObj.setSourceID(store.networkID);
        netObj.setType(MessageTypes.USERNAME_CHANGE);
        try{
        net.sendMulticast(netObj);
        }catch(IOException e){}
    }

    public void exitProgram(){
        VariableSave vs = new VariableSave();
        vs.saveValues();
        NetworkObj netObj = new NetworkObj();
        netObj.setSource(store.username);
        netObj.setSourceID(store.networkID);
        netObj.setType(MessageTypes.USER_LEFT);
        try{
            net.sendMulticast(netObj);
        }catch(IOException e){}
        System.exit(0);
    }
}