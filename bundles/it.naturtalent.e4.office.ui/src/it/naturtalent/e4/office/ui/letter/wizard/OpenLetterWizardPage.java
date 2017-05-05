package it.naturtalent.e4.office.ui.letter.wizard;

import it.naturtalent.e4.office.IOfficeService;
import it.naturtalent.e4.office.letter.IOfficeLetterAdapter;
import it.naturtalent.e4.office.letter.OfficeLetterModel;
import it.naturtalent.e4.office.ui.Activator;
import it.naturtalent.e4.office.ui.letter.LetterBetreffWizardGroup;
import it.naturtalent.e4.office.ui.letter.LetterContentWizardGroup;
import it.naturtalent.e4.office.ui.letter.LetterOfficeWizardComposite;
import it.naturtalent.e4.project.NtProject;

import java.io.File;

import javax.annotation.PostConstruct;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;


public class OpenLetterWizardPage extends WizardPage
{
	private LetterOfficeWizardComposite officeWizardComposite;
	private IOfficeLetterAdapter textLetterAdapter;
	private IOfficeService officeSerivce;

	private IEclipseContext context;
	private OfficeLetterModel wizardOfficeModel;
	
	//private File fileTemplate = null;	
	private String initialTemplate;
	
	// Handler ueberwacht Aenderungen der Vorlage in 'LetterOfficeWizardComposite'
	private IEventBroker broker;
	private EventHandler setTemplateHandler = new EventHandler()
	{
		@Override
		public void handleEvent(Event event)
		{
			String templatePath = (String) event.getProperty(IEventBroker.DATA);
			officeWizardComposite.setSelectedTemplate(templatePath);			
			update();
		}
	};

	private IResource selectedResource = null;
	
	private LetterBetreffWizardGroup groupBetreff;
	private LetterContentWizardGroup groupInhalt;
	private Composite compositeButton;

	/**
	 * Create the wizard.
	 */
	public OpenLetterWizardPage()
	{
		super("wizardPage");
		setTitle("Office Dokument \u00F6ffnen");
		setDescription("liest den Inhalt eines Dokuments und zeigt diesen in einer Vorlage an");
	}

	/**
	 * Create contents of the wizard.
	 * @param parent
	 */
	public void createControl(Composite parent)
	{
		Composite container = new Composite(parent, SWT.NULL);

		setControl(container);
		container.setLayout(new GridLayout(3, false));
		
		officeWizardComposite = new LetterOfficeWizardComposite(container, SWT.NONE);
		officeWizardComposite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		officeWizardComposite.setBroker(broker);
		officeWizardComposite.setOfficeContext(((OpenLetterWizard)getWizard()).officeContext);	
		officeWizardComposite
		.setSettingOfficeTemplateKey(((OpenLetterWizard) getWizard()).settingOfficeTemplateKey);

		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		
		groupBetreff = new LetterBetreffWizardGroup(container, SWT.NONE);
		GridData gd_betreffWizardGroup = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_betreffWizardGroup.heightHint = 89;
		groupBetreff.setLayoutData(gd_betreffWizardGroup);
		ContextInjectionFactory.invoke(groupBetreff, PostConstruct.class,context);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		
		groupInhalt = new LetterContentWizardGroup(container, SWT.NONE, ((OpenLetterWizard)getWizard()).officeContext);
		GridData gd_cntntwzrdgrpInhalt = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_cntntwzrdgrpInhalt.heightHint = 310;
		groupInhalt.setLayoutData(gd_cntntwzrdgrpInhalt);
		ContextInjectionFactory.invoke(groupInhalt, PostConstruct.class, context);
		
		compositeButton = new Composite(container, SWT.NONE);
		compositeButton.setLayout(new GridLayout(1, false));
		compositeButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		
		init();
		
		update();
	}
	
	@PostConstruct
	private void postConstruct(@Optional
	IOfficeService officeService, @Optional
	IEventBroker broker, @Optional
	ESelectionService selectionService, @Optional
	IEclipseContext context)
	{		
		this.context = context;		
		wizardOfficeModel = context.get(OfficeLetterModel.class);
		
		// die ausgewaehlte Dokumentdatei
		Object selObj = selectionService.getSelection();
		if(selObj instanceof IFile)
			selectedResource = (IResource)selObj;
		
		this.officeSerivce = officeService;
		
		this.broker = broker;		
		if(broker != null)
			broker.subscribe(Activator.SELECT_TEMPLATE_EVENT, setTemplateHandler);
	}

	private void init()
	{
		if (selectedResource != null)
		{
			// Composite im OPEN_MODUS initialisieren
			officeWizardComposite.setSelectedResource(selectedResource);

			// Vorlagen initialisieren
			officeWizardComposite.initTemplates(officeSerivce);

			// dass OfficeDokument mit einem praktikablem Adapter oeffnen
			File officeFile = officeWizardComposite.getSelectedOfficeFile();
			initialTemplate = officeSerivce.getPracticalTemplate(
					officeFile.getPath(),
					((OpenLetterWizard) getWizard()).officeContext);
			if (initialTemplate == null)
				return;

			textLetterAdapter = officeSerivce
					.getLetterTemplateAdapter(initialTemplate);
			textLetterAdapter.openTextDocument(officeFile);
			textLetterAdapter.readOfficeLetterModel();

			// Inhalt des Dokuments in Modell laden
			OfficeLetterModel officeModel = textLetterAdapter
					.getOfficeLetterModel();
			//wizardOfficeModel.setAddress(officeModel.getAddress());
			wizardOfficeModel.setProfile(officeModel.getProfile());

			// Betreff anzeigen
			groupBetreff.setOfficeModel(officeModel.getBetreff());

			// Textinhalt anzeigen
			groupInhalt.setOfficeModel(officeModel.getContent());

			// der Key des TextLetterAdapters entspricht dem TemplateFilePath
			officeWizardComposite.setSelectedTemplate(initialTemplate);
		}
	}
	
	private String getContainerName()
	{
		String containerName = "";
		if(selectedResource != null)
		{
			IPath iPath = selectedResource.getFullPath();
			containerName = new NtProject(selectedResource.getProject()).getName();
			iPath = iPath.removeFirstSegments(1);
			containerName = containerName+File.separator+iPath.toString();
		}
		return containerName;
	}
	
	private void update()
	{
		/*
		boolean templateFlag = (fileTemplate != null) && (fileTemplate.exists());
		setPageComplete(templateFlag);
		*/
		setPageComplete(true);
	}

	@Override
	public void dispose()
	{
		if(broker != null)
			broker.unsubscribe(setTemplateHandler);
			
		officeWizardComposite.dispose();
		super.dispose();
	}
	
	public IOfficeLetterAdapter getTextLetterAdapter()
	{
		return textLetterAdapter;
	}

	public String getSelectedTemplate()
	{
		return officeWizardComposite.getSelectedTemplate();
	}

	public String getInitialTemplate()
	{
		return initialTemplate;
	}
	
	public File getAutoFile()
	{
		return officeWizardComposite.getAutoDocument();
	}

}
