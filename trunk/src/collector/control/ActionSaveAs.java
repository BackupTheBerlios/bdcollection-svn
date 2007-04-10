
package collector.control;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;

import org.apache.log4j.Logger;

/**
 * ActionSave that saves the whole DataBase.
 *
 * @version 1.0
 * $Date: 2004/02/10$<br>
 * @author Alain$
 */

public class ActionSaveAs extends AbstractAction 
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /** all other actions */
    Actions theActions;

    /** Creation */
    ActionSaveAs( Actions p_actions )
    {
	super();
	logger = Logger.getLogger(Actions.class);
	
	// set Properties
	putValue( AbstractAction.NAME, new String( "Renomer BDD" ));
	putValue( AbstractAction.MNEMONIC_KEY, new Integer( KeyEvent.VK_R ) );
	putValue( AbstractAction.SHORT_DESCRIPTION, new String( "Renomme la Base de Donnée actuelle dans un nouveau Fichier." ));

	theActions = p_actions;
    }
    /** Action : save BDD */
    public void actionPerformed( ActionEvent e )
    {
	logger.info("Save Database");
	

	// create a File Choser
    JFileChooser aFileChooser = new JFileChooser( theActions.theJBDD.theBDD.fileDesc );
	DatabaseFilter myDatabaseFilter = new DatabaseFilter();

	// set the right Filter
	aFileChooser.addChoosableFileFilter( myDatabaseFilter );
	aFileChooser.setFileFilter( myDatabaseFilter );
	
	// ask the user for a file
	int returnVal = aFileChooser.showSaveDialog( theActions.theFrame );

	// remove the File Filter
	aFileChooser.removeChoosableFileFilter( myDatabaseFilter );
	
	logger.debug("Choice made ");
	// check it was a valide choice
	if (returnVal == JFileChooser.APPROVE_OPTION) {
	    File file = aFileChooser.getSelectedFile();

	    try {
		theActions.theJBDD.theBDD.write( file );
		Actions.flag_savedBDD = true;
	    }
	    catch (Exception except) {
		logger.error("Save Database exception: " + except.getMessage());
	    }
	}
    }


    /**
     * Class needed in order to filter files for the JFileChoser.
     *
     * <p>
     * File recognised as Database: dir, '.dta'.
     */
    class DatabaseFilter extends javax.swing.filechooser.FileFilter {
	
	/**
	 * Filter files.
	 *
	 * Return true for directories and '.dta' files
	 */ 
	public boolean accept(File f) {
	    if (f.isDirectory()) {
		return true;
	    }
	    
	    String extension = Utils.getExtension(f);
	    if (extension != null) {
		if (extension.equals( Utils.dta ) ) {
                    return true;
		} else {
		    return false;
		}
	    }
	    
	    return false;
	}
	
	/**
	 * The description of this filter.
	 */
	public String getDescription() {
	    return "Database Files *.dta";
	}
    }
    // ---------- a Private Logger ---------------------
    private Logger logger;
    // --------------------------------------------------

} // ActionSave
    








