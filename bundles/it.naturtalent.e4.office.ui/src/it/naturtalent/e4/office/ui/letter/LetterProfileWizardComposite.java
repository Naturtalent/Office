package it.naturtalent.e4.office.ui.letter;

import java.util.List;

import it.naturtalent.e4.office.letter.OfficeLetterProfile;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.beans.BeansObservables;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.databinding.viewers.ObservableMapLabelProvider;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

import it.naturtalent.e4.office.letter.TitelTextLetterRow;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.core.databinding.beans.IBeanValueProperty;
import org.eclipse.jface.databinding.viewers.ObservableValueEditingSupport;


public class LetterProfileWizardComposite extends Composite
{
	private DataBindingContext m_bindingContext;
	
	
	
	private OfficeLetterProfile profileModel;
	
	private final FormToolkit formToolkit = new FormToolkit(Display.getDefault());
	private Section sctnFooter;
	private Composite compositeFooter;
	private Section sctnReferenz;
	private Composite compositeReferenz;
	private Text txtHeader;
	private Composite composite;
	private Table tableReference;
	private Table footerTable;
	private TableColumn tblclmnBezeichnung;
	private TableViewer referenceViewer;
	private Section sctnSignature;
	private Composite compositeSignature;
	private Text txtSignature;
	private Section sctnAbsender;
	private Composite compositeAbsender;

	private TableViewer absenderViewer;
	private TableViewer praesentationViewer;
	private TableViewer footerViewer;
	private Section sctnPraesentation;
	private Composite compositePraesentation;
	
	// Validator fuer InputDialog
	IInputValidator validator = new IInputValidator()
	{
		public String isValid(String string)
		{
			if (StringUtils.isEmpty(string))						
				return "leere Eingabe";
			
			return null;
		}
	};



