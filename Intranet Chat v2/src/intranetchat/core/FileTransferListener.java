/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package intranetchat.core;

import intranetchat.display.FileTransferDisplay;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Observable;
import javax.swing.JOptionPane;

/**
 *
 * @author Philip
 */
public class FileTransferListener extends Observable implements Runnable{
    private static volatile FileTransferListener instance;
    ServerSocket fileSocket;
    Socket client;
    DataInputStream in;
    DataOutputStream out;
    private byte[] inBytes;
    private String name;
    private int pos;
    private int total;

    private FileTransferListener (){
        try{
        fileSocket = new ServerSocket(4000,1);
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
                out = new DataOutputStream(client.getOutputStream());
                byte[] data = new byte[1024];
                in.read(data);
                String s = new String(data);
                String[] conf = s.split("~#");

                int response = JOptionPane.showConfirmDialog(null,conf[0]+" wants to send a file", "File incoming",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if(response == JOptionPane.NO_OPTION){
                    out.writeBytes("NO");
                    out.flush();
                    out.close();
                    in.close();
                }else if(response == JOptionPane.YES_OPTION){
                    out.writeBytes("YES");
                    //Start the Graphical User Interface and pass the data
                    FileTransferDisplay display = new FileTransferDisplay(null, false, this);

                    //once accepted then the file is sent
                    int offset = 0;
                    int numRead = 0;
                    while (offset < inBytes.length && (numRead=in.read(inBytes, offset, inBytes.length-offset)) >= 0) {
                        offset += numRead;
                        pos = offset;
                        this.setChanged();
                        this.notifyObservers();
                    }
                    in.close();
                    out.close();
                }
            }catch(IOException ex){}
        }
    }

    public int getPosition(){
        return pos;
    }
}
