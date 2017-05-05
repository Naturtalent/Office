package it.naturtalent.e4.kontakte;

public class AddHelperEvent
{
	public KontakteDataModel model;
	public KontakteData addedData;
	public AddHelperEvent(KontakteDataModel model, KontakteData addedData)
	{
		super();
		this.model = model;
		this.addedData = addedData;
	}
	
	
}
