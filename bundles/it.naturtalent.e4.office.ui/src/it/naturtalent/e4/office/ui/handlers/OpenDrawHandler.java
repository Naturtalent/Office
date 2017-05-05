package it.naturtalent.e4.office.ui.handlers;


import it.naturtalent.e4.office.IOfficeService;

import java.io.File;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.swt.widgets.Shell;

public class OpenDrawHandler
{
	@Inject @Optional IOfficeService officeService;
	
	public static final String ODFDRAWOFFICE_EXTENSION = "odg"; //$NON-NLS-1$
	
	
	@Execute
	public void execute(ESelectionService selectionService, Shell shell)
	{
		Object selObj = selectionService.getSelection();
		if(selObj instanceof IResource)
		{
			IResource resource = (IResource) selObj;
			if(resource.getType() == IResource.FILE)
			{
				File drawFile = ((IFile) resource).getLocation().toFile();
				NewDrawOfficeHandler drawOfficeHandler = new NewDrawOfficeHandler();
				drawOfficeHandler.showDocument(drawFile);
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
			if(resource.getType() == IResource.FILE)
			{
				String extension = ((IFile) resource).getLocation().getFileExtension();
				return StringUtils.equals(extension, ODFDRAWOFFICE_EXTENSION);				
			}
		}
		
		return false;
	}
	
}