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

/**
 * Export der Projektdaten in einer Runningprogress Operation realisieren.
 * 
 * @author dieter
 *
 */
public class JournalProjektExportOperation implements IRunnableWithProgress
{
	private static final String PROJECT_TABLENAME = "Projekte";
	
	private File destFile; 
	private IProject[]selectedProjects;
	
	
	
	public JournalProjektExportOperation(File destFile, IProject[] selectedProjects)
	{
		super();
		this.destFile = destFile;
		this.selectedProjects = selectedProjects;
	}



	/**
	 * 
	 */
	@Override
	public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException
	{
		SpreadsheetDocument calcDoc = null;
		
		monitor.beginTask("Projektdaten exportieren",IProgressMonitor.UNKNOWN);
		
		try
		{
			calcDoc = (destFile.exists()) ? SpreadsheetDocument.loadDocument(destFile) :  
				SpreadsheetDocument.newSpreadsheetDocument();
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Tabelle erzeugen
		Table projectTable = calcDoc.getSheetByName(PROJECT_TABLENAME);
		int lastIdx = calcDoc.getSheetCount(); 
		for(int idx = 0;idx < lastIdx;idx++)
		{
			if(projectTable == calcDoc.getSheetByIndex(idx))
			{
				calcDoc.removeSheet(idx);
				break;
			}
		}
		projectTable = calcDoc.appendSheet(PROJECT_TABLENAME);
		
		// Tabelle vorbereiten
		prepareTable(projectTable);
		
		// Projecte uebertragen
		if(projectTable != null)
		{
			int rowIdx = 1;
			for(IProject iProject : selectedProjects)			
				addProjectData (projectTable, rowIdx++, iProject);			
		}
		
		try
		{
			calcDoc.save(destFile);
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		cell.setStringValue("Projekt ID");		
				
		cell = row.getCellByIndex(1);
		cell.setHorizontalAlignment(HorizontalAlignmentType.CENTER);
		cell.setFont(new Font("Arial", FontStyle.BOLD, 10));
		cell.setStringValue("Projektname");
	}
	
	private void addProjectData	(Table projectTable, int rowIdx, IProject iProject)
	{
		int cellIndex = 0;
		Row row = projectTable.getRowByIndex(rowIdx);
		Cell cell = row.getCellByIndex(cellIndex++);
		
		cell.setStringValue(iProject.getName());
		
		try
		{
			String name = iProject.getPersistentProperty(INtProject.projectNameQualifiedName);
			cell = row.getCellByIndex(cellIndex++);
			cell.setStringValue(name);
			
		} catch (CoreException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	
}
