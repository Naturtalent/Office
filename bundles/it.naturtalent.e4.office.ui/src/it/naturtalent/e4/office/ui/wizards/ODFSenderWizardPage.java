package it.naturtalent.e4.office.ui.wizards;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EventObject;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.e4.core.contexts.IEclipseContext;
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
import org.odftoolkit.simple.table.CellRange;
import org.odftoolkit.simple.table.Table;

import it.naturtalent.e4.office.ui.IODFWriteAdapter;
import it.naturtalent.e4.office.ui.ODFDocumentUtils;
import it.naturtalent.e4.office.ui.OfficeUtils;
import it.naturtalent.office.model.address.Absender;
import it.naturtalent.office.model.address.AddressPackage;
import it.naturtalent.office.model.address.Adresse;
import it.naturtalent.office.model.address.Kontakt;
import it.naturtalent.office.model.address.Sender;



/**
 * Wizardseite zur Definition der Absenderangaben. 
 * 
 * @author dieter
 *
 */
public class ODFSenderWizardPage extends WizardPage implements IWriteWizardPage
{
	
	private class SenderCommandListener implements CommandStackListener
	{
		@Override
		public void commandStackChanged(EventObject event)
		{
			EMFStoreBasicCommandStack commandStack = (EMFStoreBasicCommandStack) event.getSource();
			Command command = commandStack.getMostRecentCommand();	
			
			if (command instanceof CreateChildCommand)
			{
				// selektiert das neue Kontakt im Mastertree
				CreateChildCommand addCommand = (CreateChildCommand) command;
				Collection<?>createResults = addCommand.getResult();
				Object createdObj = createResults.iterator().next();
				if(createdObj instanceof Absender)
				{
					Absender absender = (Absender) createdObj; 					
					ODFDefaultWriteAdapterWizard wizard = (ODFDefaultWriteAdapterWizard) getWizard();
					String officeContext = wizard.getOfficeContext();				
					absender.setContext(officeContext);
					System.out.println("CreateChild: "+absender.getName());
				}
			}
			
			if (command instanceof AddCommand)
			{
				Collection<?>addResults = command.getResult();
				Object addedObj = addResults.iterator().next();
				if(addedObj instanceof Absender)
				{
					Absender absender = (Absender) addedObj;
					ODFDefaultWriteAdapterWizard wizard = (ODFDefaultWriteAdapterWizard) getWizard();
					String officeContext = wizard.getOfficeContext();				
					absender.setContext(officeContext);
					System.out.println("ADD: "+absender.getName());
				}
			}
		
			
		}
	}
	private SenderCommandListener senderCommandListener = new SenderCommandListener();


	// Key des DialogSetting Absenders
	private static final String SETTINGABSENDER = "settingabsender";
	
	// Label des aus dem Dokument gelesenen Absenders
	private static final String LOADED_ABSENDER = "Absender aus der Datei";
	
	private IDialogSettings dialogSettings = WorkbenchSWTActivator.getDefault().getDialogSettings();
	
	private Sender senders;
	private Absender selectedAbsender;
	
	private IEventBroker eventBroker;
	
	
	
	/**
	 * Create the wizard.
	 */
	public ODFSenderWizardPage()
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
			// Sender mit den auf 'context' gefilterten Absender im MasterDetailView zeigen  
			senders = OfficeUtils.getSender();
			ECPSWTViewRenderer.INSTANCE.render(container, (EObject) senders);	
			
			// den im Dialogsetting gespeicherte Absender im MasterView selektieren
			String settingAbsenderName = dialogSettings.get(SETTINGABSENDER);
			if(StringUtils.isNotEmpty(settingAbsenderName))
			{
				List<Absender>absenders = senders.getSenders();
				for(Absender absender : absenders)
					if(StringUtils.equals(absender.getName(), settingAbsenderName))
					{
						eventBroker.post(OfficeUtils.SET_ABSENDERMASTER_SELECTION_EVENT, absender);
						break;
					}							
			}
			
			EditingDomain domain = AdapterFactoryEditingDomain.getEditingDomainFor(senders);
			EMFStoreBasicCommandStack commandStack = (EMFStoreBasicCommandStack) domain.getCommandStack();
			domain.getCommandStack().addCommandStackListener(senderCommandListener);
			
			
		} catch (ECPRendererException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
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
	
	@Override
	public void writeToDocument (TextDocument odfDocument)
	{
		if((selectedAbsender != null) && (odfDocument != null))
		{
			// Adresstabelle im Dokument addressieren
			Table table = odfDocument.getTableByName(IODFWriteAdapter.ODF_WRITESENDER);
			
			// Tabelle loeschen			
			CellRange cellRange = ODFDocumentUtils.markTable(table); 		
			ODFDocumentUtils.clearCellRange(cellRange);
							
			// Modelldaten (Adresse) in das Dokument schreiben 
			Adresse adresse = selectedAbsender.getAdresse();
			if (adresse != null)
			{
				String value = adresse.getName();
				if (StringUtils.isNotEmpty(value))
					ODFDocumentUtils.writeTableText(table, 1, 0, value);

				value = adresse.getStrasse() + ",";
				value = value + adresse.getOrt();
				ODFDocumentUtils.writeTableText(table, 2, 0, value);
				
				// Name des Absenders in DialogSetting speichern
				dialogSettings.put(SETTINGABSENDER, selectedAbsender.getName());				
			}		
			
			// temporaere Absender loeschen
			//cancelPage(odfDocument);
			
		}
		
		// temporaere Absender loeschen
		removeTemporaereAbsender();
	}

	/* 
	 * Absenderdaten aus dem Dokument lesen und temporaer in einem EMF-Objekt speichern
	 * 
	 */
	@Override
	public void readFromDocument(TextDocument odfDocument)
	{	
		Table table = odfDocument.getTableByName(IODFWriteAdapter.ODF_WRITESENDER);
		if (table != null)
		{
			// temporaes AbsenderModell erstellen und hinzufuegen
			EClass absenderClass = AddressPackage.eINSTANCE.getAbsender();
			Absender tempAbsender = (Absender) EcoreUtil.create(absenderClass);
			tempAbsender.setName(LOADED_ABSENDER);
			tempAbsender.setContext(((ODFDefaultWriteAdapterWizard) getWizard()).getOfficeContext());
			
			EClass adresseClass = AddressPackage.eINSTANCE.getAdresse();
			Adresse adresse = (Adresse) EcoreUtil.create(adresseClass);
			tempAbsender.setAdresse(adresse);
			
			EditingDomain domain = AdapterFactoryEditingDomain.getEditingDomainFor(senders);
			EReference eReference = AddressPackage.eINSTANCE.getSender_Senders();
			Command addCommand = AddCommand.create(domain, senders , eReference, tempAbsender);
			if(addCommand.canExecute())
				domain.getCommandStack().execute(addCommand);
			
			int rowCount = table.getRowCount();
			for (int i = rowCount - 1; i >= 0; i--)
			{
				String rowValue = ODFDocumentUtils.readTableText(table, i, 0);
				if (StringUtils.isNotEmpty(rowValue))
				{
					// in der untersten Zeile der Tabelle wird (Strasse,Ort) erwartet
					String[] strOrt = StringUtils.split(rowValue, ",");
					adresse.setStrasse(strOrt[0]);
					adresse.setOrt(strOrt[1]);
					
					if(--i >= 0)						
						adresse.setName(ODFDocumentUtils.readTableText(table, i, 0));
					
					break;
				}
			}
		}
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
			if(StringUtils.equals(absender.getName(), LOADED_ABSENDER))
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
