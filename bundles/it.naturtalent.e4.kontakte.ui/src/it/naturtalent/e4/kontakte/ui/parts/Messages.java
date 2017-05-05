package it.naturtalent.e4.kontakte.ui.parts;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS
{
	private static final String BUNDLE_NAME = "it.naturtalent.e4.kontakte.ui.parts.messages"; //$NON-NLS-1$

	public static String ContactView_ContactLabel;

	public static String ContactView_PersistMessage;
	static
	{
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages()
	{
	}
}
