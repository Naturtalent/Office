package it.naturtalent.e4.office.ui;


import it.naturtalent.application.AbstractPreferenceAdapter;
import it.naturtalent.application.IPreferenceAdapter;
import it.naturtalent.application.IPreferenceNode;
import it.naturtalent.e4.office.odf.ODFOfficeDocumentHandler;
import it.naturtalent.e4.preferences.DirectoryEditorComposite;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.osgi.service.prefs.BackingStoreException;

public class OfficeApplicationPreferenceAdapter extends AbstractPreferenceAdapter
{
	private DirectoryEditorComposite directoryEditorComposite;
	
	private Log log = LogFactory.getLog(this.getClass());
	
	
	
	public OfficeApplicationPreferenceAdapter()
	{
		instancePreferenceNode = InstanceScope.INSTANCE.getNode(it.naturtalent.e4.office.Activator.ROOT_OFFICE_PREFERENCES_NODE);
		defaultPreferenceNode = DefaultScope.INSTANCE.getNode(it.naturtalent.e4.office.Activator.ROOT_OFFICE_PREFERENCES_NODE);	
	}

	@Override
	public String getLabel()
	{	
		return "Applikation";
	}

	@Override
	public String getNodePath()
	{
		return "Office";
	}

	@Override
	public void restoreDefaultPressed()
	{
		String value = getDefaultPreference().get(ODFOfficeDocumentHandler.OFFICE_APPLICATION_PREF, null);
		if(StringUtils.isNotEmpty(value))
			directoryEditorComposite.setDirectory(value);
	}

	@Override
	public void appliedPressed()
	{
		String value = directoryEditorComposite.getDirectory();
		if(StringUtils.isNotEmpty(value))
			instancePreferenceNode.put(ODFOfficeDocumentHandler.OFFICE_APPLICATION_PREF, value);
		try
		{
			instancePreferenceNode.flush();
		} catch (BackingStoreException e)
		{
			log.error(e);			
		}
	}

	@Override
	public void okPressed()
	{
		appliedPressed();
	}

	@Override
	public Composite createNodeComposite(IPreferenceNode parentNodeComposite)
	{
		parentNodeComposite.setTitle(getLabel());		
		OfficeApplicationPreferenceComposite comp = new OfficeApplicationPreferenceComposite(
				parentNodeComposite.getParentNode(), SWT.NONE);
		
		directoryEditorComposite = comp.getDirectoryEditorComposite();
		directoryEditorComposite.setLabel("Verzeichnis der Officeanwendung auswählen");
		String value = getInstancePreference().get(ODFOfficeDocumentHandler.OFFICE_APPLICATION_PREF, null);
		if(StringUtils.isEmpty(value))
			value = getDefaultPreference().get(ODFOfficeDocumentHandler.OFFICE_APPLICATION_PREF, null);
		
		if(StringUtils.isNotEmpty(value))
			directoryEditorComposite.setDirectory(value);
		
		return comp;
	}

}
