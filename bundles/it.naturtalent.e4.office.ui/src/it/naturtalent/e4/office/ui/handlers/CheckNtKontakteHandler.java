 
package it.naturtalent.e4.office.ui.handlers;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Named;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.core.ECPProject;
import org.eclipse.swt.widgets.Shell;

import it.naturtalent.e4.office.ui.KontakteProjectProperty;
import it.naturtalent.e4.office.ui.OfficeUtils;
import it.naturtalent.e4.office.ui.dialogs.CheckNtKontakteDialog;
import it.naturtalent.e4.project.ui.emf.ExpImpUtils;
import it.naturtalent.office.model.address.NtProjektKontakte;



public class CheckNtKontakteHandler
{
	@Execute
	public void execute(@Named(IServiceConstants.ACTIVE_SHELL)@Optional Shell shell )
	{
		Map<IProject,NtProjektKontakte>ntKontakte = readNtKontakte();
		
		CheckNtKontakteDialog checkDialog = new CheckNtKontakteDialog(shell, ntKontakte);
		if(checkDialog.open() == CheckNtKontakteDialog.OK)
		{
			ECPProject ecpProject = OfficeUtils.getOfficeProject();
			if (ecpProject != null)
			{
				IProject[] checkedProjects = checkDialog.getResultCheckedProjects();
				for (IProject iProject : checkedProjects)
				{					
					NtProjektKontakte projectKontakte = ntKontakte.get(iProject);
					ecpProject.getContents().add(projectKontakte);					
				}
				
				ecpProject.saveContents();
			}
		}
	}
	
	/*
	 * Auslesen der Datei *EXPIMP_NTPROJECTKONTAKTDATA_FILE' und auflisten der Kontakte.
	 *  
	 */
	private Map<IProject,NtProjektKontakte> readNtKontakte()
	{
		Map<IProject,NtProjektKontakte>foundedKontacts = new HashMap<IProject,NtProjektKontakte>();
		
		// durchforstet alle Projekte und sucht nach der Datei *EXPIMP_NTPROJECTKONTAKTDATA_FILE'
		IProject[] iProjects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		for(IProject iProject : iProjects)
		{
			EObject kontaktProjectData = ExpImpUtils.importNtPropertyData(
					KontakteProjectProperty.EXPIMP_NTPROJECTKONTAKTDATA_FILE,iProject.getName());
			if(kontaktProjectData != null)
				foundedKontacts.put(iProject, (NtProjektKontakte) kontaktProjectData);
		}
		
		return foundedKontacts;
	}

}