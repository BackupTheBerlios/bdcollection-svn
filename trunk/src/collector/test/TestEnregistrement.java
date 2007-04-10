
import java.io.*;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import collector.data.*;

import com.megginson.sax.DataWriter;

//import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
//import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
//import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;
import javax.xml.parsers.SAXParser; 
import javax.xml.parsers.SAXParserFactory; 

/**
 * Test the Enregistrement
 *
 * @version 1.0
 * $Date: 2003/07/08$<br>
 * @author Alain$
 */

class TestEnregistrement 
{
    Enregistrement myEnregistrement;
    Header myHeader;
    SearchedElement searchPattern;
    SearchOperator theSearch;

    Field field1;
    Field field2;
    Field field3;
    /**
     * Creation
     */
    public TestEnregistrement() 
    {
	myHeader = new Header();
	field1 = new Field( "un", Element.typeString, 1);
	field2 = new Field( "deux", Element.typeString, 2);
	field3 = new Field( "trois", Element.typeString, 3 );
	field3.setParent( field2 );
	myHeader.add( field2 );
	myHeader.add( field1 );
	myHeader.add( field3 );

	myEnregistrement = new Enregistrement( myHeader );
	myEnregistrement.add( 0, "cell_zero", false );
	myEnregistrement.add( 1, "cell_un", false );
	myEnregistrement.add( 2, "cell_deux", false );

	CellStr myCell = (CellStr) myEnregistrement.data[2];

	myCell.setInherit( true );

	logger.info("--- Begin ---");
	logger.info( myEnregistrement.toString() );
	myCell.setParent( myEnregistrement.data[1] );
	myCell.setInherit( true );
	logger.info("--- Begin ---");
	logger.info( myEnregistrement.toString() );	
	  


    }
    /**
     * test
     */
    public void test()
    {
	logger.info("--- Begin ---");
	logger.info( myEnregistrement.toString() );

	logger.info("--- End ---");
	logger.info( myEnregistrement.displayData() );

	logger.info("--- Search exact ---");
	searchPattern = new SearchedElement( "cell", field2, SearchedElement.EXACT );
	if (searchPattern.isIn( myEnregistrement )) {
	    logger.info( "Search is successfull");
	} 
	else {
	    logger.info( "Search failed");
	}
	searchPattern = new SearchedElement( "cell_un", field2, SearchedElement.EXACT );
	if (searchPattern.isIn( myEnregistrement )) {
	    logger.info( "Search is successfull");
	} 
	else {
	    logger.info( "Search failed");
	}
	searchPattern = new SearchedElement( "cell", field2, SearchedElement.INCLUDED );
	if (searchPattern.isIn( myEnregistrement )) {
	    logger.info( "Search is successfull");
	} 
	else {
	    logger.info( "Search failed");
	}
	searchPattern = new SearchedElement( ".*un.*", field2, SearchedElement.RLIKE );
	if (searchPattern.isIn( myEnregistrement )) {
	    logger.info( "Search is successfull");
	} 
	else {
	    logger.info( "Search failed");
	}

	logger.info("--- SearchOperator ---");
	theSearch = new SearchOperator();
	if (theSearch.applyTo( myEnregistrement )) {
	    logger.info( "Search is successfull");
	} 
	else {
	    logger.info( "Search failed");
	}
	theSearch.addSearchItem( "cell", field2, SearchedElement.EXACT );
	if (theSearch.applyTo( myEnregistrement )) {
	    logger.info( "Search is successfull");
	} 
	else {
	    logger.info( "Search failed");
	}
	theSearch.addSearchItem( "cell", field2, SearchedElement.INCLUDED );
	if (theSearch.applyTo( myEnregistrement )) {
	    logger.info( "Search is successfull");
	} 
	else {
	    logger.info( "Search failed");
	}
	theSearch.setSearchType( SearchOperator.AND );
	if (theSearch.applyTo( myEnregistrement )) {
	    logger.info( "Search is successfull");
	} 
	else {
	    logger.info( "Search failed");
	}
	
     }

    /**
     * Basic test with no GUI
     */
    public static void main(String[] args) 
    {
	// logger configuration 
	PropertyConfigurator.configure("../etc/log4j.config");
	logger = Logger.getLogger(TestEnregistrement.class);
	
	TestEnregistrement myTest = new TestEnregistrement();
        myTest.test();
    }

    // ---------- a Private Logger ---------------------
    private static Logger logger;
    // --------------------------------------------------
} // TestEnregistrement
    








