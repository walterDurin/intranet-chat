/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PrivateChat.java
 *
 * Created on 23-May-2009, 12:29:40
 */

package intranetchat.display;
import intranetchat.core.MulticastInterface;
import intranetchat.core.ChatEncryption;
import intranetchat.core.FileServer;
import intranetchat.core.NetworkListener;
import intranetchat.core.PrivateChatCollection;
import intranetchat.core.Sounds;
import intranetchat.saving.SavedValues;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.text.BadLocationException;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

/**
 *
 * @author Philip White
 * @version 1.0
 */
public class PrivateChat extends javax.swing.JFrame implements Observer{
    private Observable observable;
    public String destinationID;
    private String destinationName;
    private SavedValues values;
    private MulticastInterface network;
    private StringBuffer log;
    private StyledDocument cDoc;
    PrivateChatCollection parent;
    Sounds sound;
    ChatEncryption encrypt;

    /** Creates new form PrivateChat */
    public PrivateChat(Observable obs, String id, String name, PrivateChatCollection p) {
        values = SavedValues.getInstance();
        network = MulticastInterface.getInstance();
        observable = obs;
        observable.addObserver(this);
        destinationID = id;
        destinationName = name;
        parent = p;
        log = new StringBuffer("");
        this.setTitle("Private Chat with "+destinationName);
        this.setLocation(values.x+20,values.y+20);
        initComponents();
        cDoc = displayArea.getStyledDocument();
        sound = Sounds.getInstance();
        encrypt = ChatEncryption.getInstance();

        this.setVisible(true);

    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        displayArea = new javax.swing.JTextPane();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        send = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel1.setLayout(new java.awt.GridBagLayout());

        jScrollPane1.setPreferredSize(new java.awt.Dimension(400, 350));

        displayArea.setBackground(new java.awt.Color(254, 254, 254));
        displayArea.setEditable(false);
        jScrollPane1.setViewportView(displayArea);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 3, 0);
        jPanel1.add(jScrollPane1, gridBagConstraints);

