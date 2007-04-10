package collector.control;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;

import org.apache.log4j.Logger;

import collector.data.Table;
import collector.data.View;

/**
 * Action that pops-up a DialogSearch and eventually search a Table.
 *
 * @version 1.0
 * $Date: 2004/05/04$<br>
 * @author Alain$
 */

public class ActionSearchTable extends AbstractAction 
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
    ActionSearchTable(Actions p_actions )
    {
        super();
        logger = Logger.getLogger(Actions.class);
        
        // set Properties
        putValue( AbstractAction.NAME, new String( "Recherche Enr." ));
        putValue( AbstractAction.MNEMONIC_KEY, new Integer( KeyEvent.VK_R ) );
        putValue( AbstractAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke( KeyEvent.VK_R, ActionEvent.CTRL_MASK) );
        putValue( AbstractAction.SHORT_DESCRIPTION, new String( "Recherche les Enregistrement à l'aide de chaînes de caractères." ));
        
        theActions = p_actions;
    }
    
    /**
     * Action : Pop-up DialogSearch.
     */
    public void actionPerformed( ActionEvent e )
    {
        Table theTable = theActions.theJBDD.getSelectedTable();
        logger.debug( "Search Table = " + theTable.getLabel() );
        
        DialogSearch theDialogSearch = new DialogSearch( theActions.theFrame,
                theTable );
        
        
        View theResult = theDialogSearch.getResult();
        if( theResult != null ) {
            logger.debug( theResult.displayData() );
            theActions.theJBDD.addView( theResult, theActions.actionDeleteView );
        }
    }
    
    // ---------- a Private Logger ---------------------
    private Logger logger;
    // --------------------------------------------------
    
} // ActionSearchTable
