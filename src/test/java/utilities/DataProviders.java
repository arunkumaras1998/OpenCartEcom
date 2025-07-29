package utilities;

import org.testng.annotations.DataProvider;

import java.io.File;
import java.io.IOException;

public class DataProviders {

    @DataProvider(name = "LoginData")
    public Object[][] getLoginData() throws IOException {
        //taking xlfile from testData folder
        String path = System.getProperty("user.dir") + File.separator + "testData" + File.separator + "opencart_LoginData.xlsx";

        //create object of ExcelUtility
        ExcelUtility excelUtility = new ExcelUtility(path);

        //Sheet name is configurable
        String sheetName = "sheet1";
        int totalRows = excelUtility.getRowCount(sheetName);
        if (totalRows <= 1) {
            throw new IllegalStateException("No data found in sheet: " + sheetName);
        }
        int totalCells = excelUtility.getCellCount(sheetName, 1); // Assuming row 1 is first data row

        // created two-dimensional array which can store the data
        // [rows - 1] because row 0 is likely header
        String[][] loginData = new String[totalRows - 1][totalCells];

        // populate array, starting from row 1 (skip header)
        for (int row = 1; row < totalRows; row++) { //1 // read the data from the Excel storing in two-dimensional array
            for (int col = 0; col < totalCells; col++) {
                loginData[row - 1][col] = excelUtility.getCellData(sheetName, row, col);
                // row-1 -  array index start from zero
            }
        }

        return loginData; // return two-dimensional array
    }
}
