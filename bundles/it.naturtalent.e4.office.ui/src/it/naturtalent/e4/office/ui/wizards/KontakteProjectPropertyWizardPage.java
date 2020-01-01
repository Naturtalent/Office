package it.naturtalent.e4.office.ui.wizards;

import org.eclipse.emf.ecp.ui.view.ECPRendererException;
import org.eclipse.emf.ecp.ui.view.swt.ECPSWTViewRenderer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import it.naturtalent.e4.office.ui.KontakteProjectProperty;
import it.naturtalent.e4.office.ui.OfficeUtils;
import it.naturtalent.office.model.address.Kontakte;
import it.naturtalent.office.model.address.NtProjektKontakte;

/**
 * Wizardpage zur Bearbeitung der projektspezifischen Kontakte
 * 
 * @author dieter
 *
 */
public class KontakteProjectPropertyWizardPage extends WizardPage
{

	private KontakteProjectProperty kontakteProjectProperty;
	
	public KontakteProjectPropertyWizardPage()
	{
		super("KontakteProjectPropertyWizardPage");
		setTitle("Kontakte");
		setDescription("projektspezifische Kontakte definieren ");
	}

	@Override
	public void createControl(Composite parent)
	{
		Composite container = new Composite(parent, SWT.NULL);
		setControl(container);
		container.setLayout(new GridLayout(2, false));

		NtProjektKontakte kontakte = OfficeUtils.getProjectKontacts(kontakteProjectProperty.getNtProjectID());
		kontakteProjectProperty.setNtPropertyData(kontakte);
		if(kontakte != null)
		{
			try
			{
				ECPSWTViewRenderer.INSTANCE.render(container, kontakte);
				//eventBroker.post(ArchivUtils.SELECT_REGISTER_REQUEST, archivProjectProperty.getNtPropertyData());
			}
			catch (ECPRendererException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
	}

	public KontakteProjectProperty getKontakteProjectProperty()
	{
		return kontakteProjectProperty;
	}

	public void setKontakteProjectProperty(
			KontakteProjectProperty kontakteProjectProperty)
	{
		this.kontakteProjectProperty = kontakteProjectProperty;
	}
	
	

}
