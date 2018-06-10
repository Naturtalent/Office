/**
 */
package it.naturtalent.office.model.address;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Kontakte</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link it.naturtalent.office.model.address.Kontakte#getKontakte <em>Kontakte</em>}</li>
 * </ul>
 *
 * @see it.naturtalent.office.model.address.AddressPackage#getKontakte()
 * @model
 * @generated
 */
public interface Kontakte extends EObject
{
	/**
	 * Returns the value of the '<em><b>Kontakte</b></em>' containment reference list.
	 * The list contents are of type {@link it.naturtalent.office.model.address.Kontakt}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Kontakte</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Kontakte</em>' containment reference list.
	 * @see it.naturtalent.office.model.address.AddressPackage#getKontakte_Kontakte()
	 * @model containment="true"
	 * @generated
	 */
	EList<Kontakt> getKontakte();

} // Kontakte
