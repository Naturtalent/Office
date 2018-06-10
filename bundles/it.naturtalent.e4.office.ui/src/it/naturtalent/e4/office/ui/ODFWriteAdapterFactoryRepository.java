package it.naturtalent.e4.office.ui;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

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

	@Override
	public IODFWriteAdapterFactory getWriteAdapter(String factoryClass)
	{
		for(IODFWriteAdapterFactory adapterFactory : writeAdapterFactories)
		{
			if(StringUtils.equals(adapterFactory.getClass().getName(), factoryClass))
				return adapterFactory;
		}
			
		return null;
	}

}
