package it.naturtalent.e4.office.ui.renderer;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.inject.Inject;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.internal.workbench.E4Workbench;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.workbench.IWorkbench;
import org.eclipse.emf.common.notify.impl.BasicNotifierImpl.EObservableAdapterList.Listener;
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
import org.eclipse.emf.ecp.view.spi.util.swt.ImageRegistryService;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.core.services.label.EMFFormsLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import it.naturtalent.e4.office.ui.OfficeUtils;
import it.naturtalent.icons.core.Icon;
import it.naturtalent.icons.core.IconSize;
import it.naturtalent.office.model.address.Absender;
import it.naturtalent.office.model.address.AddressPackage;
import it.naturtalent.office.model.address.Kontakt;
import it.naturtalent.office.model.address.NtProjektKontakte;
import it.naturtalent.office.model.address.Senders;

public class SendersRenderer extends MultiReferenceSWTRenderer
{

	private IEventBroker eventBroker;
	
	private Button btnAddNew;
	
	@Inject
	public SendersRenderer(VControl vElement,
			ViewModelContext viewContext, ReportService reportService,
			EMFFormsDatabinding emfFormsDatabinding,
			EMFFormsLabelProvider emfFormsLabelProvider,
			VTViewTemplateProvider vtViewTemplateProvider,
			ImageRegistryService imageRegistryService)
	{
		super(vElement, viewContext, reportService, emfFormsDatabinding,
				emfFormsLabelProvider, vtViewTemplateProvider, imageRegistryService);
		
		MApplication currentApplication = E4Workbench.getServiceContext().get(IWorkbench.class).getApplication();
		//eSelectionService = currentApplication.getContext().get(ESelectionService.class);
		eventBroker = currentApplication.getContext().get(IEventBroker.class);
	}
	
	@Override
	protected boolean showAddExistingButton()
	{
		return false;
	}

	/*
	 * Die Senderdaten muessen als Kontakt definiert. 
	 * Mit dieser Funktion wird der Kontakt selektiert und die Daten uebernommen.
	 * 
	 */
	@Override
	protected void handleAddNew(TableViewer tableViewer, EObject eObject,
			EStructuralFeature structuralFeature)
	{
		EList<Kontakt>allKontacts = OfficeUtils.getKontakte().getKontakte();
		
		Set<EObject> elements = new LinkedHashSet<EObject>();
		for(Kontakt kontact : allKontacts)
			elements.add(kontact);
		
		final Set<EObject> selectedElements = SelectModelElementWizardFactory
				.openModelElementSelectionDialog(elements, true);
		
		if ((selectedElements != null) && (!selectedElements.isEmpty()))
		{
			for (EObject selectedElement : selectedElements)
			{
				// der selektierte Kontakt
				Kontakt kontakt = (Kontakt) EcoreUtil.copy(selectedElement);
				
				// Absender erzeugen
				EClass absenderClass = AddressPackage.eINSTANCE.getAbsender();
				Absender absender = (Absender) EcoreUtil.create(absenderClass);
				absender.setName(kontakt.getName());
				absender.setAdresse(kontakt.getAdresse());				
				((Senders) eObject).getSenders().add(absender);
			}
		}
	}
	
}
