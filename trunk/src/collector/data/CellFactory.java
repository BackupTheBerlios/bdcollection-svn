
package collector.data;

import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
/**
 * CellFactory creates new Cell from XML Events.
 *
 * @version 1.0
 * $Date: 2003/08/06$<br>
 * @author Alain$
 */

public class CellFactory 
{
    /** Cell can be a CellStr */
    CellStr myCellStr;
    /** Cell can be a CellInt */
    CellInt myCellInt;

    /** A current Label */
    String myLabel;
    /** A current Type */
    String myType;
    /** Inherit ? */
    Boolean inherit;

    /** Possible parent Enregistrement */
    Enregistrement parentData;

    /** boolean flag for Str*/
    boolean inCellStr = false;
    /** boolean flag for Int */
    boolean inCellInt = false;

    /**
     * Creation.
     *
     * @param p_parentData Possible parent Enregistrement for finding Cell's parent.
     */
    public CellFactory( Enregistrement p_parentData ) 
    {
	//logger = Logger.getLogger(CellFactory.class);
	logger = Logger.getLogger( "collector.data.XML.CellFactory");
	parentData = p_parentData;
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
     * Decode startElement from XML.
     *
     * @param p_field Supposed field linked to the cell. It will not be checked.
     */
    public void startElement( String qName,
			      Attributes attributes,
			      Field p_field)
    {
	logger.debug("start element " + qName);
	// look for the type...
	if (attributes != null) {
	    myType = attributes.getValue( Element.ATT_ELEMENT_TYPE );
	    logger.debug("  of type=" + myType );
	    
	    inherit = new Boolean( attributes.getValue( Element.ATT_ELEMENT_INHERIT ));
	    logger.debug("  inherit=" + inherit );

	    // creating the right Cell
	    if( myType.equals( Element.typeString )) {
		logger.debug("Create CellStr");
		myCellStr = new CellStr( p_field );

		//check that possible parent is the good one if needed
		if( parentData != null ) {
		    if( p_field.hasParent() ) {
			Field tmpField = (Field) p_field.getParent();
			int indexParent = tmpField.index;
			myCellStr.setParent( parentData.data[indexParent] );
			// only if with a parent.
			myCellStr.setInherit( inherit.booleanValue() );
		    }
		}
		inCellStr = true;
	    }
	    else if( myType.equals( Element.typeInt )) {
		logger.debug("Create CellInt");
		myCellInt = new CellInt( p_field );

		//check that possible parent is the good one if needed
		if( parentData != null ) {
		    if( p_field.hasParent() ) {
			Field tmpField = (Field) p_field.getParent();
			int indexParent = tmpField.index;
			myCellInt.setParent( parentData.data[indexParent] );
			// only if with a parent.
			myCellInt.setInherit( inherit.booleanValue() );
		    }
		}
		inCellInt = true;
	    }
	    else {
		logger.warn( "Unknwon type : " + myType );
	    }
	}
    }
    /**
     * Decode EndElement from XML.
     * 
     * @return A new Cell created with data read
     */
    public Element endElement(String qName)
    {
	logger.debug("end element " + qName);
	logger.debug("with myType = " + myType );

	// ElementStr
	if( myType.equals( Element.typeString ) ) {
	    logger.debug("Send CellStr");
	    inCellStr = false;
	    return myCellStr;
	}
	else if( myType.equals( Element.typeInt ) ) {
	    logger.debug("Send CellInt");
	    inCellInt = false;
	    return myCellInt;
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
	
	// Cell.data
	if ( inCellStr ){
	    logger.debug("Set myCellStr.data");
	    myCellStr.setData( new String(ch, start, length ) );
	}
	else if ( inCellInt ) {
	    logger.debug("Set myCellInt.data");
	    Integer dummy = new Integer( new String(ch, start, length ) );
	    myCellInt.setData( dummy.intValue() );
	}
	else {
	    logger.debug("Doing nothing with data");
	}
    }
    
    
    // ---------- a Private Logger ---------------------
    private Logger logger;
    // --------------------------------------------------
} // CellFactory
    








