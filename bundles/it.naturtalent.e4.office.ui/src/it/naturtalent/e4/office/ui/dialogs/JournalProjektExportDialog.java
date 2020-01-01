package it.naturtalent.e4.office.ui.dialogs;

import java.io.File;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import it.naturtalent.e4.project.expimp.dialogs.ExportNtProjectDialog;
import it.naturtalent.icons.core.Icon;
import it.naturtalent.icons.core.IconSize;

/**
 * Dialog zur Vorbereitung des Exports der relevanten Projektdaten in eine Tabelle.
 *   
 * @author dieter
 *
 */
public class JournalProjektExportDialog extends ExportNtProjectDialog
{

	// DialogSettings Journal - Zielverzeichnis und (Project/Workingset - Settings)
	private static final String JOURNALEXPORT_DESTDIRS_SETTINGS = "journalexportdir_settings"; //$NON-NLS-1$	
	private static final String JOURNALEXPORT_SETTINGS = "journalexport_settings"; //$NON-NLS-1$
	private static final String JOURNALEXPORT_FILE_SETTINGS = "journalexportfile_settings"; //$NON-NLS-1$
	
	// erforderlich fuer eine Konstruktor ohne Parameter
	private static Shell parentShell;
	
	// Name der Exportdatei (SpreedSheat-Datei)  
	private Text exportFileName;
	
	// Errordecoration fuer das Texteingabefeld
	private ControlDecoration controlDecorationExportFile;
	
	// die Zieldatei 
	private File destDocumentFile;
	
	// die selektierten Projekte 
	private IProject[] selectedProjects;

	
	/**
	 * @wbp.parser.constructor
	 */
	public JournalProjektExportDialog()
	{
		super(parentShell);		
		destDirSetting = JOURNALEXPORT_DESTDIRS_SETTINGS;
		destSetting = JOURNALEXPORT_SETTINGS;
	}
		
	public JournalProjektExportDialog(Shell parentShell)
	{
		super(parentShell);
		destDirSetting = JOURNALEXPORT_DESTDIRS_SETTINGS;
		destSetting = JOURNALEXPORT_SETTINGS;
	}
	
	/**
	 * 'ExportNtProjectDialog* erweitern mit Textwidget zur Eingabe des Tabellendateinamens
	 */
	@Override
	protected Control createDialogArea(Composite parent)
	{
		Control ctrl = super.createDialogArea(parent);
		setTitle("Projektdaten exportieren");
		setMessage("Projektdaten in einer Tabelle dokumentieren");
		
		// UI - Erweiterungen
		Label lblExportFile = new Label(compositeExportdir, SWT.NONE);
		lblExportFile.setBounds(0, 0, 55, 15);
		lblExportFile.setText("Exportdatei");
		
		// Dateieingabefeld
		exportFileName = new Text(compositeExportdir, SWT.NONE);
		exportFileName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		exportFileName.addModifyListener(new ModifyListener()
		{			
			@Override
			public void modifyText(ModifyEvent e)
			{
				updateWidgets();				
			}
		});
				
		// Decoration signalisiert ein leeres Eingabefeld
		controlDecorationExportFile = new ControlDecoration(exportFileName, SWT.LEFT | SWT.TOP);
		controlDecorationExportFile.setImage(Icon.OVERLAY_ERROR.getImage(IconSize._7x8_OverlayIconSize));		
		controlDecorationExportFile.setDescriptionText("kein Dateiname definiert"); //$NON-NLS-N$
		
		String settingName = settings.get(JOURNALEXPORT_FILE_SETTINGS);
		if(StringUtils.isNotEmpty(settingName))
			exportFileName.setText(settingName);
		
		return ctrl;
	}
	
	/*
	 *  Update - erweitert den Ok-Button - UpdateCheck auf erweiterten Text-Widget ('fileName')
	 *  Leeres Eingabefeld 'exportFileName' zeigt das Error-DecorationImage und disabled den Ok-Button
	 */
	@Override
	protected boolean updateWidgets()
	{
		controlDecorationExportFile.hide();
		if(StringUtils.isEmpty(exportFileName.getText()))
		{
			controlDecorationExportFile.show();
			if(okButton != null)
				okButton.setEnabled(false);
			return false;
		}

		if(!super.updateWidgets())
			return false;
		
		return true;
	}

	@Override
	protected void okPressed()
	{
		// die zum Export ausgewaehlten Projekte
		selectedProjects = getCheckedProjects();
		
		// das ausgewaehlte Zielverzeichnis
		File resultExportDir = new File(exportComboDir.getText());

		// die Zieldatei erzeugen
		destDocumentFile = new File(resultExportDir,
				FilenameUtils.removeExtension(exportFileName.getText()) + ".ods");

		boolean exitState = true;
		if(destDocumentFile.exists())
		{
			if(!MessageDialog.openQuestion(parentShell, "Export", 
					"Soll die besthende Datei überschrieben werden ?"))
				exitState = false;
		}

		if(exitState)
			super.okPressed();		
	}
	
	@Override
	protected void storeSettings()
	{
		settings.put(JOURNALEXPORT_FILE_SETTINGS, FilenameUtils.getBaseName(exportFileName.getText()));
		super.storeSettings();
	}

	public File getDestDocumentFile()
	{
		return destDocumentFile;
	}

	public IProject[] getSelectedProjects()
	{
		return selectedProjects;
	}
	


}
