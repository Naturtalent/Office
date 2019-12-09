package it.naturtalent.e4.office.ui.renderer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emf.ecp.view.spi.treemasterdetail.ui.swt.TreeMasterDetailSWTRenderer;
import org.eclipse.emf.ecp.view.treemasterdetail.model.VTreeMasterDetail;
import org.eclipse.emf.ecp.view.treemasterdetail.ui.swt.internal.RootObject;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.DeleteCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridCell;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
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
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import it.naturtalent.e4.office.ui.OfficeUtils;
import it.naturtalent.e4.office.ui.dialogs.AdresseDialog;
import it.naturtalent.e4.office.ui.dialogs.KontaktDialog;
import it.naturtalent.icons.core.Icon;
import it.naturtalent.icons.core.IconSize;
import it.naturtalent.office.model.address.AddressPackage;
import it.naturtalent.office.model.address.Adresse;
import it.naturtalent.office.model.address.Kontakt;


/**
 * Angepasster Renderer der den Masterbereich des Containers 'Kontakte' repraesentiert.
 * 
 * Fuegt ein Textfeld im Kopf der Tabelle ein mit dem nach einem Kontaktnamen gesucht werden kann.
 * 
 * 
 * @author dieter
 *
 */
public class KontakteMasterDetailRenderer extends TreeMasterDetailSWTRenderer
{

	private SashForm sash; 
	
	private TreeViewer treeViewer;
	
	private Text textFilter;
	private String stgFilter;
	
	// eigenes Kontectmenue
	private Menu contextMenu;
	private MenuManager menuMgr;
	
	private EObject container;
	private EditingDomain domain;
	private EReference eReference;
	
	public enum MenueID
	{
		DelKontakID("Kontakt löschen"),
		AddKontakID("Kontakt hinzufügen"),
		DelAddressID("Adresse löschen"),
		AddAddressID("Adresse hinzufügen");
		
		private MenueID()
		{			
		}
		
		public String text;
		
		private MenueID(String text) {
	        this.text = text;
	    }
	}	
	private Map<MenueID, Action>menuMap = new HashMap<MenueID, Action>();
	
	
	// Ein Filter fuer die Kontaktnamen
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
		
		container = OfficeUtils.findObject(AddressPackage.eINSTANCE.getKontakte());
		domain = AdapterFactoryEditingDomain.getEditingDomainFor(container);	
		eReference = AddressPackage.eINSTANCE.getKontakte_Kontakte();
	
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
	
	/*
	 * Einblenden eines Eingabefelds oberhalb des Kontaktetrees zur Eingabe der Kontaktnamenpattern.
	 * Zusammen mit dem Filter kann hiermit nach Kontakten gefiltert werden.
	 *  
	 */
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

	// eigene Eigenschaften (Sortierer und Filter) zum 'treeviewer' hinzufuegen
	@Override
	protected TreeViewer createMasterTree(Composite masterPanel)
	{		
		treeViewer = super.createMasterTree(masterPanel);
		
		// Sortierer
		treeViewer.setComparator(new ViewerComparator());
		
		// Filter Kontaktnamen
		treeViewer.setFilters(new ViewerFilter []{new NameFilter()});
					
		// Listener steuert KontextMenue in Abhaengigkeit der Selektion
		contextMenu = new Menu(treeViewer.getTree());
		treeViewer.addSelectionChangedListener(new ISelectionChangedListener()
		{			
			@Override
			public void selectionChanged(SelectionChangedEvent event)
			{
				IStructuredSelection selection = treeViewer.getStructuredSelection();				
				if ((selection != null) && (!selection.isEmpty()))
				{
					EObject root = ((RootObject) treeViewer.getInput()).getRoot();
					//contextMenu.getItem(1).setEnabled(!selection.toList().contains(root));
								
					menuMgr.removeAll();
					if(selection.toList().contains(root))
					{
						menuMgr.add(menuMap.get(MenueID.AddKontakID));
					}
					else
					{	
						Object selObject = selection.getFirstElement();
						if (selObject instanceof Kontakt)
						{
							Kontakt kontakt = (Kontakt) selObject;
							menuMgr.add(menuMap.get(MenueID.DelKontakID));							
							if(kontakt.getAdresse() == null)
								menuMgr.add(menuMap.get(MenueID.AddAddressID));							
						}
						else
						{
							if (selObject instanceof Adresse)
							{
								menuMgr.add(menuMap.get(MenueID.DelAddressID));								
							}							
						}
					}
				}
			}
		});

		// Kontextmenu einrichten
		setContextMenu();
		
		return treeViewer;
	}
	
