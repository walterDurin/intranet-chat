/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package intranetchatv3.saving;

import java.awt.*;
import java.io.Serializable;

/**
 *
 * @author Philip
 */
public class SavedObject implements Serializable {
    public String networkID;
    public String netPort;
    public boolean encrypted;
    public Point location;
    public String username;
    public String lf;
    public Font font;
    public Color back;
    public Color system;
    public Color out;
    public Color in;
    public boolean emoticons;
}
