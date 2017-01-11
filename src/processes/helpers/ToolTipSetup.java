package processes.helpers;

import processes.wrappers.Element;

/**
 *
 * @author Jared Cassarly
 */
public class ToolTipSetup {
    /**
     * Create the tool tip text for an element based on its symbol
     * @param symbol
     * @return 
     */
    public static String getMolarMassToolTip(String symbol) {
        int atomicNumber = 0;
        // get the atomic number of the selected element based on its symbol
        for (int i = 0; i < Element.SYMBOLS.length; i++) {
            if (symbol.equals(Element.SYMBOLS[i])) {
                atomicNumber = Element.ATOMIC_NUMBER[i];
            }
        }
        // get the name of the element
        String name = Element.NAMES[atomicNumber - 1];
        // get the mass of the element
        double mass = Element.MASSES[atomicNumber - 1];
        
        // create the tool tip text
        String tooltip = "Atomic Number: " + atomicNumber + ", ";
        tooltip += "Name: " + name + ", ";
        tooltip += "Mass: " + mass + ", ";
        
        // return the text
        return tooltip;
    }
}
