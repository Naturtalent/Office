package it.naturtalent.e4.kontakte.ui;

import it.naturtalent.e4.kontakte.IKontakteDataModel;
import it.naturtalent.icons.core.Icon;
import it.naturtalent.icons.core.IconSize;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

public class DefaultKontaktDetails
{
	
	protected IEventBroker eventBroker;
	
	
	private class DialogCellEditingSupport extends EditingSupport
	{
		protected final TableViewer viewer;
		public DialogCellEditingSupport(ColumnViewer viewer)
		{
			super(viewer);
			this.viewer = (TableViewer) viewer;
		}

		@Override
		protected CellEditor getCellEditor(Object element)
		{
			return new TextCellEditor(viewer.getTable());
		}

		@Override
		protected boolean canEdit(Object element)
		{			
			return true;
		}

		@Override
		protected Object getValue(Object element)
		{					
			return element.toString();
		}

		@Override
		protected void setValue(Object element, Object editedValue)
		{		
			int idx = tableViewer.getTable().getSelectionIndex();			 
			String stgEditedValue = (String) editedValue;			
			if(StringUtils.isNotEmpty(stgEditedValue))
			{
				values.set(idx, stgEditedValue);			
				tableViewer.refresh();
				
				if(eventBroker != null)
					eventBroker.send(IKontakteDataModel.KONTAKT_EVENT_DETAILS_MODIFIED, element);
			}
		}

	}

	protected Shell shell;
	
	protected Section section;
	
	protected TableViewer tableViewer;
	
	protected Table table;
	
	protected TableViewerColumn tableViewerColumn;
	
	// Liste aller Daten eines Details (z.B. alle Telefonnummern)
	protected List<String>values;
	
	protected String editDialogLabel = ""; //$NON-NLS-1$
	
	protected TableColumn tblclmn;
	
	
	
	// Validator fuer InputDialog
	protected IInputValidator validator = new IInputValidator()
	{
		public String isValid(String string)
		{
			if (StringUtils.isEmpty(string))						
				return Messages.DefaultKontaktDetails_EmptyField;
			
			return null;
		}
	};
	
	protected Action actionAdd = new Action()
	{
		public void run()
		{
			doAdd();
		}
	};
	
	private Action actionDelete = new Action()
	{
		public void run()
		{
			doDelete();
		}
	};

	protected Action actionEdit = new Action()
	{
		public void run()
		{
			doEdit();
		}
	};

	{
		// ADD Image		
		actionAdd.setImageDescriptor(Icon.COMMAND_ADD.getImageDescriptor(IconSize._16x16_DefaultIconSize));

		// Edit Image		
		actionEdit.setImageDescriptor(Icon.COMMAND_EDIT.getImageDescriptor(IconSize._16x16_DefaultIconSize));
		actionEdit.setEnabled(false);

		// Delete Image
		actionDelete.setImageDescriptor(Icon.COMMAND_DELETE.getImageDescriptor(IconSize._16x16_DefaultIconSize));
		actionDelete.setEnabled(false);
	}
	

	protected void doAdd()
	{
		InputDialog dialog = new InputDialog(shell, Messages.DefaultKontaktDetails_InputLabel,
				editDialogLabel, "", validator); //$NON-NLS-1$
		if(dialog.open() == InputDialog.OK)
		{
			String strgValue = dialog.getValue();
			values.add(strgValue);
			tableViewer.add(strgValue);
			section.setExpanded(true);
			tableViewer.setSelection(new StructuredSelection(strgValue), true);		
			
			if(eventBroker != null)
				eventBroker.post(IKontakteDataModel.KONTAKT_EVENT_DETAILS_MODIFIED, tableViewer.getInput());			

		}
	}
	
