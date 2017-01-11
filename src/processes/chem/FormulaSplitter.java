package processes.chem;

import processes.helpers.Formatting;
import java.util.ArrayList;

/**
 *
 * @author Jared Cassarly
 */
public class FormulaSplitter {
    /**
     * The elements of the formula without any multiples
     */
    private ArrayList<String> elements;
    
    /**
     * The number of each element in the formula added for totals
     * Corresponds to elements
     */
    private ArrayList<Integer> numbers;
    
    /**
     * The formatted formula
     */
    final private String formula;
    
    /**
     * Creates a new MassFinder with a specified formula to find the mass of.
     * @param f the inputted and optionally unformatted formula
     *      Formatted by Formatting.chemFormat(String f)
     */
    public FormulaSplitter(String f) {
        formula = Formatting.chemFormat(f);
        initFormulaSplitter(formula, 1);
    }
    
    public FormulaSplitter(String f, int num) {
        formula = Formatting.chemFormat(f);
        initFormulaSplitter(formula, num);
    } 
    
    /**
     * Returns the elements in the formula without any repeated occurrences
     * @return the elements in the formula without any repeated occurrences
     */
    public String[] getElements() {
        String[] e = new String[elements.size()];
        for (int i = 0; i < e.length; i++) {
            e[i] = elements.get(i);
        }
        return e;
    }
    
    /**
     * Returns The number of each element in the formula added for totals
     * Corresponds to elements
     * @return The number of each element in the formula added for totals
     */
    public int[] getNumbers() {
        int[] n = new int[numbers.size()];
        for (int i = 0; i < n.length; i++) {
            n[i] = numbers.get(i);
        }
        return n;
    }
    
    /**
     * Returns the formula of the compound
     * @return the formula of the compound in a String
     */
    public String getFormula() {
        return formula;
    }
    
    /**
     * Gives the number of elements in the formula
     * @return the number of elements
     */
    public int length() {
        return elements.size();
    }
    
