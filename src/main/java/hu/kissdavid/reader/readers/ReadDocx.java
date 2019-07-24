package hu.kissdavid.reader.readers;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.*;
import fr.opensagres.poi.xwpf.converter.pdf.PdfConverter;
import fr.opensagres.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.io.*;
import java.util.*;

public class ReadDocx {

    public void ConvertToPDF(Map<String, Set<String>> rowContainer, String docPath, String pdfPath) {

        File file = new File("");
        try {
            InputStream doc = new FileInputStream(file.getAbsolutePath() + docPath);
            XWPFDocument  document = new XWPFDocument(doc);
            PdfOptions options = PdfOptions.create();

            Iterator paragraphIterator = document.getParagraphsIterator();


            OutputStream out = new FileOutputStream(new File("D:\\converted.pdf"));
            PdfConverter.getInstance().convert(document, out, options);
            document.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void processPDF(String src, String dest,Map<String, Set<String>> rowContainer, int index) throws IOException, DocumentException {

        File file = new File("");
        PdfReader reader = new PdfReader(file.getAbsolutePath() + src);
        PdfDictionary dict = reader.getPageN(1);
        PdfObject object = dict.getDirectObject(PdfName.CONTENTS);

        if(object instanceof PRStream) {
            PRStream stream = (PRStream) object;
            byte[] data = PdfReader.getStreamBytes(stream);
            String dd = new String(data);

            Set<String> keySet = rowContainer.keySet();
            Iterator keyIterator = keySet.iterator();
            while(keyIterator.hasNext()) {
                String actualKey = (String) keyIterator.next();
                ArrayList<String> actualValues = new ArrayList<>(rowContainer.get(actualKey));
                System.out.println("key: " + actualKey);
                System.out.println("value: " + actualValues.get(index));
                actualKey = "@@" + actualKey + "@@";
                dd = dd.replace(actualKey, actualValues.get(index));
            }
            stream.setData(dd.getBytes());
        }

        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(dest));
        stamper.close();
        reader.close();
    }
}