	private TableViewer signatureViewer;
	private TableViewerColumn tableViewerColumnRefTitel;
	private TableViewerColumn tableViewerColumnRefText;
	
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public LetterProfileWizardComposite(Composite parent, int style)
	{
		super(parent, style);
		setLayout(new GridLayout(1, false));
		
		Section sctnHeader = formToolkit.createSection(this, Section.TWISTIE | Section.TITLE_BAR);
		sctnHeader.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		formToolkit.paintBordersFor(sctnHeader);
		sctnHeader.setText("Kopf");
		//new Label(this, SWT.NONE);
		
		Composite compositeHeader = new Composite(sctnHeader, SWT.NONE);
		formToolkit.adapt(compositeHeader);
		formToolkit.paintBordersFor(compositeHeader);
		sctnHeader.setClient(compositeHeader);
		compositeHeader.setLayout(new GridLayout(1, false));
		
		txtHeader = formToolkit.createText(compositeHeader, "", SWT.MULTI);
		GridData gd_txtHeader = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_txtHeader.heightHint = 55;
		txtHeader.setLayoutData(gd_txtHeader);
		
		// Absender
		sctnAbsender = formToolkit.createSection(this, Section.TWISTIE | Section.TITLE_BAR);
		GridData gd_sctnAbsender = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_sctnAbsender.heightHint = 79;
		sctnAbsender.setLayoutData(gd_sctnAbsender);
		formToolkit.paintBordersFor(sctnAbsender);
		sctnAbsender.setText("Absender");
		sctnAbsender.setExpanded(true);
		
		compositeAbsender = new Composite(sctnAbsender, SWT.NONE);
		formToolkit.adapt(compositeAbsender);
		formToolkit.paintBordersFor(compositeAbsender);
		sctnAbsender.setClient(compositeAbsender);
		compositeAbsender.setLayout(new GridLayout(1, false));
		
		absenderViewer = new TableViewer(compositeAbsender, SWT.BORDER | SWT.FULL_SELECTION);
		Table absenderTable = absenderViewer.getTable();
		absenderTable.setLinesVisible(true);
		absenderTable.setHeaderVisible(true);
		GridData gd_tableAbsender = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_tableAbsender.heightHint = 83;
		absenderTable.setLayoutData(gd_tableAbsender);
		absenderTable.setBounds(10, 0, 85, 85);
		formToolkit.paintBordersFor(absenderTable);
			
		TableViewerColumn tableViewerColumnAbsTitel = new TableViewerColumn(absenderViewer, SWT.NONE);
		TableColumn tblclmnAbsTitel = tableViewerColumnAbsTitel.getColumn();
		tblclmnAbsTitel.setWidth(400);
		tblclmnAbsTitel.setText("Absender");
		tableViewerColumnAbsTitel.setEditingSupport(new TitelCellEditingSupport(absenderViewer));
		
		// Praesentation
		sctnPraesentation = formToolkit.createSection(this, Section.TWISTIE | Section.TITLE_BAR);
		GridData gd_sctnPraesentation = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_sctnPraesentation.heightHint = 51;
		sctnPraesentation.setLayoutData(gd_sctnPraesentation);
		formToolkit.paintBordersFor(sctnPraesentation);
		sctnPraesentation.setText("Pr\u00E4sentation");
		
		compositePraesentation = new Composite(sctnPraesentation, SWT.NONE);
		formToolkit.adapt(compositePraesentation);
		formToolkit.paintBordersFor(compositePraesentation);
		sctnPraesentation.setClient(compositePraesentation);
		compositePraesentation.setLayout(new GridLayout(1, false));
				
		praesentationViewer = new TableViewer(compositePraesentation, SWT.BORDER | SWT.FULL_SELECTION);
		Table praesentationTabel = praesentationViewer.getTable(); 
		GridData gd_praesentationTabel = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_praesentationTabel.heightHint = 131;
		praesentationTabel.setLayoutData(gd_praesentationTabel);
		praesentationTabel.setLinesVisible(true);
		praesentationTabel.setHeaderVisible(true);
		
		TableViewerColumn praesentationViewerColumnTitel = new TableViewerColumn(praesentationViewer, SWT.NONE);
		TableColumn praesentationTableColumnTitel = praesentationViewerColumnTitel.getColumn();
		praesentationTableColumnTitel.setWidth(189);
		praesentationTableColumnTitel.setText("Titel");
		praesentationViewerColumnTitel.setEditingSupport(new TitelCellEditingSupport(praesentationViewer));
		 				
		TableViewerColumn praesentationViewerColumnText = new TableViewerColumn(praesentationViewer, SWT.NONE);
		TableColumn praesentationTableColumnText = praesentationViewerColumnText.getColumn();
		praesentationTableColumnText.setWidth(222);
		praesentationTableColumnText.setText("Text");		
		praesentationViewerColumnText.setEditingSupport(new TextCellEditingSupport(praesentationViewer));

		// Referenz
		sctnReferenz = formToolkit.createSection(this, Section.TWISTIE | Section.TITLE_BAR);
		sctnReferenz.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		formToolkit.paintBordersFor(sctnReferenz);
		sctnReferenz.setText("Referenz");
		sctnReferenz.setExpanded(true);
		
		compositeReferenz = new Composite(sctnReferenz, SWT.NONE);
		formToolkit.adapt(compositeReferenz);
		formToolkit.paintBordersFor(compositeReferenz);
		sctnReferenz.setClient(compositeReferenz);
		compositeReferenz.setLayout(new GridLayout(1, false));
		
		referenceViewer = new TableViewer(compositeReferenz, SWT.BORDER | SWT.FULL_SELECTION);
		tableReference = referenceViewer.getTable();
		tableReference.setLinesVisible(true);
		tableReference.setHeaderVisible(true);
		GridData gd_tableReference = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_tableReference.heightHint = 91;
		tableReference.setLayoutData(gd_tableReference);
		tableReference.setBounds(10, 0, 85, 85);
		formToolkit.paintBordersFor(tableReference);
		
		tableViewerColumnRefTitel = new TableViewerColumn(referenceViewer, SWT.NONE);
		TableColumn tblclmnRefTitel = tableViewerColumnRefTitel.getColumn();
		tblclmnRefTitel.setWidth(189);
		tblclmnRefTitel.setText("Titel");
		tableViewerColumnRefTitel.setEditingSupport(new TitelCellEditingSupport(referenceViewer));
		 				
		tableViewerColumnRefText = new TableViewerColumn(referenceViewer, SWT.NONE);
		TableColumn tblclmnRefText = tableViewerColumnRefText.getColumn();
		tblclmnRefText.setWidth(222);
		tblclmnRefText.setText("Text");
		tableViewerColumnRefText.setEditingSupport(new TextCellEditingSupport(referenceViewer));
		
		// Footer
		sctnFooter = formToolkit.createSection(this, Section.TWISTIE | Section.TITLE_BAR);
		sctnFooter.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		formToolkit.paintBordersFor(sctnFooter);
		sctnFooter.setText("Fuß");
		sctnFooter.setExpanded(true);
		
		compositeFooter = new Composite(sctnFooter, SWT.NONE);
		formToolkit.adapt(compositeFooter);
		formToolkit.paintBordersFor(compositeFooter);
		sctnFooter.setClient(compositeFooter);
		compositeFooter.setLayout(new GridLayout(1, false));
		
		footerViewer = new TableViewer(compositeFooter, SWT.BORDER | SWT.FULL_SELECTION);
		footerTable = footerViewer.getTable();
		footerTable.setLinesVisible(true);
		footerTable.setHeaderVisible(true);
		GridData gd_tableFooter = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_tableFooter.heightHint = 79;
		footerTable.setLayoutData(gd_tableFooter);
		footerTable.setBounds(10, 0, 85, 85);
		formToolkit.paintBordersFor(footerTable);
		
		TableViewerColumn tableViewerColumnFooterTitel = new TableViewerColumn(footerViewer, SWT.NONE);
		TableColumn tblclmnFooterTitel = tableViewerColumnFooterTitel.getColumn();
		tblclmnFooterTitel.setWidth(200);
		tblclmnFooterTitel.setText("Titel");
		tableViewerColumnFooterTitel.setEditingSupport(new TitelCellEditingSupport(footerViewer));

		TableViewerColumn tableViewerColumnFooterText = new TableViewerColumn(footerViewer, SWT.NONE);
		TableColumn tblclmnFooterText = tableViewerColumnFooterText.getColumn();
		tblclmnFooterText.setWidth(400);
		tblclmnFooterText.setText("Text");
		tableViewerColumnFooterText.setEditingSupport(new TextCellEditingSupport(footerViewer));
		
		sctnSignature = formToolkit.createSection(this, Section.TWISTIE | Section.TITLE_BAR);
		GridData gd_sctnSignature = new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1);
		gd_sctnSignature.heightHint = 84;
		sctnSignature.setLayoutData(gd_sctnSignature);
		formToolkit.paintBordersFor(sctnSignature);
		sctnSignature.setText("Signatur");
		sctnSignature.setExpanded(true);
		
