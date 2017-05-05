package it.naturtalent.e4.office.ui.actions;

import javax.annotation.PostConstruct;

import it.naturtalent.e4.office.IOfficeService;
import it.naturtalent.e4.office.ITextModuleDataFactory;
import it.naturtalent.e4.office.ITextModuleModel;
import it.naturtalent.e4.office.OpenDocumentUtils;
import it.naturtalent.e4.office.TextModule;
import it.naturtalent.e4.office.TextModuleModel;
import it.naturtalent.e4.office.TextModuleTheme;
import it.naturtalent.e4.office.ui.dialogs.EditTextModuleDialog;
import it.naturtalent.e4.office.ui.dialogs.AddTextModuleDialog;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Display;

public class EditTextmoduleAction extends Action
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
	public EditTextmoduleAction(String officeContext, TreeViewer textModuleViewer)
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
		IStructuredSelection selection = (IStructuredSelection) viewer.getSelection();
		if ((selection != null) && (!selection.isEmpty()))
		{
			Object obj = selection.getFirstElement();
			if (obj instanceof TextModuleTheme)
			{
				TextModuleTheme theme = (TextModuleTheme) obj;
				InputDialog dialog = new InputDialog(Display.getDefault()
						.getActiveShell(), "Textbausteine", "Thema editieren",
						theme.getName(), validator);
				dialog.setBlockOnOpen(true);
				if (dialog.open() == EditTextModuleDialog.OK)
				{
					// neuen Themanamen uebernehmen
					theme.setName(dialog.getValue());
					
					// das geanderte Modell speichen
					if(model != null)
						officeService.saveTextModuleModel(officeContext);

					// Broker informiert
					if (broker != null)
						broker.post(ITextModuleModel.UPDATE_TEXTMODULE_EVENT, theme);
				}
			}
			else
			{
				if (obj instanceof TextModule)
				{
					TextModule textModule = (TextModule) obj;
					EditTextModuleDialog dialog = new EditTextModuleDialog(
							Display.getDefault().getActiveShell());
					dialog.create();
					TextModule editModule = (TextModule) textModule.clone();
					dialog.setTextModule(editModule);
					if (dialog.open() == EditTextModuleDialog.OK)
					{
						textModule.setName(editModule.getName());
						textModule.setContent(editModule.getContent());
						
						// das geanderte Modell speichen
						if(model != null)
							officeService.saveTextModuleModel(officeContext);

						// Broker informiert
						if (broker != null)
							broker.post(ITextModuleModel.UPDATE_TEXTMODULE_EVENT, textModule);
					}
				}
			}
		}
	}	
	
}
