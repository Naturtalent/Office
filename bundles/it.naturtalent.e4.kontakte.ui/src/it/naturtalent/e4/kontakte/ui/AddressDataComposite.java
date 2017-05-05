package it.naturtalent.e4.kontakte.ui;

import it.naturtalent.e4.kontakte.IKontakteDataFactory;
import it.naturtalent.e4.kontakte.IKontakteDataModel;
import it.naturtalent.e4.kontakte.KontakteDataModel;
import it.naturtalent.e4.project.address.AddressData;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.core.databinding.validation.ValidationStatus;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.ModifyEvent;

public class AddressDataComposite extends Composite
{	
	private static IEventBroker eventBroker;
	//private KontakteDataModel model;
	
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
	public CCombo comboAnrede;
	
	// Ok Button
	private static Button btnOk;
		
	// AddressData und PropertyListener
	private AddressData addressData = new AddressData();
	private PropertyChangeListener propertyListener = new PropertyChangeListener()
	{
		@Override
		public void propertyChange(PropertyChangeEvent evt)
		{
			// Listener reagiert auf Aenderungen in AddressData		
			/*
			if(eventBroker != null)
				eventBroker.post(IKontakteDataModel.KONTAKT_EVENT_ADDRESS_MODIFIED, addressData);
				*/			
		}
	};
		
	private boolean typeSwitch = false;
	
	// Aenderungen im Modell ueberwachen
	/*
	private EventHandler modelEventHandler = new EventHandler()
	{
		@Override
		public void handleEvent(Event event)
		{	
			// ein Modell hat Daten neu eingelesen
			if(StringUtils.equals(event.getTopic(), IKontakteDataModel.KONTAKT_EVENT_LOAD_MODEL))
			{
				KontakteDataModel eventModel = (KontakteDataModel) event.getProperty(IEventBroker.DATA);
				
				// unser Modell initial speichern
				if(model == null)
					model = eventModel;

				// ist unser Modell der Ausloeser
				if (model == eventModel)
				{
					// Anredepraeferenzen uebernehmen
					String anredePreferences = (String) model
							.getModelFactory()
							.getKontaktPreference(
									model.getCollectionName(),
									IKontakteDataFactory.ANREDEN_PREFERENCE_TYPE);

					
					comboAnrede.remove(0, comboAnrede.getItemCount()-1);
					if (StringUtils.isNotEmpty(anredePreferences))
					{
						String[] anredenArray = StringUtils.split(
								anredePreferences, ',');
						for (String anrede : anredenArray)
							comboAnrede.add(anrede);
					}
				}
			}
		}
	};
	*/
	
	/**
	 * Interne Klasse zum ueberpruefen des Textfeldes 'applicationText'
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
			
			if (StringUtils.isEmpty((String) value))	
				status = ValidationStatus.error("Empty Addressname");

			if(eventBroker != null)
				eventBroker.post(IKontakteDataModel.KONTAKT_EVENT_ADDRESS_VALIDATION_STATUS, status);
			
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
		
		addDisposeListener(new DisposeListener()
		{
			public void widgetDisposed(DisposeEvent e)
			{
				// PropertyListener entfernen
				addressData.removePropertyChangeListener(propertyListener);
				
				// EventBroker abmelden
				/*
				if(eventBroker != null)
					eventBroker.unsubscribe(modelEventHandler);
					*/

