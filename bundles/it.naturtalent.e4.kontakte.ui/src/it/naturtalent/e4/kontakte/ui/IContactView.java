package it.naturtalent.e4.kontakte.ui;

import it.naturtalent.e4.kontakte.KontakteDataModel;

public interface IContactView
{
	/**
	 * Die Datenbank mit dem Namen 'collectionName' einstellen.
	 *   
	 * @param collectionName
	 */
	public void setContactCollection(String collectionName);
	
	/**
	 * Rueckgabe des momentan verwendeten Datenbanknamens
	 * 
	 * @return
	 */
	public String getContactCollection();
	
	/**
	 * Das versendete Modell zurueckgeben-
	 * 
	 * @return
	 */
	public KontakteDataModel getKontakteModel();
}
