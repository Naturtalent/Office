package it.naturtalent.e4.kontakte;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS
{
	private static final String BUNDLE_NAME = "it.naturtalent.e4.kontakte.messages"; //$NON-NLS-1$

	public static String KontaktePreferences_DefaultAnreden;
	public static String KontaktePreferences_DefaultKategorien;
	
	static
	{
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages()
	{
	}
}
