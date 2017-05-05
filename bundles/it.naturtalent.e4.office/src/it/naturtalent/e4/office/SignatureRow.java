package it.naturtalent.e4.office;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.jdom2.Document;

/**
 * Diese Klasse definiert die Signatur.
 * 
 * @author dieter
 *
 */
public class SignatureRow extends BaseBean implements Cloneable
{
	public static final String PROP_SIGNATURE_TEXT = "signaturetext"; //$NON-NLS-N$	

	private String text;
	
	public String getText()
	{
		return text;
	}
	public void setText(String text)
	{
		firePropertyChange(new PropertyChangeEvent(this, PROP_SIGNATURE_TEXT, this.text,
				this.text = text));	
	}
	@Override
	public Object clone() throws CloneNotSupportedException
	{
		// TODO Auto-generated method stub
		return super.clone();
	}
	
	public static List<SignatureRow> readSignature(Document doc)
	{
		List<SignatureRow>sigatureList = new ArrayList<SignatureRow>();
		
		List<List<String>> tableText = OpenDocumentUtils.readTableText(doc,
				Activator.mapXMLTextTags.get(INtOffice.DOCUMENT_SIGNATURE));
		
		for(List<String>row : tableText)
		{
			SignatureRow signatureRow = new SignatureRow();
			signatureRow.text = row.get(0);
			sigatureList.add(signatureRow);
		}
		
		return sigatureList;
	}
	
	public static String [][] getSignatureRows(List<SignatureRow>signatureList)
	{
		String [][] array = null;
		for(SignatureRow lRow : signatureList)
		{
			String [] row = new String[]{lRow.text};
			array = ArrayUtils.add(array, row);
		}
		return array;
	}


}
