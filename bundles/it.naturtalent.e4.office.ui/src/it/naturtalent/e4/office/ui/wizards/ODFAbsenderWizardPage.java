package it.naturtalent.e4.office.ui.wizards;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EventObject;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.internal.workbench.E4Workbench;
import org.eclipse.e4.ui.internal.workbench.swt.WorkbenchSWTActivator;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.workbench.IWorkbench;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CommandStackListener;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.ui.view.ECPRendererException;
import org.eclipse.emf.ecp.ui.view.swt.ECPSWTViewRenderer;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.CreateChildCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.emfstore.internal.client.model.changeTracking.commands.EMFStoreBasicCommandStack;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.odftoolkit.simple.TextDocument;

import it.naturtalent.e4.office.ui.OfficeUtils;
import it.naturtalent.office.model.address.Absender;
import it.naturtalent.office.model.address.AddressPackage;
import it.naturtalent.office.model.address.Adresse;
import it.naturtalent.office.model.address.Empfaenger;
import it.naturtalent.office.model.address.Kontakt;
import it.naturtalent.office.model.address.Sender;



/**
 * Wizardseite zur Definition der Absenderangaben im Standardmodus.
 * Im Defaultmodus gibt es keine praeferenzierten Absender,
 * Der zuletzt in das Dokument geschriebene Absender (Name) wird im DialogSetting gespeichert 
 * 
 * @author dieter
 *
 */
public class ODFAbsenderWizardPage extends WizardPage implements IWriteWizardPage
{
	// Name dieser WizardPage	
	private final static String SENDER_PAGE_NAME = "ODF_ABSENDER";
	
	private static final String DEFAULT_ABSENDERNAME = "DefaultAbsender"; //$NON-NLS-1$
	
	private String officeContext;
	
	private IDialogSettings dialogSettings = WorkbenchSWTActivator.getDefault().getDialogSettings();
	
	// Key des DialogSetting Absenders
	private static final String SETTINGABSENDER = "settingabsender";
	protected String dialogSettingName = SETTINGABSENDER;
	
	private Sender absenderSet;
	protected Absender selectedAbsender;
	
	private IEventBroker eventBroker;
	
	//private EditingDomain domain;

	/**
	 * Create the wizard.
	 */
	public ODFAbsenderWizardPage()
	{
		super(SENDER_PAGE_NAME);
		setMessage("einen Absender auswählen");
		setTitle("Absender");
		setDescription("Angaben zum Absender");		
	}
	
	@PostConstruct
	private void postConstruct(@Optional IEventBroker eventBroker)
	{
		this.eventBroker = eventBroker;
	}
	
