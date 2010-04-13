/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package intranetchatv3.display;

import java.io.*;
import java.net.*;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.text.*;

/**
 *
 * @author Philip
 */
public class EmoticonStyle {
    String[] emoticonList;
    String[] emoticonName;

    public EmoticonStyle(StyledDocument cDoc){
        if(getEmoticonLists()){
            Style def = StyleContext.getDefaultStyleContext().
            getStyle(StyleContext.DEFAULT_STYLE);
            Style regular = cDoc.addStyle("regular", def);
            StyleConstants.setFontFamily(def, "SansSerif");

            for(int i = 0 ; i < emoticonList.length ; i++){
                Style s = cDoc.addStyle(emoticonList[i], regular);
                StyleConstants.setAlignment(s, StyleConstants.ALIGN_CENTER);
                File f = new File("images/default/"+emoticonName[i]+".png");
                ImageIcon icon = createImageIcon(f,emoticonList[i]);
                if (icon != null) {
                    StyleConstants.setIcon(s, icon);
                }
            }
        }
    }
   
    public boolean isInList(String emotes){
        for(int i = 0 ; i < emoticonList.length; i++){
            if(emoticonList[i].compareTo(emotes)==0){
                return true;
            }
        }
        return false;
    }

    protected static ImageIcon createImageIcon(File f,String description) {
        try{
            URL imgURL = f.toURI().toURL();
            if (imgURL != null) {
                return new ImageIcon(imgURL, description);
            } else {
                System.err.println("Couldn't find file: " + f.getAbsolutePath());
                return null;
            }
        }catch(MalformedURLException e){
            e.printStackTrace();
            return null;
        }
    }

    private boolean getEmoticonLists(){
        try{
            FileReader in = new FileReader(new File("images/default/iconList.txt"));
            BufferedReader buf = new BufferedReader(in);
            String s;
            StringBuffer names = new StringBuffer();
            StringBuffer icons = new StringBuffer();
            while((s = buf.readLine()) != null){
                if(s.charAt(0) != '#'){
                    String[] a = s.split(" ");
                    names.append(a[0]+"\n");
                    icons.append(a[1]+"\n");
                }
            }
            emoticonName = new String(names).split("\n");
            emoticonList = new String(icons).split("\n");
            return true;
        }catch(FileNotFoundException e){
            JOptionPane.showMessageDialog(null,"Emoticons Missing please replace and restart","Emoticon Error",JOptionPane.ERROR_MESSAGE);
            return false;
        }catch(IOException e){
            return false;
        }
    }
}