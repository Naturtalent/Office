package it.naturtalent.e4.office.ui.wizards;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.internal.workbench.E4Workbench;
import org.eclipse.e4.ui.internal.workbench.swt.WorkbenchSWTActivator;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.ui.view.ECPRendererException;
import org.eclipse.emf.ecp.ui.view.swt.ECPSWTViewRenderer;
import org.eclipse.jface.dialogs.IDialogSettings;
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
import it.naturtalent.office.model.address.AddressPackage;
import it.naturtalent.office.model.address.Signature;
import it.naturtalent.office.model.address.SignatureSet;

/**
 * Seite zum Bearbeiten der Office Signaturen
 * 
 * @author dieter
 *
 */
public class ODFSignatureWizardPage extends WizardPage  implements IWriteWizardPage
{

	// Name dieser WizardPage
	public static final String DEFAULT_SIGNATUREPAGE_NAME = "ODF_Signature";
	
	private static final String DEFAULT_SIGNATURENAME = "DefaultSignatur"; //$NON-NLS-1$
	private static final String DEFAULT_GREETING = "Mit freundlichen Grüßen";
	
	// Name der Signaturtabelle im ODFDokument (Template)
	public static String ODF_WRITESIGNATURE = "Signaturtabelle";
	
	// Label fuer die aus dem ODF-Dokument eingelesene Signatur
	private static final String LOADSIGNATURE = "Signatur aus der Datei";
	
	private String officeContext;
	
	private IEventBroker eventBroker;	
	
	// DialogSetting der Signatur
	private IDialogSettings dialogSettings  = WorkbenchSWTActivator.getDefault().getDialogSettings();
	private static final String DEFAULT_SIGNATURE_SETTING = "signaturesetting";
	protected String dialogSettingName = DEFAULT_SIGNATURE_SETTING;
	
	private SignatureSet signatureSet;
	private Signature selectedSignature;

	/**
	 * Create the wizard.
	 */
	public ODFSignatureWizardPage()
	{
		super(DEFAULT_SIGNATUREPAGE_NAME);
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
			
		// OfficeContext aus dem Wizard uebernehmen
		//officeContext = ((ODFDefaultWriteAdapterWizard)getWizard()).getOfficeContext();
		
		try
		{
			// Container aller Signaturen
			signatureSet =  (SignatureSet) OfficeUtils.findObject(AddressPackage.eINSTANCE.getSignatureSet());
			
			// gibt es schon Signaturen oder muss initialisiert werden
			EList<Signature>signatures = signatureSet.getSignatures();

			Signature existSignature = null;
			if((signatures != null) && !signatures.isEmpty())
			{
				for (Signature signature : signatures)
					if (StringUtils.equals(signature.getContext(), officeContext))
					{
						existSignature = signature;
						break;
					}											
			}
			
			// noch keine Signaturen vorhanden oder keine mit OfficeContext
			if((signatures == null) || signatures.isEmpty() || existSignature == null)
			{							
				Signature signature = createSignature(DEFAULT_SIGNATURENAME, officeContext);			
				signature.setGreeting(DEFAULT_GREETING);
				signatureSet.getSignatures().add(signature);
			}
			
			// Liste der nichtloeschbaren Signaturen in den Eclipse4Context einbringen 		
			List<Signature>unRemovables = new ArrayList<Signature>();
			selectedSignature = OfficeUtils.findSignatureByName(DEFAULT_SIGNATURENAME, officeContext);					
			unRemovables.add(selectedSignature);
			E4Workbench.getServiceContext().set(OfficeUtils.SIGNATURE_UNREMOVABLES, unRemovables);
			
			// die gespeicherten Signaturen anzeigen (gefiltert nach OfficeContext)
			ECPSWTViewRenderer.INSTANCE.render(container, signatureSet);
			
			// im WizardCreate-Modus wird 'signature' im Renderer selektiert
			ODFDefaultWriteAdapterWizard defaultWizard =  (ODFDefaultWriteAdapterWizard) getWizard();
			if(defaultWizard.isWizardModus() == ODFDefaultWriteAdapterWizard.WIZARDCREATEMODE)
			{
				// Signatur mit dem im Setting hinterlegten Namen wird selektiert
				String settingSignatureName = dialogSettings.get(dialogSettingName);
				if(StringUtils.isNotEmpty(settingSignatureName))
				{
					selectedSignature = OfficeUtils.findSignatureByName(settingSignatureName, officeContext);							
					eventBroker.send(OfficeUtils.SIGNATURE_REQUESTSELECTIONEVENT, selectedSignature);
				}
			}
		} catch (ECPRendererException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@PostConstruct
	private void postConstruct(@Optional IEventBroker eventBroker)
	{
		this.eventBroker = eventBroker;
	}
	
	/**
	 * SignatureSetRenderer meldet die Nauaufnahmee einer Signature im Modell
	 * 
	 * @param event
	 */
	@Inject
	@Optional
	public void handleAddEvent(@UIEventTopic(OfficeUtils.ADD_SIGNATURE_EVENT) Object event)
	{
		if (event instanceof SignatureSet)
		{
			List<Signature>signatures = ((SignatureSet)event).getSignatures(); 
			Signature addedSignature = signatures.get(signatures.size() - 1);
			
			// Standardgreeting einfuegen
			if(StringUtils.isEmpty(addedSignature.getGreeting()))
					addedSignature.setGreeting(DEFAULT_GREETING);
						
			addedSignature.setContext(officeContext);
			eventBroker.post(OfficeUtils.SIGNATURE_REQUESTSELECTIONEVENT, addedSignature);	
		}		
	}
	
	/**
	 * SignatureSetRenderer meldet geloeschte Signature
	 * 
	 * @param event
	 */
	@Inject
	@Optional
	public void handlRemoveEvent(@UIEventTopic(OfficeUtils.REMOVE_SIGNATURE_EVENT) Object event)
	{
		if (event instanceof Signature)
		{
			//Signature removeSignature = (Signature) event;
			//System.out.println("Delete: " + removeSignature.getName());			
		}		
	}

	/*
	 * Renderer meldet die Selektion einer Signatur
	 * (die selektierte Signatur wird in das Dokument geschrieben)
	 */
	@Inject 
	@Optional
	public void handleSignatureSelection(@UIEventTopic(OfficeUtils.SELECT_SIGNATURE_EVENT) Object object)
	{
		if (object instanceof Signature)		
			this.selectedSignature =  (Signature) object;							
	}

	/*
	public IEventBroker getEventBroker()
	{
		return eventBroker;
	}
	*/

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
			
			signatureSet.getSignatures().add(readSignature);
		}
	}

