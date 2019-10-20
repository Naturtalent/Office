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
import it.naturtalent.e4.preferences.CheckListEditorComposite;
import it.naturtalent.office.model.address.AddressPackage;
import it.naturtalent.office.model.address.Signature;
import it.naturtalent.office.model.address.Signatures;




/**
 * Die Liste mit den Signaturen anzeigen - (Checkliste) mit den Namen der Signaturen
 * 
 * @author dieter
 *
 */
public class OfficeSignaturePreferenceComposite extends CheckListEditorComposite
{
	// Knoten der Office-Praeferenzen
	protected IEclipsePreferences instancePreferenceNode = InstanceScope.INSTANCE
			.getNode(OfficeDefaultPreferenceUtils.ROOT_DEFAULTOFFICE_PREFERENCES_NODE);
	
	// Liste aller Signaturen in diesem Context
	protected List<Signature>contextSignatures = new ArrayList<Signature>();

	// Default Office-Kontext 
	protected String officeContext = OfficeDefaultPreferenceUtils.DEFAULT_OFFICE_CONTEXT;

	// Default Signaturenname  
	protected String defaultName = OfficeDefaultPreferenceUtils.DEFAULT_SIGNATURENAME;
	
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
	public OfficeSignaturePreferenceComposite(Composite parent, int style)
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
		// Signaturemodellaenderungen via EMF-Commands realisieren
		container = OfficeUtils.findObject(AddressPackage.eINSTANCE.getSignatures());
		domain = AdapterFactoryEditingDomain.getEditingDomainFor(container);	
		eReference = AddressPackage.eINSTANCE.getSignatures_Signatures();
	
		// die Kontextsignaturen laden
		loadContextSignatures();
		
		// Praeferenznamen der Signaturen in Praeferenzliste anzeigen
		setPreferenceList();
		
		// Name der aktuelle Signaturepraeferenz
		String signaturePreferenz = instancePreferenceNode.get(
				OfficeDefaultPreferenceUtils.SIGNATURE_PREFERENCE,
				OfficeDefaultPreferenceUtils.DEFAULT_SIGNATURENAME);		
		
