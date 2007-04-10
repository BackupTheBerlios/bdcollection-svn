
package collector.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseListener;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionListener;

import org.apache.log4j.Logger;

import collector.control.Actions;
import collector.data.BaseDeDonnees;
import collector.data.Enregistrement;
import collector.data.Table;
import collector.data.View;

/**
 * A JBaseDeDonnes display the data of collector.data.Table:
 *
 * JTabbedPane::this<br>
 *
 * Right now, it uses 3 JTables corresponding to the related Table
 * of BDD (parent, basic, complete).
 *
 * @version 1.0
 * $Date: 2003/11/12$<br>
 * @author Alain$
 *
 * @warning This class is in fact a mix of control things.
 */

public class JBaseDeDonnees extends JPanel
{
    /**
     * 
     */
    private static final long serialVersionUID = 5720116887590712482L;
    
    /** Where does View begin ? */
    static final int thresoldView = 2;
    
    /** the Frame it belongs to */
    JFrame rootWindow;
    /** the Title of the application */
    String rootTitle;
    
    /** the underlying BDD */
    public BaseDeDonnees theBDD;
    /** the set of Pane */
    JTabbedPane theTabbedPane;
    
    /** One Table */
    JTableData basicJTable;
    /** The parent Table */
    JTableData parentJTable;
    /** The complete Table */
    JTableData completeJTable;
    
    /** Actions that can be done. */
    Actions theActions;
    /** MouseListener for new Table */
    MouseListener theMouseListenerTable;
    /** SelectionListener for new Table */
    ListSelectionListener theSelectionListener;
    
    /**
     * Creation from a BDD.
     */
    public JBaseDeDonnees( BaseDeDonnees p_BDD, Actions p_actions,
            JFrame rootFrame, String applicationName) 
    {
        logger = Logger.getLogger(JBaseDeDonnees.class);
        
        rootWindow = rootFrame;
        rootTitle = applicationName;
        
        theBDD = p_BDD;
        theActions = p_actions;
        theActions.attachTo( this );
        buildGUI();
    }
    /**
     * Creation without a BDD.
     */
    public JBaseDeDonnees( Actions p_actions ,
            JFrame rootFrame, String applicationName) 
    {
        logger = Logger.getLogger(JBaseDeDonnees.class);
        
        rootWindow = rootFrame;
        rootTitle = applicationName;
        
        theActions = p_actions;
        theActions.attachTo( this );
        theBDD = null;
    }
    /**
     * Attach to a new BDD and recreate new GUI.
     */
    public void attachToBDD( BaseDeDonnees p_BDD )
    {
        theBDD = p_BDD;
        buildGUI();
    }
    /**
     * Build GUI again.
     */
    public void rebuild()
    {
        buildGUI();
    }
    /**
     * classic.
     *
     * Output format:<br>
     * 
     */
    public String toString()
    {
        StringBuffer str = new StringBuffer();
        
        str.append("JBaseDeDonnees\n");
        
        return str.toString();
    }
    
    /**
     * Arrange the different parts.
     *     
     */
    void buildGUI()
    {
        setWindowTitle();
        
        theTabbedPane = new JTabbedPane();
        
        this.setName( "JBDD" );
        this.setLayout( new BorderLayout() );
        this.setBorder(BorderFactory.createEmptyBorder(5, //top
                5, //left
                5, //bottom
                5) //right
        );
        this.add( theTabbedPane );
        this.setPreferredSize( new Dimension( 400, 200 ));
        
        /* the 3 basic JTables */
        parentJTable = new JTableData( theBDD.parentTable );
        theTabbedPane.addTab( "Parent", parentJTable );
        
        basicJTable = new JTableData( theBDD.basicTable );
        theTabbedPane.addTab( "Basic", basicJTable );
        
        completeJTable = new JTableData( theBDD.completeTable );
        theTabbedPane.addTab( "Complete", completeJTable );
    }
    
