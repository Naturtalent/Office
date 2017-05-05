package it.naturtalent.e4.office.ui.letter.wizard;

import it.naturtalent.e4.office.ProfilePreferences;
import it.naturtalent.e4.office.letter.OfficeLetterModel;
import it.naturtalent.e4.office.letter.OfficeLetterProfile;
import it.naturtalent.e4.office.letter.OfficeLetterProfiles;
import it.naturtalent.e4.office.letter.OfficeLetterUtils;
import it.naturtalent.e4.office.letter.TitelTextLetterRow;
import it.naturtalent.e4.office.ui.Messages;
import it.naturtalent.e4.office.ui.letter.LetterProfileWizardComposite;

import java.io.File;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.di.extensions.Preference;
import org.eclipse.e4.ui.di.PersistState;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.wb.swt.SWTResourceManager;
import org.osgi.service.prefs.BackingStoreException;

public class ProfileLetterWizardPage extends WizardPage
{
	private final FormToolkit formToolkit = new FormToolkit(Display.getDefault());

	private LetterProfileWizardComposite profileWizardComposite;

	protected String officeContext;
	
	private OfficeLetterProfiles officeProfiles;
		
	private IEclipsePreferences node;
	
	private CCombo comboProfile;

	private ESelectionService selectionService;
	
	private OfficeLetterModel wizardOfficeModel;
	
	/**
	 * Create the wizard.
	 */
	public ProfileLetterWizardPage()
	{
		super("wizardPage"); //$NON-NLS-1$
		setTitle("Profile"); //$NON-NLS-1$
		setDescription("Individuelle Eigenschaften definieren und speichern");		 //$NON-NLS-1$
	}
	
	@PostConstruct
	public void postConstruct(
			@Preference(nodePath = ProfilePreferences.ROOT_OFFICEPROFILE_PREFERENCES_NODE) IEclipsePreferences node,
			@Optional ESelectionService selectionService, @Optional
			IEclipseContext context)
	{
		this.selectionService = selectionService;
		this.node = node;	
		wizardOfficeModel = context.get(OfficeLetterModel.class);
	}
	
	@PersistState
	public void persistState()
	{
		String profileName = comboProfile.getText();
		OfficeLetterProfiles profiles = OfficeLetterUtils.readProfiles(((NewLetterWizard) getWizard()).officeContext);
		if (profiles != null)
		{

			for (OfficeLetterProfile profile : profiles.getProfiles())
			{
				if (StringUtils.equals(profileName, profile.getName()))
				{
					try
					{
						node.put(((NewLetterWizard) getWizard())
								.getProfilenamePreferenceKey(), profileName);
						node.flush();
					} catch (BackingStoreException e)
					{
					}

					break;
				}
			}
		}
	}

	/**
	 * Der Name des momentanen Profils wird als Preferenz gespeichert.
	 * 
	 */
	public void storeProfileNamePreference(String preferenceKey)
	{
		OfficeLetterProfile officeProfile = profileWizardComposite.getProfileModel();
		if (officeProfile != null)
		{
			String profileName = officeProfile.getName();
			if (StringUtils.isNotEmpty(profileName))
			{
				// Settings aktualisieren
				InstanceScope.INSTANCE.getNode(
						ProfilePreferences.ROOT_OFFICEPROFILE_PREFERENCES_NODE)
						.put(preferenceKey, profileName);
			}
		}
	}

