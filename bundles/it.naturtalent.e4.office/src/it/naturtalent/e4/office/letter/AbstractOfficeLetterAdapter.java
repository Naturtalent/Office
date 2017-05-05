package it.naturtalent.e4.office.letter;

import java.io.File;

public abstract class AbstractOfficeLetterAdapter implements IOfficeLetterAdapter
{

	public static String LETTER_ABSENDER = "Absendertabelle";
	public static String LETTER_ADRESSE = "Adresstabelle";
	public static String LETTER_PRAESENTATION = "Praesentationstabelle";
	public static String LETTER_REFERENZ = "Referenztabelle";
	public static String LETTER_BETREFF = "Betrefftabelle";
	public static String LETTER_FOOTER = "Footertabelle";
	public static String LETTER_SIGNATURE = "Signaturtabelle";
	
	protected OfficeLetterModel letterModel = null;
	
	@Override
	public void openTextDocument(File docFile)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void closeTextDocument()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void showTextDocument()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void readOfficeLetterModel()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void writeOfficeLetterModel()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public OfficeLetterModel getOfficeLetterModel()
	{		
		return letterModel;
	}

	@Override
	public void setOfficeLetterModel(OfficeLetterModel officeModel)
	{
		letterModel = officeModel;
	}


	
	

}
