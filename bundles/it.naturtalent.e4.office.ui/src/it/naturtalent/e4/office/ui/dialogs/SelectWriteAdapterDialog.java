package it.naturtalent.e4.office.ui.dialogs;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.e4.ui.internal.workbench.swt.WorkbenchSWTActivator;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;

import it.naturtalent.e4.office.ui.IODFWriteAdapter;
import it.naturtalent.e4.office.ui.IODFWriteAdapterFactory;
import it.naturtalent.e4.office.ui.Messages;
import org.eclipse.swt.widgets.Table;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.DoubleClickEvent;

/**
 * Zeigt alle verfügbaren WriteAdapterFactories und ermöglicht die Auswahl.
 * 
 * @author dieter
 *
 */
public class SelectWriteAdapterDialog extends TitleAreaDialog
{
	/*
	 * Labelprovider gibt die Namen der gelisteten WriteAdapterFactories zurueck 
	 */
	private class TableLabelProvider extends LabelProvider implements ITableLabelProvider
	{
		public Image getColumnImage(Object element, int columnIndex)
		{
			return null;
		}

		public String getColumnText(Object element, int columnIndex)
		{
			if (element instanceof IODFWriteAdapterFactory)
			{
				IODFWriteAdapterFactory writeAdapterFactory = (IODFWriteAdapterFactory) element;
				return writeAdapterFactory.getAdapterLabel();
			}
			return element.toString();
		}
	}

	private Table table;
	private TableViewer tableViewer;
	
	private IODFWriteAdapter selectedAdapter;
	
	private IDialogSettings settings = WorkbenchSWTActivator.getDefault().getDialogSettings();
	private static final String ADAPTER_LABEL_SETTINGS_KEY = "writeadaptersettingkey";
	
	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public SelectWriteAdapterDialog(Shell parentShell)
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
		setMessage(Messages.SelectWriteAdapter_this_message);
		setTitle(Messages.SelectWriteAdapter_this_title);
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setLayout(new GridLayout(1, false));
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		Label lblAvailableAdapter = new Label(container, SWT.NONE);
		lblAvailableAdapter.setText(Messages.SelectWriteAdapter_lblAvailableAdapter_text);
		
		tableViewer = new TableViewer(container, SWT.BORDER | SWT.FULL_SELECTION);
		tableViewer.addDoubleClickListener(new IDoubleClickListener()
		{
			public void doubleClick(DoubleClickEvent event)
			{
				// Ok durch DoppelClick
				SelectWriteAdapterDialog.this.okPressed();
			}
		});
		table = tableViewer.getTable();
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		tableViewer.setLabelProvider(new TableLabelProvider());
		tableViewer.setContentProvider(new ArrayContentProvider());

		return area;
	}
	
	private void selectSettingAdapter()
	{
		String adapterLabel = settings.get(ADAPTER_LABEL_SETTINGS_KEY);
		if(StringUtils.isNotEmpty(adapterLabel))
		{
			Object tableContent = tableViewer.getInput();
			if (tableContent instanceof List)
			{
				List<IODFWriteAdapterFactory> factories = (List<IODFWriteAdapterFactory>) tableViewer.getInput();
				for(IODFWriteAdapterFactory factory : factories)
				{
					if(StringUtils.equals(adapterLabel, factory.getAdapterLabel()))
					{
						tableViewer.setSelection(new StructuredSelection(factory));
						break;
					}					
				}				
			}			
		}
	}

	/**
	 * Create contents of the button bar.
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent)
	{
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,true);
		createButton(parent, IDialogConstants.CANCEL_ID,IDialogConstants.CANCEL_LABEL, false);
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize()
	{
		return new Point(450, 523);
	}
	
	// Liste mit den verfuegbaren AdapterFactories uebergeben
	public void setAdapter(List<IODFWriteAdapterFactory> writeAdapterFactories)
	{
		tableViewer.setInput(writeAdapterFactories);
		selectSettingAdapter();
	}

	@Override
	protected void okPressed()
	{
		IODFWriteAdapterFactory adapter = null;
		
		// der selektierte Factory erzeugt den eigentlichen Adapter
		StructuredSelection selection = (StructuredSelection) tableViewer.getSelection();
		Object selObject = selection.getFirstElement();
		if (selObject instanceof IODFWriteAdapterFactory)
		{
			adapter = (IODFWriteAdapterFactory) selObject;
			selectedAdapter = adapter.createAdapter();			
		}
		
		// DialogSettings
		if(adapter != null)
			settings.put(ADAPTER_LABEL_SETTINGS_KEY, adapter.getAdapterLabel());
		super.okPressed();
	}

	public IODFWriteAdapter getSelectedAdapter()
	{
		return selectedAdapter;
	}
	
	
	

}
