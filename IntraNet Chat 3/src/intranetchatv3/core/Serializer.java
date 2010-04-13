/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package intranetchatv3.core;

import intranetchatv3.df.NetworkObj;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Philip
 */
public class Serializer {
    private String sep = "!#!";
    private String listSep = ":";

    public String serializeMessage(NetworkObj o){
        StringBuffer buf = new StringBuffer();
        buf.append(o.getSource());
        buf.append(sep);
        buf.append(o.getSourceID());
        buf.append(sep);
        buf.append(listToString(o.getList()));
        buf.append(sep);
        buf.append(o.getType()+"");
        buf.append(sep);
        buf.append(o.getPrivateID());
        buf.append(sep);
        if(o.getEncrypted()){
            buf.append(1);
        }else{
            buf.append(0);
        }
        buf.append(sep);
        buf.append(o.getMessage());
        buf.append(sep);

        return new String(buf);
    }

    public NetworkObj deserializeMessage(String s){
        NetworkObj o = new NetworkObj();
        String[] in = s.split(sep);
        o.setSource(in[0]);
        o.setSourceID(in[1]);
        o.setList(stringToList(in[2]));
        o.setType(integerConverter(in[3]));
        o.setPrivateID(in[4]);
        if(integerConverter(in[5])== 1){
            o.setEncrypted(true);
        }else{
            o.setEncrypted(false);
        }
        o.setMessage(in[6]);
        return o;
    }

    private int integerConverter(String s){
        return Integer.parseInt(s);
    }

    private String listToString(ArrayList<String> l){
        Iterator<String> i = l.iterator();
        StringBuffer buf = new StringBuffer();
        while(i.hasNext()){
            buf.append(i.next()+listSep);
        }
        return new String(buf);
    }

    private ArrayList<String> stringToList(String s){
        String[] dests = s.split(listSep);
        ArrayList<String> a = new ArrayList<String>();
        for(int i = 0 ; i < dests.length ; i++){
            a.add(dests[i]);
        }
        return a;
    }
}