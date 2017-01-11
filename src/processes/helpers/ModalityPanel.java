package processes.helpers;

import java.awt.Dialog;
import java.awt.Dimension;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import ui.infosheets.CommonElements;

/**
 *
 * @author Jared Cassarly
 */
public class ModalityPanel extends JDialog {
    
    /**
     * Open a new ModalityPanel of the EnterNumber form
     * @param owner the CommonElements Object that created the form
     */
    public ModalityPanel(CommonElements owner) {
        super(owner, "Enter Number of Items to Add", Dialog.ModalityType.DOCUMENT_MODAL);
        // add the EnterNumber form
        this.add(new EnterNumber(owner, this));
        // open the window
        pack();
        this.setVisible(true);
    }
    
    /**
     * Open a new ModalityPanel of the NewCompoundForm form
     * @param owner the CommonElements Object that created the form
     * @param numToAdd the number of NewCompounds to add
     */
    public ModalityPanel(CommonElements owner, int numToAdd) {
        super(owner, "Enter Number of Items to Add", Dialog.ModalityType.DOCUMENT_MODAL);
        // initialize the NewCompoundForm
        NewCompoundForm form = new NewCompoundForm(numToAdd, this);
        
        // Create a scrollbar and set the size of the window if there are more than 10 NewCompounds to add
        JScrollPane viewer = new JScrollPane();
        viewer.setViewportView(form);
        if (numToAdd > 10) {
            viewer.getViewport().setPreferredSize(new Dimension(275, 425));
        }
        this.add(viewer);
        
        // open the window
        pack();
        this.setVisible(true);
    }
}
