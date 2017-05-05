package it.naturtalent.e4.kontakte.ui.parts;

import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.jface.databinding.viewers.ObservableMapLabelProvider;
import org.eclipse.jface.viewers.IColorDecorator;
import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

public class ContactObservableLabelProvider extends ObservableMapLabelProvider
		implements IColorProvider
{

	final Display display = Display.getCurrent();
	
	private boolean filterState = false;
	
	public ContactObservableLabelProvider(IObservableMap [] attributeMap)
	{
		super(attributeMap);		
	}
	
	@Override
	public Color getForeground(Object element)
	{	
		return (filterState) ? display.getSystemColor(SWT.COLOR_BLUE) : null;
	}

	@Override
	public Color getBackground(Object element)
	{
		// TODO Auto-generated method stub
		return null;
	}

	public void setFilterState(boolean filterState)
	{
		this.filterState = filterState;
	}
	
}
