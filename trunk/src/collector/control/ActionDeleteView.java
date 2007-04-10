package collector.control;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;

import org.apache.log4j.Logger;

import collector.data.View;

/**
 * Action that delete a View from the JBaseDeDonnees.
 *
 * @version 1.0
 * $Date: 2004/05/04$<br>
 * @author Alain$
 */

public class ActionDeleteView extends AbstractAction 
{
    /** all other actions */
    Actions theActions;
    
    /**
     * Creation.
     */
    ActionDeleteView(Actions p_actions )
    {
	super();
	logger = Logger.getLogger(Actions.class);

	// set Properties
	putValue( AbstractAction.NAME, new String( "Détruit Vue" ));
	putValue( AbstractAction.MNEMONIC_KEY, new Integer( KeyEvent.VK_V ) );
	putValue( AbstractAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke( KeyEvent.VK_K, ActionEvent.CTRL_MASK) );
	putValue( AbstractAction.SHORT_DESCRIPTION, new String( "Efface la Vue sélectionnée de la mémoire." ));

	theActions = p_actions;
    }

    /**
     * Action : Delete the View from the JBDD
     */
    public void actionPerformed( ActionEvent e )
    {
	View theView = theActions.theJBDD.getSelectedView();
	if( theView != null ) {
	    logger.debug( "Deleting " + theView.getLabel() );

	   theActions.theJBDD.deleteSelectedView();
	}
    }

    // ---------- a Private Logger ---------------------
    private Logger logger;
    // --------------------------------------------------

} // ActionDeleteView
