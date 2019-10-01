/**
 */
package it.naturtalent.office.model.address;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see it.naturtalent.office.model.address.AddressFactory
 * @model kind="package"
 * @generated
 */
public interface AddressPackage extends EPackage
{
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "address";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://it.naturtalent/address";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "address";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	AddressPackage eINSTANCE = it.naturtalent.office.model.address.impl.AddressPackageImpl.init();

	/**
	 * The meta object id for the '{@link it.naturtalent.office.model.address.impl.AdresseImpl <em>Adresse</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see it.naturtalent.office.model.address.impl.AdresseImpl
	 * @see it.naturtalent.office.model.address.impl.AddressPackageImpl#getAdresse()
	 * @generated
	 */
	int ADRESSE = 1;

	/**
	 * The meta object id for the '{@link it.naturtalent.office.model.address.impl.ReferenzImpl <em>Referenz</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see it.naturtalent.office.model.address.impl.ReferenzImpl
	 * @see it.naturtalent.office.model.address.impl.AddressPackageImpl#getReferenz()
	 * @generated
	 */
	int REFERENZ = 10;

	/**
	 * The meta object id for the '{@link it.naturtalent.office.model.address.impl.AbsenderImpl <em>Absender</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see it.naturtalent.office.model.address.impl.AbsenderImpl
	 * @see it.naturtalent.office.model.address.impl.AddressPackageImpl#getAbsender()
	 * @generated
	 */
	int ABSENDER = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSENDER__NAME = 0;

	/**
	 * The feature id for the '<em><b>Adresse</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSENDER__ADRESSE = 1;

	/**
	 * The feature id for the '<em><b>Context</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSENDER__CONTEXT = 2;

	/**
	 * The number of structural features of the '<em>Absender</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSENDER_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Absender</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSENDER_OPERATION_COUNT = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADRESSE__NAME = 0;

	/**
	 * The feature id for the '<em><b>Name2</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADRESSE__NAME2 = 1;

	/**
	 * The feature id for the '<em><b>Name3</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADRESSE__NAME3 = 2;

	/**
	 * The feature id for the '<em><b>Strasse</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADRESSE__STRASSE = 3;

	/**
	 * The feature id for the '<em><b>Plz</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADRESSE__PLZ = 4;

	/**
	 * The feature id for the '<em><b>Ort</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADRESSE__ORT = 5;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADRESSE__TYPE = 6;

	/**
	 * The number of structural features of the '<em>Adresse</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADRESSE_FEATURE_COUNT = 7;

	/**
	 * The number of operations of the '<em>Adresse</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADRESSE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link it.naturtalent.office.model.address.impl.EmpfaengerImpl <em>Empfaenger</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see it.naturtalent.office.model.address.impl.EmpfaengerImpl
	 * @see it.naturtalent.office.model.address.impl.AddressPackageImpl#getEmpfaenger()
	 * @generated
	 */
	int EMPFAENGER = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EMPFAENGER__NAME = 0;

	/**
	 * The feature id for the '<em><b>Adresse</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EMPFAENGER__ADRESSE = 1;

	/**
	 * The feature id for the '<em><b>Context</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EMPFAENGER__CONTEXT = 2;

	/**
	 * The number of structural features of the '<em>Empfaenger</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EMPFAENGER_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Empfaenger</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EMPFAENGER_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link it.naturtalent.office.model.address.impl.ReceiversImpl <em>Receivers</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see it.naturtalent.office.model.address.impl.ReceiversImpl
	 * @see it.naturtalent.office.model.address.impl.AddressPackageImpl#getReceivers()
	 * @generated
	 */
	int RECEIVERS = 3;

	/**
	 * The feature id for the '<em><b>Receivers</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RECEIVERS__RECEIVERS = 0;

	/**
	 * The number of structural features of the '<em>Receivers</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RECEIVERS_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Receivers</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RECEIVERS_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link it.naturtalent.office.model.address.impl.KontaktImpl <em>Kontakt</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see it.naturtalent.office.model.address.impl.KontaktImpl
	 * @see it.naturtalent.office.model.address.impl.AddressPackageImpl#getKontakt()
	 * @generated
	 */
	int KONTAKT = 4;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int KONTAKT__NAME = 0;

	/**
	 * The feature id for the '<em><b>Adresse</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int KONTAKT__ADRESSE = 1;

	/**
	 * The feature id for the '<em><b>Kommunikation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int KONTAKT__KOMMUNIKATION = 2;

	/**
	 * The number of structural features of the '<em>Kontakt</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int KONTAKT_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Kontakt</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int KONTAKT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link it.naturtalent.office.model.address.impl.KontakteImpl <em>Kontakte</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see it.naturtalent.office.model.address.impl.KontakteImpl
	 * @see it.naturtalent.office.model.address.impl.AddressPackageImpl#getKontakte()
	 * @generated
	 */
	int KONTAKTE = 5;

	/**
	 * The feature id for the '<em><b>Kontakte</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int KONTAKTE__KONTAKTE = 0;

	/**
	 * The number of structural features of the '<em>Kontakte</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int KONTAKTE_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Kontakte</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int KONTAKTE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link it.naturtalent.office.model.address.impl.NtProjektKontakteImpl <em>Nt Projekt Kontakte</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see it.naturtalent.office.model.address.impl.NtProjektKontakteImpl
	 * @see it.naturtalent.office.model.address.impl.AddressPackageImpl#getNtProjektKontakte()
	 * @generated
	 */
	int NT_PROJEKT_KONTAKTE = 6;

