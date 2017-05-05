package it.naturtalent.e4.kontakte.ui;

import org.eclipse.swt.graphics.Image;

import it.naturtalent.application.IShowViewAdapter;
import it.naturtalent.e4.kontakte.ui.parts.ContactView;
import it.naturtalent.icons.core.Icon;
import it.naturtalent.icons.core.IconSize;

public class ShowContactsViewAdapter implements IShowViewAdapter
{

	@Override
	public String getLabel()
	{		
		return "Kontakte";
	}

	@Override
	public Image getImage()
	{
		return Icon.PART_CONTACT.getImage(IconSize._16x16_DefaultIconSize);
	}

	@Override
	public String getContext()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String partID()
	{
		return ContactView.CONTACT_VIEW_ID;
	}

}
