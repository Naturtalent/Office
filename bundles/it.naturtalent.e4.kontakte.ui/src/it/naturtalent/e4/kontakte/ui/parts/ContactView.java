package it.naturtalent.e4.kontakte.ui.parts;

import it.naturtalent.e4.kontakte.ContactCategoryModel;
import it.naturtalent.e4.kontakte.IKontakteDataFactory;
import it.naturtalent.e4.kontakte.IKontakteDataModel;
import it.naturtalent.e4.kontakte.KontakteData;
import it.naturtalent.e4.kontakte.KontakteDataModel;
import it.naturtalent.e4.kontakte.ui.actions.AbstractContactAction;
import it.naturtalent.e4.kontakte.ui.actions.SaveAction;
import it.naturtalent.e4.kontakte.ui.parts.ContactMasterComposite.ActionID;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.Persist;
import org.eclipse.e4.ui.di.PersistState;
import org.eclipse.e4.ui.internal.workbench.swt.WorkbenchSWTActivator;
import org.eclipse.e4.ui.model.application.ui.MDirtyable;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

public class ContactView
{	
	public static final String CONTACT_VIEW_ID = "it.naturtalent.contact.view"; //$NON-NLS-1$
	
	@Inject @Optional private IEclipseContext context;
	@Inject @Optional private IKontakteDataFactory kontaktDataFactory;
	@Inject @Optional private IEventBroker eventBroker;
	@Inject @Optional ESelectionService selectionService;
	
	// die zugrundeliegenden Modelle
	private KontakteDataModel kontakteModel;	
	private ContactCategoryModel contactCategoryModel;
	
	// Dialogsettings
	private IDialogSettings dialogSettings;
	public static final String KONTAKTENAME_SETTINGKEY  = "kontaktecollectionnamekey"; //$NON-NLS-N$	
	private String kontaktenameSettingKey = KONTAKTENAME_SETTINGKEY;
	public static final String PRAEFIX_CATEGORYFILTER_SETTINGS = "categoryfilter"; //$NON-NLS-1$

	// Name der verwendeten Datanbankcollection
	protected String contactCollectionName = null;
	
	private final FormToolkit formToolkit = new FormToolkit(Display.getDefault());
	private ContactMasterComposite contactMasterComposite;
	private ContactDetailsComposite contactDetailsComposite;	
	
	private @Inject MDirtyable dirtyable;

	// Aenderungen im Modell ueberwachen
	private EventHandler modelEventHandler = new EventHandler()
	{
		@Override
		public void handleEvent(Event event)
		{
			
			if(StringUtils.equals(event.getTopic(), IKontakteDataModel.KONTAKT_EVENT_SET_DIRTYABLE))
				dirtyable.setDirty((Boolean) event.getProperty(IEventBroker.DATA));

			// Aenderung der Datenbank
			if(StringUtils.equals(event.getTopic(), IKontakteDataModel.KONTAKT_EVENT_BEFORE_CHANGEDB))
			{
				//persistState();
			}
			
			if(StringUtils.equals(event.getTopic(), IKontakteDataModel.KONTAKT_EVENT_AFTER_CHANGEDB))
			{				
				KontakteDataModel eventDataModel = (KontakteDataModel) event.getProperty(IEventBroker.DATA);
				
				// hat unser Modell die Datenbank gewechselt
				if(eventDataModel == kontakteModel)
				{
					
					contactMasterComposite.setKontakteModel(kontakteModel);
					//contactDetailsComposite.setKontakteData(null);
					
					
					// KategorieModell
					contactCollectionName = kontakteModel.getCollectionName();
					contactCategoryModel = kontaktDataFactory
							.createContactCategoryModel(contactCollectionName);

					// KategorieModell an an Master u. Detail weitergeben
					contactMasterComposite
							.setCategoryModel(contactCategoryModel);
					contactDetailsComposite
							.setContactCategoryModel(contactCategoryModel);
				}
			}
		}
	};
	
	public ContactView()
	{
		dialogSettings = WorkbenchSWTActivator.getDefault().getDialogSettings();		
	}
	
