
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JFrame;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import collector.control.Actions;
import collector.control.ChangeListenerBDD;
import collector.control.MenuBarBDD;
import collector.control.MouseListenerBDD;
import collector.control.SelectionListenerBDD;
import collector.data.BaseDeDonnees;
import collector.gui.JBaseDeDonnees;

/**
 * Test the JBDD
 *
 * @version 1.0
 * $Date: 2003/11/13$<br>
 * @author Alain$
 */

class TestJBDD
{
    static JFrame myFrame;
    static TestJBDD myTest;

    BaseDeDonnees myBDD;
    JBaseDeDonnees myJBDD;

    MenuBarBDD myMenuBar;

    Actions myActions;

    /**
     * Creation
     */
    public TestJBDD() 
    {
	//logger = Logger.getLogger(TestJBDD.class);
        Logger.getLogger(TestJBDD.class);   

	myBDD = new BaseDeDonnees();
	try {
	    myBDD.readFromFile( "data/bandesDessinees.dta");
	} catch (Throwable t) {
 	    t.printStackTrace();
 	}

    }

    /**
     * Init the GUI
     */
    public void initGUI( JFrame p_frame) 
	throws IOException
    {
	// initialise Actions
	myActions = new Actions( myFrame, null /* not yet attached */ );

	myJBDD = new JBaseDeDonnees( myBDD, myActions, p_frame, "TestJBDD");
	myMenuBar = new MenuBarBDD( myActions);
    
    // when closing window, call ActionQuit
    p_frame.addWindowListener(new WindowAdapter() {
        public void windowClosing(WindowEvent we) {
            myActions.actionQuit.actionPerformed( null );
        }
    });

	// and Listeners
	//myMouseListenerTable = new MouseListenerTable( myFrame, myJBDD );
	
	// create basic View
	//myJBDD.addView( myBDD.basicView, myMouseListenerTable );
	//myJBDD.addRootView( myBDD.parentView, myMouseListenerTable );

	// add Listener
	myJBDD.setMouseListener( new MouseListenerBDD( myActions) );
	myJBDD.setSelectionListener( new SelectionListenerBDD( myActions ) );
	myJBDD.addChangeListener( new ChangeListenerBDD( myActions ));

	//JPanel mainPanel = new JPanel();
	//mainPanel..setLayout( new BorderLayout( 0 /*hspace*/, 2 /*vspace*/) );
	//mainPanel.add( myJBDD, BorderLayout.CENTER );
	//mainPanel.add( myMenuBar, BorderLayout.NORTH );

	p_frame.setJMenuBar( myMenuBar );
	p_frame.setContentPane( myJBDD );


    }

    /**
     * Create a top-level Frame and add a Context
     */
    public static void main(String[] args) throws IOException {
	// logger configuration 
	PropertyConfigurator.configure("etc/log4j.config");

        //Make sure we have nice window decorations.
        //JFrame.setDefaultLookAndFeelDecorated(true);

        //Create and set up the window.
        myFrame = new JFrame("TestBDD");
        //myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        

	
	myTest = new TestJBDD();
	// create a GUI
	myTest.initGUI( myFrame );



        //Display the window.
        myFrame.pack();
        myFrame.setVisible(true);
    }

    // ---------- a Private Logger ---------------------
    //private Logger logger;
    // --------------------------------------------------
} // TestJBDD
    