	/**
	 * The feature id for the '<em><b>Nt Projekt ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NT_PROJEKT_KONTAKTE__NT_PROJEKT_ID = 0;

	/**
	 * The feature id for the '<em><b>Kontakte</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NT_PROJEKT_KONTAKTE__KONTAKTE = 1;

	/**
	 * The number of structural features of the '<em>Nt Projekt Kontakte</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NT_PROJEKT_KONTAKTE_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Nt Projekt Kontakte</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NT_PROJEKT_KONTAKTE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link it.naturtalent.office.model.address.impl.FootNoteItemImpl <em>Foot Note Item</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see it.naturtalent.office.model.address.impl.FootNoteItemImpl
	 * @see it.naturtalent.office.model.address.impl.AddressPackageImpl#getFootNoteItem()
	 * @generated
	 */
	int FOOT_NOTE_ITEM = 7;

	/**
	 * The feature id for the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOOT_NOTE_ITEM__KEY = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOOT_NOTE_ITEM__VALUE = 1;

	/**
	 * The number of structural features of the '<em>Foot Note Item</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOOT_NOTE_ITEM_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Foot Note Item</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOOT_NOTE_ITEM_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link it.naturtalent.office.model.address.impl.FootNoteImpl <em>Foot Note</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see it.naturtalent.office.model.address.impl.FootNoteImpl
	 * @see it.naturtalent.office.model.address.impl.AddressPackageImpl#getFootNote()
	 * @generated
	 */
	int FOOT_NOTE = 8;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOOT_NOTE__NAME = 0;

	/**
	 * The feature id for the '<em><b>Context</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOOT_NOTE__CONTEXT = 1;

	/**
	 * The feature id for the '<em><b>Footnoteitems</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOOT_NOTE__FOOTNOTEITEMS = 2;

	/**
	 * The number of structural features of the '<em>Foot Note</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOOT_NOTE_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Foot Note</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOOT_NOTE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link it.naturtalent.office.model.address.impl.FootNotesImpl <em>Foot Notes</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see it.naturtalent.office.model.address.impl.FootNotesImpl
	 * @see it.naturtalent.office.model.address.impl.AddressPackageImpl#getFootNotes()
	 * @generated
	 */
	int FOOT_NOTES = 9;

	/**
	 * The feature id for the '<em><b>Foot Notes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOOT_NOTES__FOOT_NOTES = 0;

	/**
	 * The number of structural features of the '<em>Foot Notes</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOOT_NOTES_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Foot Notes</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOOT_NOTES_OPERATION_COUNT = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFERENZ__NAME = 0;

	/**
	 * The feature id for the '<em><b>Context</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFERENZ__CONTEXT = 1;

	/**
	 * The feature id for the '<em><b>Referenz</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFERENZ__REFERENZ = 2;

	/**
	 * The feature id for the '<em><b>Referenz2</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFERENZ__REFERENZ2 = 3;

	/**
	 * The feature id for the '<em><b>Referenz3</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFERENZ__REFERENZ3 = 4;

	/**
	 * The number of structural features of the '<em>Referenz</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFERENZ_FEATURE_COUNT = 5;

	/**
	 * The number of operations of the '<em>Referenz</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFERENZ_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link it.naturtalent.office.model.address.impl.ReferenzenImpl <em>Referenzen</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see it.naturtalent.office.model.address.impl.ReferenzenImpl
	 * @see it.naturtalent.office.model.address.impl.AddressPackageImpl#getReferenzen()
	 * @generated
	 */
	int REFERENZEN = 11;

	/**
	 * The feature id for the '<em><b>Referenzen</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFERENZEN__REFERENZEN = 0;

	/**
	 * The number of structural features of the '<em>Referenzen</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFERENZEN_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Referenzen</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFERENZEN_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link it.naturtalent.office.model.address.impl.SenderImpl <em>Sender</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see it.naturtalent.office.model.address.impl.SenderImpl
	 * @see it.naturtalent.office.model.address.impl.AddressPackageImpl#getSender()
	 * @generated
	 */
	int SENDER = 12;

	/**
	 * The feature id for the '<em><b>Senders</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SENDER__SENDERS = 0;

	/**
	 * The number of structural features of the '<em>Sender</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SENDER_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Sender</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SENDER_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link it.naturtalent.office.model.address.impl.SignatureImpl <em>Signature</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see it.naturtalent.office.model.address.impl.SignatureImpl
	 * @see it.naturtalent.office.model.address.impl.AddressPackageImpl#getSignature()
	 * @generated
	 */
	int SIGNATURE = 13;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIGNATURE__NAME = 0;

