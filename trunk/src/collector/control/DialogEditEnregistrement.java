
package collector.control;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import collector.data.Enregistrement;
import collector.data.Header;
import collector.data.Table;
import collector.gui.JEnregistrement;

/**
 * For a given enregistrement, can display/edit parentEnregistrement
 * and value for each field.
 *
 * @version 1.0
 * $Date: 2003/07/08$<br>
 * @author Alain$
 */

public class DialogEditEnregistrement extends JDialog implements ActionListener
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /** the current Enregistrement */
    protected Enregistrement myEnregistrement;
    /** does it have a parent */
    protected Table parentTable;
    /** the testField for parent */
    JTextField parentText;
    /** the JEnregistrement for data */
    JEnregistrement myJEnregistrement;
    /** is 'ok' pressed */
    protected boolean isOk;
    /** is 'check' pressed */
    protected boolean isCheck;
    protected JCheckBox isCheckBox;
    /** is 'next' pressed */
    protected boolean isNext;
    
    /**
     * Creation
     *
     * @param p_data Can be null (-> creation of a new Enregistrement )
     * @param p_check By default, do we ask for checking input
     * @param p_next Do we enable 'next' button
     */
    public DialogEditEnregistrement( Frame aFrame, Table p_parentTable, 
            Header p_header, Enregistrement p_data, String p_title,
            boolean p_check, boolean p_next) 
    {
        super( aFrame, 
                p_title,
                true /* block input */);
        
        logger = Logger.getLogger(DialogEditEnregistrement.class);
        logger.debug("Creation from a Frame");
        
        if( p_data != null ) {
            myEnregistrement = p_data;
        }
        else {
            myEnregistrement = new Enregistrement( p_header );
            
            // must add void data !!!
            buildEmptyEnregistrement( p_header );
        }
        isOk = false;
        isCheck = p_check;
        
        parentTable = p_parentTable;
        
        // the content
        setContentPane( buildEntry( myEnregistrement, p_parentTable, p_next));
        
        // set visible for testing
        pack();
        setVisible( false );
    }
    
    /** 
     * Ask for a new Enregistrement.
     */
    public boolean askNew( Enregistrement p_template)
    {
        isOk = false;
        isNext = false;
        //isCheck = false;
        // wait for input
        if( p_template != null ) {
            // use as the base for new enregistrement
            myJEnregistrement.update(p_template);
            if( myEnregistrement.hasParent() ) {    
                parentText.setText( myEnregistrement.getParent().displayData() );
                myJEnregistrement.update(); 
            }   
            else     {
                parentText.setText( "" );
            }   
        }                   
            
        
        setVisible( true );
        
        return (isOk | isNext);
    }
    /**
     * Ok was chosen?
     */
    public boolean isOk()
    {
        return isOk;
    }
    /**
     * Check was chosen?
     */
    public boolean isCheck()
    {
        return isCheck;
    }
    /**
     * Next was chosen?
     */
    public boolean isNext()
    {
        return isNext;
    }
    
    /** 
     * return the current Enregistrement.
     */
    public Enregistrement getEnregistrement()
    {
        return myEnregistrement;
    }
    
    /**
     * Builds the DialogEditEnregistrement GUI from the hardwired data.
     *
     * It consist of two parts : parent and fields.
     * And 3 buttons : check, ok, cancel.
     * @param p_next Do we enable 'next' buton
     */ 
    protected JPanel buildEntry( Enregistrement p_data, Table p_parentTable,
                boolean p_next)
    {
        logger.debug("buildEntry");
        logger.debug( p_data.displayData() );
        
        JPanel mainPanel = new JPanel();
        // the JEnregistrement
        myJEnregistrement = new JEnregistrement( p_data, true /*edit*/ );
        
        // parent Enregistrement
        JPanel parentPanel = new JPanel();
        parentPanel.setLayout( new BoxLayout( parentPanel, BoxLayout.X_AXIS ) );
        // une border!
        JLabel parentLabel = new JLabel( "Parent : ");
        parentPanel.add( parentLabel );
        parentText = new JTextField();
        parentText.setEditable( false );
        if( p_data.hasParent() ) {
            parentText.setText( p_data.getParent().displayData() );
        } 
        else {
            parentText.setText( "" );
        }
        parentPanel.add( parentText );
        JButton parentButton = new JButton( " ... " );
        parentButton.setActionCommand( "browse" );
        if( p_parentTable == null ) {
            parentButton.setEnabled( false );
        }
        parentButton.addActionListener( this );
        parentPanel.add( parentButton );
        
        // The buttons
        isCheckBox = new JCheckBox( "Vérifie", isCheck );
        isCheckBox.setMnemonic( KeyEvent.VK_V );
        isCheckBox.addItemListener( new MyCheckBoxListener() );
        
        //JButton checkButton = new JButton( "Check" );
        //checkButton.setMnemonic( KeyEvent.VK_C );
        //checkButton.setActionCommand("check");
        //checkButton.addActionListener( this );
        
        JButton okButton = new JButton( "Ok" );
        okButton.setMnemonic( KeyEvent.VK_O );
        okButton.setActionCommand("ok");
        okButton.addActionListener( this );
        
        JButton nextButton = new JButton( "Suivant" );
        nextButton.setMnemonic( KeyEvent.VK_S );
        nextButton.setActionCommand("next");
        nextButton.addActionListener( this );
        nextButton.setEnabled(p_next);
        
        JButton cancelButton = new JButton( "Annuler" );
        cancelButton.setMnemonic( KeyEvent.VK_A );
        cancelButton.setActionCommand("cancel");
        cancelButton.addActionListener( this );
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBorder( BorderFactory.createEmptyBorder( 10, 0, 0, 0) );
        buttonPanel.add(isCheckBox);
        //buttonPanel.add(checkButton);
        buttonPanel.add(okButton);
        buttonPanel.add(nextButton);
        buttonPanel.add(cancelButton);
        
        //Put the panels in mainPanel, labels on left,
        //text fields on right, button down
        mainPanel.setBorder( BorderFactory.createEmptyBorder( 10, 10, 5, 10 ) );
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add( parentPanel, BorderLayout.NORTH );
        mainPanel.add( myJEnregistrement, BorderLayout.CENTER);
        mainPanel.add( buttonPanel, BorderLayout.SOUTH );
        
        return mainPanel;
    }
    
    /**
     * Implementation of ActionListener interface.
     * Deals with the buttons :<br>
     * - browse : 
     * - ok : DialogEditEnregistrement::actionOk<br>
     * - cancel : <br> DialogEditEnregistrement::actionCancel
     */
    public void actionPerformed(ActionEvent event) {
        if (event.getActionCommand().equals("ok"))
        {
            logger.debug("ok");
            actionOk();
            setVisible( false );
        }
        else if (event.getActionCommand().equals("next"))
        {
            logger.debug("next");
            actionNext();
            setVisible( false );
        }
        else if (event.getActionCommand().equals("check"))
        {
            logger.debug("check");
            actionCheck();
            setVisible( false );
        }
        else if (event.getActionCommand().equals("cancel"))
        {
            logger.debug("cancel");
            actionCancel();
        }
        else if( event.getActionCommand().equals( "browse" )) {
            logger.debug("browse");
            actionBrowse();
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
            isCheck = isCheckBox.isSelected();
        }
    } // MyCheckBoxListener
    
    /** 
     * Raise Dialog for selecting an parent Enregistrement.
     */
    protected void actionBrowse()
    {
        // we know it can have parent
        logger.debug("select a valid parent");
        
        DialogGetEnregistrement tmpDialog = new DialogGetEnregistrement( this, parentTable, (Enregistrement) myEnregistrement.getParent() );
        Enregistrement selectedParent = tmpDialog.askForEnregistrement();
        
        // one parent selected ?
        if( selectedParent != null ) {
            logger.debug( "Browse : selectedParent\n" + selectedParent.toString());
            myEnregistrement.setParentRecursif( selectedParent );
        }
        
        if( myEnregistrement.hasParent() ) {
            parentText.setText( myEnregistrement.getParent().displayData() );
            myJEnregistrement.update();
        } 
        else {
            parentText.setText( "" );
        }
        
    }
    
    /**
     * Return the edited Enregistrement.
     * <p>
     * actionCancel is called is something goes wrong.
     */
    protected void actionOk()
    {
        logger.debug("return edited Enregistrement");
        isOk = true;
        isNext = false;
    }
    /**
     * Return the edited Enregistrement.
     * <p>
     * actionCancel is called is something goes wrong.
     */
    protected void actionNext()
    {
        logger.debug("return edited Enregistrement");
        isOk = false;
        isNext = true;
    }
    /**
     * Return the edited Enregistrement to be checked.
     * <p>
     * actionCancel is called is something goes wrong.
     */
    protected void actionCheck()
    {
        logger.debug("return edited Enregistrement for checking");
        isCheck = true;
    }			
    /*
     * Sets visibility to false.
     */
    protected void actionCancel()
    {
        setVisible( false );
        isCheck = false;
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
        
        str.append("DialogEditEnregistrement\n");
        
        return str.toString();
    }
    
    // ---------- a Private Logger ---------------------
    private Logger logger;
    // --------------------------------------------------
    
    /**
     * Builds an empty enregistrement.
     *
     * <b>Todo</b>: get Cell to return an "empty" cell
     */
    protected void buildEmptyEnregistrement( Header p_header )
    {
        for( int i = 0; i < p_header.theFields.size(); i++ ) {
            myEnregistrement.add( i, "", false /*no inherit*/ );
        }
    }
    
} // DialogEditEnregistrement









