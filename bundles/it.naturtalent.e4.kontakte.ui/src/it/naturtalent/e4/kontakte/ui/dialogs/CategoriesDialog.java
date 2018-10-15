package it.naturtalent.e4.kontakte.ui.dialogs;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.LabelProvider;
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

public class CategoriesDialog extends Dialog
{
	private Table table;
	
	private CheckboxTableViewer checkboxTableViewer;
	
	private String selectedValue;

	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public CategoriesDialog(Shell parentShell)
	{
		super(parentShell);
	}
	
	@Override
	protected void configureShell(Shell newShell)
	{	
		super.configureShell(newShell);
		newShell.setText("Kategorien");
	}


	/**
	 * Create contents of the dialog.
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent)
	{
		Composite container = (Composite) super.createDialogArea(parent);
		container.setLayout(new GridLayout(1, false));
		
		checkboxTableViewer = CheckboxTableViewer.newCheckList(container, SWT.BORDER | SWT.FULL_SELECTION);
		table = checkboxTableViewer.getTable();
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		Composite compositeButtons = new Composite(container, SWT.NONE);
		compositeButtons.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		compositeButtons.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Button btnAllSelection = new Button(compositeButtons, SWT.NONE);
		btnAllSelection.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				checkboxTableViewer.setAllChecked(true);
			}
		});
		btnAllSelection.setText("alle auswählen");
		
		Button btnNoSelection = new Button(compositeButtons, SWT.NONE);
		btnNoSelection.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				checkboxTableViewer.setAllChecked(false);
			}
		});
		btnNoSelection.setText("keine auswählen");
		checkboxTableViewer.setContentProvider(new ArrayContentProvider());
		checkboxTableViewer.setLabelProvider(new LabelProvider());
				
		return container;
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
		return new Point(306, 478);
	}
	
	public void setValues(List<String> categoryNames, String [] preferences)
	{		
		Set<String>mergeSet = new HashSet<String>();
		
		if((categoryNames != null) && (!categoryNames.isEmpty()))
		{			
			for(String cat : categoryNames)
				mergeSet.add(cat);
		}

		if(ArrayUtils.isNotEmpty(preferences))
		{
			for(String cat : preferences)
				mergeSet.add(cat);
		}

		if(!mergeSet.isEmpty())		
			checkboxTableViewer.setInput(mergeSet.toArray(new String[0]));
		
		if((categoryNames != null) && (!categoryNames.isEmpty()))
			checkboxTableViewer.setCheckedElements(categoryNames.toArray());
	}
	
	
	@Override
	protected void okPressed()
	{
		Object [] result = checkboxTableViewer.getCheckedElements();
		String [] stg = new String[result.length];
		System.arraycopy(result, 0, stg, 0,
				result.length);		
		selectedValue = StringUtils.join(stg,",");
		super.okPressed();
	}
	
	public String getValues()
	{
		return selectedValue;
	}

}
