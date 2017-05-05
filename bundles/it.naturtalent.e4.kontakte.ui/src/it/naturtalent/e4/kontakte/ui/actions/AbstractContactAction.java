package it.naturtalent.e4.kontakte.ui.actions;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.jface.action.Action;

import it.naturtalent.e4.kontakte.ContactCategoryModel;
import it.naturtalent.e4.kontakte.IKontakteDataModel;
import it.naturtalent.e4.kontakte.KontakteData;
import it.naturtalent.e4.kontakte.KontakteDataModel;

public abstract class AbstractContactAction extends Action
{		
	protected KontakteDataModel kontakteDataModel;
	protected IEventBroker eventBroker;
	//protected String anredePreferences;
	protected ContactCategoryModel contactCategoryModel;

	/*
	public void setModel(KontakteDataModel model)
	{
		this.kontakteDataModel = model;
	}
	*/
	
	public KontakteDataModel getKontakteDataModel()
	{
		return kontakteDataModel;
	}

	public void setKontakteDataModel(KontakteDataModel kontakteDataModel)
	{
		this.kontakteDataModel = kontakteDataModel;
	}


	public ContactCategoryModel getContactCategoryModel()
	{
		return contactCategoryModel;
	}

	public void setContactCategoryModel(ContactCategoryModel contactCategoryModel)
	{
		this.contactCategoryModel = contactCategoryModel;
	}



	public void setEventBroker(IEventBroker eventBroker)
	{
		this.eventBroker = eventBroker;
	}
	
	/*
	public String getAnredePreferences()
	{
		return anredePreferences;
	}
	*/

	/*
	public void setAnredePreferences(String anredePreferences)
	{
		this.anredePreferences = anredePreferences;
	}
	*/
	
	public void postSetSelectionEvent(KontakteData kontakteData)
	{
		if(eventBroker != null)
			eventBroker.post(IKontakteDataModel.KONTAKT_EVENT_SETSELECTION, kontakteData);
	}

	@Override
	public abstract void run();

}
