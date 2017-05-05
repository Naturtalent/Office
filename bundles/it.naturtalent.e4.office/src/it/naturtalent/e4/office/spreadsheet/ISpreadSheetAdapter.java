package it.naturtalent.e4.office.spreadsheet;

import java.io.File;

public interface ISpreadSheetAdapter
{
	public static final String DEFAULT_SPREADSHEET_ADAPTER = "defaultspreadsheetadapter";
	public static final String DEFAULT_MSSPREADSHEET_ADAPTER = "defaultmsspreadsheetadapter";
	
	public void openSpreadsheetDocument(File docFile);
	
	public boolean openSheet(String tableName);
	public void appendSheet(String tableName);
	public void cloneSheet(int srcIdx, String name);
	public void renameSheet(String sheetName);
	public String [] readRow(int rowIdx, int nCols);
	public void writeRow(String [] values, int rowIdx, int colIdx);
	
	public void closeSpreadsheetDocument();
	public void showSpreadsheetDocument();

		
}
