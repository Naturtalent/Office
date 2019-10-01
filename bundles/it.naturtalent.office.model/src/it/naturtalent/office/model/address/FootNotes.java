/**
 */
package it.naturtalent.office.model.address;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Foot Notes</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link it.naturtalent.office.model.address.FootNotes#getFootNotes <em>Foot Notes</em>}</li>
 * </ul>
 *
 * @see it.naturtalent.office.model.address.AddressPackage#getFootNotes()
 * @model
 * @generated
 */
public interface FootNotes extends EObject
{
	/**
	 * Returns the value of the '<em><b>Foot Notes</b></em>' containment reference list.
	 * The list contents are of type {@link it.naturtalent.office.model.address.FootNote}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Foot Notes</em>' containment reference list.
	 * @see it.naturtalent.office.model.address.AddressPackage#getFootNotes_FootNotes()
	 * @model containment="true"
	 * @generated
	 */
	EList<FootNote> getFootNotes();

} // FootNotes