	/**
	 * The feature id for the '<em><b>Greeting</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIGNATURE__GREETING = 1;

	/**
	 * The feature id for the '<em><b>Signer</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIGNATURE__SIGNER = 2;

	/**
	 * The feature id for the '<em><b>Cosigner</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIGNATURE__COSIGNER = 3;

	/**
	 * The feature id for the '<em><b>Context</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIGNATURE__CONTEXT = 4;

	/**
	 * The feature id for the '<em><b>Status1</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIGNATURE__STATUS1 = 5;

	/**
	 * The feature id for the '<em><b>Status2</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIGNATURE__STATUS2 = 6;

	/**
	 * The number of structural features of the '<em>Signature</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIGNATURE_FEATURE_COUNT = 7;

	/**
	 * The number of operations of the '<em>Signature</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIGNATURE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link it.naturtalent.office.model.address.impl.SignatureSetImpl <em>Signature Set</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see it.naturtalent.office.model.address.impl.SignatureSetImpl
	 * @see it.naturtalent.office.model.address.impl.AddressPackageImpl#getSignatureSet()
	 * @generated
	 */
	int SIGNATURE_SET = 14;

	/**
	 * The feature id for the '<em><b>Signatures</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIGNATURE_SET__SIGNATURES = 0;

	/**
	 * The number of structural features of the '<em>Signature Set</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIGNATURE_SET_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Signature Set</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIGNATURE_SET_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link it.naturtalent.office.model.address.AddressType <em>Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see it.naturtalent.office.model.address.AddressType
	 * @see it.naturtalent.office.model.address.impl.AddressPackageImpl#getAddressType()
	 * @generated
	 */
	int ADDRESS_TYPE = 15;


	/**
	 * Returns the meta object for class '{@link it.naturtalent.office.model.address.Adresse <em>Adresse</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Adresse</em>'.
	 * @see it.naturtalent.office.model.address.Adresse
	 * @generated
	 */
	EClass getAdresse();

	/**
	 * Returns the meta object for the attribute '{@link it.naturtalent.office.model.address.Adresse#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see it.naturtalent.office.model.address.Adresse#getName()
	 * @see #getAdresse()
	 * @generated
	 */
	EAttribute getAdresse_Name();

	/**
	 * Returns the meta object for the attribute '{@link it.naturtalent.office.model.address.Adresse#getName2 <em>Name2</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name2</em>'.
	 * @see it.naturtalent.office.model.address.Adresse#getName2()
	 * @see #getAdresse()
	 * @generated
	 */
	EAttribute getAdresse_Name2();

	/**
	 * Returns the meta object for the attribute '{@link it.naturtalent.office.model.address.Adresse#getName3 <em>Name3</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name3</em>'.
	 * @see it.naturtalent.office.model.address.Adresse#getName3()
	 * @see #getAdresse()
	 * @generated
	 */
	EAttribute getAdresse_Name3();

	/**
	 * Returns the meta object for the attribute '{@link it.naturtalent.office.model.address.Adresse#getStrasse <em>Strasse</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Strasse</em>'.
	 * @see it.naturtalent.office.model.address.Adresse#getStrasse()
	 * @see #getAdresse()
	 * @generated
	 */
	EAttribute getAdresse_Strasse();

	/**
	 * Returns the meta object for the attribute '{@link it.naturtalent.office.model.address.Adresse#getPlz <em>Plz</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Plz</em>'.
	 * @see it.naturtalent.office.model.address.Adresse#getPlz()
	 * @see #getAdresse()
	 * @generated
	 */
	EAttribute getAdresse_Plz();

	/**
	 * Returns the meta object for the attribute '{@link it.naturtalent.office.model.address.Adresse#getOrt <em>Ort</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Ort</em>'.
	 * @see it.naturtalent.office.model.address.Adresse#getOrt()
	 * @see #getAdresse()
	 * @generated
	 */
	EAttribute getAdresse_Ort();

	/**
	 * Returns the meta object for the attribute '{@link it.naturtalent.office.model.address.Adresse#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see it.naturtalent.office.model.address.Adresse#getType()
	 * @see #getAdresse()
	 * @generated
	 */
	EAttribute getAdresse_Type();

	/**
	 * Returns the meta object for class '{@link it.naturtalent.office.model.address.Referenz <em>Referenz</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Referenz</em>'.
	 * @see it.naturtalent.office.model.address.Referenz
	 * @generated
	 */
	EClass getReferenz();

	/**
	 * Returns the meta object for the attribute '{@link it.naturtalent.office.model.address.Referenz#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see it.naturtalent.office.model.address.Referenz#getName()
	 * @see #getReferenz()
	 * @generated
	 */
	EAttribute getReferenz_Name();

	/**
	 * Returns the meta object for the attribute '{@link it.naturtalent.office.model.address.Referenz#getContext <em>Context</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Context</em>'.
	 * @see it.naturtalent.office.model.address.Referenz#getContext()
	 * @see #getReferenz()
	 * @generated
	 */
	EAttribute getReferenz_Context();

	/**
	 * Returns the meta object for the attribute '{@link it.naturtalent.office.model.address.Referenz#getReferenz <em>Referenz</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Referenz</em>'.
	 * @see it.naturtalent.office.model.address.Referenz#getReferenz()
	 * @see #getReferenz()
	 * @generated
	 */
	EAttribute getReferenz_Referenz();

	/**
	 * Returns the meta object for the attribute '{@link it.naturtalent.office.model.address.Referenz#getReferenz2 <em>Referenz2</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Referenz2</em>'.
	 * @see it.naturtalent.office.model.address.Referenz#getReferenz2()
	 * @see #getReferenz()
	 * @generated
	 */
	EAttribute getReferenz_Referenz2();

