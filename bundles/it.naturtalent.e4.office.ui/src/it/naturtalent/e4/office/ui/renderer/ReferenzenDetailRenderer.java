package it.naturtalent.e4.office.ui.renderer;

import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.databinding.observable.IObserving;
import org.eclipse.core.databinding.observable.value.IObservableValue;
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
import org.eclipse.emf.ecp.view.spi.util.swt.ImageRegistryService;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.core.services.label.EMFFormsLabelProvider;
import org.eclipse.jface.viewers.IOpenListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.OpenEvent;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import it.naturtalent.e4.office.ui.OfficeUtils;
import it.naturtalent.office.model.address.Absender;
import it.naturtalent.office.model.address.Referenz;


/**
 *  Referenzen Detailrenderer
 *  
 *  - filtern nach OfficeContext-Flag, das im Eclipse4 Context unter dem Namen 'OfficeUtils.OFFICE_CONTEXT' hinterlegt sein muss.
 *  
 * @author dieter
 *
 */
public class ReferenzenDetailRenderer extends MultiReferenceSWTRenderer
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
			if (element instanceof Referenz)
			{
				Referenz referenz = (Referenz) element;
				return (StringUtils.equals(referenz.getContext(),
						officeContext));
			}

			return true;
		}
	}
	
	/**
	 * Konstrultion
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
	public ReferenzenDetailRenderer(VControl vElement,
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