    /**
     * Checks to see if the formula contains the String element
     * @param element the String to check if the formula contains it
     * @param number
     * @return true if the formula contains element, false otherwise
     */
    public boolean contains(String element, int number) {
        for (int i = 0, n = elements.size(); i < n; i++) {
            if (element.equals(elements.get(i)) && number == numbers.get(i)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Checks to see if the elements and numbers of the FormulaSplitter are the same as those of another.
     * @param other another FormulaSplitter
     * @return true if the elements and numbers are the same, false otherwise.
     */
    public boolean equals(FormulaSplitter other) {
        
        if (this.length() != other.length()) {
            return false;
        }
        
        for (int i = 0, n = this.length(); i < n; i++) {
            if (!other.contains(this.elements.get(i), this.numbers.get(i))) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Split up the formula into its parts
     * @param f the compound as a string (eg Fe2O3)
     * @param num the number of compounds htere are
     */
    private void initFormulaSplitter(String f, int num) {
        elements = new ArrayList<>();
        numbers = new ArrayList<>();
        
        // Make sure the first character is not a digit
        if (Formatting.isDigit(formula.charAt(0))) {
            throw new UnsupportedOperationException("Coefficients are not allowed.\nThe formula must start with an element.");
        }
        
        // Make sure the open and close parens are formatted correctly
        this.checkParens();
        
        // Split the formula
        for (int i = 0; i < formula.length(); i++) {
            // If the character at the current position is uppercase and the next character is lowercase, add that element
            if (Formatting.isUpper(formula.charAt(i)) && i < formula.length() - 1 && Formatting.isLower(formula.charAt(i+1))) {
                elements.add(formula.substring(i, i+2));
                i++;
            }
            // If the character at the current position is upper case add the element
            else if (Formatting.isUpper(formula.charAt(i))) {
                elements.add(formula.substring(i, i+1));          
            }
            // If the character at the current position is a digit, find where the number ends and add the whole thing to the numbers list
            else if (Formatting.isDigit(formula.charAt(i))) {
                int toEnd = i;
                // find the end of the number
                for (int x = i; x < formula.length(); x++) {
                    if (!Formatting.isDigit(formula.charAt(x))) {
                        x = formula.length();
                    }
                    else {
                        toEnd++;
                    }
                }
                // add it to the numbers list
                String theInt = formula.substring(i, toEnd);
                numbers.add(Integer.parseInt(theInt));
                
                i = toEnd-1;
            }
            // If the character is a '(' add the insides to the lists
            else if (Formatting.isStartParen(formula.charAt(i))) {
                int begin = i+1;
                int openParens = 1, closeParens = 0;
                i++;
                // find the next endParen where the number of starting and ending parentheses are equal
                while (openParens != closeParens && i < formula.length()) {
                    if (Formatting.isStartParen(formula.charAt(i))) {
                        openParens++;
                    }
                    else if (Formatting.isEndParen(formula.charAt(i))) {
                        closeParens++;
                    }
                    i++;
                }
                // get the string between the parentheses
                String toAdd = formula.substring(begin, i-1);
                // make sure there is text between the parenthese
                if (toAdd.equals("")) {
                    throw new UnsupportedOperationException("Formula contains illegal string of  \"()\"");
                }
                // if there is a number after the ending parenthesis found
                if (Formatting.isDigit(formula.charAt(i))) {
                    int toEnd = i;
                    // get the number after the end paren
                    for (int x = i; x < formula.length(); x++) {
                        if (!Formatting.isDigit(formula.charAt(x))) {
                            x = formula.length();
                        }
                        else {
                            toEnd++;
                        }
                    }
                    String theInt = formula.substring(i, toEnd);
                    // add the text between the parentheses to the list recursively with the number after it found
                    this.add(new FormulaSplitter(toAdd, Integer.parseInt(theInt)));

                    i = toEnd-1;
                }
                else {
                    this.add(new FormulaSplitter(toAdd, 1));
                }
            }
            // Else somethings wrong
            else {
                throw new UnsupportedOperationException("Please enter a valid chemical formula.\nFormat should be element followed by another\nformula or element.\nDo not enter any other characters.");
            }
        }
        
        // add the list of elements to a new list to remove repeats
        ArrayList<String> elementsToSet = new ArrayList<>();
        for (int i = 0, n = elements.size(); i < n; i++) {
            String toAdd = elements.get(i);
            elementsToSet.add(toAdd);
            if (!Formatting.isElement(elements.get(i))) {
                throw new UnsupportedOperationException(toAdd + " is not an  valid element symbol.\nPlease use only valid element symbols. \nElement symbols are case sensitive.");
            }
        }
        
        // Set up the numbersToSet Array list and multiply through by num
        ArrayList<Integer> numbersToSet = new ArrayList<>();
        for (int i = 0, n = numbers.size(); i < n; i++) {
            numbersToSet.add(numbers.get(i) * num);
        }
        
        // Make the no repeat element list
        setNoRepeat(elementsToSet, numbersToSet);
        
    }
    
    /**
     * Add a new list of compounds to the lists
     * @param fs the Compound as a FormulaSplitter
     */
    private void add(FormulaSplitter fs) {
        String[] e = fs.getElements();
        int[] n = fs.getNumbers();
        
        // add each element and its number to their respective lists
        for (int i = 0; i < e.length; i++) {
            elements.add(e[i]);
            numbers.add(n[i]);
        }
    }
    
    /**
     * Check to make sure the parentheses in the formula match
     */
    private void checkParens() {
        int openParenCount = 0, closeParenCount = 0;
        for (int i = 0; i < formula.length(); i++) {
            // increment the open paren count when one is found
            if (Formatting.isStartParen(formula.charAt(i))) {
                openParenCount++;
            }
            // increment the close paren count then one is found
            else if (Formatting.isEndParen(formula.charAt(i))) {
                closeParenCount++;
            }
            
            // throw an error if the close paren count ever becomes greater than the open paren count
            if (closeParenCount > openParenCount) {
                throw new UnsupportedOperationException("Invalid Close Parenthesis at index " + i + formula);
            }
        }
        
        // make sure the counts are equal at the end of the formula
        if (openParenCount != closeParenCount) {
            throw  new  UnsupportedOperationException("Open and Close Parentheses counts must match.");
        }
    }

    /**
     * Sets the up the elements and numbers ArrayLists so that there are no repeats based on the elements list
     * @param e the ArrayList of Strings that is the original
     */
    private void setNoRepeat(ArrayList<String> e, ArrayList<Integer> n) {
        ArrayList<String> elementsNoRepeats = new ArrayList<>();
        ArrayList<Integer> numbersNoRepeats = new ArrayList<>();
        // Set up Element names to have no repeats
        if (e.size() > 0) {
            // parse through the elements list
            for (int i = 0, size = e.size(); i < size; i++) {
                boolean alreadyThere = false;
                // check if the selected element is already in the no repeats list
                for (int j = 0; j < elementsNoRepeats.size(); j++) {
                    if (elementsNoRepeats.get(j).equals(e.get(i))) {
                        alreadyThere = true;
                        j = elementsNoRepeats.size();
                    }
                }
                // if the element is not in the no repeats list, add it
                if (!alreadyThere) {
                    elementsNoRepeats.add(e.get(i));
                }
            }
        }
        
        // Set up the counts that correspond to the elements list
        // initialize the list
        for (int i = 0; i < elementsNoRepeats.size(); i++) {
            numbersNoRepeats.add(0);
        }
        
        for (int i = 0; i < elementsNoRepeats.size(); i++) {
            for (int j = 0; j < e.size(); j++) {
                // if the element in the original elements list is the element selected in the no repeats list, add their numbers together
                if (e.get(j).equals(elementsNoRepeats.get(i))) {
                    numbersNoRepeats.set(i, numbersNoRepeats.get(i) + n.get(j));
                }
            }
        }
        
        // Reset the elements and number lists to the no repeat versions.
        elements = new ArrayList<>();
        numbers = new ArrayList<>();
        for (int i = 0; i < elementsNoRepeats.size(); i++) {
            elements.add(elementsNoRepeats.get(i));
            numbers.add(numbersNoRepeats.get(i));
        }
    }
    
}
