package it.naturtalent.e4.office.ui.actions;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.internal.workbench.E4Workbench;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;

import it.naturtalent.e4.office.ui.KontakteProjectProperty;
import it.naturtalent.e4.office.ui.KontakteProjectPropertyWizardPage;

/**
 * Die Aktion generiert einen DialogWizard mit der WizardPage 
 * KontakteProjectPropertyWizardPage
 * 
 * Diese Aktion wird ueber als dyn. Eigenschaft im ProjectView angezeigt.
 * @see 'KontakteProjectProperty'
 * 
 * @author dieter
 *
 */
public class ProjectKontakteAction extends Action
{
	
	private KontakteProjectProperty kontactsProjectProperty;
	
	private KontakteProjectPropertyWizardPage ntProjektKontakteWizardPage;
	
	/**
	 * Wizard nur fuer diesen Dialog
	 * 
	 * @author dieter
	 *
	 */
	private class ProjectKontakteWizard extends Wizard
	{

		@Override
		public void addPages()
		{			
			addPage(ntProjektKontakteWizardPage);
		}

		@Override
		public boolean performFinish()
		{
			// TODO Auto-generated method stub
			return false;
		}

	}
	
	public ProjectKontakteAction()
	{
		IEclipseContext context = E4Workbench.getServiceContext();	
		ntProjektKontakteWizardPage = ContextInjectionFactory
				.make(KontakteProjectPropertyWizardPage.class, context);
	}

	public ProjectKontakteAction(String text, ImageDescriptor image)
	{
		super(text, image);
		// TODO Auto-generated constructor stub
	}

	public ProjectKontakteAction(String text, int style)
	{
		super(text, style);
		// TODO Auto-generated constructor stub
	}

	public ProjectKontakteAction(String text)
	{
		super(text);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run()
	{	
		WizardDialog dialog = new WizardDialog(
				Display.getDefault().getActiveShell(), new ProjectKontakteWizard()); 
				
		if(dialog.open() == WizardDialog.OK)
		{
			
		}
		
		System.out.println("KontaktAction");
		super.run();
	}

	public void setKontactsProjectProperty(
			KontakteProjectProperty kontactsProjectProperty)
	{
		ntProjektKontakteWizardPage.setKontakteProjectProperty(kontactsProjectProperty);		
	}
	
	

	
}
