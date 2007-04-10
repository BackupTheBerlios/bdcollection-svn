
package collector.gui;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

import collector.data.CellInt;
import collector.data.CellStr;
import collector.data.Element;
import collector.data.Enregistrement;

/**
 * Vertical row of JCell.
 *
 * @version 1.0
 * $Date: 2003/09/12$<br>
 * @author Alain$
 */

public class JEnregistrement extends JPanel
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /** Data is an Enregistrement */
    Enregistrement data;

    /** Need to store the different JCell */
    JCell cellGUI[];

    /**
     * Creation
     */
    public JEnregistrement( Enregistrement p_data, boolean p_editable ) 
    {
	logger = Logger.getLogger(JEnregistrement.class);

	data = p_data;
	if( data == null ) {
	    logger.warn( "Not able to deal with null data");
	}

	// create the BoxLayout
	BoxLayout layout = new BoxLayout( this, BoxLayout.Y_AXIS );
	this.setLayout( layout );

	// rigid area
	this.add( Box.createRigidArea( new Dimension (0,5)));

	// creation of the JCells
	cellGUI = new JCell[ data.data.length ];
	// add each Cell by turn
	for( int i=0; i < data.data.length; i++ ) {
	    Element cell = data.data[i];

	    if( cell.getType().equals( Element.typeString ) ) {
		cellGUI[i] = new JCellStr( (CellStr) cell, p_editable );
		this.add( cellGUI[i] );

		// rigid area
		this.add( Box.createRigidArea( new Dimension (0,2)));
	    }
	    else if( cell.getType().equals( Element.typeInt ) ) {
		cellGUI[i] = new JCellInt( (CellInt) cell, p_editable );
		this.add( cellGUI[i] );

		// rigid area
		this.add( Box.createRigidArea( new Dimension (0,2)));
	    }
	    else {
		logger.warn( "Cell is of unknown type " + cell.getType() );
	    }
	}
    }

    /**
     * Update the JEnregistrement.
     *
     * When the status of the Enregistrement has changed (mostly parent),
     * update the status of the boxes.
     */
    public void update()
    {
	// add each Cell by turn
	for( int i=0; i < data.data.length; i++ ) {
	    //cellGUI[i].update( (CellStr) data.data[i] ); 
	    cellGUI[i].update();
	}
    }
    /**
     * Update with new value.
     * 
     * @param p_enr
     */
    public void update( Enregistrement p_enr)
    {
        //update each Cell in turn
        for (int i = 0; i < p_enr.data.length; i++) {
            cellGUI[i].update( p_enr.data[i] );
        }
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

	str.append("JEnregistrement\n");

	return str.toString();
    }

    // ---------- a Private Logger ---------------------
    private Logger logger;
    // --------------------------------------------------
} // JEnregistrement
    








