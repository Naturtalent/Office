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
import it.naturtalent.office.model.address.FootNote;


/**
 * FootNotes ist ein Container in dem alle FootNote-Referenzen gespeichert sind. Dieser Renderer
 * fuegt dem TableViewer ein OfficeContext - Filter hinzu, so dass nur die FootNotes des im
 * E4Context hinterlegten OfficeContext angezeigt werden. 
 *  
 * @author dieter
 *
 */
public class FootNotesRenderer extends MultiReferenceSWTRenderer
{

	private IEventBroker eventBroker;
	private String officeContext;
	private TableViewer tableViewer ;
	
	private Button delButton;
	
	//private EditingDomain domain;
	
	// Liste mit den statischen Absendern (steuert delButton-Status)
	private List<Absender>unremoveableAbsender;

	
	
	/**
	 * OfficeContext Filter erzeugen
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
			if (element instanceof FootNote)
			{	
				FootNote footNote = (FootNote) element;
				return(StringUtils.equals(footNote.getContext(), officeContext));															
			}
			
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
	public FootNotesRenderer(VControl vElement,
			ViewModelContext viewContext, ReportService reportService,
			EMFFormsDatabinding emfFormsDatabinding,
			EMFFormsLabelProvider emfFormsLabelProvider,
			VTViewTemplateProvider vtViewTemplateProvider,
			ImageRegistryService imageRegistryService)
	{
		super(vElement, viewContext, reportService, emfFormsDatabinding,
				emfFormsLabelProvider, vtViewTemplateProvider, imageRegistryService);
		
		// den hinterlegten OfficeContext verfuegbar machen
		officeContext = (String) E4Workbench.getServiceContext().get(OfficeUtils.E4CONTEXTKEY_OFFICECONTEXT);
		
		// Broker bereitstellen
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
				/*
				IStructuredSelection selection = ((IStructuredSelection) event.getSelection());
				Object selObj = selection.getFirstElement();
				if (selObj instanceof Absender)				
					eventBroker.post(OfficeUtils.GET_ABSENDER_DETAIL_SELECTED_EVENT, selObj);
					*/					
			}
		});
		
		return tableViewer;
	}



}
