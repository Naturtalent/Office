package it.naturtalent.e4.project.address.ui.actions;

import it.naturtalent.e4.project.address.AddressData;
import it.naturtalent.e4.project.address.Addresses;
import it.naturtalent.e4.project.address.ui.Messages;
import it.naturtalent.e4.project.address.ui.ProjectAddressWizardPage;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.graphics.Image;
import org.eclipse.wb.swt.SWTResourceManager;

public class AddAddressAction extends Action
{

	@Inject @Optional private ProjectAddressWizardPage addressWizardPage;
	
	public AddAddressAction()
	{
		super();
		
		Image image = SWTResourceManager.getImage(ProjectAddressWizardPage.class, "/icons/full/obj16/add_obj.gif");
		setImageDescriptor(ImageDescriptor.createFromImage(image));
	}

	@Override
	public void run()
	{		
		// Validator fuer InputDialog
		IInputValidator validator = new IInputValidator()
		{
			public String isValid(String string)
			{
				if (StringUtils.isEmpty(string))						
					return Messages.CustomerPage_InputDialog_UndefinedNameError;
				
				return null;
			}
		};
		
		// als neuen Datenatz hinzufuegen mit Dialog bestaetigen 
		InputDialog dialog = new InputDialog(
				addressWizardPage.getShell(),
				Messages.CustomerPage_InputDialog_AddressTitle,
				Messages.CustomerPage_InputDialog_Message, null,validator);
		dialog.setBlockOnOpen(true);
		if (dialog.open() == Window.OK)
		{
			// einen neuen Datensatz mit dem eingegebenen Namen erzeugen
			AddressData newAddressData = new AddressData();			
			newAddressData.setName(dialog.getValue());
			
			// den neuen Datensatz im Modell hinzufuegen
			Addresses addressDatas = addressWizardPage.getAddresses();
			addressDatas.getAdressDatas().add(newAddressData);			
			addressWizardPage.setAddresses(addressDatas); 
			
			// in der Tabelle selektieren
			addressWizardPage.getTableViewer().setSelection(new StructuredSelection(newAddressData));
			addressWizardPage.updateWidgets();
		}
	}

	
	
}
