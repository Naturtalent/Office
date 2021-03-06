package it.naturtalent.e4.office.ui;

/**
 * Diese Factory erzeugt den DefaultWriteAdapter.
 *  
 * @author dieter
 *
 */
public class DefaultWriteAdapterFactory implements IODFWriteAdapterFactory
{

	/*
	 * Anzeige unter diesem Namen im KontextMenue
	 */
	@Override
	public String getAdapterLabel()
	{		
		return "Standard";
	}

	/*
	 * Diesen Adapter erzeugt die Factory
	 */
	@Override
	public IODFWriteAdapter createAdapter()
	{		
		return new ODFDefaultWriteAdapter();
	}

}
