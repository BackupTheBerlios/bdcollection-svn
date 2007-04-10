
package collector.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.apache.log4j.Logger;

import collector.data.CellStr;
import collector.data.Element;
/**
 * A horizontal row made of label+check+rigid+JTextField.
 * 
 * Check boxes used to inherit from parent (set by default if possible).
 *
 * @version 1.0
 * $Date: 2003/09/12$<br>
 * @author Alain$
 */

public class JCellStr extends JCell
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /** Data is the CellStr */
    CellStr data;
    
    /** data displayed in */
    JTextField text;
    /** Inherit if checked box */
    JCheckBox checkBox;

    /** Width of the fields */
    final static int WIDTH_FIELD = 30;
    
    /**
     * Creation
     */
    public JCellStr( CellStr p_data, boolean p_editable ) 
    {
	logger = Logger.getLogger(JCellStr.class);

	data = p_data;
	if( data == null ) {
	    logger.warn( "Not able to deal with null data");
	}

	// create the BoxLayout
	BoxLayout layout = new BoxLayout( this, BoxLayout.X_AXIS );
	this.setLayout( layout );

	// rigid area
	this.add( Box.createRigidArea( new Dimension (5,0)));

	// label
	JLabel label = new JLabel( data.getLabel() + " : ");
	this.add( label );

	// check box
	checkBox = new JCheckBox();
	checkBox.addItemListener( new MyCheckBoxListener() );
	// usable only if has parent
	if( data.hasParent() == false ) {
	    setEnabled( false );
	}
	this.add( checkBox );

	// rigid area
	this.add( Box.createRigidArea( new Dimension (5,0)));

	// text field
	text = new JTextField( WIDTH_FIELD );
	text.setText( data.displayData() );
	text.setEditable( p_editable );
	text.addActionListener( new MyTextFieldListener() );
	text.getDocument().addDocumentListener( new MyTextFieldChangeListener() );
	this.add( text );

	// rigid area
	this.add( Box.createRigidArea( new Dimension (5,0)));

	// if inheriting
	if( data.isInherit() ) {
	    setChecked( true );
	    checkBox.setSelected( true  );
	}
	else {
	    setChecked( false );
	    checkBox.setSelected( false );
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

	str.append("JCellStr\n");

	return str.toString();
    }

    /** 
     * Enable the use of check box according to parent existence.
     */
    public void setEnabled( boolean p_state )
    {
	checkBox.setEnabled( p_state );
    }

    /**
     * If checked, then text is disabled and non-editable.
     *
     * Mainly for inheritance from ParentCell/Table.
     */
    public void setChecked( boolean p_state )
    {
	if( data.hasParent() ) {
	    if( p_state ) {
		data.setInherit( true );
		text.setText( "Parent::"+ data.getParent().displayData() );
		text.setEnabled( false );
	    }
	    else {
		data.setInherit( false );
		text.setText( data.displayData() );
		text.setEnabled( true );
	    }
	}
    }
    
    /**
     * Update According to CellStr.
     */
    public void update()
    {
	// check box
	if( data.hasParent() == false ) {
	    setEnabled( false );
	}
	else {
	    setEnabled( true );
	}
    }
    /**
     * Update with new value.
     * 
     * @param p_data must be a CellInt
     */
    public void update( Element p_data)
    {
        if (p_data instanceof CellStr) {
            data = (CellStr) p_data;
            text.setText( data.displayData() );
            update();
        }
    }
    /** 
     * Listen to checkBox to change the state of text.
     */
    class MyCheckBoxListener implements ItemListener {
	/**
	 * call to setChecked().
	 */
	public void itemStateChanged(ItemEvent e) {
	    setChecked( checkBox.isSelected() );
	}
    } // MyCheckBoxListener

    /**
     * Can be made editable by default.
     */
    public void setEditable( boolean p_state )
    {
	text.setEditable( p_state );
    }
    /**
     * Listen to validation (RETURN) in text Field.
     */
    class MyTextFieldListener implements ActionListener {
	/**
	 * when text is changed.
	 */
	public void actionPerformed(ActionEvent evt) {
	    String textStr = text.getText();
	    data.setData( textStr );
	    //textField.selectAll();
	}
    } //MyTextFieldListener

    /**
     * Listen to change in text Field.
     */
    class MyTextFieldChangeListener implements DocumentListener {
	public void insertUpdate(DocumentEvent e) {
	    if( data.isInherit() == false ) {
		String textStr = text.getText();
		data.setData( textStr );
	    }
	}
	public void removeUpdate(DocumentEvent e) {
	    if( data.isInherit() == false ) {
		String textStr = text.getText();
		data.setData( textStr );
	    }
	}
	public void changedUpdate(DocumentEvent e) {
	    //String textStr = text.getText();
	    //data.setData( textStr );
	}
	
    } // MyTextFieldChangeListener

    // ---------- a Private Logger ---------------------
    private Logger logger;
    // --------------------------------------------------
} // JCellStr
    








