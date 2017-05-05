package it.naturtalent.e4.office.ui.dialogs;

import java.util.List;

import it.naturtalent.e4.office.TextModuleModel;
import it.naturtalent.e4.office.TextModuleTheme;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class TextmoduleContentProvider implements IStructuredContentProvider
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

}
