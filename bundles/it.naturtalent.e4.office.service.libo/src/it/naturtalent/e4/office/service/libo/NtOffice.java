package it.naturtalent.e4.office.service.libo;



import it.naturtalent.e4.office.INtOffice;
import it.naturtalent.e4.office.OpenDocumentUtils;
import it.naturtalent.e4.office.letter.IOfficeLetterAdapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipFile;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.jdom2.Document;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;



public class NtOffice implements INtOffice
{
	private static final String OFFICE_PROCESS = "soffice";
	
	private NtOfficeContent ntOfficeContent = new NtOfficeContent();
	
	private Map<String, Document> mapDOM = new HashMap<String, Document>();
	
	//private Document openDocument = null;
	
	private Source sourceXSL;
	
	private XslDom xslDom;
	
	   @Override
	public void openDocument(File fileDocument)
	{
		ntOfficeContent.unpackDocument(fileDocument);		
	}
	   
	private boolean isMSDocument(File fileDocument)
	{
		String fileExtension = FilenameUtils.getExtension(fileDocument.getName());
		return (StringUtils.equals(fileExtension, "docx"));
	}
	

	@Override
	public Document getOpenDocument()
	{		
		return getOpenDocument(INtOffice.OPENDOCUMENT_CONTENT);
	}

	@Override
	public Document getOpenDocument(String componentName)
	{
		Document document = null;
		File xmlContent = ntOfficeContent.getMapDocContentFiles().get(componentName);			
		if(xmlContent != null)
		{
			document = OpenDocumentUtils.getDocument(xmlContent);
			mapDOM.put(componentName, document);			
		}
		
		return document;

		/*
		if (openDocument == null)
		{
			File xmlContent = ntOfficeContent.getMapDocContentFiles().get(componentName);			
			if(xmlContent != null)		
				openDocument = OpenDocumentUtils.getDocument(xmlContent);
		}

		return openDocument;
		*/
		
		
		
	}


	@Override
	public void setStyleSheet(File fileXSL)
	{
		if(fileXSL != null)
		{
			sourceXSL = new StreamSource(fileXSL);
			xslDom = new XslDom(fileXSL);
		}
	}



