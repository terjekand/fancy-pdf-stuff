package hu.kissdavid.reader.readers;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.*;

public class DateFetcher {

    public void addParagraphs(String filename) {

        File file = new File("");

        try{
            Document pdfDoc = new Document(PageSize.A4);
            PdfWriter.getInstance(pdfDoc, new FileOutputStream("D:\\txt.pdf"))
                    .setPdfVersion(PdfWriter.PDF_VERSION_1_7);
            pdfDoc.open();

            Font myfont = new Font();
            myfont.setStyle(Font.NORMAL);
            myfont.setSize(11);
            pdfDoc.add(new Paragraph("\n"));

            BufferedReader br = new BufferedReader(new FileReader(file.getAbsolutePath() + filename));
            String strLine;
            while ((strLine = br.readLine()) != null) {
                Paragraph para = new Paragraph(strLine + "\n", myfont);
                para.setAlignment(Element.ALIGN_JUSTIFIED);
                pdfDoc.add(para);
            }
            pdfDoc.close();
            br.close();
        } catch (IOException |DocumentException e) {
            e.printStackTrace();
        }
    }

}
