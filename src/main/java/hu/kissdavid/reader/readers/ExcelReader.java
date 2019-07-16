package hu.kissdavid.reader.readers;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

@Getter
public class ExcelReader {

    public ExcelReader(){

    }


    public LinkedList<String> readFile(String path) {
        LinkedList<String> fileContent = new LinkedList();
        try  {
            FileReader reader = new FileReader(path);
            BufferedReader br = new BufferedReader(reader);
            String line = StringUtils.EMPTY;
            while ((line = br.readLine()) != null) {
                fileContent.add(line);
            }

        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
            return null;
        }
        return fileContent;
    }
}
