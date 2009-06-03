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
 * This class will play the sounds when a user forces it
 * @author Philip White
 */
public class Sounds {
    private static volatile Sounds instance;
    private AudioClip userJoinClip;
    private AudioClip userLeaveClip;
    private AudioClip messClip;
    SavedValues values;

    /**
     * This is the private constructor for the singleton
     */
    private Sounds(){
        values = SavedValues.getInstance();
        try {
            URL url = new URL(getClass().getResource("dooropen.wav"), "dooropen.wav");
            userJoinClip = Applet.newAudioClip(url);
            url = new URL(getClass().getResource("doorslam.wav"),"doorslam.wav");
            userLeaveClip = Applet.newAudioClip(url);
            url = new URL(getClass().getResource("imsend.wav"),"imsend.wav");
            messClip = Applet.newAudioClip(url);
        } catch (IOException e){
            System.out.println("Sound Error");
        }
    }

    /**
     * Returns an instance of the class
     * @return an instance of this class
     */
    public static synchronized Sounds getInstance (){
        if(instance == null){
            instance = new Sounds();
        }
        return instance;
    }

    /**
     * plays the user entered audio clip
     */
    public void newUserEntered(){
        if(values.soundEntrance){
            userJoinClip.play();
        }
    }

    /**
     * plays the user left audio clip
     */
    public void UserLeft(){
        if(values.soundEntrance){
            userLeaveClip.play();
         }
    }

    /**
     * plays the message incoming sound
     */
    public void newMessageIncoming(){
        if(values.soundMessage){
            messClip.play();
        }
    }
}
