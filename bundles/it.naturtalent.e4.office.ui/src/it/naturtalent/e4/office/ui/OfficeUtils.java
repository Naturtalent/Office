package it.naturtalent.e4.office.ui;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.core.ECPProject;
import org.eclipse.emf.ecp.core.util.ECPUtil;
import org.eclipse.emf.ecp.spi.ui.util.ECPHandlerHelper;

import it.naturtalent.emf.model.EMFModelUtils;
import it.naturtalent.office.model.address.AddressPackage;
import it.naturtalent.office.model.address.Kontakte;
import it.naturtalent.office.model.address.NtProjektKontakte;



public class OfficeUtils
{
	
	// Name des Office - ECP Projekt
	public final static String OFFICEPROJECTNAME = "OfficeEMFProject";
	
	//MasterTreeRefreshing erforderlich 
	public final static String KONTAKTE_REFRESH_MASTER_REQUEST = "kontakterefreshmasterrequest";

	
	private static Log log = LogFactory.getLog(OfficeUtils.class);
	
	private static Kontakte kontakte;

	/*
	 * Im OfficeProjekt sind alle Office-Modelle zusammengefasst 
	 */
	public static ECPProject getOfficeProject()
	{
		ECPProject officeProject = null; 

		officeProject = ECPUtil.getECPProjectManager().getProject(OFFICEPROJECTNAME);
		if(officeProject == null)
		{
			officeProject = EMFModelUtils.createProject(OFFICEPROJECTNAME);
				if(officeProject == null)
					log.error("es konnte kein Office-ECPProject erzeugt werden");
		}
		
		return officeProject;
	}
	
	/**
	 * Zugriff auf alle Kontakte des ECPProjekts.
	 * @return
	 */
	public static Kontakte getKontakte()
	{
		if(kontakte != null)
			return kontakte;
		
		ECPProject ecpProject = getOfficeProject();
		if(ecpProject != null)
		{
			EList<Object> projectContents = ecpProject.getContents();
			if (!projectContents.isEmpty())
			{
				for (Object projectContent : projectContents)
				{
					if (projectContent instanceof Kontakte)
					{
						kontakte = (Kontakte) projectContent;
						break;
					}
				}
			}
			else
			{
				// das Modell Kontakte erzeugen und im ECPProject speichern
				EClass kontakteClass = AddressPackage.eINSTANCE.getKontakte();
				kontakte = (Kontakte) EcoreUtil.create(kontakteClass);
				projectContents.add(kontakte);
				ecpProject.saveContents();
			}
		}
		
		return kontakte;
	}
	

	/**
	 * Alle einem NtProjekt zugeordneten Kontakte zurueckgeben.
	 * 
	 * Wird kein Eintrag gefunden oder ist ist die 'projectID' == null wird
	 * ein 'leerer' Container zurueckgegeben
	 * 
	 * @return
	 */
	public static NtProjektKontakte getProjectKontacts(String ntProjectID)
	{
		NtProjektKontakte ntKontakte;
			
		if(StringUtils.isNotEmpty(ntProjectID))
		{
			ECPProject ecpProject = getOfficeProject();
			if(ecpProject != null)
			{
				EList<Object> projectContents = ecpProject.getContents();
				if (!projectContents.isEmpty())
				{
					for (Object projectContent : projectContents)
					{
						if (projectContent instanceof NtProjektKontakte)
						{
							ntKontakte = (NtProjektKontakte) projectContent; 							
							if(StringUtils.equals(ntKontakte.getNtProjektID(), ntProjectID))
								return ntKontakte;							
						}
					}					
				}
			}	
		}
		
		EClass ntProjektClass = AddressPackage.eINSTANCE.getNtProjektKontakte();
		ntKontakte = (NtProjektKontakte) EcoreUtil.create(ntProjektClass);
		return ntKontakte;
	}
}