        jTextField1.setPreferredSize(new java.awt.Dimension(335, 20));
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField1KeyPressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 3, 0);
        jPanel1.add(jTextField1, gridBagConstraints);

        jButton1.setText("Send");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jButton1MouseReleased(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 3, 0);
        jPanel1.add(jButton1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 4, 4);
        getContentPane().add(jPanel1, gridBagConstraints);

        jMenu1.setText("File");

        send.setText("Send File");
        send.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                sendMousePressed(evt);
            }
        });
        jMenu1.add(send);

        jMenuItem3.setText("Clear Screen");
        jMenuItem3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jMenuItem3MouseReleased(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuItem4.setText("Close ");
        jMenuItem4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jMenuItem4MouseReleased(evt);
            }
        });
        jMenu1.add(jMenuItem4);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Settings");

        jMenuItem1.setText("Preferences");
        jMenuItem1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jMenuItem1MouseReleased(evt);
            }
        });
        jMenu2.add(jMenuItem1);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Help");

        jMenuItem2.setText("About");
        jMenuItem2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jMenuItem2MouseReleased(evt);
            }
        });
        jMenu3.add(jMenuItem2);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        this.exitConversation();
    }//GEN-LAST:event_formWindowClosing

    private void jButton1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseReleased
        sendMessage();
        String s = getTime()+": "+values.networkName+" : "+jTextField1.getText()+"\n";
        this.appendMessage(s, null);
        jTextField1.setText("");
    }//GEN-LAST:event_jButton1MouseReleased

    private void jMenuItem1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuItem1MouseReleased
        Preference prefs = new Preference(this,true);
    }//GEN-LAST:event_jMenuItem1MouseReleased

    private void jMenuItem2MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuItem2MouseReleased
        About about = new About(this,true);
    }//GEN-LAST:event_jMenuItem2MouseReleased

    private void jMenuItem3MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuItem3MouseReleased
        displayArea.setText("");
        log.append("*** Screen Cleared *** \n");
    }//GEN-LAST:event_jMenuItem3MouseReleased

    private void jMenuItem4MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuItem4MouseReleased
        this.exitConversation();
    }//GEN-LAST:event_jMenuItem4MouseReleased

    private void jTextField1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            this.sendMessage();
            String s = getTime()+": "+values.networkName+" : "+jTextField1.getText()+"\n";
            this.appendMessage(s,null);
            jTextField1.setText("");
        }
    }//GEN-LAST:event_jTextField1KeyPressed

    private void sendMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sendMousePressed
        this.sendFile();
    }//GEN-LAST:event_sendMousePressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextPane displayArea;
    private javax.swing.JButton jButton1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JMenuItem send;
    // End of variables declaration//GEN-END:variables


    /**
     * Observer update method for the private chat will listen to the network
     * and saving window and update its local values when the method calls it
     * @param o observalble class
     * @param arg object thrown
     */
    public void update(Observable o, Object arg) {
        if(o instanceof NetworkListener){
            NetworkListener list = (NetworkListener)o;
            sortMessage(list.getMessage());
        }else if(o instanceof SavedValues){
            this.updateSettings();
        }
    }

    /**
     * this will add a string to the main screen of the private chat and allows
     * a user to change the colour of the message or send null to use the standard
     * colour
     * @param message the message to be sent to the screen
     * @param colour the colour of the message that the message is or null
     */
    private void appendMessage(String message, Color colour){
                try{
        MutableAttributeSet chatAttr = new SimpleAttributeSet();
        if(colour != null){
            StyleConstants.setForeground( chatAttr, colour );
        }
        cDoc.insertString( cDoc.getLength(), message, chatAttr );
        displayArea.setCaretPosition(cDoc.getLength());
        }catch(BadLocationException ex){}
        log.append(message);
    }

    /**
     * Will send a private message across the multicast system to the person on
     * the other end
     */
    private void sendMessage(){
        String mes = jTextField1.getText();
        if(values.encrypted){
            mes = encrypt.encryptChat(mes);
        }
        StringBuffer buf = new StringBuffer("3~");
        buf.append(destinationID+"~");
        buf.append(values.networkName+"~");
        buf.append(mes+"~");
        try{
            network.sendMulticast(new String(buf));
        }catch(IOException ex){
            JOptionPane.showMessageDialog(this, "The message was unable to be sent please check the network connection", "Message Not Sent", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Sorts the message and updates the screen if required
     * @param input incoming message
     */
    public void sortMessage(String input){
        String[] mes = input.split("~");

        //checks to see of the message is a private chat message or system message
        switch(Integer.parseInt(mes[1])){
            case 2:
                if(mes[0].compareTo(destinationID)==0){
                    if(Integer.parseInt(mes[3]) == 3){
                        appendMessage(getTime()+": "+ destinationName +" has left", values.systemColour);
                        jTextField1.setEditable(false);
                    }
                }
                break;

            case 3:
                //checks to see if the message is meant to be read by this certain program
                if(Integer.parseInt(mes[2]) == values.networkID){
                    //check to see if the message is meant for this private chat window
                    if(mes[0].compareTo(destinationID)==0){
                        //if the name of the user has changed then it will adjust the title of the window
                        if(mes[4].compareTo(destinationName)!=0){
                            destinationName = mes[3];
                            this.setTitle("Private Chat with "+destinationName);
                        }
                        //if the message is encrypted it will be sent to the decrypter
                        if(mes[4].charAt(0)== '^'){
                            this.appendMessage(getTime()+": "+destinationName+" : "+encrypt.decryptChat(mes[4])+"\n",null);
                        }else{
                            this.appendMessage(getTime()+": "+destinationName+" : "+mes[4]+"\n",null);
                        }
                        //play the sound to indicate a new message has arrived
                        sound.newMessageIncoming();
                    }
                }
                break;
        }


        

    }
    /**
     * Updates the saved data variables so that they can be saved to a file
     */
    private void updateSettings(){
        this.setFontType(values.font);
        this.setBackgroundColour(values.background);
        this.setForegroundColour(values.foreGround);
        this.setDisplayLF(values.landf);
    }

    /**
     * Sets the font that is being used by the gui
     * @param f the font selected
     */
    private void setFontType(Font f){
        displayArea.setFont(f);
        jTextField1.setFont(f);
    }

    /**
     * sets the background colour of the programs frames
     * @param c colour chosen by the user
     */
    private void setBackgroundColour(Color c){
        jPanel1.setBackground(c);
        displayArea.setBackground(c);
        jTextField1.setBackground(c);
    }

    /**
     * sets the foreground colour of the programs frames
     * @param c colour chosen by the user
     */
    private void setForegroundColour(Color c){
        displayArea.setForeground(c);
        jTextField1.setForeground(c);
    }

    /**
     * this will return a string which will display the current time in a 
     * [hour:minute:second] format for the main display
     * @return the time for the main display
     */
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

    /**
     * this method will return a timestamp for the log file saving
     * @return a timestamp with the current date and time
     */
    private String getTimeStamp(){
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        return day+""+month+""+year+""+hour+""+minute;
    }

    /**
     * This will shut down the private chat correctly and remove it from the list
     * of chats
     */
    private void exitConversation(){
        if(values.privateLog){
            values.saveLog("log\\"+this.getTimeStamp()+destinationName+".txt", new String(log));
        }
        this.setVisible(false);
        parent.removePrivateChat(destinationID);
    }

    /**
     * Changes the look and feel of the privateChat and all option panes
     * linked to it
     * @param name name of the selected look and feel
     */
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

    private void sendFile(){
        File f;
        JFileChooser chooser = new JFileChooser();
        int r = chooser.showOpenDialog(this);
        if(r == JFileChooser.APPROVE_OPTION){
            f = chooser.getSelectedFile();
        }else{
            return;
        }

        StringBuffer buf = new StringBuffer("2~");
        buf.append(destinationID+"~");
        buf.append("5~");
        buf.append(values.networkName+"~");
        buf.append(f.getName()+"~");
        buf.append(f.length()+"~");
        buf.append(getLocalIP()+"~");

        try{
            network.sendMulticast(new String(buf));
        }catch(IOException ex){}


        //start a file server and display
        Observable obs = new FileServer(f);
        FileTransferDialog display = new FileTransferDialog(this,false,obs);
        display.setDisplay(destinationName, values.networkName, f.getName(),(int)f.length());
        (new Thread((FileServer)obs)).start();
        //the thread will complete the transfer and then kill it's self
    }

    /**
     * Gets the local hosts ip address for the file transfer
     * @return local ip address
     */
    public String getLocalIP(){
        try{
            Enumeration e = NetworkInterface.getNetworkInterfaces();
            while(e.hasMoreElements()){
                NetworkInterface n = (NetworkInterface)e.nextElement();
                Enumeration inet = n.getInetAddresses();
                while(inet.hasMoreElements()){
                    InetAddress i = (InetAddress) inet.nextElement();
                    if((!i.isLoopbackAddress())&&(i.isSiteLocalAddress())){
                        return i.getHostAddress();
                    }
                }
            }
        }catch(SocketException ex){}
        return "";
    }
}
