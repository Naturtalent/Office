/**
 */
package it.naturtalent.office.model.address;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Signature Set</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link it.naturtalent.office.model.address.SignatureSet#getSignatures <em>Signatures</em>}</li>
 * </ul>
 *
 * @see it.naturtalent.office.model.address.AddressPackage#getSignatureSet()
 * @model
 * @generated
 */
public interface SignatureSet extends EObject
{
	/**
	 * Returns the value of the '<em><b>Signatures</b></em>' containment reference list.
	 * The list contents are of type {@link it.naturtalent.office.model.address.Signature}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Signatures</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Signatures</em>' containment reference list.
	 * @see it.naturtalent.office.model.address.AddressPackage#getSignatureSet_Signatures()
	 * @model containment="true"
	 * @generated
	 */
	EList<Signature> getSignatures();

} // SignatureSet
