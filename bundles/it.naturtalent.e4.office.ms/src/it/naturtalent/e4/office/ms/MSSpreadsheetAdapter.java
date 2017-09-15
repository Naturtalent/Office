package it.naturtalent.e4.office.ms;

import java.io.File;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import it.naturtalent.e4.office.spreadsheet.AbstractSpreadSheetAdapter;
import it.naturtalent.e4.office.spreadsheet.ISpreadSheetAdapter;

public class MSSpreadsheetAdapter extends AbstractSpreadSheetAdapter
{
	
	private MSSpreadsheetDocumentHandler documentHandler = new MSSpreadsheetDocumentHandler();
	
	private XSSFWorkbook workbook;
	private XSSFSheet sheet;
	

	@Override
	public void openSpreadsheetDocument(File docFile)
	{
		documentHandler.openOfficeDocument(docFile);
		workbook = (XSSFWorkbook) documentHandler.getDocument();
	}

	@Override
	public boolean openSheet(String tableName)
	{
		sheet = null;
		if(StringUtils.isEmpty(tableName))
			sheet = workbook.getSheetAt(0);
		else
			sheet = workbook.getSheet(tableName);
		
		return (sheet != null);
	}
	
	@Override
	public void appendSheet(String tableName)
	{
		if(!StringUtils.isEmpty(tableName))
			sheet = workbook.createSheet(tableName);
	}

	@Override
	public void cloneSheet(int idx, String name)
	{		
		sheet = workbook.cloneSheet(idx);
		int cloneIdx = workbook.getSheetIndex(sheet);
		workbook.setActiveSheet(cloneIdx);		
		workbook.setSheetName(cloneIdx, name);
	}

	@Override
	public void renameSheet(String sheetName)
	{
		if(workbook != null)
		{
			int idx = workbook.getActiveSheetIndex();
			workbook.setSheetName(idx, sheetName);
		}
	}

	@Override
	public String[] readRow(int rowIdx, int nCols)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void writeRow(String[] values, int rowIdx, int colIdx)
	{
		if(sheet != null)
		{
			XSSFRow row = sheet.getRow(rowIdx);
			if (row == null)
				row = sheet.createRow(rowIdx);

			for (int i = 0; i < values.length; i++)
			{
				XSSFCell cell = row.getCell(colIdx + i);
				if (cell == null)
					cell = row.createCell(colIdx + i);
				cell.setCellValue(values[i]);
			}

		}
	}

	@Override
	public void closeSpreadsheetDocument()
	{
		documentHandler.saveOfficeDocument();
		documentHandler.closeOfficeDocument();
	}

	@Override
	public void showSpreadsheetDocument()
	{
		documentHandler.showOfficeDocument();
	}

}
