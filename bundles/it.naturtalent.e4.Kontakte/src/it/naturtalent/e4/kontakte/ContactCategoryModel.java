package it.naturtalent.e4.kontakte;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ContactCategoryModel implements IContactCategoryModel
{

	private static String CONTACT_CATEGORY_COLLECTION_NAME = "ContactCategoryCollection";
	
	private String kontakteCollectionName;
	
	private List<KontakteCategory>contactCategories = new ArrayList<KontakteCategory>();
	
	// IDs der zuloeschenden Kategorien
	private List<String>deletedCategories = new ArrayList<String>();
	
	private Log log = LogFactory.getLog(this.getClass());
	
	private boolean modified = false;
	
	public ContactCategoryModel(String kontakteCollectionName)
	{
		super();
		this.kontakteCollectionName = kontakteCollectionName;
	}


	@Override
	public List<KontakteCategory> getContactCategories()
	{
		return contactCategories;
	}


	@Override
	public List<KontakteCategory> getContactCategories(String kontaktID)
	{
		List<KontakteCategory>categories = new ArrayList<KontakteCategory>();
		for(KontakteCategory category : contactCategories)
		{
			String[] idsArray = category.getKontakte();
			if (!ArrayUtils.isEmpty(idsArray))
			{
				List<String> ids = Arrays.asList(idsArray);
				if (ids.contains(kontaktID))
					categories.add(category);
			}
		}
		return categories;
	}


	@Override
	public void setContactCategories(String[] names, String kontaktID)
	{
		// 'kontaktID' aus allen Kategorien entfernen		
		removeKontakt(kontaktID);
					
		// 'kontaktID' in Kategorien 'names' eintragen 
		for(String name : names)
		{
			KontakteCategory category = getContactCategory(name);
			if(category == null)
			{
				// Kategorie neu erzeugen
				category = new KontakteCategory();
				category.setName(name);			
				contactCategories.add(category);
			}
			
			String [] ids = category.getKontakte();
			ids = (String[]) ArrayUtils.add(ids, kontaktID);
			category.setKontakte(ids);
			
			modified = true;
		}
	}

	public void updateContactCategory(KontakteCategory kontakteCategory)
	{
		
	}

	@Override
	public void loadContactCategories()
	{
		contactCategories.clear();
		deletedCategories.clear();
		modified = false;
		
		/*
		if(StringUtils.isNotEmpty(kontakteCollectionName))
		{
			Collection kontakteDataCollection = XMLDBConnectionFactory
					.getCollection(kontakteCollectionName);
			try
			{
				Collection categorieCollection = kontakteDataCollection.getChildCollection(CONTACT_CATEGORY_COLLECTION_NAME);
				
				if(categorieCollection == null)
					categorieCollection = XMLDBConnectionFactory.getChildCollection(kontakteDataCollection, CONTACT_CATEGORY_COLLECTION_NAME);

				String [] kategorieIDs = categorieCollection.listResources();
				for(String id : kategorieIDs)
				{
					Resource resource = categorieCollection.getResource(id);
					if (resource != null)
					{
						InputStream is = IOUtils.toInputStream(
								(String) resource.getContent(), "UTF-8");
						
						KontakteCategory contactCategory = JAXB.unmarshal(is,KontakteCategory.class);
						contactCategories.add(contactCategory);
					}
				}					
				
			} catch (Exception e)
			{
				log.error(e);
			}			
		}
		*/
	}

	@Override
	public void saveContactCategories()
	{
		/*
		// die zum Loeschen vorgemerkte Kategorien loeschen		
		deleteContactCategory(deletedCategories);

		Collection kontakteDataCollection = XMLDBConnectionFactory
				.getCollection(kontakteCollectionName);
		try
		{
			Collection categorieCollection = kontakteDataCollection
					.getChildCollection(CONTACT_CATEGORY_COLLECTION_NAME);
			
			if(categorieCollection == null)
				categorieCollection = XMLDBConnectionFactory.getChildCollection(kontakteDataCollection, CONTACT_CATEGORY_COLLECTION_NAME);
						
			// alle Kategorien des Modells neu speichern			
			for(KontakteCategory category : contactCategories)
			{
				String catagoryID = category.getId();
				
				Resource resource = categorieCollection.getResource(catagoryID);
				if(resource == null)				
					resource = categorieCollection.createResource(catagoryID,XMLResource.RESOURCE_TYPE);
				
				if (resource != null)
				{
					ByteArrayOutputStream out = new ByteArrayOutputStream();										
					JAXB.marshal(category, out);
					resource.setContent(StringUtils.toString(out.toByteArray(),"UTF-8"));
					categorieCollection.storeResource(resource);
				}
			}
			
			modified = false;
			
		} catch (Exception e)
		{
			log.error(e);
		}
		*/
	}
	
	@Override
	public void deleteContactCategory(List<String>categoryIDs)
	{
		/*
		try
		{
			Collection kontakteDataCollection = XMLDBConnectionFactory
					.getCollection(kontakteCollectionName);
			
			if (kontakteDataCollection != null)
			{
				Collection categorieCollection = kontakteDataCollection
						.getChildCollection(CONTACT_CATEGORY_COLLECTION_NAME);

				if (categorieCollection != null)
				{
					categorieCollection = XMLDBConnectionFactory
							.getChildCollection(kontakteDataCollection,
									CONTACT_CATEGORY_COLLECTION_NAME);

					for (String categoryID : categoryIDs)
					{
						Resource resource = categorieCollection
								.getResource(categoryID);
						if (resource != null)
							categorieCollection.removeResource(resource);
					}
				}
			}
			
		} catch (Exception e)
		{
			log.error(e);
		}
		*/
	}

	@Override
	public List<String> getContactCategoryNames()
	{
		List<String>names = new ArrayList<String>();
		for(KontakteCategory category : contactCategories)
			names.add(category.getName());
		return names;
	}


	@Override
	public List<String> getContactCategoryNames(String kontaktID)
	{
		List<String>names = new ArrayList<String>();
		List<KontakteCategory>categories = getContactCategories(kontaktID);
		for(KontakteCategory catagory : categories)
			names.add(catagory.getName());
		
		return names;
	}


	private KontakteCategory getContactCategory(String name)
	{
		for(KontakteCategory category : contactCategories)
		{
			if(StringUtils.equals(category.getName(), name))
				return category;			
		}
		
		return null;
	}

	// bestehende Kontaktzuordnungen entfernen
	public void removeKontakt(String kontaktID)
	{		
		// die zuegordneten Kategorien listen
		List<KontakteCategory>existCategories = getContactCategories(kontaktID);
		for(KontakteCategory existCategory : existCategories)
		{
			String [] ids = existCategory.getKontakte();
			ids = (String[]) ArrayUtils.removeElement(ids, kontaktID);
			
			if(ArrayUtils.isEmpty(ids))
			{
				// leere Kategorie aus dem Modell entfernen
				contactCategories.remove(existCategory);
				
				// zum Loeschen vormerken
				deletedCategories.add(existCategory.getId());
			}
			else
			{
				// aktualisierte (Kontakt 'kontaktID' wurde entfernt) Kategorie zurueckspeichern
				existCategory.setKontakte(ids);
			}
			
			modified = true;
		}
	}
	
	public void removeInvalides()
	{
		/*
		if(StringUtils.isNotEmpty(kontakteCollectionName))
		{
			Collection kontakteDataCollection = XMLDBConnectionFactory
					.getCollection(kontakteCollectionName);
			try
			{
				Collection categorieCollection = kontakteDataCollection.getChildCollection(CONTACT_CATEGORY_COLLECTION_NAME);
				
				if(categorieCollection == null)
					return;

				String [] kategorieNames = categorieCollection.listResources();
				for(String name : kategorieNames)
				{
					Resource resource = categorieCollection.getResource(name);
					if (resource != null)
					{
						InputStream is = IOUtils.toInputStream(
								(String) resource.getContent(), "UTF-8");
						
						KontakteCategory contactCategory = JAXB.unmarshal(is,KontakteCategory.class);
						String [] ids = contactCategory.getKontakte();
						String catName= contactCategory.getName();
						
						if(ArrayUtils.isEmpty(ids) || StringUtils.isEmpty(catName))
							categorieCollection.removeResource(resource);
					}
				}					
				
			} catch (Exception e)
			{
				log.error(e);
			}			
		}
		*/		
	}


	@Override
	public boolean isModified()
	{		
		return modified;
	}


}
