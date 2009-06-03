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

    private ChatEncryption(){

    }

    public static synchronized ChatEncryption getInstance(){
        if (instance == null){
            instance = new ChatEncryption();
        }
        return instance;
    }

    public String encryptChat(String message){
        return message;
    }

    public String decryptChat(String message){
        return message;
    }
}
