/**
 */
package it.naturtalent.office.model.address;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Footer Class</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link it.naturtalent.office.model.address.FooterClass#getFooterClassName <em>Footer Class Name</em>}</li>
 *   <li>{@link it.naturtalent.office.model.address.FooterClass#getFooterClassFootNotes <em>Footer Class Foot Notes</em>}</li>
 * </ul>
 *
 * @see it.naturtalent.office.model.address.AddressPackage#getFooterClass()
 * @model
 * @generated
 */
public interface FooterClass extends EObject
{
	/**
	 * Returns the value of the '<em><b>Footer Class Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Footer Class Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Footer Class Name</em>' attribute.
	 * @see #setFooterClassName(String)
	 * @see it.naturtalent.office.model.address.AddressPackage#getFooterClass_FooterClassName()
	 * @model
	 * @generated
	 */
	String getFooterClassName();

	/**
	 * Sets the value of the '{@link it.naturtalent.office.model.address.FooterClass#getFooterClassName <em>Footer Class Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Footer Class Name</em>' attribute.
	 * @see #getFooterClassName()
	 * @generated
	 */
	void setFooterClassName(String value);

	/**
	 * Returns the value of the '<em><b>Footer Class Foot Notes</b></em>' containment reference list.
	 * The list contents are of type {@link it.naturtalent.office.model.address.FootNotes}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Footer Class Foot Notes</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Footer Class Foot Notes</em>' containment reference list.
	 * @see it.naturtalent.office.model.address.AddressPackage#getFooterClass_FooterClassFootNotes()
	 * @model containment="true"
	 * @generated
	 */
	EList<FootNotes> getFooterClassFootNotes();

} // FooterClass
