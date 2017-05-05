package it.naturtalent.e4.office.ui.expimp;

import it.naturtalent.e4.office.INtOffice;
import it.naturtalent.e4.office.IOfficeService;
import it.naturtalent.e4.office.ProfilePreferences;
import it.naturtalent.e4.office.letter.OfficeLetterProfile;
import it.naturtalent.e4.office.letter.OfficeLetterProfiles;
import it.naturtalent.e4.office.letter.OfficeLetterUtils;
import it.naturtalent.e4.office.ui.dialogs.OfficeProfileImportDialog;

import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.jface.action.Action;

/**
 * Importquelle (XML - Datei) auswaehlen, die selektierten Module in das
 * Textbausteinmodell uebernehmen (vorhandene Themen werden ueberschrieben)
 * und ueber Eventbroker informieren.
 * 
 * @author apel.dieter
 *
 */
public class OfficeProfileImportAction extends Action
{
	@Inject @Optional IEclipseContext context;
	@Inject @Optional IEventBroker broker;
	
	protected String officeContext = IOfficeService.NTOFFICE_CONTEXT;
	
	private OfficeLetterProfiles officeProfiles;
	private List<OfficeLetterProfile>profiles;
	
	@Override
	public void run()
	{			
		OfficeProfileImportDialog dialog = ContextInjectionFactory.make(OfficeProfileImportDialog.class, context);
		dialog.create();
		if(dialog.open() == OfficeProfileImportDialog.OK)
		{
			OfficeLetterProfiles importProfiles = dialog.getImportetProfiles();
			if(importProfiles != null)			
				mergeImportProfiles(importProfiles, dialog.isOverwriteFlag());			
		}
	}
	
	/*
	 * Die importierten Profile mit den vorhandenen zusammenfassen
	 */
	private void mergeImportProfiles(OfficeLetterProfiles importProfiles, boolean overwriteFlag)
	{
		// die vorhandenen Profile einlesen
		//officeProfiles = OpenDocumentUtils.readProfiles(officeContext);		
		officeProfiles = OfficeLetterUtils.readProfiles(officeContext);
		profiles = officeProfiles.getProfiles();
		
		// die importierten Profile hinzufuegen
		for(OfficeLetterProfile importProfile : importProfiles.getProfiles())
		{
			// wenn overwrite - vorhandenes Profile entfernen
			OfficeLetterProfile existProfile = existProfile(importProfile);
			if((existProfile != null) && (overwriteFlag))
				profiles.remove(existProfile);
			else
			{
				// Defaultprofile wird immer ueberschrieben
				if (existProfile != null)
					if (StringUtils.equals(existProfile.getName(),
							ProfilePreferences.DEFAULT_OFFICE_PROFILE))
						profiles.remove(existProfile);
			}

			profiles.add(importProfile);
		}
		
		// Profile wieder speichern
		OfficeLetterUtils.writeProfiles(officeContext, officeProfiles);
	}
	
	private OfficeLetterProfile existProfile(OfficeLetterProfile importProfile)
	{
		if(profiles != null)
		{
			String importName = importProfile.getName();
			for(OfficeLetterProfile profile : profiles)
			{
				if (StringUtils.equals(profile.getName(),importName))
					return profile;				
			}	
		}
		
		return null;
	}

}
