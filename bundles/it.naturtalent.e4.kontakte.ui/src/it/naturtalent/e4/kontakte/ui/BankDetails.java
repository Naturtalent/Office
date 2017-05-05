package it.naturtalent.e4.kontakte.ui;

import it.naturtalent.e4.kontakte.BankData;
import it.naturtalent.e4.kontakte.IKontakteDataModel;
import it.naturtalent.e4.kontakte.ui.dialogs.EditBankenDialog;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

public class BankDetails extends DefaultKontaktDetails
{
	private List<BankData>allBankData;
	
	private class BankDialogCellEditingSupport extends EditingSupport
	{
		protected final TableViewer viewer;
		public BankDialogCellEditingSupport(ColumnViewer viewer)
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
			BankData bankDAta = (BankData)element;
			return bankDAta.getIban();
		}

		@Override
		protected void setValue(Object element, Object editedValue)
		{		
			((BankData)element).setIban(editedValue.toString());
			viewer.update(element, null);			
			if(eventBroker != null)
				eventBroker.post(IKontakteDataModel.KONTAKT_EVENT_DETAILS_MODIFIED, element);
		}

	}

	public BankDetails(final Shell shell, Section section, FormToolkit formToolkit)
	{
		super(shell, section, formToolkit);	
		
		// IBAN Column 
		tableViewerColumn.setLabelProvider(new CellLabelProvider()
		{			
			@Override
			public void update(ViewerCell cell)
			{
				BankData bankData = (BankData) cell.getElement();				
				cell.setText(bankData.getIban());				
			}
		});
		tableViewerColumn.setEditingSupport(new BankDialogCellEditingSupport(tableViewer));
		tableViewerColumn.getColumn().setText(Messages.BankDetails_IBANLABEL);
		
		// BIC
		TableViewerColumn tableViewerColumnBic = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnBic = tableViewerColumnBic.getColumn();
		tblclmnBic.setWidth(100);		
		tableViewerColumnBic.setLabelProvider(new CellLabelProvider()
		{			
			@Override
			public void update(ViewerCell cell)
			{
				BankData bankData = (BankData) cell.getElement();				
				cell.setText(bankData.getBic());			
			}
		});
		tableViewerColumnBic.setEditingSupport(new BankDialogCellEditingSupport(tableViewer)
		{
			@Override
			protected Object getValue(Object element)
			{
				BankData bankDAta = (BankData)element;
				return (bankDAta.getBic() == null) ? "" : bankDAta.getBic(); //$NON-NLS-1$
			}

			@Override
			protected void setValue(Object element, Object editedValue)
			{
				String value = editedValue.toString();
				value = StringUtils.isEmpty(value) ? null : value;
				((BankData)element).setBic(value);
				
				viewer.update(element, null);				
				if(eventBroker != null)
					eventBroker.post(IKontakteDataModel.KONTAKT_EVENT_DETAILS_MODIFIED, element);
			}			
		});
		tableViewerColumnBic.getColumn().setText(Messages.BankDetails_BicLabel);
		
		// KtoNr
		TableViewerColumn tableViewerColumnKto = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnKto = tableViewerColumnKto.getColumn();
		tblclmnKto.setWidth(100);		
		tableViewerColumnKto.setLabelProvider(new CellLabelProvider()
		{			
			@Override
			public void update(ViewerCell cell)
			{
				BankData bankData = (BankData) cell.getElement();				
				cell.setText(bankData.getKontonr());			
			}
		});
		tableViewerColumnKto.setEditingSupport(new BankDialogCellEditingSupport(tableViewer)
		{
			@Override
			protected Object getValue(Object element)
			{
				BankData bankDAta = (BankData)element;
				return (bankDAta.getKontonr() == null) ? "" : bankDAta.getKontonr(); //$NON-NLS-1$
			}

			@Override
			protected void setValue(Object element, Object editedValue)
			{
				String value = editedValue.toString();
				value = StringUtils.isEmpty(value) ? null : value;

				((BankData)element).setKontonr(value);
				viewer.update(element, null);
				if(eventBroker != null)
					eventBroker.post(IKontakteDataModel.KONTAKT_EVENT_DETAILS_MODIFIED, element);
			}			
		});
		tableViewerColumnKto.getColumn().setText(Messages.BankDetails_KtoNr);
		
		
		// BLZ
		TableViewerColumn tableViewerColumnBlz = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnBlz = tableViewerColumnBlz.getColumn();
		tblclmnBlz.setWidth(100);		
		tableViewerColumnBlz.setLabelProvider(new CellLabelProvider()
		{			
			@Override
			public void update(ViewerCell cell)
			{
				BankData bankData = (BankData) cell.getElement();				
				cell.setText(bankData.getBlz());			
			}
		});
		tableViewerColumnBlz.setEditingSupport(new BankDialogCellEditingSupport(tableViewer)
		{
			@Override
			protected Object getValue(Object element)
			{
				BankData bankDAta = (BankData)element;
				return (bankDAta.getBlz() == null) ? "" : bankDAta.getBlz(); //$NON-NLS-1$
			}

			@Override
			protected void setValue(Object element, Object editedValue)
			{
				String value = editedValue.toString();
				value = StringUtils.isEmpty(value) ? null : value;
				((BankData)element).setBlz(value);
				
				viewer.update(element, null);
				if(eventBroker != null)
					eventBroker.post(IKontakteDataModel.KONTAKT_EVENT_DETAILS_MODIFIED, element);
			}			
		});
		tableViewerColumnBlz.getColumn().setText(Messages.BankDetails_BLZLabel);
		
		// Institut
		TableViewerColumn tableViewerColumnInst = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnInst = tableViewerColumnInst.getColumn();
		tblclmnInst.setWidth(100);		
		tableViewerColumnInst.setLabelProvider(new CellLabelProvider()
		{			
			@Override
			public void update(ViewerCell cell)
			{
				BankData bankData = (BankData) cell.getElement();				
				cell.setText(bankData.getInstitut());			
			}
		});
		tableViewerColumnInst.setEditingSupport(new BankDialogCellEditingSupport(tableViewer)
		{
			@Override
			protected Object getValue(Object element)
			{
				BankData bankDAta = (BankData)element;
				return (bankDAta.getInstitut() == null) ? "" : bankDAta.getInstitut(); //$NON-NLS-1$
			}

			@Override
			protected void setValue(Object element, Object editedValue)
			{
				String value = editedValue.toString();
				value = StringUtils.isEmpty(value) ? null : value;
				((BankData)element).setInstitut(value);
				
				viewer.update(element, null);
				if(eventBroker != null)
					eventBroker.post(IKontakteDataModel.KONTAKT_EVENT_DETAILS_MODIFIED, element);
			}			
		});
		tableViewerColumnInst.getColumn().setText(Messages.BankDetails_Institut);
	}
		
	public void setBankData(List<BankData> allBankData)
	{
		this.allBankData = allBankData;
		//this.allBankData = (this.allBankData != null) ? this.allBankData : new ArrayList<BankData>();
		tableViewer.setInput(this.allBankData);
	}

	@Override
	protected void doAdd()
	{
		EditBankenDialog dialog = new EditBankenDialog(shell); //$NON-NLS-1$
		dialog.create();
		BankData bankData = new BankData();
		dialog.setBankData(bankData);
		if (dialog.open() == EditBankenDialog.OK)
		{
			allBankData.add(bankData);
			tableViewer.add(bankData);
			section.setExpanded(true);
			tableViewer.setSelection(new StructuredSelection(bankData), true);	
			if(eventBroker != null)
				eventBroker.post(IKontakteDataModel.KONTAKT_EVENT_DETAILS_MODIFIED, bankData);
		}
	}

	@Override
	protected void doEdit()
	{
		IStructuredSelection selection = (IStructuredSelection) tableViewer.getSelection();
		Object selObj = selection.getFirstElement();
		if(selObj instanceof BankData)
		{
			BankData bankData = (BankData) selObj;
			EditBankenDialog dialog = new EditBankenDialog(shell); //$NON-NLS-1$
			dialog.create();
			dialog.setBankData(bankData);
			if (dialog.open() == EditBankenDialog.OK)
			{				
				tableViewer.update(bankData, null);
				if(eventBroker != null)
					eventBroker.post(IKontakteDataModel.KONTAKT_EVENT_DETAILS_MODIFIED, bankData);			
			}
		}
	}
	
	@Override
	protected void doDelete()
	{
		IStructuredSelection selection = (IStructuredSelection) tableViewer.getSelection();
		Object selObj = selection.getFirstElement();
		if(selObj instanceof BankData)
		{
			BankData bankData = (BankData) selObj;
			allBankData.remove(bankData);
			tableViewer.refresh();
			if(eventBroker != null)
				eventBroker.post(IKontakteDataModel.KONTAKT_EVENT_KONTAKTE_MODIFIED, bankData);			
		}
	}




}
