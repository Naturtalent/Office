/**
 */
package it.naturtalent.office.model.address;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Footer Notes</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link it.naturtalent.office.model.address.FooterNotes#getName <em>Name</em>}</li>
 *   <li>{@link it.naturtalent.office.model.address.FooterNotes#getFooternotes <em>Footernotes</em>}</li>
 * </ul>
 *
 * @see it.naturtalent.office.model.address.AddressPackage#getFooterNotes()
 * @model
 * @generated
 */
public interface FooterNotes extends EObject
{
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see it.naturtalent.office.model.address.AddressPackage#getFooterNotes_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link it.naturtalent.office.model.address.FooterNotes#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Footernotes</b></em>' containment reference list.
	 * The list contents are of type {@link it.naturtalent.office.model.address.FooterNote}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Footernotes</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Footernotes</em>' containment reference list.
	 * @see it.naturtalent.office.model.address.AddressPackage#getFooterNotes_Footernotes()
	 * @model containment="true"
	 * @generated
	 */
	EList<FooterNote> getFooternotes();

} // FooterNotes
