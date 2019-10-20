package it.naturtalent.e4.office.ui.preferences;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

import it.naturtalent.application.IPreferenceNode;
import it.naturtalent.e4.office.ui.Activator;
import it.naturtalent.e4.office.ui.ODFDefaultWriteAdapter;
import it.naturtalent.e4.office.ui.OfficeUtils;
import it.naturtalent.e4.preferences.AbstractPreferenceAdapter;
import it.naturtalent.e4.project.ui.emf.ExpImpUtils;
import it.naturtalent.office.model.address.Absender;
import it.naturtalent.office.model.address.AddressPackage;
import it.naturtalent.office.model.address.Referenz;
import it.naturtalent.office.model.address.Sender;

/**
 * Adapter zur Verwaltung der Vorlagen (Layouts)
 * 
 * Eine Praeferenzliste (Checkliste) mit den Namen (vom Path separierter Name) der Vorlagen.
 * Der Path der Vorlagen wird als Praeferenz gespeichert.   
 * 
 * @author dieter
 *
 */
public class OfficeTemplatePreferenceAdapter extends AbstractPreferenceAdapter
{
	// UI der Template-Praeferenzliste
	protected OfficeTemplatePreferenceComposite templateComposite;
	
	
	
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
		
		// Absender UI
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
        		{
        			//templateComposite.
        			//ExpImpUtils.saveEObjectToResource(preferenceData, exportPath);
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
            }
        });
	}
	
	// realisiert die zum Import anstehenden Absender
	protected void postImport(List<Absender>importedAbsenderList)
	{
		
	}


	/*
	 * Canceln der Eingabe
	 */
	@Override
	public void cancelPressed()
	{
	}
	

}
