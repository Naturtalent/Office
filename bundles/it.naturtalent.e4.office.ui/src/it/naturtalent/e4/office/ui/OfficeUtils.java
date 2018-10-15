package it.naturtalent.e4.office.ui;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
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
import it.naturtalent.office.model.address.Absender;
import it.naturtalent.office.model.address.AddressPackage;
import it.naturtalent.office.model.address.FootNote;
import it.naturtalent.office.model.address.FootNotes;
import it.naturtalent.office.model.address.FooterClass;
import it.naturtalent.office.model.address.Kontakt;
import it.naturtalent.office.model.address.Kontakte;
import it.naturtalent.office.model.address.NtProjektKontakte;
import it.naturtalent.office.model.address.Referenz;
import it.naturtalent.office.model.address.ReferenzGruppe;
import it.naturtalent.office.model.address.ReferenzSet;
import it.naturtalent.office.model.address.ReferenzenClass;
import it.naturtalent.office.model.address.Sender;




public class OfficeUtils
{
	
	// Name des Office - ECP Projekt
	public final static String OFFICEPROJECTNAME = "OfficeEMFProject";
	
	//MasterTreeRefreshing erforderlich 
	public final static String KONTAKTE_REFRESH_MASTER_REQUEST = "kontakterefreshmasterrequest";
	
	// Selection im Master anfordern
	public static final String KONTACTMASTER_SELECTIONREQUEST = "kontactselectionrequest"; //$NON-NLS-N$
	
	// Inhalt des OfficeProjekt wurde gespeichert 
	public static final String OFFICEPROJECT_CONTENTSAVEACCOMPLISHED = "officesaveaccomplished"; //$NON-NLS-N$
	
	public static final String REFERENZGROUP_SELECTREFERENCEEVENT = "selectgroupreferenceselectionevent"; //$NON-NLS-N$
	public static final String REFERENZGROUP_REQUESTSELECTREFERENCEEVENT = "requestselectgroupreferenceselectionevent"; //$NON-NLS-N$

	// EventBroker Definitions
	public static final String RECEIVER_SELECTED_EVENT = "receiverselected"; //$NON-NLS-N$
	public static final String SET_RECEIVER_SELECTED_EVENT = "setreceiverselected"; //$NON-NLS-N$
	public static final String ABSENDERMASTER_SELECTED_EVENT = "absenderselected"; //$NON-NLS-N$
	public static final String SET_ABSENDERMASTER_SELECTION_EVENT = "setabsendermasterselection"; //$NON-NLS-N$
	public static final String ABSENDER_DETAIL_SELECTED_EVENT = "absenderdetailselected"; //$NON-NLS-N$
	
	private static Log log = LogFactory.getLog(OfficeUtils.class);
	
	private static Kontakte kontakte;
	
	private static Sender senders;
	
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
			
