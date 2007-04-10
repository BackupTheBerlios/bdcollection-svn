
package collector.control;

import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import org.apache.log4j.Logger;

/**
 * A MenuBar for the BDD.
 *
 * @version 1.0
 * $Date: 2004/05/11$<br>
 * @author Alain$
 */

public class MenuBarBDD extends JMenuBar
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
    public MenuBarBDD( Actions p_actions) 
    {
	//super( "Table Menu");
	super();
	//logger = Logger.getLogger(MenuBarBDD.class);
    Logger.getLogger(MenuBarBDD.class);
    
	theActions = p_actions;

	// A Title
	//add( "Actions Table" );
	//addSeparator();

	// Add actions

	JMenu menu;

	// File Menu
	menu = new JMenu( "Fichier" );
	menu.setMnemonic(KeyEvent.VK_F);
	menu.getAccessibleContext().setAccessibleDescription("Gérer les Bases de Données.");
	this.add(menu);
	JMenuItem tmpLoadBDD= new JMenuItem( theActions.actionLoad );
	menu.add( tmpLoadBDD );
	JMenuItem tmpSaveBDD = new JMenuItem( theActions.actionSave );
	menu.add( tmpSaveBDD );
    JMenuItem tmpSaveASBDD = new JMenuItem( theActions.actionSaveAs );
    menu.add( tmpSaveASBDD );
    menu.addSeparator();
    JMenuItem tmpQuitBDD = new JMenuItem( theActions.actionQuit );
    menu.add( tmpQuitBDD );
	
	// Enregistrement
	menu = new JMenu( "Enregistrement" );
	menu.setMnemonic(KeyEvent.VK_E);
	menu.getAccessibleContext().setAccessibleDescription("Gérer les Enregistrements.");
	this.add(menu);
	// Add
	JMenuItem tmpAddItem = new JMenuItem( theActions.actionAddEnregistrement );
	menu.add( tmpAddItem );
	JMenuItem tmpAddItem1 = new JMenuItem( theActions.actionAddEnregistrementParent );
	menu.add( tmpAddItem1 );
	JMenuItem tmpAddItem2 = new JMenuItem( theActions.actionAddEnregistrementBasic );
	menu.add( tmpAddItem2 );
	menu.addSeparator();
	// On Selected Enregistrement
	itemEdit = new JMenuItem( theActions.actionEditEnregistrement );
	menu.add( itemEdit );
	itemDelete = new JMenuItem( theActions.actionDeleteEnregistrement );
	menu.add( itemDelete );
	
	// Table
	menu = new JMenu( "Table" );
	menu.setMnemonic(KeyEvent.VK_T);
	menu.getAccessibleContext().setAccessibleDescription("Operations sur une Table.");
	this.add(menu);
	// Search
	itemSearch = new JMenuItem( theActions.actionSearchTable );
	menu.add( itemSearch );
	JMenuItem tmpPrint = new JMenuItem( theActions.actionPrint );
	menu.add( tmpPrint );

	updateStatus();

    }

    /**
     * Set the status of menuItem.
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
} // MenuBarBDD
