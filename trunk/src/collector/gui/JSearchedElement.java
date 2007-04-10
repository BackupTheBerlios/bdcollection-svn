package collector.gui;

import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import collector.data.Field;
import collector.data.SearchedElement;

/**
 * A horizontal row made of a check box, the name of the field, the type of 
 * search and the pattern looked for.
 *
 * @todo Allow to enter a SearchedElement that could be modified.
 *
 * @version 1.0
 * $Date: 2004/04/29$<br>
 * @author Alain$
 */

public class JSearchedElement extends JPanel 
{
    /** Do we look for that element */
    JCheckBox checkBoxLook;
    /** Name of field */
    JLabel labelField;
    /** type of search */
    JComboBox comboBoxType;
    /** pattern looked for */
    JTextField textFieldPattern;

    /** The field we might be looking in */
    Field theField;
    /** The SearchedElement that might be returned */
    SearchedElement theSearchedElement;

    /** Width of the fields */
    final static int WIDTH_FIELD = 30;

    /**
     * Creation from a Field.
     */
    public JSearchedElement( Field p_field )
    {
	logger = Logger.getLogger(JSearchedElement.class);

	// create internal objects
	theField = p_field;
	theSearchedElement = null;

	buildGUI();
    }

    /** 
     * Build a GUI from allowable data.
     */
    public void buildGUI()
    {
	// create the BoxLayout
	BoxLayout layout = new BoxLayout( this, BoxLayout.X_AXIS );
	this.setLayout( layout );

	// rigid area
	this.add( Box.createRigidArea( new Dimension (5,0)));

	// add a the checkBox
	checkBoxLook = new JCheckBox();
	checkBoxLook.addItemListener( new MyCheckBoxListener() );
	this.add( checkBoxLook );
	// rigid area
	this.add( Box.createRigidArea( new Dimension (5,0)));
	
	// add the label
	labelField = new JLabel( theField.getLabel() + " : ");
	this.add( labelField );

	// add a comboBox
	comboBoxType = new JComboBox( SearchedElement.SEARCH_STR );
	comboBoxType.setSelectedItem( SearchedElement.INCLUDED_STR );
	this.add( comboBoxType );
	// rigid area
	this.add( Box.createRigidArea( new Dimension (5,0)));

	// add a textField for pattern
	textFieldPattern = new JTextField(WIDTH_FIELD);
	//if( theSearchedElement != null ) {
	//    textFieldPattern.setText( theSearchedElement.getPattern() );
	//}
	textFieldPattern.setEditable( true );
	this.add( textFieldPattern );
	
	// set the state of the whole
	// if the SearchedElement already exists, then check box
	if( theSearchedElement != null ) {
	    setSelected( true );
	}
	else {
	    setSelected( false );
	}
    }

    /**
     * change the status of the SearchedElement.
     */
    public void setSelected( boolean p_status )
    {
	checkBoxLook.setSelected( p_status );
	comboBoxType.setEnabled( p_status );
	textFieldPattern.setEnabled( p_status );
    }

    /**
     * getSelected status.
     */
    public boolean isSelected()
    {
	return checkBoxLook.isSelected();
    }
    /**
     * return a SearchedElement that is created if needed.
     */
    public SearchedElement getSearchedElement()
    {
	if( theSearchedElement == null ) {
	    theSearchedElement = new SearchedElement( textFieldPattern.getText(),
						      theField,
						      (String) comboBoxType.getSelectedItem() );
	}
	else {
	    theSearchedElement.setUp( textFieldPattern.getText(),
				      theField,
				      (String) comboBoxType.getSelectedItem() );
	}
	return theSearchedElement;
    }
    /** 
     * Listen to checkBoxLook to change the state of everythinh.
     */
    class MyCheckBoxListener implements ItemListener {
	/**
	 * call to setChecked().
	 */
	public void itemStateChanged(ItemEvent e) {
	    setSelected( checkBoxLook.isSelected() );
	}
    } // MyCheckBoxListener

    // ---------- a Private Logger ---------------------
    private Logger logger;
    // --------------------------------------------------
} // JSearchedElement
