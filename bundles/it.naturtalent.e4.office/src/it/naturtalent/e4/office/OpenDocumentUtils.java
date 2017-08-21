package it.naturtalent.e4.office;

import it.naturtalent.e4.office.letter.IOfficeLetterAdapter;
import it.naturtalent.e4.project.address.AddressData;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.StringTokenizer;

import javax.xml.bind.JAXB;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.NameFileFilter;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrTokenizer;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.jdom2.Attribute;
import org.jdom2.Content;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.filter.ContentFilter;
import org.jdom2.filter.Filter;
import org.jdom2.input.SAXBuilder;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;




public class OpenDocumentUtils
{

	public static final String OFFICEDATADIR = "office";
	
	public static final String NAMESPACE_TABLE = "urn:oasis:names:tc:opendocument:xmlns:table:1.0";
	public static final String NAMESPACE_TEXT = "urn:oasis:names:tc:opendocument:xmlns:text:1.0";
	public static final String NAMESPACE_OFFICE = "urn:oasis:names:tc:opendocument:xmlns:office:1.0";

	
	
	public static void copyContent(Document destDoc, Document srcDoc)
	{
		Element destOfficeTextElement = findOfficeText(destDoc);
		Element destParent = (Element) destOfficeTextElement.getParent();
		destParent.removeContent(destOfficeTextElement);
		
		Element srcOfficeTextElement = findOfficeText(srcDoc);
		destParent.addContent(srcOfficeTextElement.clone());
	}
	
	/**
	 * Kopiert alle Textelemente 'text:p' der Vorlage 'srcDoc' in das
	 * Zieldokument 'destDoc'.
	 * Die Attribute der Quellelemente werden unveraendert uebernommen.
	 * 
	 * @param destDoc
	 * @param srcDoc
	 */
	public static void copyContentText(Document destDoc, Document srcDoc)
	{
		// Parentelement ist 'text:office'
		Element destOfficeTextElement = findOfficeText(destDoc);
			
		// bestehende Texte loeschen
		destOfficeTextElement.removeChildren("p", Namespace
				.getNamespace("text",NAMESPACE_TEXT));
		
		// kopieren
		Element srcOfficeTextElement = findOfficeText(srcDoc);		
		List<Content>srcOfficeContentElement = readOfficeTextElements(srcOfficeTextElement);
		for(Content srcContent : srcOfficeContentElement)		
			destOfficeTextElement.addContent(srcContent.clone());
	}	

	
	public static void writeTableText(Document doc, String tableName, String [][] tableText)
	{
		if (tableText != null)
		{
			Element elemTable = findOfficeTable(doc, tableName);
			if (elemTable != null)
			{
				List<Content>tableRows = getTableRows(elemTable);
				if(tableRows != null)
				{
					int nRows = tableRows.size();
					for(int row = 0;row < nRows;row++)
					{
						if(row >= tableText.length)
							break;
						
						String[]rowLine = tableText[row];
						List<Content>rowCells = getRowCells(tableRows.get(row));
						if(rowCells != null)
						{
							int nCells = rowCells.size();
							for(int cell = 0;cell < nCells;cell++)
							{
								if(cell >= rowLine.length)
									break;
																
								List<Element>texte = ((Element)rowCells.get(cell)).getChildren();
								writeTextLine(texte.get(0), rowLine[cell]);
							}
						}					
					}				
				}
			}
		}
	}
	
	public static void moveContentText(Document doc)
	{
		Element officeTextElement = findOfficeText(doc);
		List<Content> existTextElemente = readOfficeTextElements(officeTextElement);		
		if (!existTextElemente.isEmpty())
		{
			// Signatur addressieren
			Element signatureTable = findOfficeTable(doc,Activator.mapXMLTextTags.get(INtOffice.DOCUMENT_SIGNATURE));
			if (signatureTable != null)
			{				
				Element parent = (Element) signatureTable.getParent();
				parent.addContent(signatureTable.clone());
				parent.removeContent(signatureTable);
			}
		}
	}

