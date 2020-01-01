package it.naturtalent.e4.office.ui.dialogs;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;

import it.naturtalent.e4.office.ui.OfficeUtils;
import it.naturtalent.e4.project.INtProjectPropertyFactory;
import it.naturtalent.e4.project.expimp.ExpImportData;
import it.naturtalent.e4.project.expimp.dialogs.AbstractImportDialog;
import it.naturtalent.e4.project.ui.parts.emf.NtProjectView;
import it.naturtalent.office.model.address.AddressPackage;
import it.naturtalent.office.model.address.Kontakt;
import it.naturtalent.office.model.address.Kontakte;


/**
 * Dialog zum Import von Kontakten.
 * 
 * @author dieter
 *
 */
public class KontakteImportDialog extends AbstractImportDialog
{
	private static final String KONTAKTEMORTPATH_SETTING_KEY = "importkontaktepathsetting"; //$NON-NLS-N$

	@Inject @Optional public IEventBroker eventBroker;
	
	private Kontakte importKontakte;
	
	
	@Override
	protected void init()
	{		
		importSettingKey = KONTAKTEMORTPATH_SETTING_KEY;		
		super.init();
	}

	@Override
	protected Control createDialogArea(Composite parent)
	{
		// TODO Auto-generated method stub
		Control control = super.createDialogArea(parent);
		checkBoxTableViewer.setLabelProvider(new GrayedTableLabelProvider());		
		
		// vorhandene Kontakte 'eingrauen'
		try
		{
			disableKontakte(lexpimpdata);
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}

		checkBoxTableViewer.refresh();
		
		btnCheckOverwrite.dispose();
		return control;
	}



	/* 
	 * Wird im Zuge der Initialisierung aufgerufen (einlesen Resource des SettingImportPfad)
	 * oder nach der Auswahl der Quelldatei im Dialog.
	 * 
	 * (non-Javadoc)
	 * @see it.naturtalent.e4.project.expimp.dialogs.AbstractImportDialog#readImportSource()
	 */
	@Override
	public void readImportSource()
	{
		URI fileURI = URI.createFileURI(importPath);		
		ResourceSet resourceSet = new ResourceSetImpl();
		
		Resource resource = resourceSet.getResource(fileURI, true);
		EList<EObject>eObjects = resource.getContents();
		
		//importKontakte = (Kontakte) eObjects.get(0);
		//setTitle("Archiv: "+importKontakte.
		
		// alle Kontakte einlesen
		lexpimpdata = new ArrayList<ExpImportData>();
		//EList<Kontakt>kontaktList = importKontakte.getKontakte();
		if (eObjects != null)
		{
			for (EObject eObject : eObjects)
			{
				Kontakt kontakt = (Kontakt) eObject;

				ExpImportData expimpdata = new ExpImportData();
				expimpdata.setLabel(kontakt.getName());
				expimpdata.setData(kontakt);
				lexpimpdata.add(expimpdata);
			}

			setModelData(lexpimpdata);
		}
	}
	
	/*
	 * 
	 */
	private void disableKontakte(List<ExpImportData>lexpimpdata)
	{
		for(ExpImportData lexpimp : lexpimpdata)
		{
			/*
			Ordner ordner = (Ordner) lexpimp.getData();
			for(Register register : ordner.getRegisters())
			{
				String iProjectID = register.getProjectID();
				if(StringUtils.isNotEmpty(iProjectID))
				{
					// gekoppeltes Projekt
					IProject iProject = ResourcesPlugin.getWorkspace().getRoot().getProject(iProjectID);
					if(iProject.exists())
					{						
						if(ArchivUtils.findIProjectRegister(iProject.getName()) != null)
							checkBoxTableViewer.setGrayed(lexpimp, true);
						break;						
					}
				}				
			}
			*/
		}
	}

	/* 
	 * Die selektierten Kontakte werden importiert.
	 * 
	 * (non-Javadoc)
	 * @see it.naturtalent.e4.project.expimp.dialogs.AbstractImportDialog#doImport()
	 */
	@Override
	public void doImport(ExpImportData [] selectedData)
	{
		// die selektierten Kontakte in einer Liste 'allImportKontakte' zusammenfassen
		final List<Kontakt>allImportKontakte = new ArrayList<Kontakt>();
		for(ExpImportData expimpdata : selectedData)
		{
			Object obj = expimpdata.getData();
			if (obj instanceof Kontakt)
				allImportKontakte.add((Kontakt)obj);		
		}
		
		// Importdaten ueber einen ProgressDialog einlesen
		ProgressMonitorDialog progressDialog = new ProgressMonitorDialog(
				Display.getDefault().getActiveShell());				
		progressDialog.open();
		try
		{
			progressDialog.run(true, true, new IRunnableWithProgress()
			{
				@Override
				public void run(IProgressMonitor monitor)throws InvocationTargetException,InterruptedException
				{
					monitor.beginTask("Kontaktdaten werden eingelesen",IProgressMonitor.UNKNOWN);										
					Kontakte kontakte = OfficeUtils.getKontakte();
					kontakte.getKontakte().addAll(allImportKontakte);					
					OfficeUtils.getOfficeProject().saveContents();
					monitor.done();
				}
			});
		}
		catch(Exception e)
		{
			
		}

		progressDialog.close();	
		eventBroker.post(OfficeUtils.KONTAKTE_REFRESH_MASTER_REQUEST, null);
	}
	
	// Name des ArchivPropertyFactory
	/*
	private String ArchivPropertyFactoryName = ArchivProjectProperty.class.getName()
			+ INtProjectPropertyFactory.PROJECTPROPERTYFACTORY_EXTENSION;
			*/
	
	@Override
	public void removeExistedObjects(List<EObject> importObjects)
	{
		// TODO Auto-generated method stub
	}
	
	
}
