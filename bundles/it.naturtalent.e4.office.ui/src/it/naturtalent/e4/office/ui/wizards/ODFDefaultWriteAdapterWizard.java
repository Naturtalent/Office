package it.naturtalent.e4.office.ui.wizards;

import org.eclipse.jface.wizard.Wizard;

public class ODFDefaultWriteAdapterWizard extends Wizard
{
	
	

	@Override
	public void addPages()
	{
		addPage(new ODFSenderWizardPage());
	}

	@Override
	public boolean performFinish()
	{
		// TODO Auto-generated method stub
		return true;
	}

}
