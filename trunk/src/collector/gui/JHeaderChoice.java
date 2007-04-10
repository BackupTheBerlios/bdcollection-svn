package collector.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

import collector.data.Field;
import collector.data.Header;

/**
 * A JHeaderChoice allow to choose an ordered set of Fields from a given set.
 *
 * @version 1.0
 * $Date: 2003/07/08$<br>
 * @author Alain$
 */

public class JHeaderChoice extends JPanel
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /** A From JHeaderList */
    JHeaderList source;
    /** A To JHeaderList */
    JHeaderList dest;

    /**
     * Creation.
     * @param p_choice The choice of Field can already be initiated.
     */
    public JHeaderChoice( Header p_source, Header p_choice) 
    {
	logger = Logger.getLogger(JHeaderChoice.class);

	source = new JHeaderList( p_source );
	dest = new JHeaderList( p_choice );

	buildGUI();
    }

    /**
     * the GUI uses a GridBagLayout
     */
    void buildGUI()
    {
	GridBagLayout gridbag = new GridBagLayout();
	GridBagConstraints c = new GridBagConstraints();
	this.setLayout( gridbag );
	c.insets = new Insets(5,5,5,5);

	// JHeaderList 'source'
	c.gridx = 0; // at left
	c.gridy = 0; // at top
	c.gridwidth = 1; 
	c.gridheight = 3; // top to bottom
	c.fill = GridBagConstraints.BOTH;
	c.weightx = 1.0; // grow more than Buttons
	c.weighty = 1.0; // grow more than Buttons
	gridbag.setConstraints( source , c);
	add( source );

	// Middle Buttons
	JPanel middleButtonPanel = new JPanel();
	middleButtonPanel.setLayout( new BoxLayout( middleButtonPanel, BoxLayout.Y_AXIS));
	JButton toButton = new JButton( ">>" );
	toButton.addActionListener( new toListener() );
	middleButtonPanel.add( toButton );
	JButton fromButton = new JButton( "<<" );
	fromButton.addActionListener( new fromListener() );
	middleButtonPanel.add( fromButton );
	c.gridx = 1; // at center
	c.gridy = 1; // at cener
	c.gridwidth = 1; 
	c.gridheight = 1; 
	c.fill = GridBagConstraints.NONE;
	c.weightx = 0.0; // don't grow 
	gridbag.setConstraints(middleButtonPanel, c);
	add( middleButtonPanel );

	// JHeaderList 'dest'
	c.gridx = 2; // at left
	c.gridy = 0; // at top
	c.gridwidth = 1; 
	c.gridheight = 3; // top to bottom
	c.fill = GridBagConstraints.BOTH;
	c.weightx = 1.0; // grow more than Buttons
	c.weighty = 1.0; // grow more than Buttons
	gridbag.setConstraints( dest , c);
	add( dest );

	// vertical Buttons
	JPanel verticalButtonPanel = new JPanel();
	verticalButtonPanel.setLayout( new BoxLayout( verticalButtonPanel, BoxLayout.Y_AXIS));
	JButton upButton = new JButton( "UP" );
	upButton.addActionListener( new upListener() );
	upButton.setAlignmentX( CENTER_ALIGNMENT );
	verticalButtonPanel.add( upButton );
	JButton downButton = new JButton( "DOWN" );
	downButton.addActionListener( new downListener() );
	downButton.setAlignmentX( CENTER_ALIGNMENT );
	verticalButtonPanel.add( downButton );
	c.gridx = 3; // at center
	c.gridy = 1; // at cener
	c.gridwidth = 1; 
	c.gridheight = 1; 
	c.fill = GridBagConstraints.NONE;
	c.weightx = 0.0; // don't grow 
	gridbag.setConstraints(verticalButtonPanel, c);
	add( verticalButtonPanel );
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

	str.append("JHeaderChoice \n");

	return str.toString();
    }

    // ---------------------- ACTIONS --------------------------------
    /**
     * 'to' : remove from source to add at end of dest.
     */
    class toListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //This method can be called only if
            //there's a valid selection
            //so go ahead and remove whatever's selected.
	    
	    // Object selected
	    int[] selection = source.theJList.getSelectedIndices();

	    for( int i=selection.length-1; i>=0; i--) {
		logger.debug("Removing " + selection[i] + " from source");
		Field tmpField = (Field) source.data.get( selection[i] );
		source.data.remove( selection[i] );
		dest.data.addElement( tmpField );
	    }
        }
    }
    /**
     * 'from' : remove from dest to add at end of source.
     * 
     * Could also put back in order (thanks to alphabetical or Field.index.
     */
    class fromListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //This method can be called only if
            //there's a valid selection
            //so go ahead and remove whatever's selected.
	    
	    // Object selected
	    int[] selection = dest.theJList.getSelectedIndices();

	    for( int i=selection.length-1; i>=0; i--) {
		logger.debug("Removing " + selection[i] + " from dest");
		Field tmpField = (Field) dest.data.get( selection[i] );
		dest.data.remove( selection[i] );
		source.data.addElement( tmpField );
	    }
        }
    }
    /**
     * 'up' : go up in dest.
     */
    class upListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //This method can be called only if
            //there's a valid selection
            //so go ahead and remove whatever's selected.
	    
	    // Object selected
	    int[] selection = dest.theJList.getSelectedIndices();

	    for( int i=0; i<selection.length; i++) {
		logger.debug("Removing " + selection[i] + " from dest");
		if( selection[i] == 0 ) {
		    // stop going up
		    break;
		}
		Field tmpField = (Field) dest.data.get( selection[i] );
		dest.data.remove( selection[i] );
		dest.data.add( selection[i]-1, tmpField );
		selection[i]--;
	    }
	    dest.theJList.setSelectedIndices( selection );
        }
    }
    /**
     * 'down' : go up in dest.
     */
    class downListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //This method can be called only if
            //there's a valid selection
            //so go ahead and remove whatever's selected.
	    
	    // Object selected
	    int[] selection = dest.theJList.getSelectedIndices();

	    for( int i=selection.length-1; i>=0; i--) {
		logger.debug("Removing " + selection[i] + " from dest");
		if( selection[i] == dest.data.size()-1 ) {
		    // stop going down
		    break;
		}
		Field tmpField = (Field) dest.data.get( selection[i] );
		dest.data.remove( selection[i] );
		dest.data.add( selection[i]+1, tmpField );
		selection[i]++;
	    }
	    dest.theJList.setSelectedIndices( selection );
        }
    }

    // ---------- a Private Logger ---------------------
    private Logger logger;
    // --------------------------------------------------
} // JHeaderChoice
    








