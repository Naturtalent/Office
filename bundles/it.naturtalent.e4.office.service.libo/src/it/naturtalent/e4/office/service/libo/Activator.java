package it.naturtalent.e4.office.service.libo;

import it.naturtalent.e4.office.INtOffice;

import java.io.File;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator
{

	private static BundleContext context;
	
	//private static final String LINUX_UNO_PATH = "/usr/bin/";
	
	private static final String LINUX_UNO_PATH = "/usr/lib/libreoffice/program/";
	
	private static final String WINDOWS_UNO_PATH = "\\Office\\LibreOfficeProtable4\\App\\libreoffice\\program";


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
		
		String unoPath;
		
		if(SystemUtils.IS_OS_LINUX)
			unoPath = LINUX_UNO_PATH;
		else
		{
			String key = it.naturtalent.application.Activator.NT_PROGRAM_HOME;
			String extProgs = System.getProperty(key); 					
			unoPath = extProgs + WINDOWS_UNO_PATH;			
		}
		
		if(StringUtils.isNotEmpty(unoPath))
			System.setProperty(INtOffice.EXTERN_OFFICE_PROPERTY, unoPath);
		
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

}
