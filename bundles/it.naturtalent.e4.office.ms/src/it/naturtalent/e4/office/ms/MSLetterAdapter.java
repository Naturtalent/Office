package it.naturtalent.e4.office.ms;

import java.io.File;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFFooter;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.xmlbeans.XmlCursor;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSpacing;

import it.naturtalent.e4.office.letter.AbstractOfficeLetterAdapter;
import it.naturtalent.e4.office.letter.OfficeLetterModel;
import it.naturtalent.e4.office.letter.OfficeLetterProfile;
import it.naturtalent.e4.office.letter.TitelTextLetterRow;
import it.naturtalent.e4.project.address.AddressData;


public class MSLetterAdapter extends AbstractOfficeLetterAdapter
{

	public String [] LETTER_TABLENAMES = {
			LETTER_ABSENDER,
			LETTER_ADRESSE,
			LETTER_PRAESENTATION,
			LETTER_REFERENZ,
			LETTER_BETREFF,
			LETTER_SIGNATURE,
			LETTER_FOOTER
	};
		
	private Map<String,XWPFTable>letterTables = new HashMap<String, XWPFTable>();

	// key = LetterTableName, value = Anzahl der im Template vorgesehenen Zeilen
	private Map<String,Integer>templateStructure = new HashMap<String, Integer>();
	{
		templateStructure.put(LETTER_ABSENDER, 3);
		templateStructure.put(LETTER_ADRESSE, 5);
		templateStructure.put(LETTER_PRAESENTATION, 3);
		templateStructure.put(LETTER_REFERENZ, 3);
		templateStructure.put(LETTER_BETREFF, 3);
		templateStructure.put(LETTER_SIGNATURE, 3);
		templateStructure.put(LETTER_FOOTER, 10);
	}
	
	// key = LetterTableName, value = Liste der TableRows
	private Map<String,List<XWPFTableRow>>letterTableRows = new HashMap<String, List<XWPFTableRow>>();
	
	
	private MSOfficeTextDocumentHandler documentHandler = new MSOfficeTextDocumentHandler();
	private XWPFDocument document;
	
	List<XWPFParagraph> contentBeforeSig;
	List<XWPFParagraph> contentAfterSig;
	
	@Override
	public void openTextDocument(File docFile)
	{
		documentHandler.openOfficeDocument(docFile);
		document = (XWPFDocument) documentHandler.getDocument();
		if(document != null)
			detectTemplateStructure();		
	}
	
	@Override
	public void writeOfficeLetterModel()
	{
		writeDocument();
		
		documentHandler.setProperty(DEFAULT_MSLETTER_ADAPTER);
		documentHandler.saveOfficeDocument();
	}
	
	@Override
	public void closeTextDocument()
	{
		if(document != null)
		{			
			documentHandler.closeOfficeDocument();
			document = null;
			letterModel = null;
		}
	}

	@Override
	public void showTextDocument()
	{		
		documentHandler.showOfficeDocument();		
	}
	

	@Override
	public void readOfficeLetterModel()
	{
		if(document != null)
		{
			letterModel = new OfficeLetterModel();
			OfficeLetterProfile letterProfile = new OfficeLetterProfile();
			letterModel.setProfile(letterProfile);
			
			// Inhalt lesen
			if (contentBeforeSig != null)
			{
				String[] content = null;
				int n = contentBeforeSig.size();
				for (int i = 0;i < n;i++)
				{
					XWPFParagraph paragraph = contentBeforeSig.get(i);
					String line = paragraph.getText();					
					content = ArrayUtils.add(content, line);
				}
				letterModel.setContent(content);
			}			
			
			// Adresse lesen
			List<TitelTextLetterRow>lRows = readLetterTable(LETTER_ADRESSE);
			String[] rows = null;
			if (!lRows.isEmpty())
			{
				for (TitelTextLetterRow row : lRows)
					rows = ArrayUtils.add(rows, row.getTitel());
			}
			if(rows != null)
			{
				AddressData adr = AddressData.getAddressData(rows);
				letterModel.setAddress(adr);
			}

			// Betreff lesen
			letterModel.setBetreff(readLetterTable(LETTER_BETREFF));

			// Profil lesen
			letterProfile.setAbsender(readLetterTable(LETTER_ABSENDER));
			letterProfile.setReferences(readLetterTable(LETTER_REFERENZ));
			letterProfile.setSignature(readLetterTable(LETTER_SIGNATURE));
			letterProfile.setFooter(readLetterTable(LETTER_FOOTER));

		}
		
	}

