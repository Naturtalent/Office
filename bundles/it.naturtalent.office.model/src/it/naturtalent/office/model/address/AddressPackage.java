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
	int ADRESSE = 2;

	/**
	 * The meta object id for the '{@link it.naturtalent.office.model.address.impl.ReferenzImpl <em>Referenz</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see it.naturtalent.office.model.address.impl.ReferenzImpl
	 * @see it.naturtalent.office.model.address.impl.AddressPackageImpl#getReferenz()
	 * @generated
	 */
	int REFERENZ = 3;

	/**
	 * The meta object id for the '{@link it.naturtalent.office.model.address.impl.AbsenderImpl <em>Absender</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see it.naturtalent.office.model.address.impl.AbsenderImpl
	 * @see it.naturtalent.office.model.address.impl.AddressPackageImpl#getAbsender()
	 * @generated
	 */
	int ABSENDER = 1;

	/**
	 * The meta object id for the '{@link it.naturtalent.office.model.address.impl.SendersImpl <em>Senders</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see it.naturtalent.office.model.address.impl.SendersImpl
	 * @see it.naturtalent.office.model.address.impl.AddressPackageImpl#getSenders()
	 * @generated
	 */
	int SENDERS = 0;

	/**
	 * The feature id for the '<em><b>Senders</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SENDERS__SENDERS = 0;

	/**
	 * The number of structural features of the '<em>Senders</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SENDERS_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Senders</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SENDERS_OPERATION_COUNT = 0;

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
	 * The feature id for the '<em><b>Referenz</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSENDER__REFERENZ = 2;

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
	 * The feature id for the '<em><b>Hsnr</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADRESSE__HSNR = 4;

	/**
	 * The feature id for the '<em><b>Plz</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADRESSE__PLZ = 5;

	/**
	 * The feature id for the '<em><b>Ort</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADRESSE__ORT = 6;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADRESSE__TYPE = 7;

	/**
	 * The number of structural features of the '<em>Adresse</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADRESSE_FEATURE_COUNT = 8;

	/**
	 * The number of operations of the '<em>Adresse</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADRESSE_OPERATION_COUNT = 0;

	/**
	 * The feature id for the '<em><b>Referenz</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFERENZ__REFERENZ = 0;

	/**
	 * The feature id for the '<em><b>Referenz2</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFERENZ__REFERENZ2 = 1;

	/**
	 * The feature id for the '<em><b>Referenz3</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFERENZ__REFERENZ3 = 2;

	/**
	 * The number of structural features of the '<em>Referenz</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFERENZ_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Referenz</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFERENZ_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link it.naturtalent.office.model.address.AddressType <em>Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see it.naturtalent.office.model.address.AddressType
	 * @see it.naturtalent.office.model.address.impl.AddressPackageImpl#getAddressType()
	 * @generated
	 */
	int ADDRESS_TYPE = 4;


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
	 * Returns the meta object for the attribute '{@link it.naturtalent.office.model.address.Adresse#getHsnr <em>Hsnr</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Hsnr</em>'.
	 * @see it.naturtalent.office.model.address.Adresse#getHsnr()
	 * @see #getAdresse()
	 * @generated
	 */
	EAttribute getAdresse_Hsnr();

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
	 * Returns the meta object for the containment reference '{@link it.naturtalent.office.model.address.Absender#getReferenz <em>Referenz</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Referenz</em>'.
	 * @see it.naturtalent.office.model.address.Absender#getReferenz()
	 * @see #getAbsender()
	 * @generated
	 */
	EReference getAbsender_Referenz();

	/**
	 * Returns the meta object for class '{@link it.naturtalent.office.model.address.Senders <em>Senders</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Senders</em>'.
	 * @see it.naturtalent.office.model.address.Senders
	 * @generated
	 */
	EClass getSenders();

	/**
	 * Returns the meta object for the containment reference list '{@link it.naturtalent.office.model.address.Senders#getSenders <em>Senders</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Senders</em>'.
	 * @see it.naturtalent.office.model.address.Senders#getSenders()
	 * @see #getSenders()
	 * @generated
	 */
	EReference getSenders_Senders();

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
		 * The meta object literal for the '<em><b>Hsnr</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ADRESSE__HSNR = eINSTANCE.getAdresse_Hsnr();
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
		 * The meta object literal for the '<em><b>Referenz</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ABSENDER__REFERENZ = eINSTANCE.getAbsender_Referenz();
		/**
		 * The meta object literal for the '{@link it.naturtalent.office.model.address.impl.SendersImpl <em>Senders</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see it.naturtalent.office.model.address.impl.SendersImpl
		 * @see it.naturtalent.office.model.address.impl.AddressPackageImpl#getSenders()
		 * @generated
		 */
		EClass SENDERS = eINSTANCE.getSenders();
		/**
		 * The meta object literal for the '<em><b>Senders</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SENDERS__SENDERS = eINSTANCE.getSenders_Senders();
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
