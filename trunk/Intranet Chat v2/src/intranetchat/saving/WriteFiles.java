/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package intranetchat.saving;

import java.io.FileWriter;
import java.io.IOException;


/**
 * Class to interface with files to write files
 * @author Philip White
 * @version 1.0
 */
public class WriteFiles {
    FileWriter writer;

    public WriteFiles(){

    }

    /**
     * This writes the data to the file where the path point to
     * @param path location of the file
     * @param data data to be entered into the file
     */
    public void writeFile(String path, String data){
        try{
            writer = new FileWriter(path);
            writer.write(data);
            writer.flush();
            writer.close();
        }catch(IOException ex){
            System.out.println("writing error");
        }

    }


}
