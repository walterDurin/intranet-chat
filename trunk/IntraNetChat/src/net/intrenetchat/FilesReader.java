package net.intrenetchat;
import java.io.*;


/**
 * Class that is used to read files in.
 * @author Philip White
 */
public class FilesReader {
    String[] sentence;
    String[] words;
    String complete = "";
    BufferedReader buf;

    /**
     * Main constructor for the FilesReader
     */
    public FilesReader(){
        sentence = new String[1000];
        words = new String[10000];
    }

    /**
     * Method to read lines in from a file into a string
     * @param loc file Location
     * @return The complete text in a string 
     */
    public String loadLines(String loc){
        complete = "";
        try{
            buf = new BufferedReader(new FileReader(loc));
            String line = "";
            while((line = buf.readLine())!=null){
                complete += line + "\n";
            }
            buf.close();
        }catch(IOException e){
            
        }
        return complete;
    }

    /**
     * Reads a file a sentence at a time and stores it into a string
     * @param loc File location
     * @return the complete text in a string
     */
    public String loadSentences(String loc){
        String[] array = new String[30];
        complete = "";
        String line = "";
        try{
            buf = new BufferedReader(new FileReader(loc));
                while(buf.readLine()!= null){
                line = buf.readLine();
                complete = complete + line;
            }
            array = complete.split(".");
            int i = 0;
            complete = "";
                while(array[i]!= null){
                complete = complete + array[i] + "/n"; 
                }
            }catch(IOException e){}
        return complete;
    }

    /**
     * Reads a file a word at a time and stores it in a string array
     * @param loc File location
     * @return A string array of words
     */
    public String[] loadWords(String loc){
        String line = "";
        String complete = "";
        String[] array = new String[10000];
        try{
            buf = new BufferedReader(new FileReader(loc));
            while(buf.readLine()!= null){
                line = buf.readLine();
                complete = complete + line;
            }
            array = complete.split(" ", 10000);
        }catch(IOException e){}
        return array;
    }

    /**
     * Just loads the first line of a file into a string
     * @param loc file location
     * @return The first line of the file
     */
    public String loadFirstLine(String loc){
        String line = "";
        try{
            buf = new BufferedReader(new FileReader(loc));
            line = buf.readLine();
        }catch(IOException e){}
        return line;
    }
}
