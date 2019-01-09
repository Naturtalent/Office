package it.naturtalent.e4.office.ui.wizards;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.internal.workbench.E4Workbench;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.workbench.IWorkbench;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.ui.view.ECPRendererException;
import org.eclipse.emf.ecp.ui.view.swt.ECPSWTViewRenderer;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
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
import it.naturtalent.e4.project.IResourceNavigator;
import it.naturtalent.office.model.address.Absender;
import it.naturtalent.office.model.address.AddressPackage;
import it.naturtalent.office.model.address.Adresse;
import it.naturtalent.office.model.address.Empfaenger;
import it.naturtalent.office.model.address.Kontakt;
import it.naturtalent.office.model.address.NtProjektKontakte;
import it.naturtalent.office.model.address.Receivers;
import it.naturtalent.office.model.address.Sender;



/**
 * 
 * Wizardseite zur Bearbeitung der projektspezifischen Kontakte. Mit dem Kontakt kann auch eine Adresse definiert werden.
 * Im Zusammenhang mit Anschreiben ist diese Adresse von Interesse und kann als Adressat in das Dokument geschrieben
 * werden. Die selektierte Adresse wird in das Dokument geschrieben.
 * 
 *  ODFDefaultWriteAdapterWizard
 * 
 * @author dieter
 *
 */
public class ODFEmpfaengerWizardPage extends WizardPage implements IWriteWizardPage
{
	public final static String SENDER_PAGE_NAME = "ODF_SENDER";
	
	private Receivers receivers;
	private Empfaenger selectedEmpfaenger;
	private IEventBroker eventBroker;
	
	// wird im WIZARDCREATEMODE selektiert 
	private Empfaenger firstEmpfaenger;
	
	/**
	 * Create the wizard.
	 */
	public ODFEmpfaengerWizardPage()
	{
		super(SENDER_PAGE_NAME);
		setMessage("Einen Empfänger definieren, dessen Adresse in das Dokument übernommen wird");
		setTitle("Empfänger");
		setDescription("einen Empfänger festlegen");
		
		// Receivers (Container aller Empfaenger) anlegen
		EClass receiversClass = AddressPackage.eINSTANCE.getReceivers();
		receivers = (Receivers) EcoreUtil.create(receiversClass);				
	}
	
	@PostConstruct
	private void postConstuct(@Optional IEventBroker eventBroker)
	{
		this.eventBroker = eventBroker;
	}
	
	/*
	 * Der im MasterView selektierte Empaenger wird in diese WizardPage uebernommen.
	 */
	@Inject
	@Optional
	public void handleModelChangedEvent(@UIEventTopic(OfficeUtils.RECEIVER_MASTER_SELECTED_EVENT) Empfaenger empfaenger)
	{
		selectedEmpfaenger = empfaenger;
	}
	
