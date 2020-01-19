package it.naturtalent.e4.office.ui;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.graphics.Image;

import it.naturtalent.e4.office.ui.actions.JournalKontaktExportAction;
import it.naturtalent.e4.project.IExportAdapter;
import it.naturtalent.icons.core.Icon;
import it.naturtalent.icons.core.IconSize;

/**
 * Adapter zum Speichern von Projektdaten in einem SpreeSheat.
 * 
 * @author dieter
 *
 */
public class JournalKontaktExportAdapter implements IExportAdapter
{
	@Override
	public String getLabel()
	{		
		return "Kontakte";
	}

	@Override
	public Image getImage()
	{
		return Icon.ICON_KONTAKT.getImage(IconSize._16x16_DefaultIconSize);		
	}

	@Override
	public String getCategory()
	{		
		return "Journal";
	}

	@Override
	public String getMessage()
	{		
		return "Kontaktdaten in eine Tabelle exportieren";
	}

	/*
	 * Instanziirung an dieser Stelle nicht erforderlich, Rueckgabe der Klasse wuerde genuegen.
	 */
	@Override
	public Action getExportAction()
	{				
		return new JournalKontaktExportAction();
	}
	

}
