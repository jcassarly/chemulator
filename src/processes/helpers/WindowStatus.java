package processes.helpers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;

/**
 *
 * @author Jared Cassarly
 */
public class WindowStatus {
    
    final public static String FILENAME = "WindowStatus.txt";
    
    /**
     * Writes a given text to a file with name fileName
     * @param text the text to write to the file
     * @param windowName the name of the file
     */
    public static void writeFile(String text, String windowName) {
        
        // Writes to the file
        try {
            // Gets the lines of the file and makes the PrintWriter
            ArrayList<String> lines;
            PrintWriter out;
            File f = new File(FILENAME);
            // if the file exists, reads in the lines and readies them for reprinting
            if(f.exists() && !f.isDirectory()) { 
                lines = getLines();
                out = new PrintWriter(FILENAME);
                boolean unchanged = true;
                for (int i = 0, n = lines.size(); i < n; i++) {
                    // changes the status of the line starting with String windowName
                    if (lines.get(i).contains(windowName)) {
                        lines.set(i, windowName + ": " + text);
                        unchanged = false;
                    }
                }
                // if none of the lines contained the String windowName, the line is added to the end.
                if (unchanged) {
                    lines.add(windowName + ": " + text);
                }
            }
            // if the file does not exist, a new one is created and readied for printing
            else {
                out = new PrintWriter(FILENAME);
                lines = new ArrayList<>();
                lines.add(windowName + ": " + text);
            }
            
            // prints the lines in file that have been previously readied
            for (int i = 0, n = lines.size(); i < n; i++) {
                out.println(lines.get(i));
            }
            out.close();
        }
        catch (FileNotFoundException ex) {
            //File makefile = new File("windowStatus.txt");
            Logger.getLogger(WindowStatus.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Checks whether the window is open and returns true or false in string form
     * @param windowName the name of the window to check if it is open
     * @return "true" if the window is open, "false" otherwise
     */
    public static String getWindowStatus(String windowName) {
        String status = "";
        try {
            // read in the lines of the file
            ArrayList<String> lines = getLines();
            boolean found = false;
            // parse through the lines
            for (int i = 0, n = lines.size(); i < n; i++) {
                // if the sleected line contains the windowName, get the status on the line
                if (lines.get(i).contains(windowName)) {
                    String toIgnore = windowName + ": ";
                    status = lines.get(i).substring(toIgnore.length());
                    found = true;
                }
            }
            // if the windowname was not found, add the windowName parameter's text to the lines list and set the status to false
            if (!found) {
                lines.add(windowName + ": false");
                status = "false";
            }
        }
        catch (FileNotFoundException ex) {
            // if the file was not found, add the windowname to the file
            writeFile("false", windowName);
            status = "false";
        }
        return status;
    }
    
    /**
     * Gets the lines in the windowstatus file contained by FILENAME
     * @return an ArrayList of Strings that each containa aline of the file
     * @throws FileNotFoundException 
     */
    private static ArrayList<String> getLines() throws FileNotFoundException {
        // Initializes Variables
        ArrayList<String> lines = new ArrayList<>();
        File input = new File(FILENAME);
        Scanner in = new Scanner(input);
        
        // Reads the lines from the file and adds it to the arraylist
        while(in.hasNextLine()) {
            lines.add(in.nextLine());
        }
        
        // Closes the file and returns the lines
        in.close();
        return lines;
    }
}
