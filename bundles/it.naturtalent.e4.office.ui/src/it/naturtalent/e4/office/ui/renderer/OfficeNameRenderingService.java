package it.naturtalent.e4.office.ui.renderer;

import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedReport;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.swt.core.AbstractSWTRenderer;
import org.eclipse.emfforms.spi.swt.core.di.EMFFormsDIRendererService;

import it.naturtalent.office.model.address.AddressPackage;




/**
 * @author dieter.apel
 *
 * Implementiert das Interface EMFFormsDIRendererService und wird als OSGI-Service
 * zur Verfuegung gestellt (@see referenzrenderer.aml)
 * 
 * Fuer das Rendering der soll 'ReferenzRendering' verwendet werden. 
 */
public class OfficeNameRenderingService implements EMFFormsDIRendererService<VControl>
{

	private EMFFormsDatabinding databindingService;
	private ReportService reportService;
	
	/**
	 * Called by the initializer to set the EMFFormsDatabinding.
	 *
	 * @param databindingService The EMFFormsDatabinding
	 */
	protected void setEMFFormsDatabinding(EMFFormsDatabinding databindingService)
	{
		this.databindingService = databindingService;		
	}
	
	/**
	 * Called by the initializer to set the ReportService.
	 *
	 * @param reportService The ReportService
	 */
	protected void setReportService(ReportService reportService) {
		this.reportService = reportService;
	}

	@Override
	public double isApplicable(VElement vElement, ViewModelContext viewModelContext)
	{
		if (!VControl.class.isInstance(vElement))
		{
			return NOT_APPLICABLE;
		}
		final VControl control = (VControl) vElement;
		IValueProperty<?, ?> valueProperty;
		try
		{
			valueProperty = databindingService.getValueProperty(
					control.getDomainModelReference(),
					viewModelContext.getDomainModel());
		} catch (final DatabindingFailedException ex)
		{
			reportService.report(new DatabindingFailedReport(ex));
			return NOT_APPLICABLE;
		}
		
		// Feature des Elements
		final EStructuralFeature eStructuralFeature = EStructuralFeature.class
				.cast(valueProperty.getValueType());
			
		// Absendername
		if (AddressPackage.eINSTANCE.getAbsender_Name().equals(eStructuralFeature))
		{			
			return 21.0;					
		}
		
		// Signaturname
		if (AddressPackage.eINSTANCE.getSignature_Name().equals(eStructuralFeature))
		{
			return 21.0;					
		}

		// Referenzname
		if (AddressPackage.eINSTANCE.getReferenz_Name().equals(eStructuralFeature))
		{			
			return 21.0;					
		}
		
		// FootNoteName
		if (AddressPackage.eINSTANCE.getFootNote_Name().equals(eStructuralFeature))
		{
			return 21.0;					
		}
		
	
		return NOT_APPLICABLE;
	}

	@Override
	public Class<? extends AbstractSWTRenderer<VControl>> getRendererClass()
	{
		return OfficeNameRendering.class;
	}

}
