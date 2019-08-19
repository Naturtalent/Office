package it.naturtalent.e4.office.ui;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.wizard.IWizard;
import org.odftoolkit.simple.TextDocument;
import org.odftoolkit.simple.meta.Meta;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

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
	
	// Basisname der neuen Textdatei (Zieldatei)
	public static final String ODFTEXT_FILENAME = "text.odt"; //$NON-NLS-1$
	
	// Template der mit diesem Adapter verwendete ODF-Datei  
	private static final String ODFTEXT_TEMPLATE = "/templates/ODFText.odt"; //$NON-NLS-1$

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
		if(destDir.isDirectory())
		{
			// sicherstellen, dass kein bereits vorhandener Name benutzt wird
			String newFileName = getAutoFileName(destDir,ODFTEXT_FILENAME);
			File newFile = new File(destDir, newFileName);
			createODFFile(newFile);
			try
			{
				// die Factoryklassname des Adapters als Property im Dokument speichern
				TextDocument odfDocument = TextDocument.loadDocument(newFile);
				Meta meta = odfDocument.getOfficeMetadata();
				meta.setUserDefinedData(ODFADAPTERFACTORY, "Text",
						DefaultWriteAdapterFactory.class.getName()); // $NON-NLS-N$
				odfDocument.save(newFile);
				
				
			} catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return newFile;
		}		
		return null;
	}

	/*
	 * Vorlage kopieren
	 */
	private static void createODFFile(File newODFFile)
	{
		Bundle bundle = FrameworkUtil.getBundle(Activator.class);
		BundleContext bundleContext = bundle.getBundleContext();
		URL urlTemplate = FileLocator.find(bundleContext.getBundle(),new Path(ODFTEXT_TEMPLATE), null);
		try
		{
			urlTemplate = FileLocator.resolve(urlTemplate);
			try
			{				
				FileUtils.copyURLToFile(urlTemplate, newODFFile);				
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

}
