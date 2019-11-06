package it.naturtalent.e4.office.ui;

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
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.internal.workbench.E4Workbench;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.swt.widgets.Display;
import org.odftoolkit.simple.TextDocument;
import org.odftoolkit.simple.meta.Meta;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

import it.naturtalent.e4.office.ui.dialogs.ODFSelectVorlagenDialog;
import it.naturtalent.e4.office.ui.wizards.ODFDefaultWriteAdapterWizard;

/**
 * Diese Klasse implementiert einen Anschreiben-Adapter der obligatorisch zur Verfuegung steht.
 * 
 * Der mit diesem Adapter erzeugte Wizard ermoeglicht das Handling mit dem ODFTextDokument
 * 
 * @author dieter
 *
 */
public class ODFDefaultWriteAdapter implements IODFWriteAdapter
{
	
	public static final String OFFICEWRITEDOCUMENT_EXTENSION = "odt"; //$NON-NLS-N$
	
	// in diesem Verzeichnis befinden sich die Default Vorlagen
	public static final String PLUGIN_TEMPLATES_DIR = "templates"; //$NON-NLS-1$


	// Subdirectory der Vorlagen relativ zu Workspace/OFFICEDATADIR 
	public static final String ODFTEXT_TEMPLATE_DIRECTORY = "templates"; //$NON-NLS-1$

	// Default Template Name
	public static final String ODFTEXT_TEMPLATE_NAME = "ODFText"; //$NON-NLS-1$

	// ODF - Dokument
	private TextDocument odfDocument;
	
	
	 
	/*
	 * Wizard, mit dem Eintragungen (Adresse, Absender etc.) 'von aussen' im Anschreiben  vorgenommen werden koennen.
	 *  
	 */
	@Override
	public IWizard createWizard(IEclipseContext context)
	{				
		return ContextInjectionFactory.make(ODFDefaultWriteAdapterWizard.class, context);
	}

