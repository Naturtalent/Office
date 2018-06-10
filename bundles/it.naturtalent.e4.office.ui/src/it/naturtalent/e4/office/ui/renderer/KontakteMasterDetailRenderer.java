package it.naturtalent.e4.office.ui.renderer;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emf.ecp.view.spi.treemasterdetail.ui.swt.TreeMasterDetailSWTRenderer;
import org.eclipse.emf.ecp.view.treemasterdetail.model.VTreeMasterDetail;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridCell;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import it.naturtalent.e4.office.ui.OfficeUtils;

public class KontakteMasterDetailRenderer extends TreeMasterDetailSWTRenderer
{

	private SashForm sash; 
	
	private TreeViewer treeViewer;
		
	@Inject
	public KontakteMasterDetailRenderer(VTreeMasterDetail vElement,
			ViewModelContext viewContext, ReportService reportService)
	{
		super(vElement, viewContext, reportService);		
	}

	@Override
	protected SashForm createSash(Composite parent)
	{
		// TODO Auto-generated method stub
		sash = super.createSash(parent);		
		return sash;
	}

	@Override
	protected Control renderControl(SWTGridCell cell, Composite parent)
			throws NoRendererFoundException, NoPropertyDescriptorFoundExeption
	{
		Control control = super.renderControl(cell, parent);
		sash.setWeights(new int[] { 2, 2 });
		return control;
	}

	@Override
	protected TreeViewer createMasterTree(Composite masterPanel)
	{		
		treeViewer = super.createMasterTree(masterPanel);
		
		// Sortierer
		treeViewer.setComparator(new ViewerComparator());
		
		return treeViewer;
	}

	/**
	 * RefreshMasterTree
	 * 
	 * 
	 * @param register
	 */
	@Inject
	@Optional
	public void handleRefreshRequest(@UIEventTopic(OfficeUtils.KONTAKTE_REFRESH_MASTER_REQUEST) Object object)
	{
		if (!treeViewer.getTree().isDisposed())
		{
			if(object == null)
				treeViewer.refresh();
			else
				treeViewer.refresh(object);
		}
	}

	
}
