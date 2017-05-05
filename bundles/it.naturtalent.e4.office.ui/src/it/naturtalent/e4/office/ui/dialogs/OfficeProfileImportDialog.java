package it.naturtalent.e4.office.ui.dialogs;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.layout.GridLayout;

import it.naturtalent.e4.office.OfficeProfiles;
import it.naturtalent.e4.office.letter.OfficeLetterProfiles;
import it.naturtalent.e4.office.ui.OfficeProfileComposite;

import org.eclipse.swt.widgets.Label;

import it.naturtalent.e4.office.ui.OfficeProfileImportComposite;

import org.eclipse.swt.widgets.Tree;
import org.eclipse.wb.swt.SWTResourceManager;

public class OfficeProfileImportDialog extends TitleAreaDialog
{
	@Inject
	@Optional
	static Shell shell;

	public static final String OFFICEPROFILE_IMPORTPATH_SETTINGS = "officeprofileimportpath"; //$NON-NLS-N$
	protected String settingKey = OFFICEPROFILE_IMPORTPATH_SETTINGS;
	
	private OfficeProfileImportComposite officeProfileImportComposite;
	private Button okButton;
	
	/**
	 * Create the dialog.
	 * @param parentShell
	 * @wbp.parser.constructor
	 */
	public OfficeProfileImportDialog()
	{
		super(shell);
	}

	
	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public OfficeProfileImportDialog(Shell parentShell)
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
		setMessage("Einlesen der ausgewählten Profile die in einer externen Datei gespeichert sind.");
		setTitleImage(SWTResourceManager.getImage(OfficeProfileImportDialog.class, "/icons/full/wizban/import_wiz.png"));
		setTitle("Import Officeprofile");
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setLayout(new GridLayout(1, false));
		GridData gd_container = new GridData(GridData.FILL_BOTH);
		gd_container.widthHint = 669;
		container.setLayoutData(gd_container);
		
		officeProfileImportComposite = new OfficeProfileImportComposite(container, SWT.NONE);
		officeProfileImportComposite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		officeProfileImportComposite.setOkButton(okButton);
		officeProfileImportComposite.init(settingKey);
		
		

		return area;
	}

	/**
	 * Create contents of the button bar.
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent)
	{
		okButton = createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				true);
		
		okButton.setEnabled(false);
		if(officeProfileImportComposite != null)
			officeProfileImportComposite.setOkButton(okButton);
			
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize()
	{
		return new Point(535, 463);
	}

	@Override
	protected void okPressed()
	{
		officeProfileImportComposite.okPressed();
		super.okPressed();
	}
	
	public OfficeLetterProfiles getImportetProfiles()
	{
		return officeProfileImportComposite.getImportedProfiles();
	}
	
	public boolean isOverwriteFlag()
	{
		return officeProfileImportComposite.isOverwriteFlag();
	}
	
	
}
