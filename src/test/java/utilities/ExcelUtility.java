package utilities;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.*;

import java.io.*;

public class ExcelUtility {
    private final String path;

    public ExcelUtility(String path) {
        this.path = path;
    }

    // Get actual number of non-empty rows
    public int getRowCount(String sheetName) throws IOException {
        try (FileInputStream fis = new FileInputStream(path);
             XSSFWorkbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            return (sheet != null) ? sheet.getPhysicalNumberOfRows() : 0;
        }
    }

    // Get number of cells (columns) in a row
    public int getCellCount(String sheetName, int rowNum) throws IOException {
        try (FileInputStream fis = new FileInputStream(path);
             XSSFWorkbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) return 0;

            Row row = sheet.getRow(rowNum);
            return (row != null) ? row.getLastCellNum() : 0;
        }
    }

    // Get data from a specific cell
    public String getCellData(String sheetName, int rowNum, int colNum) throws IOException {
        try (FileInputStream fis = new FileInputStream(path);
             XSSFWorkbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) return "";

            Row row = sheet.getRow(rowNum);
            if (row == null) return "";

            Cell cell = row.getCell(colNum);
            if (cell == null) return "";

            DataFormatter formatter = new DataFormatter();
            return formatter.formatCellValue(cell);
        }
    }

    // Set data in a specific cell
    public void setCellData(String sheetName, int rowNum, int colNum, String data) throws IOException {
        File file = new File(path);

        XSSFWorkbook workbook;
        Sheet sheet;

        if (!file.exists()) {
            // Create a new workbook and sheet if file doesn't exist
            workbook = new XSSFWorkbook();
            sheet = workbook.createSheet(sheetName);
        } else {
            try (FileInputStream fis = new FileInputStream(file)) {
                workbook = new XSSFWorkbook(fis);
                sheet = workbook.getSheet(sheetName);
                if (sheet == null) {
                    sheet = workbook.createSheet(sheetName);
                }
            }
        }

        Row row = sheet.getRow(rowNum);
        if (row == null) row = sheet.createRow(rowNum);

        Cell cell = row.createCell(colNum);
        cell.setCellValue(data);

        try (FileOutputStream fos = new FileOutputStream(path)) {
            workbook.write(fos);
        }

        workbook.close();
    }

    // Optional: List all sheet names
    public String[] getSheetNames() throws IOException {
        try (FileInputStream fis = new FileInputStream(path);
             XSSFWorkbook workbook = new XSSFWorkbook(fis)) {

            int sheetCount = workbook.getNumberOfSheets();
            String[] sheetNames = new String[sheetCount];

            for (int i = 0; i < sheetCount; i++) {
                sheetNames[i] = workbook.getSheetName(i);
            }

            return sheetNames;
        }
    }
}
