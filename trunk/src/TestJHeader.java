
import javax.swing.JFrame;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import collector.data.Element;
import collector.data.Field;
import collector.data.Header;
import collector.gui.JHeaderList;
/**
 * Test the JHeader
 *
 * @version 1.0
 * $Date: 2003/07/08$<br>
 * @author Alain$
 */

class TestJHeader
{
    static JFrame myFrame;
    static TestJHeader myTest;

    Header myHeader;
    JHeaderList myJHeader;

    Field field1 = new Field( "un", Element.typeString, 1);
    Field field2 = new Field( "deux", Element.typeString, 2);
    Field field3 = new Field( "trois", Element.typeString, 3 );
    
    /**
     * Creation
     */
    public TestJHeader() 
    {
	logger = Logger.getLogger(TestJHeader.class);

	myHeader = new Header();
	myHeader.add( field1 );
	myHeader.add( field2 );
	myHeader.add( field3 );
	myHeader.add( field1 );
	myHeader.add( field2 );
	myHeader.add( field3 );
	myHeader.add( field1 );
	myHeader.add( field2 );
	myHeader.add( field3 );
	myHeader.add( field1 );
	myHeader.add( field2 );
	myHeader.add( field3 );
    }

    /**
     * Init the GUI
     */
    public void initGUI( JFrame p_frame)
    {
	myJHeader = new JHeaderList( myHeader );
	p_frame.setContentPane( myJHeader );
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
        myFrame = new JFrame("TestHeader");
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	
	myTest = new TestJHeader();
	// create a GUI
	myTest.initGUI( myFrame );

        //Display the window.
        myFrame.pack();
        myFrame.setVisible(true);
    }

    // ---------- a Private Logger ---------------------
    private Logger logger;
    // --------------------------------------------------
} // TestJHeader 
    








