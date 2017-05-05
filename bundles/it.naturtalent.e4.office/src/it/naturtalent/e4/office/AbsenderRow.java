package it.naturtalent.e4.office;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.jdom2.Document;

/**
 * Diese Klasse definiert die Zellen einer Zeile in der Absendertabelle.
 * 
 * @author dieter
 *
 */
public class AbsenderRow extends BaseBean implements Cloneable
{
	public static final String PROP_ABSENDER_TEXT = "ansendertext"; //$NON-NLS-N$	

	private String text;
	
	public String getText()
	{
		return text;
	}
	public void setText(String text)
	{
		firePropertyChange(new PropertyChangeEvent(this, PROP_ABSENDER_TEXT, this.text,
				this.text = text));	
	}
	@Override
	public Object clone() throws CloneNotSupportedException
	{
		// TODO Auto-generated method stub
		return super.clone();
	}
	
	public static List<AbsenderRow> readAbsender(Document doc)
	{
		List<AbsenderRow>absenderList = new ArrayList<AbsenderRow>();
		
		List<List<String>> tableText = OpenDocumentUtils.readTableText(doc,
				Activator.mapXMLTextTags.get(INtOffice.DOCUMENT_ABSENDER));
		
		for(List<String>row : tableText)
		{
			AbsenderRow absender = new AbsenderRow();
			absender.text = row.get(0);
			absenderList.add(absender);
		}
		
		return absenderList;
	}
	
	public static String [][] getAbsenderRows(List<AbsenderRow>absenderList)
	{
		String [][] array = null;
		for(AbsenderRow lRow : absenderList)
		{
			String [] row = new String[]{lRow.text};
			array = ArrayUtils.add(array, row);
		}
		return array;
	}


}
