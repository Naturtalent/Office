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
import org.eclipse.swt.widgets.Display;

import it.naturtalent.e4.office.ui.OfficeUtils;
import it.naturtalent.e4.office.ui.dialogs.KontaktDialog;
import it.naturtalent.icons.core.Icon;
import it.naturtalent.icons.core.IconSize;
import it.naturtalent.office.model.address.Empfaenger;
import it.naturtalent.office.model.address.Kontakt;
import it.naturtalent.office.model.address.Referenz;


/**
 * Angepasster Renderer der den Detailbereich des Containers 'Kontakte' repraesentiert.
 * 
 * 
 * 
 * @author dieter
 *
 */
public class KontakteDetailsRenderer extends MultiReferenceSWTRenderer
{

	private IEventBroker eventBroker;
	
	private Button btnAddNew;
	
	private EditingDomain domain;
	
	@Inject
	public KontakteDetailsRenderer(VControl vElement,
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

	// 'ExistingButton' ausblenden
	@Override
	protected boolean showAddExistingButton()
	{		
		return false;
	}

	@Override
	protected boolean showDeleteButton()
	{		
		return false;
	}
	
	@Override
	protected Button createAddNewButton(Composite parent,
			EStructuralFeature structuralFeature)
	{
		// TODO Auto-generated method stub
		Button addButton = super.createAddNewButton(parent, structuralFeature);
		addButton.setImage(Icon.COMMAND_ADD.getImage(IconSize._16x16_DefaultIconSize));
		addButton.setToolTipText(KontakteMasterDetailRenderer.MenueID.AddKontakID.text);
		return addButton;
	}
	

	@Override
	protected void handleAddNew(TableViewer tableViewer, EObject eObject,
			EStructuralFeature structuralFeature)
	{
		KontaktDialog kontaktDialog = new KontaktDialog(Display.getDefault().getActiveShell());
		if(kontaktDialog.open() == KontaktDialog.OK)
		{
			eventBroker.post(OfficeUtils.KONTACTFILTER_CLEARREQUEST, null);
			Kontakt addedKontakt = kontaktDialog.getNewKontakt();	
			eventBroker.post(OfficeUtils.KONTAKTE_REFRESH_MASTER_REQUEST, null);
			eventBroker.post(OfficeUtils.KONTACTMASTER_SELECTIONREQUEST, addedKontakt);
		}
	}

	
	
}
