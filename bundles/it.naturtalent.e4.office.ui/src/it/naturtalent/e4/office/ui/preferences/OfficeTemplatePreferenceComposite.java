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
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.service.prefs.BackingStoreException;

import it.naturtalent.e4.office.ui.Activator;
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
	
	private static final String TEMPLATE_DIRNAME = "templates"; //$NON-NLS-N$
	private static final String BACKUP_VORLAGEN_DIRNAME = "vorlagen"; //$NON-NLS-N$
	private static final String ORIGINAL_DIRNAME = "original"; //$NON-NLS-N$

	// Knoten der Office-Praeferenzen
	protected IEclipsePreferences instancePreferenceNode;
	
	// in diesem Verzeichnis befinden sich die Vorlagen 
	private File templateDir;
	
	// die Namen der existierenden Vorlagen
	protected List<String>templateNames;
	
	// in dieses Verzeichnis werden die momentanen Vorlagen gerettet
	private File templatesCopyDir;
	
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
						File srcFile = new File(templatesCopyDir,((String) selObj)+"."+ODFDefaultWriteAdapter.OFFICEWRITEDOCUMENT_EXTENSION);
						File destFile = new File(templatesCopyDir,newName+"."+ODFDefaultWriteAdapter.OFFICEWRITEDOCUMENT_EXTENSION);
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
		
		templateDir = getPlugInDirectory(TEMPLATE_DIRNAME);
		
		
		try
		{
			// die vorhandenen Vorlagen in ein temporaeres Verzeichnis kopieren
			IOFileFilter suffixFilter = FileFilterUtils.or(FileFilterUtils
					.suffixFileFilter(ODFDefaultWriteAdapter.OFFICEWRITEDOCUMENT_EXTENSION));
			templatesCopyDir = new File(FileUtils.getTempDirectory(), BACKUP_VORLAGEN_DIRNAME);	
			if(templatesCopyDir.exists())
				FileUtils.cleanDirectory(templatesCopyDir);
			FileUtils.copyDirectory(templateDir,templatesCopyDir,suffixFilter);
			
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

	/*
	 * Gibt das absolute Verzeichnis von 'relativePath' innerhalb eines PlugIn zurueck.
	 */
	protected File getPlugInDirectory(String relativePath)
	{
		return ODFDefaultWriteAdapter.getPluginDirectory(relativePath);
	}
	
	
	protected void init()
	{
		// alle Template-Files aus dem TemplateVerzeichnis einlesen
		templateNames = ODFDefaultWriteAdapter.readTemplateNames();
		checkboxTableViewer.setInput(templateNames);
		updateWidgets();
	}
	
	@Override
	protected void updateWidgets()
	{
		// TODO Auto-generated method stub
		super.updateWidgets();
		
		btnRemove.setEnabled(false);
		btnRename.setEnabled(false);
		IStructuredSelection selection = checkboxTableViewer.getStructuredSelection();
		Object selObj = selection.getFirstElement();
		if (selObj instanceof String)
		{
			// Default-Tempfile wird nicht geloescht oder umbenannt werden			
			btnRemove.setEnabled(!StringUtils.equals(ODFDefaultWriteAdapter.ODFTEXT_TEMPLATE_NAME,(String) selObj));
			btnRename.setEnabled(btnRemove.getEnabled());
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
	 * Die neue Vorlage wird zunaechst im 'templatesCopyDir' erzeugt.
	 */
	@Override
	protected void doAdd()
	{
		IStructuredSelection selection = checkboxTableViewer.getStructuredSelection();
		Object selObj = selection.getFirstElement();
		if (selObj instanceof String)
		{
			// Name der neuen Vorlage
			String newName = renameDialog((String) selObj);
			if(StringUtils.isNotEmpty(newName))
			{						
				File destFile = new File(templatesCopyDir, newName+"."+ODFDefaultWriteAdapter.OFFICEWRITEDOCUMENT_EXTENSION);				
				File srcFile = new File(templatesCopyDir, ((String) selObj)+"."+ODFDefaultWriteAdapter.OFFICEWRITEDOCUMENT_EXTENSION);				
				try
				{
					FileUtils.copyFile(srcFile, destFile);
					addEntry(newName);
					checkboxTableViewer.setSelection(new StructuredSelection(newName));
					
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
			File templateFile = new File(templatesCopyDir,tempName); 			
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
			File templateFile = new File(templatesCopyDir,tempName); 			
			try
			{
				FileUtils.forceDelete(templateFile);				
			} catch (IOException e)
			{				
				e.printStackTrace();
			}
		}
		super.doRemove();
		checkboxTableViewer.setSelection(new StructuredSelection(ODFDefaultWriteAdapter.ODFTEXT_TEMPLATE_NAME));
	}
	
	/*
	 * Backupdateien aus dem 'templatesCopyDir' zurueckspeichern.
	 */
	public void doApplied()
	{
		// zurueckspeichern der Backupdaten
		try
		{
			// die 'alten' Vorlagen loeschen
			IOFileFilter suffixFilter = FileFilterUtils.or(FileFilterUtils
					.suffixFileFilter(ODFDefaultWriteAdapter.OFFICEWRITEDOCUMENT_EXTENSION));
			Collection<File> tempFiles = FileUtils.listFiles(templateDir,suffixFilter,null);						
			for(File tmpFile : tempFiles)			
				FileUtils.forceDelete(tmpFile);
			
			// alle Vorlagen zurueckspeichern
			FileUtils.copyDirectory(templatesCopyDir,templateDir);
			init();
			
			// das gecheckte Element wird als Praeferenz gespeichert
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
	 * Die Originaldateien werden wiederhergestellt
	 */
	public void doRestoreDefaultPressed()
	{
		try
		{
			File originalDir = getPlugInDirectory(ORIGINAL_DIRNAME);			
			FileUtils.copyDirectory(originalDir,templateDir);
			init();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
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
	 * Die Namen der Vorlagen im 'templatesCopyDir' auflisten.
	 */
	public List<String> readTempCopyNames()
	{
		List<String>templateNames = new ArrayList<String>();
		
		IOFileFilter suffixFilter = FileFilterUtils.or(FileFilterUtils
				.suffixFileFilter(ODFDefaultWriteAdapter.OFFICEWRITEDOCUMENT_EXTENSION));
		Collection<File> tempFiles = FileUtils.listFiles(templatesCopyDir,suffixFilter,null);
					
		for(File tmpFile : tempFiles)
			templateNames.add(FilenameUtils.getBaseName(tmpFile.getPath()));
		
		return templateNames;
	}
}
