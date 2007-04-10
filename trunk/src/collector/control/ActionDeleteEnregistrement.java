
package collector.control;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import org.apache.log4j.Logger;

import collector.data.Enregistrement;
import collector.data.Header;
import collector.data.Table;

/**
 * Action to delete an Enregistrement from a Table.
 *
 * @version 1.0
 * $Date: 2004/04/13$<br>
 * @author Alain$
 */

public class ActionDeleteEnregistrement extends AbstractAction 
{
    /** all other actions */
    Actions theActions;
    
    /** Creation */
    ActionDeleteEnregistrement( Actions p_actions )
    {
	super();
	logger = Logger.getLogger(Actions.class);
	
	// set Properties
	putValue( AbstractAction.NAME, new String( "Détruit Enr." ));
	putValue( AbstractAction.MNEMONIC_KEY, new Integer( KeyEvent.VK_D ) );
	putValue( AbstractAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke( KeyEvent.VK_D, ActionEvent.CTRL_MASK) );
	putValue( AbstractAction.SHORT_DESCRIPTION, new String( "Efface l'Enregistrement sélectionné." ));

	theActions = p_actions;
    }
    /** Action : print all information */
    public void actionPerformed( ActionEvent e )
    {
	Table theTable = theActions.theJBDD.getSelectedTable();
	Enregistrement editedEnregistrement = theActions.theJBDD.getSelectedEnregistrement();
	
	// check that defaultParentEnregistrement (i.e. index=0) is not deletable
	if ( (theTable == theActions.theJBDD.theBDD.parentTable) &&
	     (theTable.getIndex( editedEnregistrement ) == 0 )) {
	    // cannot do that
	    JOptionPane.showMessageDialog( theActions.theFrame,
					   "Must not delete this Element !",
					   "Deleting default Parent",
					   JOptionPane.WARNING_MESSAGE );
	}
	else {
	    
	    Table parentTable = (Table) theTable.getParent();
	    
	    logger.debug( "Table = " + theTable.getLabel() );
	    if( parentTable != null )
		logger.debug( "Parent = " + parentTable.getLabel() );
	    
	    Header theHeader = theTable.myHeader;
	    /* when related to completeTable, change to basicHeader */
	    if (theTable == theActions.theJBDD.theBDD.completeTable ) {
		theHeader = theActions.theJBDD.theBDD.basicTable.myHeader;
	    }
	    
	    int dialogAnswer 
		= JOptionPane.showConfirmDialog( theActions.theFrame,
						 "Ready to delete ?",
						 "Delete Enregistrement",
						 JOptionPane.YES_NO_OPTION,
						 JOptionPane.QUESTION_MESSAGE );
	    
	    // modified
	    if( dialogAnswer == JOptionPane.YES_OPTION ) {
		logger.debug( "delete : " + editedEnregistrement.toString() );
		theActions.theJBDD.theBDD.deleteEnregistrement( theTable, editedEnregistrement );
		theActions.flag_savedBDD = false;
	    }
	}
    }
    
    
    // ---------- a Private Logger ---------------------
    private Logger logger;
    // --------------------------------------------------
    
} // ActionDeleteEnregistrement









