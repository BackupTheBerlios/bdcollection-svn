
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
 * Test the ElementStr
 *
 * @version 1.0
 * $Date: 2003/07/08$<br>
 * @author Alain$
 */

class TestElement 
{
    ElementStr myElement;
    

    /**
     * Creation
     */
    public TestElement() 
    {
	myElement = new ElementStr( "truc", "machin et bidule");
    }
    /**
     * test
     */
    public void test()
    {
	try {
	    // On affiche
	    logger.info("--- Begin ---");
	    logger.info( myElement.toString() );
	    
	    StringWriter myWriter = new StringWriter();
	    DataWriter myDataWriter = new DataWriter( myWriter );
	    myDataWriter.setIndentStep(2);
	    myDataWriter.startDocument();
	    myDataWriter.startElement("Test");

	    myElement.toXML( myDataWriter );

	    myDataWriter.endElement("Test");
	    myDataWriter.endDocument();
	    
	    logger.info( myWriter.toString() );

	    logger.info( "Parsing" );
	    
	    DataContentHandler handler = new DataContentHandler();
	    StringReader myReader = new StringReader( myWriter.toString() );
	    //Marche pas
	    //XMLReader xmlReader = XMLReaderFactory.createXMLReader();
	    SAXParserFactory factory = SAXParserFactory.newInstance();
	    SAXParser saxParser = factory.newSAXParser();
	    XMLReader xmlReader = saxParser.getXMLReader();
	    xmlReader.setContentHandler(handler);
	    xmlReader.parse( new InputSource(myReader) );

	    Element newElement = handler.getData();
	    logger.info( newElement.toString() );

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
	logger = Logger.getLogger(TestElement.class);
	
	TestElement myTest = new TestElement();
        myTest.test();
    }

    // ---------- a Private Logger ---------------------
    private static Logger logger;
    // --------------------------------------------------
} // TestElement
    








