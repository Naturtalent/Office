package it.naturtalent.e4.office.ui.renderer;

import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.treemasterdetail.model.VTreeMasterDetail;
import org.eclipse.emf.ecp.view.treemasterdetail.ui.swt.internal.TreeMasterDetailSWTRendererService;
import org.eclipse.emfforms.spi.swt.core.AbstractSWTRenderer;

import it.naturtalent.office.model.address.Kontakte;

public class KontakteMasterDetailRendererService extends TreeMasterDetailSWTRendererService
{
	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.swt.core.di.EMFFormsDIRendererService#isApplicable(org.eclipse.emf.ecp.view.spi.model.VElement,
	 *      org.eclipse.emf.ecp.view.spi.context.ViewModelContext)
	 */
	@Override
	public double isApplicable(VElement vElement,ViewModelContext viewModelContext)
	{
		if (VTreeMasterDetail.class.isInstance(vElement))
		{	
			if(viewModelContext.getDomainModel() instanceof Kontakte)
				return 30d;
		}
		return NOT_APPLICABLE;
	}
	
	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.swt.core.di.EMFFormsDIRendererService#getRendererClass()
	 */
	@Override
	public Class<? extends AbstractSWTRenderer<VTreeMasterDetail>> getRendererClass() {
		return KontakteMasterDetailRenderer.class;
	}

}
