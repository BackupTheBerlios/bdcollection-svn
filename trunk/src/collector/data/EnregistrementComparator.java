/*
 * Created on Jan 29, 2007
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package collector.data;

import java.util.Comparator;
/**
 * Comparison of Two Enregistrement based on the data situated at the 
 * position 'index'.
 * @author dutech
 */
public class EnregistrementComparator
   implements Comparator
{
    protected int index; // index of the Element of comparison
    protected boolean ascending; // in which order to compare
    
    /**
     * Create a comparator based on the p_index'th composant.
     * @param p_index
     * @param p_ascending or descending order?
     */
    public EnregistrementComparator( int p_index, boolean p_ascending)
    {
        index = p_index;
        ascending = p_ascending;
    }
    /**
     * Compare 2 Enregistrement based on data at index.
     */
    public int compare(Object arg0, Object arg1)
    {
        if( (arg0 instanceof Enregistrement )
                && (arg1 instanceof Enregistrement )) {
            Enregistrement enrOne = (Enregistrement) arg0;
            Enregistrement enrTwo = (Enregistrement) arg1;
            
            if( ascending ) {
                return enrOne.data[index].displayData().compareToIgnoreCase(enrTwo.data[index].displayData());
            }
            else {
                return enrTwo.data[index].displayData().compareToIgnoreCase(enrOne.data[index].displayData());
            }
        }
        else {
            throw new ClassCastException( "not Enregistrement");
        }
    }
    
}
