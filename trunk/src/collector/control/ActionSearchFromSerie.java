package collector.control;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.apache.log4j.Logger;

import collector.data.Enregistrement;
import collector.data.Field;
import collector.data.SearchOperator;
import collector.data.SearchedElement;
import collector.data.Table;
import collector.data.View;

/**
 * Action that pops-up a DialogSearch and eventually search a Table.
 *
 * @version 1.0
 * $Date: 2004/05/04$<br>
 * @author Alain$
 */

public class ActionSearchFromSerie extends AbstractAction 
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    /** SPECIFIC : index of Serie */
    final int indexSerie = 0;
    
    /** all other actions */
    Actions theActions;
    
    /**
     * Creation.
     */
    ActionSearchFromSerie(Actions p_actions )
    {
        super();
        logger = Logger.getLogger(Actions.class);
        
        // set Properties
        putValue( AbstractAction.NAME, new String( "Rech. Serie." ));
        //putValue( AbstractAction.MNEMONIC_KEY, new Integer( KeyEvent.VK_R ) );
        //putValue( AbstractAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke( KeyEvent.VK_R, ActionEvent.CTRL_MASK) );
        putValue( AbstractAction.SHORT_DESCRIPTION, new String( "Recherche tous les Enregistrements de cette série." ));
        
        theActions = p_actions;
    }
    
    /**
     * Action : Pop-up DialogSearch.
     */
    public void actionPerformed( ActionEvent e )
    {
        logger.debug( "Search Serie ");
        Table theTable = theActions.theJBDD.theBDD.completeTable;
        Enregistrement selectedEnregistrement = theActions.theJBDD.getSelectedEnregistrement();
        
        // Create the SearchPattern
        String nameSerie = selectedEnregistrement.data[indexSerie].displayData();
        Field fieldSearched = theTable.myHeader.getField(indexSerie);
        SearchedElement searchedSerie = new SearchedElement( nameSerie, fieldSearched, SearchedElement.SIMILAR); 
        SearchOperator searchEngine = new SearchOperator();
        searchEngine.addSearchItem( searchedSerie );
        View theResult = searchEngine.applyTo( theTable );
        
        if( theResult != null ) {
            logger.debug( theResult.displayData() );
            theActions.theJBDD.addView( theResult, theActions.actionDeleteView );
        }
    }
    
    // ---------- a Private Logger ---------------------
    private Logger logger;
    // --------------------------------------------------
    
} // ActionSearchTable
