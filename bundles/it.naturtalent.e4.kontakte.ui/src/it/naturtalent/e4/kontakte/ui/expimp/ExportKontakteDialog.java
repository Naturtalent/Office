package it.naturtalent.e4.kontakte.ui.expimp;

import it.naturtalent.e4.kontakte.IKontakteDataFactory;
import it.naturtalent.e4.kontakte.KontakteData;
import it.naturtalent.e4.kontakte.KontakteDataModel;
import it.naturtalent.e4.kontakte.ui.Activator;
import it.naturtalent.e4.kontakte.ui.Messages;
import it.naturtalent.icons.core.Icon;
import it.naturtalent.icons.core.IconSize;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

import javax.inject.Inject;
import javax.xml.bind.JAXB;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.beans.BeansObservables;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.internal.workbench.swt.WorkbenchSWTActivator;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.databinding.viewers.ObservableMapLabelProvider;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;


public class ExportKontakteDialog extends TitleAreaDialog implements EventHandler
{
	private DataBindingContext m_bindingContext;

	@Inject @Optional private IKontakteDataFactory kontakDataFactory;	
	protected String kontakteCollectionName = null;
	private KontakteDataModel kontaktModel;
	
	public static final String KONTAKTE_EXPORTPATH_SETTING = "kontaktexportpath"; //$NON-NLS-N$ 
	private String exportSettingKey = KONTAKTE_EXPORTPATH_SETTING;
	
	private IDialogSettings dialogSettings;
	
	private Button okButton;
	
	private CheckboxTableViewer tableViewer;
	
	private ExportDestinationComposite exportDestinationComposite;
	
	private String exportPath;
	
	
	@Inject
	@Optional
	private static Shell shell;
	
	@Inject
	@Optional
	private IEventBroker eventBroker;
	
	
	private Table table;
	private Composite compositeButton;
	private Button btnSelectAll;
	private Button btnNoSelect;
	private TableColumn tblclmnName;
	
	/**
	 * Create the dialog.
	 * @param parentShell
	 * @wbp.parser.constructor
	 */
	public ExportKontakteDialog()
	{
		super(shell);
	}
		
	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public ExportKontakteDialog(Shell parentShell)
	{
		super(parentShell);		
	}

