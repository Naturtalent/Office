package it.naturtalent.e4.project.address;

import it.naturtalent.e4.project.AbstractProjectData;
import it.naturtalent.e4.project.IProjectData;

import java.beans.PropertyChangeEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;


public class AddressData extends AbstractProjectData implements IAddressData, Cloneable, IProjectData 
{
	// Adress properties	
	public static final String PROP_ID = "id"; //$NON-NLS-N$
	public static final String PROP_ANREDE = "anrede"; //$NON-NLS-N$
	public static final String PROP_NAME = "name"; //$NON-NLS-N$
	public static final String PROP_NAMEZUSATZ1 = "namezusatz1"; //$NON-NLS-N$
	public static final String PROP_NAMEZUSATZ2 = "namezusatz2"; //$NON-NLS-N$
	public static final String PROP_STRASSE = "strasse"; //$NON-NLS-N$
	public static final String PROP_PLZ = "plz"; //$NON-NLS-N$
	public static final String PROP_ORT = "ort"; //$NON-NLS-N$
	public static final String PROP_TYPE = "type"; //$NON-NLS-N$	
	public static final String PROP_NOTICE = "notice"; //$NON-NLS-N$
	
	// XML-Classname
	public static final String CLASS_ADDRESSDATA = "addressData"; //$NON-NLS-N$
	
	public static final String TYPE_PRIVATE = "private"; //$NON-NLS-N$
	public static final String TYPE_PUBLIC = "public"; //$NON-NLS-N$

	// primary key
	private java.lang.String id;
	
	// Datenfelder
	private String anrede;
	private String name;
	private String namezusatz1;
	private String namezusatz2;
	private String strasse;
	private String plz;
	private String ort;
	private String type;
	private String notice;

	public AddressData()
	{
		type = TYPE_PUBLIC;
		anrede = name = namezusatz1 = namezusatz2 = strasse = plz = ort = notice = "";
	}


	public String getAnrede()
	{
		return anrede;
	}

	public void setAnrede(String anrede)
	{
		firePropertyChange(new PropertyChangeEvent(this, PROP_ANREDE, this.anrede,
				this.anrede = anrede));
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		firePropertyChange(new PropertyChangeEvent(this, PROP_NAME, this.name,
				this.name = name));
	}

	public String getNamezusatz1()
	{
		return namezusatz1;
	}

	public void setNamezusatz1(String namezusatz1)
	{
		firePropertyChange(new PropertyChangeEvent(this, PROP_NAMEZUSATZ1, this.namezusatz1,
				this.namezusatz1 = namezusatz1));
	}

	public String getNamezusatz2()
	{
		return namezusatz2;
	}

	public void setNamezusatz2(String namezusatz2)
	{
		firePropertyChange(new PropertyChangeEvent(this, PROP_NAMEZUSATZ2, this.namezusatz1,
				this.namezusatz2 = namezusatz2));
	}

	public String getStrasse()
	{
		return strasse;
	}

	public void setStrasse(String strasse)
	{
		firePropertyChange(new PropertyChangeEvent(this, PROP_STRASSE, this.strasse,
				this.strasse = strasse));
	}

	public String getPlz()
	{
		return plz;
	}

	public void setPlz(String plz)
	{
		firePropertyChange(new PropertyChangeEvent(this, PROP_PLZ, this.plz,
				this.plz = plz));
	}

	public String getOrt()
	{
		return ort;
	}

	public void setOrt(String ort)
	{
		firePropertyChange(new PropertyChangeEvent(this, PROP_ORT, this.ort,
				this.ort = ort));
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		firePropertyChange(new PropertyChangeEvent(this, PROP_TYPE, this.type,
				this.type = type));
	}
	

	public String getNotice()
	{
		return notice;
	}


	public void setNotice(String notice)
	{
		firePropertyChange(new PropertyChangeEvent(this, PROP_NOTICE, this.type,
				this.notice = notice));		
	}
	

	/*
	public boolean equals(Object obj)
	{
		return EqualsBuilder.reflectionEquals(this, obj);
	}
	*/
	
	
	public static String getText(AddressData officeAddressData)
	{
		String stgValue;
		StringBuilder text = new StringBuilder(50);
		
		text.append(officeAddressData.name);
		
		stgValue = officeAddressData.namezusatz1;
		if(StringUtils.isNotEmpty(stgValue))
			text.append(" "+stgValue);
		
		stgValue = officeAddressData.plz;
		if(StringUtils.isNotEmpty(stgValue))
			text.append(", "+stgValue);
		
		stgValue = officeAddressData.ort;
		if(StringUtils.isNotEmpty(stgValue))
			text.append(" "+stgValue);
		
		return text.toString();
	}
	
	public static AddressData getAddressData(String [] rows)
	{
		int i = -1;
		AddressData address = new AddressData();
		
		// mit letzter Zeile beginnen (erwartet PLZ u Ort)
		for(i = rows.length - 1;i >= 0;i--)
		{
			if(StringUtils.isNotEmpty(rows[i]))
			{
				if(parseAddressPLZ(address, rows[i]))
					break;
			}					
		}
		
		if(--i >= 0) address.setStrasse(rows[i]);			

		if(--i >= 0) address.setNamezusatz2(rows[i]);			

		if(--i >= 0) address.setNamezusatz1(rows[i]);			

		if(--i >= 0) address.setAnrede(rows[i]);	
				
		rotateAddressName(address);
		return address;
	}
	
