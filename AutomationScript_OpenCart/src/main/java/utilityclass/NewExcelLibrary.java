package utilityclass;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;



/**
 * Author: Shreyas Bhagat
 * Date: 24/05/2025
 * NewExcelLibrary provides methods to read, write, and manipulate Excel files using Apache POI.
 */

public class NewExcelLibrary {

	private String path;
    private FileInputStream fis = null;
    private FileOutputStream fileOut = null;
    private XSSFWorkbook workbook = null;
    private XSSFSheet sheet = null;
    private XSSFRow row = null;
    private XSSFCell cell = null;
    
    private static final String DEFAULT_SHEET_NAME = "LUMA_TestData.xlsx";
    
    private static final String DEFAULT_PATH = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "TestData" + File.separator + DEFAULT_SHEET_NAME;
    
    public NewExcelLibrary() {
        this.path = DEFAULT_PATH; // Initialize with default path
        initializeWorkbook();
    }
    
    public NewExcelLibrary(String path) {
        this.path = path; // Initialize with provided path
        initializeWorkbook();
    }
    
    private void initializeWorkbook() {
        try {
            fis = new FileInputStream(path);
            workbook = new XSSFWorkbook(fis);
            sheet = workbook.getSheetAt(0);
        } catch (Exception e) {
        	System.err.println("Failed to read Excel file at: " + path);
            e.printStackTrace(); // Consider using a logging framework
        } finally {
            closeFileInputStream();
        }
    }
    


	// returns the row count in a sheet
	public int getRowCount(String sheetName){
		int index = workbook.getSheetIndex(sheetName);
		if(index==-1)
			return 0;
		else{
			sheet = workbook.getSheetAt(index);
			int number=sheet.getLastRowNum()+1;
			return number;
		}

	}



	// returns the string data in a sheet
	public String getCellData(String sheetName,String colName,int rowNum){
		try{
			if(rowNum <=0)
				return "";

			int index = workbook.getSheetIndex(sheetName);
			int col_Num=-1;
			if(index==-1)
				return "";

			sheet = workbook.getSheetAt(index);
			row=sheet.getRow(0);
			for(int i=0;i<row.getLastCellNum();i++){
				//System.out.println(row.getCell(i).getStringCellValue().trim());
				if(row.getCell(i).getStringCellValue().trim().equals(colName.trim()))
					col_Num=i;
			}
			if(col_Num==-1)
				return "";

			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(rowNum-1);
			if(row==null)
				return "";
			cell = row.getCell(col_Num);

			if(cell==null)
				return "";
			//System.out.println(cell.getCellType());
			if(cell.getCellType().name().equals("STRING"))
				return cell.getStringCellValue();
			else if(cell.getCellType().name().equals("NUMERIC") || cell.getCellType().name().equals("FORMULA") ){

				String cellText  = String.valueOf(cell.getNumericCellValue());
				if (DateUtil.isCellDateFormatted(cell)) {
					// format in form of M/D/YY
					double d = cell.getNumericCellValue();

					Calendar cal =Calendar.getInstance();
					cal.setTime(DateUtil.getJavaDate(d));
					cellText =
							(String.valueOf(cal.get(Calendar.YEAR))).substring(2);
					cellText = cal.get(Calendar.DAY_OF_MONTH) + "/" +
							cal.get(Calendar.MONTH)+1 + "/" + 
							cellText;

					//System.out.println(cellText);

				}  
				return cellText;
			}else if(cell.getCellType().name().equals("BLANK"))
				return ""; 
			else 
				return String.valueOf(cell.getBooleanCellValue());

		}
		catch(Exception e){

			e.printStackTrace();
			return "row "+rowNum+" or column "+colName +" does not exist in xls";
		}
	}





	// returns the data from a cell
	public String getCellData(String sheetName,int colNum,int rowNum){
		try{
			if(rowNum <=0)
				return "";

			int index = workbook.getSheetIndex(sheetName);
			if(index==-1)
				return "";


			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(rowNum-1);
			if(row==null)
				return "";
			cell = row.getCell(colNum);
			if(cell==null)
				return "";

			if(cell.getCellType().name().equals("STRING"))
				return cell.getStringCellValue();
			else if(cell.getCellType().name().equals("NUMERIC") || cell.getCellType().name().equals("FORMULA") ){

				String cellText  = String.valueOf(cell.getNumericCellValue());
				if (DateUtil.isCellDateFormatted(cell)) {
					// format in form of M/D/YY
					double d = cell.getNumericCellValue();

					Calendar cal =Calendar.getInstance();
					cal.setTime(DateUtil.getJavaDate(d));
					cellText =
							(String.valueOf(cal.get(Calendar.YEAR))).substring(2);
					cellText = cal.get(Calendar.MONTH)+1 + "/" +
							cal.get(Calendar.DAY_OF_MONTH) + "/" +
							cellText;    
					// System.out.println(cellText);
				}
				return cellText;
			}else if(cell.getCellType().name().equals("BLANK"))
				return "";
			else 
				return String.valueOf(cell.getBooleanCellValue());
		}
		catch(Exception e){
			e.printStackTrace();
			return "row "+rowNum+" or column "+colNum +" does not exist  in xls";
		}
	}	



