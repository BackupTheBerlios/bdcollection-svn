
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;

import javax.swing.JFrame;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
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
	static File startBDD = null;
	
	BaseDeDonnees myBDD;
	JBaseDeDonnees myJBDD;

	MenuBarBDD myMenuBar;

	Actions myActions;

	/** To store preferences : JFrame position */
    Preferences thePref;
    static final String KEYX = "keyPrefX";
    static final String KEYY = "keyPrefY";
	
	/**
	 * Creation
	 */
	public TestJBDD() 
	{
		//logger = Logger.getLogger(TestJBDD.class);
		Logger.getLogger(TestJBDD.class);   

		//		 set our Preferences root
		Preferences ourRoot = Preferences.userNodeForPackage( getClass() );
		thePref = ourRoot;
		if( startBDD != null ) {
			try {
				myBDD = new BaseDeDonnees();
				//myBDD.readFromFile( "data/bandesDessinees.dta");
				myBDD.readFromFile( startBDD.getAbsolutePath());
				// update thePref
				thePref = ourRoot.node( myBDD.fileDesc.getPath() );
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
		else {
			myBDD = null;
		}

	}

	/**
	 * Init the GUI
	 */
	public void initGUI( JFrame p_frame) 
	throws IOException
	{
		// set Location
		p_frame.setLocation(thePref.getInt(KEYX, 10), thePref.getInt(KEYY, 10));
		
		// initialise Actions
		myActions = new Actions( myFrame, null /* not yet attached */ );

		if( myBDD != null ) {
			myJBDD = new JBaseDeDonnees( myBDD, myActions, p_frame, "TestJBDD");
		}
		else {
			myJBDD = new JBaseDeDonnees( myActions, p_frame, "TestJBDD");
		}
		myMenuBar = new MenuBarBDD( myActions);

		// when closing window, call ActionQuit
		p_frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				myActions.actionQuit.actionPerformed( null );
			}
		});
		// when moved, store position in Preferences
		p_frame.addComponentListener( new ComponentAdapter() {
			public void componentMoved( ComponentEvent e) {
				thePref.putInt( KEYX, e.getComponent().getX());
				thePref.putInt( KEYY, e.getComponent().getY());
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
	
	static void checkConfig()
	{
		// User Home Dir
		File userHomeFile = new File(System.getProperty( "user.home"));
		
		// $HOME/.collector/log4j.config exists
		File collectorHome = new File( userHomeFile, ".collector");
		File loggerConfigFile = new File( collectorHome, "log4j.config");
		if( !loggerConfigFile.exists() ) {
			// Set up a simple configuration that logs on the console.
		     BasicConfigurator.configure();
		     Logger.getRootLogger().setLevel(Level.FATAL);
		     System.err.println( "Basic Configuration");
		}
		else {
			//logger configuration
			PropertyConfigurator.configure( loggerConfigFile.getAbsolutePath());
			System.err.println( "File Configuration");
		}
	}

	/**
	 * Create a top-level Frame and add a Context
	 */
	public static void main(String[] args) throws IOException {
		
		// first parameter may be a BDD name
		if( args.length > 0) {
			startBDD = new File( args[0] );
		}
		
		checkConfig();

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









