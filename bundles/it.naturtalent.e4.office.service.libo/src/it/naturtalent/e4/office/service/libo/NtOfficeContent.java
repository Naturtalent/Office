package it.naturtalent.e4.office.service.libo;



import it.naturtalent.e4.office.INtOffice;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

import javax.xml.transform.Result;
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

public class NtOfficeContent
{
	
	//static public final String CONTENT_FILENAME = "content.xml";
	//static final String STYLES_FILENAME = "styles.xml";
	
	// LiboOfficedokument
	private File docFile;
	
	private File packedFile = null;
	
	//private Map<String, Document> mapDocContents = null;
	
	private Map<String, File> mapDocContentFiles = new HashMap<String, File>();
	
	public Map<String, File> getMapDocContentFiles()
	{
		return mapDocContentFiles;
	}

	//Zip Buffer
	private final byte[] buffer = new byte[ 0xFFFF ]; 

	/**
	 * @param fileDoc
	 */
	
	public void unpackDocument(File fileDoc)
	{		
		this.docFile = fileDoc;
		if ((docFile != null) && (docFile.exists()))
		{
			File contentFile = unpackDocument(fileDoc, INtOffice.OPENDOCUMENT_CONTENT);			
			mapDocContentFiles.put(INtOffice.OPENDOCUMENT_CONTENT,contentFile);
			
			File stylesFile = unpackDocument(fileDoc, INtOffice.OPENDOCUMENT_STYLES);			
			mapDocContentFiles.put(INtOffice.OPENDOCUMENT_STYLES,stylesFile);

			// des Dokument als entpackt markieren
			packedFile = null;
		}		
	}

	private File unpackDocument(File fileDoc, String contentFile)
	{
		int fileLength = 0;
		File ooXmlContent = createEmptyTempFile(null);

		InputStream ooXmlContentIs = null;
		try
		{
			ZipFile ooZipFile = new ZipFile(fileDoc);

			Enumeration<? extends ZipEntry> ooZipEntrys = ooZipFile.entries();

			// File Schreiben
			FileOutputStream ooXmlContentFo = new FileOutputStream(ooXmlContent);

			while (ooZipEntrys.hasMoreElements())
			{
				// Elemente in der Zipdatei durchgehen
				ZipEntry ooZipEntryInFile = ooZipEntrys.nextElement();

				// Content xml entpacken
				if (ooZipEntryInFile.getName().equals(contentFile))
				{
					// Content xml in Filestream schreiben
					ooXmlContentIs = ooZipFile.getInputStream(ooZipEntryInFile);
				}
			}

			// Input Sream zurück in Datei schreiben
			while ((fileLength = ooXmlContentIs.read(buffer)) != -1)
			{
				ooXmlContentFo.write(buffer, 0, fileLength);
			}

		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ooXmlContent;
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
					
					String entryName = zipEntry.getName();
					File fileContent = mapDocContentFiles.get(entryName);
					if(fileContent != null)
					{
						// 'ContentFiles' einmischen
						zipEntry = new ZipEntry(entryName);
						
						FileInputStream in = new FileInputStream(fileContent);			
						buf = IOUtils.toByteArray(in);
					}
					else
					{
						// Zip-Komponente unverändert übernehmen
						buf = IOUtils.toByteArray(zipFile
								.getInputStream(zipEntry));
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
	public void transform(Source xsl)
	{
		File fileResult = transform(xsl, INtOffice.OPENDOCUMENT_CONTENT);
		
		if(fileResult != null)
			mapDocContentFiles.put(INtOffice.OPENDOCUMENT_CONTENT, fileResult);
	}
	
	/*
	 * Transform durch Stylesheet auf den Inhalt durchfuehren. Das interne Ziel (content,styles) wird 
	 * durch den Key festgelegt.
	 */
	public File transform(Source xsl, String contentKey)
	{
		File fileResult = null;
		InputStream is = null;		
		
		if ((xsl != null) && (packedFile == null) && StringUtils.isNotEmpty(contentKey))
		{			
			File fileContent = mapDocContentFiles.get(contentKey);
			if (fileContent != null)
			{		
				try
				{
					Source xmlSource = new StreamSource(fileContent);

					Transformer transformer = TransformerFactory.newInstance()
							.newTransformer(xsl);
					
					fileResult = createEmptyTempFile(null);
					transformer.transform(xmlSource,new StreamResult(fileResult));
					
				} catch (Exception e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finally
				{
					if(is != null)
					{
						try
						{
							is.close();
						} catch (IOException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		}
		
		return fileResult;
	}



	
}