	/**
	 * Create contents of the dialog.
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent)
	{		
		setTitleImage(Icon.WIZBAN_EXPORT.getImage(IconSize._75x66_TitleDialogIconSize));
		setTitle(Messages.ExportKontankteDialog_this_title);
		setMessage(Messages.ExportKontankteDialog_this_message);
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setLayout(new GridLayout(1, false));
		GridData gd_container = new GridData(GridData.FILL_BOTH);
		gd_container.verticalAlignment = SWT.TOP;
		container.setLayoutData(gd_container);
		
		exportDestinationComposite = new ExportDestinationComposite(container, SWT.NONE);
		exportDestinationComposite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));	
		
		tableViewer = CheckboxTableViewer.newCheckList(container, SWT.BORDER | SWT.FULL_SELECTION);
		tableViewer.addCheckStateListener(new ICheckStateListener()
		{
			public void checkStateChanged(CheckStateChangedEvent event)
			{
				update();
			}
		});
		
		table = tableViewer.getTable();
		GridData gd_table = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_table.heightHint = 432;
		table.setLayoutData(gd_table);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		tblclmnName = new TableColumn(table, SWT.NONE);
		tblclmnName.setWidth(400);
		tblclmnName.setText(Messages.ExportKontankteDialog_tblclmnName_text);
		
		
		compositeButton = new Composite(container, SWT.NONE);
		compositeButton.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		btnSelectAll = new Button(compositeButton, SWT.NONE);
		btnSelectAll.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				tableViewer.setAllChecked(true);
				update();
			}
		});
		btnSelectAll.setText(Messages.ExportKontankteDialog_btnSelectAll_text);
		
		btnNoSelect = new Button(compositeButton, SWT.NONE);
		btnNoSelect.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				tableViewer.setAllChecked(false);
				update();
			}
		});
		btnNoSelect.setText(Messages.ExportKontankteDialog_btnNoSelect_text);
		
		// ab jetzt ueberwacht der Broker Eingaben in 'ExportDestinationComposite'
		eventBroker.subscribe(ExportDestinationComposite.KONTAKT_EXPORTDESTINATION_EVENT, this);		
		exportDestinationComposite.setEventBroker(eventBroker);
				
		return area;
	}
	
	public void init(String kontakteCollectionName, String exportSettingKey)
	{
		this.kontakteCollectionName = kontakteCollectionName;
		this.exportSettingKey = StringUtils.isEmpty(exportSettingKey) ? KONTAKTE_EXPORTPATH_SETTING : exportSettingKey;
		
		dialogSettings= WorkbenchSWTActivator.getDefault().getDialogSettings();		
		if (dialogSettings != null)
		{
			exportPath = dialogSettings.get(this.exportSettingKey);
			if (StringUtils.isNotEmpty(exportPath))			
				exportDestinationComposite.setExportPath(exportPath);
		}	
			
		// Kontakte laden und anzeigen
		if(kontakDataFactory != null)	
		{
			kontaktModel = kontakDataFactory.createModel(kontakteCollectionName);
			kontaktModel.loadModel();
			m_bindingContext = initDataBindings();
		}
		
	}
	
	private void update()
	{
		if(tableViewer.getCheckedElements().length == 0)
		{
			okButton.setEnabled(false);
			return;
		}
		okButton.setEnabled(StringUtils.isNotEmpty(exportPath));
	}
	
	@Override
	public void handleEvent(Event event)
	{
		exportPath = (String) event.getProperty(IEventBroker.DATA);
		update();
	}

	/**
	 * Create contents of the button bar.
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent)
	{
		okButton = createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				true);
		okButton.setEnabled(false);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);		
		m_bindingContext = initDataBindings();
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize()
	{
		return new Point(450, 727);
	}

	@Override
	protected void okPressed()
	{
		if ((dialogSettings != null) && (StringUtils.isNotEmpty(exportPath)))
			dialogSettings.put(exportSettingKey, exportPath);
		
		Object[] result = tableViewer.getCheckedElements();
		if(ArrayUtils.isNotEmpty(result))
		{
			KontakteData [] data = new KontakteData[result.length];
				System.arraycopy(result, 0, data, 0,result.length);
				
			if(doExportKontakte(data))
				super.okPressed();
		}
	}
			
	private boolean doExportKontakte(final KontakteData [] checkedData)
	{		
		final File expFile = new File(exportPath);
		if(expFile.exists())
		{
			if(!MessageDialog.openQuestion(getShell(), Messages.ExportKontakteDialog_OverwriteLabel, Messages.ExportKontakteDialog_OverwriteMessage))
					return false;
		}
		
		Job j = new Job("Save Job") //$NON-NLS-1$
		{
			@Override
			protected IStatus run(final IProgressMonitor monitor)
			{				
				KontakteDataModel dataModel = new KontakteDataModel();
				dataModel.setKontakteData(Arrays.asList(checkedData));						
				ByteArrayOutputStream out = new ByteArrayOutputStream();

				JAXBKontakteModel jaxbModel = new JAXBKontakteModel();
				jaxbModel.setKontakte(dataModel.getKontakteData());
				JAXB.marshal(jaxbModel, out);
				
				FileOutputStream fos = null;
				try
				{
					fos = new FileOutputStream(expFile);
					fos.write(out.toByteArray());
				} catch (Exception e)
				{
					MultiStatus info = new MultiStatus(Activator.PLUGIN_ID, 1, Messages.bind(Messages.ExportKontakteDialog_ErrorExport, exportPath), null);
					info.add(new Status(IStatus.INFO, Activator.PLUGIN_ID, 1, e.getMessage(), null));
					ErrorDialog.openError(getShell(), Messages.ExportKontakteDialog_ExportErrorMessageLabel, null, info);
					return info;
					
				}finally
				{
					try
					{
						fos.close();
					} catch (IOException e)
					{
					}
				}
				return Status.OK_STATUS;
			}
		};
		j.schedule();	
		return true;
	}
	
	@Override
	public boolean close()
	{
		eventBroker.unsubscribe(this);
		return super.close();
	}
	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();
		//
		ObservableListContentProvider listContentProvider = new ObservableListContentProvider();
		IObservableMap observeMap = BeansObservables.observeMap(listContentProvider.getKnownElements(), KontakteData.class, "address.name");
		tableViewer.setLabelProvider(new ObservableMapLabelProvider(observeMap));
		tableViewer.setContentProvider(listContentProvider);
		//
		IObservableList kontakteDataKontaktModelObserveList = BeanProperties.list("kontakteData").observe(kontaktModel);
		tableViewer.setInput(kontakteDataKontaktModelObserveList);
		//
		return bindingContext;
	}

	public void setKontakteCollectionName(String kontakteCollectionName)
	{
		this.kontakteCollectionName = kontakteCollectionName;
	}

	

}
