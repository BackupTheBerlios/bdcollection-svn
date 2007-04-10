
package collector.data;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.log4j.Logger;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

/**
 * Creates Table from File.
 *
 * @version 1.0
 * $Date: 2003/09/01$<br>
 * @author Alain$
 */

public class TableFactory 
{

    /**
     * Creation
     */
    public TableFactory() 
    {
	logger = Logger.getLogger( "collector.data.XML.TableFactory" );
    }

    /**
     * Read a Table from File.
     *
     * @param parentTable is a possible parent for the new Table
     */
    public Table createFromFile( File file )
	throws IOException
    {
	FileReader fileReader = new FileReader( file );
	
	DataContentHandler handler = new DataContentHandler();

	try {
	    //Marche pas
	    //XMLReader xmlReader = XMLReaderFactory.createXMLReader();
	    SAXParserFactory factory = SAXParserFactory.newInstance();
	    SAXParser saxParser = factory.newSAXParser();
	    XMLReader xmlReader = saxParser.getXMLReader();
	    xmlReader.setContentHandler(handler);
	    xmlReader.parse( new InputSource( fileReader ) );
	}
	catch(SAXException t ) {
	    logger.error( "SAXException : " + t.toString() );
	}
	catch(javax.xml.parsers.ParserConfigurationException t ) {
	    logger.error( "ParserConfigurationException" + t.toString() );
	}
	return (Table) handler.getData();
    }

    /**
     * classic.
     *
     * Output format:<br>
     * 
     */
    public String toString()
    {
	StringBuffer str = new StringBuffer();

	str.append("TableFactory\n");

	return str.toString();
    }

    // ---------- a Private Logger ---------------------
    private Logger logger;
    // --------------------------------------------------
} // TableFactory
    








