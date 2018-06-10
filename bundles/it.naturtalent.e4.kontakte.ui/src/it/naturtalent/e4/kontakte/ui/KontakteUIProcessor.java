package it.naturtalent.e4.kontakte.ui;

import it.naturtalent.application.IShowViewAdapterRepository;
import it.naturtalent.e4.kontakte.IKontakteDataFactory;
import it.naturtalent.e4.kontakte.ui.expimp.ExportKontakteAdapter;
import it.naturtalent.e4.kontakte.ui.expimp.ImportKontakteAdapter;
import it.naturtalent.e4.preferences.IPreferenceRegistry;
import it.naturtalent.e4.project.IExportAdapterRepository;
import it.naturtalent.e4.project.IImportAdapterRepository;
import it.naturtalent.e4.project.INewActionAdapterRepository;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;

public class KontakteUIProcessor
{
	@Inject @Optional IImportAdapterRepository importAdapterRepository;
	@Inject @Optional IExportAdapterRepository exportAdapterRepository;
	@Optional @Inject INewActionAdapterRepository newWizardRepository;	
	@Inject @Optional IShowViewAdapterRepository showViewAdapterRepository;
	@Inject @Optional IPreferenceRegistry preferenceRegistry;
	
	static @Inject @Optional private IKontakteDataFactory kontakteDataFactory;
	
	
	@Execute
	void init (MApplication app, EModelService modelService, EPartService partService)
	{
		/*
		if(showViewAdapterRepository != null)
			showViewAdapterRepository.addShowViewAdapter(new KontakteShowViewAdapter());
			*/
			
		/*
		if(newWizardRepository != null)
			newWizardRepository.addNewActionAdapter(new NewKontaktWizardAdapter());
		
		if(exportAdapterRepository != null)
			exportAdapterRepository.addExportAdapter(new ExportKontakteAdapter()); 

		if(importAdapterRepository != null)
			importAdapterRepository.addImportAdapter(new ImportKontakteAdapter());
		
		if(preferenceRegistry != null)
			preferenceRegistry.getPreferenceAdapters().add(new KontaktePrefereceAdapter());
		
		if(showViewAdapterRepository != null)
			showViewAdapterRepository.addShowViewAdapter(new ShowContactsViewAdapter());
			*/
		
		/*
		MPartStack myPartStack = (MPartStack)modelService.find("it.naturtalent.e4.project.ui.partstack.1", app);
		MPart mPart = partService.createPart("it.naturtalent.e4.kontakte.ui.partdescriptor.0");		
		myPartStack.getChildren().add(mPart);
		*/
		
	}


	public static IKontakteDataFactory getKontakteDataFactory()
	{
		return kontakteDataFactory;
	}
	
	
		
}
