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
	
	// DefaultOffice Context 
	public static final String DEFAULT_OFFICE_CONTEXT = "defaultofficecontext"; //$NON-NLS-1$
	
	// Preferenzname des Absenders
	public static final String ABSENDER_PREFERENCE= "absenderpreference"; //$NON-NLS-1$
	
	// unter diesem Namen wird der Defautabsender gespeichert
	public static final String DEFAULT_ABSENDERNAME = "Defaultabsender"; //$NON-NLS-1$
	
	// E4Context DefaultName 
	public static final String E4CONTEXT_DEFAULTNAME = "defaultName"; //$NON-NLS-1$

	
	public static void exportPreference(EObject preferenceData)
	{
		String exportPath = null;
		
		// Filedialog im 'SAVE*-Modus
		Shell shell = Display.getCurrent().getActiveShell();
		FileDialog dlg = new FileDialog(shell, SWT.SAVE);

		// 'xml' - Files filtern
		dlg.setText("Importverzeichnis"); //$NON-NLS-1$
		dlg.setFilterExtensions(new String[]{"*.xml"}); //$NON-NLS-1$
		dlg.setFilterPath(exportPath);
		
		// Exportpath ueber den Dialog festlegen
		exportPath = dlg.open();
		if(exportPath != null)
			ExpImpUtils.saveEObjectToResource(preferenceData, exportPath);
	}

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
