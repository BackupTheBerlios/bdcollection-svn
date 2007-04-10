
package collector.data;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;


/**
 * A View is a Table used for storing result of search.
 * A View should only be created by a SearchOperator.
 * It keeps a link with its original Table and with the SearchOperator used
 * in creating it.
 * A View has also alongside estimations of the search confidence.
 *
 * @version 1.0
 * $Date: 2004/05/04$<br>
 * @author Alain$
 */
public class View extends Table 
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /** Counts nb of view created */
    static int nbCreated;

    /** Link to original Table */
    Table originalTable;
    /** Search operator used to create the Table */
    SearchOperator theSearchOperator;
    /** Estimation of search confidence for each found Enregistrement */
    public ArrayList confidence;

    /** 
     * Creation with an automatic name.
     */
    public View ( Table p_original, SearchOperator p_search )
    {
	super( "View_" + nbCreated );
	nbCreated++;

	originalTable = p_original;
	theSearchOperator = p_search;
	confidence = new ArrayList();
    }
    /**
     * Creation with a name.
     */
    public View ( String p_name, Table p_original, SearchOperator p_search )
    {
	super( p_name );
	nbCreated++;
	
	originalTable = p_original;
	theSearchOperator = p_search;
	confidence = new ArrayList();
    }

    /**
     * Access the original Table.
     */
    public Table getOriginalTable()
    {
	return originalTable;
    }

    /**
     * Display data as a String with confidence !!.
     */
    public String displayConfidenceData()
    {
	StringBuffer str = new StringBuffer();
	
	/*str.append("\n--------------------------------------------------\n");
	str.append("Table : " + getLabel() + "\n");
	if( hasParent() ) {
	    str.append( "parent: " + parent.getLabel() + "\n" );
	}
	else {
	    str.append( "no-parent\n" );
	}
	*/    
	str.append( "Conf" + myHeader.displayData() + "\n" );
	str.append("--------------------------------------------------\n");

	for (int i = 0; i < listEnregistrement.size(); i++ )
	    {
		Enregistrement tmpEnr = (Enregistrement) listEnregistrement.get(i);
		Double tmpConf = (Double) confidence.get(i);
		String confStr = tmpConf.toString();
		str.append("("+confStr.substring( 0, Math.min( 4, confStr.length()))+") " + tmpEnr.displayData() + "\n");
	    }
	str.append("--------------------------------------------------\n");

	return str.toString();
    }
    /**
     * Display data as a String with confidence, sorted along confidence !!.
     */
    public String displaySortedConfidenceData()
    {
	// sort data
	TreeSet sorted = new TreeSet( new ConfEnrComparator() );
	for (int i = 0; i < listEnregistrement.size(); i++ ) {
	    Integer index = new Integer(i);
	    sorted.add( index );
	}
	
	StringBuffer str = new StringBuffer();
	
	/*str.append("\n--------------------------------------------------\n");
	str.append("Table : " + getLabel() + "\n");
	if( hasParent() ) {
	    str.append( "parent: " + parent.getLabel() + "\n" );
	}
	else {
	    str.append( "no-parent\n" );
	}
	*/
	str.append( "Conf" + myHeader.displayData() + "\n" );
	str.append("--------------------------------------------------\n");

	for (Iterator i = sorted.iterator(); i.hasNext();  ) {
	    Integer tmpIndex = (Integer) i.next();
	    Enregistrement tmpEnr = (Enregistrement) listEnregistrement.get( tmpIndex.intValue() );
	    Double tmpConf = (Double) confidence.get( tmpIndex.intValue() );
	    String confStr = tmpConf.toString();
	    str.append("("+confStr.substring( 0, Math.min( 4, confStr.length()))+") " + tmpEnr.displayData() + "\n");
	}
	str.append("--------------------------------------------------\n");

	return str.toString();
    }

    /**
     * A special comparator for sorting (confidence+Enregistrement pair)
     * that must be used with Integer(index) of listEnregistrement or confidence*/
    class ConfEnrComparator implements Comparator 
    {
	/**
	 * Invoque on Integer, Integer
	 */
	public int compare(Object o1, Object o2) 
	{
	    Integer index1 = (Integer) o1;
	    Integer index2 = (Integer) o2;

	    Double conf1 = (Double) confidence.get(index1.intValue());
	    Double conf2 = (Double) confidence.get(index2.intValue());

	    return Double.compare( conf2.doubleValue(), conf1.doubleValue() );
	}
	/**
	 * Invoque on Integer, Integer
	 */
	public boolean equals(Object obj)
	{
	    return this.equals(obj);
	}
    } // ConfEnrComparator

    // ---------- a Private Logger ---------------------
    //private Logger logger;
    // --------------------------------------------------
    static {
	nbCreated = 0;
    }
} // View
    








