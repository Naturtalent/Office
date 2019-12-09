package it.naturtalent.e4.office.ui.dialogs;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.ui.view.ECPRendererException;
import org.eclipse.emf.ecp.ui.view.swt.ECPSWTViewRenderer;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;

import it.naturtalent.e4.office.ui.OfficeUtils;
import it.naturtalent.e4.office.ui.preferences.OfficeDefaultPreferenceUtils;
import it.naturtalent.office.model.address.Absender;
import it.naturtalent.office.model.address.AddressPackage;
import it.naturtalent.office.model.address.Adresse;
import it.naturtalent.office.model.address.Kontakt;

/**
 * Dialog zum Bearbeiten eines Kontaktes
 * 
 * @author dieter
 *
 */
public class KontaktDialog extends Dialog
{
	
	private EditingDomain domain;
	private EReference eReference;
	
	private Kontakt newKontakt;
	
	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public KontaktDialog(Shell parentShell)
	{
		super(parentShell);
	}
	
	/*
	 * Dialogtitel definieren
	 */
	@Override
	protected void configureShell(Shell newShell)
	{	
		super.configureShell(newShell);
		newShell.setText("neuer Kontakt"); //$NON-NLS-N$
	}

	/**
	 * Create contents of the dialog.
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent)
	{
		Composite container = (Composite) super.createDialogArea(parent);
		
		try
		{	
			// einen neuen Kontakt Adresse erzeugen
			EClass kontaktClass = AddressPackage.eINSTANCE.getKontakt();
			newKontakt = (Kontakt) EcoreUtil.create(kontaktClass);
			
			/*
			EClass addressClass = AddressPackage.eINSTANCE.getAdresse();
			Adresse adresse = (Adresse) EcoreUtil.create(addressClass);
			newKontakt.setAdresse(adresse);
			*/
						
			// Absender im Dialog bearbeiten
			ECPSWTViewRenderer.INSTANCE.render(container, newKontakt);			
			
		} catch (ECPRendererException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	
				
		return container;
	}

	/**
	 * Create contents of the button bar.
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent)
	{
		Button okButton = createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,true);
		createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize()
	{
		return new Point(450, 300);
	}

	@Override
	protected void okPressed()
	{
		EObject container = OfficeUtils.findObject(AddressPackage.eINSTANCE.getKontakte());
		domain = AdapterFactoryEditingDomain.getEditingDomainFor(container);	
		eReference = AddressPackage.eINSTANCE.getKontakte_Kontakte();
		Command addCommand = AddCommand.create(domain, container , eReference, newKontakt);
		if(addCommand.canExecute())	
			domain.getCommandStack().execute(addCommand);	
			
		super.okPressed();
	}

	public Kontakt getNewKontakt()
	{
		return newKontakt;
	}
	
	

}
