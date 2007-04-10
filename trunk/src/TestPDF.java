import java.io.File;
import java.io.IOException;

import org.apache.log4j.PropertyConfigurator;

import output.PDFWriter;
import collector.data.BaseDeDonnees;

import com.lowagie.text.DocumentException;

/*
 * Created on Jan 4, 2007
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

public class TestPDF {
    
    
    public void run()
        throws DocumentException, IOException
    {
//        File f_hello = new File("tmp/testHello.pdf");
//        PDFWriter doc1 = new PDFWriter( f_hello, "","");
//        doc1.testHello();
        
        String text = "Lots of text. ";
        for (int k = 0; k < 10; ++k)
            text += text;
        
        BaseDeDonnees myBDD = new BaseDeDonnees();
        myBDD.readFromFile( "data/bandesDessinees.dta");
        
        File f_structure = new File("tmp/testBDD.pdf");
        PDFWriter doc2 = new PDFWriter( f_structure, "BDD Bande dessinée","data/bandesDessinees.dta");
//        doc2.addParagraph(text);
//        doc2.addSection( "Section UN");
//        doc2.addParagraph(text);
//        doc2.addSection( "Section DEUX");
        doc2.addTable("Séries",myBDD.parentTable, myBDD.parentTable.myHeader);
        doc2.addTable("BD", myBDD.completeTable, myBDD.completeTable.myHeader);
        
        doc2.end();
    }
    
    /**
     * @param args
     */
    public static void main(String[] args)
      throws DocumentException, IOException
    {
        // logger configuration 
        PropertyConfigurator.configure("etc/log4j.config");
        //logger = Logger.getLogger(TestCell.class);
        
        TestPDF myTest = new TestPDF();
        myTest.run();
    }

        // ---------- a Private Logger ---------------------
        //private static Logger logger;
        // --------------------------------------------------
    
}
