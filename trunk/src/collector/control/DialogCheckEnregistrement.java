
package collector.control;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import collector.data.Enregistrement;
import collector.data.View;

/**
 * DialogCheckEnregistrement will display the Enregistrement and those which
 * are close to it and ask for confirmation.
 * <p>
 * <b>Return</b> true or false to askOk
 * 
 * Buttons : Ok : confirm Enregistrement and return true.<br>
 *           Cancel : return false<br>
 * @version 1.0
 * $Date: 2004/05/16$<br>
 * @author Alain$ */

public class DialogCheckEnregistrement extends JDialog implements ActionListener {

    /** is 'ok' pressed */
    protected boolean isOk;
    
    /**
     * Creator with a Frame
     *
     * @param aFrame The parent Frame.
     * @param p_view The closest Enregistrement
     * @param p_enr The Enregistrement to confirm
     * @param p_name Title of Dialog
     */
    public DialogCheckEnregistrement( Frame aFrame, Enregistrement p_enr, View p_view, String p_name )
    {
	super( aFrame,
	       p_name,
	       true /* block input */);

	logger = Logger.getLogger(DialogCheckEnregistrement.class);
	logger.debug("Creation from a Frame");

	// the content
	setContentPane( buildEntry( p_enr, p_view ) );
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
     * @param p_view The closest Enregistrement
     * @param p_enr The Enregistrement to confirm
     * @param p_name Title of Dialog
     */
    public DialogCheckEnregistrement( Dialog aDialog, Enregistrement p_enr, View p_view, String p_name )
    {
	super( aDialog,
	       p_name,
	       true /* block input */);

	logger = Logger.getLogger(DialogCheckEnregistrement.class);
	logger.debug("Creation from a Dialog");

	// the content
	setContentPane( buildEntry( p_enr, p_view) );
	// sets the focus
	//.requestFocus();
	
	// set invisible
	setVisible(false);
	pack();
    }
    
    /**
     * Builds the DialogCheckEnregistrement GUI from the hardwired data.
     *
     * Warning message + p_enr + "is close to " + list.
     * And 2 buttons : ok, cancel.
     */ 
    protected JPanel buildEntry( Enregistrement p_enr, View p_view)
    {
	logger.debug("buildEntry");

	JPanel mainPanel = new JPanel();
	mainPanel.setLayout( new BoxLayout( mainPanel, BoxLayout.Y_AXIS));
	// Warning message
	JLabel warnLabel = new JLabel( "Attention le nouveal Enregistrement,");
	mainPanel.add( warnLabel );

	// The Enregistrement
	JTextField enrText = new JTextField( p_enr.displayData() );
	enrText.setEditable( false );
	mainPanel.add( enrText );

	// message
	JLabel warnLabel2 = new JLabel( "est très proche de : ");
	mainPanel.add( warnLabel2 );

	// The list of close match
	JTextArea listTextArea = new JTextArea( p_view.displaySortedConfidenceData() );
	listTextArea.setEditable( false );
	JScrollPane areaScrollPane = new JScrollPane( listTextArea );
	areaScrollPane.setPreferredSize(new Dimension(250, 250));
	mainPanel.add( areaScrollPane );

	// message
	JLabel warnLabel3 = new JLabel( "On le garde quand même ???");
	mainPanel.add( warnLabel3 );

	// Buttons
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
	//mainPanel.setBorder( BorderFactory.createEmptyBorder( 10, 10, 5, 10 ) );
	//mainPanel.setLayout(new BorderLayout());
	//mainPanel.add( myTableData, BorderLayout.CENTER);
	mainPanel.add( buttonPanel ); //, BorderLayout.SOUTH );

	return mainPanel;
    }

    /**
     * Implementation of ActionListener interface.
     * Deals with the buttons :<br>
     * - ok :DialogCheckEnregistrement::actionOk<br>
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
	logger.debug("confirm selection");
	isOk = true;
    }
			
    /**
     * Clean selection and sets visibility to false.
     */
    protected void actionCancel()
    {
	logger.debug("clearing selection");
	setVisible( false );
    }

    /** 
     * Ask for confirmation
     */
    public boolean askOk()
    {
	isOk = false;
	// wait for input
	setVisible( true );

	return isOk;
    }

    // ---------- a Private Logger ---------------------
    private Logger logger;
    // --------------------------------------------------

} // DialogCheckEnregistrement
