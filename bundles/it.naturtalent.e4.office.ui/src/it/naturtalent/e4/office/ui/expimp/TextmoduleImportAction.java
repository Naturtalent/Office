package it.naturtalent.e4.office.ui.expimp;

import java.util.List;

import it.naturtalent.e4.office.INtOffice;
import it.naturtalent.e4.office.IOfficeService;
import it.naturtalent.e4.office.ITextModuleDataFactory;
import it.naturtalent.e4.office.ITextModuleModel;
import it.naturtalent.e4.office.TextModule;
import it.naturtalent.e4.office.TextModuleModel;
import it.naturtalent.e4.office.TextModuleTheme;
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
public class TextmoduleImportAction extends Action
{

	@Inject @Optional IEclipseContext context;
	@Inject @Optional IOfficeService officeService;
	@Inject @Optional IEventBroker broker;
	
	@Override
	public void run()
	{			
		ITextModuleModel model = officeService
				.getTextModuleModel(IOfficeService.NTOFFICE_CONTEXT);
		
		TextmoduleImportDialog dialog = ContextInjectionFactory.make(TextmoduleImportDialog.class, context);
		dialog.create();
		dialog.init(model);
		
		if(dialog.open() == TextmoduleImportDialog.OK)
		{		
			List<TextModuleTheme>modelThemes = model.getTextThemes();
			
			// die zuimportierenden Themen werden hinzugfuegt
			List<TextModuleTheme> addedThemes = dialog.getAddedThemes();					
			for(TextModuleTheme theme : addedThemes)
			{					
				modelThemes.add(theme);					
				broker.post(ITextModuleModel.ADD_TEXTMODULE_EVENT, theme);
			}

			// namesgleiche Themen aktualisieren		
			List<TextModuleTheme> updatedThemes = dialog.getUpdatedThemes();					
			for(TextModuleTheme updatedTheme : updatedThemes)
			{								
				int idx = TextModuleModel.indexOfTheme(
						(TextModuleModel) model, updatedTheme);
				if (idx >= 0)
				{	
					// die Textbausteine werden ersetzt
					TextModuleTheme existTheme = modelThemes.get(idx);
					existTheme.setModules(updatedTheme.getModules());
					broker.post(ITextModuleModel.UPDATE_TEXTMODULE_EVENT,existTheme);
				}
			}

			officeService.saveTextModuleModel(IOfficeService.NTOFFICE_CONTEXT);

		}
		
		/*
		TextmoduleImportDialogOLD dialog = ContextInjectionFactory.make(TextmoduleImportDialogOLD.class, context);
		if(dialog.open() == TextmoduleImportDialogOLD.OK)
		{						
			if(textModuleFactory != null)	
			{
				ITextModuleModel model = textModuleFactory
						.getTextModuleModel(INtOffice.NTOFFICE_CONTEXT);

				List<TextModuleTheme>modelThemes = model.getTextThemes();
				
				// die zuimportierenden Themen werden hinzugfuegt
				List<TextModuleTheme> addedThemes = dialog.getAddedThemes();					
				for(TextModuleTheme theme : addedThemes)
				{					
					modelThemes.add(theme);					
					broker.post(ITextModuleModel.ADD_TEXTMODULE_EVENT, theme);
				}

				// namesgleiche Themen aktualisieren		
				List<TextModuleTheme> updatedThemes = dialog.getUpdatedThemes();					
				for(TextModuleTheme updatedTheme : updatedThemes)
				{								
					int idx = TextModuleModel.indexOfTheme(
							(TextModuleModel) model, updatedTheme);
					if (idx >= 0)
					{	
						// die Textbausteine werden ersetzt
						TextModuleTheme existTheme = modelThemes.get(idx);
						existTheme.setModules(updatedTheme.getModules());
						broker.post(ITextModuleModel.UPDATE_TEXTMODULE_EVENT,existTheme);
					}
				}

				textModuleFactory.saveTextModuleModel(INtOffice.NTOFFICE_CONTEXT);
			}
		}
		*/
	}

}
