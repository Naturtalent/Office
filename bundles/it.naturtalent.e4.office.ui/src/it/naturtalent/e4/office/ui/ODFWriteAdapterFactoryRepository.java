package it.naturtalent.e4.office.ui;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dieter
 *
 */
public class ODFWriteAdapterFactoryRepository implements IODFWriteAdapterFactoryRepository
{

	private static List<IODFWriteAdapterFactory> writeAdapterFactories = new ArrayList<IODFWriteAdapterFactory>();
	
	@Override
	public List<IODFWriteAdapterFactory> getWriteAdapterFactories()
	{		
		return writeAdapterFactories;
	}

}
