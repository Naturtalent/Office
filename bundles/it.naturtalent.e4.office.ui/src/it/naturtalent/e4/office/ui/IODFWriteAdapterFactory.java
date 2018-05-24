package it.naturtalent.e4.office.ui;

/**
 * Diese Klasse erzeugt die unterschiedlichen Adapter.
 * Alle verfuegbaren Factories werden in einem zentralen Repository gespeichert.
 * 
 * @author dieter
 *
 */
public interface IODFWriteAdapterFactory
{
	// Name des Adapters, der mit dieser Fabrikation erzeugt wird.
	public String getAdapterLabel();
	
	// den Adapter erzeugen
	public IODFWriteAdapter createAdapter();
}