	protected void doEdit()
	{
		IStructuredSelection selection = (IStructuredSelection) tableViewer.getSelection();
		Object selObj = selection.getFirstElement();
		if(selObj instanceof String)
		{
			String strgValue = (String) selObj;
			InputDialog dialog = new InputDialog(shell, Messages.DefaultKontaktDetails_Label,
					editDialogLabel, strgValue, validator);
			if(dialog.open() == InputDialog.OK)
			{
				int idx = tableViewer.getTable().getSelectionIndex();
				values.set(idx, dialog.getValue());
				tableViewer.refresh();
				
				if(eventBroker != null)
					eventBroker.post(IKontakteDataModel.KONTAKT_EVENT_DETAILS_MODIFIED, tableViewer.getInput());			

			}				
		}
	}

	protected void doDelete()
	{
		IStructuredSelection selection = (IStructuredSelection) tableViewer.getSelection();
		Object selObj = selection.getFirstElement();
		if(selObj instanceof String)
		{
			for ( Iterator i = selection.iterator(); i.hasNext(); )
				values.remove(i.next());
			tableViewer.refresh();
			
			if(eventBroker != null)
				eventBroker.post(IKontakteDataModel.KONTAKT_EVENT_DETAILS_MODIFIED, tableViewer.getInput());			

		}
	}


	public DefaultKontaktDetails(Shell shell, Section section, FormToolkit formToolkit)
	{
		super();
		this.shell = shell;
		this.section = section;
		createSectionToolbar(section);
		tableViewer = new TableViewer(section, SWT.BORDER | SWT.FULL_SELECTION  | SWT.MULTI);
		tableViewer.setContentProvider(new ArrayContentProvider());
		tableViewer.addSelectionChangedListener(new ISelectionChangedListener()
		{
			@Override
			public void selectionChanged(SelectionChangedEvent event)
			{
				updateWidgets();			
			}
		});
		
		tableViewer.addDoubleClickListener(new IDoubleClickListener()
		{			
			@Override
			public void doubleClick(DoubleClickEvent event)
			{
				actionEdit.run();				
			}
		});
		
		table = tableViewer.getTable();
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		formToolkit.paintBordersFor(table);
		section.setClient(table);
		
		tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		tblclmn = tableViewerColumn.getColumn();
		tblclmn.setWidth(100);		
		tableViewerColumn.setLabelProvider(new CellLabelProvider()
		{			
			@Override
			public void update(ViewerCell cell)
			{
				String item = (String) cell.getElement();
				cell.setText(item);				
			}
		});
		tableViewerColumn.setEditingSupport(new DialogCellEditingSupport(tableViewer));
	}
	
	private void createSectionToolbar(Section section)
	{
		ToolBarManager toolBarManager = new ToolBarManager(SWT.FLAT);
		ToolBar toolbar = toolBarManager.createControl(section);
		final Cursor handCursor = new Cursor(Display.getCurrent(),SWT.CURSOR_HAND);
		toolbar.setCursor(handCursor);
		// Cursor needs to be explicitly disposed
		toolbar.addDisposeListener(new DisposeListener()
		{
			public void widgetDisposed(DisposeEvent e)
			{
				if (handCursor.isDisposed() == false)
				{
					handCursor.dispose();
				}
			}
		});
		
		// Add Action
		toolBarManager.add(actionAdd);
		
		// Edit Action
		toolBarManager.add(actionEdit);
		
		// Delete Actions to the tool bar
		toolBarManager.add(actionDelete);
		
		toolBarManager.update(true);
		section.setTextClient(toolbar);
	}
	
	public List<String> getValues()
	{		
		return values;
	}

	
	/**
	 * Alle Daten eines Details uebernehmen (z.B. alle Telefonnummern)
	 * 
	 * @param values
	 */
	public void setValues(List<String> values)
	{		
		this.values = values;
		tableViewer.setInput(this.values);
	}

	private void updateWidgets()
	{
		actionDelete.setEnabled(table.getSelectionCount() > 0);
		actionEdit.setEnabled(table.getSelectionCount() > 0);
	}


	public void setEventBroker(IEventBroker eventBroker)
	{
		this.eventBroker = eventBroker;
	}
	
	
	
}
