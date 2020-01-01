package it.naturtalent.e4.office.ui.renderer;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.inject.Inject;

import org.eclipse.e4.core.services.events.IEventBroker;
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
import org.eclipse.emf.ecp.view.spi.util.swt.ImageRegistryService;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.core.services.label.EMFFormsLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import it.naturtalent.e4.office.ui.OfficeUtils;
import it.naturtalent.icons.core.Icon;
import it.naturtalent.icons.core.IconSize;
import it.naturtalent.office.model.address.AddressPackage;
import it.naturtalent.office.model.address.Empfaenger;
import it.naturtalent.office.model.address.Kontakt;


/**
 * Ein modifizierter Renderer fuer die Detailsicht des Empfaengers.
 * Angepasst wurde die 'handleAddExisting()' - Funktion mit Moeglichkeit eine Empfaengeradresse aus
 * der Kontaktdatenbank auszuwaehlen.
 * 
 * @author dieter
 *
 */
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
	 * Aktion 'AddExistingButton'  EventBroker meldet die Execution der Add - Aktion
	 * Auswahl aus der Kontaktdatenbank
	 */
	@Override
	protected void handleAddExisting(TableViewer tableViewer, EObject eObject, EStructuralFeature structuralFeature)
	{ 
		// Kontakte aus der Datenbank fuer den SelectWizard in einem Set vorbereiten
		EList<Kontakt>allKontacts = OfficeUtils.getKontakte().getKontakte();		
		Set<EObject> elements = new LinkedHashSet<EObject>();
		for(Kontakt kontact : allKontacts)
			elements.add(kontact);
		
		// Kontakt(e) mit Wizard auswaehlen
		final Set<EObject> selectedElements = SelectModelElementWizardFactory
				.openModelElementSelectionDialog(elements, structuralFeature.isMany());
		
		// Command 'add' vorbereiten
		EObject container = eObject;
		EditingDomain domain = AdapterFactoryEditingDomain.getEditingDomainFor(container);
		EReference eReference = AddressPackage.eINSTANCE.getReceivers_Receivers();
		
		if ((selectedElements != null) && (!selectedElements.isEmpty()))
		{
			for (EObject selectedElement : selectedElements)
			{
				Kontakt kontakt = (Kontakt) EcoreUtil.copy(selectedElement);
				
				// Empfaengerobjekt erzeugen und Kontakt hineinkopieren
				EClass empfaengerClass = AddressPackage.eINSTANCE.getEmpfaenger();
				Empfaenger empfaenger = (Empfaenger) EcoreUtil.create(empfaengerClass);
				empfaenger.setName(kontakt.getName());
				empfaenger.setAdresse(kontakt.getAdresse());
								
				Command addCommand = AddCommand.create(domain, eObject , eReference, empfaenger);
				if(addCommand.canExecute())
					domain.getCommandStack().execute(addCommand);					
				
				eventBroker.post(OfficeUtils.SET_OFFICEMASTER_SELECTION_EVENT, empfaenger);
			}
			
			//ECPHandlerHelper.saveProject(OfficeUtils.getOfficeProject());
			//eventBroker.post(OfficeUtils.OFFICEPROJECT_CONTENTSAVEACCOMPLISHED, null);
		}
	}
	
}
