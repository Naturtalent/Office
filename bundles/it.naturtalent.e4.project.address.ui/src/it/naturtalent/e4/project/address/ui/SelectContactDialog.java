package it.naturtalent.e4.project.address.ui;

import it.naturtalent.e4.kontakte.IKontakteDataFactory;
import it.naturtalent.e4.kontakte.KontakteData;
import it.naturtalent.e4.kontakte.KontakteDataModel;
import it.naturtalent.e4.kontakte.ui.parts.ContactMasterComposite;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.internal.workbench.swt.WorkbenchSWTActivator;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class SelectContactDialog extends TitleAreaDialog
{
	
	@Inject @Optional private IKontakteDataFactory kontaktDataFactory;
	@Inject @Optional private IEventBroker eventBroker;
	@Inject @Optional ESelectionService selectionService;

	private ContactMasterComposite contactMasterComposite;
	
	// das zugrundeliegende Modell
	private KontakteDataModel kontakteModel;
	
	// Dialogsettings
	private IDialogSettings dialogSettings = WorkbenchSWTActivator.getDefault().getDialogSettings();	
	public static final String CONTACTCOLLECTIONNAME_SETTINGKEY  = "contactcollectionnamekey"; //$NON-NLS-N$	
	private String kontaktenameSettingKey = CONTACTCOLLECTIONNAME_SETTINGKEY;
	public static final String PRAEFIX_CATEGORYFILTER_SETTINGS = "categoryfilter"; //$NON-NLS-1$

	// Name der verwendeten Datanbankcollection
	private String contactCollectionName = null;
	
	// die selektiereten Kontakte
	private KontakteData selectedContactData;
	
	/**
	 * @wbp.parser.constructor
	 */
	@Inject
	public SelectContactDialog()
	{
		super(Display.getDefault().getActiveShell());
	}
	
	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public SelectContactDialog(Shell parentShell)
	{
		super(parentShell);			
	}
	
	@Override
	protected void configureShell(Shell newShell)
	{	
		super.configureShell(newShell);
		newShell.setText(Messages.SelectContactDialog_ContactTitle);
	}

	/**
	 * Create contents of the dialog.
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent)
	{
		setTitle(Messages.SelectContactDialog_TitleMessage);
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setLayout(new GridLayout(1, false));
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		contactMasterComposite = new ContactMasterComposite(container, SWT.NONE);
		contactMasterComposite.getTableViewer().addDoubleClickListener(
				new IDoubleClickListener()
				{
					public void doubleClick(DoubleClickEvent event)
					{
						okPressed();
					}
				});
		contactMasterComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		contactMasterComposite.setEventBroker(eventBroker);
		contactMasterComposite.setSelectionService(selectionService);	
		
		init();
		
		return area;
	}
	
	private void init()
	{
		// Name der zuletzt benutzten Collection
		if (dialogSettings != null)
			contactCollectionName = dialogSettings.get(kontaktenameSettingKey);
		
		contactCollectionName = StringUtils
				.isNotEmpty(contactCollectionName) ? contactCollectionName
				: IKontakteDataFactory.KONTAKTEDATA_COLLECTION_NAME;

		
		if(kontaktDataFactory != null)
		{
			kontakteModel = kontaktDataFactory.createModel(contactCollectionName);
			kontakteModel.setBroker(eventBroker);
			kontakteModel.loadModel();			
			contactMasterComposite.setKontakteModel(kontakteModel);
			
			// KategorieModell
			contactMasterComposite.setCategoryModel(kontaktDataFactory
					.createContactCategoryModel(contactCollectionName));
		}
		
	}


	/**
	 * Create contents of the button bar.
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent)
	{
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize()
	{
		return new Point(600, 674);
	}
	
	/*
	 * Eine Einzelselektion speichern.
	 * 
	 * Den im Master selektierten Eintrag sichern
	 */	
	@Inject
	public void setKontakteData(@Optional @Named(IServiceConstants.ACTIVE_SELECTION)
	KontakteData kontakteData)
	{
		selectedContactData = kontakteData;
	}
	
	@Override
	protected void okPressed()
	{
		// in Dialogsettings speichern
		if (dialogSettings != null)
		{
			// Collectionname speichern 
			dialogSettings.put(kontaktenameSettingKey,
					kontakteModel.getCollectionName());
		}
		
		if(kontakteModel.isModified())
		{
			// geanderte Daten dauerhaft speichern ?
			if (MessageDialog.openQuestion(Display.getCurrent()
					.getActiveShell(), Messages.SelectContactDialog_ContactTitle, Messages.SelectContactDialog_MessageSavePersist))
			{				
				kontakteModel.saveModel();
			}
		}
		
		super.okPressed();
	}
	
	public KontakteData getSelectedContactData()
	{
		return selectedContactData;
	}

	public KontakteData[] getSelectedContacts()
	{
		return contactMasterComposite.getSelectedContacts();
	}

}
