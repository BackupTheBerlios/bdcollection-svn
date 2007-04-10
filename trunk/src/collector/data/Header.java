
package collector.data;

import java.io.Writer;

import javax.swing.DefaultListModel;

import org.apache.log4j.Logger;

import com.megginson.sax.DataWriter;

/**
 * A Header is an ordered list of Field. This list can be used as a JListModel.
 *
 * It has no parent. 
 *
 * @version 1.0
 * $Date: 2003/08/07$<br>
 * @author Alain$
 */

public class Header
implements Element
{
    
    /** the Element.type */
    final static String typeElement = Element.typeHeader;
    
    /** the label */
    final static String label = Element.typeHeader;
    
    /** the Fields */
    public DefaultListModel theFields;
    
    /**
     * Creation
     */
    public Header() 
    {
        logger = Logger.getLogger(Header.class);
        
        theFields = new DefaultListModel();
    }
    /**
     * A shallow clone.
     */
    public Element getClone()
    {
        Header truc = new Header();
        
        for( int i = 0; i < theFields.size(); i++ ) {
            Field tmpField = (Field) theFields.get(i);
            truc.add( (Field) tmpField.getClone() );
        }
        
        return truc;
    }
    /**
     * classic.
     *
     * Output format:<br>
     * [...] Field # -> Field.toString()
     * 
     */
    public String toString()
    {
        StringBuffer str = new StringBuffer();
        
        Field tmpField;
        for( int i = 0; i < theFields.size(); i++ ) {
            tmpField = (Field) theFields.get(i);
            str.append("Field " + i + " -> " + tmpField.toString() );
            str.append("\n");
        }
        
        return str.toString();
    }
    /**
     * Write to a DataWriter.
     *
     * Ouput Format:<br>
     * &lt;header&gt;<br>
     * &npsb;&nbsp;[...] Field.toXML()<br>
     * &lt;header&gt;
     */
    public void toXML( DataWriter w, Writer fw )
    throws org.xml.sax.SAXException
    {
        w.startElement( TAG_ELEMENT_HEADER);
        
        Field tmpField;
        for( int i = 0; i < theFields.size(); i++ ) {
            tmpField = (Field) theFields.get(i);
            tmpField.toXML( w,fw );
        }
        
        w.endElement( TAG_ELEMENT_HEADER );
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
        
        str.append("|");
        Field tmpField;
        for( int i = 0; i < theFields.size(); i++ ) {
            tmpField = (Field) theFields.get(i);
            str.append( " " + tmpField.displayData() + " |");
        }
        
        return str.toString();
    }
    
    /**
     * Adds a new Field at end.
     *
     * @todo Check that index is well updated....
     */
    public void add( Field p_field )
    {
        theFields.addElement( p_field );
    }
    /**
     * Adds a new Field at indexed position.
     *
     * <code>p_index</code> is forced to belong to [O,size[
     */
    public void add( Field p_field, int p_index )
    {
        if( p_index < 0 ) p_index = 0;
        if( p_index > theFields.size() ) p_index = theFields.size();
        
        theFields.add(p_index, p_field);
    }
    /**
     * Remove at indexed position.
     *
     * <code>p_index</code> is forced to belong to [O,size[
     */
    public Field remove( int p_index )
    {
        if( p_index < 0 ) p_index = 0;
        if( p_index > theFields.size() ) p_index = theFields.size();
        
        return (Field) theFields.remove(p_index);
        
    }
    /**
     * Remove according to label.
     *
     * @return null if not found
     */
    public Field remove( String p_label )
    {
        Field tmpField;
        for( int i = 0; i < theFields.size(); i++ ) {
            tmpField = (Field) theFields.get( i );
            if( tmpField.label.equals( p_label )) {
                theFields.remove( i );
                
                return tmpField;
            }
        }
        
        return null;
    }
    /**
     * Get a field at indexed position.
     * p_index is not checked.
     */
    public Field getField( int p_index )
    {
        return (Field) theFields.elementAt( p_index );
    }
    /**
     * Get a field position by its name.
     * 
     * @return -1 if not found.
     */
    public int getFieldPosition( String p_name )
    {
        Field tmpField;
        for( int i = 0; i < theFields.size(); i++ ) {
            tmpField = (Field) theFields.get( i );
            if( tmpField.label.equals( p_name )) {
                
                return i;
            }
        }
        
        return -1;
    }
    /**
     * Recompute Field.index according to they current position in Header.
     *
     * Is it really usefull????
     */
    public void recomputeIndex()
    {
        Field tmpField;
        for( int i = 0; i < theFields.size(); i++ ) {
            tmpField = (Field) theFields.get(i);
            tmpField.index = i;
        }
    }
    
    /**
     * Ask wether it has a parent.
     */
    public boolean hasParent()
    {
        return false;
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
        logger.warn( "Header can't have parent" );
    }
    /**
     * Delete the parent.
     */
    public void deleteParent()
    {
    }
    
    /**
     * Parent can be retrieved.
     */
    public Element getParent()
    {
        return null;
    }
    
    
    // ---------- a Private Logger ---------------------
    private Logger logger;
    // --------------------------------------------------
} // Header









