package it.naturtalent.e4.office.ui.part;



import it.naturtalent.e4.office.IOfficeService;
import it.naturtalent.e4.office.ITextModuleModel;
import it.naturtalent.e4.office.TextModule;
import it.naturtalent.e4.office.ui.Messages;
import it.naturtalent.e4.office.ui.actions.AddTextmoduleAction;
import it.naturtalent.e4.office.ui.actions.DeleteTextModuleAction;
import it.naturtalent.e4.office.ui.actions.EditTextmoduleAction;
import it.naturtalent.e4.office.ui.dialogs.TextmoduleTreeContentProvider;
import it.naturtalent.e4.office.ui.dialogs.TextmoduleTreeLabelProvider;
import it.naturtalent.e4.office.ui.handlers.AddBrokerEventHandler;
import it.naturtalent.e4.office.ui.handlers.DeleteBrokerEventHandler;
import it.naturtalent.e4.office.ui.handlers.EditBrokerEventHandler;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.wb.swt.SWTResourceManager;

public class TextbausteinePart
{

	public static final String TEXTBAUSTEINE_VIEW_ID = "it.naturtalent.e4.office.ui.textbausteine"; //$NON-NLS-1$
	
	protected String officeContext = IOfficeService.NTOFFICE_CONTEXT;

	@Inject
	@Optional
	private MApplication application;
	
	@Inject
	@Optional
	private IEventBroker broker;

	@Inject
	@Optional
	private IOfficeService officeService;

	
	private TreeViewer treeViewer;
	private TreeColumn trclmnNewColumn;
	private Text textDetails;
	private Composite composite;
	private Button btnAdd;
	private Button btnDelete;
	private Button btnEdit;
		
	private AddBrokerEventHandler addEventHandler;	
	private DeleteBrokerEventHandler deleteEventHandler;
	private EditBrokerEventHandler editEventHandler;

	
	public TextbausteinePart() 
	{
	}

	/**
	 * Create contents of the view part.
	 */
	@PostConstruct
	public void createControls(Composite parent)
	{
		parent.setLayout(new GridLayout(1, false));
		
		treeViewer = new TreeViewer(parent, SWT.BORDER);
		treeViewer.addSelectionChangedListener(new ISelectionChangedListener()
		{
			public void selectionChanged(SelectionChangedEvent event)
			{
				textDetails.setText(""); //$NON-NLS-1$
				
				IStructuredSelection selection = (IStructuredSelection) treeViewer
						.getSelection();
				if ((selection != null) && (!selection.isEmpty()))
				{
					Object obj = selection.getFirstElement();
					if (obj instanceof TextModule)
					{
						TextModule textModule = (TextModule) obj;
						textDetails.setText(textModule.getContent());
					}
				}
				
				updateWidgetState();
			}
		});
		treeViewer.addDoubleClickListener(new IDoubleClickListener()
		{
			public void doubleClick(DoubleClickEvent event)
			{
				EditTextmoduleAction editAction = new EditTextmoduleAction(officeContext, treeViewer);
				ContextInjectionFactory.invoke(editAction, PostConstruct.class, application.getContext());
				editAction.run();												
			}
		});
		Tree tree = treeViewer.getTree();
		tree.setHeaderVisible(true);
		tree.addMouseTrackListener(new MouseTrackAdapter()
		{
			@Override
			public void mouseHover(MouseEvent e)
			{
				 TreeItem item = treeViewer.getTree().getItem(new Point(e.x, e.y));
				 if (item != null && item.getData() instanceof TextModule) 
				 {
					 TextModule textModule = (TextModule) item.getData();
					 treeViewer.getTree().setToolTipText(TextModule.getToolTip(textModule));
				 }
				 else treeViewer.getTree().setToolTipText(null);
			}
		});
		GridData gd_tree = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_tree.heightHint = 168;
		tree.setLayoutData(gd_tree);
		
		trclmnNewColumn = new TreeColumn(tree, SWT.NONE);
		trclmnNewColumn.setWidth(400);
		trclmnNewColumn.setText(Messages.TextModuleImportComposite_trclmnNewColumn_text);
		
		composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new FillLayout(SWT.HORIZONTAL));
		composite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		btnAdd = new Button(composite, SWT.NONE);
		btnAdd.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				AddTextmoduleAction addAction = new AddTextmoduleAction(officeContext, treeViewer);
				ContextInjectionFactory.invoke(addAction, PostConstruct.class, application.getContext());
				addAction.run();						
			}
		});
		btnAdd.setImage(SWTResourceManager.getImage(TextbausteinePart.class, "/icons/full/obj16/add_obj.gif"));
		
		
		btnDelete = new Button(composite, SWT.NONE);
		btnDelete.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				DeleteTextModuleAction deleteAction = new DeleteTextModuleAction(officeContext, treeViewer);
				ContextInjectionFactory.invoke(deleteAction, PostConstruct.class, application.getContext());				
				deleteAction.run();							
			}
		});
		btnDelete.setImage(SWTResourceManager.getImage(TextbausteinePart.class, "/icons/full/etool16/delete.gif"));
		
		
		btnEdit = new Button(composite, SWT.NONE);
		btnEdit.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				EditTextmoduleAction editAction = new EditTextmoduleAction(officeContext, treeViewer);
				ContextInjectionFactory.invoke(editAction, PostConstruct.class, application.getContext());
				editAction.run();										
			}
		});
		btnEdit.setImage(SWTResourceManager.getImage(TextbausteinePart.class, "/icons/full/etool16/editor_area.gif"));
		
		
		textDetails = new Text(parent, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL | SWT.MULTI);		
		textDetails.setEditable(false);
		GridData gd_textDetails = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_textDetails.heightHint = 59;
		textDetails.setLayoutData(gd_textDetails);
		treeViewer.setLabelProvider(new TextmoduleTreeLabelProvider());
		treeViewer.setContentProvider(new TextmoduleTreeContentProvider());
		
		
		if(officeService != null)
		{
			ITextModuleModel model = officeService.getTextModuleModel(officeContext);
			if(model != null)
			{
				treeViewer.setInput(model);
				if(broker != null)
				{
					addEventHandler = new AddBrokerEventHandler(treeViewer);
					broker.subscribe(ITextModuleModel.ADD_TEXTMODULE_EVENT, addEventHandler);
					
					deleteEventHandler = new DeleteBrokerEventHandler(treeViewer);
					broker.subscribe(ITextModuleModel.DELETE_TEXTMODULE_EVENT, deleteEventHandler);	
					
					editEventHandler = new EditBrokerEventHandler(treeViewer);
					broker.subscribe(ITextModuleModel.UPDATE_TEXTMODULE_EVENT, editEventHandler);
				}
			}
		}
	}
	
	private void updateWidgetState()
	{
		Object selObj = null;
		StructuredSelection selection = (StructuredSelection) treeViewer.getSelection();
		if((selection != null) && (!selection.isEmpty()))
			selObj = selection.getFirstElement();
		
		btnAdd.setEnabled(true);
		if(selObj instanceof TextModule)
			btnAdd.setEnabled(false);
		
		btnDelete.setEnabled(selObj != null);
		btnEdit.setEnabled(selObj != null);	
	}


	@PreDestroy
	public void dispose()
	{		
		if(broker != null)
		{
			broker.unsubscribe(addEventHandler);
			broker.unsubscribe(deleteEventHandler);
			broker.unsubscribe(editEventHandler);
		}
	}

	@Focus
	public void setFocus()
	{
		if(treeViewer != null)
			treeViewer.getTree().setFocus();
	}


}
