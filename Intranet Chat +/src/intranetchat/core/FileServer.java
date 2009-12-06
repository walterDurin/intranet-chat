/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package intranetchat.core;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Observable;


/**
 *
 * @author philip
 */
public class FileServer extends Observable implements Runnable{
    private File outGoing;
    private boolean authenticated;

    private int value;
    private ServerSocket servsock;

    public FileServer(File f){
        outGoing = f;
        authenticated = false;
        value = 0;
    }

    /**
     * Starts the file transfer server and waits for a client
     */
    public void run() {
        try{
            servsock = new ServerSocket(4000);
            Socket sock = servsock.accept();
            authenticated = true;
            this.setChanged();
            this.notifyObservers();
            byte [] mybytearray  = new byte [(int)outGoing.length()];
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(outGoing));
            bis.read(mybytearray,0,mybytearray.length);
            OutputStream os = sock.getOutputStream();
            os.write(mybytearray,0,mybytearray.length);
            os.flush();
            BufferedReader read = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            do{
                value = Integer.parseInt(read.readLine());
                this.setChanged();
                this.notifyObservers();
            }while((value < (int)outGoing.length())||value != -1);


            sock.close();
            servsock.close();

        }catch(IOException ex){
            System.out.println("Error with server details below");
            ex.printStackTrace();
        }
    }

    public boolean getAuthenticated(){
        return authenticated;
    }

    public int getPosition(){
        return value;
    }

    public void abortTransfer(){
        try{
            if(!servsock.isClosed()){
                servsock.close();
            }
        }catch(IOException ex){}
    }


}