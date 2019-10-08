package it.naturtalent.e4.office.ui.renderer;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.internal.workbench.E4Workbench;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
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
 * Eingabefeld der Preferenznamen anpassen.
 * 
 * Verhindert, dass der jeweilige Defaultnamen editiert werden kann.
 * Modifiziert WidgetStyle so, dass das Eingabefeld deaktiviert ist.
 * 
 * Der zu pruefende 'defaultName' muss im E4Context unter dem Namen 'E4CONTEXT_DEFAULTNAME' hinterlegt sein.
 * Ist kein 'defaultName' definiert wird das Eingabefeld ebenfalls gesperrt.
 * 
 * Der EventBroker liefert den momentan editierten Namen.
 * 
 * @author dieter
 *
 */
public class OfficeNameRendering extends TextControlSWTRenderer
{
	// der per E4Context uebergebene Defaultname
	private String defaultName;
	
	// dieses Objekt ist momentan in Bearbeitung
	//private EObject editingPreferenceObject;
	private String currentName;
	
	private IEventBroker eventBroker;

	
	@Inject
	public OfficeNameRendering(VControl vElement,
			ViewModelContext viewContext, ReportService reportService,
			EMFFormsDatabinding emfFormsDatabinding,
			EMFFormsLabelProvider emfFormsLabelProvider,
			VTViewTemplateProvider vtViewTemplateProvider,
			EMFFormsEditSupport emfFormsEditSupport)
	{
		super(vElement, viewContext, reportService, emfFormsDatabinding,
				emfFormsLabelProvider, vtViewTemplateProvider, emfFormsEditSupport);
		
		// das in Bearbeitung befindliche Objekt
		EObject editingPreferenceObject = viewContext.getDomainModel();
				
		try
		{
			IValueProperty valueProperty = emfFormsDatabinding.getValueProperty(
					vElement.getDomainModelReference(),
					viewContext.getDomainModel());
			
			EStructuralFeature eStructuralFeature = EStructuralFeature.class
					.cast(valueProperty.getValueType());
			
			currentName = (String) editingPreferenceObject.eGet(eStructuralFeature);
			
		} catch (DatabindingFailedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		eventBroker = E4Workbench.getServiceContext().get(IEventBroker.class);
		
		// Textstyle-Wert aus dem E$-Context uebernehmen, wenn einer uebergeben wurde 				
		IEclipseContext context = E4Workbench.getServiceContext();
		defaultName = (String) context.get(OfficeDefaultPreferenceUtils.E4CONTEXT_DEFAULTNAME);	
	}

	/*
	 * Methode wir nur benoetigt fuer den Zugriff auf das Text-Eingabefeld.
	 * Text-Widget wird mit einem Modify-Listener versehen und liefert durch einen EventBroker
	 * staendig den aktuellen Inhalt. 
	 */
	@Override
	protected Binding[] createBindings(Control control) throws DatabindingFailedException
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

	/*
	 * abhaengig von 'defaultName' wird das Eingabefeld aktiviert/deaktiviert
	 * 
	 */
	@Override
	protected int getTextWidgetStyle()
	{
		int style = super.getTextWidgetStyle();
		
		// Textfeld wird deaktiviert, wenn kein 'defaultName' uebergeben wurde
		if(StringUtils.isEmpty(defaultName))
			return style | SWT.Deactivate;
				
		// Textfeld wird deaktiviert, wenn Inhalt == 'defaultName' ist
		if(StringUtils.equals(currentName, defaultName))		
			return style | SWT.Deactivate;
						
		// normale Texteingabe wenn 'defaultName != Inhalt
		return style;
	}

	
	

}
