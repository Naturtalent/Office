package it.naturtalent.e4.office.ui;

import it.naturtalent.e4.office.letter.OfficeLetterProfile;
import it.naturtalent.e4.office.letter.OfficeLetterProfiles;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXB;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.beans.BeansObservables;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.e4.ui.internal.workbench.swt.WorkbenchSWTActivator;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.databinding.viewers.ObservableMapLabelProvider;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

public class OfficeProfileImportComposite extends Composite
{
	private DataBindingContext m_bindingContext;

	private Shell shell;
	private OfficeLetterProfiles profiles;
	private OfficeLetterProfiles resultProfiles;
	private boolean overwriteFlag;
	
	private IDialogSettings dialogSettings;
	private String settingKey;
	private CCombo comboPath;
	private Button btnAllSelect;
	private Button btnNoSelect;
	private Button btnCheckOverwrite;
	private Button btnPathSelect;
	
	private Button okButton;

	private String importFileFilter = "*.xml";
	
	private SelectionListener btnPathSelectListener = new SelectionAdapter()
	{
		@Override
		public void widgetSelected(SelectionEvent e)
		{
			FileDialog dlg = new FileDialog(getShell());

			// Change the title bar text
			dlg.setText("Importverzeichnis");

			dlg.setFileName(FilenameUtils.getBaseName(comboPath.getText()));
			dlg.setFilterExtensions(new String[]{ importFileFilter });
			dlg.setFilterPath(FilenameUtils.getFullPath(comboPath.getText()));				

			String importFile = dlg.open();
			if (importFile != null)
			{					
				comboPath.setText(importFile);
				loadImportSources();					
			}

			updateWidgetStatus();
		}		
	};
	private Table table;
	private CheckboxTableViewer tableViewer;
	private TableColumn tblclmnProfile;
	private TableViewerColumn tableViewerColumn;
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public OfficeProfileImportComposite(Composite parent, int style)
	{
		super(parent, style);
		this.shell = parent.getShell();
		setLayout(new GridLayout(3, false));
		
		Composite compositePath = new Composite(this, SWT.NONE);
		compositePath.setLayout(new GridLayout(3, false));
		compositePath.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		
		Label lblFile = new Label(compositePath, SWT.NONE);
		lblFile.setSize(208, 13);
		lblFile.setText("Dateiname");
		
		comboPath = new CCombo(compositePath, SWT.BORDER);
		comboPath.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		comboPath.setEditable(false);
		comboPath.setBounds(0, 0, 31, 21);
		
		btnPathSelect = new Button(compositePath, SWT.NONE);
		btnPathSelect.addSelectionListener(btnPathSelectListener);
		
	
		btnPathSelect.setText("ausw\u00E4hlen");
		
		tableViewer =  CheckboxTableViewer.newCheckList(compositePath, SWT.BORDER | SWT.FULL_SELECTION);
		tableViewer.addCheckStateListener(new ICheckStateListener()
		{
			public void checkStateChanged(CheckStateChangedEvent event)
			{
				updateWidgetStatus();
			}
		});
		table = tableViewer.getTable();
		table.setHeaderVisible(true);
		GridData gd_table = new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1);
		gd_table.heightHint = 165;
		table.setLayoutData(gd_table);
		
		tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		tblclmnProfile = tableViewerColumn.getColumn();
		tblclmnProfile.setWidth(343);
		tblclmnProfile.setText(Messages.OfficeProfileImportComposite_tblclmnProfile_text);
		
