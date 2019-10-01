package it.naturtalent.e4.office.ui.renderer;

import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.databinding.Binding;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.internal.workbench.E4Workbench;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.workbench.IWorkbench;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.core.swt.renderer.TextControlSWTRenderer;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.core.services.editsupport.EMFFormsEditSupport;
import org.eclipse.emfforms.spi.core.services.label.EMFFormsLabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;

import it.naturtalent.e4.office.ui.OfficeUtils;
import it.naturtalent.e4.office.ui.preferences.OfficeDefaultPreferenceUtils;
import it.naturtalent.office.model.address.FootNote;

/**
 * Die Eingabe des Namens soll laufend ueberwacht werden um eine Verifizierung zu ermoeglichen
 * 
 * @author dieter
 *
 */
public class FootNoteNameRenderer extends TextControlSWTRenderer
{
	private String defaultName;
	
	private FootNote footNote;
		
	private IEventBroker eventBroker;
	
	@Inject
	public FootNoteNameRenderer(VControl vElement,
			ViewModelContext viewContext, ReportService reportService,
			EMFFormsDatabinding emfFormsDatabinding,
			EMFFormsLabelProvider emfFormsLabelProvider,
			VTViewTemplateProvider vtViewTemplateProvider,
			EMFFormsEditSupport emfFormsEditSupport)
	{
		super(vElement, viewContext, reportService, emfFormsDatabinding,
				emfFormsLabelProvider, vtViewTemplateProvider, emfFormsEditSupport);
		
		// dieser Absender wird editiert
		footNote = (FootNote) viewContext.getDomainModel();
				
		MApplication currentApplication = E4Workbench.getServiceContext().get(IWorkbench.class).getApplication();
		eventBroker = currentApplication.getContext().get(IEventBroker.class);
		
		// Textstyle-Wert aus dem E$-Context uebernehmen, wenn einer uebergeben wurde 				
		IEclipseContext context = E4Workbench.getServiceContext();
		defaultName = (String) context.get(OfficeDefaultPreferenceUtils.E4CONTEXT_DEFAULTNAME);	
	}
	

	/*
	 * der Defaultwert darf nicht veraendert werden, deshalb Eingabefeld deaktivieren  
	 * 
	 */
	@Override
	protected int getTextWidgetStyle()
	{
		int style = super.getTextWidgetStyle();
		
		String name = footNote.getName();
		if(StringUtils.equals(name, defaultName))		
			style = style | SWT.Deactivate;
						
		return style;
	}
	

	/*
	 * Broker meldet laufend die aktuelle Eingabe
	 */
	@Override
	protected Binding[] createBindings(Control control)
			throws DatabindingFailedException
	{
		Binding[] bindings = super.createBindings(control);

		Object obj = Composite.class.cast(control).getChildren()[0];
		if (obj instanceof Text)
		{
			final Text text = (Text) obj;
			text.addModifyListener(new ModifyListener()
			{				
				@Override
				public void modifyText(ModifyEvent e)
				{					 
					// Broker meldet den im Text-Widget modifizierte Einbagetext				
					eventBroker.post(OfficeUtils.MODIFY_EDITOR_EVENT, text.getText());
				}
			});
		}
		
		return bindings;
	}
	
}
