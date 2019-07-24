package hu.kissdavid.reader.readers;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.pdf.*;
import com.itextpdf.tool.xml.Pipeline;
import com.itextpdf.tool.xml.XMLWorker;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.tool.xml.css.CssFilesImpl;
import com.itextpdf.tool.xml.css.StyleAttrCSSResolver;
import com.itextpdf.tool.xml.html.CssAppliersImpl;
import com.itextpdf.tool.xml.html.HTML;
import com.itextpdf.tool.xml.html.TagProcessorFactory;
import com.itextpdf.tool.xml.html.Tags;
import com.itextpdf.tool.xml.parser.XMLParser;
import com.itextpdf.tool.xml.pipeline.css.CssResolverPipeline;
import com.itextpdf.tool.xml.pipeline.end.PdfWriterPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext;
import fr.opensagres.poi.xwpf.converter.pdf.PdfConverter;
import fr.opensagres.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.jsoup.Jsoup;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
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
        PRStream stream = null;
        if(object instanceof PRStream) {
            stream = (PRStream) object;
            byte[] data2 = PdfReader.getStreamBytes(stream);
            byte[] data = PdfReader.getPageContent(dict);
            String dd = new String(data,"UTF-8");

            Set<String> keySet = rowContainer.keySet();
            Iterator keyIterator = keySet.iterator();
            while(keyIterator.hasNext()) {
                String actualKey = (String) keyIterator.next();
                ArrayList<String> actualValues = new ArrayList<>(rowContainer.get(actualKey));

                actualKey = "@@" + actualKey + "@@";
                dd = dd.replace(actualKey, actualValues.get(index));
                System.out.println(dd);
            }
            stream.setData(dd.getBytes("UTF-8"));
        }

        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(dest));
        stamper.close();
        reader.close();
    }

    private String toXHTML( String html ) {
        final org.jsoup.nodes.Document document = Jsoup.parse(html);
        document.outputSettings().syntax(org.jsoup.nodes.Document.OutputSettings.Syntax.xml);
        return document.html();
    }

    private byte[] toPdf(String html) {
        try {
            String xhtml = toXHTML(html);

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//			Document document = new Document();
            com.itextpdf.text.Document document = new com.itextpdf.text.Document(com.itextpdf.text.PageSize.A4, 50, 50, 50, 50);
            PdfWriter writer = PdfWriter.getInstance(document, byteArrayOutputStream);
            document.open();
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(xhtml.getBytes(StandardCharsets.UTF_8));

            XMLWorkerHelper.getInstance().parseXHtml(writer, document, byteArrayInputStream, Charset.forName("UTF-8"));
            document.close();

            return byteArrayOutputStream.toByteArray();
        } catch(Exception e) {
            e.printStackTrace();
            return new byte[]{};
        }

    }


    public void convertHtmlToPdf(String path) {
        File file = new File("");
        String absPath = file.getAbsolutePath() + path;
        String xhtml = "";
        try{
            BufferedReader br = new BufferedReader(new FileReader(new File(absPath)));
            String temp;
            while((temp = br.readLine()) != null) {
                xhtml += temp;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            xhtml = toXHTML(xhtml);
            OutputStream output = new FileOutputStream(new File("D:\\Test.pdf"));
            Document document = new Document();
            PdfWriter pdfwriter = PdfWriter.getInstance(document, output);
            document.open();
            XMLWorkerHelper xMLWorkerHelper = XMLWorkerHelper.getInstance();
            xMLWorkerHelper.parseXHtml(pdfwriter, document, new StringReader(xhtml));
            document.close();
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