	/**
	 * Returns the meta object for the attribute '{@link it.naturtalent.office.model.address.Referenz#getReferenz3 <em>Referenz3</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Referenz3</em>'.
	 * @see it.naturtalent.office.model.address.Referenz#getReferenz3()
	 * @see #getReferenz()
	 * @generated
	 */
	EAttribute getReferenz_Referenz3();

	/**
	 * Returns the meta object for class '{@link it.naturtalent.office.model.address.Referenzen <em>Referenzen</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Referenzen</em>'.
	 * @see it.naturtalent.office.model.address.Referenzen
	 * @generated
	 */
	EClass getReferenzen();

	/**
	 * Returns the meta object for the containment reference list '{@link it.naturtalent.office.model.address.Referenzen#getReferenzen <em>Referenzen</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Referenzen</em>'.
	 * @see it.naturtalent.office.model.address.Referenzen#getReferenzen()
	 * @see #getReferenzen()
	 * @generated
	 */
	EReference getReferenzen_Referenzen();

	/**
	 * Returns the meta object for class '{@link it.naturtalent.office.model.address.Empfaenger <em>Empfaenger</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Empfaenger</em>'.
	 * @see it.naturtalent.office.model.address.Empfaenger
	 * @generated
	 */
	EClass getEmpfaenger();

	/**
	 * Returns the meta object for the attribute '{@link it.naturtalent.office.model.address.Empfaenger#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see it.naturtalent.office.model.address.Empfaenger#getName()
	 * @see #getEmpfaenger()
	 * @generated
	 */
	EAttribute getEmpfaenger_Name();

	/**
	 * Returns the meta object for the containment reference '{@link it.naturtalent.office.model.address.Empfaenger#getAdresse <em>Adresse</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Adresse</em>'.
	 * @see it.naturtalent.office.model.address.Empfaenger#getAdresse()
	 * @see #getEmpfaenger()
	 * @generated
	 */
	EReference getEmpfaenger_Adresse();

	/**
	 * Returns the meta object for the attribute '{@link it.naturtalent.office.model.address.Empfaenger#getContext <em>Context</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Context</em>'.
	 * @see it.naturtalent.office.model.address.Empfaenger#getContext()
	 * @see #getEmpfaenger()
	 * @generated
	 */
	EAttribute getEmpfaenger_Context();

	/**
	 * Returns the meta object for class '{@link it.naturtalent.office.model.address.Receivers <em>Receivers</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Receivers</em>'.
	 * @see it.naturtalent.office.model.address.Receivers
	 * @generated
	 */
	EClass getReceivers();

	/**
	 * Returns the meta object for the containment reference list '{@link it.naturtalent.office.model.address.Receivers#getReceivers <em>Receivers</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Receivers</em>'.
	 * @see it.naturtalent.office.model.address.Receivers#getReceivers()
	 * @see #getReceivers()
	 * @generated
	 */
	EReference getReceivers_Receivers();

	/**
	 * Returns the meta object for class '{@link it.naturtalent.office.model.address.Kontakt <em>Kontakt</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Kontakt</em>'.
	 * @see it.naturtalent.office.model.address.Kontakt
	 * @generated
	 */
	EClass getKontakt();

	/**
	 * Returns the meta object for the attribute '{@link it.naturtalent.office.model.address.Kontakt#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see it.naturtalent.office.model.address.Kontakt#getName()
	 * @see #getKontakt()
	 * @generated
	 */
	EAttribute getKontakt_Name();

	/**
	 * Returns the meta object for the containment reference '{@link it.naturtalent.office.model.address.Kontakt#getAdresse <em>Adresse</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Adresse</em>'.
	 * @see it.naturtalent.office.model.address.Kontakt#getAdresse()
	 * @see #getKontakt()
	 * @generated
	 */
	EReference getKontakt_Adresse();

	/**
	 * Returns the meta object for the attribute '{@link it.naturtalent.office.model.address.Kontakt#getKommunikation <em>Kommunikation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Kommunikation</em>'.
	 * @see it.naturtalent.office.model.address.Kontakt#getKommunikation()
	 * @see #getKontakt()
	 * @generated
	 */
	EAttribute getKontakt_Kommunikation();

	/**
	 * Returns the meta object for class '{@link it.naturtalent.office.model.address.Kontakte <em>Kontakte</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Kontakte</em>'.
	 * @see it.naturtalent.office.model.address.Kontakte
	 * @generated
	 */
	EClass getKontakte();

	/**
	 * Returns the meta object for the containment reference list '{@link it.naturtalent.office.model.address.Kontakte#getKontakte <em>Kontakte</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Kontakte</em>'.
	 * @see it.naturtalent.office.model.address.Kontakte#getKontakte()
	 * @see #getKontakte()
	 * @generated
	 */
	EReference getKontakte_Kontakte();

	/**
	 * Returns the meta object for class '{@link it.naturtalent.office.model.address.NtProjektKontakte <em>Nt Projekt Kontakte</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Nt Projekt Kontakte</em>'.
	 * @see it.naturtalent.office.model.address.NtProjektKontakte
	 * @generated
	 */
	EClass getNtProjektKontakte();

