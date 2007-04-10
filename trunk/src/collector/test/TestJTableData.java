
import java.io.*;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

import collector.data.*;
import collector.gui.*;

/**
 * Test the JTableData
 *
 * @version 1.0
 * $Date: 2003/07/08$<br>
 * @author Alain$
 */

class TestJTableData
{
    static JFrame myFrame;
    static TestJTableData myTest;

    Table myData;
    JTableData myJTableData;

    Table myParent;
    Header myParentHeader;
    /**
     * Creation
     */
    public TestJTableData() 
    {
	logger = Logger.getLogger(TestJTableData.class);

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

	    logger.info( myData.displayData() );

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

	JTableData jtable = new JTableData( myData );
	p_frame.setContentPane( jtable );

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

	
	myTest = new TestJTableData();
	// create a GUI
	myTest.initGUI( myFrame );

        //Display the window.
        myFrame.pack();
        myFrame.setVisible(true);
    }

    // ---------- a Private Logger ---------------------
    private Logger logger;
    // --------------------------------------------------
} // TestJTableData
    