	// returns true if data is set successfully else false
	/*public boolean setCellData(String sheetName,String colName,int rowNum, String data){
				try{
					fis = new FileInputStream(path); 

					workbook = new XSSFWorkbook(fis);
					if(rowNum<=0)
						return false;
					int index = workbook.getSheetIndex(sheetName);
					int colNum=-1;
					if(index==-1)
						return false;	
					sheet = workbook.getSheetAt(index);
					row=sheet.getRow(0);
					for(int i=0;i<row.getLastCellNum();i++){
						//System.out.println(row.getCell(i).getStringCellValue().trim());
						if(row.getCell(i).getStringCellValue().trim().equals(colName))
							colNum=i;
					}
					if(colNum==-1)
						return false;
					sheet.autoSizeColumn(colNum); 
					row = sheet.getRow(rowNum-1);
					if (row == null)
						row = sheet.createRow(rowNum-1);
					cell = row.getCell(colNum);	
					if (cell == null)
						cell = row.createCell(colNum);
					// cell style
					//CellStyle cs = workbook.createCellStyle();
					//cs.setWrapText(true);
					//cell.setCellStyle(cs);
					cell.setCellValue(data);
					fileOut = new FileOutputStream(path);
					workbook.write(fileOut);
					fileOut.close();	
				}
				catch(Exception e){
					e.printStackTrace();
					return false;
				}
				return true;
			}*/


	public void setCellData(String sheetName,int rownum,int colnum,String data) throws IOException{
		File xlfile=new File(path);
		if(!xlfile.exists())    // If file not exists then create new file
		{
			workbook=new XSSFWorkbook();
			fileOut=new FileOutputStream(path);
			workbook.write(fileOut);
		}

		fis=new FileInputStream(path);
		workbook=new XSSFWorkbook(fis);

		if(workbook.getSheetIndex(sheetName)==-1) // If sheet not exists then create new Sheet
			workbook.createSheet(sheetName);

		sheet=workbook.getSheet(sheetName);

		if(sheet.getRow(rownum)==null)   // If row not exists then create new Row
			sheet.createRow(rownum);
		row=sheet.getRow(rownum);

		cell=row.createCell(colnum);
		cell.setCellValue(data);
		fileOut=new FileOutputStream(path);
		workbook.write(fileOut);		
		workbook.close();
		fis.close();
		fileOut.close();
	}

	// Closes the file input stream
    private void closeFileInputStream() {
        if (fis != null) {
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace(); // Consider using a logging framework
            }
        }
    }
    
	// returns true if sheet is created successfully else false
	public boolean addSheet(String  sheetname){		

		FileOutputStream fileOut;
		try {
			workbook.createSheet(sheetname);	
			fileOut = new FileOutputStream(path);
			workbook.write(fileOut);
			fileOut.close();		    
		} catch (Exception e) {			
			e.printStackTrace();
			return false;
		}
		return true;
	}

	// returns true if sheet is removed successfully else false if sheet does not exist
	public boolean removeSheet(String sheetName){		
		int index = workbook.getSheetIndex(sheetName);
		if(index==-1)
			return false;

		FileOutputStream fileOut;
		try {
			workbook.removeSheetAt(index);
			fileOut = new FileOutputStream(path);
			workbook.write(fileOut);
			fileOut.close();		    
		} catch (Exception e) {			
			e.printStackTrace();
			return false;
		}
		return true;
	}

	// returns true if column is created successfully
	public boolean addColumn(String sheetName,String colName){
		//System.out.println("**************addColumn*********************");

		try{				
			fis = new FileInputStream(path); 
			workbook = new XSSFWorkbook(fis);
			int index = workbook.getSheetIndex(sheetName);
			if(index==-1)
				return false;

			XSSFCellStyle style = workbook.createCellStyle();
			//style.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
			//style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

			sheet=workbook.getSheetAt(index);

			row = sheet.getRow(0);
			if (row == null)
				row = sheet.createRow(0);

			//cell = row.getCell();	
			//if (cell == null)
			//System.out.println(row.getLastCellNum());
			if(row.getLastCellNum() == -1)
				cell = row.createCell(0);
			else
				cell = row.createCell(row.getLastCellNum());

			cell.setCellValue(colName);
			cell.setCellStyle(style);

			fileOut = new FileOutputStream(path);
			workbook.write(fileOut);
			fileOut.close();		    

		}catch(Exception e){
			e.printStackTrace();
			return false;
		}

		return true;


	}

	// removes a column and all the contents
	public boolean removeColumn(String sheetName, int colNum) {
		try{
			if(!isSheetExist(sheetName))
				return false;
			fis = new FileInputStream(path); 
			workbook = new XSSFWorkbook(fis);
			sheet=workbook.getSheet(sheetName);
			XSSFCellStyle style = workbook.createCellStyle();
			//style.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
			//XSSFCreationHelper createHelper = workbook.getCreationHelper();
			//style.setFillPattern(HSSFCellStyle.NO_FILL);
			for(int i =0;i<getRowCount(sheetName);i++){
				row=sheet.getRow(i);	
				if(row!=null){
					cell=row.getCell(colNum);
					if(cell!=null){
						cell.setCellStyle(style);
						row.removeCell(cell);
					}
				}
			}
			fileOut = new FileOutputStream(path);
			workbook.write(fileOut);
			fileOut.close();
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;

	}
	// find whether sheets exists	
	public boolean isSheetExist(String sheetName){
		int index = workbook.getSheetIndex(sheetName);
		if(index==-1){
			index=workbook.getSheetIndex(sheetName.toUpperCase());
			if(index==-1)
				return false;
			else
				return true;
		}
		else
			return true;
	}

	// returns number of columns in a sheet	
	public int getColumnCount(String sheetName){
		// check if sheet exists
		if(!isSheetExist(sheetName))
			return -1;

		sheet = workbook.getSheet(sheetName);
		row = sheet.getRow(0);

		if(row==null)
			return -1;

		return row.getLastCellNum();

	}

	public int getCellRowNum(String sheetName,String colName,String cellValue){

		for(int i=2;i<=getRowCount(sheetName);i++){
			if(getCellData(sheetName,colName , i).equalsIgnoreCase(cellValue)){
				return i;
			}
		}
		return -1;

	}		
}


