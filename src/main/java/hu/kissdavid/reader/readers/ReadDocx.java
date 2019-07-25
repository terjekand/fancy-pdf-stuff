package hu.kissdavid.reader.readers;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.*;

public class ReadDocx {

    private static final String NAME = "Név";
    private byte[] bytes;
    private Map<String, Set<String>> rowContainer;

    public ReadDocx(Map<String, Set<String>> rowContainer) {
        this.rowContainer = rowContainer;
    }

    private String replaceKeys(Set<String> keySet, int index) throws UnsupportedEncodingException {

        Iterator keyIterator = keySet.iterator();
        String text = new String(bytes,"UTF-8");

        while(keyIterator.hasNext()) {
            String actualKey = (String) keyIterator.next();
            ArrayList<String> actualValues = new ArrayList<>(rowContainer.get(actualKey));
            actualKey = "@@" + actualKey + "@@";
            text = text.replace(actualKey, actualValues.get(index));
        }
        return text;
    }

    private String nameFormatter(int index) {
        String name = new ArrayList<>(rowContainer.get("Név")).get(index);
        name = name.replaceAll(" ", "_");
        name = name + "_" + index + ".pdf";
        return name;
    }

    public void processPDF(String path, int index) throws IOException, DocumentException {

        this.bytes = FileUtils.readFileToByteArray(new File(path + "sablon.html"));
        Set<String> keySet = rowContainer.keySet();

        String content = replaceKeys(keySet, index);

        File xhtmlFile = new File(path + "temp.html");

        FileUtils.writeByteArrayToFile(xhtmlFile, content.getBytes());

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, baos);
        document.open();
        XMLWorkerHelper.getInstance().parseXHtml(writer, document, new FileInputStream(xhtmlFile));
        document.close();
        writer.close();

        FileUtils.writeByteArrayToFile(new File(path + "pdf\\" + nameFormatter(index)), baos.toByteArray());
    }

}
