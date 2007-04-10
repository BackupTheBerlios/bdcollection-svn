
package collector.control;

import java.io.*;
import java.util.*;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import javax.swing.JPopupMenu;
import javax.swing.JMenuItem;
import javax.swing.Action;
import javax.swing.JFrame;
import java.awt.Component;


import collector.data.*;
import collector.gui.*;

/**
 * A PopUpMenu for the BDD.
 *
 * @version 1.0
 * $Date: 2003/11/20$<br>
 * @author Alain$
 */

public class PopupMenuBDD extends JPopupMenu
{
    /** the entry menu for editing Enregistrement. */
    JMenuItem itemEdit;
    /** the entry menu for deleting Enregistrement. */
    JMenuItem itemDelete;
    /** the entry menu for debugging Enregistrement. */
    JMenuItem itemDebug;
    /** the entry menu for searching Table. */
    JMenuItem itemSearch;

    /** Need to memorize JBDD */
    JBaseDeDonnees myJBDD;

    /**
     * Creation
     */
    public PopupMenuBDD( JFrame p_frame, JBaseDeDonnees p_jbdd ) 
    {
	//super( "Table Menu");
	super();
	logger = Logger.getLogger(PopupMenuBDD.class);

	myJBDD = p_jbdd;

	// A Title
	add( "Actions Table" );
	addSeparator();

	// Add actions

	// Todo : check that not done twice (in PopupMenuBDD for example).
	Actions tmpActions = new Actions( p_frame, p_jbdd );

	// a reusable separator
	JSeparator itemSeparator = new JSeparator();

	// Search
	itemSearch = new JMenuItem( tmpActions.actionSearchTable );
	add( itemSearch );
	add( itemSeparator );
	// Add
	JMenuItem tmpAddItem = new JMenuItem( tmpActions.actionAddEnregistrement );
	add( tmpAddItem );
	JMenuItem tmpAddItem1 = new JMenuItem( tmpActions.actionAddEnregistrementParent );
	add( tmpAddItem1 );
	JMenuItem tmpAddItem2 = new JMenuItem( tmpActions.actionAddEnregistrementBasic );
	add( tmpAddItem2 );
	add( itemSeparator );
	// On Selected Enregistrement
	itemEdit = new JMenuItem( tmpActions.actionEditEnregistrement );
	add( itemEdit );
	itemDelete = new JMenuItem( tmpActions.actionDeleteEnregistrement );
	add( itemDelete );
	add( itemSeparator );
	// On the BDD
	JMenuItem tmpLoadBDD = new JMenuItem( tmpActions.actionLoad );
	add( tmpLoadBDD );
	JMenuItem tmpSaveBDD = new JMenuItem( tmpActions.actionSave );
	add( tmpSaveBDD );

	pack();
    }

    /**
     * Set the status of menuItem.
     *
     * <li>itemEdit iff a selectedEnregistrement.</li>
     */
    public void updateStatus()
    {
	if( myJBDD.getSelectedEnregistrement() != null ) {
	    itemEdit.setEnabled( true );
	    itemDelete.setEnabled( true );
	    itemDebug.setEnabled( true );
	}
	else {
	    itemEdit.setEnabled( false );
	    itemDelete.setEnabled( false );
	    itemDebug.setEnabled( false );

	}
    }

    // ---------- a Private Logger ---------------------
    private Logger logger;
    // --------------------------------------------------
} // PopupMenuBDD
    








