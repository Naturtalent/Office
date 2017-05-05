package it.naturtalent.e4.kontakte.ui.parts;

import java.text.Collator;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;

import it.naturtalent.e4.kontakte.KontakteData;
import it.naturtalent.e4.kontakte.ui.AbstractTableSorter;

public class ContactNameSorter extends AbstractTableSorter
{

	public ContactNameSorter(TableViewer viewer, TableViewerColumn column)
	{
		super(viewer, column);		
	}
	
	Collator collator = Collator.getInstance(Locale.GERMAN);

	@Override
	protected int doCompare(Viewer viewer, Object e1, Object e2)
	{
		String stgValue1, stgValue2;
		
		stgValue1 = ((KontakteData) e1).getAddress().getName();
		stgValue2 = ((KontakteData) e2).getAddress().getName();			
		if (StringUtils.isNotEmpty(stgValue1)
				&& StringUtils.isNotEmpty(stgValue2))
			return collator.compare(stgValue1, stgValue2);
		
		return 0;
	}

}