		Composite compositeButton = new Composite(this, SWT.NONE);
		compositeButton.setLayout(new FillLayout(SWT.HORIZONTAL));
		compositeButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 3, 1));
		
		btnAllSelect = new Button(compositeButton, SWT.NONE);
		btnAllSelect.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				tableViewer.setAllChecked(true);
				updateWidgetStatus();
			}
		});
		btnAllSelect.setText(Messages.TextModuleImportComposite_btnNewButton_textFileName_2);
		
		btnNoSelect = new Button(compositeButton, SWT.NONE);
		btnNoSelect.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				tableViewer.setAllChecked(false);
				updateWidgetStatus();
			}
		});
		btnNoSelect.setText(Messages.TextModuleImportComposite_btnNewButton_textFileName_1);
		
		btnCheckOverwrite = new Button(this, SWT.CHECK);
		btnCheckOverwrite.setSelection(true);
		btnCheckOverwrite.setText("evtl. vorhandene Profile ersetzen");
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);

		updateWidgetStatus();
		m_bindingContext = initDataBindings();
	}

	public void init(String settingKey)
	{
		dialogSettings = WorkbenchSWTActivator.getDefault().getDialogSettings();
		String setting = dialogSettings.get(this.settingKey = settingKey);
				
		if (StringUtils.isNotEmpty(setting))
		{
			comboPath.setText(setting);
			loadImportSources();
		}
	}
	
	private void loadImportSources()
	{				
		File srcFile = new File(comboPath.getText());
		if(srcFile.exists())
		{
			try
			{
				FileInputStream fis = new FileInputStream(srcFile);
				OfficeLetterProfiles profiles = (OfficeLetterProfiles) JAXB.unmarshal(fis, OfficeLetterProfiles.class);
				if(profiles != null)
					setProfiles(profiles);

				
			} catch (Exception e)
			{
				MessageDialog.openError(shell, Messages.TextmoduleImportTitle, Messages.bind(it.naturtalent.e4.office.ui.Messages.TextmoduleImport_ERROR_message,
						srcFile.getPath()));
			}
		}
		updateWidgetStatus();
	}
	
	private void setProfiles(OfficeLetterProfiles profiles)
	{
		if(m_bindingContext != null)
			m_bindingContext.dispose();
		
		this.profiles = profiles;	
			m_bindingContext = initDataBindings();			
	}
	
	public void updateWidgetStatus()
	{		
		if(okButton != null)
			okButton.setEnabled(ArrayUtils.isNotEmpty(tableViewer.getCheckedElements()));		
					
		//btnAllSelect.setEnabled(checkboxTreeViewer.getTree().getItemCount() > 0);
		//btnNoSelect.setEnabled(checkboxTreeViewer.getTree().getItemCount() > 0);
		
	}
	
	public void setImportFileFilter(String importFileFilter)
	{
		this.importFileFilter = importFileFilter;
	}

	public void setOkButton(Button okButton)
	{
		this.okButton = okButton;
	}
	
	public void okPressed()
	{
		String setting = comboPath.getText();
		if(StringUtils.isNotEmpty(setting) && StringUtils.isNotEmpty(settingKey))
			dialogSettings.put(settingKey, setting);
		
		Object[] result = tableViewer.getCheckedElements();
		List<OfficeLetterProfile>profiles = new ArrayList<OfficeLetterProfile>(); 
		for(Object obj : result)		
			profiles.add((OfficeLetterProfile) obj);

		resultProfiles = new OfficeLetterProfiles();
		resultProfiles.setProfiles(profiles);
		
		overwriteFlag = btnCheckOverwrite.getSelection();
	}
	
	public OfficeLetterProfiles getImportedProfiles()
	{
		return resultProfiles;
	}
	
	public boolean isOverwriteFlag()
	{
		return overwriteFlag;
	}

	public TableViewer getViewer()
	{
		return tableViewer;
	}	
	
	public CCombo getComboPath()
	{
		return comboPath;
	}
	

	public void setFileSelectListener(SelectionListener selectionListener)
	{
		btnPathSelect.removeSelectionListener(btnPathSelectListener);
		btnPathSelect.addSelectionListener(selectionListener);
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
		tableViewer.setLabelProvider(new ObservableMapLabelProvider(observeMap));
		tableViewer.setContentProvider(listContentProvider);
		//
		IObservableList profilesProfilesObserveList = BeanProperties.list("profiles").observe(profiles);
		tableViewer.setInput(profilesProfilesObserveList);
		//
		return bindingContext;
	}
}
