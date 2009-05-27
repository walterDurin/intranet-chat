/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package intranetchat.saving;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Philip White
 */
public class ReadFiles {
    BufferedReader buf;

    public String readFile(String path){
        String ret = "";
        try {
            buf = new BufferedReader(new FileReader(path));
            String s;
            StringBuffer sb = new StringBuffer("");
            while((s = buf.readLine()) != null ){
                sb.append(s+"\n");
            }
            buf.close();
            ret = new String(sb);

        } catch (FileNotFoundException ex) {

        } catch (IOException ex) {

        }
        return ret;
    }

    public boolean checkFilePresence(String path){
        File f = new File(path);
        if(f.exists()){
            return true;
        }else{
            return false;
        }
    }
}
