/**
 */
package it.naturtalent.office.model.address;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Referenz Set</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link it.naturtalent.office.model.address.ReferenzSet#getName <em>Name</em>}</li>
 *   <li>{@link it.naturtalent.office.model.address.ReferenzSet#getReferenzen <em>Referenzen</em>}</li>
 * </ul>
 *
 * @see it.naturtalent.office.model.address.AddressPackage#getReferenzSet()
 * @model
 * @generated
 */
public interface ReferenzSet extends EObject
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
	 * @see it.naturtalent.office.model.address.AddressPackage#getReferenzSet_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link it.naturtalent.office.model.address.ReferenzSet#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Referenzen</b></em>' containment reference list.
	 * The list contents are of type {@link it.naturtalent.office.model.address.Referenz}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Referenzen</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Referenzen</em>' containment reference list.
	 * @see it.naturtalent.office.model.address.AddressPackage#getReferenzSet_Referenzen()
	 * @model containment="true"
	 * @generated
	 */
	EList<Referenz> getReferenzen();

} // ReferenzSet
