package it.naturtalent.e4.office.ui.dialogs;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.odftoolkit.simple.SpreadsheetDocument;
import org.odftoolkit.simple.style.Font;
import org.odftoolkit.simple.style.StyleTypeDefinitions.FontStyle;
import org.odftoolkit.simple.style.StyleTypeDefinitions.HorizontalAlignmentType;
import org.odftoolkit.simple.table.Cell;
import org.odftoolkit.simple.table.Column;
import org.odftoolkit.simple.table.Row;
import org.odftoolkit.simple.table.Table;

import it.naturtalent.e4.project.INtProject;
import it.naturtalent.e4.project.expimp.ExpImportData;
import it.naturtalent.office.model.address.Kontakt;

/**
 * Export der Projektdaten in einer Runningprogress Operation realisieren.
 * 
 * @author dieter
 *
 */
public class JournalKontaktExportOperation implements IRunnableWithProgress
{
	// Tabellename im SpreadSheet
	private static final String KONTAKT_TABLENAME = "Kontakte";
	
	private File destFile; 
	private ExpImportData [] expImportData;
	
	public JournalKontaktExportOperation(File destFile, ExpImportData [] selectedKontakts)
	{
		super();
		this.destFile = destFile;
		this.expImportData = selectedKontakts;
	}

	/**
	 * 
	 */
	@Override
	public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException
	{
		SpreadsheetDocument calcDoc = null;
		
		monitor.beginTask("Kontaktdaten exportieren",IProgressMonitor.UNKNOWN);
		
		// SpreadSheet laden oder neu erzeugen
		try
		{
			calcDoc = (destFile.exists()) ? SpreadsheetDocument.loadDocument(destFile) :  
				SpreadsheetDocument.newSpreadsheetDocument();
			
		} catch (Exception e)
		{
			throw new InvocationTargetException(e);			
		}
		
		// neues Tabellenblatt erzeugen
		Table kontaktTable = calcDoc.getSheetByName(KONTAKT_TABLENAME);
		int lastIdx = calcDoc.getSheetCount(); 
		for(int idx = 0;idx < lastIdx;idx++)
		{
			if(kontaktTable == calcDoc.getSheetByIndex(idx))
			{
				// evtl. bestehendes entfernen
				calcDoc.removeSheet(idx);
				break;
			}
		}
		kontaktTable = calcDoc.appendSheet(KONTAKT_TABLENAME);
		
		// Tabelle vorbereiten
		prepareTable(kontaktTable);
		
		// Kontakte uebertragen
		if(kontaktTable != null)
		{
			int rowIdx = 1;
			for(ExpImportData expImportItem : expImportData)
			{
				Object data = expImportItem.getData();
				if (data instanceof Kontakt)
				{
					System.out.println(((Kontakt) data).getName());
					addKontaktData (kontaktTable, rowIdx++, (Kontakt) data);
				}
			}
		}
		
		try
		{
			calcDoc.save(destFile);
		} catch (Exception e)
		{
			throw new InvocationTargetException(e);			
		}
		calcDoc.close();
		
		monitor.done();
	}
	
	// Tabelle vorbereiten
	private void prepareTable(Table projectTable)
	{
		int cellIndex = 0;
		
		// alle Spalten auf optimale Breite schalten
		for(cellIndex = 0;cellIndex < 10;cellIndex++)
		{
			Column column = projectTable.getColumnByIndex(cellIndex);			
			column.setUseOptimalWidth(true);
			column.setWidth(32.0);
		}
		
		// Kopfzeile
		Row row = projectTable.getRowByIndex(0);
		
		Cell cell = row.getCellByIndex(0);	
		cell.setHorizontalAlignment(HorizontalAlignmentType.CENTER);
		cell.setFont(new Font("Arial", FontStyle.BOLD, 10));
		cell.setStringValue("Name");		
				
		cell = row.getCellByIndex(1);
		cell.setHorizontalAlignment(HorizontalAlignmentType.CENTER);
		cell.setFont(new Font("Arial", FontStyle.BOLD, 10));
		cell.setStringValue("Kommunikationsdaten");
	}
	
	private void addKontaktData	(Table projectTable, int rowIdx, Kontakt kontakt)
	{
		int cellIndex = 0;
		Row row = projectTable.getRowByIndex(rowIdx);
		
		Cell cell = row.getCellByIndex(cellIndex++);		
		cell.setStringValue(kontakt.getName());
		
		cell = row.getCellByIndex(cellIndex++);
		cell.setStringValue(kontakt.getKommunikation());
	}
	
	
}
