/**
 */
package it.naturtalent.office.model.address;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Sender</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link it.naturtalent.office.model.address.Sender#getSenders <em>Senders</em>}</li>
 * </ul>
 *
 * @see it.naturtalent.office.model.address.AddressPackage#getSender()
 * @model
 * @generated
 */
public interface Sender extends EObject
{
	/**
	 * Returns the value of the '<em><b>Senders</b></em>' containment reference list.
	 * The list contents are of type {@link it.naturtalent.office.model.address.Absender}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Senders</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Senders</em>' containment reference list.
	 * @see it.naturtalent.office.model.address.AddressPackage#getSender_Senders()
	 * @model containment="true"
	 * @generated
	 */
	EList<Absender> getSenders();

} // Sender
