package it.naturtalent.e4.kontakte.ui.actions;

import it.naturtalent.e4.kontakte.IKontakteDataModel;
import it.naturtalent.icons.core.Icon;
import it.naturtalent.icons.core.IconSize;

public class SaveAction extends AbstractContactAction
{

	{
		setImageDescriptor(Icon.COMMAND_SAVE.getImageDescriptor(IconSize._16x16_DefaultIconSize));
		setToolTipText("speichern");
	}
	
	@Override
	public void run()
	{
		if(kontakteDataModel.isModified() || contactCategoryModel.isModified())
		{
			if(kontakteDataModel.isModified())
				kontakteDataModel.saveModel();
			if(contactCategoryModel.isModified())
				contactCategoryModel.saveContactCategories();
			
			eventBroker.post(IKontakteDataModel.KONTAKT_EVENT_CONTACTPERSISTENCE,null);
		}
	}

}
