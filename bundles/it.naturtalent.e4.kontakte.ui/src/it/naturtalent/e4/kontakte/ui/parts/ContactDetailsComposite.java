package it.naturtalent.e4.kontakte.ui.parts;

import it.naturtalent.e4.kontakte.ContactCategoryModel;
import it.naturtalent.e4.kontakte.IKontakteDataFactory;
import it.naturtalent.e4.kontakte.IKontakteDataModel;
import it.naturtalent.e4.kontakte.KontakteData;
import it.naturtalent.e4.kontakte.KontakteDataModel;
import it.naturtalent.e4.kontakte.ui.AddressDataComposite;
import it.naturtalent.e4.kontakte.ui.BankDetails;
import it.naturtalent.e4.kontakte.ui.EmailDetails;
import it.naturtalent.e4.kontakte.ui.FaxDetails;
import it.naturtalent.e4.kontakte.ui.Messages;
import it.naturtalent.e4.kontakte.ui.MobilDetails;
import it.naturtalent.e4.kontakte.ui.PhoneDetails;
import it.naturtalent.e4.kontakte.ui.WebDetails;
import it.naturtalent.e4.kontakte.ui.dialogs.CategoriesDialog;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

public class ContactDetailsComposite extends Composite
{

	private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());

	private KontakteData kontakteData;
	
	private AddressDataComposite addressDataComposite;
	
	private PhoneDetails phoneDetails;

	private MobilDetails mobilDetails;

	private EmailDetails emailDetails;

	private BankDetails bankDetails;
	
	private FaxDetails faxDetails;
	
	private WebDetails webDetails;	
	
	private StyledText styledTextNotice;

	//private IEventBroker eventBroker;
	private Text txtKategories;
	private String [] categoriesPreferences;
	
	private ContactCategoryModel contactCategoryModel;
	

	// Aenderungen im Details als Aenderung in KontaktData weitermelden
	private IEventBroker eventBroker;
	private EventHandler detailsEventHandler = new EventHandler()
	{
		@Override
		public void handleEvent(Event event)
		{					
			if(StringUtils.equals(event.getTopic(), IKontakteDataModel.KONTAKT_EVENT_ADDRESS_MODIFIED))
			{	
				// Aenderung in KontakteData melden
				if(eventBroker != null)
					eventBroker.post(IKontakteDataModel.KONTAKT_EVENT_KONTAKTE_MODIFIED, kontakteData);				
				return;				
			}
			
			if(StringUtils.equals(event.getTopic(), IKontakteDataModel.KONTAKT_EVENT_DETAILS_MODIFIED))
			{	
				// Aenderung in KontakteData melden
				if(eventBroker != null)
					eventBroker.post(IKontakteDataModel.KONTAKT_EVENT_KONTAKTE_MODIFIED, kontakteData);				
				return;				
			}			
		}
	};
	
	
	
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
				
				// unser Modell speichern (Initialisierung)
				if(kontakteDataModel == null)
					kontakteDataModel = eventModel;

				// ist unser Modell der Ausloeser
				if (kontakteDataModel == eventModel)
				{
					// Kategoriepreferenzen aktualisieren
					String categoryPreferences = (String) kontakteDataModel
							.getModelFactory()
							.getKontaktPreference(
									kontakteDataModel.getCollectionName(),
									IKontakteDataFactory.CATEGORIES_PREFERENCE_TYPE);

					if (StringUtils.isNotEmpty(categoryPreferences))
						categoriesPreferences = StringUtils.split(
								categoryPreferences, ",");
				
					if(contactCategoryModel == null)
					{
						contactCategoryModel = kontakteDataModel.getModelFactory()
								.createContactCategoryModel(
										kontakteDataModel.getCollectionName());						
					}				
				}
			}
		}
	};
