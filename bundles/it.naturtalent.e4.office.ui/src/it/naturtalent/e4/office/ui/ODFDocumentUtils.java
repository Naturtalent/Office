package it.naturtalent.e4.office.ui;

import org.odftoolkit.simple.table.Cell;
import org.odftoolkit.simple.table.CellRange;
import org.odftoolkit.simple.table.Table;
import org.odftoolkit.simple.text.Paragraph;

public class ODFDocumentUtils
{
	
	/**
	 * @param row
	 * @param col
	 * @param text
	 */
	public static void writeTableText(Table table, int row, int col, String text)
	{
		CellRange cellRange = markTable(table);
		Cell cell = cellRange.getCellByPosition(col, row);
		writeCellText(cell, text);
	}

	/**
	 * @param cell
	 * @param text
	 */
	public static void writeCellText(Cell cell, String text)
	{
		Paragraph para = cell.getParagraphByIndex(0, false);
		if (para == null)
			cell.addParagraph(text);
		else
			para.setTextContent(text);
	}

	/**
	 * Einen Tabellenbereich markieren und den Bereich zurueckgeben.
	 * 
	 * @param table
	 * @return
	 */
	public static CellRange markTable(Table table)
	{
		if (table != null)
		{
			CellRange cellRange = table.getCellRangeByPosition(0,
					0, table.getColumnCount() - 1, table.getRowCount() - 1);
			return cellRange;
		}
		return null;
	}
	
	/**
	 * Einen Tabellenbereich loeschen.
	 * 
	 * @param cellRange
	 */
	public static void clearCellRange(CellRange cellRange)
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
}
