package it.naturtalent.e4.office.letter;


import java.io.File;

/**
 * Zugriff auf einen Briefdokument
 * 
 * @author apel.dieter
 *
 */
public interface IOfficeLetterAdapter
{
	public static final String LETTER_ADAPTER_PROPERTY_ID = "letteradapterid";
	public static final String DEFAULT_LETTER_ADAPTER = "defaultletteradapter";
	public static final String DEFAULT_MSLETTER_ADAPTER = "msdefaultletteradapter";

	public void openTextDocument(File docFile);
	public void closeTextDocument();
	public void showTextDocument();
	public void readOfficeLetterModel();
	public void writeOfficeLetterModel();
	public OfficeLetterModel getOfficeLetterModel();
	public void setOfficeLetterModel(OfficeLetterModel officeModel);
	
	
	/**
	 * @return true, wenn das geoffnete Dokument diesen Adapter adaptiert
	 */
	public boolean isAdapted();
}
