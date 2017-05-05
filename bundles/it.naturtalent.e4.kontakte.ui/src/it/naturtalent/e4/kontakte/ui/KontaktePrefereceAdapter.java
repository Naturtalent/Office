package it.naturtalent.e4.kontakte.ui;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.osgi.service.prefs.BackingStoreException;

import it.naturtalent.application.AbstractPreferenceAdapter;
import it.naturtalent.application.IPreferenceNode;
import it.naturtalent.e4.kontakte.KontaktePreferences;
import it.naturtalent.e4.preferences.ListEditorComposite;


public class KontaktePrefereceAdapter extends AbstractPreferenceAdapter
{
	
	private String anredenKey = KontaktePreferences.ADDRESS_ANREDEN_KEY;
	private String categoriesKey = KontaktePreferences.KONTAKTE_CATEGORIES_KEY;
	
	private ListEditorComposite listAnredenPref;
	private ListEditorComposite listCategoriesPref;
	
	private Log log = LogFactory.getLog(this.getClass());
	
	public KontaktePrefereceAdapter()
	{
		instancePreferenceNode = InstanceScope.INSTANCE.getNode(KontaktePreferences.ROOT_KONTAKT_PREFERENCES_NODE);
		defaultPreferenceNode = DefaultScope.INSTANCE.getNode(KontaktePreferences.ROOT_KONTAKT_PREFERENCES_NODE);
	}

	@Override
	public String getLabel()
	{		
		return "Kontakte";
	}

	@Override
	public void restoreDefaultPressed()
	{
		String value = defaultPreferenceNode.get(anredenKey, null);
		if(StringUtils.isNotEmpty(value))
			listAnredenPref.setValues(value);	
		
		value = defaultPreferenceNode.get(categoriesKey, null);
		if(StringUtils.isNotEmpty(value))
			listCategoriesPref.setValues(value);	
	}

	@Override
	public void appliedPressed()
	{
		// Anredenprefs speichern
		String value = listAnredenPref.getValues();
		if(StringUtils.isNotEmpty(value))
			instancePreferenceNode.put(anredenKey, value);

		// Kategorieprefs speichern
		value = listCategoriesPref.getValues();
		if(StringUtils.isNotEmpty(value))
			instancePreferenceNode.put(categoriesKey, value);
		
		try
		{
			instancePreferenceNode.flush();
		} catch (BackingStoreException e)
		{
			log.error(e);	
		}
	}

	@Override
	public Composite createNodeComposite(IPreferenceNode referenceNode)
	{
		// Titel
		referenceNode.setTitle(getLabel());
		
		KontaktePreferenceComposite kontakteComp = new KontaktePreferenceComposite(
				referenceNode.getParentNode(), SWT.NONE);
				
		listAnredenPref = kontakteComp.getAnredenPreference();		
		String value = instancePreferenceNode.get(anredenKey,null);
		if(StringUtils.isEmpty(value))			
			value = getDefaultPreference().get(anredenKey,null);
		listAnredenPref.setValues(value);
		
		// Kategorien			 
		listCategoriesPref = kontakteComp.getCategoriesPreference();
		value = instancePreferenceNode.get(categoriesKey,null);
		if(StringUtils.isEmpty(value))			
			value = getDefaultPreference().get(categoriesKey,null);
		listCategoriesPref.setValues(value);
		
		return kontakteComp;

	}
}
