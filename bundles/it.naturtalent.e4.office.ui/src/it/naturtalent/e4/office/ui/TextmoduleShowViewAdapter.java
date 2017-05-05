package it.naturtalent.e4.office.ui;

import it.naturtalent.application.IShowViewAdapter;
import it.naturtalent.e4.office.ui.part.TextbausteinePart;

import org.eclipse.swt.graphics.Image;
import org.eclipse.wb.swt.SWTResourceManager;

public class TextmoduleShowViewAdapter implements IShowViewAdapter		
{

	@Override
	public String getLabel()
	{		
		return "Textbausteine";
	}

	@Override
	public Image getImage()
	{		
		return SWTResourceManager.getImage(TextbausteinePart.class,
				"/icons/full/obj16/font.gif");
	}

	@Override
	public String getContext()
	{
		return null;
	}

	@Override
	public String partID()
	{		
		return TextbausteinePart.TEXTBAUSTEINE_VIEW_ID;
	}

}
