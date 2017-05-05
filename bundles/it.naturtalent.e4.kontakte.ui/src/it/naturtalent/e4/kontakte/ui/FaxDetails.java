package it.naturtalent.e4.kontakte.ui;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

public class FaxDetails extends DefaultKontaktDetails
{
	public FaxDetails(Shell shell,Section section, FormToolkit formToolkit)
	{
		super(shell, section, formToolkit);		
		editDialogLabel = Messages.FaxDetails_DialogLabel;
		tblclmn.setText(Messages.FaxDetails_SectionLabel);
	}

}
