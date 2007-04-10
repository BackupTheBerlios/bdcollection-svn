
package collector.control;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;

import org.apache.log4j.Logger;

import collector.data.CellInt;
import collector.data.CellStr;
import collector.data.Enregistrement;
import collector.data.View;

/**
 * Action to add an Enregistrement to the parentTable of BDD.
 *
 * @version 1.0
 * $Date: 2004/04/12$<br>
 * @author Alain$
 */

public class ActionAddEnregistrementParent extends AbstractAction 
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /** all other actions */
    Actions theActions;
    
    /** Creation */
    ActionAddEnregistrementParent( Actions p_actions )
    {
        super();
        logger = Logger.getLogger(Actions.class);
        
//      set Properties
        putValue( AbstractAction.NAME, new String( "Ajouter Série" ));
        putValue( AbstractAction.MNEMONIC_KEY, new Integer( KeyEvent.VK_S ) );
        putValue( AbstractAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke( KeyEvent.VK_N, ActionEvent.CTRL_MASK) );
        putValue( AbstractAction.SHORT_DESCRIPTION, new String( "Ajouter une nouvelle Série à la Base de Données." ));
        
        theActions = p_actions;
    }
    /** Action : print all information */
    public void actionPerformed( ActionEvent e )
    {
        logger.debug( "Trying to add to parentTable" );
        
        // need to ask for a new Enregistrement
        Enregistrement newEnr = null;
        DialogEditEnregistrement dialog 
        = new DialogEditEnregistrement( theActions.theFrame, 
                null /* no parent vs theJBDD.theBDD.parentTable */, 
                theActions.theJBDD.theBDD.parentTable.myHeader,
                null /* not editing */,
                "Nouvel Enregistrement",
                true /* check enregistrement */,
                false /* no 'next' button */);
        
        // added to the view
        boolean stopAsking = false;
        while( (stopAsking == false) && (dialog.askNew(null) == true)  ) {
            boolean adding = true;
            // get the new Enregistrement
            newEnr = dialog.getEnregistrement();
            
            if( dialog.isCheck() ) {
                logger.debug( "checking : " + newEnr.displayData() );
                View close = theActions.theJBDD.theBDD.parentTable.getClosest( newEnr );
                logger.debug( close.displaySortedConfidenceData() );
                
                // Another enregistrement, at least, is close?
                if( close.listEnregistrement.size() > 0 ) {
                    DialogCheckEnregistrement dialogCheck = 
                        new DialogCheckEnregistrement( theActions.theFrame,
                                newEnr,
                                close,
                        "No error ??");
                    if( dialogCheck.askOk() != true ) {
                        adding = false;
                    }
                    // else we could do something if too close
                }
            }
            if( adding == true ) {
                logger.debug( "adding : " + newEnr.toString() );
                theActions.theJBDD.theBDD.addEnregistrement( theActions.theJBDD.theBDD.parentTable, newEnr );
                Actions.flag_savedBDD = false;
            }
            if( dialog.isOk() ) {
                stopAsking = true;
            }
            if( dialog.isNext() ) {
                // clean up a bit the Enregistrement
                // WARNING : specific to Bandes Dessinees...
                // WARNING : should call specific function maybe ??
                // data[0] is the volume number
                CellInt tmpData0 = (CellInt) newEnr.data[0];
                newEnr.data[0] = new CellInt( tmpData0.myField);
                CellStr tmpData1 = (CellStr) newEnr.data[1];
                newEnr.data[1] = new CellStr( tmpData1.myField);
            }
        }
    }
    
    // ---------- a Private Logger ---------------------
    private Logger logger;
    // --------------------------------------------------
    
} // ActionAddEnregistrementParent









