package it.naturtalent.e4.kontakte.ui.dialogs;

import it.naturtalent.e4.kontakte.BankData;
import it.naturtalent.e4.kontakte.ContactCategoryModel;
import it.naturtalent.e4.kontakte.IKontakteDataFactory;
import it.naturtalent.e4.kontakte.KontakteData;
import it.naturtalent.e4.kontakte.ui.AddressDataComposite;
import it.naturtalent.e4.kontakte.ui.BankDetails;
import it.naturtalent.e4.kontakte.ui.EmailDetails;
import it.naturtalent.e4.kontakte.ui.KontakteUIProcessor;
import it.naturtalent.e4.kontakte.ui.Messages;
import it.naturtalent.e4.kontakte.ui.MobilDetails;
import it.naturtalent.e4.kontakte.ui.PhoneDetails;
import it.naturtalent.e4.kontakte.ui.WebDetails;
import it.naturtalent.e4.project.address.AddressData;
import it.naturtalent.e4.project.address.AddressUtils;
import it.naturtalent.icons.core.Icon;
import it.naturtalent.icons.core.IconSize;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.conversion.Converter;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.internal.workbench.swt.WorkbenchSWTActivator;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

public class KontakteDialog extends TitleAreaDialog
{
	private DataBindingContext m_bindingContext;

	private final FormToolkit formToolkit = new FormToolkit(
			Display.getDefault());

	private Shell shell;

	private KontakteData kontaktData;

	private PhoneDetails phoneDetails;

	private MobilDetails mobilDetails;

	private EmailDetails emailDetails;

	private WebDetails webDetails;

	private BankDetails bankDetails;

	private AddressDataComposite addressDataComposite;

	private Text txtNotice;

	private Button btnOk;

	private String fromClipboard;
	
	private String [] categoriesPreferences;
	
	private ContactCategoryModel contactCategoryModel;
		
	// String ("item1,item2,item3") in Array[3]
	public static class ToCatagoriesDataConverter extends Converter
	{
		public ToCatagoriesDataConverter()
		{
			super(String.class, String [].class);
		}

		@Override
		public Object convert(Object fromObject)
		{			
			return StringUtils.split((String) fromObject,",");
		}
	}

	// Array[3] in String ("item1,item2,item3") verwandeln
	public static class ToCatagoriesWidgetConverter extends Converter
	{
		public ToCatagoriesWidgetConverter()
		{
			super(String [].class, String.class);		
		}

		@Override
		public Object convert(Object fromObject)
		{			
			return StringUtils.join((String[]) fromObject,",");
		}
	}


	@Inject
	@Optional
	public IEclipseContext context;

	private Action pasteAdressAction = new Action()
	{
		@Override
		public void run()
		{
			if (StringUtils.isNotEmpty(fromClipboard))
			{
				AddressData addressData = AddressUtils
						.addressDataFromClipboard(fromClipboard);
				kontaktData.setAddress(addressData);
				addressDataComposite.setAddressData(addressData);
			}
		}
	};

	private Text txtCategories;
	{
		pasteAdressAction.setImageDescriptor(Icon.MENU_PASTE.getImageDescriptor(IconSize._16x16_DefaultIconSize));
		pasteAdressAction.setToolTipText("Adresse aus Zwischablage einfügen");
		pasteAdressAction.setEnabled(false);
	}

	/**
	 * Create the dialog.
	 * 
	 * @param parentShell
	 * @wbp.parser.constructor
	 */

	public KontakteDialog()
	{
		super(Display.getDefault().getActiveShell());
	}

	/**
	 * Create the dialog.
	 * 
	 * @param parentShell
	 */
	public KontakteDialog(Shell parentShell)
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
		setMessage(Messages.KontakteDialog_this_message);
		setTitle(Messages.KontakteDialog_this_title);
		this.shell = parent.getShell();
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setLayout(new GridLayout(2, false));
		container.setLayoutData(new GridData(GridData.FILL_BOTH));

