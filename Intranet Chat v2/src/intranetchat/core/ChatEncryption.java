/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package intranetchat.core;

/**
 *
 * @author Philip
 */
public class ChatEncryption {
    private static volatile ChatEncryption instance;
    private int pos;
    private String passKey = "d4f82a";

    private ChatEncryption(){
        pos = 0;
    }

    public static synchronized ChatEncryption getInstance(){
        if (instance == null){
            instance = new ChatEncryption();
        }
        return instance;
    }

    public String encryptChat(String clear){
        pos = 0;
        String cipher = "^:";
        for(int i = 0;i < clear.length();i++){
           int c = (clear.charAt(i));
           if(pos == passKey.length()){
               pos = 0;
           }
           c += passKey.charAt(pos);
           pos++;
           cipher += c+":";
        }
        return cipher;
    }

    public String decryptChat(String cipher){
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
