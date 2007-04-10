
package collector.data;

import java.io.*;
import java.util.*;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import org.xml.sax.Attributes;
/**
 * ElementFactory creates new Element from XML Events.
 *
 * @version 1.0
 * $Date: 2003/08/06$<br>
 * @author Alain$
 */

public class ElementFactory 
{
    /** Element can be an ElementStr */
    ElementStr myElementStr;

    /** A current Label */
    String myLabel;
    /** A current Type */
    String myType;

    /** boolean flag  */
    boolean inElementStr = false;

    /**
     * Creation
     */
    public ElementFactory() 
    {
	logger = Logger.getLogger(ElementFactory.class);
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

	str.append("\n");

	return str.toString();
    }

    /**
     * Decode startElemennt from XML
     */
    public void startElement( String qName,
			      Attributes attributes)
    {
	logger.debug("start element " + qName);
	myLabel = qName;
	// look for the type...
	if (attributes != null) {
	    myType = attributes.getValue( Element.ATT_ELEMENT_TYPE );
	    logger.debug("  of type=" + myType );
	    
	    // creating the right Element
	    if( myType.equals( Element.typeString )) {
		logger.debug("Create ElementStr");
		myElementStr = new ElementStr( myLabel );
		inElementStr = true;
	    }
	}

    }
    /**
     * Decode EndElement from XML.
     * 
     * @return A new Element created with data read
     */
    public Element endElement(String qName)
    {
	logger.debug("end element " + qName);
	logger.debug("with myType = " + myType );

	// ElementStr
	if( myType.equals( Element.typeString ) ) {
	    logger.debug("Send ElementStr");
	    inElementStr = false;
	    return myElementStr;
	}
	else {
	    logger.warn("Unknown type = " + myType );
	}
	return null;
    }
    /**
     * Decode character from XML
     */
    public void characters(char[] ch, int start, int length) 
    {
	logger.debug("characters: [" + new String(ch, start, length) +"]");
	
	// Element.data
	if ( inElementStr ){
	    logger.debug("Set myElementStr.data");
	    myElementStr.data = new String(ch, start, length );
	}
	else {
	    logger.debug("Doing nothing with data");
	}
    }
    
    
    // ---------- a Private Logger ---------------------
    private Logger logger;
    // --------------------------------------------------
} // ElementFactory
    