	private void setContextMenu()
	{
		menuMgr = new MenuManager();
		menuMgr.createContextMenu(treeViewer.getControl());
		
		Menu menu = menuMgr.createContextMenu(treeViewer.getControl());
		treeViewer.getControl().setMenu(menu);
		
		delKontaktAction();
		addKontaktAction();
		delAddressAction();
		addAddressAction();
	}	
	
	
	private void delKontaktAction()
	{
		Action delAction = new Action()
		{
			@Override
			public void run()
			{
				IStructuredSelection selection = treeViewer.getStructuredSelection();		
				Object selObj = selection.getFirstElement();
				if (selObj instanceof Kontakt)
				{
					Kontakt kontakt = (Kontakt) selObj;
					if (MessageDialog.openQuestion(
							Display.getDefault().getActiveShell(), "Kontakt",
							"den selektierten Kontakt löschen")) //$NON-NLS-N$
					{								
						Command delCommand = DeleteCommand.create(domain, kontakt);
						if(delCommand.canExecute())	
							domain.getCommandStack().execute(delCommand);	
						treeViewer.refresh();								
					}							
				}				

			}		
		};
				
		delAction.setId(MenueID.DelKontakID.text);
		delAction.setText(MenueID.DelKontakID.text);
		delAction.setImageDescriptor(Icon.COMMAND_DELETE.getImageDescriptor(IconSize._16x16_DefaultIconSize));
		menuMap.put(MenueID.DelKontakID, delAction);
	}
	
	private void addKontaktAction()
	{
		Action addAction = new Action()
		{
			@Override
			public void run()
			{
				KontaktDialog kontaktDialog = new KontaktDialog(Display.getDefault().getActiveShell());
				if(kontaktDialog.open() == KontaktDialog.OK)
				{
					textFilter.setText("");
					
					// den neuen Kontakt selektieren
					Kontakt addedKontakt = kontaktDialog.getNewKontakt();					
					treeViewer.refresh();
					treeViewer.setSelection(new StructuredSelection(addedKontakt),true);					
				}
			}		
		};
				
		addAction.setId(MenueID.AddKontakID.text);
		addAction.setText(MenueID.AddKontakID.text);
		addAction.setImageDescriptor(Icon.COMMAND_ADD.getImageDescriptor(IconSize._16x16_DefaultIconSize));
		menuMap.put(MenueID.AddKontakID, addAction);
	}

	// Aktion Adresse loeschen
	private void delAddressAction()
	{
		Action delAction = new Action()
		{
			@Override
			public void run()
			{
				IStructuredSelection selection = treeViewer.getStructuredSelection();		
				Object selObj = selection.getFirstElement();
				if (selObj instanceof Adresse)
				{
					Adresse adresse = (Adresse) selObj;
					if (MessageDialog.openQuestion(
							Display.getDefault().getActiveShell(), "Adresse",
							"die selektierte Adresse löschen")) //$NON-NLS-N$
					{		
						EObject kontakt = adresse.eContainer();
						Command delCommand = DeleteCommand.create(domain, adresse);
						if(delCommand.canExecute())	
							domain.getCommandStack().execute(delCommand);	
						treeViewer.update(kontakt, null);
						treeViewer.setSelection(new StructuredSelection(kontakt),true);		
					}							
				}				
			}		
		};
				
		delAction.setId(MenueID.DelAddressID.text);
		delAction.setText(MenueID.DelAddressID.text);
		delAction.setImageDescriptor(Icon.COMMAND_DELETE.getImageDescriptor(IconSize._16x16_DefaultIconSize));
		menuMap.put(MenueID.DelAddressID, delAction);
	}
	
	// Aktion Adresse hinzufuegen
	private void addAddressAction()
	{
		Action addAction = new Action()
		{
			@Override
			public void run()
			{
				IStructuredSelection selection = treeViewer.getStructuredSelection();		
				Object selObj = selection.getFirstElement();
				if (selObj instanceof Kontakt)
				{
					Kontakt kontakt = (Kontakt) selObj;
					
					AdresseDialog adressDialog = new AdresseDialog(Display.getDefault().getActiveShell(), kontakt);
					if (adressDialog.open() == KontaktDialog.OK)
					{
						// neue Adresse zum selektierten Kontakt hinzufuegen
						kontakt.setAdresse(adressDialog.getNewAdresse());

						// Kontakt updaten						
						treeViewer.update(kontakt, null);
					}
				}
			}
		};
				
		addAction.setId(MenueID.AddAddressID.text);
		addAction.setText(MenueID.AddAddressID.text);
		addAction.setImageDescriptor(Icon.COMMAND_ADD.getImageDescriptor(IconSize._16x16_DefaultIconSize));
		menuMap.put(MenueID.AddAddressID, addAction);
	}


	/**
	 * Eine von aussen angestossene Refreshanforderung (KontakteImportDialog) realisieren.
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
	 * Eine von aussen angestossene Selektionsanforderung realisieren.
	 * 
	 * @param Kontakt
	 */
	@Inject
	@Optional
	public void handleSelectionRequest(@UIEventTopic(OfficeUtils.KONTACTMASTER_SELECTIONREQUEST) Object object)
	{
		if (!treeViewer.getTree().isDisposed() && (object instanceof Kontakt))
			treeViewer.setSelection(new StructuredSelection(object), true);
	}

	/**
	 * Externe Anforderung den Filter zurueckzusetzen-
	 * 
	 * @param keiner
	 */
	@Inject
	@Optional
	public void handleClearFilterRequest(@UIEventTopic(OfficeUtils.KONTACTFILTER_CLEARREQUEST) Object object)
	{
		textFilter.setText("");
	}

}
