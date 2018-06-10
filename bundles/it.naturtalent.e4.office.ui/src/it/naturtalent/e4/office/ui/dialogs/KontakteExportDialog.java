package it.naturtalent.e4.office.ui.dialogs;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.emf.common.util.EList;
import org.eclipse.swt.widgets.Shell;

import it.naturtalent.e4.office.ui.OfficeUtils;
import it.naturtalent.e4.project.expimp.ExpImportData;
import it.naturtalent.e4.project.expimp.dialogs.AbstractExportDialog;
import it.naturtalent.e4.project.expimp.dialogs.ExportDestinationComposite;
import it.naturtalent.office.model.address.Kontakt;
import it.naturtalent.office.model.address.Kontakte;

public class KontakteExportDialog extends AbstractExportDialog
{
	
	private static final String KONTAKTEXPORTPATH_SETTING_KEY = "exportkontaktpathsetting"; //$NON-NLS-N$

	public KontakteExportDialog()
	{
		super(shell);
		// TODO Auto-generated constructor stub
	}
	
	@Inject
	@Optional
	public void handleModelChangedEvent(@UIEventTopic(ExportDestinationComposite.EXPORTDESTINATION_EVENT) String exportPath)
	{
		this.exportPath = exportPath;		
	}
	
	@PostConstruct
	public void postConstruct(@ Named (IServiceConstants.ACTIVE_SHELL) @ Optional Shell shell)
	{
		this.shell = shell;
	}
	
	/*
	 * Initialisiert Viewer mit den zuexportierenten Daten.
	 * 
	 * (non-Javadoc)
	 * @see it.naturtalent.e4.project.expimp.dialogs.AbstractExportDialog#init()
	 */
	@Override
	protected void init()
	{	
		exportSettingKey = KONTAKTEXPORTPATH_SETTING_KEY;
		super.init();
		List<ExpImportData>lexpimpdata = new ArrayList<ExpImportData>();
		Kontakte kontakte = OfficeUtils.getKontakte();
		EList<Kontakt>kontaktList = kontakte.getKontakte();
		for(Kontakt kontakt : kontaktList)
		{
			ExpImportData expimpdata = new ExpImportData();
			expimpdata.setLabel(kontakt.getName());
			expimpdata.setData(kontakt);
			lexpimpdata.add(expimpdata);
		}
		setModelData(lexpimpdata);
	}
	
	
	@Override
	public void doExport()
	{
		exportEMFModeData();
	}

}
