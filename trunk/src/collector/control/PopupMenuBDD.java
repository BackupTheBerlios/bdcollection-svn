
package collector.control;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 * A PopUpMenu for the BDD.
 *
 * @version 1.0
 * $Date: 2003/11/20$<br>
 * @author Alain$
 */

public class PopupMenuBDD extends JPopupMenu
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    /** the entry menu for editing Enregistrement. */
    JMenuItem itemEdit;
    /** the entry menu for deleting Enregistrement. */
    JMenuItem itemDelete;
    /** the entry menu for debugging Enregistrement. */
    JMenuItem itemDebug;
    /** the entry menu for searching Table. */
    JMenuItem itemSearch;
    
    /** Need to memorize Actions */
    Actions theActions;
    
    /**
     * Creation
     */
    public PopupMenuBDD( Actions p_actions ) 
    {
        //super( "Table Menu");
        super();
        //logger = Logger.getLogger(PopupMenuBDD.class);
        
        // A Title
        //add( "Actions Table" );
        //addSeparator();
        
        // Add actions
        theActions = p_actions;
        
        // Generic 
        JMenuItem tmpGeneric = new JMenuItem( theActions.actionGeneric );
        add( tmpGeneric );
        addSeparator();
        
        // Sort
        JMenuItem tmpSortBDItem = new JMenuItem( theActions.actionSortBD );
        add( tmpSortBDItem );
        addSeparator();
        
        // Search
        itemSearch = new JMenuItem( theActions.actionSearchTable );
        add( itemSearch );
        JMenuItem tmpSearchSerieItem = new JMenuItem( theActions.actionSearchFromSerie );
        add( tmpSearchSerieItem );
        addSeparator();
        // Add
        JMenuItem tmpAddItem = new JMenuItem( theActions.actionAddEnregistrement );
        add( tmpAddItem );
        JMenuItem tmpAddItem1 = new JMenuItem( theActions.actionAddEnregistrementParent );
        add( tmpAddItem1 );
        JMenuItem tmpAddItem2 = new JMenuItem( theActions.actionAddEnregistrementBasic );
        add( tmpAddItem2 );
        addSeparator();
        // On Selected Enregistrement
        itemEdit = new JMenuItem( theActions.actionEditEnregistrement );
        add( itemEdit );
        itemDelete = new JMenuItem( theActions.actionDeleteEnregistrement );
        add( itemDelete );
        addSeparator();
        // Print
        JMenuItem tmpPrint = new JMenuItem( theActions.actionPrint );
        add( tmpPrint );
        addSeparator();
        // On the BDD
        JMenuItem tmpLoadBDD = new JMenuItem( theActions.actionLoad );
        add( tmpLoadBDD );
        JMenuItem tmpSaveBDD = new JMenuItem( theActions.actionSave );
        add( tmpSaveBDD );
        JMenuItem tmpSaveASBDD = new JMenuItem( theActions.actionSaveAs );
        add( tmpSaveASBDD );
        addSeparator();
        JMenuItem tmpQuitBDD = new JMenuItem( theActions.actionQuit );
        add( tmpQuitBDD );
        
        pack();
    }
    
    /**
     * Set the status of menuItems.
     *
     * <li>itemEdit iff a selectedEnregistrement.</li>
     */
    public void updateStatus()
    {
        /*if( myJBDD.getSelectedEnregistrement() != null ) {
	    itemEdit.setEnabled( true );
	    itemDelete.setEnabled( true );
	}
	else {
	    itemEdit.setEnabled( false );
	    itemDelete.setEnabled( false );
	    }*/
        theActions.updateStatus();
    }
    
    // ---------- a Private Logger ---------------------
    //private Logger logger;
    // --------------------------------------------------
} // PopupMenuBDD









