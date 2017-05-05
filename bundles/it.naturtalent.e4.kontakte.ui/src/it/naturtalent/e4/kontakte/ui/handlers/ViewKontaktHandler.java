 
package it.naturtalent.e4.kontakte.ui.handlers;

import it.naturtalent.e4.kontakte.ui.Activator;

import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.model.application.ui.basic.MStackElement;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;

public class ViewKontaktHandler
{
	
	@Inject EPartService partService;
	
	@Execute
	public void execute(MApplication app, EModelService modelService, EPartService partService)
	{
		
		MPartStack myPartStack = (MPartStack)modelService.find("it.naturtalent.e4.project.ui.partstack.1", app);
		
		
		MPart mPart = partService.createPart("it.naturtalent.e4.kontakte.ui.partdescriptor.0");
		
		String name = Activator.properties.getProperty("KontaktPartDescriptor");
		
		List<MStackElement>stackElements = myPartStack.getChildren();
		
		for(MStackElement mStackElement : stackElements)
		{
			System.out.println("it.naturtalent.e4.kontakte.ui.handlers.ViewKontaktHaandler: "+mStackElement.getElementId());
			
			if(StringUtils.equals(mStackElement.getElementId(),"it.naturtalent.e4.kontakte.ui.partdescriptor.0"))
				return;
		}
						
		myPartStack.getChildren().add(mPart);
		
		
		//MPart mPart = partService.findPart(KontakteView.KONTAKTE_VIEW_ID);
		/*
		MPart mPart = partService.findPart(ContactView.CONTACT_VIEW_ID);
		Object obj = mPart.getObject();
		mPart.setVisible(true);	
		partService.activate(mPart);
		*/
		
	}	
	
}