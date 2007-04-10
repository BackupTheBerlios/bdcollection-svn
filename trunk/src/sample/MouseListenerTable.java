
package collector.control;

import java.io.*;
import java.util.*;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;

import collector.data.*;
import collector.gui.*;
/**
 * A MouseListener for Table.
*
 * @version 1.0
 * $Date: 2003/12/30$<br>
 * @author Alain$
 */

public class MouseListenerTable extends MouseAdapter 
{
    PopupMenuTable thePopupMenu;
	/** 
	 * Creation : PopupMenu.
	 */
	public MouseListenerTable( JFrame p_frame, JBaseDeDonnees p_jbdd  )
	{
	    logger = Logger.getLogger(MouseListenerTable.class);

	    thePopupMenu = new PopupMenuTable( p_frame, p_jbdd );
	    thePopupMenu.setVisible( false );
	}
    /**
     * Mouse clicked : PopupMenuTable is visible.
     */
    public void mouseClicked( MouseEvent e ) 
    {
	if( (e.getButton() == MouseEvent.BUTTON3) &&
	    (e.getClickCount() == 1)) {
	    logger.debug( "a MouseClick-3" + " detected on "
			  //+ e.getComponent().getClass().getName() );
			  + e.getComponent().getName() );
	    //thePopupMenuGeneric.setVisible( true );
	    thePopupMenu.updateStatus();
	    thePopupMenu.show( e.getComponent(),
			       e.getX(), e.getY() );
	    
	}
    }
    
    // ---------- a Private Logger ---------------------
    private Logger logger;
    // --------------------------------------------------


} // MouseListenerTable
