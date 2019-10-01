package it.naturtalent.e4.office.ui.renderer;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.internal.workbench.E4Workbench;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.treemasterdetail.ui.swt.TreeMasterDetailSWTRenderer;
import org.eclipse.emf.ecp.view.treemasterdetail.model.VTreeMasterDetail;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.widgets.Composite;

import it.naturtalent.e4.office.ui.OfficeUtils;
import it.naturtalent.e4.office.ui.wizards.ODFDefaultWriteAdapterWizard;
import it.naturtalent.office.model.address.Absender;
import it.naturtalent.office.model.address.Adresse;
import it.naturtalent.office.model.address.Empfaenger;

/**
 * Angepasster Renderer 
 * SelektionListener meldet den ausgeaehlten Empfaenger 
 * @author dieter
 *
 */
public class ODFReceiverRenderer extends TreeMasterDetailSWTRenderer
{

	@Inject private IEventBroker eventBroker;
	
	private TreeViewer treeViewer;
	
	/*
	 * Listener meldet die Selektion eines Empfaegers im MasterView. 
	 */
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
			
			// Broker informiert ueber die Selektion eines Empfaengers im Master
			// @see it.naturtalent.e4.office.ui.wizards.ODFReceiverWizardPage
			eventBroker.post(OfficeUtils.RECEIVER_MASTER_SELECTED_EVENT , empfaenger);
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

	/*
	 * Ein Empfaenger wurde hinzugefuegt
	 */
	/*
	@Inject
	@Optional
	public void handleAddEmpfaeger(@UIEventTopic(OfficeUtils.ADD_EXISTING_RECEIVER) Object object)
	{
		if (object instanceof Empfaenger)
			treeViewer.add(treeViewer.getInput(), object);
	}
	*/

	/*
	 * Eine Selektion im MasterView erzwingen.
	 */
	
	@Inject 
	@Optional
	public void handleReferenceSelection(@UIEventTopic(OfficeUtils.SET_OFFICEMASTER_SELECTION_EVENT) Object empfaenger)
	{
		if (empfaenger instanceof Empfaenger)		
			treeViewer.setSelection(new StructuredSelection(empfaenger));
	}


	

}
