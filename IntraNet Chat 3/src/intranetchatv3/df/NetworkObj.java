/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package intranetchatv3.df;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Philip
 */
public class NetworkObj {
    private String from_name;
    private String from_id;
    private ArrayList<String> to;
    private int messageType;
    private String message;
    private String privateID;
    private boolean encrypted;
    public NetworkObj(){
        to = new ArrayList<String>();
    }

    public void setSource(String name){
        from_name = name;
    }

    public String getSource(){
        return from_name;
    }

    public void setSourceID(String id){
        from_id = id;
    }

    public String getSourceID(){
        return from_id;
    }

    public void addDestination(String id){
        to.add(id);
    }

    public void removeDestination(String id){
        Iterator<String> i = to.iterator();
        while(i.hasNext()){
            String s = i.next();
            if(s.compareTo(id)==0){
                i.remove();
            }
        }
    }

    public boolean containsID(String id){
        Iterator<String> i = to.iterator();
        while(i.hasNext()){
            if(i.next().compareTo(id)==0){
                return true;
            }
        }
        return false;
    }

    public void setList(ArrayList<String> a){
        to = a;
    }

    public ArrayList getList(){
        return to;
    }

    public void clearDestination(){
        to.clear();
    }

    public void setType(int type){
        messageType = type;
    }

    public int getType(){
        return messageType;
    }

    public void setMessage(String mess){
        message = mess;
    }

    public String getMessage(){
        return message;
    }

    public void setPrivateID(String id){
        privateID = id;
    }

    public String getPrivateID(){
        return privateID;
    }

    public void setEncrypted(boolean e){
        encrypted = e;
    }

    public boolean getEncrypted(){
        return encrypted;
    }
}