		compositeSignature = new Composite(sctnSignature, SWT.NONE);
		formToolkit.adapt(compositeSignature);
		formToolkit.paintBordersFor(compositeSignature);
		sctnSignature.setClient(compositeSignature);
		compositeSignature.setLayout(new GridLayout(1, false));
		
		signatureViewer = new TableViewer(compositeSignature, SWT.BORDER | SWT.FULL_SELECTION);
		Table signatureTable = signatureViewer.getTable();
		signatureTable.setLinesVisible(true);
		signatureTable.setHeaderVisible(true);
		GridData gd_tablesignature = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_tablesignature.heightHint = 83;
		signatureTable.setLayoutData(gd_tablesignature);
		signatureTable.setBounds(10, 0, 85, 85);
		formToolkit.paintBordersFor(signatureTable);
		
		TableViewerColumn tableViewerColumnSigTitel = new TableViewerColumn(signatureViewer, SWT.NONE);
		TableColumn tblclmnSigTitel = tableViewerColumnSigTitel.getColumn();
		tblclmnSigTitel.setWidth(400);
		tblclmnSigTitel.setText("Signatur");
		tableViewerColumnSigTitel.setEditingSupport(new TitelCellEditingSupport(signatureViewer));

		/*
		txtSignature = formToolkit.createText(compositeSignature, "", SWT.MULTI);		
		GridData gd_txtSignature = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_txtSignature.heightHint = 61;
		txtSignature.setLayoutData(gd_txtSignature);
		*/
		
