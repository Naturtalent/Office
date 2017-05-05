package it.naturtalent.e4.kontakte.ui;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

public class PhoneDetails extends DefaultKontaktDetails
{
	public PhoneDetails(final Shell shell, Section section, FormToolkit formToolkit)
	{
		super(shell, section, formToolkit);		
		editDialogLabel = Messages.PhoneDetails_DialogLabel;
		tblclmn.setText(Messages.PhoneDetails_SectionLabel);
	}
}
