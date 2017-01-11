package processes.wrappers;

/**
 *
 * @author Jared Cassarly
 */
public class Element {
    
    /**
     * Symbols of the elements on the periodic table in order of atomic numbers
     */
    public static final String[] SYMBOLS = {"H", "He", "Li", "Be", "B", "C", "N", "O", "F", "Ne", "Na", "Mg", "Al", "Si", "P", "S", "Cl",
                    "Ar", "K", "Ca", "Sc", "Ti", "V", "Cr", "Mn", "Fe", "Co", "Ni", "Cu", "Zn", "Ga", "Ge",
                    "As", "Se", "Br", "Kr", "Rb", "Sr", "Y", "Zr", "Nb", "Mo", "Tc", "Ru", "Rh", "Pd", "Ag",
                    "Cd", "In", "Sn", "Sb", "Te", "I", "Xe", "Cs", "Ba", "La", "Ce", "Pr", "Nd", "Pm", "Sm", "Eu",
                    "Gd", "Tb", "Dy", "Ho", "Er", "Tm", "Yb", "Lu", "Hf", "Ta", "W", "Re", "Os", "Ir", "Pt",
                    "Au", "Hg", "Tl", "Pb", "Bi", "Po", "At", "Rn", "Fr", "Ra", "Ac", "Th", "Pa", "U", "Np",
                    "Pu", "Am", "Cm", "Bk", "Cf", "Es", "Fm", "Md", "No", "Lr", "Rf", "Db", "Sg", "Bh", "Hs", "Mt"};
    
    /**
     * Masses of the elements on the periodic table corresponding to each element in SYMBOLS
     */
    public static final double[] MASSES = {1.008, 4.003, 6.941, 9.012, 10.811, 12.011, 14.007, 15.999, 18.998, 20.180, 22.990, 24.305, 
                        26.982, 28.086, 30.974, 32.066, 35.453, 39.948, 39.098, 40.078, 44.956, 47.867, 50.942,
                        51.996, 54.938, 55.845, 58.933, 58.693, 63.546, 65.390, 69.723, 72.610, 74.922, 78.960,
                        79.904, 83.800, 85.468, 87.620, 88.906, 91.224, 92.906, 95.940, 98.000, 101.07, 102.906, 
                        106.42, 107.868, 112.411, 114.818, 118.710, 121.760, 127.600, 126.904, 131.29, 132.905, 137.327,
                        138.906, 140.116, 140.908, 144.240, 145.000, 150.360, 151.964, 157.250, 158.925, 162.500, 
                        164.930, 167.260, 168.934, 173.040, 174.967, 178.49, 180.948, 183.840, 186.207, 190.230, 
                        192.217, 195.078, 196.967, 200.590, 204.383, 207.200, 208.980, 209.000, 210.000, 222.000, 
                        223.000, 226.000, 227.000, 232.036, 231.136, 238.029, 237.000, 244.000, 243.000, 247.000,
                        247.000, 251.000, 252.000, 257.000, 258.000, 259.000, 262.000, 261.000, 262.000, 263.000, 
                        262.000, 265.000, 266.000};
    
    /**
     * Names of the elements on the periodic table corresponding to each element in SYMBOLS
     */
    public static final String[] NAMES = {"Hydrogen", "Helium", "Lithium", "Beryllium", "Boron", "Carbon", "Nitrogen", "Oxygen", "Fluorine",
                        "Neon", "Sodium", "Magnesium", "Aluminum", "Silicon", "Phosphorus", "Sulfur", "Chlorine", "Argon", "Potassium", "Calcium",
                        "Scandium", "Titanium", "Vanadium", "Chromium", "Manganese", "Iron", "Cobalt", "Nickel", "Copper", "Zinc", "Gallium",
                        "Germanium", "Arsenic", "Selenium", "Bromine", "Krypton", "Rubidium", "Strontium", "Yttrium", "Zirconium", "Niobium",
                        "Molybdenum", "Technetium", "Ruthenium", "Rhodium", "Palladium", "Silver", "Cadmium", "Indium", "Tin", "Antimony", "Tellurium",
                        "Iodine", "Xenon", "Cesium", "Barium", "Lanthanum", "Cerium", "Praseodymium", "Neodymium", "Promethium", "Samarium",
                        "Europium", "Gadolinium", "Terbium", "Dysprosium", "Holmium", "Erbium", "Thulium", "Ytterbium", "Lutenium", "Hafnium",
                        "Tantalum", "Tungsten", "Rhenium", "Osmium", "Iridium", "Platinum", "Gold", "Mercury", "Thallium", "Lead", "Bismuth",
                        "Polonium", "Astatine", "Radon", "Francium", "Radium", "Actinium", "Thorium", "Protactinium", "Uranium", "Neptunium",
                        "Plutonium", "Americium", "Curium", "Berkelium", "Californium", "Einsteinium", "Fermium", "Mendelevium", "Nobelium",
                        "Lawrencium", "Rutherfordium", "Dubnium", "Scaborgium", "Bohrium", "Hassium", "Meitnerium"};
    
    /**
     * Atomic masses of the elements on the periodic table corresponding to each element in SYMBOLS
     */
    public static final int[] ATOMIC_NUMBER = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21,
                        22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45,
                        46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69,
                        70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93,
                        94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109};
    
    private String symbol;
    private int atomicNumber;
    private double atomicMass;
    
    /**
     * Creates a new element with symbol, number, and mass
     * @param symbol the elements symbol
     * @param atomicNumber the atomic number of the element
     * @param atomicMass the atomic mass of the element
     */
    public Element(String symbol, int atomicNumber, double atomicMass) {
        this.symbol = symbol;
        this.atomicNumber = atomicNumber;
        this.atomicMass = atomicMass;
    }
    
    /**
     * Returns the symbol of the element
     * @return the symbol of the element
     */
    public String getSymbol() {
        return symbol;
    }
    
    /**
     * Returns the atomic number of the element
     * @return the atomic number of the element
     */
    public int getAtomicNumber() {
        return atomicNumber;
    }
    
    /**
     * Returns the atomic mass of the element
     * @return the atomic mass of the element
     */
    public double getAtomicMass() {
        return atomicMass;
    }
}
