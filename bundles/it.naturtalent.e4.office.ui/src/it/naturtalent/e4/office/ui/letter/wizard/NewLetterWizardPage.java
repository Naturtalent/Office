package it.naturtalent.e4.office.ui.letter.wizard;

import it.naturtalent.e4.office.IOfficeService;
import it.naturtalent.e4.office.letter.IOfficeLetterAdapter;
import it.naturtalent.e4.office.letter.OfficeLetterModel;
import it.naturtalent.e4.office.letter.TitelTextLetterRow;
import it.naturtalent.e4.office.ui.Activator;
import it.naturtalent.e4.office.ui.letter.LetterBetreffWizardGroup;
import it.naturtalent.e4.office.ui.letter.LetterContentWizardGroup;
import it.naturtalent.e4.office.ui.letter.LetterOfficeWizardComposite;
import it.naturtalent.e4.project.NtProject;

import java.io.File;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.time.FastDateFormat;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.PersistState;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

public class NewLetterWizardPage extends WizardPage
{
	private String officeContext;

	private LetterOfficeWizardComposite officeWizardComposite;

	private IOfficeLetterAdapter textLetterAdapter;
	
	private ESelectionService selectionService;

	private IOfficeService officeService;

	//private OfficeLetterModel officeModel;
	
	private IEclipseContext context;
	private OfficeLetterModel wizardOfficeModel;

	protected LetterBetreffWizardGroup groupBetreff;

	protected LetterContentWizardGroup groupInhalt;

	// Handler ueberwacht 'Templateselection' in 'OfficeWizardComposite'
	protected IEventBroker broker;

	private EventHandler setTemplateHandler = new EventHandler()
	{
		@Override
		public void handleEvent(Event event)
		{
			String templateName = (String) event.getProperty(IEventBroker.DATA);			
			setTemplate(templateName);
		}
	};

	/**
	 * Create the wizard.
	 */
	public NewLetterWizardPage()
	{
		super("wizardPage");
		setTitle("Neues Office Anschreiben");
		setDescription("ein Write-Dokument definieren");
	}

	@PostConstruct
	private void postConstruct(@Optional
	IOfficeService officeService, @Optional
	IEventBroker broker, @Optional
	ESelectionService selectionService, @Optional
	IEclipseContext context)
	{		
		this.context = context;	
		this.officeService = officeService;
		this.selectionService = selectionService;

		wizardOfficeModel = context.get(OfficeLetterModel.class);
		
		this.broker = broker;
		this.broker.subscribe(Activator.SELECT_TEMPLATE_EVENT,setTemplateHandler);
		
	}

	@PersistState
	public void storeSettings()
	{
		officeWizardComposite.storeSettings();
	}

