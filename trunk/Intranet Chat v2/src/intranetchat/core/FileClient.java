/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package intranetchat.core;

import intranetchat.saving.SavedValues;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Observable;
import javax.swing.JFileChooser;

/**
 *
 * @author philip
 */
public class FileClient extends Observable implements Runnable{
    private SavedValues values;
    private String ip;
    private int fileSize;
    public FileClient(String ipadd,int fs){
        fileSize = fs;
        ip = ipadd;
        values = SavedValues.getInstance();
    }

    public void run() {
        try{
            int bytesRead;
            int current = 0;
            Socket sock = new Socket(ip,4000);
            byte [] mybytearray  = new byte [fileSize];
            InputStream is = sock.getInputStream();
            bytesRead = is.read(mybytearray,0,mybytearray.length);
            current = bytesRead;
            do {
                bytesRead = is.read(mybytearray, current, (mybytearray.length-current));
                if(bytesRead >= 0) current += bytesRead;
            } while(bytesRead > -1);
            sock.close();

            //checks to see if the user automatically saves the file
            if(values.autoAccept){
                //the auto accept location has been chosen
                saveFile(mybytearray,values.savedLocation);
            }else{
                //request a folder location to save the file
                JFileChooser chooser = new JFileChooser();
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int returnValue = chooser.showOpenDialog(null);
                if(returnValue == JFileChooser.APPROVE_OPTION){
                    File f = chooser.getSelectedFile();
                    saveFile(mybytearray,f.getAbsolutePath());
                }else{
                    saveFile(mybytearray,values.savedLocation);
                }
            }

            //File has been transfered and check to see where they want the file placed
        }catch(IOException e){
            System.out.println("error with client");
            e.printStackTrace();
        }
    }

    public void saveFile(byte[] file, String path){
        try{
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(path));
        bos.write(file, 0 , file.length);
        bos.flush();
        bos.close();
        }catch(IOException ex){
        }
    }
}
