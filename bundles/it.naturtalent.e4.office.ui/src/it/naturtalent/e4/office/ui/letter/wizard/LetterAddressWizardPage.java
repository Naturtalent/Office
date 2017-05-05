package it.naturtalent.e4.office.ui.letter.wizard;

import it.naturtalent.e4.kontakte.KontakteData;
import it.naturtalent.e4.office.letter.OfficeLetterModel;
import it.naturtalent.e4.project.IProjectDataAdapter;
import it.naturtalent.e4.project.IProjectDataFactory;
import it.naturtalent.e4.project.IResourceNavigator;
import it.naturtalent.e4.project.NtProject;
import it.naturtalent.e4.project.ProjectDataAdapterRegistry;
import it.naturtalent.e4.project.address.AddressData;
import it.naturtalent.e4.project.address.Addresses;
import it.naturtalent.e4.project.address.ui.AddressDataComposite;
import it.naturtalent.e4.project.address.ui.ImportAddressDialog;
import it.naturtalent.e4.project.address.ui.SelectContactDialog;
import it.naturtalent.e4.project.ui.navigator.ResourceNavigator;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ImageHyperlink;
import org.eclipse.wb.swt.SWTResourceManager;

import it.naturtalent.e4.office.ui.Messages;

public class LetterAddressWizardPage extends WizardPage
{
	private final FormToolkit formToolkit = new FormToolkit(Display.getDefault());

	private AddressDataComposite addressDataComposite;
	
	private NtProject ntProject;
	
	private List<AddressData>projectAddresses;
	
	private IEclipseContext context;
	
	private OfficeLetterModel wizardOfficeModel;
	
	/**
	 * Create the wizard.
	 */
	public LetterAddressWizardPage()
	{
		super("wizardPage");
		setTitle("Adresse");
		setDescription("Addressdaten definieren");
	}
	
	@PostConstruct
	private void postConstruct(@Optional
	EPartService ePartService, @Optional
	IEclipseContext context, @Optional
	IProjectDataFactory projectDataFactory)
	{
		wizardOfficeModel = context.get(OfficeLetterModel.class);
		
		// ist ein Zielcontainer im ResourceNavigator selektiert
		MPart mPart = ePartService.findPart(ResourceNavigator.RESOURCE_NAVIGATOR_ID);
		IResourceNavigator resourceNavigator = (IResourceNavigator) mPart.getObject();		
		IStructuredSelection selection = (IStructuredSelection) resourceNavigator
				.getViewer().getSelection();
		
		Object selObj = selection.getFirstElement();
		if (selObj instanceof IResource)
		{
			IProject iProject = ((IResource)selObj).getProject();
			ntProject = new NtProject(iProject);

			// Projektadresse uebernehmen
			IProjectDataAdapter projectAdapter = ProjectDataAdapterRegistry
					.getProjectDataAdapter(Addresses.PROP_ADDRESSES);
			Addresses addresses = (Addresses) projectDataFactory
					.readProjectData(projectAdapter, ntProject);
			if (addresses != null)
			{
				projectAddresses = addresses.getAdressDatas();
				if(!projectAddresses.isEmpty())
					wizardOfficeModel.setAddress(projectAddresses.get(0));
			}
		}
		
		this.context = context;
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
		container.setLayout(new GridLayout(1, false));
		
		addressDataComposite = new AddressDataComposite(container, SWT.NONE);
		addressDataComposite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		ImageHyperlink mghprlnkProjectAddress = formToolkit.createImageHyperlink(container, SWT.NONE);
		mghprlnkProjectAddress.addHyperlinkListener(new HyperlinkAdapter()
		{
			public void linkActivated(HyperlinkEvent e)
			{
				if(!projectAddresses.isEmpty())
					wizardOfficeModel.setAddress(projectAddresses.get(0));
			}
		});
		mghprlnkProjectAddress.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		formToolkit.paintBordersFor(mghprlnkProjectAddress);
		mghprlnkProjectAddress.setText(Messages.LetterAddressWizardPage_mghprlnkProjectAddress_text);
		mghprlnkProjectAddress.setEnabled((projectAddresses != null) && (!projectAddresses.isEmpty()));
		
		ImageHyperlink mghprlnkDB = formToolkit.createImageHyperlink(container, SWT.NONE);
		mghprlnkDB.addHyperlinkListener(new HyperlinkAdapter()
		{
			public void linkActivated(HyperlinkEvent e)
			{				
				SelectContactDialog dialog = ContextInjectionFactory.make(SelectContactDialog.class, context); 
				if(dialog.open() == SelectContactDialog.OK)
				{
					KontakteData selectedKontakt = dialog.getSelectedContacts()[0];
					if(selectedKontakt != null)
					{
						AddressData address = selectedKontakt.getAddress();
						addressDataComposite.setAddressData(address);
						wizardOfficeModel.setAddress(address);
					}
				}
			}
		});
		mghprlnkDB.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		formToolkit.paintBordersFor(mghprlnkDB);
		mghprlnkDB.setText(Messages.LetterAddressWizardPage_mghprlnkDB_text);
		
		init();
	}
	
	private void init()
	{	
		AddressData addressData = new AddressData();
		
		Object wizardObj = getWizard();
		if (getWizard() instanceof OpenLetterWizard)
		{
			// OpenWithWizard - Adresse aus dem Dokument holen
			OpenLetterWizard wizard = (OpenLetterWizard) wizardObj;
			OfficeLetterModel model = wizard.getModel();
			if(model != null)
			{
				// Adresse aus dem Dokument uebernehmen
				projectAddresses = new ArrayList<AddressData>();
				projectAddresses.add(model.getAddress());
			}
		}

		/*
		if((projectAddresses != null) && (!projectAddresses.isEmpty()))
		{
			wizardOfficeModel.setAddress(projectAddresses.get(0));
			addressDataComposite.setAddressData(projectAddresses.get(0));
		}
		*/

		if((projectAddresses != null) && (!projectAddresses.isEmpty()))
			addressData = projectAddresses.get(0);
		
		wizardOfficeModel.setAddress(addressData);
		addressDataComposite.setAddressData(addressData);

	}
	
	/*
	@Override
	public void dispose()
	{
		addressDataComposite.dispose();
		SWTResourceManager.disposeColors();
		SWTResourceManager.disposeImages();
		super.dispose();
	}
	*/

	public String [][] toAddressTable()
	{
		String [][] lines = null;
		
		AddressData addressData = addressDataComposite.getAddessData();
		if(addressData != null)
		{			
			if(addressData.getType() == AddressData.TYPE_PRIVATE)
			{
				lines = ArrayUtils.add(lines, new String []{addressData.getAnrede()});
				lines = ArrayUtils.add(lines, new String []{addressData.getNamezusatz1()+" "+addressData.getName()});
			}
			else
			{
				lines = ArrayUtils.add(lines, new String []{addressData.getName()});
				lines = ArrayUtils.add(lines, new String []{addressData.getNamezusatz1()});				
			}
			
			if(StringUtils.isNotEmpty(addressData.getNamezusatz2()))
				lines = ArrayUtils.add(lines, new String []{addressData.getNamezusatz2()});
			
			lines = ArrayUtils.add(lines, new String []{addressData.getStrasse()});
			lines = ArrayUtils.add(lines, new String []{addressData.getPlz()+" "+addressData.getOrt()});
		}
		
		return lines;
	}

}
