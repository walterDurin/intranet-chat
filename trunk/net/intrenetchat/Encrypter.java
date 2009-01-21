/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.intrenetchat;

/**
 *
 * @author philip
 */
public class Encrypter {
    private volatile static Encrypter instance;
    private int pos;
    private String passKey = "d4f82a";
    private Encrypter(){
        pos = 0;
    }
    
    public static Encrypter getInstance(){
        if (instance == null) {
            synchronized (Encrypter.class) {
                instance = new Encrypter();
            }
        }
        return instance;
    }
    
    public String encrypt(String clear){
        pos = 0;
        String cipher = "^:";
        for(int i = 0;i < clear.length();i++){
           int c = (clear.charAt(i));
           if(pos == passKey.length()){
               pos = 0;
           }
           c += passKey.charAt(pos);
           pos++;
           System.out.println(c);
           cipher += c+":";
        }
        return cipher;
    }
    
    public String decrypt(String cipher){
        pos = 0;
        String clear = "";
        String[] ci= cipher.split(":");
        for(int i = 1;i < ci.length;i++){
           int c = Integer.parseInt(ci[i]);
           if(pos == passKey.length()){
               pos = 0;
           }
           c -= passKey.charAt(pos);
           clear += (char) c;
           pos++;
        }
        
        return clear;
    }
    
    
}
