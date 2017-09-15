package it.naturtalent.e4.office.service.libo;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;
import org.jdom2.transform.XSLTransformer;

public class CopyOfNtOfficeContent
{
	
	static final String CONTENT_FILENAME = "content.xml";
	static final String CONTENT_KEY = "contentkey";
	
	static final String STYLES_FILENAME = "styles.xml";
	static final String STYLES_KEY = "styleskey";
	
	// LiboOfficedokument
	private File docFile;
	
	private File packedFile = null;
	
	// Map mit den ausgelagerte Komponenten des Libo Dokuments (conten.xml, stype.xml --)
	private Map<String, InputStream> mapDocContents;

	
	/**
	 * Das Dokument entpacken und Komponenten in 'mapDocContents' auslagern
	 * (noch nicht getestet)
	 * @param docPath
	 */
	public void unpackDocument(InputStream is)
	{
		mapDocContents = new HashMap<String, InputStream>();		
		if (is != null)
		{
			try
			{
				ZipInputStream zin = new ZipInputStream(is);
				ZipEntry ze = null;
				
				while ((ze = zin.getNextEntry()) != null)
				{
					if(StringUtils.equals(ze.getName(), CONTENT_FILENAME))					
						mapDocContents.put(CONTENT_KEY, zin);
					else
					{
						if(StringUtils.equals(ze.getName(), STYLES_FILENAME))					
							mapDocContents.put(STYLES_KEY, zin);
					}
					zin.closeEntry();
				}
				
				
				
				//zin.close();			    
				
			} catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
		
		// des Dokument als entpackt markieren
		packedFile = null;
	}

	
	
	/**
	 * Das Dokument entpacken und Komponenten in 'mapDocContents' auslagern
	 * 
	 * @param docPath
	 */
	public void unpackDocument(String docPath)
	{		
		ZipEntry zipEntry;
		ZipFile zipFile;
		
		mapDocContents = new HashMap<String, InputStream>();
		docFile = new File(docPath);
		if ((docFile != null) && (docFile.exists()))
		{
			try
			{
				zipFile = new ZipFile(docFile);
				
				// Stream zum 'content' - File speichern
				zipEntry = zipFile.getEntry(CONTENT_FILENAME);
				mapDocContents.put(CONTENT_KEY, zipFile.getInputStream(zipEntry));
				
				// Stream zum 'styles' - File speichern
				zipEntry = zipFile.getEntry(STYLES_FILENAME);
				mapDocContents.put(STYLES_KEY, zipFile.getInputStream(zipEntry));
				
			} catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
		
		// des Dokument als entpackt markieren
		packedFile = null;
	}
	
	/**
	 * Das Dokument 'docFile' erneut entpacken und die ausgelagerten Komponenten wieder einmischen
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public File packDocument()
	{
		ZipFile zipFile = null;
		ZipEntry zipEntry;
		Enumeration<ZipEntry> en;		
		byte[] buf;
		ZipOutputStream outZipStream = null;

		// Abbruch, wenn Dokument nicht entpackt vorliegt
		if ((docFile != null) && (packedFile == null))
		{
			try
			{
				// Komponenten in einem 'temporaeren' File zusammensetzen
				packedFile = createEmptyTempFile(docFile);
				outZipStream = new ZipOutputStream(
						FileUtils.openOutputStream(packedFile));

				// Dokument erneut entpacken
				zipFile = new ZipFile(docFile);
				
				en = (Enumeration<ZipEntry>) zipFile.entries();
				while (en.hasMoreElements())
				{
					zipEntry = en.nextElement();

					if (zipEntry.getName().equals(CONTENT_FILENAME))
					{
						// 'ContentFile' einmischen
						zipEntry = new ZipEntry(zipEntry.getName());
						buf = IOUtils.toByteArray(mapDocContents
								.get(CONTENT_KEY));						
					}
					else
					{
						if (zipEntry.getName().equals(STYLES_KEY))
						{
							// 'StylesFile' einmischen
							zipEntry = new ZipEntry(zipEntry.getName());
							buf = IOUtils.toByteArray(mapDocContents
									.get(STYLES_KEY));							
						}
						else
						{
							// Zip-Komponente unverändert übernehmen
							buf = IOUtils.toByteArray(zipFile
									.getInputStream(zipEntry));
						}
					}

					outZipStream.putNextEntry(zipEntry);
					outZipStream.write(buf, 0, buf.length);
					outZipStream.closeEntry();
				}

			} catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally
			{
				if (outZipStream != null)
					try
					{
						outZipStream.close();
					} catch (IOException e)
					{
					}
				if (zipFile != null)
					try
					{
						zipFile.close();
					} catch (IOException e)
					{
					}
			}
		}

		return packedFile;
	}

	/**
	 * @param urlOODocument
	 * @return
	 */
	public File createEmptyTempFile(File orgFile)
	{
		String extension;
		File tempFile = null;
		
		extension = null;
		
		if (orgFile != null)
		{
			extension = FilenameUtils.getExtension(orgFile.getPath());
			extension = (StringUtils.isEmpty(extension)) ? null : "."
					+ extension;
		}
		
		// neuen temp. File erstellen
		try
		{
			tempFile = File.createTempFile("ooTempFile", extension);
			tempFile.deleteOnExit();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return tempFile;
	}
	
	/*
	 * Transform durch Stylesheet auf den Inhalt durchfuehren. Das interne Ziel (content,styles) wird 
	 * durch den Key festgelegt.
	 */
	public void transform(Source xsl, String contentKey)
	{
		if ((xsl != null) && (packedFile == null) && StringUtils.isNotEmpty(contentKey))
		{			
			InputStream xmlInputStream = mapDocContents.get(contentKey);
			if (xmlInputStream != null)
			{				
				Source xmlSource = new StreamSource(xmlInputStream);
				try
				{
					Transformer transformer = TransformerFactory.newInstance()
							.newTransformer(xsl);

					int i = xmlInputStream.available();
					
					
					File tmpResult = createEmptyTempFile(null);
					transformer.transform(xmlSource,new StreamResult(tmpResult));

					// nach fehlerfreiem transform wird das Ergebnis uebernommen
					mapDocContents.put(contentKey, new FileInputStream(tmpResult));

				} catch (Exception e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}



	public Map<String, InputStream> getMapDocContents()
	{
		return mapDocContents;
	}
	
}
