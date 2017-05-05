package it.naturtalent.e4.kontakte.ui.parts;

import it.naturtalent.e4.kontakte.AddHelperEvent;
import it.naturtalent.e4.kontakte.ContactCategoryModel;
import it.naturtalent.e4.kontakte.IKontakteData;
import it.naturtalent.e4.kontakte.IKontakteDataModel;
import it.naturtalent.e4.kontakte.KontakteData;
import it.naturtalent.e4.kontakte.KontakteDataModel;
import it.naturtalent.e4.kontakte.ui.AbstractTableSorter;
import it.naturtalent.e4.kontakte.ui.Messages;
import it.naturtalent.e4.kontakte.ui.actions.AbstractContactAction;
import it.naturtalent.e4.kontakte.ui.actions.AddAction;
import it.naturtalent.e4.kontakte.ui.actions.CategoryFilterAction;
import it.naturtalent.e4.kontakte.ui.actions.ChangeDBAction;
import it.naturtalent.e4.kontakte.ui.actions.DeleteAction;
import it.naturtalent.e4.kontakte.ui.actions.SaveAction;
import it.naturtalent.icons.core.Icon;
import it.naturtalent.icons.core.IconSize;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.beans.BeansObservables;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

public class ContactMasterComposite extends Composite
{
	private DataBindingContext m_bindingContext;

	// das zugrundeliegende KontakteDataModell
	private KontakteDataModel kontakteModel;

	// das zugrundeliegende Kontakte Kategorien Modell
	private ContactCategoryModel contactCategoryModel;

	private ContactDetailsComposite contactDetailsComposite;
	
