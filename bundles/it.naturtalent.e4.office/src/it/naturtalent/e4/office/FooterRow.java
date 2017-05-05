package it.naturtalent.e4.office;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.jdom2.Document;

/**
 * Diese Klasse definiert die Zellen einer Zeile in der Footertabelle.
 * 
 * @author dieter
 *
 */
public class FooterRow extends BaseBean implements Cloneable
{
	public static final String PROP_FOOTER_TITEL = "footertitel"; //$NON-NLS-N$
	public static final String PROP_FOOTER_TEXT = "footertext"; //$NON-NLS-N$

	private String titel;
	private String text;
	
	
	public String getTitel()
	{
		return titel;
	}
	public void setTitel(String titel)
	{
		firePropertyChange(new PropertyChangeEvent(this, PROP_FOOTER_TITEL, this.titel,
				this.titel = titel));			
	}
	public String getText()
	{
		return text;
	}
	public void setText(String text)
	{
		firePropertyChange(new PropertyChangeEvent(this, PROP_FOOTER_TEXT, this.text,
				this.text = text));	
	}
	@Override
	public Object clone() throws CloneNotSupportedException
	{
		// TODO Auto-generated method stub
		return super.clone();
	}

	public static List<FooterRow> readFooter(Document doc)
	{
		List<FooterRow>footerList = new ArrayList<FooterRow>();
		
		List<List<String>> tableText = OpenDocumentUtils.readTableText(doc,
				Activator.mapXMLTextTags.get(INtOffice.DOCUMENT_FOOTER));
		
		for(List<String>row : tableText)
		{
			FooterRow footerRow = new FooterRow();
			footerRow.titel = row.get(0);
			
			// ueberspringt Gap 
			footerRow.text = row.get(2);
			footerList.add(footerRow);
		}
		
		return footerList;
	}
	public static String [][] getFooterRows(List<FooterRow>footerList)
	{
		String [][] array = null;
		for(FooterRow lRow : footerList)
		{
			String [] row = new String[]{lRow.titel,"",lRow.text};
			array = ArrayUtils.add(array, row);
		}

		return array;
	}

}
