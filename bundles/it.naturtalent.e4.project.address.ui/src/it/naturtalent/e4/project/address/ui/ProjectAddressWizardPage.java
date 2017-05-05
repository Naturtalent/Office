package it.naturtalent.e4.project.address.ui;

import it.naturtalent.e4.kontakte.IKontakteDataFactory;
import it.naturtalent.e4.kontakte.KontakteData;
import it.naturtalent.e4.project.IProjectDataAdapter;
import it.naturtalent.e4.project.ProjectDataAdapterRegistry;
import it.naturtalent.e4.project.address.Activator;
import it.naturtalent.e4.project.address.AddressData;
import it.naturtalent.e4.project.address.AddressUtils;
import it.naturtalent.e4.project.address.Addresses;
import it.naturtalent.e4.project.address.ui.actions.AddAddressAction;
import it.naturtalent.e4.project.address.ui.actions.DeleteAddressAction;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.lang3.ArrayUtils;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.beans.BeansObservables;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.databinding.viewers.ObservableMapLabelProvider;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ImageHyperlink;
import org.eclipse.ui.forms.widgets.Section;
//import it.naturtalent.e4.kontakte.AddressData;
import org.eclipse.wb.swt.SWTResourceManager;

public class ProjectAddressWizardPage extends WizardPage
{
	private DataBindingContext m_bindingContext;
	private final FormToolkit formToolkit = new FormToolkit(Display.getDefault());
	private Table table;
	private AddressDataComposite addressDataComposite;
	private ImageHyperlink mghprlnkImportAddress;
	
	private TableViewer tableViewer;
	//private Button btnDelete;
	
	private AddAddressAction addAction;
	private DeleteAddressAction deleteAction;
	
	private Addresses addresses = new Addresses();
	
	//private Shell shell;
	
	private IEclipseContext context;
	//private IKontakteDataFactory kontakteDataFactory;
	//private String kontakteCollectionName = null;
	//private KontakteDataModel kontakteModel;
	//private EPartService partService;
	//private MApplication application;
	
	private Action copyAdressAction = new Action()
	{
		@Override
		public void run()
		{
			Clipboard clipboard = new Clipboard(Display.getDefault());
			clipboard.setContents(new Object[]
					{ AddressUtils.createClipboardText(addressDataComposite.getAddessData()) }, new Transfer[]
					{ TextTransfer.getInstance() });		
			clipboard.dispose();					
		}		
	};
	{
		Image image = SWTResourceManager.getImage(this.getClass(), "/icons/full/etool16/copy_edit.gif"); //$NON-NLS-1$;
		copyAdressAction.setImageDescriptor(ImageDescriptor.createFromImage(image));
		copyAdressAction.setToolTipText("Adresse in Zwischablage kopieren");
		copyAdressAction.setEnabled(true);
	}

	/**
	 * Create the wizard.
	 */
	public ProjectAddressWizardPage()
	{
		super("wizardPage"); //$NON-NLS-1$
		setTitle(Messages.CustomerPage_Title); 
		setDescription(Messages.CustomerPage_Description); 
	}
		
	@PostConstruct
	public void initWizard(IEclipseContext context,
			IKontakteDataFactory kontakteDataFactory, EPartService partService, MApplication application)
	{
		this.context = context;
		
		/*
		if(kontakteDataFactory != null)
			kontakteModel = kontakteDataFactory.createModel(kontakteCollectionName);
			*/
		
		//this.partService = partService;
		//this.application = application;
		context.set(ProjectAddressWizardPage.class, this);		
	}

