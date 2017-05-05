package it.naturtalent.e4.kontakte.ui.actions;

import it.naturtalent.e4.kontakte.KontakteData;
import it.naturtalent.e4.kontakte.ui.Messages;
import it.naturtalent.icons.core.Icon;
import it.naturtalent.icons.core.IconSize;

import org.apache.commons.lang3.ArrayUtils;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.widgets.Display;

public class DeleteAction extends AbstractContactAction
{

	private KontakteData [] selectedContactsData;
	
	public DeleteAction()
	{
		super();
		setImageDescriptor(Icon.COMMAND_DELETE.getImageDescriptor(IconSize._16x16_DefaultIconSize));
		setToolTipText(Messages.KontakteView_TooltipDelete);
		setEnabled(false);
	}

	public void setSelectedKontakteData(KontakteData [] selectedKontakteData)
	{
		this.selectedContactsData = selectedKontakteData;
	}

	@Override
	public void run()
	{
		if (ArrayUtils.isNotEmpty(selectedContactsData)
				&& (kontakteDataModel != null))
		{
			if (MessageDialog.openQuestion(Display.getDefault()
					.getActiveShell(), Messages.DeleteAction_ContactLabel,
					Messages.DeleteAction_DeleteQuestion))
			{
				Runnable longJob = new Runnable()
				{
					@Override
					public void run()
					{
						for (KontakteData kontakteData : selectedContactsData)
						{
							// Kontakt aus dem Modell entfernen
							kontakteDataModel.delete(kontakteData.getId());

							// Kontakt aus dem KategorieModell entfernen
							if (contactCategoryModel != null)
								contactCategoryModel.removeKontakt(kontakteData
										.getId());
						}
						kontakteDataModel.saveModel();
					}
				};

				BusyIndicator.showWhile(Display.getDefault(), longJob);
			}
		}
	}

}
