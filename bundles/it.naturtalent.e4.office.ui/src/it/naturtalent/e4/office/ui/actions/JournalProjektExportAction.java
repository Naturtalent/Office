package it.naturtalent.e4.office.ui.actions;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.widgets.Display;
import org.odftoolkit.odfdom.type.Color;
import org.odftoolkit.simple.SpreadsheetDocument;
import org.odftoolkit.simple.style.Font;
import org.odftoolkit.simple.style.StyleTypeDefinitions.FontStyle;
import org.odftoolkit.simple.style.StyleTypeDefinitions.HorizontalAlignmentType;
import org.odftoolkit.simple.table.Cell;
import org.odftoolkit.simple.table.CellRange;
import org.odftoolkit.simple.table.Column;
import org.odftoolkit.simple.table.Row;
import org.odftoolkit.simple.table.Table;

import it.naturtalent.e4.office.ui.JournalProjectExportAdapter;
import it.naturtalent.e4.office.ui.dialogs.JournalProjektExportDialog;
import it.naturtalent.e4.office.ui.dialogs.JournalProjektExportFinish;
import it.naturtalent.e4.office.ui.dialogs.JournalProjektExportOperation;
import it.naturtalent.e4.project.INtProject;

/**
 * Diese Aktion wird wom Adapter 'JournalProjectExportAdapter' definiert und in 
 * it.naturtalent.e4.project.expimp.dialogs.Export Dialog via ContextInjectionFactory.make() instanziiert.
 * 
 * Die Action ruft den Dialog 'JournalProjektExportDialog' auf
 * mit dem die zu exportierenden Projekte ausgeaehlt werden koennen. 
 * 
 * 'context' wird durch die Instanziierung in ContextInjectionFactory.make() in 
 * it.naturtalent.e4.project.expimp.dialogs.ExportDialog gesetzt.
 * 
 * @author dieter
 *
 */
public class JournalProjektExportAction extends Action
{	
	@Inject
	@Optional
	private IEclipseContext context;
	
	private static final String PROJECT_TABLENAME = "Projekte";

	@Override
	public void run()
	{
		// mit den Dialog 'JournalProjektExportDialog' Zielfile und Exportprojekte definieren 
		JournalProjektExportDialog exportDialog = ContextInjectionFactory.make(JournalProjektExportDialog.class, context);
		if(exportDialog.open() == JournalProjektExportDialog.OK)
		{
			try
			{
				doExport(exportDialog.getDestDocumentFile(), exportDialog.getSelectedProjects());
			} catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		super.run();	
	}

	// den Eyport mit einer running Operation durchfuhren
	private void doExport(File destFile, IProject[]selectedProjects) throws Exception
	{
		// Progressdialog erzeugen und starten 
		JournalProjektExportOperation exportOperation = new JournalProjektExportOperation (destFile, selectedProjects);
		
		try
		{
			// Import ausfuehren
			ProgressMonitorDialog dialog = new ProgressMonitorDialog(Display.getDefault().getActiveShell());			
			dialog.setCancelable(true);
			dialog.run(true, true, exportOperation);
			
		} catch (InvocationTargetException e)
		{
			// Error
			Throwable realException = e.getTargetException();
			String message = realException.getMessage();
			message = StringUtils.isNotEmpty(message) ? message : "interner Fehler";
			MessageDialog.openError(Display.getCurrent().getActiveShell(), "Export Fehler", message);			
			return;
			
		} catch (InterruptedException e)
		{
			// Abbruch
			MessageDialog.openError(Display.getDefault().getActiveShell(), "Export Abbruch", "die Aktion wurde abgebrochen");
			return;
		}
		
		JournalProjektExportFinish dialog = new JournalProjektExportFinish(Display.getDefault().getActiveShell());
		dialog.setExportFile(destFile.getPath());
		//dialog.create();
		//dialog.setExportFile(destFile.getPath());
		dialog.open();
		
	}
	/*
	private void performExport(File destFile, IProject[]selectedProjects) throws Exception
	{
		SpreadsheetDocument calcDoc;
		
		calcDoc = (destFile.exists()) ? SpreadsheetDocument.loadDocument(destFile) :  
			SpreadsheetDocument.newSpreadsheetDocument();
		
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

		// Zieldateipfand vor den Schliessen des ODFDokuments retten
		//String exportFile = calcDoc.getBaseURI();
		
		calcDoc.save(destFile);
		calcDoc.close();
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
	*/
	
}
