package it.naturtalent.e4.office.ui;

import java.util.ArrayList;
import java.util.List;

import org.odftoolkit.simple.table.Cell;
import org.odftoolkit.simple.table.CellRange;
import org.odftoolkit.simple.table.Table;
import org.odftoolkit.simple.text.Paragraph;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

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
		{
			// ChildNodes (text:span) entfernen
			List<Node>toDelNodes = new ArrayList<Node>();
			NodeList nodes =  para.getOdfElement().getChildNodes();			
			int n = nodes.getLength();
			if (n > 0)
			{							
				for (int i = 0; i < n; i++)				
					toDelNodes.add(nodes.item(i));
				
				for(Node oldNode : toDelNodes)
					para.getOdfElement().removeChild(oldNode);
			}
		}

		para.setTextContent(text);
	}

	public static String readCellText(Cell cell)
	{
		Paragraph para = cell.getParagraphByIndex(0, false);
		if (para == null)
			return cell.getStringValue();
		else
		{	
			// auf ChildNodes (text:span) verteilten Text zusammenfassen
			NodeList nodes =  para.getOdfElement().getChildNodes();			
			int n = nodes.getLength();
			if (n > 0)
			{
				StringBuilder textContext = new StringBuilder();
				for (int i = 0; i < n; i++)
				{
					Node node = nodes.item(i);
					textContext.append(node.getTextContent());
				}
				return textContext.toString();
			}
			
			return para.getTextContent();
		}
		
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
	
	/**
	 * @param row
	 * @param col
	 * @param text
	 */
	public static String readTableText(Table table, int row, int col)
	{
		CellRange cellRange = markTable(table);
		Cell cell = cellRange.getCellByPosition(col, row);
		return readCellText(cell);
	}


}
