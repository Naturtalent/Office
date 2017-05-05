package it.naturtalent.e4.office.ui.expimp;

import it.naturtalent.e4.project.IExportAdapter;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.graphics.Image;
import org.eclipse.wb.swt.SWTResourceManager;


public class TextmoduleExportAdapter implements IExportAdapter
{
	@Override
	public Image getImage()
	{
		return SWTResourceManager.getImage(this.getClass(), "/icons/full/obj16/font.gif"); //$NON-NLS-1$;
	}

	@Override
	public String getLabel()
	{		
		return "Textbausteine";
	}

	@Override
	public String getCategory()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Action getExportAction()
	{		
		return new TextmoduleExportAction();
	}

	@Override
	public String getMessage()
	{
		return "Telekom Textbausteine exportieren";
	}

}
