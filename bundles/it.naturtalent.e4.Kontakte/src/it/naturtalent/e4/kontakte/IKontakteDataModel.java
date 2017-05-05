package it.naturtalent.e4.kontakte;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.e4.core.services.events.IEventBroker;

/**
 * @author Apel.Dieter
 *
 */
public interface IKontakteDataModel
{
	public static final String KONTAKT_EVENT = "kontaktevent/";
	public static final String KONTAKT_EVENT_ADD_KONTAKT = KONTAKT_EVENT+"addKontakt"; //$NON-NLS-N$
	public static final String KONTAKT_EVENT_UPDATE_KONTAKT = KONTAKT_EVENT+"updateKontakt"; //$NON-NLS-N$
	public static final String KONTAKT_EVENT_DELETE_KONTAKT = KONTAKT_EVENT+"deleteKontakt"; //$NON-NLS-N$
	public static final String KONTAKT_EVENT_LOAD_MODEL = KONTAKT_EVENT+"loadModel"; //$NON-NLS-N$
	public static final String KONTAKT_EVENT_SAVE_MODEL = KONTAKT_EVENT+"saveModel"; //$NON-NLS-N$
	public static final String KONTAKT_EVENT_SET_DIRTYABLE = KONTAKT_EVENT+"kontaktModelSetDirtyable"; //$NON-NLS-N$
	public static final String KONTAKT_EVENT_IMPORTED_KONTAKTE = KONTAKT_EVENT+"importedkontaktdata"; //$NON-NLS-N$
	
	public static final String KONTAKT_EVENT_ADDRESS_VALIDATION_STATUS = KONTAKT_EVENT+"addressvalidation"; //$NON-NLS-N$
	public static final String KONTAKT_EVENT_ADDRESS_MODIFIED = KONTAKT_EVENT+"addressmodified"; //$NON-NLS-N$
	public static final String KONTAKT_EVENT_KONTAKTE_MODIFIED = KONTAKT_EVENT+"kontaktemodified"; //$NON-NLS-N$
	
	//public static final String KONTAKT_EVENT_BANKDATA_MODIFIED = KONTAKT_EVENT+"kontaktebankdatamodified"; //$NON-NLS-N$
	public static final String KONTAKT_EVENT_DETAILS_MODIFIED = KONTAKT_EVENT+"kontaktedetailsmodified"; //$NON-NLS-N$
	
	public static final String KONTAKT_EVENT_SETSELECTION = KONTAKT_EVENT+"setselection"; //$NON-NLS-N$
	public static final String KONTAKT_EVENT_BEFORE_CHANGEDB = KONTAKT_EVENT+"beforechangedb"; //$NON-NLS-N$
	public static final String KONTAKT_EVENT_AFTER_CHANGEDB = KONTAKT_EVENT+"afterchangedb"; //$NON-NLS-N$
	public static final String KONTAKT_EVENT_CONTACTFILTER = KONTAKT_EVENT+"contactfilter"; //$NON-NLS-N$
	public static final String KONTAKT_EVENT_CONTACTPERSISTENCE = KONTAKT_EVENT+"contactpersistence"; //$NON-NLS-N$
	
	/**
	 * Die im Modell gespeicherten Kontakte zurueckgeben.
	 * 
	 * @return
	 */
	public List<KontakteData>getKontakteData();
	
	/**
	 * Das Modell mit einer Liste von Kontakten initialisieren
	 * 
	 * @param datas
	 */
	public void setKontakteData(List<KontakteData> kontakteData);
	
	
	/**
	 * Einen identifizierten Kontakt zurueckgeben.
	 * 
	 * @param id
	 * @return
	 */
	public KontakteData getData(String id);
	
	
	/**
	 * Einen neuen Kontakt im Modell speichern.
	 * 
	 * @param kontaktData
	 * @return
	 */
	public void addData(KontakteData kontaktData);

	/**
	 * Einen Kontakt im Modell aktualisieren.
	 * 
	 * @param kontaktData
	 * @return
	 */
	public void updateData(KontakteData kontaktData);

	
	/**
	 * Einen Kontakt im Modell entfernen.
	 * 
	 * @param kontaktDataId
	 */
	public void delete(String kontaktDataId);	
	
	
	/**
	 * Die Daten in Datenbank speichern
	 */
	public void saveModel();
	
	/**
	 * Die Daten in Datenbank speichern
	 */
	public void loadModel();

	/*
	 * Wurde das Modell modifiziert
	 */
	public boolean isModified();
	
	/**
	 * List mit den hinzugefuegten Datensaetzen zurueckgeben.
	 * Zurueckgegeben wird eine Liste mit den geloeschten Datensaetzen
	 * @return
	 */
	public List<KontakteData>getDeletedKontakte();
	
	/**
	 * List mit den geloeschten Datensaetzen zurueckgeben
	 * Zurueckgegeben wird eine Liste mit den ID der Datensaetze
	 * @return
	 */
	public List<String>getAddedKontakte();
	
	/**
	 * List mit den geanderten Datensaetzen zurueckgeben
	 * Zurueckgegeben wird eine Liste mit den ID der Datensaetze
	 * @return
	 */
	public Map<String, KontakteData>getModifiedKontakte();

}
