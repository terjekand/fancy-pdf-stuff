package hu.kissdavid.reader.readers;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

import java.io.File;
import java.io.IOException;

public class PDFReader {

    public void readPdf(String path){

        File file = new File("");
        try {
            PdfReader pdfReader = new PdfReader(file.getAbsolutePath() + path);
            String textFromPDF = PdfTextExtractor.getTextFromPage(pdfReader,1);
            System.out.println(textFromPDF);
            pdfReader.close();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }
}
