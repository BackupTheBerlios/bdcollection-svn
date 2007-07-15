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
 * indexes 'Série', 'Vol' and 'Title'.
 * SPECIFIC for BandesDessinees.
 * @author dutech
 */
public class BandeDessineeComparator
implements Comparator
{
    final String labelSerie = new String("Série");
    final String labelVolume = new String("Vol.");
    final String labelTitle = new String( "Titre");
    
    protected boolean ascending; // in which order to compare
    
    /**
     * Create a comparator.
     * @param p_ascending or descending order?
     */
    public BandeDessineeComparator( boolean p_ascending)
    {
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
            
            int indexSerie = enrOne.myHeader.getFieldPosition( labelSerie);
            int indexVol = enrOne.myHeader.getFieldPosition( labelVolume);
            int indexTitle = enrOne.myHeader.getFieldPosition( labelTitle);
            
            CellStr dataSerieOne = (CellStr) enrOne.data[indexSerie];
            CellStr dataSerieTwo = (CellStr) enrTwo.data[indexSerie];
            CellInt dataVolOne = (CellInt) enrOne.data[indexVol];
            CellInt dataVolTwo = (CellInt) enrTwo.data[indexVol];
            CellStr dataTitleOne = (CellStr) enrOne.data[indexTitle];
            CellStr dataTitleTwo = (CellStr) enrTwo.data[indexTitle];
            
            if( ascending ) {
                int cmpVal = dataSerieOne.compareTo( dataSerieTwo );
                if( cmpVal == 0 ) {
                    cmpVal = dataVolOne.compareTo( dataVolTwo );
                }
                if( cmpVal == 0) {
                    cmpVal = dataTitleOne.compareTo( dataTitleTwo );
                }
                return cmpVal;
            }
            else {
                int cmpVal = dataSerieTwo.compareTo( dataSerieOne );
                if( cmpVal == 0 ) {
                    cmpVal = dataVolTwo.compareTo( dataVolOne );
                }
                if( cmpVal == 0) {
                    cmpVal = dataTitleTwo.compareTo( dataTitleOne );
                }
                return cmpVal;
            }
        }
        else {
            throw new ClassCastException( "not Enregistrement");
        }
    }
    
}
