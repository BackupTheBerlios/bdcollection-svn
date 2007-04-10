
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
 * Test the JTruc
 *
 * @version 1.0
 * $Date: 2003/07/08$<br>
 * @author Alain$
 */

class TestJTruc
{
    static JFrame myFrame;
    static TestJTruc myTest;

    Truc myTruc;
    JTruc myJTruc;

    /**
     * Creation
     */
    public TestJTruc() 
    {
	logger = Logger.getLogger(TestJTruc.class);

	myTruc = new Truc();
    }

    /**
     * Init the GUI
     */
    public void initGUI( JFrame p_frame)
    {
	myJTruc = new JTruc( myTruc );
	p_frame.setContentPane( myJTruc );
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
        myFrame = new JFrame("TestTruc");
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	
	myTest = new TestJTruc();
	// create a GUI
	myTest.initGUI( myFrame );

        //Display the window.
        myFrame.pack();
        myFrame.setVisible(true);
    }

    // ---------- a Private Logger ---------------------
    private Logger logger;
    // --------------------------------------------------
} // TestJTruc
    








