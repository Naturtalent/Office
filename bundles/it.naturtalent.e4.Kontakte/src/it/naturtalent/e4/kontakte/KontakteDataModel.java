package it.naturtalent.e4.kontakte;

import it.naturtalent.e4.project.BaseBean;
import it.naturtalent.e4.project.address.AddressData;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.e4.core.services.events.IEventBroker;

public class KontakteDataModel extends BaseBean implements IKontakteDataModel
{
	public static final String PROP_ALLDATA = "kontakteData"; //$NON-NLS-N$

	// EventBroker meldet die Ereignisse
	private IEventBroker broker = null;

	// Model Factory
	private IKontakteDataFactory modelFactory = null;

	private String collectionName = null;

	// IDs der zum Modell hinzugefuegten Kontaktdaten
	private List<String> addedKontakte = new ArrayList<String>();

	// IDs/KontakteData der modifizierten Kontaktdaten
	private Map<String, KontakteData> modifiedKontakte = new HashMap<String, KontakteData>();

	// die geloeschten Kontaktdaten
	private List<KontakteData> deletedKontakte = new ArrayList<KontakteData>();

	// Modell Property 'PROP_ALLDATA'
	private List<KontakteData> modelKontakteData = new ArrayList<KontakteData>();

	@Override
	public List<KontakteData> getKontakteData()
	{
		return modelKontakteData;
	}

	@Override
	public void setKontakteData(List<KontakteData> kontakteData)
	{
		if ((kontakteData != null) && (!kontakteData.isEmpty()))
		{
			addedKontakte.clear();
			modifiedKontakte.clear();
			deletedKontakte.clear();
		}

		firePropertyChange(new PropertyChangeEvent(this, PROP_ALLDATA,
				this.modelKontakteData, this.modelKontakteData = kontakteData));
	}

	@Override
	public KontakteData getData(String id)
	{
		return getKontaktData(id);
	}

	@Override
	public void addData(KontakteData data)
	{
		String id = data.getId();
		if (StringUtils.isEmpty(id))
		{
			// neue Id
			id = KontakteData.makeIdentifier();
			data.setId(id);
		}
		else
		{
			// Abbruch, wenn Datansatz bereits existiert
			if (modelKontakteData.contains(data))
				return;
		}

		// zum Modell hinzufuegen
		modelKontakteData.add(data);
		
		// modifyrelevant nur, wenn noch nicht persistent vorhanden
		KontakteData persistData = modelFactory.loadKontakteData(this, id);
		if(persistData == null)		
			addedKontakte.add(id);

		if (broker != null)
			broker.post(IKontakteDataModel.KONTAKT_EVENT_ADD_KONTAKT, data);
		
		/*
		if (broker != null)
		{
			AddHelperEvent helper = new AddHelperEvent(this, data);
			broker.post(IKontakteDataModel.KONTAKT_EVENT_ADD_KONTAKT, helper);
		}
		*/
	}

	@Override
	public void updateData(KontakteData modifiedData)
	{
		if (modifiedData != null)
		{
			String id = modifiedData.getId();
			if (StringUtils.isNotEmpty(id))
			{
				// den persistent Datensatz laden
				KontakteData persistData = modelFactory.loadKontakteData(this,id);
				if (persistData == null)
				{
					// ein anderes Modell hat den Datensatz wahrscheinlich
					// geloescht
					modifiedKontakte.remove(id);
					return;
				}

				if (StringUtils.equals(modifiedData.getId(),
						persistData.getId()))
				{
					// Datensaetze vergleichen
					if (!persistData.equals(modifiedData))
					{
						// in die modifizierten Liste eintragen
						modifiedKontakte.put(id, modifiedData);
					}
					else
					{
						// 'modifiedData' wieder identisch mit persist, aus
						// modListe entfernen
						modifiedKontakte.remove(id);
					}

					// ueber die Aktualisierung informieren
					if (broker != null)
						broker.post(
								IKontakteDataModel.KONTAKT_EVENT_UPDATE_KONTAKT,
								persistData);
				}
			}
		}
	}

	@Override
	public void delete(String id)
	{
		if (StringUtils.isNotEmpty(id))
		{
			KontakteData existData = getData(id);
			if (existData != null)
			{
				// aus dem Modell entfernen
				modelKontakteData.remove(existData);

				// in deleteList eintragen
				if(!addedKontakte.contains(existData.getId()))				
					deletedKontakte.add(existData);

				// ggf. aus addedList/modlist entfernen				
				addedKontakte.remove(id);
				modifiedKontakte.remove(id);

				if (broker != null)
					broker.post(
							IKontakteDataModel.KONTAKT_EVENT_DELETE_KONTAKT,
							existData);
			}
		}
	}

	private KontakteData getKontaktData(String id)
	{
		for (KontakteData data : modelKontakteData)
			if (StringUtils.equals(id, data.getId()))
				return data;

		return null;
	}

	@Override
	public List<KontakteData> getDeletedKontakte()
	{
		return deletedKontakte;
	}

	@Override
	public List<String> getAddedKontakte()
	{
		return addedKontakte;
	}

	@Override
	public Map<String, KontakteData> getModifiedKontakte()
	{
		return modifiedKontakte;
	}

	@Override
	public boolean isModified()
	{
		if (!deletedKontakte.isEmpty())
			return true;

		if (!addedKontakte.isEmpty())
			return true;

		if (!modifiedKontakte.isEmpty())
			return true;

		return false;
	}

	@Override
	public void saveModel()
	{
		if (modelFactory != null)
			modelFactory.saveModel(this);

		if (broker != null)
			broker.post(IKontakteDataModel.KONTAKT_EVENT_SAVE_MODEL, this);
	}

	@Override
	public void loadModel()
	{
		if (modelFactory != null)
			modelFactory.loadModel(this);

		if (broker != null)
			broker.post(IKontakteDataModel.KONTAKT_EVENT_LOAD_MODEL, this);
	}

	public IEventBroker getBroker()
	{
		return broker;
	}

	public void setBroker(IEventBroker broker)
	{
		this.broker = broker;
	}

	public IKontakteDataFactory getModelFactory()
	{
		return modelFactory;
	}

	public void setModelFactory(IKontakteDataFactory modelFactory)
	{
		this.modelFactory = modelFactory;
	}

	public String getCollectionName()
	{
		return collectionName;
	}

	public void setCollectionName(String collectionName)
	{
		this.collectionName = collectionName;
	}
	
}
