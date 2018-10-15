package it.naturtalent.e4.office.ui.renderer;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.services.internal.events.EventBroker;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.treemasterdetail.ui.swt.TreeMasterDetailSWTRenderer;
import org.eclipse.emf.ecp.view.treemasterdetail.model.VTreeMasterDetail;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;

import it.naturtalent.e4.office.ui.OfficeUtils;
import it.naturtalent.e4.office.ui.wizards.ODFDefaultWriteAdapterWizard;
import it.naturtalent.office.model.address.Absender;
import it.naturtalent.office.model.address.Adresse;
import it.naturtalent.office.model.address.Empfaenger;

/**
 * Angepasster Renderer zur Darstellung 
 * @author dieter
 *
 */
public class ODFReceiverRenderer extends TreeMasterDetailSWTRenderer
{

	@Inject private IEventBroker eventBroker;
	
	private TreeViewer treeViewer;
	
	private ISelectionChangedListener selectionListener = new ISelectionChangedListener()
	{		
		@Override
		public void selectionChanged(SelectionChangedEvent event)
		{
			Empfaenger empfaenger = null;
			IStructuredSelection selection = event.getStructuredSelection();
			Object selObject = selection.getFirstElement();
			if (selObject instanceof Empfaenger)
				empfaenger = (Empfaenger) selObject;
			else
			{
				if (selObject instanceof Adresse)									
					empfaenger = (Empfaenger) ((Adresse) selObject).eContainer();				
			}
			
			// Broker informiert ueber die Selektion 
			// @see it.naturtalent.e4.office.ui.wizards.ODFReceiverWizardPage
			eventBroker.post(OfficeUtils.RECEIVER_SELECTED_EVENT , empfaenger);
		}
	};


	@Inject
	public ODFReceiverRenderer(VTreeMasterDetail vElement,
			ViewModelContext viewContext, ReportService reportService)
	{
		super(vElement, viewContext, reportService);		
	}

	/* 
	 * Methodenueberschreibung um Zugriff auf den TreeViewer zu bekommen
	 */
	@Override
	protected TreeViewer createMasterTree(Composite masterPanel)
	{
		treeViewer = super.createMasterTree(masterPanel);
		treeViewer.addSelectionChangedListener(selectionListener);
		return treeViewer;
	}
	
	@Inject
	@Optional
	public void handleModelChangedEvent(@UIEventTopic(OfficeUtils.SET_RECEIVER_SELECTED_EVENT) Empfaenger empfaenger)
	{
		treeViewer.removeSelectionChangedListener(selectionListener);
		treeViewer.setSelection(new StructuredSelection(empfaenger));
		treeViewer.addSelectionChangedListener(selectionListener);
	}

}
