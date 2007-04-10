
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
 * Test the Dialog
 *
 * @version 1.0
 * $Date: 2003/07/08$<br>
 * @author Alain$
 */

class TestDialogEditEnregistrement
{
    static JFrame myFrame;
    static TestDialogEditEnregistrement  myTest;

    Table myData;
    JTableData myJTableData;

    Table myParent;
    Header myParentHeader;
    /**
     * Creation
     */
    public TestDialogEditEnregistrement() 
    {
	logger = Logger.getLogger(TestDialogEditEnregistrement.class);

	// Creation of a parent Table
	myParentHeader = new Header();
	Field parentField1 = new Field( "unParent", Element.typeString, 0);
	myParentHeader.add( parentField1 );

	myParent = new Table( "ZeBigBrother" );
	myParent.setHeader( myParentHeader );

	Enregistrement parentEnr1 = new Enregistrement( myParentHeader );
	parentEnr1.key = 11;
	parentEnr1.add( 0, "parent_zero", true );
	myParent.add( parentEnr1 );

	try {
	    TableFactory creator = new TableFactory();
	    myData= creator.createFromFile( new File("../data/table.dta"), myParent);

	    logger.info( "----------------- READ ------------------" );
	    logger.info( myData.toString() );

	} catch (Throwable t) {
 	    t.printStackTrace();
 	}
	
    }

    /**
     * Init the GUI
     */
    public void initGUI( JFrame p_frame) 
	throws IOException
    {

	//JTableData jtable = new JTableData( myData );
	//p_frame.setContentPane( jtable );

	// build the Dialog
	logger.info( myData.getAt(0).toString() );

	DialogEditEnregistrement myDialog = new DialogEditEnregistrement( p_frame, myData.myHeader, myData.getAt(0) );
	/*while( true ) {
	    Enregistrement tmpEnr = myDialog.askForEnregistrement();
	    if( tmpEnr != null ) {
		logger.info("------ RESULTAT -------");
		logger.info( tmpEnr.displayData() );
	    }
	    else {
		logger.info("------ RESULTAT : null ");
	    }
	    }*/

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

	
	myTest = new TestDialogEditEnregistrement();
	// create a GUI
	myTest.initGUI( myFrame );

        //Display the window.
        myFrame.pack();
        myFrame.setVisible(true);
    }

    // ---------- a Private Logger ---------------------
    private Logger logger;
    // --------------------------------------------------
} // TestDialogEditEnregistrement
    





