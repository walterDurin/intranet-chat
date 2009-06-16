/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package intranetchat.core;

import intranetchat.saving.SavedValues;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.Observer;

/**
 *
 * @author Philip
 */
public class FileTransfer extends Observable implements Observer{
    SavedValues values;
    boolean authenticated = false;
    int position = 0;

    public FileTransfer(){
        values = SavedValues.getInstance();
    }

    public void sendFile(File f){
        //wrap in a new thread
//        try{
//            ServerSocket servsock = new ServerSocket(4000);
//            Socket sock = servsock.accept();
//            byte [] mybytearray  = new byte [(int)f.length()];
//            FileInputStream fis = new FileInputStream(f);
//            BufferedInputStream bis = new BufferedInputStream(fis);
//            bis.read(mybytearray,0,mybytearray.length);
//            OutputStream os = sock.getOutputStream();
//            os.write(mybytearray,0,mybytearray.length);
//            os.flush();
//            sock.close();
//        }catch(IOException ex){}
    }

    public byte[] getFile(String ip,int fileSize){
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
            return mybytearray;
        }catch(IOException e){}
        return null;
    }

    public void update(Observable o, Object arg) {
        if(o instanceof NetworkListener){
            NetworkListener nl = (NetworkListener)o;
            String in = nl.getMessage();
            String[] breakup = in.split("~");
            //Checks to see if the message received is a file transfer reply method
            if(Integer.parseInt(breakup[1]) == 2){
                if(Integer.parseInt(breakup[3]) == 6){
                    if(Integer.parseInt(breakup[2]) == values.networkID){
                        if(breakup[5].compareTo("YES")==0){
                            authenticated = true;
                            
                        }else{
                            authenticated = false;
                        }
                        this.setChanged();
                        this.notifyObservers();
                    }
                }
            }
        }
    }

    public boolean isAuthenticated(){
        return authenticated;
    }

    public int getPosition(){
        return position;
    }

    public String getLocalIP(){
        String address = null;
        try{
        InetAddress ip = InetAddress.getLocalHost();
        address = ip.getHostAddress();
        }catch(UnknownHostException ex){}

        return address;
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
