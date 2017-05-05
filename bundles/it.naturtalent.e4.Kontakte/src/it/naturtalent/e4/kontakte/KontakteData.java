package it.naturtalent.e4.kontakte;


import it.naturtalent.e4.project.BaseBean;
import it.naturtalent.e4.project.address.AddressData;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="kontakteData")
public class KontakteData extends BaseBean implements IKontakteData, Cloneable
{

	// Properties
	public static final String PROP_ADDRESS = "address"; //$NON-NLS-N$
	public static final String PROP_TYPE = "type"; //$NON-NLS-N$
	public static final String PROP_TELEFON = "telefon"; //$NON-NLS-N$
	public static final String PROP_FAX = "fax"; //$NON-NLS-N$
	public static final String PROP_MOBILE = "mobile"; //$NON-NLS-N$
	public static final String PROP_EMAIL = "email"; //$NON-NLS-N$
	public static final String PROP_URL = "url"; //$NON-NLS-N$
	public static final String PROP_BANK = "bank"; //$NON-NLS-N$	
	public static final String PROP_NOTICE = "notice"; //$NON-NLS-N$

	// primary key
	private java.lang.String id;
	
	private java.lang.Integer type;
	private AddressData address;
	private List<String>phones;
	private List<String>fax;
	private List<String>mobiles;
	private List<String>emails;
	private List<String>urls;
	private List<BankData>banks;
	private String notice;

	
	
	public KontakteData()
	{
		id = makeIdentifier();
	}

	public java.lang.String getId()
	{
		return id;
	}

	public void setId(java.lang.String id)
	{
		this.id = id;
	}
	
	public java.lang.Integer getType()
	{
		return type;
	}

	public void setType(java.lang.Integer type)
	{
		firePropertyChange(new PropertyChangeEvent(this, PROP_TYPE, this.type,
				this.type = type));		
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

	public List<String> getPhones()
	{
		return phones;
	}

	public void setPhones(List<String> phones)
	{
		firePropertyChange(new PropertyChangeEvent(this, PROP_TELEFON, this.phones,
				this.phones = phones));
	}

	public List<String> getMobiles()
	{
		return mobiles;
	}

	public void setMobiles(List<String> mobiles)
	{
		firePropertyChange(new PropertyChangeEvent(this, PROP_MOBILE, this.mobiles,
				this.mobiles = mobiles));
	}
	
	public List<String> getFax()
	{
		return fax;
	}

	public void setFax(List<String> fax)
	{		
		firePropertyChange(new PropertyChangeEvent(this, PROP_FAX, this.fax,
				this.fax = fax));
	}

	public List<String> getEmails()
	{
		return emails;
	}

	public void setEmails(List<String> emails)
	{
		firePropertyChange(new PropertyChangeEvent(this, PROP_EMAIL, this.emails,
				this.emails = emails));
	}

	public List<String> getUrls()
	{
		return urls;
	}

	public void setUrls(List<String> urls)
	{
		firePropertyChange(new PropertyChangeEvent(this, PROP_URL, this.urls,
				this.urls = urls));
	}
	
	public List<BankData> getBanks()
	{
		return banks;
	}

	public void setBanks(List<BankData> banks)
	{
		firePropertyChange(new PropertyChangeEvent(this, PROP_BANK, this.banks,
				this.banks = banks));
	}
	
	public String getNotice()
	{
		return notice;
	}

	public void setNotice(String notice)
	{
		firePropertyChange(new PropertyChangeEvent(this, PROP_NOTICE, this.notice,
				this.notice = notice));		
	}

	/**
	 * einen datumsbasierenden Key erzeugen
	 */

	private static String date;

	private static long identifierCounter;

	/**
	 * Einen eindeutigen, datumsbasierenden Schluessel erzeugen
	 * 
	 * @return
	 */
	public static String makeIdentifier()
	{
		if (date == null)
			date = Long.toString((new Date().getTime())) + "-";
		return date + Long.toString(++identifierCounter);
	}	

	@Override
	public KontakteData clone()
	{		
		try
		{
			KontakteData kontakteClone = (KontakteData) super.clone();
			
			if(kontakteClone.address != null)
				kontakteClone.address = (AddressData) kontakteClone.address.clone();
			
			if(kontakteClone.phones != null)
				kontakteClone.phones = (List<String>) ((ArrayList)kontakteClone.phones).clone();
			
			if(kontakteClone.mobiles != null)
				kontakteClone.mobiles = (List<String>) ((ArrayList)kontakteClone.mobiles).clone();
			
			if(kontakteClone.fax != null)
				kontakteClone.fax = (List<String>) ((ArrayList)kontakteClone.fax).clone();
			
			if(kontakteClone.emails != null)
				kontakteClone.emails = (List<String>) ((ArrayList)kontakteClone.emails).clone();
			
			if(kontakteClone.urls != null)
				kontakteClone.urls = (List<String>) ((ArrayList)kontakteClone.urls).clone();
			
			if(kontakteClone.banks != null)
				kontakteClone.banks = (List<BankData>) ((ArrayList)kontakteClone.banks).clone();
			
			return kontakteClone;
		} catch (CloneNotSupportedException e)
		{			
		}
		return null;
	}
	
	public void initKontakteData()
	{		
		address = new AddressData();
		phones = new ArrayList<String>();
		fax = new ArrayList<String>();
		mobiles = new ArrayList<String>();
		emails = new ArrayList<String>();
		urls = new ArrayList<String>();
		banks = new ArrayList<BankData>();
		notice = "";
	}

	/*
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((banks == null) ? 0 : banks.hashCode());
		result = prime * result + ((emails == null) ? 0 : emails.hashCode());
		result = prime * result + ((fax == null) ? 0 : fax.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((mobiles == null) ? 0 : mobiles.hashCode());
		result = prime * result + ((notice == null) ? 0 : notice.hashCode());
		result = prime * result + ((phones == null) ? 0 : phones.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((urls == null) ? 0 : urls.hashCode());
		return result;
	}
	*/

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		KontakteData other = (KontakteData) obj;
		if (address == null)
		{
			if (other.address != null)
				return false;
		}
		else if (!address.equals(other.address))
			return false;
		if (banks == null)
		{
			if (other.banks != null)
				return false;
		}
		else if (!banks.equals(other.banks))
			return false;
		if (emails == null)
		{
			if (other.emails != null)
				return false;
		}
		else if (!emails.equals(other.emails))
			return false;
		if (fax == null)
		{
			if (other.fax != null)
				return false;
		}
		else if (!fax.equals(other.fax))
			return false;
		if (id == null)
		{
			if (other.id != null)
				return false;
		}
		else if (!id.equals(other.id))
			return false;
		if (mobiles == null)
		{
			if (other.mobiles != null)
				return false;
		}
		else if (!mobiles.equals(other.mobiles))
			return false;
		if (notice == null)
		{
			if (other.notice != null)
				return false;
		}
		else if (!notice.equals(other.notice))
			return false;
		if (phones == null)
		{
			if (other.phones != null)
				return false;
		}
		else if (!phones.equals(other.phones))
			return false;
		if (type == null)
		{
			if (other.type != null)
				return false;
		}
		else if (!type.equals(other.type))
			return false;
		if (urls == null)
		{
			if (other.urls != null)
				return false;
		}
		else if (!urls.equals(other.urls))
			return false;
		return true;
	}
	
	
	

