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
import it.naturtalent.e4.office.ui.dialogs.OfficePreferenzDialog;
import it.naturtalent.e4.office.ui.dialogs.ReferenzDialog;
import it.naturtalent.e4.preferences.CheckListEditorComposite;
import it.naturtalent.office.model.address.AddressPackage;
import it.naturtalent.office.model.address.Referenz;
import it.naturtalent.office.model.address.Referenzen;




/**
 * Eine Praeferenzliste (Checkliste) mit den Namen der Referenz - Praeferenznamen,
 * 
 * @author dieter
 *
 */
public class OfficeReferenzPreferenceComposite extends CheckListEditorComposite
{
	// Knoten der Office-Praeferenzen
	protected IEclipsePreferences instancePreferenceNode = InstanceScope.INSTANCE
			.getNode(OfficeDefaultPreferenceUtils.ROOT_DEFAULTOFFICE_PREFERENCES_NODE);
	
	// Liste aller Referenzen in diesem Context
	protected List<Referenz>contextReferenzen = new ArrayList<Referenz>();

	// Default Office-Kontext 
	protected String officeContext = OfficeDefaultPreferenceUtils.DEFAULT_OFFICE_CONTEXT;

	// Default Praeferenzname der Referenz 
	protected String defaultName = OfficeDefaultPreferenceUtils.DEFAULT_REFERENZNAME;
	
	// erfoerderlich fuer EMF-Commandos
	private EObject container;
	private EditingDomain domain;
	private EReference eReference;
	
	// zusaetzliche Copy-Button
	private Button btnCopy;
		
	/**
	 * Konstruktion
	 * 
	 * @param parent
	 * @param style
	 */	
	public OfficeReferenzPreferenceComposite(Composite parent, int style)
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
		// Referenzmodellaenderungen via EMF-Commands realisieren
		container = OfficeUtils.findObject(AddressPackage.eINSTANCE.getReferenzen());
		domain = AdapterFactoryEditingDomain.getEditingDomainFor(container);	
		eReference = AddressPackage.eINSTANCE.getReferenzen_Referenzen();
	
		// die Kontextreferenzen laden
		loadContextReferenzen();
		
		// Praeferenznamen der Refenenzen in Praeferenzliste anzeigen
		setPreferenceList();
		
		// Name der aktuelle Referenzpraeferenz
		String referenzPreferenz = instancePreferenceNode.get(
				OfficeDefaultPreferenceUtils.REFERENZ_PREFERENCE,
				OfficeDefaultPreferenceUtils.DEFAULT_REFERENZNAME);		
		
