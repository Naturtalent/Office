package it.naturtalent.e4.office.ui.handlers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import it.naturtalent.e4.office.INtOffice;
import it.naturtalent.e4.office.IOfficeService;
import it.naturtalent.e4.office.OpenDocumentUtils;
import it.naturtalent.e4.office.odf.ODFOfficeDocumentHandler;
import it.naturtalent.e4.office.odf.ODFSpreadsheetDocumentHandler;
import it.naturtalent.e4.project.ui.utils.CreateNewFile;

import org.apache.commons.io.FilenameUtils;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.swt.widgets.Shell;

public class CalcOfficeHandler 
{
	public static final String CALCOFFICE_TEMPLATE = "calc.ods"; //$NON-NLS-1$

	protected String templateName = CALCOFFICE_TEMPLATE;
	
	@Execute
	public void execute(ESelectionService selectionService, Shell shell)
	{
		File templateFile = null;
		IContainer container;
		String dir = IOfficeService.OFFICEDATADIR+File.separator+IOfficeService.NTOFFICE_CONTEXT;
		
		Object selObj = selectionService.getSelection();
		if(selObj instanceof IResource)
		{
			IResource resource = (IResource) selObj;
			if((resource.getType() & (IResource.PROJECT | IResource.FOLDER)) != 0)			
				container = (IContainer) selObj;
			else
				container = resource.getParent();

			try
			{
				templateFile = OpenDocumentUtils.getWorkspaceTemplate(dir, templateName);
				String newName = templateFile.getName();
				newName = getAutoFileName(container, templateFile.getName());
				InputStream content = new FileInputStream(templateFile);
				CreateNewFile.createFile(shell, container, newName, content, null);
								
				IPath path = container.getLocation();
				path = path.append(newName);
				templateFile = path.toFile();
				
			} catch (FileNotFoundException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			templateFile = OpenDocumentUtils.getTemporaryTemplate(dir, templateName);
		}
		
		showDocument(templateFile);
	}
	
	protected void showDocument(File templateFile)
	{
		ODFSpreadsheetDocumentHandler documentHandler = new ODFSpreadsheetDocumentHandler();
		documentHandler.openOfficeDocument(templateFile);
		documentHandler.closeOfficeDocument();
		documentHandler.showOfficeDocument();		
	}
	
	private String getAutoFileName(IContainer dir, String originalFileName)
	{
		String autoFileName;

		if (dir == null)
			return "";

		int counter = 1;
		while (true)
		{
			if (counter > 1)
			{
				autoFileName = FilenameUtils.getBaseName(originalFileName)
						+ new Integer(counter) + "."
						+ FilenameUtils.getExtension(originalFileName);
			}
			else
			{
				autoFileName = originalFileName;
			}

			IResource res = dir.findMember(autoFileName);
			if (res == null)
				return autoFileName;

			counter++;
		}
	}
	
}
