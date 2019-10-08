package it.naturtalent.e4.office.ui.wizards;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

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
import org.odftoolkit.simple.TextDocument;
import org.odftoolkit.simple.table.CellRange;
import org.odftoolkit.simple.table.Table;
import org.odftoolkit.simple.text.Footer;

import it.naturtalent.e4.office.ui.ODFDocumentUtils;
import it.naturtalent.e4.office.ui.OfficeUtils;
import it.naturtalent.e4.office.ui.preferences.OfficeDefaultPreferenceUtils;
import it.naturtalent.office.model.address.Absender;
import it.naturtalent.office.model.address.AddressPackage;
import it.naturtalent.office.model.address.FootNote;
import it.naturtalent.office.model.address.FootNoteItem;
import it.naturtalent.office.model.address.FootNotes;
import it.naturtalent.office.model.address.Referenz;

public class ODFFootNoteWizardPage extends WizardPage implements IWriteWizardPage
{
	// Name dieser WizardPage	
	private final static String FOOTNOTE_PAGE_NAME = "ODF_FOOTNOTE";
	
	// Name der FootNotetabelle im ODFDokument
	public static String ODF_WRITEFOOTNOTE = "Footertabelle"; //$NON-NLS-N$
	
	// Label fuer die aus dem ODF-Dokument eingelesene Referenz
	private static final String LOADEDFOOTNOTE_LABEL = "Fußnoten aus der Datei"; //$NON-NLS-N$
	private EditingDomain domain;
	private EReference eReference;
		
	// Office-Praeferenzknoten
	protected IEclipsePreferences instancePreferenceNode;
		
	// OfficeKontext 
	private String officeContext;
	
	private IEventBroker eventBroker;
	
	// Container aller FootNotes im Modell
	private FootNotes footNotes;
	
	private FootNote selectedFootNote;
	
	
	
	public ODFFootNoteWizardPage()
	{
		super(FOOTNOTE_PAGE_NAME);
		
		setMessage("Fußnoten erstellen, bearbeiten und auswählen\n(siehe auch Präferenzen)"); //$NON-NLS-N$
		setTitle("Fußnoten"); //$NON-NLS-N$
		setDescription("Anmerkungen, Legenden bzw. Informationen außerhalb des Layouts"); //$NON-NLS-N$
	}
	
	@PostConstruct
	private void postConstruct(@Optional IEventBroker eventBroker, IEclipseContext context)
	{
		this.eventBroker = eventBroker;
		
		// der Wizard hat den zubenutzenden OfficeContext im E4Context hinterlegt
		officeContext = (String) context.get(OfficeUtils.E4CONTEXTKEY_OFFICECONTEXT);
		
		instancePreferenceNode = (IEclipsePreferences) context.get(OfficeUtils.E4CONTEXTKEY_OFFICEPRAEFERENZNODE);
	}

