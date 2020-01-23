package it.naturtalent.e4.office.ui.wizards;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.ui.view.ECPRendererException;
import org.eclipse.emf.ecp.ui.view.swt.ECPSWTViewRenderer;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContextFactory;
import org.eclipse.emf.ecp.view.spi.model.ModelChangeListener;
import org.eclipse.emf.ecp.view.spi.model.ModelChangeNotification;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.provider.ViewProviderHelper;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
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
import it.naturtalent.e4.office.ui.preferences.OfficeDefaultPreferenceUtils;
import it.naturtalent.office.model.address.Absender;
import it.naturtalent.office.model.address.AddressPackage;
import it.naturtalent.office.model.address.Adresse;
import it.naturtalent.office.model.address.Sender;



/**
 * Die Wizardseite bietet alle definierten Absender (s. Praeferenzen) zur Auswahl an. Der aktuelle praeferenzierte
 * Absender ist voreingestellt. 
 * 
 * @author dieter
 *
 */
public class ODFAbsenderWizardPage extends WizardPage implements IWriteWizardPage
{
	// Name dieser WizardPage	
	private final static String SENDER_PAGE_NAME = "ODF_ABSENDER";
	
	// Office-Praeferenzknoten
	protected IEclipsePreferences instancePreferenceNode;
		
	// OfficeKontext 
	private String officeContext;
	
	// Container aller Absender im Modell
	private Sender sender;
	
	// der praeferenzierte Absender
	//private Absender prefAbsender;
	
	private IEventBroker eventBroker;
	
	// der momentan im MasterRenderer selektierte Absender
	private Absender selectedAbsender;
	
	public static final String LOADED_ABSENDER = "Absender aus der Datei";
	//private EditingDomain domain;
	//private EReference eReference;
	
	/**
	 * Create the wizard.
	 */
	public ODFAbsenderWizardPage()
	{
		super(SENDER_PAGE_NAME);
		setMessage("Absender erstellen, bearbeiten und auswählen\n(siehe auch Präferenzen)"); //$NON-NLS-N$
		setTitle("Absender");
		setDescription("Angaben zum Absender");		
	}
	
