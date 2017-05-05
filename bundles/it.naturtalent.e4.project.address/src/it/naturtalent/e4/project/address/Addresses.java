package it.naturtalent.e4.project.address;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

import it.naturtalent.e4.project.AbstractProjectData;

public class Addresses extends AbstractProjectData
{
	// XML-Classname
	public static final String CLASS_ADDRESSES = "addresses"; //$NON-NLS-N$

	// Properties	
	public static final String PROP_ADDRESSES = "addresses"; //$NON-NLS-N$

	//Liste mit den Adressen
	private List<AddressData>adressDatas = new ArrayList<AddressData>();

	public List<AddressData> getAdressDatas()
	{
		return adressDatas;
	}

	public void setAdressDatas(List<AddressData> adressDatas)
	{
		firePropertyChange(new PropertyChangeEvent(this, PROP_ADDRESSES, this.adressDatas,
				this.adressDatas = adressDatas));
	}
	
	

}
