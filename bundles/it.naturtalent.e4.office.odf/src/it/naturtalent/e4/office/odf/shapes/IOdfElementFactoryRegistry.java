package it.naturtalent.e4.office.odf.shapes;


public interface IOdfElementFactoryRegistry
{
	public void registerOdfElementFactory(String factoryName, IOdfElementFactory odfElementFactory);
	public IOdfElementFactory getOdfElementFactory(String factoryKey);
	public String [] getOdfElementFatoryNames();
}
