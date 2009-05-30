/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package intranetchat.core;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Observable;

/**
 *
 * @author Philip
 */
public class FileTransferListener extends Observable implements Runnable{
    private static volatile FileTransferListener instance;
    ServerSocket fileSocket;
    Socket client;
    DataInputStream in;
    private byte[] inBytes;

    private FileTransferListener (){
        try{
        fileSocket = new ServerSocket(4000,3);
        }catch(IOException ex){}
    }

    public static synchronized FileTransferListener getInstance (){
        if(instance == null){
            instance = new FileTransferListener();
        }
        return instance;
    }

    public void run(){
        while(true){
            try{
                client = fileSocket.accept();
                in = new DataInputStream(client.getInputStream());
                int length = in.readInt();
                inBytes = new byte[length];

                int offset = 0;
                int numRead = 0;
                while (offset < inBytes.length && (numRead=in.read(inBytes, offset, inBytes.length-offset)) >= 0) {
                    offset += numRead;
                }
                in.close();
                this.setChanged();
                this.notifyObservers();


            }catch(IOException ex){}
        }
    }

    public byte[] getFileBytes(){
        return inBytes;
    }
}
