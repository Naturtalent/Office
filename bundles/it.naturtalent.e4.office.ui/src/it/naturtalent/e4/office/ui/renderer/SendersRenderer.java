package it.naturtalent.e4.office.ui.renderer;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.internal.workbench.E4Workbench;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.treemasterdetail.ui.swt.TreeMasterDetailSWTRenderer;
import org.eclipse.emf.ecp.view.treemasterdetail.model.VTreeMasterDetail;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.widgets.Composite;

import it.naturtalent.e4.office.ui.OfficeUtils;
import it.naturtalent.office.model.address.Absender;
import it.naturtalent.office.model.address.Adresse;



/**
 * MasterViewRenderer des Absender Objekts.
 *  
 * 
 * @author dieter
 *
 */
public class SendersRenderer extends TreeMasterDetailSWTRenderer
{

	@Inject private IEventBroker eventBroker;
	
	private TreeViewer treeViewer;
	
	// filtert nach dem TelekomOffice - Context
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
				String elementContext = ((Absender)element).getContext();
					return(StringUtils.equals(elementContext, officeContext));															
			}
			
			return true;
		}
	}
	
	// Listener informiert ueber den selektierten Absender im MasterTree
	private ISelectionChangedListener selectionListener = new ISelectionChangedListener()
	{		
		@Override
		public void selectionChanged(SelectionChangedEvent event)
		{
			Absender absender = null;
			IStructuredSelection selection = event.getStructuredSelection();
			Object selObject = selection.getFirstElement();
			if (selObject instanceof Absender)
				absender = (Absender) selObject;
			else
			{
				if (selObject instanceof Adresse)									
					absender = (Absender) ((Adresse) selObject).eContainer();				
			}
			
			// Broker informiert ueber die Selektion 
			// @see it.naturtalent.e4.office.ui.wizards.ODFSenderWizardPage
			eventBroker.post(OfficeUtils.ABSENDERMASTER_SELECTED_EVENT , absender);
		}
	};
	
	/**
	 * Konstruktion
	 * 
	 * @param vElement
	 * @param viewContext
	 * @param reportService
	 */
	@Inject
	public SendersRenderer(VTreeMasterDetail vElement,
			ViewModelContext viewContext, ReportService reportService)
	{
		super(vElement, viewContext, reportService);	
	}
	
	/* 
	 * Methodenueberschreibung um Zugriff auf den TreeViewer zu bekommen
	 * @see org.eclipse.emf.ecp.view.spi.treemasterdetail.ui.swt.TreeMasterDetailSWTRenderer#createMasterTree(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected TreeViewer createMasterTree(Composite masterPanel)
	{
		IEclipseContext context = E4Workbench.getServiceContext();
		String officeContext = (String) context.get(OfficeUtils.OFFICE_CONTEXT);		
		
		treeViewer = super.createMasterTree(masterPanel);
		treeViewer.addSelectionChangedListener(selectionListener);	
		treeViewer.setFilters(new ViewerFilter []{new ContextFilter(officeContext)});
		return treeViewer;
	}
	
	@Inject
	@Optional
	public void handleModelChangedEvent(@UIEventTopic(OfficeUtils.SET_ABSENDERMASTER_SELECTION_EVENT) Absender absender)
	{
		//treeViewer.removeSelectionChangedListener(selectionListener);
		treeViewer.setSelection(new StructuredSelection(absender));
		//treeViewer.addSelectionChangedListener(selectionListener);
	}

/*
	@Override
	protected Button createAddExistingButton(Composite parent,
			EStructuralFeature structuralFeature)
	{
		Button btn = super.createAddExistingButton(parent, structuralFeature);
		btn.setImage(Icon.ICON_DATABASE_GET.getImage(IconSize._16x16_DefaultIconSize));
		btn.setToolTipText("aus Datenbank kopieren");
		return btn;
	}
*/

/*
	@Override
	protected void handleAddExisting(TableViewer tableViewer, EObject eObject,
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
				((Sender) eObject).getSenders().add(absender);
			}
		}
	}


	@Override
	protected Button createAddNewButton(Composite parent,
			EStructuralFeature structuralFeature)
	{
		Button btn = super.createAddNewButton(parent, structuralFeature);
		//btn.setImage(Icon.ICON_DATABASE_GET.getImage(IconSize._16x16_DefaultIconSize));
		btn.setToolTipText("einen neuen Absender hinzufuegen");
		return btn;
	}
*/
	
}