	private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());
	private Table table;
	private Text txtFilter;
	private TableViewer tableViewer;
	private TableColumn tblclmnName;
	private Label lblDBName;
	private ContactNameSorter nameSorter;
	private ESelectionService selectionService;
	private IEventBroker eventBroker;
	private String stgFilter;
	private List<String>categoriesList = new ArrayList<String>();
	
	private KontakteData [] selectedContacts;
	
	public enum ActionID
	{
		ADD_CONTACT,
		DELETE_CONTACT,
		FILTER_CATEGORY,
		SAVE_ACTION,
		CHANGE_DB; 
	}
	private Map<ActionID,AbstractContactAction>actionRegistry = new HashMap<ContactMasterComposite.ActionID, AbstractContactAction>();
	
	// Aenderungen im Modell ueberwachen
	private EventHandler modelEventHandler = new EventHandler()
	{
		@Override
		public void handleEvent(Event event)
		{
			// Aenderung in KontakteData
			if(StringUtils.equals(event.getTopic(), IKontakteDataModel.KONTAKT_EVENT_KONTAKTE_MODIFIED))
			{
				KontakteData kontakteData = (KontakteData) event.getProperty(IEventBroker.DATA);
				kontakteModel.updateData(kontakteData);
				return;
			}
			
			// Update
			if(StringUtils.equals(event.getTopic(), IKontakteDataModel.KONTAKT_EVENT_UPDATE_KONTAKT))
			{								
				updateWidgetState();
				return;
			}
			
			// add - ein Modell hat einen Eintrag hinzugefuegt
			if(StringUtils.equals(event.getTopic(), IKontakteDataModel.KONTAKT_EVENT_ADD_KONTAKT))
			{
				KontakteData data = (KontakteData) event.getProperty(IEventBroker.DATA);
				kontakteModel.loadModel();
				setKontakteModel(kontakteModel);
				tableViewer.setSelection(new StructuredSelection(data), true);
			}

			
			// Eintrag geloescht
			if(StringUtils.equals(event.getTopic(), IKontakteDataModel.KONTAKT_EVENT_DELETE_KONTAKT))
			{				
				KontakteData kontakteData = (KontakteData) event.getProperty(IEventBroker.DATA);				
				tableViewer.remove(kontakteData);
				
				// aktuelle Selektion anpassen
				if(tableViewer.getTable().getItemCount() > 0)
				{
					// Index(0) Item selektieren
					Object topItem = tableViewer.getTable().getItem(0).getData();
					tableViewer.setSelection(new StructuredSelection(topItem));
				}
				else
				{
					// Tabelle ist leer - Details disablen
					contactDetailsComposite.setKontakteData(null);
				}
								
				return;
			}
			
			// importierte Kontakte
			if(StringUtils.equals(event.getTopic(), IKontakteDataModel.KONTAKT_EVENT_IMPORTED_KONTAKTE))
			{				
				kontakteModel.loadModel();
				tableViewer.refresh();				
			}
			

			// Daten wurden persistent gespeichert
			if(StringUtils.equals(event.getTopic(), IKontakteDataModel.KONTAKT_EVENT_CONTACTPERSISTENCE))
			{
				kontakteModel.loadModel();
				tableViewer.refresh();
				updateWidgetState();
				return;
			}

			// Aenderung der Datenbank		
			if(StringUtils.equals(event.getTopic(), IKontakteDataModel.KONTAKT_EVENT_AFTER_CHANGEDB))
			{
				// Modell mit Ausrichtung auf die neue Datenbank
				KontakteDataModel eventModel = (KontakteDataModel) event.getProperty(IEventBroker.DATA);
				
				// hat unser Modell die Datenbank gewechselt
				if(eventModel == kontakteModel)
				{
					// Kategorie- und AddressAnredePreferenzen anpassen				
					contactDetailsComposite.setModel(eventModel);
					
					// selektierten KontaktDatas nochmals setzen damit geanderte Preferenzen greifen
					contactDetailsComposite
							.setKontakteData(contactDetailsComposite
									.getKontakteData());
				}
			}
			
			// Kategorienfilter
			if(StringUtils.equals(event.getTopic(), IKontakteDataModel.KONTAKT_EVENT_CONTACTFILTER))
			{
				Object obj = tableViewer.getLabelProvider();
				if (obj instanceof ContactObservableLabelProvider)
				{
					categoriesList.clear();
					String categoryNames = (String) event.getProperty(IEventBroker.DATA);
					ContactObservableLabelProvider labelProvider = (ContactObservableLabelProvider) obj;
					labelProvider.setFilterState(StringUtils.isNotEmpty(categoryNames));
					
					if(StringUtils.isNotEmpty(categoryNames))
					{
						String [] categories = StringUtils.split(categoryNames,",");
						for(String name : categories)
							categoriesList.add(name);
						
						tblclmnName.setImage(Icon.COMMAND_FILTER.getImage(IconSize._16x16_DefaultIconSize));
					}
					else tblclmnName.setImage(null);
										
					tableViewer.refresh();
					
					// aktuelle Selektion anpassen
					if(tableViewer.getTable().getItemCount() > 0)
					{
						// Index(0) Item selektieren
						Object topItem = tableViewer.getTable().getItem(0).getData();
						tableViewer.setSelection(new StructuredSelection(topItem));
					}
					else
					{
						// Tabelle ist leer - Details disablen
						contactDetailsComposite.setKontakteData(null);
					}
				}
			}

		
		}		
		
		
		public void handleEventOLD(Event event)
		{	
			// der momentan selektierte Datansatz
			/*
			KontakteData selectedKontakteData = null;
			IStructuredSelection selection = (IStructuredSelection) tableViewer.getSelection();
			if(selection != null)
			{
				Object selObj = selection.getFirstElement();
				if(selObj instanceof KontakteData)
					selectedKontakteData = (KontakteData) selObj;
			}
			*/
						
			// Aenderung in AddressData
			/*
			if(StringUtils.equals(event.getTopic(), IKontakteDataModel.KONTAKT_EVENT_ADDRESS_MODIFIED))
			{
				AddressData addressData = (AddressData) event.getProperty(IEventBroker.DATA);	
				
				KontakteData copySelectedData = selectedKontakteData.clone(); 				
				copySelectedData.setAddress(addressData);
				
				kontakteModel.updateData(copySelectedData);
				return;
			}
			*/			

			// Aenderung in KontakteData
			if(StringUtils.equals(event.getTopic(), IKontakteDataModel.KONTAKT_EVENT_KONTAKTE_MODIFIED))
			{
				KontakteData kontakteData = (KontakteData) event.getProperty(IEventBroker.DATA);
				kontakteModel.updateData(kontakteData);
				return;
			}
			
			// Update
			if(StringUtils.equals(event.getTopic(), IKontakteDataModel.KONTAKT_EVENT_UPDATE_KONTAKT))
			{				
				KontakteData kontakteData = (KontakteData) event.getProperty(IEventBroker.DATA);
				tableViewer.update(kontakteData,null);		
				updateWidgetState();
				return;
			}
			
			// Daten wurden persistent gespeichert
			if(StringUtils.equals(event.getTopic(), IKontakteDataModel.KONTAKT_EVENT_CONTACTPERSISTENCE))
			{
				updateWidgetState();
				return;
			}

			


			// Aenderung in Bankdaten
			/*
			if(StringUtils.equals(event.getTopic(), IKontakteDataModel.KONTAKT_EVENT_BANKDATA_MODIFIED))
			{
				BankData bankData = (BankData) event.getProperty(IEventBroker.DATA);				
				List<KontakteData>kontakte = kontakteModel.getKontakteData();
				for(KontakteData kontakt : kontakte)
				{
					List<BankData>bankDetails = kontakt.getBanks();
					if(bankDetails.contains(bankData))
					{
						kontakteModel.updateData(kontakt);
						return;
					}
				}
			}
			*/			
			
			// add - ein Modell hat einen Eintrag hinzugefuegt
			if(StringUtils.equals(event.getTopic(), IKontakteDataModel.KONTAKT_EVENT_ADD_KONTAKT))
			{		
				// der neue Eintrag
				AddHelperEvent addHelper = (AddHelperEvent) event.getProperty(IEventBroker.DATA);
				
				// hat unser Modell den Eintrag hinzugefuegt 
				if(addHelper.model.equals(kontakteModel))
				{
					// einen Refresh provozieren und Eintrag selektieren
					setKontakteModel(kontakteModel);
					tableViewer.setSelection(new StructuredSelection(
							addHelper.addedData), true);
					return;
				}
				
				// fremdes Modell aber gleiche Datenbank (Collectionname)
				if(StringUtils.equals(kontakteModel.getCollectionName(),addHelper.model.getCollectionName()))
				{						
					contactCategoryModel.loadContactCategories();
					kontakteModel.addData(addHelper.addedData);
				}
			}
			
			// Aenderung der Datenbank
			if(StringUtils.equals(event.getTopic(), IKontakteDataModel.KONTAKT_EVENT_AFTER_CHANGEDB))
			{
				// Modell mit geanderten Daten
				KontakteDataModel eventModel = (KontakteDataModel) event.getProperty(IEventBroker.DATA);
				
				// hat unser Modell die Datenbank gewechselt
				if(eventModel == kontakteModel)
				{
					// das geanderete Modell zuordnen - einen Refresh provozieren
					setKontakteModel(kontakteModel);
				}
			}
			
			// Eintrag geloescht
			if(StringUtils.equals(event.getTopic(), IKontakteDataModel.KONTAKT_EVENT_DELETE_KONTAKT))
			{				
				KontakteData kontakteData = (KontakteData) event.getProperty(IEventBroker.DATA);				
				tableViewer.remove(kontakteData);
				
				// aktuelle Selektion anpassen
				if(tableViewer.getTable().getItemCount() > 0)
				{
					// Index(0) Item selektieren
					Object topItem = tableViewer.getTable().getItem(0).getData();
					tableViewer.setSelection(new StructuredSelection(topItem));
				}
				else
				{
					// Tabelle ist leer - Details disablen
					contactDetailsComposite.setKontakteData(null);
				}
								
				return;
			}
			
			// eine Selection erzwingen (erforderliche nach dem Loeschen anderer Eintraege)
			if(StringUtils.equals(event.getTopic(), IKontakteDataModel.KONTAKT_EVENT_SETSELECTION))
			{
				KontakteData kontakteData = (KontakteData) event.getProperty(IEventBroker.DATA);
				tableViewer.setSelection(new StructuredSelection(kontakteData),true);
			}
			
			if(StringUtils.equals(event.getTopic(), IKontakteDataModel.KONTAKT_EVENT_CONTACTFILTER))
			{
				Object obj = tableViewer.getLabelProvider();
				if (obj instanceof ContactObservableLabelProvider)
				{
					categoriesList.clear();
					String categoryNames = (String) event.getProperty(IEventBroker.DATA);
					ContactObservableLabelProvider labelProvider = (ContactObservableLabelProvider) obj;
					labelProvider.setFilterState(StringUtils.isNotEmpty(categoryNames));
					
					if(StringUtils.isNotEmpty(categoryNames))
					{
						String [] categories = StringUtils.split(categoryNames,",");
						for(String name : categories)
							categoriesList.add(name);
					}
					
					tableViewer.refresh();
				}
			}
			
			// Daten wurden persistent gespeichert
			if(StringUtils.equals(event.getTopic(), IKontakteDataModel.KONTAKT_EVENT_CONTACTPERSISTENCE))
			{
				updateWidgetState();
			}
			
			
		}
	};

	private class NameFilter extends ViewerFilter
	{
		@Override
		public boolean select(Viewer viewer, Object parentElement,
				Object element)
		{			
			if (element instanceof IKontakteData)
			{
				KontakteData kontakt = (KontakteData) element;
				if(StringUtils.isNotEmpty(stgFilter))					
					return StringUtils.containsIgnoreCase(kontakt.getAddress().getName(), stgFilter);					
			}
			
			return true;
		}
	}

	private class CategoryFilter extends ViewerFilter
	{
		@Override
		public boolean select(Viewer viewer, Object parentElement,
				Object element)
		{			
			if (contactCategoryModel != null)
			{
				if (element instanceof IKontakteData)
				{
					KontakteData kontakt = (KontakteData) element;
					if (!categoriesList.isEmpty())
					{
						List<String> kontaktCatagories = contactCategoryModel
								.getContactCategoryNames(kontakt.getId());
						
						for (String check : kontaktCatagories)
							if (categoriesList.contains(check))
									return true;
						
						return false;
					}
				}
			}
			
			return true;
		}
	}
	private CategoryFilter categoryFilter = new CategoryFilter();
	
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public ContactMasterComposite(Composite parent, int style)
	{
		super(parent, style);
		addDisposeListener(new DisposeListener()
		{
			public void widgetDisposed(DisposeEvent e)
			{
				// EventBroker abmelden
				if(eventBroker != null)
					eventBroker.unsubscribe(modelEventHandler);

				toolkit.dispose();
			}
		});
		toolkit.adapt(this);
		toolkit.paintBordersFor(this);
		setLayout(new GridLayout(1, false));
		
		Section sctnMaster = toolkit.createSection(this, Section.TITLE_BAR);
		sctnMaster.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		sctnMaster.setBounds(0, 0, 117, 23);
		toolkit.paintBordersFor(sctnMaster);
		sctnMaster.setText("Kontakte");
		createSectionToolbar(sctnMaster);
		
		Composite composite = new Composite(sctnMaster, SWT.NONE);
		toolkit.adapt(composite);
		toolkit.paintBordersFor(composite);
		sctnMaster.setClient(composite);
		composite.setLayout(new GridLayout(1, false));
		
		txtFilter = toolkit.createText(composite, "", SWT.NONE);
		txtFilter.addModifyListener(new ModifyListener()
		{
			public void modifyText(ModifyEvent e)
			{
				stgFilter = txtFilter.getText();
				tableViewer.refresh();
			}
		});
		txtFilter.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		tableViewer = new TableViewer(composite, SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI);
		tableViewer.addSelectionChangedListener(new ISelectionChangedListener()
		{
			public void selectionChanged(SelectionChangedEvent event)
			{
				if(selectionService != null)
				{					
					IStructuredSelection selection = (IStructuredSelection) 
					        tableViewer.getSelection();
					if (selection.getFirstElement() instanceof KontakteData)
					{
						// an SelectionService weitergeben
						KontakteData kontakteDate = (KontakteData) selection
								.getFirstElement();
						selectionService.setSelection(kontakteDate);

						// die selektierten Eintraege an DeleteAktion weitergeben
						DeleteAction delAction = (DeleteAction) actionRegistry
								.get(ActionID.DELETE_CONTACT);												
						Object [] selectedObj = selection.toArray();
						selectedContacts = new KontakteData [selectedObj.length];
						System.arraycopy(selectedObj, 0, selectedContacts, 0,
								selectedObj.length);
						delAction.setSelectedKontakteData(selectedContacts);

					}					
				}
				updateWidgetState();
			}
		});
		//tableViewer.addFilter(new NameFilter());
		tableViewer.setFilters(new ViewerFilter []{new NameFilter(), categoryFilter});
		
		table = tableViewer.getTable();
		table.setHeaderVisible(true);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		toolkit.paintBordersFor(table);
		
		TableViewerColumn tableViewerNameColumn = new TableViewerColumn(tableViewer, SWT.NONE);		
		tblclmnName = tableViewerNameColumn.getColumn();		
		tblclmnName.setWidth(262);
		tblclmnName.setText("Name");
		nameSorter = new ContactNameSorter(tableViewer,tableViewerNameColumn);
		
		TableViewerColumn tableViewerOrtColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmOrt = tableViewerOrtColumn.getColumn();
		tblclmOrt.setWidth(100);
		tblclmOrt.setText("Ort");
		
		TableViewerColumn tableViewerStrasseColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnStrasse = tableViewerStrasseColumn.getColumn();
		tblclmnStrasse.setWidth(100);
		tblclmnStrasse.setText("Strasse");
		
		lblDBName = toolkit.createLabel(sctnMaster, "", SWT.NONE);
		sctnMaster.setDescriptionControl(lblDBName);
		m_bindingContext = initDataBindings();
		
	}
	
	private void createSectionToolbar(Section section)
	{
		AbstractContactAction action;
		ToolBarManager toolBarManager = new ToolBarManager(SWT.FLAT);
		ToolBar toolbar = toolBarManager.createControl(section);		
		section.setTextClient(toolbar);
		
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
		action = new AddAction();
		toolBarManager.add(action);
		actionRegistry.put(ActionID.ADD_CONTACT, action);
		
		// Edit Action
		//toolBarManager.add(actionEdit);
		
		// Delete Actions to the tool bar
		action = new DeleteAction();
		toolBarManager.add(action);
		actionRegistry.put(ActionID.DELETE_CONTACT, action);
		
		
		// Datenbank wechseln 
		action = new ChangeDBAction();				
		toolBarManager.add(action);
		actionRegistry.put(ActionID.CHANGE_DB, action);

		// Filter
		action = new CategoryFilterAction();				
		toolBarManager.add(action);
		actionRegistry.put(ActionID.FILTER_CATEGORY, action);
		
		// speichern
		action = new SaveAction();	
		action.setEnabled(false);
		toolBarManager.add(action);
		actionRegistry.put(ActionID.SAVE_ACTION, action);
		


		toolBarManager.update(true);
	}
	
	private void updateWidgetState()
	{
		boolean state;
		
		SaveAction saveAction = (SaveAction) actionRegistry.get(ActionID.SAVE_ACTION);	
		state = false;
		if((kontakteModel != null) && (contactCategoryModel != null))
		{
			state = (kontakteModel.isModified() || contactCategoryModel.isModified());
			saveAction.setEnabled(state);
			if(eventBroker != null)
				eventBroker.post(IKontakteDataModel.KONTAKT_EVENT_SET_DIRTYABLE, state);
		}
				
		DeleteAction delAction = (DeleteAction) actionRegistry
				.get(ActionID.DELETE_CONTACT);
		
		delAction.setEnabled(false);
		IStructuredSelection selection = (IStructuredSelection) 
		        tableViewer.getSelection();
		if (selection.getFirstElement() instanceof KontakteData)
		{
			delAction.setEnabled(true);
		}
	}
	
	public KontakteDataModel getKontakteModel()
	{
		return kontakteModel;
	}

	/**
	 * Ein Modell mit den Kontaktedaten zuordnen. Die Funktion provoziert ein refresh des Viewers
	 * via 'databinding'. Das Modell wird an alle Aktionen weitergegeben.
	 * 
	 * @param kontakteModel
	 */
	public void setKontakteModel(KontakteDataModel kontakteModel)
	{
		if(m_bindingContext != null)
			m_bindingContext.dispose();

		this.kontakteModel = kontakteModel;		
		if(kontakteModel != null)
		{
			m_bindingContext = initDataBindings();
						
			// die zugeordneten Modelle an die angeschlossenen Aktionen weitergeben
			Set<ActionID> keySet = actionRegistry.keySet();
			for(ActionID id : keySet)
			{
				AbstractContactAction action = actionRegistry.get(id);
				action.setKontakteDataModel(kontakteModel);				
			}	
			
			// Name der Datenbank
			String dbLabel = kontakteModel.getModelFactory()
					.getCollectionLabel(kontakteModel.getCollectionName());
			lblDBName.setText(Messages.bind(
					Messages.KontakteView_sctnNewSection_description,
					dbLabel));
			
			//categoryFilter.initCategoryModel(kontakteModel);

			// aufsteigend sortieren
			nameSorter.setSorter(nameSorter, AbstractTableSorter.ASC);

			// ersten Tabelleneintrag selektieren
			Object obj = tableViewer.getElementAt(0);
			if (obj != null)
				tableViewer.setSelection(new StructuredSelection(
						tableViewer.getElementAt(0)),true);
			else
			{
				if(contactDetailsComposite != null)
					contactDetailsComposite.setKontakteData(null);
			}

			/*
			else
			{
				Table table = tableViewer.getTable();
				table.setTopIndex(0);
				table.setSelection(0);
				table.deselectAll();				
			}
			*/

		}		
		
	}
	
	public void setCategoryModel(ContactCategoryModel contactCategoryModel)
	{
		this.contactCategoryModel = contactCategoryModel;
		
		// das zugeordneten Kategoriemodelle an die angeschlossenen Aktionen weitergeben
		Set<ActionID> keySet = actionRegistry.keySet();
		for(ActionID id : keySet)
		{
			AbstractContactAction action = actionRegistry.get(id);			
			action.setContactCategoryModel(contactCategoryModel);
		}
		
		// disable ChangeDBAction, wenn keine Auswahl besteht
		if(kontakteModel.getModelFactory().getCollectionNames().size() < 2)
			actionRegistry.get(ActionID.CHANGE_DB).setEnabled(false);
	}
	
	public void setEventBroker(IEventBroker eventBroker)
	{
		this.eventBroker = eventBroker;
		
		// Broker an die Aktionen weitergeben
		Set<ActionID> keySet = actionRegistry.keySet();
		for(ActionID id : keySet)
		{
			AbstractContactAction action = actionRegistry.get(id);
			action.setEventBroker(eventBroker);				
		}	

		eventBroker.subscribe(IKontakteDataModel.KONTAKT_EVENT+"*",modelEventHandler);
	}

	public ContactNameSorter getNameSorter()
	{
		return nameSorter;
	}

	public void setSelectionService(ESelectionService selectionService)
	{
		this.selectionService = selectionService;
	}
	
	public TableViewer getTableViewer()
	{
		return tableViewer;
	}
	
	public Map<ActionID, AbstractContactAction> getActionRegistry()
	{
		return actionRegistry;
	}
	
	public KontakteData[] getSelectedContacts()
	{
		return selectedContacts;
	}

	public void setContactDetailsComposite(
			ContactDetailsComposite contactDetailsComposite)
	{
		this.contactDetailsComposite = contactDetailsComposite;
	}

	@Override
	public boolean setFocus()
	{
		// TODO Auto-generated method stub
		return super.setFocus();
	}
	
	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();
		//
		ObservableListContentProvider listContentProvider = new ObservableListContentProvider();
		IObservableMap[] observeMap = BeansObservables.observeMaps(listContentProvider.getKnownElements(), KontakteData.class, new String[]{"address.name", "address.ort", "address.strasse"});
		tableViewer.setLabelProvider(new ContactObservableLabelProvider(observeMap));
		tableViewer.setContentProvider(listContentProvider);
		//
		IObservableList kontakteDataKontakteModelObserveList = BeanProperties.list("kontakteData").observe(kontakteModel);
		tableViewer.setInput(kontakteDataKontakteModelObserveList);
		//
		return bindingContext;
	}
}
