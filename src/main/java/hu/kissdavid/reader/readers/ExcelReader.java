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

    public void readExcel(String path) {



        try {
            workbook = WorkbookFactory.create(new File(file.getAbsolutePath() + path));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }

        Sheet sheet = workbook.getSheetAt(0);
        readRows(sheet);

        System.out.println("Workbook has " + workbook.getNumberOfSheets() + " Sheets : ");

        System.out.println(rowContainer);
        /*Iterator<Row> rowIterator = sheet.rowIterator();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();


            Iterator<Cell> cellIterator = row.cellIterator();

            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                String cellValue = dataFormatter.formatCellValue(cell);
                System.out.print(cellValue + "\t");
            }
            System.out.println();
        }

        try {
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }


    public void readRows(Sheet sheet) {

        initRowContainer(sheet);
        Iterator<Row> rowIterator = sheet.rowIterator();
        while(rowIterator.hasNext()) {
            addRowToMap(rowIterator.next());

        }
    }

    private void addRowToMap(Row row) {
        Iterator<Cell> cellIterator = row.cellIterator();
        Set<String> columnCells = new LinkedHashSet<String>();
        Set<String> keys = rowContainer.keySet();
        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            columnCells.add(cell.toString());
            rowContainer.put(keys,columnCells);
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