	/* 
	 * Dieser Aufruf erfolgt im Zuge einer 'doPerformFinish()' Action des Wizards.
	 * Die selektierte Signatur in das Dokument schreiben.
	 * Name der Signatur im DialogSetting speichern.
	 */
	@Override
	public void writeToDocument(TextDocument odfDocument)
	{
		removeTemporaereSignaturen();
		
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

				value = selectedSignature.getSigner();
				if(StringUtils.isNotEmpty(value))
					ODFDocumentUtils.writeTableText(table, 2, 0, value);

				value = selectedSignature.getCosigner();
				if(StringUtils.isNotEmpty(value))
					ODFDocumentUtils.writeTableText(table, 2, 1, value);
			}
			
			// Signaturnamen im Setting ablegen
			dialogSettings.put(dialogSettingName, selectedSignature.getName());
		}		
	}
	
	/*
	 * Neue Signature erzeugen mit Namen und OfficeContext
	 */
	private Signature createSignature(String signatureName, String officeContext)
	{
		EClass signatureClass = AddressPackage.eINSTANCE.getSignature();
		Signature signature = (Signature) EcoreUtil.create(signatureClass);
		signature.setName(signatureName);
		signature.setContext(officeContext);
		return signature;
	}

	@Override
	public void cancelPage(TextDocument odfDocument)
	{
		removeTemporaereSignaturen();
	}
	
	/*
	 * die temporaer erzeugten Objekte (z.B. beim Einlesen aus dem Dokument erzeugt) wieder loeschen
	 */
	private void removeTemporaereSignaturen()
	{
		List<Signature>removeList = new ArrayList<Signature>();
		
		EList<Signature>signatures = signatureSet.getSignatures();
		for(Signature signature : signatures)
		{
			if(StringUtils.equals(signature.getName(), LOADSIGNATURE))
				removeList.add(signature);
		}
		
		signatureSet.getSignatures().removeAll(removeList);
	}

	@Override
	public void unDo(TextDocument odfDocument)
	{
		// TODO Auto-generated method stub
		
	}



}
