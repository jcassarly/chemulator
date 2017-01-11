package processes.wrappers;

import processes.chem.FormulaSplitter;
import processes.helpers.Formatting;

/**
 *
 * @author Jared Cassarly
 */
public class Compound {
    
    private FormulaSplitter compound;
    private int coef;
    
    /**
     * Create a new Compound given an integer coefficient and a valid string with a chemical formula
     * @param coefficient the coefficient of the formula
     * @param formula a string containing the chemical formula of the compound
     */
    public Compound(int coefficient, String formula) {
        this.coef = coefficient;
        this.compound = new FormulaSplitter(formula);
    }
    
    /**
     * Create a new Compound given an integer coefficient and a FormulaSplitter that contains the chemical formula
     * @param coefficient
     * @param compound 
     */
    public Compound(int coefficient, FormulaSplitter compound) {
        this.coef = coefficient;
        this.compound = compound;
    }
    
    /**
     * Create a new Compound given a string that has a coefficient and 
     * @param formulaWithCoefficient 
     */
    public Compound(String formulaWithCoefficient) {
        coef = 1;
        String compound = "";
        int i;
        // parse through the formula until an upper case character or a '(' character is found and use the string from there until the end as the compound
        for (i = 0; i < formulaWithCoefficient.length(); i++) {
            if (Formatting.isUpper(formulaWithCoefficient.charAt(i)) || Formatting.isStartParen(formulaWithCoefficient.charAt(i))) {
                compound = formulaWithCoefficient.substring(i);
                break;
            }
        }
        // get the coefficient of the formula if there is one
        if (i > 0) {
            coef = Integer.parseInt(formulaWithCoefficient.substring(0, i));
        }
        
        // create new FormulaSplitter object using the compound formula found
        this.compound = new FormulaSplitter(compound);
    }
    
    /**
     * Returns the coefficient of the compound
     * @return the coefficient of the compound
     */
    public int getCoefficient() {
        return coef;
    }
    
    /**
     * Returns the compound formula as a FormulaSplitter
     * @return the compound formula as a FormulaSplitter
     */
    public FormulaSplitter getCompound() {
        return compound;
    }
    
    /**
     * Returns the compound formula with the coefficient multiplied through by putting it on the end of the formula
     * @return the Compound formula with the coefficient on the end plugged into a FormulaSplitter
     */
    public FormulaSplitter getCompoundWithCoef() {
        return new FormulaSplitter("(" + getCompound().getFormula() + ")" + getCoefficient());
    }
}
