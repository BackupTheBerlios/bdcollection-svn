
package collector.control;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.apache.log4j.Logger;

import collector.data.Enregistrement;
import collector.data.Table;

/**
 * Action to debug an Enregistrement from a Table by calling toString().
 *
 * @version 1.0
 * $Date: 2004/04/13$<br>
 * @author Alain$
 */

public class ActionDebugEnregistrement extends AbstractAction 
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /** all other actions */
    Actions theActions;
    
    /** Creation */
    ActionDebugEnregistrement( String p_name, Actions p_actions )
    {
        super( p_name );
        logger = Logger.getLogger(Actions.class);
        
        theActions = p_actions;
    }
    /** Action : print all information */
    public void actionPerformed( ActionEvent e )
    {
        Table theTable = theActions.theJBDD.getSelectedTable();
        Enregistrement editedEnregistrement = theActions.theJBDD.getSelectedEnregistrement();
        Table parentTable = (Table) theTable.getParent();
        
        logger.debug( "Table = " + theTable.getLabel() );
        if( parentTable != null )
            logger.debug( "Parent = " + parentTable.getLabel() );
        
        //Header theHeader = theTable.myHeader;
        /* when related to completeTable, change to basicHeader */
        if (theTable == theActions.theJBDD.theBDD.completeTable ) {
            
            //theHeader = theActions.theJBDD.theBDD.basicTable.myHeader;
        }
        logger.debug( "----- action : " + e.paramString());
        logger.debug( "----- DEBUG -----\n" + editedEnregistrement.toString() );
    }
    
    
    // ---------- a Private Logger ---------------------
    private Logger logger;
    // --------------------------------------------------
    
} // ActionDebugEnregistrement









