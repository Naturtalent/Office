
package it.naturtalent.e4.kontakte.ui.actions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import it.naturtalent.e4.kontakte.IKontakteDataFactory;
import it.naturtalent.e4.kontakte.IKontakteDataModel;
import it.naturtalent.e4.kontakte.KontakteData;
import it.naturtalent.e4.kontakte.ui.Messages;
import it.naturtalent.e4.kontakte.ui.dialogs.CategoriesDialog;
import it.naturtalent.e4.kontakte.ui.dialogs.SelectKontaktDataDB;
import it.naturtalent.icons.core.Icon;
import it.naturtalent.icons.core.IconSize;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.swt.widgets.Display;

public class CategoryFilterAction  extends AbstractContactAction
{		
	
	private Set<String>currentSelectedCategories = new HashSet<String>();
	
	public CategoryFilterAction()
	{
		super();		
		setImageDescriptor(Icon.ICON_FILTER.getImageDescriptor(IconSize._16x16_DefaultIconSize));
		setToolTipText(Messages.KontakteView_TooltipKategoriesFilter);
	}
	
	@Override
	public void run()
	{		
		if (kontakteDataModel != null)
		{
			CategoriesDialog dialog = new CategoriesDialog(Display.getDefault().getActiveShell());
			dialog.create();	
			
			// Kategoriepreferenzen aktualisieren
			String categoryPreferences = (String) kontakteDataModel
					.getModelFactory()
					.getKontaktPreference(
							kontakteDataModel.getCollectionName(),
							IKontakteDataFactory.CATEGORIES_PREFERENCE_TYPE);
						
			String [] categoriesPreferences = StringUtils.split(categoryPreferences, ",");			
			dialog.setValues(new ArrayList(currentSelectedCategories),categoriesPreferences);
			if (dialog.open() == CategoriesDialog.OK)
			{
				String values = dialog.getValues();
				eventBroker.send(IKontakteDataModel.KONTAKT_EVENT_CONTACTFILTER,values);
				
				currentSelectedCategories.clear();
				String [] names = StringUtils.split(values,",");
				for(String name : names)
					currentSelectedCategories.add(name);
				
			}
		}		
	}

}