	/**
	 * Returns the meta object for the attribute '{@link it.naturtalent.office.model.address.NtProjektKontakte#getNtProjektID <em>Nt Projekt ID</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Nt Projekt ID</em>'.
	 * @see it.naturtalent.office.model.address.NtProjektKontakte#getNtProjektID()
	 * @see #getNtProjektKontakte()
	 * @generated
	 */
	EAttribute getNtProjektKontakte_NtProjektID();

	/**
	 * Returns the meta object for the containment reference list '{@link it.naturtalent.office.model.address.NtProjektKontakte#getKontakte <em>Kontakte</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Kontakte</em>'.
	 * @see it.naturtalent.office.model.address.NtProjektKontakte#getKontakte()
	 * @see #getNtProjektKontakte()
	 * @generated
	 */
	EReference getNtProjektKontakte_Kontakte();

	/**
	 * Returns the meta object for class '{@link it.naturtalent.office.model.address.FootNoteItem <em>Foot Note Item</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Foot Note Item</em>'.
	 * @see it.naturtalent.office.model.address.FootNoteItem
	 * @generated
	 */
	EClass getFootNoteItem();

	/**
	 * Returns the meta object for the attribute '{@link it.naturtalent.office.model.address.FootNoteItem#getKey <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Key</em>'.
	 * @see it.naturtalent.office.model.address.FootNoteItem#getKey()
	 * @see #getFootNoteItem()
	 * @generated
	 */
	EAttribute getFootNoteItem_Key();

	/**
	 * Returns the meta object for the attribute '{@link it.naturtalent.office.model.address.FootNoteItem#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see it.naturtalent.office.model.address.FootNoteItem#getValue()
	 * @see #getFootNoteItem()
	 * @generated
	 */
	EAttribute getFootNoteItem_Value();

	/**
	 * Returns the meta object for class '{@link it.naturtalent.office.model.address.FootNote <em>Foot Note</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Foot Note</em>'.
	 * @see it.naturtalent.office.model.address.FootNote
	 * @generated
	 */
	EClass getFootNote();

	/**
	 * Returns the meta object for the attribute '{@link it.naturtalent.office.model.address.FootNote#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see it.naturtalent.office.model.address.FootNote#getName()
	 * @see #getFootNote()
	 * @generated
	 */
	EAttribute getFootNote_Name();

	/**
	 * Returns the meta object for the attribute '{@link it.naturtalent.office.model.address.FootNote#getContext <em>Context</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Context</em>'.
	 * @see it.naturtalent.office.model.address.FootNote#getContext()
	 * @see #getFootNote()
	 * @generated
	 */
	EAttribute getFootNote_Context();

	/**
	 * Returns the meta object for the containment reference list '{@link it.naturtalent.office.model.address.FootNote#getFootnoteitems <em>Footnoteitems</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Footnoteitems</em>'.
	 * @see it.naturtalent.office.model.address.FootNote#getFootnoteitems()
	 * @see #getFootNote()
	 * @generated
	 */
	EReference getFootNote_Footnoteitems();

	/**
	 * Returns the meta object for class '{@link it.naturtalent.office.model.address.FootNotes <em>Foot Notes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Foot Notes</em>'.
	 * @see it.naturtalent.office.model.address.FootNotes
	 * @generated
	 */
	EClass getFootNotes();

	/**
	 * Returns the meta object for the containment reference list '{@link it.naturtalent.office.model.address.FootNotes#getFootNotes <em>Foot Notes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Foot Notes</em>'.
	 * @see it.naturtalent.office.model.address.FootNotes#getFootNotes()
	 * @see #getFootNotes()
	 * @generated
	 */
	EReference getFootNotes_FootNotes();

	/**
	 * Returns the meta object for class '{@link it.naturtalent.office.model.address.Sender <em>Sender</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Sender</em>'.
	 * @see it.naturtalent.office.model.address.Sender
	 * @generated
	 */
	EClass getSender();

	/**
	 * Returns the meta object for the containment reference list '{@link it.naturtalent.office.model.address.Sender#getSenders <em>Senders</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Senders</em>'.
	 * @see it.naturtalent.office.model.address.Sender#getSenders()
	 * @see #getSender()
	 * @generated
	 */
	EReference getSender_Senders();

	/**
	 * Returns the meta object for class '{@link it.naturtalent.office.model.address.Signature <em>Signature</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Signature</em>'.
	 * @see it.naturtalent.office.model.address.Signature
	 * @generated
	 */
	EClass getSignature();

	/**
	 * Returns the meta object for the attribute '{@link it.naturtalent.office.model.address.Signature#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see it.naturtalent.office.model.address.Signature#getName()
	 * @see #getSignature()
	 * @generated
	 */
	EAttribute getSignature_Name();

	/**
	 * Returns the meta object for the attribute '{@link it.naturtalent.office.model.address.Signature#getGreeting <em>Greeting</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Greeting</em>'.
	 * @see it.naturtalent.office.model.address.Signature#getGreeting()
	 * @see #getSignature()
	 * @generated
	 */
	EAttribute getSignature_Greeting();

	/**
	 * Returns the meta object for the attribute '{@link it.naturtalent.office.model.address.Signature#getSigner <em>Signer</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Signer</em>'.
	 * @see it.naturtalent.office.model.address.Signature#getSigner()
	 * @see #getSignature()
	 * @generated
	 */
	EAttribute getSignature_Signer();

