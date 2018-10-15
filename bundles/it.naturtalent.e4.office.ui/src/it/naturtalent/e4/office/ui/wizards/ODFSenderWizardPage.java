package it.naturtalent.e4.office.ui.wizards;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.internal.workbench.swt.WorkbenchSWTActivator;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.ui.view.ECPRendererException;
import org.eclipse.emf.ecp.ui.view.swt.ECPSWTViewRenderer;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
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
import it.naturtalent.office.model.address.Referenz;
import it.naturtalent.office.model.address.Sender;



/**
 * Wizardseite zur Definition der Absenderangaben. 
 * 
 * @author dieter
 *
 */
public class ODFSenderWizardPage extends WizardPage implements IWriteWizardPage
{
	
	// Key DialogSetting der Kontaktnamen
	private static final String SENDERKONTACTNAMES = "senderkontactnames";
	
	// Label des eingelesenen Absenders
	private static final String LOADED_ABSENDER = "Absender aus der Datei";
	
	private static final String DEFAULT_CONTEXT = "Defaultcontext";
	protected String context = DEFAULT_CONTEXT;


	private IDialogSettings dialogSettings = WorkbenchSWTActivator.getDefault().getDialogSettings();
	
	private Sender senders;
	private Absender selectedAbsender;
	
	
	/**
	 * Create the wizard.
	 */
	public ODFSenderWizardPage()
	{
		super(ODFDefaultWriteAdapterWizard.SENDER_PAGE_NAME);
		setMessage("einen Absender auswählen");
		setTitle("Absender");
		setDescription("Angaben zum Absender");
		
		//EClass sendersClass = AddressPackage.eINSTANCE.getSenders();
		//senders = (Senders) EcoreUtil.create(sendersClass);
		//initSendersData();
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
			senders = OfficeUtils.getSender();
			ECPSWTViewRenderer.INSTANCE.render(container, (EObject) senders);			
		} catch (ECPRendererException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	@Inject
	@Optional
	public void handleDetailModelChangedEvent(@UIEventTopic(OfficeUtils.ABSENDER_DETAIL_SELECTED_EVENT) Absender absender)
	{
		this.selectedAbsender = absender;
	}
	
	@Inject
	@Optional
	public void handleMasterModelChangedEvent(@UIEventTopic(OfficeUtils.ABSENDERMASTER_SELECTED_EVENT) Absender absender)
	{
		this.selectedAbsender = absender;
	}
	
	/*
	 * Die Absenderdaten werden von den gespeicherten Kontakten abgeleitet, d.h. der
	 * Absender muss als Kontakt gespeichert sein.
	 *  
	 * Im DialogSetting werden diejenigen Kontaktnamen gespeichert, 
	 * die als Absenderadressen verwendet werden.
	 * 
	 * 
	 */
	private void initSendersData()
	{
		String [] kontactNames = dialogSettings.getArray(SENDERKONTACTNAMES);		
		List<Kontakt>kontacts = OfficeUtils.findKontactsByNames(kontactNames);
		for(Kontakt kontakt : kontacts)
		{
			// Absender erzeugen
			EClass absenderClass = AddressPackage.eINSTANCE.getAbsender();
			Absender absender = (Absender) EcoreUtil.create(absenderClass);
			absender.setName(kontakt.getName());
			absender.setAdresse(kontakt.getAdresse());
			senders.getSenders().add(absender);
		}
	}
	
		
	/**
	 * Die Absenderdaten werden als Dialogsettings gespeichert.
	 * 
	 */
	public void storeSenderData()
	{
		List<String>kontactNames = new ArrayList<String>();
		
		List<Absender>allAbsender = senders.getSenders();
		for(Absender absender : allAbsender)
			kontactNames.add(absender.getName());
		
		String[]nameArray = kontactNames.toArray(new String[kontactNames.size()]);
		dialogSettings.put(SENDERKONTACTNAMES, nameArray);		
	}

	@Override
	public void writeToDocument (TextDocument odfDocument)
	{
		if((selectedAbsender != null) && (odfDocument != null))
		{
			// Adresstabelle lesen
			Table table = odfDocument.getTableByName(IODFWriteAdapter.ODF_WRITESENDER);
			
			// Tabelle loeschen			
			CellRange cellRange = ODFDocumentUtils.markTable(table); 		
			ODFDocumentUtils.clearCellRange(cellRange);
							
			Adresse adresse = selectedAbsender.getAdresse();
			if (adresse != null)
			{
				String value = adresse.getName();
				if (StringUtils.isNotEmpty(value))
					ODFDocumentUtils.writeTableText(table, 1, 0, value);

				value = adresse.getStrasse() + ",";
				value = value + adresse.getOrt();
				ODFDocumentUtils.writeTableText(table, 2, 0, value);
			}
		
		}
		
	}

	@Override
	public void readFromDocument(TextDocument odfDocument)
	{	
		Table table = odfDocument.getTableByName(IODFWriteAdapter.ODF_WRITESENDER);
		if (table != null)
		{
			EClass absenderClass = AddressPackage.eINSTANCE.getAbsender();
			Absender tempAbsender = (Absender) EcoreUtil.create(absenderClass);
			tempAbsender.setName(LOADED_ABSENDER);
			tempAbsender.setContext(context);
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
					// in der untersten Zeile der Tabelle wird (Stasse,Ort) erwartet
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
	
	@Override
	public void cancelPage(TextDocument odfDocument)
	{
		EditingDomain domain = AdapterFactoryEditingDomain.getEditingDomainFor(senders);	
		
		if(domain.getCommandStack().canUndo())
			domain.getCommandStack().undo();
	}
	
	
	

}
