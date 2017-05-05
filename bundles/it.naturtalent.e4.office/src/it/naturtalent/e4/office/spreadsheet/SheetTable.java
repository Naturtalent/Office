package it.naturtalent.e4.office.spreadsheet;

import it.naturtalent.e4.office.BaseBean;

import java.beans.PropertyChangeEvent;
import java.util.List;

public class SheetTable extends BaseBean 
{
	public static final String PROP_ROWS = "rows"; //$NON-NLS-N$
	
	List<SheetRow>rows;

	public List<SheetRow> getRows()
	{
		return rows;
	}

	public void setRows(List<SheetRow> rows)
	{
		firePropertyChange(new PropertyChangeEvent(this, PROP_ROWS, this.rows,
				this.rows = rows));
	}
}
