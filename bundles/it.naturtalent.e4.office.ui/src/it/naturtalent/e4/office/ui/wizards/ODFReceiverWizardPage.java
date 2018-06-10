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

import it.naturtalent.office.model.address.AddressPackage;
import it.naturtalent.office.model.address.Receivers;
import it.naturtalent.office.model.address.Senders;



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
		
		EClass receiversClass = AddressPackage.eINSTANCE.getReceivers();
		receivers = (Receivers) EcoreUtil.create(receiversClass);
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
