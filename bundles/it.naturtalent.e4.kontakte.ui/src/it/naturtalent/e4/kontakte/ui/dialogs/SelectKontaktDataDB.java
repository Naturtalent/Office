package it.naturtalent.e4.kontakte.ui.dialogs;


import java.util.List;

import it.naturtalent.e4.kontakte.IKontakteDataFactory;
import it.naturtalent.e4.kontakte.KontakteDataModel;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Button;

public class SelectKontaktDataDB extends Dialog
{
	private class TableLabelProvider extends LabelProvider implements
			ITableLabelProvider
	{
		public Image getColumnImage(Object element, int columnIndex)
		{
			return null;
		}

		public String getColumnText(Object element, int columnIndex)
		{
			if(kontaktDataFactory != null)
				return kontaktDataFactory.getCollectionLabel((String) element);
			
			return element.toString();
		}
	}
	private Table table;
	
	private IKontakteDataFactory kontaktDataFactory;
	
	private TableViewer tableViewer;

	private Button button;
	
	private String selectedName = null;
	
	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public SelectKontaktDataDB(Shell parentShell)
	{
		super(parentShell);
	}
	
	@Override
	protected void configureShell(Shell newShell)
	{	
		super.configureShell(newShell);
		newShell.setText("Kontakte Datenbank");
	}


	/**
	 * Create contents of the dialog.
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent)
	{
		Composite container = (Composite) super.createDialogArea(parent);
		
		Label lblSelect = new Label(container, SWT.NONE);
		lblSelect.setText("vorhandene Datenbank auswählen");
		
		tableViewer = new TableViewer(container, SWT.BORDER | SWT.FULL_SELECTION);
		tableViewer.addSelectionChangedListener(new ISelectionChangedListener()
		{
			public void selectionChanged(SelectionChangedEvent event)
			{
				IStructuredSelection selection = (IStructuredSelection) event.getSelection();								
				button.setEnabled((selection != null)&&(!selection.isEmpty()));
			}
		});
		tableViewer.addDoubleClickListener(new IDoubleClickListener()
		{
			public void doubleClick(DoubleClickEvent event)
			{
				IStructuredSelection selection = (IStructuredSelection) event.getSelection();
				
				okPressed();
			}
		});
		table = tableViewer.getTable();
		table.setHeaderVisible(true);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		tableViewer.setLabelProvider(new TableLabelProvider());
		tableViewer.setContentProvider(new ArrayContentProvider());
		 		
		return container;
	}
	

	/**
	 * Create contents of the button bar.
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent)
	{
		button = createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				true);
		button.setEnabled(false);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}
	
	

	@Override
	protected void okPressed()
	{
		IStructuredSelection selection = (IStructuredSelection) tableViewer.getSelection();
		selectedName = (String) selection.getFirstElement();
		super.okPressed();
	}
	
	public String getSelectedName()
	{
		return selectedName;
	}

	public void setKontaktDataFactory(IKontakteDataFactory kontaktDataFactory, String preSelectKontakteName)
	{
		this.kontaktDataFactory = kontaktDataFactory;
		
		if (kontaktDataFactory != null)
		{
			List<String>collectionNames = kontaktDataFactory.getCollectionNames();
			collectionNames.remove(preSelectKontakteName);
			tableViewer.setInput(collectionNames);	
			
			//tableViewer.setInput(kontaktDataFactory.getCollectionNames());			
			if (StringUtils.isNotEmpty(preSelectKontakteName))	
				tableViewer.setSelection(new StructuredSelection(preSelectKontakteName));
		}
	}
	
	public void setKontaktDataModel(KontakteDataModel model)
	{
		kontaktDataFactory = model.getModelFactory();		
		if (kontaktDataFactory != null)
		{			
			List<String>collectionNames = kontaktDataFactory.getCollectionNames();
			collectionNames.remove(model.getCollectionName());
			tableViewer.setInput(collectionNames);
		}
	}


	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize()
	{
		return new Point(450, 300);
	}

}
