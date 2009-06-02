/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package intranetchat.display;

import intranetchat.saving.SavedValues;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 *
 * @author Philip
 */
public class userListRenderer extends JLabel implements ListCellRenderer{
    private SavedValues values;

    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        values = SavedValues.getInstance();
        String[] in = (String[])value;

        setText(in[0]);
        if (isSelected) {
            setForeground(Color.BLUE);
        } else {
            setForeground(list.getForeground());
        }
        
        if(in[1].compareTo(values.networkID+"")==0){
            setFont( list.getFont().deriveFont( Font.BOLD ) );
        }



        return this;
    }

}
