package it.naturtalent.e4.office.ui.dialogs;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.odftoolkit.simple.SpreadsheetDocument;

import it.naturtalent.e4.office.ui.OfficeUtils;
import it.naturtalent.e4.project.expimp.ExpImportData;
import it.naturtalent.e4.project.expimp.dialogs.AbstractExportDialog;
import it.naturtalent.e4.project.expimp.dialogs.ExportDestinationComposite;
import it.naturtalent.office.model.address.Kontakt;
import it.naturtalent.office.model.address.Kontakte;

/**
 * Dialog zur Vorbereitung des Exports der Kontaktdaten in eine Tabelle. 
 *   
 * @author dieter
 *
 */
public class JournalKontaktExportDialog extends AbstractExportDialog
{

	// DialogSettings Journal - Zielverzeichnis und (Project/Workingset - Settings)
	private static final String KONTAKTEXPORTPATH_SETTING_KEY = "journalexportkontaktpathsetting"; //$NON-NLS-N$	

	 private static Shell parentShell;
	 
	 private File destFile;
	 
	/**
	 * 
	 */
	public JournalKontaktExportDialog()
	{
		super(parentShell);	
		exportSettingKey = KONTAKTEXPORTPATH_SETTING_KEY;
	}
	
	/**
	 * 
	 */
	public JournalKontaktExportDialog(Shell parentShell)
	{
		super(parentShell);	
		exportSettingKey = KONTAKTEXPORTPATH_SETTING_KEY;
	}
	
	@Override
	protected Control createDialogArea(Composite parent)
	{
		Control control = super.createDialogArea(parent);		
		setMessage("die ausgewählten Kontakte in einer Tabelle im Exportverzeichnis speichern");
		return control;
	}
	
	@Override
	protected void init()
	{
		super.init(); // Exportpath aus Settings uebernehmen
		List<ExpImportData>lexpimpdata = new ArrayList<ExpImportData>();
		Kontakte kontakte = OfficeUtils.getKontakte();
		EList<Kontakt>kontaktList = kontakte.getKontakte();
		for(Kontakt kontakt : kontaktList)
		{
			ExpImportData expimpdata = new ExpImportData();
			expimpdata.setLabel(kontakt.getName());
			expimpdata.setData(kontakt);
			lexpimpdata.add(expimpdata);
		}
		setModelData(lexpimpdata);
	}
	
	@Inject
	@Optional
	public void handleModelChangedEvent(
			@UIEventTopic(ExportDestinationComposite.EXPORTDESTINATION_EVENT) String exportPath)
	{		
		this.exportPath = exportPath;
		update();
	}

	@Override
	protected void okPressed()
	{
		// das ausgewaehlte Zielverzeichnis
		String destPath = FilenameUtils.removeExtension(exportPath) + ".ods";
		destFile = new File(destPath);		
		boolean exitState = true;
		if(destFile.exists())
		{
			if(!MessageDialog.openQuestion(parentShell, "Export", 
					"Soll die besthende Datei überschrieben werden ?"))
				exitState = false;
		}

		if(exitState)					
			super.okPressed();		
	}

	public File getDestFile()
	{
		return destFile;
	}
	
	

}
