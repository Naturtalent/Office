package it.naturtalent.e4.office.ui.renderer;

import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.table.model.VTableControl;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedReport;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.swt.core.AbstractSWTRenderer;
import org.eclipse.emfforms.spi.swt.core.di.EMFFormsDIRendererService;

import it.naturtalent.office.model.address.AddressPackage;




public class FootNotesTableControlRendererService implements EMFFormsDIRendererService<VTableControl>
{
	
	private EMFFormsDatabinding databindingService;
	private ReportService reportService;
	
	
	protected void setEMFFormsDatabinding(
			EMFFormsDatabinding databindingService)
	{
		this.databindingService = databindingService;
	}
	
	@Override
	public double isApplicable(VElement vElement,
			ViewModelContext viewModelContext)
	{
		
		if (!VControl.class.isInstance(vElement))
		{
			return NOT_APPLICABLE;
		}
		final VControl control = (VControl) vElement;
		IValueProperty valueProperty;
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
		final EStructuralFeature eStructuralFeature = EStructuralFeature.class
				.cast(valueProperty.getValueType());
				
		//if (ArchivPackage.eINSTANCE.getOrdner_Registers().equals(eStructuralFeature))
		if (AddressPackage.eINSTANCE.getFootNotes_Footnotes().equals(eStructuralFeature))
		{
			// Prioritylevel 
			return 12;
			//return NOT_APPLICABLE;
		}
		
		return NOT_APPLICABLE;
	}

	@Override
	public Class<? extends AbstractSWTRenderer<VTableControl>> getRendererClass()
	{						
		return FootNotesTableControlRenderer.class;
	}
}
