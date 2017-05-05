package it.naturtalent.e4.office.ui.dialogs;

import java.util.ArrayList;
import java.util.List;

import it.naturtalent.e4.office.TextModule;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.core.databinding.validation.ValidationStatus;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class AddTextModuleDialog extends TitleAreaDialog
{
	public class ModuleNameValidator implements IValidator
	{
		@Override
		public IStatus validate(Object value)
		{
			if (StringUtils.isNotEmpty((String) value))
			{
				controlDecorationName.hide();
				multiValidateState.remove(this);
				if(multiValidateState.isEmpty())
					okButton.setEnabled(true);
				return Status.OK_STATUS;
			}
			else
			{
				controlDecorationName.show();
				multiValidateState.add(this);
				okButton.setEnabled(false);
				return ValidationStatus.error("leeres Eingabefeld");
			}
		}
	}
	
	public class ModuleContentValidator implements IValidator
	{
		@Override
		public IStatus validate(Object value)
		{
			if (StringUtils.isNotEmpty((String) value))
			{
				controlDecorationContent.hide();
				multiValidateState.remove(this);
				if(multiValidateState.isEmpty())
					okButton.setEnabled(true);
				return Status.OK_STATUS;
			}
			else
			{
				controlDecorationContent.show();
				multiValidateState.add(this);
				okButton.setEnabled(false);
				return ValidationStatus.error("leeres Eingabefeld");
			}
		}
	}

	
	private List<Object>multiValidateState = new ArrayList<Object>();
	
	private DataBindingContext m_bindingContext;
	
	private TextModule textModule;
	
	private Text textModuleName;
	
	private Button okButton;
	private ControlDecoration controlDecorationName;
	private StyledText styledTextContent;
	private ControlDecoration controlDecorationContent;
	private Menu menu;
	private MenuItem mntmPaste;
	
	
	
	
	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public AddTextModuleDialog(Shell parentShell)
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
		setTitle("Einen neuen Textbaustein definieren");
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setLayout(new GridLayout(1, false));
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		Label lblModuleName = new Label(container, SWT.NONE);
		lblModuleName.setText("Bezeichnung des Textbausteins");
		
		textModuleName = new Text(container, SWT.BORDER);
		textModuleName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		controlDecorationName = new ControlDecoration(textModuleName, SWT.LEFT | SWT.TOP);
		controlDecorationName.setImage(SWTResourceManager.getImage(AddTextModuleDialog.class, "/org/eclipse/jface/fieldassist/images/error_ovr.gif"));
		controlDecorationName.setDescriptionText("Some description");		
		new Label(container, SWT.NONE);
				
		Label lblModuleContent = new Label(container, SWT.NONE);
		lblModuleContent.setText("Inhalt");
		
		styledTextContent = new StyledText(container, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		styledTextContent.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		controlDecorationContent = new ControlDecoration(styledTextContent, SWT.LEFT | SWT.TOP);
		controlDecorationContent.setImage(SWTResourceManager.getImage(AddTextModuleDialog.class, "/org/eclipse/jface/fieldassist/images/error_ovr.gif"));
		controlDecorationContent.setDescriptionText("Some description");
		
		menu = new Menu(styledTextContent);
		styledTextContent.setMenu(menu);
		
		MenuItem mntmCopy = new MenuItem(menu, SWT.NONE);
		mntmCopy.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				styledTextContent.copy();
			}
		});
		mntmCopy.setText("kopieren");
		
		mntmPaste = new MenuItem(menu, SWT.NONE);
		mntmPaste.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{	
				styledTextContent.paste();
			}
		});
		mntmPaste.setText("einf\u00FCgen");
		return area;
	}

	/**
	 * Create contents of the button bar.
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent)
	{
		okButton = createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
		m_bindingContext = initDataBindings();
	}
	
	public void setTextModule(TextModule textModule)
	{
		multiValidateState.clear();
		if(m_bindingContext != null)
			m_bindingContext.dispose();
		
		this.textModule = textModule;
		m_bindingContext = initDataBindings();		
	}
	
	public TextModule getTextModule()
	{
		return textModule;
	}
	
	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize()
	{
		return new Point(450, 388);
	}
	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();
		//
		IObservableValue observeTextTextModuleNameObserveWidget = WidgetProperties.text(SWT.Modify).observe(textModuleName);
		IObservableValue nameTextModuleObserveValue = BeanProperties.value("name").observe(textModule);
		UpdateValueStrategy strategy = new UpdateValueStrategy();
		strategy.setAfterGetValidator(new ModuleNameValidator());
		bindingContext.bindValue(observeTextTextModuleNameObserveWidget, nameTextModuleObserveValue, strategy, null);
		//
		IObservableValue observeTextStyledTextContentObserveWidget = WidgetProperties.text(SWT.Modify).observe(styledTextContent);
		IObservableValue contentTextModuleObserveValue = BeanProperties.value("content").observe(textModule);
		UpdateValueStrategy strategy_1 = new UpdateValueStrategy();
		strategy_1.setAfterGetValidator(new ModuleContentValidator());
		bindingContext.bindValue(observeTextStyledTextContentObserveWidget, contentTextModuleObserveValue, strategy_1, null);
		//
		return bindingContext;
	}
}
