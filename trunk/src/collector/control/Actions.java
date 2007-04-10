
package collector.control;

import javax.swing.AbstractAction;
import javax.swing.JFrame;

import org.apache.log4j.Logger;

import collector.gui.JBaseDeDonnees;

/**
 * Actions that can be done on the BDD.
 *
 * @version 1.0
 * $Date: 2003/11/20$<br>
 * @author Alain$
 */

public class Actions 
{
    /** a GenericAction for debugging */
    public AbstractAction actionGeneric;
    /** adding a new Enregistrement to the BDD */
    public AbstractAction actionAddEnregistrement;
    /** adding a new Enregistrement to the parentTable of the BDD */
    public AbstractAction actionAddEnregistrementParent;
    /** adding a new Enregistrement to the basicTable of the BDD */
    public AbstractAction actionAddEnregistrementBasic;
    /** editing an Enregistrement from a BDD */
    public AbstractAction actionEditEnregistrement;
    /** deleting an Enregistrement from a BDD */
    public AbstractAction actionDeleteEnregistrement;
    /** deleting a View from a JBDD */
    public AbstractAction actionDeleteView;
    /** debuging an Enregistrement from a BDD */
    public AbstractAction actionDebugEnregistrement;
    /** loading the BaseDeDonnees */
    public AbstractAction actionLoad;
    /** saving the BaseDeDonnees */
    public AbstractAction actionPrint;
    /** saving the BaseDeDonnees */
    public AbstractAction actionSave;
    /** renaming the BaseDeDonnees */
    public AbstractAction actionSaveAs;
    /** quiting the application */
    public AbstractAction actionQuit;
    /** searching a Table */
    public AbstractAction actionSearchTable;
    /** search all from Serie : SPECIFIC */
    public AbstractAction actionSearchFromSerie;
    /** sorting by Serie,Vol,Title */
    public AbstractAction actionSortBD;
    
    
    /** ref to the BDD */
    public JBaseDeDonnees theJBDD;
    /** ref to the frame */
    public JFrame theFrame;
    
    /** flags for saving/loading BDD */
    public static boolean flag_savedBDD;
    
    /**
     * Creation (need to attachTo() if JBaseDeDonnees is null ).
     */
    public Actions( JFrame p_frame, JBaseDeDonnees p_jbdd ) 
    {
        //logger = Logger.getLogger(Actions.class);
        Logger.getLogger(Actions.class);
        
        theJBDD = p_jbdd;
        theFrame = p_frame;
        
        flag_savedBDD = true;
        
        actionGeneric = new ActionGeneric( "Generic", this);
        actionAddEnregistrement = new ActionAddEnregistrement(  this);
        actionAddEnregistrementParent = new ActionAddEnregistrementParent(  this );
        actionAddEnregistrementBasic = new ActionAddEnregistrementBasic(  this );
        actionEditEnregistrement = new ActionEditEnregistrement(  this );
        actionDeleteEnregistrement = new ActionDeleteEnregistrement(  this );
        actionDeleteView = new ActionDeleteView(  this );
        actionDebugEnregistrement = new ActionDebugEnregistrement( "Debug Enr.", this );
        actionLoad = new ActionLoad(  this );
        actionPrint = new ActionPrint(  this );
        actionSave = new ActionSave(  this );
        actionSaveAs = new ActionSaveAs(  this );
        actionQuit = new ActionQuit( this );
        actionSortBD = new ActionSortBD( this );
        
        actionSearchTable = new ActionSearchTable(  this );
        actionSearchFromSerie = new ActionSearchFromSerie( this );
        
    }
    /**
     * Attach to a JBDD.
     */
    public void attachTo( JBaseDeDonnees p_jbdd ) 
    {
        theJBDD = p_jbdd;
    }
    /**
     * Set the status of Actions.
     *
     * <li>actionEditEnregistrement iff a selectedEnregistrement.</li>
     * <li>actionDeleteEnregistrement iff a selectedEnregistrement.</li>
     * <li>actionDebugEnregistrement iff a selectedEnregistrement.</li>
     */
    public void updateStatus()
    {
        if( theJBDD.getSelectedEnregistrement() != null ) {
            actionEditEnregistrement.setEnabled( true );
            actionDeleteEnregistrement.setEnabled( true );
            actionDebugEnregistrement.setEnabled( true );
        }
        else {
            actionEditEnregistrement.setEnabled( false );
            actionDeleteEnregistrement.setEnabled( false );
            actionDebugEnregistrement.setEnabled( false );
        }
    }
    /**
     * Set the status of Actions.
     *
     * <li>actionEditEnregistrement iff a selectedEnregistrement.</li>
     * <li>actionDeleteEnregistrement iff a selectedEnregistrement.</li>
     * <li>actionDebugEnregistrement iff a selectedEnregistrement.</li>
     */
    public void updateStatus( boolean flag)
    {
        if( flag ) {
            actionEditEnregistrement.setEnabled( true );
            actionDeleteEnregistrement.setEnabled( true );
            actionDebugEnregistrement.setEnabled( true );
        }
        else {
            actionEditEnregistrement.setEnabled( false );
            actionDeleteEnregistrement.setEnabled( false );
            actionDebugEnregistrement.setEnabled( false );
        }
    }/**
     * classic.
     *
     * Output format:<br>
     * 
     */
    public String toString()
    {
        StringBuffer str = new StringBuffer();
        
        str.append("Actions\n");
        
        return str.toString();
    }
    
    
    // ---------- a Private Logger ---------------------
    //private Logger logger;
    // --------------------------------------------------
} // Actions









