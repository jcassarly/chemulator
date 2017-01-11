package processes.chem;

import processes.wrappers.Element;

/**
 *
 * @author Jared Cassarly
 */
public class MassFinder extends FormulaSplitter{
    /**
     * The elements of the formula without any multiples
     */
    private String[] elements;
    
    /**
     * The number of each element in the formula added for totals
     * Corresponds to elements
     */
    private int[] numbers;
    
    /**
     * The molar mass of the given formula
     */
    private double molarMass;
    
    /**
     * Creates a new MassFinder with a specified formula to find the mass of.
     * @param f the inputted and optionally unformatted formula
     *      Formatted by Formatting.chemFormat(String f)
     */
    public MassFinder(String f) {
        super(f);
        
        elements = this.getElements();
        numbers = this.getNumbers();
            
        // calculates the molar mass of the compound
        for (int i = 0; i < elements.length; i++) {
            molarMass += getElementMass(elements[i]) * numbers[i];
        }
        
        
    }
    
    /**
     * Returns the molar mass of the formula
     * @return the molar mass of the formula
     */
    public double getMolarMass() { 
        return molarMass;
    }
    
    /**
     * Finds the percent composition of each element in the formula
     * Gives the total percentage of all occurrences of each element
     * For example:
     *      formula = NON
     *      return = % of both N and % of O, not % of N and % of O and % of N
     * @return The percent composition of each element
     */
    public double[] percentComposition() {
        double[] eMasses = this.getElementMasses();
        double[] percents = new double[eMasses.length];
        
        // calculate the percent composition for each element
        for (int i = 0; i < percents.length; i++) {
            // percentage of mass un the total molar mass
            percents[i] = 100 * (eMasses[i]/this.getMolarMass());
            // if divide by 0, set value to 0
            if (Double.isNaN(percents[i])) {
                percents[i] = 0;
            }
        }
        return percents;
    }
    
    /**
     * Returns the masses of each element, each mass is massed on the total mass of each individual element
     * @return the total mass of each element
     */
    public double[] getElementMasses() {
        double[] eMasses = new double[elements.length];
        for (int i = 0; i < eMasses.length; i++) {
            eMasses[i] = getElementMass(elements[i]) * numbers[i]; 
        }
        return eMasses;
    }
    
    /**
     * Finds the atomic mass of a given element
     * @param element the element symbol on the periodic table to search for
     * acceptable element symbols:
     *  H, He, Li, Be, B, C, N, O, F, Ne, Na, Mg, Al, Si, P, S, Cl,
     *  Ar, K, Ca, Sc, Ti, V, Cr, Mn, Fe, Co, Ni, Cu, Zn, Ga, Ge,
     *  As, Se, Br, Kr, Rb, Sr, Y, Zr, Nb, Mo, Tc, Ru, Rh, Pd, Ag,
     *  Cd, In, Sn, Sb, Te, I, Xe, Cs, Ba, La, Ce, Pr, Nd, Pm, Sm, Eu,
     *  Gd, Tb, Dy, Ho, Er, Tm, Yb, Lu, Hf, Ta, W, Re, Os, Ir, Pt,
     *  Au, Hg, Tl, Pb, Bi, Po, At, Rn, Fr, Ra, Ac, Th, Pa, U, Np,
     *  Pu, Am, Cm, Bk, Cf, Es, Fm, Md, No, Lr, Rf, Db, Sg, Bh, Hs, Mt
     * @return the atomic mass of the element
     * @throws UnsupportedOperationException if the given element is not an element
     */
    public double getElementMass(String element) throws UnsupportedOperationException {
        
        // create an array with all the element info
        Element[] elements = new Element[Element.SYMBOLS.length];
        for (int i = 0; i < elements.length; i++) {
            elements[i] = new Element(Element.SYMBOLS[i], i + 1, Element.MASSES[i]);
        }
        
        // calculate the mass of the element to the nearest thousandth
        for (int i = 0; i < elements.length; i++) {
            if (elements[i].getSymbol().equals(element)) {
                double rtn = elements[i].getAtomicMass();
                rtn = Math.round(rtn * 1000.0) / 1000.0;
                return rtn;
            }
        }
        throw new UnsupportedOperationException("Must be exact name of element (Case sensitive)");
    }
}
