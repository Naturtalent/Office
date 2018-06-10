package it.naturtalent.e4.office.ui;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.graphics.Image;

import it.naturtalent.e4.office.ui.actions.KontakteExportAction;
import it.naturtalent.e4.project.IExportAdapter;

public class ExportKontakteAdapter implements IExportAdapter
{

	@Override
	public String getLabel()
	{
		return "Kontakte";
	}

	@Override
	public Image getImage()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCategory()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getMessage()
	{
		return "Kontaktdaten exportieren";
	}

	@Override
	public Action getExportAction()
	{		
		return new KontakteExportAction();
	}

}
