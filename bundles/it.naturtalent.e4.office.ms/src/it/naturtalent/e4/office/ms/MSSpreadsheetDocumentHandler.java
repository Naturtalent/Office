package it.naturtalent.e4.office.ms;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class MSSpreadsheetDocumentHandler extends MSOfficeTextDocumentHandler
{
	
	private XSSFWorkbook workbook;

	@Override
	public void saveOfficeDocument()
	{
		if(workbook != null)
		{
			FileOutputStream fos;
			try
			{
				fos = new FileOutputStream(fileDoc);
				workbook.write(fos);
				fos.flush();
				fos.close();
			} catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	
	@Override
	public void openOfficeDocument(File fileDoc)
	{
		try
		{
			this.fileDoc = fileDoc;
			FileInputStream fis = new FileInputStream(fileDoc);
			workbook = new XSSFWorkbook(fis);			
			
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	@Override
	public Object getDocument()
	{		
		return workbook;
	}

	
	
}
