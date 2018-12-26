package it.naturtalent.e4.office.ui.renderer;

import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.internal.workbench.E4Workbench;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.workbench.IWorkbench;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.view.internal.control.multireference.MultiReferenceSWTRenderer;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emf.ecp.view.spi.util.swt.ImageRegistryService;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.core.services.label.EMFFormsLabelProvider;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridCell;
import org.eclipse.jface.viewers.AbstractTableViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import it.naturtalent.e4.office.ui.OfficeUtils;
import it.naturtalent.e4.office.ui.wizards.ODFDefaultWriteAdapterWizard;
import it.naturtalent.office.model.address.FootNote;

public class FootNoteSetRenderer extends MultiReferenceSWTRenderer
{
	
	private Button delButton;
	private AbstractTableViewer tableViewer;	
	
	private IEventBroker eventBroker;
	
	// Liste mit den statischen Footnotes (steuert delButton-Status)
	private List<FootNote>unremoveableFootNotes;

	/*
	 *  filtert Fussnoten nach 'officeContext' 
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
			if (element instanceof FootNote)
			{					
				String elementContext = ((FootNote)element).getContext();
					return(StringUtils.equals(elementContext, officeContext));				
			}
			
			// nicht fuer diesen Filter bestimmt
			return true;
		}
	}

	
	@Inject
	public FootNoteSetRenderer(VControl vElement,
			ViewModelContext viewContext, ReportService reportService,
			EMFFormsDatabinding emfFormsDatabinding,
			EMFFormsLabelProvider emfFormsLabelProvider,
			VTViewTemplateProvider vtViewTemplateProvider,
			ImageRegistryService imageRegistryService)
	{
		super(vElement, viewContext, reportService, emfFormsDatabinding,
				emfFormsLabelProvider, vtViewTemplateProvider, imageRegistryService);
		
		MApplication currentApplication = E4Workbench.getServiceContext().get(IWorkbench.class).getApplication();
		eventBroker = currentApplication.getContext().get(IEventBroker.class);
	}
	
	@Override
	protected Control renderMultiReferenceControl(SWTGridCell cell,
			Composite parent)
			throws NoRendererFoundException, NoPropertyDescriptorFoundExeption
	{
		Control control = super.renderMultiReferenceControl(cell, parent);
		
		tableViewer = getTableViewer();
		
		// OfficeContext fuer den Filter aus dem Eclipse4-Context abrufen
		IEclipseContext context = E4Workbench.getServiceContext();
		String officeContext = (String) context.get(ODFDefaultWriteAdapterWizard.DEFAULT_OFFICECONTEXT);		
		tableViewer.setFilters(new ViewerFilter []{new ContextFilter(officeContext)});
		
		unremoveableFootNotes = (List<FootNote>) context.get(OfficeUtils.FOOTNOTE_UNREMOVABLES);
		context.remove(OfficeUtils.FOOTNOTE_UNREMOVABLES);
		
		tableViewer.addSelectionChangedListener(new ISelectionChangedListener()
		{			
			@Override
			public void selectionChanged(SelectionChangedEvent event)
			{
				IStructuredSelection selection = event.getStructuredSelection();
				Object object = selection.getFirstElement();			
				if (object instanceof FootNote)
				{
					// nur nichtstatische FootNotes duerfen geloescht werdxen
					if ((unremoveableFootNotes != null) && (!unremoveableFootNotes.isEmpty()))					
						delButton.setEnabled(!unremoveableFootNotes.contains((FootNote) object));		
					
					eventBroker.post(OfficeUtils.SELECT_FOOTNOTE_EVENT, object);	
				}				
			}
		});
		
		return control;
	}
		
	
	@Override
	protected Button createDeleteButton(Composite parent,EStructuralFeature structuralFeature)
	{
		delButton = super.createDeleteButton(parent, structuralFeature);		
		return delButton;
	}
	
	/*
	 * ADD - Button handling
	 * ein neues Object wurde hinzugefuegt
	 * 
	 */
	@Override
	protected void handleAddNew(TableViewer tableViewer, EObject eObject,
			EStructuralFeature structuralFeature)
	{		
		super.handleAddNew(tableViewer, eObject, structuralFeature);
		eventBroker.send(OfficeUtils.ADD_NEWFOOTNOTE_EVENT, eObject);	
		tableViewer.refresh();
	}


	
	@Inject 
	@Optional
	public void handleReferenceSelection(@UIEventTopic(OfficeUtils.FOOTNOTESET_REQUESTSELECTREFERENCEEVENT) Object footNote)
	{
		if (footNote instanceof FootNote)		
			getTableViewer().setSelection(new StructuredSelection(footNote));
	}
	
	@Override
	protected boolean showAddExistingButton()
	{
		return false;
	}


	
}