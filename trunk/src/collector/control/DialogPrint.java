package collector.control;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

/**
 * Ask for various print options.
 *
 * Screen ou File, Sorted or 'As is'.
 *
 * @version 1.0
 * $Date: 2004/05/13$<br>
 * @author Alain
 */

public class DialogPrint extends JDialog implements ActionListener
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /* kind of sorting */
    /** no sorting */
    public static final int SORT_NULL = 0;
    /** sort order by each parent Enregistrement */
    public static final int SORT_PARENT = 1;
    /** sort order by "Series" -- SPECIFIC BD */
    public static final int SORT_BD = 2;
    
    /* kind of file */
    /** raw TXT */
    public static final int FILE_RAW = 10;
    /** PDF */
    public static final int FILE_PDF = 11;
    
    /** Width of the fields */
    final static int WIDTH_FIELD = 25;
    
    /** Chosen button : OK*/
    public final static int CHOICE_OK = 10;
    /** Chosen button : CANCEL */
    public final static int CHOICE_CANCEL = 11;
    /** the choice */
    int chosenButton;
    
    /** flag for File selection */
    boolean flagFile;
    /** flag for Sorted listing */
    int sortChoice;
    /** the name of the chosen File */
    String fileName;
    /** the file chosen */
    File theFile;
    /** the directory */
    File theDir;
    
    /** to get a fileName */
    JTextField fileTextField;
    /** to edit the fileName */
    JButton editButton;
    
    /**
     * Creation
     */
    public DialogPrint( Frame aFrame, String p_title )
    {
        super( aFrame, 
                p_title,
                true /* block input */);
        
        logger = Logger.getLogger(DialogPrint.class);
        logger.debug("Creation from a Frame");
        
        flagFile = false;
        sortChoice = SORT_NULL;
        fileName = null;
        theFile = null;
        theDir = null;
        
        // the content
        setContentPane( buildEntry());
        
        // set visible for testing
        pack();
        setVisible( false );
    }
    
    /**
     * Ask for popup and option to be chosed.
     */
    public int askOptions()
    {
        setVisible( true );
        return chosenButton;
    }
    
    /**
     * Ask if File was chosen.
     */
    public boolean isFileChosen()
    {
        return flagFile;
    }
    /**
     * Ask for the chosen fileName.
     */
    public String getFileName()
    {
        return fileName;
    }
    /**
     * Ask for the chosen file.
     */
    public File getFile()
    {
        return theFile;
    }/**
     * Ask for sorting method.
     */
    public int getSortMethod()
    {
        return sortChoice;
    }
    
    
    /**
     * Builds the DialogPrint GUI.
     *
     * It consist of two options lines: screen|file and sort....
     * And 2 buttons : print, cancel.
     */ 
    protected JPanel buildEntry()
    {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout( new BoxLayout( mainPanel, BoxLayout.Y_AXIS ) );
        mainPanel.setBorder( BorderFactory.createEmptyBorder(10, 10, 10, 10) );
        
        // Screen of File
        JPanel mediaPanel = new JPanel();
        mediaPanel.setLayout( new BoxLayout( mediaPanel, BoxLayout.X_AXIS ) );
        
        //label
        JLabel mediaLabel = new JLabel( "Sortie : " );
        mediaPanel.add( mediaLabel );
        // 2 Radion Buttons
        JRadioButton screenButton = new JRadioButton( "Ecran");
        screenButton.setActionCommand("screenOutput");
        screenButton.setSelected(true);
        screenButton.addActionListener( this );
        mediaPanel.add( screenButton );
        JRadioButton fileButton = new JRadioButton( "Fichier");
        fileButton.setActionCommand("fileOutput");
        fileButton.setSelected(false);
        fileButton.addActionListener( this );
        mediaPanel.add( fileButton );
        ButtonGroup groupMedia = new ButtonGroup();
        groupMedia.add( screenButton );
        groupMedia.add( fileButton );
        // A Text field
        fileTextField = new JTextField( WIDTH_FIELD );
        fileTextField.setEditable( false );
        fileTextField.setEnabled( false );
        mediaPanel.add( fileTextField );
        // A Button for editing file
        mediaPanel.add( Box.createRigidArea( new Dimension( 5, 0 )));
        editButton = new JButton( " ... " );
        editButton.setEnabled( false );
        editButton.setActionCommand( "editFile" );
        editButton.addActionListener(this);
        mediaPanel.add( editButton );
        
        mainPanel.add( mediaPanel );
        
        // Kind of sorting operation
        JPanel sortPanel = new JPanel();
        sortPanel.setLayout( new BoxLayout( sortPanel, BoxLayout.X_AXIS ) );
        //label
        JLabel sortLabel = new JLabel( "Tri : " );
        sortPanel.add( sortLabel );
        // 3 Radion Buttons
        JRadioButton noSortButton = new JRadioButton( "Aucun");
        noSortButton.setActionCommand("noSortChoice");
        noSortButton.setSelected(false);
        noSortButton.addActionListener( this );
        sortPanel.add( noSortButton );
        JRadioButton serieSortButton = new JRadioButton( "Par Série");
        serieSortButton.setActionCommand("serieSortChoice");
        serieSortButton.setSelected(true);
        serieSortButton.addActionListener( this );
        sortPanel.add( noSortButton );
        JRadioButton parentSortButton = new JRadioButton( "Par Parent");
        parentSortButton.setActionCommand("parentSortChoice");
        parentSortButton.setSelected(false);
        parentSortButton.addActionListener( this );
        sortPanel.add( parentSortButton );
        sortPanel.add(Box.createHorizontalGlue());
        sortChoice = SORT_BD;
        
        ButtonGroup groupSort = new ButtonGroup();
        groupSort.add( noSortButton );
        groupSort.add( parentSortButton );
        
        mainPanel.add( sortPanel );
        
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
        
        mainPanel.add( buttonPanel );
        
        return mainPanel;
    }
    
    /**
     * Implementation of ActionListener interface.
     *
     * Deals with the radio buttons<br>
     * Deals with the buttons :<br>
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
        else if (event.getActionCommand().equals("cancel"))
        {
            logger.debug("cancel");
            actionCancel();
        }
        // Screen or File
        else if( event.getActionCommand().equals( "screenOutput" )) {
            logger.debug("screen");
            fileTextField.setEnabled( false );
            editButton.setEnabled( false );
            flagFile = false;
        }
        else if( event.getActionCommand().equals( "fileOutput" )) {
            logger.debug("file");
            if( theFile == null ) {
                selectFile();
            }
            editButton.setEnabled( true );
            flagFile = true;
        }
        else if( event.getActionCommand().equals( "editFile" )) {
            logger.debug( "edit" );
            selectFile();
            flagFile = true;
        }
        // Kind of sorting
        else if( event.getActionCommand().equals( "noSortChoice" )) {
            logger.debug("no sort");
            sortChoice = SORT_NULL;
        }
        else if( event.getActionCommand().equals( "parentSortChoice" )) {
            logger.debug("parent sort");
            sortChoice = SORT_PARENT;
        }
        else if( event.getActionCommand().equals( "serieSortChoice" )) {
            logger.debug("serie sort");
            sortChoice = SORT_BD;
        }
    }
    
    /**
     * Select a fileName from current one.
     */
    void selectFile()
    {
        JFileChooser aFileChooser;
        if( theFile != null ) {
            // Popup a File Dialog Choser to fill in TextFile
            aFileChooser  = new JFileChooser();
            aFileChooser.setCurrentDirectory( theDir );
            aFileChooser.setSelectedFile( theFile );
        }
        else {
            // Popup a File Dialog Choser to fill in TextFile
            aFileChooser = new JFileChooser("../data");
        }
        PrintFilter myPrintFilter = new PrintFilter();
        // set the right Filter
        aFileChooser.addChoosableFileFilter( myPrintFilter );
        aFileChooser.setFileFilter( myPrintFilter );
        // ask the user for a file
        int returnVal = aFileChooser.showSaveDialog( this );
        
        // remove the File Filter
        aFileChooser.removeChoosableFileFilter( myPrintFilter );
        
        logger.debug("Choice made ");
        // check it was a valide choice
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            theDir = aFileChooser.getCurrentDirectory();
            theFile = aFileChooser.getSelectedFile();
            fileName = aFileChooser.getName( theFile );
            logger.debug( "new file : " + fileName );
            fileTextField.setText( fileName );
            aFileChooser.
        }
    }
    
    /**
     * Set up fileName is flagFile is set.
     */
    protected void actionOk()
    {
        logger.debug("set up fileName");
        chosenButton = CHOICE_OK;
    }
    
    /*
     * Sets visibility to false.
     */
    protected void actionCancel()
    {
        setVisible( false );
        chosenButton = CHOICE_CANCEL;
    }
    
    /**
     * Class needed in order to filter files for the JFileChoser.
     *
     * <p>
     * File recognised as Database: dir, '.txt'.
     */
    class PrintFilter extends javax.swing.filechooser.FileFilter {
        
        /**
         * Filter files.
         *
         * Return true for directories and '.txt' files
         */ 
        public boolean accept(File f) {
            if (f.isDirectory()) {
                return true;
            }
            
            String extension = Utils.getExtension(f);
            if (extension != null) {
                if (extension.equals( Utils.txt ) ) {
                    return true;
                } else {
                    return false;
                }
            }
            
            return false;
        }
        
        /**
         * The description of this filter.
         */
        public String getDescription() {
            return "Text Files *.txt";
        }
    } // PrintFilter
    
    // ---------- a Private Logger ---------------------
    private Logger logger;
    // --------------------------------------------------
} // DialogPrint
