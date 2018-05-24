package it.naturtalent.e4.office.spreadsheet;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.odftoolkit.odfdom.type.Color;
import org.odftoolkit.simple.TextDocument;
import org.odftoolkit.simple.style.Border;
import org.odftoolkit.simple.style.Font;
import org.odftoolkit.simple.style.StyleTypeDefinitions;
import org.odftoolkit.simple.table.Cell;
import org.odftoolkit.simple.table.CellRange;
import org.odftoolkit.simple.table.Row;
import org.odftoolkit.simple.table.Table;
import org.odftoolkit.simple.text.Footer;
import org.odftoolkit.simple.text.Paragraph;

import it.naturtalent.e4.office.letter.AbstractOfficeLetterAdapter;
import it.naturtalent.e4.office.letter.OfficeLetterModel;
import it.naturtalent.e4.office.letter.OfficeLetterProfile;
import it.naturtalent.e4.office.letter.TitelTextLetterRow;
import it.naturtalent.e4.project.ITitelTextLetterRow;
import it.naturtalent.libreoffice.odf.ODFOfficeDocumentHandler;

public class ODFLetterAdapter extends AbstractOfficeLetterAdapter
{

	public static String STANDARD_FONTNAME = "Arial";	
	protected String defaultFontName = STANDARD_FONTNAME;
	private Font defaultFont = new Font(STANDARD_FONTNAME, StyleTypeDefinitions.FontStyle.REGULAR, 12.0, Color.BLACK);
		
	private ODFOfficeDocumentHandler documentHandler = new ODFOfficeDocumentHandler();
	private TextDocument document;
	private Log log = LogFactory.getLog(ODFLetterAdapter.class.getName());

	@Override
	public void openTextDocument(File docFile)
	{
		documentHandler.openOfficeDocument(docFile);
		document = (TextDocument) documentHandler.getDocument();
		defaultFont = new Font(defaultFontName, StyleTypeDefinitions.FontStyle.REGULAR, 12.0, Color.BLACK);
	}

	@Override
	public void showTextDocument()
	{
		documentHandler.showOfficeDocument();		
	}

	@Override
	public void readOfficeLetterModel()
	{
		if (document != null)
		{
			letterModel = new OfficeLetterModel();
			OfficeLetterProfile letterProfile = new OfficeLetterProfile();
			letterModel.setProfile(letterProfile);

			// Inhalt lesen
			String[]content = null;
			for (Iterator<Paragraph> para = document.getParagraphIterator(); para
					.hasNext();)
			{
				if((content != null) && StringUtils.isEmpty(content.toString()))
					content = ArrayUtils.add(content, "\n");				
				content = ArrayUtils.add(content, para.next().getTextContent());								
			}
			letterModel.setContent(content);
						
			// Adresse lesen
			List<TitelTextLetterRow>lRows = readTableRows(LETTER_ADRESSE);
			String[] rows = null;
			if (!lRows.isEmpty())
			{
				for (ITitelTextLetterRow row : lRows)
					rows = ArrayUtils.add(rows, row.getTitel());
			}
			if(rows != null)
			{
				log.error("Adresseeingabe definieren");
				System.out.println("Adresseeingabe definieren s. log");
				
				//AddressData adr = AddressData.getAddressData(rows);
				//letterModel.setAddress(adr);
			}
			
			// Betreff lesen
			letterModel.setBetreff(readTableRows(LETTER_BETREFF));

			// Profil lesen
			letterProfile.setAbsender(readTableRows(LETTER_ABSENDER));
			letterProfile.setReferences(readTableRows(LETTER_REFERENZ));
			letterProfile.setFooter(readFooterRows());
			letterProfile.setSignature(readTableRows(LETTER_SIGNATURE));
		}
	}

	@Override
	public void writeOfficeLetterModel()
	{
		writeDocument();
		documentHandler.setProperty(DEFAULT_LETTER_ADAPTER);
		documentHandler.saveOfficeDocument();
	}

	@Override
	public void closeTextDocument()
	{
		if (document != null)
		{
			documentHandler.closeOfficeDocument();			
			document = null;
			letterModel = null;
		}
	}
	
