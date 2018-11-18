package it.naturtalent.e4.office.ui.wizards;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.internal.workbench.E4Workbench;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.services.internal.events.EventBroker;
import org.eclipse.e4.ui.workbench.IWorkbench;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.ui.view.ECPRendererException;
import org.eclipse.emf.ecp.ui.view.swt.ECPSWTViewRenderer;
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
import it.naturtalent.office.model.address.AddressPackage;
import it.naturtalent.office.model.address.Adresse;
import it.naturtalent.office.model.address.Empfaenger;
import it.naturtalent.office.model.address.Kontakt;
import it.naturtalent.office.model.address.NtProjektKontakte;
import it.naturtalent.office.model.address.Receivers;
import it.naturtalent.office.model.address.Referenz;



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
public class ODFReceiverWizardPage extends WizardPage implements IWriteWizardPage
{
	private Receivers receivers;
	private Empfaenger selectedEmpfaenger;
	
	private IEventBroker eventBroker;

	/**
	 * Create the wizard.
	 */
	public ODFReceiverWizardPage()
	{
		super(ODFDefaultWriteAdapterWizard.RECEIVER_PAGE_NAME);
		setMessage("Kontakte definieren, die ausgewählte Adresse wird in das Dokument übernommen");
		setTitle("Empfänger");
		setDescription("Angaben zum Empfänger");
		
		MApplication currentApplication = E4Workbench.getServiceContext().get(IWorkbench.class).getApplication();
		eventBroker = currentApplication.getContext().get(IEventBroker.class);
		
		// Receivers (Container aller Empfaenger) anlegen
		EClass receiversClass = AddressPackage.eINSTANCE.getReceivers();
		receivers = (Receivers) EcoreUtil.create(receiversClass);
				
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
				for(Kontakt kontact : kontacts)
				{
					EClass empfaengerClass = AddressPackage.eINSTANCE.getEmpfaenger();
					Empfaenger empfaenger = (Empfaenger) EcoreUtil.create(empfaengerClass);
					empfaenger.setName(kontact.getName());
					empfaenger.setAdresse(EcoreUtil.copy(kontact.getAdresse()));
					receivers.getReceivers().add(empfaenger);
				}
			}			
		}
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
			ECPSWTViewRenderer.INSTANCE.render(container, (EObject) receivers);			
		} catch (ECPRendererException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
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

			// Name des Empfaengers
			empfaenger.setName(ODFDocumentUtils.readTableText(table, 0, 0));
			
			// Modell 'Adresse' erzeugen, Empfaengeradresse einlesen
			EClass adressClass = AddressPackage.eINSTANCE.getAdresse();
			Adresse address = (Adresse) EcoreUtil.create(adressClass);
			readDefaultAddress(address, table);
			
			empfaenger.setAdresse(address);
			receivers.getReceivers().add(empfaenger);
			
			// 
			//eventBroker.post(OfficeUtils.SET_RECEIVER_SELECTED_EVENT , empfaenger);
		}		
	}
	
	@Override
	public void cancelPage(TextDocument odfDocument)
	{
		// TODO Auto-generated method stub
		
	}

	// Einlesen der Adresse in das Modell 'Adresse'
	private void readDefaultAddress(Adresse address, Table table)
	{
		// mit letzter Zeile beginnen (erwartet wird PLZ u Ort)
		for(int plzRow = table.getRowCount() - 1;plzRow >= 0;plzRow--)
		{
			String value = ODFDocumentUtils.readTableText(table, plzRow, 0);
			String [] resultPLZ = parsePLZ(value);
			if(StringUtils.isNotEmpty(resultPLZ[0]))
			{
				// PLZ und Ort in Ort eintragen
				address.setOrt(value);			
				
				// eine Zelle oberhalb ist Strasse bzw. Postfach
				value = ODFDocumentUtils.readTableText(table, --plzRow, 0);
				address.setStrasse(value);
								
				for(int row = 0;row < plzRow;row++)
				{
					value = ODFDocumentUtils.readTableText(table, row, 0);
					stackAdress(address, value);						
				}
			}			
		}
	}
	
	// fuellt das Modell sukzessiv
	private void stackAdress(Adresse address, String value)
	{
		if(StringUtils.isEmpty(address.getName()))
			address.setName(value);
		else
			if(StringUtils.isEmpty(address.getName2()))
				address.setName2(value);
			else
				if(StringUtils.isEmpty(address.getName3()))
					address.setName3(value);
	}				
	
	// String auf PLZ abklopfen
	private String [] parsePLZ(String plzOrt)
	{
		String [] result = new String [2];
		Pattern p = Pattern.compile("\\d+");
		Matcher m = p.matcher(plzOrt);
		if(m.find())
		{
			String numeric = m.group(0);
			if(StringUtils.startsWith(plzOrt, numeric))
			{
				result[0] = StringUtils.substring(plzOrt, 0,StringUtils.indexOf(plzOrt, " "));
				result[1] = StringUtils.substring(plzOrt, result[0].length()).trim();
			}
		}
		
		return result;
	}

	

}
