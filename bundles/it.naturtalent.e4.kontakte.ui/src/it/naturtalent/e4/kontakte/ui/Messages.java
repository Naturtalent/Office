package it.naturtalent.e4.kontakte.ui;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "it.naturtalent.e4.kontakte.ui.messages"; //$NON-NLS-1$
	public static String KontakteDialog_sctnAddress_text;
	public static String KontakteDialog_sctnPhone_text;
	public static String KontakteDialog_sctnEmail_text;
	public static String KontakteDialog_sctnFax_text;
	public static String KontakteDialog_sctnMobile_text;

	public static String KontakteDialog_sctnPhonesection_text;
	public static String KontakteDialog_sctnMobilfon_text;	
	public static String KontakteDialog_lblNotizen_text;
	public static String KontakteDialog_sctnBank_text;
	public static String KontakteDialog_tblBank_text;
	public static String KontakteDialog_tblclmnKonto_text;
	public static String KontakteDialog_tblclmnBlz_text;	

	public static String EditBankenDialog_this_title;
	public static String EditBankenDialog_this_message;
	public static String EmailDetails_DialogLabel;
	public static String EmailDetails_SectionLabel;
	public static String FaxDetails_DialogLabel;
	public static String FaxDetails_SectionLabel;
	public static String KontakteView_deleteAction;
	public static String KontakteView_MessageDialogConfirmKontakte;
	public static String KontakteView_NameColumnName;
	public static String KontakteView_NameZusatzColumnName;
	public static String KontakteView_OrtColumnName;
	public static String KontakteView_PLZColumnName;
	public static String KontakteView_SectionNewTitle;
	public static String KontakteView_StrasseColumnName;
	public static String KontakteView_TooltipDelete;
	public static String KontakteView_TooltipEdit;
	public static String KontakteView_TooltipNew;
	public static String KontakteView_TooltipSelectDB;
	public static String KontakteView_TooltipKategoriesFilter;
	public static String KontakteDialog_this_title;
	public static String KontakteDialog_this_message;
	public static String AddressDataComposite_btnRadioOeffentlich_text;
	public static String AddressDataComposite_lblStrasse_text;
	public static String AddressDataComposite_lblAnrede_text;
	public static String AddressDataComposite_lblName_text;
	public static String AddressDataComposite_lblName1_text;
	public static String AddressDataComposite_lblName2_text;
	public static String AddressDataComposite_lblPlz_text;
	public static String AddressDataComposite_lblOrt_text;
	public static String AddressDataComposite_controlDecoration_descriptionText;
	public static String AddressDataComposite_btnRadioPrivat_text;
	public static String AddressDataComposite_grpAdresstyp_text;
	public static String AddressDataComposite_lblNachname_text;
	public static String AddressDataComposite_lblVorname_text;
	public static String BankDetails_BicLabel;
	public static String BankDetails_BLZLabel;
	public static String BankDetails_IBANLABEL;
	public static String BankDetails_Institut;
	public static String BankDetails_KtoNr;
	public static String DefaultKontaktDetails_EmptyField;
	public static String DefaultKontaktDetails_InputLabel;
	public static String DefaultKontaktDetails_Label;
	public static String DeleteAction_ContactLabel;
	public static String DeleteAction_DeleteQuestion;
	public static String KontakteDialog_tblclmn_Bic_text_1;
	public static String KontakteDialog_tblclmn_IBAN_text_1;
	public static String ExportDestilnationComposite_lblExportDir_text;
	public static String ExportDestilnationComposite_btnSelectDir_text;
	public static String ExportDestilnationComposite_lblDestFile_text;
	public static String ExportKontakteAdapter_Label;
	public static String ExportKontakteAdapter_Message;
	public static String ExportKontankteDialog_this_message;
	public static String ExportKontankteDialog_this_title;
	public static String ExportDestinationComposite_controlDecorationDestDir_descriptionText;
	public static String ExportDestinationComposite_controlDecorationDestFile_descriptionText;
	public static String ExportDestinationComposite_ExportDialogLabel;
	public static String ExportKontankteDialog_btnSelectAll_text;
	public static String ExportKontankteDialog_btnNoSelect_text;
	public static String ExportKontankteDialog_tblclmnName_text;
	public static String ExportKontakteDialog_ExportErrorMessageLabel;
	public static String ExportKontakteDialog_ErrorExport;
	public static String ExportKontakteDialog_OverwriteLabel;
	public static String ExportKontakteDialog_OverwriteMessage;
	public static String ImportKontakteDialog_CheckOverwriteLabel;	
	public static String ImportKontakteDialog_ColumnNameLabel;
	public static String ImportKontakteDialog_ErrorImport;
	public static String ImportKontakteDialog_ImprtErrorMessageLabel;
	public static String ImportKontakteDialog_LabelSelect;
	public static String ImportKontakteDialog_LableImportPath;
	public static String ImportKontakteDialog_LableSource;
	public static String ImportKontakteDialog_Message;
	public static String ImportKontakteDialog_NoSelectLabel;
	public static String ImportKontakteDialog_SelectAllLabel;
	public static String ImportKontakteDialog_Title;
	public static String NewKontaktWizardAdapter_KontaktLabel;
	public static String NewKontaktWizardAdapter_KontaktMessage;
	public static String KontakteView_sctnNewSection_description;		

	public static String KontakteDialog_tblclmnMail_text;

	public static String KontakteDialog_sctnEmail_txtCategories_1;
	public static String MobilDetails_DialogLabel;
	public static String MobilDetails_SectionLabel;
	public static String PhoneDetails_DialogLabel;
	public static String PhoneDetails_SectionLabel;
	public static String KontakteDialog_sctnWeb_text;
	public static String WebDetails_DialogLabel;
	public static String WebDetails_SectilonLabel;
	public static String KontakteDialog_lblCategories_text;
	public static String KontakteDialog_btnCategories_text;
	public static String KontakteDialog_lblKategorie_text;	
	public static String ContactDetailsComposite_lblNotice_text;
	public static String ContactDetailsComposite_lblCatagories_text;
	public static String ContactDetailsComposite_lblNewLabel_text;
	public static String ContactDetailsComposite_lblNoticeLabel_txtKategories_1;
	public static String ContactDetailsComposite_btnCatagory_text;
	public static String ContactDetailsDialog_this_title;
	public static String ContactDetailsDialog_this_message;
	
	
	////////////////////////////////////////////////////////////////////////////
	//
	// Constructor
	//
	////////////////////////////////////////////////////////////////////////////
	private Messages() {
		// do not instantiate
	}
	////////////////////////////////////////////////////////////////////////////
	//
	// Class initialization
	//
	////////////////////////////////////////////////////////////////////////////
	static {
		// load message values from bundle file
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}
}
