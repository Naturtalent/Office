package it.naturtalent.e4.office.ui.expimp;

import java.io.File;

import it.naturtalent.e4.office.INtOffice;
import it.naturtalent.e4.office.IOfficeService;
import it.naturtalent.e4.office.OfficeProfiles;
import it.naturtalent.e4.office.OpenDocumentUtils;
import it.naturtalent.e4.office.letter.OfficeLetterProfiles;
import it.naturtalent.e4.office.letter.OfficeLetterUtils;
import it.naturtalent.e4.office.ui.dialogs.OfficeProfileExportDialog;
import it.naturtalent.e4.project.IImportAdapterRepository;

import javax.inject.Inject;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.jface.action.Action;
import org.eclipse.swt.widgets.Shell;

public class OfficeProfileExportAction extends Action
{

	@Override
	public void run()
	{
		OfficeProfileExportDialog dialog = new OfficeProfileExportDialog();
		dialog.create();
		dialog.initSettingKey(OfficeProfileExportDialog.OFFICEPROFILE_EXPORTPATH_SETTINGS);
		dialog.setProfiles(OfficeLetterUtils.readProfiles(IOfficeService.NTOFFICE_CONTEXT));
		if(dialog.open() == OfficeProfileExportDialog.OK)
		{
			File exportFile = dialog.getExportFile();
			OfficeLetterProfiles officeProfiles = dialog.getProfiles();
			OfficeLetterUtils.writeProfiles(exportFile, officeProfiles);
		}
		
		super.run();
	}

	
	
}
