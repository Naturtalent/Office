package it.naturtalent.e4.office.service;

import it.naturtalent.e4.office.ITextModuleDataFactory;
import it.naturtalent.e4.office.ITextModuleModel;
import it.naturtalent.e4.office.OpenDocumentUtils;
import it.naturtalent.e4.office.TextModule;
import it.naturtalent.e4.office.TextModuleModel;

import java.util.HashMap;
import java.util.Map;

public class TextModuleDataFactory implements ITextModuleDataFactory
{
	// Liste mit den Modellen
	private static Map<String,ITextModuleModel>modelMap = new HashMap<String, ITextModuleModel>();

	@Override
	public ITextModuleModel getTextModuleModel(String officeContext)
	{
		ITextModuleModel model = modelMap.get(officeContext);
		if (model == null)
		{
			model = OpenDocumentUtils.readTextModules(officeContext);
			if (model != null)				 
				modelMap.put(officeContext, model);
		}
		return model;
	}

	@Override
	public void saveTextModuleModel(String officeContext)
	{
		ITextModuleModel model = modelMap.get(officeContext);
		if(model != null)
			OpenDocumentUtils.writeTextModules(officeContext, (TextModuleModel) model);
	}




}
