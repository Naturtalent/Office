package it.naturtalent.e4.office;

/**
 * @author apel.dieter
 *
 */
@Deprecated
public interface ITextModuleDataFactory
{
	public ITextModuleModel getTextModuleModel(String officeContext);
	public void saveTextModuleModel(String officeContext);
}
