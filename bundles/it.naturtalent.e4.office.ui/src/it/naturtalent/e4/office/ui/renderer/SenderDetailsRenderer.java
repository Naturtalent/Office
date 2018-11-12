package it.naturtalent.e4.office.ui.renderer;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.internal.workbench.E4Workbench;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.workbench.IWorkbench;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.spi.common.ui.SelectModelElementWizardFactory;
import org.eclipse.emf.ecp.view.internal.control.multireference.MultiReferenceSWTRenderer;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emf.ecp.view.spi.util.swt.ImageRegistryService;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.core.services.label.EMFFormsLabelProvider;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridCell;
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
import it.naturtalent.icons.core.Icon;
import it.naturtalent.icons.core.IconSize;
import it.naturtalent.office.model.address.Absender;
import it.naturtalent.office.model.address.AddressPackage;
import it.naturtalent.office.model.address.Kontakt;
import it.naturtalent.office.model.address.Referenz;
import it.naturtalent.office.model.address.Sender;


public class SenderDetailsRenderer extends MultiReferenceSWTRenderer
{

	private IEventBroker eventBroker;
	
	private Button btnAddNew;
	
	private EditingDomain domain;
	
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
				System.out.println("Filter: "+(officeContext+"   "+((Absender)element).getContext()));
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
				if (selObj != null)
				{
					if (eventBroker != null)
						eventBroker.send(OfficeUtils.ABSENDER_DETAIL_SELECTED_EVENT, selObj);					
				}
			}
		});
		
		// Filter in TableViewer auf 'officeContext' setzen
		IEclipseContext context = E4Workbench.getServiceContext();
		String officeContext = (String) context.get(OfficeUtils.OFFICE_CONTEXT);
		tableViewer.setFilters(new ViewerFilter []{new ContextFilter(officeContext)});
		
		return control;
	}
	
	@Inject 
	@Optional
	public void handleReferenceSelection(@UIEventTopic(OfficeUtils.REFERENZGROUP_REQUESTSELECTREFERENCEEVENT) Object reference )
	{
		if (reference instanceof Referenz)		
			getTableViewer().setSelection(new StructuredSelection(reference));
	}

	@Override
	protected Button createAddExistingButton(Composite parent, EStructuralFeature structuralFeature)
	{
		Button btn = super.createAddExistingButton(parent, structuralFeature);
		btn.setImage(Icon.ICON_DATABASE_GET.getImage(IconSize._16x16_DefaultIconSize));
		btn.setToolTipText("aus Datenbank kopieren");
		return btn;
	}
	

	/* 
	 * Aktion "aus Datenbank kopieren" ausfuehren
	 */
	@Override
	protected void handleAddExisting(TableViewer tableViewer, EObject eObject,
			EStructuralFeature structuralFeature)
	{
		EList<Kontakt>allKontacts = OfficeUtils.getKontakte().getKontakte();
		
		Set<EObject> elements = new LinkedHashSet<EObject>();
		for(Kontakt kontact : allKontacts)
			elements.add(kontact);
		
		// Kontakt mit dem Dialog auswaehlen
		final Set<EObject> selectedElements = SelectModelElementWizardFactory
				.openModelElementSelectionDialog(elements, true);
		
		if ((selectedElements != null) && (!selectedElements.isEmpty()))
		{
			for (EObject selectedElement : selectedElements)
			{
				// der selektierte Kontakt (incl. Adresse) wird kopiert
				Kontakt kontakt = (Kontakt) EcoreUtil.copy(selectedElement);
				EClass absenderClass = AddressPackage.eINSTANCE.getAbsender();
				Absender absender = (Absender) EcoreUtil.create(absenderClass);
				absender.setName(kontakt.getName());
				absender.setAdresse(kontakt.getAdresse());	
				
				Sender sender = (Sender) eObject;
				//EObject container = ((Sender) eObject).getSenders();
				//EObject container = OfficeUtils.getReferenzClass(senders);				
				domain = AdapterFactoryEditingDomain.getEditingDomainFor(sender);			
				
				EReference eReference = AddressPackage.eINSTANCE.getSender_Senders();
				Command addCommand = AddCommand.create(domain, sender , eReference, absender);
				if(addCommand.canExecute())
					domain.getCommandStack().execute(addCommand);
				
				
				
				//((Sender) eObject).getSenders().add(absender);
				
				eventBroker.post(OfficeUtils.SET_ABSENDERMASTER_SELECTION_EVENT , absender);
				
				//tableViewer.setSelection(new StructuredSelection(absender));
			}
		}
	}


	
}
