package it.naturtalent.e4.office.ui.renderer;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.emf.ecp.view.internal.control.multireference.MultiReferenceSWTRendererService;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emf.ecp.view.spi.treemasterdetail.ui.swt.TreeMasterDetailSWTRenderer;
import org.eclipse.emf.ecp.view.treemasterdetail.model.VTreeMasterDetail;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridCell;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import it.naturtalent.e4.office.ui.OfficeUtils;
import it.naturtalent.office.model.address.Kontakt;


public class KontakteMasterDetailRenderer extends TreeMasterDetailSWTRenderer
{

	private SashForm sash; 
	
	private TreeViewer treeViewer;
	
	private Text textFilter;
	private String stgFilter;
	
	
	// filtert nach Kontaktnamen
	private class NameFilter extends ViewerFilter
	{
		@Override
		public boolean select(Viewer viewer, Object parentElement,
				Object element)
		{					
			if (element instanceof Kontakt)
			{	
				String kontaktName = ((Kontakt)element).getName();
				if(StringUtils.isNotEmpty(stgFilter))					
					return StringUtils.containsIgnoreCase(kontaktName, stgFilter);					
			}
			
			return true;
		}
	}

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
		sash.setWeights(new int[] { 2, 3 });
		return control;
	}
	
	@Override
	protected Composite createMasterPanel(SashForm sash)
	{
		Composite masterPanel = super.createMasterPanel(sash);
		
		textFilter = new Text(masterPanel, SWT.BORDER);
		textFilter.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		textFilter.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		textFilter.addModifyListener(new ModifyListener()
		{
			public void modifyText(ModifyEvent e)
			{				
				stgFilter = textFilter.getText();
				treeViewer.refresh();
				treeViewer.expandToLevel(2);
			}
		});
		
		return masterPanel;
	}

	@Override
	protected TreeViewer createMasterTree(Composite masterPanel)
	{		
		treeViewer = super.createMasterTree(masterPanel);
		
		// Sortierer
		treeViewer.setComparator(new ViewerComparator());
		
		// Filter Kontaktnamen
		treeViewer.setFilters(new ViewerFilter []{new NameFilter()});
				
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

	/**
	 * Selection Request
	 * 
	 * @param register
	 */
	@Inject
	@Optional
	public void handleSelectionRequest(@UIEventTopic(OfficeUtils.KONTACTMASTER_SELECTIONREQUEST) Object object)
	{
		if (!treeViewer.getTree().isDisposed() && (object instanceof Kontakt))
			treeViewer.setSelection(new StructuredSelection(object), true);
	}
	
}
