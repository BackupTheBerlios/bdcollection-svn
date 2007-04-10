
package collector.data;

import org.apache.log4j.Logger;

import tools.StringSimilarity;

/**
 * A SearchedElement is used to look for a given Enregistrement in a Table. 
 * It is the lowest level of the search process.<br>
 * Composed mainly of a pattern String that is looked for in a given Field.<br>
 * Search can be conducted many ways:
 * <ul>
 * <li>EXACT : exact match using lexicographic comparison</li>
 * <li>INCLUDED : pattern can be found in the Element</li>
 * <li>RLIKE : using regular expression match</li>
 * <li>SIMILAR : looking for similarity in the field</li>
 * <li>CLOSE : looking as the entire dataDisplay() string for similarity</li>
 * </ul>
 * @version 1.0
 * $Date: 2004/04/28$<br>
 * @author Alain$*
 */

public class SearchedElement
{
    /** the pattern that is looked for */
    String pattern;
    /** the Field in which the pattern we be looked */
    Field theField;
    /** the type on search */
    int searchType;
    
    /** exact search */
    public final static int EXACT = 10;
    public final static String EXACT_STR = "egale";
    /** pattern is included */
    public final static int INCLUDED = 11;
    public final static String INCLUDED_STR = "contient";
    /** match using rexp */
    public final static int RLIKE = 12;
    public final static String RLIKE_STR = "reg-exp";
    /** look for similarity */
    public final static int SIMILAR = 13;
    public final static String SIMILAR_STR = "similar";
    public final static double thresold = 0.85;
    /** look for close Enregistrement - not field by field */
    public final static int CLOSE = 100;
    public final static String CLOSE_STR = "close";
    public final static double thresold_close = 0.7;
    /** an Array of all search possibilities */
    public final static String SEARCH_STR[] = {EXACT_STR, INCLUDED_STR, RLIKE_STR, SIMILAR_STR};
    
    /**
     * Creation.
     */
    public SearchedElement( String p_pattern, Field p_field, int p_type )
    {
        logger = Logger.getLogger(SearchedElement.class);
        
        pattern = p_pattern;
        theField = p_field;
        searchType = p_type;
    }
    /**
     * Creation.
     */
    public SearchedElement( String p_pattern, Field p_field, String p_type )
    {
        logger = Logger.getLogger(SearchedElement.class);
        
        pattern = p_pattern;
        theField = p_field;
        searchType = tokenToInt( p_type );
    }
    /**
     * fill in every attributes.
     */
    public void setUp( String p_pattern, Field p_field, int p_type )
    {
        pattern = p_pattern;
        theField = p_field;
        searchType = p_type;
    }
    /**
     * fill in every attributes.
     */
    public void setUp( String p_pattern, Field p_field, String p_type )
    {
        pattern = p_pattern;
        theField = p_field;
        searchType = tokenToInt( p_type );
    }
    
    /**
     * Is it in the given Enregistrement.
     *
     * @return confidence that 'pattern' is in the given Enregistrement (in [O;1]).
     */
    public double isIn( Enregistrement p_enr )
    {
        // find the field
        int index = theField.getIndex();
        Element tmpElement = p_enr.data[index];
        
        if( searchType == EXACT ) {
            logger.debug( "Comparing pattern =\'"+pattern+"\' with \'"+tmpElement.displayData()+"\'" );
            if (pattern.equalsIgnoreCase( tmpElement.displayData() )) {
                return 1.0;
            }
            else {
                return 0.0;
            }
        }
        else if (searchType == RLIKE ) {
            logger.debug( "Matching pattern =\'"+pattern+"\' with \'"+tmpElement.displayData()+"\'" );
            if (tmpElement.displayData().toUpperCase().matches( pattern.toUpperCase() ) ) {
                return 1.0;
            }
            else {
                return 0.0;
            }
        }
        else if (searchType == INCLUDED ) {
            logger.debug( "pattern =\'"+pattern+"\' included in  \'"+tmpElement.displayData()+"\'" );
            if (tmpElement.displayData().toUpperCase().matches( ".*"+pattern.toUpperCase()+".*" ) ) {
                return 1.0;
            }
            else {
                return 0.0;
            }
        }
        else if (searchType == SIMILAR) {
            double similarity = StringSimilarity.comparePairSimilarity( pattern, tmpElement.displayData());
            logger.debug( "pattern =\'"+pattern+"\' similar  \'"+tmpElement.displayData()+"\' = " + similarity );
            if (similarity > thresold ) {
                return similarity;
            }
            else {
                return 0.0;
            }
        }
        else if (searchType == CLOSE) {
            double closeness = StringSimilarity.comparePairSimilarity( pattern, p_enr.displayData() );
            logger.debug( "pattern =\'"+pattern+"\' close to \'"+p_enr.displayData()+" = " + closeness );
            if( closeness > thresold_close ) {
                return closeness;
            }
            else {
                return 0.0;
            }
        }
        else {
            logger.warn( "Unknown searchType !!!");
            return 0.0;
        }
    }
    /**
     * classic.
     *
     * Output format:<br>
     * Search (type) for 'pattern' in Field->getLabel()<br>
     */
    public String toString()
    {
        StringBuffer str = new StringBuffer();
        
        str.append( "Search (" + searchType + ") '"+pattern+ "' in " + theField.getLabel() );
        return str.toString();
    }
    /**
     * classic.
     *
     * Output format:<br>
     * Search (type) for 'pattern' in Field->getLabel()<br>
     */
    public String displayData()
    {
        return toString();
    }
    
    /**
     * get 'int' representation of type.
     */
    int tokenToInt( String p_typeStr )
    {
        if( p_typeStr.equals( EXACT_STR )) {
            return EXACT;
        } 
        else if ( p_typeStr.equals( INCLUDED_STR )) {
            return INCLUDED;
        } 
        else if ( p_typeStr.equals( RLIKE_STR )) {
            return RLIKE;
        }
        else if ( p_typeStr.equals( SIMILAR_STR )) {
            return SIMILAR;
        }
        else if ( p_typeStr.equals( CLOSE_STR )) {
            return CLOSE;
        }
        else {
            logger.warn( "Unknown searchType !!!");
            return 0;
        }
    }
    
    
    // ---------- a Private Logger ---------------------
    private Logger logger;
    // --------------------------------------------------
    
} // SearchedElement
