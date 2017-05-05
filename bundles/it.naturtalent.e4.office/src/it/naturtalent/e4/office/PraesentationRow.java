package it.naturtalent.e4.office;

import java.beans.PropertyChangeEvent;

/**
 * Diese Klasse definiert die Zellen einer Zeile in der Praesentatinstabelle.
 * 
 * @author dieter
 *
 */
public class PraesentationRow extends BaseBean implements Cloneable
{
	public static final String PROP_PRAESENTATION_TITEL = "prasentationtitel"; //$NON-NLS-N$
	public static final String PROP_PRAESENTATION_TEXT = "prasentationtext"; //$NON-NLS-N$

	private String titel;
	private String text;
	public String getTitel()
	{
		return titel;
	}
	public void setTitel(String titel)
	{
		firePropertyChange(new PropertyChangeEvent(this, PROP_PRAESENTATION_TITEL, this.titel,
				this.titel = titel));			
	}
	public String getText()
	{
		return text;
	}
	public void setText(String text)
	{
		firePropertyChange(new PropertyChangeEvent(this, PROP_PRAESENTATION_TEXT, this.text,
				this.text = text));	
	}
	@Override
	public Object clone() throws CloneNotSupportedException
	{
		// TODO Auto-generated method stub
		return super.clone();
	}
	
	

}
