package it.naturtalent.e4.office.ui.dialogs;

import java.io.File;

import javax.inject.Inject;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.internal.workbench.swt.WorkbenchSWTActivator;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.GridLayout;

import it.naturtalent.e4.office.OfficeProfiles;
import it.naturtalent.e4.office.letter.OfficeLetterProfiles;
import it.naturtalent.e4.office.ui.OfficeProfileComposite;

import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.ModifyEvent;

import it.naturtalent.e4.office.ui.Messages;

public class OfficeProfileExportDialog extends TitleAreaDialog
{

	public static final String OFFICEPROFILE_EXPORTPATH_SETTINGS = "officeprofileexportpath"; //$NON-NLS-N$
	protected String settingKey = OFFICEPROFILE_EXPORTPATH_SETTINGS;
	
	
	@Inject
	@Optional
	static Shell shell;
	private Text textFilename;
	private Button okButton;
	private CCombo comboExportDir;
	private OfficeProfileComposite officeProfileComposite;
	private ControlDecoration controlDecorationFile;
	private ControlDecoration controlDecorationDir;
	
	private File destFile;
	private OfficeLetterProfiles selectedProfiles;
	private IDialogSettings dialogSettings;
	
	/**
	 * Create the dialog.
	 * @param parentShell
	 * @wbp.parser.constructor
	 */
	public OfficeProfileExportDialog()
	{
		super(shell);
	}
	
	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public OfficeProfileExportDialog(Shell parentShell)
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
		setMessage(Messages.OfficeProfileExportDialog_this_message);
		setTitle(Messages.OfficeProfileExportDialog_this_title);
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setLayout(new GridLayout(3, false));
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		Label exportDirLabel = new Label(container, SWT.NONE);
		exportDirLabel.setText(Messages.OfficeProfileExportDialog_exportDirLabel_text);
		
		comboExportDir = new CCombo(container, SWT.BORDER);
		comboExportDir.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		comboExportDir.setEditable(false);
		
		controlDecorationDir = new ControlDecoration(comboExportDir, SWT.LEFT | SWT.TOP);
		controlDecorationDir.setImage(SWTResourceManager.getImage(OfficeProfileExportDialog.class, "/org/eclipse/jface/fieldassist/images/error_ovr.gif"));
		controlDecorationDir.setDescriptionText(Messages.OfficeProfileExportDialog_controlDecorationDir_descriptionText);
		
		Button btnSelectDir = new Button(container, SWT.NONE);
		btnSelectDir.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				DirectoryDialog dlg = new DirectoryDialog(getShell());

				// Change the title bar text
				dlg.setText("Exportverzeichnis");
				dlg.setFilterPath(FilenameUtils.getFullPath(comboExportDir.getText()));				

				String importDirectory = dlg.open();
				if (importDirectory != null)					
					comboExportDir.setText(importDirectory);

				updateStatus();
	
			}
		});
		btnSelectDir.setText(Messages.OfficeProfileExportDialog_btnSelectDir_text);
		
		Label lblFile = new Label(container, SWT.NONE);
		lblFile.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblFile.setText(Messages.OfficeProfileExportDialog_lblFile_text);
		
		textFilename = new Text(container, SWT.BORDER);
		textFilename.addModifyListener(new ModifyListener()
		{
			public void modifyText(ModifyEvent e)
			{
				updateStatus();
			}
		});
		textFilename.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		controlDecorationFile = new ControlDecoration(textFilename, SWT.LEFT | SWT.TOP);
		controlDecorationFile.setImage(SWTResourceManager.getImage(OfficeProfileExportDialog.class, "/org/eclipse/jface/fieldassist/images/error_ovr.gif"));
		controlDecorationFile.setDescriptionText(Messages.OfficeProfileExportDialog_controlDecorationFile_descriptionText);
		new Label(container, SWT.NONE);
		
		officeProfileComposite = new OfficeProfileComposite(container, SWT.NONE);
		officeProfileComposite.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, true, true, 3, 1));
		officeProfileComposite.setExportDialog(this);

		
		
		return area;
	}
	
	public void initSettingKey(String settingKey)
	{
		dialogSettings = WorkbenchSWTActivator.getDefault().getDialogSettings();
		String setting = dialogSettings.get(this.settingKey = settingKey);
				
		if (StringUtils.isNotEmpty(setting))
		{
			File fileExport = new File(setting);
			if(fileExport.exists())
			{
				comboExportDir.setText(fileExport.getParent());
				textFilename.setText(fileExport.getName());				
			}
		}
	}


	/**
	 * Create contents of the button bar.
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent)
	{		
		okButton = createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,true);		
		okButton.setEnabled(false);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}
	
	@Override
	protected void okPressed()
	{
		String fileName = textFilename.getText();
		fileName = FilenameUtils.removeExtension(fileName)+".xml"; //$NON-NLS-N$
		destFile = new File(comboExportDir.getText(),fileName);
		selectedProfiles = officeProfileComposite.getCheckedProfiles();
		
		// settings speichern
		if(StringUtils.isNotEmpty(settingKey))		
			dialogSettings.put(settingKey, destFile.getPath());
		
		super.okPressed();
	}

	public void updateStatus()
	{
		controlDecorationFile.hide();
		controlDecorationDir.hide();
		if (okButton != null)
			okButton.setEnabled(false);
		
		if(StringUtils.isEmpty(textFilename.getText()))
		{
			controlDecorationFile.show();
			return;
		}
					
		if(StringUtils.isEmpty(comboExportDir.getText()))
		{
			controlDecorationDir.show();
			return;
		}
		
		if (okButton != null)
		{
			CheckboxTableViewer viewer = officeProfileComposite.getViewer();
			okButton.setEnabled(viewer.getCheckedElements().length > 0);
		}
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize()
	{
		return new Point(450, 432);
	}
		
	public void setProfiles(OfficeLetterProfiles officeProfiles)
	{
		officeProfileComposite.setProfiles(officeProfiles);
	}
	
	public OfficeLetterProfiles getProfiles()
	{
		return selectedProfiles;
	}

	public File getExportFile()
	{
		return destFile;		
	}

}
