package it.naturtalent.e4.office.ui;


import it.naturtalent.application.IShowViewAdapterRepository;
import it.naturtalent.e4.office.IOfficeDocumentHandler;
import it.naturtalent.e4.office.IOfficeService;
import it.naturtalent.e4.office.OfficeConstants;
import it.naturtalent.e4.office.OpenDocumentUtils;
import it.naturtalent.e4.office.odf.ODFOfficeDocumentHandler;
import it.naturtalent.e4.office.ui.expimp.OfficeProfileExportAdapter;
import it.naturtalent.e4.office.ui.expimp.OfficeProfileImportAdapter;
import it.naturtalent.e4.office.ui.expimp.TextmoduleExportAdapter;
import it.naturtalent.e4.office.ui.expimp.TextmoduleImportAdapter;
import it.naturtalent.e4.preferences.IPreferenceRegistry;
import it.naturtalent.e4.project.IExportAdapterRepository;
import it.naturtalent.e4.project.IImportAdapterRepository;
import it.naturtalent.e4.project.INewActionAdapterRepository;
import it.naturtalent.e4.project.ui.DynamicNewMenu;
import it.naturtalent.e4.project.ui.DynamicOpenWithMenu;

import java.io.File;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.e4.core.commands.ECommandService;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.commands.MCommand;
import org.eclipse.e4.ui.workbench.IWorkbench;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;

public class OfficeProcessor
{
	private @Inject @Optional IImportAdapterRepository importAdapterRepository;
	private @Inject @Optional IExportAdapterRepository exportAdapterRepository;
	private @Inject @Optional IShowViewAdapterRepository showViewAdapterRepository;
	private @Optional @Inject INewActionAdapterRepository newWizardRepository;
	private @Inject @Optional IOfficeService officeService;
	private @Inject @Optional IPreferenceRegistry preferenceRegistry;
	
	private @Inject @Optional EPartService partService;
	private @Inject @Optional EModelService modelService;			
	private @Inject @Optional MApplication application;
	
	// Dynamic New
	public static final String NEW_OFFICE_MENUE_ID = "it.naturtalent.office.menue.createOfficeDocument"; //$NON-NLS-1$
	public static final String NEW_OFFICE_LABEL = "Office.NewLetterLabel"; //$NON-NLS-1$
	public static final String NEW_OFFICE_COMMAND_ID = "it.naturtalent.office.command.newOfficeDocument"; //$NON-NLS-1$

	public static final String NEW_CALC_MENUE_ID = "it.naturtalent.e4.office.menue.CalcDocument"; //$NON-NLS-1$
	public static final String NEW_CALC_LABEL = "Office.CalcLabel"; //$NON-NLS-1$
	public static final String NEW_CALC_COMMAND_ID = "it.naturtalent.office.command.openCalcDocument"; //$NON-NLS-1$

	public static final String NEW_DRAW_MENUE_ID = "it.naturtalent.e4.office.menue.DrawDocument"; //$NON-NLS-1$
	public static final String NEW_DRAW_LABEL = "Office.DrawLabel"; //$NON-NLS-1$
	public static final String NEW_DRAW_COMMAND_ID = "it.naturtalent.office.command.newDrawDocument"; //$NON-NLS-1$

	// Dynamic OpenWith
	public static final String OFFICE_OPENWITHMENUE_ID = "it.naturtalent.office.menu.openOfficeDocument"; //$NON-NLS-1$
	public static final String OFFICE_OPENWITHMENUE_LABEL = "Office.NewLetterLabel"; //$NON-NLS-1$
	public static final String OFFICE_OPENWITHCOMMAND_ID = "it.naturtalent.office.command.openOfficeDocument"; //$NON-NLS-1$

