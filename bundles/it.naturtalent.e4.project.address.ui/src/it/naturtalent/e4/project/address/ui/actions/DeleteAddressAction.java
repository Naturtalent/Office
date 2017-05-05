package it.naturtalent.e4.project.address.ui.actions;

import it.naturtalent.e4.project.address.AddressData;
import it.naturtalent.e4.project.address.Addresses;
import it.naturtalent.e4.project.address.ui.ProjectAddressWizardPage;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.graphics.Image;
import org.eclipse.wb.swt.SWTResourceManager;

public class DeleteAddressAction extends Action 
{

	@Inject @Optional private ProjectAddressWizardPage addressWizardPage;
	
	//private ProjectAddressWizardPage addressWizardPage;

	public DeleteAddressAction()
	{
		super();
		
		Image image = SWTResourceManager.getImage(ProjectAddressWizardPage.class, "/icons/full/obj16/delete_obj.gif");
		setImageDescriptor(ImageDescriptor.createFromImage(image));
	}

	/*
	public DeleteAddressAction(ProjectAddressWizardPage addressWizardPage)
	{
		super();
		this.addressWizardPage = addressWizardPage;
		
		Image image = SWTResourceManager.getImage(ProjectAddressWizardPage.class, "/icons/full/obj16/delete_obj.gif");
		setImageDescriptor(ImageDescriptor.createFromImage(image));

	}
	*/
	
	@Override
	public void run()
	{
		IStructuredSelection selection = (IStructuredSelection) addressWizardPage
				.getTableViewer().getSelection();
		if (!selection.isEmpty())
		{
			AddressData addressData = (AddressData) selection
					.getFirstElement();

			// den selektierten Datensatz aus dem Modell entfernen
			Addresses addressDatas = addressWizardPage.getAddresses();
			addressDatas.getAdressDatas().remove(addressData);			
			addressWizardPage.setAddresses(addressDatas);
			
			addressWizardPage.getTableViewer().setSelection(new StructuredSelection());
			
			addressWizardPage.updateWidgets();
		}
	
	}

	
}
