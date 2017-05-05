package it.naturtalent.e4.office;

import java.util.List;

/**
 * Model der Textbausteine
 * 
 * @author apel.dieter
 *
 */
public interface ITextModuleModel
{
	
	public static final String ADD_TEXTMODULE_EVENT = "addtextmoduleevent";
	public static final String UPDATE_TEXTMODULE_EVENT = "updatetextmoduleevent";
	public static final String DELETE_TEXTMODULE_EVENT = "deltextmoduleevent";
	
	public List<TextModuleTheme> getTextThemes();
	public void setTextThemes(List<TextModuleTheme> textThemes);
}
