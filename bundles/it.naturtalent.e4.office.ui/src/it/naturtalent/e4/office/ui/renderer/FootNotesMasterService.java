package it.naturtalent.e4.office.ui.renderer;

import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.treemasterdetail.model.VTreeMasterDetail;
import org.eclipse.emf.ecp.view.treemasterdetail.ui.swt.internal.TreeMasterDetailSWTRendererService;
import org.eclipse.emfforms.spi.swt.core.AbstractSWTRenderer;

import it.naturtalent.office.model.address.FootNotes;
import it.naturtalent.office.model.address.Referenzen;

/**
 * Dieser Service stellt den Renderer fuer den FootNotes TreeMaster zur Verfuegung
 * 
 * @author dieter
 *
 */
public class FootNotesMasterService extends TreeMasterDetailSWTRendererService
{

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.swt.core.di.EMFFormsDIRendererService#isApplicable(VElement,ViewModelContext)
	 */
	@Override
	public double isApplicable(VElement vElement,ViewModelContext viewModelContext)
	{		
		
		if (VTreeMasterDetail.class.isInstance(vElement))
		{	
			// Treffer, wenn der Container FootNotes aufruft
			if(viewModelContext.getDomainModel() instanceof FootNotes)
				return 30d;
			
			// Treffer, wenn der Container FootNotes aufruft
			if(viewModelContext.getDomainModel() instanceof Referenzen)
				return 40d;
		}
		
		return NOT_APPLICABLE;
	}
	
	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.swt.core.di.EMFFormsDIRendererService#getRendererClass()
	 */
	@Override
	public Class<? extends AbstractSWTRenderer<VTreeMasterDetail>> getRendererClass() 
	{
		// erweiterter TreeMaster-Renderer mit OfficeContextFilter 
		return OfficeContextTreeMasterRenderer.class;
	}

}