	@PostConstruct
	private void postConstruct(@Optional IEventBroker eventBroker, IEclipseContext context)
	{
		this.eventBroker = eventBroker;		
		officeContext = (String) context.get(OfficeUtils.E4CONTEXTKEY_OFFICECONTEXT);		
		instancePreferenceNode = (IEclipsePreferences) context.get(OfficeUtils.E4CONTEXTKEY_OFFICEPRAEFERENZNODE);
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
			// alle gespeicherten Absender aus dem Modell einlesen
			sender = (Sender) OfficeUtils.findObject(AddressPackage.eINSTANCE.getSender());	
			sender = EcoreUtil.copy(sender);
						
			// Renderer zeigt die Absender  (filtert die Absender auf 'officecontext' im MasterDetailView) 
			//ECPSWTViewRenderer.INSTANCE.render(container, (EObject) sender);	
			
			final VView view = ViewProviderHelper.getView(sender, null);
			final ViewModelContext vmc = ViewModelContextFactory.INSTANCE.createViewModelContext(view, sender);
			ECPSWTViewRenderer.INSTANCE.render(container, vmc);		
			
			// den praeferenzierten Absender im Master selektieren			
			String prefSender = instancePreferenceNode.get(OfficeDefaultPreferenceUtils.ABSENDER_PREFERENCE, null);
			if(StringUtils.isNotEmpty(prefSender))
			{
				selectedAbsender = findAbsenderByName(sender, prefSender, officeContext);				
				eventBroker.post(OfficeUtils.SET_OFFICEMASTER_SELECTION_EVENT, selectedAbsender);
			}
						
		} catch (ECPRendererException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	private Absender findAbsenderByName(Sender sender, String absenderName,String officeContext)
	{
		EList<Absender> absenders = sender.getSenders();
		for (Absender absender : absenders)
		{
			if (StringUtils.equals(absender.getName(), absenderName)
					&& StringUtils.equals(absender.getContext(), officeContext))
				return absender;
		}

		return null;
	}

	
	/*
	 * Beim Oeffnen eines Dokuments wird der Absender eingelesen und temporaer im Modell gespeichert.
	 * Mit dieser Funktion wird der temporaere Absender wieder aus dem Modell entfernt
	 */
	@Override
	public void cancelPage(TextDocument odfDocument)
	{
	}
	
	/*
	 * Beim Oeffnen eines Dokuments wird der Absender eingelesen und temporaer im Modell gespeichert.
	 * Mit dieser Funktion wird der temporaere Absender wieder aus dem Modell entfernt
	 */
	@Override
	public void unDo(TextDocument odfDocument)
	{
		/*
		Absender toRemove = OfficeUtils.findAbsenderByName(LOADED_ABSENDER, officeContext);
		if(toRemove != null)
		{
			Command removeCommand = RemoveCommand.create(domain, sender, eReference, toRemove);
			if (removeCommand.canExecute())
				removeCommand.execute();
		}
		*/		
	}

	/* 
	 * Absenderdaten aus dem Dokument lesen und temporaer in das EMF-Modell uebernehmen.
	 * 
	 */
	@Override	
	public void readFromDocument(TextDocument odfDocument)
	{		
		selectedAbsender = readAbsenderFromDocument(odfDocument);		
		
		// den vom Dokument eingelesenen Absender im Master selektieren
		eventBroker.post(OfficeUtils.SET_OFFICEMASTER_SELECTION_EVENT, selectedAbsender);
	}

	/**
	 * Absender aus dem Dokument lesen
	 * @param odfDocument
	 * @return
	 */
	private Absender readAbsenderFromDocument(TextDocument odfDocument)
	{
		Absender tempAbsender = null;
		Table table = odfDocument.getTableByName(IODFWriteAdapter.ODF_WRITESENDER);
		if (table != null)
		{
			// einen Absender erstellen (repraesentiert die Adresse des Dokuments)
			EClass absenderClass = AddressPackage.eINSTANCE.getAbsender();
			tempAbsender = (Absender) EcoreUtil.create(absenderClass);
			tempAbsender.setName(LOADED_ABSENDER);

			// StandardOfficeContext eintragen
			tempAbsender.setContext(officeContext);

			// Absenderadresse erzeugen
			EClass adresseClass = AddressPackage.eINSTANCE.getAdresse();
			Adresse adresse = (Adresse) EcoreUtil.create(adresseClass);
			tempAbsender.setAdresse(adresse);

			// den eingelesenen Absender temporaer in das EMF-Modell uebernehemn	
			/*
			domain = AdapterFactoryEditingDomain.getEditingDomainFor(sender);
			eReference = AddressPackage.eINSTANCE.getSender_Senders();
			
			Command addCommand = AddCommand.create(domain, sender, eReference,tempAbsender);
			if (addCommand.canExecute())
				domain.getCommandStack().execute(addCommand);
				*/
			
			sender.getSenders().add(tempAbsender);

			// Absenderadresse aus dem Dokument lesen
			int rowCount = table.getRowCount();
			for (int i = 0; i < rowCount; i++)
			{
				String rowValue = ODFDocumentUtils.readTableText(table, i, 0);
				if (StringUtils.isNotEmpty(rowValue))
				{
					// Name
					String checkAdressName = adresse.getName();
					if (StringUtils.isEmpty(checkAdressName))
					{
						adresse.setName(rowValue);
						continue;
					}
					// Strasse, Ort
					String[] strOrt = StringUtils.split(rowValue, ",");
					if (strOrt.length > 0)
						adresse.setStrasse(strOrt[0]);
					if (strOrt.length > 1)
						adresse.setOrt(strOrt[1]);
				}
			}
		}
		return tempAbsender;
	}

	/* 
	 * Dieser Aufruf erfolgt im Zuge einer 'doPerformFinish()' Action des Wizards.
	 * 
	 * Diese Seite schreibt den selektierten Absenders wird in TextDokument.	
	 * 
	 */	
	@Override
	public void writeToDocument (TextDocument odfDocument)
	{
		writeAbsenderToDocument(odfDocument, selectedAbsender);
	}

	/*	 
	 * Der selektierte Absender wird in das TextDokument geschrieben.
	 */
	private void writeAbsenderToDocument(TextDocument odfDocument, Absender absender)
	{
		if ((absender != null) && (odfDocument != null))
		{
			// Adresstabelle im Dokument addressieren
			Table table = odfDocument.getTableByName(IODFWriteAdapter.ODF_WRITESENDER);

			// Tabelle loeschen
			CellRange cellRange = ODFDocumentUtils.markTable(table);
			ODFDocumentUtils.clearCellRange(cellRange);

			// Modelldaten (Adresse) in das Dokument schreiben
			Adresse adresse = absender.getAdresse();
			if (adresse != null)
			{
				String value = adresse.getName();
				if (StringUtils.isNotEmpty(value))
					ODFDocumentUtils.writeTableText(table, 1, 0, value);

				value = "";
				String strasse = adresse.getStrasse();
				if (StringUtils.isNotEmpty(strasse))
					value = strasse + ",";

				String ort = adresse.getOrt();
				if (StringUtils.isNotEmpty(ort))
					value = value + ort;

				if (StringUtils.isNotEmpty(value))
					ODFDocumentUtils.writeTableText(table, 2, 0, value);
			}
		}
	}


	/**
	 * Der Renderer 'OfficeContextTreeMasterRenderer' meldet die Selektion eines Absenders
	 * 
	 * @param absender
	 */
	@Inject
	@Optional
	public void handleSelectionChangedEvent(@UIEventTopic(OfficeUtils.GET_OFFICEMASTER_SELECTION_EVENT) Absender absender)
	{				
		selectedAbsender = absender;
	}
	
}
