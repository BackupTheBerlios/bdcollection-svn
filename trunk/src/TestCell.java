
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import collector.data.CellStr;
import collector.data.DataContentHandler;
import collector.data.Element;
import collector.data.Field;
import collector.data.Header;

import com.megginson.sax.DataWriter;

/**
 * Test the CellStr
 *
 * @version 1.0
 * $Date: 2003/07/08$<br>
 * @author Alain$
 */

class TestCell 
{
    CellStr myCell;
    Field myField;
    Header myHeader;
    

    /**
     * Creation
     */
    public TestCell() 
    {
	myField = new Field( "truc", Element.typeString, 3 );
	myHeader = new Header();
	myHeader.add( myField );
	myCell = new CellStr( myField, "machin et bidule");

    }
    /**
     * test
     */
    public void test()
    {
	logger.info("--- Begin ---");
	logger.info( myCell.toString() );
	
	try {
	    StringWriter myWriter = new StringWriter();
	    DataWriter myDataWriter = new DataWriter( myWriter );
	    myDataWriter.setIndentStep(2);
	    myDataWriter.startDocument();
	    myDataWriter.startElement("Test");

	    myHeader.toXML( myDataWriter, myWriter );
	    myCell.toXML( myDataWriter, myWriter );

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
	logger = Logger.getLogger(TestCell.class);
	
	TestCell myTest = new TestCell();
        myTest.test();
    }

    // ---------- a Private Logger ---------------------
    private static Logger logger;
    // --------------------------------------------------
} // TestCell
    








