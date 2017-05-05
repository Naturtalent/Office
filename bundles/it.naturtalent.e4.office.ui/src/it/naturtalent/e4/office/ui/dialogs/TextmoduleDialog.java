package it.naturtalent.e4.office.ui.dialogs;

import it.naturtalent.e4.office.IOfficeService;
import it.naturtalent.e4.office.ITextModuleDataFactory;
import it.naturtalent.e4.office.ITextModuleModel;
import it.naturtalent.e4.office.TextModule;
import it.naturtalent.e4.office.TextModuleModel;
import it.naturtalent.e4.office.TextModuleTheme;
import it.naturtalent.e4.office.ui.actions.AddTextmoduleAction;
import it.naturtalent.e4.office.ui.actions.DeleteTextModuleAction;
import it.naturtalent.e4.office.ui.actions.EditTextmoduleAction;
import it.naturtalent.e4.office.ui.handlers.AddBrokerEventHandler;
import it.naturtalent.e4.office.ui.handlers.DeleteBrokerEventHandler;
import it.naturtalent.e4.office.ui.handlers.EditBrokerEventHandler;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.dialogs.ContainerCheckedTreeViewer;
import org.eclipse.wb.swt.ResourceManager;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.events.MouseEvent;

import it.naturtalent.e4.office.ui.Messages;

import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.swt.custom.StyledText;

public class TextmoduleDialog extends TitleAreaDialog
{

	private ITextModuleModel model;
	private List<TextModule>selectedTextModules = new ArrayList<TextModule>();
	
	private String officeContext;	
	private Table tableTextModules;
	private ContainerCheckedTreeViewer checkboxTreeViewer;
	private TableViewer tableViewerModules;
	
	private Button btnAdd;
	private Button btnDelete;
	private Button btnEdit;
	private Button btnForward;
	private Button btnBackward;
	private Button btnUp;
	private Button btnDown;
	private Button okButton;
	private Menu menu;
	
	private MApplication application;
	private IEventBroker broker;
	
	private IOfficeService officeService;	
	private AddBrokerEventHandler addEventHandler;	
	private DeleteBrokerEventHandler deleteEventHandler;	
	private EditBrokerEventHandler editEventHandler;
	private StyledText textDetails;

	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public TextmoduleDialog(Shell parentShell, String officeContext)
	{
		super(parentShell);
		this.officeContext = officeContext;
	}

