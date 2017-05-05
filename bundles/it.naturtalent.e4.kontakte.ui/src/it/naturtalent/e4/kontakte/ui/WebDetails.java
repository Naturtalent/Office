package it.naturtalent.e4.kontakte.ui;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

public class WebDetails extends DefaultKontaktDetails
{
	public WebDetails(Shell shell, Section section, FormToolkit formToolkit)
	{
		super(shell, section, formToolkit);			
		editDialogLabel = Messages.WebDetails_DialogLabel;
		tblclmn.setText(Messages.WebDetails_SectilonLabel);
	}

}