	@Override
	public void transform()
	{
		if(sourceXSL != null)
		{		
			if(xslDom != null)
			{
				Document doc = xslDom.getDoc();
				if(doc != null)
				{
					File file = ntOfficeContent.createEmptyTempFile(null);
					try
					{
						new XMLOutputter().output(doc, new FileOutputStream(file));
						sourceXSL = new StreamSource(file);
					} catch (Exception e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
			ntOfficeContent.transform(sourceXSL);
		}		
	}



	@Override
	public File transform(File xslFile)
	{
		File result = null;
		
		if(xslFile != null)
		{
			Source sourceXSL = new StreamSource(xslFile);
			result = ntOfficeContent.transform(sourceXSL, INtOffice.OPENDOCUMENT_CONTENT);			
		}
		
		return result;
	}


	/* 
	 * Das Dokument wird wieder gepackt danach sind keine Aenderungen mehr moeglich.
	 * Ueber den zurueckgegebenen 'File' kann auf das Dokument zugegriffen werden.
	 * @see it.naturtalent.e4.office.INtOffice#closeDocument()
	 */
	@Override
	public File closeDocument()
	{		
		Set<String> componentNameSet = mapDOM.keySet();
		for(Iterator<String> i = componentNameSet.iterator(); i.hasNext();)
		{
			String componentName = i.next();
			try
			{
				File xmlContent = ntOfficeContent.getMapDocContentFiles().get(componentName);				
				FileOutputStream os = new FileOutputStream(xmlContent);				
				new XMLOutputter().output(mapDOM.get(componentName), os);
			} catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
		
		/*
		if(openDocument != null)
		{
			// ggf. JDOM content zurueckspeichern
			try
			{
				File xmlContent = ntOfficeContent.getMapDocContentFiles().get(
						INtOffice.OPENDOCUMENT_CONTENT);
				
				FileOutputStream os = new FileOutputStream(xmlContent);
				new XMLOutputter().output(openDocument, os);
				openDocument = null;
			} catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
		*/
			
		// packen (Originalversion wiederherstellen)
		return ntOfficeContent.packDocument();
	}
	
	private Document readSourceContent(Source source)
	{
		Document doc = null;		

		String path = source.getSystemId();
		path = StringUtils.substring(path, path.indexOf(':')+1);	
		
		try
		{
			if (source instanceof StreamSource)
			{
				SAXBuilder saxBuilder = new SAXBuilder();
				doc = saxBuilder.build(path);
			}
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		

		return doc;
	}
	
	
	@Override
	public void showDocument()
	{		
		showDocument(closeDocument());
	}
	
	public void showDocumentOLD()
	{		
		try
		{		
			// Pfad zur externen OfficeApplikation
			String unoPath = System.getProperty(INtOffice.EXTERN_OFFICE_PROPERTY);
			if(StringUtils.isEmpty(unoPath))
				throw new java.io.IOException( "UNO_PATH variable nicht definiert (erforderlich ist Pfad zum LibreOffice Programmverzeichnis)" );

			// Abbruch, wenn Pfad ungueltig
			File checkUnoPath = new File(unoPath);
			if(!checkUnoPath.exists() || !checkUnoPath.isDirectory())
				throw new java.io.IOException( "UNO_PATH variable nicht definiert (erforderlich ist Pfad zum LibreOffice Programmverzeichnis)" );

			// den Prozessaufruf vorbereiten
			unoPath = (new File(unoPath, OFFICE_PROCESS)).getPath();					
			String[] cmdArray;
			
			// Dokument schliessen (intern packen aller Komponenten)
			File fileDocument = closeDocument();
			if(fileDocument != null)
			{
				cmdArray = new String[2];
				cmdArray[0] = unoPath;
				cmdArray[1] = fileDocument.getPath();				
			}
			else
			{
				cmdArray = new String[1];
				cmdArray[0] = unoPath;
				//cmdArray[1] = "--writer";				
			}
			
			// externen Officeprozess starten
			Process process = Runtime.getRuntime().exec(cmdArray);
			if ( process == null )
				throw new Exception( "cannot start " + cmdArray );
			
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	public void showDocument(File fileDocument)
	{		
		try
		{		
			// Pfad zur externen OfficeApplikation
			String unoPath = System.getProperty(INtOffice.EXTERN_OFFICE_PROPERTY);
			if(StringUtils.isEmpty(unoPath))
				throw new java.io.IOException( "UNO_PATH variable nicht definiert (erforderlich ist Pfad zum LibreOffice Programmverzeichnis)" );

			// Abbruch, wenn Pfad ungueltig
			File checkUnoPath = new File(unoPath);
			if(!checkUnoPath.exists() || !checkUnoPath.isDirectory())
				throw new java.io.IOException( "UNO_PATH variable nicht definiert (erforderlich ist Pfad zum LibreOffice Programmverzeichnis)" );

			// den Prozessaufruf vorbereiten
			unoPath = (new File(unoPath, OFFICE_PROCESS)).getPath();					
			String[] cmdArray;
			
			// Dokument schliessen (intern packen aller Komponenten)			
			if(fileDocument != null)
			{
				cmdArray = new String[2];
				cmdArray[0] = unoPath;
				cmdArray[1] = fileDocument.getPath();				
			}
			else
			{
				cmdArray = new String[1];
				cmdArray[0] = unoPath;
				//cmdArray[1] = "--writer";				
			}
			
			// externen Officeprozess starten
			Process process = Runtime.getRuntime().exec(cmdArray);
			if ( process == null )
				throw new Exception( "cannot start " + cmdArray );
			
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}


	@Override
	public Source closeXSL()
	{
		Document doc = xslDom.getDoc();
		if(doc != null)
		{
			File file = ntOfficeContent.createEmptyTempFile(null);
			try
			{
				new XMLOutputter().output(doc, new FileOutputStream(file));
				return new StreamSource(file);
				
			} catch (FileNotFoundException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
		}
		
		return null;
	}



	@Override
	public void setXSLVariable(String name, String[] values)
	{
		xslDom.setTextTag(name, values);		
	}

	@Override
	public void setXSLTable(String name, String[][] values)
	{
		xslDom.setTextTable(name, values);		
	}



	@Override
	public String[] getXSLArray(String arrayName)
	{
		List<String>texte = xslDom.getTextTag(arrayName);
		return null;		
	}



	@Override
	public List<String> readTextTags(String textTag)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isOfficeDocument(File fileDocument)
	{
		try
		{
			// gueltiges OpenDocument					
			ZipFile ooZipFile = new ZipFile(fileDocument);
			if(ooZipFile.getEntry(INtOffice.OPENDOCUMENT_CONTENT) != null)
				return true;
			
		} catch (IOException e)
		{
		}

		return false;
	}


}
