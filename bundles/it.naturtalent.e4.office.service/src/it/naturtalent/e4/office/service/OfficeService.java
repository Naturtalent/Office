package it.naturtalent.e4.office.service;

import it.naturtalent.e4.office.IOfficeDocumentHandler;
import it.naturtalent.e4.office.IOfficeService;
import it.naturtalent.e4.office.ITextModuleModel;
import it.naturtalent.e4.office.OpenDocumentUtils;
import it.naturtalent.e4.office.TextModuleModel;
import it.naturtalent.e4.office.letter.AbstractOfficeLetterAdapter;
import it.naturtalent.e4.office.letter.IOfficeLetterAdapter;
import it.naturtalent.e4.office.ms.MSLetterAdapter;
import it.naturtalent.e4.office.ms.MSSpreadsheetAdapter;
import it.naturtalent.e4.office.odf.ODFLetterAdapter;
import it.naturtalent.e4.office.odf.ODFSpreadsheetAdapter;
import it.naturtalent.e4.office.spreadsheet.AbstractSpreadSheetAdapter;
import it.naturtalent.e4.office.spreadsheet.ISpreadSheetAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;


/**
 * Im OfficeService werden unterschiedliche Adapter und Templates in speziellen Speichern abgelegt.
 * 
 * @author dieter
 *
 */
public class OfficeService implements IOfficeService
{
	
	/*
	 * Tabellenadaper
	 * 
	 * Die Tabellenadapter werden mit einem Name und Klasse in der Registry gespeichert.
	 * Beim Anlegen der Registry werden 2 DefaultAdapter (LO und MS) vorbelegt.
	 * 
	 */
	private static Map<String, Class<? extends AbstractSpreadSheetAdapter>> spreadSheetAdapterClassRegistry = new HashMap<String, Class<? extends AbstractSpreadSheetAdapter>>();
	{
		// Default ODFSpreadSheet AdapterClass registrieren
		spreadSheetAdapterClassRegistry.put(
				ISpreadSheetAdapter.DEFAULT_SPREADSHEET_ADAPTER,
				ODFSpreadsheetAdapter.class);
		
		// Default MSSpreadsheet AdapterClass registrieren
		spreadSheetAdapterClassRegistry.put(
				ISpreadSheetAdapter.DEFAULT_MSSPREADSHEET_ADAPTER,
				MSSpreadsheetAdapter.class);
	}
	
	
	@Override
	public void registerSpreadSheetAdapterClass(String adapterClassName,
			Class<? extends AbstractSpreadSheetAdapter> adapter)
	{
		spreadSheetAdapterClassRegistry.put(adapterClassName, adapter);		
	}

