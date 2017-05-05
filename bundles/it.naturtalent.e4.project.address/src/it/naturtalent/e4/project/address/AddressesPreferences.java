package it.naturtalent.e4.project.address;

import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;

public class AddressesPreferences
{

	public static final String ROOT_ADDRESS_PREFERENCES_NODE = "it.naturtalent.e4.project.address"; //$NON-NLS-1$
	
	public static final String ADDRESS_ANREDEN_KEY = "address_anreden"; //$NON-NLS-1$
	
	public static final String DEFAULT_ANREDE = "Herr,Frau,Fräulein,Familie"; //$NON-NLS-1$
	
	public static void initialize()
	{
		IEclipsePreferences defaultNode = DefaultScope.INSTANCE
				.getNode(ROOT_ADDRESS_PREFERENCES_NODE);
		defaultNode.put(ADDRESS_ANREDEN_KEY, DEFAULT_ANREDE);
	}
}
