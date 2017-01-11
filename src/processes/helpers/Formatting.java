package processes.helpers;

import java.util.ArrayList;
import processes.wrappers.Element;

/**
 * Group of useful functions for formatting strings/chars and checking their formatting
 * @author Jared Cassarly
 */
public class Formatting {
    /**
     * Puts the formula into the format needed for finding the molar mass and separating into element lists
     * @param f the formula to format
     * @return the formatted formula
     */
    public static String chemFormat(String f) {
        // throw error if the string is empty
        if (f.length() == 0) {
            throw new UnsupportedOperationException("Please Enter Something to Calculate.\nThe Length of the formula must be greater than 0.");
        }
        
        // add ones after each of the elements without a number
        for (int i = 0; i < f.length(); i++) {
            // if the character after a 2 character element is not a digit
            if (isUpper(f.charAt(i)) && i < f.length() - 2 && isLower(f.charAt(i+1))) {
                if (!isDigit(f.charAt(i+2))) {
                    // add one after the current element
                    String temp = f.substring(0, i+2) + "1";
                    f = temp + f.substring(i+2);
                }
            }
            // if the caracter after the 2 character element is the end of the string, add a 1
            else if (isUpper(f.charAt(i)) && i == f.length() - 2 && isLower(f.charAt(i+1))) {
                f += "1";
            }
            // if the character is a 1 character element or an end paren  and is not at the end of the string
            else if ((isUpper(f.charAt(i)) || isEndParen(f.charAt(i))) && i < f.length() - 1) {
                // if the next character is not a digit
                if (!isDigit(f.charAt(i+1))) {
                    // add one after the current character/element
                    String temp = f.substring(0, i+1) + "1";
                    f = temp + f.substring(i+1);
                }
            }
            // if the character is a one character element or an end paren and is at the end of a string, add a 1
            else if ((isUpper(f.charAt(i)) || isEndParen(f.charAt(i))) && i == f.length() - 1) {
                f += "1";
            }
        }
        // return the new string with ones
        return f;
    }
    
    /**
     * Removes all the spaces in the String f
     * @param f String to take out all the spaces
     * @return the String f without spaces
     */
    public static String removeSpaces(String f) {
        return f.replaceAll(" ", "");
    }
    
    
    /**
     * Checks if a character is upper-case
     * @param s the character to check
     * @return true if upper-case, false otherwise
     */
    public static boolean isUpper(char s) {
        return s >= 'A' && s <= 'Z';
    }
    
    /**
     * Checks if a character is lower-case
     * @param s the character to check
     * @return true if lower-case, false otherwise
     */
    public static boolean isLower(char s) {
        return s >= 'a' && s <= 'z';
    }
    
    /**
     * Checks if a character is a digit (0, 1, 2, 3, 4, 5, 6, 7, 8, 9)
     * @param s that character to check
     * @return true if the character is a digit, false otherwise
     */
    public static boolean isDigit(char s) {
        return s >= '0' && s <= '9';
    }
    
    /**
     * Check if a string contains only digits and no other characters
     * @param s the string to check
     * @return true if all characters are digits, false otherwise
     */
    public static boolean isOnlyDigits(String s) {
        // parse through the string
        for (int i = 0; i < s.length(); i++) {
            //  return false if the current character is not a digit
            if (!isDigit(s.charAt(i))) {
                return false;
            }
        }
        // only digits were found, return ture
        return true;
    }
    
    /**
     * Checks if a character is a start of a parenthesis -> '('
     * @param s the character to check
     * @return true if '(' , false otherwise
     */
    public static boolean isStartParen(char s) {
        return s == '(';
    }
    
    /**
     * Checks if a character is an end of a parenthesis -> ')'
     * @param s the character to check
     * @return true if ')' , false otherwise
     */
    public static boolean isEndParen(char s) {
        return  s == ')';
    }
    
    /**
     * Checks if a character is alphabetical
     * @param s the character to check
     * @return true if alphabetical, false otherwise
     */
    public static boolean isAlpha(char s) {
        return isLower(s) || isUpper(s);
    }
    
    /**
     * Add one to a string that is a digit and returns in the form of a string
     * Precondition: the String must be a digit
     * @param s the string to add one to
     * @return the string's value plus one
     */
    public static String addOneToString(String s) {
        int d = Integer.parseInt(s);
        d++;
        return Integer.toString(d);
    }
    
    /**
     * Check if the String is a valid element
     * @param s the string that should be 1 or 2 characters to be an element
     * @return true if the string matches an elemental symbol (case sensitive), false otherwise
     */
    public static boolean isElement(String s) {
        final String[] symbols = Element.SYMBOLS;
        for (int i = 0; i < symbols.length; i++) {
            if (symbols[i].equals(s)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Count the number of instances of a given key are in a string
     * @param s the string to count number of occurences of the key
     * @param key the key to find the number of times it occurs
     * @return the number of indexes of a key
     */
    public static int numContained(String s, String key) {
        int count = 0;
        String sCopy = String.valueOf(s);
        int index = sCopy.indexOf(key);
        while (index >= 0) {
            count++;
            sCopy = sCopy.substring(index + 1);
            index = sCopy.indexOf(key);
        }
        return count;
    }
    
    /**
     * Split a side of an equation into individual compounds (split along the '+' characters)
     * @param s the side of the equation
     * @return the array of compound formulas
     */
    public static String[] splitIntoCompounds(String s) {
        // Initialize Variables
        String toScan = removeSpaces(s);
        ArrayList<String> rtn = new ArrayList<>();

        int begin = 0;
        int end = toScan.indexOf("+", begin);
        
        // Find all compounds and add them to the rtn list
        while (end >= 0) {
            rtn.add(toScan.substring(begin, end));
            begin = end + 1;
            if (begin + 1 > toScan.length()) {
                throw new UnsupportedOperationException("Character \'+\' cannot occur at the end of\nthe string inputted.\nThey must be surrounded by elements.");
            }
            end = toScan.indexOf("+", begin);
        }
        // Get the last compound after the final '+'
        rtn.add(toScan.substring(begin));
        
        // Convert to an array of Strings and return
        String[] toRtn = new String[rtn.size()];
        for (int i = 0, n = toRtn.length; i < n; i++) {
            toRtn[i] = rtn.get(i);
        }
        return toRtn;
    }
    
    /**
     * Removes all non-alphanumeric and non-parentheses (illegal characters) from the string
     * @param s the string to modify by removing all illegal characters
     */
    public static String removeIllegalChar(String s) {
        // parse through string
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            // remove the character if its "illegal"
            if (!isAlpha(c) && !isDigit(c) && !isStartParen(c) && !isEndParen(c)) {
                s = s.substring(0, i) + s.substring(i+1);
                i = 0;
            }
        }
        return s;
    }
}
