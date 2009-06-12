/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package intranetchat.display;

import java.awt.Component;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 *
 * @author philip
 */
public class FontComboRenderer extends JLabel implements ListCellRenderer{

    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        String s = (String) value;

        this.setText(s);
        Font f = new Font(s,Font.PLAIN,14);
        this.setFont(f);
        this.setOpaque(true);

        if(isSelected){
            this.setBackground(list.getSelectionBackground());
            this.setForeground(list.getSelectionForeground());
        }else{
            this.setBackground(list.getBackground());
            this.setForeground(list.getForeground());
        }
        return this;
    }

}
