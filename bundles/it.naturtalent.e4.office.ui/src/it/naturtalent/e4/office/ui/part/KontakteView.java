
package it.naturtalent.e4.office.ui.part;

import java.util.EventObject;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.menu.MToolItem;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CommandStackListener;
import org.eclipse.emf.ecp.ui.view.ECPRendererException;
import org.eclipse.emf.ecp.ui.view.swt.ECPSWTViewRenderer;
import org.eclipse.emf.edit.command.DeleteCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.emfstore.internal.client.model.changeTracking.commands.EMFStoreBasicCommandStack;
import org.eclipse.swt.widgets.Composite;


import it.naturtalent.e4.office.ui.OfficeUtils;
import it.naturtalent.e4.project.ui.Activator;
import it.naturtalent.e4.project.ui.parts.emf.NtProjectView;
import it.naturtalent.office.model.address.Kontakte;

public class KontakteView
{

	public final static String KONTAKTEVIEW_ID = "it.naturtalent.e4.office.ui.part.kontakte";
	
	// in 'fragment.e4xmi' definiert
	public static final String SAVE_TOOLBAR_ID = "it.naturtalent.e4.office.ui.directtoolitem.save";
	public static final String UNDO_TOOLBAR_ID = "it.naturtalent.e4.office.ui.directtoolitem.undo";
	
	@Inject @Optional private EModelService modelService;
	@Inject @Optional private EPartService partService;
	@Inject @Optional private IEventBroker eventBroker;

	
	/*
	 * CommandStackListener ueberwacht Aenderungen am Archiv
	 */
	private class KontakteCommandStackListener implements CommandStackListener
	{
		@Override
		public void commandStackChanged(EventObject event)
		{
			EMFStoreBasicCommandStack commandStack = (EMFStoreBasicCommandStack) event.getSource();
			Command command = commandStack.getMostRecentCommand();	
							
			if(command instanceof SetCommand)				
			{
				updateUNDOSAVEToolbar();
			}
			
			if(command instanceof DeleteCommand)				
			{
				updateUNDOSAVEToolbar();
				
				// NtProjectView aktualisieren
				//Archive archive = ArchivUtils.getArchive();
				//eventBroker.post(NtProjectView.UPDATE_PROJECTVIEW_REQUEST, archive);
			}
		}
	}

	private KontakteCommandStackListener kontakteCommandStackListener = new KontakteCommandStackListener();
	
	/*
	 * Status der Toolbar (undo/save) aktualisieren
	 */
	private void updateUNDOSAVEToolbar()
	{
		// Toolbarstatus updaten
		MPart mPart = partService.findPart(KONTAKTEVIEW_ID);

		// save - Toolbar
		List<MToolItem> items = modelService.findElements(mPart, SAVE_TOOLBAR_ID, MToolItem.class,null, EModelService.IN_PART);
		MToolItem item = items.get(0);
		item.setEnabled(true);
		
		// undo - Toolbar	
		items = modelService.findElements(mPart, UNDO_TOOLBAR_ID, MToolItem.class,null, EModelService.IN_PART);
		item = items.get(0);
		item.setEnabled(true);
		
	}

	
	
	@PostConstruct
	public void postConstruct(Composite parent)
	{
		Kontakte kontakte = OfficeUtils.getKontakte();
		if(kontakte != null)
		{
			try
			{
				ECPSWTViewRenderer.INSTANCE.render(parent, kontakte);
			}

			catch (ECPRendererException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		// Listener meldet Aenderungen 
		EditingDomain domain = AdapterFactoryEditingDomain.getEditingDomainFor(Activator.getECPProject());
		EMFStoreBasicCommandStack commandStack = (EMFStoreBasicCommandStack) domain.getCommandStack();
		domain.getCommandStack().addCommandStackListener(kontakteCommandStackListener);


	}

	@PreDestroy
	public void preDestroy()
	{

		// CommandStackListener entfernen
		EditingDomain domain = AdapterFactoryEditingDomain.getEditingDomainFor(Activator.getECPProject());
		EMFStoreBasicCommandStack commandStack = (EMFStoreBasicCommandStack) domain.getCommandStack();
		domain.getCommandStack().removeCommandStackListener(kontakteCommandStackListener);
	}

}