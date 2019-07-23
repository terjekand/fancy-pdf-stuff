package hu.kissdavid.reader;


import hu.kissdavid.reader.readers.ExcelReader;
import hu.kissdavid.reader.readers.PDFReader;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, InvalidFormatException {

        ExcelReader excelReader = new ExcelReader();
        PDFReader pdfReader = new PDFReader();


        excelReader.readExcel("\\src\\main\\resources\\tesztadatok.xlsx");
        pdfReader.readPdf("\\src\\main\\resources\\sablon.pdf");






    }
}
