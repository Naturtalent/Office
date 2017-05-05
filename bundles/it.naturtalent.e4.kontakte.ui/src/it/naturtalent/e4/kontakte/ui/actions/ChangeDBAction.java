
package it.naturtalent.e4.kontakte.ui.actions;

import it.naturtalent.e4.kontakte.IKontakteDataModel;
import it.naturtalent.e4.kontakte.ui.Messages;
import it.naturtalent.e4.kontakte.ui.dialogs.SelectKontaktDataDB;
import it.naturtalent.icons.core.Icon;
import it.naturtalent.icons.core.IconSize;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.swt.widgets.Display;

public class ChangeDBAction  extends AbstractContactAction
{		
	public ChangeDBAction()
	{
		super();		
		setImageDescriptor(Icon.ICON_DATABASE.getImageDescriptor(IconSize._16x16_DefaultIconSize));
		setToolTipText(Messages.KontakteView_TooltipSelectDB);
	}

	@Override
	public void run()
	{
		if (kontakteDataModel != null)
		{
			SelectKontaktDataDB dialog = new SelectKontaktDataDB(Display.getDefault().getActiveShell());
			dialog.create();			
			dialog.setKontaktDataModel(kontakteDataModel);
			if (dialog.open() == SelectKontaktDataDB.OK)
			{
				String selectedName = dialog.getSelectedName();
				if (!StringUtils.equals(selectedName, kontakteDataModel.getCollectionName()))
				{
					// das aktuelle Modell speichern
					kontakteDataModel.saveModel();
					
					// auf die mit dem Dialog selektierte Datenbank wechseln
					String collectionName = dialog.getSelectedName();
					kontakteDataModel.setCollectionName(dialog.getSelectedName());
					kontakteDataModel.loadModel();
					eventBroker.post(IKontakteDataModel.KONTAKT_EVENT_AFTER_CHANGEDB,kontakteDataModel);
					
				}
			}
		}
	}

}
