
package collector.data;

import java.io.*;
import java.util.*;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;


/**
 * A View is a Table with also a backHeader from which new field can be
 * taken.
 *
 * TODO : allow manipulation of backHeader. 
 *
 * @version 1.0
 * $Date: 2003/11/12$<br>
 * @author Alain$
 */

public class View extends Table
{
    /** list of possible Fields */
    public Header backHeader;

    /**
     * Creation : more like a copy
     */
    public View( Table p_table) 
    {
	super( p_table.getLabel() );
	logger = Logger.getLogger(View.class);
	
	myHeader = p_table.myHeader;
	listEnregistrement = p_table.listEnregistrement;
      
	backHeader = new Header();

	parent = (Table) p_table.getParent();
    }

    /**
     * classic.
     *
     * Output format:<br>
     * View : label<br>
     * Header -----------------------<br>
     * myHeader.toString()
     * Back Header -----------------------<br>
     * backHeader.toString()
     * Enregistrements (nb) -------------<br>
     * [...] Enregistrement.toString()<br>
     */
    public String toString()
    {
	StringBuffer str = new StringBuffer();
	
	str.append("View : " + getLabel() + "\n");
	if( hasParent() ) {
	    str.append( "parent: " + parent.getLabel() + "\n" );
	}
	else {
	    str.append( "no-parent\n" );
	}	
	str.append("Header -----------------------\n");
	str.append( myHeader.toString() );

	str.append("Back Header ------------------\n");
	str.append( backHeader.toString() );
	
	str.append("Enregistrements (" + listEnregistrement.size() + ") -------------\n");
	for (Iterator i = listEnregistrement.iterator(); i.hasNext(); )
	    {
		Enregistrement tmpEnr = (Enregistrement) i.next();
		str.append(tmpEnr.toString());
	    }

	return str.toString();
    }

    /**
     * Display data as a String.
     */
    public String displayData()
    {
	StringBuffer str = new StringBuffer();
	
	str.append("\n--------------------------------------------------\n");
	str.append("View : " + getLabel() + "\n");

	str.append( "FRONT:" + myHeader.displayData() + "\n" );
	str.append( "BACK :" + backHeader.displayData() + "\n" );
	str.append("--------------------------------------------------\n");

	for (Iterator i = listEnregistrement.iterator(); i.hasNext(); )
	    {
		Enregistrement tmpEnr = (Enregistrement) i.next();
		str.append(tmpEnr.displayData() + "\n");
	    }
	str.append("--------------------------------------------------\n");

	return str.toString();
    }

    // ---------- a Private Logger ---------------------
    private Logger logger;
    // --------------------------------------------------
} // View
    








