package it.naturtalent.e4.office.ui.preferences;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EventObject;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.internal.workbench.E4Workbench;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CommandStackListener;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
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
import org.eclipse.emfforms.spi.localization.LocalizationServiceHelper;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.osgi.service.prefs.BackingStoreException;

import it.naturtalent.e4.office.ui.OfficeUtils;
import it.naturtalent.e4.office.ui.dialogs.AbsenderDialog;
import it.naturtalent.e4.preferences.CheckListEditorComposite;
import it.naturtalent.office.model.address.Absender;
import it.naturtalent.office.model.address.AddressPackage;
import it.naturtalent.office.model.address.Adresse;
import it.naturtalent.office.model.address.Sender;




/**
 * Eine Praeferenzliste (Checkliste) mit den Namen der Absenderpraeferenznamen,
 * 
 * @author dieter
 *
 */
public class OfficeAbsenderPreferenceComposite extends CheckListEditorComposite
{
	
	protected IEclipsePreferences instancePreferenceNode = InstanceScope.INSTANCE
			.getNode(OfficeDefaultPreferenceUtils.ROOT_DEFAULTOFFICE_PREFERENCES_NODE);
	
	// Liste aller Absender in diesem Context
	protected List<Absender>contextAbsender = new ArrayList<Absender>();

	// Kontext 
	protected String officeContext = OfficeDefaultPreferenceUtils.DEFAULT_OFFICE_CONTEXT;

	// Default Praeferenzname des Absenders 
	protected String defaultName = OfficeDefaultPreferenceUtils.DEFAULT_ABSENDERNAME;
	
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
	public OfficeAbsenderPreferenceComposite(Composite parent, int style)
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
		// Absender(Adresse) via EMF-Commands handeln
		container = OfficeUtils.findObject(AddressPackage.eINSTANCE.getSender());
		domain = AdapterFactoryEditingDomain.getEditingDomainFor(container);	
		eReference = AddressPackage.eINSTANCE.getSender_Senders();
	
		// die Kontextabsender laden
		loadContextAbsender();
		
		// Praeferenznamen der Absender in Praeferenzliste anzeigen
		setPreferenceList();
		
		// Name der aktuelle Absenderpraeferenz in der Praeferenzliste checken
		String absenderPreferenz = instancePreferenceNode.get(
				OfficeDefaultPreferenceUtils.ABSENDER_PREFERENCE,
				OfficeDefaultPreferenceUtils.DEFAULT_ABSENDERNAME);		
		