	/**
	 * Create contents of the wizard.
	 * @param parent
	 */
	public void createControl(Composite parent)
	{
		//this.shell = parent.getShell();
		Composite container = new Composite(parent, SWT.NULL);

		setControl(container);
		container.setLayout(new FillLayout(SWT.VERTICAL));
		
		Composite composite = formToolkit.createComposite(container, SWT.NONE);
		formToolkit.paintBordersFor(composite);
		composite.setLayout(new GridLayout(2, false));
		
		// Section Liste der Adressen
		Section sctnAddresses = formToolkit.createSection(composite, Section.TITLE_BAR);
		sctnAddresses.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 2));
		formToolkit.paintBordersFor(sctnAddresses);
		sctnAddresses.setText(Messages.ProjectAddressWizardPage_sctnAddresses_text);
		
		Label lblNewLabel = formToolkit.createLabel(sctnAddresses, Messages.ProjectAddressWizardPage_lblNewLabel_text, SWT.NONE);
		sctnAddresses.setDescriptionControl(lblNewLabel);
		
		Composite compositeAddresses = formToolkit.createComposite(sctnAddresses, SWT.NONE);
		formToolkit.paintBordersFor(compositeAddresses);
		sctnAddresses.setClient(compositeAddresses);
		compositeAddresses.setLayout(new GridLayout(1, false));
		
		tableViewer = new TableViewer(compositeAddresses, SWT.BORDER | SWT.FULL_SELECTION);
		tableViewer.addSelectionChangedListener(new ISelectionChangedListener()
		{
			// Tabelleneintrag selektiert
			public void selectionChanged(SelectionChangedEvent event)
			{
				IStructuredSelection selection = (IStructuredSelection) event.getSelection();
				Object selObj = selection.getFirstElement();
				if (selObj instanceof AddressData)
				{
					AddressData addressData = (AddressData) selObj;
					addressDataComposite.setAddressData(addressData);						
				}
				else addressDataComposite.setAddressData(null);
				updateWidgets();
			}
		});
		table = tableViewer.getTable();
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		formToolkit.paintBordersFor(table);
		
		TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnName = tableViewerColumn.getColumn();
		tblclmnName.setWidth(250);
		tblclmnName.setText(Messages.CustomerPage_tblclmnName_text);
		
		TableViewerColumn tableViewerColumn_1 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnPlz = tableViewerColumn_1.getColumn();
		tblclmnPlz.setResizable(false);
		tblclmnPlz.setWidth(60);
		tblclmnPlz.setText(Messages.ProjectAddressWizardPage_tblclmnPlz_text);
		
		createSectionToolbar(sctnAddresses);
		
		// Section Adressendetails
		Section sctnAddressDetails = formToolkit.createSection(composite, Section.TITLE_BAR);
		sctnAddressDetails.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		formToolkit.paintBordersFor(sctnAddressDetails);
		sctnAddressDetails.setText(Messages.CustomerPage_sctnAddressDetails_text);
		
		createAdressSectionToolbar(sctnAddressDetails);
		
		addressDataComposite = new AddressDataComposite(sctnAddressDetails, SWT.NONE);
		formToolkit.adapt(addressDataComposite);
		formToolkit.paintBordersFor(addressDataComposite);
		sctnAddressDetails.setClient(addressDataComposite);	
		ContextInjectionFactory.invoke(addressDataComposite, PostConstruct.class, context);
		
		Composite compositeButton = new Composite(composite, SWT.NONE);
		formToolkit.adapt(compositeButton);
		formToolkit.paintBordersFor(compositeButton);
		compositeButton.setLayout(new GridLayout(2, false));
		
		Label lblNewLabel_1 = new Label(compositeButton, SWT.NONE);
		formToolkit.adapt(lblNewLabel_1, true, true);		
		
		mghprlnkImportAddress = formToolkit.createImageHyperlink(compositeButton, SWT.NONE);
		mghprlnkImportAddress.setToolTipText(Messages.ProjectAddressWizardPage_mghprlnkImportAddress_toolTipText);
		mghprlnkImportAddress.addHyperlinkListener(new HyperlinkAdapter()
		{
			public void linkActivated(HyperlinkEvent e)
			{						
				SelectContactDialog dialog = ContextInjectionFactory.make(SelectContactDialog.class, context); 
				if(dialog.open() == SelectContactDialog.OK)
				{
					KontakteData[] selectedContacts = dialog.getSelectedContacts();
					if(ArrayUtils.isNotEmpty(selectedContacts))
					{
						for(KontakteData data : selectedContacts)
						{
							AddressData newAddressData = new AddressData();			
							newAddressData = AddressData.copy(newAddressData, data.getAddress());
												
							// in das Modell uebernehmen
							addresses.getAdressDatas().add(newAddressData);
							setAddresses(addresses);
							
							// in der Tabelle selektieren
							tableViewer.setSelection(new StructuredSelection(newAddressData));
							updateWidgets();							
						}
					}
				}
			}
		});
		formToolkit.paintBordersFor(mghprlnkImportAddress);
		mghprlnkImportAddress.setText(Messages.ProjectAddressWizardPage_mghprlnkImportAddress_text);

		
		init();
		updateWidgets();
		
		m_bindingContext = initDataBindings();
	}
	
	private void createSectionToolbar(Section section)
	{
		ToolBarManager toolBarManager = new ToolBarManager(SWT.FLAT);
		ToolBar toolbar = toolBarManager.createControl(section);
		final Cursor handCursor = new Cursor(Display.getCurrent(), SWT.CURSOR_HAND);
		toolbar.setCursor(handCursor);
		// Cursor needs to be explicitly disposed
		toolbar.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				if (handCursor.isDisposed() == false) {
					handCursor.dispose();
				}
			}
		});

		if (context != null)
		{
			addAction = ContextInjectionFactory.make(
					AddAddressAction.class, context);
			toolBarManager.add(addAction);

			deleteAction = ContextInjectionFactory.make(
					DeleteAddressAction.class, context);
			toolBarManager.add(deleteAction);
		}		
				
		toolBarManager.update(true);
		section.setTextClient(toolbar);
	}
	
	private void createAdressSectionToolbar(Section section)
	{
		ToolBarManager toolBarManager = new ToolBarManager(SWT.FLAT);
		ToolBar toolbar = toolBarManager.createControl(section);
		final Cursor handCursor = new Cursor(Display.getCurrent(), SWT.CURSOR_HAND);
		toolbar.setCursor(handCursor);
		// Cursor needs to be explicitly disposed
		toolbar.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				if (handCursor.isDisposed() == false) {
					handCursor.dispose();
				}
			}
		});
		toolBarManager.add(copyAdressAction);				
		toolBarManager.update(true);
		section.setTextClient(toolbar);
	}
	
	private void init()
	{
		// die im Adapter gespeicherten Daten uebeergeben
		IProjectDataAdapter adapter = ProjectDataAdapterRegistry.getProjectDataAdapter(Addresses.PROP_ADDRESSES);		
		setAddresses((Addresses) adapter.getProjectData());
		
		// ersten Tabelleneintrag selektieren
		if(tableViewer.getTable().getItemCount() > 0)
			tableViewer.setSelection(new StructuredSelection(tableViewer.getElementAt(0)));			
	}
	
	/*
	private IKontakteData existKontakt(String name)
	{
		if((kontakteModel != null) && (StringUtils.isNotEmpty(name)))
		{
			List<KontakteData>kontakte = kontakteModel.getKontakteData();
			for(IKontakteData kontaktData : kontakte)
			{
				IAddressData address = kontaktData.getAddress();
				if(StringUtils.equals(address.getName(), name))
					return kontaktData;
			}
		}
		
		return null;
	}
	*/

	private void copyAddress(AddressData address)
	{
		Clipboard clipboard = new Clipboard(Display.getDefault());
		clipboard.setContents(new Object[]
				{ AddressUtils.createClipboardText(address) }, new Transfer[]
				{ TextTransfer.getInstance() });		
		clipboard.dispose();		
	}
	
	@Override
	public void dispose()
	{
		ContextInjectionFactory.invoke(addressDataComposite, PreDestroy.class, context);
		context.remove(ProjectAddressWizardPage.class);
		super.dispose();
	}

	public void setAddresses(Addresses addresses)
	{
		if(m_bindingContext != null)
			m_bindingContext.dispose();
		
		this.addresses = (addresses == null) ? new Addresses() : addresses;
		m_bindingContext = initDataBindings();			
	}
	
	public Addresses getAddresses()
	{
		return addresses;
	}
	
	public TableViewer getTableViewer()
	{
		return tableViewer;
	}


	public void updateWidgets()
	{
		boolean isSelected = table.getSelectionCount() > 0;		
		addressDataComposite.setEnabled(isSelected);
		deleteAction.setEnabled(isSelected);
		copyAdressAction.setEnabled(isSelected);
		
		IStructuredSelection selection = (IStructuredSelection) tableViewer
				.getSelection();
		AddressData address = (AddressData) selection
				.getFirstElement();
		
		// page complete
		/*
		if(table.getItemCount() == 0)
		{			
			setPageComplete(true);
			return;
		}	
		setPageComplete(addressDataComposite.validate());
		*/
		
		
	}
	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();
		//
		ObservableListContentProvider listContentProvider = new ObservableListContentProvider();
		IObservableMap[] observeMaps = BeansObservables.observeMaps(listContentProvider.getKnownElements(), AddressData.class, new String[]{"name", "namezusatz1"});
		tableViewer.setLabelProvider(new ObservableMapLabelProvider(observeMaps));
		tableViewer.setContentProvider(listContentProvider);
		//
		IObservableList adressDatasAddressDataObserveList = BeanProperties.list("adressDatas").observe(addresses);
		tableViewer.setInput(adressDatasAddressDataObserveList);
		//
		return bindingContext;
	}
}
