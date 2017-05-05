package it.naturtalent.e4.kontakte.ui;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

public class EmailDetails extends DefaultKontaktDetails
{
	public EmailDetails(Shell shell,Section section, FormToolkit formToolkit)
	{
		super(shell, section, formToolkit);		
		editDialogLabel = Messages.EmailDetails_DialogLabel;
		tblclmn.setText(Messages.EmailDetails_SectionLabel);
	}

}
