
package collector.data;

import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
/**
 * EnregistrementFactory creates new Enregistrement from XML Events.
 *
 * @version 1.0
 * $Date: 2003/08/06$<br>
 * @author Alain$
 */

public class EnregistrementFactory 
{
    /** Enregistrement can be a Enregistrement */
    Enregistrement myEnregistrement;

    /** Need a CellFactory for parsing */
    CellFactory myCellFactory;

    /** boolean flag for Enregistrement */
    boolean inCell = false;
    /** boolean flage for Parent */
    boolean inParent = false;
    /** what we read for parent */
    int parentKey;
    /** a set of parent Enregistrement */
    Table parentTable;
    
    /** index of the data to fill */
    int cellIndex = 0;

    /**
     * Creation.
     *
     * @param p_parentTable is a possible table to get parent Enregistrement and Cell.
     */
    public EnregistrementFactory( Table p_parentTable ) 
    {
	logger = Logger.getLogger( "collector.data.XML.EnregistrementFactory" );
	parentTable = p_parentTable;
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

	str.append("EnregistrementFactory\n");

	return str.toString();
    }

    /**
     * Decode startElemennt from XML
     */
    public void startElement( String qName,
			      Attributes attributes,
			      Header p_header )
    {
	logger.debug("start element " + qName);
	
	// we are reading a Enregistrement 
	if( qName.equals( Element.TAG_ELEMENT_ENREGISTREMENT )) {
	    Integer theKey = new Integer( attributes.getValue( Element.ATT_ELEMENT_KEY ) );
	    myEnregistrement = new Enregistrement( p_header );
	    myEnregistrement.key = theKey.intValue();
	    cellIndex = 0;
	}
	else if( qName.equals( Element.TAG_ELEMENT_PARENT )) {
	    inParent = true;
	}
	else {
	    // reading a new Cell
	    inCell = true;
	    myCellFactory.startElement( qName, attributes,
					myEnregistrement.myHeader.getField( cellIndex ));
	}
    }
    /**
     * Decode EndEnregistrement from XML.
     * 
     * @return A new Enregistrement created with data read
     */
    public Enregistrement endElement(String qName)
    {
	logger.debug("end element " + qName);

	if( inCell ) {
	    // recieve new Cell
	    myEnregistrement.data[cellIndex] = myCellFactory.endElement( qName );
	    inCell = false;
	    cellIndex++;

	}
	else if( inParent ) {
	    logger.debug("end inParent");
	    inParent = false;
	    // time to set parent
	    Enregistrement parentEnr = null;
	    // check that possible parent is the good one if needed
	    if( parentKey != Element.NUL_INT ) {
		logger.debug("Looking for parent");
		if( parentTable != null ) {
		    parentEnr = parentTable.get( parentKey );
		    if( parentEnr != null ) {
			logger.debug("Set Enr no=" + parentKey + " as parent");
			myEnregistrement.setParent( parentEnr );
		    }
		    else {
			logger.error("wanted:" + parentKey + " not found" );
		    }
		}
		else {
		    logger.error("no possible parent");
		}
	    }
	    // now we know if we have a parent...
	    myCellFactory = new CellFactory( parentEnr );
	    logger.debug("end setParent");
	}
	else if( qName.equals( Element.TAG_ELEMENT_ENREGISTREMENT )) {
	    logger.debug("Send Enregistrement");
	    if( cellIndex != myEnregistrement.data.length ) {
		logger.warn( "Enregistrement has only "+cellIndex+ " Cells");
	    }
	    return myEnregistrement;
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
	
	if (inCell) {
	    logger.debug("Sent to myCellFactory" );
	    myCellFactory.characters( ch, start, length );
	}
	else if( inParent ) {
	    Integer strKey = new Integer( new String(ch, start, length) );
	    parentKey = strKey.intValue();
	}
	else {
	    logger.debug("Doing nothing with data");
	}
    }


    // ---------- a Private Logger ---------------------
    private Logger logger;
    // --------------------------------------------------
} // Enregistrement Factory
    








