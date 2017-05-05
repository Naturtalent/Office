package it.naturtalent.e4.office.letter;

import it.naturtalent.e4.office.BaseBean;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

public class OfficeLetterProfile extends BaseBean implements Cloneable
{

	public static final String PROP_NAME = "name"; //$NON-NLS-N$
	public static final String PROP_HEADER = "header"; //$NON-NLS-N$
	public static final String PROP_ABSENDER = "absender"; //$NON-NLS-N$
	public static final String PROP_PRAESENTATION = "praesentation"; //$NON-NLS-N$
	public static final String PROP_REFERENCES = "references"; //$NON-NLS-N$
	public static final String PROP_SIGNATURE = "signature"; //$NON-NLS-N$
	public static final String PROP_FOOTER = "footer"; //$NON-NLS-N$
		
	private java.lang.String name;
	private String header;
	private List<TitelTextLetterRow>absender;	
	private List<TitelTextLetterRow>praesentation;
	private List<TitelTextLetterRow>references;
	private List<TitelTextLetterRow> signature;
	private List<TitelTextLetterRow>footer;	
	
	
	public OfficeLetterProfile()
	{
		super();
	}

	public java.lang.String getName()
	{
		return name;
	}

	public void setName(java.lang.String name)
	{
		firePropertyChange(new PropertyChangeEvent(this, PROP_NAME, this.name,
				this.name = name));		
	}

	public String getHeader()
	{
		return header;
	}

	public void setHeader(String header)
	{
		firePropertyChange(new PropertyChangeEvent(this, PROP_HEADER, this.header,
				this.header = header));		
	}
	
	public List<TitelTextLetterRow> getSignature()
	{
		return signature;
	}

	public void setSignature(List<TitelTextLetterRow> signature)
	{
		firePropertyChange(new PropertyChangeEvent(this, PROP_SIGNATURE, this.signature,
				this.signature = signature));	
	}

	public List<TitelTextLetterRow> getFooter()
	{
		return footer;
	}

	public void setFooter(List<TitelTextLetterRow> footer)
	{
		firePropertyChange(new PropertyChangeEvent(this, PROP_FOOTER, this.footer,
				this.footer = footer));		
	}

	public List<TitelTextLetterRow> getReferences()
	{
		return references;
	}

	public void setReferences(List<TitelTextLetterRow> references)
	{
		firePropertyChange(new PropertyChangeEvent(this, PROP_REFERENCES, this.references,
				this.references = references));		
	}
	
	public List<TitelTextLetterRow> getAbsender()
	{
		return absender;
	}

	public void setAbsender(List<TitelTextLetterRow> absender)
	{
		firePropertyChange(new PropertyChangeEvent(this, PROP_ABSENDER, this.absender,
				this.absender = absender));			
	}
	
	public List<TitelTextLetterRow> getPraesentation()
	{
		return praesentation;
	}

	public void setPraesentation(List<TitelTextLetterRow> praesentation)
	{
		firePropertyChange(new PropertyChangeEvent(this, PROP_PRAESENTATION, this.praesentation,
				this.praesentation = praesentation));			
	}

	@Override
	public Object clone()
	{	
		try
		{
			OfficeLetterProfile profileClone = (OfficeLetterProfile) super.clone();

			if (profileClone.absender != null)
			{
				List<TitelTextLetterRow> clone = new ArrayList<TitelTextLetterRow>(
						profileClone.absender.size());
				for (TitelTextLetterRow row : profileClone.absender)
					clone.add((TitelTextLetterRow) row.clone());
				profileClone.absender = clone;
			}

			if (profileClone.praesentation != null)
			{
				List<TitelTextLetterRow> clone = new ArrayList<TitelTextLetterRow>(
						profileClone.praesentation.size());
				for (TitelTextLetterRow row : profileClone.praesentation)
					clone.add((TitelTextLetterRow) row.clone());
				profileClone.praesentation = clone;
			}

			if (profileClone.references != null)
			{
				List<TitelTextLetterRow> clone = new ArrayList<TitelTextLetterRow>(
						profileClone.references.size());
				for (TitelTextLetterRow row : profileClone.references)
					clone.add((TitelTextLetterRow) row.clone());
				profileClone.references = clone;
			}

			if (profileClone.footer != null)
			{
				List<TitelTextLetterRow> clone = new ArrayList<TitelTextLetterRow>(
						profileClone.footer.size());
				for (TitelTextLetterRow row : profileClone.footer)
					clone.add((TitelTextLetterRow) row.clone());
				profileClone.footer = clone;
			}
			
			return profileClone;
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	
	
	
}
