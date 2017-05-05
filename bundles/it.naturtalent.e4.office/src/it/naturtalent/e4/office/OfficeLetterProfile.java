package it.naturtalent.e4.office;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Document;

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
	private List<AbsenderRow>absender;	
	private List<PraesentationRow>praesentation;
	private List<ReferenceRow>references;
	private List<SignatureRow> signature;
	private List<FooterRow>footer;	
	
	
	public OfficeLetterProfile()
	{
		super();
		absender = new ArrayList<AbsenderRow>();		
		references = new ArrayList<ReferenceRow>();
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
	
	public List<SignatureRow> getSignature()
	{
		return signature;
	}

	public void setSignature(List<SignatureRow> signature)
	{
		firePropertyChange(new PropertyChangeEvent(this, PROP_SIGNATURE, this.signature,
				this.signature = signature));	
	}

	public List<FooterRow> getFooter()
	{
		return footer;
	}

	public void setFooter(List<FooterRow> footer)
	{
		firePropertyChange(new PropertyChangeEvent(this, PROP_FOOTER, this.footer,
				this.footer = footer));		
	}

	public List<ReferenceRow> getReferences()
	{
		return references;
	}

	public void setReferences(List<ReferenceRow> references)
	{
		firePropertyChange(new PropertyChangeEvent(this, PROP_REFERENCES, this.references,
				this.references = references));		
	}
	
	public List<AbsenderRow> getAbsender()
	{
		return absender;
	}

	public void setAbsender(List<AbsenderRow> absender)
	{
		firePropertyChange(new PropertyChangeEvent(this, PROP_ABSENDER, this.absender,
				this.absender = absender));			
	}
	
	public List<PraesentationRow> getPraesentation()
	{
		return praesentation;
	}

	public void setPraesentation(List<PraesentationRow> praesentation)
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
				List<AbsenderRow> clone = new ArrayList<AbsenderRow>(
						profileClone.absender.size());
				for (AbsenderRow row : profileClone.absender)
					clone.add((AbsenderRow) row.clone());
				profileClone.absender = clone;
			}

			if (profileClone.praesentation != null)
			{
				List<PraesentationRow> clone = new ArrayList<PraesentationRow>(
						profileClone.praesentation.size());
				for (PraesentationRow row : profileClone.praesentation)
					clone.add((PraesentationRow) row.clone());
				profileClone.praesentation = clone;
			}

			if (profileClone.references != null)
			{
				List<ReferenceRow> clone = new ArrayList<ReferenceRow>(
						profileClone.references.size());
				for (ReferenceRow row : profileClone.references)
					clone.add((ReferenceRow) row.clone());
				profileClone.references = clone;
			}

			if (profileClone.footer != null)
			{
				List<FooterRow> clone = new ArrayList<FooterRow>(
						profileClone.footer.size());
				for (FooterRow row : profileClone.footer)
					clone.add((FooterRow) row.clone());
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