	/**
	 * Create contents of the view part.
	 */
	@PostConstruct
	public void createControls(Composite parent)
	{
		parent.setLayout(new GridLayout(1, false));
		
		SashForm sashForm = new SashForm(parent, SWT.NONE);
		sashForm.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		sashForm.setSize(811, 609);
		
		contactMasterComposite = new ContactMasterComposite(sashForm, SWT.NONE);
		formToolkit.adapt(contactMasterComposite);
		formToolkit.paintBordersFor(contactMasterComposite);
		contactMasterComposite.setEventBroker(eventBroker);
		contactMasterComposite.setSelectionService(selectionService);	
				
		Map<ActionID, AbstractContactAction>actions = contactMasterComposite.getActionRegistry();
		Set<ActionID> keySet = actions.keySet();
		for(ActionID id : keySet)
			actions.get(id).setEventBroker(eventBroker);
				
		
		ScrolledComposite scrolledComposite = new ScrolledComposite(sashForm, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		formToolkit.adapt(scrolledComposite);
		formToolkit.paintBordersFor(scrolledComposite);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);
		
		Composite compositeDetail = formToolkit.createComposite(scrolledComposite, SWT.NONE);
		formToolkit.paintBordersFor(compositeDetail);
		GridLayout gl_compositeDetail = new GridLayout(1, false);
		gl_compositeDetail.marginHeight = 0;
		compositeDetail.setLayout(gl_compositeDetail);
		
		contactDetailsComposite = new ContactDetailsComposite(compositeDetail, SWT.NONE);
		contactDetailsComposite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		formToolkit.adapt(contactDetailsComposite);
		formToolkit.paintBordersFor(contactDetailsComposite);
		sashForm.setWeights(new int[] {470, 470});
		
		contactDetailsComposite.setEventBroker(eventBroker);		
		scrolledComposite.setContent(compositeDetail);
		scrolledComposite.setMinSize(compositeDetail.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		
		eventBroker.subscribe(IKontakteDataModel.KONTAKT_EVENT+"*",modelEventHandler);
		init();
	}
	
	private void init()
	{
		// Name der zuletzt benutzten Collection
		if (dialogSettings != null)
			contactCollectionName = dialogSettings.get(kontaktenameSettingKey);
		
		contactCollectionName = StringUtils
				.isNotEmpty(contactCollectionName) ? contactCollectionName
				: IKontakteDataFactory.KONTAKTEDATA_COLLECTION_NAME;

		// ein Modell erzeugen, Daten einlesen, Refresh der Mastertabelle
		if(kontaktDataFactory != null)
		{
			// Modell erzeugen und mit den Daten der Collection 'contactCollectionName' fuellen
			kontakteModel = kontaktDataFactory.createModel(contactCollectionName);
			kontakteModel.setBroker(eventBroker);
			kontakteModel.loadModel();

			// KontakteModell an Master u. Detail weitergeben
			contactMasterComposite.setKontakteModel(kontakteModel);
			contactDetailsComposite.setModel(kontakteModel);
			contactMasterComposite.setContactDetailsComposite(contactDetailsComposite);

			// KategorieModell
			contactCategoryModel = kontaktDataFactory
					.createContactCategoryModel(contactCollectionName);

			// KategorieModell an an Master u. Detail weitergeben
			contactMasterComposite.setCategoryModel(contactCategoryModel);
			contactDetailsComposite.setContactCategoryModel(contactCategoryModel);
						
			contactDetailsComposite.setKontakteData(null);						
		}
	}
		
	/*
	 * Den im Master selektierten Eintrag in der Detailseite anzeigen
	 */	
	@Inject
	public void setKontakteData(@Optional @Named(IServiceConstants.ACTIVE_SELECTION)
	KontakteData kontakteData)
	{				
		if(contactDetailsComposite != null)		
			contactDetailsComposite.setKontakteData(kontakteData);		
	}
	
	@Persist
	public void persist()
	{
		kontakteModel.saveModel();
	}

	@PreDestroy
	public void dispose()
	{
		eventBroker.unsubscribe(modelEventHandler);
		
		// in Dialogsettings speichern
		if (dialogSettings != null)
		{
			// Collectionname speichern
			dialogSettings.put(kontaktenameSettingKey,
					kontakteModel.getCollectionName());
		}
		
		dirtyable.setDirty(false);
	}

	@Focus
	public void setFocus()
	{	
		contactMasterComposite.getTableViewer().getControl().setFocus();
	}

	public void setEventBroker(IEventBroker eventBroker)
	{
		this.eventBroker = eventBroker;
	}

	public void setKontaktDataFactory(IKontakteDataFactory kontaktDataFactory)
	{
		this.kontaktDataFactory = kontaktDataFactory;
	}

	public void setSelectionService(ESelectionService selectionService)
	{
		this.selectionService = selectionService;
	}
	
	

}
