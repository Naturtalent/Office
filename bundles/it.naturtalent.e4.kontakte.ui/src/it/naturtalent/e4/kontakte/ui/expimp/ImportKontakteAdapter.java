package it.naturtalent.e4.kontakte.ui.expimp;

import it.naturtalent.e4.kontakte.ui.Messages;
import it.naturtalent.e4.project.IImportAdapter;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.graphics.Image;

public class ImportKontakteAdapter implements IImportAdapter
{

	@Override
	public String getLabel()
	{		
		return Messages.ExportKontakteAdapter_Label;
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
		return "Kontaktdaten importieren";
	}

	@Override
	public Action getImportAction()
	{		
		return new ImportKontakteAction();
	}

}
