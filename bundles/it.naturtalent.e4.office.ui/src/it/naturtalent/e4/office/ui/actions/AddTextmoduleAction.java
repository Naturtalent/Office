package it.naturtalent.e4.office.ui.actions;

import it.naturtalent.e4.office.IOfficeService;
import it.naturtalent.e4.office.ITextModuleDataFactory;
import it.naturtalent.e4.office.ITextModuleModel;
import it.naturtalent.e4.office.TextModule;
import it.naturtalent.e4.office.TextModuleTheme;
import it.naturtalent.e4.office.ui.dialogs.AddTextModuleDialog;
import it.naturtalent.e4.office.ui.dialogs.SelectThemeTextModuleDialog;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Display;



public class AddTextmoduleAction extends Action
{
	private String officeContext;
	
	private IEventBroker broker;
	private IOfficeService officeService;	
	private ITextModuleModel model;

	private TreeViewer viewer;
	
	// Validator fuer InputDialog
	IInputValidator validator = new IInputValidator()
	{
		public String isValid(String string)
		{
			if (StringUtils.isEmpty(string))						
				return "leere Eingabe";
			
			return null;
		}
	};
	
	
	/**
	 * @wbp.parser.entryPoint
	 */
	public AddTextmoduleAction(String officeContext, TreeViewer textModuleViewer)
	{
		super();
		this.officeContext = officeContext;
		this.viewer = textModuleViewer;		
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
		IStructuredSelection selection = (IStructuredSelection) viewer
				.getSelection();
		if ((selection != null) && (!selection.isEmpty()))
		{
			Object obj = selection.getFirstElement();
			if (obj instanceof TextModuleTheme)
			{
				// Auswahl zwischen neuem Thema oder neuem Textbaustein
				SelectThemeTextModuleDialog selectDialog = new SelectThemeTextModuleDialog(
						Display.getDefault().getActiveShell());
				if (selectDialog.open() == SelectThemeTextModuleDialog.OK)
				{
					if (selectDialog.getSelectedMode() == SelectThemeTextModuleDialog.SELECT_THEME)
					{
						// neues Thema hinzufuegen
						doAddTheme();
					}
					else
					{
						// neuen Textbaustein zum selektierten Thema hinzufuegen
						AddTextModuleDialog dialog = new AddTextModuleDialog(
								Display.getDefault().getActiveShell());
						dialog.create();
						dialog.setTextModule(new TextModule());
						if (dialog.open() == AddTextModuleDialog.OK)
						{
							// das neue Textmodule zum selektierten Parent Thema hinzufuegen
							TextModuleTheme theme = (TextModuleTheme) obj;
							TextModule textModule = dialog.getTextModule();
							theme.getModules().add(textModule);

							// das geanderte Modell speichen
							if(model != null)
								officeService.saveTextModuleModel(officeContext);

							// Broker informiert
							if (broker != null)
								broker.post(ITextModuleModel.ADD_TEXTMODULE_EVENT,
										textModule);
						}
					}
				}
			}
		}
		else
		{
			// keine Selektion - neues Thema
			doAddTheme();
		}
	}	
	
	private void doAddTheme()
	{
		InputDialog dialog = new InputDialog(Display
				.getDefault().getActiveShell(),
				"Textbausteine", "neues Thema", null,
				validator);
		dialog.setBlockOnOpen(true);
		if (dialog.open() == InputDialog.OK)
		{
			if (model != null)
			{
				// neues Thema im Modell aufnehmen
				TextModuleTheme theme = new TextModuleTheme();
				theme.setName(dialog.getValue());
				model.getTextThemes().add(theme);

				// das geanderte Modell speichen
				officeService.saveTextModuleModel(officeContext);

				// Broker informiert
				if (broker != null)
					broker.post(ITextModuleModel.ADD_TEXTMODULE_EVENT, theme);
			}
		}
	}
	
	
}
