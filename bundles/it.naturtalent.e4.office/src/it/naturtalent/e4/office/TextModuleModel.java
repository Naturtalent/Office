package it.naturtalent.e4.office;

import java.beans.PropertyChangeEvent;
import java.io.ObjectInputStream.GetField;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * Im Modell werden alle Textmodule zusammengefasst.
 * 
 * @author dieter
 *
 */
public class TextModuleModel extends BaseBean implements ITextModuleModel
{
	// Properties	
	public static final String TEXTMODULE_THEMES = "themes"; //$NON-NLS-N$

	private List<TextModuleTheme>textThemes = new ArrayList<TextModuleTheme>();

	public List<TextModuleTheme> getTextThemes()
	{
		return textThemes;
	}

	public void setTextThemes(List<TextModuleTheme> textThemes)
	{
		firePropertyChange(new PropertyChangeEvent(this, TEXTMODULE_THEMES, this.textThemes,
				this.textThemes = textThemes));
	}
	
	public static TextModuleTheme findModuleParent(TextModuleModel model, TextModule textModule)
	{		
		for(TextModuleTheme textThema : model.getTextThemes())
		{
			List<TextModule>textModules = textThema.getModules();
			if(textModules.contains(textModule))
				return textThema;
		}

		return null;
	}
	
	public static int indexOfTheme(TextModuleModel model, TextModuleTheme theme)
	{
		List<TextModuleTheme>existThemes = model.getTextThemes();
		for(TextModuleTheme existTheme : existThemes)
		{
			if(StringUtils.equals(existTheme.getName(), theme.getName()))
				return existThemes.indexOf(existTheme);
		}
		
		return (-1);
	}
	
}
