package it.naturtalent.e4.office.ui;

/**
 * Ueber diese Factory wird der DefaultWriteAdapter erzeugt.
 *  
 * @author dieter
 *
 */
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