		/*
		OfficeProfile test = new OfficeProfile();
		initOfficeProfile(test);
		setProfileModel(test);
		*/
		
		
		m_bindingContext = initDataBindings();
		
		

	}
	
	@Override
	protected void checkSubclass()
	{
		// Disable the check that prevents subclassing of SWT components
	}
	
	private int getColumunIdx(Table table, MouseEvent e)
	{
		Point pt = new Point(e.x, e.y);
		TableItem item = table.getItem(pt);
		if (item != null)
		{
			for (int col = 0; col < table.getColumnCount(); col++)
			{
				Rectangle rect = item.getBounds(col);
				if (rect.contains(pt))
					return col;
			}
		}
		
		return (-1);
	}
	
	/*
	@Override
	public void dispose()
	{
		SWTResourceManager.disposeColors();
		SWTResourceManager.disposeImages();
		super.dispose();
	}
	*/
	
	

	public OfficeLetterProfile getProfileModel()
	{
		return profileModel;
	}

	public void setProfileModel(OfficeLetterProfile profileModel)
	{
		if (profileModel != null)
		{
			if (m_bindingContext != null)
				m_bindingContext.dispose();
			
			this.profileModel = profileModel;
			m_bindingContext = initDataBindings();
		}
	}
	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();
		//
		IObservableValue observeTextTxtHeaderObserveWidget = WidgetProperties.text(SWT.Modify).observe(txtHeader);
		IObservableValue headerProfileModelObserveValue = BeanProperties.value("header").observe(profileModel);
		bindingContext.bindValue(observeTextTxtHeaderObserveWidget, headerProfileModelObserveValue, null, null);
		//
		ObservableListContentProvider listContentProvider_1 = new ObservableListContentProvider();
		IObservableMap[] observeMaps_1 = BeansObservables.observeMaps(listContentProvider_1.getKnownElements(), TitelTextLetterRow.class, new String[]{"titel", "text"});
		absenderViewer.setLabelProvider(new ObservableMapLabelProvider(observeMaps_1));
		absenderViewer.setContentProvider(listContentProvider_1);
		//
		IObservableList absenderProfileModelObserveList = BeanProperties.list("absender").observe(profileModel);
		absenderViewer.setInput(absenderProfileModelObserveList);
		//
		ObservableListContentProvider listContentProvider_2 = new ObservableListContentProvider();
		IObservableMap[] observeMaps_2 = BeansObservables.observeMaps(listContentProvider_2.getKnownElements(), TitelTextLetterRow.class, new String[]{"titel", "text"});
		praesentationViewer.setLabelProvider(new ObservableMapLabelProvider(observeMaps_2));
		praesentationViewer.setContentProvider(listContentProvider_2);
		//
		IObservableList praesentationProfileModelObserveList = BeanProperties.list("praesentation").observe(profileModel);
		praesentationViewer.setInput(praesentationProfileModelObserveList);
		//
		ObservableListContentProvider listContentProvider = new ObservableListContentProvider();
		IObservableMap[] observeMaps = BeansObservables.observeMaps(listContentProvider.getKnownElements(), TitelTextLetterRow.class, new String[]{"titel", "text"});
		referenceViewer.setLabelProvider(new ObservableMapLabelProvider(observeMaps));
		referenceViewer.setContentProvider(listContentProvider);
		//
		IObservableList referencesProfileModelObserveList = BeanProperties.list("references").observe(profileModel);
		referenceViewer.setInput(referencesProfileModelObserveList);
		//
		ObservableListContentProvider listContentProvider_3 = new ObservableListContentProvider();
		IObservableMap[] observeMaps_3 = BeansObservables.observeMaps(listContentProvider_3.getKnownElements(), TitelTextLetterRow.class, new String[]{"titel", "text"});
		footerViewer.setLabelProvider(new ObservableMapLabelProvider(observeMaps_3));
		footerViewer.setContentProvider(listContentProvider_3);
		//
		IObservableList footerProfileModelObserveList = BeanProperties.list("footer").observe(profileModel);
		footerViewer.setInput(footerProfileModelObserveList);
		//
		ObservableListContentProvider listContentProvider_4 = new ObservableListContentProvider();
		IObservableMap[] observeMaps_4 = BeansObservables.observeMaps(listContentProvider_4.getKnownElements(), TitelTextLetterRow.class, new String[]{"titel", "text"});
		signatureViewer.setLabelProvider(new ObservableMapLabelProvider(observeMaps_4));
		signatureViewer.setContentProvider(listContentProvider_4);
		//
		IObservableList signatureProfileModelObserveList = BeanProperties.list("signature").observe(profileModel);
		signatureViewer.setInput(signatureProfileModelObserveList);
		//
		return bindingContext;
	}
}
