 
package it.naturtalent.e4.office.ui.handlers;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.odftoolkit.odfdom.dom.element.meta.MetaUserDefinedElement;
import org.odftoolkit.simple.TextDocument;
//import org.odftoolkit.simple.TextDocument;
import org.odftoolkit.simple.meta.Meta;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

import it.naturtalent.e4.office.ui.Activator;
import it.naturtalent.e4.office.ui.IODFWriteAdapter;
import it.naturtalent.e4.office.ui.IODFWriteAdapterFactory;
import it.naturtalent.e4.office.ui.IODFWriteAdapterFactoryRepository;
import it.naturtalent.e4.office.ui.wizards.ODFDefaultWriteAdapterWizard;
import it.naturtalent.e4.project.ui.navigator.ResourceNavigator;
import it.naturtalent.libreoffice.OpenLoDocument;


/**
 * Ein Text (LibreOffice Anschreiben) mit Hilfe des zugeordneten Adapters oeffnen.
 * Der zugehoerige Adapter wird ueber eine AdapterFactory erzeugt.
 * Der Klassenamen der AdapterFatory e.B. 'it.naturtalent.telekom.office.ODFTelekomWriteAdapterFactory' 
 * muss im zuoeffenden Dokument gespeichert sein. 
 * 
 * @see NewTextHandle() 
 * 
 * @author dieter
 *
 */
public class OpenTextHandler
{
	@Inject @Optional private ESelectionService selectionService;
	@Inject @Optional private IODFWriteAdapterFactoryRepository writeAdapterFactoryRepository;
	
	// Basisname der neuen Textdatei (Zieldatei)
	public static final String ODFTEXT_FILENAME_EXTENSION = "odt"; //$NON-NLS-1$
	
	// Template der neuen Tabelle benutze Vorlage (Quelle)  
	private static final String ODFTEXT_TEMPLATE = "/templates/ODFText.odt"; //$NON-NLS-1$
	
	private WizardDialog wizardDialog;
	
	@Execute
	public void execute(IEventBroker eventBroker,
			@Named(IServiceConstants.ACTIVE_SHELL) Shell shell, IEclipseContext context)
	{
		Object selObject = selectionService.getSelection(ResourceNavigator.RESOURCE_NAVIGATOR_ID);
		
		// WriteAdapterFactory-Name ermitteln
		String factoryName = getAdapterFactoryName(selObject);	
		if(StringUtils.isNotEmpty(factoryName))
		{
			// Factory ueber den Namen aus dem Repository laden  
			IODFWriteAdapterFactory writeAdapterFactory = writeAdapterFactoryRepository
					.getWriteAdapter(factoryName);
			if(writeAdapterFactory != null)
			{
				// den eigentlichen Adapter durch die Factory erzeugen
				IODFWriteAdapter writeAdapter = writeAdapterFactory.createAdapter();
				
				// der Adapter wiederum erzeugt den dokumentspezifischen Wizard
				context.set(ODFDefaultWriteAdapterWizard.CONTEXTWIZARDMODE, ODFDefaultWriteAdapterWizard.WIZARDOPENMODE);
				wizardDialog = new WizardDialog(shell,writeAdapter.createWizard(context));
				
				// die im ResourceNavigator selektierte Datei
				IResource iResource = (IResource) selObject;
				if (iResource.getType() == IResource.FILE)
				{
					// Broker sendet Dokument Location 'IFile' an den Wizard
					// @see ODFDefaultWriteAdapterWizard
					IWorkspace workspace = ResourcesPlugin.getWorkspace();		
					IFile ifile = workspace.getRoot().getFileForLocation(iResource.getLocation());
					eventBroker.post(IODFWriteAdapter.ODFWRITE_FILEDEFINITIONEVENT,ifile);

					// den Wizard oeffnen und Dokument editieren
					try
					{
						if(wizardDialog.open() == WizardDialog.OK)
						{					
							// Dokument in LibreOffice oeffnen
							File file = ifile.getLocation().toFile();
							OpenLoDocument.loadLoDocument(file.toString());							
						}
					} catch (Exception e)
					{
						MessageDialog.openError(shell, "WriteWizard", "kein Wizard verfügbar");
					}
				}
			}
			else
			{			
				// der erforderliche Adapter ist nicht im Repository gespeichert
				MessageDialog.openError(shell, "WriteWizard",
						"der Wizard '" + factoryName + "' ist nicht verfügbar");
			}
		}
		
		if (selObject instanceof IResource)
		{		
			
			/*
			// einen Adapter auswaehlen
			SelectWriteAdapterDialog adapterDialog = new SelectWriteAdapterDialog(shell);
			adapterDialog.create();
			adapterDialog.setAdapter(writeAdapterFactoryRepository.getWriteAdapterFactories());
			if (adapterDialog.open() == SelectWriteAdapterDialog.OK)
			{
				// mit dem ausgewaehlten Adapter weiterarbeiten
				IODFWriteAdapter writeAdapter = adapterDialog.getSelectedAdapter();
				if(writeAdapter != null)
				{
					// ODF-Datei erzeugen
					IResource iResource = (IResource) selObject;
					if (iResource.getType() == IResource.FILE)
						iResource = iResource.getParent();					
					IPath path = iResource.getLocation();					
					File newODF = writeAdapter.createODF(path.toFile());
					
					//WizardDialog wizardDialog = new WizardDialog(shell,writeAdapter.createWizard());					
					//wizardDialog.open();
					
					//writeAdapter.openODF(newODF);

					// Refresh IContainer (neue Datei im ResourceNavigator anzeigen)
					RefreshResource refreshResource = new RefreshResource();
					refreshResource.refresh(shell, iResource);
					
					// neue Datei im ResourceNavigator anzeigen
					IWorkspace workspace = ResourcesPlugin.getWorkspace();				
					IPath location = Path.fromOSString(newODF.getAbsolutePath());
					IFile ifile = workspace.getRoot().getFileForLocation(location);
					eventBroker.post(IResourceNavigator.NAVIGATOR_EVENT_SELECT_REQUEST,ifile);
					
					// Wizard Daten des Anschreibes 
					WizardDialog wizardDialog = new WizardDialog(shell,writeAdapter.createWizard(context));
					eventBroker.post(IODFWriteAdapter.ODFWRITE_FILEDEFINITIONEVENT,ifile);
					wizardDialog.open();

				}
			}
			*/
		}
		
	}
	
