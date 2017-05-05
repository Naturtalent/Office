package it.naturtalent.e4.office.ui;

import it.naturtalent.e4.office.ITextModuleModel;
import it.naturtalent.e4.office.TextModule;
import it.naturtalent.e4.office.TextModuleModel;
import it.naturtalent.e4.office.TextModuleTheme;
import it.naturtalent.e4.office.ui.dialogs.TextmoduleTreeContentProvider;
import it.naturtalent.e4.office.ui.dialogs.TextmoduleTreeLabelProvider;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXB;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.e4.ui.internal.workbench.swt.WorkbenchSWTActivator;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.widgets.Button;
import org.eclipse.ui.dialogs.ContainerCheckedTreeViewer;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;

public class TextModuleImportComposite extends Composite
{

	private Shell shell;
	
	private IDialogSettings dialogSettings;
	private String settingKey;
	
	private List<TextModuleTheme> addedThemes;
	private List<TextModuleTheme> updatedThemes;
	
	private ContainerCheckedTreeViewer checkboxTreeViewer;
	private Text textDetails;
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
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public TextModuleImportComposite(Composite parent, int style)
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
		
		/*
		btnPathSelect.addSelectionListener(new SelectionAdapter()
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
		});
		*/
		
		
		
		btnPathSelect.setText("ausw\u00E4hlen");
		
		checkboxTreeViewer = new ContainerCheckedTreeViewer(this, SWT.BORDER);
		checkboxTreeViewer
				.addSelectionChangedListener(new ISelectionChangedListener()
				{
					public void selectionChanged(SelectionChangedEvent event)
					{
						textDetails.setText(""); //$NON-NLS-1$
						
						IStructuredSelection selection = (IStructuredSelection)event
								.getSelection();
						if ((selection != null) && (!selection.isEmpty()))
						{
							Object obj = selection.getFirstElement();
							if (obj instanceof TextModule)
							{
								TextModule textModule = (TextModule) obj;
								textDetails.setText(textModule.getContent());
							}
						}
					}
				});
		checkboxTreeViewer.addCheckStateListener(new ICheckStateListener()
		{
			public void checkStateChanged(CheckStateChangedEvent event)
			{
				updateWidgetStatus();
			}
		});
		Tree tree = checkboxTreeViewer.getTree();	
		tree.setLinesVisible(true);
		tree.setHeaderVisible(true);		
		tree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 3, 2));

		
		TreeViewerColumn treeViewerColumn = new TreeViewerColumn(checkboxTreeViewer, SWT.NONE);
		TreeColumn trclmnModules = treeViewerColumn.getColumn();
		trclmnModules.setWidth(478);
		trclmnModules.setText(Messages.TextModuleImportComposite_trclmnNewColumn_text);
		
		checkboxTreeViewer.setLabelProvider(new TextmoduleTreeLabelProvider());
		checkboxTreeViewer.setContentProvider(new TextmoduleTreeContentProvider());
		
		
		Composite compositeButton = new Composite(this, SWT.NONE);
		compositeButton.setLayout(new FillLayout(SWT.HORIZONTAL));
		compositeButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 3, 1));
		
		btnAllSelect = new Button(compositeButton, SWT.NONE);
		btnAllSelect.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				checkboxTreeViewer.setAllChecked(true);
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
				checkboxTreeViewer.setAllChecked(false);
				updateWidgetStatus();
			}
		});
		btnNoSelect.setText(Messages.TextModuleImportComposite_btnNewButton_textFileName_1);
		
		textDetails = new Text(this, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL | SWT.MULTI);
		textDetails.setEditable(false);		
		GridData gd_textDetails = new GridData(SWT.FILL, SWT.CENTER, false, false, 3, 1);
		gd_textDetails.heightHint = 114;
		textDetails.setLayoutData(gd_textDetails);
		
		btnCheckOverwrite = new Button(this, SWT.CHECK);
		btnCheckOverwrite.setSelection(true);
		btnCheckOverwrite.setText(Messages.TextmoduleImportDialog_btnCheckButton_text);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);

		updateWidgetStatus();
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
				TextModuleModel model = (TextModuleModel) JAXB.unmarshal(fis, TextModuleModel.class);
				if(model != null)
					checkboxTreeViewer.setInput(model);
				
			} catch (Exception e)
			{
				MessageDialog.openError(shell, Messages.TextmoduleImportTitle, Messages.bind(it.naturtalent.e4.office.ui.Messages.TextmoduleImport_ERROR_message,
						srcFile.getPath()));
			}
		}
		updateWidgetStatus();
	}
	
	public void updateWidgetStatus()
	{		
		if(okButton != null)
			okButton.setEnabled(ArrayUtils.isNotEmpty(checkboxTreeViewer.getCheckedElements()));		
					
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
	
	public void okPressed(ITextModuleModel model)
	{
		String setting = comboPath.getText();
		if(StringUtils.isNotEmpty(setting))
			dialogSettings.put(settingKey, setting);
		
		addedThemes = new ArrayList<TextModuleTheme>();
		updatedThemes = new ArrayList<TextModuleTheme>();
		Object[] result = checkboxTreeViewer.getCheckedElements();
		TextModuleTheme resultTheme = null;
		for(Object obj : result)
		{			
			if(obj instanceof TextModuleTheme)
			{
				if(checkboxTreeViewer.getChecked(obj))
				{
					TextModuleTheme checkedTheme = (TextModuleTheme) obj;
					resultTheme = new TextModuleTheme();
					resultTheme.setName(checkedTheme.getName());
					checkedTheme.setModules(new ArrayList<TextModule>());
					
					// ist das Thema bereits vorhanden und ueberschreibbar
					int idx = TextModuleModel.indexOfTheme((TextModuleModel) model, checkedTheme);
					if(idx >= 0)
					{
						// Thema updaten
						if(btnCheckOverwrite.getSelection())
							updatedThemes.add(resultTheme);
					}
					else
					{
						// Thema hinzufuegen
						addedThemes.add(resultTheme);
					}
				}
				continue;
			}
			
			if(obj instanceof TextModule)
			{
				if (resultTheme != null)
					resultTheme.getModules().add((TextModule) obj);
			}
		}


	}
	
	public List<TextModuleTheme> getAddedThemes()
	{
		return addedThemes;
	}

	public List<TextModuleTheme> getUpdatedThemes()
	{
		return updatedThemes;
	}

	public TreeViewer getViewer()
	{
		return checkboxTreeViewer;
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
}