				formToolkit.dispose();
			}
		});
		
		//setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		setLayout(new GridLayout(1, false));
		
		Composite composite = formToolkit.createComposite(this, SWT.NONE);
		GridData gd_composite = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_composite.heightHint = 221;
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
		
		Group grpAdresstyp = new Group(composite, SWT.NONE);
		grpAdresstyp.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		grpAdresstyp.setText(Messages.AddressDataComposite_grpAdresstyp_text);
		formToolkit.adapt(grpAdresstyp);
		formToolkit.paintBordersFor(grpAdresstyp);
		grpAdresstyp.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		// Addresstype 'private'
		btnRadioPrivat = new Button(grpAdresstyp, SWT.RADIO);
		btnRadioPrivat.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				addressData.setType(AddressData.TYPE_PRIVATE);	
				if(eventBroker != null)
					eventBroker.post(IKontakteDataModel.KONTAKT_EVENT_ADDRESS_MODIFIED, addressData);
				updateWidgets();
			}
		});
		formToolkit.adapt(btnRadioPrivat, true, true);
		btnRadioPrivat.setText(Messages.AddressDataComposite_btnRadioPrivat_text);
		
		// Addresstype 'oeffentlich'
		btnRadioOeffentlich = new Button(grpAdresstyp, SWT.RADIO);
		btnRadioOeffentlich.setSelection(true);
		btnRadioOeffentlich.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				addressData.setType(AddressData.TYPE_PUBLIC);
				if(eventBroker != null)
					eventBroker.post(IKontakteDataModel.KONTAKT_EVENT_ADDRESS_MODIFIED, addressData);
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
		txtName.addModifyListener(new ModifyListener()
		{
			public void modifyText(ModifyEvent e)
			{
				if(eventBroker != null)
					eventBroker.post(IKontakteDataModel.KONTAKT_EVENT_ADDRESS_MODIFIED, addressData);

			}
		});
		txtName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
			
		/*
		controlDecoration = new ControlDecoration(txtName, SWT.LEFT | SWT.TOP);
		controlDecoration.setImage(Icon.OVERLAY_ERROR.getImage(IconSize._7x8_OverlayIconSize));
		controlDecoration.setDescriptionText(Messages.AddressDataComposite_controlDecoration_descriptionText);
		*/
				
		lblName1 = formToolkit.createLabel(composite, "Namezusatz1", SWT.NONE);
		GridData gd_lblName1 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_lblName1.widthHint = 91;
		lblName1.setLayoutData(gd_lblName1);
		
		txtName1 = formToolkit.createText(composite, "", SWT.NONE);		
		txtName1.addModifyListener(new ModifyListener()
		{
			public void modifyText(ModifyEvent e)
			{
				if(eventBroker != null)
					eventBroker.post(IKontakteDataModel.KONTAKT_EVENT_ADDRESS_MODIFIED, addressData);	
			}
		});
		txtName1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		
		Label lblName2 = formToolkit.createLabel(composite, Messages.AddressDataComposite_lblName2_text, SWT.NONE);
		
		txtName2 = formToolkit.createText(composite, "", SWT.NONE);		
		txtName2.addModifyListener(new ModifyListener()
		{
			public void modifyText(ModifyEvent e)
			{
				if(eventBroker != null)
					eventBroker.post(IKontakteDataModel.KONTAKT_EVENT_ADDRESS_MODIFIED, addressData);	
			}
		});
		txtName2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		
		Label lblStrasse = formToolkit.createLabel(composite, Messages.AddressDataComposite_lblStrasse_text, SWT.NONE);
		
		txtStrasse = formToolkit.createText(composite, "", SWT.NONE);		
		txtStrasse.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e)
			{
				if(eventBroker != null)
					eventBroker.post(IKontakteDataModel.KONTAKT_EVENT_ADDRESS_MODIFIED, addressData);	
			}
		});
		txtStrasse.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		
		Label lblPlz = formToolkit.createLabel(composite, Messages.AddressDataComposite_lblPlz_text, SWT.NONE);
		
		txtPlz = formToolkit.createText(composite, "", SWT.NONE);		
		txtPlz.addModifyListener(new ModifyListener()
		{
			public void modifyText(ModifyEvent e)
			{
				if(eventBroker != null)
					eventBroker.post(IKontakteDataModel.KONTAKT_EVENT_ADDRESS_MODIFIED, addressData);	
			}
		});
		GridData gd_txtPlz = new GridData(SWT.LEFT, SWT.CENTER, true, false, 2, 1);
		gd_txtPlz.widthHint = 103;
		txtPlz.setLayoutData(gd_txtPlz);
		
		Label lblOrt = formToolkit.createLabel(composite, Messages.AddressDataComposite_lblOrt_text, SWT.NONE);
		
		txtOrt = formToolkit.createText(composite, "", SWT.NONE);		
		txtOrt.addModifyListener(new ModifyListener()
		{
			public void modifyText(ModifyEvent e)
			{
				if(eventBroker != null)
					eventBroker.post(IKontakteDataModel.KONTAKT_EVENT_ADDRESS_MODIFIED, addressData);	
			}
		});
		txtOrt.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

		updateWidgets();
		m_bindingContext = initDataBindings();
	}
	

	public void setEventBroker(IEventBroker eventBroker)
	{
		AddressDataComposite.eventBroker = eventBroker;
		//eventBroker.subscribe(IKontakteDataModel.KONTAKT_EVENT+"*",modelEventHandler);
	}

	
	public static void setBtnOk(Button btnOk)
	{
		AddressDataComposite.btnOk = btnOk;
	}
	
	public void setKontakteDataModel(KontakteDataModel kontakteDataModel)
	{
		// Anredepraeferenzen uebernehmen
		String anredePreferences = (String) kontakteDataModel
				.getModelFactory()
				.getKontaktPreference(
						kontakteDataModel.getCollectionName(),
						IKontakteDataFactory.ANREDEN_PREFERENCE_TYPE);

		
		comboAnrede.remove(0, comboAnrede.getItemCount()-1);
		if (StringUtils.isNotEmpty(anredePreferences))
		{
			String[] anredenArray = StringUtils.split(
					anredePreferences, ',');
			for (String anrede : anredenArray)
				comboAnrede.add(anrede);
		}

	}

	public void setAddressData(final AddressData addressData)
	{
		// PropertyListener entfernen
		this.addressData.removePropertyChangeListener(propertyListener);
		
		if(m_bindingContext != null)
			m_bindingContext.dispose();
							
		this.addressData = (AddressData) ((addressData != null) ? addressData : new AddressData());
		this.addressData.removePropertyChangeListener(propertyListener);

		// PropertyListener einfuegen
		this.addressData.addPropertyChangeListener(propertyListener);
		
		m_bindingContext = initDataBindings();
				
		typeSwitch = (StringUtils.equals(this.addressData.getType(), AddressData.TYPE_PRIVATE));
		btnRadioPrivat.setSelection(typeSwitch);
		btnRadioOeffentlich.setSelection(!typeSwitch);
		
		updateWidgets();
	}
	
	public AddressData getAddessData()
	{
		return addressData;
	}
	
	
	
	@Override
	public void setEnabled(boolean enabled)
	{
		txtName.setEnabled(enabled);
		txtName1.setEnabled(enabled);
		txtName2.setEnabled(enabled);
		txtStrasse.setEnabled(enabled);
		txtPlz.setEnabled(enabled);
		txtOrt.setEnabled(enabled);
		
		super.setEnabled(enabled);
	}


	@Override
	protected void checkSubclass()
	{
		// Disable the check that prevents subclassing of SWT components
	}
	
	private void updateWidgets()
	{
		//lblName1.setText(Messages.AddressDataComposite_lblName1_text);
		
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
		UpdateValueStrategy strategy_1 = new UpdateValueStrategy();
		bindingContext.bindValue(observeTextTxtName1ObserveWidget, namezusatz1AddressDataObserveValue, strategy_1, null);
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
		return bindingContext;
	}
}