	/**
	 * Create contents of the wizard.
	 * @param parent
	 */
	public void createControl(Composite parent)
	{
		Composite container = new Composite(parent, SWT.NULL);
		container.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));

		setControl(container);
		container.setLayout(new GridLayout(3, false));
		
		Label lblProfile = formToolkit.createLabel(container, "Name", SWT.NONE); //$NON-NLS-1$
		lblProfile.setToolTipText("unter diesem Namen ist das Profil gespeichert"); //$NON-NLS-1$
		lblProfile.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		
		
		comboProfile = new CCombo(container, SWT.BORDER);
		comboProfile.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				OfficeLetterProfile profile = (OfficeLetterProfile) comboProfile.getData(comboProfile.getText());
				wizardOfficeModel.setProfile(profile);
				profileWizardComposite.setProfileModel(profile);								
			}
		});
		comboProfile.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		formToolkit.adapt(comboProfile);
		formToolkit.paintBordersFor(comboProfile);
		new Label(container, SWT.NONE);
		
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		
		profileWizardComposite = new LetterProfileWizardComposite(container, SWT.NONE);
		GridData gd_profileWizardComposite = new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1);
		gd_profileWizardComposite.heightHint = 600;
		profileWizardComposite.setLayoutData(gd_profileWizardComposite);
		formToolkit.adapt(profileWizardComposite);
		formToolkit.paintBordersFor(profileWizardComposite);
		
		Button btnStore = new Button(container, SWT.NONE);
		btnStore.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{				
				storeProfile(comboProfile.getText());
			}
		});
		btnStore.setToolTipText("das Profils speichern"); //$NON-NLS-1$
		formToolkit.adapt(btnStore, true, true);
		btnStore.setText("speichern"); //$NON-NLS-1$
		
		Button btnDelete = formToolkit.createButton(container, "l\u00F6schen", SWT.NONE); //$NON-NLS-1$
		btnDelete.setToolTipText("das Profil l\u00F6schen"); //$NON-NLS-1$
		btnDelete.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				deleteProfile();
			}
		});
		new Label(container, SWT.NONE);
		
		init();
	}
	
	protected void init()
	{			
		// Open- oder NewModus
		Object wizardObj = getWizard();
		if (wizardObj instanceof OpenLetterWizard)
		{
			// OpenWizard! - Profil aus dem Dokument holen
			OpenLetterWizard wizard = (OpenLetterWizard) wizardObj;			
			if(wizardOfficeModel != null)
			{
				// OfficeContext aus dem ParentWizard uebernehmen
				officeContext = wizard.officeContext;
				
				// Profildaten aus dem geoffneten Dokument
				profileWizardComposite.setProfileModel(wizardOfficeModel.getProfile());
				String openFileName = "unbekannt"; //$NON-NLS-N$
				Object selObj = selectionService.getSelection();
				if(selObj instanceof IFile)
					openFileName = FilenameUtils.getBaseName(((IFile)selObj).getName());				
				String profileName = Messages.bind(Messages.ProfileLetterWizardPage_ProfileOpenFile, openFileName );
				
				// Profil (mit Dateinamen-Label) in die Combo
				comboProfile.add(profileName);
				comboProfile.setText(comboProfile.getItem(0));
				comboProfile.setData(profileName, wizardOfficeModel.getProfile());
				
				// alle sonstigen verfuegbaren Profile ebenfalls in die Combo
				officeProfiles = OfficeLetterUtils.readProfiles(officeContext);
				if(officeProfiles != null)
				{
					for (OfficeLetterProfile profile : officeProfiles
							.getProfiles())
					{						
						comboProfile.add(profile.getName());
						comboProfile.setData(profile.getName(), profile);
					}
				}
			}
		}

		// New Wizard Modus
		if (wizardObj instanceof NewLetterWizard)
		{
			NewLetterWizard wizard = (NewLetterWizard) wizardObj;
			String prefProfileName = node.get(
					wizard.getProfilenamePreferenceKey(),
					ProfilePreferences.DEFAULT_OFFICE_PROFILE);

			// Settingname uebernehmen
			if(StringUtils.isNotEmpty(prefProfileName))
				comboProfile.setText(prefProfileName);
							
			// OfficeContext aus dem ParentWizard uebernehmen
			officeContext = wizard.officeContext;
			
			// alle sonstigen verfuegbaren Profile ebenfalls in die Combo
			officeProfiles = OfficeLetterUtils.readProfiles(officeContext);
			if(officeProfiles != null)
			{
				for (OfficeLetterProfile profile : officeProfiles
						.getProfiles())
				{						
					comboProfile.add(profile.getName());
					comboProfile.setData(profile.getName(), profile);
				}
			}
			
			OfficeLetterProfile profile = null;
			if(StringUtils.isNotEmpty(prefProfileName))
			{
				int idx = comboProfile.indexOf(prefProfileName);
				if(idx >= 0)
					profile = (OfficeLetterProfile) comboProfile.getData(prefProfileName); 
			}
			else
			{
				String profileName = comboProfile.getText();
				if(StringUtils.isNotEmpty(profileName))
					profile = (OfficeLetterProfile) comboProfile.getData(profileName);
			}
			
			if(profile != null)
			{
				profileWizardComposite.setProfileModel(profile);
				wizardOfficeModel.setProfile(profile);
			}
		}
	}
	
	private OfficeLetterProfile findProfile(String profileName)
	{
		for (OfficeLetterProfile existProfile : officeProfiles.getProfiles())
		{
			if (StringUtils.equals(profileName, existProfile.getName()))
				return existProfile;
		}
		
		return null;
	}
	
	private void deleteProfile()
	{
		String profileName = comboProfile.getText();

		if ((StringUtils.isNotEmpty(profileName))
				&& (!StringUtils.equals(profileName,
						ProfilePreferences.DEFAULT_OFFICE_PROFILE)))
		{
			// existiert ein Profil mit 'profileName'
			int idx = comboProfile.indexOf(profileName);
			if (idx >= 0)
			{
								
				// ggf. auch als Preferenz entfernen
				String profilenamePreferenceKey = null;
				if (getWizard() instanceof NewLetterWizard)
				{
					profilenamePreferenceKey = ((NewLetterWizard) getWizard())
							.getProfilenamePreferenceKey();
				}
				if (getWizard() instanceof OpenLetterWizard)
				{
					profilenamePreferenceKey = ((OpenLetterWizard) getWizard())
							.getProfilenamePreferenceKey();
				}				
				if (profilenamePreferenceKey != null)
				{
					String prefProfileName = node.get(
							ProfilePreferences.OFFICE_PROFILE_KEY,
							ProfilePreferences.DEFAULT_OFFICE_PROFILE);
					if (StringUtils.equals(profileName, prefProfileName))
						node.remove(profilenamePreferenceKey);
				}

				// aus dem Modell entfernen
				OfficeLetterProfile existProfile = (OfficeLetterProfile) comboProfile
						.getData(profileName);
				officeProfiles.getProfiles().remove(existProfile);
				OfficeLetterUtils.writeProfiles(officeContext, officeProfiles);

				// aus Combo entfernen
				comboProfile.remove(profileName);

				// Combo [0} wird aktuelles Profil
				comboProfile.setText(comboProfile.getItem(0));
				OfficeLetterProfile profile = (OfficeLetterProfile) comboProfile
						.getData(comboProfile.getText());
				profileWizardComposite.setProfileModel(profile);
			}
		}
	}
	
	private void storeProfile(String storeProfileName)
	{
		OfficeLetterProfile toStoreProfile;
		
		if (StringUtils.isNotEmpty(storeProfileName))
		{
			// das momentan selektierte Profil abfragen
			toStoreProfile = (OfficeLetterProfile) profileWizardComposite.getProfileModel();

			// neues Profile durch clonen
			toStoreProfile = (OfficeLetterProfile) toStoreProfile.clone();
			toStoreProfile.setName(storeProfileName);

			int idx = comboProfile.indexOf(storeProfileName);
			if (idx < 0)
			{
				// add Profil
				comboProfile.add(storeProfileName);
				comboProfile.setData(storeProfileName, toStoreProfile);
				officeProfiles.getProfiles().add(toStoreProfile);
			}
			else
			{
				// Name existiert, Profil austauschen
				OfficeLetterProfile existProfile = (OfficeLetterProfile) comboProfile
						.getData(storeProfileName);
				officeProfiles.getProfiles().remove(existProfile);
				comboProfile.setData(storeProfileName, toStoreProfile);
				officeProfiles.getProfiles().add(toStoreProfile);
			}

			// Profile speichern
			OfficeLetterUtils.writeProfiles(officeContext, officeProfiles);
		}
	}

}