		// existiert der Absender noch im Modell
		if(findAbsender(absenderPreferenz) == null)
			absenderPreferenz = OfficeDefaultPreferenceUtils.DEFAULT_ABSENDERNAME;			
		setCheckedElements(new String [] {absenderPreferenz});
		
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
			// den selektierten Absender suchen
			String prefName = (String) selObj;
			Absender absender = findAbsender(prefName);
			if(absender != null)
			{		
				// DefaultAbsender wird nicht geloescht
				btnRemove.setEnabled(!StringUtils.equals(absender.getName(), defaultName));
				
				btnCopy.setEnabled(true);
			}
		}
	}

	/*
	 * die Absendernamen in die Praeferenzliste eintragen 
	 */
	private void setPreferenceList()
	{
		List<String>preferenceNames = new ArrayList<String>();
		for(Absender absender : contextAbsender)
			preferenceNames.add(absender.getName());		
		checkboxTableViewer.setInput(preferenceNames);
	}
		
	/*
	 * einen neuen Absender hinzufuegen
	 */
	@Override
	protected void doAdd()
	{
		// TODO Auto-generated method stub
		Absender absender = generateContextAbsender();
		
		// neuen Absender mit Dialog bearbeiten
		AbsenderDialog dialog = new AbsenderDialog(Display.getDefault().getActiveShell(), absender);
		
		// Default-Praeferenznamen via E4Context dem Renderer uebergeben
		E4Workbench.getServiceContext().set(OfficeDefaultPreferenceUtils.E4CONTEXT_DEFAULTNAME, defaultName);
		
		if (dialog.open() == AbsenderDialog.OK)
		{			
			// DefaultAbsender zum Modell hinzufuegen und speichern
			Command addCommand = AddCommand.create(domain, container , eReference, absender);
			if(addCommand.canExecute())	
			{
				domain.getCommandStack().execute(addCommand);	
				
				// den neuen Absender zur ContextAbsender-Liste hinzufuegen
				contextAbsender.add(absender);
				
				// Praeferenzliste aktualisieren
				setPreferenceList();
			}
		}
		
		E4Workbench.getServiceContext().remove(OfficeDefaultPreferenceUtils.E4CONTEXT_DEFAULTNAME);
	}

	/* 
	 * Den selektierten Absender bearbeiten
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
			Absender absender = findAbsender(prefName);
			
			if(absender != null)
			{
				Absender absenderCopy = EcoreUtil.copy(absender);
				
				// Kopie mit dem Dialog bearbeiten
				IEclipseContext context = E4Workbench.getServiceContext();
				AbsenderDialog dialog = ContextInjectionFactory.make(AbsenderDialog.class, context);
				dialog.setAbsender(absenderCopy);
			
				
				//AbsenderDialog dialog = new AbsenderDialog(Display.getDefault().getActiveShell(), absenderCopy);
				
				// Default-Praeferenznamen via E4Context dem Renderer uebergeben
				E4Workbench.getServiceContext().set(OfficeDefaultPreferenceUtils.E4CONTEXT_DEFAULTNAME, defaultName);
				
				if (dialog.open() == AbsenderDialog.OK)
				{
					// Original-Absender aus dem Modell entfernen
					Command removeCommand = RemoveCommand.create(domain,container, eReference, absender);
					if (removeCommand.canExecute())		
					{
						domain.getCommandStack().execute(removeCommand);
						contextAbsender.remove(absender);
					}
					
					Command addCommand = AddCommand.create(domain, container , eReference, absenderCopy);
					if(addCommand.canExecute())	
					{
						domain.getCommandStack().execute(addCommand);	
						contextAbsender.add(absenderCopy);
					}
					
					// Praeferenzliste aktualisieren
					setPreferenceList();
					
					// Defaultadresse checken						
					setCheckedElements(new String[]{absenderCopy.getName()});
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
			Absender absender = findAbsender(prefName);
			if(absender != null)
			{
				// DefaultAbsender wird nicht geloescht
				if (!StringUtils.equals(absender.getName(), defaultName))
				{
					// Absender aus dem Modell entfernen
					Command removeCommand = RemoveCommand.create(domain,
							container, eReference, absender);
					if (removeCommand.canExecute())
					{
						domain.getCommandStack().execute(removeCommand);

						// den alten Absender aus der ContextAbsender-Liste entfernen
						contextAbsender.remove(absender);
						
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
		
		// das gecheckte Element wird als Absenderpraeferenz gespeichert
		String [] checkedElements = getCheckedElements();
		if(ArrayUtils.isNotEmpty(checkedElements))
		{
			instancePreferenceNode.put(OfficeDefaultPreferenceUtils.ABSENDER_PREFERENCE,checkedElements[0]);
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
			Absender absender = findAbsender(prefName);
			
			if(absender != null)
			{
				Absender absenderCopy = EcoreUtil.copy(absender);
				absenderCopy.setName(prefName+"copy");
				
				// die Kopie hinzufuegen
				Command addCommand = AddCommand.create(domain, container , eReference, absenderCopy);
				if(addCommand.canExecute())	
				{
					domain.getCommandStack().execute(addCommand);	
					
					// den neuen Absender zur ContextAbsender-Liste hinzufuegen
					contextAbsender.add(absenderCopy);
					
					// Praeferenzliste aktualisieren
					setPreferenceList();
				}
			}
		}
	}
	
	/*
	 * Alle Absenderdaten aus dem Modell einlesen und nach dem Kontext filtern
	 * Einen Defaultabsender erzeugen und hinzufuegen
	 */
	protected List<Absender> loadContextAbsender()
	{
		// alle gespeicherten Absender aus dem Modell einlesen
		Sender sender = (Sender) OfficeUtils.findObject(AddressPackage.eINSTANCE.getSender());		
		EList<Absender>allAbsender = sender.getSenders();
		
		// Absender filtern
		contextAbsender.clear();
		if(allAbsender != null)
		{
			for (Absender absender : allAbsender)
			{
				if(StringUtils.equals(absender.getContext(),officeContext))
					contextAbsender.add(absender);
			}
		}
				
		// Defaultadresse erzeugen, wenn noch kein Absender mit diesem Kontext existiert 
		if(contextAbsender.isEmpty())
		{
			// Kontextabsender erzeugen mit Default-Praeferenznamen
			Absender defAbsender = generateContextAbsender();		
			defAbsender.setName(defaultName);			
			contextAbsender.add(defAbsender);
			
			// DefaultAbsender zum Modell hinzufuegen und speichern
			Command addCommand = AddCommand.create(domain, container , eReference, defAbsender);
			if(addCommand.canExecute())	
			{
				domain.getCommandStack().execute(addCommand);		
				ECPHandlerHelper.saveProject(OfficeUtils.getOfficeProject());
			}
		}
		
		// die gefilterten Daten zurueckgeben
		return contextAbsender;
	}
	
	// einen neuen Absender generieren
	protected Absender generateContextAbsender()
	{
		// einen neuen Absender generieren mit 
		EClass absenderClass = AddressPackage.eINSTANCE.getAbsender();
		Absender defAbsender = (Absender) EcoreUtil.create(absenderClass);
		defAbsender.setContext(officeContext);

		// neue Adresse erzeugen und dem Absender hinzufuegen			
		EClass addressClass = AddressPackage.eINSTANCE.getAdresse();
		Adresse addresse = (Adresse)EcoreUtil.create(addressClass);		
		defAbsender.setAdresse(addresse);
		
		return defAbsender;		
	}

	private Absender findAbsender(String absenderName)
	{
		for (Absender absender : contextAbsender)
		{
			if(StringUtils.equals(absender.getName(), absenderName))
				return absender;
		}

		return null;
	}
	
	/**
	 */
	/*
	public void copyAbsender()
	{
		IStructuredSelection selection = checkboxTableViewer.getStructuredSelection();
		Object selObj = selection.getFirstElement();
		if (selObj instanceof String)
		{
			// den selektierten Absender suchen
			String prefName = (String) selObj;
			Absender absender = findAbsender(prefName);
			
			if(absender != null)
			{
				System.out.println("kopieren");				
			}
		}
	}
	*/
	
	/**
	 * Importierte Absender uebernehmen, filtert nach OfficeContext
	 * Bestehende (gleicher Name) werden durch die Importierten ersetzt - neue werden hinzugefuegt. 
	 * 
	 * @param importedReferences - Liste der importierten Absender
	 */
	public void importAbsender(List<Absender>importedAbsender)
	{
		if((importedAbsender != null))
		{
			for(Absender importAbsender : importedAbsender)
			{
				String importedName = importAbsender.getName();
				if ((StringUtils.isNotEmpty(importedName)
						&& StringUtils.equals(importAbsender.getContext(), officeContext)))
				{
					// existiert bereits ein gleichnamiger Absender 
					Absender existAbsender = findAbsender(importedName);
					if(existAbsender != null)
					{
						// existierender Absender wird geloescht
						Command removeCommand = RemoveCommand.create(domain,container, eReference, existAbsender);
						if (removeCommand.canExecute())
						{
							domain.getCommandStack().execute(removeCommand);
							contextAbsender.remove(existAbsender);
						}
					}
										
					// die importierte Referenz wird zum Modell hinzugefuegt
					Command addCommand = AddCommand.create(domain,container, eReference, importAbsender);
					if (addCommand.canExecute())
					{
						domain.getCommandStack().execute(addCommand);
						contextAbsender.add(importAbsender);
					}
				}
			}
			
			// Praeferenzliste aktualisieren
			setPreferenceList();
		}
	}


}
