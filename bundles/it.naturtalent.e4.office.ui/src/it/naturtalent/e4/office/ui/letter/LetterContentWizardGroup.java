package it.naturtalent.e4.office.ui.letter;

import it.naturtalent.e4.office.TextModule;
import it.naturtalent.e4.office.letter.OfficeLetterModel;
import it.naturtalent.e4.office.ui.Messages;
import it.naturtalent.e4.office.ui.dialogs.TextmoduleDialog;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.text.StrBuilder;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.wb.swt.SWTResourceManager;


public class LetterContentWizardGroup extends Group
{
	private StyledText text;
	private ToolBar toolBar;
	
	private OfficeLetterModel wizardOfficeModel;
	private IEclipseContext context;
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public LetterContentWizardGroup(Composite parent, int style, final String officeContext)
	{
		super(parent, style);
		setText("Inhalt");
		setLayout(new GridLayout(1, false));		
		
		toolBar = new ToolBar(this, SWT.FLAT | SWT.RIGHT);
		ToolItem tltmImport = new ToolItem(toolBar, SWT.NONE);
		tltmImport.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				TextmoduleDialog dialog = new TextmoduleDialog(getShell(), officeContext);
				ContextInjectionFactory.invoke(dialog, PostConstruct.class, context);
				dialog.create();					
				if(dialog.open() == TextmoduleDialog.OK)
				{					
					List<TextModule>selectedModules = dialog.getSelectedTextModules();
					StrBuilder content = new StrBuilder(text.getText());
					int cursorPos = text.getCaretOffset();						
					for(TextModule module : selectedModules)
					{
						String dialogText = module.getContent()+'\n';
						content.insert(cursorPos, dialogText);
						cursorPos += dialogText.length();
					}
					
					text.setText(content.toString());
				}
			}
		});
		tltmImport.setToolTipText("Text importieren");
		tltmImport.setImage(SWTResourceManager.getImage(LetterContentWizardGroup.class, "/icons/full/obj16/font.gif"));
		//tltmImport.setText("New Item");
		
		text = new StyledText(this, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL | SWT.MULTI);
		text.addModifyListener(new ModifyListener()
		{
			public void modifyText(ModifyEvent e)
			{
				String s = text.getText();
				String [] ary = StringUtils.splitByWholeSeparatorPreserveAllTokens(s,"\n");
				if(wizardOfficeModel != null)
					wizardOfficeModel.setContent(ary);
			}
		});
		GridData gd_text = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_text.widthHint = 255;
		gd_text.heightHint = 275;
		text.setLayoutData(gd_text);		
		
		Menu menu = new Menu(text);
		text.setMenu(menu);
		
		MenuItem mntmCopy = new MenuItem(menu, SWT.NONE);
		mntmCopy.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				text.copy();
			}
		});
		mntmCopy.setText(Messages.ContentWizardGroup_mntmCopy_text);
		
		MenuItem mntmPaste = new MenuItem(menu, SWT.NONE);
		mntmPaste.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				text.paste();
			}
		});
		mntmPaste.setText(Messages.ContentWizardGroup_mntmPaste_text);

	}
	
	@PostConstruct
	protected void postConstruct (IEclipseContext context)
	{		
		this.context = context;
		wizardOfficeModel = context.get(OfficeLetterModel.class);
	}


	public void setOfficeModel(String [] textContent)
	{
		if (wizardOfficeModel != null)
		{
			if (ArrayUtils.isEmpty(textContent))
				wizardOfficeModel.setContent(new String[]{ "" });
			else
				wizardOfficeModel.setContent(textContent);

			// Array in einen String zusammenfassen			
			text.setText(StringUtils.join(wizardOfficeModel.getContent(), "\n"));
		}
	}

	@Override
	protected void checkSubclass()
	{
		// Disable the check that prevents subclassing of SWT components
	}
}