	private List<TitelTextLetterRow> readLetterTable(String letterTableName)
	{ 
		List<XWPFTableRow> tableRows = letterTableRows.get(letterTableName);	
		return (tableRows != null) ? readTableRows(tableRows) : null;
	}
	
	private List<TitelTextLetterRow> readTableRows(List<XWPFTableRow> rows)
	{
		List<TitelTextLetterRow> titelTextRows = new ArrayList<TitelTextLetterRow>();
		
		for (XWPFTableRow row : rows)
		{
			TitelTextLetterRow titelTextRow = new TitelTextLetterRow();
			titelTextRows.add(titelTextRow);
			
			int nCells = row.getTableCells().size();
			for (int i = 0; i < nCells; i++)
			{
				XWPFTableCell cell = row.getCell(i);
				switch (i)
					{
						case 0:
							titelTextRow.setTitel(cell.getText());
							break;
							
						case 1:
							titelTextRow.setText(cell.getText());
							break;

						default:
							break;
					}
			}
		}
		
		return titelTextRows;
	}
	
	private void writeDocument()
	{
		List<TitelTextLetterRow> titelTextRows;

		if ((document != null) && (letterModel != null))
		{
			// Adresse
			AddressData addressdata = letterModel.getAddress();
			if (addressdata != null)
			{
				TitelTextLetterRow titelTextrow = null;
				String[] adrRows = AddressData.getAddressText(addressdata);
				List<TitelTextLetterRow> rows = new ArrayList<TitelTextLetterRow>();
				for (String adrRow : adrRows)
				{
					titelTextrow = new TitelTextLetterRow();
					titelTextrow.setTitel(adrRow);
					rows.add(titelTextrow);
				}
				writeLetterTable(LETTER_ADRESSE, rows);
			}

			// Betreff
			titelTextRows = letterModel.getBetreff();
			writeLetterTable(LETTER_BETREFF, titelTextRows);
						
			// Profile ausgeben
			OfficeLetterProfile letterProfile = letterModel.getProfile();	
			if (letterProfile != null)
			{
				// Absender				
				titelTextRows = letterProfile.getAbsender();
				writeLetterTable(LETTER_ABSENDER, titelTextRows);
				
				// Referenz
				titelTextRows = letterProfile.getReferences();				
				writeLetterTable(LETTER_REFERENZ, titelTextRows);

				// Praesentation
				titelTextRows = letterProfile.getPraesentation();
				writeLetterTable(LETTER_PRAESENTATION, titelTextRows);

				// Signatur
				titelTextRows = letterProfile.getSignature();
				writeLetterTable(LETTER_SIGNATURE, titelTextRows);
				
				// Footer
				titelTextRows = letterProfile.getFooter();
				writeLetterTable(LETTER_FOOTER, titelTextRows);
			}
			
			// Modellinhalt ausgeben
			writeContent();
			
			// dem Dokument ein 'Adapted' . Property hinzufuegen
			if(!isAdapted())
			{
				
			}
			
		}
	}
	
	@Override
	public boolean isAdapted()
	{
		if(document != null)
		{
			if(StringUtils.equals(DEFAULT_MSLETTER_ADAPTER, documentHandler.getProperty()))
					return true;
		}
		
		return false;
	}
	
