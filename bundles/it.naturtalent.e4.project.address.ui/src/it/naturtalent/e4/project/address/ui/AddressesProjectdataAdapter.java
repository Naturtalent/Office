package it.naturtalent.e4.project.address.ui;

import it.naturtalent.e4.project.AbstractProjectDataAdapter;
import it.naturtalent.e4.project.address.AddressData;
import it.naturtalent.e4.project.address.Addresses;

import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.widgets.Display;

public class AddressesProjectdataAdapter extends AbstractProjectDataAdapter
{
	
	@Inject @Optional IEclipseContext context;
	
	
	
	@Override
	public String getId()
	{		
		return Addresses.CLASS_ADDRESSES;
	}
	
	

	@Override
	public String getName()
	{
		return "Adressangaben"; //$NON-NLS-1$
	}



	@Override
	public Class<?> getProjectDataClass()
	{
		return Addresses.class;
	}

	@Override
	public WizardPage getWizardPage()
	{		
		return new ProjectAddressWizardPage();
	}

	@Override
	public void setProjectData(Object data)
	{	
		data = (data == null) ? new Addresses() : data;
		super.setProjectData(data);
	}
	
	@Override
	public Action getAction(final IEclipseContext context)
	{
		Action action = new Action()
		{
			@Override
			public void run()
			{
				ProjectAddressWizard wizard = ContextInjectionFactory.make(ProjectAddressWizard.class, context);				
				WizardDialog dialog = new WizardDialog(Display.getDefault().getActiveShell(), wizard);
				dialog.open();
			}
		};
		
		return action;
	}

	
	@Override
	public String [] toText(Object projectData)
	{	
		if (projectData instanceof Addresses)
		{
			List<AddressData>addresses = ((Addresses)projectData).getAdressDatas();
			if(!addresses.isEmpty())
			{
				String [] txtArray = {"Adresse"};
				String token;		
				AddressData addressData = addresses.get(0);
				
				token = addressData.getName();
				if(StringUtils.isNotEmpty(token))
					txtArray = ArrayUtils.add(txtArray, token);

				token = addressData.getNamezusatz1();
				if(StringUtils.isNotEmpty(token))
					txtArray = ArrayUtils.add(txtArray, token);
				
				token = addressData.getStrasse();
				if(StringUtils.isNotEmpty(token))
					txtArray = ArrayUtils.add(txtArray, token);
				
				token = addressData.getPlz();
				if(StringUtils.isNotEmpty(token))
					txtArray = ArrayUtils.add(txtArray, token);
				
				token = addressData.getOrt();
				if(StringUtils.isNotEmpty(token))
					txtArray = ArrayUtils.add(txtArray, token);
				
				return txtArray;
			}
		}
		return null;
	}


}
