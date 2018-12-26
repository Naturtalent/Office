package it.naturtalent.e4.office.ui.renderer;

import javax.inject.Inject;

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
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.core.services.label.EMFFormsLabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import it.naturtalent.e4.office.ui.OfficeUtils;
import it.naturtalent.icons.core.Icon;
import it.naturtalent.icons.core.IconSize;
import it.naturtalent.office.model.address.Referenz;


public class ReceiverDetailsRenderer extends MultiReferenceSWTRenderer
{

	private IEventBroker eventBroker;
	
	private Button btnAddNew;
	
	private EditingDomain domain;
	
	@Inject
	public ReceiverDetailsRenderer(VControl vElement,
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
	
	@Inject 
	@Optional
	public void handleReferenceSelection(@UIEventTopic(OfficeUtils.REFERENZSET_REQUESTSELECTREFERENCEEVENT) Object reference )
	{
		if (reference instanceof Referenz)		
			getTableViewer().setSelection(new StructuredSelection(reference));
	}

	/* 
	 * Der 'AddExistingButton' soll die Uebernahme einer Adresse aus der Kontaktdatenbank triggern.
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
	 * Aktion 'AddExistingButton'  wird gemeldet und vom Empfaenger der Events ausgefuehrt.
	 * 
	 */
	@Override
	protected void handleAddExisting(TableViewer tableViewer, EObject eObject,
			EStructuralFeature structuralFeature)
	{ 
		eventBroker.post(OfficeUtils.ADD_EXISTING_RECEIVER , eObject);
	}
	
}
