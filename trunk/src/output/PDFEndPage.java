/*
 * Created on Jan 12, 2007
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package output;

import com.lowagie.text.Document;
import com.lowagie.text.ExceptionConverter;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfTemplate;
import com.lowagie.text.pdf.PdfWriter;

public class PDFEndPage extends PdfPageEventHelper {
    
    Rectangle page;
    PdfPTable head;
    PdfPTable foot;
    // A template that will hold the total number of pages.
    PdfTemplate myTemplate;
    
    BaseFont myBaseFont;
    
    /**
     * Add a header with a runningTitle,date and pageNumber.
     */
    public PDFEndPage( Document p_doc, String p_runningTitle, BaseFont p_base )
    {
        page = p_doc.getPageSize();
        
        myBaseFont = p_base;
        
        // a head only for a running title
        head = null;
        if( p_runningTitle != null ) {
            head = new PdfPTable(1);
            head.addCell( p_runningTitle );
            head.setTotalWidth(page.width() - p_doc.leftMargin() - p_doc.rightMargin());
        }
    }
    
    /**
     * @see com.lowagie.text.pdf.PdfPageEventHelper#onOpenDocument(com.lowagie.text.pdf.PdfWriter, com.lowagie.text.Document)
     */
    public void onOpenDocument(PdfWriter writer, Document document)
    {
        try {
            // initialization of the template
            myTemplate = writer.getDirectContent().createTemplate(100, 100);
            myTemplate.setBoundingBox(new Rectangle(-20, -20, 100, 100));
        }
        catch(Exception e) {
            throw new ExceptionConverter(e);
        }
    }
    
    /**
     * @see com.lowagie.text.pdf.PdfPageEventHelper#onEndPage(com.lowagie.text.pdf.PdfWriter, com.lowagie.text.Document)
     */
    public void onEndPage(PdfWriter writer, Document document)
    {
        try {
            
            // the Direct Content
            PdfContentByte cb = writer.getDirectContent();
            // put the head table to its absolute position 
            head.writeSelectedRows(0, -1, document.leftMargin(), page.height() - document.topMargin() + head.getTotalHeight(),
                    writer.getDirectContent());
         
            // always a footer
            // IF WE WERE TO USE A TABLE...
            //foot = new PdfPTable(1);
            //foot.setTotalWidth(page.width() - document.leftMargin() - document.rightMargin());
            // put the page number at the end
            //String text = "Page " + writer.getPageNumber();
            //foot.addCell( text );
            //foot.writeSelectedRows(0, -1, document.leftMargin(), document.bottomMargin(),
            //        writer.getDirectContent());
            
            // USING A TEMPLATE
            cb.saveState();
            // compose the footer
            String text = "Page " + writer.getPageNumber() + " / ";
            float textSize = myBaseFont.getWidthPoint(text, 10);
            float textBase = document.bottom() - 20;
            cb.beginText();
            cb.setFontAndSize(myBaseFont, 10);
            // Show the footer at the left
            cb.setTextMatrix(document.left(), textBase);
            cb.showText(text);
            cb.endText();
            cb.addTemplate(myTemplate, document.left() + textSize, textBase);
            cb.restoreState();
        }
        catch (Exception e) {
            throw new ExceptionConverter(e);
        }
    }
    /**
     * @see com.lowagie.text.pdf.PdfPageEventHelper#onCloseDocument(com.lowagie.text.pdf.PdfWriter, com.lowagie.text.Document)
     */
    public void onCloseDocument(PdfWriter writer, Document document) {
       myTemplate.beginText();
       myTemplate.setFontAndSize(myBaseFont, 10);
       myTemplate.setTextMatrix(0, 0);
       myTemplate.showText("" + (writer.getPageNumber() - 1));
       myTemplate.endText();
    }

}
