package it.naturtalent.e4.office.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.beans.BeansObservables;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.databinding.viewers.ObservableMapLabelProvider;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import it.naturtalent.e4.office.letter.OfficeLetterProfile;
import it.naturtalent.e4.office.letter.OfficeLetterProfiles;
import it.naturtalent.e4.office.ui.dialogs.OfficeProfileExportDialog;

public class OfficeProfileComposite extends Composite
{
	private DataBindingContext m_bindingContext;
	
	private OfficeLetterProfiles profiles;
	
	private Table table;
	private CheckboxTableViewer checkboxTableViewer;
	private OfficeProfileExportDialog exportDialog;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public OfficeProfileComposite(Composite parent, int style)
	{
		super(parent, style);
		setLayout(new GridLayout(1, false));
		
		checkboxTableViewer = CheckboxTableViewer.newCheckList(this, SWT.BORDER | SWT.FULL_SELECTION);
		checkboxTableViewer.addCheckStateListener(new ICheckStateListener()
		{
			public void checkStateChanged(CheckStateChangedEvent event)
			{
				if(exportDialog != null)
					exportDialog.updateStatus();
			}
		});
		table = checkboxTableViewer.getTable();
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		TableColumn tblclmnNewColumn = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn.setWidth(420);
		tblclmnNewColumn.setText("verfügbare Profile");
		m_bindingContext = initDataBindings();

	}
	
	public void setProfiles(OfficeLetterProfiles officeProfiles)
	{
		if(m_bindingContext != null)
			m_bindingContext.dispose();
		this.profiles = officeProfiles;
		m_bindingContext = initDataBindings();
	}
	
	

	@Override
	protected void checkSubclass()
	{
		// Disable the check that prevents subclassing of SWT components
	}

	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();
		//
		ObservableListContentProvider listContentProvider = new ObservableListContentProvider();
		IObservableMap observeMap = BeansObservables.observeMap(listContentProvider.getKnownElements(), OfficeLetterProfile.class, "name");
		checkboxTableViewer.setLabelProvider(new ObservableMapLabelProvider(observeMap));
		checkboxTableViewer.setContentProvider(listContentProvider);
		//
		IObservableList profilesProfilesObserveList = BeanProperties.list("profiles").observe(profiles);
		checkboxTableViewer.setInput(profilesProfilesObserveList);
		//
		return bindingContext;
	}
	
	public OfficeLetterProfiles getCheckedProfiles()
	{		
		Object[] result = checkboxTableViewer.getCheckedElements();			
		OfficeLetterProfile [] profiles = new OfficeLetterProfile [result.length];
		System.arraycopy(result, 0, profiles, 0,
				result.length);
		
		Set<OfficeLetterProfile>profileSet = new HashSet<OfficeLetterProfile>(Arrays.asList(profiles));
		OfficeLetterProfiles officeProfiles = new OfficeLetterProfiles();		
		officeProfiles.setProfiles(new ArrayList<OfficeLetterProfile>(profileSet));
		return officeProfiles;
	}

	public CheckboxTableViewer getViewer()
	{
		return checkboxTableViewer;
	}

	public void setExportDialog(OfficeProfileExportDialog exportDialog)
	{
		this.exportDialog = exportDialog;
	}


	
	
}