		// existiert die Referenz noch im Modell
		if(findReferenz(referenzPreferenz) == null)
			referenzPreferenz = OfficeDefaultPreferenceUtils.DEFAULT_REFERENZNAME;			
		setCheckedElements(new String [] {referenzPreferenz});
		
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
			// die selektierte Referenz suchen
			String prefName = (String) selObj;
			Referenz referenz = findReferenz(prefName);
			if(referenz != null)
			{		
				// DefaultAbsender wird nicht geloescht
				btnRemove.setEnabled(!StringUtils.equals(referenz.getName(), defaultName));
				
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
			// gecheckte Referenz als Preferenz speichern (aber noch nicht geflushed)
			instancePreferenceNode.put(OfficeDefaultPreferenceUtils.REFERENZ_PREFERENCE, (String) selObj);
		}
	}

	/*
	 * die Referenznamen in die Praeferenzliste eintragen 
	 */
	private void setPreferenceList()
	{
		List<String>referenzNames = new ArrayList<String>();
		for(Referenz referenz : contextReferenzen)
			referenzNames.add(referenz.getName());		
		checkboxTableViewer.setInput(referenzNames);
	}
		
	/*
	 * eine neue Referenz hinzufuegen
	 */
	@Override
	protected void doAdd()
	{
		// TODO Auto-generated method stub
		Referenz referenz = generateContextReferenz();
		
		// neue Referenz mit Dialog bearbeiten
		IEclipseContext context = E4Workbench.getServiceContext();
		OfficePreferenzDialog dialog = ContextInjectionFactory.make(OfficePreferenzDialog.class, context);
		dialog.setPreferenz(referenz);
		
		// Default-Praeferenznamen via E4Context dem Renderer uebergeben
		E4Workbench.getServiceContext().set(OfficeDefaultPreferenceUtils.E4CONTEXT_DEFAULTNAME, defaultName);
		
		if (dialog.open() == OfficePreferenzDialog.OK)
		{			
			// DefaultAbsender zum Modell hinzufuegen und speichern
			Command addCommand = AddCommand.create(domain, container , eReference, referenz);
			if(addCommand.canExecute())	
			{
				domain.getCommandStack().execute(addCommand);	
				
				// die neue Referenz zur ContextReferenz-Liste hinzufuegen
				contextReferenzen.add(referenz);
				
				// Praeferenzliste aktualisieren
				setPreferenceList();
			}
		}
		
		E4Workbench.getServiceContext().remove(OfficeDefaultPreferenceUtils.E4CONTEXT_DEFAULTNAME);
	}

	/* 
	 * Die selektierte Referenz bearbeiten
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
			Referenz referenz = findReferenz(prefName);
			
			if(referenz != null)
			{
				Referenz referenzCopy = EcoreUtil.copy(referenz);
				
				// Kopie mit dem Dialog bearbeiten
				IEclipseContext context = E4Workbench.getServiceContext();
				OfficePreferenzDialog dialog = ContextInjectionFactory.make(OfficePreferenzDialog.class, context);
				dialog.setPreferenz(referenzCopy);
				
				// Default-Praeferenznamen via E4Context dem Renderer uebergeben
				E4Workbench.getServiceContext().set(OfficeDefaultPreferenceUtils.E4CONTEXT_DEFAULTNAME, defaultName);
				
				if (dialog.open() == OfficePreferenzDialog.OK)
				{
					// Original-Referenz aus dem Modell entfernen
					Command removeCommand = RemoveCommand.create(domain,container, eReference, referenz);
					if (removeCommand.canExecute())		
					{
						domain.getCommandStack().execute(removeCommand);
						contextReferenzen.remove(referenz);
					}
					
					Command addCommand = AddCommand.create(domain, container , eReference, referenzCopy);
					if(addCommand.canExecute())	
					{
						domain.getCommandStack().execute(addCommand);	
						contextReferenzen.add(referenzCopy);
					}
					
					// Praeferenzliste aktualisieren
					setPreferenceList();
					
					// Defaultadresse checken						
					setCheckedElements(new String[]{referenzCopy.getName()});
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
			// die selektierte Referenz suchen
			String prefName = (String) selObj;
			Referenz referenz = findReferenz(prefName);
			if(referenz != null)
			{
				// DefaultReferenz wird nicht geloescht
				if (!StringUtils.equals(referenz.getName(), defaultName))
				{
					// Referenz aus dem Modell entfernen
					Command removeCommand = RemoveCommand.create(domain,
							container, eReference, referenz);
					if (removeCommand.canExecute())
					{
						domain.getCommandStack().execute(removeCommand);

						// die alte Referenz aus der ContextReferenz-Liste entfernen
						contextReferenzen.remove(referenz);
						
						// Praeferenzliste aktualisieren
						setPreferenceList();
						
						// Defaultreferenz checken						
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
		
		// das gecheckte Element wird als Praeferenz gespeichert
		String [] checkedElements = getCheckedElements();
		if(ArrayUtils.isNotEmpty(checkedElements))
		{
			instancePreferenceNode.put(OfficeDefaultPreferenceUtils.REFERENZ_PREFERENCE,checkedElements[0]);
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
			Referenz referenz = findReferenz(prefName);
			
			if(referenz != null)
			{
				Referenz referenzCopy = EcoreUtil.copy(referenz);
				referenzCopy.setName(prefName+"copy");
				
				// die Kopie hinzufuegen
				Command addCommand = AddCommand.create(domain, container , eReference, referenzCopy);
				if(addCommand.canExecute())	
				{
					domain.getCommandStack().execute(addCommand);	
					
					// die neue Referenz zur ContextReferenz-Liste hinzufuegen
					contextReferenzen.add(referenzCopy);
					
					// Praeferenzliste aktualisieren
					setPreferenceList();
				}
			}
		}
	}
	
	/*
	 * Alle Referenzen aus dem Modell einlesen und nach dem Kontext filtern
	 * Eine Defaultreferenz erzeugen und hinzufuegen
	 */
	protected List<Referenz> loadContextReferenzen()
	{
		// alle gespeicherten Referenzen aus dem Modell einlesen
		Referenzen referenzen = (Referenzen) OfficeUtils.findObject(AddressPackage.eINSTANCE.getReferenzen());		
		EList<Referenz>allReferenzen = referenzen.getReferenzen();
		
		// Referenzen filtern
		contextReferenzen.clear();
		if(allReferenzen != null)
		{
			for (Referenz referenz : allReferenzen)
			{
				if(StringUtils.equals(referenz.getContext(),officeContext))
					contextReferenzen.add(referenz);
			}
		}
				
		// Defaultreferenz erzeugen, wenn noch kein Referenz mit diesem Kontext existiert 
		//if(contextReferenzen.isEmpty())
		if(findReferenz(defaultName) == null)
		{
			// Kontextreferenz erzeugen mit Default-Praeferenznamen
			Referenz defReferenz = generateContextReferenz();		
			defReferenz.setName(defaultName);			
			contextReferenzen.add(defReferenz);
			
			// DefaultAbsender zum Modell hinzufuegen und speichern
			Command addCommand = AddCommand.create(domain, container , eReference, defReferenz);
			if(addCommand.canExecute())	
			{
				domain.getCommandStack().execute(addCommand);		
				ECPHandlerHelper.saveProject(OfficeUtils.getOfficeProject());
			}
		}
		
		// die gefilterten Daten zurueckgeben
		return contextReferenzen;
	}
	
	// eine neue kontextbezogene Referenz generieren
	protected Referenz generateContextReferenz()
	{
		// eine neue Referenz generieren 
		EClass referenzClass = AddressPackage.eINSTANCE.getReferenz();
		Referenz defReferenz = (Referenz) EcoreUtil.create(referenzClass);
		defReferenz.setContext(officeContext);
		
		return defReferenz;		
	}

	private Referenz findReferenz(String referenzName)
	{
		for (Referenz referenz : contextReferenzen)
		{
			if(StringUtils.equals(referenz.getName(), referenzName))
				return referenz;
		}

		return null;
	}
	
	/**
	 * Die importierten Referenzen uebernehmen, filtert nach OfficeContext
	 * Bestehende (gleicher Name) werden durch die Importierten ersetzt - neue werden hinzugefuegt. 
	 * 
	 * @param importedReferences - Liste der importierten Absender
	 */
	public void importReferenzen(List<Referenz>importedReferenzen)
	{
		if((importedReferenzen != null))
		{
			for(Referenz importReferenz : importedReferenzen)
			{
				String importedName = importReferenz.getName();
				if ((StringUtils.isNotEmpty(importedName)
						&& StringUtils.equals(importReferenz.getContext(), officeContext)))
				{
					// existiert bereits eine gleichnamige Referenz 
					Referenz existReferenz = findReferenz(importedName);
					if(existReferenz != null)
					{
						// existierende Referenz wird geloescht
						Command removeCommand = RemoveCommand.create(domain,container, eReference, existReferenz);
						if (removeCommand.canExecute())
						{
							domain.getCommandStack().execute(removeCommand);
							contextReferenzen.remove(existReferenz);
						}
					}
										
					// die importierte Referenz wird zum Modell hinzugefuegt
					Command addCommand = AddCommand.create(domain,container, eReference, importReferenz);
					if (addCommand.canExecute())
					{
						domain.getCommandStack().execute(addCommand);
						contextReferenzen.add(importReferenz);
					}
				}
			}
			
			// Praeferenzliste aktualisieren
			setPreferenceList();
		}
	}



}
