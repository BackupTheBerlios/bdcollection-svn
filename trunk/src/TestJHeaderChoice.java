
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import collector.data.Element;
import collector.data.Field;
import collector.data.Header;
import collector.gui.JHeaderChoice;

/**
 * Test the JHeaderChoice
 *
 * @version 1.0
 * $Date: 2003/07/08$<br>
 * @author Alain$
 */

class TestJHeaderChoice
{
    static JFrame myFrame;
    static TestJHeaderChoice myTest;

    JHeaderChoice myJHeaderChoice;

    Field field1 = new Field( "un", Element.typeString, 1);
    Field field2 = new Field( "deux", Element.typeString, 2);
    Field field3 = new Field( "trois", Element.typeString, 3 );

    Header source;
    Header dest;

    /**
     * Creation
     */
    public TestJHeaderChoice() 
    {
	logger = Logger.getLogger(TestJHeaderChoice.class);

	source = new Header();
	source.add( field1 );
	source.add( field2 );
	source.add( field1 );
	source.add( field2 );
	source.add( field1 );
	source.add( field2 );
	source.add( field1 );
	source.add( field2 );

	dest = new Header();
	dest.add( field3 );
	dest.add( field3 );
	dest.add( field3 );
	
    }

    /**
     * Init the GUI
     */
    public void initGUI( JFrame p_frame)
    {
	Container contentPane = p_frame.getContentPane();
	contentPane.setLayout( new BoxLayout( contentPane, BoxLayout.Y_AXIS ));

	// Need also a button for printing
	JButton printButton = new JButton( "Print");
	printButton.addActionListener( new printListener() );
	contentPane.add( printButton );

	myJHeaderChoice = new JHeaderChoice( source, dest );
	contentPane.add( myJHeaderChoice );

    }


    /**
     * Create a top-level Frame and add a Context
     */
    public static void main(String[] args) {
	// logger configuration 
	PropertyConfigurator.configure("./etc/log4j.config");

        //Make sure we have nice window decorations.
        //JFrame.setDefaultLookAndFeelDecorated(true);

        //Create and set up the window.
        myFrame = new JFrame("TestHeaderChoice");
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	
	myTest = new TestJHeaderChoice();
	// create a GUI
	myTest.initGUI( myFrame );

        //Display the window.
        myFrame.pack();
        myFrame.setVisible(true);
    }

    // ---------- ACTION --------------------------------
    /**
     * print lists
     */
    class printListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //This method can be called only if
            //there's a valid selection
            //so go ahead and remove whatever's selected.

	    logger.info( "-- Source --");
	    logger.info( source.toString() );

	    logger.info( "-- Dest --");
	    logger.info( dest.toString() );

	    
        }
    }

    // ---------- a Private Logger ---------------------
    private Logger logger;
    // --------------------------------------------------
} // TestJHeaderChoice
    








