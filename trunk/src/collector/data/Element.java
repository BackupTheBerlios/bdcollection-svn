
package collector.data;

import java.io.Writer;

/**
 * An Element is the basic piece of data. It can be saved/read as xml,
 * displayed as a String.
 *
 * It has a <code>type</code>, a <code>data</code> and a <code>label</code>.
 * It can also have a parent.
 * 
 * @version 1.0
 * $Date: 2003/08/05$<br>
 * @author Alain$
 */

public interface Element
{

    /** the type of element **/
    final static String typeString = "String";
    final static String typeInt = "Int";
    final static String typeField = "Field";
    final static String typeHeader = "Header";
    final static String typeEnregistrement = "Enregistrement";
    final static String typeTable = "Table";
    
    // ---------- Some Constants ---------------------
    final static String ATT_ELEMENT_TYPE = "_type";
    final static String ATT_ELEMENT_KEY = "_key";
    final static String ATT_ELEMENT_INHERIT = "_inherit";
    final static String TAG_ELEMENT_HEADER = "_header";
    final static String TAG_ELEMENT_FIELD = "_field";
    final static String TAG_ELEMENT_LABEL = "_label";
    final static String TAG_ELEMENT_TYPEFIELD = "_typeOfField";
    final static String TAG_ELEMENT_INDEX = "_index";
    final static String TAG_ELEMENT_ENREGISTREMENT = "_enregistrement";
    final static String TAG_ELEMENT_TABLE = "_table";
    final static String TAG_ELEMENT_PARENT = "_parent";
    final static String TAG_ELEMENT_PARENT_TABLE = "_parent_table";
    final static String TAG_ELEMENT_DATA = "_data";

    final static String NUL_STR = "-1";
    final static int NUL_INT = -1;

    /**
     * classic.
     *
     * Output format:<br>
     * label(type):data
     */
    public String toString();

    /**
     * Write to a DataWriter.
     *
     * Ouput Format:<br>
     * &lt;label type="type"&gt;data&lt;/label&gt;<br>
     */
    public void toXML( com.megginson.sax.DataWriter w, Writer fw )
	throws org.xml.sax.SAXException;

    /**
     * Access type
     */
    public String getType();
    /**
     * Access label
     */
    public String getLabel();
    /**
     * Display data as a String.
     */
    public String displayData();

    /**
     * A shallow clone.
     */
    public Element getClone();
    
    /**
     * Ask wether it has a parent.
     */
    public boolean hasParent();
    /**
     * Ask wether it is inheriting its parent.
     */
    public boolean isInherit();
    /**
     * Parent is usually of the same type.
     */
    public void setParent( Element p_parent );
    /**
     * Parent can be retrieved.
     */
    public Element getParent();

} // Element
    








