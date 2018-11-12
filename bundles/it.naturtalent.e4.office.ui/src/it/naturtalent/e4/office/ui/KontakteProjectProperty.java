package it.naturtalent.e4.office.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.internal.workbench.E4Workbench;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.core.ECPProject;
import org.eclipse.emf.ecp.spi.ui.util.ECPHandlerHelper;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.widgets.Display;

import it.naturtalent.e4.office.ui.actions.ProjectKontakteAction;
import it.naturtalent.e4.office.ui.wizards.KontakteProjectPropertyWizardPage;
import it.naturtalent.e4.project.INtProjectProperty;
import it.naturtalent.e4.project.expimp.ecp.ECPExportHandlerHelper;
import it.naturtalent.e4.project.ui.emf.ExpImpUtils;
import it.naturtalent.e4.project.ui.emf.NtProjectPropertyFactory;
import it.naturtalent.office.model.address.Kontakt;
import it.naturtalent.office.model.address.NtProjektKontakte;

/**
 * KontakteProjectProperty Adapter
 * Kontakte einem NrProjekt zuordnen.
 * 
 * @author dieter
 *
 */
public class KontakteProjectProperty implements INtProjectProperty
{
	
	private String EXPIMP_NTPROJECTKONTAKTDATA_FILE = ".kontaktData.xmi";
	
	// ID des Projekts, auf das sich die Eigenschaft bezieht
	protected String ntProjectID;
	
	private NtProjektKontakte ntProjektKontakte;
		
	// die durch 'createWizardPage()' erzeugte WizardPage
	private KontakteProjectPropertyWizardPage ntProjektKontakteWizardPage;
	
	/* 
	 * Ueber die ProjectID wird der Container mit den Kontakten des Projekts geladen
	 * 
	 * (non-Javadoc)
	 * @see it.naturtalent.e4.project.INtProjectProperty#setNtProjectID(java.lang.String)
	 */
	@Override
	public void setNtProjectID(String ntProjectID)
	{
		this.ntProjectID = ntProjectID;	
		NtProjektKontakte ntProjektKontakte = OfficeUtils.getProjectKontacts(ntProjectID);
		if(ntProjektKontakte.getKontakte().size() > 0)
			this.ntProjektKontakte = ntProjektKontakte;
	}

	@Override
	public String getNtProjectID()
	{
		// TODO Auto-generated method stub
		return ntProjectID;
	}

	@Override
	public Object getNtPropertyData()
	{		
		return ntProjektKontakte;
	}

	/* 
	 * 
	 * (non-Javadoc)
	 * @see it.naturtalent.e4.project.INtProjectProperty#setNtPropertyData(java.lang.Object)
	 */
	@Override
	public void setNtPropertyData(Object eObject)
	{
		ntProjektKontakte = null;
		if (eObject instanceof NtProjektKontakte)
			ntProjektKontakte = (NtProjektKontakte) eObject;
	}

	@Override
	public Object getPropertyContainer()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object init()
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	/* 
	 * Die ProjectID 'ntProjectID' wird in NtProjektKontakte eingetragen.
	 * Die NtProjektKontakte werden im ECPProject gespeichert.  
	 * @see it.naturtalent.e4.project.INtProjectProperty#commit()
	 */
	@Override
	public void commit()
	{
		if((StringUtils.isNotEmpty(ntProjectID)) && (ntProjektKontakteWizardPage != null))
		{
			if(ntProjektKontakte.getKontakte().size() > 0)
			{
				// NtProjektKontakte zum ECPProject hinzufuegen
				ntProjektKontakte.setNtProjektID(ntProjectID);				
				ECPProject ecpProject = OfficeUtils.getOfficeProject();
				ecpProject.getContents().add(ntProjektKontakte);
				ECPHandlerHelper.saveProject(OfficeUtils.getOfficeProject());
			}
		}
			
		// WizardPage wird ungueltig
		if(ntProjektKontakteWizardPage != null)
			ntProjektKontakteWizardPage.setKontakteProjectProperty(null);			
		ntProjektKontakteWizardPage = null;
	}

