package it.naturtalent.e4.office.ui.letter;

import it.naturtalent.e4.office.IOfficeService;
import it.naturtalent.e4.office.ui.Activator;
import it.naturtalent.e4.office.ui.Messages;
import it.naturtalent.e4.project.NtProject;
import it.naturtalent.e4.project.ui.dialogs.ContainerSelectionDialog;
import it.naturtalent.e4.project.ui.dialogs.ContainerSelectionDialog1;
import it.naturtalent.e4.project.ui.utils.CreateNewFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.internal.workbench.swt.WorkbenchSWTActivator;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackAdapter;

/**
 * Grundlegende Daten eines Officedokuments definieren. Zielcontainer =
 * Verzeichnis in dem sich das Dokument befindet Detei = Name des Dokuments
 * Template = die verwendete Vorlage
 * 
 * Die Templates (Profile, Textbausteine etc) sind ein einem speziellem Bereich
 * des Workspaces (OfficeContext) gespeichert.
 * 
 * @author dieter
 * 
 */
public class LetterOfficeWizardComposite extends Composite
{
	private final FormToolkit formToolkit = new FormToolkit(
			Display.getDefault());

	private Text txtContainer;

	private Button btnSelect;

	private CCombo comboTemplates;

	private ControlDecoration controlContainerDecoration;

	private ControlDecoration controlTemplateDecoration;

	// der verwendete OfficeContext (Default)
	private String officeContext;

	private IContainer selectedContainer = null;

	private WizardPage parentWizardPage;

	private IResource selectedResource;

	private String settingOfficeLetterNameKey = null;
	private String settingOfficeTemplateKey = null;

	private IDialogSettings settings;

	private static final int CREATE_MODE = 0;

	private static final int OPEN_MODE = 1;

	private int mode = CREATE_MODE;

	private String selectedTemplateFile;

	private IEventBroker broker;

	public static final String TEMPORAER_CONTAINER_MARKER = Messages.LetterOfficeWizardComposite_TEMPORAER_CONTAINER_MARKER;
	private CCombo comboLetterFile;
	private ControlDecoration controlFileDecoration;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public LetterOfficeWizardComposite(final Composite parent, int style)
	{
		super(parent, style);
		setLayout(new GridLayout(3, false));

		Label lblContainer = formToolkit.createLabel(this, Messages.OfficeWizardComposite_lblContainer_text,
				SWT.NONE);
		lblContainer.setBackground(SWTResourceManager
				.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		lblContainer.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));

