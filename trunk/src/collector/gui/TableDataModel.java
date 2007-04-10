
package collector.gui;

import javax.swing.table.AbstractTableModel;

import collector.data.Table;

/**
 * A TableDataModel allow the use of Table as data for a JTable.
 *
 * @version 1.0
 * $Date: 2003/09/01$<br>
 * @author Alain$
 */

public class TableDataModel
    extends AbstractTableModel      
    implements SortTableModel   
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /** the data */
    Table data;

    /**
     * Creation
     */
    public TableDataModel( Table p_data ) 
    {
	//logger = Logger.getLogger(TableDataModel.class);
	
	data = p_data;
    }

    /** 
     * Number of Columns (TableDataModelModel)
     **/
    public int getColumnCount() 
    {
	return data.myHeader.theFields.size();
    }
        
    /** 
     * Number of Rows (TableDataModelModel) 
     */
    public int getRowCount() 
    {
	return data.listEnregistrement.size();
    }
	
    /** 
     * Name of a Column (TableDataModelModel)
     */
    public String getColumnName(int col) 
    {
	return data.myHeader.getField( col ).getLabel();
    }

    /** 
     * Get the value of a cell (TableModel)
     */
    public Object getValueAt(int row, int col) 
    {
        //logger.debug( "getValue at "+row+", "+col);
        return data.get( row, col).displayData();
    }
    
    // ---------- a Private Logger ---------------------
    //private Logger logger;
    // --------------------------------------------------

    /**
     * Is a given column sortable?
     * @param index of column.
     * @see SortTableModel
     */
    public boolean isSortable(int col)
    {
        return false;
    }

    
    public void sortColumn(int col, boolean ascending)
    {
        // TODO Auto-generated method stub
        
    }
} // TableDataModel

    








