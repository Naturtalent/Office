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
import it.naturtalent.e4.office.ui.dialogs.JournalKontaktExportDialog;
import it.naturtalent.e4.office.ui.dialogs.JournalKontaktExportOperation;
import it.naturtalent.e4.office.ui.dialogs.JournalProjektExportDialog;
import it.naturtalent.e4.office.ui.dialogs.JournalProjektExportFinish;
import it.naturtalent.e4.office.ui.dialogs.JournalProjektExportOperation;
import it.naturtalent.e4.project.INtProject;
import it.naturtalent.e4.project.expimp.ExpImportData;

/**
 * Diese Aktion wird wom Adapter 'JournalKontaktExportAdapter' definiert und in 
 * it.naturtalent.e4.project.expimp.dialogs.Export Dialog via ContextInjectionFactory.make() instanziiert.
 * 
 * Die Action ruft den Dialog 'JournalKontaktExportDialog' auf
 * mit dem die zu exportierenden Kontakte ausgeaehlt werden koennen. 
 * 
 * 'context' wird durch die Instanziierung in ContextInjectionFactory.make() in 
 * it.naturtalent.e4.project.expimp.dialogs.ExportDialog gesetzt.
 * 
 * @author dieter
 *
 */
public class JournalKontaktExportAction extends Action
{	
	@Inject
	@Optional
	private IEclipseContext context;
	
	//private static final String KONTAKT_TABLENAME = "Kontakte";

	@Override
	public void run()
	{
		// mit den Dialog 'JournalProjektExportDialog' Zielfile und Exportprojekte definieren 
		JournalKontaktExportDialog exportDialog = ContextInjectionFactory.make(JournalKontaktExportDialog.class, context);
		if(exportDialog.open() == JournalKontaktExportDialog.OK)
		{
			try
			{				
				ExpImportData [] selectedData = exportDialog.getSelectedData();						
				doExport(exportDialog.getDestFile(), exportDialog.getSelectedData());
				
			} catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		super.run();	
	}

	// den Eyport mit einer running Operation durchfuhren
	private void doExport(File destFile, ExpImportData [] selectedKontakts) throws Exception
	{
		// Progressdialog erzeugen und starten 
		JournalKontaktExportOperation exportOperation = new JournalKontaktExportOperation (destFile, selectedKontakts);
		
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
		

}
