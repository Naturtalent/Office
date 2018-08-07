package it.naturtalent.e4.office.ui.wizards;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.ui.view.ECPRendererException;
import org.eclipse.emf.ecp.ui.view.swt.ECPSWTViewRenderer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import it.naturtalent.e4.office.ui.OfficeUtils;
import it.naturtalent.e4.project.IResourceNavigator;
import it.naturtalent.office.model.address.AddressPackage;
import it.naturtalent.office.model.address.Empfaenger;
import it.naturtalent.office.model.address.Kontakt;
import it.naturtalent.office.model.address.NtProjektKontakte;
import it.naturtalent.office.model.address.Receivers;



/**
 * Wizardseite zur Definition von Empfaengerangaben. 
 * 
 * @author dieter
 *
 */
public class ODFReceiverWizardPage extends WizardPage
{
	private Receivers receivers;

	/**
	 * Create the wizard.
	 */
	public ODFReceiverWizardPage()
	{
		super(ODFDefaultWriteAdapterWizard.RECEIVER_PAGE_NAME);
		setMessage("einen Empfaenger auswählen");
		setTitle("Empfänger");
		setDescription("Angaben zum Empfänger");
		
		// Eine Receiverklasse anlegen
		EClass receiversClass = AddressPackage.eINSTANCE.getReceivers();
		receivers = (Receivers) EcoreUtil.create(receiversClass);
		
		IResourceNavigator resourceNavigator = it.naturtalent.e4.project.ui.Activator.findNavigator();
		TreeViewer treeViewer = resourceNavigator.getViewer();
		IStructuredSelection selection = treeViewer.getStructuredSelection();
		Object selObject = selection.getFirstElement();
		if (selObject instanceof IResource)
		{
			IResource resource = (IResource) selObject;
			IProject iProject = resource.getProject();
			NtProjektKontakte kontakte = OfficeUtils.getProjectKontacts(iProject.getName());
			EList<Kontakt>kontacts = kontakte.getKontakte();
			if((kontacts != null) && (!kontacts.isEmpty()))
			{
				for(Kontakt kontact : kontacts)
				{
					EClass empfaengerClass = AddressPackage.eINSTANCE.getEmpfaenger();
					Empfaenger empfaenger = (Empfaenger) EcoreUtil.create(empfaengerClass);
					empfaenger.setName(kontact.getName());
					empfaenger.setAdresse(EcoreUtil.copy(kontact.getAdresse()));
					receivers.getReceivers().add(empfaenger);
				}
			}
			
		}
	}

	/**
	 * Create contents of the wizard.
	 * @param parent
	 */
	public void createControl(Composite parent)
	{
		Composite container = new Composite(parent, SWT.NULL);

		setControl(container);
		container.setLayout(new GridLayout(2, false));
		
		try
		{		
			ECPSWTViewRenderer.INSTANCE.render(container, (EObject) receivers);			
		} catch (ECPRendererException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	
	}

	public Receivers getReceivers()
	{
		return receivers;
	}

	public void setReceivers(Receivers receivers)
	{
		this.receivers = receivers;
	}
	
	

}
