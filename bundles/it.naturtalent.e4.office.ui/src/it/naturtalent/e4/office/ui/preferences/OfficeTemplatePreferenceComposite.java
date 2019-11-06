package it.naturtalent.e4.office.ui.preferences;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.service.prefs.BackingStoreException;

import it.naturtalent.e4.office.ui.ODFDefaultWriteAdapter;
import it.naturtalent.e4.preferences.CheckListEditorComposite;
import it.naturtalent.libreoffice.OpenLoDocument;




/**
 * Eine Praeferenzliste (Checkliste) mit den Namen (vom Path separierter Name) der Vorlagen.
 * Vorlagen (Templates) sind speziell formartierte (meistens Tabellen) ODF-Files die mit den jeweiligen Adaptern (IODFWriteAdapter)
 * kommunizieren koennen.  
 * 
 * Die Vorlagedateien werden zunaechst in ein temporaeres Verzeichnis kopiert. Alle Aenderungen (add, remove ...) werden
 * dort ausgefuehrt. Erst mit einem 'doApplied' werden die Vorlagen wieder zurueck in das Originalverzeichnis kopiert.   
 * 
 * @author dieter
 *
 */
public class OfficeTemplatePreferenceComposite extends CheckListEditorComposite
{
	
	//private static final String TEMPLATE_DIRNAME = "templates"; //$NON-NLS-N$
	private static final String TEMPORARY_VORLAGEN_DIRNAME = "vorlagen"; //$NON-NLS-N$

	// Knoten der Office-Praeferenzen
	protected IEclipsePreferences instancePreferenceNode;
	
	// Workspaceverzeichnis in dem die Vorlagen gehalten werden
	protected File templateWSDir;
	
	// Liste mit den Namen der existierenden Vorlagen
	protected List<String>templateNames;
	
	// fuer die Bearbeitung der Vorlagen werden diese in ein tempraeres Verzeichnis kopiert
	private File temporaryDir;
	
	// zusaetzliche Copy-Button
	private Button btnRename;
		
