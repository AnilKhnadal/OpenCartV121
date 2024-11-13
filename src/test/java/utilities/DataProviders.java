package utilities;

import java.io.IOException;

import org.testng.annotations.DataProvider;

public class DataProviders {

	// DataProvider 1

	@DataProvider(name = "LoginData")
	public String[][] getData() throws IOException {
		String path = "./testData/OpenCart_LoginData.xlsx"; // taking xl file from testData

		ExcelUtility xlutil = new ExcelUtility(path);

		int totalrows = xlutil.getRowCount("Sheet1");
		System.out.println("Rows = " + totalrows);
		
		int totalcols = xlutil.getCellCount("Sheet1", 1); // 1 row num
		System.out.println("Cols = " + totalcols);
		
		String logindata[][] = new String[totalrows][totalcols]; // Created for two dimension array which can store

		for (int i = 1; i <= totalrows; i++) // 1 // read data from xl storing in two dimension array
		{
			for (int j = 0; j < totalcols; j++) // 0 i is rows j is col
			{
				logindata[i-1][j] = xlutil.getCellData("Sheet1", i, j); // 1,0
			}
		}
		return logindata; // retuning two dimension array
	}
}
// DataProvider 2

// DataProvider 3

// DataProvider 4
