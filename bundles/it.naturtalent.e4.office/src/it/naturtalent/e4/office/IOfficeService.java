package it.naturtalent.e4.office;

import it.naturtalent.e4.office.letter.AbstractOfficeLetterAdapter;
import it.naturtalent.e4.office.letter.IOfficeLetterAdapter;
import it.naturtalent.e4.office.spreadsheet.AbstractSpreadSheetAdapter;
import it.naturtalent.e4.office.spreadsheet.ISpreadSheetAdapter;

import java.util.List;

@Deprecated
public interface IOfficeService
{
	public static final String OFFICEDATADIR = "office";
	public static final String NTOFFICE_CONTEXT = "xntw";

	public ISpreadSheetAdapter getAdapter(String adapterClassName);
	public void registerSpreadSheetAdapterClass(String adapterClassName,Class<? extends AbstractSpreadSheetAdapter>adapter);
	public Class<? extends AbstractSpreadSheetAdapter> getSheetAdapterClass(String adapterClassName);
		
	/**
	 * Eine LetterAdapterklasse registrieren.
	 * 
	 * @param adapterClassName
	 * @param adapter
	 */
	public void registerLetterAdapterClass(String adapterName,Class<? extends AbstractOfficeLetterAdapter>adapter);		
	
	/**
	 * Registriert ein Template mit dem angegebenen Pfad. Entsprechend der
	 * Dateierweiterung wird intern ein entsprechender DefaultAdapter zugeordnet.
	 * 
	 * @param templatePath
	 */
	public void registerLetterTemplate(String templatePath);
	
	
	/**
	 * Registriert ein Template mit dem angegebenen Pfad. Dem Template wird
	 * der Adapter 'adapterClassName' zugeordnet. Der Adapter muss unter dem
	 * angegebenen Namen ebenfalls registriert sein.
	 * 
	 * @param adapterName
	 * @param adapterClassName
	 */
	public void registerLetterTemplate(String templatePath, String adapterClassName);
	
	
	/**
	 * Rueckgabe einer dem Template zugeordneten Adapterinstanz. 
	 * @param templatePath
	 * @return
	 */
	public IOfficeLetterAdapter getLetterTemplateAdapter(String templatePath);
	
	
	/**
	 * Rueckgabe aller registrierten Templates eines Office Kontextes.
	 * Zurueckgegeben werden die Pfade.
	 * 
	 * officeContextFilter null; 
	 * Rueckgabe aller registrierten Templates 
	 * 
	 * @param officeContextFilter
	 * @return
	 */
	public List<String> getLetterTemplates(String officeContextFilter);

	
	/**
	 * Ermittelt zu einem Dokument ein brauchbares Template.
	 *  
	 * @param checkPath
	 * @param officeContextFilter
	 * @return
	 */
	public String getPracticalTemplate(String documentPath, String officeContextFilter);

	// Textbausteine
	public ITextModuleModel getTextModuleModel(String officeContext);
	public void saveTextModuleModel(String officeContext);
}
