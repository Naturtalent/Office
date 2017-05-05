package it.naturtalent.e4.kontakte;

import java.util.List;

public interface IContactCategoryModel
{
	/**
	 * Rueckgabe aller Kontaktekategorien die in der KontakteCollection 'kontakteCollectionName' definiert sind.
	 * 
	 * @param categoryName
	 * @return
	 */
	public List<KontakteCategory> getContactCategories();
		
	/**
	 * Rueckgabe aller Kategorien, denen der Kontakt 'kontaktID' zugeordnet ist.
	 * 
	 * @param kontaktID
	 * @return
	 */
	public List<KontakteCategory>getContactCategories(String kontaktID);

	/**
	 * Der Kontakt 'kontaktID' wird den Kategorien 'names' zugeordnet. Alle eventuell bestehende
	 * Zuordnungen werden geloest.
	 * 
	 * @param kontaktID
	 * @return
	 */
	public void setContactCategories(String [] names, String kontaktID);

	public List<String> getContactCategoryNames();
	
	public List<String> getContactCategoryNames(String kontaktID);
	
	public boolean isModified();
	
	public void loadContactCategories();
	
	public void saveContactCategories();
	
	public void deleteContactCategory(List<String>categoriesIDs);

}
