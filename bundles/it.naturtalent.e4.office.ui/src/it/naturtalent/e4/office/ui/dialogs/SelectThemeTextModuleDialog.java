package it.naturtalent.e4.office.ui.dialogs;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class SelectThemeTextModuleDialog extends Dialog
{

	public static final int SELECT_THEME = 0;
	public static final int SELECT_MODULE = 1;
	
	private int selectedMode = SELECT_THEME; 
	
	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public SelectThemeTextModuleDialog(Shell parentShell)
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
		Composite container = (Composite) super.createDialogArea(parent);
		container.setLayout(new FillLayout(SWT.VERTICAL));
		
		Button btnThema = new Button(container, SWT.RADIO);
		btnThema.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				selectedMode = SELECT_THEME;
			}
		});
		btnThema.setSelection(true);
		btnThema.setText("neues Thema");
		
		Button btnModule = new Button(container, SWT.RADIO);
		btnModule.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				selectedMode = SELECT_MODULE;
			}
		});
		btnModule.setText("neuer Textbaustein");

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
		return new Point(450, 300);
	}

	public int getSelectedMode()
	{
		return selectedMode;
	}
	
	

}
