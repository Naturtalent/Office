/**
 */
package it.naturtalent.office.model.address;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Referenz Gruppe</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link it.naturtalent.office.model.address.ReferenzGruppe#getGroupname <em>Groupname</em>}</li>
 *   <li>{@link it.naturtalent.office.model.address.ReferenzGruppe#getReferenz <em>Referenz</em>}</li>
 * </ul>
 *
 * @see it.naturtalent.office.model.address.AddressPackage#getReferenzGruppe()
 * @model
 * @generated
 */
public interface ReferenzGruppe extends EObject
{
	/**
	 * Returns the value of the '<em><b>Groupname</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Groupname</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Groupname</em>' attribute.
	 * @see #setGroupname(String)
	 * @see it.naturtalent.office.model.address.AddressPackage#getReferenzGruppe_Groupname()
	 * @model
	 * @generated
	 */
	String getGroupname();

	/**
	 * Sets the value of the '{@link it.naturtalent.office.model.address.ReferenzGruppe#getGroupname <em>Groupname</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Groupname</em>' attribute.
	 * @see #getGroupname()
	 * @generated
	 */
	void setGroupname(String value);

	/**
	 * Returns the value of the '<em><b>Referenz</b></em>' containment reference list.
	 * The list contents are of type {@link it.naturtalent.office.model.address.Referenz}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Referenz</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Referenz</em>' containment reference list.
	 * @see it.naturtalent.office.model.address.AddressPackage#getReferenzGruppe_Referenz()
	 * @model containment="true"
	 * @generated
	 */
	EList<Referenz> getReferenz();

} // ReferenzGruppe
