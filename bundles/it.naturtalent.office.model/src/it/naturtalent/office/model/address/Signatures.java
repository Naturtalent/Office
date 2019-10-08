/**
 */
package it.naturtalent.office.model.address;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Signatures</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link it.naturtalent.office.model.address.Signatures#getSignatures <em>Signatures</em>}</li>
 * </ul>
 *
 * @see it.naturtalent.office.model.address.AddressPackage#getSignatures()
 * @model
 * @generated
 */
public interface Signatures extends EObject
{
	/**
	 * Returns the value of the '<em><b>Signatures</b></em>' containment reference list.
	 * The list contents are of type {@link it.naturtalent.office.model.address.Signature}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Signatures</em>' containment reference list.
	 * @see it.naturtalent.office.model.address.AddressPackage#getSignatures_Signatures()
	 * @model containment="true"
	 * @generated
	 */
	EList<Signature> getSignatures();

} // Signatures
