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
import it.naturtalent.office.model.address.Referenz;
import it.naturtalent.office.model.address.Signature;
import it.naturtalent.office.model.address.SignatureSet;

public class SignatureSetRenderer extends MultiReferenceSWTRenderer
{
	//private AbstractTableViewer tableViewer;
	
	// Liste mit den statischen Signaturen (z.B. temp. erzeugte, steuert delButton-Status)
	private List<Signature>unremoveableSignatures;
	
	private IEventBroker eventBroker;
	
	private Button delButton;

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
			if (element instanceof Signature)
			{					
				String elementContext = ((Signature)element).getContext();
					return(StringUtils.equals(elementContext, officeContext));				
			}
			
			// nicht fuer diesen Filter bestimmt
			return true;
		}
	}

	
	@Inject
	public SignatureSetRenderer(VControl vElement,
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
		
		AbstractTableViewer tableViewer = getTableViewer();
				
		// OfficeContext fuer den Filter aus dem Eclipse4-Context abrufen
		IEclipseContext context = E4Workbench.getServiceContext();
		String officeContext = (String) context.get(OfficeUtils.E4CONTEXTKEY_OFFICECONTEXT);		
		tableViewer.setFilters(new ViewerFilter []{new ContextFilter(officeContext)});
		
		// Liste der Nichtloeschbaren aus dem Eclipse4Context holen
		unremoveableSignatures = (List<Signature>) context.get(OfficeUtils.SIGNATURE_UNREMOVABLES);
		context.remove(OfficeUtils.SIGNATURE_UNREMOVABLES);
		
		tableViewer.addSelectionChangedListener(new ISelectionChangedListener()
		{			
			@Override
			public void selectionChanged(SelectionChangedEvent event)
			{
				IStructuredSelection selection = event.getStructuredSelection();
				Object object = selection.getFirstElement();			
				if (object instanceof Signature)
				{
					// nur nichtstatische FootNotes duerfen geloescht werdxen
					if ((unremoveableSignatures != null) && (!unremoveableSignatures.isEmpty()))					
						delButton.setEnabled(!unremoveableSignatures.contains((Signature) object));
					
					eventBroker.post(OfficeUtils.SELECT_SIGNATURE_EVENT, object);
				}
			}
		});
		
		return control;
	}

/*
	@Inject 
	@Optional
	public void handleReferenceSelection(@UIEventTopic(OfficeUtils.FOOTNOTESET_REQUESTSELECTREFERENCEEVENT) Object footNote)
	{
		if (footNote instanceof FootNote)		
			getTableViewer().setSelection(new StructuredSelection(footNote));
	}
*/
	
	
	/*
	 * AddExistingButton ausblenden
	 * 
	 */
	@Override
	protected boolean showAddExistingButton()
	{
		return false;
	}
	
	@Override
	protected Button createDeleteButton(Composite parent,EStructuralFeature structuralFeature)
	{
		delButton = super.createDeleteButton(parent, structuralFeature);		
		return delButton;
	}

	@Override
	protected void handleDelete(TableViewer tableViewer, EObject eObject,
			EStructuralFeature structuralFeature)
	{
		// die selektierte Signatur melden
		IStructuredSelection selection = tableViewer.getStructuredSelection();
		eventBroker.post(OfficeUtils.REMOVE_SIGNATURE_EVENT, selection.getFirstElement());
		
		super.handleDelete(tableViewer, eObject, structuralFeature);
	}

	/*
	 *  Die hinzugefuegte Signatur wird gemeldet.
	 * 
	 */
	@Override
	protected void handleAddNew(TableViewer tableViewer, EObject eObject,
			EStructuralFeature structuralFeature)
	{		
		super.handleAddNew(tableViewer, eObject, structuralFeature);		
		eventBroker.post(OfficeUtils.ADD_SIGNATURE_EVENT, eObject);	
	}

	@Inject 
	@Optional
	public void handleSignatureSelectionRequest(@UIEventTopic(OfficeUtils.SIGNATURE_REQUESTSELECTIONEVENT) Object event)
	{
		if (event instanceof Signature)	
		{
			getTableViewer().refresh();
			getTableViewer().setSelection(new StructuredSelection(event));
		}
	}

	
}
