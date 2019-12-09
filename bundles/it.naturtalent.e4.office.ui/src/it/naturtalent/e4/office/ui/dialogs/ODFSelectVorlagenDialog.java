package it.naturtalent.e4.office.ui.dialogs;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;

import it.naturtalent.e4.office.ui.ODFDefaultWriteAdapter;
import it.naturtalent.e4.office.ui.preferences.OfficeDefaultPreferenceUtils;
import it.naturtalent.icons.core.Icon;
import it.naturtalent.icons.core.IconSize;

/**
 * Mit diesem Dialog wird die Vorlage fuer das Textdokument festgelegt.
 * 
 * Die praeferenzierte Vorlage wird voreingestellt
 * 
 * @author dieter
 *
 */
public class ODFSelectVorlagenDialog extends TitleAreaDialog
{
	protected Table table;
	
	private String selectedName; 
	
	protected TableViewer tableViewer;

	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public ODFSelectVorlagenDialog(Shell parentShell)
	{
		super(parentShell);
	}

	/**
	 * Create contents of the dialog.
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent)
	{
		setTitleImage(Icon.WIZBAN_SYNCHRONIZE_GIT.getImage(IconSize._75x66_TitleDialogIconSize));
		setMessage("eine Vorlage (Layout) auswählen");
		setTitle("Vorlagen");
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setLayout(new GridLayout(1, false));
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		Label lblLayout = new Label(container, SWT.NONE);
		lblLayout.setText("Vorlagen");
		
		tableViewer = new TableViewer(container, SWT.BORDER | SWT.FULL_SELECTION);
		table = tableViewer.getTable();
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		tableViewer.setContentProvider(new ArrayContentProvider());
		tableViewer.setInput(getInputList());
		tableViewer.addSelectionChangedListener(new ISelectionChangedListener()
		{			
			@Override
			public void selectionChanged(SelectionChangedEvent event)
			{
				StructuredSelection selection = (StructuredSelection) tableViewer.getSelection();
				Object selObject = selection.getFirstElement();
				if (selObject instanceof String)
					selectedName = (String) selObject;				
			}
		});
		
		selectPreferencedLayput();
		return area;
	}

	/**
	 * Create contents of the button bar.
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent)
	{
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize()
	{
		return new Point(450, 498);
	}
	
	/*
	 * Listet alle verfuegbaren Vorlagen auf,
	 * 
	 */
	protected List<String>getInputList()
	{
		return ODFDefaultWriteAdapter.readTemplateNames();		
	}
	
	// die praeferenzierte Vorlage wird selektiert
	protected void selectPreferencedLayput()
	{
		IEclipsePreferences instancePreferenceNode = InstanceScope.INSTANCE
				.getNode(OfficeDefaultPreferenceUtils.ROOT_DEFAULTOFFICE_PREFERENCES_NODE);
		
		String preferencedName = instancePreferenceNode.get(OfficeDefaultPreferenceUtils.TEMPLATE_PREFERENCE,
				ODFDefaultWriteAdapter.ODFTEXT_TEMPLATE_NAME);
		
		if(StringUtils.isNotEmpty(preferencedName) && (tableViewer != null) && (!table.isDisposed()))
				tableViewer.setSelection(new StructuredSelection(preferencedName));		
	}

	public String getSelectedName()
	{
		return selectedName;
	}

	

}
