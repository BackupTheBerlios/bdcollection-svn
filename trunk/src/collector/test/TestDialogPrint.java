
import java.io.*;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

import collector.data.*;
import collector.gui.*;
import collector.control.*;

/**
 * Test the DialogPrint
 *
 * @version 1.0
 * $Date: 2003/07/08$<br>
 * @author Alain$
 */

class TestDialogPrint
{
    static JFrame myFrame;
    static TestDialogPrint myTest;

    /**
     * Creation
     */
    public TestDialogPrint() 
    {
	logger = Logger.getLogger(TestDialog.class);
    }

    /**
     * Init the GUI
     */
    public void initGUI( JFrame p_frame) 
	throws IOException
    {

	//JTableData jtable = new JTableData( myData );
	//p_frame.setContentPane( jtable );

	// build the DialogPrint
	DialogPrint myDialogPrint = new DialogPrint( p_frame, "Print List");

	int result = myDialogPrint.askOptions();
	if( result == DialogPrint.CHOICE_OK ) {
	    if( myDialogPrint.isFileChosen() ) {
		logger.debug( "ChosenFile = " + myDialogPrint.getFileName() );
	    }
	    else {
		logger.debug( "Screen" );
	    }
	    logger.debug( "Option = " + myDialogPrint.getSortMethod() );
	}

    }


    /**
     * Create a top-level Frame and add a Context
     */
    public static void main(String[] args) throws IOException {
	// logger configuration 
	PropertyConfigurator.configure("../etc/log4j.config");

        //Make sure we have nice window decorations.
        //JFrame.setDefaultLookAndFeelDecorated(true);

        //Create and set up the window.
        myFrame = new JFrame("TestTableData");
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	
	myTest = new TestDialogPrint();
	// create a GUI
	myTest.initGUI( myFrame );

        //Display the window.
        myFrame.pack();
        myFrame.setVisible(true);
    }

    // ---------- a Private Logger ---------------------
    private Logger logger;
    // --------------------------------------------------
} // TestDialogPrint
    








