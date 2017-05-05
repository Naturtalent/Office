package it.naturtalent.e4.kontakte.ui.actions;

import it.naturtalent.e4.kontakte.KontakteData;
import it.naturtalent.e4.kontakte.ui.Messages;
import it.naturtalent.e4.kontakte.ui.dialogs.ContactDetailsDialog;
import it.naturtalent.icons.core.Icon;
import it.naturtalent.icons.core.IconSize;

import org.eclipse.swt.widgets.Display;

public class AddAction extends AbstractContactAction
{
	
	private ContactDetailsDialog dialog;
	
	public AddAction()
	{
		super();
		setImageDescriptor(Icon.COMMAND_ADD.getImageDescriptor(IconSize._16x16_DefaultIconSize));
		setToolTipText(Messages.KontakteView_TooltipNew);
	}
	
	@Override
	public void run()
	{
		dialog = new ContactDetailsDialog(Display.getDefault().getActiveShell());
		dialog.create();	
		dialog.setEventBroker(eventBroker);
		
		// Modelle an den Dialog uebergeben
		dialog.setKontakteDataModel(kontakteDataModel);
		dialog.setContactCategoryModel(contactCategoryModel);		
		
		KontakteData kontakteData = new KontakteData();
		kontakteData.initKontakteData();	
		dialog.setKontakteData(kontakteData);
		
		if(dialog.open() == ContactDetailsDialog.OK)
		{
			kontakteDataModel.addData(kontakteData);
			kontakteDataModel.saveModel();
		}
	}
}
