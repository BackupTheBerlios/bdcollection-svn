
package collector.control;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import org.apache.log4j.Logger;

/**
 * listen for Mouse event, normally on a BDD.
 */
public class MouseListenerBDD extends MouseAdapter 
{
    PopupMenuBDD thePopupMenuBDD;
    
    /** 
     * Creation : PopupMenuBDD.
     */
    public MouseListenerBDD( Actions p_actions )
    { 
	logger = Logger.getLogger(MouseListenerBDD.class);
	thePopupMenuBDD = new PopupMenuBDD( p_actions );
	thePopupMenuBDD.setVisible( false );
    }
    /**
     * Mouse clicked : PopupMenuBDD.
     */
    public void mouseClicked( MouseEvent e ) 
    {
	if( (e.getButton() == MouseEvent.BUTTON3) &&
	    (e.getClickCount() == 1)) {
	    logger.debug( "a MouseClick-3" + " detected on "
			  //+ e.getComponent().getClass().getName() );
			  + e.getComponent().getName() );
	    //thePopupMenuGeneric.setVisible( true );
	    thePopupMenuBDD.updateStatus();
	    thePopupMenuBDD.show( e.getComponent(),
				  e.getX(), e.getY() );
	    
	}
    }
    // ---------- a Private Logger ---------------------
    private Logger logger;
    // --------------------------------------------------
} // MouseListenerBDD
