/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package intranetchatv3.df;

/**
 *
 * @author Philip
 */
public class MessageTypes {
    public static final int PUBLIC_MESSAGE = 1;
    public static final int PRIVATE_MESSAGE = 2;
    public static final int USER_JOINED = 3;
    public static final int USER_BROADCAST = 4;
    public static final int USER_LEFT = 5;
    public static final int RETRANSMIT = 6;
    public static final int FILE_TRANSFER = 7; /*Not implemented at present*/
    public static final int SHUTDOWN = 8;
    public static final int USERNAME_CHANGE = 9;
    public static final int PRIVATE_USERADD = 10;
    public static final int PRIVATE_USERLEFT = 11;
}
