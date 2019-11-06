package it.naturtalent.e4.office.ui.wizards;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.ui.view.ECPRendererException;
import org.eclipse.emf.ecp.ui.view.swt.ECPSWTViewRenderer;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.odftoolkit.simple.TextDocument;
import org.odftoolkit.simple.table.CellRange;
import org.odftoolkit.simple.table.Table;

import it.naturtalent.e4.office.ui.ODFDocumentUtils;
import it.naturtalent.e4.office.ui.OfficeUtils;
import it.naturtalent.e4.office.ui.preferences.OfficeDefaultPreferenceUtils;
import it.naturtalent.office.model.address.AddressPackage;
import it.naturtalent.office.model.address.Signature;
import it.naturtalent.office.model.address.Signatures;


/**
 * Seite zum Bearbeiten der Office Signaturen
 * 
 * @author dieter
 *
 */
public class ODFSignatureWizardPage extends WizardPage  implements IWriteWizardPage
{

	// Name dieser WizardPage
	public static final String SIGNATURE_PAGE_NAME = "ODF_Signature";
	
	/*
	private static final String DEFAULT_SIGNATURENAME = "DefaultSignatur"; //$NON-NLS-1$
	private static final String DEFAULT_GREETING = "Mit freundlichen Grüßen";
	*/
	
	// Name der Signaturtabelle im ODFDokument (Template)
	public static String ODF_WRITESIGNATURE = "Signaturtabelle";
	
	// Label fuer die aus dem ODF-Dokument eingelesene Signatur
	private static final String LOADSIGNATURE = "Signatur aus der Datei";
	private EditingDomain domain;
	private EReference eReference;
	
	// Telekom-Praeferenzknoten
	private IEclipsePreferences instancePreferenceNode;
	
	// OfficeKontext 
	private String officeContext;
	
	private IEventBroker eventBroker;	
		
	private Signatures signatures;
	
	private Signature selectedSignature;

	/**
	 * Create the wizard.
	 */
	public ODFSignatureWizardPage()
	{
		super(SIGNATURE_PAGE_NAME);
		setMessage("Signatur erstellen und bearbeiten");
		setTitle("Signaturen");
		setDescription("Signaturen auswählen");
	}
	
	/**
	 * Der inhalt der Seite wird definiert.
	 * 
	 * @param parent
	 */
	public void createControl(Composite parent)
	{
		Composite container = new Composite(parent, SWT.NULL);

		setControl(container);
		container.setLayout(new GridLayout(2, false));
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);				
			
