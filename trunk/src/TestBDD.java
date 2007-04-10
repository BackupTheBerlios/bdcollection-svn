
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import collector.data.BaseDeDonnees;

/**
 * Test the BDD
 *
 * @version 1.0
 * $Date: 2003/11/12$<br>
 * @author Alain$
 */

class TestBDD
{
    BaseDeDonnees bdd;

    /**
     * Creation
     */
    public TestBDD() 
    {
	bdd = new BaseDeDonnees();
    }
    /**
     * test
     */
    public void test()
    {
 	try {
	
	    logger.info("--- Read ---");
	    bdd.readFromFile( "data/nouveau.dta" );
	    
	    logger.info("--- DISPLAY ----");
	    logger.info( "\n" + bdd.toString() );

 	} catch (Throwable t) {
 	    t.printStackTrace();
 	}
     }

    /**
     * Basic test with no GUI
     */
    public static void main(String[] args) 
    {
    //File currentDir = new File(".");
    //System.out.println( "pwd = " + currentDir.getAbsolutePath());
        
	// logger configuration 
	PropertyConfigurator.configure("etc/log4j.config");
	logger = Logger.getLogger(TestBDD.class);
	
	TestBDD myTest = new TestBDD();
        myTest.test();
    }

    // ---------- a Private Logger ---------------------
    private static Logger logger;
    // --------------------------------------------------
} // TestBDD
    








