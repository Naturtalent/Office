package it.naturtalent.e4.office.ui;

import it.naturtalent.e4.office.ui.actions.KontakteImportAction;
import it.naturtalent.e4.project.IImportAdapter;
import it.naturtalent.icons.core.Icon;
import it.naturtalent.icons.core.IconSize;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.graphics.Image;

/**
 * Mit diesem Adapter wird die Import-Funktionalitaet in der Application angemeldet
 * 
 * @author dieter
 *
 */
public class ImportKontakteAdapter implements IImportAdapter
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
	public String getMessage()
	{
		return "Kontkatdaten importieren";
	}

	@Override
	public Action getImportAction()
	{		
		return new KontakteImportAction();
	}

}
