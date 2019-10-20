package it.naturtalent.e4.office.ui.wizards;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Table;

import it.naturtalent.e4.office.ui.ODFDefaultWriteAdapter;
import it.naturtalent.icons.core.Icon;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;

public class ODFVorlagenWizardPage extends WizardPage
{
	private Table table;
	
	private List<String>tableInputList;

	public ODFVorlagenWizardPage()
	{
		super("wizardPage");
		setMessage("eine Vorlage (Layout) auswählen");
		setTitle("Vorlagen");
		setDescription("Wizard Page description");		
	}

	@Override
	public void createControl(Composite parent)
	{
		Composite container = new Composite(parent, SWT.NONE);

		setControl(container);
		container.setLayout(new GridLayout(1, false));
		
		Label lblLayput = new Label(container, SWT.NONE);
		lblLayput.setText("verfügbare Vorlagen");
		
		TableViewer tableViewer = new TableViewer(container, SWT.BORDER | SWT.FULL_SELECTION);
		table = tableViewer.getTable();
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		tableViewer.setContentProvider(new ArrayContentProvider());
		tableViewer.setInput(getInputList());
	}
	
	protected List<String>getInputList()
	{
		return ODFDefaultWriteAdapter.readTemplateNames();		
	}

}