			if(kontakte == null)
			{
				EClass kontakteClass = AddressPackage.eINSTANCE.getKontakte();
				kontakte = (Kontakte) EcoreUtil.create(kontakteClass);
				projectContents.add(kontakte);
				ecpProject.saveContents();				
			}
		}
		
		return kontakte;
	}

	public static Sender getSender(String context)
	{
		// nach 'context' gefilterte Absender
		List<Absender>contextAbsender = new ArrayList<Absender>();
		
		// die nach context gefilterten Elemente hinzufuegen
		ECPProject ecpProject = getOfficeProject();
		if(ecpProject != null)
		{
			EList<Object> projectContents = ecpProject.getContents();
			if (!projectContents.isEmpty())
			{
				for (Object projectContent : projectContents)
				{
					if (projectContents instanceof Sender)
					{
						Sender sender = (Sender) projectContents;
						List<Absender>allAbsender = sender.getSenders();
						for(Absender absender : allAbsender)
						{
							if(StringUtils.equals(absender.getContext(), context))
								contextAbsender.add(absender);
						}
						break;
					}
				}
			}
		}
		
		// Sender mit den gefilterten Absender zurueckgeben
		EClass sendersClass = AddressPackage.eINSTANCE.getSender();
		Sender sender = (Sender) EcoreUtil.create(sendersClass);
		sender.getSenders().addAll(contextAbsender);
		
		return sender;
	}
	
	public static Sender getSender()
	{
		if(senders != null)
			return senders;
		
		ECPProject ecpProject = getOfficeProject();
		if(ecpProject != null)
		{
			EList<Object> projectContents = ecpProject.getContents();
			if (!projectContents.isEmpty())
			{
				for (Object projectContent : projectContents)
				{
					if (projectContent instanceof Sender)
					{
						senders = (Sender) projectContent;
						break;
					}
				}
			}
			
			if(senders == null)
			{
				EClass sendersClass = AddressPackage.eINSTANCE.getSender();
				senders = (Sender) EcoreUtil.create(sendersClass);
				projectContents.add(senders);
				ecpProject.saveContents();				
			}
		}
		
		return senders;	
	}
	
	/**
	 * Listet alle Kontakte mit den Namen im Array 'kontactNames'
	 * Sind mehrer Kontakte unter dem gleichen Namen gespeichert (soll eigentlich verhindert werden)
	 * wird nur der zuerst gefundene Kontakt gelistet.
	 * 
	 * @param kontactNames
	 * @return
	 */
	public static List<Kontakt>findKontactsByNames(String [] kontactNames)
	{
		List<Kontakt>kontakte = new ArrayList<Kontakt>();
		if(ArrayUtils.isNotEmpty(kontactNames))
		{
			EList<Kontakt>kontacts = getKontakte().getKontakte();
			for(String kontactName : kontactNames)
			{
				Kontakt kontakt = findKontactByName(kontacts, kontactName);
				if(kontakt != null)
					kontakte.add(kontakt);				
			}
		}

		return kontakte;
	}
	
	// sucht einen Kontakt ueber seinen Namen
	private static Kontakt findKontactByName(EList<Kontakt>kontacts, String kontactName)
	{
		for(Kontakt kontact : kontacts)
		{
			if(StringUtils.equals(kontact.getName(), kontactName))
				return kontact;
		}
		
		return null;
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
	
	/**
	 * Listet alle Footer einer Footerklasse
	 * @param footerClassName
	 * @return
	 */
	public static List<FootNotes> getFooterGroups(String footerClassName)
	{		
		FooterClass footerClass = getFooterClass(footerClassName);		
		return (footerClass != null) ? footerClass.getFooterClassFootNotes() : null;
	}

	/**
	 * Rueckgabe der FooterKlasse (Container) aller FootNotes.
	 * 
	 * @param footerClassName
	 * @return
	 */
	public static FooterClass getFooterClass(String footerClassName)
	{	
		ECPProject ecpProject = getOfficeProject();
		EList<Object> projectContents = ecpProject.getContents();
		if (!projectContents.isEmpty())
		{
			for (Object projectContent : projectContents)
			{
				if (projectContent instanceof FooterClass)
				{
					FooterClass footerClass = (FooterClass) projectContent; 
					if(StringUtils.equals(footerClass.getFooterClassName(), footerClassName))
						return (FooterClass) projectContent;
				}
			}
		}

		return null;
	}
	
	/**
	 * Rueckgabe der ReferenzenKlasse (Container)
	 * 
	 * @param footerClassName
	 * @return
	 */
	public static ReferenzenClass getReferenzClass(String referenzClassName)
	{	
		ECPProject ecpProject = getOfficeProject();
		EList<Object> projectContents = ecpProject.getContents();
		if (!projectContents.isEmpty())
		{
			for (Object projectContent : projectContents)
			{
				if (projectContent instanceof ReferenzenClass)
				{
					ReferenzenClass referenzenClass = (ReferenzenClass) projectContent; 
					if(StringUtils.equals(referenzenClass.getReferenzenClassName(), referenzClassName))
						return (ReferenzenClass) projectContent;
				}
			}
		}

		return null;
	}

	/**
	 * Listet alle ReferenzGroups einer spezifischen Klasse (r.B. Business, Telekom) auf.
	 * 
	 * @param referenzClassName
	 * @return
	 */
	public static List<ReferenzGruppe> getReferenzGroups(String referenzClassName)
	{		
		ReferenzenClass referenzenClass = getReferenzClass(referenzClassName);		
		return (referenzenClass != null) ? referenzenClass.getReferenzClassReferenzen() : null;
	}

	public static ReferenzGruppe findReferenceGroup(String referenzClassName, String referenzGroupName)
	{
		List<ReferenzGruppe>referenzGroups = getReferenzGroups(referenzClassName);
		if((referenzGroups != null) && (!referenzGroups.isEmpty()))
		{
			for (ReferenzGruppe referenzGroup : referenzGroups)
			{
				if (StringUtils.equals(referenzGroupName,referenzGroup.getGroupname()))
					return referenzGroup;
			}
		}

		return null;
	}
	

	/**
	 * @param referenzClassName
	 * @param referenzGroupName
	 * @param referenzName
	 * @return
	 */
	public static Referenz getReferenz(String referenzClassName, String referenzGroupName, String referenzName)
	{
		List<ReferenzGruppe>referenzGroups = getReferenzGroups(referenzClassName);
		if((referenzGroups != null) && (!referenzGroups.isEmpty()))
		{
			for (ReferenzGruppe referenzGroup : referenzGroups)
			{
				if (StringUtils.equals(referenzGroupName,referenzGroup.getGroupname()))
				{
					List<Referenz>references = referenzGroup.getReferenz();
					if((references != null) && (!references.isEmpty()))
					{
						for(Referenz reference : references)
						{
							if(StringUtils.equals(referenzName, reference.getName()))
								return reference;
						}
					}
				}
			}
		}
		return null;
	}

}
