/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package intranetchat.core;

import intranetchat.saving.SavedValues;
import java.applet.Applet;
import java.applet.AudioClip;
import java.io.IOException;
import java.net.URL;

/**
 * This class will play the sounds when a user calls one of the sound methods
 * @author Philip White
 * @version 1.0
 */
public class Sounds {
    private static volatile Sounds instance;
    private AudioClip userJoinClip;
    private AudioClip userLeaveClip;
    private AudioClip messClip;
    SavedValues values;

    /**
     * This is the private constructor for the singleton so that only one
     * instance of the sound class exists and others cannot be created accidently
     */
    private Sounds(){
        values = SavedValues.getInstance();
        try {
            //Sound for people entering chat
            URL url = new URL(getClass().getResource("online.wav"), "online.wav");
            userJoinClip = Applet.newAudioClip(url);
            //Sound for people leaving chat
            url = new URL(getClass().getResource("offline.wav"),"offline.wav");
            userLeaveClip = Applet.newAudioClip(url);
            //Sound for incoming message
            url = new URL(getClass().getResource("alert.wav"),"alert.wav");
            messClip = Applet.newAudioClip(url);
        } catch (IOException e){
            //thrown if the program cannot find the sound files in the jar
            System.out.println("Sound Error");
        }
    }

    /**
     * Returns an instance of the class for the singleton
     * @return an instance of this class
     */
    public static synchronized Sounds getInstance (){
        if(instance == null){
            //if the instance hasn't been created then it is created
            instance = new Sounds();
        }
        return instance;
    }

    /**
     * plays the user entered audio clip if the sounds have been enabled
     */
    public void newUserEntered(){
        if(values.soundEntrance){
            userJoinClip.play();
        }
    }

    /**
     * plays the user left audio clip if the sounds have been enabled
     */
    public void UserLeft(){
        if(values.soundEntrance){
            userLeaveClip.play();
         }
    }

    /**
     * plays the message incoming sound if the sounds have been enabled
     */
    public void newMessageIncoming(){
        if(values.soundMessage){
            messClip.play();
        }
    }
}
