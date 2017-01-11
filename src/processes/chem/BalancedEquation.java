package processes.chem;

import processes.helpers.Formatting;
import processes.wrappers.Compound;

/**
 *
 * @author Jared Cassarly
 */
public class BalancedEquation {
    
    private Compound[] reactants;
    private Compound[] products;
    
    /**
     * Creates a new Balanced equation using the given compounds on each side and the coefficients of each compounds
     * Precondition: the length of reactantFormulas must equal that of rectantCoefs
     * Precondition: the length of productFormulas must equal that of productCoefs
     * Precondition: the values of reactantCoefs and productCoefs must all be greater than 0
     * @param reactantFormulas The compounds on the reactant side
     * @param productFormulas The compounds on the product side
     * @param reactantCoefs The coefficients corresponding to the reactants
     * @param productCoefs The coefficients corresponding to the reactants
     */
    public BalancedEquation(FormulaSplitter[] reactantFormulas,  FormulaSplitter[] productFormulas, int[] reactantCoefs, int[] productCoefs) {
        if (reactantFormulas.length != reactantCoefs.length) {
            throw new UnsupportedOperationException("The reactant's formulas and reactant's coefficients do not match.");
        }
        else if (productFormulas.length != productCoefs.length) {
            throw new UnsupportedOperationException("The product's formulas and product's coefficients do not match.");
        }
        
        if (containsLessThanOne(reactantCoefs) || containsLessThanOne(productCoefs)) {
            throw new UnsupportedOperationException("The coefficients of reactants and products are not greater than 0.");
        }
        
        initCompounds(reactantFormulas, productFormulas, reactantCoefs, productCoefs);
    }
    
    /**
     * Creates a new Balanced equation using the given compounds' formulas on each side and the coefficients of each compounds
     * Precondition: the length of reactantFormulas must equal that of rectantCoefs
     * Precondition: the length of productFormulas must equal that of productCoefs
     * Precondition: the values of reactantCoefs and productCoefs must all be greater than 0
     * @param reactantFormulas The compounds formulas on the reactant side
     * @param productFormulas The compounds formulas on the product side
     * @param reactantCoefs The coefficients corresponding to the reactants
     * @param productCoefs The coefficients corresponding to the reactants
     */
    public BalancedEquation(String[] reactantFormulas, String[] productFormulas, int[] reactantCoefs, int[] productCoefs) {
        if (reactantFormulas.length != reactantCoefs.length) {
            throw new UnsupportedOperationException("The reactant's formulas and reactant's coefficients do not match.");
        }
        else if (productFormulas.length != productCoefs.length) {
            throw new UnsupportedOperationException("The product's formulas and product's coefficients do not match.");
        }
        
        if (containsLessThanOne(reactantCoefs) || containsLessThanOne(productCoefs)) {
            throw new UnsupportedOperationException("The coefficients of reactants and products are not greater than 0.");
        }
        
        // Make reactants and products FormulaSplitters arrays to be the length of their repective Strings to input
        FormulaSplitter[] r = new FormulaSplitter[reactantFormulas.length];
        FormulaSplitter[] p = new FormulaSplitter[productFormulas.length];
        
        // Set the reactants and products to have their respective formulas split and analyzed
        for (int i = 0; i < r.length; i++) {
            r[i] = new FormulaSplitter(reactantFormulas[i]);
        }
        for (int i = 0; i < p.length; i++) {
            p[i] = new FormulaSplitter(productFormulas[i]);
        }
        
        initCompounds(r, p, reactantCoefs, productCoefs);
    }
    
    /**
     * Creates a new Balanced Equation by splitting a string with the reactants and their coefficients and a string with the products and their coefficients
     * @param reactantsWithCoefs The reactants in the equation with coefficients before them and each reactant is separated by a '+'
     * @param productsWithCoefs The products in the equation with coefficients before them and each products is separated by a '+'
     */
    public BalancedEquation(String reactantsWithCoefs, String productsWithCoefs) {
        // split the 2 sides of the equation into individual compounds
        String[] rCompounds = Formatting.splitIntoCompounds(reactantsWithCoefs);
        String[] pCompounds = Formatting.splitIntoCompounds(productsWithCoefs);
        reactants = new Compound[rCompounds.length];
        products = new Compound[pCompounds.length];
        
        // create the arrays of compounds
        try {
            for (int i = 0; i < reactants.length; i++) {
                reactants[i] = new Compound(rCompounds[i]);
            }
            for (int i = 0; i < products.length; i++) {
                products[i] = new Compound(pCompounds[i]);
            }
        } catch(NumberFormatException ex) {
            throw new UnsupportedOperationException("Please enter a valid chemical formula.\nFormat should be element followed by\nanother formula, element, or plus.\nDo not enter any other characters.");
        }
        
    }
    
    /**
     * Initializes the compounds
     * Precondition: r.length == reactantCoefs.length AND p.length == productCoefs.length
     * @param r The compounds on the reactant side
     * @param p The compounds on the product side
     * @param reactantCoefs The coefficients corresponding to the reactants
     * @param productCoefs The coefficients corresponding to the products
     */
    private void initCompounds(FormulaSplitter[] r, FormulaSplitter[] p, int[] reactantCoefs, int[] productCoefs) {
        reactants = new Compound[r.length];
        products = new Compound[p.length];
        
        // Initialize the Compounds
        for (int i = 0; i < r.length; i++) {
            reactants[i] = new Compound(reactantCoefs[i], r[i]);
        }
        
        for (int i = 0; i < p.length; i++) {
            products[i] = new Compound(productCoefs[i], p[i]);
        }
    }
    