		txtContainer = formToolkit.createText(this, "", SWT.NONE); //$NON-NLS-1$
		txtContainer.setEditable(false);
		txtContainer.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));

		controlContainerDecoration = new ControlDecoration(txtContainer,
				SWT.LEFT | SWT.TOP);
		controlContainerDecoration
				.setDescriptionText(Messages.LetterOfficeWizardComposite_MissingDirectoryErraor);
		controlContainerDecoration.setImage(SWTResourceManager.getImage(
				LetterOfficeWizardComposite.class,
				"/org/eclipse/jface/fieldassist/images/error_ovr.gif")); //$NON-NLS-1$

		btnSelect = formToolkit.createButton(this, Messages.LetterOfficeWizardComposite_LabelSelect, SWT.NONE);
		btnSelect.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				ContainerSelectionDialog1 selectContainerDialog = new ContainerSelectionDialog1(
						parent.getShell());
				selectContainerDialog.create();
				if (selectedContainer != null)
					selectContainerDialog
							.setSelectedContainer(selectedContainer);
				if (selectContainerDialog.open() == ContainerSelectionDialog.OK)
				{
					selectedContainer = selectContainerDialog
							.getSelectedContainer();
					txtContainer.setText(getContainerName());
					releaseTemporay();
					updateWizard();
				}
			}
		});

		Label lblFile = formToolkit.createLabel(this, Messages.OfficeWizardComposite_lblFile_text, SWT.NONE);
		lblFile.setBackground(SWTResourceManager
				.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		lblFile.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false,
				1, 1));
		
		comboLetterFile = new CCombo(this, SWT.BORDER);
		comboLetterFile.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				String ext = FilenameUtils.getExtension(selectedTemplateFile);				
				String fileName = comboLetterFile.getItem(comboLetterFile
						.getSelectionIndex());								
				fileName = FilenameUtils.removeExtension(fileName)+"."+ext;				 //$NON-NLS-1$
				comboLetterFile.setText(getAutoFileName(selectedContainer,
							fileName));
			}
		});
		comboLetterFile.addModifyListener(new ModifyListener()
		{
			public void modifyText(ModifyEvent e)
			{
				updateWizard();
			}
		});
		comboLetterFile.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		formToolkit.adapt(comboLetterFile);
		formToolkit.paintBordersFor(comboLetterFile);
		
		controlFileDecoration = new ControlDecoration(comboLetterFile, SWT.LEFT | SWT.TOP);
		controlFileDecoration.setImage(SWTResourceManager.getImage(LetterOfficeWizardComposite.class, "/org/eclipse/jface/fieldassist/images/error_ovr.gif"));		 //$NON-NLS-1$
		new Label(this, SWT.NONE);

		// Vorlagen
		Label lblLayout = formToolkit.createLabel(this, Messages.OfficeWizardComposite_lblLayout_text, SWT.NONE);
		lblLayout.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblLayout.setBackground(SWTResourceManager
				.getColor(SWT.COLOR_WIDGET_BACKGROUND));

		comboTemplates = new CCombo(this, SWT.BORDER);		
		comboTemplates.setEditable(false);
		comboTemplates.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				selectedTemplateFile = (String) comboTemplates
						.getData(comboTemplates.getText());
				if (broker != null)
					broker.post(Activator.SELECT_TEMPLATE_EVENT,
							selectedTemplateFile);

				// Dateiname an geaenderte Vorlage anpassen
				String ext = FilenameUtils.getExtension(selectedTemplateFile);
				String fileName = comboLetterFile.getText();					
				fileName = FilenameUtils.removeExtension(fileName)+"."+ext;				 //$NON-NLS-1$
				comboLetterFile.setText(getAutoFileName(selectedContainer,
							fileName));

				updateWizard();
			}
		});
		comboTemplates.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));
		formToolkit.adapt(comboTemplates);
		formToolkit.paintBordersFor(comboTemplates);
		controlTemplateDecoration = new ControlDecoration(comboTemplates,
				SWT.LEFT | SWT.TOP);
		controlTemplateDecoration.setDescriptionText(Messages.LetterOfficeWizardComposite_NoTemplateError);
		controlTemplateDecoration.setImage(SWTResourceManager.getImage(
				LetterOfficeWizardComposite.class,
				"/org/eclipse/jface/fieldassist/images/error_ovr.gif")); //$NON-NLS-1$

		new Label(this, SWT.NONE);

		updateWizard();
	}
	
	public void initTemplates(IOfficeService officeServices)
	{
		selectedTemplateFile = null;
		settings = WorkbenchSWTActivator.getDefault().getDialogSettings();
		
		List<String>availTemplates = officeServices.getLetterTemplates(officeContext);		
		if (!availTemplates.isEmpty())
		{
			// das zuverwendente Template aus den Settings
			String settingTemplateName = settings.get(settingOfficeTemplateKey);
			if (StringUtils.isEmpty(settingTemplateName))
			{
				// noch keine Setting - dann 1. der Verfuegbaren				
				selectedTemplateFile = availTemplates.get(0);				
			}
			else
			{
				// in der Liste der Verfuegbaren den Setting suchen
				for (String template : availTemplates)
				{
					if (StringUtils.equals(template,
							settingTemplateName))
					{
						selectedTemplateFile = template;
						break;
					}
				}
			}

			// TemplateCombo mit den verfuegbaren Templates initialisieren
			for (String template : availTemplates)
			{
				String baseName = FilenameUtils.getBaseName(template);
				comboTemplates.add(baseName);
				comboTemplates.setData(baseName, template);
			}
			
			if(selectedTemplateFile != null)
				comboTemplates.setText(FilenameUtils.getBaseName(selectedTemplateFile));
			
			// einen Namen fuer das neue Dokument vorgeben
			if (!StringUtils.equals(txtContainer.getText(),
					TEMPORAER_CONTAINER_MARKER) && (mode != OPEN_MODE))
			{
				String originalFileName = null;
				
				// aus dem Setting
				String [] names = settings.getArray(settingOfficeLetterNameKey);
				if(ArrayUtils.isEmpty(names))			
					names = new String [] {Messages.LetterOfficeWizardComposite_EmptyLetterName};
				
				originalFileName = names[0];
				if(StringUtils.isEmpty(originalFileName))
				{
					originalFileName = Messages.LetterOfficeWizardComposite_EmptyLetterName;
					names[0] = originalFileName;
				}
							
				comboLetterFile.setItems(names);								
				comboLetterFile.setText(originalFileName);

				String ext = FilenameUtils.getExtension(settingTemplateName);
				originalFileName = FilenameUtils
						.removeExtension(originalFileName) + "." + ext; //$NON-NLS-1$
				if (StringUtils.isNotEmpty(originalFileName))
					comboLetterFile.setText(getAutoFileName(selectedContainer,
							originalFileName));

			}
		}
		
		// Broker informiert ueber selektierte Templatedatei
		if(mode == CREATE_MODE)
		{
			if (broker != null)
				broker.post(Activator.SELECT_TEMPLATE_EVENT,
						selectedTemplateFile != null ? selectedTemplateFile
								: ""); //$NON-NLS-1$
		}
		
		updateWizard();
	}
	
	public File getAutoDocument()
	{
		File autoDocument = null;
				
		try
		{
			String autoFileName = getAutoFileName(selectedContainer, getSelectedFilename());
			File autoFile = selectedContainer.getLocation().toFile();	
			autoFile = new File(autoFile, autoFileName);
			InputStream content = new FileInputStream(new File(selectedTemplateFile));
			
			CreateNewFile.createFile(getShell(), selectedContainer, autoFileName, content, null);					
			autoDocument = new File(selectedContainer.getLocation().toFile(),autoFileName);

		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return autoDocument;
	}

	private String getAutoFileName(IContainer dir, String originalFileName)
	{
		String autoFileName;

		if (dir == null)
			return ""; //$NON-NLS-1$

		int counter = 1;
		while (true)
		{
			if (counter > 1)
			{
				autoFileName = FilenameUtils.getBaseName(originalFileName)
						+ new Integer(counter) + "." //$NON-NLS-1$
						+ FilenameUtils.getExtension(originalFileName);
			}
			else
			{
				autoFileName = originalFileName;
			}

			IResource res = dir.findMember(autoFileName);
			if (res == null)
				return autoFileName;

			counter++;
		}
	}

	public void storeSettings()
	{
		if (selectedTemplateFile != null)
			settings.put(settingOfficeTemplateKey,selectedTemplateFile);
		
		if(StringUtils.isNotEmpty(settingOfficeLetterNameKey))
		{			
			String value = comboLetterFile.getText().replaceAll("\\d","");			
			int idx = comboLetterFile.indexOf(value);
			if(idx >= 0)
				comboLetterFile.remove(idx);
			comboLetterFile.add(value,0);			
			if(comboLetterFile.getItemCount() > 10)
				comboLetterFile.remove(9);
			settings.put(settingOfficeLetterNameKey, comboLetterFile.getItems());
		}
	}

	private void updateWizard()
	{ 
		
		if (mode == OPEN_MODE)
		{
			// der Wizard verhindert eigene Selektionen			
			txtContainer.setEnabled(false);
			comboLetterFile.setEnabled(false);
			btnSelect.setEnabled(false);
			
			//comboTemplates.setEnabled(false);
		}
		
	
		setWizardPageCompleteState(true);
		controlContainerDecoration.hide();
		controlFileDecoration.hide();
		controlTemplateDecoration.hide();

		if (StringUtils.isEmpty(txtContainer.getText()))
		{
			setWizardPageCompleteState(false);
			controlContainerDecoration.show();
			return;
		}
	
		if (StringUtils.isEmpty(comboLetterFile.getText()))
		{
			if (!StringUtils.equals(txtContainer.getText(),
					TEMPORAER_CONTAINER_MARKER))
			{
				setWizardPageCompleteState(false);
				controlFileDecoration
						.setDescriptionText(Messages.LetterOfficeWizardComposite_MissingFilename);
				controlFileDecoration.show();
			}
			return;
		}		

		if (StringUtils.isEmpty(comboTemplates.getText()))
		{
			setWizardPageCompleteState(false);
			controlTemplateDecoration.show();
			return;
		}

		//if (mode == CREATE_MODE)
		//{
			if(StringUtils.equals(txtContainer.getText(), TEMPORAER_CONTAINER_MARKER))
					return;
			
			String fullFileName = getFullFilename();
			if (StringUtils.isNotEmpty(fullFileName))
			{
				if (selectedContainer.exists(new Path(fullFileName)))
				{
					setWizardPageCompleteState(false);
					controlFileDecoration
							.setDescriptionText(Messages.LetterOfficeWizardComposite_ExistingFileError);
					controlFileDecoration.show();
					return;
				}
			}
		//}
	}

	private String getFullFilename()
	{
		String fileName = comboLetterFile.getText();
		if (StringUtils.isNotEmpty(fileName))
		{
			fileName = FilenameUtils.removeExtension(fileName);

			String tmplateFile = (String) comboTemplates.getData(comboTemplates
					.getText());
			String extension = FilenameUtils.getExtension(tmplateFile);
			if (StringUtils.isNotEmpty(extension))
				fileName = fileName + "." + extension; //$NON-NLS-1$
		}

		return fileName;
	}

	private void setWizardPageCompleteState(boolean state)
	{
		if (parentWizardPage != null)
			parentWizardPage.setPageComplete(state);
	}

	@Override
	protected void checkSubclass()
	{
		// Disable the check that prevents subclassing of SWT components
	}

	public IContainer getSelectedContainer()
	{
		return selectedContainer;
	}

	public void setSelectedContainer(IContainer selectedContainer)
	{
		this.selectedContainer = selectedContainer;
		txtContainer.setText(getContainerName());
		updateWizard();
	}
	
	public void setTemporary()
	{		
		txtContainer.setText(TEMPORAER_CONTAINER_MARKER);	
		comboLetterFile.setText(""); //$NON-NLS-1$
		comboLetterFile.setEnabled(false);	
		updateWizard();
	}
	
	private void releaseTemporay()
	{
		comboLetterFile.setText(""); //$NON-NLS-1$
		comboLetterFile.setEnabled(true);		
		comboLetterFile.setText(getAutoFileName(selectedContainer,
				comboTemplates.getText()));
		updateWizard();
	}
	

	/**
	 * Setzt die Komponente in den 'OPEN_MODE'
	 * 
	 * @param selectedResource
	 */
	public void setSelectedResource(IResource selectedResource)
	{
		this.selectedResource = selectedResource;
		if (selectedResource != null)
		{
			if ((selectedResource instanceof IContainer)
					|| (selectedResource.getType() == IResource.FILE))
			{
				IContainer selectedContainer = (IContainer) ((selectedResource
						.getType() == IResource.FILE) ? selectedResource
						.getParent() : selectedResource);
				setSelectedContainer(selectedContainer);
			}

			if (selectedResource.getType() == IResource.FILE)
			{
				comboLetterFile.setText(selectedResource.getName());
			}

			mode = OPEN_MODE;
			updateWizard();
		}
	}

	private String getContainerName()
	{
		String containerName = ""; //$NON-NLS-1$
		if (selectedContainer != null)
		{
			IPath iPath = selectedContainer.getFullPath();
			containerName = new NtProject(selectedContainer.getProject())
					.getName();
			iPath = iPath.removeFirstSegments(1);
			containerName = containerName + File.separator + iPath.toString();
		}
		return containerName;
	}

	public String getSelectedFilename()
	{
		return getFullFilename();
	}

	public void setParentWizardPage(WizardPage parentWizardPage)
	{
		this.parentWizardPage = parentWizardPage;
	}

	/**
	 * Die ausgewaehlte Vorlage zurueckgeben.
	 * 
	 * @return
	 */
	public File getInUsedTemplateFile()
	{
		String templateFileName = (String) comboTemplates
				.getData(comboTemplates.getText());
		if (StringUtils.isNotEmpty(templateFileName))
		{
			File templateFile = new File(templateFileName);
			return (templateFile.exists()) ? templateFile : null;
		}
		return null;
	}

	public void setSelectedTemplate(String templateFilePath)
	{
		for(int i = 0;i < comboTemplates.getItemCount();i++)
		{
			String key = comboTemplates.getItem(i);
			String availTemplateFileName = (String) comboTemplates.getData(key);
			if(StringUtils.equals(templateFilePath, availTemplateFileName))
			{
				selectedTemplateFile = templateFilePath;
				comboTemplates.select(i);				
				break;
			}				
		}
	}
	
	public String getSelectedTemplate()
	{
		return selectedTemplateFile;
	}

	/**
	 * Rueckgabe des selektierten OfficeDokuments (OPEN_MODE)
	 * 
	 * @return
	 */
	public File getSelectedOfficeFile()
	{
		if ((selectedResource != null)
				&& (selectedResource.getType() == IResource.FILE))
			return ((IFile) selectedResource).getLocation().toFile();
		return null;
	}

	public void setBroker(IEventBroker broker)
	{
		this.broker = broker;
	}

	public String getOfficeContext()
	{
		return officeContext;
	}

	public void setOfficeContext(String officeContext)
	{
		this.officeContext = officeContext;
	}

	public String getSettingOfficeTemplateKey()
	{
		return settingOfficeTemplateKey;
	}

	public void setSettingOfficeTemplateKey(String settingOfficeTemplateKey)
	{
		this.settingOfficeTemplateKey = settingOfficeTemplateKey;
	}

	public void setSettingOfficeLetterNameKey(String settingOfficeLetterNameKey)
	{
		this.settingOfficeLetterNameKey = settingOfficeLetterNameKey;
	}
	
	

}
