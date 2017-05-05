package it.naturtalent.e4.office.ui.dialogs;


import java.text.Collator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Table;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.ViewerSorter;
import org.jdom2.Element;

public class TextbausteineDialogOLD extends TitleAreaDialog
{
	
	static public final String ATTRIBUTE_NAME = "name";
	static public final String ATTRIBUTE_ALIAS = "alias";
	static public final String SECTION_THEME = "thema";
	static public final String ATTRIBUTE_TEXT = "text";
	
	private static class Sorter extends ViewerSorter
	{
		Collator collator = Collator.getInstance( Locale.GERMAN );
		
		public int compare(Viewer viewer, Object e1, Object e2)
		{
			String item1 = ((Element) e1).getAttributeValue(ATTRIBUTE_NAME);
			String item2 = ((Element) e2).getAttributeValue(ATTRIBUTE_NAME);
						
			if(StringUtils.isNotEmpty(item1) && StringUtils.isNotEmpty(item2))
				return collator.compare(item1, item2);
			
			return 0;
		}
	}

	private class TableLabelProvider extends LabelProvider implements
			ITableLabelProvider
	{
		public Image getColumnImage(Object element, int columnIndex)
		{
			return null;
		}

		public String getColumnText(Object element, int columnIndex)
		{
			Element elem;
			
			if(element instanceof Element)
			{
				elem = (Element) element;
				return elem.getAttributeValue(ATTRIBUTE_ALIAS);
			}
				
			return element.toString();
		}
	}

	private static class TableContentProvider implements IStructuredContentProvider
	{
		List<Element>texte;
		
		public Object[] getElements(Object inputElement)
		{
			if(texte != null)
				return texte.toArray(new Element[texte.size()]);
			return new Object[0];
		}
	