	/**
	 * Create contents of the dialog.
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent)
	{
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setLayout(new GridLayout(6, false));
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		checkboxTreeViewer = new ContainerCheckedTreeViewer(container, SWT.BORDER);
		checkboxTreeViewer.addCheckStateListener(new ICheckStateListener()
		{
			public void checkStateChanged(CheckStateChangedEvent event)
			{
				updateWidgetState();
			}
		});
		checkboxTreeViewer.addDoubleClickListener(new IDoubleClickListener()
		{
			public void doubleClick(DoubleClickEvent event)
			{
				EditTextmoduleAction editAction = new EditTextmoduleAction(officeContext, checkboxTreeViewer);
				ContextInjectionFactory.invoke(editAction, PostConstruct.class, application.getContext());
				editAction.run();	
				updateWidgetState();
			}
		});
		checkboxTreeViewer
				.addSelectionChangedListener(new ISelectionChangedListener()
				{
					public void selectionChanged(SelectionChangedEvent event)
					{
						textDetails.setText("");
						
						IStructuredSelection selection = (IStructuredSelection) checkboxTreeViewer
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
		Tree tree = checkboxTreeViewer.getTree();
		tree.addMouseTrackListener(new MouseTrackAdapter()
		{
			@Override
			public void mouseHover(MouseEvent e)
			{
				 TreeItem item = checkboxTreeViewer.getTree().getItem(new Point(e.x, e.y));
				 if (item != null && item.getData() instanceof TextModule) 
				 {
					 TextModule textModule = (TextModule) item.getData();
					 checkboxTreeViewer.getTree().setToolTipText(TextModule.getToolTip(textModule));
				 }
				 else checkboxTreeViewer.getTree().setToolTipText(null);
			}
		});
		GridData gd_tree = new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1);
		gd_tree.heightHint = 273;
		gd_tree.widthHint = 314;
		tree.setLayoutData(gd_tree);
		checkboxTreeViewer.setLabelProvider(new TextmoduleTreeLabelProvider());
		checkboxTreeViewer.setContentProvider(new TextmoduleTreeContentProvider());
				
		Composite compositeHorizNavButtons = new Composite(container, SWT.NONE);
		compositeHorizNavButtons.setLayout(new FillLayout(SWT.VERTICAL));
		
		btnForward = new Button(compositeHorizNavButtons, SWT.NONE);
		btnForward.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{																
				for(Object chkElement : checkboxTreeViewer.getCheckedElements())
				{
					if(chkElement instanceof TextModule)	
					{
						selectedTextModules.add((TextModule) chkElement);
						tableViewerModules.add(chkElement);
						checkboxTreeViewer.remove(chkElement);
					}
					else
						checkboxTreeViewer.setChecked(chkElement,false);
				}	
				updateWidgetState();
			}
		});
		btnForward.setToolTipText(Messages.TextmoduleDialog_btnForward_toolTipText);
		btnForward.setImage(ResourceManager.getPluginImage("it.naturtalent.e4.office.ui", "icons/forward_nav.gif"));
		
		btnBackward = new Button(compositeHorizNavButtons, SWT.NONE);
		btnBackward.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				IStructuredSelection selection = (IStructuredSelection) tableViewerModules.getSelection();
				Object [] selObjs = selection.toArray();
				for(Object selObj : selObjs)
				{					
					TextModule module = (TextModule) selObj;					
					TextModuleTheme theme = TextModuleModel.findModuleParent((TextModuleModel) model, module);
					checkboxTreeViewer.add(theme, new TextModule [] {module});
				}
				selectedTextModules.removeAll(selection.toList());
				tableViewerModules.remove(selObjs);	
				updateWidgetState();
			}
		});
		btnBackward.setToolTipText(Messages.TextmoduleDialog_btnBackward_toolTipText);
		btnBackward.setSelection(true);
		btnBackward.setImage(ResourceManager.getPluginImage("it.naturtalent.e4.office.ui", "icons/backward_nav.gif"));
		
		tableViewerModules = new TableViewer(container, SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI);
		tableViewerModules
				.addSelectionChangedListener(new ISelectionChangedListener()
				{
					public void selectionChanged(SelectionChangedEvent event)
					{
						updateWidgetState();
					}
				});
		tableTextModules = tableViewerModules.getTable();
		tableTextModules.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{				
				setDetailText((IStructuredSelection) tableViewerModules
						.getSelection());
				updateWidgetState();
			}
		});
		tableTextModules.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		tableViewerModules.setContentProvider(new ArrayContentProvider());
		tableViewerModules.setLabelProvider(new LabelProvider()
		{
			@Override
			public String getText(Object element)
			{
				TextModule module = (TextModule) element;
				return module.getName();
			}			
		});		
		tableViewerModules.setInput(selectedTextModules);
		
		Composite compositeVertNavButtons = new Composite(container, SWT.NONE);
		compositeVertNavButtons.setLayout(new FillLayout(SWT.VERTICAL));
		
		btnUp = new Button(compositeVertNavButtons, SWT.NONE);
		btnUp.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				moveUp(((IStructuredSelection) tableViewerModules.getSelection())
						.toList());
			}
		});
		btnUp.setImage(ResourceManager.getPluginImage("it.naturtalent.e4.office.ui", "icons/up_nav.gif"));
		
		btnDown = new Button(compositeVertNavButtons, SWT.NONE);
		btnDown.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				moveDown(((IStructuredSelection) tableViewerModules.getSelection())
						.toList());
			}
		});
		btnDown.setImage(ResourceManager.getPluginImage("it.naturtalent.e4.office.ui", "icons/down_nav.gif"));
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		
		Composite compositeTreeButtons = new Composite(container, SWT.NONE);
		compositeTreeButtons.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		btnAdd = new Button(compositeTreeButtons, SWT.NONE);
		btnAdd.setToolTipText(Messages.TextmoduleDialog_btnAdd_toolTipText);
		btnAdd.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				AddTextmoduleAction addAction = new AddTextmoduleAction(officeContext, checkboxTreeViewer);
				ContextInjectionFactory.invoke(addAction, PostConstruct.class, application.getContext());
				addAction.run();
			}
		});
		btnAdd.setImage(SWTResourceManager.getImage(TextmoduleDialog.class, "/icons/full/obj16/add_obj.gif"));
		
		btnDelete = new Button(compositeTreeButtons, SWT.NONE);
		btnDelete.setToolTipText(Messages.TextmoduleDialog_btnDelete_toolTipText);
		btnDelete.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				DeleteTextModuleAction deleteAction = new DeleteTextModuleAction(officeContext, checkboxTreeViewer);
				ContextInjectionFactory.invoke(deleteAction, PostConstruct.class, application.getContext());
				deleteAction.run();
			}
		});
		btnDelete.setImage(SWTResourceManager.getImage(TextmoduleDialog.class, "/icons/full/obj16/delete_obj.gif"));
		
		btnEdit = new Button(compositeTreeButtons, SWT.NONE);
		btnEdit.setToolTipText(Messages.TextmoduleDialog_btnEdit_toolTipText);
		btnEdit.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				EditTextmoduleAction editAction = new EditTextmoduleAction(officeContext, checkboxTreeViewer);
				ContextInjectionFactory.invoke(editAction, PostConstruct.class, application.getContext());
				editAction.run();				
			}
		});
		btnEdit.setImage(SWTResourceManager.getImage(TextmoduleDialog.class, "/icons/full/etool16/editor_area.gif"));
		
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		
		
		Composite compositeResult = new Composite(container, SWT.NONE);
		compositeResult.setLayout(new GridLayout(1, false));
		compositeResult.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 5, 1));
		
		textDetails = new StyledText(compositeResult, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		GridData gd_textDetails = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_textDetails.heightHint = 255;
		textDetails.setLayoutData(gd_textDetails);
		new Label(container, SWT.NONE);

		menu = new Menu(textDetails);
		textDetails.setMenu(menu);
		MenuItem mntmCopy = new MenuItem(menu, SWT.NONE);
		mntmCopy.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				textDetails.copy();
			}
		});
		mntmCopy.setText("kopieren");
		
		MenuItem mntmPaste = new MenuItem(menu, SWT.NONE);
		mntmPaste.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{	
				textDetails.paste();
			}
		});
		mntmPaste.setText("einf\u00FCgen");
		
		if(officeService != null)
		{
			model = officeService.getTextModuleModel(officeContext);
			if(model != null)
			{
				checkboxTreeViewer.setInput(model);
				if(broker != null)
				{
					addEventHandler = new AddBrokerEventHandler(checkboxTreeViewer);
					broker.subscribe(ITextModuleModel.ADD_TEXTMODULE_EVENT, addEventHandler);
					
					deleteEventHandler = new DeleteBrokerEventHandler(checkboxTreeViewer);
					broker.subscribe(ITextModuleModel.DELETE_TEXTMODULE_EVENT, deleteEventHandler);
					
					editEventHandler = new EditBrokerEventHandler(checkboxTreeViewer);
					broker.subscribe(ITextModuleModel.UPDATE_TEXTMODULE_EVENT, editEventHandler);
				}
			}
		}
		
		

		updateWidgetState();
		
		return area;
	}

	/**
	 * Create contents of the button bar.
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent)
	{
		okButton = createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);		
	}
	
	@PostConstruct
	protected void postConstruct(@Optional
	MApplication application, @Optional
	IEventBroker broker, @Optional
	IOfficeService officeService)
	{
		this.application = application; 
		this.broker = broker;		
		this.officeService = officeService;		
	}
	
	@Override
	public boolean close()
	{
		if(broker != null)
		{
			broker.unsubscribe(addEventHandler);
			broker.unsubscribe(deleteEventHandler);
			broker.unsubscribe(editEventHandler);
		}
		return super.close();
	}

	private void updateWidgetState()
	{
		Object selTreeObj = null;
		StructuredSelection selection = (StructuredSelection) checkboxTreeViewer.getSelection();
		if((selection != null) && (!selection.isEmpty()))
			selTreeObj = selection.getFirstElement();
		
		btnAdd.setEnabled(true);
		if(selTreeObj instanceof TextModule)
			btnAdd.setEnabled(false);
		
		btnDelete.setEnabled(selTreeObj != null);
		btnEdit.setEnabled(selTreeObj != null);	
				
		btnForward.setEnabled(checkboxTreeViewer.getCheckedElements().length > 0);
		btnBackward.setEnabled(!tableViewerModules.getSelection().isEmpty());
		
		btnDown.setEnabled(false);
		btnUp.setEnabled(false);
		selection = (StructuredSelection)tableViewerModules.getSelection();
		Object selTableObj = null;
		if((selection != null) && (!selection.isEmpty()))
			selTableObj  = selection.getFirstElement();

		if (selection.toArray().length == 1)
		{
			int idx = tableViewerModules.getTable().getSelectionIndex();
			if (idx > 0)
				btnUp.setEnabled(true);
			if(idx < tableViewerModules.getTable().getItemCount() - 1)
				btnDown.setEnabled(true);
		}
		
		if(okButton != null)
			okButton.setEnabled(!selectedTextModules.isEmpty());

		// Detailtext zuruecksetzen wenn kein TextModul selektiert ist
		if(!(selTableObj instanceof TextModule) && !(selTreeObj instanceof TextModule))
			setDetailText(null);
	}
	
	private void setDetailText(IStructuredSelection selection)
	{
		String text = "";
		if ((selection != null) && (!selection.isEmpty()))
		{
			Object obj = selection.getFirstElement();
			if (obj instanceof TextModule)
				text = ((TextModule) obj).getContent();
		}	
		textDetails.setText(text);
	}
	

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize()
	{
		return new Point(728, 757);
	}
	
	private void moveDown(List toMoveDown)
	{
		if (toMoveDown.size() > 0)
		{
			setElements(reverse(moveUp(reverse(selectedTextModules), toMoveDown)));
			tableViewerModules.reveal(toMoveDown.get(toMoveDown.size() - 1));
		}
	}

	
	private void moveUp(List toMoveUp)
	{
		if (toMoveUp.size() > 0)
		{
			setElements(moveUp(selectedTextModules, toMoveUp));
			tableViewerModules.reveal(toMoveUp.get(0));
		}
	}
	
	private List moveUp(List elements, List move)
	{
		int nElements = elements.size();
		List res = new ArrayList(nElements);
		Object floating = null;
		for (int i = 0; i < nElements; i++)
		{
			Object curr = elements.get(i);
			if (move.contains(curr))
			{
				res.add(curr);
			}
			else
			{
				if (floating != null)
				{
					res.add(floating);
				}
				floating = curr;
			}
		}
		if (floating != null)
		{
			res.add(floating);
		}
		return res;
	}
	
	private void setElements(List elements)
	{
		selectedTextModules = elements;
		tableViewerModules.setInput(selectedTextModules);
		updateWidgetState();
	}
	
	private List reverse(List p)
	{
		List reverse = new ArrayList(p.size());
		for (int i = p.size() - 1; i >= 0; i--)
		{
			reverse.add(p.get(i));
		}
		return reverse;
	}
	
		
	public List<TextModule> getSelectedTextModules()
	{
		return selectedTextModules;
	}

	public void setSelectedTextModules(List<TextModule> selectedTextModules)
	{
		this.selectedTextModules = selectedTextModules;
	}


}
