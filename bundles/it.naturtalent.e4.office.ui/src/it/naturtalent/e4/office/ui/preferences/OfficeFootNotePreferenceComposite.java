package it.naturtalent.e4.office.ui.preferences;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.internal.workbench.E4Workbench;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.spi.ui.util.ECPHandlerHelper;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.osgi.service.prefs.BackingStoreException;

import it.naturtalent.e4.office.ui.OfficeUtils;
import it.naturtalent.e4.office.ui.dialogs.AbsenderDialog;
import it.naturtalent.e4.office.ui.dialogs.FootNoteDialog;
import it.naturtalent.e4.office.ui.dialogs.OfficePreferenzDialog;
import it.naturtalent.e4.preferences.CheckListEditorComposite;
import it.naturtalent.office.model.address.Absender;
import it.naturtalent.office.model.address.AddressPackage;
import it.naturtalent.office.model.address.Adresse;
import it.naturtalent.office.model.address.FootNote;
import it.naturtalent.office.model.address.FootNoteItem;
import it.naturtalent.office.model.address.FootNotes;
import it.naturtalent.office.model.address.Sender;




/**
 * Eine Praeferenzliste (Checkliste) mit den Namen der FootNootepraeferenznamen,
 * 
 * @author dieter
 *
 */
public class OfficeFootNotePreferenceComposite extends CheckListEditorComposite
{
	// Office-Praeferenzknoten
	protected IEclipsePreferences instancePreferenceNode = InstanceScope.INSTANCE
			.getNode(OfficeDefaultPreferenceUtils.ROOT_DEFAULTOFFICE_PREFERENCES_NODE);
	
	// Liste aller FootNotes in diesem Context
	protected List<FootNote>contextFootNotes = new ArrayList<FootNote>();

	// Kontext 
	protected String officeContext = OfficeDefaultPreferenceUtils.DEFAULT_OFFICE_CONTEXT;

	// Default Praeferenzname der FootNotes 
	protected String defaultName = OfficeDefaultPreferenceUtils.DEFAULT_FOOTNOTENAME;
	
	// erfoerderlich fuer EMF-Commandos
	private EObject container;
	private EditingDomain domain;
	private EReference eReference;
	
	// zusaetzliche Copy-Button
	private Button btnCopy;
		
