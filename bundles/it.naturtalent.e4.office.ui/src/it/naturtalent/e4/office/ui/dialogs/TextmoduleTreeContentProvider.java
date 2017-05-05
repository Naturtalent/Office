package it.naturtalent.e4.office.ui.dialogs;

import java.util.ArrayList;
import java.util.List;

import it.naturtalent.e4.office.TextModule;
import it.naturtalent.e4.office.TextModuleModel;
import it.naturtalent.e4.office.TextModuleTheme;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class TextmoduleTreeContentProvider implements ITreeContentProvider
{

	private TextModuleModel model;
	
	@Override
	public void dispose()
	{		
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput)
	{
		model = null;
		if(newInput instanceof TextModuleModel)
			model = (TextModuleModel) newInput;
	}

	@Override
	public Object[] getElements(Object inputElement)
	{
		if(model != null)
		{
			List<TextModuleTheme>themes = model.getTextThemes();
			if(themes != null)				
				return themes.toArray(new TextModuleTheme[themes.size()]);
		}
			
		return null;
	}

	@Override
	public Object[] getChildren(Object parentElement)
	{
		if(parentElement instanceof TextModuleTheme)
		{		
			List<TextModule>modules = ((TextModuleTheme) parentElement).getModules();
			return modules.toArray(new TextModule[modules.size()]);
		}
		
		return null;
	}

	@Override
	public Object getParent(Object element)
	{
		return null;		
	}

	@Override
	public boolean hasChildren(Object element)
	{		
		return (getChildren(element) != null);
	}

}
