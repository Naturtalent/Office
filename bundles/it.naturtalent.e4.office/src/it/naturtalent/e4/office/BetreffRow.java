package it.naturtalent.e4.office;



import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;



import org.apache.commons.lang3.ArrayUtils;
import org.jdom2.Document;

/**
 * Diese Klasse definiert die Zellen einer Zeile in der Betrefftabelle.
 * 
 * @author dieter
 *
 */
public class BetreffRow extends BaseBean
{
	public static final String PROP_BETREFF_TITEL = "betrefftitel"; //$NON-NLS-N$
	public static final String PROP_BETREFF_TEXT = "betrefftext"; //$NON-NLS-N$

	private String titel;
	private String text;
	public String getTitel()
	{
		return titel;
	}
	public void setTitel(String titel)
	{
		firePropertyChange(new PropertyChangeEvent(this, PROP_BETREFF_TITEL, this.titel,
				this.titel = titel));			
	}
	public String getText()
	{
		return text;
	}
	public void setText(String text)
	{
		firePropertyChange(new PropertyChangeEvent(this, PROP_BETREFF_TEXT, this.text,
				this.text = text));	
	}
	
	public static List<BetreffRow> readBetreff(Document doc)
	{
		List<BetreffRow>betreffList = new ArrayList<BetreffRow>();
		
		List<List<String>> tableText = OpenDocumentUtils.readTableText(doc,
				Activator.mapXMLTextTags.get(INtOffice.DOCUMENT_BETREFF));
		
		for(List<String>row : tableText)
		{
			BetreffRow betreff = new BetreffRow();
			betreff.titel = row.get(0);
			
			// ueberspringt Gap im DefaultLayout
			betreff.text = row.get(2);
			betreffList.add(betreff);
		}
		
		return betreffList;
	}
	
	public static String [][] getBetreffRows(List<BetreffRow>betreffList)
	{
		String [][] array = null;
		for(BetreffRow lRow : betreffList)
		{
			String [] row = new String[]{lRow.titel,"",lRow.text};
			array = ArrayUtils.add(array, row);
		}
		return array;
	}
	
	public static void writeBetreff(List<BetreffRow>rows, Document doc)
	{
		String [][] tableText = null;
		for(BetreffRow row : rows)
			tableText = ArrayUtils.add(tableText, getRow(row));
		OpenDocumentUtils.writeTableText(doc, Activator.mapXMLTextTags.get(INtOffice.DOCUMENT_BETREFF), tableText);
	}

	private static String [] getRow(BetreffRow row)
	{
		String[] rowArray = new String[3];
		rowArray[0] = row.titel;
		rowArray[2] = row.text;
		return rowArray;
	}
	
	
}
