package it.naturtalent.e4.office.ui.dialogs;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

public class EditTextModuleDialog extends AddTextModuleDialog
{
	public EditTextModuleDialog(Shell parentShell)
	{
		super(parentShell);		
	}

	@Override
	protected Control createDialogArea(Composite parent)
	{
		Control control = super.createDialogArea(parent);
		
		setTitle("Einen Textbaustein editieren");
				
		return control;
	}
	
	

}
