package it.naturtalent.e4.office;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

public class OfficeDocumentUtils
{
	public static final String OFFICEDATADIR = "office";
	
	private static final String PLUGIN_TEMPLATE_DIR = File.separator + "templates"; //$NON-NLS-1$
	
	
	public static File getTemporaeryTemplate(Class<?>pluginClass, String templateName)
	{
		File tempFile = null;
		
		// die erforderliche temporaere Vorlage erzeugen
		tempFile = getTemporaryTemplate(OpenDocumentUtils.OFFICEDATADIR,templateName);		
		if (tempFile == null)
		{
			// originaere Vorlage in das Workspace kopieren
			File templateSRCDir = getPluginTemplateDir(pluginClass);
			copyTemplatesToWorkspace(templateSRCDir,OFFICEDATADIR, null);			
			tempFile = getTemporaryTemplate(OFFICEDATADIR,templateName);
		}
		
		return tempFile;
	}
	
	public static File getPluginTemplateDir(Class<?>pluginClass)
	{
		try
		{
			BundleContext bundleContext = FrameworkUtil.getBundle(pluginClass).getBundleContext();                    
			Path path = new Path(PLUGIN_TEMPLATE_DIR); 
			URL url = FileLocator.find(bundleContext.getBundle(), path, null);
			url = FileLocator.resolve(url);
			
			return FileUtils.toFile(url);
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	private static File destDir = null;
	public static void copyTemplatesToWorkspace(File templatesSrcDir, String templateDirName, String suffixFilter)
	{
		// filtert auf Dateierweiterung
		IOFileFilter odtSuffixFilter = null;
		
		// filtert existierende Dateien
		IOFileFilter existFileFilter = new IOFileFilter(){

			@Override
			public boolean accept(File arg0)
			{
				File fileCheck = new File(destDir,arg0.getName());
				boolean check = !fileCheck.exists();
				return check;
			}

			@Override
			public boolean accept(File arg0, String arg1)
			{				
				return true;
			}			
		};
				
		try
		{
			destDir = getWorkspaceTemplateDir(templateDirName);
						
			if (StringUtils.isNotEmpty(suffixFilter))
			{
				odtSuffixFilter = FileFilterUtils
						.suffixFileFilter(suffixFilter);

				odtSuffixFilter = FileFilterUtils.and(odtSuffixFilter, existFileFilter);
				FileUtils.copyDirectory(templatesSrcDir, destDir,
						odtSuffixFilter);
			}
			else
			{
				FileUtils.copyDirectory(templatesSrcDir, destDir);
			}
			
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}


	public static File getWorkspaceTemplateDir(String templateDirName)
	{
		IWorkspace workspace = ResourcesPlugin.getWorkspace();  
		File archivDir = new File(workspace.getRoot().getLocation().toFile(), templateDirName);
				
		try
		{
			FileUtils.forceMkdir(archivDir);
			return archivDir;
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static File getTemporaryTemplate(String relativeWorkspaceDir, String templateName)
	{
		File temporaerFile = null;
		File workspaceDir = getWorkspaceTemplateDir(relativeWorkspaceDir);
		if(workspaceDir.exists() && workspaceDir.isDirectory())
		{
			File templateFile = new File(workspaceDir, templateName);
			if(templateFile.exists())
			{				
				// Vorlage in temporaere Datei umkopieren
				String extention = FilenameUtils.getExtension(templateFile.getPath());
				extention = (StringUtils.isEmpty(extention)) ? null : "."+ extention;
				try
				{
					temporaerFile = File.createTempFile("temporaerTemplate", extention); //$NON-NLS-N$
					FileUtils.copyFile(templateFile, temporaerFile);
				} catch (IOException e)
				{				
					e.printStackTrace();
				}
			}
		}
		
		return temporaerFile;
	}
}
