package it.naturtalent.e4.office.ui.letter;

import java.util.List;

import javax.annotation.PostConstruct;

import it.naturtalent.e4.office.letter.OfficeLetterModel;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.beans.BeansObservables;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.databinding.viewers.ObservableMapLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import it.naturtalent.e4.office.letter.TitelTextLetterRow;

public class LetterBetreffWizardGroup extends Group
{
	private DataBindingContext m_bindingContext;
	private Table tableBetreff;
	
	private OfficeLetterModel wizardOfficeModel;	
	private TableViewer tableBetreffViewer;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public LetterBetreffWizardGroup(Composite parent, int style)
	{
		super(parent, style);
		setText("Betreff");
		setLayout(new GridLayout(1, false));
		
		tableBetreffViewer = new TableViewer(this, SWT.BORDER | SWT.FULL_SELECTION);
		tableBetreff = tableBetreffViewer.getTable();
		tableBetreff.setLinesVisible(true);
		tableBetreff.setHeaderVisible(true);
		GridData gd_tableBetreff = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_tableBetreff.heightHint = 66;
		tableBetreff.setLayoutData(gd_tableBetreff);
		
		TableViewerColumn tableViewerColumnTitel = new TableViewerColumn(tableBetreffViewer, SWT.NONE);
		TableColumn tblclmnTitel = tableViewerColumnTitel.getColumn();
		tblclmnTitel.setWidth(102);
		tblclmnTitel.setText("Titel");
		tableViewerColumnTitel.setEditingSupport(new TitelCellEditingSupport(tableBetreffViewer));
		
		TableViewerColumn tableViewerColumnText = new TableViewerColumn(tableBetreffViewer, SWT.NONE);
		TableColumn tblclmnText = tableViewerColumnText.getColumn();
		tblclmnText.setWidth(327);
		tblclmnText.setText("Text");
		tableViewerColumnText.setEditingSupport(new TextCellEditingSupport(tableBetreffViewer));
		m_bindingContext = initDataBindings();


	}
	
	@PostConstruct
	protected void postConstruct (@Optional IEclipseContext context)
	{		
		wizardOfficeModel = context.get(OfficeLetterModel.class);
	}

	public void setOfficeModel(List<TitelTextLetterRow>betreffRows)
	{
		if ((wizardOfficeModel != null) && (betreffRows != null)
				&& (!betreffRows.isEmpty()))
		{
			if (m_bindingContext != null)
				m_bindingContext.dispose();
			
			wizardOfficeModel.setBetreff(betreffRows);
			m_bindingContext = initDataBindings();
		}
	}

	@Override
	protected void checkSubclass()
	{
		// Disable the check that prevents subclassing of SWT components
	}
	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();
		//
		ObservableListContentProvider listContentProvider = new ObservableListContentProvider();
		IObservableMap[] observeMaps = BeansObservables.observeMaps(listContentProvider.getKnownElements(), TitelTextLetterRow.class, new String[]{"titel", "text"});
		tableBetreffViewer.setLabelProvider(new ObservableMapLabelProvider(observeMaps));
		tableBetreffViewer.setContentProvider(listContentProvider);
		//
		IObservableList betreffProfileModelObserveList = BeanProperties.list("betreff").observe(wizardOfficeModel);
		tableBetreffViewer.setInput(betreffProfileModelObserveList);
		//
		return bindingContext;
	}
}
