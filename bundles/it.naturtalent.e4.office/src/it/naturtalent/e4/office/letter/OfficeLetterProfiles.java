package it.naturtalent.e4.office.letter;

import it.naturtalent.e4.office.BaseBean;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

public class OfficeLetterProfiles extends BaseBean
{
	// Properties	
	public static final String PROP_OFFICE_PROFILES = "officeprofiles"; //$NON-NLS-N$
	
	private List<OfficeLetterProfile>profiles = new ArrayList<OfficeLetterProfile>();

	public List<OfficeLetterProfile> getProfiles()
	{
		return profiles;
	}

	public void setProfiles(List<OfficeLetterProfile> profiles)
	{
		firePropertyChange(new PropertyChangeEvent(this, PROP_OFFICE_PROFILES, this.profiles,
				this.profiles = profiles));
	}
	
}
