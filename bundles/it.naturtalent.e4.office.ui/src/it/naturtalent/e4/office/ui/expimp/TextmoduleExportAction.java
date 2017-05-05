package it.naturtalent.e4.office.ui.expimp;

import java.io.File;
import java.io.IOException;
import java.util.List;

import it.naturtalent.e4.office.INtOffice;
import it.naturtalent.e4.office.IOfficeService;
import it.naturtalent.e4.office.ITextModuleDataFactory;
import it.naturtalent.e4.office.ITextModuleModel;
import it.naturtalent.e4.office.OpenDocumentUtils;
import it.naturtalent.e4.office.TextModule;
import it.naturtalent.e4.office.TextModuleModel;
import it.naturtalent.e4.office.TextModuleTheme;
import it.naturtalent.e4.office.ui.dialogs.TextmoduleExportDialog;
import it.naturtalent.e4.office.ui.dialogs.TextmoduleImportDialog;






import javax.inject.Inject;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.jface.action.Action;

/**
 * Importquelle (XML - Datei) auswaehlen, die selektierten Module in das
 * Textbausteinmodell uebernehmen (vorhandene Themen werden ueberschrieben)
 * und ueber Eventbroker informieren.
 * 
 * @author apel.dieter
 *
 */
public class TextmoduleExportAction extends Action
{

	@Inject @Optional IEclipseContext context;
	@Inject @Optional IOfficeService officeService;
	@Inject @Optional IEventBroker broker;
	
	@Override
	public void run()
	{			
		ITextModuleModel model = officeService
				.getTextModuleModel(IOfficeService.NTOFFICE_CONTEXT);
		
		TextmoduleExportDialog dialog = ContextInjectionFactory.make(TextmoduleExportDialog.class, context);
		dialog.create();
		dialog.init(model);
		
		if(dialog.open() == TextmoduleExportDialog.OK)
		{	
			TextModuleModel expModel = new TextModuleModel();
			expModel.setTextThemes(dialog.getCheckedThemes());			
			String exportPath = dialog.getSelectedExportPath();
			
			/*
			File exportFile = new File(exportPath);
			if(!exportFile.exists())
			{
				try
				{
					exportFile.createNewFile();
				} catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			*/
						
			OpenDocumentUtils.writeTextModules(new File(exportPath), (TextModuleModel) expModel);
		}
	}

}
