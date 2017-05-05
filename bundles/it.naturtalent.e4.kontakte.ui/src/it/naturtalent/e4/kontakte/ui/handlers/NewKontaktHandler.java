package it.naturtalent.e4.kontakte.ui.handlers;
 


import it.naturtalent.e4.kontakte.ui.actions.NewContactWizardAction;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Execute;


public class NewKontaktHandler
{
	@Execute
	public void execute(IEclipseContext context)
	{
		NewContactWizardAction action = ContextInjectionFactory.make(NewContactWizardAction.class, context);
		action.run();
	}
}