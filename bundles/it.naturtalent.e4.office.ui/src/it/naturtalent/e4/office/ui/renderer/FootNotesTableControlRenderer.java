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
import org.eclipse.emf.ecp.view.spi.table.model.VTableControl;
import org.eclipse.emf.ecp.view.spi.table.swt.TableControlSWTRenderer;
import org.eclipse.emf.ecp.view.spi.util.swt.ImageRegistryService;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emf.ecp.view.template.style.tableValidation.model.VTTableValidationStyleProperty;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.core.services.databinding.emf.EMFFormsDatabindingEMF;
import org.eclipse.emfforms.spi.core.services.editsupport.EMFFormsEditSupport;
import org.eclipse.emfforms.spi.core.services.label.EMFFormsLabelProvider;
import org.eclipse.jface.viewers.AbstractTableViewer;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.widgets.Composite;

import it.naturtalent.e4.office.ui.OfficeUtils;
import it.naturtalent.e4.office.ui.wizards.ODFDefaultWriteAdapterWizard;
import it.naturtalent.office.model.address.Absender;
import it.naturtalent.office.model.address.FootNote;

/**
 * Renderer der die FootNoteItems in einer Tabelle darstellt und somit die eigentliche Footnotes darstellt die in einem
 * Anschreiben dargestellt werden.
 * 
 * @author dieter
 *
 */
public class FootNotesTableControlRenderer extends TableControlSWTRenderer
{
	@Inject private IEventBroker eventBroker;
	
	private AbstractTableViewer tableViewer;
	
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
	public FootNotesTableControlRenderer(VTableControl vElement,
			ViewModelContext viewContext, ReportService reportService,
			EMFFormsDatabinding emfFormsDatabinding,
			EMFFormsLabelProvider emfFormsLabelProvider,
			VTViewTemplateProvider vtViewTemplateProvider,
			ImageRegistryService imageRegistryService,
			EMFFormsEditSupport emfFormsEditSupport)
	{
		super(vElement, viewContext, reportService, (EMFFormsDatabindingEMF) emfFormsDatabinding,
				emfFormsLabelProvider, vtViewTemplateProvider, imageRegistryService,
				emfFormsEditSupport);
	}

	/*
	 * Validation Spalte unsichtbar machen
	 * 
	 * @see org.eclipse.emf.ecp.view.spi.table.swt.TableControlSWTRenderer#getTableValidationStyleProperty()
	 */
	@Override
	protected VTTableValidationStyleProperty getTableValidationStyleProperty()
	{
		VTTableValidationStyleProperty styleProperty = super.getTableValidationStyleProperty();		
		styleProperty.setColumnWidth(1);		
		return styleProperty;
	}
	
	
	/*
	 * geeignete Funktion um Filter einzuschalten 
	 */
	@Override
	protected void setTableViewer(AbstractTableViewer tableViewer)
	{
		// OfficeContext fuer den Filter aus dem Eclipse4-Context abrufen
		this.tableViewer = tableViewer;
		IEclipseContext context = E4Workbench.getServiceContext();
		String officeContext = (String) context.get(ODFDefaultWriteAdapterWizard.DEFAULT_OFFICECONTEXT);		
		//tableViewer.setFilters(new ViewerFilter []{new ContextFilter(officeContext)});
		
		super.setTableViewer(tableViewer);
	}

	@Inject
	@Optional
	public void handleModelChangedEvent(@UIEventTopic(OfficeUtils.FOOTNOTESET_REQUESTSELECTREFERENCEEVENT) EObject eObject)
	{		
		if(eObject != null)
			tableViewer.setSelection(new StructuredSelection(eObject));	
	}

}
