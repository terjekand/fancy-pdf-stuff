package hu.kissdavid.reader;

import hu.kissdavid.reader.readers.ExcelReader;
import hu.kissdavid.reader.readers.ReadDocx;

import java.io.File;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Main {

    private static final String TEMP_PATH = "D:\\TO_PDF\\";

    public static void main(String[] args)  {

        ExcelReader excelReader = new ExcelReader();


        Map<String, Set<String>> rowContainer = excelReader.readExcel(TEMP_PATH);
        ReadDocx readDocx = new ReadDocx(rowContainer);
        int n = rowContainer.values().iterator().next().size();
        try {
            for(int i = 0; i < n; i++){
                 readDocx.processPDF(TEMP_PATH , i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            new File(TEMP_PATH + "temp.html").delete();
        }
    }
}
