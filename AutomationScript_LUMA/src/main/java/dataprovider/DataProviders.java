package dataprovider;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.testng.annotations.DataProvider;
import utilityclass.NewExcelLibrary;


/**
 * Author: Shreyas Bhagat
 * Date: 24/05/2025
 * BaseClass provides necessary functionalities for handling WebDriver actions,
 * loading configurations, and supporting DataProvider-driven test execution.
 */


public class DataProviders {
	NewExcelLibrary excel = new NewExcelLibrary();

	/**
     * Data provider that combines data from the "Question" and "User " sheets.
     * @return Combined data as an Object array for TestNG.
     * @throws Exception if there is an error reading the Excel file.
     */
	@DataProvider(name = "Combined Data Provider")
	public Object[] [] AllData() throws Throwable {
		Object[][] dataSheet1 = getProduct();
		Object[][] dataSheet2 = getUser();
		
		int totalrows=Math.min(dataSheet1.length, dataSheet1.length);

		Object[][] combinedData=new Object[totalrows][];
		for(int i=0;i<totalrows;i++) {
			Map<String, Object>  combinedMap=new HashMap<>();
	
			Map<String, Object> dataMap1= (Map<String, Object>) dataSheet1[i][0];
			Map<String, Object> dataMap2= (Map<String, Object>) dataSheet2[i][0];
		
			
			combinedMap.putAll(dataMap1);
			combinedMap.putAll(dataMap2);
		
			
			combinedData[i]=new Object[] {combinedMap};
		}
		return combinedData;		
	}
	
	 /**
     * Retrieves data from the specified sheet and returns it as an Object array.
     * 
     * @param sheetName The name of the Excel sheet to read data from.
     * @return Data from the specified sheet as an Object array.
     * @throws Exception if there is an error reading the Excel file.
     */
	@DataProvider(name = "Product Data Provider")
	public Object[][] getProduct() throws Throwable {
		//HashMap<String, String> hashMap= new HashMap<String, String>();
		// Totals rows count
		int rows = excel.getRowCount("ProductPage");
		// Total Columns
		int column = excel.getColumnCount("ProductPage");
		int actRows = rows - 1;
		//Created an object of array to store data
		Object[][] data = new Object[actRows][1];

		for (int i = 0; i < actRows; i++) {
			Map<String, String> hashMap = new HashMap<>();
			for (int j = 0; j < column; j++) {
				hashMap.put(excel.getCellData("ProductPage", j, 1),
						excel.getCellData("ProductPage", j, i + 2));	
			}
			data [i][0]=hashMap;
		}
		return data;		

	}	
	
	public Object[][] getUser() throws Throwable {
		// Totals rows count
		int rows = excel.getRowCount("CreateAccount");
		// Total Columns
		int column = excel.getColumnCount("CreateAccount");
		int actRows = rows - 1;
		Object[][] data = new Object[actRows][1];

		for (int i = 0; i < actRows; i++) {
			Map<String, String> hashMap = new HashMap<>();
			for (int j = 0; j < column; j++) {
				hashMap.put(excel.getCellData("CreateAccount", j, 1),
						excel.getCellData("CreateAccount", j, i + 2));	
			}
			data [i][0]=hashMap;
		}
		return data;		
	}

	
	
	
	
	
}
