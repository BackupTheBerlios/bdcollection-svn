
package collector.control;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

import collector.data.Table;
import collector.data.View;
import collector.gui.JSearchOperator;

/**
 * DialogSearch will ask to fill in a JSearchOperator for a given Table.
 *
 * Button : Search : return the result of the Search (a Table).<br>
 *          Cancel : return null.
 *
 * @version 1.0
 * $Date: 2004/05/04$<br>
 * @author Alain$ 
 */

public class DialogSearch extends JDialog implements ActionListener
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /** the Table where search is conducted */
    Table theTable;
    /** the JSearchOperator that will be filled */
    JSearchOperator theJSearchOperator;
    /** the result of the search */
    View result;
    
    /**
     * Creator with a Frame and a Table.
     */
    public DialogSearch( Frame aFrame, Table pTable )
    {
        super( aFrame, 
                "Critères de Recherche",
                true /* block input */);
        
        logger = Logger.getLogger(DialogSearch.class);
        logger.debug("Creation from a Frame");
        
        theTable = pTable;
        theJSearchOperator = null;
        result = null;
        
        // the content
        setContentPane( buildEntry() );
        
        pack();
        setVisible(true);
    }
    
    /**
     * Builds the DialogSearch GUI for theTable.
     */
    protected JPanel buildEntry()
    {
        JPanel mainPanel = new JPanel();
        mainPanel.setBorder( BorderFactory.createEmptyBorder( 10, 10, 5, 10 ) );
        mainPanel.setLayout(new BorderLayout());
        
        // A Title
        JLabel labelTitre = new JLabel( "Recherche dans la table : " + theTable.getLabel() );
        mainPanel.add( labelTitre, BorderLayout.NORTH );
        
        // The search
        theJSearchOperator = new JSearchOperator( theTable.myHeader );
        mainPanel.add( theJSearchOperator, BorderLayout.CENTER );
        
        // The buttons
        JButton okButton = new JButton( "Chercher" );
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
        mainPanel.add( buttonPanel, BorderLayout.SOUTH );
        
        return mainPanel;
    }
    
    /**
     * Implementation of ActionListener interface.
     * Deals with the buttons :<br>
     * - ok : Fill in SearchOperator, search theTable and builds result<br>
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
     * Fill in SearchOperator, search theTable and builds result.
     */
    protected void actionOk()
    {
        logger.debug("Search the Table " + theTable.getLabel() );
        result = theJSearchOperator.getSearchOperator().applyTo( theTable );
    }
    
    /**
     * Clean selection and sets visibility to false.
     */
    protected void actionCancel()
    {
        logger.debug("cancel search");
        setVisible( false );
    }
    
    /**
     * Get the result of the Search.
     */
    public View getResult()
    {
        return result;
    }
    
    // ---------- a Private Logger ---------------------
    private Logger logger;
    // --------------------------------------------------
    
} // DialogSearch 