	// geanderter hashCode fuehrt zu Fehler beim update im viewer
/*
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((banks == null) ? 0 : banks.hashCode());
		result = prime * result + ((emails == null) ? 0 : emails.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((mobiles == null) ? 0 : mobiles.hashCode());
		result = prime * result + ((notice == null) ? 0 : notice.hashCode());
		result = prime * result + ((phones == null) ? 0 : phones.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((urls == null) ? 0 : urls.hashCode());
		return result;
	}
	*/


/*
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		KontakteData other = (KontakteData) obj;
		if (address == null)
		{
			if (other.address != null)
				return false;
		}
		else if (!address.equals(other.address))
			return false;
		if (banks == null)
		{
			if (other.banks != null)
				return false;
		}
		else if (!banks.equals(other.banks))
			return false;
		if (emails == null)
		{
			if (other.emails != null)
				return false;
		}
		else if (!emails.equals(other.emails))
			return false;
		if (id == null)
		{
			if (other.id != null)
				return false;
		}
		else if (!id.equals(other.id))
			return false;
		if (mobiles == null)
		{
			if (other.mobiles != null)
				return false;
		}
		else if (!mobiles.equals(other.mobiles))
			return false;
		if (notice == null)
		{
			if (other.notice != null)
				return false;
		}
		else if (!notice.equals(other.notice))
			return false;
		if (phones == null)
		{
			if (other.phones != null)
				return false;
		}
		else if (!phones.equals(other.phones))
			return false;
		if (type == null)
		{
			if (other.type != null)
				return false;
		}
		else if (!type.equals(other.type))
			return false;
		if (urls == null)
		{
			if (other.urls != null)
				return false;
		}
		else if (!urls.equals(other.urls))
			return false;
		return true;
	}
	*/

	
	/*
	@Override
	public boolean equals(Object obj)
	{
		return EqualsBuilder.reflectionEquals(this, obj);
	}
	*/
	
	
}