	private static void rotateAddressName(AddressData address)
	{
		if(StringUtils.isEmpty(address.name))
		{
			if(StringUtils.isNotEmpty(address.namezusatz1))
			{
				address.name = address.namezusatz1;
				address.namezusatz1 = address.namezusatz2;
				address.namezusatz2 = "";
				return;
			}
			if(StringUtils.isNotEmpty(address.namezusatz2))
			{
				address.name = address.namezusatz2;
				address.namezusatz1 = "";
				address.namezusatz2 = "";
				return;
			}
		}
	}
	
	private static boolean parseAddressPLZ(AddressData address, String plzOrt)
	{
		Pattern p = Pattern.compile("\\d+");
		Matcher m = p.matcher(plzOrt);
		if(m.find())
		{			
			address.setPlz(StringUtils.substring(plzOrt, 0,
					StringUtils.indexOf(plzOrt, " ")));

			address.setOrt(StringUtils.substring(plzOrt, address.getPlz()
					.length()).trim());
			
			return true;
		}
		
		return (false);
	}

	public static String[] getAddressText(AddressData officeAddressData)
	{
		String[] result = new String[0];
		String stgValue;

		if (officeAddressData != null)
		{
			if (officeAddressData.type.equals(TYPE_PRIVATE))
			{
				// Anrede
				result = createAddressTextArray(officeAddressData.anrede,
						result);

				// Vorname und Nachname
				stgValue = "";
				if (StringUtils.isNotEmpty(officeAddressData.namezusatz1))
					stgValue = officeAddressData.namezusatz1;
				if (StringUtils.isNotEmpty(officeAddressData.name))
					stgValue = stgValue + " " + officeAddressData.name;
				result = createAddressTextArray(stgValue, result);
			}
			else
			{
				// Name
				result = createAddressTextArray(officeAddressData.name, result);

				// Zusatz
				result = createAddressTextArray(officeAddressData.namezusatz1,
						result);
				result = createAddressTextArray(officeAddressData.namezusatz2,
						result);
			}

			// Strasse
			result = createAddressTextArray(officeAddressData.strasse, result);

			// PLZ und Ort
			stgValue = "";
			if (StringUtils.isNotEmpty(officeAddressData.plz))
				stgValue = officeAddressData.plz;
			if (StringUtils.isNotEmpty(officeAddressData.ort))
				stgValue = stgValue + " " + officeAddressData.ort;
			result = createAddressTextArray(stgValue, result);
		}

		return result;
	}
	
	private static String [] createAddressTextArray(String item, String [] textArray)
	{
		if(StringUtils.isNotEmpty(item))
			textArray = ArrayUtils.add(textArray, item);		
		return textArray;
	}
	
	public static AddressData copy(AddressData destData, IAddressData srcData)
	{
		destData.setAnrede(srcData.getAnrede());
		destData.setName(srcData.getName());
		destData.setNamezusatz1(srcData.getNamezusatz1());
		destData.setNamezusatz2(srcData.getNamezusatz2());
		destData.setStrasse(srcData.getStrasse());
		destData.setPlz(srcData.getPlz());
		destData.setOrt(srcData.getOrt());
		destData.setType(srcData.getType());		
		return destData;
	}

	public void copy(IAddressData srcData)
	{
		setAnrede(srcData.getAnrede());
		setName(srcData.getName());
		setNamezusatz1(srcData.getNamezusatz1());
		setNamezusatz2(srcData.getNamezusatz2());
		setStrasse(srcData.getStrasse());
		setPlz(srcData.getPlz());
		setOrt(srcData.getOrt());
		setType(srcData.getType());		
	}


	
	@Override
	public String getDescription()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDescription(String description)
	{
		// TODO Auto-generated method stub
		
	}


	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((anrede == null) ? 0 : anrede.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((namezusatz1 == null) ? 0 : namezusatz1.hashCode());
		result = prime * result
				+ ((namezusatz2 == null) ? 0 : namezusatz2.hashCode());
		result = prime * result + ((notice == null) ? 0 : notice.hashCode());
		result = prime * result + ((ort == null) ? 0 : ort.hashCode());
		result = prime * result + ((plz == null) ? 0 : plz.hashCode());
		result = prime * result + ((strasse == null) ? 0 : strasse.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		AddressData other = (AddressData) obj;
		if (anrede == null)
		{
			if (other.anrede != null)
				return false;
		}
		else if (!anrede.equals(other.anrede))
			return false;
		if (id == null)
		{
			if (other.id != null)
				return false;
		}
		else if (!id.equals(other.id))
			return false;
		if (name == null)
		{
			if (other.name != null)
				return false;
		}
		else if (!name.equals(other.name))
			return false;
		if (namezusatz1 == null)
		{
			if (other.namezusatz1 != null)
				return false;
		}
		else if (!namezusatz1.equals(other.namezusatz1))
			return false;
		if (namezusatz2 == null)
		{
			if (other.namezusatz2 != null)
				return false;
		}
		else if (!namezusatz2.equals(other.namezusatz2))
			return false;
		if (notice == null)
		{
			if (other.notice != null)
				return false;
		}
		else if (!notice.equals(other.notice))
			return false;
		if (ort == null)
		{
			if (other.ort != null)
				return false;
		}
		else if (!ort.equals(other.ort))
			return false;
		if (plz == null)
		{
			if (other.plz != null)
				return false;
		}
		else if (!plz.equals(other.plz))
			return false;
		if (strasse == null)
		{
			if (other.strasse != null)
				return false;
		}
		else if (!strasse.equals(other.strasse))
			return false;
		if (type == null)
		{
			if (other.type != null)
				return false;
		}
		else if (!type.equals(other.type))
			return false;
		return true;
	}
	
	
}
