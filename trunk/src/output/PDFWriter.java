/*
 * Created on Jan 4, 2007
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package output;

import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import collector.data.Enregistrement;
import collector.data.Field;

import com.lowagie.text.Cell;
import com.lowagie.text.Chapter;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Section;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;

public class PDFWriter {
    
    Document myDocument;
    Chapter myChapter;
    Section mySection;
    PdfWriter myWriter;
    BaseFont myBaseFont;
    Font myBodyFont;
    
    /**
     * Create a PDF document that will be saved in 'p_output',
     * with a runningTitle.
     * @param p_output
     * @param p_runTitle can be null
     */
    public PDFWriter( File p_output, String p_title, String p_runTitle )
       throws DocumentException, IOException
    {
        // Create document
        // Document document = new Document(PageSize.A4, 50, 50, 70, 70);
        myDocument = new Document();
        // step 2:
        // we create a writer that listens to the document
        // and directs a PDF-stream to a file
        myWriter = PdfWriter.getInstance(myDocument,
                new FileOutputStream(p_output));
        
        // get Fonts
        myBaseFont = BaseFont.createFont("Helvetica", BaseFont.WINANSI, false);
        myBodyFont = new Font( myBaseFont, 10);
        
        // we add some page Event to deal with page number and running title
        myWriter.setPageEvent(new PDFEndPage( myDocument, p_runTitle, myBaseFont ));
        
        
        // step 3: we open the document
        myDocument.open();
        
        if( p_title != null ) {
            Paragraph title = new Paragraph( p_title, FontFactory.getFont(FontFactory.HELVETICA, 18, Font.BOLDITALIC, new Color(0, 0, 255)));
            myChapter = new Chapter(title, 0 /*number*/);
        }
        else {
            myChapter = new Chapter( "", 0 /*number*/);
        }
        // do not print Chapter number
        myChapter.setNumberDepth( 0 );
        mySection = null;
       
    }
    /**
     * Add a section to the document. 
     * @param p_section
     */
    public void addSection( String p_section )
    {   
        Paragraph titleSec = new Paragraph(p_section, FontFactory.getFont(FontFactory.HELVETICA, 16, Font.BOLD, new Color(255, 0, 0)));
        mySection = myChapter.addSection(titleSec);
    }
    /**
     * Add a paragraph to the document.
     * @param p_text
     */
    public void addParagraph( String p_text )
    {
        Paragraph para = new Paragraph( p_text, myBodyFont );
        if( mySection != null ) {
            mySection.add( para );
        }
        else {
            myChapter.add( para );
        }
    }
    /**
     * Add a Table to the document with the elements from 'p_table',
     * but with only the columns described by 'p_choix'.
     * @param p_table contains the data
     * @param p_choix is an orderer list of column to print
     * @throws DocumentException 
     */
    public void addTable( collector.data.Table p_table, 
                          collector.data.Header p_choix)
        throws DocumentException
    
    {
        // nb of column
        int nbCol = p_choix.theFields.size();
        Table datatable = new Table( nbCol );
        
        int headerwidths[] = new int[nbCol];
        for( int i=0; i<nbCol; i++ ) {
            headerwidths[i] = 1;
        }
        datatable.setWidths(headerwidths);
        datatable.setWidth(100);
        datatable.setPadding(3);
        
        // cell for header, with grey background
        datatable.setDefaultCellBorderWidth(2);
        datatable.setDefaultHorizontalAlignment( Element.ALIGN_CENTER);

        Field tmpField;
        for( int i = 0; i < p_choix.theFields.size(); i++ ) {
            tmpField = (Field) p_choix.theFields.get(i);
            Cell zeCell = new Cell( new Phrase(tmpField.displayData(),myBodyFont));
            zeCell.setBackgroundColor( new Color( 0.9f, 0.9f, 0.9f));
            datatable.addCell( zeCell );
        }
        // this is the end of the table header
        datatable.endHeaders();
        
        // now for each Enregistrement
        datatable.setDefaultCellBorderWidth(1);
        datatable.setDefaultHorizontalAlignment( Element.ALIGN_LEFT);
        for (Iterator ite = p_table.listEnregistrement.iterator(); ite.hasNext(); )
        {
            Enregistrement tmpEnr = (Enregistrement) ite.next();
            for( int i = 0; i < p_choix.theFields.size(); i++ ) {
                tmpField = (Field) p_choix.theFields.get(i);
                Cell zeCell = new Cell( new Phrase(tmpEnr.data[tmpField.getIndex()].displayData(), myBodyFont) );
                datatable.addCell( zeCell );
            }
        }
        
        if( mySection != null ) {
            mySection.add( datatable);
        }
        else {
            myChapter.add( datatable );
        }
        
    }
    /**
     * Add a Table with Title as a new section to the document 
     * with the elements from 'p_table',
     * but with only the columns described by 'p_choix'.
     * @param p_titre Title of the Table
     * @param p_table contains the data
     * @param p_choix is an orderer list of column to print
     * @throws DocumentException 
     */
    public void addTable( String p_titre, collector.data.Table p_table, 
                          collector.data.Header p_choix)
        throws DocumentException
    {
        addSection(p_titre);
        addTable( p_table, p_choix );
    }
    /**
     * Ends the document and print it.
     */
    public void end()
    {
        try {
            myDocument.add( myChapter );
        }
        catch( DocumentException de ) {
            System.out.println( "PDFWriter.end() " + de.getMessage());
        }
        
        // we can close the document
        myDocument.close();
    }
    
    public void testHello() {
        //  step 1: creation of a document-object
        Document document = new Document();
        try {
            // step 2:
            // we create a writer that listens to the document
            // and directs a PDF-stream to a file
            PdfWriter.getInstance(document,
                    new FileOutputStream("tmp/HelloWorld.pdf"));
            
            // step 3: we open the document
            document.open();
            
            Paragraph title2 = new Paragraph("This is Chapter 2", FontFactory.getFont(FontFactory.HELVETICA, 18, Font.BOLDITALIC, new Color(0, 0, 255)));
            Chapter chapter2 = new Chapter(title2, 2);
            chapter2.setNumberDepth(0);
            Paragraph someText = new Paragraph("This is some text");
            chapter2.add(someText);
            Paragraph title21 = new Paragraph("This is Section 1 in Chapter 2", FontFactory.getFont(FontFactory.HELVETICA, 16, Font.BOLD, new Color(255, 0, 0)));
            Section section1 = chapter2.addSection(title21);
            Paragraph someSectionText = new Paragraph("This is some silly paragraph in a chapter and/or section. It contains some text to test the functionality of Chapters and Section.");
            section1.add(someSectionText);
            document.add( chapter2);
            
            // step 4: we add a paragraph to the document
            document.add(new Paragraph("Hello World"));
        } catch (DocumentException de) {
            System.err.println(de.getMessage());
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        }
        
        // step 5: we close the document
        document.close();
    }
    
    public void testStructure() 
    {
        
    }
} //PDFWriter
