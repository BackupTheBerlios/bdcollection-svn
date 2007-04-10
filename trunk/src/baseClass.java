
import org.apache.log4j.Logger;


/**
 * Template for all classes
 *
 * @version 1.0
 * $Date: 2003/07/08$<br>
 * @author Alain$
 */

class baseClass 
{

    /**
     * Creation
     */
    public baseClass() 
    {
	logger = Logger.getLogger(baseClass.class);
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

    // ---------- a Private Logger ---------------------
    private Logger logger;
    // --------------------------------------------------
} // baseClass
    








