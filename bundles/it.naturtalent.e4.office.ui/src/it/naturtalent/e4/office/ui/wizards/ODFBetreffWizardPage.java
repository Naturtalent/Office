package it.naturtalent.e4.office.ui.wizards;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.internal.workbench.E4Workbench;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.odftoolkit.simple.TextDocument;
import org.odftoolkit.simple.table.Table;

import it.naturtalent.e4.office.ui.IODFWriteAdapter;
import it.naturtalent.e4.office.ui.ODFDocumentUtils;
import it.naturtalent.e4.office.ui.OfficeUtils;
import it.naturtalent.e4.project.INtProject;
import it.naturtalent.e4.project.ui.navigator.ResourceNavigator;

/**
 * Wizardseite zur Definition des Betrefftextes.
 * 
 * @author dieter
 *
 */
public class ODFBetreffWizardPage extends WizardPage implements IWriteWizardPage
{
	
	@Inject @Optional private ESelectionService selectionService;
	
	
	// Name dieser WizardPage	
	private final static String BETREFF_PAGE_NAME = "ODF_BETREFF";
		
	private Text textBetreff;

	public ODFBetreffWizardPage()
	{
		super(BETREFF_PAGE_NAME);
		setMessage("Die Betreffzeile definieren."); //$NON-NLS-N$
		setTitle("Betreffzeile"); //$NON-NLS-N$
		setDescription("Betrefftext");  //$NON-NLS-N$
	}

	@Override
	public void createControl(Composite parent)
	{
		Composite container = new Composite(parent, SWT.NONE);

		setControl(container);
		container.setLayout(new GridLayout(3, false));
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		
		Label lblBetreff = new Label(container, SWT.NONE);
		lblBetreff.setText("Betreff");
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		
		textBetreff = new Text(container, SWT.BORDER | SWT.WRAP | SWT.MULTI);
		GridData gd_textBetreff = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_textBetreff.heightHint = 86;
		textBetreff.setLayoutData(gd_textBetreff);
		
		// im CreateMode wird versucht ein Betrefftrext vorzugeben
		ODFDefaultWriteAdapterWizard wizard = (ODFDefaultWriteAdapterWizard) getWizard();
		if(wizard.isWizardModus() == ODFDefaultWriteAdapterWizard.WIZARDCREATEMODE)						
			autoSetBetreff();
		
	}
	
	// ist in der Vorlage ein Betreff vorgegeben, darf dieser nicht ueberschrieben werden
	private void autoSetBetreff()
	{		
		// die selektierte Vorlage ermitteln
		IEclipseContext context = E4Workbench.getServiceContext();
		TextDocument odfDocument = (TextDocument) context.get(OfficeUtils.E4CONTEXTKEY_CREATED_ODFDOCUMENT);
		if(odfDocument != null)
		{
			// den vorgegebenen Betrefftext uebernehmen 
			context.remove(OfficeUtils.E4CONTEXTKEY_CREATED_ODFDOCUMENT);
			Table table = odfDocument.getTableByName(IODFWriteAdapter.ODF_BETREFF);
			String value = ODFDocumentUtils.readTableText(table, 0, 1); 
			if(StringUtils.isNotEmpty(value))
			{
				textBetreff.setText(value);
				return;
			}				
		}
		
		// Name des im Navigator selektierten Projekts als Betreff vorbesetzen
		Object selProject = selectionService.getSelection(ResourceNavigator.RESOURCE_NAVIGATOR_ID);
		if (selProject instanceof IResource)
		{
			try
			{
				// den Projektname als Betreff vorgeben 
				IProject iProject = ((IResource) selProject).getProject();
				String value = iProject.getPersistentProperty(INtProject.projectNameQualifiedName);
				//String value = ((IResource) selProject).getPersistentProperty(INtProject.projectNameQualifiedName);
				textBetreff.setText(value);
			} catch (CoreException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	@Override
	public void writeToDocument(TextDocument odfDocument)
	{
		if (odfDocument != null)
		{
			// Betrefftabelle im Dokument addressieren
			Table table = odfDocument.getTableByName(IODFWriteAdapter.ODF_BETREFF);
			String value = textBetreff.getText(); 
			if(StringUtils.isNotEmpty(value))
				ODFDocumentUtils.writeTableText(table, 0, 1, value);
		}		
	}

	@Override
	public void readFromDocument(TextDocument odfDocument)
	{
		Table table = odfDocument.getTableByName(IODFWriteAdapter.ODF_BETREFF);
		if (table != null)
			textBetreff.setText(ODFDocumentUtils.readTableText(table, 0, 1));
	}
	
	@Override
	public void cancelPage(TextDocument odfDocument)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unDo(TextDocument odfDocument)
	{
		// TODO Auto-generated method stub
		
	}
	

}