	/* 
	 * Ein Anschreiben (TextDokument) erstellen.
	 * 
	 * Eine neue Datei wird 'erzeugt' durch kopieren einer Anschreiben - Vorlage in das Zielverzichnis.
	 * Mit 'getAutoFileName()' wird verhndert, dass ein bereits besthender Dateiname verwendet wird.
	 * 
	 */
	@Override
	public File createODF(File destDir)
	{
		File newFile = null;
		
		if(destDir.isDirectory())
		{
			// neues Dokument als Kopie der Vorlage erzeugen
			newFile = createODFFile(destDir);			
			if (newFile != null)
			{
				try
				{
					// die Factoryklassname des Adapters als Property im neuen ODF-Dokument speichern 
					TextDocument odfDocument = TextDocument.loadDocument(newFile);
					Meta meta = odfDocument.getOfficeMetadata();
					meta.setUserDefinedData(ODFADAPTERFACTORY, "Text",
							DefaultWriteAdapterFactory.class.getName()); // $NON-NLS-N$
					odfDocument.save(newFile);
					
					// das neue ODF-Dokument im E4Context hinterlegen
					E4Workbench.getServiceContext().set(OfficeUtils.E4CONTEXTKEY_CREATED_ODFDOCUMENT, odfDocument);
					
				} catch (Exception e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}			
		}		
		return newFile;
	}

	/*
	 * Vorlage kopieren
	 */
	private File createODFFile(File destDir)
	{		
		// Vorlage im Dialog auswaehlen
		ODFSelectVorlagenDialog selectLayoutDialog = new ODFSelectVorlagenDialog(Display.getDefault().getActiveShell());
		if(selectLayoutDialog.open() == ODFSelectVorlagenDialog.OK)
		{
			// Name der Vorlage wird 'Basis'-Name der neuen Datei
			String baseName = selectLayoutDialog.getSelectedName();
			
			// neue Datei im Zielverzeichnis erzeugen
			// sicherstellen, dass kein bereits vorhandener Name benutzt wird
			//String newFileName = getAutoFileName(destDir,BASE_ODFTEXT_FILENAME);
			String newFileName = getAutoFileName(destDir, baseName+"."+ODFDefaultWriteAdapter.OFFICEWRITEDOCUMENT_EXTENSION);
			File newFile = new File(destDir, newFileName);
			
			// die selektierte Vorlage in die neue Datei kopieren
			String vorlagenName = selectLayoutDialog.getSelectedName();
			
			// Verzeichnis mit den Vorlagen
			File destOfficeWorkspaceDir = new File(ResourcesPlugin.getWorkspace().getRoot().getLocation().toFile(),
					it.naturtalent.e4.office.ui.Activator.OFFICEDATADIR + File.separator + ODFDefaultWriteAdapter.ODFTEXT_TEMPLATE_DIRECTORY);			
			File destOfficeWorkspaceFile = new File(destOfficeWorkspaceDir,vorlagenName + "."
							+ ODFDefaultWriteAdapter.OFFICEWRITEDOCUMENT_EXTENSION);
			
			try
			{
				// Vorlage in 'newODFFile' kopieren				
				FileUtils.copyFile(destOfficeWorkspaceFile, newFile);
				return newFile;
				
			} catch (IOException e1)
			{						
				//log.error(Messages.DesignUtils_ErrorCreateDrawFile);
				e1.printStackTrace();
			}			
		}
		return null;
	}

	/*
	 * ein bereits existierendes TextDokument oeffnen
	 * @see it.naturtalent.e4.office.ui.IODFWriteAdapter#openODF(java.io.File)
	 */
	@Override
	public void openODF(File odfFile)
	{
		try
		{
			odfDocument = TextDocument.loadDocument(odfFile);
			
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void closeODF()
	{
		// TODO Auto-generated method stub
		
	}
	
	/*
	 * Durch 'Hochzaehlen' von Dateienamen wird Namensgleichheit verhindert.
	 */
	protected String getAutoFileName(File destDir, String originalFileName)
	{
		String autoFileName;

		if (destDir == null)
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

			File res = new File(destDir, autoFileName);
			if (!res.exists())
				return autoFileName;

			counter++;
		}
	}
	
	/*
	 * 
	 */
	public static List<String> readTemplateNames()
	{
		List<String>templateNames = new ArrayList<String>();
		
		IOFileFilter suffixFilter = FileFilterUtils.or(FileFilterUtils
				.suffixFileFilter(OFFICEWRITEDOCUMENT_EXTENSION));
		Collection<File> tempFiles = FileUtils.listFiles(getWorkspaceTemplateDirectory(),suffixFilter,null);
					
		for(File tmpFile : tempFiles)
			templateNames.add(FilenameUtils.getBaseName(tmpFile.getPath()));
		
		return templateNames;
	}	
	
	/*
	public static List<String> readTemplateNames()
	{
		List<String>templateNames = new ArrayList<String>();
		
		Bundle bundle = FrameworkUtil.getBundle(Activator.class);
		BundleContext bundleContext = bundle.getBundleContext();
		URL urlTemplate = FileLocator.find(bundleContext.getBundle(),new Path(ODFTEXT_TEMPLATE_DIRECTORY), null);
		
		try
		{
			urlTemplate = FileLocator.resolve(urlTemplate);
			File tempDir = FileUtils.toFile(urlTemplate);
			
			IOFileFilter suffixFilter = FileFilterUtils.or(FileFilterUtils
					.suffixFileFilter(OFFICEWRITEDOCUMENT_EXTENSION));
			Collection<File> tempFiles = FileUtils.listFiles(tempDir,suffixFilter,null);
						
			for(File tmpFile : tempFiles)
				templateNames.add(FilenameUtils.getBaseName(tmpFile.getPath()));
			
		} catch (IOException e1)
		{						
			e1.printStackTrace();
		}
		
		return templateNames;
	}
	*/
	
	/*
	 * 
	 */
	public static File templateDirectory()
	{
		Bundle bundle = FrameworkUtil.getBundle(Activator.class);
		BundleContext bundleContext = bundle.getBundleContext();
		URL urlTemplate = FileLocator.find(bundleContext.getBundle(),new Path(ODFTEXT_TEMPLATE_DIRECTORY), null);

		try
		{
			urlTemplate = FileLocator.resolve(urlTemplate);
			return FileUtils.toFile(urlTemplate);
			
		} catch (IOException e1)
		{						
			e1.printStackTrace();
		}
		return null;
	}

	// Rueckgabe eines Verzeichnisses in diesem PlugIn
	public static File getPluginDirectory(String dirName)
	{
		Bundle bundle = FrameworkUtil.getBundle(Activator.class);
		BundleContext bundleContext = bundle.getBundleContext();
		URL urlPluginDir = FileLocator.find(bundleContext.getBundle(),new Path(dirName), null);

		try
		{
			urlPluginDir = FileLocator.resolve(urlPluginDir);
			return FileUtils.toFile(urlPluginDir);
			
		} catch (IOException e1)
		{						
			e1.printStackTrace();
		}
		return null;
	}

	/**
	 * Rueckgabe eines Verzeichnisses der Volagen im Workspace. 	 
	 * @param subDir
	 * @return
	 */
	public static File getWorkspaceTemplateDirectory()
	{
		File destOfficeWorkspaceDir = new File(
				ResourcesPlugin.getWorkspace().getRoot().getLocation().toFile(),
				it.naturtalent.e4.office.ui.Activator.OFFICEDATADIR + File.separator + ODFTEXT_TEMPLATE_DIRECTORY);

		return destOfficeWorkspaceDir;
	}


}