*/
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public ContactDetailsComposite(Composite parent, int style)
	{
		super(parent, style);
		addDisposeListener(new DisposeListener()
		{
			public void widgetDisposed(DisposeEvent e)
			{
				// EventBroker abmelden						
				if(eventBroker != null)
					eventBroker.unsubscribe(detailsEventHandler);
												
				toolkit.dispose();
			}
		});
		
		toolkit.adapt(this);
		toolkit.paintBordersFor(this);
		setLayout(new GridLayout(2, false));
		
		Section sctnAddress = toolkit.createSection(this, Section.TITLE_BAR);
		sctnAddress.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		toolkit.paintBordersFor(sctnAddress);
		sctnAddress.setText("Adresse");
		sctnAddress.setExpanded(true);
		
		addressDataComposite = new AddressDataComposite(sctnAddress, SWT.NONE);
		sctnAddress.setClient(addressDataComposite);
		addressDataComposite.setBounds(0, 0, 438, 231);
		toolkit.adapt(addressDataComposite);
		toolkit.paintBordersFor(addressDataComposite);
		
		// Festnetz
		Section sctnPhone = toolkit.createSection(this, Section.TWISTIE | Section.TITLE_BAR);
		phoneDetails = new PhoneDetails(getShell(), sctnPhone, toolkit);
		GridData gd_sctnPhone = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_sctnPhone.widthHint = 230;
		sctnPhone.setLayoutData(gd_sctnPhone);
		sctnPhone.setBounds(0, 0, 117, 23);
		toolkit.paintBordersFor(sctnPhone);
		sctnPhone.setText(Messages.KontakteDialog_sctnPhonesection_text);
		
		// Mobile
		Section sctnMobile = toolkit.createSection(this, Section.TWISTIE | Section.TITLE_BAR);
		mobilDetails = new MobilDetails(getShell(), sctnMobile, toolkit);
		sctnMobile.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		toolkit.paintBordersFor(sctnMobile);
		sctnMobile.setText(Messages.KontakteDialog_sctnMobilfon_text);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		// Fax
		Section sctnFax = toolkit.createSection(this, Section.TWISTIE | Section.TITLE_BAR);
		faxDetails = new FaxDetails(getShell(), sctnFax, toolkit);
		GridData gd_sctnFax = new GridData(SWT.FILL, SWT.FILL, true, false, 2,1);
		gd_sctnFax.heightHint = 92;
		sctnFax.setLayoutData(gd_sctnFax);
		toolkit.paintBordersFor(sctnFax);
		sctnFax.setText(Messages.KontakteDialog_sctnFax_text);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);

		
		// eMail
		Section sctnEmail = toolkit.createSection(this, Section.TWISTIE | Section.TITLE_BAR);
		emailDetails = new EmailDetails(getShell(), sctnEmail, toolkit);
		sctnEmail.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		toolkit.paintBordersFor(sctnEmail);
		sctnEmail.setText(Messages.KontakteDialog_sctnEmail_txtCategories_1);
		
		// Web
		Section sctnWeb = toolkit.createSection(this, Section.TWISTIE | Section.TITLE_BAR);
		sctnWeb.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		webDetails = new WebDetails(getShell(), sctnWeb, toolkit);
		toolkit.paintBordersFor(sctnWeb);
		sctnWeb.setText(Messages.KontakteDialog_sctnWeb_text);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		// Bank
		Section sctnBank = toolkit.createSection(this, Section.TWISTIE | Section.TITLE_BAR);
		bankDetails = new BankDetails(getShell(), sctnBank, toolkit);
		GridData gd_sctnBank = new GridData(SWT.FILL, SWT.FILL, true, false, 2,1);
		gd_sctnBank.heightHint = 92;
		sctnBank.setLayoutData(gd_sctnBank);
		toolkit.paintBordersFor(sctnBank);
		sctnBank.setText(Messages.KontakteDialog_sctnBank_text);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		// Kategorien
		Composite compositeCategories = new Composite(this, SWT.NONE);
		compositeCategories.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		toolkit.adapt(compositeCategories);
		toolkit.paintBordersFor(compositeCategories);
				compositeCategories.setLayout(new GridLayout(3, false));
		
		Label lblKategorie = toolkit.createLabel(compositeCategories, Messages.KontakteDialog_lblKategorie_text, SWT.NONE);
		lblKategorie.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		
		// Categories
		txtKategories = toolkit.createText(compositeCategories, "", SWT.NONE);		
		txtKategories.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
				
		// select Categories
		Button btnCatagory = toolkit.createButton(compositeCategories, Messages.ContactDetailsComposite_btnCatagory_text, SWT.NONE);
		btnCatagory.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				// Kategorieauswahldialog
				CategoriesDialog dialog = new CategoriesDialog(getShell());
				dialog.create();	
				
				// alle Kategorien in denen der Kontakts eingetragen ist
				List<String>categoriesName = null;
				if(contactCategoryModel != null) 
					categoriesName = contactCategoryModel.getContactCategoryNames(kontakteData.getId());
				
				// Kontaktkategorien und Preferenzen an den Dialog uebergeben
				dialog.setValues(categoriesName,categoriesPreferences);
				if(dialog.open() == CategoriesDialog.OK)
				{
					String value = dialog.getValues();					
					txtKategories.setText(StringUtils.isNotEmpty(value) ? value : "");
					
					if(contactCategoryModel != null)
					{
						// Kontakt in den Kategorien eintragen
						String [] names = StringUtils.split(value,",");
						contactCategoryModel.setContactCategories(names, kontakteData.getId());
						contactCategoryModel.saveContactCategories();
					}
				}
			}
		});
		
		// Notizen
		Label lblNoticeLabel = toolkit.createLabel(this, Messages.ContactDetailsComposite_lblNoticeLabel_txtKategories_1, SWT.NONE);
		new Label(this, SWT.NONE);
		
		styledTextNotice = new StyledText(this, SWT.BORDER | SWT.WRAP);
		styledTextNotice.addModifyListener(new ModifyListener()
		{
			public void modifyText(ModifyEvent e)
			{
				if (kontakteData != null)
				{
					String text = styledTextNotice.getText();
					text = StringUtils.isEmpty(text) ? null : text;
					kontakteData.setNotice(text);
					if (eventBroker != null)
						eventBroker
								.post(IKontakteDataModel.KONTAKT_EVENT_KONTAKTE_MODIFIED,
										kontakteData);
				}
			}
		});
		GridData gd_styledTextNotice = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
		gd_styledTextNotice.heightHint = 133;
		styledTextNotice.setLayoutData(gd_styledTextNotice);
		toolkit.adapt(styledTextNotice);
		toolkit.paintBordersFor(styledTextNotice);

	}

	public void setEventBroker(IEventBroker eventBroker)
	{
		this.eventBroker = eventBroker;
		addressDataComposite.setEventBroker(eventBroker);
		phoneDetails.setEventBroker(eventBroker);
		mobilDetails.setEventBroker(eventBroker);
		faxDetails.setEventBroker(eventBroker);
		emailDetails.setEventBroker(eventBroker);
		bankDetails.setEventBroker(eventBroker);
		webDetails.setEventBroker(eventBroker);
		eventBroker.subscribe(IKontakteDataModel.KONTAKT_EVENT+"*",detailsEventHandler);
	}
	
	
	public void setContactCategoryModel(ContactCategoryModel contactCategoryModel)
	{
		this.contactCategoryModel = contactCategoryModel;		
	}
	

	public void setModel(KontakteDataModel kontakteDataModel)
	{
		// Kategoriepreferenzen aktualisieren
		String categoryPreferences = (String) kontakteDataModel
				.getModelFactory()
				.getKontaktPreference(
						kontakteDataModel.getCollectionName(),
						IKontakteDataFactory.CATEGORIES_PREFERENCE_TYPE);

		if (StringUtils.isNotEmpty(categoryPreferences))
			categoriesPreferences = StringUtils.split(
					categoryPreferences, ",");

		// Anredepreferenzen aktualisieren
		addressDataComposite.setKontakteDataModel(kontakteDataModel);
	}

	/*
	public IEventBroker getEventBroker()
	{
		return eventBroker;
	}
	*/

	public void setKontakteData(KontakteData kontakteData)
	{
		if (!isDisposed())
		{
			this.kontakteData = kontakteData;

			addressDataComposite.setEnabled(kontakteData != null);

			addressDataComposite.setAddressData((kontakteData != null) ? kontakteData.getAddress() : null);
			phoneDetails.setValues((kontakteData != null) ? kontakteData.getPhones() : null);
			mobilDetails.setValues((kontakteData != null) ? kontakteData.getMobiles() : null);
			faxDetails.setValues((kontakteData != null) ? kontakteData.getFax() : null);
			emailDetails.setValues((kontakteData != null) ? kontakteData.getEmails() : null);
			webDetails.setValues((kontakteData != null) ? kontakteData.getUrls() : null);
			bankDetails.setBankData((kontakteData != null) ? kontakteData.getBanks() : null);
			
			String notice = "";
			if(kontakteData != null)
			{
				notice = kontakteData.getNotice();
				notice = (StringUtils.isNotEmpty(notice)) ? notice : "";
			}
			styledTextNotice.setText(notice);

			// die zugeordneten Kategorien
			txtKategories.setText("");

			if (kontakteData != null)
			{
				if (contactCategoryModel != null)
				{
					List<String> categoriesNames = contactCategoryModel
							.getContactCategoryNames(kontakteData.getId());
					if (!categoriesNames.isEmpty())
						txtKategories.setText(StringUtils.join(categoriesNames,","));
				}
			}
		}
	}	
	
	
	/*
	public void setPreferences(String anreden)
	{		
		if(addressDataComposite != null)
		{			
			CCombo comboAnrede = addressDataComposite.comboAnrede;
			comboAnrede.removeAll();
			if (StringUtils.isNotEmpty(anreden))
			{
				String[] anredenArray = StringUtils.split(anreden, ',');
				for (String anrede : anredenArray)
					comboAnrede.add(anrede);
			}			
		}	
	}
	*/
	
	
	
	public KontakteData getKontakteData()
	{
		return kontakteData;
	}

	/*
	public static KontakteData createDetailsKontaktData(KontakteData kontakteData)
	{
		KontakteData clone = kontakteData.clone();
		
		if (kontakteData.getAddress() == null)
			kontakteData.setAddress(new AddressData());
			
		if(clone.getPhones() == null)
			clone.setPhones(new ArrayList<String>());

		if(clone.getMobiles() == null)
			clone.setMobiles(new ArrayList<String>());
	
		
		if(clone.getEmails() == null)
			clone.setEmails(new ArrayList<String>());

		
		if(clone.getUrls() == null)
			clone.setUrls(new ArrayList<String>());

		
		if(clone.getBanks() == null)
			clone.setBanks(new ArrayList<BankData>());


		
		return clone;
	}
	*/
	
	

}
