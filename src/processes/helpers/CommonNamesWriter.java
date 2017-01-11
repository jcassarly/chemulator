package processes.helpers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Arrays;

/**
 *
 * @author Jared Cassarly
 */
public class CommonNamesWriter {
    // <editor-fold defaultstate="collapsed" desc="Default Headers">
    final public static String[] DEFAULT_HEADERS = new String[] {"Name", "Formula", "Charge"};
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Default Common Names">
    final public static String[][] DEFAULT_COMMON_NAMES = new String[][] {
        {"Acetate", "C2H3O2", "1-"},
            {"Ammonium", "NH4", "1+"},
            {"Arsenate", "AsO4", "3-"},
            {"Arsenite", "AsO3", "3-"},
            {"Borate", "BO3", "3-"},
            {"Bromate", "BrO3", "1-"},
            {"Bromite", "BrO2", "1-"},
            {"Carbonate", "CO3", "2-"},
            {"Chlorate", "ClO3", "1-"},
            {"Chlorite", "ClO2", "1-"},
            {"Chromate", "CrO4", "2-"},
            {"Cyanate", "OCN", "1-"},
            {"Cyanide", "CN", "1-"},
            {"Dichromate", "Cr2O7", "2-"},
            {"Dihydrogen Arsenate", "H2AsO4", "1-"},
            {"Dihydrogen Phosphate", "H2PO4", "1-"},
            {"Hydrogen Carbonate (Bicarbonate)", "HCO3", "1-"},
            {"Hydrogen Oxalate (Binoxalate)", "HC2O4", "1-"},
            {"Hydrogen Phosphate", "HPO4", "2-"},
            {"Hydrogen Selenate", "HSeO4", "1-"},
            {"Hydrogen Sulfate (Bisulfate)", "HSO4", "1-"},
            {"Hydrogen Sulfite (Bisulfite)", "HSO3", "1-"},
            {"Hydroxide", "OH", "1-"},
            {"Hypochlorite", "ClO", "1-"},
            {"Iodate", "IO3", "1-"},
            {"Mangenate", "MnO3", "1-"},
            {"Molybdate", "MoO4", "2-"},
            {"Nitrate", "NO3", "1-"},
            {"Nitrite", "NO2", "1-"},
            {"Oxalate", "C2O4", "2-"},
            {"Perchlorate", "ClO4", "1-"},
            {"Permanganate", "MnO4", "1-"},
            {"Peroxide", "O2", "2-"},
            {"Phosphate", "PO4", "3-"},
            {"Phosphite", "PO3", "3-"},
            {"Phythalate", "C8H4O4", "2-"},
            {"Pyrophosphate", "P2O7", "4-"},
            {"Selenate", "SeO4", "2-"},
            {"Silicate", "SiO3", "2-"},
            {"Sulfate", "SO4", "2-"},
            {"Sulfite", "SO3", "2-"},
            {"Tartrate", "C4H4O6", "2-"},
            {"Thiocyanate", "SCN", "1-"},
            {"Thiosulfate", "S2O3", "2-"}
    };
    // </editor-fold>
    
    final public static String CSV_FILENAME = "Common_Names.csv";
    
    /**
     * Adds the new input for common names and formulas to the csv file
     * @param name the name of the compound to add
     * @param formula the chemical formula of the compound to add
     * Precondition: the formula must be a legal chemical formula or it will not work when adding to the molar mass calculator
     * @param charge the charge of the compound to add
     */
    public static void add(String name, String formula, String charge) {
        // get the array of common names
        String[][] current = getCommonNames();
        // create an array to copy the current contents into
        String[][] newCommonNames = new String[current.length + 1][current[0].length];
        // copy the current info into the array
        for (int r = 0; r < current.length; r++) {
            for (int c = 0; c < current[0].length; c++) {
                newCommonNames[r][c] = current[r][c];
            }
        }
        // add the new info to the array at the end
        newCommonNames[newCommonNames.length - 1] = new String[] {name, formula, charge};
        
        // sort the new array
        Arrays.sort(newCommonNames, new NameComparator());
        
        // Write the new contents of the array to a file
        writeFile(newCommonNames);
    }
    
