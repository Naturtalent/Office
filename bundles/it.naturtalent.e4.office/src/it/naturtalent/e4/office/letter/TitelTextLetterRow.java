package it.naturtalent.e4.office.letter;

import it.naturtalent.e4.office.BaseBean;
import it.naturtalent.e4.project.ITitelTextLetterRow;

import java.beans.PropertyChangeEvent;

public class TitelTextLetterRow extends BaseBean implements ITitelTextLetterRow, Cloneable
{
	public static final String PROP_TITEL = "titel"; //$NON-NLS-N$
	public static final String PROP_TEXT = "text"; //$NON-NLS-N$

	protected String titel;
	protected String text;


	@Override
	public String getTitel()
	{		
		return titel;
	}

	@Override
	public void setTitel(String titel)
	{
		firePropertyChange(new PropertyChangeEvent(this, PROP_TITEL, this.titel,
				this.titel = titel));				
	}

	@Override
	public String getText()
	{
		return text;
	}

	@Override
	public void setText(String text)
	{
		firePropertyChange(new PropertyChangeEvent(this, PROP_TEXT, this.text,
				this.text = text));			
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException
	{
		// TODO Auto-generated method stub
		return super.clone();
	}

}
