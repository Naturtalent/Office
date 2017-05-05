package it.naturtalent.e4.office.spreadsheet;

import it.naturtalent.e4.office.BaseBean;

import java.beans.PropertyChangeEvent;
import java.util.List;

public class SheetRow extends BaseBean 
{
	public static final String PROP_CELLS = "cells"; //$NON-NLS-N$
	
	List<SheetCell>cells;

	public List<SheetCell> getCells()
	{
		return cells;
	}

	public void setCells(List<SheetCell> cells)
	{
		firePropertyChange(new PropertyChangeEvent(this, PROP_CELLS, this.cells,
				this.cells = cells));
	}
	
}
