package it.naturtalent.e4.office.spreadsheet;

import it.naturtalent.e4.office.BaseBean;
import java.beans.PropertyChangeEvent;

public class SheetCell extends BaseBean 
{
	public static final String PROP_CELL = "cell"; //$NON-NLS-N$
	
	protected String cell;
	
	public String getCell()
	{
		return cell;
	}

	public void setCell(String cell)
	{
		firePropertyChange(new PropertyChangeEvent(this, PROP_CELL, this.cell,
				this.cell = cell));
	}

}
