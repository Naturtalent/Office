package it.naturtalent.e4.office.ui.handlers;

import it.naturtalent.e4.office.TextModule;
import it.naturtalent.e4.office.TextModuleModel;
import it.naturtalent.e4.office.TextModuleTheme;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;


public class AddBrokerEventHandler implements EventHandler
{
	private TreeViewer treeViewer;

	public AddBrokerEventHandler(TreeViewer treeViewer)
	{
		super();
		this.treeViewer = treeViewer;
	}

	@Override
	public void handleEvent(Event event)
	{
		Object textModuleObject = event.getProperty(IEventBroker.DATA);
		if(textModuleObject instanceof TextModule)
		{
			// Textmodule wurde eingefuegt
			TextModule newModule = (TextModule) textModuleObject; 
			
			// Parentthema suchen
			TextModuleModel model = (TextModuleModel) treeViewer.getInput();
			if(model != null)
			{
				TextModuleTheme thema = TextModuleModel.findModuleParent(model, newModule);
				if(thema != null)
				{
					treeViewer.add(thema, newModule);
					treeViewer.setSelection(new StructuredSelection(newModule));											
				}
			}
		}
		else
		{
			if(textModuleObject instanceof TextModuleTheme)
			{
				TextModuleTheme newTheme = (TextModuleTheme) textModuleObject;
				treeViewer.add(treeViewer.getInput(), newTheme);
			}
		}
	}

}
