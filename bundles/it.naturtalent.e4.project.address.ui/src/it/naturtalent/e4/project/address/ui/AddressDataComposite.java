package it.naturtalent.e4.project.address.ui;

import java.beans.PropertyChangeEvent;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import it.naturtalent.e4.project.address.AddressData;
import it.naturtalent.e4.project.address.AddressesPreferences;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.SWT;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.core.databinding.validation.ValidationStatus;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.di.extensions.Preference;
import org.osgi.service.prefs.Preferences;

public class AddressDataComposite extends Composite
{
	private DataBindingContext m_bindingContext;
	private final FormToolkit formToolkit = new FormToolkit(Display.getDefault());
	private Text txtName;
	private Text txtName1;
	private Text txtName2;
	private Text txtStrasse;
	private Text txtPlz;
	private Text txtOrt;
	private Button btnRadioOeffentlich;
	private Button btnRadioPrivat;
	private Label lblName;
	private Label lblName1;
	private CCombo comboAnrede;
	
	
	private AddressData addressData = new AddressData();
	
	private static ControlDecoration controlDecoration;
	
	private boolean typeSwitch = false;
	
	private static ProjectAddressWizardPage addressWizardPage;
	private Label lblNotice;
	private Text text;
	
	@PostConstruct
	public void initWizardPage(
			ProjectAddressWizardPage addressWizardPage,
			@Preference(value = AddressesPreferences.ADDRESS_ANREDEN_KEY, nodePath = AddressesPreferences.ROOT_ADDRESS_PREFERENCES_NODE)
			String defAnreden)
	{
		AddressDataComposite.addressWizardPage = addressWizardPage;

		// AnredenCombo mit Prefaerenzdaten fuellen
		if(StringUtils.isNotEmpty(defAnreden))
		{
			String [] anreden = StringUtils.split(defAnreden, ',');
			for(String anrede : anreden)
				comboAnrede.add(anrede);
		}
	}
	
	/**
	 * Interne Klasse zum �berpr�fen des Textfeldes 'applicationText'
	 * 
	 * @author dieter
	 * 
	 */
	public static class EmptyStringValidator implements IValidator
	{
		public EmptyStringValidator()
		{
			super();
		}

