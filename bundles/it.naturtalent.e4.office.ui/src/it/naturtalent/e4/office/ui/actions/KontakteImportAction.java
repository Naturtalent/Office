package it.naturtalent.e4.office.ui.actions;

import javax.inject.Inject;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.jface.action.Action;

import it.naturtalent.e4.office.ui.dialogs.KontakteImportDialog;


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
		importDialog.open();		
	}
}