	public static void writeContentText(Document doc, String [] contentText)
	{
		Element officeTextElement = findOfficeText(doc);
		
		// an die vorhandenen Textelemente anhaengen
		List<Content>existTextElemente = readOfficeTextElements(officeTextElement);
		if (!existTextElemente.isEmpty())
		{
			// eine neues Textelement ableiten
			Element textElementNew = (Element)existTextElemente.get(0).clone();
			textElementNew.removeContent();
			
			// alten Inhalt entfernen
			officeTextElement.removeChildren("p", Namespace
					.getNamespace("text",NAMESPACE_TEXT));
			for (String line : contentText)
			{
				Element textElement = textElementNew.clone();
				writeTextLine(textElement, line);
				officeTextElement.addContent(textElement);
			}
		}		
	}

	public static void addTextLine(Document doc, String line)
	{
		Element officeTextEelement = findOfficeText(doc);		
		Element destTextElement = new Element("p",Namespace
				.getNamespace("text",NAMESPACE_TEXT));
		destTextElement.setAttribute("style", "P5", Namespace
				.getNamespace("text",NAMESPACE_TEXT));
		officeTextEelement.addContent(destTextElement);
		writeTextLine(destTextElement, line);
	}
	
	public static void writeTextLine(Element destTextElement, String line)
	{
		if (destTextElement != null)
		{
		
			if (line != null)
			{
				StringTokenizer token = new StringTokenizer(line, "\n", true);
				while (token.hasMoreTokens())
				{
					String tok = token.nextToken();
					if(StringUtils.equals(tok, "\n"))
					{
						destTextElement.addContent(new Element("line-break",Namespace
								.getNamespace("text",NAMESPACE_TEXT)));
					}
					else destTextElement.addContent(tok);
				}
			}

			
			
			/*
			String[] toks = StringUtils.split(line, '\n');
			if (toks != null)
			{
				for (int i = 0; i < toks.length; i++)
				{
					destTextElement.addContent(toks[i]);
					if(i < (toks.length -1))
					{
						destTextElement.addContent(new Element("line-break",Namespace
								.getNamespace("text",NAMESPACE_TEXT)));
					}
				}
			}
			*/
			
			
		}
	}

	public static String [] getOfficeContentTexte(Document doc)
	{
		String [] lines = null;
		
		Element officeTextElement = findOfficeText(doc);
		List<Content>officeContent = readOfficeTextElements(officeTextElement);
		
		for(Content content : officeContent)	
			lines = ArrayUtils.add(lines, getTextContent((Element) content));
				
		return lines;
	}

	public static String getOfficeContentText(Document doc)
	{
		StringBuilder contentText = new StringBuilder();
		
		Element officeTextElement = findOfficeText(doc);
		List<Content>officeContent = readOfficeTextElements(officeTextElement);
		
		for(Content content : officeContent)	
			contentText.append(getTextContent((Element) content));
				
		return contentText.toString();
	}

	/**
	 * Den Textbereich 'office:text' zurueckgeben.
	 * 
	 * @param doc
	 * @param tableName
	 * @return
	 */
	public static Element findOfficeText(Document doc)
	{
		StringBuilder pathBuilder;
		
		pathBuilder = new StringBuilder("//"+"office:text");
		
		Filter<Content> filter = new ContentFilter(ContentFilter.ELEMENT);		
		XPathExpression<Content> xpath = XPathFactory.instance()
				.compile(pathBuilder.toString(),filter,null,Namespace
						.getNamespace("office",NAMESPACE_OFFICE));
		
		return (Element) xpath.evaluateFirst(doc);
	}

	/**
	 * Alle Textelemente (text:p) des XMLDocuments in einer Liste zurueckgeben.
	 * 
	 * @param doc
	 * @return
	 */
	public static List<Content> readOfficeTextElements(Element officeTextElement)
	{
		StringBuilder pathBuilder;
		
		pathBuilder = new StringBuilder("text:p");		
		Filter<Content> filter = new ContentFilter(ContentFilter.ELEMENT);
		
		XPathExpression<Content> xpath = XPathFactory.instance()
				.compile(pathBuilder.toString(),filter,null,Namespace
						.getNamespace("text",NAMESPACE_TEXT));
		
		return xpath.evaluate(officeTextElement);				
	}
		
