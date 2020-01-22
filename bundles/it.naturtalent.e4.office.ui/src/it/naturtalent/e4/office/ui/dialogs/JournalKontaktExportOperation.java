package it.naturtalent.e4.office.ui.dialogs;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
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
import it.naturtalent.office.model.address.Adresse;
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
	
	private String[] headerLabel = new String[]
		{ "Name", "Kommunikationsdaten", "Adresse Name", "Adresse NameZusatz","Adresse Strasse", "Adresse Ort" };
	
	/**
	 * Konstruktion
	 * @param destFile
	 * @param selectedKontakts
	 */
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
						
			// StandardTabellenseite '0' entfernen
			List<String>tableNames = getTableNames(calcDoc);
			if(tableNames.size() > 0)
				calcDoc.removeSheet(0);
			
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
					Kontakt kontakt = (Kontakt) data;
					if(StringUtils.equals(kontakt.getName(), "Firma Jost Tiefbau"))
							System.out.println(kontakt.getName());
					
					addKontaktData (kontaktTable, rowIdx, kontakt);					
					Adresse adresse = kontakt.getAdresse();
					if(adresse != null)
						addAddressData (kontaktTable, rowIdx++, kontakt.getAdresse());
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
		int n = headerLabel.length;
		for(int cellIdx = 0; cellIdx < n; cellIdx++)
			createHeaderCell(row, cellIdx, headerLabel[cellIdx]);
		
	}
	
	// Kontaktdaten ausgeben
	private void addKontaktData	(Table projectTable, int rowIdx, Kontakt kontakt)
	{
		int cellIndex = 0;
		Row row = projectTable.getRowByIndex(rowIdx);
		
		Cell cell = row.getCellByIndex(cellIndex++);		
		cell.setStringValue(kontakt.getName());
		
		cell = row.getCellByIndex(cellIndex++);
		cell.setStringValue(kontakt.getKommunikation());
	}
	
	// Adressdaten ausgeben
	private void addAddressData	(Table projectTable, int rowIdx, Adresse adresse)
	{
		String name = adresse.getName();
		if(StringUtils.isNotEmpty(name))
		{
			int cellIndex = 2;
			Row row = projectTable.getRowByIndex(rowIdx);

			Cell cell = row.getCellByIndex(cellIndex++);
			cell.setStringValue(name);

			cell = row.getCellByIndex(cellIndex++);
			cell.setStringValue(adresse.getName2());
			
			cell = row.getCellByIndex(cellIndex++);
			cell.setStringValue(adresse.getStrasse());
			
			cell = row.getCellByIndex(cellIndex++);
			cell.setStringValue(adresse.getOrt());
		}
	}

	
	// Header ausgeben
	private void createHeaderCell(Row headerRow, int cellIdx, String label)
	{
		Cell cell = headerRow.getCellByIndex(cellIdx);
		cell.setHorizontalAlignment(HorizontalAlignmentType.CENTER);
		cell.setFont(new Font("Arial", FontStyle.BOLD, 10));
		cell.setStringValue(label);		
	}
	
	//Tabellennamen auflisten und zurueckgeben
	private List<String> getTableNames(SpreadsheetDocument spreadSheet)
	{
		List<String>tableNames = new ArrayList<String>();
		
		List<Table>tables = spreadSheet.getTableList();
		if((tables != null ) && (!tables.isEmpty()))
		{
			for(Table table : tables)
				tableNames.add(table.getTableName());
		}
						
		return tableNames;
	}
}
