package it.naturtalent.e4.office.ui;

public class DefaultWriteAdapterFactory implements IODFWriteAdapterFactory
{

	@Override
	public String getAdapterLabel()
	{		
		return "DefaultWrite";
	}

	@Override
	public IODFWriteAdapter createAdapter()
	{		
		return new ODFDefaultWriteAdapter();
	}

}
