package it.naturtalent.e4.office.ui.expimp;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.graphics.Image;
import org.eclipse.wb.swt.SWTResourceManager;

import it.naturtalent.e4.project.IImportAdapter;

public class OfficeProfileImportAdapter implements IImportAdapter
{

	@Override
	public String getLabel()
	{		
		return "Officeprofile";
	}

	@Override
	public Image getImage()
	{		
		return SWTResourceManager.getImage(this.getClass(), "/icons/full/obj16/font.gif"); //$NON-NLS-1$;
	}

	@Override
	public String getContext()
	{
		return null;
	}

	@Override
	public Action getImportAction()
	{		
		return new OfficeProfileImportAction();
	}

	@Override
	public String getMessage()
	{
		return "Office Benutzerprofile importieren";
	}

}
