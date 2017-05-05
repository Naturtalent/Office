package it.naturtalent.e4.office.ui.letter;

import it.naturtalent.e4.office.letter.TitelTextLetterRow;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;

public class TextCellEditingSupport extends EditingSupport
{
	protected final TableViewer viewer;
	
	public TextCellEditingSupport(TableViewer viewer)
	{
		super(viewer);
		this.viewer = viewer;
	}

	@Override
	protected Object getValue(Object element)
	{
		TitelTextLetterRow row = (TitelTextLetterRow) element;
		return (row.getText() == null) ? "" : row.getText();
	}

	@Override
	protected void setValue(Object element, Object value)
	{
		((TitelTextLetterRow) element).setText(String.valueOf(value));
		viewer.update(element, null);
	}

	@Override
	protected CellEditor getCellEditor(Object element)
	{
		return new TextCellEditor(viewer.getTable());
	}

	@Override
	protected boolean canEdit(Object element)
	{		
		return true;
	}
	
	
}
