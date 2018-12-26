package it.naturtalent.e4.office.ui.wizards;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EventObject;
import java.util.List;

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
 * 
 * @author dieter
 *
 */
public class ODFAbsenderWizardPage extends WizardPage implements IWriteWizardPage
{
	
	// Key des DialogSetting Absenders
	private static final String SETTINGABSENDER = "settingabsender";
	
	private IDialogSettings dialogSettings = WorkbenchSWTActivator.getDefault().getDialogSettings();
	
	private Sender senders;
	private Absender selectedAbsender;
	
	private IEventBroker eventBroker;
	
	private EditingDomain domain;
	
	/*
	 * Auf bestimmte Aenderungen im EMF-CommandStack reagieren
	 * 
	 * Beim Hinzuefegen eines Absenders muss der DefaultOfficeContext gesetzt werden, sonst wird
	 * er nicht durch den Renderer angezeigt.
	 * 
	 */
	private class SenderCommandListener implements CommandStackListener
	{
		@Override
		public void commandStackChanged(EventObject event)
		{
			EMFStoreBasicCommandStack commandStack = (EMFStoreBasicCommandStack) event.getSource();
			Command command = commandStack.getMostRecentCommand();	
			
			// ADD-Aktion ausgeloest durch Kontextmenue des Masters
			if (command instanceof CreateChildCommand)
			{
				// ADD-Aktion, ausgeloest durch Kontextmenue im Master
				// - selektiert den neue Kontakt im Mastertree
				CreateChildCommand addCommand = (CreateChildCommand) command;
				Collection<?>createResults = addCommand.getResult();
				Object createdObj = createResults.iterator().next();
				if(createdObj instanceof Absender)
				{
					postAdded(createdObj);					 
				}
			}
				
			// ADD-Aktion ausgeloest durch Toolbaxmenue des Details
			if (command instanceof AddCommand)
			{				
				Collection<?>addResults = command.getResult();
				Object addedObj = addResults.iterator().next();
				if(addedObj instanceof Absender)
				{					
					Absender absender = (Absender) addedObj;					
					if(!StringUtils.equals(absender.getName(), OfficeUtils.LOADED_ABSENDER))
					{	
						postAdded(addedObj);
					}
				}
			}			
		}
		
