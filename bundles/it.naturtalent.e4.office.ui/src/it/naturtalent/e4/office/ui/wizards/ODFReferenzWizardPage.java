package it.naturtalent.e4.office.ui.wizards;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.EventObject;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.di.extensions.Preference;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.internal.workbench.E4Workbench;
import org.eclipse.e4.ui.internal.workbench.swt.WorkbenchSWTActivator;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CommandStackListener;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.ui.view.ECPRendererException;
import org.eclipse.emf.ecp.ui.view.swt.ECPSWTViewRenderer;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.DeleteCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.emfstore.internal.client.model.changeTracking.commands.EMFStoreBasicCommandStack;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.PreferencesUtil;
import org.odftoolkit.simple.TextDocument;
import org.odftoolkit.simple.table.CellRange;
import org.odftoolkit.simple.table.Table;

import it.naturtalent.e4.office.ui.ODFDocumentUtils;
import it.naturtalent.e4.office.ui.OfficeUtils;
import it.naturtalent.e4.office.ui.preferences.OfficeDefaultPreferenceUtils;
import it.naturtalent.e4.office.ui.wizards.IWriteWizardPage;
import it.naturtalent.e4.office.ui.wizards.ODFDefaultWriteAdapterWizard;
import it.naturtalent.office.model.address.Absender;
import it.naturtalent.office.model.address.AddressPackage;
import it.naturtalent.office.model.address.FootNote;
import it.naturtalent.office.model.address.Referenz;
import it.naturtalent.office.model.address.ReferenzSet;
import it.naturtalent.office.model.address.Referenzen;


/**
 * Seite zum Bearbeiten der Telekom Office Referenzen.
 * 
 * @author dieter
 *
 */
public class ODFReferenzWizardPage extends WizardPage  implements IWriteWizardPage
{

	// Name dieser WizardPage
	public static final String REFERENCE_PAGE_NAME = "referenzpage";
	
	// Name der Referenztabelle im ODFDokument
	public static String ODF_WRITEREFERENZ = "Referenztabelle";
	
	// Label fuer die aus dem ODF-Dokument eingelesene Referenz
	private static final String LOADEDREFERENCE = "Referenz aus der Datei";
	private EditingDomain domain;
	private EReference eReference;
	
	// Office-Praeferenzknoten
	private IEclipsePreferences instancePreferenceNode;
	
	// OfficeKontext 
	private String officeContext;
	
	
	private IEventBroker eventBroker;	
		
	//private Text textReferences;
		
	private Referenzen referenzen;
	
	// die aktuelle Referenz
	private Referenz referenz;
	
	/**
	 * Create the wizard.
	 */
	public ODFReferenzWizardPage()
	{
		super(REFERENCE_PAGE_NAME);
		setMessage("Referenzen erstellen, bearbeiten und auswählen\n(siehe auch Präferenzen)");
		setTitle("Referenzen");
		setDescription("Referenz auswählen");
	}
	
