package it.naturtalent.e4.office.ui;


import java.io.File;
import java.io.FilenameFilter;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.e4.core.commands.EHandlerService;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.internal.workbench.E4Workbench;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.commands.MHandler;
import org.eclipse.e4.ui.workbench.IWorkbench;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.osgi.service.prefs.BackingStoreException;

import it.naturtalent.application.ChooseWorkspaceData;
import it.naturtalent.application.IPreferenceNode;
import it.naturtalent.e4.office.OfficeConstants;
import it.naturtalent.e4.preferences.AbstractPreferenceAdapter;
import it.naturtalent.e4.preferences.DirectoryEditorComposite;
import it.naturtalent.e4.preferences.handlers.PreferenceHandler;


public class OfficeApplicationPreferenceAdapter extends AbstractPreferenceAdapter
{
	private OfficeApplicationPreferenceComposite comp;
	
	private DirectoryEditorComposite directoryEditorComposite;
	
	private Log log = LogFactory.getLog(this.getClass());
	
	
	
	public OfficeApplicationPreferenceAdapter()
	{
		// die Preferencenodes fuer diesen Adapter
		instancePreferenceNode = InstanceScope.INSTANCE.getNode(OfficeConstants.ROOT_OFFICE_PREFERENCES_NODE);
		defaultPreferenceNode = DefaultScope.INSTANCE.getNode(OfficeConstants.ROOT_OFFICE_PREFERENCES_NODE);	
	}

	@Override
	public String getLabel()
	{	
		return Messages.OfficeApplicationPreferenceAdapter_ApplicationReferenceLabel;
	}

	@Override
	public String getNodePath()
	{
		return Messages.OfficeApplicationPreferenceAdapter_OfficeReferenceNode;
	}

	@Override
	public void restoreDefaultPressed()
	{
		String value = getDefaultPreference().get(OfficeConstants.OFFICE_APPLICATION_PREF, null);
		if(StringUtils.isNotEmpty(value))
			directoryEditorComposite.setDirectory(value);
	}

	@Override
	public void appliedPressed()
	{
		// LibreOffice-Installationspfad als Praeferenz speichern
		String value = directoryEditorComposite.getDirectory();
		if(StringUtils.isNotEmpty(value))
			instancePreferenceNode.put(OfficeConstants.OFFICE_APPLICATION_PREF, value);
		
		try
		{
			// JPIPE-Lib suchen und Ergebnis im PreferenceDialog anzeigen und als Preference speichern
			String jpipeDir = findJPIPELibDirectory(value);						
			jpipeDir = StringUtils.isNotEmpty(jpipeDir) ? jpipeDir : Messages.OfficeApplicationPreferenceAdapter_JPIPEnotFoundError; 
			comp.getJpipeDirectoryComposite().setDirectory(jpipeDir);
			
			// ist das Verzeichnis gueltig, wird es als Preference gespeichert
			if(!StringUtils.equals(jpipeDir, Messages.OfficeApplicationPreferenceAdapter_JPIPEnotFoundError))
					instancePreferenceNode.put(OfficeConstants.OFFICE_JPIPE_PREF, jpipeDir);
			else
				instancePreferenceNode.put(OfficeConstants.OFFICE_JPIPE_PREF, "");
			
			// den Preferenceknoten (alle Preferenzen dieses Konotens) speichern
			instancePreferenceNode.flush();
			
		} catch (BackingStoreException e)
		{
			log.error(e);			
		}
	}

