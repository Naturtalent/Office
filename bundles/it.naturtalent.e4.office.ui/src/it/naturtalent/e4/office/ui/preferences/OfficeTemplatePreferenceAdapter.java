package it.naturtalent.e4.office.ui.preferences;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.e4.ui.internal.workbench.swt.WorkbenchSWTActivator;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.forms.widgets.Hyperlink;

import it.naturtalent.application.IPreferenceNode;
import it.naturtalent.e4.office.ui.ODFDefaultWriteAdapter;
import it.naturtalent.e4.preferences.AbstractPreferenceAdapter;

/**
 * Adapter zur Verwaltung der Vorlagen (Layouts)
 * 
 * Eine Praeferenzliste (Checkliste) mit den Namen (vom Path separierter Name) der Vorlagen.
 * Der Name der Vorlagen wird als Praeferenz gespeichert.   
 * 
 * @author dieter
 *
 */
public class OfficeTemplatePreferenceAdapter extends AbstractPreferenceAdapter
{
	// UI der Template-Praeferenzliste
	protected OfficeTemplatePreferenceComposite templateComposite;
	
	IDialogSettings settings = WorkbenchSWTActivator.getDefault().getDialogSettings();
	private static final String EXPORT_VORLAGEN_PATH = "exportvorlagenpath";
	
	@Override
	public String getNodePath()
	{
		return "Office/Default"; //$NON-NLS-N$
	}
	
	@Override
	public String getLabel()
	{
		return "Vorlagen"; //$NON-NLS-N$
	}

	@Override
	public void restoreDefaultPressed()
	{
		templateComposite.doRestoreDefaultPressed();
	}

	/*
	 * Festschreiben der Eingabe
	 */
	@Override
	public void appliedPressed()
	{		
		templateComposite.doApplied();
	}

	@Override
	public Composite createNodeComposite(IPreferenceNode referenceNode)
	{
		// Composite beschriften
		referenceNode.setTitle(getLabel());
		
		// Template UI Composite
		templateComposite = new OfficeTemplatePreferenceComposite(referenceNode.getParentNode(), SWT.NONE);
		
		init(templateComposite);
				
		return templateComposite;
	}
	
	protected void init(Composite composite)
	{
		// einen Infotext hinzufuegen
		Label label = new Label(composite, SWT.NONE);
		label.setText("Vorlagen verwalten"); //$NON-NLS-N$;
		
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
		Hyperlink hyperlinkExport = new Hyperlink(composite, SWT.NONE);
		hyperlinkExport.setText("exportieren"); //$NON-NLS-N$
		hyperlinkExport.setToolTipText("Vorlagen exportieren"); //$NON-NLS-N$
		hyperlinkExport.addListener(SWT.MouseDown, new Listener() {

            @Override
            public void handleEvent(Event event) 
            {
            	String exportDir;
        		Shell shell = Display.getCurrent().getActiveShell();
        		DirectoryDialog dlg = new DirectoryDialog(shell, SWT.SAVE);
        		dlg.setText("Export Office-Vorlagen"); //$NON-NLS-N$
				dlg.setMessage("ein Verzeichnis zum Exportieren der Vorlagen auswählen"); //$NON-NLS-N$
				String expostSettingPath = getExportPathSetting(settings);
				dlg.setFilterPath(expostSettingPath);
			
        		// Exportpath ueber den Dialog festlegen
        		exportDir = dlg.open();
        		if(exportDir != null)
        		{        			
        			try
					{        				
        				IOFileFilter suffixFilter = FileFilterUtils.or(FileFilterUtils
        						.suffixFileFilter(ODFDefaultWriteAdapter.OFFICEWRITEDOCUMENT_EXTENSION));        				
        				File wsTeplateDir = getTemporaryPath();
						FileUtils.copyDirectory(wsTeplateDir, new File(exportDir),suffixFilter);
						setExportPathSetting(settings, exportDir);
					} catch (IOException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
        		}

            }
        });
		
		new Label(composite, SWT.NONE);
				
		Hyperlink hyperlinkImport = new Hyperlink(composite, SWT.NONE);
		hyperlinkImport.setText("importieren"); //$NON-NLS-N$
		hyperlinkImport.setToolTipText("Vorlagen importieren"); //$NON-NLS-N$
		hyperlinkImport.addListener(SWT.MouseDown, new Listener() {

            @Override
            public void handleEvent(Event event) 
            {
            	String exportDir;
        		Shell shell = Display.getCurrent().getActiveShell();
        		DirectoryDialog dlg = new DirectoryDialog(shell, SWT.SAVE);
        		dlg.setText("Import Office-Vorlagen"); //$NON-NLS-N$
				dlg.setMessage("ein Verzeichnis zum Importieren der Vorlagen auswählen"); //$NON-NLS-N$
				String expostSettingPath = getExportPathSetting(settings);
				dlg.setFilterPath(expostSettingPath);

        		// Exportpath ueber den Dialog festlegen
        		exportDir = dlg.open();
        		if(exportDir != null)
				{
					try
					{
						IOFileFilter suffixFilter = FileFilterUtils.or(FileFilterUtils.suffixFileFilter(
								ODFDefaultWriteAdapter.OFFICEWRITEDOCUMENT_EXTENSION));
						File wsTeplateDir = getTemporaryPath();
						FileUtils.copyDirectory(new File(exportDir),wsTeplateDir,suffixFilter);
					} catch (IOException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
            }
        });
	}
	
	/*
	 * Export-,Importpfad aus den Dialogsetting lesen 
	 */
	protected String getExportPathSetting(IDialogSettings settings)
	{		
		String settingPath = settings.get(EXPORT_VORLAGEN_PATH);
		return StringUtils.isNotEmpty(settingPath) ? settingPath : System.getProperty("user.dir");
	}

	/*
	 * Export-,Importpfad in Dialogsetting schreiben 
	 */
	protected void setExportPathSetting(IDialogSettings settings, String path)
	{		
		settings.put(EXPORT_VORLAGEN_PATH, path);		
	}
	
	/*
	 * Rueckgabe des Vorlagenverzeichnis
	 */
	protected File getTemporaryPath()
	{
		return templateComposite.getTemporaryDir();
	}

	/*
	 * Canceln der Eingabe
	 */
	@Override
	public void cancelPressed()
	{
	}
	

}
