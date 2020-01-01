package it.naturtalent.e4.office.ui;

import it.naturtalent.e4.office.ui.actions.CSVKontakteImportAction;
import it.naturtalent.e4.office.ui.actions.KontakteImportAction;
import it.naturtalent.e4.project.IImportAdapter;
import it.naturtalent.icons.core.Icon;
import it.naturtalent.icons.core.IconSize;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.graphics.Image;

/**
 * Mit diesem Adapter werden Kontakte eingelesen, die in einem CSV Format gespeichert wurden.
 *  
 * 
 * @author dieter
 *
 */
public class ImportCSVKontakteAdapter implements IImportAdapter
{

	@Override
	public String getLabel()
	{		
		return "CSV Kontakte";
	}

	@Override
	public Image getImage()
	{
		return Icon.ICON_CONNECT.getImage(IconSize._16x16_DefaultIconSize);			
	}

	@Override
	public String getContext()
	{		
		return "CSV";
	}

	@Override
	public String getMessage()
	{
		return "CSV Kontaktdatei (Florian)";
	}

	@Override
	public Action getImportAction()
	{		
		return new CSVKontakteImportAction();
	}

}
