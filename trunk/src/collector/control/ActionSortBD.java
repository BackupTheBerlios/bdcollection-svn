package collector.control;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;

import org.apache.log4j.Logger;

import collector.data.BandeDessineeComparator;
import collector.data.Table;

/**
 * Action that sort a Table which is a collection of 'Seri'+'Vol'+'Title'.
 * VERY SPECIFIC.
 *
 * @version 1.0
 * $Date: 2004/05/04$<br>
 * @author Alain$
 */

public class ActionSortBD extends AbstractAction 
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /** all other actions */
    Actions theActions;
    
    /**
     * Creation.
     */
    ActionSortBD(Actions p_actions )
    {
        super();
        logger = Logger.getLogger(Actions.class);
        
        // set Properties
        putValue( AbstractAction.NAME, new String( "Trier par série." ));
        putValue( AbstractAction.MNEMONIC_KEY, new Integer( KeyEvent.VK_T ) );
        putValue( AbstractAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke( KeyEvent.VK_T, ActionEvent.CTRL_MASK) );
        putValue( AbstractAction.SHORT_DESCRIPTION, new String( "Trier par Série, Volume et Titre." ));
        
        theActions = p_actions;
    }
    
    /**
     * Action : Sort the Table.
     */
    public void actionPerformed( ActionEvent e )
    {
        //Table theTable = theActions.theJBDD.theBDD.completeTable;
        Table theTable = theActions.theJBDD.getSelectedTable();
        if( theTable.getParent() != null ) {
            logger.debug( "Sort BD = " + theTable.getLabel() );
            
        
            theTable.sort( new BandeDessineeComparator( true /* ascending */));
        }
    }
    
    // ---------- a Private Logger ---------------------
    private Logger logger;
    // --------------------------------------------------
    
} // ActionSearchTable
