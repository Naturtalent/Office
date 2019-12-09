package it.naturtalent.e4.office.ui;

import org.eclipse.swt.graphics.Image;

import it.naturtalent.application.IShowViewAdapter;

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
		System.out.println("it.naturtalent.e4.office.ui.TextmoduleShowViewAdapter.getImage()");
		return null;
	}

	@Override
	public String getContext()
	{
		return null;
	}

	@Override
	public String partID()
	{		
		System.out.println("it.naturtalent.e4.office.ui.TextmoduleShowViewAdapter.partID");
		return null;
		
	}

}
