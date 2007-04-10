
package collector.data;

import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
/**
 * FieldFactory creates new Field from XML Events.
 *
 * @version 1.0
 * $Date: 2003/08/06$<br>
 * @author Alain$
 */

public class FieldFactory 
{
    /** Field can be a Field */
    Field myField;

    /** A Header where to look for parent Field */
    Header parentHeader;
    /** name of parent Field */
    String parent;

    /** boolean flag for Field */
    boolean inLabel = false;
    boolean inTypeOfField = false;
    boolean inIndex = false;
    boolean inParent = false;

    /**
     * Creation
     *
     * @param p_parentHeader is a possible set of parent Fields.
     */
    public FieldFactory( Header p_parentHeader ) 
    {
	logger = Logger.getLogger( "collector.data.XML.FieldFactory" );
	
	parentHeader = p_parentHeader;
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

	str.append("FieldFactory\n");

	return str.toString();
    }

    /**
     * Decode startElemennt from XML
     */
    public void startElement( String qName,
			      Attributes attributes)
    {
	logger.debug("start element " + qName);
	
	// we are reading a Field 
	if( qName.equals( Element.TAG_ELEMENT_FIELD )) {
	    myField = new Field();
	}
	else if( qName.equals( Element.TAG_ELEMENT_TYPEFIELD )) {
	    inTypeOfField = true;
	}
	else if ( qName.equals( Element.TAG_ELEMENT_INDEX )) {
	    inIndex = true;
	}
	else if ( qName.equals( Element.TAG_ELEMENT_LABEL )) {
	    inLabel = true;
	}
	else if( qName.equals( Element.TAG_ELEMENT_PARENT )) {
	    inParent = true;
	}
	else {
	    logger.warn("Unknown Element");
	}
    }
    /**
     * Decode EndField from XML.
     * 
     * @return A new Field created with data read
     */
    public Field endElement(String qName)
    {
	logger.debug("end element " + qName);

	if( qName.equals( Field.TAG_ELEMENT_TYPEFIELD )) {
	    inTypeOfField = false;
	}
	else if ( qName.equals( Field.TAG_ELEMENT_INDEX )) {
	    inIndex = false;
	}
	else if ( qName.equals( Field.TAG_ELEMENT_LABEL )) {
	    inLabel = false;
	}
	else if( qName.equals( Element.TAG_ELEMENT_FIELD )) {
	    logger.debug("Send Field");
	    return myField;
	}
	else if( qName.equals( Element.TAG_ELEMENT_PARENT )) {
	    inParent = false;

	    // check that possible parent is the good one if needed
	    if( parent.equals(Element.NUL_STR) == false ) {
		logger.debug("Looking for parent");
		if( parentHeader != null ) {
		    int parentIndex = parentHeader.getFieldPosition( parent );
		    if( parentIndex != -1 ) {
			logger.debug("Set Field no=" + parentIndex + " as parent");
			myField.setParent( parentHeader.getField( parentIndex) );
		    }
		    else {
			logger.error("wanted:" + parent + " possible:" + parentIndex );
		    }
		}
		else {
		    logger.error("no possible parent");
		}
	    }
	}
	else {
	    logger.warn("Unknown Element");
	}
	return null;
    }
    /**
     * Decode character from XML
     */
    public void characters(char[] ch, int start, int length) 
    {
	logger.debug("characters: [" + new String(ch, start, length) +"]");
	
	if (inTypeOfField) {
	    logger.debug("Set myField.typeOfField" );
	    myField.typeOfField = new String(ch, start, length );
	}
	else if( inIndex ) {
	    logger.debug("Set myField.index");
	    Integer intVal = new Integer( new String(ch, start, length) );
	    myField.index = intVal.intValue();
	}
	else if( inLabel ) {
	    logger.debug("Set myField.label" );
	    myField.label = new String(ch, start, length );
	}
	else if( inParent ) {
	    logger.debug("Set parentField Name");
	    parent = new String(ch, start, length );
	}
	else {
	    logger.debug("Doing nothing with data");
	}
    }


    // ---------- a Private Logger ---------------------
    private Logger logger;
    // --------------------------------------------------
} // Field Factory
    








