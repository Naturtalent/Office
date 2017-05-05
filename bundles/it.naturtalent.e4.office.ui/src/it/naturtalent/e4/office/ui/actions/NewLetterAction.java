package it.naturtalent.e4.office.ui.actions;

import it.naturtalent.e4.office.ui.letter.wizard.NewLetterWizard;

import javax.inject.Inject;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;

public class NewLetterAction extends Action
{
	@Inject @Optional private Shell shell;
	@Inject @Optional IEclipseContext context;
	@Inject @Optional private ESelectionService selectionService;
	
	@Override
	public void run()
	{
		NewLetterWizard wizard = ContextInjectionFactory.make(NewLetterWizard.class, context);		
		WizardDialog dialog = new WizardDialog(shell, wizard);
		dialog.open();
	}
	
	@Override
	public boolean isHandled()
	{
		if(selectionService != null)
		{
			Object obj = selectionService.getSelection();
			if(obj instanceof IResource)
			{
				IResource iResource = (IResource) obj;
				
				if(iResource.getType() == IProject.PROJECT)
					if(!((IProject)iResource).isOpen())
						return false;
		
				return (iResource != null && (iResource.getType() & (IResource.FOLDER) | (IResource.PROJECT)) != 0);
			}			
			return false;	
		}
		
		return super.isHandled();		
	}
}