package it.naturtalent.e4.office.ui;

import it.naturtalent.e4.office.ui.actions.KontakteImportAction;
import it.naturtalent.e4.project.IImportAdapter;

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
		//return SWTResourceManager.getImage(this.getClass(), "/icons/full/elcl16/pin_view.gif"); //$NON-NLS-1$;
		return null;
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
