/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package intranetchatv3.core;

/**
 *
 * @author Philip
 */
public class Encryption {
    private final static String key = "290348572039847239062705968734950278340592384750293847529034857230945";
    private int pos = 0;

    public String encryptString(String clear){
        pos = 0;
        String cipher = "";
        for(int i = 0;i < clear.length();i++){
           int c = (clear.charAt(i));
           if(pos > key.length()){
               pos = 0;
           }
           c += key.charAt(pos);
           pos++;
           cipher += c+":";
        }
        return cipher;
    }

    public String decryptString(String cipher){
        pos = 0;
        String clear = "";
        String[] ci= cipher.split(":");
        for(int i = 1;i < ci.length;i++){
           int c = Integer.parseInt(ci[i]);
           if(pos == key.length()){
               pos = 0;
           }
           c -= key.charAt(pos);
           clear += (char) c;
           pos++;
        }
        return clear;
    }
}
