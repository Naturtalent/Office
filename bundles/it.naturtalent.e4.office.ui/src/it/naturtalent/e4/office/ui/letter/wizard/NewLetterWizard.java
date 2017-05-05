package it.naturtalent.e4.office.ui.letter.wizard;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import it.naturtalent.e4.office.INtOffice;
import it.naturtalent.e4.office.IOfficeService;
import it.naturtalent.e4.office.ProfilePreferences;
import it.naturtalent.e4.office.letter.IOfficeLetterAdapter;
import it.naturtalent.e4.office.letter.OfficeLetterModel;
import it.naturtalent.e4.office.ui.OfficeConstants;
import it.naturtalent.e4.project.IResourceNavigator;
import it.naturtalent.e4.project.ui.navigator.ResourceNavigator;
import it.naturtalent.e4.project.ui.utils.CreateNewFile;

import javax.annotation.PostConstruct;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.PersistState;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.wizard.Wizard;



public class NewLetterWizard extends Wizard
{

	protected String officeContext = IOfficeService.NTOFFICE_CONTEXT;
	
	
	protected NewLetterWizardPage newOfficeTextWizardPage;
	protected LetterAddressWizardPage addressOfficeWizardPage;
	protected ProfileLetterWizardPage profileWizardPage;
	
	// ueber 'context' fuer alle Pages verfuegbar
	private OfficeLetterModel wizardOfficeModel = new OfficeLetterModel();
	
	protected IEclipseContext context;		
		
	private IContainer selectedContainer = null;
		
	private IFile officeSelectedFile = null;
	
	// unter diesem Schluessel wird der Name des momentanen Profils gespeichert
	protected String profilenamePreferenceKey = ProfilePreferences.OFFICE_PROFILE_KEY;
		
	protected String settingOfficeLetterNameKey = OfficeConstants.NTOFFICE_SETTINGLETTERFILENAME_KEY;
	public void setSettingOfficeLetterNameKey(String settingOfficeLetterNameKey)
	{
		this.settingOfficeLetterNameKey = settingOfficeLetterNameKey;
	}

	protected String settingOfficeTemplateKey = it.naturtalent.e4.office.ui.Activator.NTOFFICE_SETTINGTEMPLATE_KEY;


	private IOfficeService officeService;


	private MApplication application;
	
	
	public NewLetterWizard()
	{
		setWindowTitle("New Office Wizard");
	}
	
	@PostConstruct
	protected void postConstruct (@Optional MApplication application,@Optional
			IOfficeService officeService)
	{		
		this.application = application;		
		context = application.getContext();
		context.set(OfficeLetterModel.class, wizardOfficeModel);
		
		
		this.officeService = officeService; 
	}

	@Override
	public void addPages()
	{
		// Dokument
		newOfficeTextWizardPage = ContextInjectionFactory.make(NewLetterWizardPage.class, context);		
		addPage(newOfficeTextWizardPage);		
			
		// Adresse
		addressOfficeWizardPage = ContextInjectionFactory.make(LetterAddressWizardPage.class, context);
		addPage(addressOfficeWizardPage);
				
		// Profil
		profileWizardPage = ContextInjectionFactory.make(ProfileLetterWizardPage.class, context);		
		addPage(profileWizardPage);		
	}


	@Override
	public boolean performFinish()
	{
		File createdDocument = null;
		String newDocumentName = null;
		
		// der ausgewaehlte ZielContainer
		IContainer container = newOfficeTextWizardPage.getSelectedContainer();
		
		// die ausgewaehlte Vorlage
		File templateFile = newOfficeTextWizardPage.getSelectedTemplate();
		
		if(container != null)
		{
			newDocumentName = newOfficeTextWizardPage.getSelectedFilename();
			if(StringUtils.isNotEmpty(newDocumentName))
			{
				try
				{
					// neues Dokument auf der Grundlage des verwendeten Templates					
					InputStream content = new FileInputStream(templateFile);
					CreateNewFile.createFile(getShell(), container, newDocumentName, content, null);					
					createdDocument = new File(container.getLocation().toFile(),newDocumentName);
				} catch (Exception e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
			}
		}
		else
		{			
			if (templateFile != null)
			{
				try
				{
					// temporaeres Dokument
					String extention = FilenameUtils.getExtension(templateFile
							.getPath());
					extention = (StringUtils.isEmpty(extention)) ? null : "."
							+ extention;
					createdDocument = File.createTempFile(
							"officeLetterTempFile", extention);

					// Vorlage in temporaere Datei kopieren
					FileUtils.copyFile(templateFile, createdDocument);

				} catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		
		if(createdDocument != null)	
		{
			
			IOfficeLetterAdapter textLetterAdapter = officeService.getLetterTemplateAdapter(templateFile.getPath());
			textLetterAdapter.openTextDocument(createdDocument);
			textLetterAdapter.setOfficeLetterModel(wizardOfficeModel);
			textLetterAdapter.writeOfficeLetterModel();			
			textLetterAdapter.closeTextDocument();
			textLetterAdapter.showTextDocument();
		}
		
		
		// Settings speichern
		//profileWizardPage.storeProfileNamePreference(profilenamePreferenceKey);		
		ContextInjectionFactory.invoke(newOfficeTextWizardPage, PersistState.class, context);
		ContextInjectionFactory.invoke(profileWizardPage, PersistState.class, context);
		
		// im Navigator selektieren
		if(newDocumentName != null)
			expandAndSelectInResourceNavigator(container, newDocumentName);
		
		return true;
	}
	
	
	

	private void expandAndSelectInResourceNavigator(IContainer container, String filename)
	{
		
		EModelService eModelService = (EModelService) application
				.getContext().get(EModelService.class.getName());
		MPart mPart = (MPart) eModelService.find(
				ResourceNavigator.RESOURCE_NAVIGATOR_ID, application);
		IResourceNavigator resourceNavigator = (IResourceNavigator) mPart.getObject();
		
		List itemsToExpand = new ArrayList();
		IContainer parent = container.getParent();
		while (parent != null)
		{
			itemsToExpand.add(0, parent);
			parent = parent.getParent();
		}
		
		IResource file = container.findMember(filename, false);
		TreeViewer treeViewer = resourceNavigator.getViewer();
		treeViewer.setExpandedElements(itemsToExpand.toArray());
		treeViewer.setSelection(new StructuredSelection(file), true);

	}
	
	
	@Override
	public void dispose()
	{
		if(context != null)
			context.remove(OfficeLetterModel.class);
		
		super.dispose();
	}

	public String getProfilenamePreferenceKey()
	{
		return profilenamePreferenceKey;
	}

	public OfficeLetterModel getOfficeLetterModel()
	{
		return wizardOfficeModel;
	}

	public void setOfficeLetterModel(OfficeLetterModel officeLetterModel)
	{
		this.wizardOfficeModel = officeLetterModel;
	}

	public void setProfilenamePreferenceKey(String profilenamePreferenceKey)
	{
		this.profilenamePreferenceKey = profilenamePreferenceKey;
	}
	
	public String getSettingOfficeTemplateKey()
	{
		return settingOfficeTemplateKey;
	}

	public String getSettingOfficeLetterNameKey()
	{
		return settingOfficeLetterNameKey;
	}


	
	

}
