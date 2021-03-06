package it.naturtalent.e4.office.ui;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.commands.MCommand;
import org.eclipse.e4.ui.workbench.IWorkbench;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

import it.naturtalent.application.IShowViewAdapterRepository;
import it.naturtalent.application.services.IOpenWithEditorAdapterRepository;
import it.naturtalent.e4.office.ui.preferences.OfficeAbsenderPreferenceAdapter;
import it.naturtalent.e4.office.ui.preferences.OfficeFootNotePreferenceAdapter;
import it.naturtalent.e4.office.ui.preferences.OfficeReferenzPreferenceAdapter;
import it.naturtalent.e4.office.ui.preferences.OfficeSigaturePreferenceAdapter;
import it.naturtalent.e4.office.ui.preferences.OfficeTemplatePreferenceAdapter;
import it.naturtalent.e4.preferences.IPreferenceRegistry;
import it.naturtalent.e4.project.IExportAdapterRepository;
import it.naturtalent.e4.project.IImportAdapterRepository;
import it.naturtalent.e4.project.INewActionAdapterRepository;
import it.naturtalent.e4.project.INtProjectPropertyFactory;
import it.naturtalent.e4.project.INtProjectPropertyFactoryRepository;
import it.naturtalent.e4.project.ui.DynamicNewMenu;


/**
 * Initialisierung officespezfischer Funktionen.
 * 
 * @author dieter
 *
 */
public class OfficeProcessor
{

	private @Inject @Optional IODFWriteAdapterFactoryRepository writeAdapterFactoryRepository;
	@Inject @Optional
	private IOpenWithEditorAdapterRepository openwithAdapterRepository;
	
	// das zentrale ProjectPropertyRepository
	private @Inject INtProjectPropertyFactoryRepository ntProjektDataFactoryRepository;
	
	private @Inject @Optional IImportAdapterRepository importAdapterRepository;
	private @Inject @Optional IExportAdapterRepository exportAdapterRepository;
	private @Inject @Optional IShowViewAdapterRepository showViewAdapterRepository;
	private @Optional @Inject INewActionAdapterRepository newWizardRepository;
	private @Inject @Optional IPreferenceRegistry preferenceRegistry;
	
	private @Inject @Optional EPartService partService;
	private @Inject @Optional EModelService modelService;			
	private @Inject @Optional MApplication application;
	
	
	private static final int OFFICE_MENUE_POSITION = 5;
	
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
		
		// ein Defaultanschreiben-FactoryAdapter im Factory-Registry eintragen
		if(writeAdapterFactoryRepository != null)
			writeAdapterFactoryRepository.getWriteAdapterFactories()
					.add(new DefaultWriteAdapterFactory());
		
		// Praeferenceadapter in das Repository eintragen
		if(preferenceRegistry != null)	
		{
			preferenceRegistry.getPreferenceAdapters().add(new OfficeTemplatePreferenceAdapter());
			preferenceRegistry.getPreferenceAdapters().add(new OfficeAbsenderPreferenceAdapter());
			preferenceRegistry.getPreferenceAdapters().add(new OfficeReferenzPreferenceAdapter());
			preferenceRegistry.getPreferenceAdapters().add(new OfficeFootNotePreferenceAdapter());
			preferenceRegistry.getPreferenceAdapters().add(new OfficeSigaturePreferenceAdapter());
		}
		
		// WorkspaceDir in dem die Vorlagen gespeichert werden (deprecated) 
		File destOfficeWorkspaceDir = new File(
				ResourcesPlugin.getWorkspace().getRoot().getLocation().toFile(),
				it.naturtalent.e4.office.ui.Activator.OFFICEDATADIR + File.separator + ODFDefaultWriteAdapter.ODFTEXT_TEMPLATE_DIRECTORY);

		if(!destOfficeWorkspaceDir.exists())
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

				// die hardcoded Vorlagen werden kopiert
				FileUtils.copyDirectory(FileUtils.toFile(urlPluginTemplate),destOfficeWorkspaceDir);
				
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}

		
		
		// kopiert die Default Vorlagen vom PlugIn Verzeichnis 'templates' in das Workspace-Vorlagenverzeichnis
		/*
		Bundle bundle = FrameworkUtil.getBundle(this.getClass());
		BundleContext bundleContext = bundle.getBundleContext();
		URL urlPluginTemplate = FileLocator.find(bundleContext.getBundle(),new Path(TEMPLATES_DIR), null);
		try
		{
			// Quelle ist PlugIn 'templates' - Dir
			urlPluginTemplate = FileLocator.resolve(urlPluginTemplate);
				
			// Ziel ist WorkspaceDir mit den Telekomvorlagen 
			File destOfficeWorkspaceDir = new File(
					ResourcesPlugin.getWorkspace().getRoot().getLocation().toFile(),
					it.naturtalent.e4.office.ui.Activator.OFFICEDATADIR + File.separator + TEMPLATES_DIR);
			
			// Defaultvorlagen kopieren
			FileUtils.copyDirectory(FileUtils.toFile(urlPluginTemplate), destOfficeWorkspaceDir);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		*/

		
		// Kontakte Projectproperties
		List<INtProjectPropertyFactory>ntPropertyFactories = ntProjektDataFactoryRepository.getAllProjektDataFactories();
		ntPropertyFactories.add(new KontakteProjectPropertyFactory());

		// 'Open With' Menue einer Textdatei
		openwithAdapterRepository.getOpenWithAdapters().add(new ODFWriteOpenWithAdapter());
		
		DynamicNewMenu newMenu = new DynamicNewMenu();

		List<MCommand>commands = application.getCommands();
		for(MCommand command : commands)
		{
			
			// Dynamic New			
			if(StringUtils.equals(command.getElementId(),NEW_OFFICE_COMMAND_ID))
			{						
				label = Activator.properties.getProperty(NEW_OFFICE_LABEL);											
				newMenu.addHandledDynamicItem(NEW_OFFICE_MENUE_ID,label,command,6);
				continue;
			}
		}
		
		
		
		/*
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
		*/
		
		
		/*
		 *  Templates registrieren
		 * 
		 * 
		 */
		
		/*
		
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
		*/

		if(importAdapterRepository != null)
		{
			importAdapterRepository.addImportAdapter(new ImportKontakteAdapter());
			importAdapterRepository.addImportAdapter(new ImportCSVKontakteAdapter());
			//importAdapterRepository.addImportAdapter(new TextmoduleImportAdapter());
			//importAdapterRepository.addImportAdapter(new OfficeProfileImportAdapter());
		}
				
		if(exportAdapterRepository != null)
		{
			exportAdapterRepository.addExportAdapter(new ExportKontakteAdapter());
			exportAdapterRepository.addExportAdapter(new JournalProjectExportAdapter());			
			exportAdapterRepository.addExportAdapter(new JournalKontaktExportAdapter());
		}

		if(showViewAdapterRepository != null)
		{
			showViewAdapterRepository.addShowViewAdapter(new ShowKontakteViewAdapter());
			//showViewAdapterRepository.addShowViewAdapter(new TextmoduleShowViewAdapter());
		}
		
		/*
		if(newWizardRepository != null)
			newWizardRepository.addNewActionAdapter(new NewLetterAdapter());
			*/
			
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
