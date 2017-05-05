package it.naturtalent.e4.kontakte.ui.dialogs;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.FillLayout;

import it.naturtalent.e4.kontakte.IBankData;
import it.naturtalent.e4.kontakte.BankDataComposite;
import it.naturtalent.e4.kontakte.ui.Messages;

/**
 * Dialog zur Eingabe von Bankdaten.
 * 
 * @author A682055
 *
 */
public class EditBankenDialog extends TitleAreaDialog
{
	
	private BankDataComposite bankDataComposite;

	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public EditBankenDialog(Shell parentShell)
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
		setMessage(Messages.EditBankenDialog_this_message);
		setTitle(Messages.EditBankenDialog_this_title);
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setLayout(new FillLayout(SWT.HORIZONTAL));
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		bankDataComposite = new BankDataComposite(container, SWT.NONE);
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
		return new Point(450, 300);
	}
	
	/**
	 * Dieser Datensatz wird mit dem Dialog bearbeitet.
	 * 
	 * @param bankData
	 */
	public void setBankData(IBankData bankData)
	{
		bankDataComposite.setIBankData(bankData);
	}

}