	private void writeContent()
	{
		if(contentBeforeSig != null)
		{		
			// 'alten' Inhalt im Dokument loeschen			
			while(!contentBeforeSig.isEmpty())
			{
				XWPFParagraph existParagraph = contentBeforeSig.get(0);
				int pos = document.getPosOfParagraph(existParagraph);
				document.removeBodyElement(pos);
				contentBeforeSig.remove(existParagraph);
			}
			
			XWPFTable tableSig = letterTables.get(LETTER_SIGNATURE);
			XmlCursor cursor = tableSig.getCTTbl().newCursor();
			XWPFParagraph newParagraph = document.insertNewParagraph(cursor);
			setParagraphLinespace(newParagraph);
			contentBeforeSig.add(newParagraph);
			
			// Textzeilen aus dem Modell
			String [] lines = letterModel.getContent();			
			if (ArrayUtils.isNotEmpty(lines))
			{
				XWPFParagraph baseParagraph  = contentBeforeSig.get(0);

				// Textzeilen in neuen Paragraphen ausgeben
				for(int i = 0; i < lines.length;i++)
				{
					// die neuen Paragraphen werden vor dem 'base' eingefuegt
					cursor = baseParagraph.getCTP().newCursor();
					newParagraph = document.insertNewParagraph(cursor);

					// 1 - zeilieges Linespace vorgeben 
					setParagraphLinespace(newParagraph);
					
					// Ausgabe
					newParagraph.createRun().setText(lines[i]);
				}
				
				// der 'baseParagraph' wird geloescht
				int pos = document.getPosOfParagraph(baseParagraph);
				document.removeBodyElement(pos);
				contentBeforeSig = null;
			}
		}	
	}
	
	private void setParagraphLinespace(XWPFParagraph paragraph)
	{
		// 1 - zeilieges Linespace vorgeben 
		CTP ctp = paragraph.getCTP();				
		CTPPr ppr = ctp.getPPr()== null? ctp.addNewPPr() : ctp.getPPr();
		ppr.addNewSpacing();
		CTSpacing spacing = ppr.getSpacing();
		BigInteger line = new BigInteger("0");
		spacing.setAfter(line);
	}
	
	private void writeTable(XWPFTable table, List<TitelTextLetterRow> titelTextRows)	
	{
		if ((table != null) && (titelTextRows != null))
		{
			List<XWPFTableRow> tableRows = table.getRows();
			writeTableRows(tableRows, titelTextRows);
		}
	}

	private void writeLetterTable(String letterName, List<TitelTextLetterRow> titelTextRows)	
	{
		List<XWPFTableRow> tableRows = letterTableRows.get(letterName);		
		if (tableRows != null)
			writeTableRows(tableRows, titelTextRows);
	}

	private void writeTableRows(List<XWPFTableRow> tableRows, List<TitelTextLetterRow> titelTextRows)	
	{
		if(tableRows != null)
		{
			int nRows = tableRows.size();
			
			TitelTextLetterRow clearTitelTextRow = new TitelTextLetterRow();
			clearTitelTextRow.setTitel(" ");
			clearTitelTextRow.setText(" ");

			if(titelTextRows == null)
			{
				for (int row = 0; row < nRows; row++)							
					writeTableRow(tableRows.get(row), clearTitelTextRow);			
			}
			else
			{
				int nTitelTextidx = titelTextRows.size();
				int titelTextidx = 0;

				for (int row = 0; row < nRows; row++)
				{
					TitelTextLetterRow titelTextRow = (titelTextidx < nTitelTextidx) ? titelTextRows
							.get(titelTextidx++) : clearTitelTextRow;
					writeTableRow(tableRows.get(row), titelTextRow);
				}
			}
		}
	}
	
	private void writeTableRow(XWPFTableRow tableRow, TitelTextLetterRow titelTextRow)
	{
		List<XWPFTableCell> cells = tableRow.getTableCells();
					
		int nCells = cells.size();
		for (int pos = 0;pos < nCells;pos++)
		{
			String value = "";			
			XWPFTableCell cell = tableRow.getCell(pos);

				switch (pos)
				{
					case 0:
						value = titelTextRow.getTitel();						
						break;

					case 1:
						value = titelTextRow.getText();
						break;

					default:
						break;
				}
				
			writeCell(cell, value);			
		}
	}
	
	private void writeCell(XWPFTableCell cell, String value)
	{
		List<XWPFParagraph> paragraphs = cell.getParagraphs();		
		for (XWPFParagraph paragraph : paragraphs)
		{
			List<XWPFRun> runs = paragraph.getRuns();			
			while(runs.size() > 1)
				paragraph.removeRun(1);

			if(runs.size() == 1)
				runs.get(0).setText(value, 0);
		}		
	}


	private void printTitleTextLetterRows(List<TitelTextLetterRow>rows)
	{
		for(TitelTextLetterRow row : rows)
		{
			System.out.println(row.getTitel()+" | "+row.getText());	
		}		
	}
	
