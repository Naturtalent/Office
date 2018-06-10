 
package it.naturtalent.e4.office.ui.actions;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.MContribution;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.menu.MToolBarElement;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import it.naturtalent.e4.office.ui.OfficeUtils;
import it.naturtalent.e4.office.ui.part.KontakteView;

import java.util.List;

import org.eclipse.e4.core.di.annotations.CanExecute;

public class UndoKontakteAction
{
	@Execute
	public void execute(EModelService modelService, MPart part)
	{
		EditingDomain domain = AdapterFactoryEditingDomain
				.getEditingDomainFor(OfficeUtils.getOfficeProject());
		
		if (domain != null)
		{
			// undo
			domain.getCommandStack().undo();
			if(!domain.getCommandStack().canUndo())
			{
				// keine weiteren undos - Daten via SaveAction festschreiben
				List<MToolBarElement> items = modelService.findElements(part, KontakteView.SAVE_TOOLBAR_ID, MToolBarElement.class,null, EModelService.IN_PART);
				MToolBarElement toolItem = items.get(0);
				if (toolItem instanceof MContribution)
				{
					MContribution directTool = (MContribution) toolItem;
					Object obj = directTool.getObject();
					if (obj instanceof SaveKontakteAction)
					{
						SaveKontakteAction saveAction = (SaveKontakteAction) obj;
						saveAction.execute(modelService, part);
					}
				}
			}
		}
	}

	@CanExecute
	public boolean canExecute()
	{
		EditingDomain domain = AdapterFactoryEditingDomain.getEditingDomainFor(OfficeUtils.getOfficeProject());
		return domain.getCommandStack().canUndo();	
	}
		
}