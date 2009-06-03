/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package intranetchat.core;

/**
 * this class will encrypt and decrypt the chat messages
 * @author Philip White
 * @version 1.0
 */
public class ChatEncryption {
    private static volatile ChatEncryption instance;
    private int pos;
    private String passKey = "d4f82a";

     /**
     * private constructor for the encryptor class
     */
    private ChatEncryption(){
        pos = 0;
    }

    /**
     * this creates a single instance of this class
     * @return an instance of this class
     */
    public static synchronized ChatEncryption getInstance(){
        if (instance == null){
            instance = new ChatEncryption();
        }
        return instance;
    }

    /**
     * This will encrypt the message that is going to be sent to the recipiant
     * @param clear a clear text message to be encrypted
     * @return the ciphered string that will be sent
     */
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

     /**
     * This will encrypt the message that is going to be sent to the recipiant
     * @param clear a clear text message to be encrypted
     * @return the ciphered string that will be sent
     */
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
