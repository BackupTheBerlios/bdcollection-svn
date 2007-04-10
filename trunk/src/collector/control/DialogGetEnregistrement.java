
package collector.control;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

import collector.data.Enregistrement;
import collector.data.Table;
import collector.gui.JTableData;

/**
 * DialogGetEnregistrement will display a Table and ask the user to select
 * an Enregistrement.
 * <p>
 * <b>Return</b>of askForEnregistrement: selected Enregistrement or null.
 * 
 * Buttons : Ok : return selected Enregistrement.<br>
 *           Cancel : return null<br>
 * @version 1.0
 * $Date: 2003/10/04$<br>
 * @author Alain$ */

public class DialogGetEnregistrement extends JDialog implements ActionListener {

    /** */
    JTableData myTableData;

    /** The selected Enregistrement */
    Enregistrement selectedEnregistrement;

    /**
     * Creator with a Frame
     *
     * @param aFrame The parent Frame.
     * @param pTable The parent Table.
     * @param p_enr A possible existing parentEnregistrement
     */
    public DialogGetEnregistrement( Frame aFrame, Table pTable, Enregistrement p_enr )
    {
	super( aFrame,
	       "Choisir un Enregistrement",
	       true /* block input */);

	logger = Logger.getLogger(DialogGetEnregistrement.class);
	logger.debug("Creation from a Frame");

	selectedEnregistrement = p_enr;
	
	// the content
	setContentPane( buildEntry( pTable ) );
	// sets the focus
	//.requestFocus();
	
	// set invisible
	setVisible(false);
	pack();
    }
    /**
     * Creator with a Dialog
     *
     * @param aDialog The parent Frame.
     * @param pTable The parent Table.
     */
    public DialogGetEnregistrement( Dialog aDialog, Table pTable, Enregistrement p_enr  )
    {
	super( aDialog,
	       "Choisir un Enregistrement",
	       true /* block input */);

	logger = Logger.getLogger(DialogGetEnregistrement.class);
	logger.debug("Creation from a Dialog");

	selectedEnregistrement = p_enr;
	
	// the content
	setContentPane( buildEntry( pTable ) );
	// sets the focus
	//.requestFocus();
	
	// set invisible
	setVisible(false);
	pack();
    }
    
    /**
     * Builds the DialogGetEnregistrement GUI from the hardwired data.
     *
     * It consist of the scrollable JTableData
     * And 2 buttons : ok, cancel.
     */ 
    protected JPanel buildEntry( Table pTable )
    {
	logger.debug("buildEntry");

	JPanel mainPanel = new JPanel();
	// the JTableData
	myTableData = new JTableData( pTable );

	// select Enregistrement if exists
	if( selectedEnregistrement != null )
	    {
		myTableData.setSelection( selectedEnregistrement );
	    }

	// The buttons
	JButton okButton = new JButton( "Ok" );
	okButton.setMnemonic( KeyEvent.VK_O );
	okButton.setActionCommand("ok");
	okButton.addActionListener( this );
	
	JButton cancelButton = new JButton( "Annuler" );
	cancelButton.setMnemonic( KeyEvent.VK_A );
	cancelButton.setActionCommand("cancel");
	cancelButton.addActionListener( this );

	JPanel buttonPanel = new JPanel();
	buttonPanel.setBorder( BorderFactory.createEmptyBorder( 10, 0, 0, 0) );
	buttonPanel.add(okButton);
	buttonPanel.add(cancelButton);

	//Put the panels in mainPanel, labels on left,
	//text fields on right, button down
	mainPanel.setBorder( BorderFactory.createEmptyBorder( 10, 10, 5, 10 ) );
	mainPanel.setLayout(new BorderLayout());
	mainPanel.add( myTableData, BorderLayout.CENTER);
	mainPanel.add( buttonPanel, BorderLayout.SOUTH );

	return mainPanel;
    }

    /**
     * Implementation of ActionListener interface.
     * Deals with the buttons :<br>
     * - ok :DialogGetEnregistrement::actionOk<br>
     * - cancel : does nothing and setsInvisible<br>
     */
    public void actionPerformed(ActionEvent event) {
	if (event.getActionCommand().equals("ok"))
	    {
		logger.debug("ok");
		actionOk();
		setVisible( false );
	    }
	else if (event.getActionCommand().equals("cancel"))
	    {
		logger.debug("cancel");
		actionCancel();
	    }
    }

    /**
     * Return the selected Enregistrement.
     * <p>
     * actionCancel is called is something goes wrong.
     */
    protected void actionOk()
    {
	logger.debug("getting selected from theGUITable");
	selectedEnregistrement = myTableData.selectedEnregistrement;
    }
			
    /**
     * Clean selection and sets visibility to false.
     */
    protected void actionCancel()
    {
	logger.debug("clearing selection");
	selectedEnregistrement = null;

	setVisible( false );
    }

    /**
     * Pop up DialogGetEnregistrement for selecting an Enregistrement.
     *
     * Clean selection and make Dialog visible
     */
    public Enregistrement askForEnregistrement()
    {
	// clean seletion
	selectedEnregistrement = null;
	
	setVisible( true );

	logger.debug("Selection should be done");
	return selectedEnregistrement;
    }

    // ---------- a Private Logger ---------------------
    private Logger logger;
    // --------------------------------------------------

} // DialogGetEnregistrement
