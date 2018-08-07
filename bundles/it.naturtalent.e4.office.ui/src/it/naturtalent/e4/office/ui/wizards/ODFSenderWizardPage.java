package it.naturtalent.e4.office.ui.wizards;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.eclipse.e4.ui.internal.workbench.swt.WorkbenchSWTActivator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.ui.view.ECPRendererException;
import org.eclipse.emf.ecp.ui.view.swt.ECPSWTViewRenderer;
import org.eclipse.jface.dialogs.DialogSettings;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import it.naturtalent.e4.office.ui.OfficeUtils;
import it.naturtalent.office.model.address.Absender;
import it.naturtalent.office.model.address.AddressPackage;
import it.naturtalent.office.model.address.Kontakt;
import it.naturtalent.office.model.address.Senders;



/**
 * Wizardseite zur Definition der Absenderangaben. 
 * 
 * @author dieter
 *
 */
public class ODFSenderWizardPage extends WizardPage
{
	
	// Key DialogSetting der Kontaktnamen
	private static final String SENDERKONTACTNAMES = "senderkontactnames";

	private IDialogSettings dialogSettings = WorkbenchSWTActivator.getDefault()
			.getDialogSettings();
	
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
		initSendersData();
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
	
	/*
	 * Die Absenderdaten werden von den gespeicherten Kontakten abgeleitet, d.h. der
	 * Absender muss als Kontakt gespeichert sein.
	 *  
	 * Im DialogSetting werden diejenigen Kontaktnamen gespeichert, 
	 * die als Absenderadressen verwendet werden.
	 * 
	 * 
	 */
	private void initSendersData()
	{
		String [] kontactNames = dialogSettings.getArray(SENDERKONTACTNAMES);		
		List<Kontakt>kontacts = OfficeUtils.findKontactsByNames(kontactNames);
		for(Kontakt kontakt : kontacts)
		{
			// Absender erzeugen
			EClass absenderClass = AddressPackage.eINSTANCE.getAbsender();
			Absender absender = (Absender) EcoreUtil.create(absenderClass);
			absender.setName(kontakt.getName());
			absender.setAdresse(kontakt.getAdresse());
			senders.getSenders().add(absender);
		}
	}
	
		
	/**
	 * Die Absenderdaten werden als Dialogsettings gespeichert.
	 * 
	 */
	public void storeSenderData()
	{
		List<String>kontactNames = new ArrayList<String>();
		
		List<Absender>allAbsender = senders.getSenders();
		for(Absender absender : allAbsender)
			kontactNames.add(absender.getName());
		
		String[]nameArray = kontactNames.toArray(new String[kontactNames.size()]);
		dialogSettings.put(SENDERKONTACTNAMES, nameArray);
		super.dispose();
	}
	
	
	

}