	/**
	 * Returns the meta object for the attribute '{@link it.naturtalent.office.model.address.Signature#getCosigner <em>Cosigner</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Cosigner</em>'.
	 * @see it.naturtalent.office.model.address.Signature#getCosigner()
	 * @see #getSignature()
	 * @generated
	 */
	EAttribute getSignature_Cosigner();

	/**
	 * Returns the meta object for the attribute '{@link it.naturtalent.office.model.address.Signature#getContext <em>Context</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Context</em>'.
	 * @see it.naturtalent.office.model.address.Signature#getContext()
	 * @see #getSignature()
	 * @generated
	 */
	EAttribute getSignature_Context();

	/**
	 * Returns the meta object for the attribute '{@link it.naturtalent.office.model.address.Signature#getStatus1 <em>Status1</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Status1</em>'.
	 * @see it.naturtalent.office.model.address.Signature#getStatus1()
	 * @see #getSignature()
	 * @generated
	 */
	EAttribute getSignature_Status1();

	/**
	 * Returns the meta object for the attribute '{@link it.naturtalent.office.model.address.Signature#getStatus2 <em>Status2</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Status2</em>'.
	 * @see it.naturtalent.office.model.address.Signature#getStatus2()
	 * @see #getSignature()
	 * @generated
	 */
	EAttribute getSignature_Status2();

	/**
	 * Returns the meta object for class '{@link it.naturtalent.office.model.address.SignatureSet <em>Signature Set</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Signature Set</em>'.
	 * @see it.naturtalent.office.model.address.SignatureSet
	 * @generated
	 */
	EClass getSignatureSet();

	/**
	 * Returns the meta object for the containment reference list '{@link it.naturtalent.office.model.address.SignatureSet#getSignatures <em>Signatures</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Signatures</em>'.
	 * @see it.naturtalent.office.model.address.SignatureSet#getSignatures()
	 * @see #getSignatureSet()
	 * @generated
	 */
	EReference getSignatureSet_Signatures();

	/**
	 * Returns the meta object for class '{@link it.naturtalent.office.model.address.Absender <em>Absender</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Absender</em>'.
	 * @see it.naturtalent.office.model.address.Absender
	 * @generated
	 */
	EClass getAbsender();

	/**
	 * Returns the meta object for the attribute '{@link it.naturtalent.office.model.address.Absender#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see it.naturtalent.office.model.address.Absender#getName()
	 * @see #getAbsender()
	 * @generated
	 */
	EAttribute getAbsender_Name();

	/**
	 * Returns the meta object for the containment reference '{@link it.naturtalent.office.model.address.Absender#getAdresse <em>Adresse</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Adresse</em>'.
	 * @see it.naturtalent.office.model.address.Absender#getAdresse()
	 * @see #getAbsender()
	 * @generated
	 */
	EReference getAbsender_Adresse();

	/**
	 * Returns the meta object for the attribute '{@link it.naturtalent.office.model.address.Absender#getContext <em>Context</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Context</em>'.
	 * @see it.naturtalent.office.model.address.Absender#getContext()
	 * @see #getAbsender()
	 * @generated
	 */
	EAttribute getAbsender_Context();

