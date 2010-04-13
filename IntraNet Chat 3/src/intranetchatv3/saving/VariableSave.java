/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package intranetchatv3.saving;

import intranetchatv3.df.VariableStore;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

/**
 *
 * @author Philip
 */
public class VariableSave {
    private static String default_file = "inc.sts";
    private VariableStore store = VariableStore.getInstance();

    public boolean isSavePresent(){
        File f = new File(default_file);
        return f.exists();
    }

    public void saveValues(){
        try {
            ObjectOutput out = new ObjectOutputStream(new FileOutputStream(default_file));
            SavedObject o = new SavedObject();
            retrieveData(o);
            out.writeObject(o);
            out.flush();
            out.close();
        } catch (IOException ex) {
            System.out.println("file error");
            ex.printStackTrace();
        }
    }

    public void loadValues(){
        try {
            ObjectInput in = new ObjectInputStream(new FileInputStream(default_file));
            SavedObject o = (SavedObject) in.readObject();
            dumpData(o);
        } catch (IOException ex) {
            System.out.println("file error");
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            System.out.println("class error");
            ex.printStackTrace();
        }
    }

    private void dumpData(SavedObject o){
        store = VariableStore.getInstance();
        store.networkID = o.networkID;
        store.netPort = o.netPort;
        store.encrypted = o.encrypted;
        store.location = o.location;
        store.username = o.username;
        store.lf = o.lf;
        store.font = o.font;
        store.back = o.back;
        store.system = o.system;
        store.out = o.out;
        store.in = o.in;
        store.emoticons = o.emoticons;
    }

    private void retrieveData(SavedObject o){
        store = VariableStore.getInstance();
        o.networkID = store.networkID;
        o.netPort = store.netPort;
        o.encrypted = store.encrypted;
        o.location = store.location;
        o.username = store.username;
        o.lf = store.lf;
        o.font = store.font;
        o.back = store.back;
        o.system = store.system;
        o.out = store.out;
        o.in = store.in;
        o.emoticons = store.emoticons;
    }
}
