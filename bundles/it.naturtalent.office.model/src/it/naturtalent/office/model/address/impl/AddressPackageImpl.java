/**
 */
package it.naturtalent.office.model.address.impl;

import it.naturtalent.office.model.address.Absender;
import it.naturtalent.office.model.address.AddressFactory;
import it.naturtalent.office.model.address.AddressPackage;
import it.naturtalent.office.model.address.AddressType;

import it.naturtalent.office.model.address.Adresse;
import it.naturtalent.office.model.address.Referenz;
import it.naturtalent.office.model.address.Senders;
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
	private EClass absenderEClass = null;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass sendersEClass = null;
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
		AddressPackageImpl theAddressPackage = (AddressPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof AddressPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new AddressPackageImpl());

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
	public EClass getAdresse()
	{
		return adresseEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAdresse_Name()
	{
		return (EAttribute)adresseEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAdresse_Name2()
	{
		return (EAttribute)adresseEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAdresse_Name3()
	{
		return (EAttribute)adresseEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAdresse_Strasse()
	{
		return (EAttribute)adresseEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAdresse_Hsnr()
	{
		return (EAttribute)adresseEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAdresse_Plz()
	{
		return (EAttribute)adresseEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAdresse_Ort()
	{
		return (EAttribute)adresseEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAdresse_Type()
	{
		return (EAttribute)adresseEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getReferenz()
	{
		return referenzEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getReferenz_Referenz()
	{
		return (EAttribute)referenzEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getReferenz_Referenz2()
	{
		return (EAttribute)referenzEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getReferenz_Referenz3()
	{
		return (EAttribute)referenzEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAbsender()
	{
		return absenderEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAbsender_Name()
	{
		return (EAttribute)absenderEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAbsender_Adresse()
	{
		return (EReference)absenderEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAbsender_Referenz()
	{
		return (EReference)absenderEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSenders()
	{
		return sendersEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSenders_Senders()
	{
		return (EReference)sendersEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getAddressType()
	{
		return addressTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
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
		sendersEClass = createEClass(SENDERS);
		createEReference(sendersEClass, SENDERS__SENDERS);

		absenderEClass = createEClass(ABSENDER);
		createEAttribute(absenderEClass, ABSENDER__NAME);
		createEReference(absenderEClass, ABSENDER__ADRESSE);
		createEReference(absenderEClass, ABSENDER__REFERENZ);

		adresseEClass = createEClass(ADRESSE);
		createEAttribute(adresseEClass, ADRESSE__NAME);
		createEAttribute(adresseEClass, ADRESSE__NAME2);
		createEAttribute(adresseEClass, ADRESSE__NAME3);
		createEAttribute(adresseEClass, ADRESSE__STRASSE);
		createEAttribute(adresseEClass, ADRESSE__HSNR);
		createEAttribute(adresseEClass, ADRESSE__PLZ);
		createEAttribute(adresseEClass, ADRESSE__ORT);
		createEAttribute(adresseEClass, ADRESSE__TYPE);

		referenzEClass = createEClass(REFERENZ);
		createEAttribute(referenzEClass, REFERENZ__REFERENZ);
		createEAttribute(referenzEClass, REFERENZ__REFERENZ2);
		createEAttribute(referenzEClass, REFERENZ__REFERENZ3);

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
		initEClass(sendersEClass, Senders.class, "Senders", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getSenders_Senders(), this.getAbsender(), null, "Senders", null, 0, -1, Senders.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(absenderEClass, Absender.class, "Absender", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAbsender_Name(), ecorePackage.getEString(), "name", null, 0, 1, Absender.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAbsender_Adresse(), this.getAdresse(), null, "adresse", null, 0, 1, Absender.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAbsender_Referenz(), this.getReferenz(), null, "referenz", null, 0, 1, Absender.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(adresseEClass, Adresse.class, "Adresse", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAdresse_Name(), ecorePackage.getEString(), "name", null, 0, 1, Adresse.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAdresse_Name2(), ecorePackage.getEString(), "name2", null, 0, 1, Adresse.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAdresse_Name3(), ecorePackage.getEString(), "name3", null, 0, 1, Adresse.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAdresse_Strasse(), ecorePackage.getEString(), "strasse", null, 0, 1, Adresse.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAdresse_Hsnr(), ecorePackage.getEString(), "hsnr", null, 0, 1, Adresse.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAdresse_Plz(), ecorePackage.getEString(), "plz", null, 0, 1, Adresse.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAdresse_Ort(), ecorePackage.getEString(), "ort", null, 0, 1, Adresse.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAdresse_Type(), this.getAddressType(), "type", null, 0, 1, Adresse.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(referenzEClass, Referenz.class, "Referenz", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getReferenz_Referenz(), ecorePackage.getEString(), "referenz", null, 0, 1, Referenz.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getReferenz_Referenz2(), ecorePackage.getEString(), "referenz2", null, 0, 1, Referenz.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getReferenz_Referenz3(), ecorePackage.getEString(), "referenz3", null, 0, 1, Referenz.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(addressTypeEEnum, AddressType.class, "AddressType");
		addEEnumLiteral(addressTypeEEnum, AddressType.PRIVATE_ADDRESS);
		addEEnumLiteral(addressTypeEEnum, AddressType.PUBLIC_ADDRESS);

		// Create resource
		createResource(eNS_URI);
	}

} //AddressPackageImpl
