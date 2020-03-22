package it.naturtalent.e4.office.ui.actions;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.internal.progress.ProgressMonitorJobsDialog;

import it.naturtalent.e4.office.ui.OfficeUtils;
import it.naturtalent.e4.office.ui.dialogs.KontakteImportDialog;
import it.naturtalent.e4.project.expimp.ExpImportData;
import it.naturtalent.office.model.address.AddressPackage;
import it.naturtalent.office.model.address.Kontakt;
import it.naturtalent.office.model.address.Kontakte;


public class KontakteImportAction extends Action
{

	@Inject
	@Optional
	private IEclipseContext context;

	@Inject
	@Optional
	private IEventBroker eventBroker;

	@Override
	public void run()
	{	
		final KontakteImportDialog importDialog = ContextInjectionFactory
				.make(KontakteImportDialog.class, context);
		if(importDialog.open() == KontakteImportDialog.OK)
		{			
			doImport(importDialog.getSelectedData());
			OfficeUtils.getOfficeProject().saveContents();
			
			if(eventBroker != null)
				eventBroker.post(OfficeUtils.KONTAKTE_REFRESH_MASTER_REQUEST, null);
		}
	}
	
	private void doImport(final ExpImportData [] selectedData)
	{		
		final EObject container = OfficeUtils.findObject(AddressPackage.eINSTANCE.getKontakte());
		final EditingDomain domain = AdapterFactoryEditingDomain.getEditingDomainFor(container);	
		final EReference eReference = AddressPackage.eINSTANCE.getKontakte_Kontakte();
		
		ProgressMonitorDialog progressDialog = new ProgressMonitorDialog(Display.getDefault().getActiveShell());
		progressDialog.open();
		try
		{
			progressDialog.run(true, true, new IRunnableWithProgress()
			{
				@Override
				public void run(IProgressMonitor monitor)throws InvocationTargetException,InterruptedException
				{					
					Kontakte kontakte = (Kontakte) OfficeUtils.findObject(AddressPackage.eINSTANCE.getKontakte()); 
					EList<Kontakt>kontakteList = kontakte.getKontakte();
					
					monitor.beginTask("Kontaktdaten werden eingelesen",selectedData.length);							
					for(ExpImportData expimpdata : selectedData)
					{
						Object obj = expimpdata.getData();
						if (obj instanceof Kontakt)
						{
							Kontakt kontakt = (Kontakt) obj;
							Command addCommand = AddCommand.create(domain, container , eReference, kontakt);
							if(addCommand.canExecute())	
								domain.getCommandStack().execute(addCommand);
							monitor.worked(1);
						}
					}
					
					monitor.done();
				}
			});
		}
		catch(Exception e)
		{
			
		}

		progressDialog.close();			
	}
	
}
