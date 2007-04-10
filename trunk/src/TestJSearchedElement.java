
import javax.swing.JFrame;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import collector.data.Element;
import collector.data.Field;
import collector.data.SearchedElement;
import collector.gui.JSearchedElement;

/**
 * Test the JSearchedElement
 *
 * @version 1.0
 * $Date: 2004/04/30$<br>
 * @author Alain$
 */

class TestJSearchedElement
{
    static JFrame myFrame;
    static TestJSearchedElement myTest;

    static Field myField;
    static SearchedElement mySearchedElement;
    static JSearchedElement myJSearchedElement;


    /**
     * Creation
     */
    public TestJSearchedElement( JFrame p_frame ) 
    {
	logger = Logger.getLogger(TestJSearchedElement.class);

	myField = new Field( "truc", Element.typeString, 3 );
	// create a GUI
	initGUI( p_frame );

    }

    /**
     * Init the GUI
     */
    public void initGUI( JFrame p_frame)
    {
	myJSearchedElement = new JSearchedElement( myField );
	p_frame.setContentPane( myJSearchedElement );
    }

    /**
     * run testing.
     */
    public void run()
    {
	try {
	    java.lang.Thread.sleep( (long) 2000 );
	} catch (Throwable t) {
	    t.printStackTrace();
	}
	
	do {
	    mySearchedElement = myJSearchedElement.getSearchedElement();
	    logger.info( mySearchedElement.displayData() );
	    try {
		java.lang.Thread.sleep( 1000 );
	    } catch (Throwable t) {
		t.printStackTrace();
	    }	
	} 
	while( true );
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

	
	myTest = new TestJSearchedElement( myFrame);
        //Display the window.
	myFrame.pack();
        myFrame.setVisible(true);

	myTest.run();
        
    }

    // ---------- a Private Logger ---------------------
    private static Logger logger;
    // --------------------------------------------------
} // TestJSearchedElement
    








