package it.naturtalent.e4.office.ui;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.core.ECPProject;
import org.eclipse.emf.ecp.core.util.ECPUtil;
import org.eclipse.emf.ecp.spi.common.ui.SelectModelElementWizardFactory;
import org.eclipse.emf.ecp.spi.ui.util.ECPHandlerHelper;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.odftoolkit.simple.TextDocument;
import org.odftoolkit.simple.table.CellRange;
import org.odftoolkit.simple.table.Table;

import it.naturtalent.e4.office.ui.preferences.OfficeDefaultPreferenceUtils;
import it.naturtalent.e4.office.ui.wizards.ODFDefaultWriteAdapterWizard;
import it.naturtalent.office.model.address.Absender;
import it.naturtalent.office.model.address.AddressPackage;
import it.naturtalent.office.model.address.Adresse;
import it.naturtalent.office.model.address.FootNote;
import it.naturtalent.office.model.address.FootNotes;
import it.naturtalent.office.model.address.Kontakt;
import it.naturtalent.office.model.address.Kontakte;
import it.naturtalent.office.model.address.NtProjektKontakte;
import it.naturtalent.office.model.address.Referenz;
import it.naturtalent.office.model.address.ReferenzSet;
import it.naturtalent.office.model.address.Referenzen;
import it.naturtalent.office.model.address.Sender;
import it.naturtalent.office.model.address.Signature;
import it.naturtalent.office.model.address.Signatures;


public class OfficeUtils
{

	// Name des Office - ECP Projekt
	public final static String OFFICEPROJECTNAME = "OfficeEMFProject";
	
	// Name unter dem der aktuelle OfficeContext im E4Context hinterlegt wird  
	public static final String E4CONTEXTKEY_OFFICECONTEXT = "officecontext";

	// Name unter dem der aktuelle Praeferenzknoten im E4Context hinterlegt wird  
	public static final String E4CONTEXTKEY_OFFICEPRAEFERENZNODE = "officepraeferenznode";
	
	// das neu erzeugte ODFDocument wird unter diesem Namen hinterlegt
	public static final String E4CONTEXTKEY_CREATED_ODFDOCUMENT = "createdodfdocument";
	

	// EventBroker Keys
	public static final String SET_OFFICEMASTER_SELECTION_EVENT = "setofficemasterselection"; // $NON-NLS-N$	
	public static final String GET_OFFICEMASTER_SELECTION_EVENT = "getofficemasterselection"; // $NON-NLS-N$


	// MasterTreeRefreshing erforderlich
	public final static String KONTAKTE_REFRESH_MASTER_REQUEST = "kontakterefreshmasterrequest";

	// Selection im Master anfordern
	public static final String KONTACTMASTER_SELECTIONREQUEST = "kontactselectionrequest"; // $NON-NLS-N$
	
	// die Zuruecksetzung des Kontaktnamenfilter anfordern
	public static final String KONTACTFILTER_CLEARREQUEST = "kontactfilterclearrequest"; // $NON-NLS-N$

	// Inhalt des OfficeProjekt wurde gespeichert
	public static final String OFFICEPROJECT_CONTENTSAVEACCOMPLISHED = "officesaveaccomplished"; // $NON-NLS-N$

	// Das Kopieren eines Absenders aus der Kontaktdatenbank anfordern
	public static final String ADD_EXISTING_SENDER = "addexistingsender"; // $NON-NLS-N$
	
	// Modify-Event einer Texteingabe 
	public static final String MODIFY_EDITOR_EVENT = "modifyeditorevent"; // $NON-NLS-N$
	
	// E4Context Name fuer die Hinterlegungn eines WidgetStyle 
	//public static final String E4CONTEXT_WIDGETSTYLE = "officewidgetstyle";

	// Selektanforderungen an den Rederer
	public static final String SET_ABSENDERMASTER_SELECTION_EVENT = "setabsendermasterselection"; // $NON-NLS-N$	
	public static final String SET_RECEIVERMASTER_SELECTION_EVENT = "setreceivermasterselection"; // $NON-NLS-N$
	public static final String REFERENZSET_REQUESTSELECTREFERENCEEVENT = "requestselectgroupreferenceselectionevent"; // $NON-NLS-N$
	public static final String FOOTNOTESET_REQUESTSELECTREFERENCEEVENT = "requestfootnoteselectionevent"; // $NON-NLS-N$
	public static final String SET_FOOTNOTEMASTER_SELECTION_EVENT = "setfootnotemasterselection"; // $NON-NLS-N$
	public static final String SIGNATURE_REQUESTSELECTIONEVENT = "requestsignatureselectionevent"; // $NON-NLS-N$