	@Override
	public void okPressed()
	{
		appliedPressed();
		
		// wurde eine JPIPE-Lib gefunden, -vmargs -Djava.library.path im Launch-Configurationfile aktualisieren
		String libPath = comp.getJpipeDirectoryComposite().getDirectory();
		if(StringUtils.isNotEmpty(libPath) &&
				!StringUtils.equals(libPath, Messages.OfficeApplicationPreferenceAdapter_JPIPEnotFoundError))
		{
			// einen eventuell vorhandenen JPIPE-Eintrag sichen
			String jpipeEntry = findJPIPEConfigurationEntry();

			// den alten Eintrag gegen den neuen tauschen
			ChooseWorkspaceData wd = new ChooseWorkspaceData();
			wd.setJPIPEConfigurationEntry(jpipeEntry, libPath);

			// Neustart der Applikation abfragen
			if (MessageDialog.openQuestion(
					Display.getDefault().getActiveShell(), "Präferenzen", //$NON-NLS-1$
					"Die Änderung wird erst nach einem Neustart wirksam. \nJetzt Programm neustarten ?"))//$NON-NLS-1$
			{
				// fuer den Neustart die Workbench ueber den Preferencehandler ermitteln
				MApplication currentApplication = E4Workbench.getServiceContext().get(IWorkbench.class).getApplication();
				EModelService modelService = currentApplication.getContext().get(EModelService.class);		
				List<MHandler> handlers = modelService.findElements(currentApplication, null, MHandler.class,null);
				for(MHandler handler : handlers)
				{
					if(StringUtils.equals(handler.getElementId(), PreferenceHandler.PREFERENCEHANDLER_ID))
					{
						Object object = handler.getObject(); 
						if(object instanceof PreferenceHandler)
						{
							IWorkbench workbench = ((PreferenceHandler)object).getWorkbench();
							if(workbench != null)
								workbench.restart();
						}
					}
				}
			}
		}		
	}
	
	/*
	 * Sucht den vorhandenen JPIPE-Konfigurationseintrag, falls schon einer existiert.
	 * 
	 */
	private String findJPIPEConfigurationEntry()
	{
		ChooseWorkspaceData wd = new ChooseWorkspaceData();		
		String [] vmargs = wd.getConfigLibraryPaths();
		if(vmargs != null)
		{	
			for(String vmarg : vmargs)
			{
				File libDir = new File(vmarg);
				if(libDir.isDirectory())
				{					
					Iterator<File>itFiles = FileUtils.iterateFiles(libDir, null, false);
					for ( Iterator<File> iterator = itFiles; iterator.hasNext(); )
					{
						String libPath = iterator.next().getName();
						String baseName = FilenameUtils.getBaseName(libPath);
						if(StringUtils.contains(baseName, "jpipe"))
							return libDir.getPath();
					}						  
				}
			}
		}
		
		return null;
	}

	@Override
	public Composite createNodeComposite(IPreferenceNode parentNodeComposite)
	{
		// Titel der Applikationspreferenzen
		parentNodeComposite.setTitle(getLabel());
		
		// Verzeichnis indem die Application 'LibreOffice' installiert ist
		comp = new OfficeApplicationPreferenceComposite(
				parentNodeComposite.getParentNode(), SWT.NONE);		
		directoryEditorComposite = comp.getDirectoryEditorComposite();
		directoryEditorComposite.setLabel("Homeverzeichnis LibreOffice auswählen"); //$NON-NLS-1$
		
		String value = getInstancePreference().get(OfficeConstants.OFFICE_APPLICATION_PREF, null);
		if(StringUtils.isEmpty(value))
			value = getDefaultPreference().get(OfficeConstants.OFFICE_APPLICATION_PREF, null);		
		if(StringUtils.isNotEmpty(value))		
			directoryEditorComposite.setDirectory(value);

		value = getInstancePreference().get(OfficeConstants.OFFICE_JPIPE_PREF, null);
		if(StringUtils.isEmpty(value))
			value = getDefaultPreference().get(OfficeConstants.OFFICE_JPIPE_PREF, null);		
		if(StringUtils.isNotEmpty(value))		
			comp.getJpipeDirectoryComposite().setDirectory(value);

		
		return comp;
	}
	
	/*
	 * Wird in dem Verzeichnis 'libreOfficePath' eine jpipe-lib gefunden, so wird das Verzeichnis 'parent'
	 * dieser Datei zurueckgegeben.
	 * 
	 * null - wenn keine jpipe Bibliothek gefunden wurde 
	 */
	private String findJPIPELibDirectory(String libreOfficePath)
	{
		// Filerdefinition
		IOFileFilter jpipeFileFilterf = FileFilterUtils.asFileFilter(new FilenameFilter()
		{			
			@Override
			public boolean accept(File dir, String name)
			{
				String check  = FilenameUtils.getBaseName(name);					
				return (StringUtils.contains(check, "jpipe"));	//$NON-NLS-1$
			}
		});
			
		File libreOfficeDir = new File(libreOfficePath);
		if(libreOfficeDir.exists() && libreOfficeDir.isDirectory())
		{		
			List<File>libFiles =  FileFilterUtils.filterList(jpipeFileFilterf, libreOfficeDir.listFiles());
			return (libFiles.isEmpty() ? null : libFiles.get(0).getParent());
		}
		
		return null;
	}


}
