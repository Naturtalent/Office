package it.naturtalent.e4.office.ui;

import it.naturtalent.application.services.IOpenWithEditorAdapter;

/**
 * Adapter zum Einfuegen eines dynamischen 'OpenWithManue'
 * 
 * @author dieter
 *
 */
public class ODFWriteOpenWithAdapter implements IOpenWithEditorAdapter
{

	@Override
	public String getCommandID()
	{		
		return "it.naturtalent.office.command.openOfficeDocument";
	}

	@Override
	public String getMenuID()
	{		
		return "it.naturtalent.e4.project.menu.openOfficeDocument";		
	}

	@Override
	public String getMenuLabel()
	{		
		return "Anschreiben Wizard";
	}

	
	@Override
	public String getContribURI()
	{
		return null;
		//return "bundleclass://it.naturtalent.e4.project.ui/it.naturtalent.e4.project.ui.handlers.TESThandler";		
	}

	@Override
	public boolean getType()
	{		
		return true;
	}

	@Override
	public int getIndex()
	{		
		return 2;
	}

}
