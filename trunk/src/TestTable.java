
import java.io.File;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import collector.data.Field;
import collector.data.Header;
import collector.data.SearchOperator;
import collector.data.SearchedElement;
import collector.data.Table;
import collector.data.TableFactory;
import collector.data.View;

/**
 * Test the Table
 *
 * @version 1.0
 * $Date: 2003/07/08$<br>
 * @author Alain$
 */

class TestTable 
{
    Table myTable;
    Table myParent;
    Header myHeader;
    Header myParentHeader;
    SearchedElement searchPattern;
    SearchOperator theSearch;

    Field field1;
    Field field2;
    Field field3;
    /**
     * Creation
     */
    public TestTable() 
    {
	/*
	// Creation of a parent Table
	myParentHeader = new Header();
	Field parentField1 = new Field( "unParent", Element.typeString, 0);
	myParentHeader.add( parentField1 );

	myParent = new Table( "ZeBigBrother" );
	myParent.setHeader( myParentHeader );

	Enregistrement parentEnr1 = new Enregistrement( myParentHeader );
	parentEnr1.key = 11;
	parentEnr1.add( 0, "parent_zero", true );
	myParent.add( parentEnr1 );

	myHeader = new Header();
	field1 = new Field( "un", Element.typeString, 1);
	field1.setParent( parentField1 );
	field2 = new Field( "deux", Element.typeString, 0);
	field3 = new Field( "trois", Element.typeString, 2 );
	myHeader.add( field2 );
	myHeader.add( field1 );
	myHeader.add( field3 );

	myTable = new Table( "Pour voir" );
	myTable.setHeader( myHeader );
	myTable.setParent( myParent );
	
	Enregistrement enr1 = new Enregistrement( myHeader );
	myTable.add( enr1 );
	enr1.setParent( parentEnr1 );
	enr1.key = 1;
	enr1.add( 0, "cell_zero", true );
	enr1.add( 1, "cell_un", true );
	enr1.add( 2, "cell_deux", false );
	
	Enregistrement enr2 = new Enregistrement( myHeader );
	myTable.add( enr2 );
	enr2.key = 2;
	enr2.add( 0, "cellule_zero", false );
	enr2.add( 1, "cellule_un", false );
	enr2.add( 2, "cellule_deux", false );	  

	*/


    }
    /**
     * test
     */
    public void test()
    {
 	try {
	
	    //logger.info("--- Begin ---");
	    //logger.info( myTable.toString() );
	    /*
	    logger.info("--- Save ---");
	    File outputFile2 = new File("../data/metatable.dta");
	    myTable.write( outputFile2 );
	    */
	    logger.info("--- Read ---");
	    TableFactory creator = new TableFactory();
	    Table table = creator.createFromFile( new File("../data/bandesDessinees.dta") );
	    
	    logger.info("--- STRING ----");
	    logger.info( "\n" + table.toString() );
	    logger.info("--- DISPLAY ----");
	    logger.info( "\n" + table.displayData() );
	    
	    logger.info("--- SearchOperator ---");
	    theSearch = new SearchOperator();
	    View tmpTable;
	    tmpTable = theSearch.applyTo( table );
	    logger.info( tmpTable.displayData()+"\n-------------------------" );
	    theSearch.addSearchItem( "cell", table.myHeader.getField(0), SearchedElement.INCLUDED );
	    tmpTable = theSearch.applyTo( table );
	    logger.info( tmpTable.displayData()+"\n-------------------------" );
	    theSearch.addSearchItem( "cell", table.myHeader.getField(1), SearchedElement.INCLUDED );
	    tmpTable = theSearch.applyTo( table );
	    logger.info( tmpTable.displayData()+"\n-------------------------" );
	    theSearch.setSearchType( SearchOperator.AND );
	    tmpTable = theSearch.applyTo( table );
	    logger.info( tmpTable.displayData()+"\n-------------------------" );
	    
	    tmpTable = table.getClosest( table.getAt(0) );
	    logger.info( tmpTable.displayConfidenceData()+"\n-------------------------" );
	    logger.info( tmpTable.displaySortedConfidenceData()+"\n-------------------------" );

 	} catch (Throwable t) {
 	    t.printStackTrace();
 	}
     }

    /**
     * Basic test with no GUI
     */
    public static void main(String[] args) 
    {
	// logger configuration 
	PropertyConfigurator.configure("../etc/log4j.config");
	logger = Logger.getLogger(TestTable.class);
	
	TestTable myTest = new TestTable();
        myTest.test();
    }

    // ---------- a Private Logger ---------------------
    private static Logger logger;
    // --------------------------------------------------
} // TestTable
    








