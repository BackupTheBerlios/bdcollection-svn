
package collector.data;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.log4j.Logger;

/**
 * The search operator contains a list of SearchedElement and operates
 * on a Table. For each Enregistrement of the Table, it looks if the proper
 * combination (OR or AND) of SearchedElement can be found and delivers a 
 * new Table with cloned Enregistrement.
 *
 * @todo Enable modification of search pattern...
 * @todo A search could have a name...
 *
 * @version 1.0
 * $Date: 2004/04/28$<br>
 * @author Alain$* </ul>
 */

public class SearchOperator 
{
    /** collection of search items */
    ArrayList searchItems;
    /** type of combination */
    int typeSearch;
    
    /** OR search */
    public final static int OR = 10;
    /** AND search */
    public final static int AND = 11;
    
    /**
     * Creation with no items and OR.
     */
    public SearchOperator ()
    {
        logger = Logger.getLogger(SearchOperator.class);
        
        typeSearch = OR;
        searchItems = new ArrayList();
    }
    
    /**
     * Sets type of search (OR or AND).
     */
    public void setSearchType( int p_type )
    {
        typeSearch = p_type;
    }
    /** 
     * Get type of search.
     */
    public int getSearchType()
    {
        return typeSearch;
    }
    
    /**
     * Adds directly a new SearchedElement.
     */
    public void addSearchItem( SearchedElement p_search )
    {
        searchItems.add( p_search );
    }
    /**
     * Creates and add a new search.
     */
    public void addSearchItem( String p_pattern, Field p_field, int p_type )
    {
        searchItems.add( new SearchedElement( p_pattern, p_field, p_type ) );
    }
    /**
     * Clear all searchItems.
     */
    public void clearSearch()
    {
        searchItems.clear();
    }
    
    /**
     * Check if a given Enregistrement matches searchItems.
     *
     * @return confidence value of the match (in [0;1])
     */
    public double applyTo( Enregistrement p_enr )
    {
        // for each searchItem
        SearchedElement tmpItem;
        double result = 0.0;
        
        logger.debug( "Apply on " + p_enr.displayData() );
        for (Iterator i = searchItems.iterator(); i.hasNext(); )
        {
            tmpItem = (SearchedElement) i.next();
            double tmpResult = tmpItem.isIn( p_enr );
            if (tmpResult > 0.0) {
                logger.debug( "SearchedElement is successfull");
            } 
            else {
                logger.debug( "SearchedElement failed");
            }
            if( (typeSearch == AND) && (tmpResult == 0.0)) {
                logger.debug( "Enregistrement does not match" );
                return 0.0;
            }
            else if( typeSearch == AND ) {
                if( result > 0.0 ) {
                    result = Math.min( result, tmpResult );
                }
                else {
                    result = tmpResult;
                }
            }
            else {
                result = Math.max( result, tmpResult );
            }
        }
        if( result > 0.0) {
            logger.debug( "Enregistrement does match" ); 
        }
        return result;
    }
    
    /**
     * Apply on a Table.
     */
    public View applyTo( Table p_table )
    {
        // create a new View
        View result = new View( /* no name */ p_table, this );
        result.cloneStructure( p_table );
        
        // for each Enregistrement
        Enregistrement tmpEnr;
        for (Iterator i = p_table.listEnregistrement.iterator(); i.hasNext(); )
        {
            tmpEnr = (Enregistrement) i.next();
            double tmpResult = applyTo( tmpEnr );
            if( tmpResult > 0.0 ) {
                result.add( tmpEnr, tmpEnr.key );
                result.confidence.add( new Double(tmpResult) );
            }
        }
        return result;
    }
    /**
     * Update an existing View.
     */
    public void updateView( View p_view )
    {
        // get source Table
        Table source = p_view.getOriginalTable();
        // remove old Enregistrement
        p_view.listEnregistrement.clear();
        p_view.confidence.clear();
        
        // for each Enregistrement
        Enregistrement tmpEnr;
        for (Iterator i = source.listEnregistrement.iterator(); i.hasNext(); )
        {
            tmpEnr = (Enregistrement) i.next();
            double tmpResult = applyTo( tmpEnr );
            if( tmpResult > 0.0 ) {
                p_view.add( tmpEnr, tmpEnr.key );
                p_view.confidence.add( new Double(tmpResult) );
            }
        }
    }
    
    
    /**
     * classic.
     *
     * Output format:<br>
     * SearchType=OR|AND<br>
     * [...] searchItems.toString()
     */
    public String toString()
    {
        StringBuffer str = new StringBuffer();
        
        str.append( "SearchType= " );
        switch ( typeSearch ) {
        case OR : str.append( "OR\n" );
        break;
        case AND : str.append( "AND\n" );
        break;
        default: logger.warn( "Unknown typeSearch !!!" );
        }
        
        SearchedElement tmpItem;
        for (Iterator i = searchItems.iterator(); i.hasNext(); )
        {
            tmpItem = (SearchedElement) i.next();
            str.append( tmpItem.toString() + "\n" );
        }
        
        return str.toString();
    }
    
    // ---------- a Private Logger ---------------------
    private Logger logger;
    // --------------------------------------------------
} // SearchOperator
