package collector.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import org.apache.log4j.Logger;

import collector.data.Field;
import collector.data.Header;
import collector.data.SearchOperator;

/**
 * An JSearchedOperator displays the type of search (OR or AND),
 * a list of searchedPattend.
 * If associated to a Table, it displays all Fields.
 * 
 * @todo Maybe associate an SearchOperator with a table or, better, with an Header.
 * @todo Allow to associate to a SearchOperator that will be edited.
 *
 * @version 1.0
 * $Date: 2004/04/30$<br>
 * @author Alain$
 */

public class JSearchOperator extends JPanel
{
    /** the associated SearchOperator */
    SearchOperator theSearchOperator;
    /** the Header for which it is designed */
    Header theHeader;

    /** for the OR search */
    JRadioButton buttonOR;
    /** for the AND search */
    JRadioButton buttonAND;

    /** an Array of JSearchedElement */
    JSearchedElement[] theJSearchElements;

    /** 
     * Creation linked to a given Header.
     */
    public JSearchOperator( Header p_header )
    {
	logger = Logger.getLogger(JSearchOperator.class);

	theSearchOperator = new SearchOperator();
	theHeader = p_header;
	theJSearchElements = new JSearchedElement[theHeader.theFields.size()];

	buildGUI();
    }

    /**
     * Builds the GUI.
     */
    void buildGUI()
    {
	// ----- BorderLayout ----------
	this.setLayout( new BorderLayout( 0 /*hspace*/, 2 /*vspace*/) );
	this.setBorder(BorderFactory.createEmptyBorder(5, //top
						       5, //left
						       5, //bottom
						       5) //right

		       );
	
	// ----- The Panel for the search Button
	JPanel panelType = new JPanel(); // default is flowLayout
	
	buttonOR = new JRadioButton( "OR" );
	buttonOR.setActionCommand( "OR" );
	buttonOR.addActionListener( new ButtonTypeListener() );
	panelType.add( buttonOR );
	buttonAND = new JRadioButton( "AND" );
	buttonAND.setActionCommand( "AND" );
	buttonAND.addActionListener( new ButtonTypeListener() );
	panelType.add( buttonAND );
	
	ButtonGroup groupType = new ButtonGroup();
	groupType.add( buttonOR );
	groupType.add( buttonAND );
	this.setType( SearchOperator.OR );

	this.add( panelType, BorderLayout.NORTH );

	// ----- The Panel for the various JSearchedElement
	JPanel panelSearch = new JPanel();
	panelSearch.setLayout(new BoxLayout(panelSearch, BoxLayout.Y_AXIS));

	// create all the JSearchedElement
	// suppose that theJSearchOperator was null.... (@todo)
	Field tmpField;
	
	for( int i = 0; i < theHeader.theFields.size(); i++ ) {
	    tmpField = (Field) theHeader.theFields.get(i);
	    
	    theJSearchElements[i] = new JSearchedElement( tmpField );
	    panelSearch.add( theJSearchElements[i] );
	    panelSearch.add(Box.createRigidArea(new Dimension(0,5)));
	}
	// @todo pb of too large JSearchElements when resizing 
	panelSearch.add(Box.createVerticalGlue());

	this.add( panelSearch, BorderLayout.CENTER );
    }

    /** 
     * switch panelType's buttons according to the type of Search.
     */
    void setType( int p_type)
    {
	switch( p_type ) {
	case SearchOperator.OR : buttonOR.setSelected( true );
	    theSearchOperator.setSearchType( SearchOperator.OR );
	    break;
	case SearchOperator.AND : buttonAND.setSelected( true );
	    theSearchOperator.setSearchType( SearchOperator.AND );
	    break;
	default: logger.warn( "Unknown search type = " + p_type );
	}
    }

    /**
     * Create a SearchOperator from the content of the JSearchOperator.
     */
    public SearchOperator getSearchOperator()
    {
	// theSearchOperator.setType() is set
	
	// add SearchedElement if needed
	theSearchOperator.clearSearch();
	for( int i = 0; i < theHeader.theFields.size(); i++ ) {
	    if( theJSearchElements[i].isSelected() ) {
		theSearchOperator.addSearchItem( theJSearchElements[i].getSearchedElement() );
	    }
	}
	return theSearchOperator;
    }

    /** 
     * Listens to the radio buttons for type.
     */
    class ButtonTypeListener implements ActionListener { 
        public void actionPerformed(ActionEvent e) {
	    if( e.getActionCommand().compareTo("OR") == 0 ) {
		setType( SearchOperator.OR );
	    } 
	    else if ( e.getActionCommand().compareTo("AND") == 0 ) {
		setType( SearchOperator.AND );
	    } 
	    else {
		logger.warn( "Unknown action = " + e.getActionCommand() );
	    }
	}
    } // buttonTypeListener

    // ---------- a Private Logger ---------------------
    private Logger logger;
    // --------------------------------------------------
} //JSearchOperator

