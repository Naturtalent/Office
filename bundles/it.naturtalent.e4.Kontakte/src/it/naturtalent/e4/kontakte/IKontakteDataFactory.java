package it.naturtalent.e4.kontakte;

import java.util.List;

public interface IKontakteDataFactory
{
	public static final String KONTAKTEDATA_COLLECTION_NAME = "kontaktedatadata";
	
	public static final String ANREDEN_PREFERENCE_TYPE = "anreden";
	public static final String CATEGORIES_PREFERENCE_TYPE = "categories";

	/**
	 * Ein Model mit den Kontaktdaten aus der Datenbank erzeugen.
	 * Das Modell enthaelt die Daten der Standardkollektion 'KONTAKTEDATA_COLLECTION_NAME'
	 * 
	 * @return
	 */
	public KontakteDataModel createModel();
	
	/**
	 * Ein Modell mit den Kontaktdaten der Collection 'collectionName'
	 * @param collectionName
	 * @return
	 */
	public KontakteDataModel createModel(String collectionName);
	
	public ContactCategoryModel createContactCategoryModel(String collectionName);
	
	/**
	 * Die Modelldaten aus der Datenbank laden.
	 */
	public void loadModel(KontakteDataModel model);
	
	/**
	 * Einen einzelnen Datensatz laden.
	 */
	public KontakteData loadKontakteData(KontakteDataModel model, String id);

	
	/**
	 * Ein Modelldaten werden in der Datenbank gespeichet.
	 */
	public void saveModel(KontakteDataModel model);

	
	/**
	 * Eine Collectionnamen registrieren
	 * 
	 * @param name
	 * @param label
	 */
	public void registerCollectionName(String name, String label);
	
	
	/**
	 * Alle registrierten Collectionnamen zurueckgeben
	 * @return
	 */
	public List<String> getCollectionNames();
	
	
	/**
	 * 
	 * @param collectionName
	 * @return
	 */
	public String getCollectionLabel(String collectionName);

	/**
	 * Einen AnredenPreferenzKey registrieren
	 * 
	 * @param name
	 * @param label
	 */
	public void registerKontaktPreference(String collectionName, String type, String prefereceNode, String preferenceKey);
	
	
	/**
	 * Preferenzen im Zusammenhang mit den Kontakten zurueckgeben. Ueber 'collectionName' und 'type' kann gefiltert werden. 
	 * @return
	 */
	public Object getKontaktPreference(String collectionName, String type);
	
		
	/*
	public List<IKontakteData>getData();
	public IKontakteData getData(String id);
	public String saveOrUpdate(IKontakteData kontaktData);
	public void delete(String kontaktDataId);
	*/
}
