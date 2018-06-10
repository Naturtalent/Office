package it.naturtalent.e4.office.ui;

import java.util.List;

/**
 * Interface des Repository in dem alle AdapterFactories gespeichert sind.
 * 
 * @author dieter
 *
 */
public interface IODFWriteAdapterFactoryRepository
{
	// gibt alle registrierten WriteAdapterFactories in einer Liste zurueck
	public List <IODFWriteAdapterFactory> getWriteAdapterFactories();
	
	public IODFWriteAdapterFactory getWriteAdapter(String factoryClass);
}
