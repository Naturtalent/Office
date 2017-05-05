package it.naturtalent.e4.office.ui;

import it.naturtalent.e4.preferences.DirectoryEditorComposite;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

public class OfficeApplicationPreferenceComposite extends Composite
{

	private DirectoryEditorComposite directoryEditorComposite;
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public OfficeApplicationPreferenceComposite(Composite parent, int style)
	{
		super(parent, style);
		setLayout(null);
		
		directoryEditorComposite = new DirectoryEditorComposite(this, SWT.NONE);
		directoryEditorComposite.setBounds(5, 5, 529, 61);

	}
	
	

	public DirectoryEditorComposite getDirectoryEditorComposite()
	{
		return directoryEditorComposite;
	}



	@Override
	protected void checkSubclass()
	{
		// Disable the check that prevents subclassing of SWT components
	}
}