    /**
     * Set the Title of the root Window.
     * It is either 'rootName - databaseName' or 'rootName - no base'.
     */
    protected void setWindowTitle()
    {
        if( theBDD != null ) {
            if( theBDD.fileDesc != null) {
                String titleStr = new String(  rootTitle + " - " + theBDD.fileDesc.getPath());
                rootWindow.setTitle( titleStr);
            }
            else {
                rootWindow.setTitle( rootTitle + " - no base");
            }
        }
        else {
            rootWindow.setTitle( rootTitle + " - no base");
        }
    }
    
    /**
     * Add MouseListener to sub components.
     */
    public void addMouseListener(MouseListener p_listen)
    {
        /* MouseListener for the Table */
        //theMouseListenerTable = p_listen;
        //logger.debug( "theMouseListener = " + theMouseListenerTable.toString() );
        
        // add to the primary tables
        parentJTable.addMouseListener( theMouseListenerTable );
        basicJTable.addMouseListener( theMouseListenerTable );
        completeJTable.addMouseListener( theMouseListenerTable );
        
        theTabbedPane.addMouseListener( theMouseListenerTable );
        super.addMouseListener( theMouseListenerTable );
        //logger.debug( "theMouseListener = " + theMouseListenerTable.toString() );
    }
    /** 
     * Set the MouseListener for later use.
     */
    public void setMouseListener(MouseListener p_listen)
    {
        /* MouseListener for the Table */
        theMouseListenerTable = p_listen;
        
        addMouseListener( theMouseListenerTable );
    }
    /**
     * Add SelectionListener.
     */
    public void addSelectionListener( ListSelectionListener l )
    {
        /* SelectionListener for the Table */
        //theSelectionListener = l;
        
        // add to the primary tables
        parentJTable.addSelectionListener( theSelectionListener);
        //basicJTable.addSelectionListener( theSelectionListener );
        completeJTable.addSelectionListener( theSelectionListener );
        
    }
    /**
     * set SelectionListener for later usage.
     */
    public void setSelectionListener( ListSelectionListener l )
    {
        /* SelectionListener for the Table */
        theSelectionListener = l;
        
        addSelectionListener( theSelectionListener );
    }
    /**
     * Add ChangeListener.
     */
    public void addChangeListener( ChangeListener l )
    {
        theTabbedPane.addChangeListener( l );
    }
    /**
     * Add a View.
     *
     * @warning control things
     */
    public void addView( View p_view, AbstractAction p_action )
    {
        JTableData tmpTable = new JTableData( p_view, p_action );
        // control
        logger.debug( "theMouseListener = " + theMouseListenerTable.toString() );
        tmpTable.addMouseListener( theMouseListenerTable );
        tmpTable.addSelectionListener( theSelectionListener );
        // control
        theTabbedPane.addTab( p_view.getLabel(), tmpTable );
        theTabbedPane.setSelectedIndex( theTabbedPane.indexOfComponent( tmpTable ));
    }
    
    /** 
     * get the actual selected Table.
     *
     * @warning Beware of Views and Table....
     */
    public Table getSelectedTable()
    {
        JTableData tmpTable = (JTableData) theTabbedPane.getSelectedComponent();
        return (Table) tmpTable.data;
    }
    
    /** 
     * get the actual selected View.
     *
     * @return null id currently selected is not a View.
     */
    public View getSelectedView()
    {
        JTableData tmpTable = (JTableData) theTabbedPane.getSelectedComponent();
        
        // only possible if index superior than thresoldView.
        if( theTabbedPane.getSelectedIndex() > thresoldView ) {
            return (View) tmpTable.data;
        }
        else {
            return null;
        }
    }
    /**
     * Delete the actual selected View.
     */
    public void deleteSelectedView()
    {
        int tmpIndex = theTabbedPane.getSelectedIndex();
        if( tmpIndex > thresoldView ) {
            theTabbedPane.remove( tmpIndex );
        }
        else {
            logger.warn( "Not possible to delete index = " + tmpIndex );
        }
    }
    /**
     * get the actual selected Enregistrement.
     *
     */
    public Enregistrement getSelectedEnregistrement()
    {
        JTableData tmpTable = (JTableData) theTabbedPane.getSelectedComponent();
        return (Enregistrement) tmpTable.selectedEnregistrement;
    }
    
    // ---------- a Private Logger ---------------------
    private Logger logger;
    // --------------------------------------------------
    
} // JBaseDeDonnees