		@Override
		public IStatus validate(Object value)
		{
			IStatus status = Status.OK_STATUS;
			
			if (StringUtils.isNotEmpty((String) value))
				controlDecoration.hide();
			
			else
			{
				controlDecoration.show();				
				status = ValidationStatus.error("Empty Addressname");
			}
			
			if(addressWizardPage != null)
				addressWizardPage.updateWidgets();
				
			return status;
		}
	}

	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public AddressDataComposite(Composite parent, int style)
	{
		super(parent, style);
		setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		setLayout(new GridLayout(1, false));
		
		Composite composite = formToolkit.createComposite(this, SWT.NONE);
		GridData gd_composite = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_composite.heightHint = 288;
		composite.setLayoutData(gd_composite);
		formToolkit.paintBordersFor(composite);
		composite.setLayout(new GridLayout(3, false));
		
		Label lblAnrede = new Label(composite, SWT.NONE);
		formToolkit.adapt(lblAnrede, true, true);
		lblAnrede.setText(Messages.AddressDataComposite_lblAnrede_text);
		
		comboAnrede = new CCombo(composite, SWT.BORDER);
		GridData gd_comboAnrede = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_comboAnrede.widthHint = 129;
		comboAnrede.setLayoutData(gd_comboAnrede);
		formToolkit.adapt(comboAnrede);
		formToolkit.paintBordersFor(comboAnrede);
		
		Group group = new Group(composite, SWT.NONE);
		group.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		group.setText(Messages.AddressDataComposite_group_text);
		formToolkit.adapt(group);
		formToolkit.paintBordersFor(group);
		group.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		// Addresstype 'private'
		btnRadioPrivat = new Button(group, SWT.RADIO);
		btnRadioPrivat.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				addressData.setType(AddressData.TYPE_PRIVATE);
				updateWidgets();
			}
		});
		formToolkit.adapt(btnRadioPrivat, true, true);
		btnRadioPrivat.setText(Messages.AddressDataComposite_btnRadioPrivat_text);
		
		// Addresstype 'oeffentlich'
		btnRadioOeffentlich = new Button(group, SWT.RADIO);
		btnRadioOeffentlich.setSelection(true);
		btnRadioOeffentlich.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				addressData.setType(AddressData.TYPE_PUBLIC);
				updateWidgets();
			}
		});
		formToolkit.adapt(btnRadioOeffentlich, true, true);
		btnRadioOeffentlich.setText(Messages.AddressDataComposite_btnRadioOeffentlich_text);
		
		lblName = formToolkit.createLabel(composite, Messages.AddressDataComposite_lblName_text);
		GridData gd_lblName = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_lblName.widthHint = 70;
		lblName.setLayoutData(gd_lblName);
		
		txtName = formToolkit.createText(composite, "", SWT.NONE);		
		txtName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		
		controlDecoration = new ControlDecoration(txtName, SWT.LEFT | SWT.TOP);
		controlDecoration.setImage(SWTResourceManager.getImage(AddressDataComposite.class, "/icons/full/ovr16/error_ovr.gif"));
		controlDecoration.setDescriptionText(Messages.CustomerPage_InputDialog_UndefinedNameError);
		
		lblName1 = formToolkit.createLabel(composite, Messages.AddressDataComposite_lblName1_text, SWT.NONE);
		GridData gd_lblName1 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_lblName1.widthHint = 62;
		lblName1.setLayoutData(gd_lblName1);
		
		txtName1 = formToolkit.createText(composite, "", SWT.NONE);		
		txtName1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		
		Label lblName2 = formToolkit.createLabel(composite, Messages.AddressDataComposite_lblName2_text, SWT.NONE);
		
		txtName2 = formToolkit.createText(composite, "", SWT.NONE);		
		txtName2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		
		Label lblStrasse = formToolkit.createLabel(composite, Messages.AddressDataComposite_lblStrasse_text, SWT.NONE);
		
		txtStrasse = formToolkit.createText(composite, "", SWT.NONE);		
		txtStrasse.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		
		Label lblPlz = formToolkit.createLabel(composite, Messages.AddressDataComposite_lblPlz_text, SWT.NONE);
		
		txtPlz = formToolkit.createText(composite, "", SWT.NONE);		
		GridData gd_txtPlz = new GridData(SWT.LEFT, SWT.CENTER, true, false, 2, 1);
		gd_txtPlz.widthHint = 103;
		txtPlz.setLayoutData(gd_txtPlz);
		
		Label lblOrt = formToolkit.createLabel(composite, Messages.AddressDataComposite_lblOrt_text, SWT.NONE);
		
		txtOrt = formToolkit.createText(composite, "", SWT.NONE);		
		txtOrt.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		
		lblNotice = new Label(composite, SWT.NONE);
		lblNotice.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, true, 1, 1));
		formToolkit.adapt(lblNotice, true, true);
		lblNotice.setText(Messages.AddressDataComposite_lblNotice_text);
		
		text = new Text(composite, SWT.BORDER | SWT.MULTI);		
		text.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
		formToolkit.adapt(text, true, true);

		updateWidgets();
		m_bindingContext = initDataBindings();
	}
	
	@Override
	public void dispose()
	{
		SWTResourceManager.disposeColors();
		SWTResourceManager.disposeImages();
		super.dispose();
	}
	
	public void setAddressData(AddressData addressData)
	{
		if(m_bindingContext != null)
			m_bindingContext.dispose();
							
		
		if(addressData == null)
		{
			this.addressData = new AddressData();
			this.addressData.setType(AddressData.TYPE_PRIVATE);
		}
		else this.addressData = addressData;
					
		m_bindingContext = initDataBindings();
		
		typeSwitch = (StringUtils.equals(this.addressData.getType(), AddressData.TYPE_PRIVATE));
		btnRadioPrivat.setSelection(typeSwitch);
		btnRadioOeffentlich.setSelection(!typeSwitch);
		
		updateWidgets();
	}
	
	@PreDestroy
	public void storePreferes()
	{
		// Referenzen in Combo Anreden speichern
		String [] anreden = comboAnrede.getItems();		
		String txtAnrede = comboAnrede.getText();
		if (StringUtils.isNotEmpty(txtAnrede)
				&& !ArrayUtils.contains(anreden, txtAnrede))
		{
			Set<String> set = new HashSet<String>();			
			
			anreden = ArrayUtils.add(anreden, txtAnrede);
			Preferences node = InstanceScope.INSTANCE
					.getNode(AddressesPreferences.ROOT_ADDRESS_PREFERENCES_NODE);
			node.put(AddressesPreferences.ADDRESS_ANREDEN_KEY,
					StringUtils.join(anreden, ","));
		}
	}

	public AddressData getAddessData()
	{
		return addressData;
	}
	
	public boolean validate()
	{
		return !controlDecoration.isVisible();
	}

	@Override
	protected void checkSubclass()
	{
		// Disable the check that prevents subclassing of SWT components
	}
	
	private void updateWidgets()
	{
		lblName.setText(btnRadioPrivat.getSelection() ? Messages.AddressDataComposite_lblNachname_text
				: Messages.AddressDataComposite_lblName_text);
		lblName1.setText(btnRadioPrivat.getSelection() ? Messages.AddressDataComposite_lblVorname_text
				: Messages.AddressDataComposite_lblName1_text);
		comboAnrede.setEnabled(btnRadioPrivat.getSelection());
	}

	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();
		//
		IObservableValue observeTextTxtNameObserveWidget = WidgetProperties.text(SWT.Modify).observe(txtName);
		IObservableValue nameAddressDataObserveValue = BeanProperties.value("name").observe(addressData);
		UpdateValueStrategy strategy = new UpdateValueStrategy();
		strategy.setAfterGetValidator(new EmptyStringValidator());
		bindingContext.bindValue(observeTextTxtNameObserveWidget, nameAddressDataObserveValue, strategy, null);
		//
		IObservableValue observeTextTxtName1ObserveWidget = WidgetProperties.text(SWT.Modify).observe(txtName1);
		IObservableValue namezusatz1AddressDataObserveValue = BeanProperties.value("namezusatz1").observe(addressData);
		bindingContext.bindValue(observeTextTxtName1ObserveWidget, namezusatz1AddressDataObserveValue, null, null);
		//
		IObservableValue observeTextTxtName2ObserveWidget = WidgetProperties.text(SWT.Modify).observe(txtName2);
		IObservableValue namezusatz2AddressDataObserveValue = BeanProperties.value("namezusatz2").observe(addressData);
		bindingContext.bindValue(observeTextTxtName2ObserveWidget, namezusatz2AddressDataObserveValue, null, null);
		//
		IObservableValue observeTextTxtStrasseObserveWidget = WidgetProperties.text(SWT.Modify).observe(txtStrasse);
		IObservableValue strasseAddressDataObserveValue = BeanProperties.value("strasse").observe(addressData);
		bindingContext.bindValue(observeTextTxtStrasseObserveWidget, strasseAddressDataObserveValue, null, null);
		//
		IObservableValue observeTextTxtPlzObserveWidget = WidgetProperties.text(SWT.Modify).observe(txtPlz);
		IObservableValue plzAddressDataObserveValue = BeanProperties.value("plz").observe(addressData);
		bindingContext.bindValue(observeTextTxtPlzObserveWidget, plzAddressDataObserveValue, null, null);
		//
		IObservableValue observeTextTxtOrtObserveWidget = WidgetProperties.text(SWT.Modify).observe(txtOrt);
		IObservableValue ortAddressDataObserveValue = BeanProperties.value("ort").observe(addressData);
		bindingContext.bindValue(observeTextTxtOrtObserveWidget, ortAddressDataObserveValue, null, null);
		//
		IObservableValue observeTextComboAnredeObserveWidget = WidgetProperties.text().observe(comboAnrede);
		IObservableValue anredeAddressDataObserveValue = BeanProperties.value("anrede").observe(addressData);
		bindingContext.bindValue(observeTextComboAnredeObserveWidget, anredeAddressDataObserveValue, null, null);
		//
		IObservableValue observeTextTextObserveWidget = WidgetProperties.text(SWT.Modify).observe(text);
		IObservableValue noticeAddressDataObserveValue = BeanProperties.value("notice").observe(addressData);
		bindingContext.bindValue(observeTextTextObserveWidget, noticeAddressDataObserveValue, null, null);
		//
		return bindingContext;
	}
}
