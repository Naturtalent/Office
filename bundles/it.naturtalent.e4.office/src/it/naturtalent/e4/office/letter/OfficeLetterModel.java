package it.naturtalent.e4.office.letter;

import java.beans.PropertyChangeEvent;
import java.util.List;

//import it.naturtalent.e4.project.address.AddressData;

import it.naturtalent.e4.office.BaseBean;
import it.naturtalent.e4.project.address.AddressData;

/**
 * Das Modell eines Briefkopfes
 * 
 * @author apel.dieter
 *
 */
public class OfficeLetterModel extends BaseBean
{

	public static final String PROP_BETREFF = "betreff"; //$NON-NLS-N$
	public static final String PROP_CONTENT = "content"; //$NON-NLS-N$
	public static final String PROP_PROFILE = "profile"; //$NON-NLS-N$
	public static final String PROP_ADDRESS = "address"; //$NON-NLS-N$
	
	private List<TitelTextLetterRow>betreff;
	private String [] content;
	private OfficeLetterProfile profile;
	private AddressData address; 
	
	public List<TitelTextLetterRow> getBetreff()
	{
		return betreff;
	}

	public void setBetreff(List<TitelTextLetterRow> betreff)
	{
		firePropertyChange(new PropertyChangeEvent(this, PROP_BETREFF, this.betreff,
				this.betreff = betreff));		
	}

	public String[] getContent()
	{
		return content;
	}

	public void setContent(String[] content)
	{
		firePropertyChange(new PropertyChangeEvent(this, PROP_CONTENT, this.content,
				this.content = content));	
	}

	public OfficeLetterProfile getProfile()
	{
		return profile;
	}

	public void setProfile(OfficeLetterProfile profile)
	{
		firePropertyChange(new PropertyChangeEvent(this, PROP_PROFILE, this.profile,
				this.profile = profile));		
	}

	public AddressData getAddress()
	{
		return address;
	}

	public void setAddress(AddressData address)
	{
		firePropertyChange(new PropertyChangeEvent(this, PROP_ADDRESS, this.address,
				this.address = address));		
	}
	
	
	
}