	/**
	 * Den Inhalt eines Textelements zu einem String zusammenfassen.
	 * Kindelemente (im wesentlichen text:line-break) werden in Zeilenvorschub "\n" gewandelt.
	 * 
	 * @param textElement
	 * @return
	 */
	public static String getTextContent(Element textElement)
	{
		StringBuilder lineBuilder = new StringBuilder();
		List<Content>contents = textElement.getContent();
	
		for(Content content : contents)
		{
			if(content.getCType() == Content.CType.Text)
			{				
				lineBuilder.append(content.getValue());
				//lineBuilder.append("\n");
				continue;
			}
			
			// 'line-break' durch "\n" ersetzen
			if(content.getCType() == Content.CType.Element)
			{				
				if(StringUtils.equals(((Element)content).getName(), "line-break"))				
					lineBuilder.append("\n");
				continue;
			}
		}
		
		return lineBuilder.toString();
	}

	/**
	 * Die Text einer Tabelle zurueckgeben.
	 * Pro Zeile wird ein Array auf Zelleninhalte dieser Zeile zurueckgegeben.
	 *  
	 * @param doc
	 * @param tableName
	 * @return
	 */
	public static List<List<String>> readTableText(Document doc, String tableName)
	{
		List<List<String>> tableText = new ArrayList<List<String>>(); 
		Element elemTable = findOfficeTable(doc, tableName);
		if(elemTable != null)
		{
			List<Content>rows = getTableRows(elemTable);
			if(rows != null)
			{
				for(Content row : rows)			
					tableText.add(getTableCellText(row));
			}			
		}
		
		return tableText;
	}

	/**
	 * Die Textinhalte aller Zellen einer Zeile zurueckgeben.
	 * 
	 * @param row
	 * @return
	 */
	public static List<String> getTableCellText(Content row)
	{
		List<String>textArray = new ArrayList<String>();

		List<Content>cells = getRowCells(row);
		if(cells != null)
		{
			for(Content cell : cells)
			{
				String s = "";
				
				List<Element>textElements = ((Element)cell).getChildren();
				for(int i = 0;i < textElements.size();i++)
				{	
					Element textElement = textElements.get(i);
					
					if(i > 0)
						s = s + "\n";
					
					// Text im Element 'text:p'
					s = s + textElement.getText();
					
					// Texte der Kindelement (z.B. text:span)
					List<Element>childElements = textElement.getChildren();
					for(Element childElement : childElements)
						s = s + childElement.getText();
					
				}
				textArray.add(s);
			}				
		}
		
		return textArray;
	}
	
	/**
	 * Die Zellelemente einer Zeile zurueckgeben.
	 * 
	 * @param row
	 * @return
	 */
	public static List<Content> getRowCells(Content row)
	{
		StringBuilder pathBuilder;
		
		pathBuilder = new StringBuilder("table:table-cell");		
		Filter<Content> filter = new ContentFilter(ContentFilter.ELEMENT);
		
		XPathExpression<Content> xpath = XPathFactory.instance()
				.compile(pathBuilder.toString(),filter,null,Namespace
						.getNamespace("table",NAMESPACE_TABLE));
		
		return xpath.evaluate(row);		
	}
	
	/**
	 * Die Zeilenelemente einer Tabelle zurueckgeben.
	 * 
	 * @param elemTable
	 * @return
	 */
	public static List<Content> getTableRows(Element elemTable)
	{
		StringBuilder pathBuilder;
		
		pathBuilder = new StringBuilder("table:table-row");		
		Filter<Content> filter = new ContentFilter(ContentFilter.ELEMENT);
		
		XPathExpression<Content> xpath = XPathFactory.instance()
				.compile(pathBuilder.toString(),filter,null,Namespace
						.getNamespace("table",NAMESPACE_TABLE));
		
		return xpath.evaluate(elemTable);
	}
	
	/**
	 * Eine Tabelle mit dem Namen 'tableName' zurueckgeben.
	 * 
	 * @param doc
	 * @param tableName
	 * @return
	 */
	public static Element findOfficeTable(Document doc, String tableName)
	{
		StringBuilder pathBuilder;
		
		pathBuilder = new StringBuilder("//"+"table:table");
		pathBuilder.append("[@"+"table:name");
		pathBuilder.append("='"+tableName+"']");
		
		Filter<Content> filter = new ContentFilter(ContentFilter.ELEMENT);
		
		XPathExpression<Content> xpath = XPathFactory.instance()
				.compile(pathBuilder.toString(),filter,null,Namespace
						.getNamespace("table",NAMESPACE_TABLE));
		
		return (Element) xpath.evaluateFirst(doc);
	}
	
