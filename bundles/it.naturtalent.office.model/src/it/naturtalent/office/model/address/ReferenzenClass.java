/**
 */
package it.naturtalent.office.model.address;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Referenzen Class</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link it.naturtalent.office.model.address.ReferenzenClass#getReferenzenClassName <em>Referenzen Class Name</em>}</li>
 *   <li>{@link it.naturtalent.office.model.address.ReferenzenClass#getReferenzClassReferenzen <em>Referenz Class Referenzen</em>}</li>
 * </ul>
 *
 * @see it.naturtalent.office.model.address.AddressPackage#getReferenzenClass()
 * @model
 * @generated
 */
public interface ReferenzenClass extends EObject
{
	/**
	 * Returns the value of the '<em><b>Referenzen Class Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Referenzen Class Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Referenzen Class Name</em>' attribute.
	 * @see #setReferenzenClassName(String)
	 * @see it.naturtalent.office.model.address.AddressPackage#getReferenzenClass_ReferenzenClassName()
	 * @model
	 * @generated
	 */
	String getReferenzenClassName();

	/**
	 * Sets the value of the '{@link it.naturtalent.office.model.address.ReferenzenClass#getReferenzenClassName <em>Referenzen Class Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Referenzen Class Name</em>' attribute.
	 * @see #getReferenzenClassName()
	 * @generated
	 */
	void setReferenzenClassName(String value);

	/**
	 * Returns the value of the '<em><b>Referenz Class Referenzen</b></em>' containment reference list.
	 * The list contents are of type {@link it.naturtalent.office.model.address.ReferenzGruppe}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Referenz Class Referenzen</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Referenz Class Referenzen</em>' containment reference list.
	 * @see it.naturtalent.office.model.address.AddressPackage#getReferenzenClass_ReferenzClassReferenzen()
	 * @model containment="true"
	 * @generated
	 */
	EList<ReferenzGruppe> getReferenzClassReferenzen();

} // ReferenzenClass
