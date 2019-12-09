package it.naturtalent.e4.office.ui.dialogs;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.ui.view.ECPRendererException;
import org.eclipse.emf.ecp.ui.view.swt.ECPSWTViewRenderer;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import it.naturtalent.office.model.address.AddressPackage;
import it.naturtalent.office.model.address.Adresse;
import it.naturtalent.office.model.address.Kontakt;

/**
 * Dialog zum Bearbeiten einer Adresse
 * 
 * @author dieter
 *
 */
public class AdresseDialog extends Dialog
{

	// der bearbeitete Absender
	private Adresse newAdresse;
	
	private Button okButton;
	
	private Kontakt parentKontakt;
	
	private static Shell parentShell = Display.getDefault().getActiveShell();
	
	public AdresseDialog()
	{
		super(parentShell);
	}
	
	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public AdresseDialog(Shell parentShell)
	{
		super(parentShell);
	}
	
	public AdresseDialog(Shell parentShell, Kontakt parentKontakt)
	{
		super(parentShell);
		this.parentKontakt = parentKontakt;
	}

	/*
	 * Dialogtitel definieren
	 */
	@Override
	protected void configureShell(Shell newShell)
	{	
		super.configureShell(newShell);
		newShell.setText("Adresse"); //$NON-NLS-N$
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
			// einen neue Adresse erzeugen
			EClass adresseClass = AddressPackage.eINSTANCE.getAdresse();
			newAdresse = (Adresse) EcoreUtil.create(adresseClass);
			
			if(parentKontakt != null)
				newAdresse.setName(parentKontakt.getName());
							
			// Absender im Dialog bearbeiten
			ECPSWTViewRenderer.INSTANCE.render(container, newAdresse);			
			
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
		okButton = createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize()
	{
		return new Point(450, 300);
	}

	public Adresse getNewAdresse()
	{
		return newAdresse;
	}
	
	
	
}
