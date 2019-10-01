package it.naturtalent.e4.office.ui.renderer;

import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.view.internal.table.swt.TableControlDetailDialogSWTRendererService;
import org.eclipse.emf.ecp.view.internal.table.swt.TableControlSWTRendererService;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.table.model.DetailEditing;
import org.eclipse.emf.ecp.view.spi.table.model.VTableControl;
import org.eclipse.emf.ecp.view.spi.table.swt.TableControlDetailDialogSWTRenderer;
import org.eclipse.emf.ecp.view.spi.table.swt.TableControlDetailPanelRenderer;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedReport;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.swt.core.AbstractSWTRenderer;
import org.eclipse.emfforms.spi.swt.core.di.EMFFormsDIRendererService;

import it.naturtalent.office.model.address.AddressPackage;

/**
 * Service, der den Tabellenrenderer der FootNoteItems zur Verfuegung stellt.
 * 
 * @author dieter
 *
 */
public class FootNoteItemsTableControlRendererService  implements EMFFormsDIRendererService<VTableControl> 
{	
	private EMFFormsDatabinding databindingService;
	private ReportService reportService;
	
	protected void setEMFFormsDatabinding(
			EMFFormsDatabinding databindingService)
	{
		this.databindingService = databindingService;
	}
	
	protected void setReportService(ReportService reportService)
	{
		this.reportService = reportService;
	}
	
	
	@Override
	public double isApplicable(VElement vElement,
			ViewModelContext viewModelContext)
	{
		
		if (!VTableControl.class.isInstance(vElement))
		{
			return NOT_APPLICABLE;
		}
		
		if (DetailEditing.WITH_PANEL != VTableControl.class.cast(vElement).getDetailEditing())
		{
			return NOT_APPLICABLE;
		}
		
		final VTableControl control = VTableControl.class.cast(vElement);
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
				
		// die Items einer Fussnote werden in einer Tabelle dargestellt			
		if (AddressPackage.eINSTANCE.getFootNote_Footnoteitems().equals(eStructuralFeature))
		{
			// Prioritylevel 
			return 20;			
		}
		
		return NOT_APPLICABLE;
	}

	@Override
	public Class<? extends AbstractSWTRenderer<VTableControl>> getRendererClass()
	{
		return FootNoteItemsTableControlRenderer.class;
	}
}
