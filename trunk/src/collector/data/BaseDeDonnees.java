
package collector.data;

import java.io.File;
import java.util.Iterator;

import org.apache.log4j.Logger;


/**
 * Template for all classes
 *
 * @version 1.0
 * $Date: 2003/11/12$<br>
 * @author Alain$
 */

public class BaseDeDonnees 
{

    /** FileName associated */
    public File fileDesc;
    
    /** One Table */
    public Table basicTable;
    /** The parent Table */
    public Table parentTable;
    /** The complete Table */
    public Table completeTable;
    
    /** needed to make new Enregistrement for completeTable */
    Header fromParentHeader;

    /**
     * Creation
     */
    public BaseDeDonnees() 
    {
	logger = Logger.getLogger(BaseDeDonnees.class);
    fileDesc = null;
    }

    /**
     * classic.
     *
     * Output format:<br>
     * basicTable<br>
     * parenTable<br>
     * completeTable
     * 
     */
    public String toString()
    {
	StringBuffer str = new StringBuffer();

    if( fileDesc != null ) {
        str.append( "-- " + fileDesc.getPath() + " --");
    }
    
	str.append("\n");

	str.append("--- TABLE ---\n" + basicTable.toString() );
	if( parentTable != null ) {
	    str.append("--- ParentTable ---\n" + parentTable.displayData() );
	}
	str.append("--- BasicTable ---\n" + basicTable.displayData() );
	str.append("\n--- CompleteTable ---\n" + completeTable.displayData() );

	return str.toString();
    }

    // ---------- ACTIONS -------------------------------
    /**
     * Read from a file and create tables.
     *
     * basicTable is read from file. Then parentTable is set up.
     * completeTable is then constructed with all fields from both table 
     * (except when they overlap).
     *
     * @todo If can't read p_filename.
     */
    public void readFromFile( String p_filename )
    {
	// set basicTable
	try {
	    TableFactory creator = new TableFactory();
        fileDesc = new File( p_filename );
	    basicTable = creator.createFromFile( fileDesc );
	} catch (Throwable t) {
        fileDesc = null;
 	    t.printStackTrace();
	}
	// set parentTable
	if( basicTable.hasParent() ) {
	    parentTable = (Table) basicTable.getParent();
	}
	else {
	    parentTable = null;
	}

	// prepare complete Table
	// fromParentHeader is the list of Fields kept from parentTable.
	fromParentHeader = new Header();
	// add all Fields from parent
	for( int i=0; i < parentTable.myHeader.theFields.size(); i++ ) {
	    //logger.debug("trying to add " + parentTable.myHeader.getField( i ).toString() );
	    fromParentHeader.add( parentTable.myHeader.getField( i ) );
	    //logger.debug("result in\n" + fromParentHeader.toString() );
	}
	// remove the Field that are specialized in basicTable
	for( int i=0; i < basicTable.myHeader.theFields.size(); i++ ) {
	    Field tmpField = basicTable.myHeader.getField( i );
	    if( tmpField.hasParent() ) {
		fromParentHeader.remove( tmpField.getParent().getLabel() );
	    }
	}
	logger.debug("Fields kept from parentTable: \n" + fromParentHeader.toString());
	
	// now we can compute the Header of the complete Table
	Header completeHeader = new Header();
	// add the Fields kept from parentTable
	for( int iField = 0; iField < fromParentHeader.theFields.size(); iField++ ) {
	    Field current = (Field) fromParentHeader.theFields.get(iField);
	    /*Field newField = new Field( current.getLabel(), current.getTypeOfField(), -1 );
	    if( current.hasParent() ) {
		newField.setParent( current.getParent() );
		}*/
	    Field newField = new Field( 0 /* tmp rank*/, current );
	    completeHeader.add( newField );
	}
	// then from basicTable
	for(  int iField = 0; iField < basicTable.myHeader.theFields.size(); iField++ ) {
	    Field current = (Field) basicTable.myHeader.theFields.get(iField);
	    /*Field newField = new Field( current.getLabel(), current.getTypeOfField(), -1 );
	    if( current.hasParent() ) {
		newField.setParent( current.getParent() );
		}*/
	    Field newField = new Field( 0 /* tmp rank */, current );
	    completeHeader.add( newField );
	}
	completeHeader.recomputeIndex();
	logger.debug("Fields of completeTable: \n" + completeHeader.toString());

	// then the Enregistrement
	completeTable = new Table( "Complete" );
	completeTable.setHeader( completeHeader );
	completeTable.setParent( parentTable );
	for( Iterator iEnr = basicTable.listEnregistrement.iterator(); iEnr.hasNext(); ) {

	    Enregistrement current = (Enregistrement) iEnr.next();
	    logger.debug( "Creating complete from " + current.displayData() );
	    addToComplete( current );
	    logger.debug("actual version of completeTable\n"+completeTable.displayData() );
	}

    }
    /**
     * Add a new Enregsitrement to complete from one 'basic'.
     */
    void addToComplete( Enregistrement p_basicEnr )
    {
	    Enregistrement newEnr = new Enregistrement( completeTable.myHeader );
	    newEnr.key = p_basicEnr.key;
	    int indexData = 0;

	    // first, element from parent
	    // Every Enregistrement has a parent
	    if( p_basicEnr.hasParent() == false ) {
		logger.error( "p_basicEnr has no parent");
	    }
	    Enregistrement currentParent = (Enregistrement) p_basicEnr.getParent();
	    newEnr.setParent( currentParent );
	    for( int iData = 0; iData < fromParentHeader.theFields.size(); iData++ ) {
		Field fieldParent = fromParentHeader.getField( iData );
		logger.debug( "newEnr.add("+indexData+", "+ currentParent.data[ fieldParent.getIndex() ].displayData()+", false)");
		//newEnr.add( indexData, currentParent.data[ fieldParent.getIndex() ].displayData(), false /* no inherit */ );
		newEnr.add( indexData, currentParent, fieldParent.getIndex() );
		indexData ++;
	    }
	  
	    // then from basic
	    for( int iData = 0; iData < basicTable.myHeader.theFields.size(); iData++ ) {
		Field fieldCurrent = basicTable.myHeader.getField( iData );
		logger.debug( "newEnr.add("+indexData+", "+p_basicEnr.data[ fieldCurrent.getIndex() ].displayData()+", "+p_basicEnr.data[ fieldCurrent.getIndex() ].isInherit()+")");
		//newEnr.add( indexData, p_basicEnr.data[ fieldCurrent.getIndex() ].displayData(), current.data[ fieldCurrent.getIndex() ].isInherit() );
		newEnr.add( indexData, p_basicEnr, fieldCurrent.getIndex() );
		indexData ++;
	    }

	    // then add to Table
	    completeTable.add( newEnr, newEnr.key );

    }
    

