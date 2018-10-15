package it.naturtalent.e4.office.ui.handlers;

import it.naturtalent.e4.office.ui.letter.wizard.OpenLetterWizard;

import javax.annotation.PostConstruct;

import org.eclipse.core.resources.IResource;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;

/**
 * @author dieter
 * 
 * Handler oeffnet eine Datei mit einem Wizard.
 *
 */
@Deprecated
public class OpenOfficeHandler
{
	//@Inject @Optional IEclipseContext context;
	
	//@Inject @Optional INtOfficeFactory ntOfficeFactory;
	
	@Execute
	public void execute(IEclipseContext context, Shell shell, ESelectionService selectionService)
	{		
		Object selObj = selectionService.getSelection();
		if(selObj instanceof IResource)
		{
			IResource resource = (IResource) selObj;
			if(resource.getType() == resource.FILE)
			{	
				OpenLetterWizard wizard = new OpenLetterWizard();
				ContextInjectionFactory.invoke(wizard, PostConstruct.class, context);
				WizardDialog dialog = new WizardDialog(shell, wizard);
				dialog.open();
			}
		}
	}

	
	@CanExecute
	public boolean canExecute(ESelectionService selectionService)
	{		
		Object selObj = selectionService.getSelection();
		if(selObj instanceof IResource)
		{
			IResource resource = (IResource) selObj;
			return (resource.getType() == IResource.FILE);
		}
		return false;
	}
	

}