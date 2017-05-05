package it.naturtalent.e4.kontakte.ui;

import it.naturtalent.e4.preferences.ListEditorComposite;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class KontaktePreferenceComposite extends Composite
{
	
	private ListEditorComposite listEditorPreference;
	
	private ListEditorComposite listEditorCategories;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public KontaktePreferenceComposite(Composite parent, int style)
	{
		super(parent, style);
		setLayout(new GridLayout(2, false));
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		Label lblAnrden = new Label(this, SWT.NONE);
		lblAnrden.setText("Anreden bei persönlichen Adressen");
		new Label(this, SWT.NONE);
		
		listEditorPreference = new ListEditorComposite(this, SWT.NONE);
		listEditorPreference.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		Label lblCategories = new Label(this, SWT.NONE);
		lblCategories.setText("Kategorien");
		new Label(this, SWT.NONE);
		
		listEditorCategories = new ListEditorComposite(this, SWT.NONE);
		listEditorCategories.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

	}
	
	public ListEditorComposite getAnredenPreference()
	{
		return listEditorPreference;
	}

	public ListEditorComposite getCategoriesPreference()
	{
		return listEditorCategories;
	}


	@Override
	protected void checkSubclass()
	{
		// Disable the check that prevents subclassing of SWT components
	}

}
