/*
 * Created on Jan 4, 2007
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package collector.control;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import org.apache.log4j.Logger;

public class ActionQuit extends AbstractAction {

    /** all other actions */
    Actions theActions;

    /** Creation */
    ActionQuit( Actions p_actions )
    {
        super();
        logger = Logger.getLogger(Actions.class);

        //  set Properties
        putValue( AbstractAction.NAME, new String( "Quitter BDD" ));
        putValue( AbstractAction.MNEMONIC_KEY, new Integer( KeyEvent.VK_Q ) );
        putValue( AbstractAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke( KeyEvent.VK_Q, ActionEvent.CTRL_MASK) );
        putValue( AbstractAction.SHORT_DESCRIPTION, new String( "Quitter l'application." ));
        
        
        theActions = p_actions;
    }
    /**
     * Action : quit.
     */
    public void actionPerformed(ActionEvent e)
    {
        logger.info( "----< quit >------");
        
        // check that do not want to save BDD
        if( Actions.flag_savedBDD == false ) {
            int dialogAnswer 
            = JOptionPane.showConfirmDialog( theActions.theFrame,
                             "WARNING quitting whereas the DataBase not saved!\nWant to save it?",
                             "Last minute savings...",
                             JOptionPane.YES_NO_OPTION,
                             JOptionPane.QUESTION_MESSAGE );
            if( dialogAnswer == JOptionPane.YES_OPTION ) {
            // saving : maybe not very clean !!!
            theActions.actionSave.actionPerformed( e );
            }
        }
        System.exit(0);
    }
    
    //  ---------- a Private Logger ---------------------
    private Logger logger;
    // --------------------------------------------------
    
} // ActionQuit