	/**
	 * Den Inhalt der XML-OpenDocumentDatei 'xmlFile' als JDOM-XMLDocument zurueckgeben.
	 *  
	 * @param xmlFile
	 * @return
	 */
	public static Document getDocument(File xmlFile)
	{
		try
		{
			return new SAXBuilder().build(xmlFile);
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	/*
	 * 
	 * 
	 * 
	 * 
	 */

	//private static final String OFFICE_PROFILE_FILE = "profiles.xml";

	/**
	 * 
	 * @param ntOffice
	 * @return
	 */
	public static OfficeLetterModel getOfficeModel(INtOffice ntOffice)
	{
		OfficeLetterModel officeModel = null;
		
		if(ntOffice != null)
		{
			Document doc = ntOffice.getOpenDocument();
			if(doc != null)
			{
				// Betreff
				officeModel = new OfficeLetterModel();
				officeModel.setBetreff(BetreffRow.readBetreff(doc));
					
				// Adresse
				List<List<String>> tableText = OpenDocumentUtils.readTableText(doc,
						Activator.mapXMLTextTags.get(INtOffice.DOCUMENT_ADRESSE));
				String [][]rows = toArray(tableText);
				String [] array = null;
				for(String [] row : rows)
					array = ArrayUtils.add(array, row[0]);
				AddressData address = AddressData.getAddressData(array);
				officeModel.setAddress(address);
				
				// Inhalt
				officeModel.setContent(getOfficeContentTexte(doc));
				
				//Profil
				OfficeLetterProfile profile = new OfficeLetterProfile();
				officeModel.setProfile(profile);
				profile.setAbsender(AbsenderRow.readAbsender(doc));
				profile.setReferences(ReferenceRow.readReference(doc));
				profile.setFooter(FooterRow.readFooter(ntOffice
						.getOpenDocument(INtOffice.OPENDOCUMENT_STYLES)));
				profile.setSignature(SignatureRow.readSignature(doc));
											
			}
			
		}
		return officeModel;
	}

	/**
	 * Die im OfficeContext 'officeContext' verfuegbaren Officeprofile laden.
	 * 
	 * @param officeContext
	 * @return
	 */
	/*
	public static OfficeProfiles readProfiles(IOfficeWizard officeWizard)
	{
		File fileProfiles = new File(getOfficeDir(officeWizard.getOfficeContext()),OFFICE_PROFILE_FILE);
		try
		{
			if(!fileProfiles.exists())
				officeWizard.initializeDefaultProfile(
						officeWizard.getOfficeContext(),
						ProfilePreferences.DEFAULT_OFFICE_PROFILE);
			
			FileInputStream fis = new FileInputStream(fileProfiles);
			return (OfficeProfiles) JAXB.unmarshal(fis, OfficeProfiles.class);
			
		} catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	*/

	
	/**
	 * Die im OfficeContext 'officeContext' verfuegbaren Officeprofile laden.
	 * 
	 * @param officeContext
	 * @return
	 */
	public static OfficeProfiles readProfiles(String officeContext)
	{
		File fileProfiles = new File(getOfficeDir(officeContext),Activator.OFFICE_PROFILE_FILE);
		try
		{
			if (fileProfiles.exists())
			{
				FileInputStream fis = new FileInputStream(fileProfiles);
				return (OfficeProfiles) JAXB.unmarshal(fis,
						OfficeProfiles.class);
			}
			
		} catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}	
	
	public static void writeProfiles(String officeContext, OfficeProfiles officeProfiles)
	{
		FileOutputStream fos = null;
		File fileProfiles = new File(getOfficeDir(officeContext),Activator.OFFICE_PROFILE_FILE);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		JAXB.marshal(officeProfiles, out);
		ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());

		try
		{
			fos = new FileOutputStream(fileProfiles);
			fos.write(out.toByteArray());
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			try
			{
				fos.close();
			} catch (IOException e)
			{
			}
		}
	}
	
	public static void writeProfiles(File destFile, OfficeProfiles officeProfiles)
	{
		FileOutputStream fos = null;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		JAXB.marshal(officeProfiles, out);
		ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());

		try
		{
			fos = new FileOutputStream(destFile);
			fos.write(out.toByteArray());
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			try
			{
				fos.close();
			} catch (IOException e)
			{
			}
		}
	}
	
