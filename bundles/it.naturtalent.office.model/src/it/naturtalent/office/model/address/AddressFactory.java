/**
 */
package it.naturtalent.office.model.address;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see it.naturtalent.office.model.address.AddressPackage
 * @generated
 */
public interface AddressFactory extends EFactory
{
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	AddressFactory eINSTANCE = it.naturtalent.office.model.address.impl.AddressFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Adresse</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Adresse</em>'.
	 * @generated
	 */
	Adresse createAdresse();

	/**
	 * Returns a new object of class '<em>Referenz</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Referenz</em>'.
	 * @generated
	 */
	Referenz createReferenz();

	/**
	 * Returns a new object of class '<em>Empfaenger</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Empfaenger</em>'.
	 * @generated
	 */
	Empfaenger createEmpfaenger();

	/**
	 * Returns a new object of class '<em>Receivers</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Receivers</em>'.
	 * @generated
	 */
	Receivers createReceivers();

	/**
	 * Returns a new object of class '<em>Kontakt</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Kontakt</em>'.
	 * @generated
	 */
	Kontakt createKontakt();

	/**
	 * Returns a new object of class '<em>Kontakte</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Kontakte</em>'.
	 * @generated
	 */
	Kontakte createKontakte();

	/**
	 * Returns a new object of class '<em>Nt Projekt Kontakte</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Nt Projekt Kontakte</em>'.
	 * @generated
	 */
	NtProjektKontakte createNtProjektKontakte();

	/**
	 * Returns a new object of class '<em>Foot Note</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Foot Note</em>'.
	 * @generated
	 */
	FootNote createFootNote();

	/**
	 * Returns a new object of class '<em>Foot Notes</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Foot Notes</em>'.
	 * @generated
	 */
	FootNotes createFootNotes();

	/**
	 * Returns a new object of class '<em>Footer Class</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Footer Class</em>'.
	 * @generated
	 */
	FooterClass createFooterClass();

	/**
	 * Returns a new object of class '<em>Referenz Set</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Referenz Set</em>'.
	 * @generated
	 */
	ReferenzSet createReferenzSet();

	/**
	 * Returns a new object of class '<em>Referenzen Class</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Referenzen Class</em>'.
	 * @generated
	 */
	ReferenzenClass createReferenzenClass();

	/**
	 * Returns a new object of class '<em>Referenz Gruppe</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Referenz Gruppe</em>'.
	 * @generated
	 */
	ReferenzGruppe createReferenzGruppe();

	/**
	 * Returns a new object of class '<em>Absender</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Absender</em>'.
	 * @generated
	 */
	Absender createAbsender();

	/**
	 * Returns a new object of class '<em>Senders</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Senders</em>'.
	 * @generated
	 */
	Senders createSenders();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	AddressPackage getAddressPackage();

} //AddressFactory