	/**
	 * Returns the meta object for enum '{@link it.naturtalent.office.model.address.AddressType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Type</em>'.
	 * @see it.naturtalent.office.model.address.AddressType
	 * @generated
	 */
	EEnum getAddressType();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	AddressFactory getAddressFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals
	{
		/**
		 * The meta object literal for the '{@link it.naturtalent.office.model.address.impl.AdresseImpl <em>Adresse</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see it.naturtalent.office.model.address.impl.AdresseImpl
		 * @see it.naturtalent.office.model.address.impl.AddressPackageImpl#getAdresse()
		 * @generated
		 */
		EClass ADRESSE = eINSTANCE.getAdresse();
		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ADRESSE__NAME = eINSTANCE.getAdresse_Name();
		/**
		 * The meta object literal for the '<em><b>Name2</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ADRESSE__NAME2 = eINSTANCE.getAdresse_Name2();
		/**
		 * The meta object literal for the '<em><b>Name3</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ADRESSE__NAME3 = eINSTANCE.getAdresse_Name3();
		/**
		 * The meta object literal for the '<em><b>Strasse</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ADRESSE__STRASSE = eINSTANCE.getAdresse_Strasse();
		/**
		 * The meta object literal for the '<em><b>Plz</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ADRESSE__PLZ = eINSTANCE.getAdresse_Plz();
		/**
		 * The meta object literal for the '<em><b>Ort</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ADRESSE__ORT = eINSTANCE.getAdresse_Ort();
		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ADRESSE__TYPE = eINSTANCE.getAdresse_Type();
		/**
		 * The meta object literal for the '{@link it.naturtalent.office.model.address.impl.ReferenzImpl <em>Referenz</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see it.naturtalent.office.model.address.impl.ReferenzImpl
		 * @see it.naturtalent.office.model.address.impl.AddressPackageImpl#getReferenz()
		 * @generated
		 */
		EClass REFERENZ = eINSTANCE.getReferenz();
		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REFERENZ__NAME = eINSTANCE.getReferenz_Name();
		/**
		 * The meta object literal for the '<em><b>Context</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REFERENZ__CONTEXT = eINSTANCE.getReferenz_Context();
		/**
		 * The meta object literal for the '<em><b>Referenz</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REFERENZ__REFERENZ = eINSTANCE.getReferenz_Referenz();
		/**
		 * The meta object literal for the '<em><b>Referenz2</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REFERENZ__REFERENZ2 = eINSTANCE.getReferenz_Referenz2();
		/**
		 * The meta object literal for the '<em><b>Referenz3</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REFERENZ__REFERENZ3 = eINSTANCE.getReferenz_Referenz3();
		/**
		 * The meta object literal for the '{@link it.naturtalent.office.model.address.impl.ReferenzenImpl <em>Referenzen</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see it.naturtalent.office.model.address.impl.ReferenzenImpl
		 * @see it.naturtalent.office.model.address.impl.AddressPackageImpl#getReferenzen()
		 * @generated
		 */
		EClass REFERENZEN = eINSTANCE.getReferenzen();
		/**
		 * The meta object literal for the '<em><b>Referenzen</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REFERENZEN__REFERENZEN = eINSTANCE.getReferenzen_Referenzen();
		/**
		 * The meta object literal for the '{@link it.naturtalent.office.model.address.impl.EmpfaengerImpl <em>Empfaenger</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see it.naturtalent.office.model.address.impl.EmpfaengerImpl
		 * @see it.naturtalent.office.model.address.impl.AddressPackageImpl#getEmpfaenger()
		 * @generated
		 */
		EClass EMPFAENGER = eINSTANCE.getEmpfaenger();
		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EMPFAENGER__NAME = eINSTANCE.getEmpfaenger_Name();
		/**
		 * The meta object literal for the '<em><b>Adresse</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EMPFAENGER__ADRESSE = eINSTANCE.getEmpfaenger_Adresse();
		/**
		 * The meta object literal for the '<em><b>Context</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EMPFAENGER__CONTEXT = eINSTANCE.getEmpfaenger_Context();
		/**
		 * The meta object literal for the '{@link it.naturtalent.office.model.address.impl.ReceiversImpl <em>Receivers</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see it.naturtalent.office.model.address.impl.ReceiversImpl
		 * @see it.naturtalent.office.model.address.impl.AddressPackageImpl#getReceivers()
		 * @generated
		 */
		EClass RECEIVERS = eINSTANCE.getReceivers();
		/**
		 * The meta object literal for the '<em><b>Receivers</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RECEIVERS__RECEIVERS = eINSTANCE.getReceivers_Receivers();
		/**
		 * The meta object literal for the '{@link it.naturtalent.office.model.address.impl.KontaktImpl <em>Kontakt</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see it.naturtalent.office.model.address.impl.KontaktImpl
		 * @see it.naturtalent.office.model.address.impl.AddressPackageImpl#getKontakt()
		 * @generated
		 */
		EClass KONTAKT = eINSTANCE.getKontakt();
		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute KONTAKT__NAME = eINSTANCE.getKontakt_Name();
		/**
		 * The meta object literal for the '<em><b>Adresse</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference KONTAKT__ADRESSE = eINSTANCE.getKontakt_Adresse();
		/**
		 * The meta object literal for the '<em><b>Kommunikation</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute KONTAKT__KOMMUNIKATION = eINSTANCE.getKontakt_Kommunikation();
		/**
		 * The meta object literal for the '{@link it.naturtalent.office.model.address.impl.KontakteImpl <em>Kontakte</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see it.naturtalent.office.model.address.impl.KontakteImpl
		 * @see it.naturtalent.office.model.address.impl.AddressPackageImpl#getKontakte()
		 * @generated
		 */
		EClass KONTAKTE = eINSTANCE.getKontakte();
		/**
		 * The meta object literal for the '<em><b>Kontakte</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference KONTAKTE__KONTAKTE = eINSTANCE.getKontakte_Kontakte();
		/**
		 * The meta object literal for the '{@link it.naturtalent.office.model.address.impl.NtProjektKontakteImpl <em>Nt Projekt Kontakte</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see it.naturtalent.office.model.address.impl.NtProjektKontakteImpl
		 * @see it.naturtalent.office.model.address.impl.AddressPackageImpl#getNtProjektKontakte()
		 * @generated
		 */
		EClass NT_PROJEKT_KONTAKTE = eINSTANCE.getNtProjektKontakte();
		/**
		 * The meta object literal for the '<em><b>Nt Projekt ID</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NT_PROJEKT_KONTAKTE__NT_PROJEKT_ID = eINSTANCE.getNtProjektKontakte_NtProjektID();
		/**
		 * The meta object literal for the '<em><b>Kontakte</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NT_PROJEKT_KONTAKTE__KONTAKTE = eINSTANCE.getNtProjektKontakte_Kontakte();
		/**
		 * The meta object literal for the '{@link it.naturtalent.office.model.address.impl.FootNoteItemImpl <em>Foot Note Item</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see it.naturtalent.office.model.address.impl.FootNoteItemImpl
		 * @see it.naturtalent.office.model.address.impl.AddressPackageImpl#getFootNoteItem()
		 * @generated
		 */
		EClass FOOT_NOTE_ITEM = eINSTANCE.getFootNoteItem();
		/**
		 * The meta object literal for the '<em><b>Key</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FOOT_NOTE_ITEM__KEY = eINSTANCE.getFootNoteItem_Key();
		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FOOT_NOTE_ITEM__VALUE = eINSTANCE.getFootNoteItem_Value();
		/**
		 * The meta object literal for the '{@link it.naturtalent.office.model.address.impl.FootNoteImpl <em>Foot Note</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see it.naturtalent.office.model.address.impl.FootNoteImpl
		 * @see it.naturtalent.office.model.address.impl.AddressPackageImpl#getFootNote()
		 * @generated
		 */
		EClass FOOT_NOTE = eINSTANCE.getFootNote();
		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FOOT_NOTE__NAME = eINSTANCE.getFootNote_Name();
		/**
		 * The meta object literal for the '<em><b>Context</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FOOT_NOTE__CONTEXT = eINSTANCE.getFootNote_Context();
		/**
		 * The meta object literal for the '<em><b>Footnoteitems</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FOOT_NOTE__FOOTNOTEITEMS = eINSTANCE.getFootNote_Footnoteitems();
		/**
		 * The meta object literal for the '{@link it.naturtalent.office.model.address.impl.FootNotesImpl <em>Foot Notes</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see it.naturtalent.office.model.address.impl.FootNotesImpl
		 * @see it.naturtalent.office.model.address.impl.AddressPackageImpl#getFootNotes()
		 * @generated
		 */
		EClass FOOT_NOTES = eINSTANCE.getFootNotes();
		/**
		 * The meta object literal for the '<em><b>Foot Notes</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FOOT_NOTES__FOOT_NOTES = eINSTANCE.getFootNotes_FootNotes();
		/**
		 * The meta object literal for the '{@link it.naturtalent.office.model.address.impl.SenderImpl <em>Sender</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see it.naturtalent.office.model.address.impl.SenderImpl
		 * @see it.naturtalent.office.model.address.impl.AddressPackageImpl#getSender()
		 * @generated
		 */
		EClass SENDER = eINSTANCE.getSender();
		/**
		 * The meta object literal for the '<em><b>Senders</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SENDER__SENDERS = eINSTANCE.getSender_Senders();
		/**
		 * The meta object literal for the '{@link it.naturtalent.office.model.address.impl.SignatureImpl <em>Signature</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see it.naturtalent.office.model.address.impl.SignatureImpl
		 * @see it.naturtalent.office.model.address.impl.AddressPackageImpl#getSignature()
		 * @generated
		 */
		EClass SIGNATURE = eINSTANCE.getSignature();
		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SIGNATURE__NAME = eINSTANCE.getSignature_Name();
		/**
		 * The meta object literal for the '<em><b>Greeting</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SIGNATURE__GREETING = eINSTANCE.getSignature_Greeting();
		/**
		 * The meta object literal for the '<em><b>Signer</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SIGNATURE__SIGNER = eINSTANCE.getSignature_Signer();
		/**
		 * The meta object literal for the '<em><b>Cosigner</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SIGNATURE__COSIGNER = eINSTANCE.getSignature_Cosigner();
		/**
		 * The meta object literal for the '<em><b>Context</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SIGNATURE__CONTEXT = eINSTANCE.getSignature_Context();
		/**
		 * The meta object literal for the '<em><b>Status1</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SIGNATURE__STATUS1 = eINSTANCE.getSignature_Status1();
		/**
		 * The meta object literal for the '<em><b>Status2</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SIGNATURE__STATUS2 = eINSTANCE.getSignature_Status2();
		/**
		 * The meta object literal for the '{@link it.naturtalent.office.model.address.impl.SignatureSetImpl <em>Signature Set</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see it.naturtalent.office.model.address.impl.SignatureSetImpl
		 * @see it.naturtalent.office.model.address.impl.AddressPackageImpl#getSignatureSet()
		 * @generated
		 */
		EClass SIGNATURE_SET = eINSTANCE.getSignatureSet();
		/**
		 * The meta object literal for the '<em><b>Signatures</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SIGNATURE_SET__SIGNATURES = eINSTANCE.getSignatureSet_Signatures();
		/**
		 * The meta object literal for the '{@link it.naturtalent.office.model.address.impl.AbsenderImpl <em>Absender</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see it.naturtalent.office.model.address.impl.AbsenderImpl
		 * @see it.naturtalent.office.model.address.impl.AddressPackageImpl#getAbsender()
		 * @generated
		 */
		EClass ABSENDER = eINSTANCE.getAbsender();
		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ABSENDER__NAME = eINSTANCE.getAbsender_Name();
		/**
		 * The meta object literal for the '<em><b>Adresse</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ABSENDER__ADRESSE = eINSTANCE.getAbsender_Adresse();
		/**
		 * The meta object literal for the '<em><b>Context</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ABSENDER__CONTEXT = eINSTANCE.getAbsender_Context();
		/**
		 * The meta object literal for the '{@link it.naturtalent.office.model.address.AddressType <em>Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see it.naturtalent.office.model.address.AddressType
		 * @see it.naturtalent.office.model.address.impl.AddressPackageImpl#getAddressType()
		 * @generated
		 */
		EEnum ADDRESS_TYPE = eINSTANCE.getAddressType();

	}

} //AddressPackage
