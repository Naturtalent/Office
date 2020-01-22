package it.naturtalent.e4.office.ui.actions;

import java.lang.reflect.InvocationTargetException;

import javax.inject.Inject;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Display;

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

	@Override
	public void run()
	{	
		KontakteImportDialog importDialog = ContextInjectionFactory
				.make(KontakteImportDialog.class, context);
		if(importDialog.open() == KontakteImportDialog.OK)
		{
			doImport(importDialog.getSelectedData());
		}
	}
	
	/* 
	 * Die selektierten Ordner werden importiert.
	 * 
	 * (non-Javadoc)
	 * @see it.naturtalent.e4.project.expimp.dialogs.AbstractImportDialog#doImport()
	 */
	public void doImport(final ExpImportData [] selectedData)
	{
		// Container zur Aufnahme der importierenden Kontakte
		final EList<Kontakt>kontakteList = OfficeUtils.getKontakte().getKontakte();
		
		// Importdaten ueber einen ProgressDialog einlesen
		ProgressMonitorDialog progressDialog = new ProgressMonitorDialog(Display.getDefault().getActiveShell());				
		progressDialog.open();
		try
		{
			progressDialog.run(true, true, new IRunnableWithProgress()
			{
				@Override
				public void run(IProgressMonitor monitor)throws InvocationTargetException,InterruptedException
				{
					monitor.beginTask("Kontaktdaten werden eingelesen",IProgressMonitor.UNKNOWN);
							
					for(ExpImportData expimpdata : selectedData)
					{
						Object obj = expimpdata.getData();
						if (obj instanceof Kontakt)
						{
							Kontakt kontakt = (Kontakt) obj;
							kontakteList.add(kontakt);
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
