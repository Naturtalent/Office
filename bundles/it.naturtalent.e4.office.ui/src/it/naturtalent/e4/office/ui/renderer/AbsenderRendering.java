package it.naturtalent.e4.office.ui.renderer;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.databinding.Binding;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.internal.workbench.E4Workbench;
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
import it.naturtalent.office.model.address.Absender;



/**
 * Eingabefeld des Absendernamens anpassen.
 * 
 * Verhindert, dass im DefaultAbsender der 'defaultName' editiert werden kann.
 * Modifiziert WidgetStyle so, dass das Eingabefeld deaktiviert ist.
 * 
 * Der 'defaultName' muss im E4Context unter dem Namen 'E4CONTEXT_DEFAULTNAME' hinterlegt sein.
 * 
 * Der EventBroker liefert den momentan editierten Namen.
 * 
 * @author dieter
 *
 */
public class AbsenderRendering extends TextControlSWTRenderer
{
	private String defaultName;
	
	private Absender absender;
	
	private IEventBroker eventBroker;

	
	@Inject
	public AbsenderRendering(VControl vElement,
			ViewModelContext viewContext, ReportService reportService,
			EMFFormsDatabinding emfFormsDatabinding,
			EMFFormsLabelProvider emfFormsLabelProvider,
			VTViewTemplateProvider vtViewTemplateProvider,
			EMFFormsEditSupport emfFormsEditSupport)
	{
		super(vElement, viewContext, reportService, emfFormsDatabinding,
				emfFormsLabelProvider, vtViewTemplateProvider, emfFormsEditSupport);
		
		// dieser Absender wird editiert
		absender = (Absender) viewContext.getDomainModel();
		
		eventBroker = E4Workbench.getServiceContext().get(IEventBroker.class);
		
		// Textstyle-Wert aus dem E$-Context uebernehmen, wenn einer uebergeben wurde 				
		IEclipseContext context = E4Workbench.getServiceContext();
		defaultName = (String) context.get(OfficeDefaultPreferenceUtils.E4CONTEXT_DEFAULTNAME);	
	}

	/*
	 * Methode wir nur benoetigt fuer den Zugriff auf das Text-Eingabefeld
	 */
	@Override
	protected Binding[] createBindings(Control control)
			throws DatabindingFailedException
	{
		Binding[] bindings = super.createBindings(control);

		Object obj = ((Composite)control).getChildren()[0];		
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

	/*
	 * im DefaultAbsender wir das Eingabefeld Text-Widget deaktiviert
	 * 
	 */
	@Override
	protected int getTextWidgetStyle()
	{
		int style = super.getTextWidgetStyle();
		
		String name = absender.getName();
		if(StringUtils.equals(name, defaultName))		
			style = style | SWT.Deactivate;
						
		return style;
	}

	
	

}
