package it.naturtalent.e4.office.ui.actions;

import it.naturtalent.e4.office.IOfficeService;
import it.naturtalent.e4.office.ITextModuleDataFactory;
import it.naturtalent.e4.office.ITextModuleModel;
import it.naturtalent.e4.office.TextModule;
import it.naturtalent.e4.office.TextModuleModel;
import it.naturtalent.e4.office.TextModuleTheme;
import it.naturtalent.e4.office.ui.Messages;

import javax.annotation.PostConstruct;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Display;

public class DeleteTextModuleAction extends Action
{		
	private TreeViewer viewer;
	private String officeContext;
	
	private IEventBroker broker;
	private IOfficeService officeService;	
	private ITextModuleModel model;

	
	public DeleteTextModuleAction(String officeContext, TreeViewer viewer)
	{
		super();
		this.officeContext = officeContext;
		this.viewer = viewer;
	}
	
	@PostConstruct
	protected void postConstruct (@Optional IEventBroker broker, 
			@Optional IOfficeService officeService)
	{		
		this.broker = broker;
		this.officeService = officeService;
		if(officeService != null)
			model = officeService.getTextModuleModel(officeContext);
	}


	@Override
	public void run()
	{
		if ((model != null) && (viewer != null))
		{
			IStructuredSelection selection = (IStructuredSelection) viewer.getSelection();
			if ((selection != null) && (!selection.isEmpty()))
			{
				Object obj = selection.getFirstElement();
				if (obj instanceof TextModuleTheme)
				{
					TextModuleTheme theme = (TextModuleTheme) obj;
					if (MessageDialog
							.openQuestion(
									Display.getDefault().getActiveShell(),
									Messages.DeleteTextModuleAction_DeleteDialogTitle,
									Messages.bind(
											Messages.DeleteTextModuleAction_DeleteDialogMessage,
											theme.getName())))
					{
						// Thema aus dem Modell entfernen
						model.getTextThemes().remove(theme);

						// das geanderte Modell speichen
						officeService.saveTextModuleModel(officeContext);

						// Broker informiert
						if (broker != null)
							broker.post(ITextModuleModel.DELETE_TEXTMODULE_EVENT,theme);
					}
				}
				else
				{
					if (obj instanceof TextModule)
					{
						// das selektierte Textmodule loeschen
						TextModule module = (TextModule) obj;

						// loeschen bestaetigen
						if (MessageDialog
								.openQuestion(
										Display.getDefault().getActiveShell(),
										Messages.DeleteTextModuleAction_DeleteDialogTitle,
										Messages.bind(
												Messages.DeleteTextModuleAction_DeleteTextmoduleDialogMessage,
												module.getName())))
						{							
							// Modul aus dem Modell entfernen
							TextModuleTheme theme = TextModuleModel
									.findModuleParent((TextModuleModel) model,
											module);							
							if (theme != null)
								theme.getModules().remove(module);

							// das geanderte Modell speichen
							officeService.saveTextModuleModel(officeContext);
							
							// Broker informiert 
							if (broker != null)
								broker.post(ITextModuleModel.DELETE_TEXTMODULE_EVENT,module);
						}
					}
				}
			}
		}
	}

}