	/**
	 * Konstruktion
	 * 
	 * @param parent
	 * @param style
	 */	
	public OfficeTemplatePreferenceComposite(Composite parent, int style)
	{
		super(parent, style);
		
		initPreferenceNode();
		
		// Tooltip fuer Add)
		btnAdd.setToolTipText("Hinzufüge durch Kopieren der selektierten Vorlage"); 
		
		// Umbenennen
		btnRename = new Button(btnComposite, SWT.NONE);		
		btnRename.setText("Rename"); //$NON-NLS-N$
		btnRename.setToolTipText("Vorlage umbenennen"); //$NON-NLS-N$
		btnRename.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				IStructuredSelection selection = checkboxTableViewer.getStructuredSelection();
				Object selObj = selection.getFirstElement();
				if (selObj instanceof String)
				{					
					String newName = renameDialog((String) selObj);
					if(StringUtils.isNotEmpty(newName))
					{									
						File srcFile = new File(temporaryDir,((String) selObj)+"."+ODFDefaultWriteAdapter.OFFICEWRITEDOCUMENT_EXTENSION);
						File destFile = new File(temporaryDir,newName+"."+ODFDefaultWriteAdapter.OFFICEWRITEDOCUMENT_EXTENSION);
						try
						{
							FileUtils.moveFile(srcFile, destFile);
						} catch (IOException e1)
						{
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}	
						updateEntry(newName);
					}
				}
			}
		});
		
		// Verzeichnis der Vorlagen im Workspace
		templateWSDir = getWSTemplateDirectory();
		
		try
		{
			// die vorhandenen Vorlagen in ein temporaeres Verzeichnis kopieren
			IOFileFilter suffixFilter = FileFilterUtils.or(FileFilterUtils
					.suffixFileFilter(ODFDefaultWriteAdapter.OFFICEWRITEDOCUMENT_EXTENSION));
			temporaryDir = new File(FileUtils.getTempDirectory(), TEMPORARY_VORLAGEN_DIRNAME);	
			if(temporaryDir.exists())
				FileUtils.cleanDirectory(temporaryDir);
			FileUtils.copyDirectory(templateWSDir,temporaryDir,suffixFilter);
			
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		init();
		
		// die praeferenzierte Vorlage checken
		String tempPreference = instancePreferenceNode.get(
				OfficeDefaultPreferenceUtils.TEMPLATE_PREFERENCE,
				ODFDefaultWriteAdapter.ODFTEXT_TEMPLATE_NAME);
		checkboxTableViewer.setChecked(tempPreference, true);		
	}

	/*
	 * den PreferenceNode setzen
	 */
	protected void initPreferenceNode()
	{
		instancePreferenceNode = InstanceScope.INSTANCE
				.getNode(OfficeDefaultPreferenceUtils.ROOT_DEFAULTOFFICE_PREFERENCES_NODE);	
	}

	// gibt das Verzeichnis der Vorlagen im Workspace zurueck
	protected File getWSTemplateDirectory()
	{
		return ODFDefaultWriteAdapter.getWorkspaceTemplateDirectory();
	}
	
	protected void init()
	{
		// alle Template-Files aus dem TemplateVerzeichnis einlesen
		templateNames = ODFDefaultWriteAdapter.readTemplateNames();
		checkboxTableViewer.setInput(templateNames);
		updateWidgets();
	}

	public void setTemplateNames()
	{
		List<String>templateNames = readTempCopyNames();		
		setTemplateNames(templateNames);		
	}

	/*
	 * die Namen der Vorlagen in den 'checkboxTableViewer' speichern 
	 */
	public void setTemplateNames(List<String>templateNames)
	{
		checkboxTableViewer.setInput(templateNames);
		updateWidgets();
	}
	
	@Override
	protected void updateWidgets()
	{
		// TODO Auto-generated method stub
		super.updateWidgets();
		
		btnAdd.setEnabled(false);
		btnRemove.setEnabled(false);
		btnRename.setEnabled(false);
		IStructuredSelection selection = checkboxTableViewer.getStructuredSelection();
		Object selObj = selection.getFirstElement();
		if (selObj instanceof String)
		{
			// Default-Tempfile wird nicht geloescht oder umbenannt werden			
			btnRemove.setEnabled(!StringUtils.equals(ODFDefaultWriteAdapter.ODFTEXT_TEMPLATE_NAME,(String) selObj));
			btnRename.setEnabled(btnRemove.getEnabled());
			
			// Add (Kopieren) nur moeglich wenn eine Vorlage selektiert ist
			btnAdd.setEnabled(true);
		}
		
		// sicherstellen, dass ein Eintrag gecheckt ist
		if(checkboxTableViewer.getElementAt(0) != null)
		{
			if(ArrayUtils.isEmpty(checkboxTableViewer.getCheckedElements()))
				checkboxTableViewer.setChecked(checkboxTableViewer.getElementAt(0), true);
		}
	}
	
	@Override
	protected void doChecked()
	{
		IStructuredSelection selection = checkboxTableViewer.getStructuredSelection();
		Object selObj = selection.getFirstElement();
		if (selObj instanceof String)
		{
			// gecheckte Vorlage als Preferenz speichern (aber noch nicht festschreiben)
			instancePreferenceNode.put(OfficeDefaultPreferenceUtils.TEMPLATE_PREFERENCE, (String) selObj);
		}
	}

	/*
	 * Eine neue Vorlage durch Kopieren der selektierten erzeugen.
	 * Die neue Vorlage wird im temporaeren Arbeitsverzeichnis erzeugt
	 */
	@Override
	protected void doAdd()
	{
		IStructuredSelection selection = checkboxTableViewer.getStructuredSelection();
		Object selObj = selection.getFirstElement();
		if (selObj instanceof String)
		{
			// Name der neuen Vorlage
			String newTemplateName = renameDialog((String) selObj);
			if(StringUtils.isNotEmpty(newTemplateName))
			{						
				File destFile = new File(temporaryDir, newTemplateName+"."+ODFDefaultWriteAdapter.OFFICEWRITEDOCUMENT_EXTENSION);				
				File srcFile = new File(temporaryDir, ((String) selObj)+"."+ODFDefaultWriteAdapter.OFFICEWRITEDOCUMENT_EXTENSION);				
				try
				{
					// die neue Datei ist die Kopie der selektierten mit anderem Namen
					FileUtils.copyFile(srcFile, destFile);
					
					// Praeferenzliste aktualisieren
					setTemplateNames();
					
				} catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	/* 
	 * Die selektierte Vorlage oeffnen.
	 */
	@Override
	protected void doEdit()
	{		
		IStructuredSelection selection = checkboxTableViewer.getStructuredSelection();
		Object selObj = selection.getFirstElement();
		if (selObj instanceof String)
		{
			// Vorlage oeffnen					
			String tempName = (String) selObj+"."+ODFDefaultWriteAdapter.OFFICEWRITEDOCUMENT_EXTENSION;			
			File templateFile = new File(temporaryDir,tempName); 			
			OpenLoDocument.loadLoDocument(templateFile.getPath());
		}
	}
	

	@Override
	protected void doRemove()
	{
		IStructuredSelection selection = checkboxTableViewer.getStructuredSelection();
		Object selObj = selection.getFirstElement();
		if (selObj instanceof String)
		{
			// Vorlage loeschen				
			String tempName = (String) selObj+"."+ODFDefaultWriteAdapter.OFFICEWRITEDOCUMENT_EXTENSION;			
			File templateFile = new File(temporaryDir,tempName); 			
			try
			{
				FileUtils.forceDelete(templateFile);
				
				// Praeferenzliste aktualisieren
				setTemplateNames();
				
			} catch (IOException e)
			{				
				e.printStackTrace();
			}
		}
	}
	
	/*
	 * die temporaer zwischengespeicherten Dateien wieder in das Originalverzeichnis zurueckspeichern
	 */
	public void doApplied()
	{		
		try
		{
			// die 'alten' Vorlagen im Originalverzeichnis loeschen
			IOFileFilter suffixFilter = FileFilterUtils.or(FileFilterUtils
					.suffixFileFilter(ODFDefaultWriteAdapter.OFFICEWRITEDOCUMENT_EXTENSION));
			Collection<File> tempFiles = FileUtils.listFiles(templateWSDir,suffixFilter,null);						
			for(File tmpFile : tempFiles)			
				FileUtils.forceDelete(tmpFile);
			
			// alle Vorlagen zurueckspeichern
			FileUtils.copyDirectory(temporaryDir,templateWSDir);
			init();
			
			// Name der gecheckten Vorlage als Praeferenz soeichern
			String [] checkedElements = getCheckedElements();
			if(ArrayUtils.isNotEmpty(checkedElements))
			{
				instancePreferenceNode.put(OfficeDefaultPreferenceUtils.TEMPLATE_PREFERENCE,checkedElements[0]);
				try
				{
					instancePreferenceNode.flush();
				} catch (BackingStoreException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// Vorlagenname editieren
	private String renameDialog(String oldName)
	{
		final List<String>copiedTemplateNames = readTempCopyNames();
		
		InputDialog inputDialog = new InputDialog(getShell(), "Vorlage", "Name der Vorlage", oldName, new IInputValidator()
		{					
			@Override
			public String isValid(String newText)
			{
				if (StringUtils.isEmpty(newText))	
					return "leees Eingabefeld";
											
				if(copiedTemplateNames.contains(newText))
					return "der eingegebene Name existiert bereits";
				
				return null;
			}
		});
		
		return (inputDialog.open() == InputDialog.OK) ? inputDialog.getValue() : null; 
	}
	
	/*
	 * Die im PlugIn gespeicherten hardcodierten Vorlagn werden in das temporaere Arbeitsverzeichnis kopiert
	 */
	public void doRestoreDefaultPressed()
	{		
		// existiert das Verzeichnis noch nicht wird es initialisiert
		Bundle bundle = FrameworkUtil.getBundle(this.getClass());
		BundleContext bundleContext = bundle.getBundleContext();
		URL urlPluginTemplate = FileLocator.find(bundleContext.getBundle(),
				new Path(ODFDefaultWriteAdapter.PLUGIN_TEMPLATES_DIR), null);
		try
		{
			// Quelle ist das Verzeichnis mit den hardcodierten Vorlagen im PlugIn
			urlPluginTemplate = FileLocator.resolve(urlPluginTemplate);
			
			// Ziel ist temporaere Arbeitsverzeichnis
			File wsTeplateDir = getTemporaryDir();
			
			// hardcodierte 'odt'-Files kopieren
			IOFileFilter suffixFilter = FileFilterUtils.or(FileFilterUtils.suffixFileFilter(
					ODFDefaultWriteAdapter.OFFICEWRITEDOCUMENT_EXTENSION));
			FileUtils.copyDirectory(FileUtils.toFile(urlPluginTemplate),wsTeplateDir,suffixFilter);	
			
			// Praeferenzliste aktualisieren
			setTemplateNames();
			
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/*
	 * Gibt es bereits eine Datei mit diesem Name wird ein zaehlerbasierende Erweiterung hinzugefuegt.
	 */
	private static String getAutoFileName(File dir, String originalFileName)
	{
		String autoFileName;

		if (dir == null)
			return ""; //$NON-NLS-1$

		int counter = 1;
		while (true)
		{
			if (counter > 1)
			{
				autoFileName = FilenameUtils.getBaseName(originalFileName)
						+ new Integer(counter) + "." //$NON-NLS-1$
						+ FilenameUtils.getExtension(originalFileName);
			}
			else
			{
				autoFileName = originalFileName;
			}
			File res = new File(dir, autoFileName);
			if (!res.exists())
				return autoFileName;

			counter++;
		}
	}
	
	/*
	 * Die Namen der Vorlagen im temporaeren Arbeitsverzeichnis 'templatesCopyDir' auflisten.
	 */
	public List<String> readTempCopyNames()
	{
		List<String>templateNames = new ArrayList<String>();
		
		IOFileFilter suffixFilter = FileFilterUtils.or(FileFilterUtils
				.suffixFileFilter(ODFDefaultWriteAdapter.OFFICEWRITEDOCUMENT_EXTENSION));
		Collection<File> tempFiles = FileUtils.listFiles(temporaryDir,suffixFilter,null);
					
		for(File tmpFile : tempFiles)
			templateNames.add(FilenameUtils.getBaseName(tmpFile.getPath()));
		
		return templateNames;
	}

	public File getTemporaryDir()
	{
		return temporaryDir;
	}
	
	
}