	private void writeDocument()
	{
		Table table;
		CellRange cellRange;
		
		if ((document != null) && (letterModel != null))
		{
			// 'alten' Inhalt im Dokument loeschen
			List<Paragraph>allParagraphs = new ArrayList<Paragraph>();
			for (Iterator<Paragraph> para = document.getParagraphIterator(); para.hasNext();)
				allParagraphs.add(para.next());
			for(Paragraph paragraph : allParagraphs)
				document.removeParagraph(paragraph);								
			
			// Modellinhalt ausgeben
			String [] lines = letterModel.getContent();			
			if (ArrayUtils.isNotEmpty(lines))
			{
				for (String line : letterModel.getContent())
				{
					Paragraph para = document.addParagraph(line);
					para.setFont(defaultFont);					
				}
			}

			// Adresse	
			table = document.getTableByName(LETTER_ADRESSE);
			if (table != null)
			{
				cellRange = markTable(table);
				clearCellRange(cellRange);
				
				log.info("Adresseeingabe definieren");
				System.out.println("Adresseeingabe definieren s. log");


				/*
				AddressData addressdata = letterModel.getAddress();				
				if(addressdata != null)
				{
					TitelTextLetterRow row;
					String [] adrRows = AddressData.getAddressText(addressdata);  					
					List<TitelTextLetterRow> rows = new ArrayList<TitelTextLetterRow>();
					for(String adrRow : adrRows)
					{
						row = new TitelTextLetterRow();
						row.setTitel(adrRow);
						rows.add(row);						
					}
					writeTable(cellRange, rows);
				}
				*/
			}
	
			// Betreff
			table = document.getTableByName(LETTER_BETREFF);
			if (table != null)
			{
				cellRange = markTable(table);
				clearCellRange(cellRange);
				writeTable(cellRange, letterModel.getBetreff());
			}
			
			// Profile ausgeben
			OfficeLetterProfile letterProfile = letterModel.getProfile();	
			if (letterProfile != null)
			{

				// Referenz
				table = document.getTableByName(LETTER_REFERENZ);
				if (table != null)
				{
					cellRange = markTable(table);
					clearCellRange(cellRange);
					writeTable(cellRange, letterProfile.getReferences());
				}

				// Absender
				table = document.getTableByName(LETTER_ABSENDER);
				if (table != null)
				{
					cellRange = markTable(table);
					clearCellRange(cellRange);
					writeTable(cellRange, letterProfile.getAbsender());
				}

				// Praesentation
				table = document.getTableByName(LETTER_PRAESENTATION);
				if (table != null)
				{
					cellRange = markTable(table);
					clearCellRange(cellRange);
					writeTable(cellRange, letterProfile.getPraesentation());
				}

				// Footer
				table = document.getFooter().getTableByName(LETTER_FOOTER);
				if (table != null)
				{
					cellRange = markTable(table);
					clearCellRange(cellRange);
					writeTable(cellRange, letterProfile.getFooter());
				}

				// Signatur
				table = updateSigatureTable();
				if (table != null)
				{
					cellRange = markTable(table);
					clearCellRange(cellRange);
					writeTable(cellRange, letterProfile.getSignature());
				}
			}

		}
		
	}
	
	/*
	 * TitelTextLetterRows in einen Tabellenbereich ausgeben
	 * 
	 */
	private void writeTable(CellRange cellRange, List<TitelTextLetterRow> rows)
	{
		if (cellRange != null)
		{
			if ((rows != null) && (!rows.isEmpty()))
			{				
				int nRows = cellRange.getRowNumber();
				int nCols = cellRange.getColumnNumber();
				for(int r = 0;r < nRows;r++)
				{
					for(int c = 0;c < nCols;c++)
					{
						Cell cell = cellRange.getCellByPosition(c, r);
						String value = "";

						switch (c)
							{
								case 0:
									value = getTitleLetterRow(rows, r);
									break;

								case 1:
									value = getTextLetterRow(rows, r);
									break;

								default:
									break;
							}

						Paragraph para = cell.getParagraphByIndex(0, false);
						if (para == null)
							cell.addParagraph(value);
						else
							para.setTextContent(value);
					}
				}
			}
		}
	}
	
	private String getTextLetterRow(List<TitelTextLetterRow> rows, int row)
	{
		return (row < rows.size()) ? rows.get(row).getText() : "";
	}

	private String getTitleLetterRow(List<TitelTextLetterRow> rows, int row)
	{
		return (row < rows.size()) ? rows.get(row).getTitel() : "";
	}

