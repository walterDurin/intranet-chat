/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package intranetchatv3.saving;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;


/**
 *
 * @author Philip
 */
public class FileSaving {

    public void saveLog(String contents,String fn){
        String fileName = getTime()+fn;
        try {
            FileWriter fw = new FileWriter(fileName);
            fw.write(contents);
            fw.flush();
            fw.close();
        } catch (IOException ex) {
        }
    }

    private String getTime(){
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR);
        String minute = calendar.get(Calendar.MINUTE)+"";
        String second = calendar.get(Calendar.SECOND)+"";
        if(calendar.get(Calendar.AM_PM)== Calendar.PM){
            hour = hour + 12;
        }
        String h = hour+"";
        if(h.length() < 2){
            h = "0"+h;
        }
        if(minute.length() < 2){
            minute = "0"+minute;
        }
        if(second.length() < 2){
            second = "0"+second;
        }
        return h+minute+second;
    }
}
