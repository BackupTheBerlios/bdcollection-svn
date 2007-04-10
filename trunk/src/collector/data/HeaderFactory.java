
package collector.data;

import org.apache.log4j.Logger;
import org.xml.sax.Attributes;

/**
 * Creates an Header from XML.
 *
 * @version 1.0
 * $Date: 2003/08/13$<br>
 * @author Alain$
 */

class HeaderFactory
{
    /** Data can only be a Header */
    Header myHeader;
    
    /** Are we in a Field */
    boolean inField = false;
    /** Are we in a Header */
    boolean inHeader = false;

    /** Need a FieldFactory for parsing */
    FieldFactory myFieldFactory;
    
    /**
     * Creation.
     *
     * @param p_parentHeader Can be used to create parent for Fields
     */
    public HeaderFactory( Header p_parentHeader ) 
    {
	logger = Logger.getLogger( "collector.data.XML.HeaderFactory" );
	myFieldFactory = new FieldFactory( p_parentHeader );
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
	
	str.append("HeaderFactory\n");
	
	return str.toString();
    }
    /**
     * Decode startElemennt from XML
     */
    public void startElement( String qName,
			      Attributes attributes)
    {
	logger.debug("start element " + qName);
	
	// in Field already ?
	if( inField ) {
	    myFieldFactory.startElement( qName, attributes );
	}
	// is it a Header ?
	else if( qName.equals( Element.TAG_ELEMENT_HEADER )) {
	    logger.debug("Entering Header");
	    myHeader = new Header();
	    inHeader = true;
	}
	// a Field ?
	else if( qName.equals( Element.TAG_ELEMENT_FIELD )) {
	    logger.debug("Entering Field");
	    inField = true;
	    myFieldFactory.startElement( qName, attributes );
	}
	else {
	    logger.warn("Don't know how to deal with tag");
	}
    }
    /**
     * Decode EndField from XML.
     * 
     * @return A new Field created with data read
     */
    public Header endElement(String qName)
    {
	logger.debug("end element " + qName);
	
	// in Field already ?
	if( inField ) {
	    myFieldFactory.endElement( qName );
	    if( qName.equals( Element.TAG_ELEMENT_FIELD )) {
		logger.debug("Adding Field");
		inField = false;
		myHeader.add( myFieldFactory.endElement( qName ) );
	    }
	}
	// is it a Header ?
	else if( qName.equals( Element.TAG_ELEMENT_HEADER ) ) {
	    logger.debug("Ending : Header");
	    inHeader = false;
	    return myHeader;
	}
	else {
	    logger.warn("Don't know how to deal with tag");
	}
	return null;
    }
    /**
     * Decode character from XML
     */
    public void characters(char[] ch, int start, int length) 
    {
	if( inField ) {
	    myFieldFactory.characters( ch, start, length );
	}
	else if (inHeader) {
	}
	else {
	    logger.warn( "### inField = " + inField );
	    logger.warn( "### inHeader = " + inHeader);
	    logger.warn( "########## Don't know what to do" );
	}
    }
    // ---------- a Private Logger ---------------------
    private Logger logger;
    // --------------------------------------------------
} // HeaderFactory









