
package collector.gui;

import java.awt.Component;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
/**
 * JHeaderList is a kind of Scrollable JList.
 *
 * @version 1.0
 * $Date: 2003/07/08$<br>
 * @author Alain$
 */

public class JHeaderList extends JScrollPane
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /** the JList */
    JList theJList;
    /** Data is the Header.thefields */
    DefaultListModel data;
    
    /**
     * Creation
     */
    public JHeaderList( collector.data.Header p_header ) 
    {
        //logger = Logger.getLogger(JHeaderList.class);
        
        data = p_header.theFields;
        theJList = new JList( data );
        theJList.setCellRenderer(new MyFieldRenderer());
        
        //breaktheJList.setPreferredSize( new Dimension( 100, 100 ));
        
        this.setViewportView( theJList ); 
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
        
        str.append("JHeaderList\n");
        
        return str.toString();
    }
    
    class MyFieldRenderer extends JLabel implements ListCellRenderer {
        
        // This is the only method defined by ListCellRenderer.
        // We just reconfigure the JLabel each time we're called.
        
        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        public Component getListCellRendererComponent(
                JList list,
                Object value,            // value to display
                int index,               // cell index
                boolean isSelected,      // is the cell selected
                boolean cellHasFocus)    // the list and the cell have the focus
        {
            collector.data.Field theField = (collector.data.Field) value;
            setText( theField.displayData() );
            
            if (isSelected) {
                setBackground(list.getSelectionBackground());
                setForeground(list.getSelectionForeground());
            }
            else {
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            }
            setEnabled(list.isEnabled());
            setOpaque(true);
            return this;
        }
    } // MyFieldRenderer
    
    // ---------- a Private Logger ---------------------
    //private Logger logger;
    // --------------------------------------------------
} // JHeaderList