    /**
     * Checks to make sure an array of ints does not have any elements less than or equal to 0
     * @param toCheck the int array to check that it does not have any elements less than o equal to 0
     * @return true is toCheck contains elements less than or equal to 0, false otherwise
     */
    private static boolean containsLessThanOne(int[] toCheck) {
        // parse through array of ints
        for (int i = 0; i < toCheck.length; i++) {
            // if int is <= 0, exit and return true
            if (toCheck[i] <= 0) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Returns the coefficients of an array of Compounds
     * @param compounds an array of compounds to get all the coefficients from
     * @return the coefficients of an array of Compounds
     */
    public static int[] getCoefficients(Compound[] compounds) {
        int[] coefs = new int[compounds.length];
        // parse through the array of compunds and get a coefficient associated with each compound, adding it to the array of them
        for (int i = 0 ; i < compounds.length; i++) {
            coefs[i] = compounds[i].getCoefficient();
        }
        return coefs;
    }
    
    /**
     * Gets the formulas (no coefficients) of the compounds
     * @param compounds the array of compounds which contain the formulas
     * @return an array of strings which hold compounds
     */
    public static String[] getFormulas(Compound[] compounds) {
        String[] f = new String[compounds.length];
        // parse through the compounds and get the string formula of each compound
        for (int i = 0 ; i < compounds.length; i++) {
            f[i] = compounds[i].getCompound().getFormula();
        }
        return f;
    }
    
    
    /**
     * Returns a FormulaSplitter that has the total number of each element in toCondense
     * @param toCondense an array of FormulaSplitters to condense
     * @return the condensed FormulaSplitter with the elements in no particular order.
     */
    public static FormulaSplitter getCondensed(FormulaSplitter[] toCondense) {
        String newFormula = "";
        // add all the string formulas to a string
        for (int i = 0; i < toCondense.length; i++) {
            newFormula += toCondense[i].getFormula();
        }
        // create a new FormulaSplitter that uses the String with all compounds added to it as basically one big compound
        FormulaSplitter condensed = new FormulaSplitter(newFormula);
        // return the new FormulaSplitter
        return condensed;
    }
    
    /**
     * Checks to see if this BalancedEquation is balanced
     * @return true if the equation is balanced, false otherwise.
     */
    public boolean isBalanced() {
        FormulaSplitter[] reactantsWithCoefs = new FormulaSplitter[reactants.length];
        FormulaSplitter[] productsWithCoefs = new FormulaSplitter[products.length];
        
        // add all the compounds to a formula so that they can be made into a new FormulaSplitter that takes into account their coeffcient
        for (int i = 0; i < reactantsWithCoefs.length; i++) {
            reactantsWithCoefs[i] = reactants[i].getCompoundWithCoef();
        }
        for (int i = 0; i < productsWithCoefs.length; i++) {
            productsWithCoefs[i] = products[i].getCompoundWithCoef();
        }
        
        // condense the new formulas
        FormulaSplitter rCondensed = getCondensed(reactantsWithCoefs);
        FormulaSplitter pCondensed = getCondensed(productsWithCoefs);
        
        // check if the number of elements in each is equal
        return rCondensed.equals(pCondensed);
    }
    
    /**
     * Returns a string with the reactants and their coefficients as they would be represented in the equation
     * @return a string with the reactants and their coefficients as they would be represented in the equation
     */
    public String getReactantSide() {
        int[] coefs = getCoefficients(reactants);
        if (containsLessThanOne(coefs)) {
            throw new UnsupportedOperationException("The coefficients of reactants and products are not greater than 0.");
        }
        String reactantSide = "";
        for (int i = 0; i < reactants.length; i++) {
            reactantSide += coefs[i] + reactants[i].getCompound().getFormula() + " + ";
        }
        reactantSide = reactantSide.substring(reactantSide.length() - 3);
        return reactantSide;
    }
    
    /**
     * Returns a string with the products and their coefficients as they would be represented in the equation
     * @return a string with the products and their coefficients as they would be represented in the equation
     */
    public String getProductSide() {
        int[] coefs = getCoefficients(products);
        for (int i = 0 ; i < products.length; i++) {
            coefs[i] = reactants[i].getCoefficient();
        }
        if (containsLessThanOne(coefs)) {
            throw new UnsupportedOperationException("The coefficients of reactants and prooducts must be greater than 0.");
        }
        String reactantSide = "";
        for (int i = 0; i < products.length; i++) {
            reactantSide += coefs[i] + products[i].getCompound().getFormula() + " + ";
        }
        reactantSide = reactantSide.substring(reactantSide.length() - 3);
        return reactantSide;
    }
    
    /**
     * Returns a string with the reactants side on the right and arrow and then the products side on the right
     * @return a String with the entire equation
     */
    public String printBalancedEquation() {
        return this.getReactantSide() + " -----> " + this.getProductSide();
    }
    
    /**
     * Get the reactant formulas as FormulaSplitters
     * @return the reactant formulas as FormulaSplitters
     */
    public FormulaSplitter[] getReactantFormulas() {
        FormulaSplitter[] r = new FormulaSplitter[reactants.length];
        for (int i = 0; i < r.length; i++) {
            r[i] = reactants[i].getCompound();
        }
        return r;
    }
    
    /**
     * Get the product formulas as FormulaSplitters
     * @return the product formulas as FormulaSplitters
     */
    public FormulaSplitter[] getProductFormulas() {
        FormulaSplitter[] p = new FormulaSplitter[products.length];
        for (int i = 0; i < p.length; i++) {
            p[i] = products[i].getCompound();
        }
        return p;
    }
    
    // tester
    public static void main (String[] args) {
        String reactants = "2HCl + Ba(OH)2"; // check for nuber format exception on the end
        String products = "BaCl2 + 2H2O";
        
        BalancedEquation eq = new BalancedEquation(reactants,  products);
        System.out.println(eq.isBalanced());
    }
}