	@Override
	public ISpreadSheetAdapter getAdapter(String adapterClassName)
	{
		Class<? extends AbstractSpreadSheetAdapter> adapterClass = spreadSheetAdapterClassRegistry
				.get(adapterClassName);
		try
		{
			return adapterClass.newInstance();
			
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public Class<? extends AbstractSpreadSheetAdapter> getSheetAdapterClass(
			String adapterClassName)
	{		
		return spreadSheetAdapterClassRegistry.get(adapterClassName);
	}

	/*
	 * 
	 *  LetterAdapter Klasse
	 * 
	 */

	/**
	 * Map mit den registrierten Adapterklassen
	 * Key = Adaptername
	 * Value = Adapterklasse
	 */
	private static Map<String, Class<? extends AbstractOfficeLetterAdapter>> letterAdapterClassRegistry = new HashMap<String, Class<? extends AbstractOfficeLetterAdapter>>();
	{
		// Default ODF LetterAdaper
		letterAdapterClassRegistry.put(IOfficeLetterAdapter.DEFAULT_LETTER_ADAPTER,
				ODFLetterAdapter.class);
		
		// Default MS LetterAdaper
		letterAdapterClassRegistry.put(IOfficeLetterAdapter.DEFAULT_MSLETTER_ADAPTER,
				MSLetterAdapter.class);		
	}
	
	@Override
	public void registerLetterAdapterClass(String adapterName,
			Class<? extends AbstractOfficeLetterAdapter> adapter)
	{
		letterAdapterClassRegistry.put(adapterName, adapter);		
	}


	/*
	 * Vorlagen wrden mit mit Ihrem Pfad (Key) und einem Namen gespeichert.
	 * 
	 * 
	 */
	
	/**
	 * Map mit den registrierten Templates
	 * Key = Templatepath
	 * Value = Name des zugeordneten Adapters
	 */
	private static Map<String, String> templateRegistry = new HashMap<String, String>();

	@Override
	public void registerLetterTemplate(String templatePath)
	{
		String ext = FilenameUtils.getExtension(templatePath);
		if(StringUtils.equals(ext, IOfficeDocumentHandler.ODF_OFFICETEXTDOCUMENT_EXTENSION))
		{
			templateRegistry.put(templatePath, IOfficeLetterAdapter.DEFAULT_LETTER_ADAPTER);
			return;
		}
		if(StringUtils.equals(ext, IOfficeDocumentHandler.MS_OFFICETEXTDOCUMENT_EXTENSION))
		{
			templateRegistry.put(templatePath, IOfficeLetterAdapter.DEFAULT_MSLETTER_ADAPTER);
			return;
		}		
	}
	
	@Override
	public void registerLetterTemplate(String templatePath,
			String adapterName)
	{
		Class<? extends AbstractOfficeLetterAdapter> adapterClass = letterAdapterClassRegistry.get(adapterName);
		if(adapterClass != null)
			templateRegistry.put(templatePath, adapterName);
	}

	@Override
	public IOfficeLetterAdapter getLetterTemplateAdapter(String templatePath)
	{
		String adapterName = templateRegistry.get(templatePath);
		if(StringUtils.isNotEmpty(adapterName))
		{
			Class<? extends AbstractOfficeLetterAdapter> adapterClass = letterAdapterClassRegistry.get(adapterName);
			if(adapterClass != null)
			{
				try
				{
					return adapterClass.newInstance();					
				} catch (Exception e)
				{
				}
			}		
		}
		
		return null;
	}
	
	@Override
	public String getPracticalTemplate(String documentPath,
			String officeContextFilter)
	{
		String extension = FilenameUtils.getExtension(documentPath);
		List<String>names = getLetterTemplates(officeContextFilter);
		
		// Template durch Auswerten des Dokumentproperty ermitteln
		String templateName = detectTemplate(documentPath, names);		
		if(templateName != null)
			return templateName;
	
		// Template durch Auswerten der Dateierweiterung	
		for(String name : names)
		{			
			if(StringUtils.equals(FilenameUtils.getExtension(name), extension))
				return name;
		}
		return null;
	}
	
	/*
	 * das Dokument mit allen registrierten Adaptern oeffnen und ueberpruefen,
	 * ob Dokumentproperty mit Adapternamen uebereinstimmt 
	 */
	private String detectTemplate(String documentPath, List<String>templatenames)
	{
		// Dokument mit allen registrieren Adaptern oeffnen
		for(String templatename : templatenames)
		{			
			IOfficeLetterAdapter adapter = getLetterTemplateAdapter(templatename);
			if(adapter != null)
			{
				File docFile = new File(documentPath);
				adapter.openTextDocument(docFile);
				if(adapter.isAdapted())
					return templatename;
				adapter.closeTextDocument();
			}
		}
				
		return null;
	}

	@Override
	public List<String> getLetterTemplates(String officeContextFilter)
	{
		List<String>keys = new ArrayList<String>();
		
		if(StringUtils.isEmpty(officeContextFilter))
		{
			// Parameter null gibt alle Templates zurueck
			keys.addAll(templateRegistry.keySet());
			return keys;
		}
		
		String filter = IOfficeService.OFFICEDATADIR+File.separator+officeContextFilter;
		Iterator<String>itKey = templateRegistry.keySet().iterator();
		while(itKey.hasNext())
		{
			String key = itKey.next();
			if(StringUtils.contains(key, filter))
				keys.add(key);
		}
		
		return keys;
	}

	

	
	/*
	 * 
	 *  Textbausteine
	 * 
	 */

	// Map mit den Textbausteinen
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
