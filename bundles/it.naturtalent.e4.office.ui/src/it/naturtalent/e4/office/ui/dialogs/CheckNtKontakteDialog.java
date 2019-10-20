package it.naturtalent.e4.office.ui.dialogs;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.core.ECPProject;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;

import it.naturtalent.e4.office.ui.OfficeUtils;
import it.naturtalent.e4.project.INtProject;
import it.naturtalent.office.model.address.NtProjektKontakte;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class CheckNtKontakteDialog extends TitleAreaDialog
{
	private class TableLabelProvider extends LabelProvider implements ITableLabelProvider
	{
		public Image getColumnImage(Object element, int columnIndex)
		{
			return null;
		}

		public String getColumnText(Object element, int columnIndex)
		{
			if (element instanceof IProject)
			{
				IProject iProject = (IProject) element;
				try
				{
					return iProject.getPersistentProperty(INtProject.projectNameQualifiedName);
					
				} catch (CoreException e)
				{
				}
			}
			return element.toString();
		}
	}

	private CheckboxTableViewer checkboxTableViewer;
	
	private Map<IProject,NtProjektKontakte>ntKontakte;
	
	private IProject [] resultCheckedProjects;	
	
	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public CheckNtKontakteDialog(Shell parentShell, Map<IProject, NtProjektKontakte> ntKontakte)
	{
		super(parentShell);
		this.ntKontakte = ntKontakte;
	}
	
	//projektName = iProject.getPersistentProperty(INtProject.projectNameQualifiedName);

	/**
	 * Create contents of the dialog.
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent)
	{
		setMessage("prueft, ob Kontakte zugeordnet wurden aber nicht im EMF Model gespeichert sind");
		setTitle("Check NtKontakte");
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setLayout(new GridLayout(1, false));
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		Label lblKontakte = new Label(container, SWT.NONE);
		lblKontakte.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		lblKontakte.setText("folgende Kontakte wurden gefunden");
		
		checkboxTableViewer = CheckboxTableViewer.newCheckList(container, SWT.BORDER | SWT.FULL_SELECTION | SWT.CHECK);		
		Table table = checkboxTableViewer.getTable();
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		Composite composite = new Composite(container, SWT.NONE);
		composite.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Button btnAllCheck = new Button(composite, SWT.NONE);
		btnAllCheck.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				// grayed Items werden nicht gecheckt
				Table table = checkboxTableViewer.getTable();
				int n = table.getItemCount();
				for(int i = 0;i < n;i++)
				{
					Object item = checkboxTableViewer.getElementAt(i);
					checkboxTableViewer.setChecked(item, !checkboxTableViewer.getGrayed(item));
				}
			}
		});
		
		checkboxTableViewer.addCheckStateListener(new ICheckStateListener()
		{			
			@Override
			public void checkStateChanged(CheckStateChangedEvent event)
			{
				Object element= event.getElement();
				
				if(checkboxTableViewer.getGrayed(element))
					checkboxTableViewer.setChecked(element, false);
			}
		});
		
		
		btnAllCheck.setText("alle Checken");
		
		Button btnNoCheck = new Button(composite, SWT.NONE);
		btnNoCheck.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				checkboxTableViewer.setAllChecked(false);
			}
		});
		btnNoCheck.setText("keine Checken");
		checkboxTableViewer.setLabelProvider(new TableLabelProvider());
		checkboxTableViewer.setContentProvider(new ArrayContentProvider());

		setNtKontakte();
		
		return area;
	}
	
	private void setNtKontakte()
	{
		Set<IProject> keySet = ntKontakte.keySet();
		IProject[] iProjectArray = (IProject[]) keySet.toArray(new IProject[keySet.size()]);
		checkboxTableViewer.setInput(iProjectArray);
		disableExistObjects(iProjectArray);
	}

	/**
	 * Create contents of the button bar.
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent)
	{
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize()
	{
		return new Point(450, 531);
	}
	
	/*
	 * 
	 */
	private void disableExistObjects(IProject[]iProjects)
	{		
		for(IProject iProject : iProjects)
		{
			if(iProject.exists())
			{
				NtProjektKontakte existKontakt = existProjectKontakt(iProject.getName());
				checkboxTableViewer.setGrayed(iProject, (existKontakt != null) ? true : false);			
			}
		}
		
		checkboxTableViewer.refresh();
	}

	private NtProjektKontakte existProjectKontakt(String ntProjectID)
	{
		NtProjektKontakte ntKontakte;

		if (StringUtils.isNotEmpty(ntProjectID))
		{
			ECPProject ecpProject = OfficeUtils.getOfficeProject();
			if (ecpProject != null)
			{
				EList<Object> projectContents = ecpProject.getContents();
				if (!projectContents.isEmpty())
				{
					for (Object projectContent : projectContents)
					{
						if (projectContent instanceof NtProjektKontakte)
						{
							ntKontakte = (NtProjektKontakte) projectContent;
							if (StringUtils.equals(ntKontakte.getNtProjektID(),ntProjectID))
								return ntKontakte;
						}
					}
				}
			}
		}
		
		return null;
	}

	@Override
	protected void okPressed()
	{
		// die ausgewaehlten NtProjekt Defaulteigenschaften listen
		Object[] result = checkboxTableViewer.getCheckedElements();
		resultCheckedProjects = new IProject[result.length];
		System.arraycopy(result, 0, resultCheckedProjects, 0,result.length);
		super.okPressed();
	}

	public IProject[] getResultCheckedProjects()
	{
		return resultCheckedProjects;
	}
	
	

}
