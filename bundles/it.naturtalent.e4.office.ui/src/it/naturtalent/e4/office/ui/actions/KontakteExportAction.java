package it.naturtalent.e4.office.ui.actions;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.action.Action;
import org.eclipse.swt.widgets.Shell;

import it.naturtalent.e4.office.ui.dialogs.KontakteExportDialog;
import it.naturtalent.e4.project.expimp.ExpImportData;
import it.naturtalent.e4.project.ui.emf.ExpImpUtils;
import it.naturtalent.office.model.address.AddressPackage;
import it.naturtalent.office.model.address.Kontakt;
import it.naturtalent.office.model.address.Kontakte;

/**
 * Aktuelle Exportfunktion fuer Kontakte
 * 
 * @author dieter
 *
 */
public class KontakteExportAction extends Action
{
	@Inject
	@Optional
	private IEclipseContext context;
	
	private Shell shell;
	
	@PostConstruct
	private void postConstruct(@Named(IServiceConstants.ACTIVE_SHELL) @Optional Shell shell)
	{
		this.shell = shell;
	}


	@Override
	public void run()
	{
		KontakteExportDialog exportDialog = ContextInjectionFactory.make(KontakteExportDialog.class, context);
		if(exportDialog.open() == KontakteExportDialog.OK)
		{
			String exportPath = exportDialog.getExportPath();
			ExpImportData[] selectedData = exportDialog.getSelectedData();
			if(StringUtils.isNotEmpty(exportPath) && ArrayUtils.isNotEmpty(selectedData))
				doExport(exportPath, selectedData);
		}
		super.run();	
	}

	/*
	 * Die ausgewaehlten Einzelarchive werden in einem neuerstellten Container 'Archive' eingetragen und
	 * dieser wird dann in einer Datei gespeichert.
	 */
	public void doExport(String exportPath, ExpImportData[] selectedData)
	{
		// Container Archive erstellen
		EClass kontakteClass = AddressPackage.eINSTANCE.getKontakte();
		Kontakte kontakte = (Kontakte) EcoreUtil.create(kontakteClass);		
		
		// die selektierten Einzelkontakte im Container sammeln
		for(ExpImportData expImport : selectedData)
		{
			Object obj = expImport.getData();
			if (obj instanceof Kontakt)
			{				
				Kontakt kontakt = (Kontakt) obj;
				kontakte.getKontakte().add(EcoreUtil.copy(kontakt));
			}			
		}
		
		// Container mit den Kontakten in einer Datai speichern
		ExpImpUtils.saveEObjectToResource(kontakte, exportPath);	
	}
	
}
