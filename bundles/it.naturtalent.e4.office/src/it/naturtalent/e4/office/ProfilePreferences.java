package it.naturtalent.e4.office;

import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;

public class ProfilePreferences
{

	public static final String ROOT_OFFICEPROFILE_PREFERENCES_NODE = "it.naturtalent.office.profiles"; //$NON-NLS-1$
	
	public static final String OFFICE_PROFILE_KEY = "office.profile"; //$NON-NLS-1$
	
	public static final String DEFAULT_OFFICE_PROFILE = "Defaultprofil"; //$NON-NLS-1$
	
	public static void initialize()
	{
		// Name des Default Profils
		IEclipsePreferences defaultNode = DefaultScope.INSTANCE
				.getNode(ROOT_OFFICEPROFILE_PREFERENCES_NODE);
		defaultNode.put(OFFICE_PROFILE_KEY, DEFAULT_OFFICE_PROFILE);
	}
}
