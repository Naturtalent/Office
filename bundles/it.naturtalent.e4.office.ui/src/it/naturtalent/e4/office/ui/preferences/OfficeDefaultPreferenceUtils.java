package it.naturtalent.e4.office.ui.preferences;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

import it.naturtalent.e4.project.ui.emf.ExpImpUtils;

public class OfficeDefaultPreferenceUtils
{
	// Name des OfficePreferenzknoten
	public static final String ROOT_DEFAULTOFFICE_PREFERENCES_NODE = "it.naturtalent.office"; //$NON-NLS-1$

	// Praeferenzname der Signature
	public static final String SIGNATURE_PREFERENCE= "signaturepreference"; //$NON-NLS-1$

	// DefaultOffice Context 
	public static final String DEFAULT_OFFICE_CONTEXT = "defaultofficecontext"; //$NON-NLS-1$
	
	// Praeferenzname des Absenders
	public static final String ABSENDER_PREFERENCE= "absenderpreference"; //$NON-NLS-1$
	
	// Praeferenzname der Preferenz
	public static final String REFERENZ_PREFERENCE= "referenzpreference"; //$NON-NLS-1$

	// Praeferenzname der FootNotes
	public static final String FOOTNOTE_PREFERENCE= "footnotepreference"; //$NON-NLS-1$
	
	// Praeferenzname der Signature
	public static final String TEMPLATE_PREFERENCE= "templatepreference"; //$NON-NLS-1$

	// unter diesem Namen wird der Defaultabsender gespeichert
	//public static final String DEFAULT_TEMPLATENAME = "Defaultvorlagen"; //$NON-NLS-1$
	
	// unter diesem Namen wird der Defaultabsender gespeichert
	public static final String DEFAULT_ABSENDERNAME = "Defaultabsender"; //$NON-NLS-1$
	
	// unter diesem Namen wird die Defaultreferenz gespeichert
	public static final String DEFAULT_REFERENZNAME = "Defaultreferenz"; //$NON-NLS-1$

	// unter diesem Namen wird die Defaultfootnote gespeichert
	public static final String DEFAULT_FOOTNOTENAME = "Defaultfootnote"; //$NON-NLS-1$
	
	// unter diesem Namen wird die Defaultfootnote gespeichert
	public static final String DEFAULT_SIGNATURENAME = "Defaultsignature"; //$NON-NLS-1$


	
	// E4Context DefaultName 
	public static final String E4CONTEXT_DEFAULTNAME = "defaultName"; //$NON-NLS-1$

	
	/**
	 * Mit einem Filedialog die Ziel-Exportdatei auswaehlen.
	 * 
	 * @param preferenceData
	 */
	public static void exportPreference(EObject preferenceData)
	{
		String exportPath = null;
		
		// Filedialog im 'SAVE*-Modus
		Shell shell = Display.getCurrent().getActiveShell();
		FileDialog dlg = new FileDialog(shell, SWT.SAVE);

		// 'xml' - Files filtern
		dlg.setText("Exportverzeichnis"); //$NON-NLS-1$
		dlg.setFilterExtensions(new String[]{"*.xml"}); //$NON-NLS-1$
		dlg.setFilterPath(exportPath);
		
		// Exportpath ueber den Dialog festlegen
		exportPath = dlg.open();
		if(exportPath != null)
			ExpImpUtils.saveEObjectToResource(preferenceData, exportPath);
	}

	/**
	 * Mit einem Filedialog die Quell-Importdatei auswaehlen.
	 * 
	 * @return
	 */
	public static EList<EObject> importPreference()
	{	
		// Filedialog im 'OPEN*-Modus
		Shell shell = Display.getCurrent().getActiveShell();		
		String importPath = null;
		FileDialog dlg = new FileDialog(shell, SWT.OPEN);

		// 'xml' - Files filtern
		dlg.setText("Importverzeichnis");
		dlg.setFilterExtensions(new String[]{"*.xml"}); //$NON-NLS-1$
		dlg.setFilterPath(importPath);//
		
		importPath = dlg.open();
		return (importPath != null) ? ExpImpUtils.loadEObjectFromResource(importPath) : null; 
	}

	
}
