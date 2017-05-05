package it.naturtalent.e4.office.ui.handlers;

import it.naturtalent.e4.office.odf.ODFDrawDocumentHandler;

import java.io.File;

import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.swt.widgets.Shell;

public class NewDrawOfficeHandler extends CalcOfficeHandler
{
	
	public static final String DRAWOFFICE_TEMPLATE = "draw.odg"; //$NON-NLS-1$
	protected String template = DRAWOFFICE_TEMPLATE;
	
	@Override
	public void execute(ESelectionService selectionService, Shell shell)
	{
		templateName = DRAWOFFICE_TEMPLATE;
		super.execute(selectionService, shell);
	}
	@Override
	protected void showDocument(File templateFile)
	{
		ODFDrawDocumentHandler documentHandler = new ODFDrawDocumentHandler();
		documentHandler.openOfficeDocument(templateFile);
		documentHandler.closeOfficeDocument();
		documentHandler.showOfficeDocument();		

	}
	

	
	

}