package it.naturtalent.e4.office.ui.wizards;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.ui.view.ECPRendererException;
import org.eclipse.emf.ecp.ui.view.swt.ECPSWTViewRenderer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import it.naturtalent.office.model.address.Absender;
import it.naturtalent.office.model.address.AbsenderRoot;
import it.naturtalent.office.model.address.AddressPackage;
import it.naturtalent.office.model.address.Adresse;
import it.naturtalent.office.model.address.Senders;



public class ODFSenderWizardPage extends WizardPage
{

	/**
	 * Create the wizard.
	 */
	public ODFSenderWizardPage()
	{
		super("wizardPage");
		setMessage("einen Absender auswählen");
		setTitle("Absender");
		setDescription("Angaben zum Absender");
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
		
		EClass sendersClass = AddressPackage.eINSTANCE.getSenders();
		Senders senders = (Senders) EcoreUtil.create(sendersClass);
		
		try
		{		
			ECPSWTViewRenderer.INSTANCE.render(container, (EObject) senders);			
		} catch (ECPRendererException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
	}

}
