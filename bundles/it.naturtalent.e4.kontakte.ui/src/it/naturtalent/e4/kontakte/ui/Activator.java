package it.naturtalent.e4.kontakte.ui;

import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator
{

	public static final String PLUGIN_ID = "it.naturtalent.e4.kontakte.ui";
	
	private static BundleContext context;
	
    // Zugriff auf 'plugin.properties'
    public static Properties properties = new Properties();
    

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
		initPluginProperties();
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

	private void initPluginProperties()
	{
		try
		{
			String path = "platform:/plugin/"+PLUGIN_ID+"/plugin.properties";
			URL url = new URL(path);	
			InputStream inputStream = url.openConnection().getInputStream();			
			properties.load(inputStream);			
		} catch (Exception e)
		{
		}
	}

}
