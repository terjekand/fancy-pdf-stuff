package hu.kissdavid.reader.readers;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

@Getter
public class ExcelReader {

    private Workbook workbook;
    private DataFormatter dataFormatter;
    private File file;
    private Map<String,Set<String>> rowContainer;

    public ExcelReader(){
        dataFormatter = new DataFormatter();
        file = new File("");
        workbook = null;
        rowContainer = new LinkedHashMap<String, Set<String>>();
    }

    public ExcelReader(File file ,Workbook workbook, DataFormatter dataFormatter,Map<String,Set<String>> rowContainer){
        this.file = file;
        this.workbook = workbook;
        this.dataFormatter = dataFormatter;
        this.rowContainer = rowContainer;
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

    public Map<String,Set<String>> readExcel(String path) {

        try {
            workbook = WorkbookFactory.create(new File(file.getAbsolutePath() + path));
        } catch (IOException | InvalidFormatException e) {
            e.printStackTrace();
            return null;
        }

        Sheet sheet = workbook.getSheetAt(0);
        readRows(sheet);

        System.out.println("Workbook has " + workbook.getNumberOfSheets() + " Sheets : ");

        System.out.println(rowContainer);
        try {
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return rowContainer;
    }


    public void readRows(Sheet sheet) {

        initRowContainer(sheet);
        Iterator<Row> rowIterator = sheet.rowIterator();
        rowIterator.next();
        while(rowIterator.hasNext()) {
            addRowToMap(rowIterator.next());

        }
    }

    private void addRowToMap(Row row) {
        Iterator<Cell> cellIterator = row.cellIterator();
        Set<String> columnCells = null;
        Set<String> keys = rowContainer.keySet();
        Iterator keyIterator = keys.iterator();

        while (cellIterator.hasNext() && keyIterator.hasNext()) {
            Cell cell = cellIterator.next();
            String actualKey = (String) keyIterator.next();
            columnCells = rowContainer.get(actualKey);
            columnCells.add(cell.toString());
            rowContainer.put(actualKey, columnCells);
        }

    }

    private void initRowContainer(Sheet sheet){

        Row row = sheet.getRow(0);
        Iterator<Cell> cellIterator = row.cellIterator();

        while(cellIterator.hasNext()){
            Cell cell = cellIterator.next();
            String cellValue = dataFormatter.formatCellValue(cell);
            rowContainer.put(cellValue, new LinkedHashSet<String>());

            for(int i=1;i<sheet.getLastRowNum();i++){
                Row contentRows = sheet.getRow(i);
            }
        }
    }

}