		Section sctnAddress = formToolkit.createSection(container,
				Section.TITLE_BAR);
		sctnAddress.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 2, 1));
		formToolkit.paintBordersFor(sctnAddress);
		sctnAddress.setText(Messages.KontakteDialog_sctnAddress_text);
		createAdressSectionToolbar(sctnAddress);

		// Adressdaten Composite erzeugen
		addressDataComposite = new AddressDataComposite(sctnAddress, SWT.NONE);
		formToolkit.adapt(addressDataComposite);
		formToolkit.paintBordersFor(addressDataComposite);
		sctnAddress.setClient(addressDataComposite);

		// Telefon
		Section sctnPhone = formToolkit.createSection(container,
				Section.TWISTIE | Section.TITLE_BAR);
		phoneDetails = new PhoneDetails(shell, sctnPhone, formToolkit);
		GridData gd_sctnPhone = new GridData(SWT.FILL, SWT.FILL, true, false,
				1, 1);
		gd_sctnPhone.heightHint = 110;
		sctnPhone.setLayoutData(gd_sctnPhone);
		formToolkit.paintBordersFor(sctnPhone);
		sctnPhone.setText(Messages.KontakteDialog_sctnPhonesection_text);
		sctnPhone.setExpanded(true);

		// Mobilfone
		Section sctnMobilfon = formToolkit.createSection(container,
				Section.TWISTIE | Section.TITLE_BAR);
		mobilDetails = new MobilDetails(shell, sctnMobilfon, formToolkit);
		GridData gd_sctnMobilfon = new GridData(SWT.FILL, SWT.FILL, true,
				false, 1, 1);
		gd_sctnMobilfon.heightHint = 110;
		sctnMobilfon.setLayoutData(gd_sctnMobilfon);
		formToolkit.paintBordersFor(sctnMobilfon);
		sctnMobilfon.setText(Messages.KontakteDialog_sctnMobilfon_text);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);

		// eMail
		Section sctnEmail = formToolkit.createSection(container,
				Section.TWISTIE | Section.TITLE_BAR);
		emailDetails = new EmailDetails(shell, sctnEmail, formToolkit);
		GridData gd_sctnEmail = new GridData(SWT.FILL, SWT.CENTER, true, false,
				1, 1);
		gd_sctnEmail.heightHint = 162;
		sctnEmail.setLayoutData(gd_sctnEmail);
		formToolkit.paintBordersFor(sctnEmail);
		sctnEmail.setText(Messages.KontakteDialog_sctnEmail_txtCategories_1);

		// Web
		Section sctnWeb = formToolkit.createSection(container, Section.TWISTIE
				| Section.TITLE_BAR);
		webDetails = new WebDetails(shell, sctnWeb, formToolkit);
		GridData gd_sctnWeb = new GridData(SWT.FILL, SWT.CENTER, true, false,
				1, 1);
		gd_sctnWeb.heightHint = 162;
		sctnWeb.setLayoutData(gd_sctnWeb);
		formToolkit.paintBordersFor(sctnWeb);
		sctnWeb.setText(Messages.KontakteDialog_sctnWeb_text);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);

		// Bank
		Section sctnBank = formToolkit.createSection(container, Section.TWISTIE
				| Section.TITLE_BAR);
		bankDetails = new BankDetails(shell, sctnBank, formToolkit);
		GridData gd_sctnBank = new GridData(SWT.FILL, SWT.FILL, true, false, 2,
				1);
		gd_sctnBank.heightHint = 92;
		sctnBank.setLayoutData(gd_sctnBank);
		formToolkit.paintBordersFor(sctnBank);
		sctnBank.setText(Messages.KontakteDialog_sctnBank_text);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);

		Composite composite = formToolkit.createComposite(container, SWT.NONE);						
		composite.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));		
		composite.setLayout(new GridLayout(3, false));
		composite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 2, 1));
		formToolkit.paintBordersFor(composite);
		
		Label lblKategorie = formToolkit.createLabel(composite, Messages.KontakteDialog_lblKategorie_text, SWT.NONE);
		lblKategorie.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));
		lblKategorie.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));

		txtCategories = formToolkit.createText(composite, "", SWT.NONE);
		txtCategories.setEditable(false);
		txtCategories.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				true, 1, 1));

		Button btnCategories = formToolkit.createButton(composite,
				Messages.KontakteDialog_btnCategories_text, SWT.NONE);
		btnCategories.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				CategoriesDialog dialog = new CategoriesDialog(getShell());
				dialog.create();	
				
				List<String>categoriesName = null;
				if(contactCategoryModel != null) 
					categoriesName = contactCategoryModel.getContactCategoryNames(kontaktData.getId());

				
				dialog.setValues(categoriesName,categoriesPreferences);
				if(dialog.open() == CategoriesDialog.OK)
				{
					String value = dialog.getValues();
					if(StringUtils.isNotEmpty(value))
						txtCategories.setText(value);
					
					if(contactCategoryModel != null)
					{
						String [] names = StringUtils.split(value,",");
						contactCategoryModel.setContactCategories(names, kontaktData.getId());
					}

				}
			}
		});

		Label lblNotizen = formToolkit.createLabel(container,
				Messages.KontakteDialog_lblNotizen_text, SWT.NONE);
		lblNotizen.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));
		new Label(container, SWT.NONE);

		txtNotice = formToolkit.createText(container, "", SWT.MULTI);
		GridData gd_txtNotice = new GridData(SWT.FILL, SWT.CENTER, true, false,
				2, 1);
		gd_txtNotice.heightHint = 68;
		txtNotice.setLayoutData(gd_txtNotice);

		initWidgets();

		return area;
	}

	private void createAdressSectionToolbar(Section section)
	{
		ToolBarManager toolBarManager = new ToolBarManager(SWT.FLAT);
		ToolBar toolbar = toolBarManager.createControl(section);
		final Cursor handCursor = new Cursor(Display.getCurrent(),
				SWT.CURSOR_HAND);
		toolbar.setCursor(handCursor);
		// Cursor needs to be explicitly disposed
		toolbar.addDisposeListener(new DisposeListener()
		{
			public void widgetDisposed(DisposeEvent e)
			{
				if (handCursor.isDisposed() == false)
				{
					handCursor.dispose();
				}
			}
		});
		toolBarManager.add(pasteAdressAction);
		toolBarManager.update(true);
		section.setTextClient(toolbar);
	}

	private void initWidgets()
	{
		Clipboard clipboard = new Clipboard(Display.getDefault());
		String string = (String) clipboard.getContents(TextTransfer
				.getInstance());
		clipboard.dispose();

		if (StringUtils.startsWith(string, AddressData.TYPE_PUBLIC)
				|| StringUtils.startsWith(string, AddressData.TYPE_PRIVATE))
		{
			fromClipboard = string;
			pasteAdressAction.setEnabled(true);
		}
	}
	
	public void setAnredenPreferences(String anreden)
	{		
		if(addressDataComposite != null)
		{
			CCombo comboAnrede = addressDataComposite.comboAnrede;
			if (StringUtils.isNotEmpty(anreden))
			{
				String[] anredenArray = StringUtils.split(anreden, ',');
				for (String anrede : anredenArray)
					comboAnrede.add(anrede);
			}	
			else
			{
				// Default Preferencen
				IKontakteDataFactory kontakteDataFaktory = KontakteUIProcessor.getKontakteDataFactory();
				if(kontakteDataFaktory != null)
				{
					anreden = (String) kontakteDataFaktory
							.getKontaktPreference(
									IKontakteDataFactory.KONTAKTEDATA_COLLECTION_NAME,
									IKontakteDataFactory.ANREDEN_PREFERENCE_TYPE);
					String[] anredenArray = StringUtils.split(anreden, ',');
					for (String anrede : anredenArray)
						comboAnrede.add(anrede);					
				}			
			}
		}			
	}
	
	public void setCategoriesPreferences(String categoriesPreferences)
	{		
		this.categoriesPreferences = StringUtils.split(categoriesPreferences,",");
	}
	
	

	/**
	 * Im 'ContactCategoryModel' ist festgelegt, welchen Kategorien der Kontakt zugeordnet ist.
	 * 
	 * @param contactCategoryModel
	 */
	public void setContactCategoryModel(ContactCategoryModel contactCategoryModel)
	{
		this.contactCategoryModel = contactCategoryModel;			
	}

	@Override
	protected void okPressed()
	{
		/*
		Set<String> set = new HashSet<String>();
		String[] anreden = addressDataComposite.comboAnrede.getItems();

		String txtAnrede = addressDataComposite.comboAnrede.getText();
		if (StringUtils.isNotEmpty(txtAnrede)
				&& !ArrayUtils.contains(anreden, txtAnrede))
		{
			anreden = ArrayUtils.add(anreden, txtAnrede);
			Preferences node = InstanceScope.INSTANCE
					.getNode(KontaktePreferences.ROOT_KONTAKT_PREFERENCES_NODE);
			node.put(KontaktePreferences.PREFERNCE_ADDRESS_ANREDEN_KEY,
					StringUtils.join(anreden, ","));
		}
		*/

		super.okPressed();
	}

	/**
	 * Create contents of the button bar.
	 * 
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent)
	{
		btnOk = createButton(parent, IDialogConstants.OK_ID,
				IDialogConstants.OK_LABEL, true);

		// zugriff auf OkButton auch im Adressdaten Composite
		if (addressDataComposite != null)
			AddressDataComposite.setBtnOk(btnOk);

		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
		m_bindingContext = initDataBindings();
	}

	public void setData(KontakteData kontakteData)
	{
		if (m_bindingContext != null)
			m_bindingContext.dispose();

		// Adresse
		this.kontaktData = (kontakteData == null) ? new KontakteData()
				: kontakteData;
		if (this.kontaktData.getAddress() == null)
			this.kontaktData.setAddress(new AddressData());
		addressDataComposite.setAddressData(kontakteData.getAddress());

		// phones
		this.kontaktData
				.setPhones((this.kontaktData.getPhones() == null) ? new ArrayList<String>()
						: this.kontaktData.getPhones());
		phoneDetails.setValues(this.kontaktData.getPhones());

		// mobiles
		this.kontaktData
				.setMobiles((this.kontaktData.getMobiles() == null) ? new ArrayList<String>()
						: this.kontaktData.getMobiles());
		mobilDetails.setValues(this.kontaktData.getMobiles());

		// emails
		this.kontaktData
				.setEmails((this.kontaktData.getEmails() == null) ? new ArrayList<String>()
						: this.kontaktData.getEmails());
		emailDetails.setValues(this.kontaktData.getEmails());

		// urls
		this.kontaktData
				.setUrls((this.kontaktData.getUrls() == null) ? new ArrayList<String>()
						: this.kontaktData.getUrls());
		webDetails.setValues(this.kontaktData.getUrls());

		// Bankdaten
		this.kontaktData
				.setBanks((this.kontaktData.getBanks() == null) ? new ArrayList<BankData>()
						: this.kontaktData.getBanks());
		bankDetails.setBankData(this.kontaktData.getBanks());
		
		if(contactCategoryModel != null)
		{
			List<String>names = contactCategoryModel.getContactCategoryNames(kontaktData.getId());
			txtCategories.setText(StringUtils.join(names,","));
		}
					
		m_bindingContext = initDataBindings();
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize()
	{
		return new Point(585, 849);
	}
	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();
		//
		IObservableValue observeTextTxtNoticeObserveWidget = WidgetProperties.text(SWT.Modify).observe(txtNotice);
		IObservableValue noticeKontaktDataObserveValue = BeanProperties.value("notice").observe(kontaktData);
		bindingContext.bindValue(observeTextTxtNoticeObserveWidget, noticeKontaktDataObserveValue, null, null);
		//
		return bindingContext;
	}
}
