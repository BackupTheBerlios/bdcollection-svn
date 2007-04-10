
package collector.control;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.apache.log4j.Logger;

/**
 * ActionGeneric that can be done on any Element.
 * Print its information.
 *
 * @version 1.0
 * $Date: 2003/11/20$<br>
 * @author Alain$
 */

public class ActionGeneric extends AbstractAction 
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /** all other actions */
    Actions theActions;
    
    /** Creation */
    ActionGeneric( String p_name, Actions p_actions )
    {
        super( p_name );
        logger = Logger.getLogger(Actions.class);
        
        theActions = p_actions;
    }
    /** Action : print all information */
    public void actionPerformed( ActionEvent e )
    {
        logger.debug( "----< generic >------");
        logger.debug( "generic: Event = ------------\n"
                + e.toString() 
                + "-----------------------------" );
        logger.debug( "generic: Source = -----------\n" 
                + e.getSource().toString() 
                + "-----------------------------");
        logger.debug( "generic: ActionCommand = " + e.getActionCommand() );
        logger.debug( "generic: Modifier = " + e.getModifiers() );
        logger.debug( "generic: paramString = " + e.paramString() );
        logger.debug( "generic: flag_savedBDD = " + Actions.flag_savedBDD );
    }
    
    // ---------- a Private Logger ---------------------
    private Logger logger;
    // --------------------------------------------------
    
} // ActionGeneric









