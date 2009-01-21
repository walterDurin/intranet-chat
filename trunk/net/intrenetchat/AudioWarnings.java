package net.intrenetchat;

import java.applet.*;
import java.io.File;
import java.io.IOException;
import java.net.*;
import javax.swing.JOptionPane;


/**
 *
 * @author philip
 */
public class AudioWarnings {
    private Saving s;
    private volatile static AudioWarnings uniqueInstance;
    private AudioClip userJoinClip;
    private AudioClip messClip;
    private AudioClip userLeaveClip;
    
    private AudioWarnings() {
        s = Saving.getInstance();
        try {
            URL url = new URL(getClass().getResource("dooropen.wav"), "dooropen.wav");
            userJoinClip = Applet.newAudioClip(url);
            url = new URL(getClass().getResource("doorslam.wav"),"doorslam.wav");
            userLeaveClip = Applet.newAudioClip(url);
            url = new URL(getClass().getResource("imsend.wav"),"imsend.wav");
            messClip = Applet.newAudioClip(url);
        } catch (IOException e){
            JOptionPane.showMessageDialog(null, "Error Loading Audio");
        }
    }
    
    public static AudioWarnings getInstance(){
                if(uniqueInstance == null){
            synchronized(Saving.class){
                uniqueInstance = new AudioWarnings();
            }
      }
        return uniqueInstance;
    }
    
    public void newUserEntered(){
        if(s.getAudioUser() == 1){
            userJoinClip.play(); 
        }
    }
    
    public void UserLeft(){
        if(s.getAudioUser() == 1){
            userLeaveClip.play();
         }
    }
    
    public void newMessageIncoming(){
        if(s.getAudioMess() ==1){
            messClip.play();
        }
    }
}
