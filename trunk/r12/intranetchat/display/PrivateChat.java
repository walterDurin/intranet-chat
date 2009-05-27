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

import intranetchat.core.NetworkInterface;
import intranetchat.core.NetworkListener;
import intranetchat.saving.SavedValues;
import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JOptionPane;

/**
 *
 * @author Philip White
 * @version 1.0
 */
public class PrivateChat extends javax.swing.JFrame implements Observer{
    private Observable observable;
    private String destinationID;
    private String destinationName;
    private SavedValues values;
    private NetworkInterface network;

    /** Creates new form PrivateChat */
    public PrivateChat(Observable obs, String id, String name) {
        values = SavedValues.getInstance();
        network = NetworkInterface.getInstance();
        observable = obs;
        observable.addObserver(this);
        destinationID = id;
        destinationName = name;
        this.setTitle(destinationName);
        initComponents();
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
        jTextArea1 = new javax.swing.JTextArea();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
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

        jScrollPane1.setPreferredSize(new java.awt.Dimension(280, 350));

        jTextArea1.setColumns(20);
        jTextArea1.setEditable(false);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 3, 0);
        jPanel1.add(jScrollPane1, gridBagConstraints);

        jTextField1.setPreferredSize(new java.awt.Dimension(220, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
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
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
        getContentPane().add(jPanel1, gridBagConstraints);

        jMenu1.setText("File");

        jMenuItem3.setText("Clear Screen");
        jMenu1.add(jMenuItem3);

        jMenuItem4.setText("Close ");
        jMenu1.add(jMenuItem4);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Settings");

        jMenuItem1.setText("Preferences");
        jMenu2.add(jMenuItem1);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Help");

        jMenuItem2.setText("About");
        jMenu3.add(jMenuItem2);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowClosing

    private void jButton1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseReleased
        sendMessage();
        jTextField1.setText("");
    }//GEN-LAST:event_jButton1MouseReleased

    // Variables declaration - do not modify//GEN-BEGIN:variables
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
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
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
            String s = list.getMessage();
        }else if(o instanceof SavedValues){
            this.updateSettings();
        }
    }

    private void sendMessage(){
        String mes = jTextField1.getText();
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
    private void sortMessage(String input){
        
    }
    /**
     * Updates the saved data variables so that they can be saved to a file
     */
    private void updateSettings(){
        this.setFontType(values.font);
        this.setBackgroundColour(values.background);
        this.setForegroundColour(values.foreGround);
    }

    /**
     * Sets the font that is being used by the gui
     * @param f the font selected
     */
    private void setFontType(Font f){
        jTextArea1.setFont(f);
        jTextField1.setFont(f);
    }

    /**
     * sets the background colour of the programs frames
     * @param c colour chosen by the user
     */
    private void setBackgroundColour(Color c){
        jPanel1.setBackground(c);
        jTextArea1.setBackground(c);
        jTextField1.setBackground(c);
    }

    /**
     * sets the foreground colour of the programs frames
     * @param c colour chosen by the user
     */
    private void setForegroundColour(Color c){
        jTextArea1.setForeground(c);
        jTextField1.setForeground(c);
    }

    /**
     * This will shut down the private chat correctly and remove it from the list
     * of chats
     */
    private void exitConversation(){
        
    }
}