	//
	// Selektion 
	//
	// Renderer melden die Selektion eines Ojekcs
	public static final String ABSENDERMASTER_SELECTED_EVENT = "absenderselected"; // $NON-NLS-N$
	public static final String GET_ABSENDER_DETAIL_SELECTED_EVENT = "getabsenderdetailselected"; // $NON-NLS-N$
	public static final String RECEIVER_MASTER_SELECTED_EVENT = "receiverselected"; // $NON-NLS-N$
	public static final String SELECT_REFERENZ_EVENT = "selectreferezevent"; // $NON-NLS-N$
	public static final String SELECT_FOOTNOTE_EVENT = "selectfootnoteevent"; // $NON-NLS-N$
	public static final String SELECT_SIGNATURE_EVENT = "selectsignatureeevent"; // $NON-NLS-N$
	
	//
	// ADD 
	//
	// Renderer melden das Hinzufuegen eines Objects
	public static final String ADD_NEWREFERENZ_EVENT = "addnewreferenzevent"; // $NON-NLS-N$
	public static final String ADD_NEWFOOTNOTE_EVENT = "addnewfootnoteevent"; // $NON-NLS-N$
	public static final String ADD_EXISTING_RECEIVER = "addexistingreceiver"; // $NON-NLS-N$
	public static final String ADD_NEWSENDER_EVENT = "addnewsenderevent"; // $NON-NLS-N$
	
	// SignatureSetRenderer meldet das Signatur Events
	public static final String ADD_SIGNATURE_EVENT = "addsignatureevent"; // $NON-NLS-N$
	public static final String REMOVE_SIGNATURE_EVENT = "removesignatureevent"; // $NON-NLS-N$
	
	//
	// Eclipse4Context ID's
	//
	// Liste mit nichtloeschbaren Fussnoten (z.B. alle FootNote-Praeferenzen) 
	public static final String FOOTNOTE_UNREMOVABLES = "footnoteunremovable"; // $NON-NLS-N$
	
	// Liste mit nichtloeschbaren Referenzen  
	public static final String REFERENZ_UNREMOVABLES = "referenzunremovable"; // $NON-NLS-N$

	// Liste mit nichtloeschbaren Absender  
	public static final String ABSENDER_UNREMOVABLES = "absenderunremovable"; // $NON-NLS-N$

	// Liste mit nichtloeschbaren Signaturen  
	public static final String SIGNATURE_UNREMOVABLES = "signatureunremovable"; // $NON-NLS-N$


	private static Log log = LogFactory.getLog(OfficeUtils.class);

	private static Sender senders;

	/*
	 * Im OfficeProjekt sind alle Office-Modelle zusammengefasst
	 */
	public static ECPProject getOfficeProject()
	{
		ECPProject officeProject = null;

		officeProject = ECPUtil.getECPProjectManager()
				.getProject(OFFICEPROJECTNAME);
		if (officeProject == null)
		{
			//officeProject = EMFModelUtils.createProject(OFFICEPROJECTNAME);
			officeProject = it.naturtalent.e4.project.ui.Activator.createProject(OFFICEPROJECTNAME);
			if (officeProject == null)
				log.error("es konnte kein Office-ECPProject erzeugt werden");
		}

		return officeProject;
	}

	/**
	 * Zugriff auf alle Kontakte des ECPProjekts.
	 * 
	 * @return
	 */
	private static Kontakte kontakte;
	public static Kontakte getKontakte()
	{
		
		if (kontakte != null)
			return kontakte;
		

		ECPProject ecpProject = getOfficeProject();
		if (ecpProject != null)
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

			if (kontakte == null)
			{
				EClass kontakteClass = AddressPackage.eINSTANCE.getKontakte();
				kontakte = (Kontakte) EcoreUtil.create(kontakteClass);
				projectContents.add(kontakte);
				ecpProject.saveContents();
			}
		}

