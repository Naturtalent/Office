package it.naturtalent.e4.office.ui.dialogs;

import java.util.List;

import it.naturtalent.e4.office.TextModule;
import it.naturtalent.e4.office.TextModuleTheme;

import org.eclipse.jface.viewers.LabelProvider;

public class TextmoduleTreeLabelProvider extends LabelProvider
{

	@Override
	public String getText(Object element)
	{
		if(element instanceof TextModuleTheme)
		{
			TextModuleTheme theme = (TextModuleTheme) element;
			return theme.getName();
		}
		
		if(element instanceof TextModule)
		{
			TextModule module = (TextModule) element;
			return module.getName();
		}

		
		return super.getText(element);
	}

}
