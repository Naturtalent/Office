package it.naturtalent.e4.kontakte.ui.expimp;

import it.naturtalent.e4.kontakte.ui.Messages;
import it.naturtalent.icons.core.Icon;
import it.naturtalent.icons.core.IconSize;

import java.io.File;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class ExportDestinationComposite extends Composite
{
	private Text textDestFile;
	
	private CCombo comboDestDir;
	private ControlDecoration controlDecorationDestFile;
	private ControlDecoration controlDecorationDestDir;
	
	public final static String KONTAKT_EXPORTDESTINATION_EVENT = "kontaktexportdestination"; //$NON-NLS-1$
	private IEventBroker eventBroker;
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public ExportDestinationComposite(Composite parent, int style)
	{
		super(parent, style);
		setLayout(new GridLayout(3, false));
		
		Label lblExportDir = new Label(this, SWT.NONE);
		lblExportDir.setText(Messages.ExportDestilnationComposite_lblExportDir_text);
		
		comboDestDir = new CCombo(this, SWT.BORDER);
		comboDestDir.setEditable(false);
		comboDestDir.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		controlDecorationDestDir = new ControlDecoration(comboDestDir, SWT.LEFT | SWT.TOP);
		controlDecorationDestDir.setImage(Icon.OVERLAY_ERROR.getImage(IconSize._7x8_OverlayIconSize));		
		controlDecorationDestDir.setDescriptionText(Messages.ExportDestinationComposite_controlDecorationDestDir_descriptionText);
		
		Button btnSelectDir = new Button(this, SWT.NONE);
		btnSelectDir.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				DirectoryDialog dlg = new DirectoryDialog(getShell());
				
				// Change the title bar text
				dlg.setText(Messages.ExportDestinationComposite_ExportDialogLabel);
				dlg.setFilterPath(comboDestDir.getText());
				
				String exportDirectory = dlg.open();
				comboDestDir.setText(StringUtils.isNotEmpty(exportDirectory) ? exportDirectory : ""); //$NON-NLS-1$
				updateWidgets();					
			}
		});
		btnSelectDir.setText(Messages.ExportDestilnationComposite_btnSelectDir_text);
		
		Label lblDestFile = new Label(this, SWT.NONE);
		lblDestFile.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblDestFile.setText(Messages.ExportDestilnationComposite_lblDestFile_text);
		
		textDestFile = new Text(this, SWT.BORDER);		
		textDestFile.addModifyListener(new ModifyListener()
		{
			public void modifyText(ModifyEvent e)
			{
				updateWidgets();					
			}
		});
		textDestFile.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		controlDecorationDestFile = new ControlDecoration(textDestFile, SWT.LEFT | SWT.TOP);
		controlDecorationDestFile.setImage(Icon.OVERLAY_ERROR.getImage(IconSize._7x8_OverlayIconSize));
		controlDecorationDestFile.setDescriptionText(Messages.ExportDestinationComposite_controlDecorationDestFile_descriptionText);
		new Label(this, SWT.NONE);

		comboDestDir.setText(SystemUtils.getUserHome().getPath());
		updateWidgets();
	}
	
	private void updateWidgets()
	{
		controlDecorationDestFile.hide();
		controlDecorationDestDir.hide();
		
		if(StringUtils.isEmpty(comboDestDir.getText()))
			controlDecorationDestDir.show();
				
		if(StringUtils.isEmpty(textDestFile.getText()))
			controlDecorationDestFile.show();
		
		postEventBroker();
	}
	
	private void postEventBroker()
	{
		String exportPath = null;
		
		if(eventBroker != null)
		{
			String expDir = comboDestDir.getText();
			if(StringUtils.isNotEmpty(expDir))
			{
				String expFile = textDestFile.getText();								
				if(StringUtils.isNotEmpty(expFile))
				{
					expFile = FilenameUtils.removeExtension(expFile)+"."+"xml";
					exportPath = expDir+File.separator+expFile;
				}
			}			
			eventBroker.post(KONTAKT_EXPORTDESTINATION_EVENT, exportPath);
		}
	}

	@Override
	protected void checkSubclass()
	{
		// Disable the check that prevents subclassing of SWT components
	}

	public void setExportPath(String exportPath)
	{
		if(StringUtils.isNotEmpty(exportPath))
		{
			File file = new File(exportPath);
			if(file.isDirectory() && file.exists())
			{
				comboDestDir.setText(file.getPath());
				return;
			}
			
			if(file.exists())
			{
				File dir = file.getParentFile();
				comboDestDir.setText(dir.getPath());
				textDestFile.setText(file.getName());
			}
		}
	}

	public void setEventBroker(IEventBroker eventBroker)
	{
		this.eventBroker = eventBroker;
	}

	
	
	
}
