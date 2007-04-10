
package collector.data;

import java.io.IOException;
import java.io.Writer;

import org.apache.log4j.Logger;

import com.megginson.sax.DataWriter;

/**
 * A Field is the basic component of the header of a Table.
 *
 * <li>label : name of the field
 * <li>typeField : type of Data 
 * <li>index : rank in the referenceTable
 *
 * A Field can inherit from <b>one</b> other Field. None by default.
 *
 * @version 1.0
 * $Date: 2003/07/08$<br>
 * @author Alain$
 */

public class Field 
    implements Element
{
    /** name of the field */
    String label;
    /** type of Data in the Field */
    String typeOfField;
    /** rank in the referenceTable */
    int index;

    /** parent */
    Field parent;

    final static String typeElement = Element.typeField;

    /**
     * Creation from Scratch.
     */
    public Field( String p_label, String p_type, int p_index) 
    {
	logger = Logger.getLogger(Field.class);

	label = p_label;
	typeOfField = p_type;
	index = p_index;

	parent = null;
    }
    /**
     * Creation from Scratch.
     */
    public Field()
    {
	logger = Logger.getLogger(Field.class);

	label = Element.NUL_STR;
	typeOfField = Element.NUL_STR;
	index = Element.NUL_INT;

	parent = null;
    }
    /**
     * Creation that share label, typeOfField and inheritance.
     */
    public Field( int p_rank, Field p_field )
    {
	logger = Logger.getLogger(Field.class);

	label = p_field.label;
	typeOfField = p_field.typeOfField;
	index = p_rank;

	parent = p_field.parent;
    }
    /**
     * A shallow clone.
     */
    public Element getClone()
    {
        Field truc = new Field( label, typeOfField, index);
        
        return truc;
    }
    /**
     * classic.
     *
     * Output format:<br>
     * label[index] (of typeOfField)<br>
     * parent: label | no-parent<br>
     * 
     */
    public String toString()
    {
	StringBuffer str = new StringBuffer();

	str.append( label + " [" + index + "] (of " + typeOfField + ")\n" );
	if( hasParent() ) {
	    str.append( "parent: " + parent.getLabel() + "\n");
	}
	else {
	    str.append( "no parent\n" );
	}
	       
	return str.toString();
    }
    /**
     * Write to a DataWriter.
     *
     * Ouput Format:<br>
     * &lt;Field&gt;<br>
     * &npsb;&nbsp;&lt;label&gt;label&lt;/label&gt;<br>
     * &npsb;&nbsp;&lt;typeOfField&gt;typeOfField&lt;/typeOfField&gt;<br>
     * &npsb;&nbsp;&lt;index&gt;index&lt;/index&gt;<br>
     * &lt;Field&gt;
     */
    public void toXML( DataWriter w, Writer fw )
	throws org.xml.sax.SAXException
    {
	w.startElement( TAG_ELEMENT_FIELD);
	if( hasParent() ) {
	    //w.dataElement( TAG_ELEMENT_PARENT, getParent().getLabel() );
	    w.startElement( TAG_ELEMENT_PARENT );
	    try {
		fw.write( getParent().getLabel() );
	    } catch(IOException e) {
		logger.error( e.toString() );
	    }
	    w.endElement( TAG_ELEMENT_PARENT );
	}
	else {
	    w.dataElement( TAG_ELEMENT_PARENT, NUL_STR );
	}
	//w.dataElement( TAG_ELEMENT_LABEL, label );
	w.startElement( TAG_ELEMENT_LABEL );
	try {
	    fw.write( label );
	} catch(IOException e) {
	    logger.error( e.toString() );
	}
	w.endElement( TAG_ELEMENT_LABEL );
	w.dataElement( TAG_ELEMENT_TYPEFIELD, typeOfField );
	Integer tmpInt = new Integer( index );
	w.dataElement( TAG_ELEMENT_INDEX, tmpInt.toString() );
	w.endElement( TAG_ELEMENT_FIELD );
    }

    /**
     * Access type
     */
    public String getType()
    {
	return typeElement;
    }
    /** 
     * Type of data in Field
     */
    public String getTypeOfField()
    {
	return typeOfField;
    }
    /**
     * Access label
     */
    public String getLabel()
    {
	return label;
    }
    /**
     * Acces index.
     */
    public int getIndex()
    {
	return index;
    }
    /**
     * Display data as a String.
     */
    public String displayData()
    {
	return label;
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
	if( p_parent.getType().equals( Element.typeField ) ) {
	    parent = (Field) p_parent;
	}
	else {
	    logger.warn( "Trying to make Field inherit from " + p_parent.getType() );
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


    // ---------- a Private Logger ---------------------
    private Logger logger;
    // --------------------------------------------------
} // Field
    








