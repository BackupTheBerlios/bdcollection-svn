
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
 * Test the Header
 *
 * @version 1.0
 * $Date: 2003/07/08$<br>
 * @author Alain$
 */

class TestHeader 
{
    Header myHeader;
    

    /**
     * Creation
     */
    public TestHeader() 
    {
	myHeader = new Header();
    }
    /**
     * test
     */
    public void test()
    {

	Field field1 = new Field( "un", Element.typeString, 1);
	Field field2 = new Field( "deux", Element.typeString, 2);
	Field field3 = new Field( "trois", Element.typeString, 3 );

	try {
	    // On affiche
	    logger.info("--- Begin ---");
	    logger.info( myHeader.toString() );

	    myHeader.add( field2 );
	    myHeader.add( field1 );
	    myHeader.add( field3, 1 );
	    myHeader.add( field3, 5 );
	    logger.info("--- add1 ---");
	    logger.info( myHeader.toString() );

	    myHeader.remove( "trois" );
	    myHeader.remove( 1 );
	    logger.info("--- remove ---");
	    logger.info( myHeader.toString() );
	    
	    StringWriter myWriter = new StringWriter();
	    DataWriter myDataWriter = new DataWriter( myWriter );
	    myDataWriter.setIndentStep(2);
	    myDataWriter.startDocument();
	    myDataWriter.startElement("Test");

	    myHeader.toXML( myDataWriter );

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

	    Element newHeader = handler.getData();
	    logger.info( newHeader.toString() );
	    
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
	logger = Logger.getLogger(TestHeader.class);
	
	TestHeader myTest = new TestHeader();
        myTest.test();
    }

    // ---------- a Private Logger ---------------------
    private static Logger logger;
    // --------------------------------------------------
} // TestHeader
    








