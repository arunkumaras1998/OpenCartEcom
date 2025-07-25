package utilities;

import org.testng.annotations.DataProvider;

import java.io.IOException;

public class DataProviders {

    @DataProvider(name = "LoginData")
    public Object[][] getData() throws IOException {
        //taking xlfile from testData folder
        String path = System.getProperty("user.dir") + "\\testData\\opencart_LoginData.xlsx";

        //create object of ExcelUtility
        ExcelUtility excelUtility = new ExcelUtility(path);
        int totalRows = excelUtility.getRowCount("sheet1");
        int totalCells = excelUtility.getCellCount("sheet1", 1);

        // created two-dimensional array which can store the data
        String loginData[][] = new String[totalRows][totalCells];

        for (int row = 1; row < totalRows; row++) { //1 // read the data from the excel storing in two dimensional array
            for (int cell = 0; cell < totalCells; cell++) {
                loginData[row-1][cell] = excelUtility.getCellData("sheet1",row,cell);
                // row-1 -  array index start from zero
            }
        }

        return loginData; // return two-dimensional array
    }
}
