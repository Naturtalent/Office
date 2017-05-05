package it.naturtalent.e4.office.ui.dialogs;

import java.io.File;
import java.util.List;

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
import org.eclipse.swt.layout.GridLayout;

import it.naturtalent.e4.office.INtOffice;
import it.naturtalent.e4.office.ITextModuleDataFactory;
import it.naturtalent.e4.office.ITextModuleModel;
import it.naturtalent.e4.office.TextModuleTheme;
import it.naturtalent.e4.office.ui.TextModuleImportComposite;

import org.eclipse.swt.widgets.Label;

/**
 * @author dieter
 *
 */
public class TextmoduleImportDialog extends TitleAreaDialog
{

	@Inject
	@Optional
	static Shell shell;
	
	private ITextModuleModel model;

	private TextModuleImportComposite textModuleImportComposite;

	private static final String TEXTMODULE_IMPORTPATH_SETTINGS = "nttextmoduleimport";

	private String settingKey = TEXTMODULE_IMPORTPATH_SETTINGS;
	
	private Button okButton;

	/**
	 * Create the dialog.
	 * 
	 * @param parentShell
	 * @wbp.parser.constructor
	 */
	public TextmoduleImportDialog()
	{
		super(shell);
	}

	/**
	 * Create the dialog.
	 * 
	 * @param parentShell
	 */
	public TextmoduleImportDialog(Shell parentShell)
	{
		super(parentShell);
	}

	/**
	 * Create contents of the dialog.
	 * 
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent)
	{
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setLayout(new GridLayout(1, false));
		container.setLayoutData(new GridData(GridData.FILL_BOTH));

		textModuleImportComposite = new TextModuleImportComposite(container,
				SWT.NONE);
		GridData gd_textModuleImportComposite = new GridData(SWT.FILL,
				SWT.FILL, true, true, 1, 1);
		gd_textModuleImportComposite.heightHint = 497;
		textModuleImportComposite.setLayoutData(gd_textModuleImportComposite);

		return area;
	}

	public void init(ITextModuleModel model)
	{
		this.model = model;
		
		if(textModuleImportComposite != null)
			textModuleImportComposite.init(settingKey);
		
		textModuleImportComposite.setOkButton(okButton);
	}

	/**
	 * Create contents of the button bar.
	 * 
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent)
	{
		okButton = createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,false);		
		okButton.setEnabled(false);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}
	
	

	public void setSettingKey(String settingKey)
	{
		this.settingKey = settingKey;
	}

	@Override
	protected void okPressed()
	{
		// die zuimportierenden Themen in added- und updated-Listen zusammenfassen
		textModuleImportComposite.okPressed(model);		
		super.okPressed();
	}
		
	/**
	 * Liste der zuimportierenden Themen, die zum Modell hinzugefuegt werden muessen
	 * @return
	 */
	public List<TextModuleTheme> getAddedThemes()
	{
		return textModuleImportComposite.getAddedThemes();
	}

	/**
	 * Liste der zuimportierenden Themen, die namensgleich bereits im Modell existieren und
	 * im Modell aktualisiert werden muessen.
	 * 
	 * @return
	 */
	public List<TextModuleTheme> getUpdatedThemes()
	{
		return textModuleImportComposite.getUpdatedThemes();
	}


	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize()
	{
		return new Point(673, 762);
	}

}