	/**
	 * Create contents of the wizard.
	 * 
	 * @param parent
	 */
	public void createControl(Composite parent)
	{
		Composite container = new Composite(parent, SWT.NULL);

		setControl(container);
		container.setLayout(new GridLayout(3, false));

		// Composite fuer Container, Datei, Template des Officedokuments
		officeWizardComposite = new LetterOfficeWizardComposite(container, SWT.NONE);
		officeWizardComposite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER,
				true, false, 3, 1));
		officeWizardComposite.setParentWizardPage(this);
		officeWizardComposite.setBroker(broker);
		officeContext = ((NewLetterWizard) getWizard()).officeContext;
		officeWizardComposite.setOfficeContext(officeContext);
		officeWizardComposite
				.setSettingOfficeTemplateKey(((NewLetterWizard) getWizard())
						.getSettingOfficeTemplateKey());

		officeWizardComposite
		.setSettingOfficeLetterNameKey(((NewLetterWizard) getWizard())
				.getSettingOfficeLetterNameKey());

		// Betreff
		groupBetreff = new LetterBetreffWizardGroup(container, SWT.NONE);
		ContextInjectionFactory.invoke(groupBetreff, PostConstruct.class, context);
		groupBetreff.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 3, 1));

		// Inhalt
		groupInhalt = new LetterContentWizardGroup(container, SWT.NONE, officeContext);
		ContextInjectionFactory.invoke(groupInhalt, PostConstruct.class,context);
		groupInhalt.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 3, 1));

		init();
	}

	public void init()
	{
		// Zielverzeichnis des neuen Officedokuments
		Object selObj = selectionService.getSelection();
		if (selObj instanceof IResource)
		{
			IResource selectedResource = (IResource) selObj;
			if ((selectedResource instanceof IContainer)
					|| (selectedResource.getType() == IResource.FILE))
			{
				IContainer selectedContainer = (IContainer) ((selectedResource
						.getType() == IResource.FILE) ? selectedResource
						.getParent() : selectedResource);
				officeWizardComposite.setSelectedContainer(selectedContainer);
			}
		}
		else
		{
			// temp Zielcontainer
			officeWizardComposite.setTemporary();
		}

		// Vorlagenverwaltung initialisieren
		officeWizardComposite.initTemplates(officeService);
		
		// Daten der ausgewaehlten Vorlage auslesen
		File fileTemplate = officeWizardComposite.getInUsedTemplateFile();
		if(fileTemplate != null)
			setTemplate(fileTemplate.getPath());
	}

	/*
	 * Die ausgewaehlte Vorlage wird geoeffnet, die Officedaten ausgelesen und
	 * als Betreff und Inhalt angezeigt.
	 */
	private void setTemplate(String templateFileName)
	{		
		if (StringUtils.isNotEmpty(templateFileName))
		{
			// Template oeffnen, Modelldaten auslesen und wieder schliessen
			IOfficeLetterAdapter textAdapter = officeService.getLetterTemplateAdapter(templateFileName);
			File templateFile = new File(templateFileName);
			textAdapter.openTextDocument(templateFile);
			textAdapter.readOfficeLetterModel();

			// Officemodelldaten aus dem Template lesen
			OfficeLetterModel templateOfficeModel = textAdapter.getOfficeLetterModel();
			if (templateOfficeModel != null)
			{
				// Betreffdaten generieren
				List<TitelTextLetterRow>betreffRows = templateOfficeModel.getBetreff();
				
				if ((betreffRows != null) && (!betreffRows.isEmpty()))
				{
					// das aktuelle Datum vorbelegen
					FastDateFormat df = FastDateFormat
							.getDateInstance(FastDateFormat.MEDIUM);
					TitelTextLetterRow row = betreffRows.get(0);
					row.setTitel("Datum");
					row.setText(df.format(new Date()));

					// Betrefftext mit Projektnamen vorbelegen
					row = betreffRows.get(1);
					row.setTitel("Betreff");

					Object selObj = selectionService.getSelection();
					if (selObj instanceof IResource)
					{
						IProject iProject = ((IResource) selObj).getProject();
						NtProject ntProject = new NtProject(iProject);
						row.setText(ntProject.getName());
					}

					// Betreffdaten an gui uebergeben
					groupBetreff.setOfficeModel(betreffRows);

				}
				
				// Inhalt an gui uebergeben
				groupInhalt.setOfficeModel(templateOfficeModel.getContent());
			}

			// Templante schliessen
			textAdapter.closeTextDocument();
		}
	}

	public IContainer getSelectedContainer()
	{
		return officeWizardComposite.getSelectedContainer();
	}

	public String getSelectedFilename()
	{
		return officeWizardComposite.getSelectedFilename();
	}

	public File getSelectedTemplate()
	{
		return officeWizardComposite.getInUsedTemplateFile();
	}


	/*
	public String getContent()
	{
		return groupInhalt.getContent();
	}
	*/

	/*
	public File getTemporaryFile()
	{
		return (File) officeWizardComposite
				.getData(OfficeWizardComposite.TEMP_CONTAINER_MARKER);
	}
	*/

	@Override
	public void dispose()
	{
		if (broker != null)
			broker.unsubscribe(setTemplateHandler);

		super.dispose();
	}

}