	@PostConstruct
	private void postConstruct(@Optional IEventBroker eventBroker, IEclipseContext context)	
	{				
		this.eventBroker = eventBroker;
		
		// der Wizard hat den zubenutzenden OfficeContext im E4Context hinterlegt
		officeContext = (String) context.get(OfficeUtils.E4CONTEXTKEY_OFFICECONTEXT);
		
		instancePreferenceNode = (IEclipsePreferences) context.get(OfficeUtils.E4CONTEXTKEY_OFFICEPRAEFERENZNODE);
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
		
		referenzen = (Referenzen) OfficeUtils.findObject(AddressPackage.eINSTANCE.getReferenzen());
		
		// Parameter fuer die EMF Command (add)
		domain = AdapterFactoryEditingDomain.getEditingDomainFor(referenzen);
		eReference = AddressPackage.eINSTANCE.getReferenzen_Referenzen();
		
		try
		{	
			// die Referenzen im officeContext anzeigen
			ECPSWTViewRenderer.INSTANCE.render(container, referenzen);	
			
			selectDefaultReferenz();
			
		} catch (ECPRendererException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	/**
	 * im 'Create-Modus' wird die praeferenzierte Referenz im Master selektiert. 
	 *  
	 * @return
	 */
	public void selectDefaultReferenz()
	{
		ODFDefaultWriteAdapterWizard defaultWizard = (ODFDefaultWriteAdapterWizard) getWizard();
		if (defaultWizard.isWizardModus() == ODFDefaultWriteAdapterWizard.WIZARDCREATEMODE)		
		{
			String preferenceName = instancePreferenceNode.get(OfficeDefaultPreferenceUtils.REFERENZ_PREFERENCE, null);
			Referenz referenz = OfficeUtils.findReferenz(preferenceName, officeContext);
			
			eventBroker.post(OfficeUtils.SET_OFFICEMASTER_SELECTION_EVENT, referenz);
		}
	}

	/* 
	 * Ist eine Referenz im Modell selektiert, wird diese beim Beenden des Wizards mit 'OK' in das ODFDokument geschrieben.
	 * @see handleReferenceSelection()
	 */
	@Override
	public void writeToDocument(TextDocument odfDocument)
	{
		if((referenz != null) && (odfDocument != null))
		{
			Table table = odfDocument.getTableByName(ODF_WRITEREFERENZ);
			if (table != null)
			{
				// Spalte 1 der Refereztabelle loeschen (ausser Datum)				
				CellRange cellRange = table.getCellRangeByPosition(1,
						0, table.getColumnCount() - 1, table.getRowCount() - 2);
				ODFDocumentUtils.clearCellRange(cellRange);
				
				// 'Ihre Referenzen'				
				int row = 0;
				String value = referenz.getReferenz();
				if(StringUtils.isNotEmpty(value))
					ODFDocumentUtils.writeTableText(table, row, 1, value);
				
				// Ansprechpartner
				++row;
				value = referenz.getReferenz2();
				if(StringUtils.isNotEmpty(value))
					ODFDocumentUtils.writeTableText(table, row, 1, value);
				
				// Durchwahl
				++row;
				value = referenz.getReferenz3();
				if(StringUtils.isNotEmpty(value))
					ODFDocumentUtils.writeTableText(table, row, 1, value);
				
				
				// Datum
				// im CreateMode wird das heutige Datum eingetragn
				++row;
				
				ODFDefaultWriteAdapterWizard wizard = (ODFDefaultWriteAdapterWizard) getWizard();
				if(wizard.isWizardModus() == ODFDefaultWriteAdapterWizard.WIZARDCREATEMODE)				
				{
					DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
					Calendar cal = df.getCalendar();
					cal.setTimeInMillis(System.currentTimeMillis());
					value = df.format(cal.getTime());
					ODFDocumentUtils.writeTableText(table, row, 1, value);
				}
			}	
		}
	}

	@Override
	public void readFromDocument(TextDocument odfDocument)
	{
		referenz = readReferenz(odfDocument);
		
		eventBroker.post(OfficeUtils.SET_OFFICEMASTER_SELECTION_EVENT, referenz);
	}
	
	/* 
	 * Die aus dem ODFDokument eingelesene Referenz wird temporaer dem Modell hinzugefuegt. 
	 * 
	 */	
	private Referenz readReferenz(TextDocument odfDocument)
	{
		Referenz tempReferenz = null;
		
		Table table = odfDocument.getTableByName(ODF_WRITEREFERENZ);
		if (table != null)
		{
			EClass referenzClass = AddressPackage.eINSTANCE.getReferenz();
			tempReferenz = (Referenz) EcoreUtil.create(referenzClass);
			
			tempReferenz.setName(LOADEDREFERENCE);
			tempReferenz.setContext(officeContext);
			tempReferenz.setReferenz(ODFDocumentUtils.readTableText(table, 0, 1));
			tempReferenz.setReferenz2(ODFDocumentUtils.readTableText(table, 1, 1));
			tempReferenz.setReferenz3(ODFDocumentUtils.readTableText(table, 2, 1));
			
			Command addCommand = AddCommand.create(domain, referenzen, eReference, tempReferenz);
			if (addCommand.canExecute())
				domain.getCommandStack().execute(addCommand);
		}
		
		return tempReferenz;
	}
	
	/* 
	 * Undo Aktion nach einem Cancel oder nach dem 'writeToDocument(TextDocument odfDocument)'
	 * im Zuge von Performd OK des Wizards
	 * 
	 */
	@Override
	public void cancelPage(TextDocument odfDocument)
	{
		
	}

	@Override
	public void unDo(TextDocument odfDocument)
	{
		Referenz toRemove = OfficeUtils.findReferenz(LOADEDREFERENCE, officeContext);
		if(toRemove != null)
		{
			Command removeCommand = RemoveCommand.create(domain, referenzen, eReference, toRemove);
			if (removeCommand.canExecute())
				removeCommand.execute();
		}				
	}

	/*
	 * der Broker hat eine Selection im Master empangen und meldet diese
	 */
	@Inject
	@Optional
	public void handleSelectionChangedEvent(@UIEventTopic(OfficeUtils.GET_OFFICEMASTER_SELECTION_EVENT) EObject eObject)
	{
		// handelt es sich bei der Selektion um eine Referenz, dann wird diese als 'aktuelle' gespeichert
		if (eObject instanceof Referenz)
			referenz =  (Referenz) eObject;
	}
	
	

}
