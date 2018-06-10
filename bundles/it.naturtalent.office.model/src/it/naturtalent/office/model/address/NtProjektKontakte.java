/**
 */
package it.naturtalent.office.model.address;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Nt Projekt Kontakte</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link it.naturtalent.office.model.address.NtProjektKontakte#getNtProjektID <em>Nt Projekt ID</em>}</li>
 *   <li>{@link it.naturtalent.office.model.address.NtProjektKontakte#getKontakte <em>Kontakte</em>}</li>
 * </ul>
 *
 * @see it.naturtalent.office.model.address.AddressPackage#getNtProjektKontakte()
 * @model
 * @generated
 */
public interface NtProjektKontakte extends EObject
{
	/**
	 * Returns the value of the '<em><b>Nt Projekt ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Nt Projekt ID</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Nt Projekt ID</em>' attribute.
	 * @see #setNtProjektID(String)
	 * @see it.naturtalent.office.model.address.AddressPackage#getNtProjektKontakte_NtProjektID()
	 * @model
	 * @generated
	 */
	String getNtProjektID();

	/**
	 * Sets the value of the '{@link it.naturtalent.office.model.address.NtProjektKontakte#getNtProjektID <em>Nt Projekt ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Nt Projekt ID</em>' attribute.
	 * @see #getNtProjektID()
	 * @generated
	 */
	void setNtProjektID(String value);

	/**
	 * Returns the value of the '<em><b>Kontakte</b></em>' containment reference list.
	 * The list contents are of type {@link it.naturtalent.office.model.address.Kontakt}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Kontakte</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Kontakte</em>' containment reference list.
	 * @see it.naturtalent.office.model.address.AddressPackage#getNtProjektKontakte_Kontakte()
	 * @model containment="true"
	 * @generated
	 */
	EList<Kontakt> getKontakte();

} // NtProjektKontakte
