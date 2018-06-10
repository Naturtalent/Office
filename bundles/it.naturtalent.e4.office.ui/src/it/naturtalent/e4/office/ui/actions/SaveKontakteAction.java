 
package it.naturtalent.e4.office.ui.actions;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.menu.MToolItem;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.emf.ecp.spi.ui.util.ECPHandlerHelper;

import it.naturtalent.e4.office.ui.OfficeUtils;
import it.naturtalent.e4.office.ui.part.KontakteView;

import java.util.List;

import org.eclipse.e4.core.di.annotations.CanExecute;

public class SaveKontakteAction
{
	@Execute
	public void execute(EModelService modelService, MPart part)
	{
		MToolItem item;
		List<MToolItem> items;
		
		//OfficeUtils.getOfficeProject().saveContents();
		ECPHandlerHelper.saveProject(OfficeUtils.getOfficeProject());

		// ToolbarStatus triggern  
		items = modelService.findElements(part, KontakteView.SAVE_TOOLBAR_ID, MToolItem.class,null, EModelService.IN_PART);
		item = items.get(0);
		item.setEnabled(false);

		items = modelService.findElements(part, KontakteView.UNDO_TOOLBAR_ID, MToolItem.class,null, EModelService.IN_PART);
		item = items.get(0);
		item.setEnabled(false);


	}

	@CanExecute
	public boolean canExecute()
	{
		return OfficeUtils.getOfficeProject().hasDirtyContents();
	}
		
}