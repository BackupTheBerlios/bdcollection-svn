
import javax.swing.JFrame;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import collector.data.Element;
import collector.data.Field;
import collector.data.Header;
import collector.data.SearchOperator;
import collector.gui.JSearchOperator;
/**
 * Test the JHeader
 *
 * @version 1.0
 * $Date: 2003/07/08$<br>
 * @author Alain$
 */

class TestJSearchOperator
{
    static JFrame myFrame;
    static TestJSearchOperator myTest;

    Header myHeader;
    JSearchOperator myJSearchOperator;
    SearchOperator mySearchOperator;

    Field field1 = new Field( "un", Element.typeString, 1);
    Field field2 = new Field( "deux", Element.typeString, 2);
    Field field3 = new Field( "trois", Element.typeString, 3 );
    
    /**
     * Creation
     */
    public TestJSearchOperator() 
    {
	logger = Logger.getLogger(TestJSearchOperator.class);

	myHeader = new Header();
	myHeader.add( field1 );
	myHeader.add( field2 );
	myHeader.add( field3 );
    }

    /**
     * Init the GUI
     */
    public void initGUI( JFrame p_frame)
    {
	myJSearchOperator = new JSearchOperator( myHeader );
	p_frame.setContentPane( myJSearchOperator );
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
	    mySearchOperator = myJSearchOperator.getSearchOperator();
	    logger.info( mySearchOperator.toString() );
	    try {
		java.lang.Thread.sleep( 5000 );
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
        myFrame = new JFrame("TestJOperator");
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	
	myTest = new TestJSearchOperator();
	// create a GUI
	myTest.initGUI( myFrame );

        //Display the window.
        myFrame.pack();
        myFrame.setVisible(true);

	myTest.run();
    }

    // ---------- a Private Logger ---------------------
    private Logger logger;
    // --------------------------------------------------
} // TestJSearchOperator 
    