		// existiert die Signature noch im Modell
		if(findSignature(signaturePreferenz) == null)
			signaturePreferenz = OfficeDefaultPreferenceUtils.DEFAULT_SIGNATURENAME;			
		setCheckedElements(new String [] {signaturePreferenz});
		
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
			// die selektierte Signatur suchen
			String prefName = (String) selObj;
			Signature signature = findSignature(prefName);
			if(signature != null)
			{		
				// DefaultAbsender wird nicht geloescht
				btnRemove.setEnabled(!StringUtils.equals(signature.getName(), defaultName));
				
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
			// gecheckte Signature  als Preferenz speichern (aber noch nicht festschreiben)
			instancePreferenceNode.put(OfficeDefaultPreferenceUtils.SIGNATURE_PREFERENCE, (String) selObj);
		}
	}


	/*
	 * die Signaturenamen in die Praeferenzliste eintragen 
	 */
	private void setPreferenceList()
	{
		List<String>signatureNames = new ArrayList<String>();
		for(Signature signature : contextSignatures)
			signatureNames.add(signature.getName());		
		checkboxTableViewer.setInput(signatureNames);
	}
		
	/*
	 * eine neue Signature hinzufuegen
	 */
	@Override
	protected void doAdd()
	{
		// TODO Auto-generated method stub
		Signature signature = generateContextSignature();
		
		// neue Signature mit Dialog bearbeiten
		IEclipseContext context = E4Workbench.getServiceContext();
		OfficePreferenzDialog dialog = ContextInjectionFactory.make(OfficePreferenzDialog.class, context);
		dialog.setPreferenz(signature);
		
		// Default-Praeferenznamen via E4Context dem Renderer uebergeben
		E4Workbench.getServiceContext().set(OfficeDefaultPreferenceUtils.E4CONTEXT_DEFAULTNAME, defaultName);
		
		if (dialog.open() == OfficePreferenzDialog.OK)
		{			
			// DefaultAbsender zum Modell hinzufuegen und speichern
			Command addCommand = AddCommand.create(domain, container , eReference, signature);
			if(addCommand.canExecute())	
			{
				domain.getCommandStack().execute(addCommand);	
				
				// die neue Referenz zur ContextReferenz-Liste hinzufuegen
				contextSignatures.add(signature);
				
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
			Signature referenz = findSignature(prefName);
			
			if(referenz != null)
			{
				Signature referenzCopy = EcoreUtil.copy(referenz);
				
				// Kopie mit dem Dialog bearbeiten
				IEclipseContext context = E4Workbench.getServiceContext();
				OfficePreferenzDialog dialog = ContextInjectionFactory.make(OfficePreferenzDialog.class, context);
				dialog.setPreferenz(referenzCopy);
				
				// Default-Praeferenznamen via E4Context dem Renderer uebergeben
				E4Workbench.getServiceContext().set(OfficeDefaultPreferenceUtils.E4CONTEXT_DEFAULTNAME, defaultName);
				
				if (dialog.open() == OfficePreferenzDialog.OK)
				{
					// Original-Signature aus dem Modell entfernen
					Command removeCommand = RemoveCommand.create(domain,container, eReference, referenz);
					if (removeCommand.canExecute())		
					{
						domain.getCommandStack().execute(removeCommand);
						contextSignatures.remove(referenz);
					}
					
					Command addCommand = AddCommand.create(domain, container , eReference, referenzCopy);
					if(addCommand.canExecute())	
					{
						domain.getCommandStack().execute(addCommand);	
						contextSignatures.add(referenzCopy);
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
			// die selektierte Signature suchen
			String prefName = (String) selObj;
			Signature referenz = findSignature(prefName);
			if(referenz != null)
			{
				// DefaultSignature wird nicht geloescht
				if (!StringUtils.equals(referenz.getName(), defaultName))
				{
					// Signature aus dem Modell entfernen
					Command removeCommand = RemoveCommand.create(domain,
							container, eReference, referenz);
					if (removeCommand.canExecute())
					{
						domain.getCommandStack().execute(removeCommand);

						// die alte Signature aus der ContextSignature-Liste entfernen
						contextSignatures.remove(referenz);
						
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
			instancePreferenceNode.put(OfficeDefaultPreferenceUtils.SIGNATURE_PREFERENCE,checkedElements[0]);
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
			Signature signature = findSignature(prefName);
			
			if(signature != null)
			{
				Signature signatureCopy = EcoreUtil.copy(signature);
				signatureCopy.setName(prefName+"copy");
				
				// die Kopie hinzufuegen
				Command addCommand = AddCommand.create(domain, container , eReference, signatureCopy);
				if(addCommand.canExecute())	
				{
					domain.getCommandStack().execute(addCommand);	
					
					// die neue Signature zur ContextSignature-Liste hinzufuegen
					contextSignatures.add(signatureCopy);
					
					// Praeferenzliste aktualisieren
					setPreferenceList();
				}
			}
		}
	}
	
	/*
	 * Alle Signaturen aus dem Modell einlesen und nach dem Kontext filtern
	 * Eine Defaultsignatur erzeugen und hinzufuegen
	 */
	protected List<Signature> loadContextSignatures()
	{
		// alle gespeicherten Signaturen aus dem Modell einlesen
		Signatures signatures = (Signatures) OfficeUtils.findObject(AddressPackage.eINSTANCE.getSignatures());		
		EList<Signature>allSignatures = signatures.getSignatures();
		
		// Signaturen filtern
		contextSignatures.clear();
		if((allSignatures != null) && (!allSignatures.isEmpty()))
		{
			for (Signature signature : allSignatures)
			{
				if(StringUtils.equals(signature.getContext(),officeContext))
					contextSignatures.add(signature);
			}
		}
				
		// Defaultsignature erzeugen, wenn noch kein Signature mit diesem Kontext existiert 		
		if(findSignature(defaultName) == null)
		{
			// Kontextsignature erzeugen mit Default-Praeferenznamen
			Signature defSignature = generateContextSignature();		
			defSignature.setName(defaultName);			
			contextSignatures.add(defSignature);
			
			// DefaultAbsender zum Modell hinzufuegen und speichern
			Command addCommand = AddCommand.create(domain, container , eReference, defSignature);
			if(addCommand.canExecute())	
			{
				domain.getCommandStack().execute(addCommand);		
				ECPHandlerHelper.saveProject(OfficeUtils.getOfficeProject());
			}
		}
		
		// die gefilterten Daten zurueckgeben
		return contextSignatures;
	}
	
	// eine neue kontextbezogene Signature generieren
	protected Signature generateContextSignature()
	{
		// eine neue Signature generieren 
		EClass signatureClass = AddressPackage.eINSTANCE.getSignature();
		Signature defSignature = (Signature) EcoreUtil.create(signatureClass);
		defSignature.setContext(officeContext);
		
		return defSignature;		
	}

	private Signature findSignature(String signatureName)
	{
		for (Signature signature : contextSignatures)
		{
			if(StringUtils.equals(signature.getName(), signatureName))
				return signature;
		}

		return null;
	}
	
	/**
	 * Die importierten Signatures uebernehmen, die Funktion filtert nach OfficeContext
	 * Bestehende (gleicher Name) werden durch die Importierten ersetzt - neue werden hinzugefuegt. 
	 * 
	 * @param importedSigantures - Liste der importierten Signaturen
	 */
	public void importSignatures(List<Signature>importedSigantures)
	{
		if((importedSigantures != null))
		{
			for(Signature importSignature : importedSigantures)
			{
				String importedName = importSignature.getName();
				if ((StringUtils.isNotEmpty(importedName)
						&& StringUtils.equals(importSignature.getContext(), officeContext)))
				{
					// existiert bereits eine gleichnamige Signatur 
					Signature existSignature = findSignature(importedName);
					if(existSignature != null)
					{
						// existierende Signature wird geloescht
						Command removeCommand = RemoveCommand.create(domain,container, eReference, existSignature);
						if (removeCommand.canExecute())
						{
							domain.getCommandStack().execute(removeCommand);
							contextSignatures.remove(existSignature);
						}
					}
										
					// die importierte Signature wird zum Modell hinzugefuegt
					Command addCommand = AddCommand.create(domain,container, eReference, importSignature);
					if (addCommand.canExecute())
					{
						domain.getCommandStack().execute(addCommand);
						contextSignatures.add(importSignature);
					}
				}
			}
			
			// Praeferenzliste aktualisieren
			setPreferenceList();
		}
	}



}
