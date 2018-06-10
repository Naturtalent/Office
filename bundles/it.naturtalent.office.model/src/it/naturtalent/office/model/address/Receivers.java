/**
 */
package it.naturtalent.office.model.address;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Receivers</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link it.naturtalent.office.model.address.Receivers#getReceivers <em>Receivers</em>}</li>
 * </ul>
 *
 * @see it.naturtalent.office.model.address.AddressPackage#getReceivers()
 * @model
 * @generated
 */
public interface Receivers extends EObject
{
	/**
	 * Returns the value of the '<em><b>Receivers</b></em>' containment reference list.
	 * The list contents are of type {@link it.naturtalent.office.model.address.Empfaenger}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Receivers</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Receivers</em>' containment reference list.
	 * @see it.naturtalent.office.model.address.AddressPackage#getReceivers_Receivers()
	 * @model containment="true"
	 * @generated
	 */
	EList<Empfaenger> getReceivers();

} // Receivers
