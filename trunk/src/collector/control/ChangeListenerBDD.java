
package collector.control;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.log4j.Logger;

/**
 * listen for change in the table selected in the BDD.
 */
public class ChangeListenerBDD implements ChangeListener
{
    /** Need to memorize Actions */
    Actions theActions;

    /** 
     * Creation.
     */
    public ChangeListenerBDD( Actions p_actions )
    { 
	logger = Logger.getLogger(ChangeListenerBDD.class);
	theActions = p_actions;
    }
    /**
     * When selection changes, update the status of the Actions.
     */
    public void stateChanged(ChangeEvent e) {
	    
	    logger.debug( "Tablechanged -> update Actions" );
	    theActions.updateStatus();
    }
    // ---------- a Private Logger ---------------------
    private Logger logger;
    // --------------------------------------------------
} // ChangeListenerBDD