    /**
     * Add an element to either parent or basic Table.
     */
    public void addEnregistrement( Table p_table, Enregistrement p_enr )
    {
	if (p_table == parentTable) {
	    logger.debug( "Adding to parentTable");
	    parentTable.add( p_enr ); // dealing with 'key'
	}
	else if ((p_table == basicTable) || (p_table == completeTable)) {
	    logger.debug( "Adding to basicTable" );
	    basicTable.add( p_enr ); // dealing with 'key'
	    // should also add to 'completeTable';
	    addToComplete( p_enr );
	}
	else {
	    logger.error( "Unknown Table " + p_table.getLabel() + " to add an enregistrement!");
	}
    }
    /**
     * Edit an element to either parent or complete Table.
     */
    public void editEnregistrement( Table p_table )
    {
	if (p_table == parentTable) {
	    logger.debug( "Edit parentTable");
	    //signal change to other tables
	    basicTable.changedData();
	    completeTable.changedData();
	    logger.debug( "---- Complete ----\n" + completeTable.displayData() );
	}
	else if ((p_table == basicTable) || (p_table == completeTable)) {
	    logger.debug( "Edit basicTable" );
	    completeTable.changedData();
	}
	else {
	    logger.error( "Unknown Table " + p_table.getLabel() + " to edit an enregistrement!");
	}
    }
    /**
     * Delete an Element from Table.
     */
    public void deleteEnregistrement( Table p_table, Enregistrement p_enr )
    {
	if (p_table == parentTable) {
	    logger.debug( "Delete from parentTable");
	    // must first re-link dependant childs
	    // parent for basic Element
	    logger.debug( "Relink in basicTable" );
	    for( Iterator iEnr = basicTable.listEnregistrement.iterator(); iEnr.hasNext(); ) {
		
		Enregistrement current = (Enregistrement) iEnr.next();
		Enregistrement currentParent = (Enregistrement) current.getParent();
		// if was parent of current, then new parent is default (index=0)
		if (currentParent.key == p_enr.key ) {
		    logger.debug( "Changing parent of " + current.displayData() );
		    current.setParentRecursif( parentTable.getAt( 0 ) );
		}
	    }
	    basicTable.changedData();
	    logger.debug( "Relink in completeTable" );
	    // parent and some fields for complete Element
	    for( Iterator iEnr = completeTable.listEnregistrement.iterator(); iEnr.hasNext(); ) {
		
		Enregistrement current = (Enregistrement) iEnr.next();
		Enregistrement currentParent = (Enregistrement) current.getParent();
		// if was parent of current, then new parent is default (index=0)
		if (currentParent.key == p_enr.key ) {
		    // changing parent
		    logger.debug( "Changing parent of " + current.displayData() );
		    current.setParentRecursif( parentTable.getAt( 0 ) );

		    // the fields coming from parent
		    for( int iData = 0; iData < fromParentHeader.theFields.size(); iData++ ) {
			Field fieldParent = fromParentHeader.getField( iData );
			int fieldCurrentPosition = completeTable.myHeader.getFieldPosition( fieldParent.getLabel() );
			logger.debug( "current.add("+ fieldCurrentPosition +", "+ parentTable.getAt( 0 ).data[ fieldParent.getIndex() ].displayData()+", "+ fieldParent.getIndex()+")");
		//newEnr.add( indexData, currentParent.data[ fieldParent.getIndex() ].displayData(), false /* no inherit */ );
			// change Field
			current.add( fieldCurrentPosition, parentTable.getAt( 0 ), fieldParent.getIndex() );
		    }
		}
	    }
	    completeTable.changedData();
	    // then can remove
	    parentTable.remove( p_enr.key );
	}
	else if ((p_table == basicTable) || (p_table == completeTable)) {
	    logger.debug( "Delete from basicTable" );
	    completeTable.remove( p_enr.key );
	    basicTable.remove( p_enr.key );
	}
	else {
	    logger.error( "Unknown Table " + p_table.getLabel() + " to delete an enregistrement!");
	}
    }

    /**
     * Write to a file, using XML.
     */
    public void write( File file )
	throws java.io.IOException
    {
	basicTable.write( file );
    }	
    // ---------- a Private Logger ---------------------
    private Logger logger;
    // --------------------------------------------------
} // BaseDeDonnees
    