	private void detectTemplateStructure()
	{
		int pos;
		List<XWPFTableRow> tableRows;
		
		detectTables();
		
		XWPFTable table  = letterTables.get(LETTER_ABSENDER);
		if(table != null)
		{
			tableRows = table.getRows();
		
			pos = 0;
			pos = detectLetterSection(LETTER_ABSENDER, tableRows, pos);
			pos = detectLetterSection(LETTER_ADRESSE, tableRows, pos);
			pos = detectLetterSection(LETTER_PRAESENTATION, tableRows, pos);
			pos = detectLetterSection(LETTER_REFERENZ, tableRows, pos);
			pos = detectLetterSection(LETTER_BETREFF, tableRows, pos);
			pos = detectLetterSection(LETTER_SIGNATURE, tableRows, pos);
		}
				
		detectLetterSection(LETTER_SIGNATURE, letterTables.get(LETTER_SIGNATURE).getRows(), 0);
		detectLetterSection(LETTER_FOOTER, letterTables.get(LETTER_FOOTER).getRows(), 0);
		
		detectContentParagraphs();
	}
	
	private int detectLetterSection(String name, List<XWPFTableRow> tableRows, int pos)
	{
		int nPos = tableRows.size();
		int nRows = templateStructure.get(name);
		List<XWPFTableRow>letterRows = new ArrayList<XWPFTableRow>();
		letterTableRows.put(name, letterRows);
		
		for(int i = 0;i < nRows;i++)
		{
			if(pos >= nPos)
				break;				
			letterRows.add(tableRows.get(pos++));
		}
		return pos;
	}

	private void detectTables()
	{
		List<XWPFTable>tables = document.getTables();
		int nTables = tables.size();
		for(int i = 0;i < nTables;i++)
		{			
			switch (i)
				{
					case 0: 
						letterTables.put(LETTER_ABSENDER, tables.get(i));
						break;
						
					case 1: 
						letterTables.put(LETTER_SIGNATURE, tables.get(i));
						break;
						
					default:
						break;
				}
		}
		
		// Footertabelle
		XWPFTable table = null;
		List<XWPFFooter> footers = document.getFooterList();
		for(XWPFFooter footer : footers)
		{
			List<XWPFTable> footerTables = footer.getTables();
			if(footerTables.size() > 0)
			{
				table = footerTables.get(0);
				break;
			}
		}
		
		if(table != null)
			letterTables.put(LETTER_FOOTER, table);
	}
	
	private void detectContentParagraphs()
	{
		int sigPos = -1;
		
		contentBeforeSig = null;
		contentAfterSig = null;
		List<IBodyElement> bodyElements = document.getBodyElements();
		if(bodyElements.size() == 0)
			return;
		
		// Position der Signaturtabelle
		XWPFTable tableSig = letterTables.get(LETTER_SIGNATURE);
		if(tableSig != null)
			sigPos =  document.getPosOfTable(tableSig);
		
		contentBeforeSig = new ArrayList<XWPFParagraph>();
		contentAfterSig = new ArrayList<XWPFParagraph>();
		for (int i = 0; i < bodyElements.size(); i++)
		{
			if (bodyElements.get(i) instanceof XWPFParagraph)
			{
				XWPFParagraph paragraph = (XWPFParagraph) bodyElements.get(i);
				if (i < sigPos)
					contentBeforeSig.add(paragraph);
				else
					contentAfterSig.add(paragraph);
			}
		}
		
		if((contentBeforeSig == null) || (contentBeforeSig.isEmpty())) 
		{			
			// die neuen Paragraphen werden vor dem 'base' eingefuegt
			if (tableSig != null)
			{
				XmlCursor cursor = tableSig.getCTTbl().newCursor();
				XWPFParagraph newParagraph = document
						.insertNewParagraph(cursor);

				if (contentBeforeSig == null)
					contentBeforeSig = new ArrayList<XWPFParagraph>();
				
				if(contentBeforeSig.isEmpty())
					contentBeforeSig.add(newParagraph);				
				else 
					contentBeforeSig.set(0, newParagraph);				
			}
		}		
	}

}
