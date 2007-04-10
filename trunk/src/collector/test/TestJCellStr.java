
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
 * Test the JCellStr
 *
 * @version 1.0
 * $Date: 2003/07/08$<br>
 * @author Alain$
 */

class TestJCellStr
{
    static JFrame myFrame;
    static TestJCellStr myTest;

    Field myField;
    CellStr myCellStr;
    JCellStr myJCellStr;


    /**
     * Creation
     */
    public TestJCellStr() 
    {
	logger = Logger.getLogger(TestJCellStr.class);

	myField = new Field( "truc", Element.typeString, 3 );
	myCellStr = new CellStr( myField, "machin et bidule");
    }

    /**
     * Init the GUI
     */
    public void initGUI( JFrame p_frame)
    {
	myJCellStr = new JCellStr( myCellStr );
	p_frame.setContentPane( myJCellStr );
    }


    /**
     * Create a top-level Frame and add a Context
     */
    public static void main(String[] args) {
	// logger configuration 
	PropertyConfigurator.configure("../etc/log4j.config");

        //Make sure we have nice window decorations.
        //JFrame.setDefaultLookAndFeelDecorated(true);

        //Create and set up the window.
        myFrame = new JFrame("TestCellStr");
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	
	myTest = new TestJCellStr();
	// create a GUI
	myTest.initGUI( myFrame );

        //Display the window.
        myFrame.pack();
        myFrame.setVisible(true);
    }

    // ---------- a Private Logger ---------------------
    private Logger logger;
    // --------------------------------------------------
} // TestJCellStr
    








