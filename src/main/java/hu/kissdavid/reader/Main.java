package hu.kissdavid.reader;

import hu.kissdavid.reader.readers.ExcelReader;

import java.io.File;
import java.util.LinkedList;

public class Main {

    public static void main(String[] args) {
        ExcelReader excelReader = new ExcelReader();
        File file = new File("");
        LinkedList<String> fileContent = excelReader.readFile(file.getAbsolutePath() + "\\src\\main\\resources\\bajbaj.txt");
        for(String e : fileContent) {
            System.out.println(e);
        }
    }
}
