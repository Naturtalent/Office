package it.naturtalent.e4.office.ui.renderer;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.internal.workbench.E4Workbench;
import org.eclipse.emf.ecore.EObject;
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



/**
 * Den Absender Master-Renderer anpassen.
 * 
 * - filtern nach OfficeContext-Flag, das im Eclipse4 Context unter dem Namen 'OfficeUtils.OFFICE_CONTEXT' hinterlegt sein muss.
 * - meldet die Selektion eines Absenders mit einem 'OfficeUtils.SET_ABSENDERMASTER_SELECTION_EVENT' Event  
 * 
 * @author dieter
 *
 */
public class SendersRenderer extends TreeMasterDetailSWTRenderer
{

	@Inject private IEventBroker eventBroker;
	
	private TreeViewer treeViewer;
	
	/*
	 *  filtert Absender nach dem in 'officeContext' hinterlegten Referenzwert
	 *   
	 */
	private class ContextFilter extends ViewerFilter
	{
		String officeContext;
		
		public ContextFilter(String officeContext)
		{
			super();
			this.officeContext = officeContext;
		}

		@Override
		public boolean select(Viewer viewer, Object parentElement, Object element)
		{					
			if (element instanceof Absender)
			{					
				String elementContext = ((Absender)element).getContext();
					return(StringUtils.equals(elementContext, officeContext));				
			}
			
			// nicht fuer diesen Filter bestimmt
			return true;
		}
	}
	
	/*
	 * Listener informiert ueber den selektierten Absender im MasterTree
	 * 
	 */
	private ISelectionChangedListener selectionListener = new ISelectionChangedListener()
	{		
		@Override
		public void selectionChanged(SelectionChangedEvent event)
		{
			Absender absender = null;
			IStructuredSelection selection = event.getStructuredSelection();
			Object selObject = selection.getFirstElement();
			if (selObject instanceof Absender)
				absender = (Absender) selObject;
			else
			{
				if (selObject instanceof Adresse)									
					absender = (Absender) ((Adresse) selObject).eContainer();				
			}
			
			// Broker informiert ueber die Selektion 
			// @see it.naturtalent.e4.office.ui.wizards.ODFSenderWizardPage
			eventBroker.post(OfficeUtils.ABSENDERMASTER_SELECTED_EVENT , absender);
		}
	};
	
	/**
	 * Konstruktion
	 * 
	 * @param vElement
	 * @param viewContext
	 * @param reportService
	 */
	@Inject
	public SendersRenderer(VTreeMasterDetail vElement,
			ViewModelContext viewContext, ReportService reportService)
	{
		super(vElement, viewContext, reportService);	
	}
	
	/** 
	 *  Erweiterung 'createMasterTree()' zur Realisiereung der o.g. Features
	 */
	@Override
	protected TreeViewer createMasterTree(Composite masterPanel)
	{
		IEclipseContext context = E4Workbench.getServiceContext();
		String officeContext = (String) context.get(ODFDefaultWriteAdapterWizard.DEFAULT_OFFICECONTEXT);		
		
		treeViewer = super.createMasterTree(masterPanel);
		treeViewer.addSelectionChangedListener(selectionListener);	
		treeViewer.setFilters(new ViewerFilter []{new ContextFilter(officeContext)});
		return treeViewer;
	}
	
	@Inject
	@Optional
	public void handleModelChangedEvent(@UIEventTopic(OfficeUtils.SET_ABSENDERMASTER_SELECTION_EVENT) EObject eObject)
	{		
		if(eObject != null)
			treeViewer.setSelection(new StructuredSelection(eObject));	
	}

	
}
