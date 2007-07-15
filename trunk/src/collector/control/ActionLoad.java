
package collector.control;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import org.apache.log4j.Logger;

import collector.data.BaseDeDonnees;

/**
 * ActionLoad that loads a DataBase.
 *
 * @version 1.0
 * $Date: 2004/04/15$<br>
 * @author Alain$
 */

public class ActionLoad extends AbstractAction 
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    /** all other actions */
    Actions theActions;

    /** Creation */
    ActionLoad( Actions p_actions )
    {
	super();
	logger = Logger.getLogger(Actions.class);
	
	// set Properties
	putValue( AbstractAction.NAME, new String( "Ouvrir BDD" ));
	putValue( AbstractAction.MNEMONIC_KEY, new Integer( KeyEvent.VK_O ) );
	putValue( AbstractAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke( KeyEvent.VK_O, ActionEvent.CTRL_MASK) );
	putValue( AbstractAction.SHORT_DESCRIPTION, new String( "Charge une nouvelle Base de Donn�e en m�moire." ));
	
	theActions = p_actions;
    }
    /** Action : load BDD */
    public void actionPerformed( ActionEvent e )
    {
	logger.info("Load Database");
	
	// check that do not want to save BDD
	if( Actions.flag_savedBDD == false ) {
	    int dialogAnswer 
		= JOptionPane.showConfirmDialog( theActions.theFrame,
						 "DataBase not saved. Want to save it?",
						 "Last minute savings...",
						 JOptionPane.YES_NO_OPTION,
						 JOptionPane.QUESTION_MESSAGE );
	    if( dialogAnswer == JOptionPane.YES_OPTION ) {
		// saving : maybe not very clean !!!
		theActions.actionSave.actionPerformed( e );
	    }
	}

	// create a File Choser
	JFileChooser aFileChooser;
	if( theActions.theJBDD.theBDD != null ) {
		aFileChooser = new JFileChooser( theActions.theJBDD.theBDD.fileDesc );
	}
	else {
		aFileChooser = new JFileChooser();
	}
	DatabaseFilter myDatabaseFilter = new DatabaseFilter();

	// set the right Filter
	aFileChooser.addChoosableFileFilter( myDatabaseFilter );
	aFileChooser.setFileFilter( myDatabaseFilter );
	
	// ask the user for a file
	int returnVal = aFileChooser.showOpenDialog( theActions.theFrame );

	// remove the File Filter
	aFileChooser.removeChoosableFileFilter( myDatabaseFilter );
	
	logger.debug("Choice made ");
	// check it was a valide choice
	if (returnVal == JFileChooser.APPROVE_OPTION) {
	    File file = aFileChooser.getSelectedFile();

	    try {
	    	logger.debug( "Loading : " + file.getAbsolutePath());
	    	if( theActions.theJBDD.theBDD == null) 
	    		theActions.theJBDD.theBDD = new BaseDeDonnees();
		theActions.theJBDD.theBDD.readFromFile( file.getAbsolutePath() );
		theActions.theFrame.getContentPane().removeAll();
		// must also add again the MouseListener
		logger.debug("--------------------------------\n" + theActions.theJBDD.theBDD.toString() + "------------------------------------" );
	        theActions.theJBDD.rebuild();
		
		theActions.theJBDD.setMouseListener( new MouseListenerBDD( theActions) );
		theActions.theJBDD.setSelectionListener( new SelectionListenerBDD( theActions ) );
		theActions.theJBDD.addChangeListener( new ChangeListenerBDD( theActions ));
		Actions.flag_savedBDD = true;
	    }
	    catch (Exception except) {
		logger.error("Load Database exception: " + except.getMessage());
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

} // ActionLoad
    








