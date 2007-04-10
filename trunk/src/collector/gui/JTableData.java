
package collector.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseListener;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;

import org.apache.log4j.Logger;

import collector.data.Enregistrement;
import collector.data.Header;
import collector.data.Table;
import collector.data.View;

/**
 * A JTableData display the data of collector.data.Table:
 *
 * The <code>selectedEnregistrement</code> is updated after every change in 
 * the selection (mouse).
 *
 * JPane::this<br>
 *  |<br>
 *  + JPane::paneUp<br>
 *  | |<br>
 *  | + JLabel::labelTitre<br>
 *  | |<br>
 *  | + JButton::buttonClose<br>
 *  |<br>
 *  + JScrollPane::scrollTable<br>
 *  |<br>
 *  + JSortTable::theTable<br>
 *
 * @version 1.0
 * $Date: 2003/08/25$<br>
 * @author Alain$
 */

public class JTableData extends JPanel
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /** JLabel for title */
    JLabel labelTitre = null;
    /** JButton for closing */
    JButton buttonClose = null;
    /** The Panel for the title and buttons */
    JPanel panelUp;

    /** JPane for table */
    JScrollPane scrollTable = null;
    /** JTable for the data */
    JSortTable tableData = null;

    /** Table with data */
    Table data;

    /** the current Selected Enregistrement */
    public Enregistrement selectedEnregistrement = null;

    /**
     * Creation from Table (no close buttons).
     */
    public JTableData( Table p_table ) 
    {
	logger = Logger.getLogger(JTableData.class);

	data = p_table;
	logger.info("Get the data for the Table");
        if( data == null ) {
            data = defaultTable();
        }
	logger.debug( data.toString() );
	buildGUI( data.getLabel() );
    }
    /**
     * Creation from View (with close buttons).
     */
    public JTableData( View p_table, AbstractAction p_action ) 
    {
	logger = Logger.getLogger(JTableData.class);

	data = p_table;
	logger.info("Get the data for the Table");
        if( data == null ) {
            data = defaultTable();
        }
	logger.debug( data.toString() );
	buildGUI( data.getLabel() + " (source: " + p_table.getOriginalTable().getLabel() + ")");
	addCloseButton( p_action );
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

	str.append("JTableData\n");

	return str.toString();
    }

    /**
     * Arrange the different parts.
     *     
     * <p><b>Todo</b>: set an automated way to get 'no.xpm'
     */
    void buildGUI( String p_name )
    {
	// ----- BorderLayout ----------
	this.setName( data.getLabel() );
	this.setLayout( new BorderLayout( 0 /*hspace*/, 2 /*vspace*/) );
	this.setBorder(BorderFactory.createEmptyBorder(5, //top
						       5, //left
						       5, //bottom
						       5) //right
			     );
	logger.debug( "JPanel created");

	// ----- UpPanel ---------------
	panelUp = new JPanel( new BorderLayout(0, 0));
	labelTitre = new JLabel( p_name );
	labelTitre.setBorder(BorderFactory.createEmptyBorder(
							     0, //top
							     10, //left
							     0, //bottom
							     10) //right
			     );

	panelUp.add( labelTitre, BorderLayout.WEST );

// 	ImageIcon buttonIcon = new ImageIcon("../share/no.png");
// 	buttonClose = new JButton( buttonIcon );
// 	buttonClose.setPreferredSize( new Dimension( buttonIcon.getIconWidth()+2,
// 						    buttonIcon.getIconHeight() ));
// 	buttonClose.setBorder(BorderFactory.createEmptyBorder(
// 							     0, //top
// 							     0, //left
// 							     0, //bottom
// 							     2) //right
// 			     );
// 	panelUp.add( buttonClose, BorderLayout.EAST );
	logger.debug( "panelUp created" );
	
	// ----- main Panel --------------
        //tableData = new JTable( new TableDataModel( data ) );
	tableData = new JSortTable( data );

	// Column size
	TableColumn column = null;
	for (int i = 0; i < tableData.getColumnCount(); i++) {
	    column = tableData.getColumnModel().getColumn(i);
	    column.setMinWidth(150);
	    //column.setPreferredWidth(200);
	    }
	// to be sure that columns with have their right size
	tableData.setAutoResizeMode( JTable.AUTO_RESIZE_OFF);
	logger.debug( "tableChanged with resizeMode = " + tableData.getAutoResizeMode() + " (" + JTable.AUTO_RESIZE_OFF + ")");
	// Only one line can be selected
	tableData.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
	ListSelectionModel selectModel = tableData.getSelectionModel();
	selectModel.addListSelectionListener( new TableDataSelectionEvents() );

	//tableData.setPreferredSize(new Dimension(150, 200));
	//tableData.addMouseListener( new MyMouseListener() );

	logger.debug("Trackviewportwidth = " + tableData.getScrollableTracksViewportWidth());
	scrollTable = new JScrollPane( tableData );
	//scrollTable.addMouseListener( new MyMouseListener() );

	// add to the main Panel
	this.add( panelUp, BorderLayout.NORTH );
	this.add( scrollTable, BorderLayout.CENTER );
	
	//addMouseListener( new MyMouseListener() );
    }
    /**
     * Add a close button (for View ).
     */
    void addCloseButton( AbstractAction p_action )
    {
 	ImageIcon buttonIcon = new ImageIcon("../share/no.png");
 	buttonClose = new JButton();
	buttonClose.setAction( p_action );
	buttonClose.setText("");
	buttonClose.setIcon( buttonIcon );
 	buttonClose.setPreferredSize( new Dimension( buttonIcon.getIconWidth()+2,
 						    buttonIcon.getIconHeight() ));
 	buttonClose.setBorder(BorderFactory.createEmptyBorder(
 							     0, //top
 							     0, //left
 							     0, //bottom
 							     2) //right
 			     );
 	panelUp.add( buttonClose, BorderLayout.EAST );
    }


    /**
     * Add MouseListener to all subcomponents.
     */
    public void addMouseListener(MouseListener l)
    {
	tableData.addMouseListener( l );
	tableData.getTableHeader().addMouseListener( l );
	scrollTable.addMouseListener( l );
	super.addMouseListener( l );
    }
    /**
     * Add SelectionListener.
     */
    public void addSelectionListener( ListSelectionListener l )
    {
	ListSelectionModel selectModel = tableData.getSelectionModel();
	selectModel.addListSelectionListener( l );
    }
    /** 
     * Select a given Enregistrement.
     */
    public void setSelection( Enregistrement p_enr )
    {
	// exists ?
	int index = data.getIndex( p_enr );
	logger.debug( "Selection found at " + index );
	if( index != -1 ) {
	    selectedEnregistrement = p_enr;
	    tableData.setRowSelectionInterval( index, index );
	}
	    
    }

    /**
     * Responding to change is Selection
     */
    class TableDataSelectionEvents implements ListSelectionListener {
	public void valueChanged(ListSelectionEvent e) {
	    //Ignore extra messages and wait for end of change
	    if (e.getValueIsAdjusting()) return;
	    
	    ListSelectionModel lsm =
		(ListSelectionModel)e.getSource();
	    if (lsm.isSelectionEmpty()) {
		//no rows are selected
		logger.debug("Selection: none");
		selectedEnregistrement = null;
	    } else {
		int selectedRow = lsm.getMinSelectionIndex();
		//selectedRow is selected
		logger.debug("Selection of row " + selectedRow );
		selectedEnregistrement = data.getAt( selectedRow );
		// selectedEnregistrement.display();
	    }
	}
    } // MySelectionEvents


    // ---------- a Private Logger ---------------------
    private Logger logger;
    // --------------------------------------------------

    /**
     * A default Table for display.
     * 
     * The defaultTable is has no data and is names "Pas de données".
     */
    private Table defaultTable() {
	Header defaultHeader = new Header();

	Table theTable = new Table( "Pas de données");
 	theTable.setHeader( defaultHeader );
	
        return theTable;
    }
    
} // JTableData
    








