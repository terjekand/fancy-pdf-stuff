package hu.kissdavid.reader;


import com.itextpdf.text.DocumentException;
import hu.kissdavid.reader.readers.DateFetcher;
import hu.kissdavid.reader.readers.ExcelReader;
import hu.kissdavid.reader.readers.PDFReader;
import hu.kissdavid.reader.readers.ReadDocx;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

public class Main {

    public static void main(String[] args) throws IOException, InvalidFormatException {

        ExcelReader excelReader = new ExcelReader();
        PDFReader pdfReader = new PDFReader();


        Map<String, Set<String>> rowContainer = excelReader.readExcel("\\src\\main\\resources\\tesztadatok.xlsx");
        ReadDocx readDocx = new ReadDocx();
        try {
            readDocx.processPDF( "\\src\\main\\resources\\sablon2.pdf", "D:\\test.pdf", rowContainer, 0);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }
}