		// Nachbearbeitung des neuhinzugefuegten Absenders
		private void postAdded(Object addedObj)
		{
			if (addedObj instanceof Absender)
			{
				Absender absender = (Absender) addedObj;
				
				// Absender einem OfficeContext zuordnen
				ODFDefaultWriteAdapterWizard wizard = (ODFDefaultWriteAdapterWizard) getWizard();				
				absender.setContext(wizard.getOfficeContext());
				
				// Absenderadresse erzeugen	und hinzufuegen				
				EClass adresseClass = AddressPackage.eINSTANCE.getAdresse();
				Adresse adresse = (Adresse) EcoreUtil.create(adresseClass);
				absender.setAdresse(adresse);
			}
		}
		
		
	}
	private SenderCommandListener senderCommandListener = new SenderCommandListener();

		
	/**
	 * Create the wizard.
	 */
	public ODFAbsenderWizardPage()
	{
		super(ODFDefaultWriteAdapterWizard.SENDER_PAGE_NAME);
		setMessage("einen Absender auswählen");
		setTitle("Absender");
		setDescription("Angaben zum Absender");
		
		MApplication currentApplication = E4Workbench.getServiceContext().get(IWorkbench.class).getApplication();
		eventBroker = currentApplication.getContext().get(IEventBroker.class);
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
		
		try
		{							
			// Renderer filtert die Absender auf 'officecontext' im MasterDetailView 
			senders = OfficeUtils.getSender();
			ECPSWTViewRenderer.INSTANCE.render(container, (EObject) senders);	
			
		} catch (ECPRendererException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		// Einen CommandStackListener installieren
		domain = AdapterFactoryEditingDomain.getEditingDomainFor(senders);
		EMFStoreBasicCommandStack commandStack = (EMFStoreBasicCommandStack) domain.getCommandStack();
		domain.getCommandStack().addCommandStackListener(senderCommandListener);
		
		// im CreateModus wird der Absdender aus dem DialogSetting selektiert
		ODFDefaultWriteAdapterWizard defaultWizard = (ODFDefaultWriteAdapterWizard) getWizard();
		if(defaultWizard.isWizardModus() == ODFDefaultWriteAdapterWizard.WIZARDCREATEMODE)
		{	
			// den im Dialogsetting gespeicherte Absender im MasterView selektieren
			String settingAbsenderName = dialogSettings.get(SETTINGABSENDER);
			if(StringUtils.isNotEmpty(settingAbsenderName))
			{
				Absender absender = OfficeUtils.findAbsenderByName(
						settingAbsenderName,
						ODFDefaultWriteAdapterWizard.DEFAULT_OFFICECONTEXT);
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
	
	/**
	 * Absender im MasterView selektiert
	 * @param absender
	 */
	@Inject
	@Optional
	public void handleMasterModelChangedEvent(@UIEventTopic(OfficeUtils.ABSENDERMASTER_SELECTED_EVENT) Absender absender)
	{
		this.selectedAbsender = absender;
	}
	
	/*
	 * Der 'AddExistingButton' soll die Uebernahme einer Adresse aus der Kontaktdatenbank triggern.
	 * 
	 */
	@Inject
	@Optional
	public void handleAddExisting(@UIEventTopic(OfficeUtils.ADD_EXISTING_SENDER) Object object)
	{	
		Kontakt dbKontakt = OfficeUtils.readKontaktFromDatabase();
		if(dbKontakt != null)
		{
			EClass absenderClass = AddressPackage.eINSTANCE.getAbsender();
			Absender absender = (Absender) EcoreUtil.create(absenderClass);
			absender.setName(dbKontakt.getName());
			absender.setAdresse(dbKontakt.getAdresse());
			absender.setContext(((ODFDefaultWriteAdapterWizard) getWizard()).getOfficeContext());

			// den Absender mit der Adresse aus der Datenbank hinzufuegen
			senders.getSenders().add(absender);
			
			// den neuen Absender selektieren
			eventBroker.post(OfficeUtils.SET_ABSENDERMASTER_SELECTION_EVENT , absender);
		}
	}
	
	/*
	 * Schreibt den selektierten Absender, bzw. dessen Adresse in das Dokument.
	 * 
	 */
	@Override
	public void writeToDocument (TextDocument odfDocument)
	{
		if(OfficeUtils.writeToDocument(odfDocument, selectedAbsender))
		{
			// Name des Absenders im DialogSetting speichern
			dialogSettings.put(SETTINGABSENDER, selectedAbsender.getName());
		}
		
		// temporaere Absender (wird beim Laden des Dokuments erzeugt) loeschen
		removeTemporaereAbsender();
	}
	
	/* 
	 * Absenderdaten aus dem Dokument lesen und temporaer in einem EMF-Objekt speichern
	 * 
	 */
	@Override	
	public void readFromDocument(TextDocument odfDocument)
	{
		OfficeUtils.readFromDocument(odfDocument);
	}
	
	/*
	 * die temporaer erzeugten Objekte (beim Einlesen aus dem Dokument) wieder loeschen
	 */
	private void removeTemporaereAbsender()
	{
		List<Absender>removeList = new ArrayList<Absender>();
		
		Sender senders = OfficeUtils.getSender();
		List<Absender>allAbsender = senders.getSenders();
		for(Absender absender : allAbsender)
		{
			if(StringUtils.equals(absender.getName(), ODFDefaultWriteAdapterWizard.LOADED_ABSENDER))
				removeList.add(absender);
		}
		senders.getSenders().removeAll(removeList);
	}
	
	
	@Override
	public void cancelPage(TextDocument odfDocument)
	{
		EditingDomain domain = AdapterFactoryEditingDomain.getEditingDomainFor(senders);			
		while(domain.getCommandStack().canUndo())
			domain.getCommandStack().undo();
		removeTemporaereAbsender();
	}
	
	
	

}
