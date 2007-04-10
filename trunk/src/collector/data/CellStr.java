
package collector.data;

import java.io.IOException;
import java.io.Writer;

import java.lang.ClassCastException;

import org.apache.log4j.Logger;
import org.xml.sax.helpers.AttributesImpl;

import com.megginson.sax.DataWriter;
/**
 * An Cell is the basic piece of data, always associated to a Field.
 * It can be saved/read as xml, displayed as a String.
 *
 * It has a <code>type</code>, a <code>data</code> and its label comes
 * from the Field.
 *
 * @version 1.0
 * $Date: 2003/08/05$<br>
 * @author Alain$
 */

public class CellStr
implements Element, Comparable
{
    
    /** the type */
    final String type = Element.typeString;
    /** the data **/
    StringBuffer data;
    /** the associated Field */
    public Field myField;
    
    /** Parent is a CellStr */
    CellStr parent;
    /** Does it inherit ? */
    boolean inheritFlag;
    
    //** a ref to the ElementFactory */
    //private static ElementFactory myFactory;
    
    /**
     * Creation from scratch, empty.
     */
    public CellStr( Field p_field ) 
    {
        logger = Logger.getLogger(CellStr.class);
        
        myField = p_field ;
        data = new StringBuffer( Element.NUL_STR );
        
        parent = null;
        inheritFlag = false;
    }
    /**
     * Creation from scratch, with data.
     */
    public CellStr( Field p_field, String p_data) 
    {
        logger = Logger.getLogger(CellStr.class);
        
        myField = p_field;
        data = new StringBuffer( p_data );
        
        parent = null;
        inheritFlag = false;
    }
    /**
     * Creation with shared data.
     */
    public CellStr( Field p_field, CellStr p_src )
    {
        logger = Logger.getLogger(CellStr.class);
        
        myField = p_field;
        
        data = p_src.data;
        parent = p_src.parent;
        inheritFlag = p_src.inheritFlag;
    }

    /**
     * A shallow clone.
     */
    public Element getClone()
    {
        CellStr truc = new CellStr( myField);
        
        truc.data = new StringBuffer( data );
        truc.parent = parent;
        truc.inheritFlag = inheritFlag;
        
        return truc;
    }
    
    /**
     * classic.
     *
     * Output format:<br>
     * label(Str):data<br>
     * parent:parent.data (inherit) | no-parent
     */
    public String toString()
    {
        StringBuffer str = new StringBuffer();
        
        str.append( getLabel() + "(Str):" + data + "\n" );
        if( hasParent() ) {
            str.append( "parent: " + parent.data + " (" + inheritFlag + ")\n" );
        }
        else {
            str.append( "no-parent\n" );
        }
        return str.toString();
    }
    
    /**
     * Write to a DataWriter.
     *
     * Ouput Format:<br>
     * &lt;label type="type"&gt;data&lt;/label&gt;<br>
     */
    public void toXML( DataWriter w, Writer fw )
    throws org.xml.sax.SAXException
    {
        // prepare the attributes
        AttributesImpl msgAtt = new AttributesImpl();
        msgAtt.addAttribute( "", ATT_ELEMENT_TYPE, "", "CDATA", type);
        
        // inherit from parent
        Boolean tmpBool = new Boolean( inheritFlag );
        msgAtt.addAttribute( "", ATT_ELEMENT_INHERIT, "", "CDATA", tmpBool.toString() );
        
        //w.dataElement( "", getLabel(), "", msgAtt, data.toString() );
        w.startElement( "", TAG_ELEMENT_DATA, "", msgAtt );
        try {
            fw.write( data.toString() );
        } catch(IOException e) {
            logger.error( e.toString() );
        }
        w.endElement(  TAG_ELEMENT_DATA);
    }
    /**
     * Access type
     */
    public String getType()
    {
        return type;
    }
    /**
     * Access label : the label of the Field
     */
    public String getLabel()
    {
        return myField.getLabel();
    }
    /**
     * Display data as a String.
     */
    public String displayData()
    {
        if( isInherit() ) {
            return parent.data.toString();
        }
        else {
            return data.toString();
        }
    }
    /**
     * Change data from String.
     */
    public void setData( String p_data )
    {
        data.delete( 0, data.length() );
        data.append( p_data );
    }
    
    /**
     * Implements comparison with anoter Element.
     * @param p_other must be an Element
     * @return comparison with p_other.displayData()
     * @throws ClassCastException if not an Element
     */
    public int compareTo(Object p_other)
    {
        // check if its an Element
        if( p_other instanceof Element ) {
            Element other = (Element) p_other;
            return displayData().compareToIgnoreCase( other.displayData() );
        }
        throw new ClassCastException( "Not an Element");
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
     * Parent is usually of the same type.
     */
    public void setParent( Element p_parent )
    {
        if( p_parent.getType().equals( Element.typeString ) ) {
            parent = (CellStr) p_parent;
        }
        else {
            logger.warn( "Trying to make CellStr inherit from " + p_parent.getType() );
        }
    }
    /**
     * Delete the parent.
     */
    public void deleteParent()
    {
        parent = null;
    }
    /**
     * Parent can be retrieved.
     */
    public Element getParent()
    {
        return parent;
    }
    
    /** 
     * Can only inherit if hasParent.
     */
    public void setInherit( boolean p_flag )
    {
        if( hasParent() ) {
            inheritFlag = p_flag;
        }
    }
    /** 
     * Is it inheriting?
     */
    public boolean isInherit()
    {
        return inheritFlag;
    }
    
    // ---------- a Private Logger ---------------------
    private Logger logger;
    // --------------------------------------------------
    
    
} // CellStr









