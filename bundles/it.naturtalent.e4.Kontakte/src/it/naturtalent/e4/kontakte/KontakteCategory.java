package it.naturtalent.e4.kontakte;

import java.beans.PropertyChangeEvent;
import java.util.Date;
import java.util.List;

import it.naturtalent.e4.project.BaseBean;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="kontakteCategory")
public class KontakteCategory extends BaseBean
{
	// Properties
	public static final String PROP_ID = "id"; //$NON-NLS-N$
	public static final String PROP_NAME = "name"; //$NON-NLS-N$
	public static final String PROP_KONTAKTE = "kontakteids"; //$NON-NLS-N$

	private String id;  	   	// ID der Kategorie
	private String name;    	// Name der Kategorie
	private String [] kontakte; // alle zugeordneten KontakteData ID's 

	public KontakteCategory()
	{
		super();
		id = makeIdentifier();
	}
	
	/**
	 * ID der Kategorie
	 * 
	 * @return
	 */
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		firePropertyChange(new PropertyChangeEvent(this, PROP_ID, this.id,
				this.id = id));		
	}
	
	/**
	 * Name der Kategorie
	 * 
	 * @return
	 */
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		firePropertyChange(new PropertyChangeEvent(this, PROP_NAME, this.name,
				this.name = name));				
	}
		
	/**
	 * Rueckgabe der ID aller zugeordneten Kontakte.
	 * 
	 * @return
	 */
	public String[] getKontakte()
	{
		return kontakte;
	}
	public void setKontakte(String[] kontakte)
	{
		firePropertyChange(new PropertyChangeEvent(this, PROP_KONTAKTE, this.kontakte,
				this.kontakte = kontakte));					
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
	
}
