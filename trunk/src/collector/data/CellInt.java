
package collector.data;

import java.io.Writer;

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

public class CellInt
implements Element, Comparable
{
    
    /** the type */
    final String type = Element.typeInt;
    /** the data **/
    int data;
    /** the associated Field */
    public Field myField;
    
    /** Parent is a CellInt */
    CellInt parent;
    /** Does it inherit ? */
    boolean inheritFlag;
    
    //** a ref to the ElementFactory */
    //private static ElementFactory myFactory;
    
    /**
     * Creation from scratch, empty.
     */
    public CellInt( Field p_field ) 
    {
        logger = Logger.getLogger(CellInt.class);
        
        myField = p_field ;
        data = Element.NUL_INT;
        
        parent = null;
        inheritFlag = false;
    }
    /**
     * Creation from scratch, with data.
     */
    public CellInt( Field p_field, int p_data) 
    {
        logger = Logger.getLogger(CellInt.class);
        
        myField = p_field;
        data = p_data;
        
        parent = null;
        inheritFlag = false;
    }

    /**
     * Creation with shared data.
     */
    public CellInt( Field p_field, CellInt p_src )
    {
        logger = Logger.getLogger(CellInt.class);
        
        myField = p_field;
        
        data = p_src.data;
        parent = p_src.parent;
        inheritFlag = p_src.inheritFlag;
    }
    /**
     * A shallow Clone.
     */
    public Element getClone()
    {
        CellInt truc = new CellInt(myField);
        truc.data = data;  
        
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
        
        str.append( getLabel() + "(Int):" + data + "\n" );
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
        
        //w.dataElement( "", getLabel(), "", msgAtt, new Integer(data).toString());
        w.dataElement( "", TAG_ELEMENT_DATA, "", msgAtt, new Integer(data).toString());
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
            return parent.displayData();
        }
        else {
            return new Integer(data).toString();
        }
    }
    /**
     * Change data from String.
     */
    public void setData( int p_data )
    {
        data = p_data;
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
        if( p_other instanceof CellInt ) {
            CellInt other = (CellInt) p_other;
            return (data - other.data);
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
            parent = (CellInt) p_parent;
        }
        else {
            logger.warn( "Trying to make CellInt inherit from " + p_parent.getType() );
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
} // CellInt









