package it.naturtalent.e4.office;

import java.util.HashMap;
import java.util.Map;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator
{

	private static BundleContext context;
	
	public static final String OFFICE_PROFILE_FILE = "profiles.xml"; //$NON-NLS-1$
	public static final String OFFICE_LETTERPROFILE_FILE = "letterprofiles.xml"; //$NON-NLS-1$
	
	public static Map<String,String> mapXMLTextTags = new HashMap<String, String>();
	{
		mapXMLTextTags.put(INtOffice.DOCUMENT_ABSENDER, "Absendertabelle");		
		mapXMLTextTags.put(INtOffice.DOCUMENT_ADRESSE, "Adresstabelle");
		mapXMLTextTags.put(INtOffice.DOCUMENT_PRAESENTATION, "Praesentationstabelle");
		mapXMLTextTags.put(INtOffice.DOCUMENT_REFERENZ, "Referenztabelle");
		mapXMLTextTags.put(INtOffice.DOCUMENT_BETREFF, "Betrefftabelle");
		mapXMLTextTags.put(INtOffice.DOCUMENT_FOOTER, "Footertabelle");
		mapXMLTextTags.put(INtOffice.DOCUMENT_SIGNATURE, "Signaturtabelle");
	}

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
		ProfilePreferences.initialize();
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
