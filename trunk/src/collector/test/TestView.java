
import java.io.*;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import collector.data.*;

import com.megginson.sax.DataWriter;

//import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
//import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
//import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;
import javax.xml.parsers.SAXParser; 
import javax.xml.parsers.SAXParserFactory; 

/**
 * Test the View
 *
 * @version 1.0
 * $Date: 2003/11/12$<br>
 * @author Alain$
 */

class TestView
{
    Table myTable;
    View myView;

    /**
     * Creation
     */
    public TestView() 
    {
    }
    /**
     * test
     */
    public void test()
    {
 	try {
	
	    logger.info("--- Read ---");
	    TableFactory creator = new TableFactory();
	    Table myTable = creator.createFromFile( new File("../data/metatable.dta" ) );
	    logger.info("--- TABLE ----");
	    logger.info( "\n" + myTable.toString() );

	    myView = new View( myTable );

	    logger.info("--- TABLE ----");
	    logger.info( "\n" + myView.toString() );
	    logger.info("--- DISPLAY ----");
	    logger.info( "\n" + myView.displayData() );

 	} catch (Throwable t) {
 	    t.printStackTrace();
 	}
     }

    /**
     * Basic test with no GUI
     */
    public static void main(String[] args) 
    {
	// logger configuration 
	PropertyConfigurator.configure("../etc/log4j.config");
	logger = Logger.getLogger(TestView.class);
	
	TestView myTest = new TestView();
        myTest.test();
    }

    // ---------- a Private Logger ---------------------
    private static Logger logger;
    // --------------------------------------------------
} // TestView
    








