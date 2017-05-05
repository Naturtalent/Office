package it.naturtalent.e4.project.address.ui;

import javax.inject.Inject;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;

public class ProjectAddressWizard extends Wizard
{

	@Inject @Optional IEclipseContext context;
	
	public ProjectAddressWizard()
	{
		setWindowTitle("New Wizard");
	}

	@Override
	public void addPages()
	{
		addPage((IWizardPage) ContextInjectionFactory.make(ProjectAddressWizardPage.class, context));
	}

	@Override
	public boolean performFinish()
	{
		return true;
	}

}
