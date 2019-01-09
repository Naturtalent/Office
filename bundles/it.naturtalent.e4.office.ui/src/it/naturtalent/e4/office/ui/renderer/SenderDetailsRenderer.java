package it.naturtalent.e4.office.ui.renderer;

import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.internal.workbench.E4Workbench;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.workbench.IWorkbench;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.view.internal.control.multireference.MultiReferenceSWTRenderer;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emf.ecp.view.spi.util.swt.ImageRegistryService;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.core.services.label.EMFFormsLabelProvider;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridCell;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import it.naturtalent.e4.office.ui.OfficeUtils;
import it.naturtalent.e4.office.ui.wizards.ODFDefaultWriteAdapterWizard;
import it.naturtalent.icons.core.Icon;
import it.naturtalent.icons.core.IconSize;
import it.naturtalent.office.model.address.Absender;
import it.naturtalent.office.model.address.AddressPackage;
import it.naturtalent.office.model.address.FootNote;
import it.naturtalent.office.model.address.Kontakt;
import it.naturtalent.office.model.address.Referenz;


/**
 *  Den Absender-Detail-Renderer anpassen.
 *  
 *  - filtern nach OfficeContext-Flag, das im Eclipse4 Context unter dem Namen 'OfficeUtils.OFFICE_CONTEXT' hinterlegt sein muss.
 *  - Absender (genauer die Adresse) kann aus der Kontaktdatenbank uebernommen werden. 
 *  - DELETE-Button dauerhaft disablen TpDo: komplett entfernen
 *  
 * @author dieter
 *
 */
public class SenderDetailsRenderer extends MultiReferenceSWTRenderer
{

	private IEventBroker eventBroker;
	
	private Button delButton;
	
	//private EditingDomain domain;
	
	// Liste mit den statischen Absendern (steuert delButton-Status)
	private List<Absender>unremoveableAbsender;

	
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
			if (element instanceof Absender)
			{	
				Absender absender = (Absender)element;				
				String elementContext = ((Absender)element).getContext();
					return(StringUtils.equals(elementContext, officeContext));															
			}
			
			return true;
		}
	}

	
	@Inject
	public SenderDetailsRenderer(VControl vElement,
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
		
		TableViewer tableViewer = getTableViewer();
		tableViewer.addSelectionChangedListener(new ISelectionChangedListener()
		{			
			@Override
			public void selectionChanged(SelectionChangedEvent event)
			{
				IStructuredSelection selection = ((IStructuredSelection) event.getSelection());
				Object selObj = selection.getFirstElement();
				if (selObj instanceof Absender)
				{
					// nur nichtstatische Absender duerfen geloescht werdxen
					if ((unremoveableAbsender != null) && (!unremoveableAbsender.isEmpty()))					
						delButton.setEnabled(!unremoveableAbsender.contains((Absender) selObj));						
				}				
				
				if (selObj != null)
				{
					// den im Detail selektierten Absender melden damit z.B. der Wizard nachziehen kann
					if (eventBroker != null)
						eventBroker.send(OfficeUtils.ABSENDER_DETAIL_SELECTED_EVENT, selObj);					
				}
				
				
				
			}
		});
		
		// Filter in TableViewer auf 'officeContext' setzen
		IEclipseContext context = E4Workbench.getServiceContext();
		String officeContext = (String) context.get(ODFDefaultWriteAdapterWizard.DEFAULT_OFFICECONTEXT);
		tableViewer.setFilters(new ViewerFilter []{new ContextFilter(officeContext)});
		
		// Liste der Nichtloeschbaren aus dem Eclipse4Context holen
		unremoveableAbsender = (List<Absender>) context.get(OfficeUtils.ABSENDER_UNREMOVABLES);
		
		return control;
	}
	
	@Override
	protected Button createDeleteButton(Composite parent,EStructuralFeature structuralFeature)
	{
		delButton = super.createDeleteButton(parent, structuralFeature);		
		return delButton;
	}
	
	/*
	 * ADD-Button auf Datenbankselektion umdekorieren
	 * 
	 */
	@Override
	protected Button createAddExistingButton(Composite parent, EStructuralFeature structuralFeature)
	{
		Button btn = super.createAddExistingButton(parent, structuralFeature);
		btn.setImage(Icon.ICON_DATABASE.getImage(IconSize._16x16_DefaultIconSize));
		btn.setToolTipText("aus Datenbank kopieren");
		return btn;
	}
	

	/* 
	 * Aktion "aus Datenbank kopieren" ausfuehren.
	 * Ein neues Absenderobjekt erzeugen, mit dem DefaultOfficekontext versehen, die Kontaktadresse hinzufuegen und
	 * diesen Absender ueber den Broker melden.
	 */ 
	@Override
	protected void handleAddExisting(TableViewer tableViewer, EObject eObject, EStructuralFeature structuralFeature)
	{				
		eventBroker.post(OfficeUtils.ADD_EXISTING_SENDER , eObject);
	}

	
	@Override
	protected void handleAddNew(TableViewer tableViewer, EObject eObject,
			EStructuralFeature structuralFeature)
	{
		// TODO Auto-generated method stub
		super.handleAddNew(tableViewer, eObject, structuralFeature);
		eventBroker.post(OfficeUtils.ADD_NEWSENDER_EVENT , eObject);
	}

	


}