	/**
	 * Die Funktion 'handleAddExisting' (Kopieren aus der Kontaktdatenbank) des DetailRenderers 
	 * wird hier ausgefuhrt.
	 * 
	 * @param empfaenger
	 */
	@Inject
	@Optional
	public void handleAddExisting(@UIEventTopic(OfficeUtils.ADD_EXISTING_RECEIVER) Object object)
	{		
		// einen Kontakt in der Datenbank selektieren
		Kontakt dbKontakt = OfficeUtils.readKontaktFromDatabase();
		if(dbKontakt != null)			
		{
			// der selektierte Kontakt wird als Empfaenger mit der Kontaktadresse uebernaommen
			EClass empfaengerClass = AddressPackage.eINSTANCE.getEmpfaenger();
			Empfaenger empfaenger = (Empfaenger) EcoreUtil.create(empfaengerClass);
			empfaenger.setName(dbKontakt.getName());
			empfaenger.setAdresse(dbKontakt.getAdresse());
			receivers.getReceivers().add(empfaenger);

			// den Empfaenger selektieren
			eventBroker.post(OfficeUtils.SET_RECEIVERMASTER_SELECTION_EVENT , empfaenger);
		}

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
		
		initNtProjectsReceivers();
		
		try
		{		
			ECPSWTViewRenderer.INSTANCE.render(container, (EObject) receivers);	
			
			// im CreateModus wird der erste Empfaeger selektiert
			if (firstEmpfaenger != null)
			{
				ODFDefaultWriteAdapterWizard defaultWizard = (ODFDefaultWriteAdapterWizard) getWizard();
				if (defaultWizard.isWizardModus() == ODFDefaultWriteAdapterWizard.WIZARDCREATEMODE)			
					eventBroker.post(OfficeUtils.SET_RECEIVERMASTER_SELECTION_EVENT, firstEmpfaenger);			
			}
			
		} catch (ECPRendererException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	
	}

	/**
	 * Die Adressen der den NtProjekten zugeordnetetn Kontakten werden als mögliche Empfaenger angeboten.
	 * Die Adresse des 'ersten' Kontakts wird zudem noch selektiert.
	 * 
	 */
	private void initNtProjectsReceivers()
	{
		// die projektspezifischen Kontakte (Adressen)  werden als Empfaenger in das Modell eingelesen
		IResourceNavigator resourceNavigator = it.naturtalent.e4.project.ui.Activator.findNavigator();
		TreeViewer treeViewer = resourceNavigator.getViewer();
		IStructuredSelection selection = treeViewer.getStructuredSelection();
		Object selObject = selection.getFirstElement();
		if (selObject instanceof IResource)
		{
			IResource resource = (IResource) selObject;
			IProject iProject = resource.getProject();
			NtProjektKontakte kontakte = OfficeUtils.getProjectKontacts(iProject.getName());
			EList<Kontakt>kontacts = kontakte.getKontakte();
			if((kontacts != null) && (!kontacts.isEmpty()))
			{	
				firstEmpfaenger = null;
				for(Kontakt kontact : kontacts)
				{
					EClass empfaengerClass = AddressPackage.eINSTANCE.getEmpfaenger();
					Empfaenger empfaenger = (Empfaenger) EcoreUtil.create(empfaengerClass);
					
					if(firstEmpfaenger == null)
						firstEmpfaenger = empfaenger;
					
					empfaenger.setName(kontact.getName());
					empfaenger.setAdresse(EcoreUtil.copy(kontact.getAdresse()));
					ODFDefaultWriteAdapterWizard wizard = (ODFDefaultWriteAdapterWizard) getWizard();									
					empfaenger.setContext(wizard.getOfficeContext());
					receivers.getReceivers().add(empfaenger);
				}
			}			
		}
	}

	/*
	 * Die Adresse des Empfaengers in das Dokument schreiben 
	 * @see it.naturtalent.e4.office.ui.wizards.IWriteWizardPage#writeToDocument(org.odftoolkit.simple.TextDocument)
	 */
	@Override
	public void writeToDocument(TextDocument odfDocument)
	{
		if((selectedEmpfaenger != null) && (odfDocument != null))
		{
			// Adresstabelle lesen
			Table table = odfDocument.getTableByName(IODFWriteAdapter.ODF_WRITEADRESSE);
			if (table != null)
			{
				// Tabelle loeschen
				CellRange cellRange = ODFDocumentUtils.markTable(table); 
				ODFDocumentUtils.clearCellRange(cellRange);
				
				Adresse adr = selectedEmpfaenger.getAdresse();
				
				if (adr != null)
				{
					int row = 0;

					String adrText = adr.getName();
					if (StringUtils.isNotEmpty(adrText))
						ODFDocumentUtils.writeTableText(table, row++, 0,adrText);

					adrText = adr.getName2();
					if (StringUtils.isNotEmpty(adrText))
						ODFDocumentUtils.writeTableText(table, row++, 0,adrText);

					adrText = adr.getName3();
					if (StringUtils.isNotEmpty(adrText))
						ODFDocumentUtils.writeTableText(table, row++, 0,adrText);

					adrText = adr.getStrasse();
					if (StringUtils.isNotEmpty(adrText))
						ODFDocumentUtils.writeTableText(table, row++, 0,adrText);

					adrText = adr.getOrt();
					if (StringUtils.isNotEmpty(adrText))
						ODFDocumentUtils.writeTableText(table, row++, 0,adrText);

				}
			}
		}
	}

	/* 
	 * Empfaenger aus dem Dokument lesen und zu den Empfaenger 'receivers' hinzufuegen
	 * 
	 */
	@Override
	public void readFromDocument(TextDocument odfDocument)
	{
		Table table = odfDocument.getTableByName(IODFWriteAdapter.ODF_WRITEADRESSE);
		if (table != null)
		{
			EClass empfaengerClass = AddressPackage.eINSTANCE.getEmpfaenger();
			Empfaenger empfaenger = (Empfaenger) EcoreUtil.create(empfaengerClass);
			empfaenger.setContext(((ODFDefaultWriteAdapterWizard) getWizard()).getOfficeContext());
			empfaenger.setName(ODFDefaultWriteAdapterWizard.LOADED_EMPFAENGER);
			
			// Modell 'Adresse' erzeugen, Empfaengeradresse einlesen
			EClass adressClass = AddressPackage.eINSTANCE.getAdresse();
			Adresse address = (Adresse) EcoreUtil.create(adressClass);
			readDefaultAddress(address, table);
			
			empfaenger.setAdresse(address);
			receivers.getReceivers().add(empfaenger);
		}		
	}
	
	@Override
	public void cancelPage(TextDocument odfDocument)
	{
		// TODO Auto-generated method stub
		
	}

	private void readDefaultAddress(Adresse address, Table table)
	{
		// Adresstabelle (getrimmt) aus dem Dokument
		String [] addressTable = readAddressTable(table);	
		
		if(ArrayUtils.isNotEmpty(addressTable))
		{
			int plzRowIdx = findPLZRow(address, addressTable);
			if(plzRowIdx >= 0)
			{
				switch (plzRowIdx)
					{
						case 0:
							// es existiert nur plz/ort
							break;
							
						case 1:
							// strasse/hsnr wird unterstellt
							address.setStrasse(addressTable[plzRowIdx-1]);
							break;
								
						case 2:
							// strasse/hsnr und Name wird unterstellt
							address.setStrasse(addressTable[plzRowIdx-1]);
							address.setName(addressTable[plzRowIdx-2]);
							break;

						case 3:
							// strasse/hsnr Name und Name1 wird unterstellt
							address.setStrasse(addressTable[plzRowIdx-1]);
							address.setName(addressTable[plzRowIdx-3]);
							address.setName2(addressTable[plzRowIdx-2]);
							break;

						case 4:
							// strasse/hsnr Name und Name1 wird unterstellt
							address.setStrasse(addressTable[plzRowIdx-1]);
							address.setName(addressTable[plzRowIdx-4]);
							address.setName2(addressTable[plzRowIdx-3]);
							address.setName3(addressTable[plzRowIdx-2]);
							break;

						default:
							break;
					}
			}
			else
			{
				// es wurde keine plz/ort - Zeile erkannt (auslaendische Schreibweise, unzulaessige etc ...)
				int n = addressTable.length;
				switch (n)
				{
					case 1:
						address.setName(addressTable[0]);
						break;
						
					case 2:						
						address.setName(addressTable[0]);
						address.setName2(addressTable[1]);
						break;
							
					case 3:
						address.setName(addressTable[0]);
						address.setName2(addressTable[1]);
						address.setStrasse(addressTable[2]);						
						break;

					case 4:
						address.setName(addressTable[0]);
						address.setName2(addressTable[1]);
						address.setStrasse(addressTable[2]);						
						address.setOrt(addressTable[3]);
						break;

					case 5:
						address.setName(addressTable[0]);
						address.setName2(addressTable[1]);
						address.setName3(addressTable[4]);
						address.setStrasse(addressTable[2]);						
						address.setOrt(addressTable[3]);
						break;

					default:
						break;
				}
			}
		}		
	}
	
	/*
	 * Sucht in der Adresstabelle nach der Zeile mit der PLZ (letzte Zeile einer Adressdefinition)
	 * Wird eine PLZ erkannt, wird die PLZ und der Ort in die Adresse eingetragen und den Index dieser
	 * Zeile (Indey in der Adressentabelle) wird zuruckgegeben
	 */
	private int findPLZRow(Adresse adresse, String [] addressTable)
	{
		int n = addressTable.length;
		for (int i = 0; i < n; i++)
		{
			String plz = extractPLZ(addressTable[i]);
			if (StringUtils.isNotEmpty(plz))
			{
				adresse.setPlz(plz);
				
				String ort = addressTable[i];
				int idx = StringUtils.indexOf(ort, plz);
				ort = StringUtils.substring(ort, idx+plz.length());	
				ort = StringUtils.trim(ort);
				adresse.setOrt(ort);
				return i;
			}
		}

		return (-1);
	}
	
	/*
	 * Extrahiert aus einem String eine PLZ, prueft den Algorithmus und gibt diese zurueck
	 */
	private String extractPLZ(String stgCheck)
	{		
		// im ersten Schritt die Zahl im String extrahiern (incl. vorangeschriebenen D)
		Pattern p = Pattern.compile("([Dd]\\s*[-–—]?)?\\d+");
		Matcher m = p.matcher(stgCheck);
		if(m.find())
		{
			// PLZ Algorithmus nochmal pruefen
			String plz = m.group(0);
			p = Pattern.compile("^(?:[Dd]\\s*[-–—]?\\s*)?[0-9]{5}$");
			m = p.matcher(plz);
			return (m.find() ? plz : null);
		}
		
		return null;
	}
	
	private String parseNumerus(String stgParse)
	{
		String [] result = new String [2];
		Pattern p = Pattern.compile("\\d+");
		Matcher m = p.matcher(stgParse);
		if(m.find())
			return m.group(0);
		
		return null;
	}
	
	/*
	 *  Adresstabelle aus dem Dokument lesen
	 */
	private String [] readAddressTable(Table table)
	{
		String [] trimmedAdressTable = null;
		
		int n = table.getRowCount();
		for(int i = 0;i < n;i++)
		{
			String value = ODFDocumentUtils.readTableText(table, i, 0);
			if(StringUtils.isNoneEmpty(value))
				trimmedAdressTable = ArrayUtils.add(trimmedAdressTable, value);
		}
		return trimmedAdressTable;
	}
	


}
