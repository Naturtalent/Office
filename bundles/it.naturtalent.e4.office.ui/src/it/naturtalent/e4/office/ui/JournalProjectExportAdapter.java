package it.naturtalent.e4.office.ui;

import java.io.File;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.action.Action;
import org.eclipse.swt.graphics.Image;
import org.odftoolkit.simple.SpreadsheetDocument;
import org.odftoolkit.simple.table.Cell;
import org.odftoolkit.simple.table.Row;
import org.odftoolkit.simple.table.Table;

import it.naturtalent.e4.office.ui.actions.JournalProjektExportAction;
import it.naturtalent.e4.project.IExportAdapter;
import it.naturtalent.icons.core.Icon;
import it.naturtalent.icons.core.IconSize;

/**
 * Adapter zum Speichern von Projektdaten in einem SpreeSheat.
 * 
 * @author dieter
 *
 */
public class JournalProjectExportAdapter implements IExportAdapter
{
	@Override
	public String getLabel()
	{		
		return "Projekte";
	}

	@Override
	public Image getImage()
	{
		return Icon.ICON_PROJECT.getImage(IconSize._16x16_DefaultIconSize);		
	}

	@Override
	public String getCategory()
	{		
		return "Journal";
	}

	@Override
	public String getMessage()
	{		
		return "Projektdaten in eine Tabelle exportieren";
	}

	/*
	 * Instanziirung an dieser Stelle nicht erforderlich, Rueckgabe der Klasse wuerde genuegen.
	 */
	@Override
	public Action getExportAction()
	{				
		return new JournalProjektExportAction();
	}
	

}
