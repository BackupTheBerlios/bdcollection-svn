
package collector.data;

import java.io.Writer;

import org.apache.log4j.Logger;
import org.xml.sax.helpers.AttributesImpl;

import com.megginson.sax.DataWriter;
/**
 * An Enregistrement is an Array of Cell and an unique key.
 * It is also an Element.
 *
 * An Enregistrement can inherit from <b>one</b> parent Enregistrement.
 *
 * @version 1.0
 * $Date: 2003/08/22$<br>
 * @author Alain$
 */

public class Enregistrement 
implements Element
{
    
    /** Array of Cell : the main data */
    public Element data[];
    /** the key should be unique */
    public int key;
    /** linked Header */
    Header myHeader;
    
    /** type is common for all Enregistrement */
    final static String typeElement = Element.typeEnregistrement;
    /** the label */
    final static String label = Element.typeEnregistrement;
    
    /** Its parent must be an Enregistrement */
    Enregistrement parent;
    
    /**
     * Creation.
     *
     * @param p_header All the info about the fields and the size of the Array.
     */
    public Enregistrement( Header p_header) 
    {
        logger = Logger.getLogger(Enregistrement.class);
        
        key = Element.NUL_INT;
        myHeader = p_header;
        data = new Element[ myHeader.theFields.size() ];
        
        parent = null;
    }
    
    /**
     * A shallow clone.
     */
    public Element getClone()
    {
        Enregistrement truc = new Enregistrement( myHeader);
        
        truc.key = key;
        truc.parent = parent;
        
        for (int i = 0; i < data.length; i++) {
            truc.data[i] = data[i].getClone();
        }
        
        return truc;
    }
    /**
     * classic.
     *
     * Output format:<br>
     * Enr (key) parent: key | no-parent<br>
     * [...] Cell.toString()
     */
    public String toString()
    {
        StringBuffer str = new StringBuffer();
        
        str.append( getLabel() + " (" + key + ")");
        if( hasParent() ) {
            str.append( " parent: " + parent.key + "\n" );
        }
        else {
            str.append( " no-parent\n" );
        }
        
        for( int i = 0; i < data.length; i++ ) {
            str.append( data[i].toString() + "\n");
        }
        
        return str.toString();
    }
    
    /**
     * Write to a DataWriter.
     *
     * Ouput Format:<br>
     * &lt;_enregistrement _key="key"&gt;<br>
     * &npsb;&nbsp;[...] Element.toXML()<br>
     * &lt;_enregistrement&gt;
     */
    public void toXML( DataWriter w, Writer fw )
    throws org.xml.sax.SAXException
    {
        // prepare the attributes
        AttributesImpl msgAtt = new AttributesImpl();
        Integer tmpInt = new Integer( key );
        msgAtt.addAttribute( "", ATT_ELEMENT_KEY, "", "CDATA", tmpInt.toString() );
        
        w.startElement( "", TAG_ELEMENT_ENREGISTREMENT, "", msgAtt);
        
        if( hasParent() ) {
            Integer parentKey = new Integer( parent.key );
            w.dataElement( TAG_ELEMENT_PARENT, parentKey.toString() );
        }
        else {
            w.dataElement( TAG_ELEMENT_PARENT, NUL_STR );
        }
        
        for( int i = 0; i < data.length; i++ ) {
            data[i].toXML(w,fw);
        }
        
        w.endElement( TAG_ELEMENT_ENREGISTREMENT );
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
        for( int i = 0; i < data.length; i++ ) {
            str.append( " " + data[i].displayData() + " |");
        }
        
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
        if( p_parent.getType().equals( Element.typeEnregistrement ) ) {
            parent = (Enregistrement) p_parent;
            
            // same for childs
            /*for( int i = 0; i < data.length; i++ ) {
		// index of parent Field for i
		String parentFieldName = myHeader.getField( i ).getParent().getLabel(); 
		int parentIndex = ((Header) myHeader.getParent()).getFieldPosition( parentFieldName );
		logger.debug( "Field " + i + " parent = " + parentIndex + " ( " + parentFieldName + ")" );
		data[i].setParent( ((Enregistrement) p_parent).data[ parentIndex ]);
		}*/
        }
        else {
            logger.warn( "Trying to make Enregistrement inherit from " + p_parent.getType() );
        }
    }
    /**
     * Set the parent (for Cell also).
     */
    public void setParentRecursif( Enregistrement p_parent )
    {
        logger.debug( "setParentRecursif for \n" + toString() + "with Header\n" + myHeader.toString() );
        
        setParent( p_parent );
        
        // same for childs
        for( int i = 0; i < data.length; i++ ) {
            logger.debug( "Field " + i + " ---------");
            // index of parent Field for i
            Field parentField = (Field) myHeader.getField( i ).getParent();
            if( parentField != null ) {
                int parentFieldIndex = parentField.getIndex();
                logger.debug( "--> ("  + parentField.getLabel() + ") parentFieldIndex = " + parentFieldIndex);
                data[i].setParent( ((Enregistrement) p_parent).data[ parentFieldIndex ]);
            }
            else {
                logger.debug( "Field " + i + " has no parent");
            }
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
     * Adds a new piece of data at indexed position.
     *
     * Furthermore, we check that cell[i] is allowed to be a String.
     *
     * <p><b>Todo</b>: use exceptions?</p>
     */
    public boolean add( int p_index, String p_data, boolean p_inherit )
    {
        logger.debug("Adding a String to field index="+p_index);
        logger.debug(myHeader.getField( p_index ).toString() );
        logger.debug(myHeader.getField( p_index ).typeOfField);
        
        if( (myHeader.getField( p_index ).typeOfField).equals( Element.typeString ) ) {
            CellStr myCellStr = new CellStr( myHeader.getField( p_index ), p_data );
            
            // Has a parent ?
            if( hasParent() ) {
                // look for the right field
                Field parentField =  myHeader.getField( p_index ).parent;
                if( parentField != null ) {
                    int indexParent = parentField.index;
                    myCellStr.setParent( parent.data[indexParent] );
                    myCellStr.setInherit( p_inherit);
                }
            }
            data[p_index] = myCellStr;
            return true;
        }
        else if( (myHeader.getField( p_index ).typeOfField).equals( Element.typeInt ) ) {
            if( p_data.equals( "" ) ) {
                p_data = Element.NUL_STR;
            }
            CellInt myCellInt = new CellInt( myHeader.getField( p_index ), new Integer(p_data).intValue() );
            
            // Has a parent ?
            if( hasParent() ) {
                // look for the right field
                Field parentField =  myHeader.getField( p_index ).parent;
                if( parentField != null ) {
                    int indexParent = parentField.index;
                    myCellInt.setParent( parent.data[indexParent] );
                    myCellInt.setInherit( p_inherit);
                }
            }
            data[p_index] = myCellInt;
            return true;
        }
        else {
            logger.warn("Field(" + p_index + ") is not of typeString");
        }
        return false;
    }
    /**
     * Adds a shared data at indexed position from another Enregistrement.
     *
     * Check that types are compatibles.
     *
     * <p><b>Todo</b>: use exceptions?</p>
     */
    public boolean add( int p_index, Enregistrement p_src, int p_srcIndex )
    {
        logger.debug("Adding shared data from Enregistremto to field index="+p_index);
        logger.debug(myHeader.getField( p_index ).toString() );
        logger.debug(myHeader.getField( p_index ).typeOfField);
        
        // check types
        if( (myHeader.getField( p_index ).typeOfField).equals( p_src.myHeader.getField( p_srcIndex ).typeOfField) ) {
            if( (myHeader.getField( p_index ).typeOfField).equals( Element.typeString ) ) {
                CellStr myCellStr = new CellStr( myHeader.getField( p_index ), (CellStr) p_src.data[p_srcIndex] );
                data[p_index] = myCellStr;
                return true;
            }
            else if( (myHeader.getField( p_index ).typeOfField).equals( Element.typeInt ) ) {
                CellInt myCellInt = new CellInt( myHeader.getField( p_index ), (CellInt) p_src.data[p_srcIndex] );
                data[p_index] = myCellInt;
                return true;
            }
            else {
                logger.warn(" Field(" + p_index + ") is of unknown type.");
            }
            return false;
        }
        else {
            logger.warn("Field(" + p_index + ") is not of same type than Field(" + p_srcIndex + ") of srcEnregistrement");
            logger.debug(p_index + "-->" + myHeader.getField(p_index).toString());
            logger.debug(p_index + "-->" + myHeader.getField(p_srcIndex).toString());
        }
        return false;
    }
    // ---------- a Private Logger ---------------------
    private Logger logger;
    // --------------------------------------------------
    
} // Enregistrement









