package it.naturtalent.e4.project.address.ui;

import java.beans.Beans;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.eclipse.osgi.util.NLS;


public class Messages extends NLS
{
	private static final String BUNDLE_NAME = "it.naturtalent.e4.project.address.ui.messages"; //$NON-NLS-1$
	public static String AddressDataComposite_lblAnrede_text;
	public static String AddressDataComposite_group_text;
	public static String AddressDataComposite_btnRadioPrivat_text;
	public static String AddressDataComposite_btnRadioOeffentlich_text;
	public static String AddressDataComposite_lblName_text;
	public static String AddressDataComposite_lblName1_text;
	public static String AddressDataComposite_lblName2_text;
	public static String AddressDataComposite_lblNachname_text;
	public static String AddressDataComposite_lblVorname_text;
	public static String AddressDataComposite_lblOrt_text;
	public static String AddressDataComposite_lblPlz_text;
	public static String AddressDataComposite_lblStrasse_text;
	public static String AddressDataComposite_sctnAddress_text;
	public static String AddressDataComposite_txtOrt_text;
	public static String AddressDataComposite_txtPlz_text;
	public static String CustomerPage_tblclmnName_text;
	public static String CustomerPage_sctnAddressDetails_text;
	public static String CustomerPage_btnAdd_text;
	public static String CustomerPage_btnNewButton_text;
	public static String CustomerPage_btnDelete_text_1;
	public static String CustomerPage_btnNewButton_1_text;
	public static String CustomerPage_Description;
	
	
	public static String CustomerPage_InputDialog_AddressTitle;
	public static String CustomerPage_InputDialog_Message;
	public static String CustomerPage_InputDialog_UndefinedNameError;
	public static String CustomerPage_Title;
	public static String ProjectAddressWizardPage_sctnAddresses_text;
	public static String ProjectAddressWizardPage_lblNewLabel_text;
	public static String ProjectAddressWizardPage_tblclmnPlz_text;
	public static String ProjectAddressWizardPage_lblNewLabel_1_text;
	public static String ProjectAddressWizardPage_mghprlnkImportAddress_text;
	public static String KontakViewDialog_tblclmnName_text;
	public static String KontakViewDialog_tblclmnZusatz1_text;	
	public static String KontakViewDialog_tblclmnPLZ_text;
	public static String KontakViewDialog_tblclmnOrt_text;
	public static String KontakViewDialog_tblclmnStrasse_text;
	public static String ProjectAddressWizardPage_lblNewLabel_1_text_1;
	public static String ProjectAddressWizardPage_sctnNewSection_text;
	public static String ProjectAddressWizardPage_lblNewLabel_1_text_2;
	public static String ProjectAddressWizardPage_mghprlnkExportAddress_text;
	public static String ProjectAddressWizardPage_mghprlnkOpenKontakte_text;
	public static String AddressDataComposite_lblNotice_text;
	public static String ProjectAddressWizardPage_Titel_Kontakt;
	public static String ProjectAddressWizardPage_Titel_Kontakt_exist;
	public static String ProjectAddressWizardPage_Kontakt_stored;
	public static String SelectContactDialog_ContactTitle;
	public static String SelectContactDialog_MessageSavePersist;
	public static String SelectContactDialog_TitleMessage;
	public static String SelectKontaktDialog_this_title;
	public static String SelectKontaktDialog_this_message;
	public static String ProjectAddressWizardPage_mghprlnkImportAddress_toolTipText;


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
