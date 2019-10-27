 
package it.naturtalent.e4.office.ui.handlers;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.eclipse.core.resources.IContainer;
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
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

import it.naturtalent.e4.office.ui.Activator;
import it.naturtalent.e4.office.ui.IODFWriteAdapter;
import it.naturtalent.e4.office.ui.IODFWriteAdapterFactoryRepository;
import it.naturtalent.e4.office.ui.dialogs.SelectWriteAdapterDialog;
import it.naturtalent.e4.office.ui.wizards.ODFDefaultWriteAdapterWizard;
import it.naturtalent.e4.project.IResourceNavigator;
import it.naturtalent.e4.project.ui.navigator.ResourceNavigator;
import it.naturtalent.e4.project.ui.utils.RefreshResource;
import it.naturtalent.libreoffice.OpenLoDocument;

/**
 * Ein neues Anschreiben mit einem auszuwaehlenden Adapter erstellen.
 * Die verfuegbaren Adapter sind ueber ihre Factories (alle im Registry 'writeAdapterFactoryRepository') gespeichert. 
 * 
 * @author dieter
 *
 */
public class NewTextHandle
{
	@Inject @Optional private ESelectionService selectionService;
	@Inject @Optional private IODFWriteAdapterFactoryRepository writeAdapterFactoryRepository;
	
	// Basisname der neuen Textdatei (Zieldatei)
	public static final String ODFTEXT_FILENAME = "text.odt"; //$NON-NLS-1$
	
	// Template der neuen Tabelle benutze Vorlage (Quelle)  
	private static final String ODFTEXT_TEMPLATE = "/templates/ODFText.odt"; //$NON-NLS-1$
	
	@Execute
	public void execute(IEventBroker eventBroker,
			@Named(IServiceConstants.ACTIVE_SHELL) Shell shell, IEclipseContext context)
	{
		Object selObject = selectionService.getSelection(ResourceNavigator.RESOURCE_NAVIGATOR_ID);
		if (selObject instanceof IResource)
		{		
			// einen Adapter mit einem Dialog auswaehlen
			SelectWriteAdapterDialog adapterDialog = new SelectWriteAdapterDialog(shell);
			adapterDialog.create();
			adapterDialog.setAdapter(writeAdapterFactoryRepository.getWriteAdapterFactories());
			if (adapterDialog.open() == SelectWriteAdapterDialog.OK)
			{
				// mit dem ausgewaehlten Adapter weiterarbeiten
				IODFWriteAdapter writeAdapter = adapterDialog.getSelectedAdapter();
				if(writeAdapter != null)
				{
					// ODF-Datei erzeugen (createODF-Auftuf im ausgewahlten Wizard)
					IResource iResource = (IResource) selObject;
					if (iResource.getType() == IResource.FILE)
						iResource = iResource.getParent();					
					IPath path = iResource.getLocation();					
					File newODF = writeAdapter.createODF(path.toFile());
					
					if (newODF != null)
					{
						// Refresh IContainer (neue Datei im ResourceNavigator
						// anzeigen)
						RefreshResource refreshResource = new RefreshResource();
						refreshResource.refresh(shell, iResource);

						// neue Datei im ResourceNavigator anzeigen
						IWorkspace workspace = ResourcesPlugin.getWorkspace();
						IPath location = Path.fromOSString(newODF.getAbsolutePath());
						IFile ifile = workspace.getRoot().getFileForLocation(location);
						eventBroker.post(IResourceNavigator.NAVIGATOR_EVENT_SELECT_REQUEST,ifile);

						// Wizard Daten zu dem Anschreibes abfragen
						// der Adapter wiederum erzeugt den dokumentspezifischen
						// Wizard
						context.set(
								ODFDefaultWriteAdapterWizard.E4CONTEXT_WIZARDMODE,
								ODFDefaultWriteAdapterWizard.WIZARDCREATEMODE);
						
						WizardDialog wizardDialog = new WizardDialog(shell,
								writeAdapter.createWizard(context));
						
						eventBroker.post(IODFWriteAdapter.ODFWRITE_FILEDEFINITIONEVENT,ifile);
						if (wizardDialog.open() == WizardDialog.OK)
						{
							// Dokument in LibreOffice oeffnen
							String filePath;
							try
							{
								filePath = FileUtils
										.toFile(ifile.getLocationURI().toURL())
										.getPath();
								OpenLoDocument.loadLoDocument(filePath);
							} catch (Exception e)
							{
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				}
			}
		}
		
	}

	@CanExecute
	public boolean canExecute()
	{
		return (selectionService.getSelection(
				ResourceNavigator.RESOURCE_NAVIGATOR_ID) instanceof IResource);
	}
	
	/*
	 * Kopiert die Vorlage in das Zielverzeichnis.
	 */
	public static void createWriteFile(File destDirectory)
	{
		Bundle bundle = FrameworkUtil.getBundle(Activator.class);
		BundleContext bundleContext = bundle.getBundleContext();
		URL urlTemplate = FileLocator.find(bundleContext.getBundle(),new Path(ODFTEXT_TEMPLATE), null);
		try
		{
			urlTemplate = FileLocator.resolve(urlTemplate);
			try
			{				
				FileUtils.copyURLToFile(urlTemplate, destDirectory);				
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

	
	/*
	 * 
	 */
	private String getAutoFileName(IContainer dir, String originalFileName)
	{
		String autoFileName;

		if (dir == null)
			return "";

		int counter = 1;
		while (true)
		{
			if (counter > 1)
			{
				autoFileName = FilenameUtils.getBaseName(originalFileName)
						+ new Integer(counter) + "."
						+ FilenameUtils.getExtension(originalFileName);
			}
			else
			{
				autoFileName = originalFileName;
			}

			IResource res = dir.findMember(autoFileName);
			if (res == null)
				return autoFileName;

			counter++;
		}
	}
		
}