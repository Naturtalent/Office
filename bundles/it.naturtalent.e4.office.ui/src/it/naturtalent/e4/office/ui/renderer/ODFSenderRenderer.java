package it.naturtalent.e4.office.ui.renderer;

import java.util.List;

import javax.inject.Inject;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emf.ecp.view.spi.treemasterdetail.ui.swt.MasterDetailAction;
import org.eclipse.emf.ecp.view.spi.treemasterdetail.ui.swt.TreeMasterDetailSWTRenderer;
import org.eclipse.emf.ecp.view.treemasterdetail.model.VTreeMasterDetail;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridCell;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import it.naturtalent.e4.office.ui.wizards.ODFDefaultWriteAdapterWizard;

public class ODFSenderRenderer extends TreeMasterDetailSWTRenderer
{

	private SashForm sash; 
	
	@Inject private IEventBroker eventBroker;

	@Inject
	public ODFSenderRenderer(VTreeMasterDetail vElement,
			ViewModelContext viewContext, ReportService reportService)
	{
		super(vElement, viewContext, reportService);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected TreeViewer createMasterTree(Composite masterPanel)
	{
		TreeViewer treeViewer = super.createMasterTree(masterPanel);
		treeViewer.addSelectionChangedListener(new ISelectionChangedListener()
		{
			public void selectionChanged(SelectionChangedEvent event)
			{	
				IStructuredSelection selection = ((IStructuredSelection) event.getSelection());	
				Object selObj = selection.getFirstElement();
				if (selObj != null)
				{
					if (eventBroker != null)
						eventBroker.post(
								ODFDefaultWriteAdapterWizard.SENDER_MASTERSELECTION_EVENT,selObj);
				}
			}
		});
		
		return treeViewer;
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


	

}