	@Override
	public void createControl(Composite parent)
	{
		Composite container = new Composite(parent, SWT.NULL);	
		setControl(container);
		container.setLayout(new GridLayout(2, false));

		footNotes = (FootNotes) OfficeUtils.findObject(AddressPackage.eINSTANCE.getFootNotes());
				
		try
		{
			// Renderer zur Anzeige der definierten FootNotes  (filtert 'officecontext' im Master) 
			ECPSWTViewRenderer.INSTANCE.render(container, (EObject) footNotes);
			
			selectDefaultFootNote();
			
		} catch (ECPRendererException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
	}
	
	/**
	 * im 'Create-Modus' wird die praeferenzierte Referenz im Master selektiert. 
	 *  
	 * @return
	 */
	public void selectDefaultFootNote()
	{
		ODFDefaultWriteAdapterWizard defaultWizard = (ODFDefaultWriteAdapterWizard) getWizard();
		if (defaultWizard.isWizardModus() == ODFDefaultWriteAdapterWizard.WIZARDCREATEMODE)		
		{
			String preferenceName = instancePreferenceNode.get(OfficeDefaultPreferenceUtils.FOOTNOTE_PREFERENCE, null);
			FootNote footNote = OfficeUtils.findFootNoteByName(preferenceName, officeContext);
			
			eventBroker.post(OfficeUtils.SET_OFFICEMASTER_SELECTION_EVENT, footNote);
		}
	}

	
	@Override
	public void writeToDocument(TextDocument odfDocument)
	{
		writeToDocument(odfDocument, selectedFootNote);
	}

	
	private void writeToDocument(TextDocument odfDocument, FootNote footNote)
	{
		if((footNote != null) && (odfDocument != null))
		{
			Footer footer = odfDocument.getFooter();
			Table table = footer.getTableByName(ODF_WRITEFOOTNOTE);
			if (table != null)
			{
				// Tabelle loeschen		
				CellRange cellRange = ODFDocumentUtils.markTable(table); 
				ODFDocumentUtils.clearCellRange(cellRange);
				
				EList<FootNoteItem>footNoteItems = footNote.getFootnoteitems();
				if((footNoteItems != null) && (!footNoteItems.isEmpty()))
				{					
					int n = footNoteItems.size();
					for(int i = 0, row = 0; i < n; i++, row++)
					{
						if(row >= table.getRowCount())
							break;
						
						FootNoteItem footNoteItem = footNoteItems.get(i);
						String key = footNoteItem.getKey();
						ODFDocumentUtils.writeTableText(table, row, 0,key);
						
						String value = footNoteItem.getValue();
						ODFDocumentUtils.writeTableText(table, row, 1,value);						
					}					
				}
			}
		}		
	}

	@Override
	public void readFromDocument(TextDocument odfDocument)
	{
		selectedFootNote = readFootNote(odfDocument);
		
		// den vom Dokument eingelesenen Absender im Master selektieren
		eventBroker.post(OfficeUtils.SET_OFFICEMASTER_SELECTION_EVENT, selectedFootNote);
	}
		
	/*
	 * die Fussnote des Dokuments wird eingelesen und temporaer im EMF Modell gespeichert
	 */
	private FootNote readFootNote(TextDocument odfDocument)
	{
		FootNote footNote = null; 
		
		Table table = odfDocument.getFooter().getTableByName(ODF_WRITEFOOTNOTE);
		if (table != null)
		{
			// eine Fussnote erstellen 
			EClass footNoteClass = AddressPackage.eINSTANCE.getFootNote();
			footNote = (FootNote) EcoreUtil.create(footNoteClass);
			footNote.setName(LOADEDFOOTNOTE_LABEL);
			footNote.setContext(officeContext);

			// die Items einlesen und hinzufuegen
			int n = table.getRowCount();
			for (int row = 0; row < n; row++)
			{
				// neues FootNoteItem erzeugen
				EClass footeNoteItemClass = AddressPackage.eINSTANCE.getFootNoteItem();
				FootNoteItem footNoteItem = (FootNoteItem) EcoreUtil.create(footeNoteItemClass);

				// Daten aus dem ODFDokument einlesen
				String key = ODFDocumentUtils.readTableText(table, row, 0);
				String value = ODFDocumentUtils.readTableText(table, row,1);

				//if (StringUtils.isEmpty(key) && StringUtils.isEmpty(value))
					//continue;

				footNoteItem.setKey(key);
				footNoteItem.setValue(value);

				footNote.getFootnoteitems().add(footNoteItem);
			}
			
			// die eingelesenen Fussnote temporaer in das EMF-Modell uebernehemn			
			domain = AdapterFactoryEditingDomain.getEditingDomainFor(footNotes);
			eReference = AddressPackage.eINSTANCE.getFootNotes_FootNotes();
			
			Command addCommand = AddCommand.create(domain, footNotes, eReference, footNote);
			if (addCommand.canExecute())
				domain.getCommandStack().execute(addCommand);
			
			
		}
		return footNote;			
	}


	@Override
	public void cancelPage(TextDocument odfDocument)
	{
		// TODO Auto-generated method stub
	
	}

	@Override
	public void unDo(TextDocument odfDocument)
	{
		FootNote toRemove = OfficeUtils.findFootNoteByName(LOADEDFOOTNOTE_LABEL, officeContext);
		if(toRemove != null)
		{
			Command removeCommand = RemoveCommand.create(domain, footNotes, eReference, toRemove);
			if (removeCommand.canExecute())
				removeCommand.execute();
		}
	}
	
	@Inject
	@Optional
	public void handleSelectionChangedEvent(@UIEventTopic(OfficeUtils.GET_OFFICEMASTER_SELECTION_EVENT) EObject eObject)
	{
		if (eObject instanceof FootNote)		
			selectedFootNote = (FootNote) eObject;								
		else
		{
			if (eObject instanceof FootNoteItem)
			{
				selectedFootNote = (FootNote) ((FootNoteItem) eObject).eContainer();				
			}
		}		
	}
	

}
