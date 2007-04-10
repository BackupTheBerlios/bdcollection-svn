
package collector.control;

import java.io.*;
import java.util.*;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import javax.swing.JPopupMenu;
import javax.swing.JMenuItem;
import javax.swing.Action;
import javax.swing.JFrame;

import collector.gui.*;
import collector.data.*;

/**
 * A generic PopUpMenu.
 *
 * @version 1.0
 * $Date: 2003/11/20$<br>
 * @author Alain$
 */

public class PopupMenuGeneric extends JPopupMenu
{

    /**
     * Creation
     */
    public PopupMenuGeneric( JFrame p_frame, JBaseDeDonnees p_jbdd ) 
    {
	super( "Generic Menu");
	logger = Logger.getLogger(PopupMenuGeneric.class);


	// A Title
	add( "Generic" );
	addSeparator();

	// Add actions
	Actions tmpActions = new Actions( p_frame, p_jbdd );

	JMenuItem tmpGenericItem = new JMenuItem( tmpActions.actionGeneric);
	add( tmpGenericItem );	

	pack();
    }

    // ---------- a Private Logger ---------------------
    private Logger logger;
    // --------------------------------------------------
} // PopupMenuGeneric
    








