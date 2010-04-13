/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package intranetchatv3.display;

import intranetchatv3.df.VariableStore;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 *
 * @author Philip
 */
public class Preferences extends JDialog implements ActionListener{
    private boolean changed = false;
    private VariableStore store;
    private Dimension textField = new Dimension(300,27);
    private Dimension comboSize = new Dimension(120,27);
    private JTextField username;
    private JTextField commPort;
    private JCheckBox encrypted, emoticon;
    private JComboBox fontName, fontType, fontSize, lfList;
    private JPanel outputColour,inputColour,systemColour,backgroundColour;
    private JButton OCB,ICB,SCB,BCB,commit,cancel;

    public Preferences (MainDisplay main){
        super((JFrame)main, "Edit Preferences", true);
        store = VariableStore.getInstance();
        this.setLocation(250,200);
        buildFrame();
        assignData();
        pack();
    }

    private void buildFrame(){
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH; // components grow in both dimensions
        c.insets = new Insets(2, 2, 2, 2);

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 3;
        c.gridheight = 1;
        c.weighty = 1.0;
        c.weightx = 0.0;
        this.add(usernamePanel(), c);

        c.gridy = 1;
        this.add(networkingPanel(), c);

        c.gridy = 2;
        this.add(fontPanel(), c);

        c.gridy = 3;
        this.add(stylePanel(), c);

        commit = new JButton("Save");
        commit.setName("commit");
        commit.addActionListener(this);
        c.gridy = 4;
        c.gridwidth = 1;
        c.gridheight = 1;
        this.add(commit, c);

        cancel = new JButton("Cancel");
        cancel.setName("cancel");
        cancel.addActionListener(this);
        c.gridx = 1;
        c.gridwidth = 1;
        c.gridheight = 1;
        this.add(cancel, c);
    }

    private JPanel usernamePanel(){
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder("Username"));
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH; // components grow in both dimensions
        c.insets = new Insets(2, 2, 2, 2);

        JLabel label = new JLabel("Username :");
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.weighty = 0.0;
        c.weightx = 0.0;
        panel.add(label, c);

