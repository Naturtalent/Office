package it.naturtalent.e4.office.ui;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "it.naturtalent.e4.office.ui.messages"; //$NON-NLS-1$
	
	public static String TextmoduleImportTitle;
	
	public static String DeleteTextModuleAction_DeleteDialogMessage;
	public static String DeleteTextModuleAction_DeleteTextmoduleDialogMessage;
	public static String DeleteTextModuleAction_DeleteDialogTitle;

	public static String WriteWizardComposite_AutoFileNameTwoArgs;
	public static String WriteWizardComposite_AutoFileNameOneArgs;
	public static String TextmoduleDialog_btnAdd_toolTipText;
	public static String TextmoduleDialog_btnDelete_toolTipText;
	public static String TextmoduleDialog_btnEdit_toolTipText;
	public static String TextmoduleDialog_btnForward_toolTipText;
	public static String TextmoduleDialog_btnBackward_toolTipText;
	public static String TextbausteinePart_storeModified;

	public static String TextmoduleImportDialog_lblURL_text;
	public static String TextmoduleImportDialog_btnURLLoad_text;
	public static String TextmoduleImportDialog_btnUrlSelcet_text;
	public static String TextmoduleImportDialog_lblDestDir_text;
	public static String TextmoduleImportDialog_btnPathSelect_text;
	public static String TextmoduleImportDialog_btnPathLoad_text;
	public static String TextmoduleImportDialog_this_title;
	public static String TextmoduleImportDialog_btnCheckButton_text;
	public static String TextModuleImportComposite_btnNewButton_text;
	public static String TextModuleImportComposite_trclmnNewColumn_text;
	public static String TextModuleImportComposite_btnNewButton_textFileName_1;
	public static String TextModuleImportComposite_btnNewButton_textFileName_2;	
	public static String TextModuleImportComposite_btnCheckButton_text;
	public static String TextModuleExportComposite_lblDestFile_text;
	
	public static String TextmoduleImport_ERROR_message;
	public static String ContentWizardGroup_mntmCopy_text;
	public static String ContentWizardGroup_mntmPaste_text;
	public static String LetterOfficeWizardComposite_EmptyLetterName;

	public static String LetterOfficeWizardComposite_ExistingFileError;

	public static String LetterOfficeWizardComposite_LabelSelect;

	public static String LetterOfficeWizardComposite_MissingDirectoryErraor;

	public static String LetterOfficeWizardComposite_MissingFilename;

	public static String LetterOfficeWizardComposite_NoTemplateError;

	public static String LetterOfficeWizardComposite_TEMPORAER_CONTAINER_MARKER;

	public static String OfficeApplicationPreferenceAdapter_ApplicationReferenceLabel;

	public static String OfficeApplicationPreferenceAdapter_JPIPEnotFoundError;

	public static String OfficeApplicationPreferenceAdapter_OfficeReferenceNode;

	public static String OfficeWizardComposite_lblContainer_text;
	public static String OfficeWizardComposite_lblFile_text;
	public static String OfficeWizardComposite_lblLayout_text;
	public static String OfficeProfileImportComposite_tblclmnProfile_text;
	public static String OfficeProfileExportDialog_this_title;
	public static String OfficeProfileExportDialog_this_message;
	public static String OfficeProfileExportDialog_exportDirLabel_text;
	public static String OfficeProfileExportDialog_lblFile_text;
	public static String OfficeProfileExportDialog_btnSelectDir_text;
	public static String OfficeProfileExportDialog_controlDecorationDir_descriptionText;
	public static String OfficeProfileExportDialog_controlDecorationFile_descriptionText;

	public static String ProfileLetterWizardPage_ProfileOpenFile;
	public static String LetterAddressWizardPage_mghprlnkDB_text;
	public static String LetterAddressWizardPage_mghprlnkProjectAddress_text;

	public static String NewLetterAdapter_Message;
	public static String SelectWriteAdapter_lblAvailableAdapter_text;
	public static String SelectWriteAdapter_this_title;
	public static String SelectWriteAdapter_this_message;

	
	////////////////////////
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
