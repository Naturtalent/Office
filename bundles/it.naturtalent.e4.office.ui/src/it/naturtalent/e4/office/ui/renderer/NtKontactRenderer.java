package it.naturtalent.e4.office.ui.renderer;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.inject.Inject;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.internal.workbench.E4Workbench;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.workbench.IWorkbench;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.edit.spi.ReferenceService;
import org.eclipse.emf.ecp.spi.common.ui.SelectModelElementWizardFactory;
import org.eclipse.emf.ecp.spi.ui.util.ECPHandlerHelper;
import org.eclipse.emf.ecp.view.internal.control.multireference.MultiReferenceSWTRenderer;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.util.swt.ImageRegistryService;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.core.services.label.EMFFormsLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import it.naturtalent.e4.office.ui.OfficeUtils;
import it.naturtalent.icons.core.Icon;
import it.naturtalent.icons.core.IconSize;
import it.naturtalent.office.model.address.Kontakt;
import it.naturtalent.office.model.address.NtProjektKontakte;

/**
 * Der zur Bearbeitung des NtProjektKontaktes angepasste Renderer. Dieser Renderer ermoeglicht das Einkopieren
 * eines Kontakts aus der Kontakt-Datenbank. Im Vordergrund steht hier der Kontakt mit den Kontaktdaten im
 * Datenbereich. Ist auch eine Adresse definiert, wird diese vom WriteAdapter benutzt und in ein Anschreiben
 * uebernommen.
 * 
 * @author dieter
 *
 */
public class NtKontactRenderer extends MultiReferenceSWTRenderer
{
	//private ESelectionService eSelectionService;	
	private IEventBroker eventBroker;

	private Button btnMoveDown;
	
	private Button btnMoveUp;
	
		
	@Inject
	public NtKontactRenderer(VControl vElement,
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
	
	
	/*
	 * den standardmaessingen 'existButton' ausblenden
	 */
	@Override
	protected boolean showAddExistingButton()
	{
		return false;
	}

	/*
	 * den MoveUp - Button 'zweckentfremdet' einblenden (dient der Auswahl aus der Datenbank) 
	 */
	protected boolean showMoveUpButton() 
	{
		return true;
	}
	
	/*
	 * MoveUp-Button anpassen (DatenbankImage und Tooltip)
	 */
	@Override
	protected Button createMoveUpButton(Composite parent,
			EStructuralFeature structuralFeature)
	{
		btnMoveUp = super.createMoveUpButton(parent, structuralFeature);
		btnMoveUp.setImage(Icon.ICON_DATABASE.getImage(IconSize._16x16_DefaultIconSize));
		btnMoveUp.setToolTipText("Kontakt aus der Datenbank kopieren"); //$NON-NLS-N$
		return btnMoveUp;
	}

	@Override
	protected void handleMoveUp(TableViewer tableViewer, EObject eObject,
			EStructuralFeature structuralFeature)
	{
		final ReferenceService referenceService = getViewModelContext().getService(ReferenceService.class);
		addExistingModelElements(eObject, (EReference) structuralFeature);		
	}
	
	

	@Override
	protected void updateButtonEnabling()
	{		
		super.updateButtonEnabling();
		if(btnMoveUp != null)
			btnMoveUp.setEnabled(true);
	}

	/*
	 * Mit dem ECP-SelectWizard einen Kontakt auswahlen, kopieren und dem Contaimerelemen 'NtProjektKontakte' hinzufuegen.
	 * 
	 */
	private void addExistingModelElements(EObject eObject, EReference eReference)
	{		
		// Kontakte fuer den SelectWizard in einem Set vorbereiten
		EList<Kontakt>allKontacts = OfficeUtils.getKontakte().getKontakte();		
		Set<EObject> elements = new LinkedHashSet<EObject>();
		for(Kontakt kontact : allKontacts)
			elements.add(kontact);
		
		// Kantakt mit Wizard auswaehlen
		final Set<EObject> selectedElements = SelectModelElementWizardFactory
				.openModelElementSelectionDialog(elements, eReference.isMany());
			
		if ((selectedElements != null) && (!selectedElements.isEmpty()))
		{
			for (EObject selectedElement : selectedElements)
			{
				EObject copyelement = EcoreUtil.copy(selectedElement);
				((NtProjektKontakte) eObject).getKontakte().add((Kontakt) copyelement);
			}
			
			ECPHandlerHelper.saveProject(OfficeUtils.getOfficeProject());
			eventBroker.post(OfficeUtils.OFFICEPROJECT_CONTENTSAVEACCOMPLISHED, null);
		}
	}
	
	

	/*
	protected boolean showMoveDownButton() 
	{
		return true;
	}
	
	@Override
	protected Button createMoveDownButton(Composite parent,
			EStructuralFeature structuralFeature)
	{
		btnMoveDown = super.createMoveDownButton(parent, structuralFeature);
		btnMoveDown.setImage(Icon.ICON_DATABASE_ADD.getImage(IconSize._16x16_DefaultIconSize));
		btnMoveDown.setToolTipText("Kontakt in die Datenbank kopieren");
		return btnMoveDown;
	}

	@Override
	protected void handleMoveDown(TableViewer tableViewer, EObject eObject,
			EStructuralFeature structuralFeature)
	{
		System.out.println("Move down");
	}

	*/

	


}