	/**
	 * Create contents of the wizard.
	 * @param parent
	 */
	public void createControl(Composite parent)
	{
		Composite container = new Composite(parent, SWT.NULL);	
		setControl(container);
		container.setLayout(new GridLayout(2, false));
		
		officeContext = ((ODFDefaultWriteAdapterWizard)getWizard()).getOfficeContext();
		
		try
		{
			absenderSet = OfficeUtils.getSender();	
			
			// gibt es schon Absender oder muss initialisiert werden
			EList<Absender>allAbsenders = absenderSet.getSenders();

			Absender existAbsender = null;
			if((allAbsenders != null) && !allAbsenders.isEmpty())
			{				
				for (Absender absender : allAbsenders)
					if (StringUtils.equals(absender.getContext(), officeContext))
					{
						existAbsender = absender;
						break;
					}											
			}
			
			// noch keine Absender vorhanden oder keine im OfficeContext
			if((allAbsenders == null) || allAbsenders.isEmpty() || existAbsender == null)
			{	
				//Initialisierung
				Absender defaultAbsender = createAbsender(DEFAULT_ABSENDERNAME, officeContext);
				
				// Absenderadresse erzeugen	und zum Absender hinzufuegen			
				EClass adresseClass = AddressPackage.eINSTANCE.getAdresse();
				Adresse defaultAdresse = (Adresse) EcoreUtil.create(adresseClass);
				defaultAdresse.setName(DEFAULT_ABSENDERNAME);
				defaultAbsender.setAdresse(defaultAdresse);

				absenderSet.getSenders().add(defaultAbsender);
			}
			
			// Liste der nichtloeschbaren Absender in Eclipse4Context einbringen
			List<Absender>unRemovables = new ArrayList<Absender>();			
			Absender staticAbsender = OfficeUtils.findAbsenderByName(DEFAULT_ABSENDERNAME, officeContext);			
			unRemovables.add(staticAbsender);
			E4Workbench.getServiceContext().set(OfficeUtils.ABSENDER_UNREMOVABLES, unRemovables);

			// Renderer zeigt die Absender  (filtert die Absender auf 'officecontext' im MasterDetailView) 
			ECPSWTViewRenderer.INSTANCE.render(container, (EObject) absenderSet);	
			
		} catch (ECPRendererException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// im CreateModus wird der Absdender aus dem DialogSetting selektiert
		ODFDefaultWriteAdapterWizard defaultWizard = (ODFDefaultWriteAdapterWizard) getWizard();
		if(defaultWizard.isWizardModus() == ODFDefaultWriteAdapterWizard.WIZARDCREATEMODE)
		{	
			// den im Dialogsetting gespeicherte Absender im MasterView selektieren
			String settingAbsenderName = dialogSettings.get(dialogSettingName);
			if(StringUtils.isNotEmpty(settingAbsenderName))
			{
				Absender absender = OfficeUtils.findAbsenderByName(settingAbsenderName,officeContext);
				eventBroker.post(OfficeUtils.SET_ABSENDERMASTER_SELECTION_EVENT, absender);
			}
		}			
	}

	/**
	 * Absender im DetailView selektiert
	 * @param absender
	 */
	@Inject
	@Optional
	public void handleDetailModelChangedEvent(@UIEventTopic(OfficeUtils.ABSENDER_DETAIL_SELECTED_EVENT) Absender absender)
	{
		this.selectedAbsender = absender;
	}
		
	@Inject
	@Optional
	public void handleAbsenderSelection(@UIEventTopic(OfficeUtils.ABSENDERMASTER_SELECTED_EVENT) Object object)
	{
		if (object instanceof Absender)		
			selectedAbsender = (Absender) object;
	}
	
	/**
	 * DetailRenderer meldet das ein neuer Absender hinzugefuegt wurde
	 * 
	 * @param object
	 */
	@Inject
	@Optional
	public void handleAddAbsender(@UIEventTopic(OfficeUtils.ADD_NEWSENDER_EVENT) Object object)
	{
		if (object instanceof Sender)
		{
			Sender senderSet = (Sender) object;
			EList<Absender>absenderList = senderSet.getSenders();
			Absender addedAbsender = absenderList.get(absenderList.size()-1);										
			addedAbsender.setContext(officeContext);	
			
			// Absenderadresse erzeugen	und zum Absender hinzufuegen			
			EClass adresseClass = AddressPackage.eINSTANCE.getAdresse();
			Adresse adresse = (Adresse) EcoreUtil.create(adresseClass);
			adresse.setName(addedAbsender.getName());
			addedAbsender.setAdresse(adresse);
			
			// die Adresse des neuen Absenders selektieren
			eventBroker.post(OfficeUtils.SET_ABSENDERMASTER_SELECTION_EVENT , adresse);
		}
	}
	
	/*
	 * Aktion 'aus Datenbank kopieren' wurde vom 'SenderDetailsRenderer' ausgeloest.
	 * 
	 */
	@Inject
	@Optional
	public void handleAddExisting(@UIEventTopic(OfficeUtils.ADD_EXISTING_SENDER) Object object)
	{	
		Kontakt dbKontakt = OfficeUtils.readKontaktFromDatabase();
		if(dbKontakt != null)
		{
			Absender absender = createAbsender(dbKontakt.getName(), officeContext);
			Adresse adresse = dbKontakt.getAdresse();
			absender.setAdresse(adresse);

			// den Absender mit der Adresse aus der Datenbank hinzufuegen
			absenderSet.getSenders().add(absender);
			
			// den neuen Absender selektieren
			eventBroker.post(OfficeUtils.SET_ABSENDERMASTER_SELECTION_EVENT , absender);
		}
	}
	
	/* 
	 * Dieser Aufruf erfolgt im Zuge einer 'doPerformFinish()' Action des Wizards.
	 * Die Adresse des selektierten Absenders wird in das TextDokument geschrieben.
	 * Der Name des Absenders wird im DialogSetting gespeichert.
	 * 
	 * Der richtige Zeitpunkt temporaer erzeugte Absender (beim Oeffnen eines Dokuments) wieder 
	 * aus dem EFM-Modell zu entfernen.
	 * 
	 */	
	@Override
	public void writeToDocument (TextDocument odfDocument)
	{
		// temporaere Absender aus dem Modell entfernen
		removeTemporaereAbsender();
		
		if(OfficeUtils.writeAbsenderToDocument(odfDocument, selectedAbsender))
		{
			// Name des Absenders im DialogSetting speichern
			if(dialogSettings != null)
				dialogSettings.put(dialogSettingName, selectedAbsender.getName());
		}
	}
	
	/* 
	 * Absenderdaten aus dem Dokument lesen und temporaer in einem EMF-Objekt speichern.
	 * Der temp. Absender wird dem ODFDefaultWriteAdapterWizard.DEFAULT_OFFICECONTEXT zugeordnet.
	 * 
	 */
	@Override	
	public void readFromDocument(TextDocument odfDocument)
	{
		Absender tempAbsender = OfficeUtils.readAbsenderFromDocument(odfDocument);
		tempAbsender.setContext(officeContext);	
	}
	
	@Override
	public void cancelPage(TextDocument odfDocument)
	{
		removeTemporaereAbsender();
	}

	/*
	 * die temporaer erzeugten Absender-Objekte (beim Einlesen aus dem Dokument erzeugt) wieder loeschen
	 */
	protected void removeTemporaereAbsender()
	{
		List<Absender>removeList = new ArrayList<Absender>();
		
		List<Absender>absenderList = absenderSet.getSenders();		
		for(Absender absender : absenderList)
		{
			if(StringUtils.equals(absender.getName(), OfficeUtils.LOADED_ABSENDER))
				removeList.add(absender);
		}
		
		absenderSet.getSenders().removeAll(removeList);
	}
	
	/*
	 * Neuen Absender erzeugen mit Namen und OfficeContext
	 */
	private Absender createAbsender(String absenderName, String officeContext)
	{
		EClass absenderClass = AddressPackage.eINSTANCE.getAbsender();
		Absender absender = (Absender) EcoreUtil.create(absenderClass);
		absender.setName(absenderName);
		absender.setContext(officeContext);
		return absender;
	}

	

}
