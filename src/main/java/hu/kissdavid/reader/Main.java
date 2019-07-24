package hu.kissdavid.reader;


import com.itextpdf.text.DocumentException;
import hu.kissdavid.reader.readers.DateFetcher;
import hu.kissdavid.reader.readers.ExcelReader;
import hu.kissdavid.reader.readers.PDFReader;
import hu.kissdavid.reader.readers.ReadDocx;
import org.apache.commons.io.FileUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

public class Main {

    public static void main(String[] args) throws IOException, InvalidFormatException {

        ExcelReader excelReader = new ExcelReader();


        Map<String, Set<String>> rowContainer = excelReader.readExcel("\\src\\main\\resources\\tesztadatok.xlsx");
        ReadDocx readDocx = new ReadDocx();
        try {
            readDocx.convertHtmlToPdf("\\src\\main\\resources\\sablon2.html");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