	@Override
	public IWizardPage createWizardPage()
	{
		IEclipseContext context = E4Workbench.getServiceContext();		
		ntProjektKontakteWizardPage  = ContextInjectionFactory.make(KontakteProjectPropertyWizardPage.class, context);	
		ntProjektKontakteWizardPage.setKontakteProjectProperty(this);
		return ntProjektKontakteWizardPage;
	}

	/* Alle Aenderungen rueckaengig machen und keinen 'DirtyContent' zuruecklassen
	 * (non-Javadoc)
	 * @see it.naturtalent.e4.project.INtProjectProperty#undo()
	 */
	@Override
	public void undo()
	{
		EditingDomain domain = AdapterFactoryEditingDomain
				.getEditingDomainFor(OfficeUtils.getOfficeProject());
		while(domain.getCommandStack().canUndo())
			domain.getCommandStack().undo();
		ECPHandlerHelper.saveProject(OfficeUtils.getOfficeProject());
		
		// WizardPage wird ungueltig
		if(ntProjektKontakteWizardPage != null)
			ntProjektKontakteWizardPage.setKontakteProjectProperty(null);			
		ntProjektKontakteWizardPage = null;
	}

	@Override
	public void delete()
	{
		if (StringUtils.isNotEmpty(ntProjectID))	
		{
			NtProjektKontakte ntProjektKontakte = OfficeUtils.getProjectKontacts(ntProjectID);
			if(ntProjektKontakte.getKontakte().size() > 0)
			{
				ECPProject ecpProject = OfficeUtils.getOfficeProject();
				ecpProject.getContents().remove(ntProjektKontakte);
				ECPHandlerHelper.saveProject(OfficeUtils.getOfficeProject());
			}			
		}
	}

	@Override
	public Action createAction()
	{
		IEclipseContext context = E4Workbench.getServiceContext();
		ProjectKontakteAction projectKontactAction = ContextInjectionFactory
				.make(ProjectKontakteAction.class, context);
		projectKontactAction.setKontactsProjectProperty(this);
		
		return projectKontactAction;		
	}

	@Override
	public String getLabel()
	{				
		return "Kontakt";
	}

	@Override
	public String toString()
	{		
		String stgKontakt = "";
		if(ntProjektKontakte != null)
		{
			Kontakt kontakt = ntProjektKontakte.getKontakte().get(0);
			stgKontakt = kontakt.getName();			
		}
		return getLabel() + " : "+stgKontakt;
	}
	
	@Override
	public String getPropertyFactoryName()
	{		
		// Factoryname = diese Klasse + Extension 'Factory'
		return this.getClass().getName()
				+ NtProjectPropertyFactory.PROJECTPROPERTYFACTORY_EXTENSION;
	}

	@Override
	public void importProperty()
	{		
		EObject kontaktProjectData = ExpImpUtils.importNtPropertyData(EXPIMP_NTPROJECTKONTAKTDATA_FILE, ntProjectID);
		if (kontaktProjectData instanceof NtProjektKontakte)
		{
			NtProjektKontakte projectKontakt = (NtProjektKontakte) kontaktProjectData;
			
			// nochmal pruefen, ob die IDs uebereinstimmen
			if(StringUtils.equals(ntProjectID, projectKontakt.getNtProjektID()))
			{
				ECPProject ecpProject = OfficeUtils.getOfficeProject();
				ecpProject.getContents().add(kontaktProjectData);				
			}
		}
	}

	@Override
	public void exportProperty()
	{
		// sind Propertydaten vorhanden @see setNtProjectID(String ntProjectID)
		if(ntProjektKontakte != null)
		{			
			if(StringUtils.equals(ntProjectID, ntProjektKontakte.getNtProjektID()))
				ExpImpUtils.exportNtPropertyData(ntProjectID, ntProjektKontakte, EXPIMP_NTPROJECTKONTAKTDATA_FILE);
		}		
	}
	

	
}
