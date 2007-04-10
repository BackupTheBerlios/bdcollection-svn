
package collector.data;

import java.io.*;
import java.util.*;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import org.xml.sax.helpers.AttributesImpl;
import com.megginson.sax.DataWriter;
/**
 * An Element is the basic piece of data.
 * It can be saved/read as xml, displayed as a String.
 *
 * It has a <code>type</code>, a <code>data</code> and a <code>label</code>.
 *
 * @version 1.0
 * $Date: 2003/08/05$<br>
 * @author Alain$
 */

public class ElementStr
    implements Element
{

    /** the type */
    final String type = Element.typeString;
    /** the data **/
    String data;
    /** the associated Field */
    String label;
    
    //** a ref to the ElementFactory */
    //private static ElementFactory myFactory;

    /**
     * Creation
     */
    public ElementStr( String p_label ) 
    {
	logger = Logger.getLogger(ElementStr.class);

	label = p_label ;
	data = Element.NUL_STR;
    }
    /**
     * Creation
     */
    public ElementStr( String p_label, String p_data) 
    {
	logger = Logger.getLogger(ElementStr.class);
	
	label = p_label;
	data = new String( p_data );
    }

    /**
     * classic.
     *
     * Output format:<br>
     * label(Str):data
     */
    public String toString()
    {
	String tmpStr = new String( getLabel() + "(Str):" + data );
	return tmpStr;
    }

    /**
     * Write to a DataWriter.
     *
     * Ouput Format:<br>
     * &lt;label type="type"&gt;data&lt;/label&gt;<br>
     */
    public void toXML( DataWriter w )
	throws org.xml.sax.SAXException
    {
	// prepare the attributes
	AttributesImpl msgAtt = new AttributesImpl();
	msgAtt.addAttribute( "", ATT_ELEMENT_TYPE, "", "CDATA", type);

	w.dataElement( "", getLabel(), "", msgAtt, data);
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
	return label;
    }
    /**
     * Display data as a String.
     */
    public String displayData()
    {
	return data;
    }

    // ---------- a Private Logger ---------------------
    private Logger logger;
    // --------------------------------------------------
} // ElementStr
    








