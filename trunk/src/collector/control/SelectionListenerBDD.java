
package collector.control;

import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.log4j.Logger;

/**
 * listen Selection events, normally on the Table of a BDD.
 */
public class SelectionListenerBDD implements ListSelectionListener
{
    /** Need to memorize Actions */
    Actions theActions;

    /** 
     * Creation.
     */
    public SelectionListenerBDD( Actions p_actions )
    { 
	logger = Logger.getLogger(SelectionListenerBDD.class);
	theActions = p_actions;
    }
    /**
     * When selection changes, update the status of the Actions.
     */
    public void valueChanged(ListSelectionEvent e) {
	    //Ignore extra messages and wait for end of change
	    if (e.getValueIsAdjusting()) return;
	    
	    logger.debug( "Selection changed -> update Actions" );
	    ListSelectionModel lsm =
		(ListSelectionModel)e.getSource();
	    if (lsm.isSelectionEmpty()) {
		//no rows are selected
		logger.debug("Selection: none");
		theActions.updateStatus( false );
	    } else {
		int selectedRow = lsm.getMinSelectionIndex();
		//selectedRow is selected
		logger.debug("Selection of row " + selectedRow );
		theActions.updateStatus( true );
		// selectedEnregistrement.display();
	    }
    }
    // ---------- a Private Logger ---------------------
    private Logger logger;
    // --------------------------------------------------
} // SelectionListenerBDD
