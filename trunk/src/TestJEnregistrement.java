
import javax.swing.JFrame;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import collector.data.Element;
import collector.data.Enregistrement;
import collector.data.Field;
import collector.data.Header;
import collector.gui.JEnregistrement;

/**
 * Test the JEnregistrement
 *
 * @version 1.0
 * $Date: 2003/07/08$<br>
 * @author Alain$
 */

class TestJEnregistrement
{
    static JFrame myFrame;
    static TestJEnregistrement myTest;

    Enregistrement myEnregistrement;
    Header myHeader;

    JEnregistrement myJEnregistrement;

    /**
     * Creation
     */
    public TestJEnregistrement() 
    {
	logger = Logger.getLogger(TestJEnregistrement.class);

	myHeader = new Header();
	Field field1 = new Field( "un", Element.typeString, 1);
	Field field2 = new Field( "deux", Element.typeString, 2);
	Field field3 = new Field( "trois", Element.typeString, 3 );
	myHeader.add( field2 );
	myHeader.add( field1 );
	myHeader.add( field3 );

	myEnregistrement = new Enregistrement( myHeader );
	myEnregistrement.add( 0, "cell_zero", false );
	myEnregistrement.add( 1, "cell_un", false );
	myEnregistrement.add( 2, "cell_deux", false );

    }

    /**
     * Init the GUI
     */
    public void initGUI( JFrame p_frame)
    {
	myJEnregistrement = new JEnregistrement( myEnregistrement, true );
	p_frame.setContentPane( myJEnregistrement );
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
        myFrame = new JFrame("TestEnregistrement");
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	
	myTest = new TestJEnregistrement();
	// create a GUI
	myTest.initGUI( myFrame );

        //Display the window.
        myFrame.pack();
        myFrame.setVisible(true);
    }

    // ---------- a Private Logger ---------------------
    private Logger logger;
    // --------------------------------------------------
} // TestJEnregistrement
    