        username = new JTextField();
        username.setPreferredSize(textField);
        c.gridx = 1;
        c.gridwidth = 2;
        c.weighty = 1.0;
        panel.add(username, c);
        return panel;
    }

    private JPanel networkingPanel(){
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder("Networking"));
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH; // components grow in both dimensions
        c.insets = new Insets(2, 2, 2, 2);

        JLabel label = new JLabel("Comm Port :");
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.weighty = 0.0;
        c.weightx = 0.0;
        panel.add(label, c);
        
        commPort = new JTextField();
        commPort.setPreferredSize(textField);
        c.gridx = 1;
        panel.add(commPort, c);

        encrypted = new JCheckBox();
        encrypted.setText("Encrypt Private Chat");
        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 1;
        c.gridheight = 1;
        panel.add(encrypted, c);
        
        return panel;
    }

    private JPanel fontPanel(){
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder("Font"));
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH; // components grow in both dimensions
        c.insets = new Insets(2, 2, 2, 2);

        JLabel label = new JLabel("Font Name :");
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.weighty = 0.0;
        c.weightx = 0.0;
        panel.add(label, c);

        fontName = new JComboBox();
        fontName.setRenderer(new FontComboRenderer());
        fontName.setPreferredSize(textField);
        c.gridx = 1;
        c.gridwidth = 2;
        panel.add(fontName, c);

        label = new JLabel("Font Type :");
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        panel.add(label, c);

        fontType = new JComboBox();
        fontType.setRenderer(new FontComboRenderer());
        fontType.setPreferredSize(comboSize);
        fontType.setModel(new DefaultComboBoxModel(new String[] { "Plain", "Bold", "Italic" }));
        c.gridx = 1;
        panel.add(fontType, c);

        label = new JLabel("Font Size :");
        c.gridx = 0;
        c.gridy = 2;
        panel.add(label, c);

        fontSize = new JComboBox();
        fontSize.setRenderer(new FontComboRenderer());
        fontSize.setPreferredSize(comboSize);
        fontSize.setModel(new DefaultComboBoxModel(new String[] { "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19" }));
        c.gridx = 1;
        panel.add(fontSize, c);

        label = new JLabel("Outgoing :");
        c.gridx = 0;
        c.gridy = 3;
        panel.add(label, c);

        OCB = new JButton("Select Colour");
        OCB.setName("OCB");
        OCB.setPreferredSize(comboSize);
        OCB.addActionListener(this);
        c.gridx = 1;
        panel.add(OCB, c);

        outputColour = new JPanel();
        c.gridx = 2;
        panel.add(outputColour, c);

        label = new JLabel("Incoming :");
        c.gridx = 0;
        c.gridy = 4;
        panel.add(label, c);

        ICB = new JButton("Select Colour");
        ICB.setName("ICB");
        ICB.setPreferredSize(comboSize);
        ICB.addActionListener(this);
        c.gridx = 1;
        panel.add(ICB, c);

        inputColour = new JPanel();
        c.gridx = 2;
        panel.add(inputColour, c);
        label = new JLabel("System :");
        c.gridx = 0;
        c.gridy = 5;
        panel.add(label, c);

        SCB = new JButton("Select Colour");
        SCB.setName("SCB");
        SCB.setPreferredSize(comboSize);
        SCB.addActionListener(this);
        c.gridx = 1;
        panel.add(SCB, c);

        systemColour = new JPanel();
        c.gridx = 2;
        panel.add(systemColour, c);

        label = new JLabel("Background :");
        c.gridx = 0;
        c.gridy = 6;
        panel.add(label, c);

        BCB = new JButton("Select Colour");
        BCB.setName("BCB");
        BCB.setPreferredSize(comboSize);
        BCB.addActionListener(this);
        c.gridx = 1;
        panel.add(BCB, c);

        backgroundColour = new JPanel();
        c.gridx = 2;
        panel.add(backgroundColour, c);
        return panel;
    }

    private JPanel stylePanel(){
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder("Style"));
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH; // components grow in both dimensions
        c.insets = new Insets(2, 2, 2, 2);

        JLabel label = new JLabel("Look and Feel :");
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.weighty = 0.0;
        c.weightx = 0.0;
        panel.add(label, c);

        lfList = new JComboBox();
        lfList.setPreferredSize(textField);
        c.gridx = 1;
        c.gridwidth = 2;
        panel.add(lfList, c);

        emoticon = new JCheckBox();
        emoticon.setText("Show Emoticons");
        c.gridy = 1;
        c.gridwidth = 1;
        panel.add(emoticon,c);
        return panel;
    }

    private void assignData(){
        username.setText(store.username);
        commPort.setText(store.netPort);
        encrypted.setSelected(store.encrypted);
        String[] array = store.fonts;
        for(int i=0;i < array.length;i++){
            fontName.addItem(array[i]);
            if(array[i].compareTo(store.font.getFamily())==0){
                fontName.setSelectedIndex(i);
            }
        }
        fontType.setSelectedIndex(store.font.getStyle());
        fontSize.setSelectedItem(store.font.getSize()+"");
        outputColour.setBackground(store.out);
        inputColour.setBackground(store.in);
        systemColour.setBackground(store.system);
        backgroundColour.setBackground(store.back);
        UIManager.LookAndFeelInfo plaf[] = UIManager.getInstalledLookAndFeels();
        for (int i = 0, n = plaf.length; i < n; i++) {
            lfList.addItem(plaf[i].getName());
            if(plaf[i].getName().compareTo(store.lf)==0){
                lfList.setSelectedIndex(i);
            }
        }
        emoticon.setSelected(store.emoticons);
    }

    public void saveData(){
        store.username = username.getText();
        store.netPort = commPort.getText();
        store.encrypted = encrypted.isSelected();
        store.font = new Font((String)fontName.getSelectedItem(),fontType.getSelectedIndex(),Integer.parseInt((String)fontSize.getSelectedItem()));
        store.out = outputColour.getBackground();
        store.in = inputColour.getBackground();
        store.system = systemColour.getBackground();
        store.back = backgroundColour.getBackground();
        store.lf = (String)lfList.getSelectedItem();
        store.emoticons = emoticon.isSelected();
    }

    public boolean isChanged(){
        return changed;
    }

    public void actionPerformed(ActionEvent e) {
        JButton b = (JButton)e.getSource();
        String name = b.getName();
        if(name.compareTo("OCB")==0){
            Color newColor = JColorChooser.showDialog(null, "Choose Colour", outputColour.getBackground());
            if(newColor != null){
                outputColour.setBackground(newColor);
            }
        }else if(name.compareTo("ICB")==0){
            Color newColor = JColorChooser.showDialog(null, "Choose Colour", inputColour.getBackground());
            if(newColor != null){
                inputColour.setBackground(newColor);
            }
        }else if(name.compareTo("SCB")==0){
            Color newColor = JColorChooser.showDialog(null, "Choose Colour", systemColour.getBackground());
            if(newColor != null){
                systemColour.setBackground(newColor);
            }
        }else if(name.compareTo("BCB")==0){
            Color newColor = JColorChooser.showDialog(null, "Choose Colour", backgroundColour.getBackground());
            if(newColor != null){
                backgroundColour.setBackground(newColor);
            }
        }else if(name.compareTo("commit")==0){
            changed = true;
            this.dispose();
        }else if(name.compareTo("cancel")==0){
            this.dispose();
        }
    }
}
