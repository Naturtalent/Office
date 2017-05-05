package it.naturtalent.e4.kontakte.ui;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

public class MobilDetails extends DefaultKontaktDetails
{
	public MobilDetails(final Shell shell, Section section, FormToolkit formToolkit)
	{
		super(shell, section, formToolkit);			
		editDialogLabel = Messages.MobilDetails_DialogLabel;
		tblclmn.setText(Messages.MobilDetails_SectionLabel);
	}
	

}