	/*
	@Inject
	@Optional
	public void handleModelChangedEvent(@UIEventTopic(it.naturtalent.libreoffice.text.TextDocument.TEXTDOCUMENT_EVENT_DOCUMENT_OPEN) Object textDocument)
	{		
		// den adapterspezifischen Wizard oeffnen
		if(wizardDialog.open() == WizardDialog.OK)
		{					
		
		}		
	}
	*/

	/**
	 * true, wenn in dem selektierte WriteDocument ein Adapterklassenname gespeichert ist und der Adapter auch im
	 * Registry gespeichert ist.
	 * (moeglicherweise zu zeitaufwaendig) 
	 * 
	 * @return
	 */
	@CanExecute
	public boolean canExecute()
	{
		Object selObject = selectionService.getSelection(ResourceNavigator.RESOURCE_NAVIGATOR_ID);
		String factoryName = getAdapterFactoryName(selObject);
		IODFWriteAdapterFactory writeAdapterFactory = writeAdapterFactoryRepository.getWriteAdapter(factoryName);
		return (writeAdapterFactory != null);
	}
	
	/*
	 * WriteAdapterFactoryName aus dem Dokument lesen
	 */
	private String getAdapterFactoryName (Object selObject)
	{		
		if (selObject instanceof IResource)
		{
			IResource iResource = (IResource) selObject;
			if (iResource.getType() == IResource.FILE)
			{
				IPath path = iResource.getLocation();					
				File writeFile = path.toFile();
				String extension = FilenameUtils.getExtension(writeFile.getName());
				if(StringUtils.equals(extension, ODFTEXT_FILENAME_EXTENSION))
				{
					TextDocument odfDocument;
					try
					{
						// der FactoryName ist als MetaData im Dokument gespeichert
						odfDocument = TextDocument.loadDocument(writeFile);
						Meta meta = odfDocument.getOfficeMetadata();
						MetaUserDefinedElement element = meta
								.getUserDefinedElementByAttributeName(
										IODFWriteAdapter.ODFADAPTERFACTORY);
						if(element != null)					
							return element.getTextContent();

					} catch (Exception e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}			
		}
		
		return null;
	}
	
	/*
	 * Kopiert die DrawTemplateDatei in das Zielverzeichnis.
	 */
	public static void createDrawFile(File destDrawFile)
	{
		Bundle bundle = FrameworkUtil.getBundle(Activator.class);
		BundleContext bundleContext = bundle.getBundleContext();
		URL urlTemplate = FileLocator.find(bundleContext.getBundle(),new Path(ODFTEXT_TEMPLATE), null);
		try
		{
			urlTemplate = FileLocator.resolve(urlTemplate);
			try
			{				
				FileUtils.copyURLToFile(urlTemplate, destDrawFile);				
			} catch (IOException e)
			{							
				//log.error(Messages.DesignUtils_ErrorCreateDrawFile);
				e.printStackTrace();
			}
			
		} catch (IOException e1)
		{						
			//log.error(Messages.DesignUtils_ErrorCreateDrawFile);
			e1.printStackTrace();
		}
	}
		
}