		// Container aller Signaturen
		signatures =  (Signatures) OfficeUtils.findObject(AddressPackage.eINSTANCE.getSignatures());
		signatures = EcoreUtil.copy(signatures);
		
					
		// Parameter fuer die EMF Command (add)			
		//domain = AdapterFactoryEditingDomain.getEditingDomainFor(signatures);
		//eReference = AddressPackage.eINSTANCE.getSignatures_Signatures();

		
		try
		{
			// die gespeicherten Signaturen anzeigen (gefiltert nach OfficeContext)
			ECPSWTViewRenderer.INSTANCE.render(container, signatures);
			
			selectDefaultSignature();
			
		} catch (ECPRendererException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * im 'Create-Modus' wird die praeferenzierte Signature im Master selektiert. 
	 *  
	 * @return
	 */
	public void selectDefaultSignature()
	{
		ODFDefaultWriteAdapterWizard defaultWizard = (ODFDefaultWriteAdapterWizard) getWizard();
		if (defaultWizard.isWizardModus() == ODFDefaultWriteAdapterWizard.WIZARDCREATEMODE)		
		{
			String preferenceName = instancePreferenceNode.get(OfficeDefaultPreferenceUtils.SIGNATURE_PREFERENCE, null);
			Signature signature = findSignatureByName(signatures, preferenceName, officeContext);
			
			eventBroker.post(OfficeUtils.SET_OFFICEMASTER_SELECTION_EVENT, signature);
		}
	}
	
	public static Signature findSignatureByName(Signatures signatures, String signatureName,String officeContext)
	{		
		EList<Signature> sigatures = signatures.getSignatures();
		for (Signature signature : sigatures)
		{
			if (StringUtils.equals(signature.getName(), signatureName)
					&& StringUtils.equals(signature.getContext(), officeContext))
				return signature;
		}

		return null;
	}


	
	@PostConstruct
	private void postConstruct(@Optional IEventBroker eventBroker, IEclipseContext context)
	{
		this.eventBroker = eventBroker;
		
		// der Wizard hat den zubenutzenden OfficeContext im E4Context hinterlegt
		officeContext = (String) context.get(OfficeUtils.E4CONTEXTKEY_OFFICECONTEXT);
		
		// Preferenceknoten ist ebenfalls im E4Context hinterlegt
		instancePreferenceNode = (IEclipsePreferences) context.get(OfficeUtils.E4CONTEXTKEY_OFFICEPRAEFERENZNODE);
	}
	

	/* 
	 * Signatur aus dem Dokument einlesen 
	 */
	@Override
	public void readFromDocument(TextDocument odfDocument)
	{
		Table table = odfDocument.getTableByName(ODF_WRITESIGNATURE);
		if (table != null)
		{
			EClass signatureClass = AddressPackage.eINSTANCE.getSignature();
			Signature readSignature = (Signature) EcoreUtil.create(signatureClass);
			
			readSignature.setName(LOADSIGNATURE);
			readSignature.setContext(officeContext);
			readSignature.setGreeting(ODFDocumentUtils.readTableText(table, 0, 0));
			readSignature.setSigner(ODFDocumentUtils.readTableText(table, 2, 0));
			readSignature.setCosigner(ODFDocumentUtils.readTableText(table, 2, 1));

			signatures.getSignatures().add(readSignature);
			
			/*
			Command addCommand = AddCommand.create(domain, eReference, eReference, readSignature);
			if (addCommand.canExecute())
				domain.getCommandStack().execute(addCommand);
				*/
		}
	}

	/*
	public void readFromDocument(TextDocument odfDocument)
	{
		Table table = odfDocument.getTableByName(ODF_WRITESIGNATURE);
		if (table != null)
		{
			EClass signatureClass = AddressPackage.eINSTANCE.getSignature();
			Signature readSignature = (Signature) EcoreUtil.create(signatureClass);
			
			readSignature.setName(LOADSIGNATURE);
			readSignature.setContext(officeContext);
			readSignature.setGreeting(ODFDocumentUtils.readTableText(table, 0, 0));
			readSignature.setSigner(ODFDocumentUtils.readTableText(table, 2, 0));
			readSignature.setCosigner(ODFDocumentUtils.readTableText(table, 2, 1));
			
			Command addCommand = AddCommand.create(domain, eReference, eReference, readSignature);
			if (addCommand.canExecute())
				domain.getCommandStack().execute(addCommand);
		}
	}
	*/

	/* 
	 * Ist eine Sinatur im Master selektiert, wird diese beim Beenden des Wizards mit 'OK' in das ODFDokument geschrieben.
	 * 
	 */
	@Override
	public void writeToDocument(TextDocument odfDocument)
	{
		if((selectedSignature != null) && (odfDocument != null))
		{
			Table table = odfDocument.getTableByName(ODF_WRITESIGNATURE);
			if (table != null)
			{
				// Tabelle loeschen					
				CellRange cellRange = ODFDocumentUtils.markTable(table); 
				ODFDocumentUtils.clearCellRange(cellRange);
				
				String value = selectedSignature.getGreeting();
				if(StringUtils.isNotEmpty(value))
					ODFDocumentUtils.writeTableText(table, 0, 0, value);

				value = selectedSignature.getStatus1();
				if(StringUtils.isNotEmpty(value))
					ODFDocumentUtils.writeTableText(table, 2, 0, value);

				value = selectedSignature.getSigner();
				if(StringUtils.isNotEmpty(value))
					ODFDocumentUtils.writeTableText(table, 4, 0, value);

				value = selectedSignature.getStatus2();
				if(StringUtils.isNotEmpty(value))
					ODFDocumentUtils.writeTableText(table, 2, 1, value);

				value = selectedSignature.getCosigner();
				if(StringUtils.isNotEmpty(value))
					ODFDocumentUtils.writeTableText(table, 4, 1, value);
			}
		}		
	}
	
	/*
	 * Neue Signature erzeugen mit Namen und OfficeContext
	 */
	/*
	private Signature createSignature(String signatureName, String officeContext)
	{
		EClass signatureClass = AddressPackage.eINSTANCE.getSignature();
		Signature signature = (Signature) EcoreUtil.create(signatureClass);
		signature.setName(signatureName);
		signature.setContext(officeContext);
		return signature;
	}
	*/

	@Override
	public void cancelPage(TextDocument odfDocument)
	{
		
	}
	

	@Override
	public void unDo(TextDocument odfDocument)
	{
		/*
		Signature toRemove = OfficeUtils.findSignatureByName(LOADSIGNATURE, officeContext);
		if(toRemove != null)
		{
			Command removeCommand = RemoveCommand.create(domain, eReference, eReference, toRemove);
			if (removeCommand.canExecute())
				removeCommand.execute();
		}
		*/				
	}

	/*
	 * der Broker hat eine Selection im Master empangen und meldet diese
	 */
	@Inject
	@Optional
	public void handleSelectionChangedEvent(@UIEventTopic(OfficeUtils.GET_OFFICEMASTER_SELECTION_EVENT) EObject eObject)
	{
		// handelt es sich bei der Selektion um eine Referenz, dann wird diese als 'aktuelle' gespeichert
		if (eObject instanceof Signature)
			selectedSignature =  (Signature) eObject;
	}

}
