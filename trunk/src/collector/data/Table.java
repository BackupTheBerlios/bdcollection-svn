
package collector.data;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import javax.swing.table.AbstractTableModel;

import org.apache.log4j.Logger;

import collector.gui.SortTableModel;

import com.megginson.sax.DataWriter;

/**
 * A Table is the root piece of info. It consists of a Header and a collection
 * of Enregistrement that can be edited, and save/read as XML.<br>
 * A Table can inherit from <b>one</b> another Table.
 *
 * @version 1.0
 * $Date: 2003/08/22$<br>
 * @author Alain$
 */

public class Table 
extends AbstractTableModel
implements Element, SortTableModel

{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /** type is common for all Enregistrement */
    final static String typeElement = Element.typeTable;
    /** it has also a label for name */
    protected String label;
    
    
    /** Header */
    public Header myHeader;
    /** Collection of Enregistrement */
    public ArrayList listEnregistrement;
    /** Current max key used */
    int maxKey;
    
    /** Its parent is a table */
    Table parent;
    
    /**
     * Creation with a name.
     */
    public Table( String p_name ) 
    {
        logger = Logger.getLogger(Table.class);
        
        label = p_name;
        myHeader = null;
        listEnregistrement = new ArrayList();
        maxKey = 0;
        
        parent = null;
        
        // TableModel
        changedStructure();
    }
    
    /**
     * Clone the structure (Header and Parent) of another Table.
     */
    public void cloneStructure( Table p_table )
    {
        setHeader( p_table.myHeader );
        setParent( p_table.parent );
        changedStructure();
    }
    /**
     * A shallow clone.
     */
    public Element getClone()
    {
        Table truc = new Table( label );
        truc.cloneStructure( this );
        
        for (Iterator i = listEnregistrement.iterator(); i.hasNext(); )
        {
            Enregistrement tmpEnr = (Enregistrement) i.next();
            truc.add(tmpEnr);
        }
        
        return truc;
    }
    /**
     * classic.
     *
     * Output format:<br>
     * Table : label<br>
     * Header -----------------------<br>
     * myHeader.toString()
     * Enregistrements (nb) -------------<br>
     * [...] Enregistrement.toString()<br>
     */
    public String toString()
    {
        StringBuffer str = new StringBuffer();
        
        str.append("Table : " + getLabel() + "\n");
        if( hasParent() ) {
            str.append( "parent: " + parent.getLabel() + "\n" );
        }
        else {
            str.append( "no-parent\n" );
        }	
        str.append("Header -----------------------\n");
        str.append( myHeader.toString() );
        
        str.append("Enregistrements (" + listEnregistrement.size() + ") -------------\n");
        for (Iterator i = listEnregistrement.iterator(); i.hasNext(); )
        {
            Enregistrement tmpEnr = (Enregistrement) i.next();
            str.append(tmpEnr.toString());
        }
        
        return str.toString();
    }
    
    /**
     * Write to a DataWriter.
     *
     * Ouput Format:<br>
     * &lt;_table&gt;<br>
     * &nbsp;&nbsp;&lt;_parent&gt;parent::label|NUL_STR&lt;/_parent&gt;<br>
     * &nbsp;&nbsp;&lt;_label&gt;label&lt;/_label&gt;<br>
     * &nbsp;&nbsp;Header.toXML()<br>
     * &nbsp;&nbsp;[...] Enregistrement.toXML()<br>
     * &lt;_table&gt;
     */
    public void toXML( DataWriter w, Writer fw )
    throws org.xml.sax.SAXException
    {
        w.startElement( TAG_ELEMENT_TABLE );
        
        w.startElement( TAG_ELEMENT_LABEL );
        try {
            fw.write( getLabel() );
        } catch(IOException e) {
            logger.error( e.toString() );
        }
        w.endElement( TAG_ELEMENT_LABEL );
        
        if( hasParent() ) {
            w.startElement( TAG_ELEMENT_PARENT_TABLE );
            getParent().toXML( w, fw );
            w.endElement( TAG_ELEMENT_PARENT_TABLE );
        }
        
        myHeader.toXML(w,fw);
        
        Enregistrement tmpEnr;
        for (Iterator i = listEnregistrement.iterator(); i.hasNext(); )
        {
            tmpEnr = (Enregistrement) i.next();
            tmpEnr.toXML(w,fw);
        }
        
        w.endElement( TAG_ELEMENT_TABLE );
    }
    /**
     * Write to a file, using XML.
     */
    public void write( File file )
    throws java.io.IOException
    {
        FileWriter fileWriter = new FileWriter( file );
        DataWriter dataWriter = new DataWriter( fileWriter );
        try {
            dataWriter.setIndentStep(2);
            dataWriter.startDocument();
            
            this.toXML( dataWriter, fileWriter );
            
            dataWriter.endDocument();
        } 
        catch (org.xml.sax.SAXException t) {
            logger.error( t.toString() );
        }
        fileWriter.close();
    }	
    
    /** 
     * Set the Header.
     */
    public void setHeader( Header p_header )
    {
        myHeader = p_header;
        /* TableModel */
        /* fireTableStructureChanged(); */
        changedStructure();
    }
    
    /**
     * Access type
     */
    public String getType()
    {
        return typeElement;
    }
    /**
     * Access label.
     */
    public String getLabel()
    {
        return label;
    }
    /**
     * Display data as a String.
     */
    public String displayData()
    {
        StringBuffer str = new StringBuffer();
        
        str.append("\n--------------------------------------------------\n");
        str.append("Table : " + getLabel() + "\n");
        if( hasParent() ) {
            str.append( "parent: " + parent.getLabel() + "\n" );
        }
        else {
            str.append( "no-parent\n" );
        }
        str.append( myHeader.displayData() + "\n" );
        str.append("--------------------------------------------------\n");
        
        for (Iterator i = listEnregistrement.iterator(); i.hasNext(); )
        {
            Enregistrement tmpEnr = (Enregistrement) i.next();
            str.append(tmpEnr.displayData() + "\n");
        }
        str.append("--------------------------------------------------\n");
        
        return str.toString();
    }
    
    /**
     * Ask wether it has a parent.
     */
    public boolean hasParent()
    {
        if (parent != null ) {
            return true;
        }
        else {
            return false;
        }
    }
    /**
     * It is not inheriting.
     */
    public boolean isInherit()
    {
        return false;
    }
    /**
     * Parent is usually of the same type.
     */
    public void setParent( Element p_parent )
    {
        if( p_parent != null ) {
            if( p_parent.getType().equals( Element.typeTable ) ) {
                parent = (Table) p_parent;
            }
            else {
                logger.warn( "Trying to make Table inherit from " + p_parent.getType() );
            }
        }
        else {
            parent = null;
        };  
        
        // TableModel
        //changedData();
    }
    /**
     * Delete the parent.
     */
    public void deleteParent()
    {
        parent = null;
        // TableModel
        //changedData();
    }
    
    /**
     * Parent can be retrieved.
     */
    public Element getParent()
    {
        return parent;
    }
    
    
    /** 
     * Adds a new Enregistrement at end of table, no key provided.
     *
     * Set key to current maxKey and increase maxKey.
     *
     * <p><b>Todo</b> : Check that headers match !
     */
    public void add( Enregistrement p_enr )
    {
        Enregistrement tmpEnr = (Enregistrement) p_enr.getClone();
        // if we have a parentTable
        tmpEnr.key = maxKey;
        maxKey++;
        listEnregistrement.add( tmpEnr );
        /* TableModel */
        /* fireTableRowsInserted( listEnregistrement.size()-1, listEnregistrement.size()-1 );*/
        changedData();
    }
    /** 
     * Adds an Enregistrement at end of table with given key.
     *
     * <p><b>Todo</b> : Check that headers match ! And that key is available.
     */
    public void add( Enregistrement p_enr, int p_key )
    {
        Enregistrement tmpEnr = (Enregistrement) p_enr.getClone();
        if (p_key != Element.NUL_INT ) {
            
            if (p_key >= maxKey) {
                maxKey = p_key + 1;
            }
            
            tmpEnr.key = p_key;
            listEnregistrement.add( tmpEnr );
            /* TableModel */
            /* fireTableRowsInserted( listEnregistrement.size()-1, listEnregistrement.size()-1 );*/
            changedData();
        }
        else {
            add( tmpEnr );
        }
    }
    /**
     * Remove an Enregistrement according to its unique key.
     *
     * @return null if not found
     */
    public Enregistrement remove( int p_key )
    {
        Enregistrement tmpEnr;
        for( int i = 0; i < listEnregistrement.size(); i++ ) {
            tmpEnr = (Enregistrement) listEnregistrement.get( i );
            if( tmpEnr.key == p_key ) {
                listEnregistrement.remove( i );
                
                /* TableModel */
                /* fireTableRowsDeleted( i, i); */
                changedData();
                
                return tmpEnr;
            }
        }
        return null;
    }
    /**
     * Find a given Enregistrement by its key.
     *
     * @return null if not found
     */
    public Enregistrement get( int p_key )
    {
        Enregistrement tmpEnr;
        for (Iterator i = listEnregistrement.iterator(); i.hasNext(); )
        {
            tmpEnr = (Enregistrement) i.next();
            if( tmpEnr.key == p_key ) {
                return tmpEnr;
            }
        }
        return null;
    }
    
    /**
     * Check that an Enregistrement is not too 'close' to another.
     */
    public View getClosest( Enregistrement p_enr )
    {
        // build the SearchOperator
        SearchOperator operator = new SearchOperator();
        // and now for the searched items
        operator.addSearchItem( p_enr.displayData(), 
                myHeader.getField(0) /*dummy Field*/,
                SearchedElement.CLOSE);
        
        return operator.applyTo( this );
    }
    
    /**
     * Find a given Enregistrement by its index.
     */
    public Enregistrement getAt( int p_index )
    {
        return (Enregistrement) listEnregistrement.get( p_index );
    }
    /**
     * Find a piece of data at given position.
     *
     * @param p_index Index of Enregistrement (or row number)
     * @param p_field Index of a Field  (not exactly column number)
     */
    public Element get( int p_index, int p_field )
    {
        int cellIndex = myHeader.getField( p_field).index;
        Enregistrement tmpEnr = getAt( p_index );
        
        return tmpEnr.data[ cellIndex ];
    }
    /** 
     * Find the index of an Element.
     *
     * @return -1 if not found. 
     */
    public int getIndex( Enregistrement p_enr )
    {
        Enregistrement tmpEnr;
        int index = 0;
        for (Iterator i = listEnregistrement.iterator(); i.hasNext(); )
        {
            tmpEnr = (Enregistrement) i.next();
            if( tmpEnr.key == p_enr.key ) {
                return index;
            }
            index++;
        }
        return -1;
    }
    // ---------- a Private Logger ---------------------
    private Logger logger;
    // --------------------------------------------------
    
    /* -------------------------------------------------- 
     *  Extension of Table Model
     * --------------------------------------------------*/
    /** number of Columns */
    public int getColumnCount() {
        return myHeader.theFields.size();
    }
    /** number of Rows */
    public int getRowCount() {
        return listEnregistrement.size();
    }
    /** name of a Column */
    public String getColumnName(int col) {
        return myHeader.getField(col).getLabel();
    }
    /** value of a cell */
    public Object getValueAt(int row, int col) {
        Element aElement ;
        aElement = (Element) get(row, col);
        //logger.debug("getValueAt(" + row + ", " + col + ") = " + aElement.displayData() );
        return aElement.displayData();
    }
    /** dataTable has changed */
    public void changedData()
    {
        fireTableDataChanged();
    }
    /** dataTable stucture has changed */
    public void changedStructure()
    {
        fireTableStructureChanged();
    }
    /* -------------------------------------------------- 
     *  End of Extension of Table Model
     * --------------------------------------------------*/
    
    /* --------------------------------------------------
     *  Implementation of SortTableMode
     * --------------------------------------------------*/
    /**
     * Is a given column 'col' sortable.
     * Yes by defaut.
     */
    public boolean isSortable(int col)
    {
        return true;
    }
    /**
     * Actually sort the Table according to a column.
     */
    public void sortColumn(int col, boolean ascending)
    {
        Collections.sort( listEnregistrement,

                new EnregistrementComparator(col, ascending));
    }
    /* --------------------------------------------------
     *  End of Implementation of SortTableMode
     * --------------------------------------------------*/
    
    /**
     * Sort the Table using a given comparator.
     */
    public void sort( Comparator p_comparator)
    {
        Collections.sort( listEnregistrement, p_comparator );
        changedData();
    }
    
} // Table










