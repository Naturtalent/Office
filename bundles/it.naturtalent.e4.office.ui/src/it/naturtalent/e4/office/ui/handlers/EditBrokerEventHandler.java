package it.naturtalent.e4.office.ui.handlers;

import it.naturtalent.e4.office.TextModule;
import it.naturtalent.e4.office.TextModuleModel;
import it.naturtalent.e4.office.TextModuleTheme;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeViewer;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;


public class EditBrokerEventHandler implements EventHandler
{
	private TreeViewer treeViewer;

	public EditBrokerEventHandler(TreeViewer treeViewer)
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
			// editiertes Textmodul
			TextModuleModel model = (TextModuleModel) treeViewer.getInput();
			if(model != null)
			{
				TreePath [] expTree = treeViewer.getExpandedTreePaths();
				treeViewer.refresh();
				treeViewer.setExpandedTreePaths(expTree);
			}
		}
		else
		{
			if (textModuleObject instanceof TextModuleTheme)
			{
				// editiertes Thema
				TextModuleTheme theme = (TextModuleTheme) textModuleObject;
				treeViewer.refresh(theme);
			}
		}
	}

}
