/**
 */
package it.naturtalent.office.model.address.impl;

import it.naturtalent.office.model.address.Absender;
import it.naturtalent.office.model.address.AddressFactory;
import it.naturtalent.office.model.address.AddressPackage;
import it.naturtalent.office.model.address.AddressType;

import it.naturtalent.office.model.address.Adresse;
import it.naturtalent.office.model.address.Empfaenger;
import it.naturtalent.office.model.address.FootNote;
import it.naturtalent.office.model.address.FootNoteItem;
import it.naturtalent.office.model.address.FootNotes;
import it.naturtalent.office.model.address.Kontakt;
import it.naturtalent.office.model.address.Kontakte;
import it.naturtalent.office.model.address.NtProjektKontakte;
import it.naturtalent.office.model.address.Receivers;
import it.naturtalent.office.model.address.Referenz;
import it.naturtalent.office.model.address.Referenzen;
import it.naturtalent.office.model.address.Sender;
import it.naturtalent.office.model.address.Signature;
import it.naturtalent.office.model.address.SignatureSet;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class AddressPackageImpl extends EPackageImpl implements AddressPackage
{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass adresseEClass = null;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass referenzEClass = null;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass referenzenEClass = null;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass empfaengerEClass = null;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass receiversEClass = null;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass kontaktEClass = null;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass kontakteEClass = null;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass ntProjektKontakteEClass = null;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass footNoteItemEClass = null;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass footNoteEClass = null;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass footNotesEClass = null;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass senderEClass = null;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass signatureEClass = null;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass signatureSetEClass = null;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass absenderEClass = null;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum addressTypeEEnum = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see it.naturtalent.office.model.address.AddressPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private AddressPackageImpl()
	{
		super(eNS_URI, AddressFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 *
	 * <p>This method is used to initialize {@link AddressPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static AddressPackage init()
	{
		if (isInited) return (AddressPackage)EPackage.Registry.INSTANCE.getEPackage(AddressPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredAddressPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		AddressPackageImpl theAddressPackage = registeredAddressPackage instanceof AddressPackageImpl ? (AddressPackageImpl)registeredAddressPackage : new AddressPackageImpl();

		isInited = true;

		// Create package meta-data objects
		theAddressPackage.createPackageContents();

		// Initialize created meta-data
		theAddressPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theAddressPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(AddressPackage.eNS_URI, theAddressPackage);
		return theAddressPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getAdresse()
	{
		return adresseEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getAdresse_Name()
	{
		return (EAttribute)adresseEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getAdresse_Name2()
	{
		return (EAttribute)adresseEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getAdresse_Name3()
	{
		return (EAttribute)adresseEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getAdresse_Strasse()
	{
		return (EAttribute)adresseEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getAdresse_Plz()
	{
		return (EAttribute)adresseEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getAdresse_Ort()
	{
		return (EAttribute)adresseEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getAdresse_Type()
	{
		return (EAttribute)adresseEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getReferenz()
	{
		return referenzEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getReferenz_Name()
	{
		return (EAttribute)referenzEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getReferenz_Context()
	{
		return (EAttribute)referenzEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getReferenz_Referenz()
	{
		return (EAttribute)referenzEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getReferenz_Referenz2()
	{
		return (EAttribute)referenzEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getReferenz_Referenz3()
	{
		return (EAttribute)referenzEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getReferenzen()
	{
		return referenzenEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getReferenzen_Referenzen()
	{
		return (EReference)referenzenEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getEmpfaenger()
	{
		return empfaengerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getEmpfaenger_Name()
	{
		return (EAttribute)empfaengerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getEmpfaenger_Adresse()
	{
		return (EReference)empfaengerEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getEmpfaenger_Context()
	{
		return (EAttribute)empfaengerEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getReceivers()
	{
		return receiversEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getReceivers_Receivers()
	{
		return (EReference)receiversEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getKontakt()
	{
		return kontaktEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getKontakt_Name()
	{
		return (EAttribute)kontaktEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getKontakt_Adresse()
	{
		return (EReference)kontaktEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getKontakt_Kommunikation()
	{
		return (EAttribute)kontaktEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getKontakte()
	{
		return kontakteEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getKontakte_Kontakte()
	{
		return (EReference)kontakteEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getNtProjektKontakte()
	{
		return ntProjektKontakteEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getNtProjektKontakte_NtProjektID()
	{
		return (EAttribute)ntProjektKontakteEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getNtProjektKontakte_Kontakte()
	{
		return (EReference)ntProjektKontakteEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getFootNoteItem()
	{
		return footNoteItemEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getFootNoteItem_Key()
	{
		return (EAttribute)footNoteItemEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getFootNoteItem_Value()
	{
		return (EAttribute)footNoteItemEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getFootNote()
	{
		return footNoteEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getFootNote_Name()
	{
		return (EAttribute)footNoteEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getFootNote_Context()
	{
		return (EAttribute)footNoteEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getFootNote_Footnoteitems()
	{
		return (EReference)footNoteEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getFootNotes()
	{
		return footNotesEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getFootNotes_FootNotes()
	{
		return (EReference)footNotesEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSender()
	{
		return senderEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSender_Senders()
	{
		return (EReference)senderEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSignature()
	{
		return signatureEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getSignature_Name()
	{
		return (EAttribute)signatureEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getSignature_Greeting()
	{
		return (EAttribute)signatureEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getSignature_Signer()
	{
		return (EAttribute)signatureEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getSignature_Cosigner()
	{
		return (EAttribute)signatureEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getSignature_Context()
	{
		return (EAttribute)signatureEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getSignature_Status1()
	{
		return (EAttribute)signatureEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getSignature_Status2()
	{
		return (EAttribute)signatureEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSignatureSet()
	{
		return signatureSetEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSignatureSet_Signatures()
	{
		return (EReference)signatureSetEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getAbsender()
	{
		return absenderEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getAbsender_Name()
	{
		return (EAttribute)absenderEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getAbsender_Adresse()
	{
		return (EReference)absenderEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getAbsender_Context()
	{
		return (EAttribute)absenderEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getAddressType()
	{
		return addressTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public AddressFactory getAddressFactory()
	{
		return (AddressFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents()
	{
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		absenderEClass = createEClass(ABSENDER);
		createEAttribute(absenderEClass, ABSENDER__NAME);
		createEReference(absenderEClass, ABSENDER__ADRESSE);
		createEAttribute(absenderEClass, ABSENDER__CONTEXT);

		adresseEClass = createEClass(ADRESSE);
		createEAttribute(adresseEClass, ADRESSE__NAME);
		createEAttribute(adresseEClass, ADRESSE__NAME2);
		createEAttribute(adresseEClass, ADRESSE__NAME3);
		createEAttribute(adresseEClass, ADRESSE__STRASSE);
		createEAttribute(adresseEClass, ADRESSE__PLZ);
		createEAttribute(adresseEClass, ADRESSE__ORT);
		createEAttribute(adresseEClass, ADRESSE__TYPE);

		empfaengerEClass = createEClass(EMPFAENGER);
		createEAttribute(empfaengerEClass, EMPFAENGER__NAME);
		createEReference(empfaengerEClass, EMPFAENGER__ADRESSE);
		createEAttribute(empfaengerEClass, EMPFAENGER__CONTEXT);

		receiversEClass = createEClass(RECEIVERS);
		createEReference(receiversEClass, RECEIVERS__RECEIVERS);

		kontaktEClass = createEClass(KONTAKT);
		createEAttribute(kontaktEClass, KONTAKT__NAME);
		createEReference(kontaktEClass, KONTAKT__ADRESSE);
		createEAttribute(kontaktEClass, KONTAKT__KOMMUNIKATION);

		kontakteEClass = createEClass(KONTAKTE);
		createEReference(kontakteEClass, KONTAKTE__KONTAKTE);

		ntProjektKontakteEClass = createEClass(NT_PROJEKT_KONTAKTE);
		createEAttribute(ntProjektKontakteEClass, NT_PROJEKT_KONTAKTE__NT_PROJEKT_ID);
		createEReference(ntProjektKontakteEClass, NT_PROJEKT_KONTAKTE__KONTAKTE);

		footNoteItemEClass = createEClass(FOOT_NOTE_ITEM);
		createEAttribute(footNoteItemEClass, FOOT_NOTE_ITEM__KEY);
		createEAttribute(footNoteItemEClass, FOOT_NOTE_ITEM__VALUE);

		footNoteEClass = createEClass(FOOT_NOTE);
		createEAttribute(footNoteEClass, FOOT_NOTE__NAME);
		createEAttribute(footNoteEClass, FOOT_NOTE__CONTEXT);
		createEReference(footNoteEClass, FOOT_NOTE__FOOTNOTEITEMS);

		footNotesEClass = createEClass(FOOT_NOTES);
		createEReference(footNotesEClass, FOOT_NOTES__FOOT_NOTES);

		referenzEClass = createEClass(REFERENZ);
		createEAttribute(referenzEClass, REFERENZ__NAME);
		createEAttribute(referenzEClass, REFERENZ__CONTEXT);
		createEAttribute(referenzEClass, REFERENZ__REFERENZ);
		createEAttribute(referenzEClass, REFERENZ__REFERENZ2);
		createEAttribute(referenzEClass, REFERENZ__REFERENZ3);

		referenzenEClass = createEClass(REFERENZEN);
		createEReference(referenzenEClass, REFERENZEN__REFERENZEN);

		senderEClass = createEClass(SENDER);
		createEReference(senderEClass, SENDER__SENDERS);

		signatureEClass = createEClass(SIGNATURE);
		createEAttribute(signatureEClass, SIGNATURE__NAME);
		createEAttribute(signatureEClass, SIGNATURE__GREETING);
		createEAttribute(signatureEClass, SIGNATURE__SIGNER);
		createEAttribute(signatureEClass, SIGNATURE__COSIGNER);
		createEAttribute(signatureEClass, SIGNATURE__CONTEXT);
		createEAttribute(signatureEClass, SIGNATURE__STATUS1);
		createEAttribute(signatureEClass, SIGNATURE__STATUS2);

		signatureSetEClass = createEClass(SIGNATURE_SET);
		createEReference(signatureSetEClass, SIGNATURE_SET__SIGNATURES);

		// Create enums
		addressTypeEEnum = createEEnum(ADDRESS_TYPE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents()
	{
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes

		// Initialize classes, features, and operations; add parameters
		initEClass(absenderEClass, Absender.class, "Absender", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAbsender_Name(), ecorePackage.getEString(), "name", null, 0, 1, Absender.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAbsender_Adresse(), this.getAdresse(), null, "adresse", null, 0, 1, Absender.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAbsender_Context(), ecorePackage.getEString(), "context", null, 0, 1, Absender.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(adresseEClass, Adresse.class, "Adresse", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAdresse_Name(), ecorePackage.getEString(), "name", null, 0, 1, Adresse.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAdresse_Name2(), ecorePackage.getEString(), "name2", null, 0, 1, Adresse.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAdresse_Name3(), ecorePackage.getEString(), "name3", null, 0, 1, Adresse.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAdresse_Strasse(), ecorePackage.getEString(), "strasse", null, 0, 1, Adresse.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAdresse_Plz(), ecorePackage.getEString(), "plz", null, 0, 1, Adresse.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAdresse_Ort(), ecorePackage.getEString(), "ort", null, 0, 1, Adresse.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAdresse_Type(), this.getAddressType(), "type", null, 0, 1, Adresse.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(empfaengerEClass, Empfaenger.class, "Empfaenger", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEmpfaenger_Name(), ecorePackage.getEString(), "name", null, 0, 1, Empfaenger.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEmpfaenger_Adresse(), this.getAdresse(), null, "adresse", null, 0, 1, Empfaenger.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEmpfaenger_Context(), ecorePackage.getEString(), "context", null, 0, 1, Empfaenger.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(receiversEClass, Receivers.class, "Receivers", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getReceivers_Receivers(), this.getEmpfaenger(), null, "Receivers", null, 0, -1, Receivers.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(kontaktEClass, Kontakt.class, "Kontakt", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getKontakt_Name(), ecorePackage.getEString(), "name", null, 0, 1, Kontakt.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getKontakt_Adresse(), this.getAdresse(), null, "adresse", null, 0, 1, Kontakt.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getKontakt_Kommunikation(), ecorePackage.getEString(), "kommunikation", null, 0, 1, Kontakt.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(kontakteEClass, Kontakte.class, "Kontakte", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getKontakte_Kontakte(), this.getKontakt(), null, "kontakte", null, 0, -1, Kontakte.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(ntProjektKontakteEClass, NtProjektKontakte.class, "NtProjektKontakte", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getNtProjektKontakte_NtProjektID(), ecorePackage.getEString(), "ntProjektID", null, 0, 1, NtProjektKontakte.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getNtProjektKontakte_Kontakte(), this.getKontakt(), null, "kontakte", null, 0, -1, NtProjektKontakte.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(footNoteItemEClass, FootNoteItem.class, "FootNoteItem", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getFootNoteItem_Key(), ecorePackage.getEString(), "key", null, 0, 1, FootNoteItem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFootNoteItem_Value(), ecorePackage.getEString(), "value", null, 0, 1, FootNoteItem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(footNoteEClass, FootNote.class, "FootNote", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getFootNote_Name(), ecorePackage.getEString(), "name", null, 0, 1, FootNote.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFootNote_Context(), ecorePackage.getEString(), "context", null, 0, 1, FootNote.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getFootNote_Footnoteitems(), this.getFootNoteItem(), null, "footnoteitems", null, 0, -1, FootNote.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(footNotesEClass, FootNotes.class, "FootNotes", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getFootNotes_FootNotes(), this.getFootNote(), null, "footNotes", null, 0, -1, FootNotes.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(referenzEClass, Referenz.class, "Referenz", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getReferenz_Name(), ecorePackage.getEString(), "name", null, 0, 1, Referenz.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getReferenz_Context(), ecorePackage.getEString(), "context", null, 0, 1, Referenz.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getReferenz_Referenz(), ecorePackage.getEString(), "referenz", null, 0, 1, Referenz.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getReferenz_Referenz2(), ecorePackage.getEString(), "referenz2", null, 0, 1, Referenz.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getReferenz_Referenz3(), ecorePackage.getEString(), "referenz3", null, 0, 1, Referenz.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(referenzenEClass, Referenzen.class, "Referenzen", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getReferenzen_Referenzen(), this.getReferenz(), null, "referenzen", null, 0, -1, Referenzen.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(senderEClass, Sender.class, "Sender", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getSender_Senders(), this.getAbsender(), null, "senders", null, 0, -1, Sender.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(signatureEClass, Signature.class, "Signature", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getSignature_Name(), ecorePackage.getEString(), "name", null, 0, 1, Signature.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSignature_Greeting(), ecorePackage.getEString(), "greeting", null, 0, 1, Signature.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSignature_Signer(), ecorePackage.getEString(), "signer", null, 0, 1, Signature.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSignature_Cosigner(), ecorePackage.getEString(), "cosigner", null, 0, 1, Signature.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSignature_Context(), ecorePackage.getEString(), "context", null, 0, 1, Signature.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSignature_Status1(), ecorePackage.getEString(), "status1", null, 0, 1, Signature.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSignature_Status2(), ecorePackage.getEString(), "status2", null, 0, 1, Signature.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(signatureSetEClass, SignatureSet.class, "SignatureSet", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getSignatureSet_Signatures(), this.getSignature(), null, "signatures", null, 0, -1, SignatureSet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(addressTypeEEnum, AddressType.class, "AddressType");
		addEEnumLiteral(addressTypeEEnum, AddressType.PRIVATE_ADDRESS);
		addEEnumLiteral(addressTypeEEnum, AddressType.PUBLIC_ADDRESS);

		// Create resource
		createResource(eNS_URI);
	}

} //AddressPackageImpl