	public static final String OPEN_ODFDRAW_WITHMENUE_ID = "it.naturtalent.office.menu.openOfficeDrawDocument"; //$NON-NLS-1$
	public static final String OPEN_ODFDRAW_WITHMENUE_LABEL = "Office.DrawLabel"; //$NON-NLS-1$
	public static final String OPEN_ODFDRAW_WITHCOMMAND_ID = "it.naturtalent.office.command.openDrawDocument"; //$NON-NLS-1$

	private IWorkbench workbench;
	
	@Execute
	void init (IEventBroker eventBroker, IEclipseContext context)
	{	
		String label;
		DynamicNewMenu newMenu = new DynamicNewMenu();
		
		List<MCommand>commands = application.getCommands();
		for(MCommand command : commands)
		{
			// Dynamic New			
			if(StringUtils.equals(command.getElementId(),NEW_OFFICE_COMMAND_ID))
			{						
				label = Activator.properties.getProperty(NEW_OFFICE_LABEL);											
				newMenu.addHandledDynamicItem(NEW_OFFICE_MENUE_ID,label,command,5);				
			}

			if(StringUtils.equals(command.getElementId(),NEW_CALC_COMMAND_ID))
			{						
				label = Activator.properties.getProperty(NEW_CALC_LABEL);											
				newMenu.addHandledDynamicItem(NEW_CALC_MENUE_ID,label,command,4);				
			}

			
			if(StringUtils.equals(command.getElementId(),NEW_DRAW_COMMAND_ID))
			{						
				label = Activator.properties.getProperty(NEW_DRAW_LABEL);											
				newMenu.addHandledDynamicItem(NEW_DRAW_MENUE_ID,label,command,3);				
			}
			

			// Dynamic OpenWith
			if(StringUtils.equals(command.getElementId(),OFFICE_OPENWITHCOMMAND_ID))
			{		
				label = Activator.properties.getProperty(OFFICE_OPENWITHMENUE_LABEL);			
				DynamicOpenWithMenu openWithMenu = new DynamicOpenWithMenu();				
				openWithMenu.addHandledDynamicItem(OFFICE_OPENWITHMENUE_ID,label,command,0);
			}
			
			if(StringUtils.equals(command.getElementId(),OPEN_ODFDRAW_WITHCOMMAND_ID))
			{		
				label = Activator.properties.getProperty(OPEN_ODFDRAW_WITHMENUE_LABEL);			
				DynamicOpenWithMenu openWithMenu = new DynamicOpenWithMenu();				
				openWithMenu.addHandledDynamicItem(OPEN_ODFDRAW_WITHMENUE_ID,label,command,0);								
			}
			
			
			this.workbench = null;

		}
		
		/* 
		 * Defaultpreferences LibreOffice
		 * 
		 */
		IEclipsePreferences defaultPreferences = DefaultScope.INSTANCE.getNode(OfficeConstants.ROOT_OFFICE_PREFERENCES_NODE);
	
		if (SystemUtils.IS_OS_LINUX)
		{
			// Linux speichert Libreoffice in einem definierten Speicher (OfficeConstants.LINUX_UNO_PATH)
			defaultPreferences.put(OfficeConstants.OFFICE_APPLICATION_PREF,OfficeConstants.LINUX_UNO_PATH);
			
			// Default JPIPE-Verzeichnis
			String check = OfficeApplicationPreferenceAdapter.findJPIPELibDirectory(OfficeConstants.LINUX_UNO_PATH);
			if(StringUtils.isNotEmpty(check))
				defaultPreferences.put(OfficeConstants.OFFICE_JPIPE_PREF,check);
			
			// Default UNO-Verzeichnis (wird exemplarisch mit der Komponente "jurt" gesucht)
			check = OfficeApplicationPreferenceAdapter.findUNOLibraryPath("jurt");
			if(StringUtils.isNotEmpty(check))
				defaultPreferences.put(OfficeConstants.OFFICE_UNO_PREF,check);
		}
		else
		{
			// in Windows gibt es kein definiertes Verzeicnis für Libreoffice, somit auch kein Defaultverzeicnis 
		}
		
		
		
		/*
		String officApplicationPath = defaultPreferences.get(ODFOfficeDocumentHandler.OFFICE_APPLICATION_PREF, null);
		if(StringUtils.isEmpty(officApplicationPath))
		{			
			String lib = getOfficeLibraryDefinition();
			if(StringUtils.isNotEmpty(lib))
			{
				defaultPreferences.put(ODFOfficeDocumentHandler.OFFICE_APPLICATION_PREF,lib);
			}
			else
			{
				if (SystemUtils.IS_OS_LINUX)
					defaultPreferences.put(
							ODFOfficeDocumentHandler.OFFICE_APPLICATION_PREF,
							OfficeConstants.LINUX_UNO_PATH);
				else
				{
					// Nt-Systemverzeichnis der Programme
					String key = it.naturtalent.application.Activator.NT_PROGRAM_HOME;
					String extProgsDir = System.getProperty(key);
					String unoPath = extProgsDir + OfficeConstants.WINDOWS_UNO_PATH;

					// existiert die Officeapplication im Systemverzeichnis
					File check = new File(unoPath);
					if (check.exists())
						defaultPreferences.put(ODFOfficeDocumentHandler.OFFICE_APPLICATION_PREF,unoPath);
				}
			}
		}
		*/
		
		if(preferenceRegistry != null)
		{			
			preferenceRegistry.getPreferenceAdapters().add(new OfficeApplicationPreferenceAdapter());
		}
		
		/*
		 *  Templates registrieren
		 * 
		 * 
		 */
		
		// Zugriff auf die plugin Templates (SRC-Dir)			
		File pluginTemplateDir = OpenDocumentUtils.getPluginTemplateDir(this.getClass());
		
		// die Plugin-Templates registrieren
		IOFileFilter suffixFilter = FileFilterUtils.or(FileFilterUtils
				.suffixFileFilter(IOfficeDocumentHandler.ODF_OFFICETEXTDOCUMENT_EXTENSION),FileFilterUtils
				.suffixFileFilter(IOfficeDocumentHandler.MS_OFFICETEXTDOCUMENT_EXTENSION));
		Collection<File> templates = FileUtils.listFiles(pluginTemplateDir, suffixFilter, null);
		if (!templates.isEmpty())
		{
			for(Iterator<File>templateIt = templates.iterator();templateIt.hasNext();)
			{
				OpenDocumentUtils.registerPluginLetterTemplate(templateIt.next(),
						IOfficeService.NTOFFICE_CONTEXT, officeService);
			}			
		}

		if(importAdapterRepository != null)
		{
			importAdapterRepository.addImportAdapter(new TextmoduleImportAdapter());
			importAdapterRepository.addImportAdapter(new OfficeProfileImportAdapter());
		}
				
		if(exportAdapterRepository != null)
		{
			exportAdapterRepository.addExportAdapter(new TextmoduleExportAdapter());
			exportAdapterRepository.addExportAdapter(new OfficeProfileExportAdapter());
		}

		if(showViewAdapterRepository != null)
			showViewAdapterRepository.addShowViewAdapter(new TextmoduleShowViewAdapter());
		
		if(newWizardRepository != null)
			newWizardRepository.addNewActionAdapter(new NewLetterAdapter());
			
	}
	
	/*
	 * In der 'ini'-Datei wird der Pfad definiert (LibraryPath), in dem der fuer die Office-Pipe notwendige 
	 * JPIPE gespeichet ist.
	 * Muesse mherere Library Pfade definiert werden, muss diese Funktion dahingehend aktualisiert werden, dass nur 
	 * der Pfad fuer 'JPIPE' zureuckgegeben wird. 
	 * 
	 */
	private String getOfficeLibraryDefinition()
	{
		String javaLib = SystemUtils.JAVA_LIBRARY_PATH;
		
		// spaeter ggf. aktualisieren
		
		return javaLib;
	}
	

}
