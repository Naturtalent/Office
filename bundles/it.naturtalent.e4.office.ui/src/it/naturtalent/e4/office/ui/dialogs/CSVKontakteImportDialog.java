package it.naturtalent.e4.office.ui.dialogs;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

import it.naturtalent.e4.office.ui.OfficeUtils;
import it.naturtalent.e4.project.INtProjectPropertyFactory;
import it.naturtalent.e4.project.expimp.ExpImportData;
import it.naturtalent.e4.project.expimp.dialogs.AbstractImportDialog;
import it.naturtalent.e4.project.ui.parts.emf.NtProjectView;
import it.naturtalent.office.model.address.AddressPackage;
import it.naturtalent.office.model.address.Adresse;
import it.naturtalent.office.model.address.Kontakt;
import it.naturtalent.office.model.address.Kontakte;


/**
 * Dialog zum Import von Kontakten aus einer CSV - Datei
 * Flothari
 * @author dieter
 *
 */
public class CSVKontakteImportDialog extends AbstractImportDialog
{
	// DialogSetting - Key fuer den Pfad der Importdatei
	private static final String KONTAKTEMORTPATH_SETTING_KEY = "cvsimportkontaktepathsetting"; //$NON-NLS-N$

	@Inject @Optional public IEventBroker eventBroker;
	
    private Shell shell;
    
	@Override
	protected void init()
	{		
		importSettingKey = KONTAKTEMORTPATH_SETTING_KEY;		
		
		// DialogSettings laden und ggf. ImportDaten einlesen 'readImportSource()'
		super.init();
	}

	@Override
	protected Control createDialogArea(Composite parent)
	{		
		this.shell = parent.getShell();
		
		// TODO Auto-generated method stub
		Control control = super.createDialogArea(parent);
		checkBoxTableViewer.setLabelProvider(new GrayedTableLabelProvider());		
		
		// vorhandene Kontakte 'eingrauen'
		try
		{
			disableKontakte();
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}

		checkBoxTableViewer.refresh();
		
		btnCheckOverwrite.dispose();
		return control;
	}

	// angepasste Reaktion am 'select' - Button (FileDialog zur Auswahl der CSV-Importdatei)
	protected void handleSelectButton()
	{
		importPath = null;
		FileDialog dlg = new FileDialog(getShell());

		// Change the title bar text
		dlg.setText("Importverzeichnis");
		dlg.setFilterExtensions(new String[]{"*.csv"}); //$NON-NLS-1$
		dlg.setFilterPath(importPath);
		
		String importFile = dlg.open();
		if (importFile != null)
		{			
			importPath = importFile;
			txtSource.setText(importFile);
			
			// einlesen der Kontake		
			readImportSource();			
		}
	}

	/*
	 *  Die CSV-Datei lesen. 
	 *  Unterstellt wird, dass diese die Kontaktdaten enthaelt, und eine definierte Syntax wird benutzt.
	 *  
	 *  Aendert sich die Syntax der Importdatei, muss die Klasse CSVKontaktImportOperation angepasst werden.
	 *  
	 * (non-Javadoc)
	 * @see it.naturtalent.e4.project.expimp.dialogs.AbstractImportDialog#readImportSource()
	 */	
	@Override
	public void readImportSource()
	{
		// in einer eigenen Operationklasse das moeglicherweise langlaufende Einlesen der Daten realisieren
		CSVKontaktImportOperation importOperation = new CSVKontaktImportOperation(importPath);
				
		try
		{
			// Import ausfuehren
			new ProgressMonitorDialog(Display.getDefault().getActiveShell()).run(true, true, importOperation);		
			
		} catch (InvocationTargetException e)
		{
			// Error
			Throwable realException = e.getTargetException();
			String message = realException.getMessage();
			message = StringUtils.isNotEmpty(message) ? message : "interner Fehler";
			MessageDialog.openError(Display.getCurrent().getActiveShell(), "Import Fehler", message);			
			return;
			
		} catch (InterruptedException e)
		{
			// Abbruch
			MessageDialog.openError(shell, "Import Abbruch", "die Aktion wurde abgebrochen");
			return;
		}
		
		// die eingelesenen Daten 'ExpImportData' an den ImportDialog uebergeben
		setModelData(importOperation.getLexpimpdata());		
	}
	
	/*
	 * 
	 */
	private void disableKontakte()
	{
		EList<Kontakt>kontakte =  OfficeUtils.getKontakte().getKontakte();
		
		List<ExpImportData>lexpimpdata = getModelData();
		
		for(ExpImportData lexpimp : lexpimpdata)
		{
			Kontakt importKontakt = (Kontakt) lexpimp.getData();			
			for(Kontakt kontakt : kontakte)
			{
				if(StringUtils.equals(importKontakt.getName(), kontakt.getName()))
					checkBoxTableViewer.setGrayed(lexpimp, true);
			}
		}
	}
	
	// die selektierten Kontakte werden in das EMF Modell eingelesen
	@Override
	public void doImport(ExpImportData [] selectedData)
	{		
		// die selektierten Kontakte in einer Liste 'allImportKontakte' zusammenfassen
		final List<Kontakt>allImportKontakte = new ArrayList<Kontakt>();
		for(ExpImportData expimpdata : selectedData)
		{			
			Kontakt kontakt = (Kontakt) expimpdata.getData();
			allImportKontakte.add(kontakt);
		}
		
		EReference eReference = AddressPackage.eINSTANCE.getKontakte_Kontakte();
		EObject container = OfficeUtils.getKontakte();
		EditingDomain domain = AdapterFactoryEditingDomain.getEditingDomainFor(container);	
		Command addCommand = AddCommand.create(domain, container, eReference, allImportKontakte);			
		if(addCommand.canExecute())
			domain.getCommandStack().execute(addCommand);		 
	}

	@Override
	public void removeExistedObjects(List<EObject> importObjects)
	{
		// TODO Auto-generated method stub
	}
	
}
