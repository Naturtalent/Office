package it.naturtalent.e4.office.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import it.naturtalent.e4.office.odf.shapes.IOdfElementFactory;
import it.naturtalent.e4.office.odf.shapes.IOdfElementFactoryRegistry;

public class OdfElementFactoryRegistry implements IOdfElementFactoryRegistry
{

	private static Map<String, IOdfElementFactory> odfElementFactoryRegistry = new HashMap<String, IOdfElementFactory>();
	
	@Override
	public void registerOdfElementFactory(String factoryName,
			IOdfElementFactory odfElementFactory)
	{
		odfElementFactoryRegistry.put(factoryName, odfElementFactory);
	}

	@Override
	public IOdfElementFactory getOdfElementFactory(String factoryKey)
	{
		return odfElementFactoryRegistry.get(factoryKey);
	}

	@Override
	public String[] getOdfElementFatoryNames()
	{
		Set<String> keySet = odfElementFactoryRegistry.keySet();
		return keySet.toArray(new String[keySet.size()]);
	}

}
