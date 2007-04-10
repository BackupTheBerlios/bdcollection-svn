
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

import javax.swing.KeyStroke;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;

import collector.data.*;
import collector.gui.*;

/**
 * A PopUpMenu for the JBDD.
 *
 * @version 1.0
 * $Date: 2003/11/20$<br>
 * @author Alain$
 */

public class PopupMenuBDD extends JPopupMenu
{
    /** the entry menu for editing Enregistrement. */
    JMenuItem itemEdit;
    
    /** Need to memorize JBDD */
    JBaseDeDonnees myJBDD;

    /**
     * Creation
     */
    public PopupMenuBDD( JFrame p_frame, JBaseDeDonnees p_jbdd ) 
    {
	super( "BDD Menu");
	logger = Logger.getLogger(PopupMenuBDD.class);

	myJBDD = p_jbdd;

	// A Title
	add( " -- BDD -- " );
	addSeparator();

	// Add actions

	Actions tmpActions = new Actions( p_frame, p_jbdd );

	JMenuItem tmpGenericItem = new JMenuItem( tmpActions.actionGeneric);
	add( tmpGenericItem );	
	JMenuItem tmpAddItem = new JMenuItem( tmpActions.actionAddEnregistrementParent );
	add( tmpAddItem );
	JMenuItem tmpAddItem2 = new JMenuItem( tmpActions.actionAddEnregistrementBasic );
	add( tmpAddItem2 );
	//itemEdit = new JMenuItem( tmpActions.actionEditEnregistrement );
	//add( itemEdit );
	JMenuItem tmpLoadBDD = new JMenuItem( tmpActions.actionLoad );
	add( tmpLoadBDD );
	JMenuItem tmpSaveBDD = new JMenuItem( tmpActions.actionSave );
	//tmpSaveBDD.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_S, 
	//						   ActionEvent.CTRL_MASK));
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
	}
	else {
	    itemEdit.setEnabled( false );
	}
    }

    // ---------- a Private Logger ---------------------
    private Logger logger;
    // --------------------------------------------------
} // PopupMenuBDD
    








