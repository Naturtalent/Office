package it.naturtalent.e4.office.ui.dialogs;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;
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
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;

import it.naturtalent.e4.office.ui.OfficeUtils;
import it.naturtalent.e4.office.ui.preferences.OfficeDefaultPreferenceUtils;
import it.naturtalent.office.model.address.Absender;

/**
 * Dialog zum Bearbeiten eines Absenders
 * 
 * Ein Absender ist definiert als eine Adresse erweitert mit einem zusaetzlichen Namen und einem OfficeContext.
 * 
 * Mit dem OfficeContext kann die Adresse einem bestimmten Bereich zugeordnet werden (Business, Privat, ...).
 * Der OfficeContext wird in den Eclips4 Context eigetragen und steuert wiederum den Filter des Renderers.
 * Im Modell sind alle Absender dem Container Sender zugeordnet.
 * 
 * Der Name des Absender korrespondiert mit dem Praeferenznamen in der EditorListe.
 * 
 * @author dieter
 *
 */
public class AbsenderDialog extends Dialog
{

	// der bearbeitete Absender
	private Absender absender;
	
	private Button okButton;
	
	//private Adresse adress;
	
	private static Shell parentShell = Display.getDefault().getActiveShell();
	
	public AbsenderDialog()
	{
		super(parentShell);
	}

	
	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public AbsenderDialog(Shell parentShell)
	{
		super(parentShell);
	}

	/*
	 * Konstruktion mit Absender (
	 */
	public AbsenderDialog(Shell parentShell, Absender absender)
	{
		super(parentShell);		
		this.absender = absender;
	}
	
	/*
	public AbsenderDialog(Shell parentShell, Adresse adress)
	{
		super(parentShell);
		this.adress = adress;
	}
	*/

	/*
	 * Dialogtitel definieren
	 */
	@Override
	protected void configureShell(Shell newShell)
	{	
		super.configureShell(newShell);
		newShell.setText("Absender"); //$NON-NLS-N$
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
			// Absender im Dialog bearbeiten
			ECPSWTViewRenderer.INSTANCE.render(container, absender);			
			
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


	public void setAbsender(Absender absender)
	{
		this.absender = absender;
	}
	
	/*
	 * Ok-Button sperren, wenn Defaultname eingegeben wurde
	 */
	@Inject
	public void handleModify(@UIEventTopic(OfficeUtils.MODIFY_EDITOR_EVENT) @Optional String text, IEclipseContext context)
	{		
		if ((okButton != null) && (!okButton.isDisposed()))
		{
			String defaultName = (String) context.get(OfficeDefaultPreferenceUtils.E4CONTEXT_DEFAULTNAME);
			okButton.setEnabled(!StringUtils.equals(defaultName, text));
		}
	}
	

}
