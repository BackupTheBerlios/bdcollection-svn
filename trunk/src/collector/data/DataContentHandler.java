
package collector.data;

import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;
/**
 * To parse a XMLReader that should contain a Element
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
	Table myData = null;
	Header myHeader = null;
	String myLabel;
	String parent;

	/** A possible parent Table */
	Table parentTable = null;

	/** Need a EnregistrementFactory for parsing */
	EnregistrementFactory myEnregistrementFactory;
	/** Need a HeaderFactory for parsing */
	HeaderFactory myHeaderFactory;
	/** Can use a DataFactory for the Parent */
	DataContentHandler myParentFactory;

	/** are we reading a Table ? */
	boolean inTable = false;
	/** are we reading a Enregistrement ? */
	boolean inEnregistrement = false;
	/** are we reading a Header ? */
	boolean inHeader = false;
	/** are we reading a label ? */
	boolean inLabel = false;
	/** are we reading a parent ? */
	boolean inParent = false;



	/**
	 * Creation
	 */
	public DataContentHandler() 
	{
		logger = Logger.getLogger( "collector.data.XML.DataContentHandler" );
		parentTable = null;

		myHeaderFactory = null;
		myEnregistrementFactory = null;
		myParentFactory = null;

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
	 * Call to Factories.
	 *
	 * @see org.xml.sax.helpers.DefaultHandler
	 */
	public void startElement(java.lang.String uri, 
			java.lang.String localName, 
			java.lang.String qName,
			Attributes attributes)
	{
		logger.debug("start element " + qName);

		if( inTable ) {

			// in Parent
			if( inParent ) {
				myParentFactory.startElement( uri, localName, qName, attributes );
			}
			// in Header
			else if( inHeader ) {
				myHeaderFactory.startElement( qName, attributes );
			}
			// a Header ?
			else if( qName.equals( Element.TAG_ELEMENT_HEADER )) {
				logger.debug("Entering Header");
				inHeader = true;

				// is there a parentTable
				if( parentTable != null ) {
					myHeaderFactory = new HeaderFactory( parentTable.myHeader );
					myEnregistrementFactory = new EnregistrementFactory( parentTable );
				}
				else {
					myHeaderFactory = new HeaderFactory( null );
					myEnregistrementFactory = new EnregistrementFactory( null );
				}

				myHeaderFactory.startElement( qName, attributes );
			}
			else if(inEnregistrement) {
				myEnregistrementFactory.startElement( qName, attributes, myHeader );
			}
			// aN Element
			else if( qName.equals( Element.TAG_ELEMENT_ENREGISTREMENT )) {
				inEnregistrement = true;
				myEnregistrementFactory.startElement( qName, attributes, myHeader );
			}
			// a Label
			else if( qName.equals( Element.TAG_ELEMENT_LABEL )) {
				logger.debug("Reading Table:label");
				inLabel = true;
			}	
			// a Parent
			else if( qName.equals( Element.TAG_ELEMENT_PARENT_TABLE )) {
				logger.debug("Reading Table:parent");
				inParent = true;

				myParentFactory = new DataContentHandler();
			}
			else {
				logger.warn("Unknown element inTable:" + qName);
			}
		}
		// a Table
		else if( qName.equals( Element.TAG_ELEMENT_TABLE )) {
			logger.debug("Entering Table");
			inTable = true;
		}
		// Unknown
		else {
			logger.warn("Unknown element !!!");
		}
	}
	/**
	 * Should create Elements.
	 *
	 * @see org.xml.sax.helpers.DefaultHandler
	 */
	public void endElement(java.lang.String uri, 
			java.lang.String localName, 
			java.lang.String qName) 
	{
		logger.debug("end element " + qName);

		// a Table
		if( qName.equals( Element.TAG_ELEMENT_TABLE )) {
			logger.debug("Leaving Table");
			inTable = true;
		}
		else if( inTable ) {

			// end of Parent
			if( qName.equals( Element.TAG_ELEMENT_PARENT_TABLE )) {
				logger.debug("End Table:parent");
				inParent = false;

				parentTable = (Table) myParentFactory.getData();
				myData.setParent( parentTable );
				logger.debug( "------PARENT-----\n" + parentTable );
			}
			// in Parent
			if( inParent ) {
				myParentFactory.endElement( uri, localName, qName );
			}
			else if( qName.equals( Element.TAG_ELEMENT_HEADER )) {
				logger.debug("Sending Header");
				inHeader = false;
				myHeader = myHeaderFactory.endElement( qName );
				myData.setHeader( myHeader );
			}
			// in Header 
			else if( inHeader ) {
				myHeaderFactory.endElement( qName );
			}
			// end of Enregistrement
			else if( qName.equals( Element.TAG_ELEMENT_ENREGISTREMENT )) {
				logger.debug("Add Enregistrement");
				// can only be an Enregistrement
				inEnregistrement = false;
				Enregistrement tmpEnr = myEnregistrementFactory.endElement( qName );
				myData.add( tmpEnr, tmpEnr.key );
			}
			else if( inEnregistrement ) {
				myEnregistrementFactory.endElement( qName ) ;
			}
			// a Label
			else if( qName.equals( Element.TAG_ELEMENT_LABEL )) {
				logger.debug("End Table:label");
				inLabel = false;
				logger.debug("Creating Table");
				myData = new Table( myLabel );
			}
		}
		// unknown
		else {
			logger.warn("Unknown element");
		} 
	}
	/**
	 * Call to Factories.
	 *
	 * @see org.xml.sax.helpers.DefaultHandler
	 */ 
	public void characters(char[] ch, int start, int length) 
	{
		logger.debug("characters: [" + new String(ch, start, length) +"]");

		if (inEnregistrement) {  
			myEnregistrementFactory.characters( ch, start, length );
		}
		else if( inHeader ) {
			myHeaderFactory.characters( ch, start, length );
		}
		else if( inLabel ) {
			myLabel = new String(ch, start, length );
		}
		else if( inParent ) {
			myParentFactory.characters(ch, start, length );
		}
		else if (inTable) {
		}
		else {
			logger.warn( "### inLabel = " + inLabel );
			logger.warn( "### inEnregistrement = "+ inEnregistrement );
			logger.warn( "### inHeader = " + inHeader );
			logger.warn( "### inParent = " + inParent );
			logger.warn( "########## Don't know what to do" );
		}
	}

	// ---------- a Private Logger ---------------------
	private Logger logger;
	// --------------------------------------------------
} // DataContentHandler