    public static void add(String[] names, String[] formulas, String[] charges) {
        // check to make sure all the array lengths have the same length
        if (names.length == formulas.length && names.length == charges.length && formulas.length == charges.length) {
            // get the array of common names
            String[][] current = getCommonNames();
            // create a new array to copy the current contents into
            String[][] newCommonNames = new String[current.length + names.length][current[0].length];
            // copy the current info into the array
            for (int r = 0; r < current.length; r++) {
                for (int c = 0; c < current[0].length; c++) {
                    newCommonNames[r][c] = current[r][c];
                }
            }
            
            // copy the new info into the array
            for (int i = 0; i < names.length; i++) {
                newCommonNames[current.length + i] = new String[] {names[i], formulas[i], charges[i]};
            }
            
            // sort the array
            Arrays.sort(newCommonNames, new NameComparator());
            
            // write the new contents of the array to a file
            writeFile(newCommonNames);
        }
    }
    
    /**
     * Gives the array of Strings containing the names, formulas, and charges
     * If the file does not exist, new file containing the default info is created
     * @return the array of Strings containing the names, formulas, and charges
     */
    public static String[][] getCommonNames() {
        String[][] commonNames;
        try {
            File f = new File(CSV_FILENAME);
            int rows = 0;
            final int cols = 3;
            
            if (f.exists() && !f.isDirectory()) {
                // Count the number of lines in the file
                Scanner lineCounter = new Scanner(f);
                while (lineCounter.hasNextLine()) {
                    String nextLine = lineCounter.nextLine();
                    if (!(nextLine.equals("") || nextLine.equals("\n"))) {
                        rows++;
                    }
                }
                lineCounter.close();
                
                // Read the lines from the file
                commonNames = new String[rows][cols];
                
                Scanner reader = new Scanner(f);
                int rowIndex = 0;
                while (reader.hasNextLine() && rowIndex < rows) {
                    String nextLine = reader.nextLine();
                    // read in the next line, splitting it around commas
                    if (!(nextLine.equals("") || nextLine.equals("\n"))) {
                        commonNames[rowIndex] = nextLine.split(",");
                        rowIndex++;
                    }
                }
                reader.close();
            } 
            else {
                writeFile(DEFAULT_COMMON_NAMES);
                return DEFAULT_COMMON_NAMES;
            }
        } catch (FileNotFoundException ex) {
            writeFile(DEFAULT_COMMON_NAMES);
            return DEFAULT_COMMON_NAMES;
        }
        
        
        return commonNames;
    }
    
    /**
     * Writes the contents of commonNames to the file at address CSV_FILENAME
     * @param commonNames the data to write to the file
     */
    public static void writeFile(String[][] commonNames) {
        try {
            PrintWriter out = new PrintWriter(CSV_FILENAME);
            // print each element with a comma separating columns of elements in the same row
            for (int r = 0; r < commonNames.length; r++) {
                for (int c = 0; c < commonNames[0].length; c++){
                    // print the element
                    out.print(commonNames[r][c]);
                    
                    // print anew line if the last element in the row was printed
                    if (c == commonNames[0].length - 1) {
                        out.println();
                    }
                    // add a comma if its not the last element in the row
                    else {
                        out.print(",");
                    }
                }
            }
            
            out.flush();
            out.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CommonNamesWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Create a new class to compare the names of an array strings
     */
    private static class NameComparator implements java.util.Comparator<String[]> {
        
        public NameComparator() {
            
        }

        /**
         * Compare 2 arrays of strings based on the comparison of the compound names as first priority, then the compound formulas as second priority and then the charges as the last priority
         * @param t1 an array of strings
         * @param t2 an array of strings
         * @return the value of the String compareTo method called upon the elements in the arrays that were compared
         */
        @Override
        public int compare(String[] t1, String[] t2) {
            // check if both compound names are empty strings
            if (t1[0].equals("") && t2[0].equals("")) {
                // check if compound formulas are equal, if they are return the comparison of the charge strings
                if (t1[1].equals(t2[1])) {
                    return t1[2].compareTo(t2[2]);
                }
                // else return the comparison of the formula strings
                else {
                    return t1[1].compareTo(t2[1]);
                }
            }
            // else if only one of the compound names is an empty string, return the comparison * -1
            else if (t1[0].equals("") || t2[0].equals("")) {
                return t1[0].compareTo(t2[0]) * -1;
            }
            // else return the string comparison of the compound names
            else {
                return t1[0].compareTo(t2[0]);
            }
        }
        
    }
}
