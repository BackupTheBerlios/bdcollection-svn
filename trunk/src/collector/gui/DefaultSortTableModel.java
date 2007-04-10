/*
=====================================================================

  DefaultSortTableModel.java
  
  Created by Claude Duguay
  Copyright (c) 2002
  
=====================================================================
*/

package collector.gui;

import java.util.*;
import javax.swing.table.*;
/** * Default Sorting model for JSortTable. * @author Claude Duguay */
public class DefaultSortTableModel
  extends DefaultTableModel
  implements SortTableModel
{
  /**     *      */    private static final long serialVersionUID = 1L;public DefaultSortTableModel() {}
  
  public DefaultSortTableModel(int rows, int cols)
  {
    super(rows, cols);
  }
  
  public DefaultSortTableModel(Object[][] data, Object[] names)
  {
    super(data, names);
  }
  
  public DefaultSortTableModel(Object[] names, int rows)
  {
    super(names, rows);
  }
  
  public DefaultSortTableModel(Vector names, int rows)
  {
    super(names, rows);
  }
  
  public DefaultSortTableModel(Vector data, Vector names)
  {
    super(data, names);
  }
  
  public boolean isSortable(int col)
  {
    return true;
  }
  
  public void sortColumn(int col, boolean ascending)
  {
    Collections.sort(getDataVector(),
      new ColumnComparator(col, ascending));
  }
}

