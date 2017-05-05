package it.naturtalent.e4.project.address.ui;

import it.naturtalent.e4.kontakte.IKontakteDataFactory;
import it.naturtalent.e4.kontakte.KontakteData;
import it.naturtalent.e4.kontakte.KontakteDataModel;
import it.naturtalent.e4.project.address.AddressData;
import it.naturtalent.e4.project.address.IAddressData;

import java.text.Collator;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.PojoObservables;
import org.eclipse.core.databinding.beans.PojoProperties;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.databinding.viewers.ObservableMapLabelProvider;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;

public class ImportAddressDialog extends TitleAreaDialog
{
	/*
	 * 
	 */
	public class NameFilter extends ViewerFilter
	{
		@Override
		public boolean select(Viewer viewer, Object parentElement,
				Object element)
		{			
			if (element instanceof IAddressData)
			{
				IAddressData address = (IAddressData) element;
				if(StringUtils.isNotEmpty(stgFilter))
				{
					//System.out.println("Address "+kontakt.getAddress());
					return StringUtils.containsIgnoreCase(address.getName(), stgFilter);					
				}				
			}
			
			return true;
		}
	}
	
	/*
	 * 
	 */
	private class Sorter extends AbstractTableSorter
	{
		public Sorter(TableViewer viewer, TableViewerColumn column)
		{
			super(viewer, column);			
		}

		Collator collator = Collator.getInstance(Locale.GERMAN);
		
		@Override
		protected int doCompare(Viewer viewer, Object e1, Object e2)
		{
			String stgValue1, stgValue2;
			
			stgValue1 = ((IAddressData) e1).getName();
			stgValue2 = ((IAddressData) e2).getName();			
			if (StringUtils.isNotEmpty(stgValue1)
					&& StringUtils.isNotEmpty(stgValue2))
				return collator.compare(stgValue1, stgValue2);
			
			return 0;
		}
	}

	
	
	/*
	 * 
	 */
	private class AddressModel
	{
		private List<AddressData>addresses;

		public List<AddressData> getAddresses()
		{
			return addresses;
		}

		public void setAddresses(List<AddressData> addresses)
		{
			this.addresses = addresses;
		}
	}
	
	// Filter auf die Kontaktdaten (null = DefaultCollection)
	private String kontakteDataCollectionName = null;
	private KontakteDataModel kontakteModel;
	
	private AddressModel addressModel = new AddressModel();
	
	private List<AddressData> addresses;
	private AddressData selectedAddress;
	
	private DataBindingContext m_bindingContext;
	private Text textFilter;
	private Table table;
	
	private TableViewer tableViewer;
	private String stgFilter;

	/**
	 * Create the dialog.
	 * @param parentShell
	 */	
	public ImportAddressDialog(Shell parentShell)
	{
		super(parentShell);
	}
	
		
	@PostConstruct
	private void initWizard (IKontakteDataFactory kontakteDataFactory, IEventBroker broker)	
	{		
		if(kontakteDataFactory != null)
		{
			kontakteModel = kontakteDataFactory.createModel(kontakteDataCollectionName);
			kontakteModel.loadModel();
			List<KontakteData>kontakte = kontakteModel.getKontakteData();
			if((kontakte != null) && (!kontakte.isEmpty()))
			{
				addresses = new ArrayList<AddressData>();
				for(KontakteData kontakt : kontakte)
					addresses.add(kontakt.getAddress());				
			}			
		}
	}

	
	/**
	 * Create contents of the dialog.
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent)
	{
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setLayout(new GridLayout(1, false));
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		textFilter = new Text(container, SWT.BORDER);
		textFilter.addModifyListener(new ModifyListener()
		{
			public void modifyText(ModifyEvent e)
			{
				stgFilter = textFilter.getText();
				tableViewer.refresh();
			}
		});
		textFilter.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		tableViewer = new TableViewer(container, SWT.BORDER | SWT.FULL_SELECTION);
		tableViewer.addDoubleClickListener(new IDoubleClickListener()
		{
			public void doubleClick(DoubleClickEvent event)
			{
				IStructuredSelection selection = (IStructuredSelection) tableViewer.getSelection();
				selectedAddress = (AddressData) selection.getFirstElement();
				okPressed();
			}
		});
		tableViewer.addFilter(new NameFilter());
		table = tableViewer.getTable();

		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnName = tableViewerColumn.getColumn();
		tblclmnName.setWidth(250);
		tblclmnName.setText(Messages.KontakViewDialog_tblclmnName_text);
		new Sorter(tableViewer,tableViewerColumn);
		
		TableViewerColumn tableViewerColumn_1 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnZusatz1 = tableViewerColumn_1.getColumn();		
		tblclmnZusatz1.setWidth(150);
		tblclmnZusatz1.setText(Messages.KontakViewDialog_tblclmnZusatz1_text);
		
		TableViewerColumn tableViewerColumn_2 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnPLZ = tableViewerColumn_2.getColumn();
		tblclmnPLZ.setWidth(57);
		tblclmnPLZ.setText(Messages.KontakViewDialog_tblclmnPLZ_text);
		
		TableViewerColumn tableViewerColumn_3 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnOrt = tableViewerColumn_3.getColumn();
		tblclmnOrt.setWidth(150);
		tblclmnOrt.setText(Messages.KontakViewDialog_tblclmnOrt_text);
		
		TableColumn tblclmnStrasse = new TableColumn(table, SWT.NONE);
		tblclmnStrasse.setWidth(300);
		tblclmnStrasse.setText(Messages.KontakViewDialog_tblclmnStrasse_text);
		
		setAddresses(addresses);
		
		
		return area;
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
		return new Point(450, 544);
	}
	
	private void setAddresses(List<AddressData>addresses)
	{
		if(m_bindingContext != null)
			m_bindingContext.dispose();
		addressModel.setAddresses(addresses);
		m_bindingContext = initDataBindings();
	}
	
	public AddressData getSelectedAddress()
	{
		return selectedAddress;
	}

	@Override
	protected void okPressed()
	{
		IStructuredSelection selection = (IStructuredSelection) tableViewer.getSelection();
		selectedAddress = (AddressData) selection.getFirstElement();
		super.okPressed();
	}


	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();
		//
		ObservableListContentProvider listContentProvider = new ObservableListContentProvider();
		IObservableMap[] observeMaps = PojoObservables.observeMaps(listContentProvider.getKnownElements(), IAddressData.class, new String[]{"name", "namezusatz1", "plz", "ort", "strasse"});
		tableViewer.setLabelProvider(new ObservableMapLabelProvider(observeMaps));
		tableViewer.setContentProvider(listContentProvider);
		//
		IObservableList addressesAddressModelObserveList = PojoProperties.list("addresses").observe(addressModel);
		tableViewer.setInput(addressesAddressModelObserveList);
		//
		return bindingContext;
	}
}
