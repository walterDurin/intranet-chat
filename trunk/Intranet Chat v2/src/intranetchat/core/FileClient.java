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
import java.io.PrintWriter;
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
    private String fileName;
    private int position;
    private boolean abort;

    public FileClient(String ipadd,int fs,String fN){
        fileSize = fs;
        ip = ipadd;
        values = SavedValues.getInstance();
        position = 0;
        fileName = fN;
        abort = false;
    }

    public int getValues(){
        return position;
    }

    public void run() {
        try{
            int bytesRead;
            int current = 0;
            Socket sock = new Socket(ip,4000);
            byte [] mybytearray  = new byte [fileSize];
            InputStream is = sock.getInputStream();
            PrintWriter writer = new PrintWriter(sock.getOutputStream(),true);
            bytesRead = is.read(mybytearray,0,mybytearray.length);
            current = bytesRead;
            do {
                bytesRead = is.read(mybytearray, current, (mybytearray.length-current));
                if(bytesRead >= 0) current += bytesRead;
                position = current;
                this.setChanged();
                this.notifyObservers();
                writer.println(current);

            } while(bytesRead > 0);
            sock.close();

            if(abort){
                return;
            }
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
                    String out = f.getAbsolutePath()+"/";
                    saveFile(mybytearray,out);
                }else{
                    saveFile(mybytearray,values.savedLocation);
                }
            }

            //File has been transfered and check to see where they want the file placed
        }catch(IOException e){
            position = -1;
            this.setChanged();
            this.notifyObservers();
        }
    }

    public void saveFile(byte[] file, String path){
        File p = new File(path);
        if(!p.isDirectory()){
            p.mkdir();
        }
        File f = new File(path+fileName);
        try{
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(f.getPath()));
        bos.write(file, 0 , file.length);
        bos.flush();
        bos.close();
        }catch(IOException ex){
            System.out.println("Error Writing File details below");
            ex.printStackTrace();
        }
    }

    public void abortTransfer(){
        abort = true;
    }
}
