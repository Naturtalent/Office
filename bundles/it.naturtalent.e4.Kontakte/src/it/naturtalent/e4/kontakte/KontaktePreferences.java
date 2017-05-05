package it.naturtalent.e4.kontakte;

import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;

public class KontaktePreferences
{

	public static final String ROOT_KONTAKT_PREFERENCES_NODE = "it.naturtalent.e4.kontakte"; //$NON-NLS-1$	
	public static final String ADDRESS_ANREDEN_KEY = "preferencekontakteaddressanreden"; //$NON-NLS-1$	
	public static final String KONTAKTE_CATEGORIES_KEY = "kontakte_categories"; //$NON-NLS-1$
	
	public static void initialize()
	{
		IEclipsePreferences defaultNode = DefaultScope.INSTANCE
				.getNode(ROOT_KONTAKT_PREFERENCES_NODE);
		defaultNode.put(ADDRESS_ANREDEN_KEY, Messages.KontaktePreferences_DefaultAnreden);
		defaultNode.put(KONTAKTE_CATEGORIES_KEY, Messages.KontaktePreferences_DefaultKategorien);
	}
}