		public void dispose()
		{
		}
	
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput)
		{
			texte = null;
			if(newInput instanceof List)
				texte = (List<Element>) newInput;
		}
	}

	private static class TreeContentProvider implements ITreeContentProvider
	{
		List<Element>texte;
		
		public Object[] getElements(Object inputElement)
		{
			if(texte != null)
				return texte.toArray(new Element[texte.size()]);
			return new Object[0];
		}

		public void dispose()
		{
		}

		@SuppressWarnings("unchecked")
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput)
		{
			texte = null;
			if(newInput instanceof List)
				texte = (List<Element>) newInput;
		}

		@Override
		public Object[] getChildren(Object parentElement)
		{
			if(parentElement instanceof Element)
			{
				Element elem = (Element) parentElement;
				if(elem.getName().equals(SECTION_THEME))
					return elem.getChildren().toArray();
			}
			
			return null;
		}

		@Override
		public Object getParent(Object element)
		{
			if(element instanceof Element)
			{
				Element elem = (Element) element;
				if(!elem.getName().equals(SECTION_THEME))
					return elem.getParent();
			}
			
			return null;
		}

		@Override
		public boolean hasChildren(Object element)
		{
			if(element instanceof Element)
			{
				Element elem = (Element) element;
				if(elem.getName().equals(SECTION_THEME))
					return true;
			}
			return false;
		}
	}
	
	private class TreeLabelProvider extends LabelProvider
	{
		public Image getColumnImage(Object element, int columnIndex)
		{
			return null;
		}
		
		@Override
		public String getText(Object element)
		{
			Element elem;
			
			if(element instanceof Element)
			{
				elem = (Element) element;
				if(elem.getName().equals(SECTION_THEME))
					return elem.getAttributeValue(ATTRIBUTE_NAME);
				if(elem.getName().equals(ATTRIBUTE_TEXT))
					return elem.getAttributeValue(ATTRIBUTE_ALIAS);
			}

			return super.getText(element);
		}

	}

	private Tree tree;
	private Table table;
	private TreeViewer treeViewer;
	private TableViewer tableViewer;
	private Button btnLeft;
	private Button btnRight;
	private Text text;
	private Button btnOk;
	private List<Element>themeElements;

	
	/**
	 * @param parentShell
	 * @param texte
	 */
	public TextbausteineDialogOLD(Shell parentShell, List<Element> themeElements)
	{
		super(parentShell);
		this.themeElements = themeElements;
	}



	/**
	 * Create contents of the dialog.
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent)
	{
		setMessage("Textbausteine auswählen und zusammenstellen");
		setTitle("Textbausteine");
		
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setLayout(new GridLayout(4, false));
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		treeViewer = new TreeViewer(container, SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI);
		treeViewer.setSorter(new Sorter());
		treeViewer
				.addSelectionChangedListener(new ISelectionChangedListener()
				{
					public void selectionChanged(SelectionChangedEvent event)
					{
						// den selektierten Text im unteren Fenster anzeigen
						showSelectedTextbaustein(event);	
						updateWidgetState();
					}
				});
		tree = treeViewer.getTree();
		GridData gd_checkTable = new GridData(SWT.FILL, SWT.FILL, false, true, 1, 1);
		gd_checkTable.widthHint = 260;
		tree.setLayoutData(gd_checkTable);
		treeViewer.setLabelProvider(new TreeLabelProvider());
		treeViewer.setContentProvider(new TreeContentProvider());
		
		Composite composite = new Composite(container, SWT.NONE);
		composite.setLayout(new FillLayout(SWT.VERTICAL));
		
		btnRight = new Button(composite, SWT.NONE);
		btnRight.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				IStructuredSelection selection;
				Object[]objs;				
				Iterator<Element>it;
				Element elem;
							
				selection = (IStructuredSelection) treeViewer.getSelection();
				if (!selection.isEmpty())
				{					
					for(it = selection.iterator();it.hasNext();)
					{
						elem = it.next();
						
						if(!elem.getName().equals(SECTION_THEME))
							tableViewer.add(elem);
					}
					updateWidgetState();
				}							
			}
		});
		//btnRight.setImage(NtOfficeImageRegistry.getImage(NtOfficeImageRegistry.ICON_NAV_RIGHT));		
		
		btnLeft = new Button(composite, SWT.NONE);
		btnLeft.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				IStructuredSelection selection;
				Element elem;
				Iterator<Element>it;
				
				selection = (IStructuredSelection) treeViewer.getSelection();
				if (!selection.isEmpty())
				{					
					for(it = selection.iterator();it.hasNext();)
					{
						elem = it.next();
						
						if(!elem.getName().equals(SECTION_THEME))
							tableViewer.remove(elem);
					}
					updateWidgetState();
				}							
				
			}
		});
		//btnLeft.setImage(NtOfficeImageRegistry.getImage(NtOfficeImageRegistry.ICON_NAV_LEFT));
		
		Composite composite_1 = new Composite(container, SWT.NONE);
		composite_1.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
		composite_1.setLayout(new TableColumnLayout());
		
		tableViewer = new TableViewer(composite_1, SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI);
		tableViewer.addSelectionChangedListener(new ISelectionChangedListener()
		{
			public void selectionChanged(SelectionChangedEvent event)
			{
				showSelectedTextbaustein(event);	
				updateWidgetState();
			}
		});
		table = tableViewer.getTable();
		tableViewer.setLabelProvider(new TableLabelProvider());
		tableViewer.setContentProvider(new TableContentProvider());
		tableViewer.setSorter(new Sorter());
		
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		
		text = new Text(container, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL | SWT.MULTI);
		text.setEditable(false);
		GridData gd_text = new GridData(SWT.FILL, SWT.CENTER, true, false, 4, 1);
		gd_text.heightHint = 135;
		text.setLayoutData(gd_text);
		
		treeViewer.setInput(themeElements);
		tableViewer.setInput(new ArrayList<Element>());
		
		return area;
	}

	/**
	 * Create contents of the button bar.
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent)
	{
		btnOk = createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
		updateWidgetState();
		
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize()
	{
		return new Point(659, 714);
	}
	
	public void setTextbausteine(List<Element>texte)
	{
		treeViewer.setInput(texte);
	}
	
	@SuppressWarnings("unchecked")
	private void showSelectedTextbaustein(SelectionChangedEvent event)
	{
		List<Element> elemParagraphs;
		StringBuilder textBuilder;
		IStructuredSelection selection = (IStructuredSelection) event.getSelection();
		if(!selection.isEmpty())
		{
			textBuilder = new StringBuilder();
			elemParagraphs = ((Element) selection.getFirstElement()).getChildren();
			for(Element elem : elemParagraphs)							
				textBuilder.append(elem.getTextNormalize()+'\n');
					
			text.setText(textBuilder.toString());							
		}	
	}
	
	private void updateWidgetState()
	{
		btnRight.setEnabled(!treeViewer.getSelection().isEmpty());
		btnLeft.setEnabled(!tableViewer.getSelection().isEmpty());
		btnOk.setEnabled(tableViewer.getTable().getItemCount() > 0);
	}
	
	@Override
	protected void okPressed()
	{
		themeElements.clear();
				
		int n = tableViewer.getTable().getItemCount();
		for(int i = 0;i < n;i++)
		{
			Element elem = (Element) tableViewer.getElementAt(i);
			themeElements.add((Element) elem.clone());			
		}
				
		super.okPressed();
	}


	public List<Element> getSelectedElements()
	{
		return themeElements;
	}


	
	
}
