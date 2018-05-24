package it.naturtalent.e4.office.ui;

import it.naturtalent.e4.office.INtOffice;
import it.naturtalent.e4.office.IOfficeService;
import it.naturtalent.e4.office.OfficeConstants;
import it.naturtalent.e4.office.OpenDocumentUtils;
import it.naturtalent.libreoffice.odf.ODFOfficeDocumentHandler;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator
{
	private static BundleContext context;

	public static final String PLUGIN_TEMPLATE_DIR = File.separator + "templates"; //$NON-NLS-1$
	
	public static final String SELECT_TEMPLATE_EVENT = "selecttemplateevent";//$NON-NLS-1$
	
	public static final String NTOFFICE_SETTINGTEMPLATE_KEY = "ntofficetemplatesetting"; //$NON-NLS-1$
	
	public static Properties properties = new Properties();
	

	/*
	public static final String OFFICE_PROFILE_FILE_PATH = PLUGIN_TEMPLATE_DIR
			+ File.separator
			+ it.naturtalent.e4.office.Activator.OFFICE_PROFILE_FILE; //$NON-NLS-1$
			*/
	
	static BundleContext getContext()
	{
		return context;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext
	 * )
	 */
	public void start(BundleContext bundleContext) throws Exception
	{
		Activator.context = bundleContext;
		
		// Vorlagen in Workspace kopieren					
		File templateSrcDir = OpenDocumentUtils.getPluginTemplateDir(this.getClass()); 
		OpenDocumentUtils.copyTemplatesToWorkspace(templateSrcDir, IOfficeService.OFFICEDATADIR+File.separator+IOfficeService.NTOFFICE_CONTEXT, "ods");
		OpenDocumentUtils.copyTemplatesToWorkspace(templateSrcDir, IOfficeService.OFFICEDATADIR+File.separator+IOfficeService.NTOFFICE_CONTEXT, "odt");
		OpenDocumentUtils.copyTemplatesToWorkspace(templateSrcDir, IOfficeService.OFFICEDATADIR+File.separator+IOfficeService.NTOFFICE_CONTEXT, "odg");
		OpenDocumentUtils.copyTemplatesToWorkspace(templateSrcDir, IOfficeService.OFFICEDATADIR+File.separator+IOfficeService.NTOFFICE_CONTEXT, "xml");
		
		initProperties();		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception
	{
		Activator.context = null;
	}
	
	public static URL getPluginPath(String path)
	{
		try
		{
			URL url = FileLocator.find(Activator.context.getBundle(), new Path(path), null);
			url = FileLocator.resolve(url);
			return url;
		
		} catch (Exception e)
		{
		}
		return null;
	}

	private void initProperties()
	{
		try
		{
			String path = "platform:/plugin/"+OfficeConstants.PLUGIN_ID+"/plugin.properties";
			URL url = new URL(path);	
			InputStream inputStream = url.openConnection().getInputStream();			
			properties.load(inputStream);			
		} catch (Exception e)
		{
		}
	}
	
	/**
	 * Returns the platform resource URI for the provided class
	 * 
	 * @param clazz
	 *            the class to get the resource URI for
	 * @return the platform resource URI
	 */
	public static final String RESOURCE_SCHEMA = "bundleclass://"; //$NON-NLS-1$

	public static final String RESOURCE_SEPARATOR = "/"; //$NON-NLS-1$
	
	public static final String PLATFORM_PREFIX = "platform:/plugin"; //$NON-NLS-1$
	
	public static final String PLUGIN_ID = "it.naturtalent.e4.office.ui";

	public static String getResourceURI(Class<?> clazz)
	{
		return RESOURCE_SCHEMA + PLUGIN_ID + RESOURCE_SEPARATOR
				+ clazz.getCanonicalName();
	}
	
}
