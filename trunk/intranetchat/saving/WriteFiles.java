/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package intranetchat.saving;

import java.io.FileWriter;
import java.io.IOException;


/**
 *
 * @author Philip White
 */
public class WriteFiles {
    FileWriter writer;

    public WriteFiles(){

    }

    public void writeFile(String path, String data){
        try{
            writer = new FileWriter(path);
            writer.write(data);
            writer.flush();
            writer.close();
        }catch(IOException ex){

        }

    }


}
