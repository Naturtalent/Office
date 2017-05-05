package it.naturtalent.e4.kontakte.ui.actions;

import it.naturtalent.e4.kontakte.ContactCategoryModel;
import it.naturtalent.e4.kontakte.IKontakteDataFactory;
import it.naturtalent.e4.kontakte.KontakteData;
import it.naturtalent.e4.kontakte.KontakteDataModel;
import it.naturtalent.e4.kontakte.KontaktePreferences;
import it.naturtalent.e4.kontakte.ui.dialogs.KontakteDialog;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.di.extensions.Preference;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.jface.action.Action;
import org.eclipse.swt.widgets.Shell;

public class NewContactWizardAction extends Action
{	
	@Inject @Optional private IEclipseContext context;
	@Inject @Optional private IKontakteDataFactory kontakDataFactory;
	@Inject @Optional private IEventBroker eventBroker;
	
	private AddAction addAction;
	private KontakteDataModel kontakteDataModel;
	private ContactCategoryModel contactCategoryModel;
	
	protected String collectionName = IKontakteDataFactory.KONTAKTEDATA_COLLECTION_NAME;
	
	@Override
	public void run()
	{		
		// eine KontakteData Add-Action erzeugen
		addAction = new AddAction();
		addAction.setEventBroker(eventBroker);
		
		// Modelle zur Add-Action hinzufuegen
		kontakteDataModel = kontakDataFactory.createModel();
		kontakteDataModel.setCollectionName(collectionName);
		kontakteDataModel.setBroker(eventBroker);		
		addAction.setKontakteDataModel(kontakteDataModel);		
		contactCategoryModel = kontakteDataModel.getModelFactory()
				.createContactCategoryModel(collectionName);		
		addAction.setContactCategoryModel(contactCategoryModel);

		// Aktion ausfuehren (neuen Datensatz hinzufuegen)
		addAction.run();
		
		// Modelldaten persistent speichern		
		if(kontakteDataModel.isModified())
			kontakteDataModel.saveModel();
		if(contactCategoryModel.isModified())
			contactCategoryModel.saveContactCategories();
	}
	

}
