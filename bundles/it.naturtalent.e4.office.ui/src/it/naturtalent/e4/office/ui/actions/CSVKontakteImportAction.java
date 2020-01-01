package it.naturtalent.e4.office.ui.actions;

import javax.inject.Inject;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.jface.action.Action;

import it.naturtalent.e4.office.ui.dialogs.CSVKontakteImportDialog;
import it.naturtalent.e4.office.ui.dialogs.KontakteImportDialog;


/**
 * Import CSV Kontakte 
 * Flothari
 * 
 * @author dieter
 *
 */
public class CSVKontakteImportAction extends Action
{
	@Inject
	@Optional
	private IEclipseContext context;

	@Override
	public void run()
	{	
		CSVKontakteImportDialog importDialog = ContextInjectionFactory
				.make(CSVKontakteImportDialog.class, context);
		importDialog.open();		
	}
}
