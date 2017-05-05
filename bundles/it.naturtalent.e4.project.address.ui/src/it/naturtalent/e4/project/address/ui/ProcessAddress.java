package it.naturtalent.e4.project.address.ui;

import it.naturtalent.e4.project.IProjectDataFactory;
import it.naturtalent.e4.project.ProjectDataAdapterRegistry;
import it.naturtalent.e4.project.address.Activator;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Execute;

public class ProcessAddress
{
	@Execute
	public void init(IEclipseContext context, IProjectDataFactory projectDataFactory)
	{
		//ProjectDataAdapterRegistry registry = ContextInjectionFactory.make(ProjectDataAdapterRegistry.class, context);
		//AddressesProjectdataAdapter addressAdaper = new AddressesProjectdataAdapter();
		AddressesProjectdataAdapter addressAdaper = ContextInjectionFactory.make(AddressesProjectdataAdapter.class, context);
		addressAdaper.setProjectDataFactory(projectDataFactory);
		ProjectDataAdapterRegistry.addAdapter(addressAdaper);
		//Activator.registry = registry;
	}
}
