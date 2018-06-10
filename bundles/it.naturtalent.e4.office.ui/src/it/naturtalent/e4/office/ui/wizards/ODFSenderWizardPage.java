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
import it.naturtalent.office.model.address.Senders;



/**
 * Wizardseite zur Definition von Empfaengerangaben. 
 * 
 * @author dieter
 *
 */
public class ODFSenderWizardPage extends WizardPage
{

	private Senders senders;
	
	
	/**
	 * Create the wizard.
	 */
	public ODFSenderWizardPage()
	{
		super(ODFDefaultWriteAdapterWizard.SENDER_PAGE_NAME);
		setMessage("einen Absender auswählen");
		setTitle("Absender");
		setDescription("Angaben zum Absender");
		
		EClass sendersClass = AddressPackage.eINSTANCE.getSenders();
		senders = (Senders) EcoreUtil.create(sendersClass);

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
			ECPSWTViewRenderer.INSTANCE.render(container, (EObject) senders);			
		} catch (ECPRendererException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public Senders getSenders()
	{
		return senders;
	}

	public void setSenders(Senders senders)
	{
		this.senders = senders;
	}
	
	

}