	public static String [][] toArray(List<List<String>>arrayList)
	{
		String [][] array = null;
		for(List<String>lRow : arrayList)
		{
			String [] row = lRow.toArray(new String[lRow.size()]);
			array = ArrayUtils.add(array, row);
		}
		
		return array;
	}
	
	/**
	 * Initialisiert ein Defaultprofil
	 * 
	 * @param officContext
	 * @param defaultProfileName
	 */
	public static void initializeDefaultProfile(String officContext, String defaultProfileName)
	{
		OfficeProfiles officeProfiles = new OfficeProfiles();	
		ArrayList<OfficeLetterProfile>profiles = new ArrayList<OfficeLetterProfile>();
		
		OfficeLetterProfile defaultProfile = new OfficeLetterProfile();		
		defaultProfile.setName(defaultProfileName);
		
		// Absender
		List<AbsenderRow>absender = new ArrayList<AbsenderRow>();
		AbsenderRow absRow = new AbsenderRow();
		absRow.setText("Name");
		absender.add(absRow);
		absRow = new AbsenderRow();
		absRow.setText("Adresse");
		absender.add(absRow);
		defaultProfile.setAbsender(absender);
		
		// Praesentation
		List<PraesentationRow>praesentation = new ArrayList<PraesentationRow>();
		PraesentationRow praesentationRow = new PraesentationRow();
		praesentationRow.setTitel("Naturtalent");		
		praesentation.add(praesentationRow);
		
		praesentationRow = new PraesentationRow();
		praesentationRow.setTitel("Webseite");		
		praesentation.add(praesentationRow);

		praesentationRow = new PraesentationRow();
		praesentationRow.setTitel("mobil Telefon");		
		praesentation.add(praesentationRow);

		praesentationRow = new PraesentationRow();
		praesentationRow.setTitel("eMail");		
		praesentation.add(praesentationRow);
		
		defaultProfile.setPraesentation(praesentation);
		
		// Referenzen		
		ReferenceRow reference;
		ArrayList<ReferenceRow>references = new ArrayList<ReferenceRow>();
		reference = new ReferenceRow();
		reference.setTitel("Ihre Referenzen");		
		references.add(reference);
		reference = new ReferenceRow();
		reference.setTitel("Ansprechpartner");		
		references.add(reference);
		reference = new ReferenceRow();
		reference.setTitel("Durchwahl");		
		references.add(reference);		
		defaultProfile.setReferences(references);
		
		// Footer
		FooterRow footerRow;
		ArrayList<FooterRow>footerRows = new ArrayList<FooterRow>();
		footerRow = new FooterRow();
		footerRow.setTitel("Bankverbindung");
		footerRow.setText("Bankdaten");
		footerRows.add(footerRow);
		defaultProfile.setFooter(footerRows);
		
		
		profiles.add(defaultProfile);
		officeProfiles.setProfiles(profiles);
		
		writeProfiles(officContext, officeProfiles);
			
		/*
		File fileProfiles = new File(getOfficeDir(officeName),OFFICE_PROFILE_FILE);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		JAXB.marshal(officeProfiles, out);
		ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
		
		try
		{
			FileOutputStream fos = new FileOutputStream(fileProfiles);
			fos.write(out.toByteArray());
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/		
	}

	
	/*
	 * 
	 * 
	 * 
	 * 
	 */

	/**
	 * Die Layoutvorlagen sind im jeweiligen office-Verzeichnis als Dateien 
	 * mit der Erweiterung "odt" und "docx" abgelegt.
	 * Zurueckgegeben wird eine Liste mit diesen Dateien.
	 * 
	 * @param officeName
	 * @return
	 */
	public static List<File> getOfficeTemplates(String officeName)
	{
		IOFileFilter iof = FileFilterUtils.asFileFilter(new FilenameFilter()
		{			
			@Override
			public boolean accept(File dir, String name)
			{
				String ext = FilenameUtils.getExtension(name);				
				return ((ext.equals("odt")) || ((ext.equals("docx"))));				
			}
		});
				
		return FileFilterUtils.filterList(iof, getOfficeDir(officeName).listFiles());
	}

	public static File getOfficeResource(String officeName, String fileName)
	{
		File officeDir = getOfficeDir(officeName);
		if(officeDir != null)
			return (new File(officeDir, fileName));
		
		return null;
	}

	public static File getOfficeDir(String officeName)
	{
		File baseDir = getBaseOfficeDir();
		if(baseDir != null)
		{
			File officeDir = new File(baseDir, officeName);
			if(!officeDir.exists())
			{
				if(officeDir.mkdir())
					return officeDir;
			}
			else
				if(officeDir.isDirectory())				
					return officeDir;		
		}
				
		return null;
	}

	/*
	 * Gibt das Directory der Vorlagen im aktuellen Workspace ('office') zurueck
	 */
	private static File getBaseOfficeDir()
	{
		IWorkspace workspace = ResourcesPlugin.getWorkspace();  
		File officeDir = new File(workspace.getRoot().getLocation().toFile(),OFFICEDATADIR);
		
		if(!officeDir.exists())
		{
			if(officeDir.mkdir())
				return officeDir;
		}
		else
			if(officeDir.isDirectory())				
				return officeDir;		
				
		return null;
	}

	/*
	 * 
	 * 
	 * 
	 */

	private static final String OFFICE_TEXTMODULES_FILE = "modules.xml";
	
	public static void writeTextModules(String officeContext, TextModuleModel model)
	{
		File fileModules = new File(getOfficeDir(officeContext),OFFICE_TEXTMODULES_FILE);
		writeTextModules(fileModules, model);
	}
	
	public static void writeTextModules(File fileModule, TextModuleModel model)
	{
		FileOutputStream fos = null;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		JAXB.marshal(model, out);
		
		try
		{
			fos = new FileOutputStream(fileModule);
			fos.write(out.toByteArray());
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally
		{
			try
			{
				fos.close();
			} catch (IOException e)
			{
			}
		}		
	}
	
	/**
	 * Die im OfficeContext 'officeContext' verfuegbaren Officeprofile laden.
	 * 
	 * @param officeContext
	 * @return
	 */
	public static TextModuleModel readTextModules(String officeContext)
	{
		File fileModules = new File(getOfficeDir(officeContext),OFFICE_TEXTMODULES_FILE);
		
		if(!fileModules.exists())
			return new TextModuleModel();
		
		try
		{
			FileInputStream fis = new FileInputStream(fileModules);
			return (TextModuleModel) JAXB.unmarshal(fis, TextModuleModel.class);
			
		} catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		
		return null;
	}


	

	
	
	/*
	 * 
	 * 
	 * 
	 */

	/**
	 * Alle Spreadsheets zurueckgeben.
	 * 
	 * @param doc
	 * @param tableName
	 * @return
	 */
	public static List<Content> findSpreadsheetTables(Document doc)
	{
		StringBuilder pathBuilder;
		
		pathBuilder = new StringBuilder("//"+"office:spreadsheet");		
		Filter<Content> filter = new ContentFilter(ContentFilter.ELEMENT);
		
		XPathExpression<Content> xpath = XPathFactory.instance()
				.compile(pathBuilder.toString(),filter,null,Namespace
						.getNamespace("office",NAMESPACE_OFFICE));
		
		return xpath.evaluate(doc);
	}

	/**
	 * Eine Tabelle mit dem Namen 'tableName' zurueckgeben.
	 * 
	 * @param doc
	 * @param tableName
	 * @return
	 */
	public static Element findSpreadsheetTable(List<Content>sheets, String sheetName)
	{
		for(Content sheet : sheets)
		{
			Element elemChild = ((Element) sheet).getChildren().get(0);
			String name = elemChild.getAttributeValue("name",Namespace
					.getNamespace("table",NAMESPACE_TABLE));			
			if(StringUtils.equals(name, sheetName))
				return elemChild;
		}

		return null;
	}
	
	public static void setSpreadsheetTableName(Element spreadSheetTable, String name)
	{
		String tableName = spreadSheetTable.getAttributeValue("name",Namespace
				.getNamespace("table",NAMESPACE_TABLE));
		if(StringUtils.isNotEmpty(tableName))
		{
			spreadSheetTable.setAttribute("name",name,Namespace
					.getNamespace("table",NAMESPACE_TABLE));
		}
	}
	
	/**
	 * Die Zellelemente einer Tabellenzeile zurueckgeben.
	 * 
	 * @param elemTable
	 * @return
	 */
	public static List<Content> getTableRowCells(Element elemTableRow)
	{
		StringBuilder pathBuilder;
		
		pathBuilder = new StringBuilder("table:table-cell");		
		Filter<Content> filter = new ContentFilter(ContentFilter.ELEMENT);
		
		XPathExpression<Content> xpath = XPathFactory.instance()
				.compile(pathBuilder.toString(),filter,null,Namespace
						.getNamespace("table",NAMESPACE_TABLE));
		
		return xpath.evaluate(elemTableRow);
	}

	
	public static void writeSpreadsheetRow(Element tableRow, List<String>cellText)
	{
		List<Content>tableCells = getTableRowCells(tableRow);
		int n = cellText.size();
		int i = 0;
		for(Content content : tableCells)
		{
			if(i >= cellText.size())
				break;
			String textValue = cellText.get(i++); 
			
			Element cellElement = (Element) content;
			Element textElement = cellElement.getChild("p", Namespace
					.getNamespace("text",NAMESPACE_TEXT));
			if(textElement == null)
			{
				textElement = new Element("p",Namespace
						.getNamespace("text",NAMESPACE_TEXT));
				cellElement.addContent(textElement);
			}
			textElement.setText(textValue);
		}
	}

	/*
	 * 
	 * 
	 * 
	 */

	public static File getTemporaryTemplate(String relativeWorkspaceDir, String templateName)
	{
		File temporaerFile = null;
		File workspaceDir = getWorkspaceTemplateDir(relativeWorkspaceDir);
		if(workspaceDir.exists() && workspaceDir.isDirectory())
		{
			File templateFile = new File(workspaceDir, templateName);
			if(templateFile.exists())
			{				
				// Vorlage in temporaere Datei umkopieren
				String extention = FilenameUtils.getExtension(templateFile.getPath());
				extention = (StringUtils.isEmpty(extention)) ? null : "."+ extention;
				try
				{
					temporaerFile = File.createTempFile("temporaerTemplate", extention); //$NON-NLS-N$
					FileUtils.copyFile(templateFile, temporaerFile);
				} catch (IOException e)
				{				
					e.printStackTrace();
				}
			}
		}
		
		return temporaerFile;
	}	
	
	
	/*
	 * 
	 * 
	 * 
	 */
	
	private static final String PLUGIN_TEMPLATE_DIR = File.separator + "templates"; //$NON-NLS-1$

	public static File getWorkspaceTemplate(String workspaceDir, String templateName)
	{
		File templateDir = getWorkspaceTemplateDir(workspaceDir);
		if (templateDir != null)
		{
			File templateFile = new File(templateDir, templateName);
			if (templateFile.exists())
				return templateFile;
		}

		return null;
	}
	
	public static File getPluginTemplateDir(Class<?>pluginClass)
	{
		try
		{
			BundleContext bundleContext = FrameworkUtil.getBundle(pluginClass)
			                .getBundleContext();                    
			Path path = new Path(PLUGIN_TEMPLATE_DIR); 
			URL url = FileLocator.find(bundleContext.getBundle(), path, null);
			url = FileLocator.resolve(url);
			
			return FileUtils.toFile(url);
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	
	public static void registerLetterTemplate(File template,
			String officeContext, IOfficeService officeService, String textLetterAdapterClass)
	{
		File workspaceDir = OpenDocumentUtils.getWorkspaceTemplateDir(OpenDocumentUtils.OFFICEDATADIR + File.separator
				+ officeContext);
		
		File workspaceTemplateFile = new File(workspaceDir,template.getName());
		officeService.registerLetterTemplate(workspaceTemplateFile.getPath(), textLetterAdapterClass);

		if(!workspaceTemplateFile.exists())
		{
			try
			{
				FileUtils.copyFile(template, workspaceTemplateFile);
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	
	/**
	 * Kopiert ein PluginTemplate in den aktuellen Workspace und registriert es gleichzeitig
	 * in der OfficeService Registry
	 * 
	 * @param pluginTemplate
	 * @param officeContext
	 * @param officeService
	 */
	public static void registerPluginLetterTemplate(File pluginTemplate,
			String officeContext, IOfficeService officeService)
	{
		// den Zielpfad der Vorlage im aktuellen Workspace deginiern (ist auch Key der Vorlage im Registry) 
		File workspaceDir = OpenDocumentUtils.getWorkspaceTemplateDir(OpenDocumentUtils.OFFICEDATADIR + File.separator
				+ officeContext);
		File workspaceTemplateFile = new File(workspaceDir,pluginTemplate.getName());
		
		// Template registieren
		officeService.registerLetterTemplate(workspaceTemplateFile.getPath());

		// ggf. auch physikalisch in das Workspace kopieren
		if(!workspaceTemplateFile.exists())
		{
			try
			{
				FileUtils.copyFile(pluginTemplate, workspaceTemplateFile);
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/*
	 * Kopiert alle Dateien mit einer bestimment Dateierweiterung (z.B. odt) in das Zielverzeichnis im Workspace. 
	 * 
	 * templateDirName = Unterverzeichnis im Workspace (office/xntw)
	 */
	private static File destDir = null;
	public static void copyTemplatesToWorkspace(File templatesSrcDir, String templateDirName, String suffixFilter)
	{
		// filtert auf Dateierweiterung
		IOFileFilter odtSuffixFilter = null;
		
		// filtert existierende Dateien
		IOFileFilter existFileFilter = new IOFileFilter(){

			@Override
			public boolean accept(File arg0)
			{
				File fileCheck = new File(destDir,arg0.getName());
				boolean check = !fileCheck.exists();
				return check;
			}

			@Override
			public boolean accept(File arg0, String arg1)
			{				
				return true;
			}			
		};
				
		try
		{
			destDir = getWorkspaceTemplateDir(templateDirName);
						
			if (StringUtils.isNotEmpty(suffixFilter))
			{
				odtSuffixFilter = FileFilterUtils
						.suffixFileFilter(suffixFilter);

				odtSuffixFilter = FileFilterUtils.and(odtSuffixFilter, existFileFilter);
				FileUtils.copyDirectory(templatesSrcDir, destDir,
						odtSuffixFilter);
			}
			else
			{
				FileUtils.copyDirectory(templatesSrcDir, destDir);
			}
			
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	
	public static void copyDefaultProfileToWorkspace(File srcDir, String officeContext)
	{
		try
		{
			File srcProfileFile = new File(srcDir,
					Activator.OFFICE_LETTERPROFILE_FILE);
			if (srcProfileFile.exists())
			{
				File destProfileFile = new File(ResourcesPlugin.getWorkspace()
						.getRoot().getLocation().toFile(),
						IOfficeService.OFFICEDATADIR + File.separator
								+ officeContext);

				destProfileFile = new File(destProfileFile,
						Activator.OFFICE_LETTERPROFILE_FILE);
				if (!destProfileFile.exists())
					FileUtils.copyFile(srcProfileFile, destProfileFile);
			}
		} catch (Exception e)
		{
			// TODO: handle exception
		}
	}
	
	
	public static File getWorkspaceTemplateDir(String templateDirName)
	{
		IWorkspace workspace = ResourcesPlugin.getWorkspace();  
		File archivDir = new File(workspace.getRoot().getLocation().toFile(), templateDirName);
		
		
		try
		{
			FileUtils.forceMkdir(archivDir);
			return archivDir;
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		/*
		if(!archivDir.exists())
		{
			if(archivDir.mkdir())
				return archivDir;
		}
		else
			if(archivDir.isDirectory())				
				return archivDir;
				*/		
				
		return null;
	}
}


