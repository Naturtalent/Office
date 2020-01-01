package it.naturtalent.e4.office.ui.dialogs;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import it.naturtalent.libreoffice.OpenLoDocument;

/**
 * Dialog informiert ueber die erfolgreiche Speicherung der Projektdaten.
 * 
 * @author dieter
 *
 */
public class JournalProjektExportFinish extends Dialog
{
	
	private Label lblMessage2;
	private Button btnCheckJustOpen;
	
	private String exportFilePath;
	private String finalNLSMessage = "Zieldatei: {0}\nTabellenblatt 'Projekt'"; //$NON-NLS-N$
	private String messageLine;	

	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public JournalProjektExportFinish(Shell parentShell)
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
		container.setLayout(new GridLayout(2, false));
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		
		Label lblMessage = new Label(container, SWT.NONE);
		lblMessage.setText("Die Projektdaten wurden erfolgreich gespeichert."); //$NON-NLS-N$
		new Label(container, SWT.NONE);
		
		lblMessage2 = new Label(container, SWT.NONE);
		lblMessage2.setText(messageLine);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		
		btnCheckJustOpen = new Button(container, SWT.CHECK);
		btnCheckJustOpen.setText("die Zieldatei öffnen?"); //$NON-NLS-N$		
	
		return container;
	}

	/**
	 * Create contents of the button bar.
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent)
	{
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize()
	{
		return new Point(450, 300);
	}

	public void setExportFile(String exportFile)
	{
		exportFilePath = exportFile;
		messageLine = NLS.bind(finalNLSMessage,exportFile);
	}

	@Override
	protected void okPressed()
	{
		if(btnCheckJustOpen.getSelection())
		{
			// Dokument in LibreOffice oeffen
			OpenLoDocument.loadLoDocument(exportFilePath);
		}
		super.okPressed();
	}
	
	

}
