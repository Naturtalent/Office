package it.naturtalent.e4.office.ui.renderer;

import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.treemasterdetail.model.VTreeMasterDetail;
import org.eclipse.emf.ecp.view.treemasterdetail.ui.swt.internal.TreeMasterDetailSWTRendererService;
import org.eclipse.emfforms.spi.swt.core.AbstractSWTRenderer;

import it.naturtalent.office.model.address.Senders;



public class ODFSenderRendererService extends TreeMasterDetailSWTRendererService
{

	@Override
	public double isApplicable(VElement vElement,ViewModelContext viewModelContext)
	{
		if (VTreeMasterDetail.class.isInstance(vElement))
		{
			if(viewModelContext.getDomainModel() instanceof Senders)
				return 30d;
		}
		return NOT_APPLICABLE;
	}

	@Override
	public Class<? extends AbstractSWTRenderer<VTreeMasterDetail>> getRendererClass()
	{
		// TODO Auto-generated method stub
		return ODFSenderRenderer.class;
	}

}
