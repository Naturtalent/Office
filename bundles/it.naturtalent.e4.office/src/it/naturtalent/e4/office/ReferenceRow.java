package it.naturtalent.e4.office;

import it.naturtalent.e4.project.ITitelTextLetterRow;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.jdom2.Document;

/**
 * Diese Klasse definiert die Zellen einer Zeile in der Preferenztabelle.
 * 
 * @author dieter
 *
 */
public class ReferenceRow extends BaseBean implements ITitelTextLetterRow, Cloneable
{
	public static final String PROP_REFERENCE_TITEL = "referencetitel"; //$NON-NLS-N$
	public static final String PROP_REFERENCE_TEXT = "referencetext"; //$NON-NLS-N$

	private String titel;
	private String text;
	public String getTitel()
	{
		return titel;
	}
	public void setTitel(String titel)
	{
		firePropertyChange(new PropertyChangeEvent(this, PROP_REFERENCE_TITEL, this.titel,
				this.titel = titel));			
	}
	public String getText()
	{
		return text;
	}
	public void setText(String text)
	{
		firePropertyChange(new PropertyChangeEvent(this, PROP_REFERENCE_TEXT, this.text,
				this.text = text));	
	}
	@Override
	public Object clone() throws CloneNotSupportedException
	{
		// TODO Auto-generated method stub
		return super.clone();
	}
	
	public static List<ReferenceRow> readReference(Document doc)
	{
		List<ReferenceRow>referenceList = new ArrayList<ReferenceRow>();
		
		List<List<String>> tableText = OpenDocumentUtils.readTableText(doc,
				Activator.mapXMLTextTags.get(INtOffice.DOCUMENT_REFERENZ));

		for(List<String>row : tableText)
		{
			ReferenceRow refRow = new ReferenceRow();
			refRow.titel = row.get(0);
			
			// ueberspringt Gap im DefaultLayout
			refRow.text = row.get(2);
			referenceList.add(refRow);
		}

		return referenceList;
	}
	
	public static String [][] getReferenceRows(List<ReferenceRow>referenceList)
	{
		String [][] array = null;
		for(ReferenceRow lRow : referenceList)
		{
			String [] row = new String[]{lRow.titel,"",lRow.text};
			array = ArrayUtils.add(array, row);
		}
		return array;
	}

}
