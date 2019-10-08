package it.naturtalent.e4.office.ui.renderer;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.databinding.observable.IObserving;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.internal.workbench.E4Workbench;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.workbench.IWorkbench;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.view.internal.control.multireference.MultiReferenceSWTRenderer;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.util.swt.ImageRegistryService;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.core.services.label.EMFFormsLabelProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import it.naturtalent.e4.office.ui.OfficeUtils;


/**
 *  universeller Detailrenderer im OfficeContext mit folgenden Aufgaben: 
 *  
 *  - filtern nach OfficeContext-Flag, das im Eclipse4 Context unter dem Namen 'OfficeUtils.OFFICE_CONTEXT' 
 *    hinterlegt sein muss
 *  
 *  - blendet die Tooltips auf Containerebene aus und verhndert somit (add, edit, etc)
 *    In diesem Kontext soll nur ausgewaehlt werden - bleibende Aenderungen am Modell nur ueber die Preferenzen 
 *  
 * @author dieter
 *
 */
public class OfficeDetailRenderer extends MultiReferenceSWTRenderer
{

	private IEventBroker eventBroker;
	private String officeContext;
	private TableViewer tableViewer ;
	
	// filtert nach dem Office - Context
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
			// 'context' - Attribut aus dem element auslesen und mit 'officeContext' vergleichen
			Object resultObject = null;
			EObject refObject = (EObject)element;
			EList<EStructuralFeature> eAllAttributes = refObject.eClass().getEAllStructuralFeatures();
			for (EStructuralFeature eAttribute : eAllAttributes)
			{
				if (StringUtils.equals(eAttribute.getName(),"context"))
				{
					resultObject = refObject.eGet(eAttribute);
					break;
				}   
		    }

			if (resultObject instanceof String)
				return (StringUtils.equals((String)resultObject, officeContext));
		
			return true;
		}
	}
	
	/**
	 * Konstruktion
	 * 
	 * @param vElement
	 * @param viewContext
	 * @param reportService
	 * @param emfFormsDatabinding
	 * @param emfFormsLabelProvider
	 * @param vtViewTemplateProvider
	 * @param imageRegistryService
	 */
	@Inject
	public OfficeDetailRenderer(VControl vElement,
			ViewModelContext viewContext, ReportService reportService,
			EMFFormsDatabinding emfFormsDatabinding,
			EMFFormsLabelProvider emfFormsLabelProvider,
			VTViewTemplateProvider vtViewTemplateProvider,
			ImageRegistryService imageRegistryService)
	{
		super(vElement, viewContext, reportService, emfFormsDatabinding,
				emfFormsLabelProvider, vtViewTemplateProvider, imageRegistryService);
		
		officeContext = (String) E4Workbench.getServiceContext().get(OfficeUtils.E4CONTEXTKEY_OFFICECONTEXT);
		MApplication currentApplication = E4Workbench.getServiceContext().get(IWorkbench.class).getApplication();
		eventBroker = currentApplication.getContext().get(IEventBroker.class);
	}

	/*
	 * TableViewer modifizieren
	 * - OfficeContext-Filter einfuegen
	 * - SelektionListener einfuegen
	 */
	@Override
	protected TableViewer getTableViewer()
	{
		tableViewer = super.getTableViewer();
		tableViewer.setFilters(new ViewerFilter []{new ContextFilter(officeContext)});
		tableViewer.addSelectionChangedListener(new ISelectionChangedListener()
		{			
			@Override
			public void selectionChanged(SelectionChangedEvent event)
			{
				IStructuredSelection selection = ((IStructuredSelection) event.getSelection());
				Object selObj = selection.getFirstElement();
				//if (selObj instanceof Absender)				
					//eventBroker.post(OfficeUtils.GET_ABSENDER_DETAIL_SELECTED_EVENT, selObj);					
			}
		});
		
		return tableViewer;
	}

	/*
	 * Buttons werden ausgeblendet
	 */
	@Override
	protected Composite createButtonComposite(Composite parent) throws DatabindingFailedException
	{		
		IObservableValue observableValue = getEMFFormsDatabinding()
				.getObservableValue(getVElement().getDomainModelReference(), getViewModelContext().getDomainModel());
			Object container = (EObject) ((IObserving) observableValue).getObserved();
			final EStructuralFeature structuralFeature = (EStructuralFeature) observableValue.getValueType();
		
		// TODO Auto-generated method stub
		//return super.createButtonComposite(parent);
		final Composite buttonComposite = new Composite(parent, SWT.NONE);
		return buttonComposite;
	}
	
	


}
