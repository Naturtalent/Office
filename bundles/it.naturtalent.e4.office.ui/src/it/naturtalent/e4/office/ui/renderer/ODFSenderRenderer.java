package it.naturtalent.e4.office.ui.renderer;

import javax.inject.Inject;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.treemasterdetail.ui.swt.TreeMasterDetailSWTRenderer;
import org.eclipse.emf.ecp.view.treemasterdetail.model.VTreeMasterDetail;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;

import it.naturtalent.e4.office.ui.wizards.ODFDefaultWriteAdapterWizard;

public class ODFSenderRenderer extends TreeMasterDetailSWTRenderer
{

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
	
	

}
