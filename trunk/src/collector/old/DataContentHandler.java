
package collector.data;

import java.io.*;
import java.util.*;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.Attributes;
/**
 * To parse a XMLReader that should contain a ElementStr
 *
 * @version 1.0
 * $Date: 2003/08/06$<br>
 * @author Alain$
 */

public class DataContentHandler extends DefaultHandler
{

    /**
     * The data that will be read from message and retrieved through
     * <code>getData()</code>.
     */
    Element myData = null;

    /** Need a ElementFactory for parsing */
    ElementFactory myElementFactory = new ElementFactory();
    /** Need a HeaderFactory for parsing */
    HeaderFactory myHeaderFactory = new HeaderFactory();

    /** are we reading a Test ? */
    boolean inTest = false;
    /** are we reading an Element ? */
    boolean inElement = false;
    /** are we reading a Header ? */
    boolean inHeader = false;
    


    /**
     * Creation
     */
    public DataContentHandler() 
    {
	logger = Logger.getLogger(DataContentHandler.class);
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

	str.append("DataContentHandler\n");

	return str.toString();
    }

    /**
     * Return an Element right now.
     */
    public Element getData()
    {
	return myData;
    }

    /**
     * classic.
     *
     * @see org.xml.sax.helpers.DefaultHandler
     */
    public void startDocument() 
    {
	logger.debug( "----- startDocument -----" );
    }

    /**
     * classic.
     *
     * @see org.xml.sax.helpers.DefaultHandler
     */
    public void endDocument() 
    {
	logger.debug( "----- endDocument -----");
    }

    /**
     * Call to ElementFactory.
     *
     * @see org.xml.sax.helpers.DefaultHandler
     */
    public void startElement(java.lang.String uri, 
			     java.lang.String localName, 
			     java.lang.String qName,
			     Attributes attributes)
    {
	logger.debug("start element " + qName);

	// in Header already ?
	if( inHeader ) {
	    myHeaderFactory.startElement( qName, attributes );
	}
	// is it a Test ?
	else if( qName.equals("Test") ) {
	    logger.debug("Entering : Test");
	    inTest = true;
	}
	// a Header ?
	else if( qName.equals( Element.TAG_ELEMENT_HEADER )) {
	    logger.debug("Entering Header");
	    inHeader = true;
	    myHeaderFactory.startElement( qName, attributes );
	}
	// can only be a Element
	else {
	    inElement = true;
	    myElementFactory.startElement( qName, attributes );
	}
    }
    /**
     * Should create Element.
     *
     * @see org.xml.sax.helpers.DefaultHandler
     */
    public void endElement(java.lang.String uri, 
			   java.lang.String localName, 
			   java.lang.String qName) 
    {
	logger.debug("end element " + qName);
	
	// in Header already ?
	if( inHeader ) {
	    myHeaderFactory.endElement( qName );
	    if( qName.equals( Element.TAG_ELEMENT_HEADER )) {
		logger.debug("Sending Header");
		inHeader = false;
		myData = myHeaderFactory.endElement( qName );
	    }
	}
	// is it a Test ?
	else if( qName.equals("Test") ) {
	    logger.debug("Leaving : Test");
	    inTest = false;
	}
	// can only be a Element
	else {
	    inElement = false;
	    myData = myElementFactory.endElement( qName );
	} 
    }
    /**
     * Call to ElementFactory.
     *
     * @see org.xml.sax.helpers.DefaultHandler
     */ 
    public void characters(char[] ch, int start, int length) 
    {
	logger.debug("characters: [" + new String(ch, start, length) +"]");
	
	if (inElement) {  
	    myElementFactory.characters( ch, start, length );
	}
	else if( inHeader ) {
	    myHeaderFactory.characters( ch, start, length );
	}
	else if (inTest) {
	}
	else {
	    logger.warn( "### inElement = "+ inElement );
	    logger.warn( "### inHeader = " + inHeader );
	    logger.warn( "########## Don't know what to do" );
	}
    }
    
    // ---------- a Private Logger ---------------------
    private Logger logger;
    // --------------------------------------------------
} // DataContentHandler
    

  






