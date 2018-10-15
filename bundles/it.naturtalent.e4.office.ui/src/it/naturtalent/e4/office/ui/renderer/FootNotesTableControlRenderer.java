package it.naturtalent.e4.office.ui.renderer;

import javax.inject.Inject;

import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.table.model.VTableControl;
import org.eclipse.emf.ecp.view.spi.table.swt.TableControlSWTRenderer;
import org.eclipse.emf.ecp.view.spi.util.swt.ImageRegistryService;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emf.ecp.view.template.style.tableValidation.model.VTTableValidationStyleProperty;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.core.services.databinding.emf.EMFFormsDatabindingEMF;
import org.eclipse.emfforms.spi.core.services.editsupport.EMFFormsEditSupport;
import org.eclipse.emfforms.spi.core.services.label.EMFFormsLabelProvider;

public class FootNotesTableControlRenderer extends TableControlSWTRenderer
{
	@Inject
	public FootNotesTableControlRenderer(VTableControl vElement,
			ViewModelContext viewContext, ReportService reportService,
			EMFFormsDatabinding emfFormsDatabinding,
			EMFFormsLabelProvider emfFormsLabelProvider,
			VTViewTemplateProvider vtViewTemplateProvider,
			ImageRegistryService imageRegistryService,
			EMFFormsEditSupport emfFormsEditSupport)
	{
		super(vElement, viewContext, reportService, (EMFFormsDatabindingEMF) emfFormsDatabinding,
				emfFormsLabelProvider, vtViewTemplateProvider, imageRegistryService,
				emfFormsEditSupport);
		
		
	}

	/*
	 * Validation Spalte unsichtbar machen
	 * 
	 * @see org.eclipse.emf.ecp.view.spi.table.swt.TableControlSWTRenderer#getTableValidationStyleProperty()
	 */
	@Override
	protected VTTableValidationStyleProperty getTableValidationStyleProperty()
	{
		VTTableValidationStyleProperty styleProperty = super.getTableValidationStyleProperty();		
		styleProperty.setColumnWidth(1);		
		return styleProperty;
	}
	
	
	
	
}