		return kontakte;
	}

	/**
	 * Einen Kontakt mit einem Dialog (SelectModelElementWizardFactory.openModelElementSelectionDialog) in der 
	 * Kontaktdatenbank selektieren und kopieren.
	 * 
	 * @return
	 */
	public static Kontakt readKontaktFromDatabase()
	{
		
		System.out.println("Trace in OfficeUtils");
		
		// Kontakte fuer anschliessenden Dialog in einem Set aufbereiten 
		EList<Kontakt> allKontacts = getKontakte().getKontakte();
		Set<EObject> elements = new LinkedHashSet<EObject>();
		for (Kontakt kontact : allKontacts)
			elements.add(kontact);

		// Kontakt mit dem Dialog auswaehlen (Einzelselektion)
		final Set<EObject> selectedElements = SelectModelElementWizardFactory
				.openModelElementSelectionDialog(elements, false);

		
		
		if ((selectedElements != null) && (!selectedElements.isEmpty()))
			return (Kontakt) EcoreUtil.copy(selectedElements.iterator().next());

		return null;
	}

	/**
	 * Sucht ein Signatur-EObject ueber den Namen und den OfficeContext
	 * 
	 * @param absenderName
	 * @param officeContext
	 * @return
	 */
	public static Signature findSignatureByName(String signatureName,String officeContext)
	{		
		Signatures signatures = (Signatures) OfficeUtils.findObject(AddressPackage.eINSTANCE.getSignatures());
		EList<Signature> sigatures = signatures.getSignatures();
		for (Signature signature : sigatures)
		{
			if (StringUtils.equals(signature.getName(), signatureName)
					&& StringUtils.equals(signature.getContext(), officeContext))
				return signature;
		}

		return null;
	}

	/**
	 * Sucht ein Absender-EObject ueber den Namen und den OfficeContext
	 * 
	 * @param absenderName
	 * @param officeContext
	 * @return
	 */
	public static Absender findAbsenderByName(String absenderName,String officeContext)
	{
		Sender sender = (Sender) OfficeUtils.findObject(AddressPackage.eINSTANCE.getSender());
		EList<Absender> absenders = sender.getSenders();
		for (Absender absender : absenders)
		{
			if (StringUtils.equals(absender.getName(), absenderName)
					&& StringUtils.equals(absender.getContext(), officeContext))
				return absender;
		}

		return null;
	}

	/**
	 * Filtert alle Absender eines vorgegebenen OfficeContext.
	 * 
	 * @param officeContext
	 * @return
	 */
	public static List<Absender> findAbsenderByContext(String officeContext)
	{
		List<Absender> resultList = new ArrayList<Absender>();
		Sender sender = (Sender) OfficeUtils
				.findObject(AddressPackage.eINSTANCE.getSender());
		EList<Absender> absenders = sender.getSenders();
		for (Absender absender : absenders)
		{
			if (StringUtils.equals(absender.getContext(), officeContext))
				resultList.add(absender);
		}

		return resultList;
	}
		
	/*
	 * sucht die Referenz ueber ihren Namen
	 * !!! Name und OfficeContext muessen uebereinstimmen
	 */
	public static Referenz findReferenz(String referenzName, String officeContext)
	{
		// ReferenzSet ist der Container aller Referenzen
		Referenzen referenzen =  (Referenzen) OfficeUtils.findObject(AddressPackage.eINSTANCE.getReferenzen());
				
		EList<Referenz> allReferenzen = referenzen.getReferenzen();
		if (referenzen != null)
		{
			for (Referenz referenz : allReferenzen)
			{
				if (StringUtils.equals(referenz.getName(), referenzName)
						&& StringUtils.equals(referenz.getContext(),officeContext))
					return referenz;
			}
		}
		return null;
	}

	
	/*
	 * sucht die Referenz ueber ihren Namen
	 * !!! Name und OfficeContext muessen uebereinstimmen
	 */
	public static FootNote findFootNoteByName(String footNoteName, String officeContext)
	{
		// FooteNoteSet ist der Container aller FootNotes
		FootNotes footNotes =  (FootNotes) OfficeUtils.findObject(AddressPackage.eINSTANCE.getFootNotes());
				
		EList<FootNote> allFootNotes = footNotes.getFootNotes();
		if (footNotes != null)
		{
			for (FootNote footNote : allFootNotes)
			{
				if (StringUtils.equals(footNote.getName(), footNoteName)
						&& StringUtils.equals(footNote.getContext(),officeContext))
					return footNote;
			}
		}
		return null;
	}


	/*
	 * Absenderdaten aus dem Dokument lesen und temporaer in einem EMF-Objekt
	 * speichern Standardfunktion
	 * 
	 */
	// Label des aus dem Dokument gelesenen Absenders
	public static final String LOADED_ABSENDER = "Absender aus der Datei";

	/**
	 * Absender aus dem Dokument lesen
	 * @param odfDocument
	 * @return
	 */
	public static Absender readAbsenderFromDocument(TextDocument odfDocument)
	{
		Absender tempAbsender = null;
		Table table = odfDocument.getTableByName(IODFWriteAdapter.ODF_WRITESENDER);
		if (table != null)
		{
			// einen Absender erstellen (repraesentiert die Adresse des
			// Dokuments)
			EClass absenderClass = AddressPackage.eINSTANCE.getAbsender();
			tempAbsender = (Absender) EcoreUtil.create(absenderClass);
			tempAbsender.setName(LOADED_ABSENDER);

			// StandardOfficeContext eintragen
			tempAbsender.setContext(OfficeDefaultPreferenceUtils.DEFAULT_OFFICE_CONTEXT);

			// Absenderadresse erzeugen
			EClass adresseClass = AddressPackage.eINSTANCE.getAdresse();
			Adresse adresse = (Adresse) EcoreUtil.create(adresseClass);
			tempAbsender.setAdresse(adresse);

			// den eingelesenen Absender temporaer zum EMF-Modell hinzufuegen
			EObject container = OfficeUtils.findObject(AddressPackage.eINSTANCE.getSender());
			EditingDomain domain = AdapterFactoryEditingDomain.getEditingDomainFor(container);
			EReference eReference = AddressPackage.eINSTANCE.getSender_Senders();
			
			Command addCommand = AddCommand.create(domain, container, eReference,tempAbsender);
			if (addCommand.canExecute())
				domain.getCommandStack().execute(addCommand);

			// Absenderadresse aus dem Dokument lesen
			int rowCount = table.getRowCount();
			for (int i = 0; i < rowCount; i++)
			{
				String rowValue = ODFDocumentUtils.readTableText(table, i, 0);
				if (StringUtils.isNotEmpty(rowValue))
				{
					// Name
					String checkAdressName = adresse.getName();
					if (StringUtils.isEmpty(checkAdressName))
					{
						adresse.setName(rowValue);
						continue;
					}
					// Strasse, Ort
					String[] strOrt = StringUtils.split(rowValue, ",");
					if (strOrt.length > 0)
						adresse.setStrasse(strOrt[0]);
					if (strOrt.length > 1)
						adresse.setOrt(strOrt[1]);
				}
			}
		}
		return tempAbsender;
	}

	/*
	 * 
	 * Die Adresse des selektierten Absenders wird in das TextDokument
	 * geschrieben. Standardfunktion
	 * 
	 * Rueckgabe: true ohne erkannbaren Fehler erfolgt
	 * 
	 */
	public static boolean writeAbsenderToDocument(TextDocument odfDocument,
			Absender absender)
	{
		if ((absender != null) && (odfDocument != null))
		{
			// Adresstabelle im Dokument addressieren
			Table table = odfDocument
					.getTableByName(IODFWriteAdapter.ODF_WRITESENDER);

			// Tabelle loeschen
			CellRange cellRange = ODFDocumentUtils.markTable(table);
			ODFDocumentUtils.clearCellRange(cellRange);

			// Modelldaten (Adresse) in das Dokument schreiben
			Adresse adresse = absender.getAdresse();
			if (adresse != null)
			{
				String value = adresse.getName();
				if (StringUtils.isNotEmpty(value))
					ODFDocumentUtils.writeTableText(table, 1, 0, value);

				value = "";
				String strasse = adresse.getStrasse();
				if (StringUtils.isNotEmpty(strasse))
					value = strasse + ",";

				String ort = adresse.getOrt();
				if (StringUtils.isNotEmpty(ort))
					value = value + ort;

				if (StringUtils.isNotEmpty(value))
					ODFDocumentUtils.writeTableText(table, 2, 0, value);

				return true;
			}
		}
		return false;
	}

	/*
	 * public static Sender getSender(String context) { // nach 'context'
	 * gefilterte Absender List<Absender>contextAbsender = new
	 * ArrayList<Absender>();
	 * 
	 * // die nach context gefilterten Elemente hinzufuegen ECPProject
	 * ecpProject = getOfficeProject(); if(ecpProject != null) { EList<Object>
	 * projectContents = ecpProject.getContents(); if
	 * (!projectContents.isEmpty()) { for (Object projectContent :
	 * projectContents) { if (projectContents instanceof Sender) { Sender sender
	 * = (Sender) projectContents; List<Absender>allAbsender =
	 * sender.getSenders(); for(Absender absender : allAbsender) {
	 * if(StringUtils.equals(absender.getContext(), context))
	 * contextAbsender.add(absender); } break; } } } }
	 * 
	 * // Sender mit den gefilterten Absender zurueckgeben EClass sendersClass =
	 * AddressPackage.eINSTANCE.getSender(); Sender sender = (Sender)
	 * EcoreUtil.create(sendersClass);
	 * sender.getSenders().addAll(contextAbsender);
	 * 
	 * return sender; }
	 */

	public static Sender getSender()
	{
		if (senders != null)
			return senders;

		ECPProject ecpProject = getOfficeProject();
		if (ecpProject != null)
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

			if (senders == null)
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
	 * Listet alle Kontakte mit den Namen im Array 'kontactNames' Sind mehrer
	 * Kontakte unter dem gleichen Namen gespeichert (soll eigentlich verhindert
	 * werden) wird nur der zuerst gefundene Kontakt gelistet.
	 * 
	 * @param kontactNames
	 * @return
	 */
	public static List<Kontakt> findKontactsByNames(String[] kontactNames)
	{
		List<Kontakt> kontakte = new ArrayList<Kontakt>();
		if (ArrayUtils.isNotEmpty(kontactNames))
		{
			EList<Kontakt> kontacts = getKontakte().getKontakte();
			for (String kontactName : kontactNames)
			{
				Kontakt kontakt = findKontactByName(kontacts, kontactName);
				if (kontakt != null)
					kontakte.add(kontakt);
			}
		}

		return kontakte;
	}

	// sucht einen Kontakt ueber seinen Namen
	private static Kontakt findKontactByName(EList<Kontakt> kontacts,
			String kontactName)
	{
		for (Kontakt kontact : kontacts)
		{
			if (StringUtils.equals(kontact.getName(), kontactName))
				return kontact;
		}

		return null;
	}

	/**
	 * Zurueckgegeben wird ein Container indem die Kontakte eines NtProjekts
	 * gehalten werden.
	 * 
	 * Wird kein Eintrag gefunden oder ist ist die 'projectID' == null wird ein
	 * 'leerer' Container zurueckgegeben
	 * 
	 * @return
	 */
	public static NtProjektKontakte getProjectKontacts(String ntProjectID)
	{
		NtProjektKontakte ntKontakte;

		if (StringUtils.isNotEmpty(ntProjectID))
		{
			ECPProject ecpProject = getOfficeProject();
			if (ecpProject != null)
			{
				EList<Object> projectContents = ecpProject.getContents();
				if (!projectContents.isEmpty())
				{
					for (Object projectContent : projectContents)
					{
						if (projectContent instanceof NtProjektKontakte)
						{
							ntKontakte = (NtProjektKontakte) projectContent;
							if (StringUtils.equals(ntKontakte.getNtProjektID(),
									ntProjectID))
								return ntKontakte;
						}
					}
				}
			}
		}

		// einen Conteiner erzeugen und zureckgeben
		EClass ntProjektClass = AddressPackage.eINSTANCE.getNtProjektKontakte();
		ntKontakte = (NtProjektKontakte) EcoreUtil.create(ntProjektClass);
		return ntKontakte;
	}

	/**
	 * Gibt den Container einer Referenz zurueck. Die Referenz wird als EClass
	 * uebergeben.
	 * 
	 * @param senderClassName
	 * @return
	 */
	/*
	public static EObject getContainer(EClass referenceClass)
	{
		ECPProject ecpProject = getOfficeProject();
		EList<Object> projectContents = ecpProject.getContents();
		if (!projectContents.isEmpty())
		{
			// ueber das instantiierte Object kann der Name ermittelt werden
			EObject checkObject = EcoreUtil.create(referenceClass);
			String checkName = checkObject.eClass().getName();

			for (Object projectContent : projectContents)
			{
				EObject projectContentObj = (EObject) projectContent;
				String contentName = projectContentObj.eClass().getName();

				if (StringUtils.equals(checkName, contentName))
					return projectContentObj;
			}
		}

		return null;
	}
	*/

	/*
	 * Mit Eingabe der Objectklasse wird im OfficeProjekt gesucht.
	 * Wird die Klasse nicht gefunden, wird das instanziierte Objekt hinzugefuegt.
	 * 
	 */
	public static EObject findObject(EClass objectClass)
	{
		ECPProject ecpProject = getOfficeProject();
		EList<Object> projectContents = ecpProject.getContents();

		// die Klasse wird instanziiert
		EObject checkObject = EcoreUtil.create(objectClass);
		String checkName = checkObject.eClass().getName();

		// ueber den Objektnamen suchen
		for (Object projectContent : projectContents)
		{
			EObject projectContentObj = (EObject) projectContent;
			String contentName = projectContentObj.eClass().getName();

			if (StringUtils.equals(checkName, contentName))
				return projectContentObj;
		}

		// noch nicht vorhanden, Objekt wird hinzugefuegt
		projectContents.add(checkObject);
		ECPHandlerHelper.saveProject(OfficeUtils.getOfficeProject());
		return checkObject;
	}

}
