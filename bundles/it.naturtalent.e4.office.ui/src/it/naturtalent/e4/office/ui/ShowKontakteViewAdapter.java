package it.naturtalent.e4.office.ui;

import org.eclipse.swt.graphics.Image;

import it.naturtalent.application.IShowViewAdapter;
import it.naturtalent.e4.office.ui.part.KontakteView;
import it.naturtalent.icons.core.Icon;
import it.naturtalent.icons.core.IconSize;


/**
 * Adapter zum Anzeigen/Aktivieren der Login-Fensters.
 * 
 * @author dieter
 *
 */
public class ShowKontakteViewAdapter implements IShowViewAdapter
{

	@Override
	public String getLabel()
	{		
		return "Kontakte";
	}

	@Override
	public Image getImage()
	{
		return Icon.ICON_CONNECT.getImage(IconSize._16x16_DefaultIconSize);	
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
		return KontakteView.KONTAKTEVIEW_ID;
	}

}
