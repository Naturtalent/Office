package it.naturtalent.e4.kontakte.ui;

import it.naturtalent.e4.kontakte.ui.actions.NewContactWizardAction;
import it.naturtalent.e4.project.AbstractNewActionAdapter;
import it.naturtalent.icons.core.Icon;
import it.naturtalent.icons.core.IconSize;

import org.eclipse.swt.graphics.Image;


public class NewKontaktWizardAdapter extends AbstractNewActionAdapter
{
	{ 		
		newActionClass = NewContactWizardAction.class;
	}

	@Override
	public String getLabel()
	{				
		return Messages.NewKontaktWizardAdapter_KontaktLabel;
	}

	@Override
	public Image getImage()
	{
		return Icon.DIALOG_NEW_CONTACT.getImage(IconSize._16x16_DefaultIconSize);
	}
	
	@Override
	public String getMessage()
	{		
		return Messages.NewKontaktWizardAdapter_KontaktMessage;
	}
	

}
