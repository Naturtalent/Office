package it.naturtalent.e4.office.ui.letter.wizard;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import it.naturtalent.e4.office.IOfficeService;
import it.naturtalent.e4.office.ProfilePreferences;
import it.naturtalent.e4.office.letter.IOfficeLetterAdapter;
import it.naturtalent.e4.office.letter.OfficeLetterModel;
import it.naturtalent.e4.project.ui.utils.CreateNewFile;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;

public class OpenLetterWizard extends Wizard
{

	protected String officeContext = IOfficeService.NTOFFICE_CONTEXT;
	private OpenLetterWizardPage officeTextWizardPage;
	
	private IEclipseContext context;	
	
	private IOfficeService officeSerivce;
	
	// ueber 'context' fuer alle Pages verfuegbar
	private OfficeLetterModel wizardOfficeModel = new OfficeLetterModel();

	
	private WizardPage parentWizardPage;
	private LetterAddressWizardPage addressOfficeWizardPage;	
	private ProfileLetterWizardPage profileWizardPage;
	
	// unter diesem Schluessel wird der Name des momentanen Profils gespeichert
	protected String profilenamePreferenceKey = ProfilePreferences.OFFICE_PROFILE_KEY;
		
	protected String settingOfficeTemplateKey = it.naturtalent.e4.office.ui.Activator.NTOFFICE_SETTINGTEMPLATE_KEY;

	
	public OpenLetterWizard()
	{		
		setWindowTitle("Open Wizard");
	}
	
	@PostConstruct
	private void postConstruct (@Optional IEclipseContext context, @Optional
			IOfficeService officeService)
	{
		this.officeSerivce = officeService;
		this.context = context;		
		context.set(OfficeLetterModel.class, wizardOfficeModel);
	}
	
	@Override
	public void addPages()
	{
		// Dokument
		officeTextWizardPage = ContextInjectionFactory.make(OpenLetterWizardPage.class, context);
		addPage(officeTextWizardPage);
		
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
		IOfficeLetterAdapter textLetterAdapter = officeTextWizardPage.getTextLetterAdapter();
		
		String selectedTamplate = officeTextWizardPage.getSelectedTemplate();
		if(StringUtils.equals(selectedTamplate, officeTextWizardPage.getInitialTemplate()))
		{
			// Vorlage wurde nicht geaendert
			textLetterAdapter = officeTextWizardPage.getTextLetterAdapter();
			textLetterAdapter.setOfficeLetterModel(wizardOfficeModel);
			textLetterAdapter.writeOfficeLetterModel();
			textLetterAdapter.closeTextDocument();
			textLetterAdapter.showTextDocument();
		}
		else
		{
			// Vorlage wurde geaendert, neues Dokument erzeugen
			File newDocumentFile = officeTextWizardPage.getAutoFile();
			if(newDocumentFile != null)
			{
				String changedTemplate = officeTextWizardPage.getSelectedTemplate();
				IOfficeLetterAdapter newLetterAdapter = officeSerivce.getLetterTemplateAdapter(changedTemplate);
				
				newLetterAdapter.openTextDocument(newDocumentFile);
				newLetterAdapter.setOfficeLetterModel(wizardOfficeModel);
				newLetterAdapter.writeOfficeLetterModel();
				newLetterAdapter.closeTextDocument();
				newLetterAdapter.showTextDocument();
				
				// das Originaldokument schliessen
				textLetterAdapter.closeTextDocument();
			}
			
			
		}
		
		return true;
	}
	
	@Override
	public void dispose()
	{		
		IOfficeLetterAdapter textLetterAdapter = officeTextWizardPage.getTextLetterAdapter();
		if(textLetterAdapter != null)
			textLetterAdapter.closeTextDocument();
		
		if(context != null)
			context.remove(OfficeLetterModel.class);
		
		super.dispose();
	}

	public void setParentWizardPage(WizardPage parentWizardPage)
	{
		this.parentWizardPage = parentWizardPage;
	}
	
	public OfficeLetterModel getModel()
	{
		return wizardOfficeModel;
	}

	public void setModel(OfficeLetterModel model)
	{
		this.wizardOfficeModel = model;
	}

	public String getProfilenamePreferenceKey()
	{
		return profilenamePreferenceKey;
	}

	public void setProfilenamePreferenceKey(String profilenamePreferenceKey)
	{
		this.profilenamePreferenceKey = profilenamePreferenceKey;
	}
	
	
}
