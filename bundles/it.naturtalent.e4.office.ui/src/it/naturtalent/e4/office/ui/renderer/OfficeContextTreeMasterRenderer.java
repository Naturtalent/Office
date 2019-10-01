package it.naturtalent.e4.office.ui.renderer;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
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
import it.naturtalent.office.model.address.Absender;
import it.naturtalent.office.model.address.Adresse;
import it.naturtalent.office.model.address.FootNote;
import it.naturtalent.office.model.address.Referenz;
import it.naturtalent.office.model.address.Referenzen;



/**
 *  ein Multi TreeMasterRenderer der Elemente nach OfficeContext filtert
 * 
 * @author dieter
 *
 */
public class OfficeContextTreeMasterRenderer extends TreeMasterDetailSWTRenderer
{

	@Inject private IEventBroker eventBroker;
	
	private TreeViewer treeViewer;
	

	/**
	 * OfficeContext - Filter
	 * 
	 * @author dieter
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
			/*
			if (element instanceof EObject)
			{
				EObject eObject = (EObject) element;
				System.out.println(eObject);
			}
			*/
			
			if (element instanceof FootNote)
			{
				FootNote footNote = (FootNote) element;
				return(StringUtils.equals(footNote.getContext(), officeContext));		
			}
						
			if (element instanceof Absender)
			{					
				String elementContext = ((Absender)element).getContext();
					return(StringUtils.equals(elementContext, officeContext));				
			}
			
			
			if (element instanceof Referenz)
			{					
				Referenz referenz = (Referenz) element;				
					return(StringUtils.equals(referenz.getContext(), officeContext));				
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
			IStructuredSelection selection = event.getStructuredSelection();
			EObject selObject = (EObject) selection.getFirstElement();
			
/*			
			if (selObject instanceof EObject)
			{
				EObject eObject = selObject;
				
			}
				absender = (Absender) selObject;
			else
			{
				if (selObject instanceof Adresse)									
					absender = (Absender) ((Adresse) selObject).eContainer();				
			}
			*/
			
			// Broker informiert ueber die Selektion 
			// @see it.naturtalent.e4.office.ui.wizards.ODFSenderWizardPage
			if(selObject != null)
				eventBroker.post(OfficeUtils.GET_OFFICEMASTER_SELECTION_EVENT, selObject);
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
	public OfficeContextTreeMasterRenderer(VTreeMasterDetail vElement,
			ViewModelContext viewContext, ReportService reportService)
	{
		super(vElement, viewContext, reportService);	
	}
	
	/** 
	 *  diese Funktion wird genutzt, um den OfficeFilter dem TreeViewer hinzuzufuegen
	 */
	@Override
	protected TreeViewer createMasterTree(Composite masterPanel)
	{		
		String officeContext = (String) E4Workbench.getServiceContext().get(OfficeUtils.E4CONTEXTKEY_OFFICECONTEXT);		
		
		treeViewer = super.createMasterTree(masterPanel);
		treeViewer.addSelectionChangedListener(selectionListener);	
		treeViewer.setFilters(new ViewerFilter []{new ContextFilter(officeContext)});
		return treeViewer;
	}
	
	
	/*
	 * Kontextmenue im TreeMaster abschalten, 
	 * 
	 */
	@Override
	protected boolean hasContextMenu()
	{
		return false;
	}

	@Inject
	@Optional
	public void handleModelChangedEvent(@UIEventTopic(OfficeUtils.SET_OFFICEMASTER_SELECTION_EVENT) EObject eObject)
	{		
		if(eObject != null)
		{
			treeViewer.refresh();
			treeViewer.setSelection(new StructuredSelection(eObject));
		}
	}

	
}
