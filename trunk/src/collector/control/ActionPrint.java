
package collector.control;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.HashSet;
import java.util.Iterator;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;

import org.apache.log4j.Logger;

import com.lowagie.text.DocumentException;

import output.PDFWriter;
import collector.data.BandeDessineeComparator;
import collector.data.Enregistrement;
import collector.data.Field;
import collector.data.Header;
import collector.data.SearchOperator;
import collector.data.SearchedElement;
import collector.data.Table;
import collector.data.View;

/**
 * ActionPrint that write a database on file|screen...
 *
 * @version 1.0
 * $Date: 2004/05/18$<br>
 * @author Alain$
 */

public class ActionPrint extends AbstractAction 
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /** all other actions */
    Actions theActions;
    
    /** Creation */
    ActionPrint( Actions p_actions )
    {
        super();
        logger = Logger.getLogger(Actions.class);
        
        // set Properties
        putValue( AbstractAction.NAME, new String( "Imprimer" ));
        putValue( AbstractAction.MNEMONIC_KEY, new Integer( KeyEvent.VK_I ) );
        putValue( AbstractAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke( KeyEvent.VK_I, ActionEvent.CTRL_MASK) );
        putValue( AbstractAction.SHORT_DESCRIPTION, new String( "Imprime une Table ou une View sur l'�cran ou dans un fichier." ));
        
        theActions = p_actions;
    }
    /** Action : save BDD */
    public void actionPerformed( ActionEvent e )
    {
        logger.info("Print Table");
        
        
        // create a Dialog for print Options
        DialogPrint theDialog = new DialogPrint( theActions.theFrame, "Options" );
        
        // ask the user for the options
        int returnVal = theDialog.askOptions();
        
        logger.debug("Choice made ");
        // check it was a valide choice
        if (returnVal == DialogPrint.CHOICE_OK) {
            // where to write
            if( theDialog.isFileChosen() ) {
            	if( theDialog.getFileKind() == DialogPrint.FILE_PDF ) {
            		writeDataPDF( theDialog.getFile(), theDialog.getSortMethod());
            	}
            	else {
            		try {
            			FileWriter theWriter = new FileWriter( theDialog.getFile() );
            			writeData( theWriter, theDialog.getSortMethod() );
            			theWriter.close();
            		}
            		catch( IOException evt) {
            			logger.error( e.toString() );
            		}
            	}
            }
            else {
                PrintWriter theWriter = new PrintWriter( System.out );
                writeData( theWriter, theDialog.getSortMethod() );
                theWriter.flush();
                
            }
        }
    }
    
    void writeDataPDF( File p_file, int p_option )
    {
//      get the table to be printed
        Table table = theActions.theJBDD.getSelectedTable();
        
        // create the PDFWriter
        PDFWriter theWriter;
        try {
            theWriter = new PDFWriter( p_file, table.getLabel(), theActions.theJBDD.getName());
            
            
            switch(p_option) {
            case DialogPrint.SORT_BD :
            case DialogPrint.SORT_PARENT :
                
                // has a parent = is a list of 'BD' within Series
                if( table.hasParent() ) {
                    // index of the Field for Serie Name
                    Table parentTable = (Table) table.getParent();
                    int indexSerie = parentTable.myHeader.getFieldPosition("Série");
                    
                    // the Choice of Header to be printed
                    Header choiceHeader = new Header();
                    // try to add chosen Fields
                    choiceHeader.add( table.myHeader.getField(table.myHeader.getFieldPosition("Vol.")));
                    choiceHeader.add( table.myHeader.getField(table.myHeader.getFieldPosition("Titre")));
                    choiceHeader.add( table.myHeader.getField(table.myHeader.getFieldPosition("Auteurs")));
                    choiceHeader.add( table.myHeader.getField(table.myHeader.getFieldPosition("Où ?")));
                    
                    // collect the set of parents 
                    HashSet setParent = new HashSet();
                    for (Iterator iEnr = table.listEnregistrement.iterator(); iEnr.hasNext();) {
                        Enregistrement tmpEnr = (Enregistrement) iEnr.next();
                        // add its Serie name
                        Enregistrement tmpParentEnr = (Enregistrement) tmpEnr.getParent();
                        setParent.add( tmpParentEnr.data[indexSerie].displayData());              
                    }
                    
                    for (Iterator iSerie = setParent.iterator(); iSerie.hasNext();) {
                        // generate all SearchRequest
                        String strSerie = (String) iSerie.next();
                        Field fieldSearched = parentTable.myHeader.getField(indexSerie);
                        SearchedElement searchedSerie = new SearchedElement( strSerie, fieldSearched, SearchedElement.SIMILAR); 
                        SearchOperator searchEngine = new SearchOperator();
                        searchEngine.addSearchItem( searchedSerie );
                        View theResult = searchEngine.applyTo( table );
                        
                        if( p_option == DialogPrint.SORT_BD) {
                            // sort Result
                            theResult.sort( new BandeDessineeComparator( true /* ascending */));
                        }
                        // write Result
                        theWriter.addTable(strSerie, theResult, choiceHeader);
                    }
                    
                }
                else { // just send out the table
                    theWriter.addTable(table, table.myHeader);
                }
                break;
            case DialogPrint.SORT_NULL :
                theWriter.addTable(table, table.myHeader);
            }
            theWriter.end();
        } catch (DocumentException e) {
            logger.fatal( e.getMessage());
            e.printStackTrace();
            System.exit( 1 );
        } catch (IOException e) {
            logger.error( e.getMessage());
        }
    }
    
    /** 
     * Write the data to the given Writer with options.
     */
    void writeData( Writer p_write, int p_option )
    {
        try {
            switch(p_option) {
            case DialogPrint.SORT_NULL :
                p_write.write( theActions.theJBDD.getSelectedTable().displayData() );
                break;
            case DialogPrint.SORT_PARENT :
                Table table = theActions.theJBDD.getSelectedTable();
                // has a parent
                if( table.hasParent() ) {
                    Table parent = (Table) table.getParent();
                    p_write.write( "Header " + parent.getLabel() + " : " + parent.myHeader.displayData() + "\n" );
                    p_write.write( "Header " + table.getLabel() + " : " + table.myHeader.displayData() + "\n" );
                    // need 2 Iterators
                    for( Iterator iParent = parent.listEnregistrement.iterator();
                    iParent.hasNext(); ) {
                        Enregistrement enrParent = (Enregistrement) iParent.next();
                        boolean parentWritten = false;
                        for( Iterator iTable = table.listEnregistrement.iterator();
                        iTable.hasNext(); ) {
                            Enregistrement enrTable = (Enregistrement) iTable.next();
                            if( enrTable.getParent().equals( enrParent ) ) {
                                // found one
                                if( parentWritten == false ) {
                                    p_write.write( "\n" + parent.getLabel() + " : " + enrParent.displayData() + "\n" );
                                    p_write.write( "----------\n" );
                                    parentWritten = true;
                                }
                                p_write.write( enrTable.displayData() + "\n" );
                            }
                        }
                    }
                }
                else {
                    p_write.write( theActions.theJBDD.getSelectedTable().displayData() );
                }
                break;
            default :
                logger.warn( "Unknown sort option" );
            }
        }
        catch( IOException e) {
            logger.error( e.toString() );
        }
    }
    // ---------- a Private Logger ---------------------
    private Logger logger;
    // --------------------------------------------------
    
} // ActionPrint