	/**
	 * Konstruktuor
	 * 
	 * @param parent
	 * @param style
	 */	
	public OfficeFootNotePreferenceComposite(Composite parent, int style)
	{
		super(parent, style);
		
		btnCopy = new Button(btnComposite, SWT.NONE);
		btnCopy.setText("copy"); //$NON-NLS-N$
		btnCopy.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				doCopy();
			}
		});
		
		init();			
	}
	
	
	protected void init()
	{
		// Footnotes via EMF-Commands handeln
		container = OfficeUtils.findObject(AddressPackage.eINSTANCE.getFootNotes());
		domain = AdapterFactoryEditingDomain.getEditingDomainFor(container);	
		eReference = AddressPackage.eINSTANCE.getFootNotes_FootNotes();
	
		// die KontextFootNotes laden
		loadContextFootNotes();
		
		// Praeferenznamen der FootNotes in der Praeferenzliste anzeigen
		setPreferenceList();
		
		// Name der aktuelle FootNotePraeferenz in der Praeferenzliste checken
		String footNotePreferenz = instancePreferenceNode.get(
				OfficeDefaultPreferenceUtils.FOOTNOTE_PREFERENCE,
				OfficeDefaultPreferenceUtils.DEFAULT_FOOTNOTENAME);		
		
		// existiert die FootNote noch im Modell
		if(findFootNote(footNotePreferenz) == null)
			footNotePreferenz = OfficeDefaultPreferenceUtils.DEFAULT_FOOTNOTENAME;
		
		//checken
		setCheckedElements(new String [] {footNotePreferenz});
		
		updateWidgets();

	}
	
	@Override
	protected void updateWidgets()
	{
		// TODO Auto-generated method stub
		super.updateWidgets();
		
		btnCopy.setEnabled(false);
		IStructuredSelection selection = checkboxTableViewer.getStructuredSelection();
		Object selObj = selection.getFirstElement();
		if (selObj instanceof String)
		{
			// die selektierte FootNote suchen
			String prefName = (String) selObj;
			FootNote footNote = findFootNote(prefName);
			if(footNote != null)
			{		
				// DefaultFootNote wird nicht geloescht
				btnRemove.setEnabled(!StringUtils.equals(footNote.getName(), defaultName));
				
				btnCopy.setEnabled(true);
			}
		}
	}
	
	@Override
	protected void doChecked()
	{
		IStructuredSelection selection = checkboxTableViewer.getStructuredSelection();
		Object selObj = selection.getFirstElement();
		if (selObj instanceof String)
		{
			// gecheckte Footnote als Preferenz speichern (noch nicht geflushed)
			instancePreferenceNode.put(OfficeDefaultPreferenceUtils.FOOTNOTE_PREFERENCE, (String) selObj);
		}
	}

	/*
	 * die FootNotenamen in die Praeferenzliste eintragen 
	 */
	private void setPreferenceList()
	{
		List<String>preferenceNames = new ArrayList<String>();
		for(FootNote footNote: contextFootNotes)
			preferenceNames.add(footNote.getName());		
		checkboxTableViewer.setInput(preferenceNames);
	}
		
	/*
	 * eine neue FootNote hinzufuegen
	 */
	@Override
	protected void doAdd()
	{
		// neue FootNote fuer diesen Context generieren 
		FootNote newFootNote = generateContextFootNote();
		
		// neue FootNote mit Dialog bearbeiten
		IEclipseContext context = E4Workbench.getServiceContext();
		OfficePreferenzDialog dialog = ContextInjectionFactory.make(OfficePreferenzDialog.class, context);
		dialog.setPreferenz(newFootNote);
		
		/*
		FootNoteDialog dialog = ContextInjectionFactory.make(FootNoteDialog.class, context);
		dialog.setFootNote(newFootNote);
		*/		
		
		// Default-Praeferenznamen via E4Context dem Renderer uebergeben
		E4Workbench.getServiceContext().set(OfficeDefaultPreferenceUtils.E4CONTEXT_DEFAULTNAME, defaultName);
		
		if (dialog.open() == OfficePreferenzDialog.OK)
		{			
			// DefaultFootNote zum Modell hinzufuegen und speichern
			Command addCommand = AddCommand.create(domain, container , eReference, newFootNote);
			if(addCommand.canExecute())	
			{
				domain.getCommandStack().execute(addCommand);	
				
				// die neue FootNote zur ContextFootNote-Liste hinzufuegen
				contextFootNotes.add(newFootNote);
				
				// Praeferenzliste aktualisieren
				setPreferenceList();
			}
		}
		
		E4Workbench.getServiceContext().remove(OfficeDefaultPreferenceUtils.E4CONTEXT_DEFAULTNAME);
	}

	/* 
	 * Die selektierte FootNote bearbeiten
	 */
	@Override
	protected void doEdit()
	{		
		IStructuredSelection selection = checkboxTableViewer.getStructuredSelection();
		Object selObj = selection.getFirstElement();
		if (selObj instanceof String)
		{
			// den selektierten Absender suchen
			String prefName = (String) selObj;
			FootNote footNote = findFootNote(prefName);
			
			if(footNote != null)
			{
				FootNote footNoteCopy = EcoreUtil.copy(footNote);
				
				// Kopie mit dem Dialog bearbeiten
				IEclipseContext context = E4Workbench.getServiceContext();
				OfficePreferenzDialog dialog = ContextInjectionFactory.make(OfficePreferenzDialog.class, context);
				
				FootNotes dlgNotes = getFootNotes(footNoteCopy);
								
				//dialog.setPreferenz(dlgNotes);
				dialog.setPreferenz(footNoteCopy);
				
				
				// Default-Praeferenznamen via E4Context dem Renderer uebergeben
				E4Workbench.getServiceContext().set(OfficeDefaultPreferenceUtils.E4CONTEXT_DEFAULTNAME, defaultName);
				
				if (dialog.open() == OfficePreferenzDialog.OK)
				{
					// Original-Absender aus dem Modell entfernen
					Command removeCommand = RemoveCommand.create(domain,container, eReference, footNote);
					if (removeCommand.canExecute())		
					{
						domain.getCommandStack().execute(removeCommand);
						contextFootNotes.remove(footNote);
					}
					
					Command addCommand = AddCommand.create(domain, container , eReference, footNoteCopy);
					if(addCommand.canExecute())	
					{
						domain.getCommandStack().execute(addCommand);	
						contextFootNotes.add(footNoteCopy);
					}
					
					// Praeferenzliste aktualisieren
					setPreferenceList();
					
					// Defaultadresse checken						
					setCheckedElements(new String[]{footNoteCopy.getName()});
				}
				
				E4Workbench.getServiceContext().remove(OfficeDefaultPreferenceUtils.E4CONTEXT_DEFAULTNAME);
			}
		}
	}
	

	@Override
	protected void doRemove()
	{
		IStructuredSelection selection = checkboxTableViewer.getStructuredSelection();
		Object selObj = selection.getFirstElement();
		if (selObj instanceof String)
		{
			// den selektierten Absender suchen
			String prefName = (String) selObj;
			FootNote footNote = findFootNote(prefName);
			if(footNote != null)
			{
				// DefaultAbsender wird nicht geloescht
				if (!StringUtils.equals(footNote.getName(), defaultName))
				{
					// Absender aus dem Modell entfernen
					Command removeCommand = RemoveCommand.create(domain,
							container, eReference, footNote);
					if (removeCommand.canExecute())
					{
						domain.getCommandStack().execute(removeCommand);

						// den alten Absender aus der ContextAbsender-Liste entfernen
						contextFootNotes.remove(footNote);
						
						// Praeferenzliste aktualisieren
						setPreferenceList();
						
						// Defaultadresse checken						
						setCheckedElements(new String[]{defaultName});
					}
				}
			}
		}
	}
	
	/*
	 * festschreiben der Eingabe
	 */	
	public void appliedPressed()
	{
		// das Modell festschreiben
		ECPHandlerHelper.saveProject(OfficeUtils.getOfficeProject());
		
		// das gecheckte Element wird als FootNotepraeferenz gespeichert
		String [] checkedElements = getCheckedElements();
		if(ArrayUtils.isNotEmpty(checkedElements))
		{
			instancePreferenceNode.put(OfficeDefaultPreferenceUtils.FOOTNOTE_PREFERENCE,checkedElements[0]);
			try
			{
				instancePreferenceNode.flush();
			} catch (BackingStoreException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/*
	 * alle Commands rueckgaengig machen 
	 */
	public void doCancel()
	{
		if(domain != null)
		{
			while(domain.getCommandStack().canUndo())
				domain.getCommandStack().undo();
		}
	}
	
	protected void doCopy()
	{
		IStructuredSelection selection = checkboxTableViewer.getStructuredSelection();
		Object selObj = selection.getFirstElement();
		if (selObj instanceof String)
		{
			// den selektierten Absender suchen
			String prefName = (String) selObj;
			FootNote footNote = findFootNote(prefName);
			
			if(footNote != null)
			{
				FootNote footNoteCopy = EcoreUtil.copy(footNote);
				footNoteCopy.setName(prefName+"copy");
				
				// die Kopie hinzufuegen
				Command addCommand = AddCommand.create(domain, container , eReference, footNoteCopy);
				if(addCommand.canExecute())	
				{
					domain.getCommandStack().execute(addCommand);	
					
					// den neuen Absender zur ContextAbsender-Liste hinzufuegen
					contextFootNotes.add(footNoteCopy);
					
					// Praeferenzliste aktualisieren
					setPreferenceList();
				}
			}
		}
	}
	
	/*
	 * Alle FootNotes aus dem Modell einlesen und nach dem Kontext filtern
	 * Eine Default-FootNote erzeugen und hinzufuegen
	 */
	protected List<FootNote> loadContextFootNotes()
	{
		// alle gespeicherten FootNotes aus dem Modell einlesen
		FootNotes footNotes = (FootNotes) OfficeUtils.findObject(AddressPackage.eINSTANCE.getFootNotes());		
		EList<FootNote>allFootNotes = footNotes.getFootNotes();
		
		// FootNotes nach Contect filtern
		contextFootNotes.clear();
		if(allFootNotes != null)
		{
			for (FootNote footNote : allFootNotes)
			{
				if(StringUtils.equals(footNote.getContext(),officeContext))
					contextFootNotes.add(footNote);
			}
		}
				
		// Default-FootNote erzeugen, wenn noch keine FootNote mit diesem Kontext existiert 		
		if(findFootNote(defaultName) == null)
		{
			// Kontextfootnote erzeugen mit Default-Praeferenznamen
			FootNote defFootNote = generateContextFootNote();		
			defFootNote.setName(defaultName);			
			contextFootNotes.add(defFootNote);
			
			// Default-FootNote zum Modell hinzufuegen und speichern
			Command addCommand = AddCommand.create(domain, container , eReference, defFootNote);
			if(addCommand.canExecute())	
			{
				domain.getCommandStack().execute(addCommand);		
				ECPHandlerHelper.saveProject(OfficeUtils.getOfficeProject());
			}
		}
		
		// die gefilterten Daten zurueckgeben
		return contextFootNotes;
	}
	
	// eine neue FootNote generieren
	protected FootNote generateContextFootNote()
	{
		// eine neue FootNote generieren 
		EClass footNoteClass = AddressPackage.eINSTANCE.getFootNote();
		FootNote footNote = (FootNote) EcoreUtil.create(footNoteClass);
		footNote.setContext(officeContext);

		// neue FootNoteItem erzeugen und der Fussnote hinzufuegen			
		EClass itemClass = AddressPackage.eINSTANCE.getFootNoteItem();
		FootNoteItem footNoteItem = (FootNoteItem)EcoreUtil.create(itemClass);		
		footNoteItem.setKey("Thema"); //$NON-NLS-N$
		footNoteItem.setValue("Beschreibung"); //$NON-NLS-N$
		footNote.getFootnoteitems().add(footNoteItem);
		
		return footNote;		
	}
	
	private FootNotes getFootNotes(FootNote footNote)
	{
		EClass footNotesClass = AddressPackage.eINSTANCE.getFootNotes();
		FootNotes footNotes = (FootNotes) EcoreUtil.create(footNotesClass);		
		footNotes.getFootNotes().add(footNote);		
		return footNotes;
	}

	private FootNote findFootNote(String footNoteName)
	{
		for (FootNote footNote : contextFootNotes)
		{
			if(StringUtils.equals(footNote.getName(), footNoteName))
				return footNote;
		}

		return null;
	}
	
	/**
	 * Importierte FootNotes, nach OfficeContext gefiltert, uebernehmen.
	 * Bestehende (gleicher Name) werden durch die Importierten ersetzt - neue werden hinzugefuegt. 
	 * 
	 * @param importedReferences - Liste der importierten Absender
	 */
	public void importFootNotes(List<FootNote>importedFootNotes)
	{
		if((importedFootNotes != null))
		{
			for(FootNote importFootNote : importedFootNotes)
			{
				String importedName = importFootNote.getName();
				if ((StringUtils.isNotEmpty(importedName)
						&& StringUtils.equals(importFootNote.getContext(), officeContext)))
				{
					// existiert bereits ein gleichnamiger FootNote
					FootNote existFootNote = findFootNote(importedName);
					if(existFootNote != null)
					{
						// existierende FootNote wird geloescht
						Command removeCommand = RemoveCommand.create(domain,container, eReference, existFootNote);
						if (removeCommand.canExecute())
						{
							domain.getCommandStack().execute(removeCommand);
							contextFootNotes.remove(existFootNote);
						}
					}
										
					// die importierte FootNote wird zum Modell hinzugefuegt
					Command addCommand = AddCommand.create(domain,container, eReference, importFootNote);
					if (addCommand.canExecute())
					{
						domain.getCommandStack().execute(addCommand);
						contextFootNotes.add(importFootNote);
					}
				}
			}
			
			// Praeferenzliste aktualisieren
			setPreferenceList();
		}
	}


}