	private void clearCellRange(CellRange cellRange)
	{
		if (cellRange != null)
		{
			int nRows = cellRange.getRowNumber();
			int nCols = cellRange.getColumnNumber();
			for (int r = 0; r < nRows; r++)
			{
				for (int c = 0; c < nCols; c++)
				{
					{
						Cell cell = cellRange.getCellByPosition(c, r);
						Paragraph para = cell.getParagraphByIndex(c, false);
						if (para != null)
							para.setTextContent("");
					}
				}
			}
		}
	}
	
	private CellRange markTable(Table table)
	{
		if (table != null)
		{
			CellRange cellRange = table.getCellRangeByPosition(0,
					0, table.getColumnCount() - 1, table.getRowCount() - 1);
			return cellRange;
		}
		return null;
	}
	
	private Table updateSigatureTable()
	{
		Table newSignatureTable = null;

		Table oldSignatureTable = document.getTableByName(LETTER_SIGNATURE);
		if (oldSignatureTable != null)
		{
			Cell refCell = oldSignatureTable.getCellByPosition(0, 0);			
			int nRows = oldSignatureTable.getRowCount();
			int nColumns = oldSignatureTable.getColumnCount();

			// 'alte' Signaturtabelle entfernen
			oldSignatureTable.remove();

			newSignatureTable = document.addTable(nRows, nColumns);
			newSignatureTable.setTableName(LETTER_SIGNATURE);
			
			// die Signaturtabelle 'markieren'
			CellRange cellRange = newSignatureTable.getCellRangeByPosition(0,
					0, nColumns - 1, nRows - 1);
			
			// DefaultFont setzen
			setTabelRangeFont(cellRange, defaultFont);
			
			// Border unsichtbar
			Border border = new Border(Color.WHITE, 0.0, StyleTypeDefinitions.SupportedLinearMeasure.CM);
			setTabelRangeBorder(cellRange, border);			
		}
		return newSignatureTable;
	}
	
	private void setTabelRangeFont(CellRange range, Font font)
	{
		for(int iRow=0;iRow < range.getRowNumber();iRow++)
		{
			for(int iCol=0;iCol<range.getColumnNumber();iCol++)
			{
				Cell cell = range.getCellByPosition(iCol,iRow);
				cell.setFont(font);
			}
		}
	}
	
	private void setTabelRangeBorder(CellRange range, Border border)
	{		
		for(int iRow=0;iRow < range.getRowNumber();iRow++)
		{
			for(int iCol=0;iCol<range.getColumnNumber();iCol++)
			{
				Cell cell = range.getCellByPosition(iCol,iRow);
				cell.setBorders(StyleTypeDefinitions.CellBordersType.TOP, border);
				cell.setBorders(StyleTypeDefinitions.CellBordersType.BOTTOM, border);
				cell.setBorders(StyleTypeDefinitions.CellBordersType.LEFT, border);
				cell.setBorders(StyleTypeDefinitions.CellBordersType.RIGHT, border);
			}
		}
	}
	
	

	/*
	 * 
	 * 
	 * 
	 */
	private List<TitelTextLetterRow> readFooterRows()
	{
		Footer footer = document.getFooter();
		Table table = footer.getTableByName(LETTER_FOOTER);		
		return readTableRows(table);
	}
	
	private List<TitelTextLetterRow> readTableRows(String tableName)
	{		
		Table table = document.getTableByName(tableName);
		return readTableRows(table);
	}
	
	private List<TitelTextLetterRow> readTableRows(Table table)
	{
		List<TitelTextLetterRow> titelTextRows = new ArrayList<TitelTextLetterRow>();
		if (table != null)
		{
			List<Row> rows = table.getRowList();
			for (Row row : rows)
			{
				TitelTextLetterRow titelTextRow = new TitelTextLetterRow();
				titelTextRows.add(titelTextRow);
				int nCells = row.getCellCount();
				for (int i = 0; i < nCells; i++)
				{
					Cell cell = row.getCellByIndex(i);
					switch (i)
						{
							case 0:
								titelTextRow.setTitel(cell.getStringValue());
								break;

							case 1:
								titelTextRow.setText(cell.getStringValue());
								break;

							default:
								break;
						}
				}
			}
		}

		return titelTextRows;
	}

	@Override
	public boolean isAdapted()
	{
		if(document != null)
		{
			if(StringUtils.equals(DEFAULT_LETTER_ADAPTER, documentHandler.getProperty()))
				return true;			
		}
		
		return false;
	}
	


}
