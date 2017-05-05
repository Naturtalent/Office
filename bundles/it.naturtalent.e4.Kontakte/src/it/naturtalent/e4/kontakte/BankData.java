package it.naturtalent.e4.kontakte;


import it.naturtalent.e4.project.BaseBean;

import java.beans.PropertyChangeEvent;


public class BankData extends BaseBean implements IBankData, Cloneable
{
	// Properties
	public static final String PROP_IBAN = "iban"; //$NON-NLS-N$
	public static final String PROP_BIC = "bic"; //$NON-NLS-N$
	public static final String PROP_BANKINSTITUT = "bankinstitut"; //$NON-NLS-N$
	public static final String PROP_BANKKONTO = "bankkonto"; //$NON-NLS-N$
	public static final String PROP_BANKBLZ = "bankleitzahl"; //$NON-NLS-N$
	
	private String iban;
	private String bic;
	private String institut;
	private String kontonr;
	private String blz;

	

	public String getIban()
	{
		return iban;
	}

	public void setIban(String iban)
	{
		firePropertyChange(new PropertyChangeEvent(this, PROP_IBAN, this.iban,
				this.iban = iban));		
	}

	public String getBic()
	{
		return bic;
	}

	public void setBic(String bic)
	{
		firePropertyChange(new PropertyChangeEvent(this, PROP_BIC, this.bic,
				this.bic = bic));		
	}

	public String getInstitut()
	{
		return institut;
	}

	public void setInstitut(String institut)
	{
		firePropertyChange(new PropertyChangeEvent(this, PROP_BANKINSTITUT, this.institut,
				this.institut = institut));		
	}

	public String getKontonr()
	{
		return kontonr;
	}

	public void setKontonr(String kontonr)
	{
		firePropertyChange(new PropertyChangeEvent(this, PROP_BANKINSTITUT, this.kontonr,
				this.kontonr = kontonr));		
	}

	public String getBlz()
	{
		return blz;
	}

	public void setBlz(String blz)
	{
		firePropertyChange(new PropertyChangeEvent(this, PROP_BANKINSTITUT, this.blz,
				this.blz = blz));		
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bic == null) ? 0 : bic.hashCode());
		result = prime * result + ((blz == null) ? 0 : blz.hashCode());
		result = prime * result + ((iban == null) ? 0 : iban.hashCode());
		result = prime * result
				+ ((institut == null) ? 0 : institut.hashCode());
		result = prime * result + ((kontonr == null) ? 0 : kontonr.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BankData other = (BankData) obj;
		if (bic == null)
		{
			if (other.bic != null)
				return false;
		}
		else if (!bic.equals(other.bic))
			return false;
		if (blz == null)
		{
			if (other.blz != null)
				return false;
		}
		else if (!blz.equals(other.blz))
			return false;
		if (iban == null)
		{
			if (other.iban != null)
				return false;
		}
		else if (!iban.equals(other.iban))
			return false;
		if (institut == null)
		{
			if (other.institut != null)
				return false;
		}
		else if (!institut.equals(other.institut))
			return false;
		if (kontonr == null)
		{
			if (other.kontonr != null)
				return false;
		}
		else if (!kontonr.equals(other.kontonr))
			return false;
		return true;
	}
	
	/*
	@Override
	public boolean equals(Object obj)
	{
		return EqualsBuilder.reflectionEquals(this, obj);
	}
	*/
	
}
