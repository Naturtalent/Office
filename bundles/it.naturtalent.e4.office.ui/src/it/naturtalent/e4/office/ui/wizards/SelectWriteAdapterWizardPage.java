package it.naturtalent.e4.office.ui.wizards;

import java.util.List;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;

import it.naturtalent.e4.office.ui.IODFWriteAdapterFactory;
import it.naturtalent.e4.office.ui.Messages;

public class SelectWriteAdapterWizardPage extends WizardPage
{
	private Table table;
	
	private TableViewer tableViewer;
	
	private List<IODFWriteAdapterFactory> writeAdapterFactories;

	private class TableLabelProvider extends LabelProvider
			implements ITableLabelProvider
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

	/**
	 * Create the wizard.
	 */
	public SelectWriteAdapterWizardPage()
	{
		super("wizardPage");
		setTitle(Messages.SelectWriteAdapter_this_title);
		setDescription(Messages.SelectWriteAdapter_this_message);
	}

	/**
	 * Create contents of the wizard.
	 * @param parent
	 */
	public void createControl(Composite parent)
	{
		Composite container = new Composite(parent, SWT.NULL);

		setControl(container);
		container.setLayout(new GridLayout(1, false));
		
		tableViewer = new TableViewer(container, SWT.BORDER | SWT.FULL_SELECTION);
		table = tableViewer.getTable();
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		tableViewer.setLabelProvider(new TableLabelProvider());
		tableViewer.setContentProvider(new ArrayContentProvider());
		if(writeAdapterFactories != null)
			tableViewer.setInput(writeAdapterFactories);
	}
	
	// Liste mit den verfuegbaren AdapterFactories uebergeben
	public void setAdapter(List<IODFWriteAdapterFactory> writeAdapterFactories)
	{
		this.writeAdapterFactories = writeAdapterFactories;
	}
	
	

}
