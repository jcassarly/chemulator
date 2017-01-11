package processes.helpers;

import java.util.ArrayList;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


/**
 *
 * @author Jared Cassarly
 */
public class NewCompoundForm extends JPanel{
    
    final private static int DEFAULT_SIZE = 8;
    
    private NewCompound[] nc;
    private JButton confirm;
    private NewCompoundHeader header;
    private JScrollPane jsp;
    
    /**
     * Creates a NewCompoundForm with a specified number of NewCompound fields
     * @param numToAdd the number of NewCompounds to add
     * @param owner the ModalityPanel that created the form
     */
    public NewCompoundForm(int numToAdd, ModalityPanel owner) {
        // create the header for the form
        header = new NewCompoundHeader();
        // create a scrollbar
        jsp = new JScrollPane();
        
        // make the new compounds needed
        nc = new NewCompound[numToAdd];
        for (int i = 0; i < numToAdd; i++) {
            nc[i] = new NewCompound();
        }
        
        // create the confirm button for the user to press when done entering compounds
        confirm = new JButton();
        confirm.setText("Confirm");
        confirm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confirmActionPerformed(evt);
            }
        });
        
        // set up the layout of the form
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        
        // Create the horizontal group
        GroupLayout.ParallelGroup hpGroup = layout.createParallelGroup(GroupLayout.Alignment.LEADING);
        // add the header
        hpGroup.addComponent(header, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE);
        // add each NewCompound
        for (int i = 0; i < nc.length; i++) {
            hpGroup.addComponent(nc[i], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE);
        }
        // add the confirm button
        hpGroup.addComponent(confirm, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE);
        // add the horizontal group to the layout
        layout.setHorizontalGroup(hpGroup);
        
        // Create the vertical group
        GroupLayout.ParallelGroup pGroup = layout.createParallelGroup(GroupLayout.Alignment.LEADING);
        GroupLayout.SequentialGroup pPGroup = layout.createSequentialGroup();
        // add the header
        pPGroup.addComponent(header, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE);
        pPGroup.addGap(DEFAULT_SIZE);
        // add the NewCompounds
        for (int i = 0; i < nc.length; i++) {
            pPGroup.addComponent(nc[i], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE);
            pPGroup.addGap(DEFAULT_SIZE);
        }
        // add the corfirm button
        pPGroup.addComponent(confirm, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE);
        // add the vertical group to the layout
        pGroup.addGroup(pPGroup);
        layout.setVerticalGroup(pGroup);
        
    }
    
    /**
     * When the confirm button is clicked, add the new compounds to the common elements file
     * @param evt the click event
     */
    private void confirmActionPerformed(java.awt.event.ActionEvent evt) {        
        // if the length is 1 add the sole compound to the common names sheet
        if (nc.length == 1) {
            if (!nc[0].getFormula().equals("")) {
                CommonNamesWriter.add(nc[0].getCompoundName(), nc[0].getFormula(), nc[0].getCharge());
            }
        }
        // else add the compounds as arrays
        else {
            // create arrays for the names, formulas, cand charges of the compounds
            ArrayList<String> names = new ArrayList<>();
            ArrayList<String> formulas = new ArrayList<>();
            ArrayList<String> charges = new ArrayList<>();
            
            // add the compound to the array of something was entered in the formula
            for (int i = 0; i < nc.length; i++) {
                if (!nc[i].getFormula().equals("")) {
                    names.add(nc[i].getCompoundName());
                    formulas.add(nc[i].getFormula());
                    charges.add(nc[i].getCharge());
                }
            }
            
            // copy used elements into new arrays of their size and add them tot eh common names sheet
            CommonNamesWriter.add(names.toArray(new String[names.size()]), formulas.toArray(new String[formulas.size()]), charges.toArray(new String[charges.size()]));
            
        }
    }    
}
