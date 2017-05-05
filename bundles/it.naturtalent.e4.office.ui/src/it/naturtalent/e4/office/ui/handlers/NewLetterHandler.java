package it.naturtalent.e4.office.ui.handlers;

import it.naturtalent.e4.office.ui.actions.NewLetterAction;
import it.naturtalent.e4.project.ui.handlers.SelectedResourcesUtils;

import org.eclipse.core.resources.IProject;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;

public class NewLetterHandler  extends SelectedResourcesUtils
{
	@Execute
	public void execute(IEclipseContext context)
	{
		NewLetterAction action = ContextInjectionFactory.make(NewLetterAction.class, context);
		action.run();
	}
	
	@CanExecute
	public boolean canExecute(MPart part)
	{		
		IProject iProject = getSelectedProject(part);
		if(iProject != null && !iProject.isOpen())
			return false;
		
		return true;
	}
}